package com.sanjin;

import com.sanjin.entity.mobentity.FemalePeasantEntity;
import com.sanjin.entity.mobentity.MalePeasantEntity;
import com.sanjin.event.FrogWineEventHandler;
import com.sanjin.event.PeasantSpawnHandler;
import com.sanjin.event.StormWineEventHandler;
import com.sanjin.register.*;
import com.sanjin.renderer.entityrender.PeasantEntityRenderer;
import com.sanjin.renderer.itemrender.OnionProjectileRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRecipeBookSearchCategoriesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(EpicMediaeval.MODID)
public class EpicMediaeval {

    public static final String MODID = "epicmediaeval";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EpicMediaeval(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

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

        ModCreativeTabs.register(modEventBus);

        NeoForge.EVENT_BUS.register(FrogWineEventHandler.class);
        NeoForge.EVENT_BUS.register(StormWineEventHandler.class);
        NeoForge.EVENT_BUS.addListener(PeasantSpawnHandler::onEntityJoinWorld);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
         LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));


    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        private static void onRegisterScreens(RegisterMenuScreensEvent event) {
            ModScreens.register(event);
        }
        @SubscribeEvent
        public static void onRegisterEntityRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
            event.registerEntityRenderer(ModMobEntities.FEMALE_PEASANT_ENTITY.get(), PeasantEntityRenderer::new);
            event.registerEntityRenderer(ModMobEntities.MALE_PEASANT_ENTITY.get(), PeasantEntityRenderer::new);
        }
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
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
