package com.sanjin.component;

import com.sanjin.menu.FermentationBarrelMenu;
import com.sanjin.recipe.recipedisplay.FermentationBarrelRecipeDisplay;
import com.sanjin.register.ModItems;
import com.sanjin.register.ModRecipeBookCategories;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.GhostSlots;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.sanjin.EpicMediaeval.LOGGER;

public class FermentationBarrelRecipeBookComponent extends RecipeBookComponent<FermentationBarrelMenu> {

    private static final List<RecipeBookComponent.TabInfo> TAB_INFOS = List.of(
            new RecipeBookComponent.TabInfo(ModItems.SWEET_PLUM_WINE.get(), ModRecipeBookCategories.FERMENTATION_BARREL_WINS.get()),
            new RecipeBookComponent.TabInfo(ModItems.CHEESE.get(), ModRecipeBookCategories.FERMENTATION_BARREL_PICKLES.get()),
            new RecipeBookComponent.TabInfo(ModItems.WINE_BOTTLE.get(), ModRecipeBookCategories.FERMENTATION_BARREL_MISC.get())
    );

    private static final WidgetSprites FILTER_BUTTON_SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted")
    );

    public FermentationBarrelRecipeBookComponent(FermentationBarrelMenu menu) {
        super(menu, TAB_INFOS);
    }

    private boolean canDisplay(RecipeDisplay display){
        return true;
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_BUTTON_SPRITES);
    }

    @Override
    protected boolean isCraftingSlot(@NotNull Slot slot) {
        int idx = this.menu.slots.indexOf(slot);
        return idx >= 0 && idx < 4;
    }

    @Override
    protected void selectMatchingRecipes(@NotNull RecipeCollection collection, @NotNull StackedItemContents sic) {
        LOGGER.debug("Recipe collection size before filter: {}", collection.getRecipes().size());
        collection.selectRecipes(sic, this::canDisplay);
        LOGGER.debug("Recipe collection size after filter: {}", collection.getRecipes().size());
        collection.selectRecipes(sic,this::canDisplay);

    }

    @Override
    protected @NotNull Component getRecipeFilterName() {
        return Component.translatable("recipe.filter.fermentation_barrel");
    }

    @Override
    protected void fillGhostRecipe(@NotNull GhostSlots ghostSlots, @NotNull RecipeDisplay recipeDisplay, @NotNull ContextMap context) {
        FermentationBarrelMenu menu = this.menu;
        if (recipeDisplay instanceof FermentationBarrelRecipeDisplay fermentationBarrelRecipeDisplay) {
            List<SlotDisplay> inputs = fermentationBarrelRecipeDisplay.getInputsDisplay();
            for (int i = 0; i < inputs.size() && i < 4; i++) {
                ghostSlots.setInput(menu.slots.get(i), context, inputs.get(i));
            }
        }
    }
}
