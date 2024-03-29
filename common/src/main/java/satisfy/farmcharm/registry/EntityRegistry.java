package satisfy.farmcharm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.FarmCharmIdentifier;
import satisfy.farmcharm.entity.RottenTomatoEntity;

import java.util.function.Supplier;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(FarmCharm.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<RottenTomatoEntity>> RottenTomato = create("rotten_tomato", () -> EntityType.Builder.<RottenTomatoEntity>of(RottenTomatoEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).build(new FarmCharmIdentifier("rotten_tomato").toString()));

    public static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new FarmCharmIdentifier(path), type);
    }

    public static void init(){
        FarmCharm.LOGGER.debug("Registering Mod Entities for " + FarmCharm.MOD_ID);
        ENTITY_TYPES.register();
    }
}
