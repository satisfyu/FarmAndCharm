package net.satisfy.farm_and_charm._jason.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.satisfy.farm_and_charm.FarmAndCharm;
import net.satisfy.farm_and_charm.entity.DrivableEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractTowableEntity extends Entity {

    public static final String DRIVER = "Driver";

    public @Nullable Entity driver;
    public double lastDriverX, lastDriverY, lastDriverZ;

    public AbstractTowableEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.2f);
    }

    public boolean hasDriver() {
        return this.driver != null;
    }

    public final @Nullable Entity getDriver() {
        return this.driver;
    }

    protected void removeDriver() {
        this.driver = null;
    }

    public boolean canAddDriver() {
        return !this.hasDriver();
    }

    public boolean addDriver(Entity entity) {
        if (entity instanceof Player) {
            List<Entity> entities = this.level().getEntitiesOfClass(Entity.class, entity.getBoundingBox().inflate(100)); // angenommene Methode mit Bereich
            for (Entity ent : entities) {
                if (ent instanceof DrivableEntity drivable) {
                    if (drivable.hasDriver()) {
                        assert drivable.getDriver() != null;
                        if (drivable.getDriver().equals(entity)) {
                            return false;
                        }
                    }
                }
            }
        }

        if (!this.hasDriver() && this.canAddDriver()) {
            this.driver = entity;
            return true;
        }
        return false;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isRemoved();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (this.hasDriver()) {
            assert this.getDriver() != null;
            compoundTag.putInt(DRIVER, this.getDriver().getId());
        }
    }

    @Override @SuppressWarnings("all")
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains(DRIVER)) {
            this.driver = this.level().getEntity(compoundTag.getInt(DRIVER));
        }
    }

    @Override @SuppressWarnings("all")
    public @NotNull InteractionResult interact(Player player, InteractionHand interactionHand) {
        if (this.hasDriver()) {
            this.removeDriver();
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WOOD_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            boolean added = this.addDriver(player);
            if (added) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WOOD_FALL, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private void setupMovement() {
        if (this.hasDriver() && this.getDriver() != null) {
            Vec3 lastMoveVec = this.position().subtract(this.lastDriverX, this.lastDriverY, this.lastDriverZ).scale(0.5D);
            Vec3 driverMoveVec = this.getDriver().position().subtract(this.lastDriverX, this.lastDriverY, this.lastDriverZ).reverse().scale(0.5D);
            Vec3 newPosVec = driverMoveVec.add(lastMoveVec).normalize().scale(2.0D);
            Vec3 desiredPos = this.getDriver().position().add(newPosVec);
            Vec3 movVec = desiredPos.subtract(this.position());
            if (this.getDeltaMovement().length() + movVec.scale(0.2D).length() < movVec.length()) {
                this.setDeltaMovement(this.getDeltaMovement().add(movVec).scale(0.2D));
            }
        }
    }

    public Vec3 getRelativeTargetVec(final float delta) {
        final double x;
        final double y;
        final double z;
        if (delta == 1.0F) {
            x = this.driver.getX() - this.getX();
            y = this.driver.getY() - this.getY();
            z = this.driver.getZ() - this.getZ();
        } else {
            x = Mth.lerp(delta, this.driver.xOld, this.driver.getX()) - Mth.lerp(delta, this.xOld, this.getX());
            y = Mth.lerp(delta, this.driver.yOld, this.driver.getY()) - Mth.lerp(delta, this.yOld, this.getY());
            z = Mth.lerp(delta, this.driver.zOld, this.driver.getZ()) - Mth.lerp(delta, this.zOld, this.getZ());
        }
        final float yaw = (float) Math.toRadians(this.driver.getYRot());
        final float nx = -Mth.sin(yaw);
        final float nz = Mth.cos(yaw);
        final double r = 0.2D;
        return new Vec3(x + nx * r, y, z + nz * r);
    }

    /**
     * Handles the rotation of this cart and its components.
     *
     */
    public void handleRotation(final Vec3 target) {
        this.setYRot(getYaw(target));
        this.setXRot(getPitch(target));
    }

    public static float getYaw(final Vec3 vec) {
        return Mth.wrapDegrees((float) Math.toDegrees(-Mth.atan2(vec.x, vec.z)));
    }

    public static float getPitch(final Vec3 vec) {
        return Mth.wrapDegrees((float) Math.toDegrees(-Mth.atan2(vec.y, Mth.sqrt((float) (vec.x * vec.x + vec.z * vec.z)))));
    }

    public void pulledTick() {
        if (this.driver == null) {
            return;
        }
        Vec3 targetVec = this.getRelativeTargetVec(1.0F);
        this.handleRotation(targetVec);
        while (this.getYRot() - this.yRotO < -180.0F) {
            this.yRotO -= 360.0F;
        }
        while (this.getYRot() - this.yRotO >= 180.0F) {
            this.yRotO += 360.0F;
        }
        if (this.driver.onGround()) {
            targetVec = new Vec3(targetVec.x, 0.0D, targetVec.z);
        }
        final double targetVecLength = targetVec.length();
        final double r = 0.2D;
        final double relativeSpacing = Math.max(2.0D + 0.5D, 1.0D);
        final double diff = targetVecLength - relativeSpacing;
        final Vec3 move;
        if (Math.abs(diff) < r) {
            move = this.getDeltaMovement();
        } else {
            move = this.getDeltaMovement().add(targetVec.subtract(targetVec.normalize().scale(relativeSpacing + r * Math.signum(diff))));
        }
        this.setOnGround(true);
        final double startX = this.getX();
        final double startY = this.getY();
        final double startZ = this.getZ();
        this.move(MoverType.SELF, move);
        if (!this.isAlive()) {
            return;
        }
        this.addStats(this.getX() - startX, this.getY() - startY, this.getZ() - startZ);
        if (this.level().isClientSide) {
//            for (final CartWheel wheel : this.wheels) {
//                wheel.tick();
//            }
        } else {
            targetVec = this.getRelativeTargetVec(1.0F);
            if (targetVec.length() > relativeSpacing + 1.0D) {
                this.driver = null;
            }
        }
        this.updatePassengers();
//        if (this.drawn != null) {
//            this.drawn.pulledTick();
//        }
    }

    public void updatePassengers() {
        for (final Entity passenger : this.getPassengers()) {
            this.positionRider(passenger);
        }
    }

    public static final ResourceLocation CART_ONE_CM = new ResourceLocation(FarmAndCharm.MOD_ID, "cart_one_cm");

    private void addStats(final double x, final double y, final double z) {
        if (!this.level().isClientSide) {
            final int cm = Math.round(Mth.sqrt((float) (x * x + y * y + z * z)) * 100.0F);
            if (cm > 0) {
                for (final Entity passenger : this.getPassengers()) {
                    if (passenger instanceof Player player) {
                        player.awardStat(CART_ONE_CM, cm);
                    }
                }
            }
        }
    }

    @Override
    public void tick() {

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.08, 0.0));
        }

        super.tick();


        if (this.driver != null) {
            this.pulledTick();
        }

//        Vec3 currentPos = this.position();
//
//        this.lastDriverX = currentPos.x;
//        this.lastDriverY = currentPos.y;
//        this.lastDriverZ = currentPos.z;
//
//        this.setupMovement();

        this.move(MoverType.SELF, this.getDeltaMovement());
    }
}
