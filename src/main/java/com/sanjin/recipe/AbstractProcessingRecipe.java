package com.sanjin.recipe;

import com.sanjin.recipe.recipeinput.ProcessingRecipeInput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class AbstractProcessingRecipe implements Recipe<ProcessingRecipeInput> {

    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ItemStack container;
    private final String group;
    private final float experience;
    private final int processingTime;
    private final boolean containerRequiredForMatching;

    protected AbstractProcessingRecipe(NonNullList<Ingredient> inputs, ItemStack output, ItemStack container,
                                       String group, float experience, int processingTime,
                                       boolean containerRequiredForMatching) {
        this.inputs = inputs;
        this.output = output;
        this.container = container;
        this.group = group;
        this.experience = experience;
        this.processingTime = processingTime;
        this.containerRequiredForMatching = containerRequiredForMatching;
    }

    public NonNullList<Ingredient> getInputs() {
        return inputs;
    }

    public ItemStack getResult() {
        return output;
    }

    public ItemStack getContainer() {
        return container;
    }

    public String getGroup() {
        return group;
    }

    public float getExperience() {
        return experience;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public boolean matches(ProcessingRecipeInput input, Level level) {
        if (level.isClientSide() || input.getNonEmptyIngredientCount() != inputs.size()) {
            return false;
        }
        if (containerRequiredForMatching && !ItemStack.isSameItem(input.getContainer(), container)) {
            return false;
        }

        List<ItemStack> remaining = new ArrayList<>();
        input.getIngredients().stream().filter(stack -> !stack.isEmpty()).forEach(remaining::add);
        for (Ingredient ingredient : inputs) {
            boolean matched = false;
            Iterator<ItemStack> iterator = remaining.iterator();
            while (iterator.hasNext()) {
                if (ingredient.test(iterator.next())) {
                    iterator.remove();
                    matched = true;
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
    public @NotNull ItemStack assemble(ProcessingRecipeInput input, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractProcessingRecipe that = (AbstractProcessingRecipe) object;
        return Float.compare(experience, that.experience) == 0
                && processingTime == that.processingTime
                && inputs.equals(that.inputs)
                && output.equals(that.output)
                && container.equals(that.container)
                && group.equals(that.group);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(inputs, output, container, group, experience, processingTime);
    }
}
