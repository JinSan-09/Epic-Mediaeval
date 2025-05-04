package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.StewStoveRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, EpicMediaeval.MODID);

    public static final Supplier<RecipeType<StewStoveRecipe>> STEW_STOVE_RECIPE_TYPE = RECIPE_TYPES.register("stew_stove",
            () -> new RecipeType<>() {
                        @Override public String toString() {
                            return EpicMediaeval.MODID + ":stew_stove";
                        }
                    });


    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
    }
}
