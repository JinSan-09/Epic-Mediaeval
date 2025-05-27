package com.sanjin.item;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class StormWineItem extends AbstractTimeComponentTagWineItem{

    public StormWineItem(Properties properties, boolean hasEnchantmentEffect, List<Component> tooltipLines, EffectComponent effects, UseRemainderComponent remainder) {
        super(properties, hasEnchantmentEffect, tooltipLines, effects, remainder);
    }

    @Override
    public void onEffectApplied(Player player, Level level, long expireTick) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 100F, 1.0F);
        serverLevel.setWeatherParameters(0, 20 * 60 * 5, true, true);
    }

    @Override
    protected String getComponentTagName() {
        return EpicMediaeval.MODID + ":storm_create_effect_expire";
    }
}
