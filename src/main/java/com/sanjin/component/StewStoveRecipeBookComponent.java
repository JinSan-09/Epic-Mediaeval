package com.sanjin.component;

import com.sanjin.menu.StewStoveMenu;
import com.sanjin.recipe.recipedisplay.StewStoveRecipeDisplay;
import com.sanjin.register.ModItems;
import com.sanjin.register.ModRecipeBookCategories;
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

import javax.annotation.Nonnull;

import static com.sanjin.EpicMediaeval.LOGGER;

public class StewStoveRecipeBookComponent extends RecipeBookComponent<StewStoveMenu> {

    private static final List<RecipeBookComponent.TabInfo> TAB_INFOS = List.of(
            new RecipeBookComponent.TabInfo(ModItems.BARLEY_BEEF_STEW.get(), ModRecipeBookCategories.STEW_STOVE_STEWS.get()),
            new RecipeBookComponent.TabInfo(ModItems.LEEK_SOUP.get(), ModRecipeBookCategories.STEW_STOVE_SOUP.get()),
            new RecipeBookComponent.TabInfo(ModItems.WOODEN_BOWL.get(), ModRecipeBookCategories.STEW_STOVE_MISC.get())
    );

    private static final WidgetSprites FILTER_BUTTON_SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_enabled_highlighted"),
            ResourceLocation.withDefaultNamespace("recipe_book/furnace_filter_disabled_highlighted")
    );

    public StewStoveRecipeBookComponent(StewStoveMenu menu) {
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
    protected boolean isCraftingSlot(@Nonnull Slot slot) {
        int idx = this.menu.slots.indexOf(slot);
        return idx >= 0 && idx < 4;
    }

    @Override
    protected void selectMatchingRecipes(@Nonnull RecipeCollection collection, @Nonnull StackedItemContents sic) {
        LOGGER.debug("Recipe collection size before filter: {}", collection.getRecipes().size());
        collection.selectRecipes(sic, this::canDisplay);
        LOGGER.debug("Recipe collection size after filter: {}", collection.getRecipes().size());
        collection.selectRecipes(sic,this::canDisplay);
    }

    @Override
    protected @NotNull Component getRecipeFilterName() {
        return Component.translatable("recipe.filter.stew_stove");
    }

    @Override
    protected void fillGhostRecipe(@Nonnull GhostSlots ghostSlots, @Nonnull RecipeDisplay recipeDisplay, @Nonnull ContextMap context) {
        StewStoveMenu menu = this.menu;
        // Fill result
        ghostSlots.setResult(menu.getResultSlot(), context, recipeDisplay.result());

        if (recipeDisplay instanceof StewStoveRecipeDisplay stewStoveRecipeDisplay) {
            List<SlotDisplay> inputs = stewStoveRecipeDisplay.getInputsDisplay();

            // Count
            int processedInputs = 0;
            LOGGER.info("Setting ghost recipe with {} inputs", inputs.size());
            // Fill inputs
            for (int i = 0; i < inputs.size() && i < 4; i++) {
                Slot targetSlot = menu.slots.get(i);
                SlotDisplay inputDisplay = inputs.get(i);
                LOGGER.info("Setting input slot {}: {}", i, inputDisplay);
                ghostSlots.setInput(targetSlot, context, inputDisplay);
                processedInputs++;
            }
            // Fill container
            SlotDisplay containerDisplay = stewStoveRecipeDisplay.container();
            LOGGER.info("Setting container slot: {}", containerDisplay);
            ghostSlots.setInput(menu.slots.get(6), context, containerDisplay);
            LOGGER.info("Ghost recipe set with {} inputs and container", processedInputs);
        }else {
            LOGGER.warn("RecipeDisplay is not a StewStoveRecipeDisplay: {}", recipeDisplay.getClass().getName());
        }
    }
}
