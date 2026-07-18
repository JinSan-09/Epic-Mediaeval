package com.sanjin.gui.widget;

import com.sanjin.enums.PeasantFavorabilityLevel;
import com.sanjin.enums.PeasantYieldLevel;
import com.sanjin.menu.PeasantMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class RelationshipDisplayWidget extends AbstractWidget {

    private final PeasantMenu menu;
    private final Font font;

    public RelationshipDisplayWidget(int x, int y, int width, int height, PeasantMenu menu) {
        super(x, y, width, height, Component.empty());
        this.menu = menu;
        this.font = Minecraft.getInstance().font;
    }

    @Override
    protected void renderWidget(@Nonnull GuiGraphics graphics, int i, int i1, float v) {
        int favorability = menu.getFavorability();
        int yield = menu.getYield();

        PeasantFavorabilityLevel favLevel = menu.getFavorabilityLevel();
        PeasantYieldLevel yieldLevel = menu.getYieldLevel();

        graphics.drawString(
                font,
                Component.literal("§l好感度: "),
                this.getX() + 8,
                this.getY() + 4,
                0x2b1f14,
                false);
        graphics.drawString(
                font,
                Component.literal(String.valueOf(favorability)),
                this.getX() + 8 + 35,
                this.getY() + 4 + 1,
                favLevel.getColor(),
                false
        );

        graphics.drawString(
                font,
                Component.literal("§l忠诚度: "),
                this.getX() + 8,
                this.getY() + 16,
                0x2b1f14,
                false);
        graphics.drawString(
                font,
                Component.literal(String.valueOf(yield)),
                this.getX() + 8 + 35,
                this.getY() + 16 + 1,
                yieldLevel.getColor(),
                false
        );
    }

    @Override
    protected void updateWidgetNarration(@Nonnull NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, Component.literal("关系显示"));
    }

    public void renderTooltip(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        List<Component> tooltip = new ArrayList<>();

        PeasantFavorabilityLevel favLevel = menu.getFavorabilityLevel();
        PeasantYieldLevel yieldLevel = menu.getYieldLevel();

        tooltip.add(Component.literal("§l关系状态"));
        tooltip.add(Component.literal("好感度: " + menu.getFavorability() + " (" + favLevel.getDisplayName().getString() + ")"));
        tooltip.add(Component.literal("忠诚度: " + menu.getYield() + " (" + yieldLevel.getDisplayName().getString() + ")"));

        if (!menu.canTrade()) {
            tooltip.add(Component.literal("§c此农民拒绝与你交易"));
        }

        if (menu.peasantHasOwner()) {
            tooltip.add(Component.literal("§6此农民已有主人"));
        }

        graphics.renderTooltip(font, tooltip, ItemStack.EMPTY.getTooltipImage(), ItemStack.EMPTY, mouseX, mouseY);
    }
}
