package com.sanjin.entity.ai;

import com.sanjin.entity.mobentity.MalePeasantEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class ProtectFemalesGoal extends TargetGoal {

    private final MalePeasantEntity malePeasant;
    private LivingEntity targetMob;
    private int startTickCount;

    public ProtectFemalesGoal(MalePeasantEntity malePeasant) {
        super(malePeasant, false);
        this.malePeasant = malePeasant;
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
}
