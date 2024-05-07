package net.satisfy.farm_and_charm.registry;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeybindRegistry {
    public static KeyMapping coupleOrPickUpCart;
    public static KeyMapping openChest;

    public static void init() {
        coupleOrPickUpCart = new KeyMapping(
                "key.farm_and_charm.couple_pick_up_cart",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "key.categories.farm_and_charm"
        );

        openChest = new KeyMapping(
                "key.farm_and_charm.openChest",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "key.categories.farm_and_charm"
        );

        KeyMappingRegistry.register(coupleOrPickUpCart);
        KeyMappingRegistry.register(openChest);
    }
}
