package satisfy.farm_and_charm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfy.farm_and_charm.registry.*;

public class FarmAndCharm {
    public static final String MOD_ID = "farm_and_charm";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    public static void init() {
        DataFixerRegistry.init();
        MobEffectRegistry.init();
        ObjectRegistry.init();
        EntityTypeRegistry.init();
        TabRegistry.init();
        ScreenhandlerTypeRegistry.init();
        RecipeTypeRegistry.init();
        KeybindRegistry.init();
    }

    public static void commonSetup(){
        FlammableBlockRegistry.init();
    }
}