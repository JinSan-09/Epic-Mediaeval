package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.FermentationBarrelRecipe;
import com.sanjin.recipe.StewStoveRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, EpicMediaeval.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<StewStoveRecipe>> STEW_STOVE_RECIPE_TYPE = RECIPE_TYPES.register("stew_stove",
        () -> new RecipeType<StewStoveRecipe>() {@Override public String toString() {return EpicMediaeval.MODID + ":stew_stove";}});

        public static final DeferredHolder<RecipeType<?>, RecipeType<FermentationBarrelRecipe>> FERMENTATION_BARREL_RECIPE = RECIPE_TYPES.register("fermentation_barrel",
        () -> new RecipeType<FermentationBarrelRecipe>() {@Override public String toString(){return EpicMediaeval.MODID + ":fermentation_barrel";}});

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}