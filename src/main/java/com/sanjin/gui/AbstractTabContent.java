package com.sanjin.gui;

import com.sanjin.menu.PeasantMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractTabContent {

    protected final PeasantMenu menu;
    protected final int x;
    protected final int y;
    protected final int width;
    protected final int height;
    protected boolean initialized = false;

    public AbstractTabContent(PeasantMenu menu, int x, int y, int width, int height) {
        this.menu = menu;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void init() {
        if (!initialized) {
            initializeContent();
            initialized = true;
        }
    }

    protected abstract void initializeContent();

    public abstract void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick);

    public abstract boolean mouseClicked(double mouseX, double mouseY, int button);

    protected boolean isMouseInContentArea(double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + width &&
                mouseY >= y && mouseY < y + height;
    }

    public PeasantMenu getMenu() {
        return menu;
    }

    // Empty method
    public void tick() {

    }

    public void onClose() {

    }
}
