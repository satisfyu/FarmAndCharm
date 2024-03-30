package satisfy.farm_and_charm.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import satisfy.farm_and_charm.client.Farm_And_CharmClient;

public class Farm_And_CharmClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Farm_And_CharmClient.preInitClient();
        Farm_And_CharmClient.onInitializeClient();
    }
}
