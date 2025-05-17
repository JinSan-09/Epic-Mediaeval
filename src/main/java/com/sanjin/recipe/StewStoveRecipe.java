package com.sanjin.recipe;

import com.sanjin.recipe.recipedisplay.StewStoveRecipeDisplay;
import com.sanjin.recipe.recipeinput.StewStoveRecipeInput;
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


public class StewStoveRecipe implements Recipe<StewStoveRecipeInput> {

    private final NonNullList<Ingredient> inputs ;
    private final ItemStack output;
    private final ItemStack container;
    private final String group;
    private final float experience;
    private final int cookingTime;


    public StewStoveRecipe(NonNullList<Ingredient> inputs, ItemStack output, @NotNull ItemStack container, String group, float experience, int cookingTime){
        this.inputs = inputs;
        this.output = output;
        this.container = container;
        this.group = group;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    // Create get-methods
    public NonNullList<Ingredient> getInputs(){
        return this.inputs;
    }
    public ItemStack getResult(){
        return this.output;
    }
    public ItemStack getContainer(){
        return this.container;
    }
    public String getGroup(){
        return this.group;
    }
    public float getExperience(){
        return this.experience;
    }
    public int getCookingTime(){
        return this.cookingTime;
    }

    @Override
    public boolean matches(@NotNull StewStoveRecipeInput recipeInput, @NotNull Level level) {
        if (level.isClientSide()) return false;
        if (recipeInput.getNonEmptyIngredientCount() != this.inputs.size()) return false;
        if (!ItemStack.isSameItem(recipeInput.getContainer(), this.container)) return false;

        // Check every ingredient in the list upon if it can match one of the ingredient in one of the recipe read from JSON file
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

        // successfully match
        return true;
     }

    @Override
    public @NotNull ItemStack assemble(@NotNull StewStoveRecipeInput recipeInput, HolderLookup.@NotNull Provider provider) {
        return this.output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<StewStoveRecipeInput>> getSerializer() {
        return ModRecipeSerializers.STEW_STOVE_RECIPE_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<StewStoveRecipeInput>> getType() {
        return ModRecipes.STEW_STOVE_RECIPE_TYPE.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        if (this.group.equals("stews"))return ModRecipeBookCategories.STEW_STOVE_STEWS.get();
        if (this.group.equals("soup"))return ModRecipeBookCategories.STEW_STOVE_SOUP.get();
        else return new RecipeBookCategory();
    }

    @Override
    public @NotNull List<RecipeDisplay> display(){
        List<SlotDisplay> displays = new ArrayList<>(inputs.size());
        for (Ingredient ingredient : inputs) {
            displays.add(ingredient.display());
        }
        return List.of(
                new StewStoveRecipeDisplay(
                        new SlotDisplay.Composite(displays).contents(),
                        new SlotDisplay.ItemStackSlotDisplay(this.container),
                        new SlotDisplay.ItemStackSlotDisplay(this.output)
                )
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StewStoveRecipe that = (StewStoveRecipe) o;

        if (Float.compare(that.getExperience(), getExperience()) != 0) return false;
        if (getCookingTime() != that.getCookingTime()) return false;
        if (!inputs.equals(that.inputs)) return false;
        if (!output.equals(that.output)) return false;
        return container.equals(that.container);
    }

    @Override
    public int hashCode() {
        int result = getInputs().hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + container.hashCode();
        result = 31 * result + (getExperience() != 0.0f ? Float.floatToIntBits(getExperience()) : 0);
        result = 31 * result + getCookingTime();
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
