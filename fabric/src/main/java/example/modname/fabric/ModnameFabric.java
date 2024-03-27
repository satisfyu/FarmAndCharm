package example.modname.fabric;

import example.modname.Modname;
import example.modname.registry.CompostableRegistry;
import net.fabricmc.api.ModInitializer;

public class ModnameFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Modname.init();
        CompostableRegistry.registerCompostable();
        Modname.commonSetup();
    }
}
