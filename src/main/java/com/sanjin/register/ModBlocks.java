package com.sanjin.register;


import com.sanjin.block.StewStoveBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import com.sanjin.EpicMediaeval;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EpicMediaeval.MODID);

    //Workstations
    public static final DeferredBlock<Block> STEW_STOVE_BLOCK = BLOCKS.registerBlock("stew_stove",
            StewStoveBlock::new,BlockBehaviour.Properties.of());


    //BlockItems
    public static void registerBlockItems(IEventBus bus) {
        ModItems.ITEMS.registerSimpleBlockItem("stew_stove", STEW_STOVE_BLOCK, new Item.Properties());
        ModItems.ITEMS.register(bus);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        registerBlockItems(eventBus);
    }
}