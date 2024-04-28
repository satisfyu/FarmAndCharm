package satisfy.farm_and_charm.item.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;

public class EffectFoodHelper {
    public static final String STORED_EFFECTS_KEY = "StoredEffects";
    public static final String FOOD_STAGE = "CustomModelData";

    public EffectFoodHelper() {
    }

    public static void addEffect(ItemStack stack, Pair<MobEffectInstance, Float> effect) {
        if (isBannedEffect(effect.getFirst())) {
            return;
        }

        ListTag nbtList = getEffectNbt(stack);
        boolean isNewEffect = true;
        int effectId = MobEffect.getId(effect.getFirst().getEffect());

        for (int i = 0; i < nbtList.size(); ++i) {
            CompoundTag nbtCompound = nbtList.getCompound(i);
            if (nbtCompound.getInt("id") == effectId) {
                isNewEffect = false;
                break;
            }
        }

        if (isNewEffect) {
            nbtList.add(createNbt((short) effectId, effect));
            stack.getOrCreateTag().put(STORED_EFFECTS_KEY, nbtList);
        }
    }

    private static boolean isBannedEffect(MobEffectInstance effectInstance) {
        return effectInstance.getEffect() == MobEffects.HUNGER;
    }

    private static ListTag getEffectNbt(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        return nbtCompound != null ? nbtCompound.getList(STORED_EFFECTS_KEY, 10) : new ListTag();
    }

    public static CompoundTag createNbt(short id, Pair<MobEffectInstance, Float> effect) {
        CompoundTag nbtCompound = new CompoundTag();
        nbtCompound.putShort("id", id);
        nbtCompound.putInt("duration", effect.getFirst().getDuration());
        nbtCompound.putInt("amplifier", effect.getFirst().getAmplifier());
        nbtCompound.putFloat("chance", effect.getSecond());
        return nbtCompound;
    }

    public static List<Pair<MobEffectInstance, Float>> getEffects(ItemStack stack) {
        if (stack.getItem() instanceof EffectFood) {
            return fromNbt(getEffectNbt(stack));
        } else if (!(stack.getItem() instanceof PotionItem)) {
            FoodProperties foodComponent = stack.getItem().getFoodProperties();
            return foodComponent != null ? foodComponent.getEffects() : Lists.newArrayList();
        } else {
            List<MobEffectInstance> effects = PotionUtils.getMobEffects(stack);
            List<Pair<MobEffectInstance, Float>> returnEffects = Lists.newArrayList();

            for (MobEffectInstance effect : effects) {
                returnEffects.add(new Pair<>(effect, 1.0F));
            }

            return returnEffects;
        }
    }

    public static List<Pair<MobEffectInstance, Float>> fromNbt(ListTag list) {
        List<Pair<MobEffectInstance, Float>> effects = Lists.newArrayList();
        for(int i = 0; i < list.size(); ++i) {
            CompoundTag nbtCompound = list.getCompound(i);
            MobEffect effect = MobEffect.byId(nbtCompound.getShort("id"));
            assert effect != null;
            effects.add(new Pair<>(new MobEffectInstance(effect, nbtCompound.getInt("duration"), nbtCompound.getInt("amplifier")), nbtCompound.getFloat("chance")));
        }
        return effects;
    }

    public static ItemStack setStage(ItemStack stack, int stage) {
        CompoundTag nbtCompound = stack.getTag() != null ? stack.getTag() : new CompoundTag();
        nbtCompound.putInt(FOOD_STAGE, stage);
        stack.setTag(nbtCompound);
        return stack;
    }

    public static int getStage(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        return nbtCompound != null ? nbtCompound.getInt(FOOD_STAGE) : 0;
    }

    public static void getTooltip(ItemStack stack, List<Component> tooltip) {
        List<Pair<MobEffectInstance, Float>> effects = getEffects(stack);
        MobEffectInstance statusEffect;
        MutableComponent mutableText;
        if (effects.isEmpty()) {
            tooltip.add(Component.translatable("effect.none").withStyle(ChatFormatting.GRAY));
        } else {
            for(Pair<MobEffectInstance, Float> effectPair : effects) {
                statusEffect = effectPair.getFirst();
                mutableText = Component.translatable(statusEffect.getDescriptionId());
                if (statusEffect.getAmplifier() > 0) {
                    mutableText = Component.translatable("potion.withAmplifier", mutableText, Component.translatable("potion.potency." + statusEffect.getAmplifier()));
                }

                if (statusEffect.getDuration() > 20) {
                    mutableText = Component.translatable("potion.withDuration", mutableText, MobEffectUtil.formatDuration(statusEffect, 1.0F));
                }
                tooltip.add(mutableText.withStyle(statusEffect.getEffect().getCategory().getTooltipFormatting()));
            }
        }
    }
}
