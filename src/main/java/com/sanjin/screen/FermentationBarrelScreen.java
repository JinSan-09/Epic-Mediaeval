package com.sanjin.screen;

import com.sanjin.EpicMediaeval;
import com.sanjin.menu.FermentationBarrelMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelScreen extends AbstractContainerScreen<FermentationBarrelMenu> {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID,"textures/gui/container/fermentation_barrel_gui.png");

    private static final int BUBBLE_X = 119;
    private static final int BUBBLE_Y = 17;
    private static final int BUBBLE_WIDTH = 27;
    private static final int BUBBLE_HEIGHT = 44;
    private static final int BUBBLE_U = 176;
    private static final int BUBBLE_V = 0;

    public FermentationBarrelScreen(FermentationBarrelMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
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
                256,256
        );

        // Print bubble level
        int bubbleLevel;
        int time = this.menu.getFermentationTime();
        int totalTime = this.menu.getFermentationTotal();
        double ratioTime = (double)time / totalTime;
        bubbleLevel = (int) (BUBBLE_HEIGHT * ratioTime);
        guiGraphics.blit(
                RenderType::guiTextured,
                TEXTURE,
                this.leftPos + BUBBLE_X, this.topPos + BUBBLE_Y + (BUBBLE_HEIGHT - bubbleLevel),
                BUBBLE_U, BUBBLE_V + (BUBBLE_HEIGHT - bubbleLevel),
                BUBBLE_WIDTH, bubbleLevel,
                256,256
        );
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
