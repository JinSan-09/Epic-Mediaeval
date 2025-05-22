package com.sanjin.entity.itemprojectile;

import com.sanjin.register.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class OnionProjectile extends ThrowableItemProjectile {

    public OnionProjectile(EntityType<OnionProjectile> entityType, Level level) {
        super(entityType, level);
    }

    private void makeOnionParticles() {
        ItemStack itemStack = this.getItemRaw();
        if (!itemStack.isEmpty()) {
            for(int i = 0; i < 4; ++i) {
                this.level().addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, itemStack),
                        this.getX(), this.getY(), this.getZ(),
                        (this.random.nextFloat() - 0.5) * 0.3,
                        (this.random.nextFloat() - 0.5) * 0.3 + 0.1,
                        (this.random.nextFloat() - 0.5) * 0.3
                );
            }
        }
    }

    private ItemStack getItemRaw() {
        return ModItems.ONION.toStack();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);

        Entity target = hitResult.getEntity();

        if (target instanceof LivingEntity livingTarget) {

            if (!livingTarget.level().isClientSide()) {
                Vec3 knockbackDir = livingTarget.position().subtract(this.position()).normalize();
                double knockbackStrength = 0.8;
                livingTarget.push(
                        knockbackDir.x * knockbackStrength,
                        0.2,
                        knockbackDir.z * knockbackStrength
                );
                livingTarget.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20, 6, false, true, true));
                livingTarget.playSound(SoundEvents.PLAYER_HURT, 0.5F, 1.2F);
                livingTarget.invulnerableTime = 10;
            }
        }
        makeOnionParticles();
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide) {
            if (this.level() instanceof ServerLevel serverLevel) {
                ItemStack itemStack = this.getItemRaw();
                if (!itemStack.isEmpty()) {
                    serverLevel.sendParticles(
                            new ItemParticleOption(ParticleTypes.ITEM, itemStack),
                            this.getX(), this.getY(), this.getZ(),
                            8, 0.0D, 0.0D, 0.0D, 0.15D
                    );
                }
            }

            this.level().playSound(
                    null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.SLIME_SQUISH_SMALL,
                    SoundSource.PLAYERS,
                    0.5F, 0.8F + this.random.nextFloat() * 0.4F
            );
            this.discard();
        }
    }

    @Override
    @NotNull
    public Item getDefaultItem() {
        return ModItems.ONION.get();
    }
}
