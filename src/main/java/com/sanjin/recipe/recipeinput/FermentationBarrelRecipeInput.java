package com.sanjin.recipe.recipeinput;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

public class FermentationBarrelRecipeInput implements RecipeInput {

    public static final FermentationBarrelRecipeInput EMPTY = new FermentationBarrelRecipeInput(NonNullList.withSize(4, ItemStack.EMPTY), ItemStack.EMPTY);
    private final NonNullList<ItemStack> ingredients;
    private final ItemStack container;
    private final StackedItemContents stackedItemContents = new StackedItemContents();
    private final int nonEmptyIngredientCount;

    public FermentationBarrelRecipeInput(NonNullList<ItemStack> ingredients, ItemStack container) {
        this.ingredients = ingredients;
        this.container = container;
        this.nonEmptyIngredientCount = ingredients.size();
    }

    public NonNullList<ItemStack> getIngredients(){
        return this.ingredients;
    }
    public ItemStack getContainer(){
        return this.container;
    }
    public StackedItemContents getStackedItemContents(){
        return this.stackedItemContents;
    }
    public int getNonEmptyIngredientCount(){
        return this.nonEmptyIngredientCount;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        if (index < ingredients.size()) {
            return ingredients.get(index);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return this.ingredients.size() + 1;
    }
}
