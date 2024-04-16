package satisfy.farm_and_charm.client.recipebook.group;

import de.cristelknight.doapi.client.recipebook.IRecipeBookGroup;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import com.google.common.collect.ImmutableList;
import satisfy.farm_and_charm.registry.ObjectRegistry;

public enum CookingPotRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    EFFECT(new ItemStack(Items.POTION)),
    MISC(new ItemStack(ObjectRegistry.GOULASH.get()));

    public static final List<IRecipeBookGroup> POT_GROUPS = ImmutableList.of(SEARCH, MISC, EFFECT);

    private final List<ItemStack> icons;

    CookingPotRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public boolean fitRecipe(Recipe<? extends Container> recipe, RegistryAccess registryAccess) {
        return switch (this) {
            case SEARCH, MISC -> true;
            case EFFECT ->
                    recipe.getIngredients().stream().anyMatch(ingredient -> ingredient.test(Items.POTION.getDefaultInstance()));
        };
    }

    public List<ItemStack> getIcons() {
        return this.icons;
    }
}
