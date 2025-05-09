package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EpicMediaeval.MODID);

    // Tableware items
    public static final DeferredItem<Item> LARGE_WOODEN_BOWL = ITEMS.registerSimpleItem("large_wooden_bowl",
            new Item.Properties());
    public static final DeferredItem<Item> WOODEN_BOWL = ITEMS.registerSimpleItem("wooden_bowl",
            new Item.Properties());
    public static final DeferredItem<Item> WHITE_PORCELAIN_DISH = ITEMS.registerSimpleItem("white_porcelain_dish",
            new Item.Properties());
    public static final DeferredItem<Item> WHINE_BOTTLE = ITEMS.registerSimpleItem("wine_bottle",
            new Item.Properties());

    // Food items
    public static final DeferredItem<Item> BARLEY_BEEF_STEW = ITEMS.registerSimpleItem("barley_beef_stew",
            new Item.Properties().food(new FoodProperties(8,0.5f,true)));
    public static final DeferredItem<Item> BACON_WHITE_BEANS = ITEMS.registerSimpleItem("bacon_white_beans",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> BEEF_PIE = ITEMS.registerSimpleItem("beef_pie",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> BROWN_OAT_CAKE = ITEMS.registerSimpleItem("brown_oat_cake",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> CHESS_GREEN_PEPPER = ITEMS.registerSimpleItem("cheese_green_pepper",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> CLAY_BAKED_TROUT = ITEMS.registerSimpleItem("clay_baked_trout",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> COD_CAKE = ITEMS.registerSimpleItem("cod_cake",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> COOKED_TROUT = ITEMS.registerSimpleItem("cooked_trout",
            new Item.Properties().food(new FoodProperties(3,0.3f, false)));
    public static final DeferredItem<Item> COOKED_ELK_MEAT = ITEMS.registerSimpleItem("cooked_elk_meat",
            new Item.Properties().food(new FoodProperties(5,0.3f, false)));
    public static final DeferredItem<Item> COOKED_VENISON = ITEMS.registerSimpleItem("cooked_venison",
            new Item.Properties().food(new FoodProperties(5,0.3f, false)));
    public static final DeferredItem<Item> DORNISH_CAKE = ITEMS.registerSimpleItem("dornish_cake",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> DORNISH_MEAT_RICE = ITEMS.registerSimpleItem("dornish_meat_rice",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> ELK_MEATBALL = ITEMS.registerSimpleItem("elk_meatball",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> FLOWER_SALAD = ITEMS.registerSimpleItem("flower_salad",
            new Item.Properties().food(new FoodProperties(5,0.3f, false)));
    public static final DeferredItem<Item> FROG_STEW = ITEMS.registerSimpleItem("frog_stew",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> GOLDEN_HONEY_CAKE = ITEMS.registerSimpleItem("golden_honey_cake",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> HONEY_CHICKEN = ITEMS.registerSimpleItem("honey_chicken",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> HORSERADISH_BEEF_STEW = ITEMS.registerSimpleItem("horseradish_beef_stew",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> LEEK_SOUP = ITEMS.registerSimpleItem("leek_soup",
            new Item.Properties());
    public static final DeferredItem<Item> LEMON_CAKE = ITEMS.registerSimpleItem("lemon_cake",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> LORD_MANDLEY_PIE = ITEMS.registerSimpleItem("lord_mandley_pie",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> NETTLE_TEA = ITEMS.registerSimpleItem("nettle_tea",
            new Item.Properties());
    public static final DeferredItem<Item> OXTAIL_SOUP = ITEMS.registerSimpleItem("oxtail_soup",
            new Item.Properties());
    public static final DeferredItem<Item> PITA_BREAD_WITH_HUMMUS = ITEMS.registerSimpleItem("pita_bread_with_hummus",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> RABBIT_MEAT_SALAD = ITEMS.registerSimpleItem("rabbit_meat_salad",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> ROAST_SUCKLING_PIG = ITEMS.registerSimpleItem("roast_suckling_pig",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> SEAFOOD_STEW = ITEMS.registerSimpleItem("seafood_stew",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> STEAK_AND_KIDNEY_PIE = ITEMS.registerSimpleItem("steak_and_kidney_pie",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> STEWED_VENISON = ITEMS.registerSimpleItem("stewed_venison",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> SWEET_COOKIE = ITEMS.registerSimpleItem("sweet_cookie",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> SWEET_PLUM_WINE = ITEMS.registerSimpleItem("sweet_plum_wine",
            new Item.Properties());
    public static final DeferredItem<Item> VEGETABLE_SALAD = ITEMS.registerSimpleItem("vegetable_salad",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));
    public static final DeferredItem<Item> VENISON_PIE = ITEMS.registerSimpleItem("venison_pie",
            new Item.Properties().food(new FoodProperties(8,0.5f, false)));

    // Primitives items
    public static final DeferredItem<Item> BARLEY = ITEMS.registerSimpleItem("barley",
            new Item.Properties());
    public static final DeferredItem<Item> BEEF_KIDNEY = ITEMS.registerSimpleItem("beef_kidney",
            new Item.Properties());
    public static final DeferredItem<Item> CHICKPEA = ITEMS.registerSimpleItem("chickpea",
            new Item.Properties());
    public static final DeferredItem<Item> DOUGH = ITEMS.registerSimpleItem("dough",
            new Item.Properties());
    public static final DeferredItem<Item> FROG_LEG = ITEMS.registerSimpleItem("frog_leg",
            new Item.Properties());
    public static final DeferredItem<Item> GREEN_PEPPER = ITEMS.registerSimpleItem("green_pepper",
            new Item.Properties());
    public static final DeferredItem<Item> HORSERADISH = ITEMS.registerSimpleItem("horseradish",
            new Item.Properties());
    public static final DeferredItem<Item> LEMON = ITEMS.registerSimpleItem("lemon",
            new Item.Properties().food(new FoodProperties(1,0.3f, true)));
    public static final DeferredItem<Item> LEEK_LEAVES = ITEMS.registerSimpleItem("leek_leaves",
            new Item.Properties());
    public static final DeferredItem<Item> NETTLE = ITEMS.registerSimpleItem("nettle",
            new Item.Properties());
    public static final DeferredItem<Item> OATS = ITEMS.registerSimpleItem("oats",
            new Item.Properties());
    public static final DeferredItem<Item> OXTAIL = ITEMS.registerSimpleItem("oxtail",
            new Item.Properties());
    public static final DeferredItem<Item> PLUM = ITEMS.registerSimpleItem("plum",
            new Item.Properties().food(new FoodProperties(1,0.3f, false)));
    public static final DeferredItem<Item> RAW_TROUT = ITEMS.registerSimpleItem("raw_trout",
            new Item.Properties().food(new FoodProperties(1,0.3f, false)));
    public static final DeferredItem<Item> RAW_ELK_MEAT = ITEMS.registerSimpleItem("raw_elk_meat",
            new Item.Properties().food(new FoodProperties(2,0.3f, false)));
    public static final DeferredItem<Item> RAW_VENISON = ITEMS.registerSimpleItem("raw_venison",
            new Item.Properties().food(new FoodProperties(2,0.3f, false)));
    public static final DeferredItem<Item> WHITE_BEANS = ITEMS.registerSimpleItem("white_beans",
            new Item.Properties().food(new FoodProperties(1,0.3f, false)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
