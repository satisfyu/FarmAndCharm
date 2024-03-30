package satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class SweetsEffect extends MobEffect {
    public static final UUID SPEED_MODIFIER_ID = UUID.fromString("2deaf4fc-1673-4c5b-ac4f-25e37e08760f");
    private static final UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    public static final UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");

    public SweetsEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide) {
            double percentIncrease = 0.02 * (amplifier + 1);
            percentIncrease = Math.min(percentIncrease, 0.3);

            applyModifier(livingEntity, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_ID, percentIncrease, "Bakery speed boost");
            applyModifier(livingEntity, Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER_ID, percentIncrease, "Bakery attack speed boost");
            applyModifier(livingEntity, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_ID, percentIncrease, "Bakery attack damage boost");
        }
    }

    private void applyModifier(LivingEntity entity, net.minecraft.world.entity.ai.attributes.Attribute attribute, UUID uuid, double percentIncrease, String name) {
        net.minecraft.world.entity.ai.attributes.AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if (attributeInstance != null) {
            AttributeModifier modifier = attributeInstance.getModifier(uuid);
            if (modifier != null) {
                attributeInstance.removeModifier(modifier);
            }
            double increase = attribute.getDefaultValue() * percentIncrease;
            attributeInstance.addTransientModifier(new AttributeModifier(uuid, name, increase, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
