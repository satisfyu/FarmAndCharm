package net.satisfy.farm_and_charm.core.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.satisfy.farm_and_charm.core.block.entity.ChickenCoopBlockEntity;
import net.satisfy.farm_and_charm.core.entity.ChickenCoopAccess;
import net.satisfy.farm_and_charm.core.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class ChickenLocateCoopGoal extends Goal {
    private final Chicken chicken;
    private BlockPos foundCoop;
    private final List<BlockPos> cachedCoops = new ArrayList<>();
    private int searchCooldown = 0;

    public ChickenLocateCoopGoal(Chicken chicken) {
        this.chicken = chicken;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (chicken.isBaby()) return false;
        ChickenCoopAccess access = (ChickenCoopAccess) chicken;
        if (access.farmAndCharm$getCoopCooldown() > 0) return false;
        ServerLevel level = (ServerLevel) chicken.level();

        if (access.farmAndCharm$hasCoopTarget()) {
            if (isUsableCoop(level, access.farmAndCharm$getCoopTarget())) return false;
            access.farmAndCharm$clearCoopTarget();
        }

        if (searchCooldown > 0) {
            searchCooldown--;
            return false;
        }
        searchCooldown = 20 + chicken.getRandom().nextInt(20);
        Iterator<BlockPos> it = cachedCoops.iterator();
        while (it.hasNext()) {
            BlockPos cached = it.next();
            if (!isUsableCoop(level, cached)) {
                it.remove();
                continue;
            }
            if (chicken.getNavigation().createPath(cached, 0) != null) {
                foundCoop = cached;
                return true;
            }
        }
        BlockPos pos = chicken.blockPosition();
        foundCoop = BlockPos.findClosestMatch(pos, 16, 4, check -> {
            if (!isUsableCoop(level, check)) return false;
            if (chicken.getNavigation().createPath(check, 0) == null) return false;
            BlockPos immutable = check.immutable();
            if (!cachedCoops.contains(immutable)) cachedCoops.add(immutable);
            return true;
        }).map(BlockPos::immutable).orElse(null);
        return foundCoop != null;
    }

    private boolean isUsableCoop(ServerLevel level, BlockPos pos) {
        if (pos == null) return false;
        if (!level.getBlockState(pos).is(ObjectRegistry.CHICKEN_COOP.get())) return false;
        BlockEntity be = level.getBlockEntity(pos);
        return be instanceof ChickenCoopBlockEntity coop && coop.hasSpaceForChicken();
    }

    @Override
    public void start() {
        ((ChickenCoopAccess) chicken).farmAndCharm$setCoopTarget(foundCoop);
        if (!chicken.getNavigation().isInProgress()) {
            chicken.getNavigation().moveTo(foundCoop.getX() + 0.5, foundCoop.getY() + 0.5, foundCoop.getZ() + 0.5, 1.0);
        }
    }

    @Override
    public void stop() {
        foundCoop = null;
    }
}
