package com.sanjin.network.packet;

import com.sanjin.EpicMediaeval;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record RecruitPeasantPacket(int entityId) implements CustomPacketPayload {

    public static final Type<RecruitPeasantPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "recruit_peasant"));

    public static final StreamCodec<ByteBuf, RecruitPeasantPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, RecruitPeasantPacket::entityId,
            RecruitPeasantPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
