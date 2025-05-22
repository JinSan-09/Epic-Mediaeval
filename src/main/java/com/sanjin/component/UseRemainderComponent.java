package com.sanjin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record UseRemainderComponent(ItemStack itemStack, int count) {

    public static final UseRemainderComponent EMPTY = new UseRemainderComponent(ItemStack.EMPTY, 0);
    
    public static final Codec<UseRemainderComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ItemStack.CODEC.fieldOf("itemStack").forGetter(UseRemainderComponent::itemStack),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(UseRemainderComponent::count)
            ).apply(instance, UseRemainderComponent::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, UseRemainderComponent> NETWORK_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, UseRemainderComponent::itemStack,
            ByteBufCodecs.INT, UseRemainderComponent::count,
            UseRemainderComponent::new
    );

    public static UseRemainderComponent of(ItemStack itemStack, int count) {
        return new UseRemainderComponent(itemStack, count);
    }
}

