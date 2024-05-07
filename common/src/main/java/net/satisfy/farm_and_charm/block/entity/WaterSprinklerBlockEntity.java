package net.satisfy.farm_and_charm.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.farm_and_charm.registry.EntityTypeRegistry;

public class WaterSprinklerBlockEntity extends BlockEntity {
    public WaterSprinklerBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.SPRINKLER_BLOCK_ENTITY.get(), pos, state);
    }
}
