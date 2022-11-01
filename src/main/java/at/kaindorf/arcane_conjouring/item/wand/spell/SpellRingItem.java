package at.kaindorf.arcane_conjouring.item.wand.spell;

import at.kaindorf.arcane_conjouring.client.render.ModBlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SpellRingItem extends Item {

    private MobEffect effect = MobEffects.REGENERATION;


    public SpellRingItem(Properties properties) {
        super(properties);
    }

    public void apply(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(effect, 1000, 0) );
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
