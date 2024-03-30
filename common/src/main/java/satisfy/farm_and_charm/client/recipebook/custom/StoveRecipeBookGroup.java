package satisfy.farm_and_charm.client.recipebook.custom;

import com.google.common.collect.ImmutableList;
import de.cristelknight.doapi.client.recipebook.IRecipeBookGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import satisfy.farm_and_charm.registry.ObjectRegistry;

import java.util.List;
//TODO
@Environment(EnvType.CLIENT)
public enum StoveRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    BREAD(new ItemStack(Items.BREAD)),
    CAKE(new ItemStack(ObjectRegistry.DOUGH.get()));

    public static final List<IRecipeBookGroup> STOVE_GROUPS = ImmutableList.of(SEARCH, BREAD, CAKE);

    private final List<ItemStack> icons;

    StoveRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    public boolean fitRecipe(Recipe<? extends Container> recipe, RegistryAccess registryAccess) {
        return switch (this) {
            case SEARCH -> true;
            case BREAD ->
                    recipe.getIngredients().stream().anyMatch((ingredient) -> ingredient.test(ObjectRegistry.DOUGH.get().getDefaultInstance()));
            case CAKE ->
                    recipe.getIngredients().stream().anyMatch((ingredient) -> ingredient.test(ObjectRegistry.BARLEY.get().getDefaultInstance()));
        };
    }

    @Override
    public List<ItemStack> getIcons() {
        return this.icons;
    }

}
