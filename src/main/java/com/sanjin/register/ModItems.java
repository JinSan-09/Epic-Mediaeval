package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import com.sanjin.item.*;
import com.sanjin.item.fooditem.FrogWineItem;
import com.sanjin.item.fooditem.StormWineItem;
import com.sanjin.item.fooditem.WineItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EpicMediaeval.MODID);

    // Tableware items
    public static final Supplier<Item> LARGE_WOODEN_BOWL = tablewareItemReg("large_wooden_bowl",16);
    public static final Supplier<Item> WOODEN_BOWL = tablewareItemReg("wooden_bowl",32);
    public static final Supplier<Item> WHITE_PORCELAIN_DISH = tablewareItemReg("white_porcelain_dish",16);
    public static final Supplier<Item> WINE_BOTTLE = tablewareItemReg("wine_bottle",32);

    // Dishes items
    public static final DeferredItem<Item> BARLEY_BEEF_STEW = advancedDishesReg("barley_beef_stew",false, ModItems.WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> BACON_WHITE_BEANS = advancedDishesReg("bacon_white_beans", false, ModItems.WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> BEEF_PIE = advancedDishesReg("beef_pie",false, ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> BROWN_OAT_CAKE = advancedDishesReg("brown_oat_cake",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> CHEESE_GREEN_PEPPER = advancedDishesReg("cheese_green_pepper", false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY );
    public static final DeferredItem<Item> CLAY_BAKED_TROUT = advancedDishesReg("clay_baked_trout",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> COD_CAKE = advancedDishesReg("cod_cake",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> COOKED_TROUT = ITEMS.registerSimpleItem("cooked_trout", new Item.Properties().food(ModComponents.MIDDLE_GRADE_FOOD));
    public static final DeferredItem<Item> COOKED_ELK_MEAT = ITEMS.registerSimpleItem("cooked_elk_meat", new Item.Properties().food(ModComponents.MIDDLE_GRADE_FOOD));
    public static final DeferredItem<Item> COOKED_VENISON = ITEMS.registerSimpleItem("cooked_venison", new Item.Properties().food(ModComponents.MIDDLE_GRADE_FOOD));
    public static final DeferredItem<Item> COOKED_FROG_LEG = ITEMS.registerSimpleItem("cooked_frog_leg", new Item.Properties().food(ModComponents.MIDDLE_GRADE_FOOD));
    public static final DeferredItem<Item> DORNISH_CAKE = advancedDishesReg("dornish_cake",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> DORNISH_MEAT_RICE = advancedDishesReg("dornish_meat_rice",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> ELK_MEATBALL = advancedDishesReg("elk_meatball",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> FLOWER_SALAD = advancedDishesReg("flower_salad",false,ModItems.WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> FROG_STEW = advancedDishesReg("frog_stew",false,ModItems.LARGE_WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> GOLDEN_HONEY_CAKE = advancedDishesReg("golden_honey_cake",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> HONEY_CHICKEN = advancedDishesReg("honey_chicken",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> HORSERADISH_BEEF_STEW = advancedDishesReg("horseradish_beef_stew",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> LEEK_SOUP = advancedDishesReg("leek_soup",true,ModItems.WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> LEMON_CAKE = advancedDishesReg("lemon_cake",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> LORD_MANDLEY_PIE = advancedDishesReg("lord_mandley_pie",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> NETTLE_TEA = advancedDishesReg("nettle_tea",true,ModItems.WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> OXTAIL_SOUP = advancedDishesReg("oxtail_soup",true,ModItems.WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> PITA_BREAD_WITH_HUMMUS = advancedDishesReg("pita_bread_with_hummus",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> RABBIT_MEAT_SALAD = advancedDishesReg("rabbit_meat_salad",false,ModItems.WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> ROAST_SUCKLING_PIG = advancedDishesReg("roast_suckling_pig",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> SEAFOOD_STEW = advancedDishesReg("seafood_stew",false,ModItems.LARGE_WOODEN_BOWL, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> STEAK_AND_KIDNEY_PIE = advancedDishesReg("steak_and_kidney_pie",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> STEWED_VENISON = advancedDishesReg("stewed_venison",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> SWEET_COOKIE = advancedDishesReg("sweet_cookie",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> VEGETABLE_SALAD = advancedDishesReg("vegetable_salad",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);
    public static final DeferredItem<Item> VENISON_PIE = advancedDishesReg("venison_pie",false,ModItems.WHITE_PORCELAIN_DISH, ModComponents.HIGH_GRADE_FOOD, EffectComponent.EMPTY);

    // Wine items
    public static final DeferredItem<Item> BARLEY_WINE = wineItemReg("barley_wine", false,"item.barley_wine.text1","item.barley_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20*60, 2, true, true), new MobEffectInstance(MobEffects.SATURATION, 20*60, 2, true, true))));
    public static final DeferredItem<Item> BARLEY_OATS_ONION_WINE = wineItemReg("barley_oats_onion_wine", false, "item.barley_oats_onion_wine.text1", "item.barley_oats_onion_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.LEVITATION, 5, 1, true, true), new MobEffectInstance(MobEffects.SLOW_FALLING, 15, 1, true, true), new MobEffectInstance(MobEffects.LUCK, 20 * 120, 3, true, true))));
    public static final DeferredItem<Item> BARLEY_PEPPER_HORSERADISH_WINE = wineItemReg("barley_pepper_horseradish_wine", false, "item.barley_pepper_horseradish_wine.text1", "item.barley_pepper_horseradish_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.HUNGER, 20 * 30, 2, true, true), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 3, true, true))));
    public static final DeferredItem<Item> BARLEY_OATS_PEPPER_WINE = wineItemReg("barley_oats_pepper_wine", false, "item.barley_oats_pepper_wine.text1", "item.barley_oats_pepper_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.SATURATION, 20 * 120, 2, true, true), new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> BARLEY_OATS_PLUM_WINE = wineItemReg("barley_oats_plum_wine", false, "item.barley_oats_plum_wine.text1", "item.barley_oats_plum_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.JUMP, 20 * 120, 2, true, true), new MobEffectInstance(MobEffects.LUCK, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> BARLEY_HORSERADISH_LEEK_WINE = wineItemReg("barley_horseradish_leek_wine", false, "item.barley_horseradish_leek_wine.text1", "item.barley_horseradish_leek_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 60, 4, true, true), new MobEffectInstance(MobEffects.SATURATION, 20 * 300, 1, true, true), new MobEffectInstance(MobEffects.WEAKNESS, 20 * 15, 3, true, true))));
    public static final DeferredItem<Item> BARLEY_NETTLE_PEPPER_WINE = wineItemReg("barley_nettle_pepper_wine", false, "item.barley_nettle_pepper_wine.text1", "item.barley_nettle_pepper_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.GLOWING, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.LUCK, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.INVISIBILITY, 20 * 10, 1, true, true))));
    public static final DeferredItem<Item> BARLEY_OATS_HORSERADISH_WINE = wineItemReg("barley_oats_horseradish_wine", false, "item.barley_oats_horseradish_wine.text1", "item.barley_oats_horseradish_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 60, 1, true, true), new MobEffectInstance(MobEffects.REGENERATION, 20 * 15, 1, true, true))));
    public static final DeferredItem<Item> BARLEY_CHICKPEA_OATS_WINE = wineItemReg("barley_chickpea_oats_wine", false, "item.barley_chickpea_oats_wine.text1", "item.barley_chickpea_oats_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.REGENERATION, 20 * 30, 2, true, true), new MobEffectInstance(MobEffects.LUCK, 20 * 60, 3, true, true))));
    public static final DeferredItem<Item> BARLEY_PEPPER_ONION_WINE = wineItemReg("barley_pepper_onion_wine", false, "item.barley_pepper_onion_wine.text1", "item.barley_pepper_onion_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 150, 2, true, true), new MobEffectInstance(MobEffects.REGENERATION, 20 * 60, 2, true, true), new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 3, true, true))));
    public static final DeferredItem<Item> BARLEY_ONION_PLUM_WINE = wineItemReg("barley_onion_plum_wine", false, "item.barley_onion_plum_wine.text1", "item.barley_onion_plum_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 3, true, true), new MobEffectInstance(MobEffects.SATURATION, 20 * 60, 2, true, true))));
    public static final DeferredItem<Item> BARLEY_OATS_LEMON_WINE = wineItemReg("barley_oats_lemon_wine", false, "item.barley_oats_lemon_wine.text1", "item.barley_oats_lemon_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.SATURATION, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.REGENERATION, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> CHICKPEA_LEMON_NETTLE_WINE = wineItemReg("chickpea_lemon_nettle_wine", false, "item.chickpea_lemon_nettle_wine.text1", "item.chickpea_lemon_nettle_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 300, 2, true, true), new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 2, true, true), new MobEffectInstance(MobEffects.WEAKNESS, 20 * 150, 2, true, true))));
    public static final DeferredItem<Item> CHICKPEA_OATS_ONION_WINE = wineItemReg("chickpea_oats_onion_wine", false, "item.chickpea_oats_onion_wine.text1", "item.chickpea_oats_onion_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.LUCK, 20 * 30, 3, true, true), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 30, 5, true, true))));
    public static final DeferredItem<Item> CHICKPEA_PEPPER_NETTLE_WINE = wineItemReg("chickpea_pepper_nettle_wine", false, "item.chickpea_pepper_nettle_wine.text1", "item.chickpea_pepper_nettle_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 120, 2, true, true), new MobEffectInstance(MobEffects.JUMP, 20 * 120, 3, true, true))));
    public static final DeferredItem<Item> CHICKPEA_HORSERADISH_NETTLE_WINE = wineItemReg("chickpea_horseradish_nettle_wine", false, "item.chickpea_horseradish_nettle_wine.text1", "item.chickpea_horseradish_nettle_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.SATURATION, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 120, 2, true, true), new MobEffectInstance(MobEffects.JUMP, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> CHICKPEA_LEMON_HORSERADISH_WINE = wineItemReg("chickpea_lemon_horseradish_wine", false, "item.chickpea_lemon_horseradish_wine.text1", "item.chickpea_lemon_horseradish_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.HUNGER, 20 * 15, 2, true, true), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> CHICKPEA_ONION_LEEK_WINE = wineItemReg("chickpea_onion_leek_wine", false, "item.chickpea_onion_leek_wine.text1", "item.chickpea_onion_leek_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.JUMP, 20 * 120, 2, true, true), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 120, 3, true, true))));
    public static final DeferredItem<Item> CHICKPEA_OATS_LEEK_WINE = wineItemReg("chickpea_oats_leek_wine", false, "item.chickpea_oats_leek_wine.text1", "item.chickpea_oats_leek_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.WEAKNESS, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> DREAM_WINE = wineItemReg("dream_wine", true, "item.dream_wine.text1", "item.dream_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.LUCK, 20 * 300, 5, true, true), new MobEffectInstance(MobEffects.JUMP, 20 * 10, 1000, true, true), new MobEffectInstance(MobEffects.INVISIBILITY, 20 * 300, 1, true, true), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 300, 5, true, true), new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 600, 5, true, true))));
    public static final DeferredItem<Item> FROG_WINE = ITEMS.register("frog_wine", registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);return new FrogWineItem(new Item.Properties().setId(key).component(DataComponents.CONSUMABLE, ModComponents.COMMON_DRINK), true, List.of(Component.translatable("item.frog_wine.text1"), Component.translatable("item.frog_wine.text2")), new EffectComponent(List.of(new MobEffectInstance(MobEffects.UNLUCK, 20 * 600, 5, true, true), new MobEffectInstance(MobEffects.CONFUSION, 20 * 600, 5, true, true), new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 600, 5, true, true), new MobEffectInstance(MobEffects.JUMP, 20 * 600, 5, true, true), new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20 * 600, 5, true, true))), new UseRemainderComponent(ModItems.WINE_BOTTLE.get().getDefaultInstance(), 1));});
    public static final DeferredItem<Item> LEMON_JUICE_WINE = wineItemReg("lemon_juice_wine", false, "item.lemon_juice_wine.text1", "item.lemon_juice_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.GLOWING, 20 * 120, 2, true, true), new MobEffectInstance(MobEffects.LUCK, 20 * 60, 2, true, true))));
    public static final DeferredItem<Item> OATS_WINE = wineItemReg("oats_wine", false, "item.oats_wine.text1", "item.oats_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.REGENERATION, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> OATS_ONION_HORSERADISH_WINE = wineItemReg("oats_onion_horseradish_wine", false, "item.oats_onion_horseradish_wine.text1", "item.oats_onion_horseradish_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.UNLUCK, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 120, 4, true, true))));
    public static final DeferredItem<Item> OATS_CHICKPEA_NETTLE_WINE = wineItemReg("oats_chickpea_nettle_wine", false, "item.oats_chickpea_nettle_wine.text1", "item.oats_chickpea_nettle_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.SATURATION, 20 * 60, 3, true, true), new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 3, true, true))));
    public static final DeferredItem<Item> OATS_LEEK_ONION_WINE = wineItemReg("oats_leek_onion_wine", false, "item.oats_leek_onion_wine.text1", "item.oats_leek_onion_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.JUMP, 20 * 300, 2, true, true), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 60, 2, true, true))));
    public static final DeferredItem<Item> OATS_HORSERADISH_ONION_WINE = wineItemReg("oats_horseradish_onion_wine", false, "item.oats_horseradish_onion_wine.text1", "item.oats_horseradish_onion_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 60, 1, true, true), new MobEffectInstance(MobEffects.SATURATION, 20 * 30, 1, true, true))));
    public static final DeferredItem<Item> OATS_CHICKPEA_PLUM_WINE = wineItemReg("oats_chickpea_plum_wine", false, "item.oats_chickpea_plum_wine.text1", "item.oats_chickpea_plum_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.GLOWING, 20 * 300, 1, true, true), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 300, 1, true, true))));
    public static final DeferredItem<Item> OATS_LEEK_PLUM_WINE = wineItemReg("oats_leek_plum_wine", false, "item.oats_leek_plum_wine.text1", "item.oats_leek_plum_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.JUMP, 20 * 30, 2, true, true), new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 60, 5, true, true))));
    public static final DeferredItem<Item> OATS_CHICKPEA_LEMON_WINE = wineItemReg("oats_chickpea_lemon_wine", false, "item.oats_chickpea_lemon_wine.text1", "item.oats_chickpea_lemon_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 3, true, true), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 150, 2, true, true), new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 150, 2, true, true))));
    public static final DeferredItem<Item> OATS_NETTLE_ONION_WINE = wineItemReg("oats_nettle_onion_wine", false, "item.oats_nettle_onion_wine.text1", "item.oats_nettle_onion_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 30, 1, true, true), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 10, 3, true, true))));
    public static final DeferredItem<Item> OATS_PEPPER_NETTLE_WINE = wineItemReg("oats_pepper_nettle_wine", false, "item.oats_pepper_nettle_wine.text1", "item.oats_pepper_nettle_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.UNLUCK, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.JUMP, 20 * 60, 2, true, true), new MobEffectInstance(MobEffects.HUNGER, 20 * 30, 2, true, true))));
    public static final DeferredItem<Item> OATS_CHICKPEA_PEPPER_WINE = wineItemReg("oats_chickpea_pepper_wine",false,"item.oats_chickpea_pepper_wine.text1","item.oats_chickpea_pepper_wine.text2",new EffectComponent(List.of(new MobEffectInstance(MobEffects.HUNGER,20*120,2,true,true), new MobEffectInstance(MobEffects.DAMAGE_BOOST,20*30,3,true,true), new MobEffectInstance(MobEffects.DIG_SPEED,20*30,3,true,true))));
    public static final DeferredItem<Item> ONION_LEMON_NETTLE_WINE = wineItemReg("onion_lemon_nettle_wine", false, "item.onion_lemon_nettle_wine.text1", "item.onion_lemon_nettle_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 300, 1, true, true), new MobEffectInstance(MobEffects.HUNGER, 20 * 10, 3, true, true))));
    public static final DeferredItem<Item> PEPPER_LEMON_LEEK_WINE = wineItemReg("pepper_lemon_leek_wine", false, "item.pepper_lemon_leek_wine.text1", "item.pepper_lemon_leek_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.GLOWING, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 300, 2, true, true), new MobEffectInstance(MobEffects.SATURATION, 20 * 5, 5, true, true))));
    public static final DeferredItem<Item> PEPPER_LEEK_NETTLE_WINE = wineItemReg("pepper_leek_nettle_wine", false, "item.pepper_leek_nettle_wine.text1", "item.pepper_leek_nettle_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.HUNGER, 20 * 30, 1, true, true))));
    public static final DeferredItem<Item> PEPPER_NETTLE_ONION_WINE = wineItemReg("pepper_nettle_onion_wine", false, "item.pepper_nettle_onion_wine.text1", "item.pepper_nettle_onion_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.UNLUCK, 20 * 120, 3, true, true), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 120, 1, true, true), new MobEffectInstance(MobEffects.JUMP, 20 * 120, 2, true, true))));
    public static final DeferredItem<Item> SWEET_PLUM_WINE = wineItemReg("sweet_plum_wine", false, "item.sweet_plum_wine.text1", "item.sweet_plum_wine.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 2, true, true), new MobEffectInstance(MobEffects.REGENERATION, 20 * 60, 2, true, true), new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 2, true, true))));
    public static final DeferredItem<Item> STORM_WINE = ITEMS.register("storm_wine", registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);return new StormWineItem(new Item.Properties().setId(key).component(DataComponents.CONSUMABLE, ModComponents.COMMON_DRINK), true, List.of(Component.translatable("item.storm_wine.text1"), Component.translatable("item.storm_wine.text2")), new EffectComponent(List.of(new MobEffectInstance(MobEffects.DARKNESS, 20 * 300, 3, true, true), new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 300, 1, true, true), new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 300, 5, true, true), new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 300, 5, true, true), new MobEffectInstance(MobEffects.ABSORPTION, 20 * 300, 3, true, true))), new UseRemainderComponent(ModItems.WINE_BOTTLE.get().getDefaultInstance(), 1));});
    public static final DeferredItem<Item> UNLUCK_WINE = wineItemReg("unluck_wine", true,"item.unluck.text1","item.unluck.text2", new EffectComponent(List.of(new MobEffectInstance(MobEffects.UNLUCK,20*300,5,true,true), new MobEffectInstance(MobEffects.BAD_OMEN, 20*300,1,true,true), new MobEffectInstance(MobEffects.POISON,20*300,3,true,true), new MobEffectInstance(MobEffects.DAMAGE_BOOST,20*150,8,true,true), new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20*150,3,true,true))));

    // Primitives items
    public static final DeferredItem<Item> BARLEY = ITEMS.registerSimpleItem("barley", new Item.Properties());
    public static final DeferredItem<Item> BEEF_KIDNEY = ITEMS.registerSimpleItem("beef_kidney", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> CHEESE = ITEMS.registerSimpleItem("cheese", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> CHICKPEA = ITEMS.registerSimpleItem("chickpea", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> DOUGH = ITEMS.registerSimpleItem("dough", new Item.Properties());
    public static final DeferredItem<Item> FROG_LEG = ITEMS.registerSimpleItem("frog_leg", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> GREEN_PEPPER = ITEMS.registerSimpleItem("green_pepper", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> HORSERADISH = ITEMS.registerSimpleItem("horseradish", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> LEMON = ITEMS.registerSimpleItem("lemon", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> LEEK_LEAVES = ITEMS.registerSimpleItem("leek_leaves", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> NETTLE_LEAVES = ITEMS.registerSimpleItem("nettle_leaves", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> OATS = ITEMS.registerSimpleItem("oats", new Item.Properties());
    public static final DeferredItem<Item> ONION = throwableItemReg("onion", ModItemEntities.ONION_ENTITY::get);
    public static final DeferredItem<Item> OXTAIL = ITEMS.registerSimpleItem("oxtail", new Item.Properties());
    public static final DeferredItem<Item> PLUM = ITEMS.registerSimpleItem("plum", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> RAW_TROUT = ITEMS.registerSimpleItem("raw_trout", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> RAW_ELK_MEAT = ITEMS.registerSimpleItem("raw_elk_meat", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> RAW_VENISON = ITEMS.registerSimpleItem("raw_venison", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));
    public static final DeferredItem<Item> WHITE_BEANS = ITEMS.registerSimpleItem("white_beans", new Item.Properties().food(ModComponents.LOW_GRADE_FOOD));

    public static @NotNull DeferredItem<Item> wineItemReg(String name, boolean hasEnchantmentEffect, String text1, String text2, EffectComponent wineEffects) {
        return ITEMS.register(name,
                registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);
                    return new WineItem(new Item.Properties()
                            .setId(key)
                            .food(ModComponents.LOW_GRADE_FOOD)
                            .component(DataComponents.CONSUMABLE, ModComponents.COMMON_DRINK)
                            .stacksTo(1),
                            hasEnchantmentEffect,
                            List.of(
                                    Component.translatable(text1),
                                    Component.translatable(text2)
                            ),
                            wineEffects,
                            new UseRemainderComponent(ModItems.WINE_BOTTLE.get().getDefaultInstance(),1)
                    );}
        );
    }
    public static @NotNull DeferredItem<Item> throwableItemReg(String name, Supplier<EntityType<? extends ThrowableItemProjectile>> projectileType){
        return ITEMS.register(name, registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);
            Item.Properties props = new Item.Properties().setId(key);
            EntityType<? extends ThrowableItemProjectile> type = projectileType.get();
            return new ThrowableItem(props, type);
        });
    }
    public static @NotNull DeferredItem<Item> advancedDishesReg(String name, boolean canDrink, Supplier<Item> remainder, FoodProperties foodLevel, EffectComponent effects){
        if(canDrink){
            return ITEMS.register(name,
                    registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);
                        return new RemainderItem(new Item.Properties()
                                .setId(key)
                                .food(foodLevel)
                                .component(DataComponents.CONSUMABLE, ModComponents.COMMON_DRINK),
                                new UseRemainderComponent(remainder.get().getDefaultInstance(),1),
                                effects
                        );
                    });
        }
        return ITEMS.register(name,
                registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);
                    return new RemainderItem(new Item.Properties()
                            .setId(key)
                            .food(foodLevel),
                            new UseRemainderComponent(remainder.get().getDefaultInstance(),1),
                            effects
                    );
                });
    }
    public static @NotNull Supplier<Item> tablewareItemReg(String name, int stacksTo){
        return ITEMS.register(name, registryName ->{ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);return new Item(new Item.Properties().stacksTo(stacksTo).setId(key));});
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
