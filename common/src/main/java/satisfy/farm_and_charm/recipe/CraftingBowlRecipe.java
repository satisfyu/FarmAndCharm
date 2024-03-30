package satisfy.farm_and_charm.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.registry.RecipeTypesRegistry;
import satisfy.farm_and_charm.util.GeneralUtil;


public class CraftingBowlRecipe implements Recipe<Container> {

    final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;

    public CraftingBowlRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, int count) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
        this.output.setCount(count);
    }

    @Override
    public boolean matches(Container inventory, Level world) {
        return GeneralUtil.matchesRecipe(inventory, inputs, 0, 3);
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypesRegistry.CRAFTING_BOWL_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypesRegistry.CRAFTING_BOWL_RECIPE_TYPE.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<CraftingBowlRecipe> {

        @Override
        public @NotNull CraftingBowlRecipe fromJson(ResourceLocation id, JsonObject json) {
            final var ingredients = GeneralUtil.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Crafting Bowl Recipe");
            } else if (ingredients.size() > 4) {
                throw new JsonParseException("Too many ingredients for Crafting Bowl Recipe");
            } else {
                final var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                final var count = GsonHelper.getAsInt(json, "count", 1);
                result.setCount(count);
                return new CraftingBowlRecipe(id, ingredients, result, count);
            }
        }

        @Override
        public @NotNull CraftingBowlRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            final var ingredients = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buf));
            final var output = buf.readItem();
            final var count = buf.readVarInt();
            output.setCount(count);
            return new CraftingBowlRecipe(id, ingredients, output, count);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, CraftingBowlRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            recipe.inputs.forEach(entry -> entry.toNetwork(buf));
            buf.writeItem(recipe.output);
            buf.writeVarInt(recipe.output.getCount());
        }
    }
}