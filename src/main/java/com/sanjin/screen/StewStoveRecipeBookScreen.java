package com.sanjin.screen;

import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.StewStoveRecipe;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

public class StewStoveRecipeBookScreen extends Screen {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "textures/gui/recipebook/recipe_book.png");
    private static final int BOOK_WIDTH = 192;
    private static final int BOOK_HEIGHT = 192;

    private final StewStoveScreen parentScreen;
    private final Player player;
    private final List<RecipeHolder<StewStoveRecipe>> recipes;
    private int currentPage = 0;
    private int totalPages;
    private static final int RECIPES_PER_PAGE = 1; // 每页显示1个配方

    private Button nextPageButton;
    private Button prevPageButton;
    private Button closeButton;

    protected StewStoveRecipeBookScreen(StewStoveScreen parentScreen, Player player) {
        super(Component.translatable("recipe_book.stew_stove.tooltip"));
        this.parentScreen = parentScreen;
        this.player = player;
        this.recipes = new ArrayList<>();

    }

}
