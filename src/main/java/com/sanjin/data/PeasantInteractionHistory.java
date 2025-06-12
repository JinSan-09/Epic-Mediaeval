package com.sanjin.data;

import com.sanjin.enums.PeasantInteractionType;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PeasantInteractionHistory {

    private final PeasantInteractionType type;
    private final long timestamp;
    private final int favorabilityChange;
    private final int yieldChange;
    private final ItemStack involvedItem;
    private final String description;

    public PeasantInteractionHistory(PeasantInteractionType type, int favorabilityChange, int yieldChange, @NotNull ItemStack involvedItem, String description) {
        this.type = type;
        this.timestamp = System.currentTimeMillis();
        this.favorabilityChange = favorabilityChange;
        this.yieldChange = yieldChange;
        this.involvedItem = involvedItem.copy();
        this.description = description;
    }

    public PeasantInteractionHistory(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        this.type = PeasantInteractionType.valueOf(tag.getString("type"));
        this.timestamp = tag.getLong("timestamp");
        this.favorabilityChange = tag.getInt("favorabilityChange");
        this.yieldChange = tag.getInt("yieldChange");
        this.involvedItem = ItemStack.parseOptional(provider, tag.getCompound("item"));
        this.description = tag.getString("description");
    }

    public CompoundTag toNBT(@NotNull HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", type.name());
        tag.putLong("timestamp", timestamp);
        tag.putInt("favorabilityChange", favorabilityChange);
        tag.putInt("yieldChange", yieldChange);
        tag.put("item", involvedItem.save(provider, new CompoundTag()));
        tag.putString("description", description);
        return tag;
    }

    public PeasantInteractionType getType() { return type; }
    public long getTimestamp() { return timestamp; }
    public int getFavorabilityChange() { return favorabilityChange; }
    public int getYieldChange() { return yieldChange; }
    public ItemStack getInvolvedItem() { return involvedItem; }
    public String getDescription() { return description; }

}
