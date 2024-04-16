package satisfy.farm_and_charm.util;

import com.google.gson.JsonArray;
import io.netty.buffer.Unpooled;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfy.farm_and_charm.FarmAndCharm;

import java.util.*;

@SuppressWarnings("unused, deprecation")
public class GeneralUtil {
	private static final String BLOCK_POS_KEY = "block_pos";
	private static final String BLOCK_POSES_KEY = "block_poses";

	public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(FarmAndCharm.MOD_ID, name));
	}

	public static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
		Objects.requireNonNull(pos, "BlockPos cannot be null");

		return tracking(world, new ChunkPos(pos));
	}

	public static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
		Objects.requireNonNull(world, "The world cannot be null");
		Objects.requireNonNull(pos, "The chunk pos cannot be null");

		return world.getChunkSource().chunkMap.getPlayers(pos, false);
	}

	public static float getInPercent(int i){
		return (float) i / 100;
	}

	public static boolean isFullAndSolid(LevelReader levelReader, BlockPos blockPos){
		return isFaceFull(levelReader, blockPos) && isSolid(levelReader, blockPos);
	}

	public static ItemStack convertStackAfterFinishUsing(LivingEntity entity, ItemStack used, Item returnItem, Item usedItem){
		if (entity instanceof ServerPlayer serverPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, used);
			serverPlayer.awardStat(Stats.ITEM_USED.get(usedItem));
		}
		if (used.isEmpty()) {
			return new ItemStack(returnItem);
		}
		if (entity instanceof Player player && !((Player)entity).getAbilities().instabuild) {
			ItemStack itemStack2 = new ItemStack(returnItem);
			if (!player.getInventory().add(itemStack2)) {
				player.drop(itemStack2, false);
			}
		}
		return used;
	}

	public static InteractionResult fillBucket(Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, ItemStack itemStack, ItemStack returnItem, BlockState blockState, SoundEvent soundEvent) {
		if (!level.isClientSide) {
			Item item = itemStack.getItem();
			player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, returnItem));
			player.awardStat(Stats.ITEM_USED.get(item));
			level.setBlockAndUpdate(blockPos, blockState);
			level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	public static InteractionResult emptyBucket(Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, ItemStack itemStack, ItemStack returnItem, BlockState blockState, SoundEvent soundEvent) {
		if (!level.isClientSide) {
			Item item = itemStack.getItem();
			player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, returnItem));
			player.awardStat(Stats.ITEM_USED.get(item));
			level.setBlockAndUpdate(blockPos, blockState);
			level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
		}

		return InteractionResult.sidedSuccess(level.isClientSide);
	}


	public static boolean isFaceFull(LevelReader levelReader, BlockPos blockPos){
		BlockPos belowPos = blockPos.below();
		return Block.isFaceFull(levelReader.getBlockState(belowPos).getShape(levelReader, belowPos), Direction.UP);
	}

	public static boolean isSolid(LevelReader levelReader, BlockPos blockPos){
		return levelReader.getBlockState(blockPos.below()).isSolid();
	}




	public static RotatedPillarBlock logBlock() {
		return new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG));
	}

	public static boolean isDamageType(DamageSource source, List<ResourceKey<DamageType>> damageTypes){
		for(ResourceKey<DamageType> key : damageTypes){
			if(source.is(key)) return true;
		}
		return false;
	}

	public static boolean isFire(DamageSource source){
		return isDamageType(source, List.of(DamageTypes.ON_FIRE, DamageTypes.IN_FIRE, DamageTypes.FIREBALL, DamageTypes.FIREWORKS, DamageTypes.UNATTRIBUTED_FIREBALL));
	}

	public static boolean matchesRecipe(Container inventory, NonNullList<Ingredient> recipe, int startIndex, int endIndex) {
		final List<ItemStack> validStacks = new ArrayList<>();
		for (int i = startIndex; i <= endIndex; i++) {
			final ItemStack stackInSlot = inventory.getItem(i);
			if (!stackInSlot.isEmpty())
				validStacks.add(stackInSlot);
		}
		for (Ingredient entry : recipe) {
			boolean matches = false;
			for (ItemStack item : validStacks) {
				if (entry.test(item)) {
					matches = true;
					validStacks.remove(item);
					break;
				}
			}
			if (!matches) {
				return false;
			}
		}
		return true;
	}
	
	public static NonNullList<Ingredient> deserializeIngredients(JsonArray json) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (int i = 0; i < json.size(); i++) {
			Ingredient ingredient = Ingredient.fromJson(json.get(i));
			if (!ingredient.isEmpty()) {
				ingredients.add(ingredient);
			}
		}
		return ingredients;
	}
	
	public static boolean isIndexInRange(int index, int startInclusive, int endInclusive) {
		return index >= startInclusive && index <= endInclusive;
	}
	
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };
		
		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.joinUnoptimized(buffer[1],
					Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX),
					BooleanOp.OR
			                                                                                            ));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}
		return buffer[0];
	}

	public static FriendlyByteBuf create() {
		return new FriendlyByteBuf(Unpooled.buffer());
	}
	
	public static Optional<Tuple<Float, Float>> getRelativeHitCoordinatesForBlockFace(BlockHitResult blockHitResult, Direction direction, Direction[] unAllowedDirections) {
		Direction direction2 = blockHitResult.getDirection();
		if (unAllowedDirections == null)
			unAllowedDirections = new Direction[] { Direction.DOWN, Direction.UP };
		if (Arrays.stream(unAllowedDirections).toList().contains(direction2))
			return Optional.empty();
		if (direction != direction2 && direction2 != Direction.UP && direction2 != Direction.DOWN) {
			return Optional.empty();
		} else {
			BlockPos blockPos = blockHitResult.getBlockPos().relative(direction2);
			Vec3 vec3 = blockHitResult.getLocation().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			float d = (float) vec3.x();
			float f = (float) vec3.z();
			
			float y = (float) vec3.y();
			
			if (direction2 == Direction.UP || direction2 == Direction.DOWN)
				direction2 = direction;
			return switch (direction2) {
				case NORTH -> Optional.of(new Tuple<>((float) (1.0 - d), y));
				case SOUTH -> Optional.of(new Tuple<>(d, y));
				case WEST -> Optional.of(new Tuple<>(f, y));
				case EAST -> Optional.of(new Tuple<>((float) (1.0 - f), y));
				case DOWN, UP -> Optional.empty();
			};
		}
	}

	public static void putBlockPos(CompoundTag compoundTag, BlockPos blockPos) {
		if (blockPos == null)
			return;
		int[] positions = new int[3];
		positions[0] = blockPos.getX();
		positions[1] = blockPos.getY();
		positions[2] = blockPos.getZ();
		compoundTag.putIntArray(BLOCK_POS_KEY, positions);
	}

	public static void putBlockPoses(CompoundTag compoundTag, Collection<BlockPos> blockPoses) {
		if (blockPoses == null || blockPoses.isEmpty()) return;
		int[] positions = new int[blockPoses.size() * 3];
		int pos = 0;
		for (BlockPos blockPos : blockPoses) {
			positions[pos * 3] = blockPos.getX();
			positions[pos * 3 + 1] = blockPos.getY();
			positions[pos * 3 + 2] = blockPos.getZ();
			pos++;
		}
		compoundTag.putIntArray(BLOCK_POSES_KEY, positions);
	}

	@Nullable
	public static BlockPos readBlockPos(CompoundTag compoundTag) {
		if (!compoundTag.contains(BLOCK_POS_KEY))
			return null;
		int[] positions = compoundTag.getIntArray(BLOCK_POS_KEY);
		return new BlockPos(positions[0], positions[1], positions[2]);
	}


	public static Set<BlockPos> readBlockPoses(CompoundTag compoundTag) {
		Set<BlockPos> blockSet = new HashSet<>();
		if (!compoundTag.contains(BLOCK_POSES_KEY))
			return blockSet;
		int[] positions = compoundTag.getIntArray(BLOCK_POSES_KEY);
		for (int pos = 0; pos < positions.length / 3; pos++)
			blockSet.add(new BlockPos(positions[pos * 3], positions[pos * 3 + 1], positions[pos * 3 + 2]));
		return blockSet;
	}

	public static final EnumProperty<LineConnectingType> LINE_CONNECTING_TYPE;

	static {
		LINE_CONNECTING_TYPE = EnumProperty.create("type", LineConnectingType.class);
	}

	public static void spawnSlice(Level level, ItemStack stack, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
		ItemEntity entity = new ItemEntity(level, x, y, z, stack);
		entity.setDeltaMovement(xMotion, yMotion, zMotion);
		level.addFreshEntity(entity);
	}



}