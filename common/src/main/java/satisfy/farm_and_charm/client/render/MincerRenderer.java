package satisfy.farm_and_charm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.block.MincerBlock;
import satisfy.farm_and_charm.client.model.MincerModel;
import satisfy.farm_and_charm.entity.MincerBlockEntity;

public class MincerRenderer implements BlockEntityRenderer<MincerBlockEntity> {
    private final ModelPart mincer;
    private final ModelPart crank;

    private static final ResourceLocation TEXTURE = new ResourceLocation(Farm_And_Charm.MOD_ID, "textures/entity/mincer.png");

    public MincerRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart root = context.bakeLayer(MincerModel.LAYER_LOCATION);

        this.mincer = root.getChild("mincer");
        this.crank = root.getChild("crank");
    }

    @Override
    public void render(MincerBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {

        Level level = blockEntity.getLevel();
        assert level != null;
        BlockState blockState = level.getBlockState(blockEntity.getBlockPos());
        if (!(blockState.getBlock() instanceof MincerBlock)) return;

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.translate(0.5f, -1.5f, -0.5f);




        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

        mincer.render(poseStack, vertexConsumer, i, j);
        if (level.getBlockState(blockEntity.getBlockPos()).getValue(MincerBlock.STIRRING) > 0) poseStack.mulPose (Axis.XP.rotation(((float) (System.currentTimeMillis() % 100000) / 100f)% 360));
        crank.render(poseStack, vertexConsumer, i, j);


        poseStack.popPose();


    }
}
