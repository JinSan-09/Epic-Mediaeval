package com.sanjin.component;

import net.minecraft.world.effect.MobEffectInstance;

public record DrinkableComponent(MobEffectInstance effect, float probability) {
}
