package com.sanjin.gui.tab;

import com.sanjin.gui.AbstractTabContent;
import com.sanjin.menu.PeasantMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class EquipmentConfigTabContent extends AbstractTabContent {

    private Font font;

    public EquipmentConfigTabContent(PeasantMenu menu, int x, int y, int width, int height) {
        super(menu, x, y, width, height);
    }

    @Override
    protected void initializeContent() {
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

        if (!menu.getPeasant().isOwner(menu.getPlayer())) {
            graphics.drawString(font, "§l装备配置", x + 8, y + 8, 0x333333, false);
            graphics.drawString(font, "§c只有主人才能配置装备",
                    x + 8, y + 30, 0xFF5555, false);
            return;
        }

        graphics.drawString(font, "§l装备配置", x + 8, y + 8, 0x333333, false);

        int currentY = y + 25;
        int lineHeight = 12;

        graphics.drawString(font, "当前装备:", x + 8, currentY, 0x555555, false);
        currentY += lineHeight + 4;

        String mainHandInfo = menu.getPeasant().getMainHandItem().isEmpty() ?
                "无" : menu.getPeasant().getMainHandItem().getDisplayName().getString();
        graphics.drawString(font, "主手: " + mainHandInfo,
                x + 16, currentY, 0x777777, false);
        currentY += lineHeight;

        String offHandInfo = menu.getPeasant().getOffhandItem().isEmpty() ?
                "无" : menu.getPeasant().getOffhandItem().getDisplayName().getString();
        graphics.drawString(font, "副手: " + offHandInfo,
                x + 16, currentY, 0x777777, false);
        currentY += lineHeight;

        currentY += 10;
        graphics.drawString(font, "§7职业系统和装备管理", x + 8, currentY, 0x888888, false);
        currentY += lineHeight;
        graphics.drawString(font, "§7功能即将开放...", x + 8, currentY, 0x888888, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }
}
