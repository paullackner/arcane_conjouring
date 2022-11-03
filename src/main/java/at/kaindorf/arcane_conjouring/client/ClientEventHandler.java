package at.kaindorf.arcane_conjouring.client;

import at.kaindorf.arcane_conjouring.client.render.WandDynamicModel;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.model.CompositeModel;
import net.minecraftforge.client.model.SeparateTransformsModel;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientEventHandler {
    private ClientEventHandler() {}

    public static void register() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(ClientEventHandler::onModelRegistry);
    }

    private static void onModelRegistry(ModelEvent.RegisterGeometryLoaders event) {
        event.register("wand", WandDynamicModel.Loader.INSTANCE);
    }
}
