package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.ExtendedRecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORY = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, EpicMediaeval.MODID);

    public static final Supplier<RecipeBookCategory> STEW_STOVE_CATEGORY = RECIPE_BOOK_CATEGORY.register("stew_stove_category", RecipeBookCategory::new);
    public static final ExtendedRecipeBookCategory STEW_STOVE_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};

    public static void register(IEventBus eventBus) {
        RECIPE_BOOK_CATEGORY.register(eventBus);
    }
}
