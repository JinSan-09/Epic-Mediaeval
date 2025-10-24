package com.sanjin.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public record EffectComponent(List<MobEffectInstance> effects) {

    public static final EffectComponent EMPTY = new EffectComponent(List.of());

    public static final Codec<EffectComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(EffectComponent::effects)
            ).apply(instance,EffectComponent::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, EffectComponent> STREAM_CODEC = StreamCodec.composite(
            getMobEffectInstanceStreamCodec(), EffectComponent::effects,
            EffectComponent::new
    );

    private static StreamCodec<RegistryFriendlyByteBuf, List<MobEffectInstance>> getMobEffectInstanceStreamCodec (){
        return StreamCodec.of(
                (buf,list) -> {
                    buf.writeInt(list.size());
                    for(MobEffectInstance effectInstance : list){
                        MobEffectInstance.STREAM_CODEC.encode(buf,effectInstance);
                    }
                },
                buf -> {
                    int size = buf.readInt();
                    List<MobEffectInstance> list = new ArrayList<>(size);
                    for(int i = 0; i < size; i++){
                        list.add(MobEffectInstance.STREAM_CODEC.decode(buf));
                    }
                    return list;
                }
        );
    }
}
