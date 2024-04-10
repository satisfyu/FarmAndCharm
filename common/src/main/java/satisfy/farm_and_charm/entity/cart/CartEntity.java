package satisfy.farm_and_charm.entity.cart;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.registry.ObjectRegistry;

import java.util.List;

public abstract class CartEntity extends DrivableEntity {
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE;
    private float wheelRot;
    private int rollOut;
    private double lastDriverX, lastDriverY, lastDriverZ;

    protected CartEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(0.5F);
    }

    protected float wheelYOffset() {
        return this.wheelRadius() / 2.0F;
    }

    protected abstract float firstPoint();

    protected abstract float lastPoint();

    protected abstract float wheelRadius();

    protected float holdOffset() {
        return 2.0F;
    }

    @Override
    public @NotNull InteractionResult interact(Player player, InteractionHand interactionHand) {
        if (this.hasDriver()) {
            this.removeDriver();
            return InteractionResult.SUCCESS;
        }
        return this.addDriver(player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasDriver()) {
            assert this.getDriver() != null;
            if (10.0F < this.position().distanceTo(this.getDriver().position())) {
                this.removeDriver();
            }
        } else if (!this.hasDriver() && this.level() instanceof ServerLevel) { //TODO not every single tick
            List<Horse> list = this.level().getEntitiesOfClass(Horse.class, this.getBoundingBox().inflate(0.20000000298023224, -0.009999999776482582, 0.20000000298023224));
            for (Horse horse : list) {
                if (horse.isTamed()) {
                    this.addDriver(horse);
                    break;
                }
            }
        }

        this.setupMovement();
        this.setupWheels();
        this.setupRotation();

        if (this.hasDriver()) {
            assert this.getDriver() != null;
            this.lastDriverX = this.getDriver().getX();
            this.lastDriverY = this.getDriver().getY();
            this.lastDriverZ = this.getDriver().getZ();
        }
    }

    private void setupMovement() {
        if (this.hasDriver()) {
            Vec3 lastMoveVec = this.position().subtract(this.lastDriverX, this.lastDriverY, this.lastDriverZ).scale(0.5D); // oldDriverPos --> this
            assert this.getDriver() != null;
            Vec3 driverMoveVec = this.getDriver().position().subtract(this.lastDriverX, this.lastDriverY, this.lastDriverZ).reverse().scale(0.5D); // driver --> oldDriverPos
            Vec3 newPosVec = driverMoveVec.add(lastMoveVec).normalize().scale(this.holdOffset());
            Vec3 desiredPos = this.getDriver().position().add(newPosVec);
            Vec3 movVec = desiredPos.subtract(this.position());
            if (this.getDeltaMovement().length() + movVec.scale(0.2D).length() < movVec.length()) {
                this.setDeltaMovement(this.getDeltaMovement().add(movVec).scale(0.2D));
            }
        }

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.08, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    private void setupRotation() {
        if (this.hasDriver()) {
            assert this.getDriver() != null;
            Vec3 lookAtVec = this.getDriver().position().subtract(this.position()).normalize();
            double yRot = Math.atan2(-lookAtVec.x, lookAtVec.z);
            double xRot = Math.atan2(-lookAtVec.y, Math.sqrt(lookAtVec.x * lookAtVec.x + lookAtVec.z * lookAtVec.z));
            this.setYRot((float) Math.toDegrees(yRot));
            this.setXRot((float) Math.toDegrees(xRot));
        }
    }

    private void setupWheels() {
        Vec3 velocity = this.getDeltaMovement();
        float xzDist = (float) Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        if (0.01F < xzDist && 10 >= this.rollOut) {
            float anglePerTick = (xzDist / this.wheelRadius()) / ((float) this.rollOut);
            this.wheelRot -= anglePerTick;
            this.wheelRot %= (float) (Math.PI * 2);
        }

        if (!this.onGround()) {
            if (10 > this.rollOut) {
                this.rollOut++;
            }
        } else {
            this.rollOut = 1;
        }
    }

    public float wheelRot() {
        return this.wheelRot;
    }

    public float balance() {
        double maxFrontX = Math.sqrt(this.firstPoint() * this.firstPoint() - this.wheelYOffset() * this.wheelYOffset());
        double maxFrontSlope = Math.atan2(-this.wheelYOffset(), maxFrontX);
        if (!this.hasDriver()) {
            return (float) maxFrontSlope;
        }
        double maxBackX = Math.sqrt(this.lastPoint() * this.lastPoint() - this.wheelYOffset() * this.wheelYOffset());
        double maxBackSlope = Math.atan2(this.wheelYOffset(), maxBackX);

        double desiredXRot = Math.toRadians(-this.getXRot());
        return this.onGround() ? (float) Math.max(Math.min(desiredXRot, maxBackSlope), maxFrontSlope) : (float) desiredXRot;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (!this.level().isClientSide && !this.isRemoved()) {
            this.setDamage(this.getDamage() + f * 10.0F);
            this.markHurt();
            this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
            boolean bl = damageSource.getEntity() instanceof Player && ((Player) damageSource.getEntity()).getAbilities().instabuild;
            if (bl || 40.0F < this.getDamage()) {
                if (!bl && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    this.destroy(damageSource);
                }

                this.discard();
            }

            return true;
        } else {
            return true;
        }
    }

    protected void destroy(DamageSource damageSource) {
        this.spawnAtLocation(ObjectRegistry.SUPPLY_CART.get());
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }


    public void setDamage(float f) {
        this.entityData.set(DATA_ID_DAMAGE, f);
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
    }

    static {
        DATA_ID_DAMAGE = SynchedEntityData.defineId(CartEntity.class, EntityDataSerializers.FLOAT);
    }
}
