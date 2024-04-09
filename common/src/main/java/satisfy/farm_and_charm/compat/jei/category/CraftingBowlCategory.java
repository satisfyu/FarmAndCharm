package satisfy.farm_and_charm.compat.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.recipe.CraftingBowlRecipe;
import satisfy.farm_and_charm.registry.ObjectRegistry;

public class CraftingBowlCategory implements IRecipeCategory<CraftingBowlRecipe> {
    public static final RecipeType<CraftingBowlRecipe> DOUGHING = RecipeType.create(Farm_And_Charm.MOD_ID, "doughing", CraftingBowlRecipe.class);
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Farm_And_Charm.MOD_ID, "textures/gui/crafting_bowl.png");

    private final IDrawable background;
    private final IDrawable icon;

    public CraftingBowlCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ObjectRegistry.CRAFTING_BOWL.get().asItem().getDefaultInstance());
    }

    @Override
    public @NotNull RecipeType<CraftingBowlRecipe> getRecipeType() {
        return DOUGHING;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("rei.farm_anc_charm.bowl_category");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CraftingBowlRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 25).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 50, 43).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 25).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 43).addIngredients(recipe.getIngredients().get(3));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 110, 35).addItemStack(recipe.getResultItem(null));
    }
}