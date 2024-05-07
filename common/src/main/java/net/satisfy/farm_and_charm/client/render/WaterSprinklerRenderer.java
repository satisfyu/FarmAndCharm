package net.satisfy.farm_and_charm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.satisfy.farm_and_charm.FarmAndCharm;
import net.satisfy.farm_and_charm.block.entity.WaterSprinklerBlockEntity;
import net.satisfy.farm_and_charm.client.model.WaterSprinklerModel;
import org.joml.Quaternionf;

import java.util.Objects;

public class WaterSprinklerRenderer implements BlockEntityRenderer<WaterSprinklerBlockEntity> {
    private final ModelPart rotating;
    private final ModelPart basin;

    private static final ResourceLocation TEXTURE = new ResourceLocation(FarmAndCharm.MOD_ID, "textures/entity/water_sprinkler.png");

    public WaterSprinklerRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(WaterSprinklerModel.LAYER_LOCATION);

        this.rotating = root.getChild("rotating");
        this.basin = root.getChild("basin");
    }

    @Override
    public void render(WaterSprinklerBlockEntity blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(TEXTURE));

        matrixStack.pushPose();

        final var angle = getAngle(blockEntity, partialTicks);

        matrixStack.translate(0.5, 0, 0.5);
        matrixStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(-angle)));
        matrixStack.translate(-0.5, 0, -0.5);

        rotating.render(matrixStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);

        matrixStack.popPose();

        basin.render(matrixStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
    }

    private static float getAngle(WaterSprinklerBlockEntity blockEntity, float partialTicks) {
        Level level = Objects.requireNonNull(blockEntity.getLevel());
        long gameTime = level.getGameTime();
        boolean isRaining = level.isRaining();
        boolean isThundering = level.isThundering();
        float angle;
        if (isRaining || isThundering) {
            angle = (float) Math.sin(gameTime * 0.03) * 7.0F;
        } else {
            float speedMultiplier = 5.0F;
            angle = (gameTime + partialTicks) * speedMultiplier % 360;
        }
        return angle;
    }
}
