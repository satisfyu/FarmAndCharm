package satisfy.farmcharm.fabric;

import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.registry.CompostableRegistry;
import net.fabricmc.api.ModInitializer;

public class FarmCharmFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FarmCharm.init();
        CompostableRegistry.registerCompostable();
        FarmCharm.commonSetup();
    }
}
