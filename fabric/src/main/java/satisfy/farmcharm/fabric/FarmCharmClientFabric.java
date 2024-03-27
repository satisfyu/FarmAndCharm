package satisfy.farmcharm.fabric;

import satisfy.farmcharm.client.FarmCharmClient;
import net.fabricmc.api.ClientModInitializer;

public class FarmCharmClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FarmCharmClient.preInitClient();
        FarmCharmClient.onInitializeClient();
    }
}
