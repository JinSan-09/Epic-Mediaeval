package com.sanjin.renderer.renderstate;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.item.ItemStack;

public class PeasantRenderState extends HumanoidRenderState {

    private boolean isSlimModel = false;
    private String texturePath = "";

    public boolean showHat = true;
    public boolean showJacket = true;
    public boolean showLeftPants = true;
    public boolean showRightPants = true;
    public boolean showLeftSleeve = true;
    public boolean showRightSleeve = true;

    public ItemStack leftHandItem = ItemStack.EMPTY;
    public ItemStack rightHandItem = ItemStack.EMPTY;

    public ItemStack helmet = ItemStack.EMPTY;
    public ItemStack chestplate = ItemStack.EMPTY;
    public ItemStack leggings = ItemStack.EMPTY;
    public ItemStack boots = ItemStack.EMPTY;

    public PeasantRenderState() {
        super();
    }

    public boolean isSlimModel() {
        return isSlimModel;
    }

    public void setSlimModel(boolean slimModel) {
        this.isSlimModel = slimModel;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
    }
}
