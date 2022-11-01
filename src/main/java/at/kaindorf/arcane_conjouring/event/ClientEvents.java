package at.kaindorf.arcane_conjouring.event;

import at.kaindorf.arcane_conjouring.Arcane_conjouring;
import at.kaindorf.arcane_conjouring.block.entity.renderer.WandWorkbenchBlockEntityRenderer;
import at.kaindorf.arcane_conjouring.init.BlockEntityInit;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Arcane_conjouring.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(BlockEntityInit.WAND_WORKBENCH.get(), WandWorkbenchBlockEntityRenderer::new);
        }
    }

}
