package satisfy.farm_and_charm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import satisfy.farm_and_charm.registry.BlockEntityTypeRegistry;

public class ScarecrowBlockEntity extends BlockEntity {
    public ScarecrowBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypeRegistry.SCARECROW.get(), pos, state);
    }
}
