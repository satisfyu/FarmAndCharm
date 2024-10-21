package net.satisfy.farm_and_charm.platform.fabric;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.satisfy.farm_and_charm.util.FarmAndCharmIdentifier;

import java.util.function.Supplier;

public class EffectRegisterImpl {
    public static Holder<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, FarmAndCharmIdentifier.of(name), effect.get());
    }
}
