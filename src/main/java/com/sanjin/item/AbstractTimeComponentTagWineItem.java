package com.sanjin.item;

import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import com.sanjin.item.fooditem.WineItem;
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

public abstract class AbstractTimeComponentTagWineItem extends WineItem {

    private final UseRemainderComponent remainder;
    private final EffectComponent effects;
    private final Random rand = new Random();

    public AbstractTimeComponentTagWineItem(Properties properties, boolean hasEnchantmentEffect, List<Component> tooltipLines, EffectComponent effects, UseRemainderComponent remainder) {
        super(properties, hasEnchantmentEffect, tooltipLines, effects, remainder);
        this.remainder = remainder;
        this.effects = effects;
    }

    protected abstract String getComponentTagName();
    // Optional add new function
    protected void onEffectApplied(Player player, Level level, long expireTick) {
    }
    protected Long getEffectTime(){
        return 20L*60*5;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user){
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
        ItemStack result = super.finishUsingItem(stack, level, user);
        if (user instanceof Player player) {
            ItemStack containerStack = new ItemStack(remainder.itemStack().getItem(), remainder.count());
            if (!player.getInventory().add(containerStack)) {
                player.drop(containerStack, false);
            }
        }

        if (!level.isClientSide && user instanceof Player player) {
            long currentTime = level.getGameTime();
            long expireTick = currentTime + getEffectTime();

            onEffectApplied(player, level, expireTick);
            String tagName = getComponentTagName();

            CompoundTag persistentData = player.getPersistentData();
            persistentData.putLong(tagName, expireTick);
        }
        return result;
    }

    // ========= Tool methods =========
    public boolean hasActiveEffect(Player player, Level level) {
        CompoundTag persistentData = player.getPersistentData();
        String tagName = getComponentTagName();

        if (!persistentData.contains(tagName)) {
            return false;
        }

        long expireTick = persistentData.getLong(tagName);
        long currentTime = level.getGameTime();

        return expireTick > currentTime;
    }

    public int getRemainingTimeInSeconds(Player player, Level level) {
        if (!hasActiveEffect(player, level)) {
            return 0;
        }

        CompoundTag persistentData = player.getPersistentData();
        long expireTick = persistentData.getLong(getComponentTagName());
        long currentTime = level.getGameTime();
        long remainingTicks = expireTick - currentTime;

        return (int) (remainingTicks / 20);
    }
}
