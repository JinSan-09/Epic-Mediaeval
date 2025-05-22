package com.sanjin.item;

import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WineItem extends RemainderItem {

    private final boolean hasEnchantmentEffect;
    private final List<Component> tooltipLines;

    public WineItem(Properties properties, boolean hasEnchantmentEffect, List<Component> tooltipLines, EffectComponent effects, UseRemainderComponent remainder) {
        super(properties, remainder, effects);
        this.hasEnchantmentEffect = hasEnchantmentEffect;
        this.tooltipLines = tooltipLines;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return hasEnchantmentEffect || super.isFoil(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable TooltipContext tooltipContext, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (tooltipContext != null) {
            super.appendHoverText(stack, tooltipContext, tooltipComponents, isAdvanced);
        }
        if (tooltipLines != null && !tooltipLines.isEmpty()) {
            tooltipComponents.addAll(tooltipLines);
        }
    }

}
