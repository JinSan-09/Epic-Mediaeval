package com.sanjin.register;

import com.sanjin.screen.FermentationBarrelScreen;
import com.sanjin.screen.StewStoveScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ModScreens {
    public static void register(RegisterMenuScreensEvent event) {
        event.register(ModMenus.STEW_STOVE_MENU.get(), StewStoveScreen::new);
        event.register(ModMenus.FERMENTATION_BARREL_MENU.get(), FermentationBarrelScreen::new);
    }
}
