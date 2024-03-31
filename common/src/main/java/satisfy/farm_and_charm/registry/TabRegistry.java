package satisfy.farm_and_charm.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import satisfy.farm_and_charm.Farm_And_Charm;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Farm_And_Charm.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> farm_and_charm_TAB = CREATIVE_MODE_TABS.register("farm_and_charm", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 1)
            .icon(() -> new ItemStack(ObjectRegistry.WATER_SPRINKLER.get()))
            .title(Component.translatable("creativetab.farm_and_charm.tab"))
            .displayItems((parameters, output) -> {
                output.accept(ObjectRegistry.CORN_SEEDS.get());
                output.accept(ObjectRegistry.CORN.get());
                output.accept(ObjectRegistry.OAT_SEEDS.get());
                output.accept(ObjectRegistry.OAT.get());
                output.accept(ObjectRegistry.BARLEY_SEEDS.get());
                output.accept(ObjectRegistry.BARLEY.get());
                output.accept(ObjectRegistry.LETTUCE_CROP.get());
                output.accept(ObjectRegistry.LETTUCE.get());
                output.accept(ObjectRegistry.ONION.get());
                output.accept(ObjectRegistry.TOMATO_SEEDS.get());
                output.accept(ObjectRegistry.TOMATO.get());
                output.accept(ObjectRegistry.ROTTEN_TOMATO.get());
                output.accept(ObjectRegistry.STRAWBERRY_SEEDS.get());
                output.accept(ObjectRegistry.STRAWBERRY.get());

                output.accept(ObjectRegistry.WILD_LETTUCE.get());
                output.accept(ObjectRegistry.WILD_ONIONS.get());
                output.accept(ObjectRegistry.WILD_TOMATOES.get());
                output.accept(ObjectRegistry.WILD_STRAWBERRIES.get());

                output.accept(ObjectRegistry.LETTUCE_BAG.get());
                output.accept(ObjectRegistry.TOMATO_BAG.get());
                output.accept(ObjectRegistry.CARROT_BAG.get());
                output.accept(ObjectRegistry.POTATO_BAG.get());
                output.accept(ObjectRegistry.ONION_BAG.get());
                output.accept(ObjectRegistry.BEETROOT_BAG.get());
                output.accept(ObjectRegistry.CORN_BAG.get());
                output.accept(ObjectRegistry.STRAWBERRY_BAG.get());
                output.accept(ObjectRegistry.SWEETBERRY_BAG.get());

                output.accept(ObjectRegistry.OAT_BALL.get());
                output.accept(ObjectRegistry.BARLEY_BALL.get());
                output.accept(ObjectRegistry.FERTILIZED_SOIL_BLOCK.get());
                output.accept(ObjectRegistry.FERTILIZED_FARM_BLOCK.get());
                output.accept(ObjectRegistry.REINFORCED_SMOKER.get());
                output.accept(ObjectRegistry.FEEDING_TROUGH.get());
                output.accept(ObjectRegistry.WATER_SPRINKLER.get());

                output.accept(ObjectRegistry.SILO_WOOD.get());
                output.accept(ObjectRegistry.SILO_COPPER.get());
                output.accept(ObjectRegistry.WINDOW_SILL.get());
                output.accept(ObjectRegistry.TOOL_RACK.get());
                output.accept(ObjectRegistry.CRAFTING_BOWL.get());
                output.accept(ObjectRegistry.MINCER.get());
                output.accept(ObjectRegistry.COOKING_POT.get());
                output.accept(ObjectRegistry.COOKING_PAN.get());
                output.accept(ObjectRegistry.COOKING_SAUCEPAN.get());


                output.accept(ObjectRegistry.BUTTER.get());
                output.accept(ObjectRegistry.YEAST.get());
                output.accept(ObjectRegistry.DOUGH.get());

                output.accept(ObjectRegistry.CAT_FOOD.get());
                output.accept(ObjectRegistry.DOG_FOOD.get());
                output.accept(ObjectRegistry.HORSE_FODDER.get());
                output.accept(ObjectRegistry.FERTILIZER.get());

                output.accept(ObjectRegistry.SCARECROW.get());


            })
            .build());

    public static void init() {
        CREATIVE_MODE_TABS.register();
    }
}
