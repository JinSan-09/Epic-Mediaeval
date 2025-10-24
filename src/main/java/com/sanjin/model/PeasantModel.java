package com.sanjin.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sanjin.renderer.renderstate.PeasantRenderState;
import net.minecraft.Util;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class PeasantModel extends HumanoidModel<PeasantRenderState> {

    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_pants";
    private static final String RIGHT_PANTS = "right_pants";
    private final List<ModelPart> bodyParts;
    public final ModelPart leftSleeve;
    public final ModelPart rightSleeve;
    public final ModelPart leftPants;
    public final ModelPart rightPants;
    public final ModelPart jacket;
    private final boolean slim;

    public PeasantModel(ModelPart root, boolean slim) {
        super(root);
        this.slim = slim;
        this.leftSleeve = getChildSafely(this.leftArm, "left_sleeve");
        this.rightSleeve = getChildSafely(this.rightArm, "right_sleeve");
        this.leftPants = getChildSafely(this.leftLeg, "left_pants");
        this.rightPants = getChildSafely(this.rightLeg, "right_pants");
        this.jacket = getChildSafely(this.body, "jacket");

        this.bodyParts = List.of(this.head, this.body, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
    }

    @Override
    public void setupAnim(@NotNull PeasantRenderState renderState) {
        boolean flag = true;

        this.body.visible = flag;
        this.rightArm.visible = flag;
        this.leftArm.visible = flag;
        this.rightLeg.visible = flag;
        this.leftLeg.visible = flag;
        this.hat.visible = renderState.showHat;
        if (this.jacket != null) {
            this.jacket.visible = renderState.showJacket;
        }
        if (this.leftPants != null) {
            this.leftPants.visible = renderState.showLeftPants;
        }
        if (this.rightPants != null) {
            this.rightPants.visible = renderState.showRightPants;
        }
        if (this.leftSleeve != null) {
            this.leftSleeve.visible = renderState.showLeftSleeve;
        }
        if (this.rightSleeve != null) {
            this.rightSleeve.visible = renderState.showRightSleeve;
        }
        super.setupAnim(renderState);
    }

    @Override
    public void setAllVisible(boolean visible) {
        super.setAllVisible(visible);
        this.leftSleeve.visible = visible;
        this.rightSleeve.visible = visible;
        this.leftPants.visible = visible;
        this.rightPants.visible = visible;
        this.jacket.visible = visible;
    }

    @Override
    public void translateToHand(@Nonnull HumanoidArm side, @Nonnull PoseStack poseStack) {
        this.root().translateAndRotate(poseStack);
        ModelPart modelpart = this.getArm(side);
        if (this.slim) {
            float f = 0.5F * (float)(side == HumanoidArm.RIGHT ? 1 : -1);
            modelpart.x += f;
            modelpart.translateAndRotate(poseStack);
            modelpart.x -= f;
        } else {
            modelpart.translateAndRotate(poseStack);
        }
    }

    @Override
    protected @NotNull ArmPose getArmPose(@Nonnull PeasantRenderState renderState, @Nonnull HumanoidArm arm) {
        if (arm == HumanoidArm.RIGHT && !renderState.rightHandItem.isEmpty()) {
            return ArmPose.ITEM;
        }
        if (arm == HumanoidArm.LEFT && !renderState.leftHandItem.isEmpty()) {
            return ArmPose.ITEM;
        }
        return ArmPose.EMPTY;
    }

    public boolean isSlim() {
        return this.slim;
    }

    public ModelPart getRandomBodyPart(RandomSource random) {
        return Util.getRandom(this.bodyParts, random);
    }

    private @NotNull ModelPart getChildSafely(@NotNull ModelPart parent, String childName) {
        if (parent.hasChild(childName)) {
            return parent.getChild(childName);
        } else {
            // 创建一个空的、不可见的ModelPart作为替代
            ModelPart emptyPart = new ModelPart(List.of(), Map.of());
            emptyPart.visible = false;
            return emptyPart;
        }
    }

}
