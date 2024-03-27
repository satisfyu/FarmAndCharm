package example.modname.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import example.modname.Modname;
import example.modname.ModnameIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Supplier;

public class MobEffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Modname.MOD_ID, Registries.MOB_EFFECT);
    private static final Registrar<MobEffect> MOB_EFFECTS_REGISTRAR = MOB_EFFECTS.getRegistrar();

    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect){
        if(Platform.isForge()){
            return MOB_EFFECTS.register(name, effect);
        }
        return MOB_EFFECTS_REGISTRAR.register(new ModnameIdentifier(name), effect);
    }

    public static void init(){
        Modname.LOGGER.debug("Mob effects");
        MOB_EFFECTS.register();
    }

    static {
    }
}
