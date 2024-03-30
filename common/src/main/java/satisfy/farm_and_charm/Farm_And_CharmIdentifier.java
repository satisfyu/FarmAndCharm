package satisfy.farm_and_charm;

import net.minecraft.resources.ResourceLocation;

public class Farm_And_CharmIdentifier extends ResourceLocation {

    public Farm_And_CharmIdentifier(String path) {
        super(Farm_And_Charm.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Farm_And_Charm.MOD_ID + ":" + path);
    }
}
