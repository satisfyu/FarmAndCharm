package net.satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.UUID;

public class SweetsEffect extends MobEffect {
    public static final UUID SPEED_MODIFIER_ID = UUID.fromString("2deaf4fc-1673-4c5b-ac4f-25e37e08760f");
    private static final UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("80c07bfd-79bb-4056-9817-534a54376ab4");
    public static final UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("b99c1012-b2d4-423f-a1af-c855433731f0");

    public SweetsEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide) {
            double increase = amplifier + 1;

            applyModifier(livingEntity, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_ID, increase / 20);
            applyModifier(livingEntity, Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER_ID, increase / 2);
            applyModifier(livingEntity, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_ID, increase);
        }
    }

    private void applyModifier(LivingEntity entity, Attribute attribute, UUID uuid, double increase) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if (attributeInstance != null) {
            AttributeModifier modifier = new AttributeModifier(uuid, "SweetsEffect modifier", increase, AttributeModifier.Operation.ADDITION);
            if (attributeInstance.getModifier(uuid) != null) {
                attributeInstance.removeModifier(uuid);
            }
            attributeInstance.addPermanentModifier(modifier);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        removeModifier(entity, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_ID);
        removeModifier(entity, Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER_ID);
        removeModifier(entity, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_ID);
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
    }

    private void removeModifier(LivingEntity entity, Attribute attribute, UUID uuid) {
        AttributeInstance attributeInstance = entity.getAttribute(attribute);
        if (attributeInstance != null) {
            attributeInstance.removeModifier(uuid);
        }
    }
}