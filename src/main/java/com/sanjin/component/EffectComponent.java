package com.sanjin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.effect.MobEffectInstance;

public record EffectComponent(MobEffectInstance effect, float probability) {
    public static final Codec<EffectComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MobEffectInstance.CODEC.fieldOf("effect").forGetter(EffectComponent::effect),
            Codec.FLOAT.fieldOf("probability").forGetter(EffectComponent::probability)
            ).apply(instance, EffectComponent::new));
}
