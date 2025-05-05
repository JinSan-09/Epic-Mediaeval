package com.sanjin.block.entity;

import com.sanjin.menu.StewStoveMenu;
import com.sanjin.register.ModBlockEntities;
import com.sanjin.register.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StewStoveBlockEntity extends BlockEntity implements MenuProvider, EntityBlock {

    private final ItemStackHandler inventory = new ItemStackHandler(8);
    private final ContainerData data = new SimpleContainerData(3);
    private int waterLevel;
    private int burnTime;
    private int cookTime;
    private boolean isCooking;

    public StewStoveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEW_STOVE_BLOCK_ENTITY.get(),pos, state);
        this.burnTime = 0;
        this.waterLevel = 0;
        this.cookTime = 0;
        this.isCooking = false;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, StewStoveBlockEntity blockEntity) {
        blockEntity.updateData();
        if (blockEntity != null) {
            blockEntity.tick(pos, state, blockEntity);
        }
    }
    public  void tick( BlockPos pos, BlockState state, StewStoveBlockEntity entity) {
        int totalFuel = 0;
        this.addWater(level);

        for (int i = 0; i < 4; i++) {
            if (this.inventory.getStackInSlot(i).is(Items.BUCKET) && this.waterLevel != 0) {
                cookTime++;
                if (cookTime >= 300) {
                    finishCooking(i);
                }
            }
        }
    }
    private void startCooking() {
       this.isCooking = true;
        this.cookTime = 0;
        this.waterLevel -= 1;
        // 消耗可燃物槽中的燃料
        // ...
    }
    private void finishCooking(int i) {
        int count = this.inventory.getStackInSlot(i).getCount();
        if (this.inventory.getStackInSlot(7).is(ModItems.LARGE_WOODEN_BOWL)) {
            this.inventory.getStackInSlot(7).setCount(this.inventory.getStackInSlot(7).getCount() + 1);
        }else {
            this.inventory.setStackInSlot(7, new ItemStack(ModItems.LARGE_WOODEN_BOWL.asItem()));
        }
        this.inventory.getStackInSlot(i).setCount(count-1);
        this.waterLevel -= 1;
        cookTime = 0;
        setChanged();
    }
    private void addWater(Level level) {
        if (this.waterLevel == 0 && this.inventory.getStackInSlot(4).getItem() == Items.WATER_BUCKET){
            this.waterLevel = 5;
            this.inventory.setStackInSlot(4, new ItemStack(Items.BUCKET));
            level.playSound(null,worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS,1.0f,1.0f);
            setChanged();
        }
    }
    private void addFuel(int i){
        if (this.inventory.getStackInSlot(5).is(Items.COAL) && i == 0){
            this.inventory.getStackInSlot(5).setCount(this.inventory.getStackInSlot(5).getCount() - 1);
            i = 100;
        }
    }
    private void removeContainer(){

    }
    private void updateData() {
        data.set(0, waterLevel);
        data.set(1, burnTime);
        data.set(2, cookTime);
    }
    private boolean hasValiRecipe(){
        return true;
    }
    private boolean hasFuel(){
        return true;
    }
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        this.waterLevel = tag.getInt("WaterLevel");
        this.cookTime = tag.getInt("CookTime");
        this.isCooking = tag.getBoolean("IsCooking");
    }
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("WaterLevel", this.waterLevel);
        tag.putInt("CookTime", this.cookTime);
        tag.putBoolean("IsCooking", this.isCooking);
    }

    public ContainerData getData() {
        return this.data;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.stew_stove.text");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new StewStoveMenu(id, inventory, this.inventory, ContainerLevelAccess.NULL);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        return new StewStoveBlockEntity(pos, state);
    }
}
