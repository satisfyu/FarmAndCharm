package satisfy.farm_and_charm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.entity.*;

import java.util.function.Supplier;

public class EntityTypeRegistry {

    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.BLOCK_ENTITY_TYPE).getRegistrar();
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<CookingPanBlockEntity>> COOKING_PAN_BLOCK_ENTITY = registerBlockEntity("cooking_pan", () -> BlockEntityType.Builder.of(CookingPanBlockEntity::new, ObjectRegistry.COOKING_PAN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingPotBlockEntity>> COOKING_POT_BLOCK_ENTITY = registerBlockEntity("cooking_pot", () -> BlockEntityType.Builder.of(CookingPotBlockEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingSaucePanBlockEntity>> COOKING_SAUCEPAN_BLOCK_ENTITY = registerBlockEntity("cooking_saucepan", () -> BlockEntityType.Builder.of(CookingSaucePanBlockEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StoveBlockEntity>> STOVE_BLOCK_ENTITY = registerBlockEntity("stove_block", () -> BlockEntityType.Builder.of(StoveBlockEntity::new, ObjectRegistry.REINFORCED_SMOKER.get(), ObjectRegistry.COBBLESTONE_STOVE.get(), ObjectRegistry.MUD_STOVE.get(), ObjectRegistry.GRANITE_STOVE.get(), ObjectRegistry.SANDSTONE_STOVE.get(), ObjectRegistry.STONE_BRICKS_STOVE.get(), ObjectRegistry.RED_NETHER_BRICKS_STOVE.get(), ObjectRegistry.DEEPSLATE_STOVE.get(), ObjectRegistry.QUARTZ_STOVE.get(), ObjectRegistry.END_STOVE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WaterSprinklerBlockEntity>> SPRINKLER_BLOCK_ENTITY = registerBlockEntity("water_sprinkler", () -> BlockEntityType.Builder.of(WaterSprinklerBlockEntity::new, ObjectRegistry.WATER_SPRINKLER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CraftingBowlBlockEntity>> CRAFTING_BOWL_BLOCK_ENTITY = registerBlockEntity("crafting_bowl", () -> BlockEntityType.Builder.of(CraftingBowlBlockEntity::new, ObjectRegistry.CRAFTING_BOWL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SiloBlockEntity>> SILO_BLOCK_ENTITY = registerBlockEntity("silo", () -> BlockEntityType.Builder.of(SiloBlockEntity::new, ObjectRegistry.SILO_WOOD.get(), ObjectRegistry.SILO_COPPER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY = registerBlockEntity("feeding_trough", () -> BlockEntityType.Builder.of(FeedingTroughBlockEntity::new, ObjectRegistry.FEEDING_TROUGH.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<ScarecrowBlockEntity>> SCARECROW_BLOCK_ENTITY = registerBlockEntity("scarecrow", () -> BlockEntityType.Builder.of(ScarecrowBlockEntity::new, ObjectRegistry.SCARECROW.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<MincerBlockEntity>> MINCER_BLOCK_ENTITY = registerBlockEntity("mincer", () -> BlockEntityType.Builder.of(MincerBlockEntity::new, ObjectRegistry.MINCER.get()).build(null));

    public static final RegistrySupplier<EntityType<RottenTomatoEntity>> RottenTomato = registerEntityType("rotten_tomato", () -> EntityType.Builder.<RottenTomatoEntity>of(RottenTomatoEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new Farm_And_CharmIdentifier("rotten_tomato").toString()));


    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new Farm_And_CharmIdentifier(path), type);
    }

    private static <T extends EntityType<?>> RegistrySupplier<T> registerEntityType(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new Farm_And_CharmIdentifier(path), type);
    }

    public static void init() {
        ENTITY_TYPES.register();
        Farm_And_Charm.LOGGER.debug("Registering Mod Entities and Block Entities for " + Farm_And_Charm.MOD_ID);
    }
}
