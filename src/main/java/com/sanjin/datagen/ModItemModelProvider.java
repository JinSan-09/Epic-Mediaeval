package com.sanjin.datagen;

import com.sanjin.EpicMediaeval;
import com.sanjin.register.ModItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider  {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EpicMediaeval.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicItem(ModItems.LARGE_WOODEN_BOWL.get());
        basicItem(ModItems.WOODEN_BOWL.get());
        basicItem(ModItems.LARGE_WOODEN_BOWL.get());
        basicItem(ModItems.WHITE_PORCELAIN_DISH.get());
        basicItem(ModItems.WINE_BOTTLE.get());

        basicItem(ModItems.BARLEY_BEEF_STEW.get());
        basicItem(ModItems.BACON_WHITE_BEANS.get());
        basicItem(ModItems.BEEF_PIE.get());
        basicItem(ModItems.BROWN_OAT_CAKE.get());
        basicItem(ModItems.CHEESE_GREEN_PEPPER.get());
        basicItem(ModItems.CLAY_BAKED_TROUT.get());
        basicItem(ModItems.COD_CAKE.get());
        basicItem(ModItems.COOKED_TROUT.get());
        basicItem(ModItems.COOKED_ELK_MEAT.get());
        basicItem(ModItems.COOKED_VENISON.get());
        basicItem(ModItems.COOKED_FROG_LEG.get());
        basicItem(ModItems.DORNISH_CAKE.get());
        basicItem(ModItems.DORNISH_MEAT_RICE.get());
        basicItem(ModItems.ELK_MEATBALL.get());
        basicItem(ModItems.FLOWER_SALAD.get());
        basicItem(ModItems.FROG_STEW.get());
        basicItem(ModItems.GOLDEN_HONEY_CAKE.get());
        basicItem(ModItems.HONEY_CHICKEN.get());
        basicItem(ModItems.HORSERADISH_BEEF_STEW.get());
        basicItem(ModItems.LEEK_SOUP.get());
        basicItem(ModItems.LEMON_CAKE.get());
        basicItem(ModItems.LORD_MANDLEY_PIE.get());
        basicItem(ModItems.NETTLE_TEA.get());
        basicItem(ModItems.OXTAIL_SOUP.get());
        basicItem(ModItems.PITA_BREAD_WITH_HUMMUS.get());
        basicItem(ModItems.RABBIT_MEAT_SALAD.get());
        basicItem(ModItems.ROAST_SUCKLING_PIG.get());
        basicItem(ModItems.SEAFOOD_STEW.get());
        basicItem(ModItems.STEAK_AND_KIDNEY_PIE.get());
        basicItem(ModItems.STEWED_VENISON.get());
        basicItem(ModItems.SWEET_COOKIE.get());
        basicItem(ModItems.VEGETABLE_SALAD.get());
        basicItem(ModItems.VENISON_PIE.get());

         basicItem(ModItems.BARLEY_WINE.get());
        // basicItem(ModItems.BARLEY_OATS_ONION_WINE.get());
        // basicItem(ModItems.BARLEY_PEPPER_HORSERADISH_WINE.get());
        // basicItem(ModItems.BARLEY_OATS_PEPPER_WINE.get());
        // basicItem(ModItems.BARLEY_OATS_PLUM_WINE.get());
        // basicItem(ModItems.BARLEY_HORSERADISH_LEEK_WINE.get());
        // basicItem(ModItems.BARLEY_NETTLE_PEPPER_WINE.get());
        // basicItem(ModItems.BARLEY_OATS_HORSERADISH_WINE.get());
        // basicItem(ModItems.BARLEY_CHICKPEA_OATS_WINE.get());
        // basicItem(ModItems.BARLEY_PEPPER_ONION_WINE.get());
        // basicItem(ModItems.BARLEY_OATS_LEMON_WINE.get());
        // basicItem(ModItems.CHICKPEA_OATS_ONION_WINE.get());
        // basicItem(ModItems.CHICKPEA_LEMON_NETTLE_WINE.get());
        // basicItem(ModItems.CHICKPEA_PEPPER_NETTLE_WINE.get());
        // basicItem(ModItems.CHICKPEA_HORSERADISH_NETTLE_WINE.get());
        // basicItem(ModItems.CHICKPEA_LEMON_HORSERADISH_WINE.get());
        // basicItem(ModItems.CHICKPEA_ONION_LEEK_WINE.get());
        // basicItem(ModItems.CHICKPEA_OATS_LEEK_WINE.get());
        // basicItem(ModItems.DREAM_WINE.get());
         basicItem(ModItems.FROG_WINE.get());
         basicItem(ModItems.LEMON_JUICE_WINE.get());
        // basicItem(ModItems.OATS_WINE.get());
        // basicItem(ModItems.OATS_ONION_HORSERADISH_WINE.get());
        // basicItem(ModItems.OATS_CHICKPEA_NETTLE_WINE.get());
        // basicItem(ModItems.OATS_LEEK_ONION_WINE.get());
        // basicItem(ModItems.OATS_HORSERADISH_ONION_WINE.get());
        // basicItem(ModItems.OATS_CHICKPEA_PLUM_WINE.get());
        // basicItem(ModItems.OATS_CHICKPEA_LEMON_WINE.get());
        // basicItem(ModItems.OATS_PEPPER_NETTLE_WINE.get());
        // basicItem(ModItems.OATS_CHICKPEA_PEPPER_WINE.get());
        // basicItem(ModItems.ONION_LEMON_NETTLE_WINE.get());
        // basicItem(ModItems.PEPPER_LEMON_LEEK_WINE.get());
        // basicItem(ModItems.PEPPER_LEEK_NETTLE_WINE.get());
        // basicItem(ModItems.PEPPER_NETTLE_ONION_WINE.get());
        // basicItem(ModItems.SWEET_PLUM_WINE.get());
        // basicItem(ModItems.STORM_WINE.get());
        // basicItem(ModItems.UNLUCK_WINE.get());

        basicItem(ModItems.BARLEY.get());
        basicItem(ModItems.BEEF_KIDNEY.get());
        basicItem(ModItems.CHEESE.get());
        basicItem(ModItems.CHICKPEA.get());
        basicItem(ModItems.DOUGH.get());
        basicItem(ModItems.FROG_LEG.get());
        basicItem(ModItems.GREEN_PEPPER.get());
        basicItem(ModItems.HORSERADISH.get());
        basicItem(ModItems.LEMON.get());
        basicItem(ModItems.LEEK_LEAVES.get());
        basicItem(ModItems.NETTLE_LEAVES.get());
        basicItem(ModItems.OATS.get());
        basicItem(ModItems.ONION.get());
        basicItem(ModItems.OXTAIL.get());
        basicItem(ModItems.PLUM.get());
        basicItem(ModItems.RAW_TROUT.get());
        basicItem(ModItems.RAW_ELK_MEAT.get());
        basicItem(ModItems.RAW_VENISON.get());
        basicItem(ModItems.WHITE_BEANS.get());

         basicItem(ModItems.SEEDS_BARLEY.get());
         basicItem(ModItems.SEEDS_GREEN_PEPPER.get());
        // basicItem(ModItems.SEEDS_NETTLE.get());
        // basicItem(ModItems.SEEDS_OATS.get());
        // basicItem(ModItems.ONION_TUBER.get());

        basicItem(ModItems.FEMALE_PEASANT_SPAWN_EGG.get());
        basicItem(ModItems.MALE_PEASANT_SPAWN_EGG.get());

        
        
    }
    
}
