package satisfy.farm_and_charm.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FertilizerItem extends BoneMealItem {
    public FertilizerItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!world.isClientSide && world instanceof ServerLevel serverWorld) {
            List<BlockPos> potentialPositions = new ArrayList<>();
            for (int x = -2; x <= 2; ++x) {
                for (int z = -2; z <= 2; ++z) {
                    BlockPos blockPos = pos.offset(x, 0, z);
                    if (world.getBlockState(blockPos).is(BlockTags.CROPS) || world.getBlockState(blockPos).is(BlockTags.BAMBOO_PLANTABLE_ON)) {
                        potentialPositions.add(blockPos);
                    }
                }
            }
            int targets = new Random().nextInt(5) + 2;
            boolean applied = false;
            for (int i = 0; i < targets && !potentialPositions.isEmpty(); i++) {
                BlockPos targetPos = potentialPositions.remove(new Random().nextInt(potentialPositions.size()));
                if (BoneMealItem.growCrop(context.getItemInHand(), world, targetPos)) {
                    serverWorld.sendParticles(ParticleTypes.HAPPY_VILLAGER, targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.0);
                    if (!applied) {
                        world.levelEvent(2005, targetPos, 0);
                        applied = true;
                    }
                }
            }
            if (applied) {
                context.getItemInHand().shrink(1);
                return InteractionResult.sidedSuccess(false);
            }
        }
        return super.useOn(context);
    }
}
