package satisfy.farmcharm.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
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

    private static final VoxelShape SHAPE = Shapes.or(
            Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375),
            Shapes.box(0.0625, 0.0625, 0.0625, 0.125, 0.75, 0.9375),
            Shapes.box(0.875, 0.0625, 0.0625, 0.9375, 0.75, 0.9375),
            Shapes.box(0.125, 0.0625, 0.0625, 0.875, 0.75, 0.125),
            Shapes.box(0.125, 0.0625, 0.875, 0.875, 0.75, 0.9375),
            Shapes.box(0.4375, 0.125, 0.4375, 0.5625, 1.0625, 0.5625)
    );

    public WaterSprinklerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockPos.betweenClosed(pos.offset(-4, -1, -4), pos.offset(4, 1, 4))
                .forEach(blockPos -> {
                    BlockState blockState = world.getBlockState(blockPos);
                    if (blockState.is(Blocks.FIRE)) {
                        world.removeBlock(blockPos, false);
                        world.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
                    }
                });
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        if (world.isClientSide) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5;
            double velocity = 0.2;
            float speedMultiplier = 5.0F;
            long gameTime = world.getGameTime();
            float partialTicks = Minecraft.getInstance().getFrameTime();
            float angle = (gameTime + partialTicks) * speedMultiplier % 360;
            double startOffset = 0.5;

            for (int i = 0; i < 4; ++i) {
                double angleRadians = Math.toRadians(angle + 90 * i);
                double cos = Math.cos(angleRadians);
                double sin = Math.sin(angleRadians);
                double dx = cos * velocity;
                double dz = sin * velocity;
                double startX = x + cos * startOffset;
                double startZ = z + sin * startOffset;

                for (double length = 0; length < 3; length += 0.5) {
                    double currentX = startX + dx * length;
                    double currentZ = startZ + dz * length;
                    world.addParticle(ParticleTypes.SPLASH, currentX, y, currentZ, dx, 0.0D, dz);
                }
            }
        }
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
