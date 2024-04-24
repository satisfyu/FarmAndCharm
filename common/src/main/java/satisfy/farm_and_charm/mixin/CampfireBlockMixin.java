package satisfy.farm_and_charm.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfy.farm_and_charm.registry.TagRegistry;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin {

    @Inject(method = "dowse", at = @At("HEAD"), cancellable = true)
    private static void modifyDowseParticles(@Nullable Entity entity, LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
        BlockPos blockAbove = blockPos.above();
        BlockState blockAboveState = levelAccessor.getBlockState(blockAbove);
        if (blockAboveState.is(TagRegistry.COOKING_POTS)) {
            ci.cancel();
        }
    }

    @Unique
    private static boolean shouldCancelParticles(LevelAccessor level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(TagRegistry.COOKING_POTS);
    }

    @Inject(method = "makeParticles", at = @At("HEAD"), cancellable = true)
    private static void onMakeParticles(Level level, BlockPos blockPos, boolean signalFire, boolean spawnExtraSmoke, CallbackInfo ci) {
        if (shouldCancelParticles(level, blockPos)) {
            ci.cancel();
        }
    }
}


