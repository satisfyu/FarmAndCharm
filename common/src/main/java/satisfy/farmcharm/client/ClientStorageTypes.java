package satisfy.farmcharm.client;

import de.cristelknight.doapi.client.render.block.storage.StorageBlockEntityRenderer;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import net.minecraft.resources.ResourceLocation;
import satisfy.farmcharm.FarmCharm;
import satisfy.farmcharm.client.render.ToolRackRenderer;
import satisfy.farmcharm.registry.StorageTypesRegistry;

public class ClientStorageTypes {
    public static void registerStorageType(ResourceLocation location, StorageTypeRenderer renderer){
        StorageBlockEntityRenderer.registerStorageType(location, renderer);
    }

    public static void init(){
        FarmCharm.LOGGER.debug("Registering Storage Block Renderers!");
        registerStorageType(StorageTypesRegistry.TOOL_RACK, new ToolRackRenderer());
    }
}
