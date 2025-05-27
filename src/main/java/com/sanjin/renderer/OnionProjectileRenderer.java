package com.sanjin.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sanjin.entity.itemprojectile.OnionProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.state.ThrownItemRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnionProjectileRenderer extends ThrownItemRenderer<OnionProjectile> {

    private final ItemRenderer itemRenderer;
    private static final float MIN_SCALE = 0.8F;
    private static final float MAX_SCALE = 1.2F;

    public OnionProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.4F;
    }

    //ThrownItemRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
    public void render(OnionProjectile entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float scale = MIN_SCALE + (MAX_SCALE - MIN_SCALE) * (0.5F + 0.5F * Mth.sin(entity.tickCount * 0.3F));
        poseStack.scale(scale, scale, scale);

        poseStack.mulPose(Axis.YP.rotationDegrees((entity.tickCount + partialTicks) * 80.0F));

        float tiltX = Mth.sin((entity.tickCount + partialTicks) * 0.1F) * 15.0F;
        float tiltZ = Mth.cos((entity.tickCount + partialTicks) * 0.1F) * 15.0F;
        poseStack.mulPose(Axis.XP.rotationDegrees(tiltX));
        poseStack.mulPose(Axis.ZP.rotationDegrees(tiltZ));

        ItemStack itemstack = entity.getItem();
        BakedModel bakedModel = this.itemRenderer.getModel(itemstack, entity.level(), null, entity.getId());

        this.itemRenderer.render(
                itemstack,
                ItemDisplayContext.GROUND,
                false,
                poseStack,
                buffer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                bakedModel
        );

        poseStack.popPose();
    }

    @Override
    public @NotNull ThrownItemRenderState createRenderState() {
        return new ThrownItemRenderState();
    }
}
