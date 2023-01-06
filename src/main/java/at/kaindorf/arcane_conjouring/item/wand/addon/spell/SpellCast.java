package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.world.entity.LivingEntity;

public abstract class SpellCast {
    private final int cost;

    protected SpellCast(int cost) {
        this.cost = cost;
    }

    public void cast(CastingTarget target) {

    }

    public int getCost() {
        return cost;
    }
}
