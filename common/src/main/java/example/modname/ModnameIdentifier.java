package example.modname;

import net.minecraft.resources.ResourceLocation;

public class ModnameIdentifier extends ResourceLocation {

    public ModnameIdentifier(String path) {
        super(Modname.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Modname.MOD_ID + ":" + path);
    }
}
