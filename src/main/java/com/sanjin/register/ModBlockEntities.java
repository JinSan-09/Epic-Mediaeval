package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.block.entity.StewStoveBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, EpicMediaeval.MODID);

    public static final Supplier<BlockEntityType<StewStoveBlockEntity>> STEW_STOVE_BLOCK_ENTITY = TILES.register("stew_stove", () ->
            new BlockEntityType<>(StewStoveBlockEntity::new, ModBlocks.STEW_STOVE_BLOCK.get()));

    public static void register(IEventBus eventBus) {
        TILES.register(eventBus);
    }
 }
