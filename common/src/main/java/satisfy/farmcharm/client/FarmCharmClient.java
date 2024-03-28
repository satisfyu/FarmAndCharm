package satisfy.farmcharm.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import satisfy.farmcharm.client.gui.CookingPanGui;
import satisfy.farmcharm.client.gui.CookingPotGui;
import satisfy.farmcharm.client.gui.CookingSaucepanGui;
import satisfy.farmcharm.client.gui.StoveGui;
import satisfy.farmcharm.client.model.CraftingBowlModel;
import satisfy.farmcharm.client.model.WaterSprinklerModel;
import satisfy.farmcharm.client.render.CraftingBowlRenderer;
import satisfy.farmcharm.client.render.WaterSprinklerRenderer;
import satisfy.farmcharm.registry.BlockEntityTypeRegistry;
import satisfy.farmcharm.registry.ObjectRegistry;
import satisfy.farmcharm.registry.ScreenhandlerTypeRegistry;

@Environment(EnvType.CLIENT)
public class FarmCharmClient {

    public static void onInitializeClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.CRAFTING_BOWL.get(), ObjectRegistry.WATER_SPRINKLER.get()
        );


        ClientStorageTypes.init();
        BlockEntityRendererRegistry.register(BlockEntityTypeRegistry.CRAFTING_BOWL_BLOCK_ENTITY.get(), CraftingBowlRenderer::new);
        BlockEntityRendererRegistry.register(BlockEntityTypeRegistry.SPRINKLER_BLOCK_ENTITY.get(), WaterSprinklerRenderer::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_SAUCEPAN_SCREEN_HANDLER.get(), CookingSaucepanGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.STOVE_SCREEN_HANDLER.get(), StoveGui::new);


    }


    public static void registerEntityRenderers() {
    }


    public static void preInitClient() {
        registerEntityRenderers();
        registerEntityModelLayer();
    }

    public static void registerEntityModelLayer() {
        EntityModelLayerRegistry.register(WaterSprinklerModel.LAYER_LOCATION, WaterSprinklerModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CraftingBowlModel.LAYER_LOCATION, CraftingBowlModel::getTexturedModelData);

    }
}
