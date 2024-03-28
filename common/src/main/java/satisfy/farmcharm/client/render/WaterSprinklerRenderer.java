package satisfy.farmcharm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.client.model.WaterSprinklerModel;
import satisfy.farmcharm.entity.WaterSprinklerBlockEntity;

import java.util.Objects;

public class WaterSprinklerRenderer implements BlockEntityRenderer<WaterSprinklerBlockEntity> {
    private final ModelPart rotating;
    private final ModelPart basin;

    private static final ResourceLocation TEXTURE = new ResourceLocation(FarmCharm.MOD_ID, "textures/entity/water_sprinkler.png");

    public WaterSprinklerRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(WaterSprinklerModel.LAYER_LOCATION);

        this.rotating = root.getChild("rotating");
        this.basin = root.getChild("basin");
    }

    @Override
    public void render(WaterSprinklerBlockEntity blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(TEXTURE));

        matrixStack.pushPose();
        long gameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();
        float speedMultiplier = 5.0F;
        float angle = ((gameTime * speedMultiplier) % 360) + (partialTicks * speedMultiplier);
        matrixStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(angle)));
        rotating.render(matrixStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();

        basin.render(matrixStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
    }
}