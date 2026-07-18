package com.sanjin.recipe.recipeinput;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class ProcessingRecipeInput implements RecipeInput {

    public static final int INGREDIENT_SLOT_COUNT = 4;

    private final NonNullList<ItemStack> ingredients;
    private final ItemStack container;
    private final int nonEmptyIngredientCount;

    public ProcessingRecipeInput(NonNullList<ItemStack> ingredients, ItemStack container) {
        this.ingredients = ingredients;
        this.container = container;
        this.nonEmptyIngredientCount = (int) ingredients.stream().filter(stack -> !stack.isEmpty()).count();
    }

    public static ProcessingRecipeInput of(List<ItemStack> ingredients) {
        return of(ingredients, ItemStack.EMPTY);
    }

    public static ProcessingRecipeInput of(List<ItemStack> ingredients, ItemStack container) {
        NonNullList<ItemStack> slots = NonNullList.withSize(INGREDIENT_SLOT_COUNT, ItemStack.EMPTY);
        for (int i = 0; i < Math.min(INGREDIENT_SLOT_COUNT, ingredients.size()); i++) {
            slots.set(i, ingredients.get(i));
        }
        return new ProcessingRecipeInput(slots, container);
    }

    public List<ItemStack> getIngredients() {
        return ingredients;
    }

    public ItemStack getContainer() {
        return container;
    }

    public int getNonEmptyIngredientCount() {
        return nonEmptyIngredientCount;
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        if (index < ingredients.size()) {
            return ingredients.get(index);
        }
        return index == ingredients.size() ? container : ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return ingredients.size() + 1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ProcessingRecipeInput that)) return false;
        return ingredients.equals(that.ingredients) && container.equals(that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients, container);
    }
}
