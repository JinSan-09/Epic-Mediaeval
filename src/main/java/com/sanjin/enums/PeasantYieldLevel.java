package com.sanjin.enums;

import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PeasantYieldLevel implements StringRepresentable {

    ENEMY(-10, "enemy", 0x111111, "仇敌"),
    INDIFFERENT(0, "indifferent", 0x888888, "冷漠"),
    HELPER(20, "helper", 0x4CAF50, "帮手"),
    SERVANT(70, "servant",  0x9C27B0, "仆人"),
    LOYAL_SERVANT(100, "loyal_servant", 0xFFD700, "忠仆");

    private final int requiredPoints;
    private final String name;
    private final int color;
    private final String displayName;

    PeasantYieldLevel(int requiredPoints, String name, int color, String displayName) {
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

    public static PeasantYieldLevel fromPoints(int points) {
        PeasantYieldLevel result = INDIFFERENT;
        for (PeasantYieldLevel level : values()) {
            if (points >= level.requiredPoints) {
                result = level;
            } else {
                break;
            }
        }
        return result;
    }

    // Get the next level
    public PeasantYieldLevel getNext() {
        int nextOrdinal = ordinal() + 1;
        return nextOrdinal < values().length ? values()[nextOrdinal] : this;
    }

    // Get the number of points that need to get to the next level
    public int getPointsToNext(int currentPoints) {
        PeasantYieldLevel next = getNext();
        if (next == this) return 0;
        return Math.max(0, next.requiredPoints - currentPoints);
    }

    // Get the percentage of progress for the current level
    public float getProgress(int currentPoints) {
        if (this == LOYAL_SERVANT) return 1.0f;

        PeasantYieldLevel next = getNext();
        int levelRange = next.requiredPoints - this.requiredPoints;
        int currentProgress = currentPoints - this.requiredPoints;

        return Math.max(0, Math.min(1, (float) currentProgress / levelRange));
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }
}
