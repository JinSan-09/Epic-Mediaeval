package com.sanjin.entity;

import com.sanjin.component.PeasantRelationshipComponent;
import com.sanjin.data.PeasantInteractionHistory;
import com.sanjin.entity.mobentity.FemalePeasantEntity;
import com.sanjin.enums.PeasantInteractionType;
import com.sanjin.gui.provider.PeasantMenuProvider;

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

import java.util.Arrays;
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

    protected String peasantName = "Unknown";
    protected String textureLocation = "textures/entity/steve.png";
    protected boolean slimModel = false;

    private ItemStack headItem = ItemStack.EMPTY;
    private ItemStack chestItem = ItemStack.EMPTY;
    private ItemStack legsItem = ItemStack.EMPTY;
    private ItemStack feetItem = ItemStack.EMPTY;
    private ItemStack mainHandItem = ItemStack.EMPTY;
    private ItemStack offHandItem = ItemStack.EMPTY;

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
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void addAdditionalSaveData(@Nonnull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        HolderLookup.Provider provider = this.level().registryAccess();
        tag.putString("PeasantName", getPeasantName());
        tag.putString("Texture", getTextureLocation());
        tag.putBoolean("Slim", isSlimModel());
        tag.put("relationships", relationshipComponent.toNBT(provider));

        CompoundTag equipmentTag = new CompoundTag();
        UUID ownerUUID = this.getOwnerUUID();

        if (!headItem.isEmpty()) {
            CompoundTag headTag = new CompoundTag();
            headItem.save(this.level().registryAccess(), headTag);
            equipmentTag.put("HeadItem", headTag);
        }
        if (!chestItem.isEmpty()) {
            CompoundTag chestTag = new CompoundTag();
            chestItem.save(this.level().registryAccess(), chestTag);
            equipmentTag.put("ChestItem", chestTag);
        }
        if (!legsItem.isEmpty()) {
            CompoundTag legsTag = new CompoundTag();
            legsItem.save(this.level().registryAccess(), legsTag);
            equipmentTag.put("LegsItem", legsTag);
        }
        if (!feetItem.isEmpty()) {
            CompoundTag feetTag = new CompoundTag();
            feetItem.save(this.level().registryAccess(), feetTag);
            equipmentTag.put("FeetItem", feetTag);
        }
        if (!mainHandItem.isEmpty()) {
            CompoundTag mainHandTag = new CompoundTag();
            mainHandItem.save(this.level().registryAccess(), mainHandTag);
            equipmentTag.put("MainHandItem", mainHandTag);
        }
        if (!offHandItem.isEmpty()) {
            CompoundTag offHandTag = new CompoundTag();
            offHandItem.save(this.level().registryAccess(), offHandTag);
            equipmentTag.put("OffHandItem", offHandTag);
        }

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
        if (tag.contains("Equipment")) {
            CompoundTag equipmentTag = tag.getCompound("Equipment");

            if (equipmentTag.contains("HeadItem")) {
                headItem = ItemStack.parseOptional(this.level().registryAccess(), equipmentTag.getCompound("HeadItem"));
            }

            if (equipmentTag.contains("ChestItem")) {
                chestItem = ItemStack.parseOptional(this.level().registryAccess(), equipmentTag.getCompound("ChestItem"));
            }

            if (equipmentTag.contains("LegsItem")) {
                legsItem = ItemStack.parseOptional(this.level().registryAccess(), equipmentTag.getCompound("LegsItem"));
            }

            if (equipmentTag.contains("FeetItem")) {
                feetItem = ItemStack.parseOptional(this.level().registryAccess(), equipmentTag.getCompound("FeetItem"));
            }

            if (equipmentTag.contains("MainHandItem")) {
                mainHandItem = ItemStack.parseOptional(this.level().registryAccess(), equipmentTag.getCompound("MainHandItem"));
                this.setItemInHand(InteractionHand.MAIN_HAND, mainHandItem.copy());
            }

            if (equipmentTag.contains("OffHandItem")) {
                offHandItem = ItemStack.parseOptional(this.level().registryAccess(), equipmentTag.getCompound("OffHandItem"));
                this.setItemInHand(InteractionHand.OFF_HAND, offHandItem.copy());
            }
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
    public void setItemSlot(@Nonnull EquipmentSlot slot, @Nonnull ItemStack stack) {
        switch (slot) {
            case HEAD -> this.headItem = stack;
            case CHEST -> this.chestItem = stack;
            case LEGS -> this.legsItem = stack;
            case FEET -> this.feetItem = stack;
            case MAINHAND -> {
                this.mainHandItem = stack;
                super.setItemSlot(slot, stack);
            }
            case OFFHAND -> {
                this.offHandItem = stack;
                super.setItemSlot(slot, stack);
            }
        }
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@Nonnull EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> this.headItem;
            case CHEST -> this.chestItem;
            case LEGS -> this.legsItem;
            case FEET -> this.feetItem;
            case MAINHAND -> this.mainHandItem;
            case OFFHAND -> this.offHandItem;
            case BODY -> ItemStack.EMPTY;
        };
    }

    @Override
    public float getEquipmentDropChance(@Nonnull EquipmentSlot slot) {
        return 0.1F;
    }

    @Override
    public @NotNull InteractionResult mobInteract(@Nonnull Player player, @Nonnull InteractionHand hand) {
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

    private void initializeRandomData() {
        RandomSource random = this.getRandom();

        String[] textures = getDefaultTexturePaths();
        System.out.println(Arrays.toString(textures));
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
        this.peasantName = name;
        this.entityData.set(DATA_NAME, name);
    }

    public String getTextureLocation() {
        return this.entityData.get(DATA_TEXTURE);
    }

    public void setTextureLocation(String location) {
        this.textureLocation = location;
        this.entityData.set(DATA_TEXTURE, location);
    }

    public boolean isSlimModel() {
        return this.entityData.get(DATA_SLIM);
    }

    public void setSlimModel(boolean slim) {
        this.slimModel = slim;
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
