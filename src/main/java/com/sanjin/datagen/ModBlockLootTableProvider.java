package com.sanjin.datagen;

import java.util.Set;

import com.sanjin.block.ModCropBlock;
import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ModBlockLootTableProvider extends BlockLootSubProvider{

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // Drop Self
        dropSelf(ModBlocks.STEW_STOVE_BLOCK.get());
        dropSelf(ModBlocks.FERMENTATION_BARREL_BLOCK.get());

        // Crop Blcok 
        addCropDrops(ModBlocks.BARLEY_CROP_BLOCK.get(), ModItems.BARLEY.get(), ModItems.SEEDS_BARLEY.get());
        addCropDrops(ModBlocks.CHICKPEA_CROP_BLOCK.get(), ModItems.CHICKPEA.get(), ModItems.CHICKPEA.get());
        addCropDrops(ModBlocks.GREEN_PEPPER_CROP_BLOCK.get(), ModItems.GREEN_PEPPER.get(), ModItems.SEEDS_GREEN_PEPPER.get());
        addCropDrops(ModBlocks.HORSERADISH_CROP_BLOCK.get(), ModItems.HORSERADISH.get(), ModItems.HORSERADISH.get());
        addCropDrops(ModBlocks.LEEK_CROP_BLOCK.get(), ModItems.LEEK_LEAVES.get(), ModItems.LEEK_LEAVES.get());
        addCropDrops(ModBlocks.NETTLE_CROP_BLOCK.get(), ModItems.NETTLE_LEAVES.get(), ModItems.SEEDS_NETTLE.get());
        addCropDrops(ModBlocks.OATS_CROP_BLOCK.get(), ModItems.OATS.get(), ModItems.SEEDS_OATS.get());
        addCropDrops(ModBlocks.ONION_CROP_BLOCK.get(), ModItems.ONION.get(), ModItems.ONION_TUBER.get());
        addCropDrops(ModBlocks.WHITE_BEANS_CROP_BLOCK.get(), ModItems.WHITE_BEANS.get(), ModItems.WHITE_BEANS.get());

        
    }

    private void addCropDrops(Block crop, Item produce, Item seed) {
        LootItemCondition.Builder mature = LootItemBlockStatePropertyCondition.hasBlockStateProperties(crop)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModCropBlock.AGE, ModCropBlock.MAX_AGE));
        add(crop, createCropDrops(crop, produce, seed, mature));
    }


    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value).toList();
    }

}
