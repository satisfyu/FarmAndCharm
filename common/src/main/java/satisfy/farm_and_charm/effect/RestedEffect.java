package satisfy.farm_and_charm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

import java.util.Objects;

public class RestedEffect extends MobEffect {
    public RestedEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }
}
