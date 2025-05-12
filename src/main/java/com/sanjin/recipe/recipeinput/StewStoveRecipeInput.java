package com.sanjin.recipe.recipeinput;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class StewStoveRecipeInput implements RecipeInput {

    public static final StewStoveRecipeInput EMPTY = new StewStoveRecipeInput(NonNullList.withSize(4, ItemStack.EMPTY), ItemStack.EMPTY);
    private final NonNullList<ItemStack> ingredients;
    private final ItemStack container;
    private final StackedItemContents stackedItemContents = new StackedItemContents();
    private final int nonEmptyIngredientCount;

    public StewStoveRecipeInput(NonNullList<ItemStack> ingredients, ItemStack container){
        int temp;
        this.container = container;
        this.ingredients = ingredients;

        int count = 0;
        // count ingredients
        for (ItemStack stack : ingredients) {
            if (!stack.isEmpty()) {
                count++;
                this.stackedItemContents.accountStack(stack, 1);
            }
        }

        // count container
        if (!container.isEmpty()) {
            this.stackedItemContents.accountStack(container, 1);
        }

        temp = count;
        this.nonEmptyIngredientCount = temp;
    }

    public static StewStoveRecipeInput of(List<ItemStack> ingredients, ItemStack container){
        NonNullList<ItemStack> list = NonNullList.withSize(4, ItemStack.EMPTY);
        for (int i = 0; i < Math.min(4, ingredients.size()); i++) {
            list.set(i, ingredients.get(i));
        }
        return new StewStoveRecipeInput(list, container);
    }
    public ItemStack getContainer() {
        return this.container;
    }
    public int getNonEmptyIngredientCount() {
        return this.nonEmptyIngredientCount;
    }
    public StackedItemContents getStackedItemContents(){
        return this.stackedItemContents;
    }
    public List<ItemStack> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StewStoveRecipeInput that)) return false;
        return nonEmptyIngredientCount == that.nonEmptyIngredientCount
                && Objects.equals(ingredients, that.ingredients)
                && Objects.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients, container, nonEmptyIngredientCount);
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        if (index < ingredients.size()) {
            return ingredients.get(index);
        } else if (index == ingredients.size()) {
            return container;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return this.ingredients.size() + 1;
    }
}
