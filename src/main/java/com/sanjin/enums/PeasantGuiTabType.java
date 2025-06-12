package com.sanjin.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PeasantGuiTabType implements StringRepresentable {
    BASIC_INFO("basic_info", PeasantFavorabilityLevel.STRANGER, PeasantYieldLevel.INDIFFERENT, "基本信息", "peasant_tab_basic_info"),
    TRADE_HISTORY("trade", PeasantFavorabilityLevel.ACQUAINTANCE, PeasantYieldLevel.INDIFFERENT, "交易", "peasant_tab_trade"),
    SECRETS("equipment_config", PeasantFavorabilityLevel.STRANGER, PeasantYieldLevel.SERVANT, "装备配置", "peasant_tab_equipment_config");

    private final String name;
    private final PeasantFavorabilityLevel requiredFavorabilityLevel;
    private final PeasantYieldLevel requiredYieldLevel;
    private final String displayName;
    private final String iconTexture;

    PeasantGuiTabType(String name, PeasantFavorabilityLevel RLOne, PeasantYieldLevel RLTwo, String displayName, String iconTexture) {
        this.name = name;
        this.requiredFavorabilityLevel = RLOne;
        this.requiredYieldLevel = RLTwo;
        this.displayName = displayName;
        this.iconTexture = iconTexture;

    }

    public PeasantFavorabilityLevel getRequiredFavorabilityLevel() {
        return requiredFavorabilityLevel;
    }

    public PeasantYieldLevel getRequiredYieldLevel() {
        return requiredYieldLevel;
    }

    public @NotNull Component getDisplayName() {
        return Component.literal(displayName);
    }

    public @NotNull ResourceLocation getIconTexture() {
        return ResourceLocation.fromNamespaceAndPath("epicmediaeval", "textures/gui/tabs/" + iconTexture + ".png");
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    public boolean isUnlockedByFav(@NotNull PeasantFavorabilityLevel level) {
        return level.ordinal() >= requiredFavorabilityLevel.ordinal();
    }

    public boolean isUnlockedByYie(@NotNull PeasantYieldLevel level){
        return level.ordinal() >= requiredYieldLevel.ordinal();
    }

    public static PeasantGuiTabType @NotNull [] getUnlockedTabs(PeasantFavorabilityLevel FavLevel,  PeasantYieldLevel YieLevel) {
        return java.util.Arrays.stream(values())
                .filter(tab -> tab.isUnlockedByFav(FavLevel))
                .filter(tab -> tab.isUnlockedByYie(YieLevel))
                .toArray(PeasantGuiTabType[]::new);
    }
}
