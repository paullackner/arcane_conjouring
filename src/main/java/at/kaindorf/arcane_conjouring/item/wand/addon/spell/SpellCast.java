package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class SpellCast {
    private final int cost;

    protected List<ItemStack> ingredients = new ArrayList<>();

    public SpellCast(int cost) {
        this.cost = cost;
    }

    public void cast(CastingTarget target) {

    }

    public List<ItemStack> getIngredients() {
        return ingredients;
    }

    public int getCost() {
        return cost;
    }
}
