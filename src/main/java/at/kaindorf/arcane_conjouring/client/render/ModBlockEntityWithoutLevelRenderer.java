package at.kaindorf.arcane_conjouring.client.render;
import at.kaindorf.arcane_conjouring.client.model.ModModelLayers;
import at.kaindorf.arcane_conjouring.client.model.SpellRingModel;
import at.kaindorf.arcane_conjouring.client.model.WandModel;
import at.kaindorf.arcane_conjouring.client.model.WandTipModel;
import at.kaindorf.arcane_conjouring.init.ItemInit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;


public class ModBlockEntityWithoutLevelRenderer extends BlockEntityWithoutLevelRenderer {

    public static final ModBlockEntityWithoutLevelRenderer RENDERER = new ModBlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    private WandModel wandModel;
    private WandTipModel wandTipModel;
    private SpellRingModel spellRingModel;

    private final EntityModelSet entityModelSet;



    public ModBlockEntityWithoutLevelRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        System.out.println("create RENDERER");
        this.entityModelSet = entityModelSet;
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        System.out.println("\nresourcereload");
        this.wandModel = new WandModel(this.entityModelSet.bakeLayer(ModModelLayers.WAND));
        this.wandTipModel = new WandTipModel(this.entityModelSet.bakeLayer(ModModelLayers.WAND_TIP));
        this.spellRingModel = new SpellRingModel(this.entityModelSet.bakeLayer(ModModelLayers.SPELL_RING));
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        System.out.println("\nrender by Item");
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {

        } else {
            if (itemStack.is(ItemInit.WAND.get())) {
                poseStack.pushPose();
                poseStack.scale(1.0F, -1.0F, 1.0F);
                VertexConsumer wandVertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.wandModel.renderType(WandTipModel.TEXTURE), false, itemStack.hasFoil());
                this.wandModel.renderToBuffer(poseStack, wandVertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            } else if (itemStack.is(ItemInit.WAND_TIP.get())) {
                poseStack.pushPose();
                VertexConsumer wandVertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.wandTipModel.renderType(WandTipModel.TEXTURE), false, itemStack.hasFoil());
                this.wandTipModel.renderToBuffer(poseStack, wandVertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            } else if (itemStack.is(ItemInit.SPELL_RING.get())) {
                poseStack.pushPose();
                VertexConsumer wandVertexConsumer = ItemRenderer.getFoilBufferDirect(bufferSource, this.spellRingModel.renderType(SpellRingModel.TEXTURE), false, itemStack.hasFoil());
                this.spellRingModel.renderToBuffer(poseStack, wandVertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.popPose();
            }
        }
    }
}
