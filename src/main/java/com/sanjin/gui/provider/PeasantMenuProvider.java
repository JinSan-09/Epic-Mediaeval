package com.sanjin.gui.provider;

import com.sanjin.entity.AbstractPeasantEntity;
import com.sanjin.menu.PeasantMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PeasantMenuProvider implements MenuProvider {

    private final AbstractPeasantEntity peasant;

    public PeasantMenuProvider(AbstractPeasantEntity peasant) {
        this.peasant = peasant;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
        return new PeasantMenu(containerId, inventory, createExtraData());
    }

    private @NotNull FriendlyByteBuf createExtraData() {
        FriendlyByteBuf buffer = new FriendlyByteBuf(io.netty.buffer.Unpooled.buffer());
        buffer.writeInt(peasant.getId());
        return buffer;
    }
}
