package com.sanjin.menu;

import com.sanjin.recipe.StewStoveRecipe;
import com.sanjin.recipe.recipeinput.StewStoveRecipeInput;
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
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public class StewStoveMenu extends RecipeBookMenu {

    private final ContainerLevelAccess access;
    private final ContainerData data;
    private boolean placingRecipe;

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

    public Player getPlayer() {
        return this.access.evaluate((level, pos) -> {
            for (Player player : level.players()) {
                if (player.containerMenu == this) {
                    return player;
                }
            }
            return null;
        }).orElse(null);
    }
    public Slot getResultSlot(){
        return this.getSlot(7);
    }
    private void beginPlacingRecipe() {
        this.placingRecipe = true;
    }
    private void finishPlacingRecipe(ServerLevel level, RecipeHolder<StewStoveRecipe> holder) {
        this.placingRecipe = false;
    }

    @Override
    public @NotNull PostPlaceAction handlePlacement(boolean b, boolean b1, @NotNull RecipeHolder<?> recipeHolder, @NotNull ServerLevel level, @NotNull Inventory inventory) {
        RecipeHolder<StewStoveRecipe> holder = (RecipeHolder<StewStoveRecipe>) recipeHolder;
        this.beginPlacingRecipe();
        RecipeBookMenu.PostPlaceAction action;
        try{
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
                            ItemStack container = StewStoveMenu.this.slots.get(4).getItem();
                            StewStoveRecipeInput recipeInput = StewStoveRecipeInput.of(inputs, container);

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

            if (quickMovedSlotIndex == 0) {
                if (!this.moveItemStackTo(rawStack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
            }else if (quickMovedSlotIndex >= 5 && quickMovedSlotIndex < 41) {
                if (!this.moveItemStackTo(rawStack, 1, 5, false)) {
                    if (quickMovedSlotIndex < 32) {
                        if (!this.moveItemStackTo(rawStack, 32, 41, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.moveItemStackTo(rawStack, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }else if (!this.moveItemStackTo(rawStack, 5, 41, false)) {
                return ItemStack.EMPTY;
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
