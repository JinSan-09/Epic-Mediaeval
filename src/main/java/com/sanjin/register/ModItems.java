package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EpicMediaeval.MODID);

    public static final DeferredItem<Item> LARGE_WOODEN_BOWL = ITEMS.registerSimpleItem("large_wooden_bowl",
            new Item.Properties());
    public static final DeferredItem<Item> WOODEN_BOWL = ITEMS.registerSimpleItem("wooden_bowl",
            new Item.Properties());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
