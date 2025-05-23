package com.sanjin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record FrogCreateComponent(Long aLong) {

    public static final FrogCreateComponent EMPTY = new FrogCreateComponent(0L);

    public static final Codec<FrogCreateComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.LONG.fieldOf("along").forGetter(FrogCreateComponent::aLong)
            ).apply(instance, instance.stable(FrogCreateComponent::new))
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, FrogCreateComponent> NETWORK_CODEC = StreamCodec.composite(
            ByteBufCodecs.LONG,FrogCreateComponent::aLong,
            FrogCreateComponent::new
    );

    public Long getTick(){
        return aLong;
    }
}

