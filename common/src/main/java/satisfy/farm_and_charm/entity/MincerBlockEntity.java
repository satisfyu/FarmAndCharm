package satisfy.farm_and_charm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

    private NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);


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
    public int getContainerSize() {
        return stacks.size();
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


    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }


    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    public int filledSlots() {
        int i = 4;

        for(int j = 0; j < this.getContainerSize(); ++j) {
            if (this.getItem(j) == ItemStack.EMPTY) {
                i--;
            }
        }

        return i;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    public boolean canAddItem(ItemStack stack) {
        return this.canPlaceItem(0, stack) && filledSlots() < this.getContainerSize() - 1;
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
        return true;
    }

    private ItemStack getRemainderItem(ItemStack stack) {
        if (stack.getItem().hasCraftingRemainingItem()) {
            return new ItemStack(Objects.requireNonNull(stack.getItem().getCraftingRemainingItem()));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, MincerBlockEntity blockEntity) {
        if (!level.isClientSide && level.getBlockState(blockPos).getBlock() instanceof MincerBlock) {
            int crank = blockState.getValue(MincerBlock.CRANK);
            int cranked = blockState.getValue(MincerBlock.CRANKED);

            if (crank > 0) {
                if (cranked < MincerBlock.CRANKS_NEEDED) {
                    cranked++;
                    MincerRecipe recipe = level.getRecipeManager().getRecipeFor(RecipeTypesRegistry.MINCER_RECIPE_TYPE.get(), blockEntity, level).orElse(null);
                    if (cranked == MincerBlock.CRANKS_NEEDED && recipe != null) {
                        recipe.getIngredients().forEach(ingredient -> {
                            int size = blockEntity.getItems().size();
                            for (int slot = 0; slot < size; slot++) {
                                ItemStack stack = blockEntity.getItem(slot);
                                if (ingredient.test(stack)) {
                                    ItemStack remainder = getRemainderItem(stack);
                                    blockEntity.setItem(slot, ItemStack.EMPTY);
                                    if (!remainder.isEmpty()) {
                                        double offsetX = level.random.nextDouble() * 0.7D + 0.15D;
                                        double offsetY = level.random.nextDouble() * 0.7D + 0.15D;
                                        double offsetZ = level.random.nextDouble() * 0.7D + 0.15D;
                                        ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + offsetX, blockPos.getY() + offsetY, blockPos.getZ() + offsetZ, remainder);
                                        level.addFreshEntity(itemEntity);
                                    }
                                    break;
                                }
                            }
                        });
                        blockEntity.setItem(4, recipe.getResultItem(level.registryAccess()));
                    }
                }

                crank -= 1;
                level.setBlock(blockPos, blockState.setValue(MincerBlock.CRANK, crank).setValue(MincerBlock.CRANKED, cranked), 3);
            } else if (cranked > 0 && cranked < MincerBlock.CRANKS_NEEDED) {
                level.setBlock(blockPos, blockState.setValue(MincerBlock.CRANKED, 0), 3);
            }
        }
    }

}
