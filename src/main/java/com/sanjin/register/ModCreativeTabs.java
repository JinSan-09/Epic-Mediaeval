package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EpicMediaeval.MODID);

    public static final Supplier<CreativeModeTab> EPIC_MEDIAEVAL_DISHES_AND_MATERIALS = CREATIVE_TABS.register("epic_mediaeval_dishes_and_materials", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.epic_mediaeval_dishes_and_materials"))
                    .icon(() -> new ItemStack(ModItems.HONEY_CHICKEN.get()))
                    .displayItems((parameters, output) -> {
                        // Food items
                        output.accept(ModItems.BARLEY_BEEF_STEW.get());
                        output.accept(ModItems.STEWED_VENISON.get());
                        output.accept(ModItems.BACON_WHITE_BEANS.get());
                        output.accept(ModItems.LEEK_SOUP.get());
                        output.accept(ModItems.NETTLE_TEA.get());
                        output.accept(ModItems.OXTAIL_SOUP.get());
                        output.accept(ModItems.FLOWER_SALAD.get());
                        output.accept(ModItems.RABBIT_MEAT_SALAD.get());
                        output.accept(ModItems.FROG_STEW.get());
                        output.accept(ModItems.SEAFOOD_STEW.get());
                        output.accept(ModItems.BEEF_PIE.get());
                        output.accept(ModItems.BROWN_OAT_CAKE.get());
                        output.accept(ModItems.CHEESE_GREEN_PEPPER.get());
                        output.accept(ModItems.CLAY_BAKED_TROUT.get());
                        output.accept(ModItems.COD_CAKE.get());
                        output.accept(ModItems.DORNISH_CAKE.get());
                        output.accept(ModItems.DORNISH_MEAT_RICE.get());
                        output.accept(ModItems.ELK_MEATBALL.get());
                        output.accept(ModItems.GOLDEN_HONEY_CAKE.get());
                        output.accept(ModItems.HONEY_CHICKEN.get());
                        output.accept(ModItems.HORSERADISH_BEEF_STEW.get());
                        output.accept(ModItems.LEMON_CAKE.get());
                        output.accept(ModItems.LORD_MANDLEY_PIE.get());
                        output.accept(ModItems.PITA_BREAD_WITH_HUMMUS.get());
                        output.accept(ModItems.ROAST_SUCKLING_PIG.get());
                        output.accept(ModItems.STEAK_AND_KIDNEY_PIE.get());
                        output.accept(ModItems.SWEET_COOKIE.get());
                        output.accept(ModItems.VEGETABLE_SALAD.get());
                        output.accept(ModItems.VENISON_PIE.get());
                        output.accept(ModItems.COOKED_FROG_LEG.get());
                        output.accept(ModItems.COOKED_TROUT.get());
                        output.accept(ModItems.COOKED_ELK_MEAT.get());
                        output.accept(ModItems.COOKED_VENISON.get());
                        // Primitives items
                        output.accept(ModItems.BARLEY.get());
                        output.accept(ModItems.OATS.get());
                        output.accept(ModItems.NETTLE_LEAVES.get());
                        output.accept(ModItems.LEEK_LEAVES.get());
                        output.accept(ModItems.HORSERADISH.get());
                        output.accept(ModItems.GREEN_PEPPER.get());
                        output.accept(ModItems.ONION.get());
                        output.accept(ModItems.CHICKPEA.get());
                        output.accept(ModItems.WHITE_BEANS.get());
                        output.accept(ModItems.LEMON.get());
                        output.accept(ModItems.PLUM.get());
                        output.accept(ModItems.BEEF_KIDNEY.get());
                        output.accept(ModItems.FROG_LEG.get());
                        output.accept(ModItems.OXTAIL.get());
                        output.accept(ModItems.RAW_TROUT.get());
                        output.accept(ModItems.RAW_ELK_MEAT.get());
                        output.accept(ModItems.RAW_VENISON.get());
                        // Tableware items
                        output.accept(ModItems.LARGE_WOODEN_BOWL.get());
                        output.accept(ModItems.WOODEN_BOWL.get());
                        output.accept(ModItems.WHITE_PORCELAIN_DISH.get());
                    }).build());
    public static final Supplier<CreativeModeTab> EPIC_MEDIAEVAL_WINE_AND_SAUCE = CREATIVE_TABS.register("epic_mediaeval_wine_and_sauce", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.epic_mediaeval_wine_and_sauce"))
                    .icon(() -> new ItemStack(ModItems.SWEET_PLUM_WINE.get())).displayItems((parameters, output) -> {
                        // Wine items
                        output.accept(ModItems.BARLEY_WINE.get());
                        output.accept(ModItems.LEMON_JUICE_WINE.get());
                        output.accept(ModItems.SWEET_PLUM_WINE.get());
                        output.accept(ModItems.FROG_WINE.get());
                        // Pickles items
                        output.accept(ModItems.CHEESE.get());
                        output.accept(ModItems.DOUGH.get());
                        // Tableware items
                        output.accept(ModItems.WINE_BOTTLE.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}
