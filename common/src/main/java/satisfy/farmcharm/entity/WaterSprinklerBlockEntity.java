package satisfy.farmcharm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfy.farmcharm.block.WaterSprinklerBlock;
import satisfy.farmcharm.registry.BlockEntityTypeRegistry;

public class WaterSprinklerBlockEntity extends BlockEntity {
    public WaterSprinklerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.SPRINKLER_BLOCK_ENTITY.get(), pos, state);
    }
}
