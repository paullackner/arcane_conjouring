package at.kaindorf.arcane_conjouring.init;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.screen.SpellTableMenu;
import at.kaindorf.arcane_conjouring.screen.WandWorkbenchMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Arcane_conjouring.MODID);

    public static final RegistryObject<MenuType<WandWorkbenchMenu>> WAND_WORKBENCH_MENU =
            registerMenuType(WandWorkbenchMenu::new, "wand_workbench_menu");
    public static final RegistryObject<MenuType<SpellTableMenu>> SPELL_TABLE_MENU =
            registerMenuType(SpellTableMenu::new, "spell_table_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
