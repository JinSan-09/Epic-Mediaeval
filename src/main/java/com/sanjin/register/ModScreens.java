package com.sanjin.register;

import com.sanjin.screen.FermentationBarrelScreen;
import com.sanjin.screen.PeasantScreen;
import com.sanjin.screen.StewStoveScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.jetbrains.annotations.NotNull;

public class ModScreens {
    public static void register(@NotNull RegisterMenuScreensEvent event) {
        event.register(ModMenus.STEW_STOVE_MENU.get(), StewStoveScreen::new);
        event.register(ModMenus.FERMENTATION_BARREL_MENU.get(), FermentationBarrelScreen::new);
        event.register(ModMenus.PEASANT_MENU.get(), PeasantScreen::new);
    }
}
