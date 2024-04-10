package satisfy.farm_and_charm.entity.cart;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PlowCart extends CartEntity {

    public PlowCart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void move(MoverType moverType, Vec3 vec3) {
        super.move(moverType, vec3);
        if (this.onGround()) {
            BlockPos blockPos = this.getOnPos();
            Direction direction = Direction.fromYRot(this.getYRot());
            BlockPos behindPos = blockPos.relative(direction.getOpposite());
            if (this.level().getBlockState(behindPos).is(Blocks.GRASS_BLOCK))
                this.level().setBlockAndUpdate(behindPos, Blocks.FARMLAND.defaultBlockState());
            if (this.level().getBlockState(behindPos.relative(direction.getClockWise())).is(Blocks.GRASS_BLOCK))
                this.level().setBlockAndUpdate(behindPos.relative(direction.getClockWise()), Blocks.FARMLAND.defaultBlockState());
            if (this.level().getBlockState(behindPos.relative(direction.getCounterClockWise())).is(Blocks.GRASS_BLOCK))
                this.level().setBlockAndUpdate(behindPos.relative(direction.getCounterClockWise()), Blocks.FARMLAND.defaultBlockState());
        }
    }

    @Override
    protected float firstPoint() {
        return 3.0F;
    }

    @Override
    protected float lastPoint() {
        return 1.0F;
    }

    @Override
    protected float wheelRadius() {
        return 1.0F;
    }
}
