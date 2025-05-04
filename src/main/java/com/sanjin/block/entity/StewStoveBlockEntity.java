package com.sanjin.block.entity;

import com.sanjin.menu.StewStoveMenu;
import com.sanjin.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StewStoveBlockEntity extends BlockEntity implements MenuProvider, EntityBlock {

    private final ItemStackHandler inventory = new ItemStackHandler(8);
    private final ContainerData data;
    private int waterLevel;
    private int cookTime;
    private boolean isCooking;

    public StewStoveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEW_STOVE_BLOCK_ENTITY.get(),pos, state);
        this.data = new SimpleContainerData(3);
        this.waterLevel = 0;
        this.cookTime = 0;
        this.isCooking = false;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, StewStoveBlockEntity blockEntity) {
        if (blockEntity != null) {
            blockEntity.tick(pos, state, blockEntity);
        }
    }
    public  void tick( BlockPos pos, BlockState state, StewStoveBlockEntity entity){

        if (this.waterLevel == 0 && this.inventory.getStackInSlot(4).getItem() == Items.WATER_BUCKET){
            this.waterLevel = 5;
            this.inventory.setStackInSlot(4, new ItemStack(Items.BUCKET));
        }
        if (this.isCooking){
            this.cookTime++;
            if (this.cookTime >= 200){
                this.finishCooking();
            }
        }else {
            this.checkForCooking();
        }
    }

    private void checkForCooking(){
        if (this.waterLevel != 0 && this.hasFuel() && this.hasValiRecipe()){
            this.startCooking();
        }
    }
    private void finishCooking(){
        this.cookTime = 0;
        this.isCooking = false;
    }
    private void startCooking() {
        this.isCooking = true;
        this.cookTime = 0;
        this.waterLevel -= 1;
        // 消耗可燃物槽中的燃料
        // ...
        this.inventory.setStackInSlot(7, new ItemStack(Items.WATER_BUCKET));
    }
    private boolean hasValiRecipe(){
        return true;
    }
    private boolean hasFuel(){
        return true;
    }
    private void removeContainer(){

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
