package satisfy.farmcharm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import satisfy.farmcharm.entity.WaterSprinklerBlockEntity;

@SuppressWarnings("deprecation")
public class WaterSprinklerBlock extends BaseEntityBlock {

    public WaterSprinklerBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375),
            Shapes.box(0.0625, 0.0625, 0.0625, 0.125, 0.75, 0.9375),
            Shapes.box(0.875, 0.0625, 0.0625, 0.9375, 0.75, 0.9375),
            Shapes.box(0.125, 0.0625, 0.0625, 0.875, 0.75, 0.125),
            Shapes.box(0.125, 0.0625, 0.875, 0.875, 0.75, 0.9375),
            Shapes.box(0.4375, 0.125, 0.4375, 0.5625, 1.0625, 0.5625)
    );

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WaterSprinklerBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

}
