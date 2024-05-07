package net.satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

public class SustenanceEffect extends MobEffect {
    private static final int INTERVAL = 120;

    public SustenanceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.getCommandSenderWorld().isClientSide && entity instanceof Player player) {
            FoodData foodData = player.getFoodData();
            if (entity.tickCount % INTERVAL == 0) {
                if (foodData.getFoodLevel() == 20) {
                    player.heal(1.0F);
                } else {
                    foodData.setFoodLevel(foodData.getFoodLevel() + 1);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
