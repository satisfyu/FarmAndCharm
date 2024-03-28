package satisfy.farmcharm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.FarmCharmIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import satisfy.farmcharm.recipe.CookingPanRecipe;
import satisfy.farmcharm.recipe.CookingPotRecipe;

import java.util.function.Supplier;

public class RecipeTypesRegistry {

    private static final Registrar<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(FarmCharm.MOD_ID, Registries.RECIPE_TYPE).getRegistrar();
    private static final Registrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(FarmCharm.MOD_ID, Registries.RECIPE_SERIALIZER).getRegistrar();


    public static final RegistrySupplier<RecipeType<CookingPanRecipe>> COOKING_PAN_RECIPE_TYPE = create("pan_cooking");
    public static final RegistrySupplier<RecipeSerializer<CookingPanRecipe>> COOKING_PAN_RECIPE_SERIALIZER = create("pan_cooking", CookingPanRecipe.Serializer::new);
    public static final RegistrySupplier<RecipeType<CookingPotRecipe>> COOKING_POT_RECIPE_TYPE = create("pot_cooking");
    public static final RegistrySupplier<RecipeSerializer<CookingPotRecipe>> COOKING_POT_RECIPE_SERIALIZER = create("pot_cooking", CookingPotRecipe.Serializer::new);


    private static <T extends Recipe<?>> RegistrySupplier<RecipeSerializer<T>> create(String name, Supplier<RecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZERS.register(new FarmCharmIdentifier(name), serializer);
    }

    private static <T extends Recipe<?>> RegistrySupplier<RecipeType<T>> create(String name) {
        Supplier<RecipeType<T>> type = () -> new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        return RECIPE_TYPES.register(new FarmCharmIdentifier(name), type);
    }

    public static void init() {
    }
}
