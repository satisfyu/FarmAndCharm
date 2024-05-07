package net.satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

import java.util.Objects;

public class FarmersBlessingEffect extends MobEffect {
    public FarmersBlessingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (this.isDurationEffectTick(Objects.requireNonNull(entity.getEffect(this)).getDuration(), amplifier)) {
            entity.getActiveEffectsMap().forEach((effect, instance) -> {
                if (effect.getCategory() == MobEffectCategory.HARMFUL) {
                    entity.removeEffect(effect);
                }
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
        entity.getActiveEffectsMap().keySet().removeIf(effect -> effect.getCategory() == MobEffectCategory.HARMFUL);
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }
}
