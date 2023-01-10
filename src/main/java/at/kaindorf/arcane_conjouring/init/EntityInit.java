package at.kaindorf.arcane_conjouring.init;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.MagicBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraftforge.versions.forge.ForgeVersion.MOD_ID;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Arcane_conjouring.MODID);


    public static final RegistryObject<EntityType<MagicBolt>> MAGIC_BOLT =
            ENTITIES.register("magic_bolt", () ->
                    EntityType.Builder.<MagicBolt>of(MagicBolt::new, MobCategory.MISC)
                            .sized(1.0F, 1.0F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build(MOD_ID + ":magic_bolt"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
