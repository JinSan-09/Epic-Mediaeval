package com.sanjin.network;

import com.sanjin.EpicMediaeval;
import com.sanjin.data.PeasantInteractionHistory;
import com.sanjin.entity.AbstractPeasantEntity;
import com.sanjin.enums.PeasantFavorabilityLevel;
import com.sanjin.enums.PeasantInteractionType;
import com.sanjin.enums.PeasantYieldLevel;
import com.sanjin.menu.PeasantMenu;
import com.sanjin.network.packet.OpenPeasantGuiPacket;
import com.sanjin.network.packet.RecruitPeasantPacket;
import com.sanjin.network.packet.SwitchPeasantTabPacket;
import com.sanjin.network.packet.UpdatePeasantRelationshipPacket;
import net.minecraft.network.chat.Component;
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

        registrar.playToServer(
                RecruitPeasantPacket.TYPE,
                RecruitPeasantPacket.STREAM_CODEC,
                NetworkHandler::handleRecruitPeasant
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

    private static void handleRecruitPeasant(RecruitPeasantPacket packet, @NotNull IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (!(player.containerMenu instanceof PeasantMenu peasantMenu)) {
                return;
            }
            if (peasantMenu.getPeasant().getId() != packet.entityId()) {
                return;
            }
            if (!(player.level().getEntity(packet.entityId()) instanceof AbstractPeasantEntity peasant)) {
                return;
            }
            if (!peasant.isAlive() || player.distanceToSqr(peasant) >= 64.0D) {
                return;
            }

            if (peasant.hasOwner()) {
                Component message = peasant.isOwner(player)
                        ? Component.translatable("message.peasant.recruit.already_owner", peasant.getName())
                        : Component.translatable("message.peasant.recruit.other_owner", peasant.getName());
                player.displayClientMessage(message, true);
                return;
            }

            int favorability = peasant.getRelationshipComponent().getFavorability(player.getUUID());
            int yield = peasant.getRelationshipComponent().getYield(player.getUUID());
            if (favorability < PeasantFavorabilityLevel.FRIEND.getRequiredPoints() ||
                    yield < PeasantYieldLevel.SERVANT.getRequiredPoints()) {
                player.displayClientMessage(Component.translatable(
                        "message.peasant.recruit.insufficient",
                        PeasantFavorabilityLevel.FRIEND.getRequiredPoints(),
                        PeasantYieldLevel.SERVANT.getRequiredPoints()
                ), true);
                return;
            }

            peasant.setOwnerUUID(player.getUUID());
            peasant.getRelationshipComponent().setMaster(player.getUUID());
            peasant.getRelationshipComponent().addInteractionHistory(player.getUUID(), new PeasantInteractionHistory(
                    PeasantInteractionType.QUEST_COMPLETION,
                    0,
                    0,
                    player.getMainHandItem(),
                    "玩家招募了农民"
            ));

            player.displayClientMessage(Component.translatable("message.peasant.recruit.success", peasant.getName()), true);
            peasantMenu.broadcastChanges();
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
