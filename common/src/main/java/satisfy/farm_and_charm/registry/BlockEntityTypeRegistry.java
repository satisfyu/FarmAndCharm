package satisfy.farm_and_charm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.entity.*;

import java.util.function.Supplier;

public class BlockEntityTypeRegistry {

    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.BLOCK_ENTITY_TYPE).getRegistrar();
    public static final RegistrySupplier<BlockEntityType<CookingPanBlockEntity>> COOKING_PAN_BLOCK_ENTITY = create("cooking_pan", () -> BlockEntityType.Builder.of(CookingPanBlockEntity::new, ObjectRegistry.COOKING_PAN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingPotBlockEntity>> COOKING_POT_BLOCK_ENTITY = create("cooking_pot", () -> BlockEntityType.Builder.of(CookingPotBlockEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CookingSaucePanBlockEntity>> COOKING_SAUCEPAN_BLOCK_ENTITY = create("cooking_saucepan", () -> BlockEntityType.Builder.of(CookingSaucePanBlockEntity::new, ObjectRegistry.COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<StoveBlockEntity>> STOVE_BLOCK_ENTITY = create("stove_block", () -> BlockEntityType.Builder.of(StoveBlockEntity::new, ObjectRegistry.COBBLESTONE_STOVE.get(), ObjectRegistry.MUD_STOVE.get(), ObjectRegistry.GRANITE_STOVE.get(), ObjectRegistry.SANDSTONE_STOVE.get(), ObjectRegistry.STONE_BRICKS_STOVE.get(), ObjectRegistry.RED_NETHER_BRICKS_STOVE.get(), ObjectRegistry.DEEPSLATE_STOVE.get(), ObjectRegistry.QUARTZ_STOVE.get(), ObjectRegistry.END_STOVE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WaterSprinklerBlockEntity>> SPRINKLER_BLOCK_ENTITY = create("water_sprinkler", () -> BlockEntityType.Builder.of(WaterSprinklerBlockEntity::new, ObjectRegistry.WATER_SPRINKLER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CraftingBowlBlockEntity>> CRAFTING_BOWL_BLOCK_ENTITY = create("crafting_bowl", () -> BlockEntityType.Builder.of(CraftingBowlBlockEntity::new, ObjectRegistry.CRAFTING_BOWL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SiloBlockEntity>> SILO_BLOCK_ENTITY = create("silo", () -> BlockEntityType.Builder.of(SiloBlockEntity::new, ObjectRegistry.SILO_WOOD.get(), ObjectRegistry.SILO_COPPER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY = create("feeding_trough", () -> BlockEntityType.Builder.of(FeedingTroughBlockEntity::new, ObjectRegistry.FEEDING_TROUGH.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<ScarecrowBlockEntity>> SCARECROW_BLOCK_ENTITY = create("scarecrow", () -> BlockEntityType.Builder.of(ScarecrowBlockEntity::new, ObjectRegistry.SCARECROW.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<MincerBlockEntity>> MINCER_BLOCK_ENTITY = create("mincer", () -> BlockEntityType.Builder.of(MincerBlockEntity::new, ObjectRegistry.MINCER.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new Farm_And_CharmIdentifier(path), type);
    }

    public static void init() {
        
    }
}
