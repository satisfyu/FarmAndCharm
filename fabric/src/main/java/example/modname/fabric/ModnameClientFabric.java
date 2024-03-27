package example.modname.fabric;

import example.modname.client.ModnameClient;
import net.fabricmc.api.ClientModInitializer;

public class ModnameClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModnameClient.preInitClient();
        ModnameClient.onInitializeClient();
    }
}
