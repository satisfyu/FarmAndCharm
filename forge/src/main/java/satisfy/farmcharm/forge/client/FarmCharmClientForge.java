package satisfy.farmcharm.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.client.FarmCharmClient;

@Mod.EventBusSubscriber(modid = FarmCharm.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FarmCharmClientForge {

    private static boolean initialized = false;

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        FarmCharmClient.preInitClient();
    }

    @SubscribeEvent
    public static void preClientSetup(FMLClientSetupEvent event) {
        if(!initialized){
            FarmCharmClient.preInitClient();
            initialized = true;
        }
    }

}
