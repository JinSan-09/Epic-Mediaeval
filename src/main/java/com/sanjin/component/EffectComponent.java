package com.sanjin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public record EffectComponent(List<MobEffectInstance> effects) {
    public static final Codec<EffectComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(EffectComponent::effects)
            ).apply(instance,EffectComponent::new)
    );

}
