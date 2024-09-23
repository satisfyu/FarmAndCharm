package net.satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class HorseFodderEffect extends MobEffect {
    private static final String JUMP_STRENGTH_MODIFIER_UUID = "7C8A6C79-4A74-4591-8F91-71F21E0A7EAD";
    private static final String MOVEMENT_SPEED_MODIFIER_UUID = "CE9DBC2A-EE3F-43F5-9DF7-F7F1EE4915A9";

    public HorseFodderEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x8B4513);
        this.addAttributeModifier(Attributes.JUMP_STRENGTH, JUMP_STRENGTH_MODIFIER_UUID, 0.1F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER_UUID, 0.1F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        if (modifier.getId().equals(UUID.fromString(JUMP_STRENGTH_MODIFIER_UUID)))
            return (amplifier + 1) * 0.5F;
        if (modifier.getId().equals(UUID.fromString(MOVEMENT_SPEED_MODIFIER_UUID)))
            return (amplifier + 1) * 0.5F;
        return amplifier + 1;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}