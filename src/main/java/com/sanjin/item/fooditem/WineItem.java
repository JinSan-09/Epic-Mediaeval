package com.sanjin.item.fooditem;

import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import com.sanjin.item.RemainderItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

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
    }

}
