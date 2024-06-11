package net.satisfy.farm_and_charm.entity;

import de.cristelknight.doapi.common.registry.DoApiSoundEventRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.satisfy.farm_and_charm.FarmAndCharm;
import net.satisfy.farm_and_charm.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public abstract class CartEntity extends DrivableEntity {
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE;

    static {
        DATA_ID_DAMAGE = SynchedEntityData.defineId(CartEntity.class, EntityDataSerializers.FLOAT);
    }

    private float originalYRot = 0.0F;
    private boolean shouldResetRot = false;
    private float wheelRot;
    private int rollOut;
    private int soundCooldown = 0;
    public double lastDriverX, lastDriverY, lastDriverZ;

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;

    protected CartEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.2F);
    }

    protected float wheelYOffset() {
        return this.wheelRadius() / 2.0F;
    }

    protected abstract float firstPoint();

    protected abstract float lastPoint();

    protected abstract float wheelRadius();

    public float holdOffset() {
        return 2.0F;
    }

    @Override
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

    @Override
    //Client
    public void lerpTo(final double x, final double y, final double z, final float yaw, final float pitch, final int posRotationIncrements, final boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = yaw;
        this.lerpPitch = pitch;
        this.lerpSteps = posRotationIncrements;
    }

    private void tickLerp() {
        if (this.lerpSteps > 0) {
            final double dx = (this.lerpX - this.getX()) / this.lerpSteps;
            final double dy = (this.lerpY - this.getY()) / this.lerpSteps;
            final double dz = (this.lerpZ - this.getZ()) / this.lerpSteps;
            this.setYRot((float) (this.getYRot() + Mth.wrapDegrees(this.lerpYaw - this.getYRot()) / this.lerpSteps));
            this.setXRot((float) (this.getXRot() + (this.lerpPitch - this.getXRot()) / this.lerpSteps));
            this.lerpSteps--;
            this.setOnGround(true);
            this.move(MoverType.SELF, new Vec3(dx, dy, dz));
            this.setRot(this.getYRot(), this.getXRot());
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

    // public static final ResourceLocation CART_ONE_CM = new ResourceLocation(FarmAndCharm.MOD_ID, "cart_one_cm");

    private void addStats(final double x, final double y, final double z) {
        if (!this.level().isClientSide) {
//            final int cm = Math.round(Mth.sqrt((float) (x * x + y * y + z * z)) * 100.0F);
//            if (cm > 0) {
//                for (final Entity passenger : this.getPassengers()) {
//                    if (passenger instanceof Player player) {
//                        player.awardStat(CART_ONE_CM, cm);
//                    }
//                }
//            }
        }
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
        this.setupWheels(move);

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

    @Override
    public void tick() {

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.08, 0.0));
        }

        super.tick();

        if (soundCooldown > 0) {
            soundCooldown--;
        }

        Vec3 currentPos = this.position();
        double distanceMoved = Math.sqrt(Math.pow(currentPos.x - this.lastDriverX, 1.5) + Math.pow(currentPos.y - this.lastDriverY, 1.5) + Math.pow(currentPos.z - this.lastDriverZ, 1.5));
        final double MIN_MOVEMENT_THRESHOLD = 0.05;

        if (distanceMoved > MIN_MOVEMENT_THRESHOLD) {
            spawnWheelParticles();
            playMovementSound();
        }



//        this.lastDriverX = currentPos.x;
//        this.lastDriverY = currentPos.y;
//        this.lastDriverZ = currentPos.z;

        //this.setupMovement();
        // this.setupRotation();

//        if (shouldResetRot) {
//            this.setYRot(originalYRot);
//            shouldResetRot = false;
//        }

        this.tickLerp();

        if (this.driver != null) {
            this.pulledTick();
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    private void setupWheels(Vec3 velocity) {
//        Vec3 velocity = this.getDeltaMovement();
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


    private void playMovementSound() {
        if (soundCooldown <= 0) {
            SoundEvent sound = DoApiSoundEventRegistry.CART_MOVING.get();
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), sound, SoundSource.NEUTRAL, 0.1F, 1.0F);
            soundCooldown = 55;
        }
    }

    private void spawnWheelParticles() {
        if (this.level() instanceof ServerLevel serverLevel) {
            BlockPos blockPosUnder = new BlockPos((int) this.getX(), (int) (this.getY() - 0.5), (int) this.getZ());
            BlockState blockState = serverLevel.getBlockState(blockPosUnder);

            if (!blockState.isAir()) {
                for (int i = 0; i < 4; ++i) {
                    double wheelParticleX = this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth() * 0.5;
                    double wheelParticleY = this.getY() + 0.1;
                    double wheelParticleZ = this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth() * 0.5;
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), wheelParticleX, wheelParticleY, wheelParticleZ, 1, 0.0D, 0.05D, 0.0D, 0.1D);
                }
            }
        }
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
    public boolean hurt(DamageSource damageSource, float damageAmount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (!this.level().isClientSide && !this.isRemoved()) {
            this.setDamage(this.getDamage() + damageAmount);
            this.markHurt();
            this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());

            if (this.level() instanceof ServerLevel serverLevel) {
                BlockState blockState = serverLevel.getBlockState(this.blockPosition().below());
                serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                        this.getX(), this.getY() + 0.1, this.getZ(), 10, 0.1D, 0.1D, 0.1D, 0.0D);

                originalYRot = this.getYRot();
                this.setYRot(originalYRot + this.random.nextFloat() * 10.0F - 5.0F);
                shouldResetRot = true;
            }

            if (this.getDamage() > 5.0F) {
                if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
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

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    public void setDamage(float damage) {
        this.entityData.set(DATA_ID_DAMAGE, Math.min(damage, 40.0F));
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
    }
}
