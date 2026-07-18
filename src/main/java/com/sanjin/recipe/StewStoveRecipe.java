package com.sanjin.recipe;

import com.sanjin.recipe.recipedisplay.StewStoveRecipeDisplay;
import com.sanjin.recipe.recipeinput.ProcessingRecipeInput;
import com.sanjin.register.ModRecipeBookCategories;
import com.sanjin.register.ModRecipeSerializers;
import com.sanjin.register.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StewStoveRecipe extends AbstractProcessingRecipe {

    public StewStoveRecipe(NonNullList<Ingredient> inputs, ItemStack output, ItemStack container,
                           String group, float experience, int cookingTime) {
        super(inputs, output, container, group, experience, cookingTime, true);
    }

    public int getCookingTime() {
        return getProcessingTime();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<ProcessingRecipeInput>> getSerializer() {
        return ModRecipeSerializers.STEW_STOVE_RECIPE_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<ProcessingRecipeInput>> getType() {
        return ModRecipes.STEW_STOVE_RECIPE_TYPE.get();
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return switch (getGroup()) {
            case "stew" -> ModRecipeBookCategories.STEW_STOVE_STEWS.get();
            case "soup" -> ModRecipeBookCategories.STEW_STOVE_SOUP.get();
            default -> ModRecipeBookCategories.STEW_STOVE_MISC.get();
        };
    }

    @Override
    public @NotNull List<RecipeDisplay> display() {
        return List.of(new StewStoveRecipeDisplay(
                getInputs().stream().map(Ingredient::display).toList(),
                new SlotDisplay.ItemStackSlotDisplay(getContainer()),
                new SlotDisplay.ItemStackSlotDisplay(getResult())
        ));
    }
}
