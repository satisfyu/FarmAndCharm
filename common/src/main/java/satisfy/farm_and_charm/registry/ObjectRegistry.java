package satisfy.farm_and_charm.registry;

import de.cristelknight.doapi.Util;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.block.*;
import satisfy.farm_and_charm.block.crops.*;
import satisfy.farm_and_charm.item.*;
import satisfy.farm_and_charm.item.food.EffectFoodItem;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Block> TOMATO_CROP = registerWithoutItem("tomato_crop", () -> new TomatoCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistrySupplier<Item> TOMATO_SEEDS = registerItem("tomato_seeds", () -> new ItemNameBlockItem(TOMATO_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> TOMATO = registerItem("tomato", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Item> ROTTEN_TOMATO = registerItem("rotten_tomato", () -> new RottenTomatoItem(getSettings().food(Foods.POISONOUS_POTATO)));
    public static final RegistrySupplier<Block> LETTUCE_CROP = registerWithoutItem("lettuce_crop", () -> new LettuceCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistrySupplier<Item> LETTUCE_SEEDS = registerItem("lettuce_seeds", () -> new ItemNameBlockItem(LETTUCE_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> LETTUCE = registerItem("lettuce", () -> new Item(getSettings().food(Foods.CARROT)));
    public static final RegistrySupplier<Block> STRAWBERRY_CROP = registerWithoutItem("strawberry_crop", () -> new StrawberryCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> STRAWBERRY_SEEDS = registerItem("strawberry_seeds", () -> new ItemNameBlockItem(STRAWBERRY_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> STRAWBERRY = registerItem("strawberry", () -> new Item(getSettings().food(Foods.BEETROOT)));
    public static final RegistrySupplier<Block> OAT_CROP = registerWithoutItem("oat_crop", () -> new OatCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistrySupplier<Item> OAT_SEEDS = registerItem("oat_seeds", () -> new ItemNameBlockItem(OAT_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> OAT = registerItem("oat", () -> new Item(getSettings().food(Foods.BEETROOT)));
    public static final RegistrySupplier<Block> BARLEY_CROP = registerWithoutItem("barley_crop", () -> new BarleyCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> BARLEY_SEEDS = registerItem("barley_seeds", () -> new ItemNameBlockItem(BARLEY_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> BARLEY = registerItem("barley", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> CORN_CROP = registerWithoutItem("corn_crop", () -> new CornCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> KERNELS = registerItem("kernels", () -> new ItemNameBlockItem(CORN_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> CORN = registerItem("corn", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> ONION_CROP = registerWithoutItem("onion_crop", () -> new OnionCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> ONION = registerItem("onion", () -> new ItemNameBlockItem(ONION_CROP.get(), getSettings()));

    public static final RegistrySupplier<Block> WILD_TOMATOES = registerWithItem("wild_tomatoes", () -> new FlowerBlock(MobEffects.HEAL, 1, BlockBehaviour.Properties.copy(Blocks.DANDELION)));
    public static final RegistrySupplier<Block> WILD_LETTUCE = registerWithItem("wild_lettuce", () -> new FlowerBlock(MobEffects.HEAL, 1, BlockBehaviour.Properties.copy(Blocks.POPPY)));
    public static final RegistrySupplier<Block> WILD_ONIONS = registerWithItem("wild_onions", () -> new FlowerBlock(MobEffects.HEAL, 1, BlockBehaviour.Properties.copy(Blocks.LILY_OF_THE_VALLEY)));
    public static final RegistrySupplier<Block> WILD_STRAWBERRIES = registerWithItem("wild_strawberries", () -> new FlowerBlock(MobEffects.HEAL, 1, BlockBehaviour.Properties.copy(Blocks.AZURE_BLUET)));

    public static final RegistrySupplier<Block> STRAWBERRY_BAG = registerWithItem("strawberry_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> CARROT_BAG = registerWithItem("carrot_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> POTATO_BAG = registerWithItem("potato_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> BEETROOT_BAG = registerWithItem("beetroot_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> LETTUCE_BAG = registerWithItem("lettuce_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> TOMATO_BAG = registerWithItem("tomato_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> CORN_BAG = registerWithItem("corn_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> ONION_BAG = registerWithItem("onion_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));
    public static final RegistrySupplier<Block> FLOUR_BAG = registerWithItem("flour_bag", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RED_WOOL)));

    public static final RegistrySupplier<Block> OAT_BALL = registerWithItem("oat_ball", () -> new HayBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).strength(2.0F, 3.0F).sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> BARLEY_BALL = registerWithItem("barley_ball", () -> new HayBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).strength(2.0F, 3.0F).sound(SoundType.GRASS)));

    public static final RegistrySupplier<Block> FERTILIZED_SOIL_BLOCK = registerWithItem("fertilized_soil", () -> new FertilizedSoilBlock(BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT).strength(2.0F, 3.0F).sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> FERTILIZED_FARM_BLOCK = registerWithItem("fertilized_farmland", () -> new FertilizedFarmlandBlock(BlockBehaviour.Properties.of().randomTicks().strength(0.6F).sound(SoundType.GRAVEL)));

    public static final RegistrySupplier<Block> FEEDING_TROUGH = registerWithItem("feeding_trough", () -> new FeedingTroughBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> WATER_SPRINKLER = registerWithItem("water_sprinkler", () -> new WaterSprinklerBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));

    public static final RegistrySupplier<Block> MINCER = registerWithItem("mincer", () -> new MincerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).forceSolidOn()));
    public static final RegistrySupplier<Block> CRAFTING_BOWL = registerWithItem("crafting_bowl", () -> new CraftingBowlBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().forceSolidOn()));
    public static final RegistrySupplier<Block> COOKING_POT = registerWithItem("cooking_pot", () -> new CookingPotBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistrySupplier<Block> COOKING_PAN = registerWithoutItem("cooking_pan", () -> new CookingPanBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Item> COOKING_PAN_ITEM = registerItem("cooking_pan", () -> new CookingPanItem(COOKING_PAN.get(), getSettings()));
    public static final RegistrySupplier<Block> COOKING_SAUCEPAN = registerWithoutItem("cooking_saucepan", () -> new CookingSaucePanBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Item> COOKING_SAUCEPAN_ITEM = registerItem("cooking_saucepan", () -> new CookingSaucePanItem(COOKING_SAUCEPAN.get(), getSettings()));
    public static final RegistrySupplier<Item> PITCHFORK = registerItem("pitchfork", () -> new HoeItem(Tiers.IRON, -1, -1.0F, new Item.Properties()));


    public static final RegistrySupplier<Block> SILO_WOOD = registerWithItem("silo_wood", () -> new SiloBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> SILO_COPPER = registerWithItem("silo_copper", () -> new SiloBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK).noOcclusion().pushReaction(PushReaction.IGNORE)));

    public static final RegistrySupplier<Item> PLACEHOLDER_ITEM = registerItem("placeholder_item", () -> new Item(getSettings()));

    public static final RegistrySupplier<Block> WINDOW_SILL = registerWithItem("window_sill", () -> new WindowSillBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).noCollission()));
    public static final RegistrySupplier<Block> TOOL_RACK = registerWithItem("tool_rack", () -> new ToolRackBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).noCollission()));

    public static final RegistrySupplier<Item> FLOUR = registerItem("flour", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> YEAST = registerItem("yeast", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> BUTTER = registerItem("butter", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> DOUGH = registerItem("dough", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> RAW_PASTA = registerItem("raw_pasta", () -> new Item(getSettings()));

    //TODO: Food values
    public static final RegistrySupplier<Item> MINTED_BEEF = registerItem("minted_beef", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> LAMB_HAM = registerItem("lamb_ham", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> BACON = registerItem("bacon", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> CHICKEN_PARTS = registerItem("chicken_parts", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));

    public static final RegistrySupplier<Item> FARMER_SALAD = registerItem("farmer_salad", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> SIMPLE_TOMATO_SOUP = registerItem("simple_tomato_soup", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> BARLEY_SOUP = registerItem("barley_soup", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> ONION_SOUP = registerItem("onion_soup", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> POTATO_SOUP = registerItem("potato_soup", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> PORRIDGE_WITH_APPLES = registerItem("porridge_with_apples", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> ROASTED_CHICKEN = registerItem("roasted_chicken", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> LAMB_WITH_CORN = registerItem("lamb_with_corn", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> BEEF_PATTY_WITH_VEGETABLES = registerItem("beef_patty_with_vegetables", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> ROASTED_CORN = registerItem("roasted_corn", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> BARLEY_PATTIES_WITH_POTATOES = registerItem("barley_patties_with_potatoes", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> POTATO_WITH_ROAST_MEAT = registerItem("potato_with_roast_meat", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> BACON_WITH_EGGS = registerItem("bacon_with_eggs", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> BAKED_LAMB_HAM = registerItem("baked_lamb_ham", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> COOKED_SALMON = registerItem("cooked_salmon", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> FARMERS_BREAKFAST = registerItem("farmers_breakfast", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> STUFFED_CHICKEN = registerItem("stuffed_chicken", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> FARMERS_BREAD = registerItem("farmers_bread", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));
    public static final RegistrySupplier<Item> VEGETABLE_SANDWICH = registerItem("vegetable_sandwich", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 4800));
    public static final RegistrySupplier<Item> SANDWICH = registerItem("sandwich", () -> new EffectFoodItem(getFoodItemSettings(5, 0.7f, EffectRegistry.STUFFED.get(), 60 * 15), 6000));

    public static final RegistrySupplier<Item> CAT_FOOD = registerItem("cat_food", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> HORSE_FODDER = registerItem("horse_fodder", () -> new HorseFodderItem(getSettings()));
    public static final RegistrySupplier<Item> DOG_FOOD = registerItem("dog_food", () -> new DogFoodItem(getSettings()));

    public static final RegistrySupplier<Item> FERTILIZER = registerItem("fertilizer", () -> new FertilizerItem(getSettings()));

    public static final RegistrySupplier<Block> SCARECROW = registerWithItem("scarecrow", () -> new ScarecrowBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

    public static final RegistrySupplier<Block> REINFORCED_SMOKER = registerWithItem("reinforced_smoker", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> COBBLESTONE_STOVE = registerWithItem("cobblestone_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> SANDSTONE_STOVE = registerWithItem("sandstone_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> STONE_BRICKS_STOVE = registerWithItem("stone_bricks_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> DEEPSLATE_STOVE = registerWithItem("deepslate_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> GRANITE_STOVE = registerWithItem("granite_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> END_STOVE = registerWithItem("end_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> MUD_STOVE = registerWithItem("mud_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> QUARTZ_STOVE = registerWithItem("quartz_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));
    public static final RegistrySupplier<Block> RED_NETHER_BRICKS_STOVE = registerWithItem("red_nether_bricks_stove", () -> new StoveBlock(BlockBehaviour.Properties.copy(Blocks.BRICKS).lightLevel(state -> state.getValue(StoveBlock.LIT) ? 13 : 0)));


    public static void init() {
        Farm_And_Charm.LOGGER.debug("Registering Mod Block and Items for " + Farm_And_Charm.MOD_ID);
        ITEMS.register();
        BLOCKS.register();
    }

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }


    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties getSlabSettings() {
        return getLogBlockSettings().explosionResistance(3.0F);
    }

    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration) {
        return getFoodItemSettings(nutrition, saturationMod, effect, duration, true, false);
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        return getSettings().food(createFood(nutrition, saturationMod, effect, duration, alwaysEat, fast));
    }

    private static FoodProperties createFood(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationMod);
        if (alwaysEat) food.alwaysEat();
        if (fast) food.fast();
        if (effect != null) food.effect(new MobEffectInstance(effect, duration), 1.0f);
        return food.build();
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new Farm_And_CharmIdentifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new Farm_And_CharmIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return Util.registerItem(ITEMS, ITEM_REGISTRAR, new Farm_And_CharmIdentifier(path), itemSupplier);
    }
}
