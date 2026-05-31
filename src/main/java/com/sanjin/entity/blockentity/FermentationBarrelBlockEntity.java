package com.sanjin.entity.blockentity;

import com.sanjin.block.FermentationBarrelBlock;
import com.sanjin.menu.FermentationBarrelMenu;
import com.sanjin.recipe.FermentationBarrelRecipe;
import com.sanjin.recipe.recipeinput.FermentationBarrelRecipeInput;
import com.sanjin.register.ModBlockEntities;
import com.sanjin.register.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
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

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;

public class FermentationBarrelBlockEntity extends BlockEntity implements MenuProvider, EntityBlock {

    private static final int MATERIAL_SLOTS_START = 0;
    private static final int MATERIAL_SLOTS_COUNT = 4;

    private FermentationBarrelRecipe currentRecipe;
    private int fermentationTime;
    private int fermentationTimeTotal;
    private boolean isFermentation;
    private boolean hasOutput;

    private final ItemStackHandler inventory = new ItemStackHandler(4){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!isFermentation && fermentedProductCount == 0) {
                tryStartFermentation();
            }
        }
    };
    private final ContainerData data = new SimpleContainerData(1);
    private ItemStack currentFermentationResult = ItemStack.EMPTY;
    private ItemStack currentRequiredContainer = ItemStack.EMPTY;
    private String group;
    private int fermentedProductCount;

    public FermentationBarrelBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FERMENTATION_BARREL_BLOCK_ENTITY.get(), pos, blockState);
        this.fermentationTime = 0;
        this.fermentationTimeTotal = 300;
        this.fermentedProductCount = 0;
        this.isFermentation = false;
        this.hasOutput = false;
    }

    // ========== Tick logic =========
    public static void serverTick(Level level, BlockPos pos, BlockState state, FermentationBarrelBlockEntity blockEntity) {
        blockEntity.tick(pos, state);
        blockEntity.updateData();
        blockEntity.tryStartFermentation();
        blockEntity.setChanged();
    }
    public void tick(BlockPos pos, BlockState state) {
        if (isFermentation) {
            Optional<FermentationBarrelRecipe> recipe = getValidRecipe();
            if (recipe.isEmpty()) {
                isFermentation = false;
                fermentationTime = 0;
                return;
            }
            if (level != null && level.getGameTime() % 20 == 0) {
                level.playSound(null, worldPosition, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 0.3f, 1.0f);
            }
            fermentationTime++;
            if (fermentationTime >= fermentationTimeTotal) {
                finishFermentation(pos, state);
            }
            setChanged();
        }else if (fermentedProductCount == 0) {
            tryStartFermentation();
        }
    }
    private void tryStartFermentation() {
        if (level == null || isFermentation || fermentedProductCount > 0) return;

        Optional<FermentationBarrelRecipe> recipe = getValidRecipe();
        if (recipe.isPresent()) {
            this.currentRecipe = recipe.get();
            this.currentFermentationResult = currentRecipe.getResult().copy();
            this.currentRequiredContainer = currentRecipe.getContainer().copy();
            this.group = currentRecipe.getGroup();
            startFermentation();
        }
    }
    private void startFermentation() {
        if (this.currentRecipe == null) return;

        this.isFermentation = true;
        this.fermentationTime = 0;
        this.fermentationTimeTotal = this.currentRecipe.getFermentationTime();
        setChanged();
    }
    private void finishFermentation(BlockPos pos, BlockState state) {
        if (currentRecipe == null || level == null) return;

        consumeIngredients();
        fermentedProductCount++;
        fermentationTime = 0;
        isFermentation = false;

        if (!state.getValue(FermentationBarrelBlock.FULL) && level != null) {
            level.setBlock(pos, state.setValue(FermentationBarrelBlock.FULL, true), 3);
        }

        if(level != null){
            level.playSound(null, worldPosition, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        
        tryStartFermentation();
        setChanged();
    }
    private void consumeIngredients() {
        for (int i = MATERIAL_SLOTS_START; i < MATERIAL_SLOTS_COUNT; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getCount() > 1) {
                stack.shrink(1);
                inventory.setStackInSlot(i, stack);
            }else {
                if (stack.getItem() == Items.MILK_BUCKET || stack.getItem() == Items.WATER_BUCKET) {
                    inventory.setStackInSlot(i, Items.BUCKET.getDefaultInstance());
                }else {
                    inventory.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        }
    }
    private Optional<FermentationBarrelRecipe> getValidRecipe() {
        if (level == null) return Optional.empty();

        NonNullList<ItemStack> inputs = NonNullList.withSize(4, ItemStack.EMPTY);
        for (int i = 0; i < 4; i++) {
            inputs.set(i, inventory.getStackInSlot(i));
        }

        FermentationBarrelRecipeInput recipeInput = new FermentationBarrelRecipeInput(inputs);

        if (level instanceof ServerLevel) {
            RecipeManager recipeManager = level.getServer().getRecipeManager();
            return recipeManager.getRecipeFor(ModRecipes.FERMENTATION_BARREL_RECIPE.get(), recipeInput, level)
                    .map(RecipeHolder::value);

        }
        return Optional.empty();
    }
    private boolean isMatchingContainer(ItemStack heldItem, ItemStack requiredContainer) {
        return !heldItem.isEmpty() && heldItem.getItem() == requiredContainer.getItem();
    }
    public InteractionResult extractFermentedItem(Player player, ItemStack heldItem){
        if (fermentedProductCount <= 0 || currentFermentationResult.isEmpty() || currentRequiredContainer.isEmpty()){
            return InteractionResult.PASS;
        }

        // If the container in player's hand is not correct
        if (!isMatchingContainer(heldItem, currentRequiredContainer)) {
            if (level != null && !level.isClientSide) {
                player.displayClientMessage(Component.translatable("message.fermentation_barrel.wrong_container", currentRequiredContainer.getDisplayName()), true);
            }
            return InteractionResult.FAIL;
        }

        // If player has correct container in hand
        if (level != null && !level.isClientSide){
            if (!player.getAbilities().instabuild) {
                heldItem.shrink(1);
            }

            ItemStack resultToGive = currentFermentationResult.copy();
            if (!player.getInventory().add(resultToGive)) {
                player.drop(resultToGive, false);
            }
            fermentedProductCount--;
            if (fermentedProductCount <= 0) {
                level.setBlock(getBlockPos(), getBlockState().setValue(FermentationBarrelBlock.FULL, false), 3);
                tryStartFermentation();
            }

            // Place sound
            if (Objects.equals(this.group, "wine")){
                level.playSound(null, worldPosition, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else if (Objects.equals(this.group, "pickles")) {
                level.playSound(null, worldPosition, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            setChanged();
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    // ========= Transfer data to StewStoveMenu class =========
    private void updateData() {
        data.set(0, fermentationTime);
    }

    // ========= Transfer data between Server and Client ==========
    @Override
    public void saveAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("FermentationTime", this.fermentationTime);
        tag.putInt("FermentationTimeTotal", this.fermentationTimeTotal);
        tag.putBoolean("IsFermentation", this.isFermentation);
        tag.putBoolean("HasOutput", this.hasOutput);

        CompoundTag inventoryTag = this.inventory.serializeNBT(provider);
        tag.put("Inventory", inventoryTag);

        if (!currentFermentationResult.isEmpty()) {
            CompoundTag resultTag = new CompoundTag();
            if (this.level != null) {
                currentFermentationResult.save(this.level.registryAccess(), resultTag);
            }
            tag.put("FermentationResult", resultTag);
        }

        if (!currentRequiredContainer.isEmpty()) {
            CompoundTag containerTag = new CompoundTag();
            if (this.level != null) {
                currentRequiredContainer.save(this.level.registryAccess(), containerTag);
            }
            tag.put("RequiredContainer", containerTag);
        }
    }

    @Override
    public void loadAdditional(@Nonnull CompoundTag tag, @Nonnull HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.fermentationTime = tag.getInt("FermentationTime");
        this.fermentationTimeTotal = tag.getInt("FermentationTimeTotal");
        this.isFermentation = tag.getBoolean("IsFermentation");
        this.hasOutput = tag.getBoolean("HasOutput");

        CompoundTag inventoryTag = tag.getCompound("Inventory");
        this.inventory.deserializeNBT(provider, inventoryTag);

        if (tag.contains("FermentedResult", Tag.TAG_COMPOUND)) {
            this.currentFermentationResult = ItemStack.parseOptional(provider, tag.getCompound("FermentedResult"));
        } else {
            this.currentFermentationResult = ItemStack.EMPTY;
        }
        if (tag.contains("RequiredContainer", Tag.TAG_COMPOUND)) {
            this.currentRequiredContainer = ItemStack.parseOptional(provider, tag.getCompound("RequiredContainer"));
        } else {
            this.currentRequiredContainer= ItemStack.EMPTY;
        }

        if (level != null) {
            BlockState newState = getBlockState();
            boolean isFull = hasOutput;
            newState = newState.setValue(FermentationBarrelBlock.FULL, isFull);

            level.setBlock(worldPosition, newState, 3);
        }

    }


    // ========= Other needed settings =========
    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @Nonnull Inventory inventory, @Nonnull Player player) {
        if (level != null) {
            return new FermentationBarrelMenu(id, inventory, this.inventory, ContainerLevelAccess.create(level, worldPosition), this.data);
        }
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new FermentationBarrelBlockEntity(pos, state);
    }
}
