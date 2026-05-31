package com.sanjin.menu;

import com.sanjin.entity.AbstractPeasantEntity;
import com.sanjin.enums.PeasantFavorabilityLevel;
import com.sanjin.enums.PeasantGuiTabType;
import com.sanjin.enums.PeasantYieldLevel;
import com.sanjin.register.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;

public class PeasantMenu extends AbstractContainerMenu {

    private final AbstractPeasantEntity peasant;
    private final Player player;
    private final ContainerData data;
    private PeasantGuiTabType currentTab;

    public PeasantMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory, getPeasantFromBuffer(inventory.player, extraData));
    }

    protected PeasantMenu(int containerId, @NotNull Inventory inventory, AbstractPeasantEntity peasant) {
        super(ModMenus.PEASANT_MENU.get(), containerId);

        this.peasant = peasant;
        this.player = inventory.player;
        this.data = new SimpleContainerData(4);
        this.currentTab = getDefaultTab();

        this.addDataSlots(this.data);

        int invY = 159;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 36 + col * 18, invY + row * 18));
            }
        }
        int hotbarY = 217;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new net.minecraft.world.inventory.Slot(inventory, col, 36 + col * 18, hotbarY));
        }

        updateContainerData();
    }

    private static AbstractPeasantEntity getPeasantFromBuffer(@NotNull Player player, @NotNull FriendlyByteBuf buffer) {
        int entityId = buffer.readInt();
        if (player.level().getEntity(entityId) instanceof AbstractPeasantEntity peasant) {
            return peasant;
        }
        throw new IllegalStateException("Invalid peasant entity ID: " + entityId);
    }

    private void updateContainerData() {
        UUID playerId = player.getUUID();
        int favorability = peasant.getRelationshipComponent().getFavorability(playerId);
        int yield = peasant.getRelationshipComponent().getYield(playerId);

        this.data.set(0, favorability + 100);
        this.data.set(1, yield + 100);
        this.data.set(2, peasant.getRelationshipComponent().refusesToTrade(playerId) ? 0 : 1);
        this.data.set(3, peasant.hasOwner() ? 1 : 0);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@Nonnull Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return peasant.isAlive() && player.distanceToSqr(peasant) < 64.0D && this.player.equals(player);
    }

    public AbstractPeasantEntity getPeasant() {
        return peasant;
    }

    public Player getPlayer() {
        return player;
    }

    public PeasantGuiTabType getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(PeasantGuiTabType tab) {
        if (canAccessTab(tab)) {
            this.currentTab = tab;
        }
    }

    public boolean canAccessTab(PeasantGuiTabType tab) {
        return peasant.getRelationshipComponent().hasTabUnlocked(player.getUUID(), tab);
    }

    public Set<PeasantGuiTabType> getUnlockedTabs() {
        return peasant.getRelationshipComponent().getUnlockedTabs(player.getUUID());
    }

    private PeasantGuiTabType getDefaultTab() {
        Set<PeasantGuiTabType> unlockedTabs = getUnlockedTabs();
        if (unlockedTabs.contains(PeasantGuiTabType.BASIC_INFO)) {
            return PeasantGuiTabType.BASIC_INFO;
        }
        return unlockedTabs.iterator().next();
    }

    public int getFavorability() {
        return this.data.get(0) - 100;
    }

    public int getYield() {
        return this.data.get(1) - 100;
    }

    public boolean canTrade() {
        return this.data.get(2) == 1;
    }

    public boolean peasantHasOwner() {
        return this.data.get(3) == 1;
    }

    public PeasantFavorabilityLevel getFavorabilityLevel() {
        return PeasantFavorabilityLevel.fromPoints(getFavorability());
    }

    public PeasantYieldLevel getYieldLevel() {
        return PeasantYieldLevel.fromPoints(getYield());
    }
}
