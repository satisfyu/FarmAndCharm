package satisfy.farmcharm.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.registry.CompostableRegistry;


@Mod(FarmCharm.MOD_ID)
public class ModnameForge {

    public ModnameForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(FarmCharm.MOD_ID, modEventBus);
        PreInit.preInit();
        FarmCharm.init();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
        FarmCharm.commonSetup();
    }
}
