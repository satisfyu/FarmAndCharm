package net.satisfy.farm_and_charm.client.recipebook.group;

import com.google.common.collect.ImmutableList;
import de.cristelknight.doapi.client.recipebook.IRecipeBookGroup;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.satisfy.farm_and_charm.registry.ObjectRegistry;

import java.util.List;

@Environment(EnvType.CLIENT)
public enum RoasterRecipeBookGroup implements IRecipeBookGroup {
    SEARCH(new ItemStack(Items.COMPASS)),
    MISC(new ItemStack(ObjectRegistry.DOUGH.get()));

    public static final List<IRecipeBookGroup> ROASTER_GROUPS = ImmutableList.of(SEARCH, MISC);
    private final List<ItemStack> icons;

    RoasterRecipeBookGroup(ItemStack... entries) {
        this.icons = ImmutableList.copyOf(entries);
    }

    @Override
    public List<ItemStack> getIcons() {
        return this.icons;
    }

    @Override
    public boolean fitRecipe(Recipe<? extends Container> recipe, RegistryAccess registryAccess) {
        return true;
    }
}
