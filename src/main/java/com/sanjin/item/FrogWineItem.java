package com.sanjin.item;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class FrogWineItem extends AbstractTimeComponentTagWineItem {

    public FrogWineItem(Properties properties, boolean hasEnchantmentEffect, List<Component> tooltipLines, EffectComponent effects, UseRemainderComponent remainder) {
        super(properties, hasEnchantmentEffect, tooltipLines, effects, remainder);
    }

    @Override
    protected String getComponentTagName() {
        return EpicMediaeval.MODID + ":frog_create_effect_expire";
    }

}
