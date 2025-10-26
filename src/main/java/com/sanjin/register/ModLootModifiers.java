package com.sanjin.register;

import java.util.function.Supplier;

import com.mojang.serialization.MapCodec;
import com.sanjin.EpicMediaeval;
import com.sanjin.loot.AddSingleItemModifier;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModLootModifiers {
    
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
        DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, EpicMediaeval.MODID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> ADD_SEEDS_TO_GRASS =
        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_seeds_to_grass", () -> AddSingleItemModifier.CODEC);

    public static void register(IEventBus eventBus) {
        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
