package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.entity.itemprojectile.OnionProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemEntities {

    // 创建实体类型注册器
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, EpicMediaeval.MODID);

    // 注册洋葱投掷物实体
    public static final DeferredHolder<EntityType<?>, EntityType<OnionProjectile>> ONION_ENTITY = ENTITY_TYPES.register("onion_entity",
            () -> EntityType.Builder.<OnionProjectile>of(OnionProjectile::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE,ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "onion_entity"))));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
