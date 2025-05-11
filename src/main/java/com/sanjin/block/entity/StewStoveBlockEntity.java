package com.sanjin.block.entity;

import com.sanjin.block.StewStoveBlock;
import com.sanjin.menu.StewStoveMenu;
import com.sanjin.recipe.StewStoveRecipe;
import com.sanjin.recipe.StewStoveRecipeInput;
import com.sanjin.register.ModBlockEntities;
import com.sanjin.register.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class StewStoveBlockEntity extends BlockEntity implements MenuProvider, EntityBlock {

    // ========= The number of all slots =========
    private static final int WATER_SLOT = 4;
    private static final int FUEL_SLOT = 5;
    private static final int CONTAINER_SLOT = 6;
    private static final int OUTPUT_SLOT = 7;
    private static final int MATERIAL_SLOTS_START = 0;
    private static final int MATERIAL_SLOTS_COUNT = 4;

    private final ContainerData data = new SimpleContainerData(4);
    private StewStoveRecipe currentRecipe;
    private int waterLevel;
    private int burnTime;
    private int cookTime;
    private int cookTimeTotal;
    private boolean isCooking;

    private final ItemStackHandler inventory = new ItemStackHandler(8){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (slot >= MATERIAL_SLOTS_START && slot < MATERIAL_SLOTS_START + MATERIAL_SLOTS_COUNT || slot == CONTAINER_SLOT) {
                tryStartCooking();
            }
            if (slot == OUTPUT_SLOT) {
                updateHasSoupState();
            }
        }
    };

    public StewStoveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STEW_STOVE_BLOCK_ENTITY.get(),pos, state);
        this.burnTime = 0;
        this.waterLevel = 0;
        this.cookTime = 0;
        this.cookTimeTotal = 300;
        this.isCooking = false;
    }

    // ========== Tick logic =========
    public static void serverTick(Level level, BlockPos pos, BlockState state, StewStoveBlockEntity blockEntity) {
        blockEntity.tick(pos,state);
        blockEntity.updateData();
        blockEntity.setChanged();
    }
    public  void tick( BlockPos pos, BlockState state) {
        addWater(level);
        // Check if the Stew stove should be lit
        if (burnTime > 0) {
            burnTime--;
            if (level != null) {
                level.setBlock(pos, state.setValue(StewStoveBlock.LIT, true), 3);
            }
        }else {
            if (level != null) {
                level.setBlock(pos, state.setValue(StewStoveBlock.LIT, false), 3);
            }
        }
        if (isCooking) {
            if (level != null) {
                level.playSound(null, worldPosition, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            // Add fuel if it can, else stop cooking
            if (burnTime <= 0 && canAddFuel()) {
                addFuel();
            } else if (burnTime <= 0 && !canAddFuel()) {
                stopCooking(pos,state);
                return;
            }
            // Check if there is enough water
            if (waterLevel <= 0) {
                stopCooking(pos,state);
                return;
            }
            cookTime++;
            if (cookTime >= cookTimeTotal) {
                finishCooking();
            }
            setChanged();
        } else {
            tryStartCooking();
        }
    }
    public void updateHasSoupState() {
        if (this.level != null && !this.level.isClientSide()) {
            // 检查输出槽是否有物品
            boolean hasSoup = !inventory.getStackInSlot(OUTPUT_SLOT).isEmpty();

            // 获取当前方块状态
            BlockState currentState = this.level.getBlockState(this.worldPosition);

            // 如果状态不同，更新方块状态
            if (currentState.getValue(StewStoveBlock.HAS_SOUP) != hasSoup) {
                this.level.setBlock(
                        this.worldPosition,
                        currentState.setValue(StewStoveBlock.HAS_SOUP, hasSoup), 3 // 发送到客户端的标志
                );
            }
        }
    }
    private void stopCooking(BlockPos pos, BlockState state) {
        isCooking = false;
        cookTime = 0;
        setChanged();
    }
    private void tryStartCooking() {
        if (level == null || isCooking) return;
        Optional<StewStoveRecipe> recipe = getValidRecipe();
        if (recipe.isPresent() && waterLevel > 0 && (burnTime > 0 || canAddFuel())) {
            this.currentRecipe = recipe.get();
            startCooking();
            setChanged();
        }
    }
    private void startCooking() {
        if (this.currentRecipe == null) return;

        this.isCooking = true;
        this.cookTime = 0;
        this.cookTimeTotal = this.currentRecipe.getCookingTime();

        // Add fuel if fuel is not enough
        if (burnTime <= 0) {
            addFuel();
        }
        setChanged();
    }
    private void finishCooking() {
        if (currentRecipe == null || level == null) return;
        ItemStack outputItem = currentRecipe.getResult().copy();

        // Check if the output sort can put result
        if (canAddOutput(outputItem)) {
            consumeIngredients();
            consumeContainer();
            addOutput(outputItem);
            waterLevel = Math.max(0, waterLevel - 1);

            // Reset cooking status
            cookTime = 0;
            isCooking = false;
            currentRecipe = null;
            level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);

            updateHasSoupState();
            setChanged();
        }
    }
    private void consumeIngredients() {
        // 从材料槽中查找并消耗所需材料
        for (int i = MATERIAL_SLOTS_START; i < MATERIAL_SLOTS_COUNT; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getCount() > 1) {
                stack.shrink(1);
                inventory.setStackInSlot(i, stack);
            }else {
                inventory.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }
    private void consumeContainer() {
        ItemStack containerStack = inventory.getStackInSlot(CONTAINER_SLOT);
        if (!containerStack.isEmpty() && containerStack.getCount() > 1) {
            containerStack.shrink(1);
            inventory.setStackInSlot(CONTAINER_SLOT, containerStack);
        }else {
            inventory.setStackInSlot(CONTAINER_SLOT, ItemStack.EMPTY);
        }
    }
    private boolean canAddOutput(ItemStack outputItem) {
        ItemStack currentOutput = inventory.getStackInSlot(OUTPUT_SLOT);

        if (currentOutput.isEmpty()) {
            return true;
        }

        if (!ItemStack.isSameItem(currentOutput, outputItem)) {
            return false;
        }

        int newCount = currentOutput.getCount() + outputItem.getCount();
        return newCount <= currentOutput.getMaxStackSize();
    }
    private void addOutput(ItemStack outputItem) {
        ItemStack currentOutput = inventory.getStackInSlot(OUTPUT_SLOT);

        if (currentOutput.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, outputItem);
        } else {
            currentOutput.grow(outputItem.getCount());
        }
    }
    private void addWater(Level level) {
        if (this.waterLevel == 0 && this.inventory.getStackInSlot(WATER_SLOT).getItem() == Items.WATER_BUCKET) {
            this.waterLevel = 10;
            this.inventory.setStackInSlot(WATER_SLOT, new ItemStack(Items.BUCKET));
            level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
            setChanged();
        }
    }
    private boolean canAddFuel() {
        return !inventory.getStackInSlot(FUEL_SLOT).isEmpty() && isFuel(inventory.getStackInSlot(FUEL_SLOT));
    }
    private void addFuel() {
        ItemStack fuelStack = inventory.getStackInSlot(FUEL_SLOT);
        if (canAddFuel()) {
            // set different burning time for different fuel
            if (fuelStack.is(Items.COAL) || fuelStack.is(Items.CHARCOAL)) {
                burnTime += 1600;
            } else if (fuelStack.is(Items.COAL_BLOCK)) {
                burnTime += 16000;
            } else if (fuelStack.is(Items.LAVA_BUCKET)) {
                burnTime += 20000;
                inventory.setStackInSlot(FUEL_SLOT, new ItemStack(Items.BUCKET));
                setChanged();
                return;
            } else {
                burnTime += 200;
            }

            // consume fuel
            fuelStack.shrink(1);
            inventory.setStackInSlot(FUEL_SLOT, fuelStack);
            setChanged();
        }
    }
    private boolean isFuel(ItemStack stack) {
        return stack.is(Items.COAL) || stack.is(Items.CHARCOAL) || stack.is(Items.COAL_BLOCK) ||
                stack.is(Items.LAVA_BUCKET) || stack.is(Items.BLAZE_ROD) ||
                stack.is(Items.STICK) || stack.is(Items.DRIED_KELP_BLOCK);
    }
    private Optional<StewStoveRecipe> getValidRecipe() {

        if (level == null) return Optional.empty();

        NonNullList<ItemStack> inputs = NonNullList.withSize(4, ItemStack.EMPTY);
        for (int i = 0; i < 4; i++) {
            inputs.set(i, inventory.getStackInSlot(i));
        }

        StewStoveRecipeInput recipeInput = new StewStoveRecipeInput(inputs,inventory.getStackInSlot(CONTAINER_SLOT));

        if (level instanceof ServerLevel) {
            RecipeManager recipeManager = level.getServer().getRecipeManager();
            return recipeManager.getRecipeFor(ModRecipes.STEW_STOVE_RECIPE_TYPE.get(), recipeInput, level)
                    .map(RecipeHolder::value);

        }

        return Optional.empty();
    }

    // ========= Transfer data to StewStoveMenu class =========
    private void updateData() {
        data.set(0, waterLevel);
        data.set(1, burnTime);
        data.set(2, cookTime);
        data.set(3,cookTimeTotal);
    }

    // ========= Transfer data between Server and Client ==========
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        this.waterLevel = tag.getInt("WaterLevel");
        this.burnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
        this.isCooking = tag.getBoolean("IsCooking");

        CompoundTag inventoryTag = tag.getCompound("Inventory");
        this.inventory.deserializeNBT(provider, inventoryTag);

        if (level != null) {
            // 更新点燃状态
            BlockState newState = getBlockState();
            if (burnTime > 0) {
                newState = newState.setValue(StewStoveBlock.LIT, true);
            } else {
                newState = newState.setValue(StewStoveBlock.LIT, false);
            }

            // 更新输出状态
            boolean hasSoup = !inventory.getStackInSlot(OUTPUT_SLOT).isEmpty();
            newState = newState.setValue(StewStoveBlock.HAS_SOUP, hasSoup);

            // 应用状态更新
            level.setBlock(worldPosition, newState, 3);
        }
    }
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("WaterLevel", this.waterLevel);
        tag.putInt("BurnTime", this.burnTime);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.cookTimeTotal);
        tag.putBoolean("IsCooking", this.isCooking);

        CompoundTag inventoryTag = this.inventory.serializeNBT(provider);
        tag.put("Inventory", inventoryTag);
    }

    // ========= Some Get-method =========
    public boolean getCookingState(){
        return this.isCooking;
    }
    public int getBurnTime() {
        return this.burnTime;
    }
    public int getWaterLevel() {
        return this.waterLevel;
    }
    public ContainerData getData() {
        return this.data;
    }

    // ========= Other needed settings =========
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.stew_stove.text");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        return new StewStoveMenu(id, inventory, this.inventory, ContainerLevelAccess.create(level, worldPosition), this.data);
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state){
        return new StewStoveBlockEntity(pos, state);
    }
}
