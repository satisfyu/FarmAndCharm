package satisfy.farm_and_charm.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import satisfy.farm_and_charm.client.FarmAndCharmClient;

public class FarmAndCharmClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FarmAndCharmClient.preInitClient();
        FarmAndCharmClient.onInitializeClient();
    }
}
