package satisfy.farm_and_charm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.entity.RottenTomatoEntity;

import java.util.function.Supplier;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<RottenTomatoEntity>> RottenTomato = create("rotten_tomato", () -> EntityType.Builder.<RottenTomatoEntity>of(RottenTomatoEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new Farm_And_CharmIdentifier("rotten_tomato").toString()));

    public static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new Farm_And_CharmIdentifier(path), type);
    }

    public static void init(){
        Farm_And_Charm.LOGGER.debug("Registering Mod Entities for " + Farm_And_Charm.MOD_ID);
        ENTITY_TYPES.register();
    }
}
