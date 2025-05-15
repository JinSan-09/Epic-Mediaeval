package com.sanjin.menu;

import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final ContainerData data;

    public FermentationBarrelMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(4),ContainerLevelAccess.NULL, new SimpleContainerData(2));
    }

    public FermentationBarrelMenu(int id, Inventory playerInv, IItemHandler dataInv, ContainerLevelAccess access, ContainerData data) {
        super(ModMenus.FERMENTATION_BARREL_MENU.get(), id);
        this.access = access;
        this.data = data;

        this.addDataSlots(data);

        for (int i = 0; i < 4; i++) {
            int row   = i / 2;
            int col   = i % 2;
            int xPos  = 71 + col * 18;
            int yPos  = 24 + row * 18;
            this.addSlot(new SlotItemHandler(dataInv, i, xPos, yPos));
        }

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

    public int getFermentationTime(){return this.data.get(0);}
    public int getFermentationTotal(){return this.data.get(1);}

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
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
    public boolean stillValid(@NotNull Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, ModBlocks.FERMENTATION_BARREL_BLOCK.get());
    }
}
