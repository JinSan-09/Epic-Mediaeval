package com.sanjin.event;

import com.sanjin.EpicMediaeval;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import org.jetbrains.annotations.NotNull;

public class StormWineEventHandler {

    private static final String STORM_EFFECT_TAG = EpicMediaeval.MODID + ":storm_create_effect_expire";

    @SubscribeEvent
    public static void stormCreate(@NotNull AttackEntityEvent event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        Level level = player.level();

        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        CompoundTag persistentData = player.getPersistentData();
        if (!persistentData.contains(STORM_EFFECT_TAG)) {
            return;
        }

        long expireTick = persistentData.getLong(STORM_EFFECT_TAG);
        long now = level.getGameTime();

        if (expireTick > now) {
            Vec3 pos = target.position();

            // 连续生成 5 道闪电
            for (int i = 0; i < 5; i++) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel, EntitySpawnReason.EVENT);
                if (bolt != null) {
                    bolt.setPos(pos.x, pos.y, pos.z);
                    serverLevel.addFreshEntity(bolt);
                }
            }
        }
    }
}
