package net.satisfy.farm_and_charm.item.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.satisfy.farm_and_charm.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class EffectItem extends Item {
    public EffectItem(Properties properties, int duration) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        List<FoodProperties.PossibleEffect> list2 = itemStack.has(DataComponents.FOOD) ? itemStack.get(DataComponents.FOOD).effects() : Lists.newArrayList();
        List<Pair<Holder<Attribute>, AttributeModifier>> list3 = Lists.newArrayList();
        if (list2.isEmpty()) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        } else {
            for (FoodProperties.PossibleEffect statusEffectInstance : list2) {
                MutableComponent mutableText = Component.translatable(statusEffectInstance.effect().getDescriptionId());
                MobEffectInstance statusEffect = statusEffectInstance.effect();

                statusEffect.getEffect().value().createModifiers(statusEffect.getAmplifier(), (holderx, attributeModifierx) -> {
                    AttributeModifier entityAttributeModifier = new AttributeModifier(
                            attributeModifierx.id(),
                            attributeModifierx.amount() * (double)(statusEffect.getAmplifier() + 1),
                            attributeModifierx.operation()
                    );
                    list3.add(new Pair<>(holderx, entityAttributeModifier));
                });
                if (statusEffectInstance.effect().getDuration() > 20) {
                    mutableText = Component.translatable(
                            "potion.withDuration",
                            mutableText, MobEffectUtil.formatDuration(statusEffect, statusEffectInstance.probability(), tooltipContext.tickRate()));
                }

                tooltip.add(mutableText.withStyle(statusEffect.getEffect().value().getCategory().getTooltipFormatting()));
            }
        }

        if (!list3.isEmpty()) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Holder<Attribute>, AttributeModifier> pair : list3) {
                AttributeModifier entityAttributeModifier3 = pair.getSecond();
                double d = entityAttributeModifier3.amount();
                double e;
                if (entityAttributeModifier3.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE && entityAttributeModifier3.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                    e = entityAttributeModifier3.amount();
                } else {
                    e = entityAttributeModifier3.amount() * 100.0;
                }

                if (d > 0.0) {
                    tooltip.add(
                            Component.translatable(
                                            "attribute.modifier.plus." + entityAttributeModifier3.operation().id(),
                                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(pair.getFirst().value().getDescriptionId()))
                                    .withStyle(ChatFormatting.BLUE)
                    );
                } else if (d < 0.0) {
                    e *= -1.0;
                    tooltip.add(
                            Component.translatable(
                                            "attribute.modifier.take." + entityAttributeModifier3.operation().id(),
                                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(pair.getFirst().value().getDescriptionId()))
                                    .withStyle(ChatFormatting.RED)
                    );
                }
            }
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack item, Level level, net.minecraft.world.entity.LivingEntity entity) {
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
