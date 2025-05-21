package com.sanjin.screen;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.FermentationBarrelRecipeBookComponent;
import com.sanjin.entity.blockentity.FermentationBarrelBlockEntity;
import com.sanjin.menu.FermentationBarrelMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.RecipeBookMenu;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelScreen extends AbstractRecipeBookScreen<FermentationBarrelMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID,"textures/gui/container/fermentation_barrel_gui.png");

    private static final int TOP_U = 0;
    private static final int TOP_V = 241;
    private static final int TOP_X = 63;
    private static final int TOP_Y = 45;
    private static final int TOP_WIDTH = 106;
    private static final int TOP_HEIGHT = 51;

    public FermentationBarrelScreen(FermentationBarrelMenu menu, Inventory playerInventory, Component title) {
        super(menu, new FermentationBarrelRecipeBookComponent(menu), playerInventory, title);
        this.imageWidth = 232;
        this.imageHeight = 241;
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY){
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
        return new ScreenPosition(this.leftPos + 5, this.topPos + 54);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics,mouseX,mouseY,partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);

    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(
                RenderType::guiTextured,
                TEXTURE,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                512,512
        );

        boolean fermentationState = this.menu.getFermentationState() > 0;
        if (fermentationState) {
            guiGraphics.blit(
                    RenderType::guiTextured,
                    TEXTURE,
                    this.leftPos + TOP_X, this.topPos + TOP_Y,
                    TOP_U, TOP_V,
                    TOP_WIDTH, TOP_HEIGHT,
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
