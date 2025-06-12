package com.sanjin.entity.ai;

import com.sanjin.entity.AbstractPeasantEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PeasantFleeWhenLowHealthGoal extends Goal {

    private final AbstractPeasantEntity peasant;
    private final double speedModifier;
    private Vec3 fleePos;
    private int fleeTime;

    public PeasantFleeWhenLowHealthGoal(AbstractPeasantEntity peasant, double speedModifier) {
        this.peasant = peasant;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!peasant.shouldFlee()) {
            return false;
        }

        this.fleePos = DefaultRandomPos.getPos(peasant, 16, 7);
        return this.fleePos != null;
    }

    @Override
    public boolean canContinueToUse() {
        return peasant.shouldFlee() && !peasant.getNavigation().isDone() && fleeTime > 0;
    }

    @Override
    public void start() {
        peasant.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, speedModifier);
        fleeTime = 100;
    }

    @Override
    public void tick() {
        fleeTime--;
    }

    @Override
    public void stop() {
        fleePos = null;
    }
}
