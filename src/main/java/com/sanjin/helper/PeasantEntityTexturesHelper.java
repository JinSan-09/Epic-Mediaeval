package com.sanjin.helper;

import com.sanjin.EpicMediaeval;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = EpicMediaeval.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class PeasantEntityTexturesHelper {

    private static final Map<String, List<String>> textureCache = new ConcurrentHashMap<>();
    private static final String TEXTURE_BASE_PATH = "textures/entity";
    private static boolean initialized = false;

    @SubscribeEvent
    public static void onClientSetup(@NotNull FMLClientSetupEvent event) {
        event.enqueueWork(PeasantEntityTexturesHelper::loadTextures);
    }

    public static void loadTextures() {
        if (initialized) return;

        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        textureCache.clear();

        List<String> maleTextures = scanTextures(resourceManager, "male_peasant");
        textureCache.put("male", maleTextures);

        List<String> femaleTextures = scanTextures(resourceManager, "female_peasant");
        textureCache.put("female", femaleTextures);

        initialized = true;
    }
    private static @NotNull List<String> scanTextures(@NotNull ResourceManager resourceManager, String prefix) {
        List<String> textures = new ArrayList<>();

        Map<ResourceLocation, ?> resources = resourceManager.listResources(
                TEXTURE_BASE_PATH,
                location -> {
                    String path = location.getPath();
                    String fileName = path.substring(path.lastIndexOf('/') + 1);
                    return fileName.startsWith(prefix) && fileName.endsWith(".png");
                }
        );

        for (ResourceLocation location : resources.keySet()) {
            if (location.getNamespace().equals(EpicMediaeval.MODID)) {
                String path = location.getPath();
                textures.add(path);
                EpicMediaeval.LOGGER.debug("发现纹理: {}", path);
            }
        }

        textures.sort(String::compareTo);
        return textures;
    }

    public static String[] getMaleTextures() {
        if (!initialized) {
            loadTextures();
        }

        List<String> textures = textureCache.get("male");
        if (textures == null || textures.isEmpty()) {
            return getDefaultMaleTextures();
        }
        return textures.toArray(new String[0]);
    }

    public static String[] getFemaleTextures() {
        if (!initialized) {
            loadTextures();
        }

        List<String> textures = textureCache.get("female");
        if (textures == null || textures.isEmpty()) {
            return getDefaultFemaleTextures();
        }
        return textures.toArray(new String[0]);
    }

    @Contract(value = " -> new", pure = true)
    private static String @NotNull [] getDefaultMaleTextures() {
        return new String[]{"textures/entity/male_peasant_1.png"};
    }

    @Contract(value = " -> new", pure = true)
    private static String @NotNull [] getDefaultFemaleTextures() {
        return new String[]{"textures/entity/female_peasant_1.png"};
    }
}
