package satisfy.farmcharm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.client.gui.handler.CookingPanGuiHandler;
import satisfy.farmcharm.client.gui.handler.CookingPotGuiHandler;
import satisfy.farmcharm.client.gui.handler.StoveGuiHandler;
import satisfy.farmcharm.client.gui.handler.CookingSaucePanGuiHandler;

import java.util.function.Supplier;

public class ScreenhandlerTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(FarmCharm.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<StoveGuiHandler>> STOVE_SCREEN_HANDLER = create("stove_gui_handler", () -> new MenuType<>(StoveGuiHandler::new, FeatureFlags.VANILLA_SET));
    public static final RegistrySupplier<MenuType<CookingSaucePanGuiHandler>> COOKING_SAUCEPAN_SCREEN_HANDLER = create("cooking_saucepan_gui_handler", () -> new MenuType<>(CookingSaucePanGuiHandler::new, FeatureFlags.VANILLA_SET));
    public static final RegistrySupplier<MenuType<CookingPanGuiHandler>> COOKING_PAN_SCREEN_HANDLER = create("cooking_pan_gui_handler", () -> new MenuType<>(CookingPanGuiHandler::new, FeatureFlags.VANILLA_SET)) ;
    public static final RegistrySupplier<MenuType<CookingPotGuiHandler>> COOKING_POT_SCREEN_HANDLER = create("cooking_pot_gui_handler", () -> new MenuType<>(CookingPotGuiHandler::new, FeatureFlags.VANILLA_SET)) ;


    public static void init() {
        MENU_TYPES.register();
    }

    private static <T extends MenuType<?>> RegistrySupplier<T> create(String name, Supplier<T> type) {
        return MENU_TYPES.register(name, type);
    }
}

