package com.sanjin.recipe.recipeinput;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FermentationBarrelRecipeInput implements RecipeInput {

    public static final FermentationBarrelRecipeInput EMPTY = new FermentationBarrelRecipeInput(NonNullList.withSize(4, ItemStack.EMPTY));
    private final NonNullList<ItemStack> ingredients;
    private final ItemStack container;
    private final int nonEmptyIngredientCount;

    public FermentationBarrelRecipeInput(NonNullList<ItemStack> ingredients) {
        int temp;
        int count = 0;
        this.ingredients = ingredients;
        this.container = ItemStack.EMPTY;
        for (ItemStack stack : ingredients) {
            if (!stack.isEmpty()) {
                count++;
            }
        }
        temp = count;
        this.nonEmptyIngredientCount = temp;
    }

    public static FermentationBarrelRecipeInput of(List<ItemStack> ingredients) {
        NonNullList<ItemStack> list = NonNullList.withSize(4, ItemStack.EMPTY);
        for (int i = 0; i < Math.min(4, ingredients.size()); i++) {
            list.set(i, ingredients.get(i));
        }
        return new FermentationBarrelRecipeInput(list);
    }
    public NonNullList<ItemStack> getIngredients(){
        return this.ingredients;
    }
    public ItemStack getContainer(){
        return this.container;
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
