package at.kaindorf.arcane_conjouring.init;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.block.ModFlammableRotatedPillarBlock;
import at.kaindorf.arcane_conjouring.world.feature.tree.VeilwoodTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Arcane_conjouring.MODID);

    public static final RegistryObject<Block> WAND_WORKBENCH = registerBlock("wand_workbench",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion().strength(3f).sound(SoundType.WOOD)),
            ModCreativeModeTab.ARCANE_CONJOURING_TAB);

    public static final RegistryObject<Block> VEILWOOD_LOG = registerBlock("veilwood_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)),
            ModCreativeModeTab.ARCANE_CONJOURING_TAB);
    public static final RegistryObject<Block> VEILWOOD_WOOD = registerBlock("veilwood_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)),
            ModCreativeModeTab.ARCANE_CONJOURING_TAB);
    public static final RegistryObject<Block> STRIPPED_VEILWOOD_LOG = registerBlock("stripped_veilwood_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
            ModCreativeModeTab.ARCANE_CONJOURING_TAB);
    public static final RegistryObject<Block> STRIPPED_VEILWOOD_WOOD = registerBlock("stripped_veilwood_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)),
            ModCreativeModeTab.ARCANE_CONJOURING_TAB);

    public static final RegistryObject<Block> VEILWOOD_PLANKS = registerBlock("veilwood_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }
            }, ModCreativeModeTab.ARCANE_CONJOURING_TAB);

    public static final RegistryObject<Block> VEILWOOD_LEAVES = registerBlock("veilwood_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 30;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 60;
                }
            }, ModCreativeModeTab.ARCANE_CONJOURING_TAB);

    public static final RegistryObject<Block> VEILWOOD_SAPLING = registerBlock("veilwood_sapling",
            () -> new SaplingBlock(new VeilwoodTreeGrower(), BlockBehaviour.Properties.copy(Blocks.BIRCH_SAPLING)),
            ModCreativeModeTab.ARCANE_CONJOURING_TAB);

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> registryObject = BLOCKS.register(name, block);
        registerBlockItem(name, registryObject, tab);
        return registryObject;
    }

    public static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

}
