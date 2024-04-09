package satisfy.farm_and_charm.compat.rei.cooking;


import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.compat.rei.Farm_And_CharmREIClientPlugin;
import satisfy.farm_and_charm.recipe.CookingPotRecipe;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CookingPotDisplay extends BasicDisplay {

    public static final CategoryIdentifier<CookingPotDisplay> COOKING_POT_DISPLAY = CategoryIdentifier.of(Farm_And_Charm.MOD_ID, "cooking_pot_display");


    public CookingPotDisplay(Recipe<Container> recipe) {
        this(EntryIngredients.ofIngredients(Farm_And_CharmREIClientPlugin.ingredients(recipe, getContainer(recipe))), Collections.singletonList(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess()))), Optional.ofNullable(recipe.getId()));
    }

    public CookingPotDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return COOKING_POT_DISPLAY;
    }

    public static ItemStack getContainer(Recipe<Container> recipe) {
        if (recipe instanceof CookingPotRecipe c) {
            return c.getContainer();
        } else return ItemStack.EMPTY;
    }

}