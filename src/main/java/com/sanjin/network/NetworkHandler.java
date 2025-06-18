package com.sanjin.network;

import com.sanjin.EpicMediaeval;
import com.sanjin.menu.PeasantMenu;
import com.sanjin.network.packet.OpenPeasantGuiPacket;
import com.sanjin.network.packet.SwitchPeasantTabPacket;
import com.sanjin.network.packet.UpdatePeasantRelationshipPacket;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

public class NetworkHandler {

    public static void register(@NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(EpicMediaeval.MODID);

        // 注册数据包类型和处理器
        registrar.playToServer(
                OpenPeasantGuiPacket.TYPE,
                OpenPeasantGuiPacket.STREAM_CODEC,
                NetworkHandler::handleOpenPeasantGui
        );

        registrar.playToServer(
                SwitchPeasantTabPacket.TYPE,
                SwitchPeasantTabPacket.STREAM_CODEC,
                NetworkHandler::handleSwitchTab
        );

        registrar.playToClient(
                UpdatePeasantRelationshipPacket.TYPE,
                UpdatePeasantRelationshipPacket.STREAM_CODEC,
                NetworkHandler::handleUpdateRelationship
        );
    }

    private static void handleOpenPeasantGui(OpenPeasantGuiPacket packet, @NotNull IPayloadContext context) {
        context.enqueueWork(() -> {

        });
    }

    private static void handleSwitchTab(SwitchPeasantTabPacket packet, @NotNull IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player.containerMenu instanceof PeasantMenu peasantMenu) {
                // 切换选项卡
                peasantMenu.setCurrentTab(packet.getTabType());
            }
        });
    }

    private static void handleUpdateRelationship(UpdatePeasantRelationshipPacket packet, @NotNull IPayloadContext context) {
        context.enqueueWork(() -> {
            // 客户端更新关系数据的处理
            var player = context.player();
            if (player.level().getEntity(packet.entityId()) instanceof com.sanjin.entity.AbstractPeasantEntity peasant) {

            }
        });
    }

}
