package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EpicMediaeval.MODID);

    public static final FoodProperties HIGH_GRADE_FOOD = new FoodProperties.Builder().nutrition(8).saturationModifier(1.0f).build();
    public static final FoodProperties MIDDLE_GRADE_FOOD = new FoodProperties.Builder().nutrition(5).saturationModifier(0.5f).build();
    public static final FoodProperties LOW_GRADE_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0).build();

    public static final Supplier<DataComponentType<UseRemainderComponent>> USE_REMAINDER_COMPONENT = REGISTRAR.registerComponentType("use_remainder_component",
            builder -> builder.persistent(UseRemainderComponent.CODEC));
    public static final Supplier<DataComponentType<EffectComponent>> EFFECT_COMPONENT = REGISTRAR.registerComponentType("effect_component",
            builder -> builder.persistent(EffectComponent.CODEC));
}
