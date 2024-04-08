package satisfy.farm_and_charm.util.api;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.client.render.feature.FullCustomArmor;
import de.cristelknight.doapi.common.item.ICustomArmor;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import satisfy.farm_and_charm.registry.StorageTypesRegistry;

import java.util.Map;
import java.util.Set;

@DoApiPlugin
public class Farm_And_CharmDoAPI implements DoApiAPI {

    @Override
    public void registerBlocks(Set<Block> blocks) {
        StorageTypesRegistry.registerBlocks(blocks);
    }

    @Override
    public <T extends LivingEntity> void registerHat(Map<ICustomArmor, EntityModel<T>> map, EntityModelSet entityModelSet) {

    }

    @Override
    public <T extends LivingEntity> void registerArmor(Map<FullCustomArmor, Pair<HumanoidModel<T>, HumanoidModel<T>>> map, EntityModelSet entityModelSet) {

    }

}
