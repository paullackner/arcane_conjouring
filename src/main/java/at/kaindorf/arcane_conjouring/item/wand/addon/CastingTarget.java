package at.kaindorf.arcane_conjouring.item.wand.addon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;

public class CastingTarget {
    LivingEntity entity;
    BlockPos block;

    public CastingTarget(LivingEntity entity) {
        this.entity = entity;
    }
    public CastingTarget(BlockPos block) {
        this.block = block;
    }

    public CastingTarget() {}

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
}

    public void setBlock(BlockPos block) {
        this.block = block;
}

    public LivingEntity getEntity() {
        return entity;
    }

    public BlockPos getBlock() {
        return block;
    }
}
