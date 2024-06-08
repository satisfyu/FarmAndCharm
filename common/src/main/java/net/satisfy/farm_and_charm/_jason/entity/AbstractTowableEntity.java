package net.satisfy.farm_and_charm._jason.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractTowableEntity extends Entity {

    public AbstractTowableEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }
}
