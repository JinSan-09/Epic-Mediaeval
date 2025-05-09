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

    private static final int FIRE_X = 77;
    private static final int FIRE_Y = 50;
    private static final int FIRE_HEIGHT = 10;
    private static final int FIRE_WIDTH = 22;
    private static final int FIRE_U = 0;
    private static final int FIRE_V = 166;
    private static int MAX_FIRE_HEIGHT = 0;

    private static final int COOK_X = 115;
    private static final int COOK_Y = 24;
    private static final int COOK_HEIGHT = 17;
    private static final int COOK_WIDTH = 22;
    private static final int COOK_U = 22;
    private static final int COOK_V = 166;

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
            // calculate the waterLevel, max is 10
            int waterHeight = (waterLevel * WATER_METER_HEIGHT) / 10;

            // print waterLevel
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

        int burnTime = this.menu.getBurnTime();
        if (burnTime > 0) {
            if (MAX_FIRE_HEIGHT < burnTime) {
                MAX_FIRE_HEIGHT = burnTime;
            }
            double burnRatio = (double) burnTime / (double) MAX_FIRE_HEIGHT;
            int fireHeight = Math.toIntExact(Math.round(FIRE_HEIGHT * burnRatio));
            guiGraphics.blit(
                    RenderType::guiTextured,
                    TEXTURE,
                    this.leftPos + FIRE_X,
                    this.topPos + FIRE_Y + (FIRE_HEIGHT - fireHeight),
                    FIRE_U, FIRE_V + (FIRE_HEIGHT - fireHeight),
                    FIRE_WIDTH, fireHeight,
                    256, 256
            );
        }else {
            MAX_FIRE_HEIGHT = 0;
        }

        int cookTime = this.menu.getCookTime();
        int cookTimeTotal = this.menu.getCookTimeTotal();
        if (cookTime > 0) {
            int cookWidth = COOK_WIDTH * cookTime/cookTimeTotal;
            guiGraphics.blit(
                    RenderType::guiTextured,
                    TEXTURE,
                    this.leftPos + COOK_X,
                    this.topPos + COOK_Y,
                    COOK_U,COOK_V,
                    cookWidth,COOK_HEIGHT,
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
