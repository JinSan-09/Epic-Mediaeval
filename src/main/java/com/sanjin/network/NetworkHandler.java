package com.sanjin.network;

import com.sanjin.EpicMediaeval;
import com.sanjin.data.PeasantInteractionHistory;
import com.sanjin.entity.AbstractPeasantEntity;
import com.sanjin.enums.PeasantFavorabilityLevel;
import com.sanjin.enums.PeasantInteractionType;
import com.sanjin.enums.PeasantYieldLevel;
import com.sanjin.menu.PeasantMenu;
import com.sanjin.network.packet.RecruitPeasantPacket;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;

public class NetworkHandler {

    public static void register(@NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(EpicMediaeval.MODID);

        registrar.playToServer(
                RecruitPeasantPacket.TYPE,
                RecruitPeasantPacket.STREAM_CODEC,
                NetworkHandler::handleRecruitPeasant
        );
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
}
