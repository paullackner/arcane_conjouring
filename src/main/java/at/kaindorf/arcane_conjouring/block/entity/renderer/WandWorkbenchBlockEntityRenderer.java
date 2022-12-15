package at.kaindorf.arcane_conjouring.block.entity.renderer;

import at.kaindorf.arcane_conjouring.block.WandWorkbenchBlock;
import at.kaindorf.arcane_conjouring.block.entity.WandWorkbenchBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class WandWorkbenchBlockEntityRenderer implements BlockEntityRenderer<WandWorkbenchBlockEntity> {
    public WandWorkbenchBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WandWorkbenchBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        ItemStack itemStack = blockEntity.getRenderStack();
        poseStack.pushPose();
        poseStack.translate(0.625f, 1.1f, 0.5f);
        poseStack.scale(1f, 1f, 1f);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(90));

        switch (blockEntity.getBlockState().getValue(WandWorkbenchBlock.FACING)) {
            case WEST -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
            case EAST -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
            case SOUTH -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
            case NORTH -> poseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
        }

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, getLigthLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, 1);
        poseStack.popPose();
    }

    private int getLigthLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
