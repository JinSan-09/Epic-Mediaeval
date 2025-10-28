package com.sanjin.recipe.recipeBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.FermentationBarrelRecipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class FermentationBarrelRecipeBuilder {

    private final List<Ingredient> ingredients = new ArrayList<>();
    private ItemStack result = ItemStack.EMPTY;
    private ItemStack container = ItemStack.EMPTY;
    private String group = "";
    private float experience = 0f;
    private int fermentationTime = 200;

    public FermentationBarrelRecipeBuilder group(@Nullable String group){
        this.group = group;
        return this;
    }

    public FermentationBarrelRecipeBuilder addIngredient(Item item) {
        if(item != Items.AIR){
            this.ingredients.add(Ingredient.of(item));
        }
        return this;
    }
    
    public FermentationBarrelRecipeBuilder result(ItemStack result){
        this.result = result;
        return this;
    }

    public FermentationBarrelRecipeBuilder result(Item item, int count) {
        this.result = new ItemStack(item, count);
        return this;
    }

    public FermentationBarrelRecipeBuilder container(ItemStack container) {
        this.container = container.copy();
        return this;
    }

    public FermentationBarrelRecipeBuilder container(Item item) {
        this.container = new ItemStack(item);
        return this;
    }

    public FermentationBarrelRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    public FermentationBarrelRecipeBuilder fermentationTime(int ticks) {
        this.fermentationTime = ticks;
        return this;
    }

    public void save(@Nonnull RecipeOutput output, @Nonnull String name) {

        NonNullList<Ingredient> ingList = NonNullList.create();
        
        ingList.addAll(ingredients);

        ResourceLocation id = ResourceLocation.parse(EpicMediaeval.MODID + ":" + name);

        FermentationBarrelRecipe recipe = new FermentationBarrelRecipe(ingList, result, container, group, experience, fermentationTime);

        ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, id);

        output.accept(key, recipe, null);

    }
}
