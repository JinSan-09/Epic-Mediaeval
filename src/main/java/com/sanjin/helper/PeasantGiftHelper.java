package com.sanjin.helper;

import com.sanjin.item.RemainderItem;
import com.sanjin.item.fooditem.WineItem;
import com.sanjin.register.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class PeasantGiftHelper {

    public record GiftValue(int favorability, int yield) {
    }

    private PeasantGiftHelper() {
    }

    public static GiftValue getGiftValue(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        if (stack.getItem() instanceof WineItem) {
            return switch (WineRiskHelper.getTier(stack)) {
                case LEGENDARY -> new GiftValue(18, 10);
                case RARE -> new GiftValue(14, 8);
                case COMMON -> new GiftValue(10, 6);
            };
        }
        if (stack.getItem() instanceof RemainderItem) {
            return new GiftValue(8, 4);
        }
        if (isValuable(stack)) {
            return new GiftValue(12, 8);
        }
        if (isIngredient(stack)) {
            return new GiftValue(3, 1);
        }
        return null;
    }

    private static boolean isValuable(ItemStack stack) {
        return stack.is(Items.EMERALD) ||
                stack.is(Items.DIAMOND) ||
                stack.is(Items.GOLD_INGOT) ||
                stack.is(Items.GOLD_BLOCK);
    }

    private static boolean isIngredient(ItemStack stack) {
        return stack.is(ModItems.BARLEY.get()) ||
                stack.is(ModItems.BEEF_KIDNEY.get()) ||
                stack.is(ModItems.CHEESE.get()) ||
                stack.is(ModItems.CHICKPEA.get()) ||
                stack.is(ModItems.DOUGH.get()) ||
                stack.is(ModItems.FROG_LEG.get()) ||
                stack.is(ModItems.COOKED_FROG_LEG.get()) ||
                stack.is(ModItems.GREEN_PEPPER.get()) ||
                stack.is(ModItems.HORSERADISH.get()) ||
                stack.is(ModItems.LEMON.get()) ||
                stack.is(ModItems.LEEK_LEAVES.get()) ||
                stack.is(ModItems.NETTLE_LEAVES.get()) ||
                stack.is(ModItems.OATS.get()) ||
                stack.is(ModItems.ONION.get()) ||
                stack.is(ModItems.OXTAIL.get()) ||
                stack.is(ModItems.PLUM.get()) ||
                stack.is(ModItems.RAW_TROUT.get()) ||
                stack.is(ModItems.COOKED_TROUT.get()) ||
                stack.is(ModItems.RAW_ELK_MEAT.get()) ||
                stack.is(ModItems.COOKED_ELK_MEAT.get()) ||
                stack.is(ModItems.RAW_VENISON.get()) ||
                stack.is(ModItems.COOKED_VENISON.get()) ||
                stack.is(ModItems.WHITE_BEANS.get()) ||
                stack.is(Items.BREAD) ||
                stack.is(Items.APPLE) ||
                stack.is(Items.CARROT) ||
                stack.is(Items.POTATO) ||
                stack.is(Items.BAKED_POTATO) ||
                stack.is(Items.COOKED_BEEF) ||
                stack.is(Items.COOKED_CHICKEN) ||
                stack.is(Items.COOKED_PORKCHOP) ||
                stack.is(Items.COOKED_MUTTON) ||
                stack.is(Items.COOKED_RABBIT) ||
                stack.is(Items.COOKED_COD) ||
                stack.is(Items.COOKED_SALMON);
    }
}
