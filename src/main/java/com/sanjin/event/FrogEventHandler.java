package com.sanjin.event;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.FrogCreateComponent;
import com.sanjin.register.ModComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class FrogEventHandler {

    private static final String FROG_EFFECT_TAG = EpicMediaeval.MODID + ":frog_effect_expire";

    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        CompoundTag persistentData = player.getPersistentData();
        if (!persistentData.contains(FROG_EFFECT_TAG)) {
            return;
        }

        long expireTick = persistentData.getLong(FROG_EFFECT_TAG);
        long now = level.getGameTime();

        if (expireTick > now) {
            Frog frog = EntityType.FROG.create(serverLevel, EntitySpawnReason.EVENT);
            if (frog != null) {
                Vec3 pos = player.position();
                frog.setPos(pos.x, pos.y, pos.z);
                frog.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20*300, 0, false, false));
                frog.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20*300, 1, false, false));
                serverLevel.addFreshEntity(frog);
            }
        }
    }
}
