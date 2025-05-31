package com.sanjin.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractPeasantEntity extends PathfinderMob {

    private static final EntityDataAccessor<String> DATA_NAME = SynchedEntityData.defineId(AbstractPeasantEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> DATA_TEXTURE = SynchedEntityData.defineId(AbstractPeasantEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> DATA_SLIM = SynchedEntityData.defineId(AbstractPeasantEntity.class, EntityDataSerializers.BOOLEAN);

    protected String peasantName = "Unknown";
    protected String textureLocation = "textures/entity/steve.png";
    protected boolean slimModel = false;

    private ItemStack headItem = ItemStack.EMPTY;
    private ItemStack chestItem = ItemStack.EMPTY;
    private ItemStack legsItem = ItemStack.EMPTY;
    private ItemStack feetItem = ItemStack.EMPTY;
    private ItemStack mainHandItem = ItemStack.EMPTY;
    private ItemStack offHandItem = ItemStack.EMPTY;

    protected Player targetPlayer;
    protected int interactionCooldown = 0;

    public AbstractPeasantEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    protected AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder var1){
        super.defineSynchedData(var1);
        var1.define(DATA_NAME, "Unknown");
        var1.define(DATA_TEXTURE, "textures/entity/steve.png");
        var1.define(DATA_SLIM, isSlimDefault());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // 添加AI目标
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        // 添加目标选择AI
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    protected abstract String[] getDefaultTexturePaths();
    protected abstract boolean isSlimDefault();
    // Return random name list
    protected abstract String[] getRandomFirstNameOptions();
    protected abstract String[] getRandomLastNameOptions();

    @Override
    public void tick() {
        super.tick();

        // 处理交互冷却
        if (interactionCooldown > 0) {
            interactionCooldown--;
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("PeasantName", getPeasantName());
        tag.putString("Texture", getTextureLocation());
        tag.putBoolean("Slim", isSlimModel());

        CompoundTag equipmentTag = new CompoundTag();

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
    }
    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains("PeasantName")) {
            setPeasantName(tag.getString("PeasantName"));
        }

        if (tag.contains("Texture")) {
            setTextureLocation(tag.getString("Texture"));
        }

        if (tag.contains("Slim")) {
            setSlimModel(tag.getBoolean("Slim"));
        }

        // 加载装备
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
    }

    public SpawnGroupData finalizeMobSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull EntitySpawnReason reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData);

        RandomSource random = level.getRandom();
        // 初始化随机模型和纹理
        String[] textures = getDefaultTexturePaths();
        setSlimModel(isSlimDefault());
        String texture = textures[random.nextInt(textures.length)];
        setTextureLocation(texture);

        // 随机选择一个名字
        String[] firstNames = getRandomFirstNameOptions();
        String[] lastNames = getRandomLastNameOptions();
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        setPeasantName(randomFirstName+"."+randomLastName);

        return data;
    }

    protected void handlePlayerInteraction(Player player) {
        player.displayClientMessage(Component.literal("Hello, my name is " + this.getPeasantName()), false);
        this.targetPlayer = player;
    }

    @Override
    public @NotNull Component getName() {
        return Component.literal(this.getPeasantName());
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

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        SoundType soundType = blockState.getSoundType(level(),pos,this);
        this.playSound(soundType.getStepSound(), 0.15F, 1.0F);
    }

    // 受伤声音
    protected abstract SoundEvent getHurtSoundEvent();

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return getHurtSoundEvent();
    }

    // 死亡声音
    protected abstract SoundEvent getDeathSoundEvent();

    @Override
    protected SoundEvent getDeathSound() {
        return getDeathSoundEvent();
    }

    // 设置装备物品
    @Override
    public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
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
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
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
    public float getEquipmentDropChance(@NotNull EquipmentSlot slot) {
        return 0.1F;
    }
}
