package at.kaindorf.arcane_conjouring.item.wand.addon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class CastingTarget {
    LivingEntity entity;
    BlockPos block;
    Level level;

    public CastingTarget(LivingEntity entity) {
        this.entity = entity;
    }
    public CastingTarget(BlockPos block) {
        this.block = block;
    }

    public CastingTarget(BlockPos block, Level level) {
        this.block = block;
        this.level = level;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
