package net.satisfy.farm_and_charm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.satisfy.farm_and_charm.client.model.CartModel;
import net.satisfy.farm_and_charm.entity.CartEntity;
import net.satisfy.farm_and_charm.util.FarmAndCharmIdentifier;
import org.jetbrains.annotations.NotNull;

public class CartRenderer extends EntityRenderer<CartEntity> {
    private static final ResourceLocation CART_TEXTURE = new FarmAndCharmIdentifier("textures/entity/supply_cart.png");
    private final CartModel<CartEntity> model;

    public CartRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CartModel<>(context.bakeLayer(CartModel.LAYER_LOCATION));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(CartEntity entity) {
        return CART_TEXTURE;
    }

    @Override
    public void render(CartEntity cart, float yaw, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int light) {
        
        if (cart.hasDriver() & cart.getDriver() != null) {

            // todo: remove hacky fix
            Vec3 lookAtVec = cart.getDriver().position().subtract(cart.position()).normalize();
            double yRot = Math.atan2(-lookAtVec.x, lookAtVec.z);
            double xRot = Math.atan2(-lookAtVec.y, Math.sqrt(lookAtVec.x * lookAtVec.x + lookAtVec.z * lookAtVec.z));
            cart.setYRot((float) Math.toDegrees(yRot));
            cart.setXRot((float) Math.toDegrees(xRot));

            Vec3 lastMoveVec = cart.position().subtract(cart.lastDriverX, cart.lastDriverY, cart.lastDriverZ).scale(0.5D);
            Vec3 driverMoveVec = cart.getDriver().position().subtract(cart.lastDriverX, cart.lastDriverY, cart.lastDriverZ).reverse().scale(0.5D);
            Vec3 newPosVec = driverMoveVec.add(lastMoveVec).normalize().scale(cart.holdOffset());
            Vec3 desiredPos = cart.getDriver().position().add(newPosVec);
            Vec3 movVec = desiredPos.subtract(cart.position());

            if (cart.getDeltaMovement().length() + movVec.scale(0.2D).length() < movVec.length()) {
                cart.setDeltaMovement(cart.getDeltaMovement().add(movVec).scale(0.2D));
            }
        }

        // todo: remove hacky fix
        if (!cart.isNoGravity()) {
            cart.setDeltaMovement(cart.getDeltaMovement().add(0.0, -0.08, 0.0));
        }

        // cart.move(MoverType.SELF, cart.getDeltaMovement());

        super.render(cart, yaw, g, poseStack, multiBufferSource, light);
        poseStack.pushPose();
        poseStack.translate(0.0D, 1.4D, 0.0D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        this.model.setupAnim(cart, 0.0F, 0.0F, cart.level().getGameTime(), yaw, cart.getXRot());
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(CART_TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }
}