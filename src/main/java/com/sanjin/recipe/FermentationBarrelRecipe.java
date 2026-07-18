package com.sanjin.recipe;

import com.sanjin.recipe.recipedisplay.FermentationBarrelRecipeDisplay;
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

public class FermentationBarrelRecipe extends AbstractProcessingRecipe {

    public FermentationBarrelRecipe(NonNullList<Ingredient> inputs, ItemStack output, ItemStack container,
                                    String group, float experience, int fermentationTime) {
        super(inputs, output, container, group, experience, fermentationTime, false);
    }

    public int getFermentationTime() {
        return getProcessingTime();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<ProcessingRecipeInput>> getSerializer() {
        return ModRecipeSerializers.FERMENTATION_BARREL_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<ProcessingRecipeInput>> getType() {
        return ModRecipes.FERMENTATION_BARREL_RECIPE.get();
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return switch (getGroup()) {
            case "wine" -> ModRecipeBookCategories.FERMENTATION_BARREL_WINS.get();
            case "pickles" -> ModRecipeBookCategories.FERMENTATION_BARREL_PICKLES.get();
            default -> ModRecipeBookCategories.FERMENTATION_BARREL_MISC.get();
        };
    }

    @Override
    public @NotNull List<RecipeDisplay> display() {
        return List.of(new FermentationBarrelRecipeDisplay(
                getInputs().stream().map(Ingredient::display).toList(),
                new SlotDisplay.ItemStackSlotDisplay(getResult())
        ));
    }
}
