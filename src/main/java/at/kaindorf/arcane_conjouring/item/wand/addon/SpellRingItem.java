package at.kaindorf.arcane_conjouring.item.wand.addon;


import at.kaindorf.arcane_conjouring.init.ItemInit;
import at.kaindorf.arcane_conjouring.init.SpellCastInit;
import at.kaindorf.arcane_conjouring.item.wand.addon.spell.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class SpellRingItem extends Item implements IWandAddon{

    private Spell spell = new Spell(new ArrayList<>());



    public SpellRingItem(Properties properties) {
        super(properties);
    }

    public static ListTag getCasts(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null ? compoundTag.getList("Casts", 10) : new ListTag();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, components, tooltipFlag);
        ListTag listTag = getCasts(stack);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            ResourceLocation resourceLocation = ResourceLocation.tryParse(compoundTag.getString("location"));
            components.add(Component.translatable(resourceLocation.toLanguageKey()));

        }


    }

    public static void addCast(ItemStack stack, SpellCast cast) {
        ListTag listTag = getCasts(stack);
        SpellCastInit.REGISTRY.get().getResourceKey(cast).ifPresent((key) -> {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("location", key.location().toString());
            listTag.add(compoundTag);
            stack.getOrCreateTag().put("Casts", listTag);
        });
    }

    public static ItemStack createForCast(SpellCast cast) {
        ItemStack itemStack = new ItemStack(ItemInit.SPELL_RING.get());
        addCast(itemStack, cast);
        return itemStack;
    }

    @Override
    public void fillItemCategory(CreativeModeTab creativeModeTab, NonNullList<ItemStack> itemStacks) {
        if (creativeModeTab == CreativeModeTab.TAB_SEARCH) {
            for (SpellCast spellCast : SpellCastInit.REGISTRY.get()) {
                itemStacks.add(createForCast(spellCast));

            }
        }
    }

    public static void apply(ItemStack stack, CastingTarget target) {
        ListTag listTag = getCasts(stack);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            ResourceLocation resourceLocation = ResourceLocation.tryParse(compoundTag.getString("location"));
            SpellCastInit.REGISTRY.get().getValue(resourceLocation).cast(target);
        }
    }

    public int getCost() { return spell.getCost();}

    @Override
    public void setPoseStack(PoseStack poseStack) {
        poseStack.translate(0.0F, 0.8F, 0.0F);
    }
}
