package satisfy.farm_and_charm.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import satisfy.farm_and_charm.farm_and_charm;
import satisfy.farm_and_charm.registry.CompostableRegistry;


@Mod(farm_and_charm.MOD_ID)
public class Farm_And_CharmForge {

    public Farm_And_CharmForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(farm_and_charm.MOD_ID, modEventBus);
        farm_and_charm.init();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
        farm_and_charm.commonSetup();
    }
}
