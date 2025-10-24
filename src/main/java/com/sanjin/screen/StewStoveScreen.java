package com.sanjin.screen;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.StewStoveRecipeBookComponent;
import com.sanjin.menu.StewStoveMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

public class StewStoveScreen extends AbstractRecipeBookScreen<StewStoveMenu> {

    private static final ResourceLocation MAIN_TEXTURE = ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "textures/gui/container/stew_stove_gui.png");

    private static final int FIRE_X = 85;
    private static final int FIRE_Y = 119;
    private static final int FIRE_U = 0;
    private static final int FIRE_WIDTH = 62;
    private static final int FIRE_HEIGHT = 27;

    private static final int SOUP_X = 58;
    private static final int SOUP_Y = 56;
    private static final int SOUP_U = 62;
    private static final int SOUP_WIDTH = 133;
    private static final int SOUP_HEIGHT = 43;

    private static final int COOKING_ONE_X = 102;
    private static final int COOKING_ONE_Y = 21;
    private static final int COOKING_ONE_U = 0;
    private static final int COOKING_ONE_V = 322;
    private static final int COOKING_ONE_WIDTH = 25;
    private static final int COOKING_ONE_HEIGHT = 30;

    private static final int COOKING_TWO_X = 176;
    private static final int COOKING_TWO_Y = 70;
    private static final int COOKING_TWO_U = 0;
    private static final int COOKING_TWO_V = 352;
    private static final int COOKING_TWO_WIDTH = 18;
    private static final int COOKING_TWO_HEIGHT = 11;

    private static int SOUP_V = 327;
    private static int FIRE_V = 241;
    private static int burnTimeTotal = 0;

    public StewStoveScreen(StewStoveMenu menu, Inventory playerInventory, Component title) {
        super(menu, new StewStoveRecipeBookComponent(menu), playerInventory, title);
        this.imageWidth = 232;
        this.imageHeight = 241;
    }

    @Override
    protected void renderLabels(@Nonnull GuiGraphics graphics, int mouseX, int mouseY){
        graphics.drawString(
                this.font,
                this.title,
                this.titleLabelX,
                this.titleLabelY,
                0x404040
        );
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected @NotNull ScreenPosition getRecipeBookButtonPosition() {
        return new ScreenPosition(this.leftPos + 6, this.topPos + 55);
    }

    @Override
    public void render(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics,mouseX,mouseY,partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
                RenderType::guiTextured,
                MAIN_TEXTURE,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                512,512
        );

        // Print soup level
        int waterLevel = this.menu.getWaterLevel();
        if (waterLevel > 0 && waterLevel <= 3) {
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + SOUP_X,this.topPos + SOUP_Y,
                    SOUP_U,SOUP_V,
                    SOUP_WIDTH,SOUP_HEIGHT,
                    512,512
            );
        }else if (waterLevel > 3 && waterLevel <= 7) {
            SOUP_V = 284;
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + SOUP_X,this.topPos + SOUP_Y,
                    SOUP_U,SOUP_V,
                    SOUP_WIDTH,SOUP_HEIGHT,
                    512,512
            );
        } else if (waterLevel > 7 && waterLevel <= 10) {
            SOUP_V = 241;
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + SOUP_X,this.topPos + SOUP_Y,
                    SOUP_U,SOUP_V,
                    SOUP_WIDTH,SOUP_HEIGHT,
                    512,512
            );
        }

        // Print fire level
        int burnTime = this.menu.getBurnTime();
        burnTimeTotal = Math.max(burnTimeTotal,burnTime);
        double OrRatio = (double) burnTime/burnTimeTotal;
        if (OrRatio <= 1 && OrRatio > 0.7) {
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + FIRE_X,this.topPos + FIRE_Y,
                    FIRE_U,FIRE_V,
                    FIRE_WIDTH,FIRE_HEIGHT,
                    512,512
            );
        }else if (OrRatio <= 0.7 && OrRatio > 0.3) {
            FIRE_V = 268;
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + FIRE_X,this.topPos + FIRE_Y,
                    FIRE_U,FIRE_V,
                    FIRE_WIDTH,FIRE_HEIGHT,
                    512,512
            );
        }else if (OrRatio > 0 && OrRatio <= 0.3) {
            FIRE_V = 295;
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + FIRE_X,this.topPos + FIRE_Y,
                    FIRE_U,FIRE_V,
                    FIRE_WIDTH,FIRE_HEIGHT,
                    512,512
            );
        }

        // Print cooking progress
        int cookTimeTotal = this.menu.getCookTimeTotal();
        int cookTime = this.menu.getCookTime();
        double cookRatio = (double)cookTime / cookTimeTotal;
        if (cookRatio <= 0.5) {
            int cookingHeight = 2 * (int) (COOKING_ONE_HEIGHT * cookRatio);
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + COOKING_ONE_X,this.topPos + COOKING_ONE_Y,
                    COOKING_ONE_U,COOKING_ONE_V,
                    COOKING_ONE_WIDTH,cookingHeight,
                    512,512
            );
        }else {
            double newCookTimeTotal = 0.5*cookTimeTotal;
            int newCookTime = (int) (cookTime - cookTimeTotal*0.5);
            double newRatio = newCookTime / newCookTimeTotal;
            int cookWidth = (int) (COOKING_TWO_WIDTH * newRatio);
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + COOKING_ONE_X,this.topPos + COOKING_ONE_Y,
                    COOKING_ONE_U,COOKING_ONE_V,
                    COOKING_ONE_WIDTH,COOKING_ONE_HEIGHT,
                    512,512
            );
            guiGraphics.blit(
                    RenderType::guiTextured,
                    MAIN_TEXTURE,
                    this.leftPos + COOKING_TWO_X,this.topPos + COOKING_TWO_Y,
                    COOKING_TWO_U,COOKING_TWO_V,
                    cookWidth,COOKING_TWO_HEIGHT,
                    512,512
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
