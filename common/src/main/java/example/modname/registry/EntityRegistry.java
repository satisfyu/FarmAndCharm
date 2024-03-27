package example.modname.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import example.modname.Modname;
import example.modname.ModnameIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class EntityRegistry {

	private static final Registrar<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Modname.MOD_ID, Registries.ENTITY_TYPE).getRegistrar();

	public static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
		return ENTITY_TYPES.register(new ModnameIdentifier(path), type);
	}
	
	public static void init() {
			}
}