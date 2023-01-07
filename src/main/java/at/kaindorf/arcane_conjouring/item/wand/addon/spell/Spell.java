package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.world.entity.LivingEntity;

import java.lang.annotation.Target;
import java.util.List;

public class Spell {
    private final List<SpellCast> casts;

    public Spell(List<SpellCast> casts) {
        this.casts = casts;
    }

    public void addCast(SpellCast cast) {
        casts.add(cast);
    }

    public int getCost() {
        return casts.stream().map(SpellCast::getCost).reduce(0, Integer::sum);
    }

    public void apply(CastingTarget target) {

        casts.forEach(c -> c.cast(target));
    }

}
