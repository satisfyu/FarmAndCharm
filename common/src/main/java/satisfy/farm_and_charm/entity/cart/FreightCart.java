package satisfy.farm_and_charm.entity.cart;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FreightCart extends CartEntity {
    public FreightCart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand interactionHand) {
        if (player.isSecondaryUseActive()) {
            return super.interact(player, interactionHand);
        } else {
            if (this.canAddPassenger(player)) {
                if (!this.level().isClientSide()) {
                    return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
                } else {
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel) {
            List<Mob> list = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(0.20000000298023224, -0.009999999776482582, 0.20000000298023224));
            for (Mob entity : list) {
                entity.startRiding(this);
                break;
            }
        }
    }

    // CART
    @Override
    protected float firstPoint() {
        return 3.0F;
    }

    @Override
    protected float lastPoint() {
        return 1.0F;
    }

    @Override
    protected float wheelRadius() {
        return 1.0F;
    }

    @Override
    public float balance() {
        double maxFrontX = Math.sqrt(this.firstPoint() * this.firstPoint() - this.wheelYOffset() * this.wheelYOffset());
        double maxFrontSlope = Math.atan2(-this.wheelYOffset(), maxFrontX);

        double maxBackX = Math.sqrt(this.lastPoint() * this.lastPoint() - this.wheelYOffset() * this.wheelYOffset());
        double maxBackSlope = Math.atan2(this.wheelYOffset(), maxBackX);
        if (!this.hasDriver()) {
            if (this.getPassengers().isEmpty()) {
                return (float) maxFrontSlope;
            }
            return (float) Mth.lerp((float) this.getPassengers().size() / this.getMaxPassengers(), maxFrontSlope, maxBackSlope);
        }
        double desiredXRot = Math.toRadians(-this.getXRot());
        return this.onGround() ? (float) Math.max(Math.min(desiredXRot, maxBackSlope), maxFrontSlope) : (float) desiredXRot;
    }

    // PASSENGER
    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() * 0.5D;
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return this.getPassengers().size() < this.getMaxPassengers() && super.canAddPassenger(entity);
    }

    protected int getMaxPassengers() {
        return 2;
    }
}
