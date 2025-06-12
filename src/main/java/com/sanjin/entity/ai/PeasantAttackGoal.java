package com.sanjin.entity.ai;

import com.sanjin.entity.mobentity.MalePeasantEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class PeasantAttackGoal extends Goal {

    private final MalePeasantEntity peasant;
    private final double speedModifier;
    private Path path;
    private int attackTime;
    private int seeTime;
    private long lastCanUseCheck;
    private static final int ATTACK_COOLDOWN = 20;

    public PeasantAttackGoal(MalePeasantEntity peasant, double speedModifier) {
        this.peasant = peasant;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {

        if (this.peasant.shouldFlee()) {
            return false;
        }

        long currentTime = this.peasant.level().getGameTime();
        if (currentTime - this.lastCanUseCheck < 20L) {
            return false;
        }

        this.lastCanUseCheck = currentTime;
        LivingEntity target = this.peasant.getTarget();

        if (target == null || !target.isAlive()) {
            return false;
        }

        if (target instanceof Player player) {
            int favorability = peasant.getRelationshipComponent().getFavorability(player.getUUID());
            return favorability < 0;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {

        if (this.peasant.shouldFlee()) {
            return false;
        }

        LivingEntity target = this.peasant.getTarget();
        if (target == null || !target.isAlive()) {
            return false;
        }

        if (target instanceof Player player) {
            int favorability = peasant.getRelationshipComponent().getFavorability(player.getUUID());
            return favorability < 0;
        }

        return true;
    }

    @Override
    public void start() {
        PathNavigation navigation = this.peasant.getNavigation();
        LivingEntity target = this.peasant.getTarget();

        if (target != null) {
            this.path = navigation.createPath(target, 0);
            navigation.moveTo(this.path, this.speedModifier);
            this.attackTime = 0;
            this.seeTime = 0;
        }
    }

    @Override
    public void stop() {
        this.peasant.getNavigation().stop();
        this.path = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.peasant.getTarget();
        if (target == null) {
            return;
        }

        this.peasant.getLookControl().setLookAt(target, 30.0F, 30.0F);

        double distanceSqr = this.peasant.distanceToSqr(target.getX(), target.getY(), target.getZ());
        boolean canSee = this.peasant.getSensing().hasLineOfSight(target);

        if (canSee) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (distanceSqr > 4.0D) {
            PathNavigation navigation = this.peasant.getNavigation();
            if (this.path == null || !navigation.isInProgress()) {
                this.path = navigation.createPath(target, 0);
                navigation.moveTo(this.path, this.speedModifier);
            }
        } else {
            this.peasant.getNavigation().stop();
        }

        this.attackTime = Math.max(this.attackTime - 1, 0);

        if (distanceSqr <= 4.0D && canSee && this.attackTime == 0) {
            if (this.peasant.level() instanceof ServerLevel serverLevel) {
                boolean attackSuccess = this.peasant.doHurtTarget(serverLevel, target);

                if (attackSuccess) {
                    this.attackTime = ATTACK_COOLDOWN;
                    this.peasant.swing(InteractionHand.MAIN_HAND);
                }
            }
        }
    }

}
