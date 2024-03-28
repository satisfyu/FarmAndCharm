package satisfy.farmcharm.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.FarmCharmIdentifier;
import satisfy.farmcharm.effect.StuffedEffect;
import satisfy.farmcharm.effect.SweetsEffect;

import java.util.function.Supplier;

public class EffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(FarmCharm.MOD_ID, Registries.MOB_EFFECT);
    private static final Registrar<MobEffect> MOB_EFFECTS_REGISTRAR = MOB_EFFECTS.getRegistrar();

    public static final RegistrySupplier<MobEffect> STUFFED;
    public static final RegistrySupplier<MobEffect> SWEETS;


    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect) {
        if (Platform.isForge()) {
            return MOB_EFFECTS.register(name, effect);
        }
        return MOB_EFFECTS_REGISTRAR.register(new FarmCharmIdentifier(name), effect);
    }

    public static void init() {
        FarmCharm.LOGGER.debug("Mob effects");
        MOB_EFFECTS.register();
    }

    static {
        STUFFED = registerEffect("stuffed", StuffedEffect::new);
        SWEETS = registerEffect("sweets", SweetsEffect::new);
    }
}
