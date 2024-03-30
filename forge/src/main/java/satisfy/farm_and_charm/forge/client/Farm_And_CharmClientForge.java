package satisfy.farm_and_charm.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import satisfy.farm_and_charm.Farm_And_Charm;
import satisfy.farm_and_charm.client.Farm_And_CharmClient;

@Mod.EventBusSubscriber(modid = Farm_And_Charm.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Farm_And_CharmClientForge {

    private static boolean initialized = false;

    @SubscribeEvent
    public static void onClientSetup(RegisterEvent event) {
        Farm_And_CharmClient.preInitClient();
    }

    @SubscribeEvent
    public static void preClientSetup(FMLClientSetupEvent event) {
        if(!initialized){
            Farm_And_CharmClient.preInitClient();
            initialized = true;
        }
    }

}
