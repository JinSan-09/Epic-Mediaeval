package com.sanjin.entity.ai;

import com.sanjin.entity.mobentity.MalePeasantEntity;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class ConditionalPanicGoal extends PanicGoal {

    private final MalePeasantEntity malePeasantEntity;

    public ConditionalPanicGoal(MalePeasantEntity entity, double speedModifier) {
        super(entity, speedModifier);
        this.malePeasantEntity = entity;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && malePeasantEntity.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && malePeasantEntity.getTarget() == null;
    }

}
