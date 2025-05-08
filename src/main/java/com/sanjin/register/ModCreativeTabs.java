package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EpicMediaeval.MODID);

    public static final Supplier<CreativeModeTab> EPIC_MEDIAEVAL_FOOD_ITEM_TAB = CREATIVE_TABS.register(
            "epic_mediaeval_food_item_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.epic_mediaeval_food_item_tab"))
                    .icon(() -> new ItemStack(ModItems.HONEY_CHICKEN.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.BARLEY_BEEF_STEW.get());
                        output.accept(ModItems.STEWED_VENISON.get());
                        output.accept(ModItems.BACON_WHITE_BEANS.get());
                        output.accept(ModItems.LEEK_SOUP.get());
                        output.accept(ModItems.OXTAIL_SOUP.get());
                        output.accept(ModItems.FLOWER_SALAD.get());
                        output.accept(ModItems.RABBIT_MEAT_SALAD.get());
                        output.accept(ModItems.FROG_STEW.get());
                        output.accept(ModItems.SEAFOOD_STEW.get());
                        output.accept(ModItems.BEEF_PIE.get());
                        output.accept(ModItems.BROWN_OAT_CAKE.get());
                        output.accept(ModItems.CHESS_GREEN_PEPPER.get());
                        output.accept(ModItems.CLAY_BAKED_TROUT.get());
                        output.accept(ModItems.COD_CAKE.get());
                        output.accept(ModItems.COOKED_TROUT.get());
                        output.accept(ModItems.DORNISH_CAKE.get());
                        output.accept(ModItems.DORNISH_MEAT_RICE.get());
                        output.accept(ModItems.ELK_MEATBALL.get());
                        output.accept(ModItems.GOLDEN_HONEY_CAKE.get());
                        output.accept(ModItems.HONEY_CHICKEN.get());
                        output.accept(ModItems.HORSERADISH_BEEF_STEW.get());
                        output.accept(ModItems.LEMON_CAKE.get());
                        output.accept(ModItems.LORD_MANDLEY_PIE.get());
                        output.accept(ModItems.NETTLE_TEA.get());
                        output.accept(ModItems.PITA_BREAD_WITH_HUMMUS.get());
                        output.accept(ModItems.ROAST_SUCKLING_PIG.get());
                        output.accept(ModItems.STEAK_AND_KIDNEY_PIE.get());
                        output.accept(ModItems.SWEET_COOKIE.get());
                        output.accept(ModItems.VEGETABLE_SALAD.get());
                        output.accept(ModItems.VENISON_PIE.get());
                        output.accept(ModItems.COOKED_ELK_MEAT.get());
                        output.accept(ModItems.COOKED_VENISON.get());
                        output.accept(ModItems.SWEET_PLUM_WINE.get());
                        output.accept(ModItems.LARGE_WOODEN_BOWL.get());
                        output.accept(ModItems.WOODEN_BOWL.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
        eventBus.addListener(ModCreativeTabs::addItemsToVanillaTabs);
    }

    private static void addItemsToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.WHINE_BOTTLE);
        }
    }
}
