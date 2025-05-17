package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.recipedisplay.StewStoveRecipeDisplay;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeDisplays {
    public static final DeferredRegister<RecipeDisplay.Type<?>> RECIPE_DISPLAYS = DeferredRegister.create(Registries.RECIPE_DISPLAY, EpicMediaeval.MODID);

    public static final Supplier<RecipeDisplay.Type<StewStoveRecipeDisplay>> STEW_STOVE_RECIPE_DISPLAY = RECIPE_DISPLAYS.register("stew_stove_recipe_display", () ->
            new RecipeDisplay.Type<>(StewStoveRecipeDisplay.MAP_CODEC, StewStoveRecipeDisplay.STREAM_CODEC));

    public static void register(IEventBus bus) {
        RECIPE_DISPLAYS.register(bus);
    }
}
