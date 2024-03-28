package satisfy.farmcharm.fabric;

import net.fabricmc.api.ModInitializer;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.registry.CompostableRegistry;

public class FarmCharmFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FarmCharm.init();
        CompostableRegistry.registerCompostable();
        FarmCharm.commonSetup();
    }
}
