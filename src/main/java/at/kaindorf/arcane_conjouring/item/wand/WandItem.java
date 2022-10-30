package at.kaindorf.arcane_conjouring.item.wand;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class WandItem extends Item {

    public WandItem(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        ItemStackHandler itemHandler = new ItemStackHandler(2);
        return new WandItemProvider(itemHandler);
    }
}
