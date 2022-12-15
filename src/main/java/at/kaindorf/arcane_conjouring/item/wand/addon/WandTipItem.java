package at.kaindorf.arcane_conjouring.item.wand.addon;

import at.kaindorf.arcane_conjouring.client.render.ModBlockEntityWithoutLevelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
import java.util.logging.Level;

public class WandTipItem extends Item implements IWandAddon {

    public WandTipItem(Properties properties) {
        super(properties);
    }

    public void cast(Player player, Level level, SpellRingItem spellRing) {
        spellRing.apply(player);
    }

    @Override
    public void setPoseStack(PoseStack poseStack) {
        poseStack.translate(0.0F, 0.95F, 0.0F);
    }
}
