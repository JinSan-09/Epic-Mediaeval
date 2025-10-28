package com.sanjin.datagen;

import java.util.Set;

import com.sanjin.block.BarleyCropBlock;
import com.sanjin.block.ChickpeaCropBlock;
import com.sanjin.block.GreenPepperCropBlock;
import com.sanjin.block.HorseradishCropBlock;
import com.sanjin.block.LeekCropBlock;
import com.sanjin.block.NettleCropBlock;
import com.sanjin.block.OatsCropBlock;
import com.sanjin.block.OnionCropBlock;
import com.sanjin.block.WhiteBeansCropBlock;
import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
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
        LootItemCondition.Builder barleyCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BARLEY_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BarleyCropBlock.AGE, 3));
        this.add(ModBlocks.BARLEY_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.BARLEY_CROP_BLOCK.get(), ModItems.BARLEY.get(), ModItems.SEEDS_BARLEY.get(), barleyCondition));

        LootItemCondition.Builder greenPepperCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GREEN_PEPPER_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(GreenPepperCropBlock.AGE, 3));
        this.add(ModBlocks.GREEN_PEPPER_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.GREEN_PEPPER_CROP_BLOCK.get(), ModItems.GREEN_PEPPER.get(), ModItems.SEEDS_GREEN_PEPPER.get(), greenPepperCondition));

        LootItemCondition.Builder nettleCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.NETTLE_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(NettleCropBlock.AGE, 3));
        this.add(ModBlocks.NETTLE_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.NETTLE_CROP_BLOCK.get(), ModItems.NETTLE_LEAVES.get(), ModItems.SEEDS_NETTLE.get(), nettleCondition));

        LootItemCondition.Builder oatsCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.OATS_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OatsCropBlock.AGE, 3));
        this.add(ModBlocks.OATS_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.OATS_CROP_BLOCK.get(), ModItems.OATS.get(), ModItems.SEEDS_OATS.get(), oatsCondition));

        LootItemCondition.Builder onionCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.ONION_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OnionCropBlock.AGE, 3));
        this.add(ModBlocks.ONION_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.ONION_CROP_BLOCK.get(), ModItems.ONION.get(), ModItems.ONION_TUBER.get(), barleyCondition));

        LootItemCondition.Builder whiteBeansCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WHITE_BEANS_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WhiteBeansCropBlock.AGE, 3));
        this.add(ModBlocks.WHITE_BEANS_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.WHITE_BEANS_CROP_BLOCK.get(), ModItems.WHITE_BEANS.get(), ModItems.WHITE_BEANS.get(), whiteBeansCondition));

        LootItemCondition.Builder horseradishCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.HORSERADISH_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(HorseradishCropBlock.AGE, 3));
        this.add(ModBlocks.HORSERADISH_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.HORSERADISH_CROP_BLOCK.get(), ModItems.HORSERADISH.get(), ModItems.HORSERADISH.get(), horseradishCondition));

        LootItemCondition.Builder leekCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.LEEK_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LeekCropBlock.AGE, 3));
        this.add(ModBlocks.LEEK_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.LEEK_CROP_BLOCK.get(), ModItems.LEEK_LEAVES.get(), ModItems.LEEK_LEAVES.get(), leekCondition));

        LootItemCondition.Builder chickpeaCondition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CHICKPEA_CROP_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ChickpeaCropBlock.AGE, 3));
        this.add(ModBlocks.CHICKPEA_CROP_BLOCK.get(), this.createCropDrops(
            ModBlocks.CHICKPEA_CROP_BLOCK.get(), ModItems.CHICKPEA.get(), ModItems.CHICKPEA.get(), chickpeaCondition));

        
    }


    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value).toList();
    }

}
