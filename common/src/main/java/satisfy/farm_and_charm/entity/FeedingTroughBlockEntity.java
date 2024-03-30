package satisfy.farm_and_charm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfy.farm_and_charm.registry.BlockEntityTypeRegistry;

public class FeedingTroughBlockEntity extends BlockEntity {

    public FeedingTroughBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.FEEDING_TROUGH.get(), pos, state);
    }
}