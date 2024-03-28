package satisfy.farmcharm.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfy.farmcharm.registry.ObjectRegistry;

@Mixin(FarmBlock.class)
public class FarmlandBlockMixin {
    @Inject(method = "isNearWater", at = @At("HEAD"), cancellable = true)
    private static void injectWaterSprinklerCheck(LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        Block targetBlock = ObjectRegistry.WATER_SPRINKLER.get();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int x = -8; x <= 8; ++x) {
            for (int y = -1; y <= 1; ++y) {
                for (int z = -8; z <= 8; ++z) {
                    mutable.setWithOffset(blockPos, x, y, z);
                    if (levelReader.getBlockState(mutable).is(targetBlock)) {
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }
    }
}
