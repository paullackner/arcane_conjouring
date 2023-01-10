package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.lang.annotation.Target;
import java.util.stream.Collectors;

public class EffectSpellCast extends SpellCast {
    private final MobEffect effect;
    private final int duration;
    private final int level;

    public EffectSpellCast(int cost, MobEffect effect, int duration, int level) {
        super(cost);
        this.effect = effect;
        this.duration = duration;
        this.level = level;
        Registry.POTION.stream().forEach(potion -> {
            if (potion.getEffects()
                    .stream()
                    .map(MobEffectInstance::getEffect)
                    .collect(Collectors.toList())
                    .contains(effect)) {
                ItemStack ingredient = new ItemStack(Items.POTION);
                PotionUtils.setPotion(ingredient, potion);
                this.ingredients.add(ingredient);
            }
        });
    }

    @Override
    public void cast(CastingTarget target) {
        super.cast(target);

        LivingEntity entity = target.getEntity();
        if (entity != null) {
            entity.addEffect(new MobEffectInstance(effect, duration, level));
        }
    }
}
