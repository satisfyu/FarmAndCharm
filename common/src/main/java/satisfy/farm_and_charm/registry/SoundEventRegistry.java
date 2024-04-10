package satisfy.farm_and_charm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;

public class SoundEventRegistry {
    private static final Registrar<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.SOUND_EVENT).getRegistrar();
    public static final RegistrySupplier<SoundEvent> COOKING_POT_BOILING = create("cooking_pot_boiling");
    public static final RegistrySupplier<SoundEvent> ROASTER_COOKING = create("roaster_cooking");
    public static final RegistrySupplier<SoundEvent> STOVE_CRACKLING = create("stove_crackling");
    public static final RegistrySupplier<SoundEvent> CRAFTING_BOWL_STIRRING = create("crafting_bowl_stirring");
    public static final RegistrySupplier<SoundEvent> MINCER_CRANKING = create("mincer_cranking");
    public static final RegistrySupplier<SoundEvent> WATER_SPRINKLER = create("water_sprinkler");



    private static RegistrySupplier<SoundEvent> create(String name) {
        final ResourceLocation id = new Farm_And_CharmIdentifier(name);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
        
    }
}
