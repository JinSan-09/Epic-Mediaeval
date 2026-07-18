package com.sanjin.datagen;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import com.sanjin.EpicMediaeval;
import com.sanjin.recipe.FermentationBarrelRecipe;
import com.sanjin.recipe.StewStoveRecipe;
import com.sanjin.register.ModBlocks;
import com.sanjin.register.ModItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

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
                .save(this.output, "epicmediaeval:oats_bread");
        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, Items.BREAD)
                .pattern("XXX")
                .define('X', ModItems.BARLEY.get())
                .unlockedBy("has_barley", has(ModItems.BARLEY.get()))
                .save(this.output, "epicmediaeval:barley_bread");
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
        stew("stewed_venison", "stew", ModItems.STEWED_VENISON.get(), 1, ModItems.WOODEN_BOWL.get(), 0.35f, 200,
                ModItems.RAW_VENISON.get(),
                Items.CARROT,
                Items.CARROT);
        stew("seafood_stew", "soup", ModItems.SEAFOOD_STEW.get(), 1, ModItems.LARGE_WOODEN_BOWL.get(), 0.35f, 200,
                Items.KELP,
                Items.COD,
                Items.SALMON,
                Items.CARROT);
        stew("barley_beef_stew", "stew", ModItems.BARLEY_BEEF_STEW.get(), 1, ModItems.WOODEN_BOWL.get(), 0.35f, 200,
                Items.BEEF,
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get());
        stew("oxtail_soup", "soup", ModItems.OXTAIL_SOUP.get(), 1, ModItems.WOODEN_BOWL.get(), 0.35f, 200,
                ModItems.OXTAIL.get(),
                Items.RED_MUSHROOM,
                Items.BROWN_MUSHROOM);
        stew("forg_stew", "stew", ModItems.FROG_STEW.get(), 1, ModItems.LARGE_WOODEN_BOWL.get(), 0.35f, 200,
                ModItems.FROG_LEG.get(),
                ModItems.FROG_LEG.get(),
                ModItems.LEEK_LEAVES.get(),
                Items.BROWN_MUSHROOM);
        stew("leek_soup", "soup", ModItems.LEEK_SOUP.get(), 1, ModItems.WOODEN_BOWL.get(), 0.35f, 200,
                ModItems.LEEK_LEAVES.get(),
                ModItems.LEEK_LEAVES.get(),
                ModItems.LEEK_LEAVES.get(),
                ModItems.LEEK_LEAVES.get());
        stew("bacon_white_beans", "soup", ModItems.BACON_WHITE_BEANS.get(), 1, ModItems.WOODEN_BOWL.get(), 0.35f, 200,
                ModItems.WHITE_BEANS.get(),
                ModItems.WHITE_BEANS.get(),
                Items.PORKCHOP,
                Items.PORKCHOP);
        stew("nettle_tea", "soup", ModItems.NETTLE_TEA.get(), 1, ModItems.WOODEN_BOWL.get(), 0.35f, 200,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get());
        // Fermentation Barrel Recipes
        fermentation("cheese", "pcikles", ModItems.CHEESE.get(), 8, ModItems.WOODEN_BOWL.get(), 0.35f, 500,
                Items.MILK_BUCKET);
        fermentation("oats_wine", "wine", ModItems.OATS_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.ONION.get(),
                ModItems.BARLEY.get());
        fermentation("sweet_plum_wine", "wine", ModItems.SWEET_PLUM_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.55f, 800,
                ModItems.PLUM.get(),
                ModItems.PLUM.get(),
                ModItems.PLUM.get(),
                ModItems.PLUM.get());
        fermentation("dough", "pickles", ModItems.DOUGH.get(), 8, ModItems.WOODEN_BOWL.get(), 0.35f, 500,
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get(),
                Items.WATER_BUCKET);
        fermentation("barley_wine", "wine", ModItems.BARLEY_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get());
        fermentation("lemon_juice_wine", "wine", ModItems.LEMON_JUICE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.LEMON.get(),
                ModItems.LEMON.get(),
                ModItems.LEMON.get(),
                ModItems.BARLEY.get());
        fermentation("barley_oats_plum_wine", "wine", ModItems.BARLEY_OATS_PLUM_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.PLUM.get(),
                ModItems.BARLEY.get());
        fermentation("barley_oats_onion_wine", "wine", ModItems.BARLEY_OATS_ONION_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.ONION.get(),
                ModItems.BARLEY.get());
        fermentation("barley_oats_lemon_wine", "wine", ModItems.BARLEY_OATS_LEMON_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.LEMON.get(),
                ModItems.OATS.get(),
                ModItems.LEMON.get(),
                ModItems.BARLEY.get());
        fermentation("barley_onion_plum_wine", "wine", ModItems.BARLEY_ONION_PLUM_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.ONION.get(),
                ModItems.ONION.get(),
                ModItems.PLUM.get(),
                ModItems.BARLEY.get());
        fermentation("oats_leek_plum_wine", "wine", ModItems.OATS_LEEK_PLUM_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.LEEK_LEAVES.get(),
                ModItems.PLUM.get());
        fermentation("oats_chickpea_plum_wine", "wine", ModItems.OATS_CHICKPEA_PLUM_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.CHICKPEA.get(),
                ModItems.PLUM.get(),
                ModItems.PLUM.get());
        fermentation("oats_leek_onion_wine", "wine", ModItems.OATS_LEEK_ONION_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.LEEK_LEAVES.get(),
                ModItems.ONION.get());
        fermentation("chickpea_oats_onion_wine", "wine", ModItems.CHICKPEA_OATS_ONION_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.CHICKPEA.get(),
                ModItems.ONION.get());
        fermentation("oats_chickpea_lemon_wine", "wine", ModItems.OATS_CHICKPEA_LEMON_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.CHICKPEA.get(),
                ModItems.LEMON.get());
        fermentation("frog_wine", "wine", ModItems.FROG_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 1.0f, 800,
                ModItems.FROG_LEG.get(),
                ModItems.NETTLE_LEAVES.get(),
                Items.GOLDEN_CARROT,
                ModItems.BARLEY.get());
        fermentation("oats_chickpea_nettle_wine", "wine", ModItems.OATS_CHICKPEA_NETTLE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.CHICKPEA.get(),
                ModItems.OATS.get());
        fermentation("chickpea_oats_leek_wine", "wine", ModItems.CHICKPEA_OATS_LEEK_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.CHICKPEA.get(),
                ModItems.LEEK_LEAVES.get());
        fermentation("barley_chickpea_oats_wine", "wine", ModItems.BARLEY_CHICKPEA_OATS_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.BARLEY.get(),
                ModItems.CHICKPEA.get(),
                ModItems.CHICKPEA.get(),
                ModItems.OATS.get());
        fermentation("barley_oats_pepper_wine", "wine", ModItems.BARLEY_OATS_PEPPER_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get(),
                ModItems.OATS.get(),
                ModItems.GREEN_PEPPER.get());
        fermentation("chickpea_lemon_nettle_wine", "wine", ModItems.CHICKPEA_LEMON_NETTLE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.CHICKPEA.get(),
                ModItems.LEMON.get());
        fermentation("oats_horseradish_onion_wine", "wine", ModItems.OATS_HORSERADISH_ONION_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.HORSERADISH.get(),
                ModItems.ONION.get());
        fermentation("oats_onion_horseradish_wine", "wine", ModItems.OATS_ONION_HORSERADISH_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.ONION.get(),
                ModItems.HORSERADISH.get());
        fermentation("barley_oats_horseradish_wine", "wine", ModItems.BARLEY_OATS_HORSERADISH_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.BARLEY.get(),
                ModItems.HORSERADISH.get());
        fermentation("barley_pepper_onion_wine", "wine", ModItems.BARLEY_PEPPER_ONION_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.GREEN_PEPPER.get(),
                ModItems.ONION.get(),
                ModItems.BARLEY.get(),
                ModItems.BARLEY.get());
        fermentation("oats_chickpea_pepper_wine", "wine", ModItems.OATS_CHICKPEA_PEPPER_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.CHICKPEA.get(),
                ModItems.GREEN_PEPPER.get());
        fermentation("chickpea_onion_leek_wine", "wine", ModItems.CHICKPEA_ONION_LEEK_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.CHICKPEA.get(),
                ModItems.CHICKPEA.get(),
                ModItems.ONION.get(),
                ModItems.LEEK_LEAVES.get());
        fermentation("oats_pepper_nettle_wine", "wine", ModItems.OATS_PEPPER_NETTLE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.OATS.get(),
                ModItems.OATS.get(),
                ModItems.GREEN_PEPPER.get(),
                ModItems.NETTLE_LEAVES.get());
        fermentation("oats_nettle_onion_wine", "wine", ModItems.OATS_NETTLE_ONION_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.OATS.get(),
                ModItems.ONION.get());
        fermentation("onion_lemon_nettle_wine", "wine", ModItems.ONION_LEMON_NETTLE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.LEMON.get(),
                ModItems.ONION.get());
        fermentation("pepper_nettle_onion_wine", "wine", ModItems.PEPPER_NETTLE_ONION_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.GREEN_PEPPER.get(),
                ModItems.GREEN_PEPPER.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.ONION.get());
        fermentation("pepper_lemon_leek_wine", "wine", ModItems.PEPPER_LEMON_LEEK_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.LEEK_LEAVES.get(),
                ModItems.LEEK_LEAVES.get(),
                ModItems.GREEN_PEPPER.get(),
                ModItems.LEMON.get());
        fermentation("chickpea_lemon_horseradish_wine", "wine", ModItems.CHICKPEA_LEMON_HORSERADISH_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.HORSERADISH.get(),
                ModItems.HORSERADISH.get(),
                ModItems.LEMON.get(),
                ModItems.CHICKPEA.get());
        fermentation("barley_horseradish_leek_wine", "wine", ModItems.BARLEY_HORSERADISH_LEEK_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.HORSERADISH.get(),
                ModItems.HORSERADISH.get(),
                ModItems.BARLEY.get(),
                ModItems.LEEK_LEAVES.get());
        fermentation("barley_nettle_pepper_wine", "wine", ModItems.BARLEY_NETTLE_PEPPER_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.BARLEY.get(),
                ModItems.GREEN_PEPPER.get());
        fermentation("barley_pepper_horseradish_wine", "wine", ModItems.BARLEY_PEPPER_HORSERADISH_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.HORSERADISH.get(),
                ModItems.HORSERADISH.get(),
                ModItems.BARLEY.get(),
                ModItems.GREEN_PEPPER.get());
        fermentation("chickpea_horseradish_nettle_wine", "wine", ModItems.CHICKPEA_HORSERADISH_NETTLE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.CHICKPEA.get(),
                ModItems.CHICKPEA.get(),
                ModItems.HORSERADISH.get(),
                ModItems.NETTLE_LEAVES.get());
        fermentation("pepper_leek_nettle_wine", "wine", ModItems.PEPPER_LEEK_NETTLE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.GREEN_PEPPER.get(),
                ModItems.LEEK_LEAVES.get());
        fermentation("chickpea_pepper_nettle_wine", "wine", ModItems.CHICKPEA_PEPPER_NETTLE_WINE.get(), 1, ModItems.WINE_BOTTLE.get(), 0.45f, 600,
                ModItems.NETTLE_LEAVES.get(),
                ModItems.NETTLE_LEAVES.get(),
                ModItems.CHICKPEA.get(),
                ModItems.GREEN_PEPPER.get());
    }
    private void stew(String name, String group, Item result, int resultCount, Item container,
                      float experience, int cookingTime, Item... ingredients) {
        saveProcessingRecipe(false, name, group, result, resultCount, container, experience, cookingTime, ingredients);
    }
    private void fermentation(String name, String group, Item result, int resultCount, Item container,
                              float experience, int fermentationTime, Item... ingredients) {
        saveProcessingRecipe(true, name, group, result, resultCount, container, experience, fermentationTime, ingredients);
    }
    private void saveProcessingRecipe(boolean fermentation, String name, String group, Item result,
                                      int resultCount, Item container, float experience,
                                      int processingTime, Item... inputItems) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (Item item : inputItems) {
            if (item != Items.AIR) {
                ingredients.add(Ingredient.of(item));
            }
        }
        Recipe<?> recipe = fermentation
                ? new FermentationBarrelRecipe(ingredients, new ItemStack(result, resultCount),
                        new ItemStack(container), group, experience, processingTime)
                : new StewStoveRecipe(ingredients, new ItemStack(result, resultCount),
                        new ItemStack(container), group, experience, processingTime);
        ResourceKey<Recipe<?>> key = ResourceKey.create(
                Registries.RECIPE,
                ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, name)
        );
        this.output.accept(key, recipe, null);
    }
    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }
        @Override
        protected @NotNull RecipeProvider createRecipeProvider(@Nonnull HolderLookup.Provider provider, @Nonnull RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }
        @Override
        public @NotNull String getName() {
            return EpicMediaeval.MODID + " Recipes";
        }
    }
}
