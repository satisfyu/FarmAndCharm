package satisfy.farmcharm.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import satisfy.farmcharm.FarmCharmIdentifier;

import java.util.Set;

public class StorageTypesRegistry {
    public static final ResourceLocation TOOL_RACK = new FarmCharmIdentifier("tool_rack");

    public static void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.TOOL_RACK.get());
    }
}
