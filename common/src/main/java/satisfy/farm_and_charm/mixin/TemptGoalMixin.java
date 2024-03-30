package satisfy.farm_and_charm.mixin;

import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfy.farm_and_charm.registry.ObjectRegistry;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void modifyCanUse(CallbackInfoReturnable<Boolean> cir) {
        TemptGoal self = (TemptGoal)(Object)this;
        if (self.mob() instanceof Cat cat) {
            Player player = ((TemptGoalAccessor)self).getPlayer();
            if (cat.distanceToSqr(player) <= 25) {
                ItemStack itemStackInHand = player.getMainHandItem();
                if (itemStackInHand.getItem() == ObjectRegistry.CAT_FOOD.get().asItem()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
