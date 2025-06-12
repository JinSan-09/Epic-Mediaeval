package com.sanjin.entity.ai;

import com.sanjin.entity.AbstractPeasantEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;

public class PeasantAttackUndeadGoal extends NearestAttackableTargetGoal<Monster> {

    private final AbstractPeasantEntity peasant;

    public PeasantAttackUndeadGoal(AbstractPeasantEntity peasant) {
        super(peasant, Monster.class, 10, true, false,
                (target, serverLevel) -> target != null && target.isInvertedHealAndHarm()
        );
        this.peasant = peasant;
    }

    @Override
    public boolean canUse() {
        return peasant.shouldBeHostileToUndead() && !peasant.shouldFlee() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return peasant.shouldBeHostileToUndead() && !peasant.shouldFlee() && super.canContinueToUse();
    }
}
