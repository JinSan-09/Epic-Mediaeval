package com.sanjin.datagen;

import java.util.concurrent.CompletableFuture;

import com.sanjin.EpicMediaeval;
import com.sanjin.loot.AddSingleItemModifier;
import com.sanjin.register.ModItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider{

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, EpicMediaeval.MODID);
        
    }

    @Override
    protected void start() {

        // Add seeds to grass
        this.add(
            "add_seeds_barley_to_short_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_BARLEY.get())
        );

        this.add(
            "add_seeds_barley_to_tall_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_BARLEY.get())
        );

        this.add(
            "add_seeds_green_pepper_to_short_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_GREEN_PEPPER.get())
        );

        this.add(
            "add_seeds_green_pepper_to_tall_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_GREEN_PEPPER.get())
        );

        this.add(
            "add_seeds_nettle_to_short_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_NETTLE.get())
        );

        this.add(
            "add_seeds_nettle_to_tall_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_NETTLE.get())
        );

        this.add(
            "add_seeds_oats_to_short_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SHORT_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_OATS.get())
        );

        this.add(
            "add_seeds_oats_to_tall_grass",
            new AddSingleItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.15f).build()}, ModItems.SEEDS_OATS.get())
        );

        // Add kindey and oxtail to cows and mooshrooms
        this.add(
            "add_oxtail_to_ox",
            new AddSingleItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/cow")).build(),
                LootItemRandomChanceCondition.randomChance(0.28f).build()}, ModItems.OXTAIL.get())
        );

        this.add(
            "add_kindey_to_ox",
            new AddSingleItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/cow")).build(),
                LootItemRandomChanceCondition.randomChance(0.28f).build()}, ModItems.BEEF_KIDNEY.get())
        );

        this.add(
            "add_oxtail_to_ox",
            new AddSingleItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/mooshroom")).build(),
                LootItemRandomChanceCondition.randomChance(0.28f).build()}, ModItems.OXTAIL.get())
        );

        this.add(
            "add_kindey_to_ox",
            new AddSingleItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/mooshroom")).build(),
                LootItemRandomChanceCondition.randomChance(0.28f).build()}, ModItems.BEEF_KIDNEY.get())
        );

        // Add forg leg to forgs
        this.add(
            "add_frog_leg_to_frog",
            new AddSingleItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/frog")).build(),
                LootItemRandomChanceCondition.randomChance(0.28f).build()}, ModItems.FROG_LEG.get())
        );
    }
}
