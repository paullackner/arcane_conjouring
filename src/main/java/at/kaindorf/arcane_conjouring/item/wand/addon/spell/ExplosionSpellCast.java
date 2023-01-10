package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class ExplosionSpellCast extends SpellCast{

    Explosion.BlockInteraction blockInteraction;

    public ExplosionSpellCast(Explosion.BlockInteraction blockInteraction) {
        super(25);
        this.blockInteraction = blockInteraction;
        this.ingredients.add(new ItemStack(Items.TNT));
    }


    @Override
    public void cast(CastingTarget target) {
        super.cast(target);

        BlockPos block = target.getBlock();
        Level level = target.getLevel();
        LivingEntity entity = target.getEntity();
        if (block == null || level == null) {
            block = entity.getOnPos();
            level = entity.getLevel();
        }

        level.explode((Entity) null, block.getX(), block.getY(), block.getZ(), 2.0F, blockInteraction);
    }
}
