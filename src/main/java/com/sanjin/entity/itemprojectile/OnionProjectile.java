package com.sanjin.entity.itemprojectile;

import com.sanjin.register.ModItemEntities;
import com.sanjin.register.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.NotNull;

public class OnionProjectile extends ThrowableItemProjectile {

    public OnionProjectile(EntityType<OnionProjectile> entityEntityType, Level level) {
        super(entityEntityType, level);
    }

    public OnionProjectile(Level level, LivingEntity shooter) {
        super(ModItemEntities.ONION_ENTITY.get(),level);
    }

    private void makeOnionParticles() {
        ItemStack itemStack = this.getItemRaw();
        if (!itemStack.isEmpty()) {
            // 创建4个物品粒子效果，模拟洋葱碎片
            for(int i = 0; i < 4; ++i) {
                this.level().addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, itemStack),
                        this.getX(), this.getY(), this.getZ(),
                        // 随机方向散射
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

        // 检查被击中的是否是生物实体
        if (target instanceof LivingEntity livingTarget) {
            // 不造成伤害，但添加药水效果
            livingTarget.addEffect(new MobEffectInstance(
                    MobEffects.CONFUSION, // 眩晕效果，可替换为自定义效果
                    10,                  // 持续0.5秒
                    6,                    // 效果等级
                    false,                // 是否环境效果
                    true,                 // 是否显示粒子
                    true                  // 是否显示图标
            ));
        }
        makeOnionParticles();
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);

        if (!this.level().isClientSide) {
            // 服务器端处理
            // 创建粒子效果
            if (this.level() instanceof ServerLevel serverLevel) {
                ItemStack itemStack = this.getItemRaw();
                if (!itemStack.isEmpty()) {
                    serverLevel.sendParticles(
                            new ItemParticleOption(ParticleTypes.ITEM, itemStack),
                            this.getX(), this.getY(), this.getZ(),
                            8, // 粒子数量
                            0.0D, 0.0D, 0.0D, // 定向速度
                            0.15D // 随机速度
                    );
                }
            }

            // 播放撞击音效
            this.level().playSound(
                    null, this.getX(), this.getY(), this.getZ(),
                    net.minecraft.sounds.SoundEvents.ITEM_PICKUP, // 可替换为更合适的音效
                    net.minecraft.sounds.SoundSource.PLAYERS,
                    0.5F,
                    0.8F + this.random.nextFloat() * 0.4F // 稍微随机化音调
            );

            // 如果你想添加某些特殊逻辑，可以在这里添加

            // 删除实体
            this.discard();
        }
    }

    @Override
    @NotNull
    public Item getDefaultItem() {
        return ModItems.ONION.get();
    }
}
