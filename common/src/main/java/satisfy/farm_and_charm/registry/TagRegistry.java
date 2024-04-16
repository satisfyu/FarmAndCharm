package satisfy.farm_and_charm.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfy.farm_and_charm.FarmAndCharmIdentifier;

public class TagRegistry {
    public static final TagKey<Block> ALLOWS_COOKING = TagKey.create(Registries.BLOCK, new FarmAndCharmIdentifier("allows_cooking"));
    public static final TagKey<Block> WILD_CROPS = TagKey.create(Registries.BLOCK, new FarmAndCharmIdentifier("wild_crops"));
    public static final TagKey<Item> CONTAINER = TagKey.create(Registries.ITEM, new FarmAndCharmIdentifier("container"));

}
