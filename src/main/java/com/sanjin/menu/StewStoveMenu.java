package com.sanjin.menu;

import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModItems;
import com.sanjin.register.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class StewStoveMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;

    public StewStoveMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(8),ContainerLevelAccess.NULL);
    }

    public StewStoveMenu(int id, Inventory playerInv, IItemHandler dataInv, ContainerLevelAccess access) {
        super(ModMenus.STEW_STOVE_MENU.get(), id);
        this.access = access;

        for (int i = 0; i < 4; i++) {
            int row   = i / 2;
            int col   = i % 2;
            int xPos  = 71 + col * 18;
            int yPos  = 15 + row * 18;
            this.addSlot(new SlotItemHandler(dataInv, i, xPos, yPos));
        }
        this.addSlot(new SlotItemHandler(dataInv, 4, 44, 61) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem() == Items.WATER_BUCKET || stack.getItem() == net.minecraft.world.item.Items.BUCKET;
            }
        });
        this.addSlot(new SlotItemHandler(dataInv, 5, 80, 61) );
        this.addSlot(new SlotItemHandler(dataInv, 6, 145, 47) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.getItem().equals(ModItems.LARGE_WOODEN_BOWL) || stack.getItem().equals(ModItems.WOODEN_BOWL);
            }
        });
        this.addSlot(new SlotItemHandler(dataInv, 7, 145, 24) {
            @Override public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
        //Payer Menu
        int invY = 84;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new net.minecraft.world.inventory.Slot(
                        playerInv, col + row * 9 + 9, 8 + col * 18, invY + row * 18));
            }
        }
        int hotbarY = 142;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new net.minecraft.world.inventory.Slot(
                    playerInv, col, 8 + col * 18, hotbarY));
        }
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
