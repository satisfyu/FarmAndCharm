package satisfy.farmcharm.client;

import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import satisfy.farmcharm.client.gui.CookingPanGui;
import satisfy.farmcharm.client.gui.CookingPotGui;
import satisfy.farmcharm.registry.ScreenhandlerTypeRegistry;

@Environment(EnvType.CLIENT)
public class FarmCharmClient {

    public static void onInitializeClient() {

        ClientStorageTypes.init();
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
    }


    public static void registerEntityRenderers() {
    }


    public static void preInitClient() {
        registerEntityRenderers();
    }
}