package example.modname.forge;

import dev.architectury.platform.forge.EventBuses;
import example.modname.Modname;
import example.modname.registry.CompostableRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(Modname.MOD_ID)
public class ModnameForge {

    public ModnameForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Modname.MOD_ID, modEventBus);
        PreInit.preInit();
        Modname.init();

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::registerCompostable);
        Modname.commonSetup();
    }
}
