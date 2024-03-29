package satisfy.farmcharm.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import satisfy.farmcharm.registry.ObjectRegistry;

public class FertilizedFarmlandBlock extends FarmBlock {
    public FertilizedFarmlandBlock(Properties properties) {
        super(properties);this.stateDefinition.any();
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        super.randomTick(blockState, serverLevel, blockPos, randomSource);
        if (randomSource.nextFloat() < 0.005) {
            BlockPos.betweenClosedStream(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))
                    .forEach(pos -> {
                        BlockState state = serverLevel.getBlockState(pos);
                        if (state.getBlock() instanceof BonemealableBlock bonemealableBlock) {
                            if (bonemealableBlock.isValidBonemealTarget(serverLevel, pos, state, serverLevel.isClientSide)) {
                                bonemealableBlock.performBonemeal(serverLevel, randomSource, pos, state);
                            }
                        }
                    });
        }

        int i = blockState.getValue(MOISTURE);
        if (!shouldMaintainFarmland(serverLevel, blockPos)) {
            turnToSoil(null, blockState, serverLevel, blockPos);
        } else if (i < 7) {
            serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, 7), 2);
        }
    }

    public static void turnToSoil(@Nullable Entity entity, BlockState blockState, Level level, BlockPos blockPos) {
        BlockState blockState2 = pushEntitiesUp(blockState, ObjectRegistry.FERTILIZED_SOIL_BLOCK.get().defaultBlockState(), level, blockPos);
        level.setBlockAndUpdate(blockPos, blockState2);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, blockState2));
    }

    private static boolean shouldMaintainFarmland(BlockGetter blockGetter, BlockPos blockPos) {
        return blockGetter.getBlockState(blockPos.above()).is(BlockTags.MAINTAINS_FARMLAND);
    }
}
