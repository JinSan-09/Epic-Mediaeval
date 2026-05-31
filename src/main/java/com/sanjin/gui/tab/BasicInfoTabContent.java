package com.sanjin.gui.tab;

import com.sanjin.entity.AbstractPeasantEntity;
import com.sanjin.enums.PeasantFavorabilityLevel;
import com.sanjin.enums.PeasantYieldLevel;
import com.sanjin.gui.AbstractTabContent;
import com.sanjin.menu.PeasantMenu;
import com.sanjin.network.packet.RecruitPeasantPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BasicInfoTabContent extends AbstractTabContent {

    private static final int RECRUIT_BUTTON_WIDTH = 72;
    private static final int RECRUIT_BUTTON_HEIGHT = 16;

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
        currentY += lineHeight + 10;

        renderRecruitButton(graphics, getRecruitButtonY());
    }

    private void renderRecruitButton(@NotNull GuiGraphics graphics, int buttonY) {
        AbstractPeasantEntity peasant = menu.getPeasant();
        int buttonX = getRecruitButtonX();
        int fillColor;
        Component label;

        if (peasant.isOwner(menu.getPlayer())) {
            label = Component.translatable("gui.peasant.recruit.already_owner");
            fillColor = 0xAA3F6F3F;
        } else if (peasant.hasOwner()) {
            label = Component.translatable("gui.peasant.recruit.other_owner");
            fillColor = 0xAA6F3F3F;
        } else if (canRecruit()) {
            label = Component.translatable("gui.peasant.recruit");
            fillColor = 0xAA3F6F3F;
        } else {
            label = Component.translatable("gui.peasant.recruit.locked");
            fillColor = 0xAA5A5145;
        }

        graphics.fill(buttonX, buttonY, buttonX + RECRUIT_BUTTON_WIDTH, buttonY + RECRUIT_BUTTON_HEIGHT, fillColor);
        graphics.fill(buttonX, buttonY, buttonX + RECRUIT_BUTTON_WIDTH, buttonY + 1, 0xFFEBD09A);
        graphics.fill(buttonX, buttonY + RECRUIT_BUTTON_HEIGHT - 1, buttonX + RECRUIT_BUTTON_WIDTH, buttonY + RECRUIT_BUTTON_HEIGHT, 0xFF2B1F14);
        int labelX = buttonX + (RECRUIT_BUTTON_WIDTH - font.width(label)) / 2;
        graphics.drawString(font, label, labelX, buttonY + 4, 0xFFFFFF, false);

        if (!peasant.hasOwner() && !canRecruit()) {
            graphics.drawString(font,
                    Component.translatable(
                            "gui.peasant.recruit.requirement",
                            PeasantFavorabilityLevel.FRIEND.getRequiredPoints(),
                            PeasantYieldLevel.SERVANT.getRequiredPoints()
                    ),
                    buttonX + RECRUIT_BUTTON_WIDTH + 8,
                    buttonY + 4,
                    0xffe187,
                    false);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseInRecruitButton(mouseX, mouseY) && !menu.getPeasant().isOwner(menu.getPlayer())) {
            PacketDistributor.sendToServer(new RecruitPeasantPacket(menu.getPeasant().getId()));
            return true;
        }
        return false;
    }

    private int getRecruitButtonX() {
        return x + 8;
    }

    private int getRecruitButtonY() {
        return y + 108;
    }

    private boolean isMouseInRecruitButton(double mouseX, double mouseY) {
        int buttonX = getRecruitButtonX();
        int buttonY = getRecruitButtonY();
        return mouseX >= buttonX && mouseX <= buttonX + RECRUIT_BUTTON_WIDTH &&
                mouseY >= buttonY && mouseY <= buttonY + RECRUIT_BUTTON_HEIGHT;
    }

    private boolean canRecruit() {
        return !menu.getPeasant().hasOwner() &&
                menu.getFavorability() >= PeasantFavorabilityLevel.FRIEND.getRequiredPoints() &&
                menu.getYield() >= PeasantYieldLevel.SERVANT.getRequiredPoints();
    }
}
