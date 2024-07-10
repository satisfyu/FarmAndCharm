package net.satisfy.farm_and_charm.item.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.farm_and_charm.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class EffectItem extends Item {
    public EffectItem(Properties properties, int duration) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        List<Pair<MobEffectInstance, Float>> list2 = getFoodProperties() != null ? getFoodProperties().getEffects() : Lists.newArrayList();
        List<Pair<Attribute, AttributeModifier>> list3 = Lists.newArrayList();
        if (list2.isEmpty()) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        } else {
            for (Pair<MobEffectInstance, Float> statusEffectInstance : list2) {
                MutableComponent mutableText = Component.translatable(statusEffectInstance.getFirst().getDescriptionId());
                MobEffect statusEffect = statusEffectInstance.getFirst().getEffect();
                Map<Attribute, AttributeModifier> map = statusEffect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier entityAttributeModifier = entry.getValue();
                        AttributeModifier entityAttributeModifier2 = new AttributeModifier(
                                entityAttributeModifier.getName(),
                                statusEffect.getAttributeModifierValue(statusEffectInstance.getFirst().getAmplifier(), entityAttributeModifier),
                                entityAttributeModifier.getOperation()
                        );
                        list3.add(new Pair<>(entry.getKey(), entityAttributeModifier2));
                    }
                }
                if (statusEffectInstance.getFirst().getDuration() > 20) {
                    mutableText = Component.translatable(
                            "potion.withDuration",
                            mutableText, MobEffectUtil.formatDuration(statusEffectInstance.getFirst(), statusEffectInstance.getSecond()));
                }

                tooltip.add(mutableText.withStyle(statusEffect.getCategory().getTooltipFormatting()));
            }
        }

        if (!list3.isEmpty()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Attribute, AttributeModifier> pair : list3) {
                AttributeModifier entityAttributeModifier3 = pair.getSecond();
                double d = entityAttributeModifier3.getAmount();
                double e;
                if (entityAttributeModifier3.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && entityAttributeModifier3.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    e = entityAttributeModifier3.getAmount();
                } else {
                    e = entityAttributeModifier3.getAmount() * 100.0;
                }

                if (d > 0.0) {
                    tooltip.add(
                            Component.translatable(
                                            "attribute.modifier.plus." + entityAttributeModifier3.getOperation().toValue(),
                                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(pair.getFirst().getDescriptionId()))
                                    .withStyle(ChatFormatting.BLUE)
                    );
                } else if (d < 0.0) {
                    e *= -1.0;
                    tooltip.add(
                            Component.translatable(
                                            "attribute.modifier.take." + entityAttributeModifier3.getOperation().toValue(),
                                            ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(pair.getFirst().getDescriptionId()))
                                    .withStyle(ChatFormatting.RED)
                    );
                }
            }
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack item, Level level, LivingEntity entity) {
        ItemStack itemStack = super.finishUsingItem(item, level, entity);

        if (entity instanceof Player && ((Player) entity).getAbilities().instabuild)
            return itemStack;

        Item giveBackItem = Items.AIR;
        if(itemStack.getItem() == ObjectRegistry.STRAWBERRY_TEA_CUP.get() || itemStack.getItem() == ObjectRegistry.NETTLE_TEA_CUP.get() || itemStack.getItem() == ObjectRegistry.RIBWORT_TEA_CUP.get()){
            giveBackItem = Items.GLASS_BOTTLE;
        }

        if (giveBackItem != Items.AIR && (!level.isClientSide) && (entity instanceof Player player)) {
            ItemStack giveBackStack = new ItemStack(giveBackItem);
            if (!player.getInventory().add(giveBackStack)) player.drop(giveBackStack, false);
        }
        return itemStack;
    }
}
