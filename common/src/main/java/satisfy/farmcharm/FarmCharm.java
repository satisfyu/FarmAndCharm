package satisfy.farmcharm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfy.farmcharm.registry.*;

public class FarmCharm {
    public static final String MOD_ID = "farm_and_charm";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    public static void init() {
        TabRegistry.init();
        ObjectRegistry.init();
        BlockEntityTypeRegistry.init();
        ScreenhandlerTypeRegistry.init();
        RecipeTypesRegistry.init();
        SoundEventRegistry.init();
    }

    public static void commonSetup(){
        FlammableBlockRegistry.init();

           }
}



