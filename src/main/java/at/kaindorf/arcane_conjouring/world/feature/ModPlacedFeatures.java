package at.kaindorf.arcane_conjouring.world.feature;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.init.BlockInit;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Arcane_conjouring.MODID);

    public static final RegistryObject<PlacedFeature> VEILWOOD_CHECKED = PLACED_FEATURES.register("veilwood_checked",
            () -> new PlacedFeature(ModConfiguratedFeatures.VEILWOOD.getHolder().get(),
                    List.of(PlacementUtils.filteredByBlockSurvival(BlockInit.VEILWOOD_SAPLING.get()))));

    public static final RegistryObject<PlacedFeature> VEILWOOD_PLACED = PLACED_FEATURES.register("veilwood_placed",
            () -> new PlacedFeature(ModConfiguratedFeatures.VEILWOOD_SPAWN.getHolder().get(), VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(0, 0.02f, 1))));

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }

}
