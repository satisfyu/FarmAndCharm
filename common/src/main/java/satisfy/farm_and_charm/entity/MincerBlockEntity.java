package satisfy.farm_and_charm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.farm_and_charm.block.MincerBlock;
import satisfy.farm_and_charm.recipe.MincerRecipe;
import satisfy.farm_and_charm.registry.EntityTypeRegistry;
import satisfy.farm_and_charm.registry.RecipeTypesRegistry;

import java.util.Objects;
import java.util.stream.IntStream;

public class MincerBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, BlockEntityTicker<MincerBlockEntity> {
    
    // constants to refer to slot values easily
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
    
    private void dropItemsInOutputSlot(Level level, BlockPos blockPos, MincerBlockEntity blockEntity) {
        if (!this.stacks.get(OUTPUT_SLOT).isEmpty()) {
            ItemStack droppedStack = new ItemStack(blockEntity.stacks.get(OUTPUT_SLOT).getItem());
            droppedStack.setCount(blockEntity.stacks.get(OUTPUT_SLOT).getCount());
            this.stacks.set(OUTPUT_SLOT, ItemStack.EMPTY);
            level.addFreshEntity(new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), droppedStack));
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

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, MincerBlockEntity blockEntity) {
        
        // Ensure crating takes 1-3 second, or less depending on the item held -
        // maybe we can also set an additional condition for the recipe so that stuff like meat will take 1 second, stone 2 seconds and metal 3 seconds?
        // Ensure that you can produce fast output when holding items in offhand
        
        if (!level.isClientSide && level.getBlockState(blockPos).getBlock() instanceof MincerBlock) {
            
            int crank = blockState.getValue(MincerBlock.CRANK);
            int cranked = blockState.getValue(MincerBlock.CRANKED);
            
            dropItemsInOutputSlot(level, blockPos, blockEntity);
            
            if (crank > 0) {
                if (cranked < MincerBlock.CRANKS_NEEDED) {
                    cranked++;
                    MincerRecipe recipe = level.getRecipeManager().getRecipeFor(RecipeTypesRegistry.MINCER_RECIPE_TYPE.get(), blockEntity, level).orElse(null);
                    if (cranked == MincerBlock.CRANKS_NEEDED && recipe != null) {
                        
                        // recipe.getIngredients().forEach(ingredient -> {});
                        
                        // Player player = level.getNearestPlayer(blockPos.getX(), blockPos.getX(), blockPos.getX(), 8.0D, false);
                        // if (player != null && player.getOffhandItem().is(recipe.getIngredients().get(0).getItems()[0].getItem())) {
                        //     String recipe_type = recipe.getRecipeType();
                        //     int modifier = 0;
                        //     switch (recipe_type) {
                        //         case "MEAT" -> modifier = 20;
                        //         case "WOOD" -> modifier = 12;
                        //         case "STONE" -> modifier = 8;
                        //         case "METAL" -> modifier = 4;
                        //     }
                        //     cranked += modifier;
                        //     cranked = Math.min(cranked, MincerBlock.CRANKS_NEEDED);
                        // }
                        
                        ItemStack inputStack = this.stacks.get(INPUT_SLOT);
                        
                        inputStack.shrink(1);
                        
                        inputStack = inputStack.isEmpty() ? ItemStack.EMPTY : inputStack;
                        
                        blockEntity.setItem(INPUT_SLOT, inputStack);
                        blockEntity.setItem(OUTPUT_SLOT, recipe.getResultItem(level.registryAccess()));
                    }
                }

                crank -= 1;

                if (cranked >= MincerBlock.CRANKS_NEEDED) {
                    cranked = 0;
                }

                level.setBlock(blockPos, blockState.setValue(MincerBlock.CRANK, crank).setValue(MincerBlock.CRANKED, cranked), Block.UPDATE_ALL);
            } else if (cranked > 0 && cranked < MincerBlock.CRANKS_NEEDED) {
                level.setBlock(blockPos, blockState.setValue(MincerBlock.CRANKED, 0), Block.UPDATE_ALL);
            }
        }
    }
}
