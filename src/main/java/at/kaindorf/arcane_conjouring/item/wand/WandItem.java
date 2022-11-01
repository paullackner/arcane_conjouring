package at.kaindorf.arcane_conjouring.item.wand;

import at.kaindorf.arcane_conjouring.item.wand.spell.SpellRingItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack wand = player.getItemInHand(interactionHand);
        if (level.isClientSide) {
            return InteractionResultHolder.consume(wand);
        }

        wand.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            if (handler.getStackInSlot(0).getItem() instanceof SpellRingItem) {
                SpellRingItem spellRing = (SpellRingItem) handler.getStackInSlot(0).getItem();
                spellRing.apply(player);
            }
        });

        return InteractionResultHolder.success(wand);
    }
}
