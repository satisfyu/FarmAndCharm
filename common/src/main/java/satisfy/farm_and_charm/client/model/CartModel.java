package satisfy.farm_and_charm.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.entity.cart.CartEntity;

public class CartModel<T extends CartEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new Farm_And_CharmIdentifier("cart"), "main");
    private final ModelPart cart;
    private final ModelPart right_wheel;
    private final ModelPart left_wheel;

    public CartModel(ModelPart root) {
        this.cart = root.getChild("cart");
        this.right_wheel = root.getChild("right_wheel");
        this.left_wheel = root.getChild("left_wheel");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition cart = partdefinition.addOrReplaceChild("cart", CubeListBuilder.create().texOffs(88, 0).addBox(5.0F, -1.5F, 24.9167F, 3.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(88, 0).addBox(-8.0F, -1.5F, 24.9167F, 3.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-12.0F, -10.5F, -15.0833F, 3.0F, 9.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).mirror().addBox(9.0F, -10.5F, -15.0833F, 3.0F, 9.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(88, 21).addBox(-9.0F, -10.5F, 13.9167F, 18.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-12.0F, -1.5F, -15.0833F, 24.0F, 3.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.5F, -4.9167F));

        PartDefinition right_wheel = partdefinition.addOrReplaceChild("right_wheel", CubeListBuilder.create().texOffs(38, 43).addBox(-26.0F, 1.0F, -1.0F, 24.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(76, 43).addBox(-2.0F, -8.0F, -8.0F, 3.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(14.0F, 16.0F, -5.0F));

        PartDefinition left_wheel = partdefinition.addOrReplaceChild("left_wheel", CubeListBuilder.create().texOffs(76, 43).mirror().addBox(-1.0F, -8.0F, -8.0F, 3.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-14.0F, 16.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.cart.xRot = entity.balance();

        this.right_wheel.xRot = entity.wheelRot();
        this.left_wheel.xRot = entity.wheelRot();
		/*
		if (entity.onGround()) {
			this.rollOut = 1;
		} else if (this.rollOut < 10){
			this.rollOut++;
		}
		 */
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.cart.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.right_wheel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.left_wheel.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}