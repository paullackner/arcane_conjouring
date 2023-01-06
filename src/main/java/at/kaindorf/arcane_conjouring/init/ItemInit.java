package at.kaindorf.arcane_conjouring.init;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.item.wand.WandItem;
import at.kaindorf.arcane_conjouring.item.wand.addon.ProjectileWandTipItem;
import at.kaindorf.arcane_conjouring.item.wand.addon.SpellRingItem;
import at.kaindorf.arcane_conjouring.item.wand.addon.WandTipItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Arcane_conjouring.MODID);

    public static final RegistryObject<Item> WAND = ITEMS.register("wand",
            () -> new WandItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)) );

    public static final RegistryObject<Item> SPELL_RING = ITEMS.register("spell_ring",
            () -> new SpellRingItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)) );

    public static final RegistryObject<Item> WAND_TIP = ITEMS.register("wand_tip",
            () -> new ProjectileWandTipItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)) );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
