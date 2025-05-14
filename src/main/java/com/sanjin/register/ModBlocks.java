package com.sanjin.register;


import com.sanjin.block.FermentationBarrelBlock;
import com.sanjin.block.StewStoveBlock;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import com.sanjin.EpicMediaeval;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EpicMediaeval.MODID);

    // Workstations
    public static final DeferredBlock<Block> STEW_STOVE_BLOCK = BLOCKS.registerBlock("stew_stove",
            StewStoveBlock::new,BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2.0f,3.0f).requiresCorrectToolForDrops());
    public static final DeferredBlock<Block> FERMENTATION_BARREL_BLOCK = BLOCKS.registerBlock("fermentation_barrel",
            FermentationBarrelBlock::new,BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0f,3.0f));

    //BlockItems
    public static void registerBlockItems() {
        ModItems.ITEMS.registerSimpleBlockItem("stew_stove", STEW_STOVE_BLOCK, new Item.Properties());
        ModItems.ITEMS.registerSimpleBlockItem("fermentation_barrel", FERMENTATION_BARREL_BLOCK, new Item.Properties());
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        registerBlockItems();
    }
}