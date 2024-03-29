package satisfy.farmcharm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfy.farmcharm.registry.BlockEntityTypeRegistry;

public class FeedingTroughBlockEntity extends BlockEntity {

    public FeedingTroughBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.FEEDING_TROUGH.get(), pos, state);
    }
}