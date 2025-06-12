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
        // 只有在没有攻击目标时才恐慌
        return super.canUse() && malePeasantEntity.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        // 如果有攻击目标，停止恐慌
        return super.canContinueToUse() && malePeasantEntity.getTarget() == null;
    }

}
