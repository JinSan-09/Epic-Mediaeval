package com.sanjin.event;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.FrogCreateComponent;
import com.sanjin.register.ModComponents;
import net.minecraft.core.component.DataComponentType;
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
    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Level level = player.level();

        if (!(level instanceof ServerLevel)) return;

        DataComponentType<FrogCreateComponent> type = ModComponents.FROG_CREATE_COMPONENT.get();
        FrogCreateComponent comp = null;

        long expireTick = comp.getTick();
        long now = level.getGameTime();
        EpicMediaeval.LOGGER.info("[FrogWine] onPlayerJump 读取 expireTick={}，当前刻={}", expireTick, now);

        // 3. 仅在服务端且仍在有效期内才生成青蛙
        if (level instanceof ServerLevel serverLevel && expireTick > now) {
            Frog frog = EntityType.FROG.create(serverLevel, EntitySpawnReason.EVENT);
            if (frog != null) {
                Vec3 pos = player.position();
                frog.setPos(pos.x, pos.y, pos.z);
                // 加发光和漂浮效果
                frog.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 0, false, false));
                frog.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 1, false, false));
                serverLevel.addFreshEntity(frog);
                EpicMediaeval.LOGGER.info("[FrogWine] 已生成青蛙 at x={} y={} z={}", pos.x, pos.y, pos.z);
            }
        }
    }
}
