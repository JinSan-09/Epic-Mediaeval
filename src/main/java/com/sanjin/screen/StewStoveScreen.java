package com.sanjin.screen;

import com.sanjin.EpicMediaeval;
import com.sanjin.menu.StewStoveMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class StewStoveScreen extends AbstractContainerScreen<StewStoveMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "textures/gui/container/stew_stove_gui.png");

    private static final int WATER_METER_X = 47;
    private static final int WATER_METER_Y = 14;
    private static final int WATER_METER_WIDTH = 10;
    private static final int WATER_METER_HEIGHT = 42;

    private static final int WATER_LEVEL_U = 176;
    private static final int WATER_LEVEL_V = 0;

    public StewStoveScreen(StewStoveMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderBackground(graphics,mouseX,mouseY,partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);

    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
                RenderType::guiTextured,
                TEXTURE,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256,256
        );

        int waterLevel = this.menu.getWaterLevel();
        if (waterLevel > 0) {
            // 计算水量高度 (满水位为5，满高度为WATER_METER_HEIGHT)
            int waterHeight = (waterLevel * WATER_METER_HEIGHT) / 5;

            // 绘制水量指示器
            guiGraphics.blit(
                    RenderType::guiTextured,
                    TEXTURE,
                    this.leftPos + WATER_METER_X,
                    this.topPos + WATER_METER_Y + (WATER_METER_HEIGHT - waterHeight),
                    WATER_LEVEL_U, WATER_LEVEL_V,
                    WATER_METER_WIDTH, waterHeight,
                    256, 256
            );
        }
    }

    @Override
    public void onClose(){
        super.onClose();
    }

    @Override
    public void removed(){
        super.removed();
    }
}
