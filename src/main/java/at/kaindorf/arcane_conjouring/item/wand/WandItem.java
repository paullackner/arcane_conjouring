package at.kaindorf.arcane_conjouring.item.wand;

import at.kaindorf.arcane_conjouring.client.render.ModBlockEntityWithoutLevelRenderer;
import at.kaindorf.arcane_conjouring.item.wand.spell.SpellRingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

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
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        System.out.println("\n\n\n\n\n\ninit\n\n\n\n\n\n\n\n");
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                System.out.println("\n\n\n\n\n\ngetRenderer\n\n\n\n\n\n\n\n");
                ModBlockEntityWithoutLevelRenderer ister = new ModBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
                return ister;
            }
        });
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
