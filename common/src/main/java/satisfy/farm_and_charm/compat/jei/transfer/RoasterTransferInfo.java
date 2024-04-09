package satisfy.farm_and_charm.compat.jei.transfer;

import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.client.gui.handler.RoasterGuiHandler;
import satisfy.farm_and_charm.compat.jei.category.RoasterCategory;
import satisfy.farm_and_charm.recipe.RoasterRecipe;
import satisfy.farm_and_charm.registry.ScreenhandlerTypeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoasterTransferInfo implements IRecipeTransferInfo<RoasterGuiHandler, RoasterRecipe> {
    @Override
    public @NotNull Class<? extends RoasterGuiHandler> getContainerClass() {
        return RoasterGuiHandler.class;
    }

    @Override
    public @NotNull Optional<MenuType<RoasterGuiHandler>> getMenuType() {
        return Optional.of(ScreenhandlerTypeRegistry.ROASTER_SCREEN_HANDLER.get());
    }

    @Override
    public @NotNull RecipeType<RoasterRecipe> getRecipeType() {
        return RoasterCategory.ROASTER;
    }

    @Override
    public boolean canHandle(RoasterGuiHandler container, RoasterRecipe recipe) {
        return true;
    }

    @Override
    public @NotNull List<Slot> getRecipeSlots(RoasterGuiHandler container, RoasterRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        slots.add(container.getSlot(7));
        for(int i = 1; i <= recipe.getIngredients().size() && i < 7; i++){
            slots.add(container.getSlot(i));
        }
        return slots;
    }

    @Override
    public @NotNull List<Slot> getInventorySlots(RoasterGuiHandler container, RoasterRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 8; i < 8 + 36; i++) {
            Slot slot = container.getSlot(i);
            slots.add(slot);
        }
        return slots;
    }
}
