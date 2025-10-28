package com.sanjin.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.recipeBuilder.FermentationBarrelRecipeBuilder;
import com.sanjin.recipe.recipeBuilder.StewStoveRecipeBuilder;
import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModItems;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder{

    private final HolderLookup.Provider provider;

    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
        this.provider = registries;
    }

    @Override
    protected void buildRecipes() {

        HolderGetter<Item> items = provider.lookupOrThrow(Registries.ITEM);

        // Shaped recipes
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.WINE_BOTTLE.get(), 8)
                .pattern("X X")
                .pattern("X X")
                .pattern("XXX")
                .define('X', Items.GLASS)
                .unlockedBy("has_glass", has(Items.GLASS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.BREAD)
                .pattern("XXX")
                .define('X', ModItems.OATS.get())
                .unlockedBy("has_oats", has(ModItems.OATS.get()))
                .save(this.output, "epicmediaeval:barley_bread");

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.BREAD)
                .pattern("XXX")
                .define('X', ModItems.BARLEY.get())
                .unlockedBy("has_barley", has(ModItems.BARLEY.get()))
                .save(this.output, "epicmediaeval:oats_bread");

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.WHITE_PORCELAIN_DISH.get(), 4)
                .pattern("X X")
                .pattern("XXX")
                .define('X', Items.QUARTZ)
                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.WOODEN_BOWL.get())
                .pattern("XBX")
                .pattern(" X ")
                .define('X', ItemTags.PLANKS)
                .define('B', Items.BOWL)
                .unlockedBy("has_bowl", has(Items.BOWL))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.LARGE_WOODEN_BOWL.get())
                .pattern("XBX")
                .pattern(" X ")
                .define('X', ItemTags.PLANKS)
                .define('B', ModItems.WOODEN_BOWL.get())
                .unlockedBy("has_wooden_bowl", has(ModItems.WOODEN_BOWL.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModBlocks.STEW_STOVE_BLOCK.asItem())
                .pattern("III")
                .pattern("B B")
                .pattern("BBB")
                .define('I', Items.IRON_INGOT)
                .define('B', Items.BRICK_SLAB)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.BROWN_OAT_CAKE.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.OATS.get())
                .define('B', Items.EGG)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_egg", has(Items.EGG))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.SWEET_COOKIE.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', Items.SUGAR)
                .define('B', ModItems.DOUGH.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_dough", has(ModItems.DOUGH.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.BEEF_PIE.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.DOUGH.get())
                .define('B', Items.BEEF)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_dough", has(ModItems.DOUGH.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.LEMON_CAKE.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', Items.SUGAR)
                .define('B', ModItems.DOUGH.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_dough", has(ModItems.DOUGH.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.COD_CAKE.get())
                .pattern("DDD")
                .pattern("CCC")
                .pattern(" W ")
                .define('D', ModItems.DOUGH.get())
                .define('C', Items.COD)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_dough", has(ModItems.DOUGH.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModBlocks.FERMENTATION_BARREL_BLOCK.asItem())
                .pattern("PIP")
                .pattern("P P")
                .pattern("BBB")
                .define('P', ItemTags.WOODEN_SLABS)
                .define('I', Items.IRON_INGOT)
                .define('B', ItemTags.PLANKS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.GOLDEN_HONEY_CAKE.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.DOUGH.get())
                .define('B', Items.HONEY_BOTTLE)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_dough", has(ModItems.DOUGH.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.VENISON_PIE.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.DOUGH.get())
                .define('B', ModItems.COOKED_VENISON.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_dough", has(ModItems.DOUGH.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.HONEY_CHICKEN.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', Items.HONEY_BOTTLE)
                .define('B', Items.COOKED_CHICKEN)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_cooked_chicken", has(Items.COOKED_CHICKEN))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.DORNISH_MEAT_RICE.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.BARLEY.get())
                .define('B', Items.COOKED_PORKCHOP)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_porkchop", has(Items.COOKED_PORKCHOP))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.CHEESE_GREEN_PEPPER.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.GREEN_PEPPER.get())
                .define('B', ModItems.CHEESE.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_cheese", has(ModItems.CHEESE.get()))
                .save(this.output);
        
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.ELK_MEATBALL.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.NETTLE_LEAVES.get())
                .define('B', ModItems.COOKED_ELK_MEAT.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_cooked_elk_meat", has(ModItems.COOKED_ELK_MEAT.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.RABBIT_MEAT_SALAD.get())
                .pattern("ABA")
                .pattern(" W ")
                .define('A', ModItems.HORSERADISH.get())
                .define('B', Items.COOKED_RABBIT)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_cooked_rabbit", has(Items.COOKED_RABBIT))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.CLAY_BAKED_TROUT.get())
                .pattern("CCC")
                .pattern("CTC")
                .pattern(" W ")
                .define('C', Items.SUGAR)
                .define('T', ModItems.RAW_TROUT.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_raw_trout", has(ModItems.RAW_TROUT.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.ROAST_SUCKLING_PIG.get())
                .pattern("PPP")
                .pattern("CPC")
                .pattern(" W ")
                .define('P', Items.COOKED_PORKCHOP)
                .define('C', Items.CARROT)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.PITA_BREAD_WITH_HUMMUS.get())
                .pattern("CCC")
                .pattern("DCD")
                .pattern(" W ")
                .define('C', ModItems.CHICKPEA.get())
                .define('D', ModItems.DOUGH.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_chickpea", has(ModItems.CHICKPEA.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.VEGETABLE_SALAD.get())
                .pattern("OHP")
                .pattern(" W ")
                .define('O', Items.POTATO)
                .define('H', ModItems.ONION.get())
                .define('P', ModItems.HORSERADISH.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_onion", has(ModItems.ONION.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.HORSERADISH_BEEF_STEW.get())
                .pattern("HBC")
                .pattern(" W ")
                .define('H', ModItems.HORSERADISH.get())
                .define('B', Items.COOKED_BEEF)
                .define('C', Items.CARROT)
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_horseradish", has(ModItems.HORSERADISH.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.STEAK_AND_KIDNEY_PIE.get())
                .pattern("ABC")
                .pattern(" W ")
                .define('A', Items.COOKED_BEEF)
                .define('B', ModItems.BEEF_KIDNEY.get())
                .define('C', ModItems.DOUGH.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_beef_kindy", has(ModItems.BEEF_KIDNEY.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.LORD_MANDLEY_PIE.get())
                .pattern("MVM")
                .pattern("DDD")
                .pattern(" W ")
                .define('M', Items.MILK_BUCKET)
                .define('V', ModItems.COOKED_VENISON.get())
                .define('D', ModItems.DOUGH.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_cooked_vension", has(ModItems.COOKED_VENISON.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.DORNISH_CAKE.get())
                .pattern("ABC")
                .pattern("DDD")
                .pattern(" W ")
                .define('A', Items.SWEET_BERRIES)
                .define('B', Items.GLOW_BERRIES)
                .define('C', ModItems.PLUM.get())
                .define('D', ModItems.DOUGH.get())
                .define('W', ModItems.WHITE_PORCELAIN_DISH.get())
                .unlockedBy("has_dough", has(ModItems.DOUGH.get()))
                .save(this.output);

        // Shapless recipes
        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.ONION_TUBER.get(), 4)
                .requires(ModItems.ONION.get())
                .unlockedBy("has_onion", has(ModItems.ONION.get()))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.FLOWER_SALAD.get())
                .requires(ItemTags.FLOWERS)
                .requires(ItemTags.FLOWERS)
                .requires(ItemTags.FLOWERS)
                .requires(ModItems.WOODEN_BOWL.get())
                .unlockedBy("has_flower", has(ItemTags.FLOWERS))
                .save(this.output);

        // Simple cooking recipes
        SimpleCookingRecipeBuilder.smelting(
                    Ingredient.of(ModItems.RAW_TROUT.get()),
                    RecipeCategory.FOOD,
                    ModItems.COOKED_TROUT.get(),
                    0.35f,   
                    200     
            ).unlockedBy("has_raw_trout", has(ModItems.RAW_TROUT.get()))
             .save(this.output);

        SimpleCookingRecipeBuilder.smelting(
                    Ingredient.of(ModItems.FROG_LEG.get()),
                    RecipeCategory.FOOD,
                    ModItems.COOKED_FROG_LEG.get(),
                    0.35f,   
                    200     
            ).unlockedBy("has_frog_leg", has(ModItems.FROG_LEG.get()))
             .save(this.output);

        SimpleCookingRecipeBuilder.smelting(
                    Ingredient.of(ModItems.RAW_VENISON.get()),
                    RecipeCategory.FOOD,
                    ModItems.COOKED_VENISON.get(),
                    0.35f,   
                    200     
            ).unlockedBy("has_raw_vension", has(ModItems.RAW_VENISON.get()))
             .save(this.output);

        SimpleCookingRecipeBuilder.smelting(
                    Ingredient.of(ModItems.RAW_ELK_MEAT.get()),
                    RecipeCategory.FOOD,
                    ModItems.COOKED_ELK_MEAT.get(),
                    0.35f,   
                    200     
            ).unlockedBy("has_elk_meat", has(ModItems.RAW_ELK_MEAT.get()))
             .save(this.output);

        // Stew Stove Recipes
        new  StewStoveRecipeBuilder()
                    .group("stew")
                    .addIngredient(ModItems.RAW_VENISON.get())
                    .addIngredient(Items.CARROT)
                    .addIngredient(Items.CARROT)
                    .result(ModItems.STEWED_VENISON.get(), 1)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "stewed_venison");

        new  StewStoveRecipeBuilder()
                    .group("soup")
                    .addIngredient(Items.KELP)
                    .addIngredient(Items.COD)
                    .addIngredient(Items.SALMON)
                    .addIngredient(Items.CARROT)
                    .result(ModItems.SEAFOOD_STEW.get(), 1)
                    .container(ModItems.LARGE_WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "seafood_stew");

        new  StewStoveRecipeBuilder()
                    .group("stew")
                    .addIngredient(Items.BEEF)
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.BARLEY_BEEF_STEW.get(), 1)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "barley_beef_stew");

        new  StewStoveRecipeBuilder()
                    .group("soup")
                    .addIngredient(ModItems.OXTAIL.get())
                    .addIngredient(Items.RED_MUSHROOM)
                    .addIngredient(Items.BROWN_MUSHROOM)
                    .result(ModItems.OXTAIL_SOUP.get(), 1)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "oxtail_soup");

        new  StewStoveRecipeBuilder()
                    .group("stew")
                    .addIngredient(ModItems.FROG_LEG.get())
                    .addIngredient(ModItems.FROG_LEG.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(Items.BROWN_MUSHROOM)
                    .result(ModItems.FROG_STEW.get(), 1)
                    .container(ModItems.LARGE_WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "forg_stew");

        new  StewStoveRecipeBuilder()
                    .group("soup")
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .result(ModItems.LEEK_SOUP.get(), 1)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "leek_soup");

        new  StewStoveRecipeBuilder()
                    .group("soup")
                    .addIngredient(ModItems.WHITE_BEANS.get())
                    .addIngredient(ModItems.WHITE_BEANS.get())
                    .addIngredient(Items.PORKCHOP)
                    .addIngredient(Items.PORKCHOP)
                    .result(ModItems.BACON_WHITE_BEANS.get(), 1)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "bacon_white_beans");

        new  StewStoveRecipeBuilder()
                    .group("soup")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .result(ModItems.NETTLE_TEA.get(), 1)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .cookingTime(200)
                    .save(this.output, "nettle_tea");

        // Fermentation Barrel Recipes
        new FermentationBarrelRecipeBuilder()
                    .group("pcikles")
                    .addIngredient(Items.MILK_BUCKET)
                    .result(ModItems.CHEESE.get(), 8)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .fermentationTime(500)
                    .save(this.output, "cheese");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.ONION.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.OATS_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.PLUM.get())
                    .addIngredient(ModItems.PLUM.get())
                    .addIngredient(ModItems.PLUM.get())
                    .addIngredient(ModItems.PLUM.get())
                    .result(ModItems.SWEET_PLUM_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.55f)
                    .fermentationTime(800)
                    .save(this.output, "sweet_plum_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("pickles")
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(Items.WATER_BUCKET)
                    .result(ModItems.DOUGH.get(), 8)
                    .container(ModItems.WOODEN_BOWL.get())
                    .experience(0.35f)
                    .fermentationTime(500)
                    .save(this.output, "dough");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.BARLEY_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.LEMON.get())
                    .addIngredient(ModItems.LEMON.get())
                    .addIngredient(ModItems.LEMON.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.LEMON_JUICE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "lemon_juice_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.PLUM.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.BARLEY_OATS_PLUM_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_oats_plum_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.ONION.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.BARLEY_OATS_ONION_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_oats_onion_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.LEMON.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.LEMON.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.BARLEY_OATS_LEMON_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_oats_lemon_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.ONION.get())
                    .addIngredient(ModItems.ONION.get())
                    .addIngredient(ModItems.PLUM.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.BARLEY_ONION_PLUM_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_onion_plum_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(ModItems.PLUM.get())
                    .result(ModItems.OATS_LEEK_PLUM_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_leek_plum_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.PLUM.get())
                    .addIngredient(ModItems.PLUM.get())
                    .result(ModItems.OATS_CHICKPEA_PLUM_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_chickpea_plum_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(ModItems.ONION.get())
                    .result(ModItems.OATS_LEEK_ONION_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_leek_onion_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.ONION.get())
                    .result(ModItems.CHICKPEA_OATS_ONION_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "chickpea_oats_onion_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.LEMON.get())
                    .result(ModItems.OATS_CHICKPEA_LEMON_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_chickpea_lemon_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.FROG_LEG.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(Items.GOLDEN_CARROT)
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.FROG_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(1.0f)
                    .fermentationTime(800)
                    .save(this.output, "frog_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.OATS.get())
                    .result(ModItems.OATS_CHICKPEA_NETTLE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_chickpea_nettle_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .result(ModItems.CHICKPEA_OATS_LEEK_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "chickpea_oats_leek_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.OATS.get())
                    .result(ModItems.BARLEY_CHICKPEA_OATS_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_chickpea_oats_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .result(ModItems.BARLEY_OATS_PEPPER_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_oats_pepper_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.LEMON.get())
                    .result(ModItems.CHICKPEA_LEMON_NETTLE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "chickpea_lemon_nettle_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.ONION.get())
                    .result(ModItems.OATS_HORSERADISH_ONION_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_horseradish_onion_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.ONION.get())
                    .addIngredient(ModItems.HORSERADISH.get())
                    .result(ModItems.OATS_ONION_HORSERADISH_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_onion_horseradish_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.HORSERADISH.get())
                    .result(ModItems.BARLEY_OATS_HORSERADISH_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_oats_horseradish_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .addIngredient(ModItems.ONION.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .result(ModItems.BARLEY_PEPPER_ONION_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_pepper_onion_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .result(ModItems.OATS_CHICKPEA_PEPPER_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_chickpea_pepper_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.ONION.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .result(ModItems.CHICKPEA_ONION_LEEK_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "chickpea_onion_leek_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .result(ModItems.OATS_PEPPER_NETTLE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_pepper_nettle_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.OATS.get())
                    .addIngredient(ModItems.ONION.get())
                    .result(ModItems.OATS_NETTLE_ONION_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "oats_nettle_onion_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.LEMON.get())
                    .addIngredient(ModItems.ONION.get())
                    .result(ModItems.ONION_LEMON_NETTLE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "onion_lemon_nettle_wine");
        
        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.ONION.get())
                    .result(ModItems.PEPPER_NETTLE_ONION_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "pepper_nettle_onion_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .addIngredient(ModItems.LEMON.get())
                    .result(ModItems.PEPPER_LEMON_LEEK_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "pepper_lemon_leek_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.LEMON.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .result(ModItems.CHICKPEA_LEMON_HORSERADISH_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "chickpea_lemon_horseradish_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .result(ModItems.BARLEY_HORSERADISH_LEEK_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_horseradish_leek_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .result(ModItems.BARLEY_NETTLE_PEPPER_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_nettle_pepper_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.BARLEY.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .result(ModItems.BARLEY_PEPPER_HORSERADISH_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "barley_pepper_horseradish_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.HORSERADISH.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .result(ModItems.CHICKPEA_HORSERADISH_NETTLE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "chickpea_horseradish_nettle_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .addIngredient(ModItems.LEEK_LEAVES.get())
                    .result(ModItems.PEPPER_LEEK_NETTLE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "pepper_leek_nettle_wine");

        new FermentationBarrelRecipeBuilder()
                    .group("wine")
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.NETTLE_LEAVES.get())
                    .addIngredient(ModItems.CHICKPEA.get())
                    .addIngredient(ModItems.GREEN_PEPPER.get())
                    .result(ModItems.CHICKPEA_PEPPER_NETTLE_WINE.get(), 1)
                    .container(ModItems.WINE_BOTTLE.get())
                    .experience(0.45f)
                    .fermentationTime(600)
                    .save(this.output, "chickpea_pepper_nettle_wine");
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(@Nonnull HolderLookup.Provider provider, @Nonnull RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return EpicMediaeval.MODID + " Recipes";
        }
    }
}
