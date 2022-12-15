package at.kaindorf.arcane_conjouring.item.wand;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WandItemProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private final LazyOptional<IItemHandler> lazyItemHandler;

    public WandItemProvider(ItemStackHandler itemHandler) {
        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return ((ItemStackHandler) lazyItemHandler.resolve().get()).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ((ItemStackHandler) lazyItemHandler.resolve().get()).deserializeNBT(nbt);
    }
}
