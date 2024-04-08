package satisfy.farm_and_charm.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import satisfy.farm_and_charm.registry.ObjectRegistry;
import de.cristelknight.doapi.common.block.FacingBlock;

import java.util.List;

@SuppressWarnings("deprecation")
public class TeaJugBlock extends FacingBlock {
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 2);

    public TeaJugBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FILL, 2));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FILL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        if (ctx.getPlayer() != null && ctx.getPlayer().isShiftKeyDown()) {
            return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
        }
        return null;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() == Items.GLASS_BOTTLE && state.getValue(FILL) > 0) {
            ItemStack newItemStack = new ItemStack(this.getReplacementItem());
            if (!player.getInventory().add(newItemStack)) {
                player.drop(newItemStack, false);
            }
            itemStack.shrink(1);
            int newFillLevel = state.getValue(FILL) - 1;
            if (newFillLevel > 0) {
                world.setBlock(pos, state.setValue(FILL, newFillLevel), 3);
            } else {
                world.removeBlock(pos, false);
                world.levelEvent(2001, pos, Block.getId(state));
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    private Item getReplacementItem() {
        if (this.equals(ObjectRegistry.STRAWBERRY_TEA.get())) {
            return ObjectRegistry.STRAWBERRY_TEA_CUP.get();
        } else if (this.equals(ObjectRegistry.NETTLE_TEA.get())) {
            return ObjectRegistry.NETTLE_TEA_CUP.get();
        } else if (this.equals(ObjectRegistry.RIBWORT_TEA.get())) {
            return ObjectRegistry.RIBWORT_TEA_CUP.get();
        }
        return Items.AIR;
    }


    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(5, 0, 5, 11, 10, 11);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, BlockGetter world, List<Component> tooltip, TooltipFlag tooltipContext) {
        tooltip.add(Component.translatable("tooltip.farm_and_charm.canbeplaced").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
