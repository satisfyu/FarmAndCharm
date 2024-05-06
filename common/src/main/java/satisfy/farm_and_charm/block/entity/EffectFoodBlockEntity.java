package satisfy.farm_and_charm.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;
import satisfy.farm_and_charm.item.food.EffectFoodHelper;
import satisfy.farm_and_charm.registry.EntityTypeRegistry;

import java.util.List;
import java.util.stream.Collectors;

public class EffectFoodBlockEntity extends BlockEntity  {
	public static final String STORED_EFFECTS_KEY = "StoredEffects";
	private List<Pair<MobEffectInstance, Float>> effects;

	public EffectFoodBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(EntityTypeRegistry.EFFECT_FOOD_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	@SuppressWarnings("all")
	public void addEffects(List<Pair<MobEffectInstance, Float>> effects) {
		List<Pair<MobEffectInstance, Float>> filteredEffects = effects.stream()
				.filter(effectPair -> effectPair.getFirst().getEffect() != MobEffects.HUNGER)
				.collect(Collectors.toList());

		this.effects = filteredEffects;
	}
	public List<Pair<MobEffectInstance, Float>> getEffects() {
		return effects != null ? effects : Lists.newArrayList();
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.effects = EffectFoodHelper.fromNbt(nbt != null ? nbt.getList(STORED_EFFECTS_KEY, 10) : new ListTag());
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		if (effects == null) {
			return;
		}
		ListTag nbtList = new ListTag();
		for (Pair<MobEffectInstance, Float> effect : effects) {
			nbtList.add(EffectFoodHelper.createNbt((short) MobEffect.getId(effect.getFirst().getEffect()), effect));

		}
		nbt.put(STORED_EFFECTS_KEY, nbtList);
	}
}


