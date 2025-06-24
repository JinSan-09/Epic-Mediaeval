package com.sanjin.component;

import com.sanjin.data.PeasantInteractionHistory;
import com.sanjin.enums.PeasantFavorabilityLevel;
import com.sanjin.enums.PeasantGuiTabType;
import com.sanjin.enums.PeasantProfession;
import com.sanjin.enums.PeasantYieldLevel;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PeasantRelationshipComponent {

    private final Map<UUID, Integer> playerFavorability = new ConcurrentHashMap<>();
    private final Map<UUID, Integer> playerYield = new ConcurrentHashMap<>();
    private final Map<UUID, List<PeasantInteractionHistory>> interactionHistories = new ConcurrentHashMap<>();
    private final Map<UUID, Set<PeasantGuiTabType>> unlockedTabsCache = new ConcurrentHashMap<>();
    private static final int MAX_HISTORY_SIZE = 50;

    private PeasantProfession profession = PeasantProfession.UNEMPLOYED;


    public int getFavorability(UUID playerId) {
        return playerFavorability.getOrDefault(playerId, 0);
    }

    public int getYield(UUID playerId) {
        return playerYield.getOrDefault(playerId, 0);
    }

    public void setFavorability(UUID playerId, int points) {
        int clampedPoints = Math.max(-100, Math.min(200, points));
        playerFavorability.put(playerId, clampedPoints);
        unlockedTabsCache.remove(playerId);
    }

    public void setYield(UUID playerId, int points) {
        int clampedPoints = Math.max(-100, Math.min(200, points));
        playerYield.put(playerId, clampedPoints);
        unlockedTabsCache.remove(playerId);
    }

    /**
     * Increase or decrease the Opinion and Loyalty attributes
     */
    public int addFavorability(UUID playerId, int points) {
        int currentPoints = getFavorability(playerId);
        int newPoints = currentPoints + points;
        setFavorability(playerId, newPoints);
        return newPoints;
    }

    public int reduceFavorability(UUID playerId, int points) {
        return addFavorability(playerId, -points);
    }

    public int addYield(UUID playerId, int points) {
        int currentPoints = getYield(playerId);
        int newPoints = currentPoints + points;
        setYield(playerId, newPoints);
        return newPoints;
    }

    public int reduceYield(UUID playerId, int points) {
        return addYield(playerId, -points);
    }

    /**
     * Gain a player's opinion level and loyalty level
     */
    public PeasantFavorabilityLevel getFavorabilityLevel(UUID playerId) {
        return PeasantFavorabilityLevel.fromPoints(getFavorability(playerId));
    }

    public PeasantYieldLevel getYieldLevel(UUID playerId) {
        return PeasantYieldLevel.fromPoints(getYield(playerId));
    }

    /**
     * Get tabs unlocked by players
     */
    public Set<PeasantGuiTabType> getUnlockedTabs(UUID playerId) {
        return unlockedTabsCache.computeIfAbsent(playerId, id -> {
            PeasantFavorabilityLevel FavLevel = getFavorabilityLevel(id);
            PeasantYieldLevel YieldLevel = getYieldLevel(id);
            Set<PeasantGuiTabType> unlocked = EnumSet.noneOf(PeasantGuiTabType.class);
            for (PeasantGuiTabType tab : PeasantGuiTabType.values()) {
                if (tab.isUnlockedByFav(FavLevel)) {
                    unlocked.add(tab);
                }
                if (tab.isUnlockedByYie(YieldLevel)) {
                    unlocked.add(tab);
                }
            }
            return unlocked;
        });
    }

    public boolean hasTabUnlocked(UUID playerId, PeasantGuiTabType tab) {
        return getUnlockedTabs(playerId).contains(tab);
    }

    /**
     * Master and servant system
     */
    public boolean setProfession(UUID requesterId, PeasantProfession newProfession) {
        if (!isMaster(requesterId) && newProfession != PeasantProfession.UNEMPLOYED) {
            return false;
        }

        this.profession = newProfession;
        return true;
    }

    public PeasantProfession getProfession() {
        return profession;
    }

    public UUID getMaster() {
        return playerYield.entrySet().stream()
                .filter(entry -> entry.getValue() >= PeasantYieldLevel.SERVANT.getRequiredPoints())
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public boolean isMaster(UUID playerId) {
        UUID currentMaster = getMaster();
        return currentMaster != null && currentMaster.equals(playerId);
    }

    public void setMaster(UUID newMasterId) {
        for (UUID playerId : playerYield.keySet()) {
            if (!playerId.equals(newMasterId)) {
                playerYield.put(playerId, 0);
            }
        }

        int currentYield = getYield(newMasterId);
        if (currentYield < PeasantYieldLevel.SERVANT.getRequiredPoints()) {
            setYield(newMasterId, PeasantYieldLevel.SERVANT.getRequiredPoints());
        }

        unlockedTabsCache.clear();
    }

    public void dismissServant() {
        UUID master = getMaster();
        if (master != null) {
            playerYield.put(master, 0);
            unlockedTabsCache.remove(master);
        }
    }

    public boolean hasActiveMaster() {
        return getMaster() != null;
    }

    public boolean isHostileTowards(UUID playerId) {
        PeasantFavorabilityLevel favLevel = getFavorabilityLevel(playerId);
        PeasantYieldLevel yieldLevel = getYieldLevel(playerId);

        return favLevel == PeasantFavorabilityLevel.HATER ||
                yieldLevel == PeasantYieldLevel.ENEMY;
    }

    public boolean refusesToTrade(UUID playerId) {
        return isHostileTowards(playerId) ||
                getFavorability(playerId) < -5;
    }

    /**
     * Contract history
     */
    public void addInteractionHistory(UUID playerId, PeasantInteractionHistory interaction) {
        List<PeasantInteractionHistory> history = interactionHistories.computeIfAbsent(playerId, k -> new ArrayList<>());

        history.add(interaction);

        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
    }

    public List<PeasantInteractionHistory> getInteractionHistory(UUID playerId) {
        return interactionHistories.getOrDefault(playerId, new ArrayList<>());
    }

    public List<PeasantInteractionHistory> getRecentInteractionHistory(UUID playerId, int count) {
        List<PeasantInteractionHistory> history = getInteractionHistory(playerId);
        int size = history.size();
        if (size <= count) {
            return new ArrayList<>(history);
        }
        return new ArrayList<>(history.subList(size - count, size));
    }

    public void clearPlayerData(UUID playerId) {
        playerFavorability.remove(playerId);
        playerYield.remove(playerId);
        interactionHistories.remove(playerId);
        unlockedTabsCache.remove(playerId);
    }

    public Set<UUID> getAllPlayerIds() {
        Set<UUID> allIds = new HashSet<>();
        allIds.addAll(playerFavorability.keySet());
        allIds.addAll(playerYield.keySet());
        allIds.addAll(interactionHistories.keySet());
        return allIds;
    }

    /**
     * NBT data interaction
     */
    public CompoundTag toNBT(@NotNull HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();

        tag.putString("profession", profession.getSerializedName());

        CompoundTag favorabilityTag = new CompoundTag();
        for (Map.Entry<UUID, Integer> entry : playerFavorability.entrySet()) {
            favorabilityTag.putInt(entry.getKey().toString(), entry.getValue());
        }
        tag.put("favorability", favorabilityTag);

        CompoundTag yieldTag = new CompoundTag();
        for (Map.Entry<UUID, Integer> entry : playerYield.entrySet()) {
            yieldTag.putInt(entry.getKey().toString(), entry.getValue());
        }
        tag.put("yield", yieldTag);

        CompoundTag historyTag = new CompoundTag();
        for (Map.Entry<UUID, List<PeasantInteractionHistory>> entry : interactionHistories.entrySet()) {
            ListTag playerHistoryList = new ListTag();
            for (PeasantInteractionHistory interaction : entry.getValue()) {
                playerHistoryList.add(interaction.toNBT(provider));
            }
            historyTag.put(entry.getKey().toString(), playerHistoryList);
        }
        tag.put("history", historyTag);

        return tag;
    }

    public void fromNBT(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {

        playerFavorability.clear();
        playerYield.clear();
        interactionHistories.clear();
        unlockedTabsCache.clear();

        if (tag.contains("profession")) {
            try {
                this.profession = PeasantProfession.valueOf(tag.getString("profession").toUpperCase());
            } catch (IllegalArgumentException e) {
                this.profession = PeasantProfession.UNEMPLOYED;
            }
        }

        if (tag.contains("favorability")) {
            CompoundTag favorabilityTag = tag.getCompound("favorability");
            for (String key : favorabilityTag.getAllKeys()) {
                try {
                    UUID playerId = UUID.fromString(key);
                    int points = favorabilityTag.getInt(key);
                    playerFavorability.put(playerId, points);
                } catch (IllegalArgumentException ignored) {

                }
            }
        }
        if (tag.contains("yield")) {
            CompoundTag yieldTag = tag.getCompound("yield");
            for (String key : yieldTag.getAllKeys()) {
                try {
                    UUID playerId = UUID.fromString(key);
                    int points = yieldTag.getInt(key);
                    playerYield.put(playerId, points);
                } catch (IllegalArgumentException ignored) {

                }
            }
        }

        if (tag.contains("history")) {
            CompoundTag historyTag = tag.getCompound("history");
            for (String key : historyTag.getAllKeys()) {
                try {
                    UUID playerId = UUID.fromString(key);
                    ListTag playerHistoryList = historyTag.getList(key, Tag.TAG_COMPOUND);
                    List<PeasantInteractionHistory> history = new ArrayList<>();

                    for (int i = 0; i < playerHistoryList.size(); i++) {
                        CompoundTag interactionTag = playerHistoryList.getCompound(i);
                        try {
                            history.add(new PeasantInteractionHistory(interactionTag, provider));
                        } catch (Exception ignored) {

                        }
                    }

                    if (!history.isEmpty()) {
                        interactionHistories.put(playerId, history);
                    }
                } catch (IllegalArgumentException ignored) {

                }
            }
        }
    }

    public String getDebugInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("PeasantRelationshipComponent Debug Info:\n");
        sb.append("Total Players: ").append(getAllPlayerIds().size()).append("\n");

        for (UUID playerId : getAllPlayerIds()) {
            sb.append("Player ").append(playerId.toString(), 0, 8).append("...: ");
            sb.append("Favorability=").append(getFavorability(playerId));
            sb.append(", Level=").append(getFavorabilityLevel(playerId));
            sb.append(", Interactions=").append(getInteractionHistory(playerId).size());
            sb.append("\n");
        }

        return sb.toString();
    }

}
