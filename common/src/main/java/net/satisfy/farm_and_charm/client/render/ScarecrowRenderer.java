package net.satisfy.farm_and_charm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.satisfy.farm_and_charm.FarmAndCharm;
import net.satisfy.farm_and_charm.block.ScarecrowBlock;
import net.satisfy.farm_and_charm.block.entity.ScarecrowBlockEntity;
import net.satisfy.farm_and_charm.client.model.ScarecrowModel;
import org.joml.Quaternionf;
public class ScarecrowRenderer implements BlockEntityRenderer<ScarecrowBlockEntity> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(FarmAndCharm.MOD_ID, "textures/entity/scarecrow.png");
    private final ModelPart scarecrow;
    private final ModelPart post;

    public ScarecrowRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(ScarecrowModel.LAYER_LOCATION);
        this.scarecrow = root.getChild("scarecrow");
        this.post = root.getChild("post");
    }

    @Override
    public void render(ScarecrowBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        Direction direction = blockEntity.getBlockState().getValue(ScarecrowBlock.FACING);
        float rotationDegrees = -direction.toYRot() + 180;

        if (blockEntity.getLevel() != null && blockEntity.getLevel().isLoaded(blockEntity.getBlockPos())) {
            long gameTime = blockEntity.getLevel().getGameTime();
            boolean isStormy = blockEntity.getLevel().isThundering() || blockEntity.getLevel().isRaining();
            float speedMultiplier = isStormy ? 0.1f : 0.05f;
            float angleDegrees = isStormy ? 2.0f : Mth.sin((gameTime + partialTicks) * speedMultiplier) * 1;

            poseStack.pushPose();
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(rotationDegrees)));
            poseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(angleDegrees)));
            poseStack.translate(-0.5, 0, -0.5);

            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            scarecrow.render(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            post.render(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            poseStack.popPose();
        }
    }
}
