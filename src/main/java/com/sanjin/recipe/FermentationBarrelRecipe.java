package com.sanjin.recipe;

import com.sanjin.recipe.recipedisplay.FermentationBarrelRecipeDisplay;
import com.sanjin.recipe.recipeinput.FermentationBarrelRecipeInput;
import com.sanjin.register.ModRecipeBookCategories;
import com.sanjin.register.ModRecipeSerializers;
import com.sanjin.register.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

public class FermentationBarrelRecipe implements Recipe<FermentationBarrelRecipeInput> {

    public final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ItemStack container;
    private final String group;
    private final float experience;
    private final int fermentationTime;
    public FermentationBarrelRecipe(NonNullList<Ingredient> inputs, ItemStack output, ItemStack container,String group, float experience, int cookingTime) {
        this.inputs = inputs;
        this.output = output;
        this.container = container;
        this.group = group;
        this.experience = experience;
        this.fermentationTime = cookingTime;
    }

    public NonNullList<Ingredient> getInputs(){
        return this.inputs;
    }
    public ItemStack getResult(){
        return this.output;
    }
    public ItemStack getContainer(){
        return this.container;
    }
    public String getGroup(){return this.group;}
    public float getExperience(){
        return this.experience;
    }
    public int getFermentationTime(){
        return this.fermentationTime;
    }

    // ========= Check whether there is a matched recipe =========
    @Override
    public boolean matches(@Nonnull FermentationBarrelRecipeInput recipeInput, @Nonnull Level level) {
        if (level.isClientSide()) return false;
        if (recipeInput.getNonEmptyIngredientCount() != this.inputs.size()) return false;

        List<ItemStack> remainingIngredients = new ArrayList<>();
        for (ItemStack stack : recipeInput.getIngredients()) {
            if (!stack.isEmpty()) {
                remainingIngredients.add(stack);
            }
        }

        for (Ingredient ingredient : this.inputs) {
            boolean matched = false;
            Iterator<ItemStack> it = remainingIngredients.iterator();
            while (it.hasNext()) {
                ItemStack stack = it.next();
                if (ingredient.test(stack)) {
                    matched = true;
                    it.remove();
                    break;
                }
            }
            if (!matched) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@Nonnull FermentationBarrelRecipeInput fermentationBarrelRecipeInput, @Nonnull HolderLookup.Provider provider) {
        return this.output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<FermentationBarrelRecipeInput>> getSerializer() {
        return ModRecipeSerializers.FERMENTATION_BARREL_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<FermentationBarrelRecipeInput>> getType() {
        return ModRecipes.FERMENTATION_BARREL_RECIPE.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FermentationBarrelRecipe that = (FermentationBarrelRecipe) o;

        if (Float.compare(that.getExperience(), getExperience()) != 0) return false;
        if (getFermentationTime() != that.getFermentationTime()) return false;
        if (!inputs.equals(that.inputs)) return false;
        if (!output.equals(that.output)) return false;
        return container.equals(that.container);
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return switch (group){
            case "wine" -> ModRecipeBookCategories.FERMENTATION_BARREL_WINS.get();
            case "pickles" -> ModRecipeBookCategories.FERMENTATION_BARREL_PICKLES.get();
            default -> ModRecipeBookCategories.FERMENTATION_BARREL_MISC.get();
        };
    }

    @Override
    public @NotNull List<RecipeDisplay> display(){
        List<SlotDisplay> displays = new ArrayList<>(inputs.size());
        for (Ingredient ingredient : inputs) {
            displays.add(ingredient.display());
        }
        return List.of(
                new FermentationBarrelRecipeDisplay(
                        new SlotDisplay.Composite(displays).contents(),
                        new SlotDisplay.ItemStackSlotDisplay(this.output)
                )
        );
    }

    @Override
    public int hashCode() {
        int result = getInputs().hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + container.hashCode();
        result = 31 * result + (getExperience() != 0.0f ? Float.floatToIntBits(getExperience()) : 0);
        result = 31 * result + getFermentationTime();
        return result;
    }

    @Override
    public String toString() {
        return "StewStoveRecipe{" +
                "output=" + output +
                ", inputs=" + inputs +
                ", container=" + container +
                '}';
    }
}
