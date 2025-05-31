package com.sanjin.item.fooditem;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import com.sanjin.item.AbstractTimeComponentTagWineItem;
import net.minecraft.network.chat.Component;

import java.util.List;

public class FrogWineItem extends AbstractTimeComponentTagWineItem {

    public FrogWineItem(Properties properties, boolean hasEnchantmentEffect, List<Component> tooltipLines, EffectComponent effects, UseRemainderComponent remainder) {
        super(properties, hasEnchantmentEffect, tooltipLines, effects, remainder);
    }

    @Override
    protected String getComponentTagName() {
        return EpicMediaeval.MODID + ":frog_create_effect_expire";
    }

}
