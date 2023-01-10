package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BlockBreakSpellCast extends SpellCast{
    public BlockBreakSpellCast() {
        super(2);
        this.ingredients.add(new ItemStack(Items.NETHERITE_PICKAXE));
    }


    @Override
    public void cast(CastingTarget target) {
        super.cast(target);

        BlockPos block = target.getBlock();
        Level level = target.getLevel();
        if (block == null || level == null || level.getBlockState(block).equals(Blocks.BEDROCK.defaultBlockState())) return;
        level.destroyBlock(block, true);
    }
}
