package com.sanjin.event;

import com.sanjin.entity.AbstractPeasantEntity;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class PeasantSpawnHandler {
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof AbstractPeasantEntity peasant) {
            if (!event.getLevel().isClientSide() && peasant.getPeasantName().equals("Unknown")) {
                initializePeasant(peasant);
            }
        }
    }

    private static void initializePeasant(AbstractPeasantEntity peasant) {
        RandomSource random = peasant.getRandom();

        // 初始化随机数据
        String[] textures = peasant.getDefaultTexturePaths();
        if (textures.length > 0) {
            String texture = textures[random.nextInt(textures.length)];
            peasant.setTextureLocation(texture);
        }

        peasant.setSlimModel(peasant.isSlimDefault());

        // 生成随机名字
        String[] firstNames = peasant.getRandomFirstNameOptions();
        String[] lastNames = peasant.getRandomLastNameOptions();
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        peasant.setPeasantName(randomFirstName + " " + randomLastName);
    }
}
