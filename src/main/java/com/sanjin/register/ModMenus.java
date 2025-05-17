package com.sanjin.register;

import com.sanjin.EpicMediaeval;
import com.sanjin.menu.FermentationBarrelMenu;
import com.sanjin.menu.StewStoveMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU,EpicMediaeval.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<StewStoveMenu>> STEW_STOVE_MENU = MENUS.register("stew_stove_menu", () ->
            new MenuType<>(StewStoveMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<FermentationBarrelMenu>> FERMENTATION_BARREL_MENU = MENUS.register("fermentation_barrel_menu", () ->
            new MenuType<>(FermentationBarrelMenu::new,FeatureFlags.DEFAULT_FLAGS));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
