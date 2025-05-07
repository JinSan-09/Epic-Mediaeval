package com.sanjin.block.entity;

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

    // The number of all slots
    private static final int WATER_SLOT = 4;
    private static final int FUEL_SLOT = 5;
    private static final int CONTAINER_SLOT = 6;
    private static final int OUTPUT_SLOT = 7;
    private static final int MATERIAL_SLOTS_START = 0;
    private static final int MATERIAL_SLOTS_COUNT = 4;

    private final ContainerData data = new SimpleContainerData(3);
    private StewStoveRecipe currentRecipe;
    private StewStoveRecipeInput recipeInput;
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

    public static void serverTick(Level level, BlockPos pos, BlockState state, StewStoveBlockEntity blockEntity) {
        blockEntity.tick(pos,state);
        blockEntity.updateData();
        blockEntity.setChanged();
    }
    public  void tick( BlockPos pos, BlockState state) {
        addWater(level);

        if (isCooking) {
            if (level != null) {
                level.playSound(null, worldPosition, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            // Check for sufficient fuel and water
            if (burnTime <= 0) {
                // If the fuel runs out, try adding new fuel
                if (!addFuel()) {
                    // If you cannot add new fuel, stop cooking
                    isCooking = false;
                    cookTime = 0;
                    setChanged();
                    return;
                }
            } else {
                burnTime--;
            }
            // Check if there is enough water
            if (waterLevel <= 0) {
                isCooking = false;
                cookTime = 0;
                setChanged();
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

        // 如果当前没有燃烧中的燃料，添加新燃料
        if (burnTime <= 0) {
            addFuel();
        }

        setChanged();
    }
    private void finishCooking() {
        if (currentRecipe == null || level == null) return;
        ItemStack outputItem = currentRecipe.getResult().copy();

        // 检查输出槽是否可以放入物品
        if (canAddOutput(outputItem)) {
            consumeIngredients();
            consumeContainer();
            addOutput(outputItem);
            waterLevel = Math.max(0, waterLevel - 1);

            // 重置烹饪状态
            cookTime = 0;
            isCooking = false;
            currentRecipe = null;
            level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
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
        if (this.waterLevel < 5 && this.inventory.getStackInSlot(WATER_SLOT).getItem() == Items.WATER_BUCKET) {
            this.waterLevel = 5;
            this.inventory.setStackInSlot(WATER_SLOT, new ItemStack(Items.BUCKET));
            level.playSound(null, worldPosition, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
            setChanged();
        }
    }
    private boolean canAddFuel() {
        return !inventory.getStackInSlot(FUEL_SLOT).isEmpty() && isFuel(inventory.getStackInSlot(FUEL_SLOT));
    }
    private boolean addFuel() {
        ItemStack fuelStack = inventory.getStackInSlot(FUEL_SLOT);

        if (!fuelStack.isEmpty() && isFuel(fuelStack)) {
            // 根据不同燃料设定燃烧时间
            if (fuelStack.is(Items.COAL) || fuelStack.is(Items.CHARCOAL)) {
                burnTime += 1600; // 煤炭/木炭燃烧时间
            } else if (fuelStack.is(Items.COAL_BLOCK)) {
                burnTime += 16000; // 煤炭块燃烧时间
            } else if (fuelStack.is(Items.LAVA_BUCKET)) {
                burnTime += 20000; // 熔岩桶燃烧时间
                inventory.setStackInSlot(FUEL_SLOT, new ItemStack(Items.BUCKET)); // 返回空桶
                setChanged();
                return true;
            } else {
                burnTime += 200; // 其他可燃物默认燃烧时间
            }

            // 消耗燃料物品
            fuelStack.shrink(1);
            inventory.setStackInSlot(FUEL_SLOT, fuelStack);
            setChanged();
            return true;
        }

        return false;
    }
    private boolean isFuel(ItemStack stack) {
        // 简单判断常用的燃料物品
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
    private void updateData() {
        data.set(0, waterLevel);
        data.set(1, burnTime);
        data.set(2, cookTime);
    }

    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(tag, provider);
        this.waterLevel = tag.getInt("WaterLevel");
        this.burnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
        this.isCooking = tag.getBoolean("IsCooking");

        CompoundTag inventoryTag = tag.getCompound("Inventory");
        this.inventory.deserializeNBT(provider, inventoryTag);
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

    public ContainerData getData() {
        return this.data;
    }

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
