package at.kaindorf.arcane_conjouring.screen;

import at.kaindorf.arcane_conjouring.block.entity.SpellTableBlockEntity;
import at.kaindorf.arcane_conjouring.init.BlockInit;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SpellTableMenu extends ItemCombinerMenu {


    public SpellTableMenu(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @Override
    protected boolean mayPickup(Player player, boolean p_39799_) {
        return false;
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {

    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return blockState.is(BlockInit.SPELL_TABLE.get());
    }

    @Override
    public void createResult() {

    }
}
