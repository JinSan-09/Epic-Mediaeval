package com.sanjin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record UpdatePeasantRelationshipPacket(int entityId, UUID playerId, int favorability, int yield) implements CustomPacketPayload {

    public static final Type<UpdatePeasantRelationshipPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("epicmediaeval", "update_peasant_relationship"));

    public static final StreamCodec<ByteBuf, UpdatePeasantRelationshipPacket> STREAM_CODEC = StreamCodec.composite(
                    ByteBufCodecs.INT, UpdatePeasantRelationshipPacket::entityId,
                    ByteBufCodecs.STRING_UTF8.map(UUID::fromString, UUID::toString), UpdatePeasantRelationshipPacket::playerId,
                    ByteBufCodecs.INT, UpdatePeasantRelationshipPacket::favorability,
                    ByteBufCodecs.INT, UpdatePeasantRelationshipPacket::yield,
                    UpdatePeasantRelationshipPacket::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
