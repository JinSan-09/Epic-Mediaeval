package com.sanjin.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PeasantFavorabilityLevel implements StringRepresentable {

    HATER(-10, "hater",0x111111,"仇视者"),
    STRANGER(0, "stranger", 0x888888, "陌生人"),
    ACQUAINTANCE(20, "acquaintance", 0x4CAF50, "熟人"),
    FRIEND(50, "friend", 0x2196F3, "朋友"),
    CLOSE_FRIEND(80, "close_friend", 0x9C27B0, "密友"),
    BEST_FRIEND(100, "best_friend", 0xFFD700, "挚友");

    private final int requiredPoints;
    private final String name;
    private final int color;
    private final String displayName;

    PeasantFavorabilityLevel(int requiredPoints, String name, int color, String displayName) {
        this.requiredPoints = requiredPoints;
        this.name = name;
        this.color = color;
        this.displayName = displayName;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public int getColor() {
        return color;
    }

    public @NotNull Component getDisplayName() {
        return Component.literal(displayName);
    }

    // Rank is obtained based on the number of opinion points
    public static PeasantFavorabilityLevel fromPoints(int points) {
        PeasantFavorabilityLevel result = STRANGER;
        for (PeasantFavorabilityLevel level : values()) {
            if (points >= level.requiredPoints) {
                result = level;
            } else {
                break;
            }
        }
        return result;
    }

    // Get the next level
    public PeasantFavorabilityLevel getNext() {
        int nextOrdinal = ordinal() + 1;
        return nextOrdinal < values().length ? values()[nextOrdinal] : this;
    }

    // Get the number of points that need to get to the next level
    public int getPointsToNext(int currentPoints) {
        PeasantFavorabilityLevel next = getNext();
        if (next == this) return 0;
        return Math.max(0, next.requiredPoints - currentPoints);
    }

    // Get the percentage of progress for the current level
    public float getProgress(int currentPoints) {
        if (this == BEST_FRIEND) return 1.0f;

        PeasantFavorabilityLevel next = getNext();
        int levelRange = next.requiredPoints - this.requiredPoints;
        int currentProgress = currentPoints - this.requiredPoints;

        return Math.max(0, Math.min(1, (float) currentProgress / levelRange));
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
