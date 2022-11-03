package at.kaindorf.arcane_conjouring.item.wand.spell;

import at.kaindorf.arcane_conjouring.client.render.ModBlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
import java.util.logging.Level;

public class WandTipItem extends Item {

    public WandTipItem(Properties properties) {
        super(properties);
    }

    public void cast(Player player, Level level, SpellRingItem spellRing) {
        spellRing.apply(player);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ModBlockEntityWithoutLevelRenderer.RENDERER;
            }
        });
    }
}
