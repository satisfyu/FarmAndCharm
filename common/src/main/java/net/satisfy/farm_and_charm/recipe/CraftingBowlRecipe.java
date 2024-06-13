package net.satisfy.farm_and_charm.recipe;

import com.google.gson.JsonObject;
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
import net.satisfy.farm_and_charm.registry.RecipeTypeRegistry;
import net.satisfy.farm_and_charm.util.FarmAndCharmUtil;
import org.jetbrains.annotations.NotNull;

public class CraftingBowlRecipe implements Recipe<Container> {
    final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final int outputCount;

    public CraftingBowlRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output, int outputCount) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
        this.outputCount = outputCount;
    }

    public int getOutputCount() {
        return outputCount;
    }


    @Override
    public boolean matches(Container inventory, Level world) {
        int nonEmptySlots = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (!inventory.getItem(i).isEmpty()) {
                nonEmptySlots++;
            }
        }
        return nonEmptySlots >= 1 && nonEmptySlots <= inputs.size() && FarmAndCharmUtil.matchesRecipe(inventory, inputs, 0, 3);
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
        ItemStack result = this.output.copy();
        result.setCount(this.outputCount);
        return result;
    }


    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegistry.CRAFTING_BOWL_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeTypeRegistry.CRAFTING_BOWL_RECIPE_TYPE.get();
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
            NonNullList<Ingredient> ingredients = FarmAndCharmUtil.deserializeIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            JsonObject resultJson = GsonHelper.getAsJsonObject(json, "result");
            ItemStack result = ShapedRecipe.itemStackFromJson(resultJson);
            int count = GsonHelper.getAsInt(resultJson, "count", 1);
            return new CraftingBowlRecipe(id, ingredients, result, count);
        }

        @Override
        public @NotNull CraftingBowlRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);
            for (int i = 0; i < ingredientCount; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack result = buf.readItem();
            int count = buf.readVarInt();
            return new CraftingBowlRecipe(id, ingredients, result, count);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, CraftingBowlRecipe recipe) {
            buf.writeVarInt(recipe.inputs.size());
            recipe.inputs.forEach(ingredient -> ingredient.toNetwork(buf));
            buf.writeItem(recipe.output);
            buf.writeVarInt(recipe.outputCount);
        }
    }
}
