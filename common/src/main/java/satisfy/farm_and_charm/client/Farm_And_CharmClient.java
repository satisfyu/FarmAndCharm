package satisfy.farm_and_charm.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import satisfy.farm_and_charm.client.gui.CookingPanGui;
import satisfy.farm_and_charm.client.gui.CookingPotGui;
import satisfy.farm_and_charm.client.gui.CookingSaucepanGui;
import satisfy.farm_and_charm.client.gui.StoveGui;
import satisfy.farm_and_charm.client.model.CraftingBowlModel;
import satisfy.farm_and_charm.client.model.MincerModel;
import satisfy.farm_and_charm.client.model.ScarecrowModel;
import satisfy.farm_and_charm.client.model.WaterSprinklerModel;
import satisfy.farm_and_charm.client.model.cart.CartModel;
import satisfy.farm_and_charm.client.render.CraftingBowlRenderer;
import satisfy.farm_and_charm.client.render.MincerRenderer;
import satisfy.farm_and_charm.client.render.ScarecrowRenderer;
import satisfy.farm_and_charm.client.render.WaterSprinklerRenderer;
import satisfy.farm_and_charm.client.render.cart.CartRenderer;
import satisfy.farm_and_charm.registry.EntityTypeRegistry;
import satisfy.farm_and_charm.registry.ModelRegistry;
import satisfy.farm_and_charm.registry.ObjectRegistry;
import satisfy.farm_and_charm.registry.ScreenhandlerTypeRegistry;

@Environment(EnvType.CLIENT)
public enum Farm_And_CharmClient {
    ;

    public static void onInitializeClient() {
        RenderTypeRegistry.register(RenderType.cutout(), ObjectRegistry.CRAFTING_BOWL.get(), ObjectRegistry.WATER_SPRINKLER.get(),
                ObjectRegistry.SCARECROW.get(), ObjectRegistry.REINFORCED_SMOKER.get(), ObjectRegistry.MINCER.get()
        );


        ClientStorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), ObjectRegistry.SCARECROW.get());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.SCARECROW_BLOCK_ENTITY.get(), ScarecrowRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.MINCER_BLOCK_ENTITY.get(), MincerRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.CRAFTING_BOWL_BLOCK_ENTITY.get(), CraftingBowlRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.SPRINKLER_BLOCK_ENTITY.get(), WaterSprinklerRenderer::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_PAN_SCREEN_HANDLER.get(), CookingPanGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_POT_SCREEN_HANDLER.get(), CookingPotGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.COOKING_SAUCEPAN_SCREEN_HANDLER.get(), CookingSaucepanGui::new);
        MenuRegistry.registerScreenFactory(ScreenhandlerTypeRegistry.STOVE_SCREEN_HANDLER.get(), StoveGui::new);
    }

    public static void registerEntityRenderers(){
        EntityRendererRegistry.register(EntityTypeRegistry.RottenTomato, ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.FREIGHT_CART, CartRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.CHEST_CART, CartRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.PLOW, CartRenderer::new);
    }


    public static void preInitClient() {
        registerEntityRenderers();
        registerEntityModelLayer();
    }

    public static void registerEntityModelLayer() {
        EntityModelLayerRegistry.register(WaterSprinklerModel.LAYER_LOCATION, WaterSprinklerModel::getTexturedModelData);
        EntityModelLayerRegistry.register(CraftingBowlModel.LAYER_LOCATION, CraftingBowlModel::getTexturedModelData);
        EntityModelLayerRegistry.register(MincerModel.LAYER_LOCATION, MincerModel::getTexturedModelData);
        EntityModelLayerRegistry.register(ScarecrowModel.LAYER_LOCATION, ScarecrowModel::getTexturedModelData);
        EntityModelLayerRegistry.register(ModelRegistry.CART, CartModel::createBodyLayer);
    }
}
