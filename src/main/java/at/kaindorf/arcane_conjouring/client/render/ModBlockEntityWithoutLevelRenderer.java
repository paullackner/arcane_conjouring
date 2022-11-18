package at.kaindorf.arcane_conjouring.client.render;
import at.kaindorf.arcane_conjouring.init.ItemInit;
import at.kaindorf.arcane_conjouring.item.wand.addon.IWandAddon;
import at.kaindorf.arcane_conjouring.item.wand.addon.SpellRingItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.common.capabilities.ForgeCapabilities;


public class ModBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {

    public static final ModBlockEntityWithoutLevelRenderer RENDERER = new ModBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());


    public ModBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {}

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Item item = itemStack.getItem();
        Minecraft minecraft = Minecraft.getInstance();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        if (item instanceof BlockItem) {

        } else {
            if (itemStack.is(ItemInit.WAND.get())) {
                poseStack.pushPose();
                poseStack.scale(1.0F, 1.0F, 1.0F);
                render(itemStack, poseStack, bufferSource, packedLight, packedOverlay, minecraft, itemRenderer, itemStack);
                itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                    for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack addon = handler.getStackInSlot(i);
                        if (addon.getItem() instanceof IWandAddon) {
                            poseStack.pushPose();
                            poseStack.scale(1.0F, 1.0F, 1.0F);
                            ((IWandAddon) addon.getItem()).setPoseStack(poseStack);
                            render(itemStack, poseStack, bufferSource, packedLight, packedOverlay, minecraft, itemRenderer, addon);
                        }
                    }
                });
            }
        }
    }

    private void render(ItemStack itemStack, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Minecraft minecraft, ItemRenderer itemRenderer, ItemStack item) {
        BakedModel addonModel = itemRenderer.getModel(item, null, minecraft.player, 0);
        RenderType addonRenderType = RenderTypeHelper.getFallbackItemRenderType(itemStack, addonModel, true);
        VertexConsumer addonVertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, addonRenderType, false, item.hasFoil());
        itemRenderer.renderModelLists(addonModel, item, packedLight, packedOverlay, poseStack, addonVertexConsumer);
        poseStack.popPose();
    }
}
