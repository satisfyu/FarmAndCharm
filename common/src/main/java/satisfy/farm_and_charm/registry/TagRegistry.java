package satisfy.farm_and_charm.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;

public class TagRegistry {
    public static final TagKey<Block> ALLOWS_COOKING = TagKey.create(Registries.BLOCK, new Farm_And_CharmIdentifier("allows_cooking"));
    public static final TagKey<Block> WILD_CROPS = TagKey.create(Registries.BLOCK, new Farm_And_CharmIdentifier("wild_crops"));
    public static final TagKey<Item> CONTAINER = TagKey.create(Registries.ITEM, new Farm_And_CharmIdentifier("container"));

}
