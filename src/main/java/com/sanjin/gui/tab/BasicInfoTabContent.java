package com.sanjin.gui.tab;

import com.sanjin.entity.AbstractPeasantEntity;
import com.sanjin.gui.AbstractTabContent;
import com.sanjin.menu.PeasantMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BasicInfoTabContent extends AbstractTabContent {

    private Font font;

    public BasicInfoTabContent(PeasantMenu menu, int x, int y, int width, int height) {
        super(menu, x, y, width, height);
    }

    @Override
    protected void initializeContent() {
        this.font = Minecraft.getInstance().font;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        AbstractPeasantEntity peasant = menu.getPeasant();

        int currentY = y + 10;
        int lineHeight = 12;

        graphics.drawString(font, "§l基本信息", x + 8, currentY, 0x2b1f14, false);
        currentY += lineHeight + 4;

        graphics.drawString(font, "姓名: " + peasant.getPeasantName(),
                x + 8, currentY, 0xffe187, false);
        currentY += lineHeight;

        graphics.drawString(font, "生命值: " + (int)peasant.getHealth() + "/" + (int)peasant.getMaxHealth(),
                x + 8, currentY, 0xffe187, false);
        currentY += lineHeight;

        currentY += 8;
        graphics.drawString(font, "§l关系状态", x + 8, currentY, 0x2b1f14, false);
        currentY += lineHeight + 4;

        if (peasant.hasOwner()) {
            graphics.drawString(font, "主人: " + (peasant.getOwner() != null ? peasant.getOwner().getName().getString() : "未知"),
                    x + 8, currentY, 0xffe187, false);
        } else {
            graphics.drawString(font, "从属状态: 自由身", x + 8, currentY, 0xffe187, false);
        }
        currentY += lineHeight;

        // canTrade()==true 表示允许交易
        if (menu.canTrade()) {
            graphics.drawString(font, "交易态度：§a可以交易", x + 8, currentY, 0xffe187, false);
        } else {
            graphics.drawString(font, "交易态度：§c拒绝交易", x + 8, currentY, 0xffe187, false);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }
}
