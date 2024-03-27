package example.modname.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import example.modname.Modname;
import example.modname.ModnameIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class RecipeTypesRegistry {

    private static final Registrar<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Modname.MOD_ID, Registries.RECIPE_TYPE).getRegistrar();
    private static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Modname.MOD_ID, Registries.RECIPE_SERIALIZER).getRegistrar();

    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(String name, Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(new ModnameIdentifier(name), serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create(String name) {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return RECIPE_TYPES.register(new ModnameIdentifier(name), type);
    }

    public static void init() {
    }
}
