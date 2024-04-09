package satisfy.farm_and_charm.compat.rei.mincing;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.recipe.MincerRecipe;

import java.util.Collections;

public class MincingDisplay extends BasicDisplay {
    public static final CategoryIdentifier<MincingDisplay> MINCING_DISPLAY = CategoryIdentifier.of(Farm_And_Charm.MOD_ID, "mincing_display");

    public MincingDisplay(MincerRecipe recipe) {
        super(
            Collections.singletonList(EntryIngredients.ofIngredient(recipe.getInput())),
            Collections.singletonList(EntryIngredients.of(recipe.getOutput()))
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MincingCategory.MINCING_DISPLAY;
    }
}
