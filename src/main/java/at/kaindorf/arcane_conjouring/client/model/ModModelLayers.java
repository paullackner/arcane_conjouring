package at.kaindorf.arcane_conjouring.client.model;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;


public class ModModelLayers {

    public static final ModelLayerLocation WAND = register("item/wand");
    public static final ModelLayerLocation WAND_TIP = register("item/wand_tip");
    public static final ModelLayerLocation SPELL_RING = register("item/spell_ring");

    private static ModelLayerLocation register(String loc) {
        return new ModelLayerLocation(new ResourceLocation(Arcane_conjouring.MODID, loc), "main");
    }
}
