package satisfy.farm_and_charm.mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfy.farm_and_charm.item.HorseFodderItem;

@Mixin(Horse.class)
public abstract class HorseMixin {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void injectCustomItemInteraction(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Horse self = (Horse)(Object)this;
        ItemStack itemStack = player.getItemInHand(hand);
        if (!self.isTamed() && itemStack.getItem() instanceof HorseFodderItem) {
            if (!self.level().isClientSide) {
                self.tameWithName(player);
                itemStack.shrink(1);
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
