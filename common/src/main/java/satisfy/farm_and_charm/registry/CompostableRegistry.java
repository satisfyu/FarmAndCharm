package satisfy.farm_and_charm.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {
    public static void registerCompostable() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.WILD_LETTUCE.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.WILD_ONIONS.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.WILD_STRAWBERRIES.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.WILD_TOMATOES.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO_SEEDS.get().asItem(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TOMATO.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CORN_SEEDS.get().asItem(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CORN.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.OAT_SEEDS.get().asItem(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.OAT.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BARLEY_SEEDS.get().asItem(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BARLEY.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE_SEEDS.get().asItem(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LETTUCE.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.STRAWBERRY_SEEDS.get().asItem(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.STRAWBERRY.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROTTEN_TOMATO.get().asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ONION.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BUTTER.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.FLOUR.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DOUGH.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.YEAST.get(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.MINTED_BEEF.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.BACON.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HAM.get(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.ROASTED_CORN.get(), 1.0F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.POTATO_WITH_ROAST_MEAT.get(), 1.0F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.STUFFED_CHICKEN.get(), 1.0F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.FARMERS_BREAKFAST.get(), 1.0F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.FARMERS_BREAD.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SANDWICH.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.VEGETABLE_SANDWICH.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CAT_FOOD.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DOG_FOOD.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HORSE_FODDER.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.FERTILIZER.get(), 2.5F);
    }
}
