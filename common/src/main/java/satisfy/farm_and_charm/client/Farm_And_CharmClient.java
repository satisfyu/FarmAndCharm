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
import satisfy.farm_and_charm.client.render.CraftingBowlRenderer;
import satisfy.farm_and_charm.client.render.MincerRenderer;
import satisfy.farm_and_charm.client.render.ScarecrowRenderer;
import satisfy.farm_and_charm.client.render.WaterSprinklerRenderer;
import satisfy.farm_and_charm.registry.EntityTypeRegistry;
import satisfy.farm_and_charm.registry.ScreenhandlerTypeRegistry;

import static satisfy.farm_and_charm.registry.ObjectRegistry.*;

@Environment(EnvType.CLIENT)
public class Farm_And_CharmClient {

    public static void onInitializeClient() {
        RenderTypeRegistry.register(RenderType.cutout(), CRAFTING_BOWL.get(), WATER_SPRINKLER.get(),
                SCARECROW.get(), STOVE.get(), MINCER.get(), WILD_RIBWORT.get(), WILD_BARLEY.get(), WILD_CARROTS.get(),
                RIBWORT_TEA.get(), NETTLE_TEA.get(), STRAWBERRY_TEA.get(), WILD_BEETROOTS.get(), WILD_CORN.get(),
                WILD_EMMER.get(), WILD_LETTUCE.get(), WILD_NETTLE.get(), WILD_OAT.get(), WILD_ONIONS.get(), WILD_POTATOES.get(),
                WILD_TOMATOES.get(), WILD_STRAWBERRIES.get(), STUFFED_RABBIT.get()

        );


        ClientStorageTypes.init();
        RenderTypeRegistry.register(RenderType.translucent(), SCARECROW.get());
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
    }
}
