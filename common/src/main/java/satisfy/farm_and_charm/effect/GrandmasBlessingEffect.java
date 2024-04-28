package satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;
import java.util.UUID;

public class GrandmasBlessingEffect extends MobEffect {
    private static final UUID LUCK_MODIFIER_ID = UUID.fromString("CC5AF142-2BD2-4215-B636-2605AED11727");
    private static final AttributeModifier LUCK_MODIFIER = new AttributeModifier(LUCK_MODIFIER_ID, "GrandmasBlessingEffect luck", 2, AttributeModifier.Operation.ADDITION);

    public GrandmasBlessingEffect() {
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
        AttributeInstance luckAttribute = attributeMap.getInstance(Attributes.LUCK);
        if (luckAttribute != null && !luckAttribute.hasModifier(LUCK_MODIFIER)) {
            luckAttribute.addPermanentModifier(LUCK_MODIFIER);
        }
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }
}
