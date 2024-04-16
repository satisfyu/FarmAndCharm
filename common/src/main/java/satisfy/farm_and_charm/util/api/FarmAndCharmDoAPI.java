package satisfy.farm_and_charm.util.api;

import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.client.render.feature.CustomArmorManager;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import satisfy.farm_and_charm.registry.StorageTypesRegistry;

import java.util.Set;

@DoApiPlugin
public class FarmAndCharmDoAPI implements DoApiAPI {

    @Override
    public void registerBlocks(Set<Block> blocks) {
        StorageTypesRegistry.registerBlocks(blocks);
    }

    @Override
    public <T extends LivingEntity> void registerArmor(CustomArmorManager<T> customArmorManager, EntityModelSet entityModelSet) {

    }


}
