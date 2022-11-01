package at.kaindorf.arcane_conjouring.item.wand.spell;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.logging.Level;

public class WandTipItem extends Item {

    public WandTipItem(Properties properties) {
        super(properties);
    }

    public void cast(Player player, Level level, SpellRingItem spellRing) {
        spellRing.apply(player);
    }
}
