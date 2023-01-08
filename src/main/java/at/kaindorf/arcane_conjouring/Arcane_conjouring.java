package at.kaindorf.arcane_conjouring;

import at.kaindorf.arcane_conjouring.client.ClientEventHandler;
import at.kaindorf.arcane_conjouring.client.render.MagicBoltRenderer;
import at.kaindorf.arcane_conjouring.init.*;
import at.kaindorf.arcane_conjouring.network.ModMessages;
import at.kaindorf.arcane_conjouring.screen.SpellTableMenu;
import at.kaindorf.arcane_conjouring.screen.SpellTableScreen;
import at.kaindorf.arcane_conjouring.screen.WandWorkbenchScreen;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Arcane_conjouring.MODID)
public class Arcane_conjouring {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "arcane_conjouring";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "arcane_conjouring" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "arcane_conjouring" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public Arcane_conjouring() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ClientEventHandler.register();

        SpellCastInit.register(modEventBus);
        BlockInit.register(modEventBus);
        ItemInit.register(modEventBus);
        EntityInit.register(modEventBus);
        BlockEntityInit.register(modEventBus);
        MenuTypeInit.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("COMMON SETUP");

        event.enqueueWork(() -> {
            ModMessages.register();
        });

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            MenuScreens.register(MenuTypeInit.WAND_WORKBENCH_MENU.get(), WandWorkbenchScreen::new);
            MenuScreens.register(MenuTypeInit.SPELL_TABLE_MENU.get(), SpellTableScreen::new);

            EntityRenderers.register(EntityInit.MAGIC_BOLT.get(), MagicBoltRenderer::new);
        }
    }
}
