package at.kaindorf.arcane_conjouring.item.wand.addon;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class WandTipItem extends Item implements IWandAddon {

    public WandTipItem(Properties properties) {
        super(properties);
    }

    private CastingTarget target = new CastingTarget();

    public void cast(SpellRingItem spellRing, Level level, LivingEntity entity) {


        spellRing.apply(target);
    }

    @Override
    public void setPoseStack(PoseStack poseStack) {
        poseStack.translate(0.0F, 0.95F, 0.0F);
    }
}
