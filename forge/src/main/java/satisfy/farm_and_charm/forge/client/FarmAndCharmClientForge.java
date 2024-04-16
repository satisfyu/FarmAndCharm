package satisfy.farm_and_charm.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import satisfy.farm_and_charm.FarmAndCharm;
import satisfy.farm_and_charm.client.FarmAndCharmClient;

@Mod.EventBusSubscriber(modid = FarmAndCharm.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FarmAndCharmClientForge {

    private static boolean initialized = false;

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        FarmAndCharmClient.preInitClient();
    }

    @SubscribeEvent
    public static void preClientSetup(FMLClientSetupEvent event) {
        if(!initialized){
            FarmAndCharmClient.preInitClient();
            initialized = true;
        }
    }

}
