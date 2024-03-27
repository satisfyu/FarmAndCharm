package example.modname;

import de.cristelknight.doapi.DoApiExpectPlatform;
import example.modname.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Modname {
    public static final String MOD_ID = "vinery";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    public static void init() {
        TabRegistry.init();
        ObjectRegistry.init();
        BoatAndSignRegistry.init();
        BlockEntityTypeRegistry.init();
        MobEffectRegistry.init();
        ScreenhandlerTypeRegistry.init();
        RecipeTypesRegistry.init();
        EntityRegistry.init();
        SoundEventRegistry.init();
        DoApiExpectPlatform.registerBuiltInPack(Modname.MOD_ID, new ModnameIdentifier("exampleresourcepack"), false);
    }

    public static void commonSetup(){
        FlammableBlockRegistry.init();

           }

    public static ResourceLocation MOD_ID(String path) {
        return new ResourceLocation(Modname.MOD_ID, path);
    }
}

