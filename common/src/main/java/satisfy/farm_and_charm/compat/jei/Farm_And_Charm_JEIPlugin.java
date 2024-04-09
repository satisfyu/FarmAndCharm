package satisfy.farm_and_charm.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.Farm_And_CharmIdentifier;
import satisfy.farm_and_charm.client.gui.handler.RoasterGuiHandler;
import satisfy.farm_and_charm.client.gui.handler.StoveGuiHandler;
import satisfy.farm_and_charm.compat.jei.category.*;
import satisfy.farm_and_charm.compat.jei.transfer.CookingPotTransferInfo;
import satisfy.farm_and_charm.compat.jei.transfer.RoasterTransferInfo;
import satisfy.farm_and_charm.recipe.*;
import satisfy.farm_and_charm.registry.ObjectRegistry;
import satisfy.farm_and_charm.registry.RecipeTypeRegistry;
import satisfy.farm_and_charm.registry.ScreenhandlerTypeRegistry;


import java.util.List;
import java.util.Objects;


@JeiPlugin
public class Farm_And_Charm_JEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CookingPotCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new StoveCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new CraftingBowlCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RoasterCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SiloCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MincerCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<CookingPotRecipe> cookingRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.COOKING_POT_RECIPE_TYPE.get());
        registration.addRecipes(CookingPotCategory.COOKING_POT, cookingRecipes);
        List<StoveRecipe> stoveRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.STOVE_RECIPE_TYPE.get());
        registration.addRecipes(StoveCategory.STOVE, stoveRecipes);
        List<CraftingBowlRecipe> doughingRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.CRAFTING_BOWL_RECIPE_TYPE.get());
        registration.addRecipes(CraftingBowlCategory.DOUGHING, doughingRecipes);
        List<RoasterRecipe> roasterRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.ROASTER_RECIPE_TYPE.get());
        registration.addRecipes(RoasterCategory.ROASTER, roasterRecipes);
        List<SiloRecipe> siloRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.SILO_RECIPE_TYPE.get());
        registration.addRecipes(SiloCategory.DRYING_TYPE, siloRecipes);
        List<MincerRecipe> mincerRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.MINCER_RECIPE_TYPE.get());
        registration.addRecipes(MincerCategory.MINCING_TYPE, mincerRecipes);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new Farm_And_CharmIdentifier("jei_plugin");
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new RoasterTransferInfo());
        registration.addRecipeTransferHandler(RoasterGuiHandler.class, ScreenhandlerTypeRegistry.ROASTER_SCREEN_HANDLER.get(), RoasterCategory.ROASTER, 1, 3, 5, 36);
        registration.addRecipeTransferHandler(new CookingPotTransferInfo());
        registration.addRecipeTransferHandler(StoveGuiHandler.class, ScreenhandlerTypeRegistry.STOVE_SCREEN_HANDLER.get(), StoveCategory.STOVE, 1, 3, 5, 36);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ObjectRegistry.COOKING_POT.get().asItem().getDefaultInstance(), CookingPotCategory.COOKING_POT);
        registration.addRecipeCatalyst(ObjectRegistry.CRAFTING_BOWL.get().asItem().getDefaultInstance(), CraftingBowlCategory.DOUGHING);
        registration.addRecipeCatalyst(ObjectRegistry.STOVE.get().asItem().getDefaultInstance(), StoveCategory.STOVE);
        registration.addRecipeCatalyst(ObjectRegistry.ROASTER.get().asItem().getDefaultInstance(), RoasterCategory.ROASTER);
        registration.addRecipeCatalyst(ObjectRegistry.MINCER.get().asItem().getDefaultInstance(), MincerCategory.MINCING_TYPE);
        registration.addRecipeCatalyst(ObjectRegistry.SILO_WOOD.get().asItem().getDefaultInstance(), SiloCategory.DRYING_TYPE);

    }

    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient){
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }
}
