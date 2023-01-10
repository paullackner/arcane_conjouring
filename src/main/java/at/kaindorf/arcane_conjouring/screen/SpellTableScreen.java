package at.kaindorf.arcane_conjouring.screen;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SpellTableScreen extends ItemCombinerScreen<SpellTableMenu> {
    private static final ResourceLocation SPELL_TABLE_LOCATION = new ResourceLocation(Arcane_conjouring.MODID, "textures/gui/spell_table_gui.png");


    public SpellTableScreen(SpellTableMenu spellTableMenu, Inventory inventory, Component component) {
        super(spellTableMenu, inventory, component, SPELL_TABLE_LOCATION);
        this.titleLabelX = 60;
        this.titleLabelY = 18;
    }

    protected void renderLabels(PoseStack poseStack, int p_99295_, int p_99296_) {
        RenderSystem.disableBlend();
        super.renderLabels(poseStack, p_99295_, p_99296_);
    }
}
