package at.kaindorf.arcane_conjouring.screen;

import at.kaindorf.arcane_conjouring.block.entity.WandWorkbenchBlockEntity;
import at.kaindorf.arcane_conjouring.init.BlockInit;
import at.kaindorf.arcane_conjouring.init.MenuTypeInit;
import at.kaindorf.arcane_conjouring.item.wand.WandItem;
import at.kaindorf.arcane_conjouring.item.wand.spell.SpellRingItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class WandWorkbenchMenu extends AbstractContainerMenu {

    public final WandWorkbenchBlockEntity blockEntity;
    private final Level level;

    public WandWorkbenchMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public WandWorkbenchMenu(int id, Inventory inv, BlockEntity entity) {
        super(MenuTypeInit.WAND_WORKBENCH_MENU.get(), id);
        checkContainerSize(inv, 3);
        blockEntity = (WandWorkbenchBlockEntity) entity;
        this.level = inv.player.level;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 80, 30) {
                @Override
                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    if (itemStack.getItem() instanceof WandItem) {
                        return super.mayPlace(itemStack);
                    }
                    return false;
                }

                @Override
                public void set(@NotNull ItemStack itemStack) {
                    super.set(itemStack);

                    if (itemStack.getItem() instanceof WandItem) {
                        itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                            slots.get(VANILLA_SLOT_COUNT + 1).set(handler.extractItem(0, 64, false));
                            slots.get(VANILLA_SLOT_COUNT + 2).set(handler.extractItem(1, 64, false));
                        });
                    }
                }

//                @Override
//                public void onTake(Player player, ItemStack itemStack) {
//                    super.onTake(player, itemStack);
//                    fillWand(itemStack);
//
//                }

                @Override
                public @NotNull ItemStack remove(int amount) {
                    fillWand(slots.get(VANILLA_SLOT_COUNT).getItem());
                    return super.remove(amount);
                }


            });
            class wandSlotHandler extends SlotItemHandler {
                public wandSlotHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
                    super(itemHandler, index, xPosition, yPosition);
                }

                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {

                    if (!slots.get(VANILLA_SLOT_COUNT).hasItem()) {
                        return false;
                    }

                    return super.mayPlace(stack);
                }
            }

            this.addSlot(new wandSlotHandler(handler, 1, 48, 30) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.getItem() instanceof SpellRingItem && super.mayPlace(stack);
                }
            });
            this.addSlot(new wandSlotHandler(handler, 2, 16, 30));
        });
    }

    private void fillWand(ItemStack itemStack) {
        if (itemStack.getItem() instanceof WandItem) {
            itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                handler.insertItem(0, slots.get(VANILLA_SLOT_COUNT + 1).getItem(), false);
                handler.insertItem(1, slots.get(VANILLA_SLOT_COUNT + 2).getItem(), false);

                slots.get(VANILLA_SLOT_COUNT + 1).set(ItemStack.EMPTY);
                slots.get(VANILLA_SLOT_COUNT + 2).set(ItemStack.EMPTY);
            });
        }
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            fillWand(sourceStack);
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                sourceSlot.set(sourceStack);
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, BlockInit.WAND_WORKBENCH.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
