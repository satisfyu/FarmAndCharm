package satisfy.farm_and_charm.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.registry.EffectRegistry;

public class DogFoodItem extends Item {
    public DogFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity entity, net.minecraft.world.InteractionHand hand) {
        if (entity instanceof Wolf wolf && !wolf.isTame()) {
            if (!entity.level().isClientSide) {
                wolf.tame(player);
                wolf.setOwnerUUID(player.getUUID());
                wolf.setInSittingPose(false);
                wolf.addEffect(new MobEffectInstance(EffectRegistry.DOG_FOOD.get(), 4000, 0));
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                Level world = entity.getCommandSenderWorld();
                world.addParticle(ParticleTypes.HEART, entity.getX(), entity.getY() + 1.0, entity.getZ(), 0.0, 0.0, 0.0);
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.FOX_EAT, entity.getSoundSource(), 1.0f, 1.0f);
            }
            return InteractionResult.sidedSuccess(entity.getCommandSenderWorld().isClientSide);
        }
        return InteractionResult.PASS;
    }
}

