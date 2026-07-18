package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.FermentationBarrelRecipe;
import com.sanjin.recipe.StewStoveRecipe;
import com.sanjin.serializer.ProcessingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, EpicMediaeval.MODID);

    public static final Supplier<RecipeSerializer<StewStoveRecipe>> STEW_STOVE_RECIPE_SERIALIZERS = RECIPE_SERIALIZERS.register("stew_stove",
            () -> new ProcessingRecipeSerializer<>(StewStoveRecipe::new));
    public static final Supplier<RecipeSerializer<FermentationBarrelRecipe>> FERMENTATION_BARREL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("fermentation_barrel",
            () -> new ProcessingRecipeSerializer<>(FermentationBarrelRecipe::new));

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
