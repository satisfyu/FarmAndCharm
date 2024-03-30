package satisfy.farm_and_charm.fabric;

import net.fabricmc.api.ModInitializer;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.registry.CompostableRegistry;

public class Farm_And_CharmFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Farm_And_Charm.init();
        CompostableRegistry.registerCompostable();
        Farm_And_Charm.commonSetup();
    }
}
