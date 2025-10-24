package com.sanjin.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

public class ThrowableItem extends Item {

    private final EntityType<? extends ThrowableItemProjectile> projectileType;

    public ThrowableItem(Properties properties, EntityType<? extends ThrowableItemProjectile> projectileType) {
        super(properties);
        this.projectileType = projectileType;
    }

    @Override
    public @NotNull InteractionResult use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // Play sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        // Add cooldown
        player.getCooldowns().addCooldown(itemstack,0);

        ThrowableItemProjectile itemProjectile = projectileType.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        if (itemProjectile != null) {
            if (!level.isClientSide) {
                double x = player.getX();
                double y = player.getEyeY();
                double z = player.getZ();
                itemProjectile.setPos(x, y, z);

                // Shoot from the player's front
                itemProjectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.2F, 1.0F);
                level.addFreshEntity(itemProjectile);
            }
        }
        // Count
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResult.SUCCESS;
    }

}
