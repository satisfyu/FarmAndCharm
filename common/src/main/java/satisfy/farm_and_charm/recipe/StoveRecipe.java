package satisfy.farm_and_charm.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.cristelknight.doapi.common.util.GeneralUtil;
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
import satisfy.farm_and_charm.registry.RecipeTypeRegistry;


public class StoveRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    protected final NonNullList<Ingredient> inputs;
    protected final ItemStack output;
    protected final float experience;

    public StoveRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, float experience) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
        this.experience = experience;
    }

    @Override
    public boolean matches(Container inventory, Level world) {
        return GeneralUtil.matchesRecipe(inventory, inputs, 1, 3);
    }
    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }


    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }


    public float getExperience() {
        return experience;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.STOVE_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegistry.STOVE_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<StoveRecipe> {

        @Override
        public @NotNull StoveRecipe fromJson(ResourceLocation id, JsonObject json) {
            final var ingredients = GeneralUtil.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Stove Recipe");
            } else if (ingredients.size() > 3) {
                throw new JsonParseException("Too many ingredients for Stove Recipe");
            } else {
                final ItemStack outputStack = ShapedRecipe.itemStackFromJson(json);
                float xp = GsonHelper.getAsFloat(json, "experience", 0.0F);
                return new StoveRecipe(id, ingredients, outputStack, xp);

            }

        }


        @Override
        public @NotNull StoveRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            final var ingredients = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buf));
            final ItemStack output = buf.readItem();
            final float xp = buf.readFloat();
            return new StoveRecipe(id, ingredients, output, xp);
        }

        @Override
        public void toNetwork(FriendlyByteBuf packet, StoveRecipe recipe) {
            packet.writeVarInt(recipe.inputs.size());
            recipe.inputs.forEach(entry -> entry.toNetwork(packet));
            packet.writeItem(recipe.output);
            packet.writeFloat(recipe.experience);
        }
    }
}