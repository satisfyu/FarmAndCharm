package satisfy.farm_and_charm.util;

import net.minecraft.resources.ResourceLocation;
import satisfy.farm_and_charm.FarmAndCharm;

public class FarmAndCharmIdentifier extends ResourceLocation {

    public FarmAndCharmIdentifier(String path) {
        super(FarmAndCharm.MOD_ID, path);
    }

    public static String asString(String path) {
        return (FarmAndCharm.MOD_ID + ":" + path);
    }
}
