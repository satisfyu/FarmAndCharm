package net.satisfy.farm_and_charm.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class EffectRegister {
    @ExpectPlatform
    public static Holder<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {throw new AssertionError();}
}
