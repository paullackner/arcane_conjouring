package at.kaindorf.arcane_conjouring.item.wand.addon.spell;

import at.kaindorf.arcane_conjouring.init.SpellCastInit;
import at.kaindorf.arcane_conjouring.item.wand.addon.CastingTarget;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        return "SpellCast{\n" +
                SpellCastInit.REGISTRY.get().getResourceKey(this) + ",\n" +
                ", ingredients=" + ingredients.stream().map(itemStack -> PotionUtils.getMobEffects(itemStack).toString()).collect(Collectors.joining()) +
                "\n}";
    }
}
