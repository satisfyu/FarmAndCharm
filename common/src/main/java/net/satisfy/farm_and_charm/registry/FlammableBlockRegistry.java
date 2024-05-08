package net.satisfy.farm_and_charm.registry;

import de.cristelknight.doapi.DoApiCommonEP;
import net.minecraft.world.level.block.Block;

public class FlammableBlockRegistry {
    public static void init() {
    }

    private static void add(int burnOdd, int igniteOdd, Block... blocks) {
        DoApiCommonEP.addFlammable(burnOdd, igniteOdd, blocks);
    }
}
