package example.modname.registry;

import de.cristelknight.doapi.DoApiExpectPlatform;
import net.minecraft.world.level.block.Block;

public class FlammableBlockRegistry {
    public static void init(){
        }
    private static void add(int burnOdd, int igniteOdd, Block... blocks){
        DoApiExpectPlatform.addFlammable(burnOdd, igniteOdd, blocks);
    }
}
