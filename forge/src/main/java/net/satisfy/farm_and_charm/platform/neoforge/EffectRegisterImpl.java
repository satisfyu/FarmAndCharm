package net.satisfy.farm_and_charm.platform.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.satisfy.farm_and_charm.FarmAndCharm;

import java.util.function.Supplier;

public class EffectRegisterImpl {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, FarmAndCharm.MOD_ID);

    public static Holder<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {
        return MOB_EFFECTS.register(name, effect);
    }
}
