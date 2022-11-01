package at.kaindorf.arcane_conjouring.item.wand.spell;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class SpellRingItem extends Item {

    private MobEffect effect = MobEffects.REGENERATION;


    public SpellRingItem(Properties properties) {
        super(properties);
    }

    public void apply(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(effect, 1000, 0) );
    }
}
