package com.sanjin;

import com.sanjin.entity.mobentity.FemalePeasantEntity;
import com.sanjin.entity.mobentity.MalePeasantEntity;
import com.sanjin.event.FrogWineEventHandler;
import com.sanjin.event.StormWineEventHandler;
import com.sanjin.network.NetworkHandler;
import com.sanjin.register.*;
import com.sanjin.renderer.entityrender.PeasantEntityRenderer;
import com.sanjin.renderer.itemrender.OnionProjectileRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.NotNull;

@Mod(EpicMediaeval.MODID)
public class EpicMediaeval {

    public static final String MODID = "epicmediaeval";
    public EpicMediaeval(@NotNull IEventBus modEventBus) {
        modEventBus.addListener(this::registerNetworkHandlers);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModComponents.register(modEventBus);
        ModRecipeDisplays.register(modEventBus);
        ModRecipeBookCategories.register(modEventBus);
        ModItemEntities.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMobEntities.register(modEventBus);

        ModMenus.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        ModCreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(FrogWineEventHandler.class);
        NeoForge.EVENT_BUS.register(StormWineEventHandler.class);

    }

    private void registerNetworkHandlers(RegisterPayloadHandlersEvent event) {
        NetworkHandler.register(event);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        private static void onRegisterScreens(RegisterMenuScreensEvent event) {
            ModScreens.register(event);
        }
        @SubscribeEvent
        public static void onRegisterEntityRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
            event.registerEntityRenderer(ModMobEntities.FEMALE_PEASANT_ENTITY.get(), PeasantEntityRenderer::new);
            event.registerEntityRenderer(ModMobEntities.MALE_PEASANT_ENTITY.get(), PeasantEntityRenderer::new);
            event.registerEntityRenderer(ModItemEntities.ONION_ENTITY.get(), OnionProjectileRenderer::new);
        }
        @SubscribeEvent
        public static void registerSearchCategories(@NotNull RegisterRecipeBookSearchCategoriesEvent event) {
            event.register(
                    ModRecipeBookCategories.STEWS_SEARCH_CATEGORY,
                    ModRecipeBookCategories.STEW_STOVE_STEWS.get()
            );
            event.register(
                    ModRecipeBookCategories.SOUP_SEARCH_CATEGORY,
                    ModRecipeBookCategories.STEW_STOVE_SOUP.get()
            );
            event.register(
                    ModRecipeBookCategories.STEW_STOVE_MISC_SEARCH_CATEGORY,
                    ModRecipeBookCategories.STEW_STOVE_MISC.get()
            );
            event.register(
                    ModRecipeBookCategories.WINS_SEARCH_CATEGORY,
                    ModRecipeBookCategories.FERMENTATION_BARREL_WINS.get()
            );
            event.register(
                    ModRecipeBookCategories.PICKLES_SEARCH_CATEGORY,
                    ModRecipeBookCategories.FERMENTATION_BARREL_PICKLES.get()
            );
            event.register(
                    ModRecipeBookCategories.FERMENTATION_BARREL_MISC_SEARCH_CATEGORY,
                    ModRecipeBookCategories.FERMENTATION_BARREL_MISC.get()
            );
        }
        @SubscribeEvent
        public static void registerEntityAttributes(@NotNull EntityAttributeCreationEvent event) {
            event.put(ModMobEntities.FEMALE_PEASANT_ENTITY.get(), FemalePeasantEntity.createAttributes().build());
            event.put(ModMobEntities.MALE_PEASANT_ENTITY.get(), MalePeasantEntity.createAttributes().build());
        }
    }
}
