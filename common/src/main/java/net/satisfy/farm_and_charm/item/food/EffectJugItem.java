package net.satisfy.farm_and_charm.item.food;

import de.cristelknight.doapi.common.util.GeneralUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class EffectJugItem extends Item {
    private final boolean returnBottle;

    public EffectJugItem(Properties properties, int duration, boolean returnBottle) {
        super(properties);
        this.returnBottle = returnBottle;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(itemStack, level, livingEntity);
        if (this.returnBottle) {
            return GeneralUtil.convertStackAfterFinishUsing(livingEntity, itemStack, Items.GLASS_BOTTLE, this);
        } else {
            return itemStack.isEmpty() ? new ItemStack(Items.AIR) : itemStack;
        }
    }
}
