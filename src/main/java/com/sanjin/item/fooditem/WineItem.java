package com.sanjin.item.fooditem;

import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import com.sanjin.helper.WineRiskHelper;
import com.sanjin.item.RemainderItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

public class WineItem extends RemainderItem {

    private final boolean hasEnchantmentEffect;
    private final List<Component> tooltipLines;

    public WineItem(Properties properties, boolean hasEnchantmentEffect, List<Component> tooltipLines, EffectComponent effects, UseRemainderComponent remainder) {
        super(properties, remainder, effects);
        this.hasEnchantmentEffect = hasEnchantmentEffect;
        this.tooltipLines = tooltipLines;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity user) {
        ItemStack consumedStack = stack.copy();
        ItemStack result = super.finishUsingItem(stack, level, user);
        WineRiskHelper.applyRisk(consumedStack, level, user);
        return result;
    }

    @Override
    public boolean isFoil(@Nonnull ItemStack stack) {
        return hasEnchantmentEffect || super.isFoil(stack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nonnull TooltipContext tooltipContext, @Nonnull List<Component> tooltipComponents, @Nonnull TooltipFlag isAdvanced) {
        if (tooltipContext != null) {
            super.appendHoverText(stack, tooltipContext, tooltipComponents, isAdvanced);
        }
        if (tooltipLines != null && !tooltipLines.isEmpty()) {
            tooltipComponents.addAll(tooltipLines);
        }
        tooltipComponents.add(Component.translatable(
                "tooltip.wine_risk." + WineRiskHelper.getTier(stack).name().toLowerCase(Locale.ROOT)));
    }

}
