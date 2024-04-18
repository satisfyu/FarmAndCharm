package satisfy.farm_and_charm.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.cristelknight.doapi.client.ClientUtil;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import de.cristelknight.doapi.common.block.entity.StorageBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class ToolRackRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        for(int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);

            matrices.pushPose();
            if (!stack.isEmpty()) {
                double translate = (i + 1) * (1D / 3);
                matrices.translate(translate - (2D / 3), 0.6f, 0.38f);
                matrices.scale(0.6f, 0.6f, 0.6f);
                matrices.mulPose(Axis.ZN.rotationDegrees(135f));
                matrices.mulPose(Axis.YN.rotationDegrees(0f));
                ClientUtil.renderItem(stack, matrices, vertexConsumers, entity);
            }
            matrices.popPose();
        }
    }
}
