package com.sanjin.entity.ai;

import com.sanjin.entity.mobentity.MalePeasantEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.pathfinder.Path;

public class ProtectFemalesGoal extends TargetGoal {

    private final MalePeasantEntity malePeasant;
    private final double speedModifier;
    private static final int ATTACK_COOLDOWN = 20;
    private LivingEntity targetMob;
    private int startTickCount;
    private Path path;
    private int attackTime;
    private int seeTime;

    public ProtectFemalesGoal(MalePeasantEntity malePeasant, double speedModifier) {
        super(malePeasant, false);
        this.malePeasant = malePeasant;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        if (malePeasant.shouldFlee()) {
            return false;
        }

        LivingEntity attacker = malePeasant.findAttackerOfNearbyFemale();
        if (attacker == null) {
            return false;
        }

        if (this.canAttack(attacker, TargetingConditions.DEFAULT)) {
            this.targetMob = attacker;
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        malePeasant.setTarget(this.targetMob);
        this.startTickCount = this.malePeasant.tickCount;
        super.start();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity currentTarget = malePeasant.getTarget();
        if (currentTarget == null || !currentTarget.isAlive()) {
            return false;
        }

        int elapsedTicks = malePeasant.tickCount - this.startTickCount;
        if (malePeasant.distanceToSqr(currentTarget) > 225.0D ||
                elapsedTicks > 600) {
            return false;
        }

        return !malePeasant.shouldFlee() && super.canContinueToUse();
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity target = this.malePeasant.getTarget();
        if (target == null) {
            return;
        }

        this.malePeasant.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double distanceSqr = this.malePeasant.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean canSee = this.malePeasant.getSensing().hasLineOfSight(target);

        if (canSee) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (distanceSqr > 4.0D) {
            PathNavigation navigation = this.malePeasant.getNavigation();
            if (this.path == null || !navigation.isInProgress()) {
                this.path = navigation.createPath(target, 0);
                navigation.moveTo(this.path, this.speedModifier);
            }
        } else {
            this.malePeasant.getNavigation().stop();
        }

        this.attackTime = Math.max(this.attackTime - 1, 0);

        if (distanceSqr <= 4.0D && canSee && this.attackTime == 0) {
            if (this.malePeasant.level() instanceof ServerLevel serverLevel) {
                boolean attackSuccess = this.malePeasant.doHurtTarget(serverLevel, target);

                if (attackSuccess) {
                    this.attackTime = ATTACK_COOLDOWN;
                    this.malePeasant.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }
}
