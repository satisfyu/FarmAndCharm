package satisfy.farmcharm.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import satisfy.farmcharm.FarmCharmIdentifier;

public class TagRegistry {
    public static final TagKey<Block> ALLOWS_COOKING = TagKey.create(Registries.BLOCK, new FarmCharmIdentifier("allows_cooking"));

}
