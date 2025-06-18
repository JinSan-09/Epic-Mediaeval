package com.sanjin.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record OpenPeasantGuiPacket(int entityId) implements CustomPacketPayload {

    public static final Type<OpenPeasantGuiPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("epicmediaeval", "open_peasant_gui"));

    public static final StreamCodec<ByteBuf, OpenPeasantGuiPacket> STREAM_CODEC = StreamCodec.composite(
                    ByteBufCodecs.INT, OpenPeasantGuiPacket::entityId,
                    OpenPeasantGuiPacket::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
