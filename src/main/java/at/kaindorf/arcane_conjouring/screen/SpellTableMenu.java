package at.kaindorf.arcane_conjouring.screen;

import at.kaindorf.arcane_conjouring.init.BlockInit;
import at.kaindorf.arcane_conjouring.init.MenuTypeInit;
import at.kaindorf.arcane_conjouring.init.SpellCastInit;
import at.kaindorf.arcane_conjouring.item.wand.addon.SpellRingItem;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.SpellCast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpellTableMenu extends ItemCombinerMenu {
    private final Level level;
    private final Map<ItemStack, List<SpellCast>> ingredients = new HashMap();


    public SpellTableMenu(int id, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(MenuTypeInit.SPELL_TABLE_MENU.get(), id, inventory, containerLevelAccess);
        this.level = inventory.player.level;
        SpellCastInit.REGISTRY
                .get().forEach(cast ->
                        cast.getIngredients().forEach(stack -> {
                            ingredients.putIfAbsent(stack, new ArrayList<>());
                            ingredients.get(stack).add(cast);
                        })
                );
    }

    public SpellTableMenu(int id, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(id, inventory, ContainerLevelAccess.NULL);
    }

    @Override
    protected boolean mayPickup(Player player, boolean p_39799_) {
        return true;
    }

    @Override
    protected void onTake(Player player, ItemStack itemStack) {
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.access.execute((p_40263_, p_40264_) -> {
            p_40263_.levelEvent(1044, p_40264_, 0);
        });
    }

    private void shrinkStackInSlot(int slot) {
        ItemStack itemstack = this.inputSlots.getItem(slot);
        itemstack.shrink(1);
        this.inputSlots.setItem(slot, itemstack);
    }

    @Override
    protected boolean isValidBlock(BlockState blockState) {
        return blockState.is(BlockInit.SPELL_TABLE.get());
    }

    @Override
    public void createResult() {
        if (inputSlots.getItem(0).isEmpty() || inputSlots.getItem(1).isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            return;
        }
        ItemStack ring = this.inputSlots.getItem(0);
        if (!(ring.getItem() instanceof SpellRingItem)) return;
        ItemStack input = this.inputSlots.getItem(1);
        ItemStack result = ring.copy();
        ingredients.entrySet()
                .stream()
                //FIX: EFFECT IS ALWAYS SLOWNESS -> all potions are the same item
                .filter(entry -> {
                    ItemStack ingredient = entry.getKey();
                    return ingredient.sameItem(input) && (!ingredient.is(Items.POTION) ||
                            PotionUtils.getPotion(input).equals(PotionUtils.getPotion(ingredient)));
                })
                .map(entry -> entry.getValue())
                .forEach(casts -> {
                    casts.forEach(cast -> SpellRingItem.addCast(result, cast));
                    this.resultSlots.setItem(0, result);
                });
    }
}
