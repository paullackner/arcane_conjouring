package at.kaindorf.arcane_conjouring.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {

    public static final CreativeModeTab ARCANE_CONJOURING_TAB = new CreativeModeTab("arcane_conjouring_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BlockInit.VEILWOOD_SAPLING.get());
        }
    };

    public static final CreativeModeTab ARCANE_CONJOURING_SPELLS = new CreativeModeTab("arcane_conjouring_spells") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.SPELL_RING.get());
        }
    };

}
