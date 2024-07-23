package net.satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SweetsEffect extends MobEffect {

    public SweetsEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 1.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
