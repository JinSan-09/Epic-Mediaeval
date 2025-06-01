package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.entity.mobentity.FemalePeasantEntity;
import com.sanjin.entity.mobentity.MalePeasantEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMobEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, EpicMediaeval.MODID);

    public static final Supplier<EntityType<FemalePeasantEntity>> FEMALE_PEASANT_ENTITY = ENTITY_TYPES.register("female_peasant", () ->
                    EntityType.Builder.of(FemalePeasantEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.95F)
                            .clientTrackingRange(10)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "female_peasant")))
    );

    public static final Supplier<EntityType<MalePeasantEntity>> MALE_PEASANT_ENTITY = ENTITY_TYPES.register("male_peasant", () ->
                    EntityType.Builder.of(MalePeasantEntity::new, MobCategory.CREATURE)
                            .sized(0.6F, 1.95F)
                            .clientTrackingRange(10)
                            .updateInterval(3)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "male_peasant")))
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
