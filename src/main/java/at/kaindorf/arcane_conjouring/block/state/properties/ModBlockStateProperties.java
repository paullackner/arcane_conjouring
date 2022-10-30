package at.kaindorf.arcane_conjouring.block.state.properties;

import at.kaindorf.arcane_conjouring.block.state.WandWorkbenchPart;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockStateProperties {
    public static final EnumProperty<WandWorkbenchPart> WAND_WORKBENCH_PART = EnumProperty.create("part", WandWorkbenchPart.class);
}
