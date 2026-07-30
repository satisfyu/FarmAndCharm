package net.satisfy.farm_and_charm.core.entity;

import net.minecraft.core.BlockPos;

public interface ChickenCoopAccess {
    boolean farmAndCharm$hasCoopTarget();

    BlockPos farmAndCharm$getCoopTarget();

    void farmAndCharm$setCoopTarget(BlockPos pos);

    void farmAndCharm$clearCoopTarget();

    int farmAndCharm$getCoopCooldown();

    void farmAndCharm$setCoopCooldown(int ticks);
}
