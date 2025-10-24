package com.sanjin.register;


import com.sanjin.block.BarleyCropBlock;
import com.sanjin.block.FermentationBarrelBlock;
import com.sanjin.block.StewStoveBlock;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import com.sanjin.EpicMediaeval;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EpicMediaeval.MODID);

    // Workstations blocks
    public static final DeferredBlock<Block> STEW_STOVE_BLOCK = BLOCKS.registerBlock("stew_stove", StewStoveBlock::new,BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2.0f,3.0f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> FERMENTATION_BARREL_BLOCK = BLOCKS.registerBlock("fermentation_barrel", FermentationBarrelBlock::new,BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0f,3.0f));
    
    // Crops blocks
    public static final DeferredHolder<Block, Block> BARLEY_CROP_BLOCK = BLOCKS.registerBlock("barley_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> CHICKPEA_CROP_BLOCK = BLOCKS.registerBlock("chickpea_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> GREEN_PEPPER_CROP_BLOCK = BLOCKS.registerBlock("green_pepper_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> HORSERADISH_CROP_BLOCK = BLOCKS.registerBlock("horseradish_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> LEEK_CROP_BLOCK = BLOCKS.registerBlock("leek_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> NETTLE_CROP_BLOCK = BLOCKS.registerBlock("nettle_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> OATS_CROP_BLOCK = BLOCKS.registerBlock("oats_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> ONION_CROP_BLOCK = BLOCKS.registerBlock("onion_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());
    public static final DeferredHolder<Block, Block> WHITE_BEANS_CROP_BLOCK = BLOCKS.registerBlock("white_beans_crop", BarleyCropBlock::new, BlockBehaviour.Properties.of().noOcclusion().noCollission().pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.CROP).randomTicks());

    //BlockItems
    public static void registerBlockItems() {
        ModItems.ITEMS.registerSimpleBlockItem("stew_stove", STEW_STOVE_BLOCK, new Item.Properties());
        ModItems.ITEMS.registerSimpleBlockItem("fermentation_barrel", FERMENTATION_BARREL_BLOCK, new Item.Properties());
    }

    // Register function
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        registerBlockItems();
    }
}