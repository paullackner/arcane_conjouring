package at.kaindorf.arcane_conjouring.item.wand.addon;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class SpellProjectile extends Projectile {
    protected SpellProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {

    }
}
