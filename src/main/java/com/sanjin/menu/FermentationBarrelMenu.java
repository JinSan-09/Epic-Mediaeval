package com.sanjin.menu;

import com.sanjin.recipe.FermentationBarrelRecipe;
import com.sanjin.recipe.recipeinput.FermentationBarrelRecipeInput;
import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModMenus;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

public class FermentationBarrelMenu extends RecipeBookMenu {

    private final ContainerLevelAccess access;
    private final ContainerData data;
    private boolean placingRecipe = false;

    public FermentationBarrelMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(4),ContainerLevelAccess.NULL, new SimpleContainerData(1));
    }

    public FermentationBarrelMenu(int id, Inventory playerInv, IItemHandler dataInv, ContainerLevelAccess access, ContainerData data) {
        super(ModMenus.FERMENTATION_BARREL_MENU.get(), id);
        this.access = access;
        this.data = data;
        this.addDataSlots(data);

        for (int i = 0; i < 4; i++) {
            int row   = i / 2;
            int col   = i % 2;
            int xPos  = 99 + col * 18;
            int yPos  = 7 + row * 18;
            this.addSlot(new SlotItemHandler(dataInv, i, xPos, yPos));
        }

        int invY = 159;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new net.minecraft.world.inventory.Slot(
                        playerInv, col + row * 9 + 9, 36 + col * 18, invY + row * 18));
            }
        }
        int hotbarY = 217;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new net.minecraft.world.inventory.Slot(
                    playerInv, col, 36 + col * 18, hotbarY));
        }
    }

    private void beginPlacingRecipe() {
        this.placingRecipe = true;
    }

    private void finishPlacingRecipe(ServerLevel level, RecipeHolder<FermentationBarrelRecipe> holder) {
        this.placingRecipe = false;
    }

    public int getFermentationState(){
        return this.data.get(0);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@Nonnull Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack original = slot.getItem();
            ItemStack copy = original.copy();

            int containerSlots = 4;
            int playerInventoryEnd = containerSlots + 27;
            int hotbarEnd = playerInventoryEnd + 9;

            if (index < containerSlots) {
                if (!moveItemStackTo(original, containerSlots, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < playerInventoryEnd) {
                if (!moveItemStackTo(original, 0, containerSlots, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(original, 0, containerSlots, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (original.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            return copy;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, ModBlocks.FERMENTATION_BARREL_BLOCK.get());
    }

    @Override
    public @NotNull PostPlaceAction handlePlacement(boolean b, boolean b1, @Nonnull RecipeHolder<?> recipeHolder, @Nonnull ServerLevel level, @Nonnull Inventory inventory) {
        RecipeHolder<FermentationBarrelRecipe> holder = (RecipeHolder<FermentationBarrelRecipe>) recipeHolder;
        this.beginPlacingRecipe();
        RecipeBookMenu.PostPlaceAction action;
        try{
            List<Slot> inputSlots = IntStream.range(0, 4).mapToObj(this.slots::get).toList();
            action = ServerPlaceRecipe.placeRecipe(
                    new ServerPlaceRecipe.CraftingMenuAccess<>(){
                        @Override
                        public void fillCraftSlotsStackedContents(@Nonnull StackedItemContents contents) {
                            FermentationBarrelMenu.this.fillCraftSlotsStackedContents(contents);
                        }

                        @Override
                        public void clearCraftingContent() {
                            for (int i = 0; i < 4; i++) {
                                FermentationBarrelMenu.this.slots.get(i).set(ItemStack.EMPTY);
                            }
                        }

                        @Override
                        public boolean recipeMatches(@Nonnull RecipeHolder<FermentationBarrelRecipe> holder) {
                            FermentationBarrelRecipe recipe = holder.value();
                            List<ItemStack> inputs = IntStream.range(0, 4).mapToObj(idx -> FermentationBarrelMenu.this.slots.get(idx).getItem()).toList();
                            FermentationBarrelRecipeInput recipeInput = FermentationBarrelRecipeInput.of(inputs);

                            return recipe.matches(recipeInput, level);
                        }
                    },
                    2, 2, inputSlots, inputSlots, inventory, holder, b, b1
            );
        }finally{
            this.finishPlacingRecipe(level, holder);
        }

        return action;
    }

    @Override
    public void fillCraftSlotsStackedContents(@Nonnull StackedItemContents contents) {
        for (int i = 0; i < 4; i++) {
            ItemStack stack = this.slots.get(i).getItem();
            if (!stack.isEmpty()) {
                contents.accountStack(stack, 1);
            }
        }
    }

    @Override
    public @NotNull RecipeBookType getRecipeBookType() {
        return RecipeBookType.valueOf("EPICMEDIAEVAL_FERMENTATION_BARREL");
    }
}
