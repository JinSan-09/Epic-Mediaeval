package com.sanjin.gui.widget;

import com.sanjin.enums.PeasantGuiTabType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class PeasantTabWidget extends AbstractWidget {

    private final PeasantGuiTabType tabType;
    private final Consumer<PeasantGuiTabType> onClicked;
    private boolean active;

    public PeasantTabWidget(int x, int y, int width, int height, @NotNull PeasantGuiTabType tabType, Consumer<PeasantGuiTabType> onClicked, boolean active) {
        super(x, y, width, height, tabType.getDisplayName());

        this.tabType = tabType;
        this.onClicked = onClicked;
        this.active = active;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, tabType.getSerializedName());
    }

    public PeasantGuiTabType getTabType() {
        return tabType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
