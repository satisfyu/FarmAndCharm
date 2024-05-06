package satisfy.farm_and_charm.client;

import de.cristelknight.doapi.client.render.block.storage.api.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.api.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import satisfy.farm_and_charm.FarmAndCharm;
import satisfy.farm_and_charm.client.render.ToolRackRenderer;
import satisfy.farm_and_charm.client.render.WindowSillRenderer;
import satisfy.farm_and_charm.registry.StorageTypeRegistry;

public class ClientStorageTypes {
    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init(){
        FarmAndCharm.LOGGER.debug("Registering Storage Block Renderers!");
        registerStorageType(StorageTypeRegistry.TOOL_RACK, new ToolRackRenderer());
        registerStorageType(StorageTypeRegistry.WINDOW_SILL, new WindowSillRenderer());

    }
}
