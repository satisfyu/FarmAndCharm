package satisfy.farm_and_charm.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.registry.EffectRegistry;

public class HorseFodderItem extends Item {
    public HorseFodderItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, net.minecraft.world.entity.player.Player player, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        if (entity instanceof Horse horse) {
            if (!entity.level().isClientSide) {
                if (!horse.isTamed()) {
                    horse.tameWithName(player);
                    horse.setOwnerUUID(player.getUUID());
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return InteractionResult.sidedSuccess(entity.getCommandSenderWorld().isClientSide);
                }
                horse.addEffect(new MobEffectInstance(EffectRegistry.HORSE_FODDER.get(), 6000, 0));
                horse.heal(10.0F);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                Level world = entity.getCommandSenderWorld();
                world.addParticle(ParticleTypes.HEART, entity.getX(), entity.getY() + 1.0, entity.getZ(), 0.0, 1.0, 1.0);
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.HORSE_EAT, entity.getSoundSource(), 1.0f, 1.0f);
            }
            return InteractionResult.sidedSuccess(entity.getCommandSenderWorld().isClientSide);
        }
        return InteractionResult.PASS;

    }
}
