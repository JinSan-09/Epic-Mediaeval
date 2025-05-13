package com.sanjin.entity.blockentity;

import com.sanjin.menu.FermentationBarrelMenu;
import com.sanjin.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FermentationBarrelBlockEntity extends BlockEntity implements MenuProvider, EntityBlock {

    private static final int MATERIAL_SLOTS_START = 0;
    private static final int MATERIAL_SLOTS_COUNT = 4;

    private final ItemStackHandler inventory = new ItemStackHandler(4);
    private final ContainerData data = new SimpleContainerData(1);

    public FermentationBarrelBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FERMENTATION_BARREL_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container.fermentation_barrel.text");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
        if (level != null) {
            return new FermentationBarrelMenu(id, inventory, this.inventory, ContainerLevelAccess.create(level, worldPosition), this.data);
        }
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new FermentationBarrelBlockEntity(pos, state);
    }
}
