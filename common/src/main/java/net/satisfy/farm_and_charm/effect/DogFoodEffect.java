package net.satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class DogFoodEffect extends MobEffect {
    private static final String KNOCKBACK_RESISTANCE_MODIFIER_UUID = "8E5D432F-91E5-4C0A-B556-3D4376F25F11";
    private static final String ATTACK_DAMAGE_MODIFIER_UUID = "CE752B4A-A279-452D-853A-73C26FB4BA46";
    private static final String ATTACK_SPEED_MODIFIER_UUID = "1809F109-92FE-410A-B78C-3BB9D0C4CC9E";

    public DogFoodEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_MODIFIER_UUID, 1.5F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_UUID, 1.0F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER_UUID, 1.5F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        if (modifier.getId().equals(UUID.fromString(KNOCKBACK_RESISTANCE_MODIFIER_UUID)))
            return (amplifier + 1) * 2.0F;
        if (modifier.getId().equals(UUID.fromString(ATTACK_DAMAGE_MODIFIER_UUID)))
            return (amplifier + 1) * 2.0F;
        if (modifier.getId().equals(UUID.fromString(ATTACK_SPEED_MODIFIER_UUID)))
            return (amplifier + 1) * 2.0F;
        return amplifier + 1;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
