package com.sanjin.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public enum PeasantProfession implements StringRepresentable {

    UNEMPLOYED("unemployed", "无业", Items.BARRIER, 0x888888),
    SOLDIER("soldier", "士兵", Items.IRON_SWORD, 0xFF0000),
    FARMER("farmer", "农民", Items.IRON_HOE, 0x00FF00),
    ALCHEMIST("alchemist", "药剂师", Items.BREWING_STAND, 0x9C27B0),
    COOK("cook", "厨师", Items.COOKED_BEEF, 0xFF8C00),
    MINER("miner", "矿工", Items.IRON_PICKAXE, 0x696969),
    BUILDER("builder", "建筑师", Items.BRICKS, 0x8B4513),
    GUARD("guard", "守卫", Items.SHIELD, 0x4169E1);

    private final String name;
    private final String displayName;
    private final ItemStack icon;
    private final int color;

    PeasantProfession(String name, String displayName, net.minecraft.world.item.Item iconItem, int color) {
        this.name = name;
        this.displayName = displayName;
        this.icon = new ItemStack(iconItem);
        this.color = color;
    }

    public @NotNull Component getDisplayName() {
        return Component.literal(displayName);
    }

    public @NotNull ItemStack getIcon() {
        return icon.copy();
    }

    public int getColor() {
        return color;
    }

    public @NotNull ResourceLocation getTexture() {
        return ResourceLocation.fromNamespaceAndPath("epicmediaeval",
                "textures/entity/professions/" + name + ".png");
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

}
