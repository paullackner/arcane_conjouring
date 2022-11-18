package at.kaindorf.arcane_conjouring.client.render;
import at.kaindorf.arcane_conjouring.init.ItemInit;
import at.kaindorf.arcane_conjouring.item.wand.spell.SpellRingItem;
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
import net.minecraftforge.items.ItemStackHandler;


public class ModBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {

    public static final ModBlockEntityWithoutLevelRenderer RENDERER = new ModBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());


    public ModBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        System.out.println("create RENDERER");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        System.out.println("\nresourcereload");
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        System.out.println("\nrender by Item");
        Item item = itemStack.getItem();
        Minecraft minecraft = Minecraft.getInstance();
        ItemRenderer itemRenderer = minecraft.getItemRenderer();
        if (item instanceof BlockItem) {

        } else {
            if (itemStack.is(ItemInit.WAND.get())) {
                poseStack.pushPose();
                poseStack.scale(1.0F, 1.0F, 1.0F);
                BakedModel model = itemRenderer.getModel(itemStack, null, minecraft.player, 0);
                RenderType renderType = RenderTypeHelper.getFallbackItemRenderType(itemStack, model, true);

                VertexConsumer vertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, renderType, false, itemStack.hasFoil());
                itemRenderer.renderModelLists(model, itemStack, packedLight, packedOverlay, poseStack, vertexConsumer);
                poseStack.popPose();
                itemStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                    if (handler.getStackInSlot(0).getItem() instanceof SpellRingItem) {
                        poseStack.pushPose();
                        poseStack.scale(1.0F, 1.0F, 1.0F);
                        ItemStack spellRing = handler.getStackInSlot(0);
                        BakedModel spellRingModel = itemRenderer.getModel(spellRing, null, minecraft.player, 0);
                        RenderType spellRingRenderType = RenderTypeHelper.getFallbackItemRenderType(itemStack, spellRingModel, true);
                        VertexConsumer spellRingVertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, spellRingRenderType, false, spellRing.hasFoil());
                        itemRenderer.renderModelLists(spellRingModel, spellRing, packedLight, packedOverlay, poseStack, spellRingVertexConsumer);

                        poseStack.popPose();
                    }
                });
            }
        }
    }
}
