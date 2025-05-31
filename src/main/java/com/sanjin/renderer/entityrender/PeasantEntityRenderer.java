package com.sanjin.renderer.entityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sanjin.EpicMediaeval;
import com.sanjin.entity.AbstractPeasantEntity;
import com.sanjin.model.PeasantModel;
import com.sanjin.renderer.renderstate.PeasantRenderState;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class PeasantEntityRenderer extends LivingEntityRenderer<AbstractPeasantEntity, PeasantRenderState, PeasantModel> {

    private final PeasantModel normalModel;
    private final PeasantModel slimModel;

    public PeasantEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PeasantModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);

        this.normalModel = new PeasantModel(context.bakeLayer(ModelLayers.PLAYER), false);
        this.slimModel = new PeasantModel(context.bakeLayer(ModelLayers.PLAYER_SLIM), true);

        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new PeasantModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR), false),
                new PeasantModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR), false),
                new EquipmentLayerRenderer(
                        context.getEquipmentModels(),
                        context.getModelManager().getAtlas(ResourceLocation.parse("textures/atlas/armor_trims.png")))
                )
        );
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PeasantRenderState renderState) {
        String texturePath = renderState.getTexturePath();
        if (texturePath != null && !texturePath.isEmpty()) {
            if (!texturePath.startsWith("minecraft:") && !texturePath.contains(":")) {
                return ResourceLocation.fromNamespaceAndPath("minecraft", texturePath);
            }
            return ResourceLocation.parse(texturePath);
        }
        return DefaultPlayerSkin.getDefaultTexture();
    }

    @Override
    public void extractRenderState(@NotNull AbstractPeasantEntity entity, @NotNull PeasantRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);

        renderState.setSlimModel(entity.isSlimModel());
        renderState.setTexturePath(entity.getTextureLocation());

        if (entity.isSlimModel()) {
            this.model = this.slimModel;
        } else {
            this.model = this.normalModel;
        }

        renderState.showHat = true;
        renderState.showJacket = true;
        renderState.showLeftPants = true;
        renderState.showRightPants = true;
        renderState.showLeftSleeve = true;
        renderState.showRightSleeve = true;

        renderState.leftHandItem = entity.getItemBySlot(EquipmentSlot.OFFHAND);
        renderState.rightHandItem = entity.getItemBySlot(EquipmentSlot.MAINHAND);

        renderState.helmet = entity.getItemBySlot(EquipmentSlot.HEAD);
        renderState.chestplate = entity.getItemBySlot(EquipmentSlot.CHEST);
        renderState.leggings = entity.getItemBySlot(EquipmentSlot.LEGS);
        renderState.boots = entity.getItemBySlot(EquipmentSlot.FEET);
    }

    @Override
    protected void scale(@NotNull PeasantRenderState renderState, @NotNull PoseStack poseStack) {
        float scale = 0.9375F;
        poseStack.scale(scale, scale, scale);
    }

    @Override
    public @NotNull PeasantRenderState createRenderState() {
        return new PeasantRenderState();
    }
}
