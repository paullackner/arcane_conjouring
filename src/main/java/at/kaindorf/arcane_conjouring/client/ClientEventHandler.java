package at.kaindorf.arcane_conjouring.client;

import at.kaindorf.arcane_conjouring.client.render.ModBlockEntityWithoutLevelRenderer;
import at.kaindorf.arcane_conjouring.client.render.WandDynamicModel;
import at.kaindorf.arcane_conjouring.init.ItemInit;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.SeparateTransformsModel;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientEventHandler {
    private ClientEventHandler() {}

    public static void register() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(ClientEventHandler::onModelRegistry);
        modBus.addListener(ClientEventHandler::registerReloadListener);
    }

    public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(ModBlockEntityWithoutLevelRenderer.RENDERER);
    }

    private static void onModelRegistry(ModelEvent.RegisterGeometryLoaders event) {
        event.register("wand", WandDynamicModel.Loader.INSTANCE);
    }
}
