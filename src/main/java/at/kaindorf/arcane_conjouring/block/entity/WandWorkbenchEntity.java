package at.kaindorf.arcane_conjouring.block.entity;

import at.kaindorf.arcane_conjouring.init.BlockEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class WandWorkbenchEntity extends BlockEntity {

//    private final ItemStackHandler itemHandler = new ItemStackHandler() {
//        @Override
//        protected void onContentsChanged(int slot) {
//            setChanged();
//        }
//    };
//
//    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public WandWorkbenchEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.WAND_WORKBENCH.get() , pos, state);
    }

//    @Override
//    public Component getDisplayName() {
//        return Component.literal("Wand Workbench");
//    }
//
//    public ClientboundBlockEntityDataPacket getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//
//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int p_39954_, Inventory inventory, Player player) {
//        return null;
//    }
//
//    @Override
//    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//        if (cap == ForgeCapabilities.ITEM_HANDLER) {
//            return  lazyItemHandler.cast();
//        }
//
//        return super.getCapability(cap, side);
//    }
//
//    @Override
//    public void onLoad() {
//        super.onLoad();
//        lazyItemHandler = LazyOptional.of(() -> itemHandler);
//    }
//
//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        lazyItemHandler.invalidate();
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag nbt) {
//        nbt.put("inventory", itemHandler.serializeNBT());
//
//        super.saveAdditional(nbt);
//    }
//
//    @Override
//    public void load(CompoundTag nbt) {
//        super.load(nbt);
//        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
//    }
//
//    public void drops() {
//        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
//
//        for (int i = 0; i < itemHandler.getSlots(); i++) {
//            inventory.setItem(i, itemHandler.getStackInSlot(i));
//        }
//
//        Containers.dropContents(this.level, this.worldPosition, inventory);
//    }

    public static <E extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, WandWorkbenchEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }
    }
}
