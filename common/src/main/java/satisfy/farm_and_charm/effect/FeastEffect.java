package satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;

public class FeastEffect extends MobEffect {
    public FeastEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.getCommandSenderWorld().isClientSide && entity instanceof Player player) {
            FoodData foodData = player.getFoodData();

            boolean shouldHeal = player.isHurt() && player.getCommandSenderWorld().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)
                    && foodData.getSaturationLevel() > 0f && foodData.getFoodLevel() >= 18;

            long worldTime = player.getCommandSenderWorld().getDayTime() % 24000;

            if (foodData.needsFood() || player.hasEffect(MobEffects.REGENERATION) || foodData.getSaturationLevel() <= 0f) {
                if (!shouldHeal) {
                    if (foodData.getFoodLevel() > 3 || foodData.getSaturationLevel() <= 0f) {
                        foodData.setFoodLevel(Math.max(foodData.getFoodLevel() - 1, 3));
                    }
                    foodData.addExhaustion(-4.0f);
                }
                if (worldTime >= 10000 && worldTime <= 13000) {
                    foodData.eat(6, 0.6f);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
