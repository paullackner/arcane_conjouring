package at.kaindorf.arcane_conjouring.item.wand.addon;

import at.kaindorf.arcane_conjouring.client.render.ModBlockEntityWithoutLevelRenderer;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.EffectSpellCast;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.Spell;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.SpellCast;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class SpellRingItem extends Item implements IWandAddon{

    private Spell spell = new Spell(Arrays.asList(new EffectSpellCast(100, MobEffects.REGENERATION, 10, 1)));


    public SpellRingItem(Properties properties) {
        super(properties);
    }

    public void apply(CastingTarget target) {
        spell.apply(target);
    }

    @Override
    public void setPoseStack(PoseStack poseStack) {
        poseStack.translate(0.0F, 0.8F, 0.0F);
    }
}
