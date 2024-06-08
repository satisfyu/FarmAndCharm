package net.satisfy.farm_and_charm._jason.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.farm_and_charm._jason.entity.JasonPlowCartEntity;
import net.satisfy.farm_and_charm._jason.model.JasonPlowCartModel;
import net.satisfy.farm_and_charm.client.model.PlowModel;
import net.satisfy.farm_and_charm.client.render.PlowRenderer;
import net.satisfy.farm_and_charm.entity.CartEntity;
import org.jetbrains.annotations.NotNull;

public class JasonPlowCartRenderer extends EntityRenderer<JasonPlowCartEntity> {

    private final JasonPlowCartModel<JasonPlowCartEntity> model;

    public JasonPlowCartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new JasonPlowCartModel<>(context.bakeLayer(PlowModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(JasonPlowCartEntity entity) {
        return PlowRenderer.CART_TEXTURE;
    }

    @Override @SuppressWarnings("all")
    public void render(JasonPlowCartEntity cart, float yaw, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        super.render(cart, yaw, g, poseStack, multiBufferSource, light);
        poseStack.pushPose();

        poseStack.translate(0.0D, 1.4D, 0.0D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));

        this.model.setupAnim(cart, 0.0F, 0.0F, cart.level().getGameTime(), yaw, cart.getXRot());
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(PlowRenderer.CART_TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
