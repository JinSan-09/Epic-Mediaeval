package com.sanjin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public record UseRemainderComponent(Item item, int count) {
    public static final Codec<UseRemainderComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(UseRemainderComponent::item),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(UseRemainderComponent::count)
            ).apply(instance, UseRemainderComponent::new)
    );}

