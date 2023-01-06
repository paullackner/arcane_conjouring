package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.lang.annotation.Target;

public class EffectSpellCast extends SpellCast {
    private final MobEffect effect;
    private final int duration;
    private final int level;

    public EffectSpellCast(int cost, MobEffect effect, int duration, int level) {
        super(cost);
        this.effect = effect;
        this.duration = duration;
        this.level = level;
    }

    @Override
    public void cast(CastingTarget target) {

        super.cast(target);
        LivingEntity entity = target.getEntity();
        if (entity == null) return;

        entity.addEffect(new MobEffectInstance(effect, duration, level));
    }
}
