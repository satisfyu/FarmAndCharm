package satisfy.farm_and_charm.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;

import java.util.Set;

public class StorageTypesRegistry {
    public static final ResourceLocation TOOL_RACK = new Farm_And_CharmIdentifier("tool_rack");
    public static final ResourceLocation WINDOW_SILL = new Farm_And_CharmIdentifier("window_sill");

    public static void registerBlocks(Set<Block> blocks) {
        blocks.add(ObjectRegistry.TOOL_RACK.get());
        blocks.add(ObjectRegistry.WINDOW_SILL.get());

    }
}
