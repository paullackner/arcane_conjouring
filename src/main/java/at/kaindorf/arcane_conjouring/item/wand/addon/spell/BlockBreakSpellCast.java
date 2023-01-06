package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.TierSortingRegistry;

public class BlockBreakSpellCast extends SpellCast{

    Tier tier;
    public BlockBreakSpellCast(Tier tier) {
        super(2);
    }


    @Override
    public void cast(CastingTarget target) {
        super.cast(target);

        BlockPos block = target.getBlock();
        Level level = target.getLevel();
        if (block == null || level == null) return;

        level.destroyBlock(block, true);
    }
}
