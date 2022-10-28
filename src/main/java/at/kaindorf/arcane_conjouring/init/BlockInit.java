package at.kaindorf.arcane_conjouring.init;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.block.WandWorkbenchBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Arcane_conjouring.MODID);

    public static final RegistryObject<Block> WAND_WORKBENCH = registerBlock("wand_workbench",
            () -> new WandWorkbenchBlock(BlockBehaviour.Properties.of(Material.WOOD)
                    .noOcclusion().strength(3f).sound(SoundType.WOOD)), CreativeModeTab.TAB_MISC);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        registerBlockItem(name, registryObject, tab);
        return registryObject;
    }

    public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

}
