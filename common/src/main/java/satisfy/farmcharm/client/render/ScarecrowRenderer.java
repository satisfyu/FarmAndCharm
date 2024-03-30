package satisfy.farmcharm.client.render;

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
import org.joml.Quaternionf;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.block.ScarecrowBlock;
import satisfy.farmcharm.client.model.ScarecrowModel;
import satisfy.farmcharm.entity.ScarecrowBlockEntity;

import java.util.Objects;

public class ScarecrowRenderer implements BlockEntityRenderer<ScarecrowBlockEntity> {
    private final ModelPart scarecrow;
    private final ModelPart post;

    private static final ResourceLocation TEXTURE = new ResourceLocation(FarmCharm.MOD_ID, "textures/entity/scarecrow.png");

    public ScarecrowRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(ScarecrowModel.LAYER_LOCATION);

        this.scarecrow = root.getChild("scarecrow");
        this.post = root.getChild("post");
    }

    @Override
    public void render(ScarecrowBlockEntity blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        Direction direction = blockEntity.getBlockState().getValue(ScarecrowBlock.FACING);
        float rotationY = (float) Math.toRadians(-direction.toYRot());

        matrixStack.pushPose();
        matrixStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(rotationY)));

        long gameTime = Objects.requireNonNull(blockEntity.getLevel()).getGameTime();
        boolean isStormy = blockEntity.getLevel().isThundering() || blockEntity.getLevel().isRaining();
        float speedMultiplier = isStormy ? 0.1f : 0.05f;
        float angleDegrees = Mth.sin((gameTime + partialTicks) * speedMultiplier);
        matrixStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(rotationY)));

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        scarecrow.render(matrixStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
        post.render(matrixStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
    }
}