package at.kaindorf.arcane_conjouring.world.feature;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.init.BlockInit;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModConfiguratedFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Arcane_conjouring.MODID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> VEILWOOD =
            CONFIGURED_FEATURES.register("veilwood", () ->
                    new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                           BlockStateProvider.simple(BlockInit.VEILWOOD_LOG.get()),
                           new StraightTrunkPlacer(6, 1, 2),
                           BlockStateProvider.simple(BlockInit.VEILWOOD_LEAVES.get()),
                           new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 4),
                           new TwoLayersFeatureSize(1, 0, 2)).build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> VEILWOOD_SPAWN =
            CONFIGURED_FEATURES.register("veilwood_spawn", () -> new ConfiguredFeature<>(Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                            ModPlacedFeatures.VEILWOOD_CHECKED.getHolder().get(),
                            0.5F)), ModPlacedFeatures.VEILWOOD_CHECKED.getHolder().get())));

    public static void register(IEventBus eventBus) {CONFIGURED_FEATURES.register(eventBus);}

}
