package satisfy.farm_and_charm.compat.rei;

import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import satisfy.farm_and_charm.compat.rei.cooking.CookingPotCategory;
import satisfy.farm_and_charm.compat.rei.cooking.CookingPotDisplay;
import satisfy.farm_and_charm.compat.rei.doughing.CraftingBowlCategory;
import satisfy.farm_and_charm.compat.rei.doughing.CraftingBowlDisplay;
import satisfy.farm_and_charm.compat.rei.roasting.RoasterCategory;
import satisfy.farm_and_charm.compat.rei.roasting.RoasterDisplay;
import satisfy.farm_and_charm.compat.rei.stove.StoveCategory;
import satisfy.farm_and_charm.compat.rei.stove.StoveDisplay;
import satisfy.farm_and_charm.recipe.CookingPotRecipe;
import satisfy.farm_and_charm.recipe.CraftingBowlRecipe;
import satisfy.farm_and_charm.recipe.RoasterRecipe;
import satisfy.farm_and_charm.recipe.StoveRecipe;
import satisfy.farm_and_charm.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.List;


public class Farm_And_CharmREIClientPlugin {
    public static void registerCategories(CategoryRegistry registry) {
        registry.add(new CookingPotCategory());
        registry.add(new StoveCategory());
        registry.add(new CraftingBowlCategory());
        registry.add(new RoasterCategory());
        registry.addWorkstations(CraftingBowlCategory.CRAFTING_BOWL_DISPLAY, EntryStacks.of(ObjectRegistry.CRAFTING_BOWL.get()));
        registry.addWorkstations(CookingPotDisplay.COOKING_POT_DISPLAY, EntryStacks.of(ObjectRegistry.COOKING_POT.get()));
        registry.addWorkstations(StoveDisplay.STOVE_DISPLAY, EntryStacks.of(ObjectRegistry.STOVE.get()));
        registry.addWorkstations(RoasterDisplay.ROASTER_DISPLAY, EntryStacks.of(ObjectRegistry.ROASTER.get()));
    }

    public static void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(CookingPotRecipe.class, CookingPotDisplay::new);
        registry.registerFiller(StoveRecipe.class, StoveDisplay::new);
        registry.registerFiller(CraftingBowlRecipe.class, CraftingBowlDisplay::new);
        registry.registerFiller(RoasterRecipe.class, RoasterDisplay::new);
    }

    public static List<Ingredient> ingredients(Recipe<Container> recipe, ItemStack stack) {
        List<Ingredient> l = new ArrayList<>(recipe.getIngredients());
        l.add(0, Ingredient.of(stack.getItem()));
        return l;
    }
}
