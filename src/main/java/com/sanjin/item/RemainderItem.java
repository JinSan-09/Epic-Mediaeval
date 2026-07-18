package com.sanjin.item;

import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class RemainderItem extends Item {

    private final UseRemainderComponent remainder;
    private final EffectComponent effects;

    public RemainderItem(Properties properties, UseRemainderComponent remainder, EffectComponent effects) {
        super(properties);
        this.remainder = remainder;
        this.effects = effects;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity user) {
        if (!level.isClientSide) {
            for (var mobEffectInstance : effects.effects()) {
                user.addEffect(new MobEffectInstance(
                        mobEffectInstance.getEffect(),
                        mobEffectInstance.getDuration(),
                        mobEffectInstance.getAmplifier(),
                        mobEffectInstance.isVisible(),
                        mobEffectInstance.showIcon()
                ));
            }
        }
        ItemStack result = super.finishUsingItem(stack, level, user);
        if (!level.isClientSide && user instanceof Player player && !player.getAbilities().instabuild) {
            ItemStack containerStack = remainder.itemStack().copy();
            containerStack.setCount(remainder.count());
            if (!player.getInventory().add(containerStack)) {
                player.drop(containerStack, false);
            }
        }
        return result;
    }
}
