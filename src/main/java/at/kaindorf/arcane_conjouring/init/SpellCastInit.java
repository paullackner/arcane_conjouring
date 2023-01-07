package at.kaindorf.arcane_conjouring.init;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.BlockBreakSpellCast;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.EffectSpellCast;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.ExplosionSpellCast;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.SpellCast;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static at.kaindorf.arcane_conjouring.Arcane_conjouring.MODID;

public class SpellCastInit {
    public static final DeferredRegister<SpellCast> SPELL_CASTS =
            DeferredRegister.create(new ResourceLocation(MODID, "spell_cast"), MODID);

    public static final Supplier<IForgeRegistry<SpellCast>> REGISTRY = SPELL_CASTS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<SpellCast> BLOCK_BREAK_SPELL_CAST = SPELL_CASTS.register("block_break",
            () -> new BlockBreakSpellCast());

    public static final RegistryObject<SpellCast> EXPLOSION_SPELL_CAST = SPELL_CASTS.register("explosion",
            () -> new ExplosionSpellCast(Explosion.BlockInteraction.BREAK));

    public static void register(IEventBus eventBus) {
        SPELL_CASTS.register(eventBus);
    }
}
