package satisfy.farmcharm;

import net.minecraft.resources.ResourceLocation;

public class FarmCharmIdentifier extends ResourceLocation {

    public FarmCharmIdentifier(String path) {
        super(FarmCharm.MOD_ID, path);
    }

    public static String asString(String path) {
        return (FarmCharm.MOD_ID + ":" + path);
    }
}
