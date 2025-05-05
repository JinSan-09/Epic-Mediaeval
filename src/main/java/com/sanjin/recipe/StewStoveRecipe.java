package com.sanjin.recipe;

import com.sanjin.register.ModRecipeSerializers;
import com.sanjin.register.ModRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StewStoveRecipe implements Recipe<RecipeWrapper> {

    public static final int INPUT_SLOT = 4;

    private final String group;
    private final NonNullList<Ingredient> inputs ;
    private final ItemStack output;
    private final ItemStack container;
    private final ItemStack containerOverride;
    private final float experience;
    private final int cookingTime;

    public StewStoveRecipe(String group, NonNullList<Ingredient> inputs, ItemStack output, @NotNull ItemStack container, float experience, int cookingTime){
        this.group = group;
        this.inputs = inputs;
        this.output = output;

        if (!container.isEmpty()) {
            this.container = container;
        } else {
            this.container = ItemStack.EMPTY;
        }
        this.containerOverride = container;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    public String getGroup(){return this.group;}
    public NonNullList<Ingredient> getInputs(){
        return this.inputs;
    }
    public ItemStack getOutput(){
        return this.output;
    }
    public ItemStack getContainer(){
        return this.container;
    }
    public ItemStack getContainerOverride(){
        return this.containerOverride;
    }
    public float getExperience(){
        return this.experience;
    }
    public int getCookingTime(){
        return this.cookingTime;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper recipeWrapper, @NotNull Level level) {
        for (int i = 0; i < inputs.size(); i++) {
            Ingredient ingredient = inputs.get(i);
            ItemStack inSlot = recipeWrapper.getItem(i);
            if (!ingredient.test(inSlot)) {
                return false;
            }
        }
        // 检查后续槽是否全是air（防止配方有3个材料你塞了第4个导致错误）
        for(int j = inputs.size(); j < StewStoveRecipe.INPUT_SLOT; j++) {
            if (!recipeWrapper.getItem(j).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper recipeWrapper, HolderLookup.@NotNull Provider provider) {
        return this.output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<RecipeWrapper>> getSerializer() {
        return ModRecipeSerializers.STEW_STOVE_RECIPE_SERIALIZERS.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<RecipeWrapper>> getType() {
        return ModRecipes.STEW_STOVE_RECIPE_TYPE.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public @NotNull RecipeBookCategory recipeBookCategory() {
        return null;
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
        int result = getGroup().hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + inputs.hashCode();
        result = 31 * result + container.hashCode();
        result = 31 * result + (getExperience() != 0.0f ? Float.floatToIntBits(getExperience()) : 0);
        result = 31 * result + getCookingTime();
        return result;
    }
}
