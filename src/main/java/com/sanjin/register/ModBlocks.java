package com.sanjin.register;


import com.sanjin.block.FermentationBarrelBlock;
import com.sanjin.block.ModCropBlock;
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
    public static final DeferredHolder<Block, Block> BARLEY_CROP_BLOCK = registerCrop("barley_crop", () -> ModItems.SEEDS_BARLEY.get());
    public static final DeferredHolder<Block, Block> CHICKPEA_CROP_BLOCK = registerCrop("chickpea_crop", () -> ModItems.CHICKPEA.get());
    public static final DeferredHolder<Block, Block> GREEN_PEPPER_CROP_BLOCK = registerCrop("green_pepper_crop", () -> ModItems.SEEDS_GREEN_PEPPER.get());
    public static final DeferredHolder<Block, Block> HORSERADISH_CROP_BLOCK = registerCrop("horseradish_crop", () -> ModItems.HORSERADISH.get());
    public static final DeferredHolder<Block, Block> LEEK_CROP_BLOCK = registerCrop("leek_crop", () -> ModItems.LEEK_LEAVES.get());
    public static final DeferredHolder<Block, Block> NETTLE_CROP_BLOCK = registerCrop("nettle_crop", () -> ModItems.SEEDS_NETTLE.get());
    public static final DeferredHolder<Block, Block> OATS_CROP_BLOCK = registerCrop("oats_crop", () -> ModItems.SEEDS_OATS.get());
    public static final DeferredHolder<Block, Block> ONION_CROP_BLOCK = registerCrop("onion_crop", () -> ModItems.ONION_TUBER.get());
    public static final DeferredHolder<Block, Block> WHITE_BEANS_CROP_BLOCK = registerCrop("white_beans_crop", () -> ModItems.WHITE_BEANS.get());

    private static DeferredHolder<Block, Block> registerCrop(String name, java.util.function.Supplier<? extends net.minecraft.world.level.ItemLike> seed) {
        return BLOCKS.registerBlock(name, properties -> new ModCropBlock(properties, seed), cropProperties());
    }

    private static BlockBehaviour.Properties cropProperties() {
        return BlockBehaviour.Properties.of()
                .noOcclusion()
                .noCollission()
                .pushReaction(PushReaction.DESTROY)
                .instabreak()
                .sound(SoundType.CROP)
                .randomTicks();
    }

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
