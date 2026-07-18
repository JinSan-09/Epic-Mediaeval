package com.sanjin.entity;

import com.sanjin.component.PeasantRelationshipComponent;
import com.sanjin.data.PeasantInteractionHistory;
import com.sanjin.entity.mobentity.FemalePeasantEntity;
import com.sanjin.enums.PeasantInteractionType;
import com.sanjin.gui.provider.PeasantMenuProvider;
import com.sanjin.helper.PeasantGiftHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

public abstract class AbstractPeasantEntity extends PathfinderMob {

    private static final EntityDataAccessor<String> DATA_NAME = SynchedEntityData.defineId(AbstractPeasantEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_TEXTURE = SynchedEntityData.defineId(AbstractPeasantEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> DATA_SLIM = SynchedEntityData.defineId(AbstractPeasantEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER = SynchedEntityData.defineId(AbstractPeasantEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private final PeasantRelationshipComponent relationshipComponent = new PeasantRelationshipComponent();

    public AbstractPeasantEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);

        if (!level.isClientSide()) {
            initializeRandomData();
        }
    }

    @Override
    public void actuallyHurt(@Nonnull ServerLevel level, @Nonnull DamageSource damageSource, float damage) {
        if (damageSource.getEntity() instanceof Player player && !this.level().isClientSide) {
            handlePlayerDamage(player, damage);
        }
        super.actuallyHurt(level, damageSource, damage);
    }

    @Override
    protected void defineSynchedData(@Nonnull SynchedEntityData.Builder var1){
        super.defineSynchedData(var1);
        var1.define(DATA_NAME, "Unknown");
        var1.define(DATA_TEXTURE, "textures/entity/steve.png");
        var1.define(DATA_SLIM, isSlimDefault());
        var1.define(DATA_OWNER, Optional.empty());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("PeasantName", getPeasantName());
        tag.putString("Texture", getTextureLocation());
        tag.putBoolean("Slim", isSlimModel());
        tag.put("relationships", relationshipComponent.toNBT(this.level().registryAccess()));
        UUID ownerUUID = this.getOwnerUUID();
        if (ownerUUID != null) {
            tag.putUUID("Owner", ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        HolderLookup.Provider provider = this.level().registryAccess();

        if (tag.contains("PeasantName")) {
            setPeasantName(tag.getString("PeasantName"));
        }
        if (tag.contains("Texture")) {
            setTextureLocation(tag.getString("Texture"));
        }
        if (tag.contains("Slim")) {
            setSlimModel(tag.getBoolean("Slim"));
        }
        if (tag.contains("relationships")) {
            relationshipComponent.fromNBT(tag.getCompound("relationships"), provider);
        }
        if (tag.contains("Owner")) {
            this.setOwnerUUID(tag.getUUID("Owner"));
        }
    }

    @Override
    public @NotNull Component getName() {
        return Component.literal(this.getPeasantName());
    }

    @Override
    protected void playStepSound(@Nonnull BlockPos pos, @Nonnull BlockState blockState) {
        SoundType soundType = blockState.getSoundType(level(),pos,this);
        this.playSound(soundType.getStepSound(), 0.15F, 1.0F);
    }

    protected abstract SoundEvent getHurtSoundEvent();

    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return getHurtSoundEvent();
    }

    protected abstract SoundEvent getDeathSoundEvent();

    @Override
    protected SoundEvent getDeathSound() {
        return getDeathSoundEvent();
    }

    @Override
    public float getEquipmentDropChance(@Nonnull EquipmentSlot slot) {
        return 0.1F;
    }

    @Override
    public @NotNull InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
        if (!this.level().isClientSide && player.isShiftKeyDown()) {
            if (hand != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }
            return handleGiftInteraction(player);
        }

        PeasantMenuProvider menuProvider = new PeasantMenuProvider(this);
        if (!this.level().isClientSide) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (this.isAlive() && player.distanceToSqr(this) < 16.0D) {
                    serverPlayer.openMenu(menuProvider, (RegistryFriendlyByteBuf buffer) -> {
                        buffer.writeInt(this.getId());
                    });
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private InteractionResult handleGiftInteraction(@NotNull Player player) {
        ItemStack giftStack = player.getMainHandItem();
        UUID playerId = player.getUUID();

        if (giftStack.isEmpty()) {
            player.displayClientMessage(Component.translatable("message.peasant.gift.empty"), true);
            return InteractionResult.FAIL;
        }

        if (this.hasOwner() && !this.isOwner(player)) {
            player.displayClientMessage(Component.translatable("message.peasant.gift.other_owner", this.getName()), true);
            return InteractionResult.FAIL;
        }

        if (relationshipComponent.isHostileTowards(playerId)) {
            player.displayClientMessage(Component.translatable("message.peasant.gift.hostile", this.getName()), true);
            return InteractionResult.FAIL;
        }

        PeasantGiftHelper.GiftValue giftValue = PeasantGiftHelper.getGiftValue(giftStack);
        if (giftValue == null) {
            player.displayClientMessage(Component.translatable("message.peasant.gift.unwanted", this.getName()), true);
            return InteractionResult.FAIL;
        }

        ItemStack recordedGift = giftStack.copy();
        recordedGift.setCount(1);

        relationshipComponent.addFavorability(playerId, giftValue.favorability());
        relationshipComponent.addYield(playerId, giftValue.yield());
        relationshipComponent.addInteractionHistory(playerId, new PeasantInteractionHistory(
                PeasantInteractionType.GIFT_GIVING,
                giftValue.favorability(),
                giftValue.yield(),
                recordedGift,
                "玩家赠送了 " + recordedGift.getHoverName().getString()
        ));

        if (!player.getAbilities().instabuild) {
            giftStack.shrink(1);
        }

        player.displayClientMessage(Component.translatable(
                "message.peasant.gift.accepted",
                this.getName(),
                recordedGift.getHoverName(),
                giftValue.favorability(),
                giftValue.yield()
        ), true);
        return InteractionResult.CONSUME;
    }

    private void initializeRandomData() {
        RandomSource random = this.getRandom();

        String[] textures = getDefaultTexturePaths();
        if (textures.length > 0) {
            String texture = textures[random.nextInt(textures.length)];
            setTextureLocation(texture);
        }
        setSlimModel(isSlimDefault());

        String[] firstNames = getRandomFirstNameOptions();
        String[] lastNames = getRandomLastNameOptions();
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        setPeasantName(randomFirstName + " " + randomLastName);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D)
                .add(Attributes.ATTACK_SPEED, 1.0D);
    }

    public String getPeasantName() {
        return this.entityData.get(DATA_NAME);
    }

    public void setPeasantName(String name) {
        this.entityData.set(DATA_NAME, name);
    }

    public String getTextureLocation() {
        return this.entityData.get(DATA_TEXTURE);
    }

    public void setTextureLocation(String location) {
        this.entityData.set(DATA_TEXTURE, location);
    }

    public boolean isSlimModel() {
        return this.entityData.get(DATA_SLIM);
    }

    public void setSlimModel(boolean slim) {
        this.entityData.set(DATA_SLIM, slim);
    }

    protected void handlePlayerDamage(@NotNull Player player, float damage) {
        UUID playerId = player.getUUID();

        int favorabilityLoss = Math.round(damage * 2);
        int yieldLoss = Math.round(damage * 3);

        relationshipComponent.addFavorability(playerId, -favorabilityLoss);
        relationshipComponent.addYield(playerId, -yieldLoss);

        PeasantInteractionHistory negativeHistory = new PeasantInteractionHistory(
                PeasantInteractionType.HOSTILE_ACTION,
                -favorabilityLoss,
                -yieldLoss,
                player.getMainHandItem(),
                "玩家攻击了农民 (伤害: " + damage + ")"
        );
        relationshipComponent.addInteractionHistory(playerId, negativeHistory);

        onPlayerAttack(player, damage);
    }

    public @Nullable UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNER).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID ownerUUID) {
        this.entityData.set(DATA_OWNER, Optional.ofNullable(ownerUUID));
    }

    public @Nullable Player getOwner() {
        UUID ownerUUID = this.getOwnerUUID();
        if (ownerUUID == null) {
            return null;
        }
        return this.level().getPlayerByUUID(ownerUUID);
    }

    public boolean hasOwner() {
        return this.getOwnerUUID() != null;
    }

    public boolean isOwner(Player player) {
        UUID ownerUUID = this.getOwnerUUID();
        return ownerUUID != null && ownerUUID.equals(player.getUUID());
    }

    public boolean shouldBeHostileToUndead() {
        return !hasOwner();
    }

    public boolean shouldFlee() {
        return this.getHealth() < 10.0F;
    }

    public LivingEntity findAttackerOfNearbyFemale() {
        if (this.getHealth() < 10.0F) {
            return null;
        }

        Level level = this.level();
        AABB searchArea = this.getBoundingBox().inflate(10.0D);

        List<FemalePeasantEntity> nearbyFemales = level.getEntitiesOfClass(
                FemalePeasantEntity.class,
                searchArea
        );

        for (FemalePeasantEntity female : nearbyFemales) {
            LivingEntity lastHurtBy = female.getLastHurtByMob();
            if (lastHurtBy != null && lastHurtBy.isAlive() && female.distanceToSqr(lastHurtBy) <= 100.0D && lastHurtBy != this) {
                return lastHurtBy;
            }
        }

        return null;
    }

    // Empty method
    protected void onPlayerAttack(Player player, float damage) {
    }

    public boolean shouldBeHostileToPlayer(@NotNull Player player) {
        return relationshipComponent.isHostileTowards(player.getUUID());
    }

    public PeasantRelationshipComponent getRelationshipComponent() {
        return relationshipComponent;
    }

    public abstract String[] getDefaultTexturePaths();

    public abstract boolean isSlimDefault();

    // Return random name list
    public abstract String[] getRandomFirstNameOptions();

    public abstract String[] getRandomLastNameOptions();
}
