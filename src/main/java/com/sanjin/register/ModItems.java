package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.component.EffectComponent;
import com.sanjin.component.UseRemainderComponent;
import com.sanjin.item.*;
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
    public static final DeferredItem<Item> SWEET_PLUM_WINE = wineItemReg("sweet_plum_wine",
            false,"item.sweet_plum_wine.text1","item.sweet_plum_wine.text2",new EffectComponent(List.of(
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20*60, 2, true, true),
                    new MobEffectInstance(MobEffects.HEAL, 20*60, 1, true, true)
            )));
    public static final DeferredItem<Item> BARLEY_WINE = wineItemReg("barley_wine",
            false,"item.barley_wine.text1","item.barley_wine.text2", new EffectComponent(List.of(
                    new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20*60, 2, true, true),
                    new MobEffectInstance(MobEffects.SATURATION, 20*60, 2, true, true)
            )));
    public static final DeferredItem<Item> LEMON_JUICE_WINE = wineItemReg("lemon_juice_wine",
            false,"item.lemon_juice_wine.text1","item.lemon_juice_wine.text2", new EffectComponent(List.of(
                    new MobEffectInstance(MobEffects.GLOWING, 20*120, 2, true, true),
                    new MobEffectInstance(MobEffects.LUCK, 20*60, 2, true, true)
            )));
    public static final DeferredItem<Item> FROG_WINE = ITEMS.register("frog_wine",
            registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);
        return new FrogWineItem(new Item.Properties()
                .setId(key)
                .component(DataComponents.CONSUMABLE, ModComponents.COMMON_DRINK),
                false,
                List.of(Component.translatable("item.frog_wine.text1"),Component.translatable("item.frog_wine.text2")),
                new EffectComponent(List.of(
                        new MobEffectInstance(MobEffects.LUCK, 3,2,true,true)
                )),
                new UseRemainderComponent(ModItems.WINE_BOTTLE.get().getDefaultInstance(),1)
        );
    });

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

    public static DeferredItem<Item> wineItemReg(String name, boolean hasEnchantmentEffect, String text1, String text2, EffectComponent wineEffects) {
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
    public static DeferredItem<Item> throwableItemReg(String name, Supplier<EntityType<? extends ThrowableItemProjectile>> projectileType){
        return ITEMS.register(name, registryName -> {ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);
            Item.Properties props = new Item.Properties().setId(key);
            EntityType<? extends ThrowableItemProjectile> type = projectileType.get();
            return new ThrowableItem(props, type);
        });
    }
    public static DeferredItem<Item> advancedDishesReg(String name, boolean canDrink, Supplier<Item> remainder, FoodProperties foodLevel, EffectComponent effects){
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
    public static Supplier<Item> tablewareItemReg(String name, int stacksTo){
        return ITEMS.register(name, registryName ->{ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, registryName);return new Item(new Item.Properties().stacksTo(stacksTo).setId(key));});
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
