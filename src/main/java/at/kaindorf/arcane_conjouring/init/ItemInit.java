package at.kaindorf.arcane_conjouring.init;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Arcane_conjouring.MODID);



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
