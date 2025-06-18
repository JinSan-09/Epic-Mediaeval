package com.sanjin.network.packet;

import com.sanjin.enums.PeasantGuiTabType;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record SwitchPeasantTabPacket(int entityId, String tabName) implements CustomPacketPayload {

    public static final Type<SwitchPeasantTabPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath("epicmediaeval", "switch_peasant_tab"));

    public static final StreamCodec<ByteBuf, SwitchPeasantTabPacket> STREAM_CODEC = StreamCodec.composite(
                    ByteBufCodecs.INT, SwitchPeasantTabPacket::entityId,
                    ByteBufCodecs.STRING_UTF8, SwitchPeasantTabPacket::tabName,
                    SwitchPeasantTabPacket::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return null;
    }

    public PeasantGuiTabType getTabType() {
        try {
            return PeasantGuiTabType.valueOf(tabName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PeasantGuiTabType.BASIC_INFO; // 默认选项卡
        }
    }
}
