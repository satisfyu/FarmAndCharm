package satisfy.farm_and_charm.compat.rei.silo;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import satisfy.farm_and_charm.FarmAndCharm;
import satisfy.farm_and_charm.recipe.SiloRecipe;

import java.util.Collections;
import java.util.stream.Collectors;

public class SiloDisplay extends BasicDisplay {
    public static final CategoryIdentifier<SiloDisplay> SILO_DISPLAY = CategoryIdentifier.of(FarmAndCharm.MOD_ID, "silo_display");

    public SiloDisplay(SiloRecipe recipe) {
        super(
                recipe.getIngredients().stream()
                        .map(EntryIngredients::ofIngredient)
                        .collect(Collectors.toList()),
                Collections.singletonList(EntryIngredients.of(recipe.getResultItem(null)))
        );
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SiloCategory.SILO_DISPLAY;
    }
}


