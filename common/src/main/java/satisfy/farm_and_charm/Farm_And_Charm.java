package satisfy.farm_and_charm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfy.farm_and_charm.registry.*;

public class Farm_And_Charm {
    public static final String MOD_ID = "farm_and_charm";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    public static void init() {
        TabRegistry.init();
        MobEffectRegistry.init();
        ObjectRegistry.init();
        EntityTypeRegistry.init();
        ScreenhandlerTypeRegistry.init();
        RecipeTypeRegistry.init();
        SoundEventRegistry.init();
    }

    public static void commonSetup(){
        FlammableBlockRegistry.init();
    }
}