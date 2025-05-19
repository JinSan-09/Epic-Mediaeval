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

    public static final Supplier<RecipeBookCategory> STEW_STOVE_STEWS = RECIPE_BOOK_CATEGORY.register("stew_stove_stews_category", RecipeBookCategory::new);
    public static final Supplier<RecipeBookCategory> STEW_STOVE_SOUP = RECIPE_BOOK_CATEGORY.register("stew_stove_soup_category", RecipeBookCategory::new);
    public static final Supplier<RecipeBookCategory> STEW_STOVE_MISC = RECIPE_BOOK_CATEGORY.register("stew_stove_misc", RecipeBookCategory::new);
    public static final Supplier<RecipeBookCategory> FERMENTATION_BARREL_WINS = RECIPE_BOOK_CATEGORY.register("fermentation_barrel_wins_category", RecipeBookCategory::new);
    public static final Supplier<RecipeBookCategory> FERMENTATION_BARREL_PICKLES = RECIPE_BOOK_CATEGORY.register("fermentation_barrel_pickles_category", RecipeBookCategory::new);
    public static final Supplier<RecipeBookCategory> FERMENTATION_BARREL_MISC = RECIPE_BOOK_CATEGORY.register("fermentation_barrel_misc", RecipeBookCategory::new);

    public static final ExtendedRecipeBookCategory STEWS_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};
    public static final ExtendedRecipeBookCategory SOUP_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};
    public static final ExtendedRecipeBookCategory STEW_STOVE_MISC_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};
    public static final ExtendedRecipeBookCategory WINS_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};
    public static final ExtendedRecipeBookCategory PICKLES_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};
    public static final ExtendedRecipeBookCategory FERMENTATION_BARREL_MISC_SEARCH_CATEGORY = new ExtendedRecipeBookCategory() {};

    public static void register(IEventBus eventBus) {
        RECIPE_BOOK_CATEGORY.register(eventBus);
    }
}
