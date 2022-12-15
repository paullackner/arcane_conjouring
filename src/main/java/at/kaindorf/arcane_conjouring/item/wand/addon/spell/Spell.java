package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class Spell {
    private final int cost;
    private final List<SpellCast> casts;

    public Spell(List<SpellCast> casts) {
        this.casts = casts;
        this.cost = casts.stream().map(SpellCast::getCost).reduce(0, Integer::sum);
    }

    public int getCost() {
        return cost;
    }

    public void apply(LivingEntity entity) {
        casts.forEach(c -> c.cast(entity));
    }

}