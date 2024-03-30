package satisfy.farm_and_charm.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import satisfy.farm_and_charm.Farm_And_Charm;

public class MincerModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Farm_And_Charm.MOD_ID, "mincer"), "main");
    private final ModelPart mincer;
    private final ModelPart crank;

    public MincerModel(ModelPart root) {
        this.mincer = root.getChild("mincer");
        this.crank = root.getChild("crank");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition mincer = partdefinition.addOrReplaceChild("mincer", CubeListBuilder.create().texOffs(0, 8).addBox(-7.0F, -24.0F, 11.0F, 12.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-1.0F, -23.0F, 13.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-1.0F, -11.0F, 13.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 4).addBox(-7.0F, -17.0F, 13.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-2.0F, -18.0F, 12.0F, 7.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition crank = partdefinition.addOrReplaceChild("crank", CubeListBuilder.create().texOffs(0, 0).addBox(13.0F, -7.0F, 7.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(14.0F, -7.0F, 7.0F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(14.0F, -7.0F, 14.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 48, 48);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        mincer.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        crank.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

}