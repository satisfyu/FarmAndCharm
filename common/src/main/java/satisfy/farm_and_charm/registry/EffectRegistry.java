package satisfy.farm_and_charm.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.effect.StuffedEffect;
import satisfy.farm_and_charm.effect.SweetsEffect;

import java.util.function.Supplier;

public class EffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.MOB_EFFECT);
    private static final Registrar<MobEffect> MOB_EFFECTS_REGISTRAR = MOB_EFFECTS.getRegistrar();

    public static final RegistrySupplier<MobEffect> STUFFED;
    public static final RegistrySupplier<MobEffect> SWEETS;


    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {
        if (Platform.isForge()) {
            return MOB_EFFECTS.register(name, effect);
        }
        return MOB_EFFECTS_REGISTRAR.register(new Farm_And_CharmIdentifier(name), effect);
    }

    public static void init() {
        Farm_And_Charm.LOGGER.debug("Mob effects");
        MOB_EFFECTS.register();
    }

    static {
        STUFFED = registerEffect("stuffed", StuffedEffect::new);
        SWEETS = registerEffect("sweets", SweetsEffect::new);
    }
}
