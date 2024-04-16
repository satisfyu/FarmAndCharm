package satisfy.farm_and_charm.fabric;

import net.fabricmc.api.ModInitializer;
import satisfy.farm_and_charm.FarmAndCharm;
import satisfy.farm_and_charm.fabric.world.FarmAndCharmBiomeModification;
import satisfy.farm_and_charm.registry.CompostableRegistry;

public class FarmAndCharmFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FarmAndCharm.init();
        CompostableRegistry.registerCompostable();
        FarmAndCharm.commonSetup();
        FarmAndCharmBiomeModification.init();
    }
}
