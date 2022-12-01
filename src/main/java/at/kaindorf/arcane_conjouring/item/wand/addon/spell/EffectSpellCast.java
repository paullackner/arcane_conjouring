package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

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
    public void cast(LivingEntity entity) {
        super.cast(entity);
        entity.addEffect(new MobEffectInstance(effect, duration, level));
    }
}
