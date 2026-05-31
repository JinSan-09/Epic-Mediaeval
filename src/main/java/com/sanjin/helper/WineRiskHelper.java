package com.sanjin.helper;

import com.sanjin.item.fooditem.WineItem;
import com.sanjin.register.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Locale;

public final class WineRiskHelper {

    public enum WineTier {
        COMMON,
        RARE,
        LEGENDARY
    }

    private WineRiskHelper() {
    }

    public static boolean isWine(ItemStack stack) {
        return stack.getItem() instanceof WineItem;
    }

    public static WineTier getTier(ItemStack stack) {
        if (isLegendaryWine(stack)) {
            return WineTier.LEGENDARY;
        }
        if (isRareWine(stack)) {
            return WineTier.RARE;
        }
        return WineTier.COMMON;
    }

    public static void applyRisk(ItemStack stack, Level level, LivingEntity user) {
        if (level.isClientSide || !(user instanceof Player player) || !isWine(stack)) {
            return;
        }

        WineTier tier = getTier(stack);
        RandomSource random = user.getRandom();
        boolean applied = switch (tier) {
            case COMMON -> applyCommonRisk(player, random);
            case RARE -> applyRareRisk(player, random);
            case LEGENDARY -> applyLegendaryRisk(player, random);
        };

        if (applied) {
            player.displayClientMessage(Component.translatable(
                    "message.wine_risk." + tier.name().toLowerCase(Locale.ROOT)), true);
        }
    }

    private static boolean applyCommonRisk(Player player, RandomSource random) {
        boolean applied = false;
        applied |= maybeApply(player, random, 0.20F, new MobEffectInstance(MobEffects.LUCK, 20 * 45, 0, true, true));
        applied |= maybeApply(player, random, 0.25F, new MobEffectInstance(MobEffects.CONFUSION, 20 * 15, 0, true, true));
        applied |= maybeApply(player, random, 0.15F, new MobEffectInstance(MobEffects.HUNGER, 20 * 20, 0, true, true));
        return applied;
    }

    private static boolean applyRareRisk(Player player, RandomSource random) {
        boolean applied = false;
        applied |= maybeApply(player, random, 0.35F, new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 0, true, true));
        applied |= maybeApply(player, random, 0.25F, new MobEffectInstance(MobEffects.WEAKNESS, 20 * 25, 0, true, true));
        applied |= maybeApply(player, random, 0.20F, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 20, 0, true, true));
        return applied;
    }

    private static boolean applyLegendaryRisk(Player player, RandomSource random) {
        boolean applied = false;
        applied |= maybeApply(player, random, 0.70F, new MobEffectInstance(MobEffects.ABSORPTION, 20 * 60, 1, true, true));
        applied |= maybeApply(player, random, 0.30F, new MobEffectInstance(MobEffects.LUCK, 20 * 120, 1, true, true));
        applied |= maybeApply(player, random, 0.45F, new MobEffectInstance(MobEffects.CONFUSION, 20 * 45, 1, true, true));
        applied |= maybeApply(player, random, 0.35F, new MobEffectInstance(MobEffects.POISON, 20 * 10, 0, true, true));
        return applied;
    }

    private static boolean maybeApply(Player player, RandomSource random, float chance, MobEffectInstance effect) {
        if (random.nextFloat() > chance) {
            return false;
        }
        player.addEffect(effect);
        return true;
    }

    private static boolean isLegendaryWine(ItemStack stack) {
        return stack.is(ModItems.DREAM_WINE.get()) ||
                stack.is(ModItems.UNLUCK_WINE.get()) ||
                stack.is(ModItems.FROG_WINE.get()) ||
                stack.is(ModItems.STORM_WINE.get());
    }

    private static boolean isRareWine(ItemStack stack) {
        return stack.is(ModItems.SWEET_PLUM_WINE.get()) ||
                stack.is(ModItems.BARLEY_OATS_ONION_WINE.get()) ||
                stack.is(ModItems.OATS_CHICKPEA_NETTLE_WINE.get()) ||
                stack.is(ModItems.BARLEY_PEPPER_HORSERADISH_WINE.get()) ||
                stack.is(ModItems.OATS_ONION_HORSERADISH_WINE.get()) ||
                stack.is(ModItems.CHICKPEA_LEMON_NETTLE_WINE.get()) ||
                stack.is(ModItems.PEPPER_LEMON_LEEK_WINE.get()) ||
                stack.is(ModItems.BARLEY_HORSERADISH_LEEK_WINE.get()) ||
                stack.is(ModItems.BARLEY_NETTLE_PEPPER_WINE.get()) ||
                stack.is(ModItems.BARLEY_PEPPER_ONION_WINE.get()) ||
                stack.is(ModItems.OATS_CHICKPEA_LEMON_WINE.get()) ||
                stack.is(ModItems.BARLEY_ONION_PLUM_WINE.get()) ||
                stack.is(ModItems.OATS_PEPPER_NETTLE_WINE.get()) ||
                stack.is(ModItems.CHICKPEA_ONION_LEEK_WINE.get()) ||
                stack.is(ModItems.BARLEY_OATS_LEMON_WINE.get()) ||
                stack.is(ModItems.PEPPER_NETTLE_ONION_WINE.get()) ||
                stack.is(ModItems.CHICKPEA_OATS_LEEK_WINE.get()) ||
                stack.is(ModItems.CHICKPEA_PEPPER_NETTLE_WINE.get()) ||
                stack.is(ModItems.CHICKPEA_HORSERADISH_NETTLE_WINE.get()) ||
                stack.is(ModItems.OATS_CHICKPEA_PEPPER_WINE.get());
    }
}
