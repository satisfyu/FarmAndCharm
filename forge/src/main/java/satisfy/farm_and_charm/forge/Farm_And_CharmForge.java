package satisfy.farm_and_charm.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.registry.CompostableRegistry;


@Mod(Farm_And_Charm.MOD_ID)
public class Farm_And_CharmForge {

    public Farm_And_CharmForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Farm_And_Charm.MOD_ID, modEventBus);
        Farm_And_Charm.init();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
        Farm_And_Charm.commonSetup();
    }
}
