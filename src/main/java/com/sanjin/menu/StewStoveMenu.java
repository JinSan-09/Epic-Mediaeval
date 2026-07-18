package com.sanjin.menu;

import com.sanjin.recipe.StewStoveRecipe;
import com.sanjin.recipe.recipeinput.ProcessingRecipeInput;
import com.sanjin.register.*;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public class StewStoveMenu extends RecipeBookMenu {

    private final ContainerLevelAccess access;
    private final ContainerData data;

    public StewStoveMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(8),ContainerLevelAccess.NULL, new SimpleContainerData(4));
    }

    public StewStoveMenu(int id, Inventory playerInv, IItemHandler dataInv, ContainerLevelAccess access, ContainerData data) {
        super(ModMenus.STEW_STOVE_MENU.get(), id);
        this.access = access;
        this.data = data;

        this.addDataSlots(data);

        for (int i = 0; i < 4; i++) {
            int row   = i / 2;
            int col   = i % 2;
            int xPos  = 59 + col * 18;
            int yPos  = 10 + row * 18;
            this.addSlot(new SlotItemHandler(dataInv, i, xPos, yPos));
        }
        this.addSlot(new SlotItemHandler(dataInv, 4, 136, 19) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() == Items.WATER_BUCKET || stack.getItem() == net.minecraft.world.item.Items.BUCKET;
            }
        });
        this.addSlot(new SlotItemHandler(dataInv, 5, 23, 123));
        this.addSlot(new SlotItemHandler(dataInv, 6, 197, 80) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(ModItems.LARGE_WOODEN_BOWL.get()) || stack.is(ModItems.WOODEN_BOWL.get());
            }
        });
        this.addSlot(new SlotItemHandler(dataInv, 7, 197, 55) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
        //Payer Menu
        int invY = 159;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9, 36 + col * 18, invY + row * 18));
            }
        }
        int hotbarY = 217;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new net.minecraft.world.inventory.Slot(
                    playerInv, col, 36 + col * 18, hotbarY));
        }
    }

    public int getWaterLevel() {
        return this.data.get(0);
    }
    public int getBurnTime() {
        return this.data.get(1);
    }
    public int getCookTime() {
        return this.data.get(2);
    }
    public int getCookTimeTotal(){return this.data.get(3);}

    public Slot getResultSlot(){
        return this.getSlot(7);
    }

    @Override
    public @NotNull PostPlaceAction handlePlacement(boolean b, boolean b1, @NotNull RecipeHolder<?> recipeHolder, @NotNull ServerLevel level, @NotNull Inventory inventory) {
        RecipeHolder<StewStoveRecipe> holder = (RecipeHolder<StewStoveRecipe>) recipeHolder;
        RecipeBookMenu.PostPlaceAction action;
        {
            List<Slot> inputSlots = IntStream.range(0, 4).mapToObj(this.slots::get).toList();
            action = ServerPlaceRecipe.placeRecipe(
                    new ServerPlaceRecipe.CraftingMenuAccess<>(){
                        @Override
                        public void fillCraftSlotsStackedContents(@NotNull StackedItemContents contents) {
                            StewStoveMenu.this.fillCraftSlotsStackedContents(contents);
                        }

                        @Override
                        public void clearCraftingContent() {
                            for (int i = 0; i < 4; i++) {
                                StewStoveMenu.this.slots.get(i).set(ItemStack.EMPTY);
                            }
                        }

                        @Override
                        public boolean recipeMatches(@NotNull RecipeHolder<StewStoveRecipe> holder) {
                            StewStoveRecipe recipe = holder.value();
                            List<ItemStack> inputs = IntStream.range(0, 4).mapToObj(idx -> StewStoveMenu.this.slots.get(idx).getItem()).toList();
                            ItemStack container = StewStoveMenu.this.slots.get(6).getItem();
                            ProcessingRecipeInput recipeInput = ProcessingRecipeInput.of(inputs, container);

                            return recipe.matches(recipeInput, level);
                        }
                    },
                    2, 2, inputSlots, inputSlots, inventory, holder, b, b1
            );
        }

        return action;
    }

    @Override
    public void fillCraftSlotsStackedContents(@NotNull StackedItemContents contents) {
        for (int i = 0; i < 4; i++) {
            ItemStack stack = this.slots.get(i).getItem();
            if (!stack.isEmpty()) {
                contents.accountStack(stack, 1);
            }
        }
    }

    @Override
    public @NotNull RecipeBookType getRecipeBookType(){
        return RecipeBookType.valueOf("EPICMEDIAEVAL_STEW_STOVE");
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int quickMovedSlotIndex) {
        ItemStack quickMovedStack = ItemStack.EMPTY;
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);

        if (quickMovedSlot.hasItem()) {
            ItemStack rawStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();

            int machineSlots = 8;
            int playerInventoryEnd = machineSlots + 27;
            int hotbarEnd = playerInventoryEnd + 9;

            if (quickMovedSlotIndex < machineSlots) {
                if (!this.moveItemStackTo(rawStack, machineSlots, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else if ((rawStack.is(Items.WATER_BUCKET) || rawStack.is(Items.BUCKET))
                    && this.moveItemStackTo(rawStack, 4, 5, false)) {
                // Water slot accepted the stack.
            } else if ((rawStack.is(ModItems.LARGE_WOODEN_BOWL.get()) || rawStack.is(ModItems.WOODEN_BOWL.get()))
                    && this.moveItemStackTo(rawStack, 6, 7, false)) {
                // Container slot accepted the stack.
            } else if (!this.moveItemStackTo(rawStack, 0, 4, false)
                    && !this.moveItemStackTo(rawStack, 5, 6, false)) {
                if (quickMovedSlotIndex < playerInventoryEnd) {
                    if (!this.moveItemStackTo(rawStack, playerInventoryEnd, hotbarEnd, false)) return ItemStack.EMPTY;
                } else if (!this.moveItemStackTo(rawStack, machineSlots, playerInventoryEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (rawStack.isEmpty()) {
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                quickMovedSlot.setChanged();
            }
            if (rawStack.getCount() == quickMovedStack.getCount()) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTake(player, rawStack);
        }
        return quickMovedStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, ModBlocks.STEW_STOVE_BLOCK.get());
    }

}
