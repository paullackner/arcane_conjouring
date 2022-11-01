package at.kaindorf.arcane_conjouring.client.model;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;


public class WandModel extends Model {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Arcane_conjouring.MODID, "textures/item/wand.png");
    private final ModelPart root;

    public WandModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.root = root;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int p_103113_, int p_103114_, float p_103115_, float p_103116_, float p_103117_, float p_103118_) {
        this.root.render(poseStack, vertexConsumer, p_103113_, p_103114_, p_103115_, p_103116_, p_103117_, p_103118_);
    }
}
