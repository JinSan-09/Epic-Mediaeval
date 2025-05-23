package com.sanjin.item;

import com.sanjin.component.EffectComponent;
import com.sanjin.component.FrogCreateComponent;
import com.sanjin.component.UseRemainderComponent;
import com.sanjin.register.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class FrogWineItem extends WineItem {

    private final UseRemainderComponent remainder;
    private final EffectComponent effects;
    private final Random rand = new Random();

    public FrogWineItem(Properties properties, boolean hasEnchantmentEffect, List<Component> tooltipLines, EffectComponent effects, UseRemainderComponent remainder) {
        super(properties, hasEnchantmentEffect, tooltipLines, effects, remainder);
        this.remainder = remainder;
        this.effects = effects;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user) {
        ItemStack result = super.finishUsingItem(stack, level, user);
        for (var mobEffectInstance : effects.effects()) {
            if (rand.nextFloat() <= mobEffectInstance.getAmplifier()) {
                user.addEffect(new MobEffectInstance(
                        mobEffectInstance.getEffect(),
                        mobEffectInstance.getDuration(),
                        mobEffectInstance.getAmplifier(),
                        mobEffectInstance.isVisible(),
                        mobEffectInstance.showIcon()
                ));
            }
        }
        if (user instanceof Player player) {
            ItemStack containerStack = new ItemStack(remainder.itemStack().getItem(), remainder.count());
            if (!player.getInventory().add(containerStack)) {
                player.drop(containerStack, false);
            }
        }
        if (!level.isClientSide && user instanceof Player player) {
            long expireTick = level.getGameTime() + 20L * 60 * 5;
            if(player instanceof MutableDataComponentHolder holder){
                holder.set(ModComponents.FROG_CREATE_COMPONENT.get(), new FrogCreateComponent(expireTick));
            }
        }
        return result;
    }
}
