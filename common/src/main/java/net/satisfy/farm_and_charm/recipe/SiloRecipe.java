package net.satisfy.farm_and_charm.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.farm_and_charm.registry.RecipeTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class SiloRecipe implements Recipe<Container> {

    final ResourceLocation id;
    private final String recipe_type;
    private final Ingredient input;
    private final ItemStack output;

    public SiloRecipe(ResourceLocation id, String type, Ingredient input, ItemStack output) {
        this.id = id;
        this.recipe_type = type;
        this.input = input;
        this.output = output;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.add(this.input);
        return nonNullList;
    }

    public String getRecipeType() {
        return this.recipe_type;
    }

    @Override
    public boolean matches(Container inventory, Level world) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if(input.test(inventory.getItem(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.SILO_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegistry.SILO_RECIPE_TYPE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<SiloRecipe> {

        @Override
        public @NotNull SiloRecipe fromJson(ResourceLocation id, JsonObject json) {
            String recipe_type = GsonHelper.getAsString(json, "recipe_type");
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new SiloRecipe(id, recipe_type, input, output);
        }

        @Override
        public @NotNull SiloRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            String recipe_type = buf.readUtf();
            return new SiloRecipe(id, recipe_type, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SiloRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeItem(recipe.output);
            buf.writeUtf(recipe.recipe_type);
        }
    }
}
