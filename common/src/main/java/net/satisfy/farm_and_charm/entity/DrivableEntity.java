package net.satisfy.farm_and_charm.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class DrivableEntity extends Entity {
    public static final String DRIVER_TAG = "Driver";
    @Nullable
    private Entity driver;

    protected DrivableEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.driver = null;
    }

    public boolean hasDriver() {
        return null != this.driver;
    }

    public final Entity getDriver() {
        return this.driver;
    }


    public boolean addDriver(Entity entity) {
        if (this.hasDriver() || !this.canAddDriver(entity)) {
            return false;
        }
        this.driver = entity;
        return true;
    }

    protected void removeDriver() {
        if (this.hasDriver()) {
            this.driver = null;
        }
    }

    public boolean canAddDriver(Entity entity) {
        return !this.hasDriver() && !this.getPassengers().contains(entity);
    }

    @Override
    protected boolean canAddPassenger(Entity entity) {
        return super.canAddPassenger(entity) && this.getDriver() != entity;
    }

    @Override
    public boolean teleportTo(ServerLevel serverLevel, double d, double e, double f, Set<RelativeMovement> set, float g, float h) {
        if (serverLevel == this.level()) {
            this.teleportDriver();
        }
        return super.teleportTo(serverLevel, d, e, f, set, g, h);
    }

    @Override
    public void teleportTo(double d, double e, double f) {
        super.teleportTo(d, e, f);
        if (this.level() instanceof ServerLevel && null != this.driver) {
            this.teleportDriver();
        }
    }

    private void teleportDriver() {
        assert null != this.driver;
        double d = this.getY() + this.getPassengersRidingOffset() + this.driver.getMyRidingOffset();
        this.driver.moveTo(this.getX(), d, this.getZ());
    }

    @Override
    public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
        if (this.hasDriver()) {
            this.getDriver().causeFallDamage(f, g, damageSource);
        }
        return super.causeFallDamage(f, g, damageSource);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (this.hasDriver()) {
            compoundTag.putInt(DRIVER_TAG, this.getDriver().getId());
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.contains(DRIVER_TAG)) {
            this.driver = this.level().getEntity(compoundTag.getInt(DRIVER_TAG));
        }
    }
}
