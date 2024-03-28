package satisfy.farmcharm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.FarmCharmIdentifier;

public class SoundEventRegistry {
    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(FarmCharm.MOD_ID, Registries.SOUND_EVENT).getRegistrar();
    public static final RegistrySupplier<SoundEvent> COOKING_POT_BOILING = create("cooking_pot_boiling");
    public static final RegistrySupplier<SoundEvent> COOKING_PAN_COOKING = create("cooking_pan_cooking");
    public static final RegistrySupplier<SoundEvent> COOKING_PAN_HIT = create("cooking_pan_hit");



    private static RegistrySupplier<SoundEvent> create(String name) {
        final ResourceLocation id = new FarmCharmIdentifier(name);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
        
    }
}
