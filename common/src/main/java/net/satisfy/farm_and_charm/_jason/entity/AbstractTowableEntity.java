package net.satisfy.farm_and_charm._jason.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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

    @Override
    public void tick() {

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.08, 0.0));
        }

        super.tick();

        Vec3 currentPos = this.position();

        this.lastDriverX = currentPos.x;
        this.lastDriverY = currentPos.y;
        this.lastDriverZ = currentPos.z;

        this.setupMovement();

        this.move(MoverType.SELF, this.getDeltaMovement());
    }
}
