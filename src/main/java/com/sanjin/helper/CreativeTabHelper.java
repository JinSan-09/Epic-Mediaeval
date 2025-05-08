package com.sanjin.helper;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

/**
 * Temporarily deprecated
 */

public class CreativeTabHelper {

    public static void addItems(BuildCreativeModeTabContentsEvent event, Supplier<? extends Item>... items) {
        for (Supplier<? extends Item> item : items) {
            event.accept(item.get());
        }
    }

    public static void addItems(BuildCreativeModeTabContentsEvent event, Item... items) {
        for (Item item : items) {
            event.accept(item);
        }
    }

    public static void addItemStacks(BuildCreativeModeTabContentsEvent event, ItemStack... itemStacks) {
        for (ItemStack stack : itemStacks) {
            event.accept(stack);
        }
    }

}
