package at.kaindorf.arcane_conjouring.block;

import at.kaindorf.arcane_conjouring.block.state.WandWorkbenchPart;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class WandWorkbenchBlock extends HorizontalDirectionalBlock {

    public static final EnumProperty<WandWorkbenchPart> PART = EnumProperty.create("part", WandWorkbenchPart.class);

    public WandWorkbenchBlock(Properties builder) {
        super(builder);
        this.registerDefaultState(this.getStateDefinition().any().setValue(PART, WandWorkbenchPart.FOOT));
    }
}
