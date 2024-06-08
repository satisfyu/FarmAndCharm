package net.satisfy.farm_and_charm._jason.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.farm_and_charm._jason.entity.JasonSupplyCartEntity;
import net.satisfy.farm_and_charm._jason.model.JasonSupplyCartModel;
import net.satisfy.farm_and_charm.client.model.CartModel;
import net.satisfy.farm_and_charm.client.render.CartRenderer;
import org.jetbrains.annotations.NotNull;

public class JasonSupplyCartRenderer extends EntityRenderer<JasonSupplyCartEntity> {

    private final JasonSupplyCartModel<JasonSupplyCartEntity> model;

    public JasonSupplyCartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new JasonSupplyCartModel<>(context.bakeLayer(CartModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(JasonSupplyCartEntity entity) {
        return CartRenderer.CART_TEXTURE;
    }

    @Override
    public void render(JasonSupplyCartEntity cart, float yaw, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        super.render(cart, yaw, g, poseStack, multiBufferSource, light);
        poseStack.pushPose();

        poseStack.translate(0.0D, 1.4D, 0.0D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));

        this.model.setupAnim(cart, 0.0F, 0.0F, cart.level().getGameTime(), yaw, cart.getXRot());
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(CartRenderer.CART_TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
