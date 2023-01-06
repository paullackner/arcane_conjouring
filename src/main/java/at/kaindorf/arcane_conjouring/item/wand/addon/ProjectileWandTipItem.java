package at.kaindorf.arcane_conjouring.item.wand.addon;

import at.kaindorf.arcane_conjouring.item.wand.addon.spell.MagicBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ProjectileWandTipItem extends WandTipItem{
    public ProjectileWandTipItem(Properties properties) {
        super(properties);
    }

    @Override
    public void cast(SpellRingItem spellRing, Level level, LivingEntity entity) {
        MagicBolt magicBolt = new MagicBolt(level, entity, spellRing);

        magicBolt.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 0.5F, 1.0F);
        level.addFreshEntity(magicBolt);
    }


}
