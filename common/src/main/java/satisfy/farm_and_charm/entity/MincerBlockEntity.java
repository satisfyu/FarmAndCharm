package satisfy.farm_and_charm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.farm_and_charm.block.MincerBlock;
import satisfy.farm_and_charm.recipe.MincerRecipe;
import satisfy.farm_and_charm.registry.EntityTypeRegistry;
import satisfy.farm_and_charm.registry.RecipeTypeRegistry;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MincerBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, BlockEntityTicker<MincerBlockEntity> {
    public final int SLOT_COUNT = 2;
    public final int INPUT_SLOT = 0;
    public final int OUTPUT_SLOT = 1;

    private NonNullList<ItemStack> stacks = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    
    public MincerBlockEntity(BlockPos position, BlockState state) {
        super(EntityTypeRegistry.MINCER_BLOCK_ENTITY.get(), position, state);
    }
    
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound))
            ContainerHelper.saveAllItems(compound, this.stacks);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    public @NotNull Component getDefaultName() {
        return Component.literal("mincer");
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }
    
    // unused
    
    public int filledSlots() {
        int i = SLOT_COUNT;

        for(int j = 0; j < this.getContainerSize(); ++j) {
            if (this.getItem(j) == ItemStack.EMPTY) {
                i--;
            }
        }

        return i;
    }

    public boolean canAddItem(ItemStack stack) {
        return this.canPlaceItem(INPUT_SLOT, stack);
    }

    public void addItemStack(ItemStack stack) {
        for(int j = 0; j < this.getContainerSize(); ++j) {
            if (this.getItem(j) == ItemStack.EMPTY) {
                this.setItem(j, stack);
                setChanged();
                return;
            }
        }
    }

    private ItemStack getRemainderItem(ItemStack stack) {
        if (stack.getItem().hasCraftingRemainingItem()) {
            return new ItemStack(Objects.requireNonNull(stack.getItem().getCraftingRemainingItem()));
        }
        return ItemStack.EMPTY;
    }
    
    private void shrinkStackInInputSlot(int count) {
        ItemStack inputSlotStack = this.stacks.get(INPUT_SLOT);
        inputSlotStack.shrink(count);
        this.setItem(INPUT_SLOT, inputSlotStack);
    }
    
    // end unused
    
    public static void spawnItem(Level world, ItemStack stack, int speed, Direction side, Position pos) {
        double d = pos.x();
        double e = pos.y();
        double f = pos.z();
        if (side.getAxis() == Direction.Axis.Y) {
            e -= 0.125;
        } else {
            e -= 0.15625;
        }
        
        ItemEntity itemEntity = new ItemEntity(world, d, e, f, stack);
        double g = world.random.nextDouble() * 0.1 + 0.2;
        itemEntity.setDeltaMovement(world.random.triangle((double)side.getStepX() * g, 0.0172275 * (double)speed), world.random.triangle(0.2, 0.0172275 * (double)speed), world.random.triangle((double)side.getStepZ() * g, 0.0172275 * (double)speed));
        world.addFreshEntity(itemEntity);
    }
    
    private void dropItemsInOutputSlot(Level level, BlockPos pos, BlockState state, MincerBlockEntity mincer) {
        
        Direction direction = state.getValue(MincerBlock.FACING).getClockWise();
        
        if (!level.isClientSide() && !this.stacks.get(OUTPUT_SLOT).isEmpty()) {
            ItemStack droppedStack = new ItemStack(mincer.stacks.get(OUTPUT_SLOT).getItem());
            droppedStack.setCount(mincer.stacks.get(OUTPUT_SLOT).getCount());
            this.stacks.set(OUTPUT_SLOT, ItemStack.EMPTY);
            
            Vec3 vec3d = Vec3.atCenterOf(pos);
            Vec3 vec3d2 = vec3d.relative(direction, 0.7);
            ((ServerLevel) level).sendParticles(ParticleTypes.SPIT, vec3d2.x(), vec3d2.y(), vec3d2.z(), 3, 0.2, 0.1, 0, 0.1);
            spawnItem(level, droppedStack, 6, direction, vec3d2);
            
            // spawning item inside the block
            // level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), droppedStack));
        }
    }
    
    @Override
    public int getMaxStackSize() {
        return 64;
    }
    
    @Override
    public int getContainerSize() {
        return stacks.size();
    }
    
    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        ItemStack inputSlotItemStack = this.stacks.get(INPUT_SLOT);
        return (index == INPUT_SLOT) && inputSlotItemStack.isEmpty() || (stack.is(inputSlotItemStack.getItem()) && (inputSlotItemStack.getCount() < inputSlotItemStack.getMaxStackSize()));
    }
    
    @Override
    public ItemStack getItem(int index) {
        return this.getItems().get(index);
    }
    
    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return this.stacks;
    }
    
    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }
    
    @Override
    public int @NotNull [] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }
    
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }
    
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return (direction == Direction.UP) && canPlaceItem(index, stack);
    }

    /** Handles crafting via recipe and retrieved block-state values.
     *  Lowers recipe difficulty based on loaded recipe type.
     *  Locates nearest player and additionally lowers the difficulty by one point if the player holds matching ingredient in their offhand.
     *  */
    @Override
    public void tick(Level level, BlockPos pos, BlockState state, MincerBlockEntity mincer) {
        
        // if something was crafted during the last tick, let's get rid of it.
        dropItemsInOutputSlot(level, pos, state, mincer);
        
        if (!level.isClientSide() && level.getBlockState(pos).getBlock() instanceof MincerBlock) {
            
            // gather our current values for use in later logic
            int crank = state.getValue(MincerBlock.CRANK);
            int cranked = state.getValue(MincerBlock.CRANKED);
            int full_rotations = state.getValue(MincerBlock.FULL_ROTATIONS);
            
            // if something was crafted during the last tick, let's get rid of it.
            // dropItemsInOutputSlot(level, pos, mincer);
            
            // built from the reconstruction of base logic.
            if (crank > 0) {
                if (cranked < MincerBlock.CRANKS_NEEDED) {
                    cranked++;
                }
                crank -= 1;
                if (cranked >= MincerBlock.CRANKS_NEEDED) {
                    cranked = 0;
                    full_rotations += 1;
                    
                    // check if we have a valid recipe or return null
                    MincerRecipe recipe = level.getRecipeManager().getRecipeFor(RecipeTypeRegistry.MINCER_RECIPE_TYPE.get(), mincer, level).orElse(null);
                    
                    if (recipe != null) {
                        
                        // used for check against player and completed craft
                        ItemStack inputStack = this.stacks.get(INPUT_SLOT);
                        
                        String recipe_type = recipe.getRecipeType();
                        int recipe_difficulty = 5;
                        
                        // lower the "difficulty" of the recipe relative to it's type
                        switch (recipe_type) {
                            case "MEAT" -> recipe_difficulty = 1;
                            case "WOOD" -> recipe_difficulty = 2;
                            case "STONE" -> recipe_difficulty = 3;
                            case "METAL" -> recipe_difficulty = 4;
                        }
                        
                        // create 3D box at block location to search for entities within
                        AABB searched_area = new AABB(pos);
                        // can probably be expanded, requires player standing up against the block entity basically
                        searched_area.inflate(4.0D);
                        // checking for Player instead of ServerPlayer returns an empty list as we're operating on the server with !level.isClientside()
                        List<ServerPlayer> playersNearby = level.getEntitiesOfClass(ServerPlayer.class, searched_area, Player::isAlive); // checking for nearby living player around the block
                        
                        if (!playersNearby.isEmpty()) {
                            for (Player player : playersNearby) {
                                if (player != null && player.getOffhandItem().is(inputStack.getItem())) {
                                    recipe_difficulty -= 1;
                                }
                            }
                        }
                        
                        if (full_rotations >= recipe_difficulty) {
                            
                            inputStack.shrink(1);
                            inputStack = inputStack.isEmpty() ? ItemStack.EMPTY : inputStack;
                            mincer.setItem(INPUT_SLOT, inputStack);
                            mincer.setItem(OUTPUT_SLOT, recipe.getResultItem(level.registryAccess()));
                            
                            // reset back to 0
                            level.setBlock(pos, state.setValue(MincerBlock.CRANK, crank).setValue(MincerBlock.CRANKED, cranked).setValue(MincerBlock.FULL_ROTATIONS, 0), Block.UPDATE_ALL);
                            return;
                        }
                        
                    }
                    
                    // hitting 4 is the only time it will reset the value back to 0 (similar logic placed in use() of MincerBlock.java)
                    if (full_rotations >= 4) {
                        level.setBlock(pos, state.setValue(MincerBlock.CRANK, crank).setValue(MincerBlock.CRANKED, cranked).setValue(MincerBlock.FULL_ROTATIONS, 0), Block.UPDATE_ALL);
                        return;
                    }
                }
                level.setBlock(pos, state.setValue(MincerBlock.CRANK, crank).setValue(MincerBlock.CRANKED, cranked).setValue(MincerBlock.FULL_ROTATIONS, full_rotations), Block.UPDATE_ALL);
            }
            else if (cranked > 0 && cranked < MincerBlock.CRANKS_NEEDED) {
                level.setBlock(pos, state.setValue(MincerBlock.CRANKED, 0), Block.UPDATE_ALL);
            }
            
            // KEEP COMMENTED: reconstruction of base logic. turns the crank, does nothing else.
//            if (crank > 0) {
//                if (cranked < MincerBlock.CRANKS_NEEDED) {
//                    cranked++;
//                }
//                crank -= 1;
//                if (cranked >= MincerBlock.CRANKS_NEEDED) {
//                    cranked = 0;
//                }
//                level.setBlock(pos, state.setValue(MincerBlock.CRANK, crank).setValue(MincerBlock.CRANKED, cranked), Block.UPDATE_ALL);
//            }
//            else if (cranked > 0 && cranked < MincerBlock.CRANKS_NEEDED) {
//                level.setBlock(pos, state.setValue(MincerBlock.CRANKED, 0), Block.UPDATE_ALL);
//            }
            
        }
        
    }
}
