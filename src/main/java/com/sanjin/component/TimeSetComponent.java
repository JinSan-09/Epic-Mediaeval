package com.sanjin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record TimeSetComponent(Long aLong) {

    public static final TimeSetComponent EMPTY = new TimeSetComponent(0L);

    public static final Codec<TimeSetComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("along").forGetter(TimeSetComponent::aLong)
            ).apply(instance, instance.stable(TimeSetComponent::new))
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, TimeSetComponent> NETWORK_CODEC = StreamCodec.composite(
            ByteBufCodecs.LONG, TimeSetComponent::aLong,
            TimeSetComponent::new
    );
}

