package com.sanjin.gui.tab;

import com.sanjin.gui.AbstractTabContent;
import com.sanjin.menu.PeasantMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TradeHistoryTabContent extends AbstractTabContent {

    public TradeHistoryTabContent(PeasantMenu menu, int x, int y, int width, int height) {
        super(menu, x, y, width, height);
    }

    @Override
    protected void initializeContent() {

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }
}
