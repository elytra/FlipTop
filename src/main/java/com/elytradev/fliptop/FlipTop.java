package com.elytradev.fliptop;

import com.elytradev.fliptop.util.FlipTopConfig;
import com.elytradev.fliptop.item.ModItems;
import com.elytradev.fliptop.proxy.CommonProxy;
import com.elytradev.fliptop.util.FlipTopRecipes;
import com.elytradev.fliptop.util.LootHandler;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = FlipTop.modId, name = FlipTop.name, version = FlipTop.version)
public class FlipTop {
    public static final String modId = "fliptop";
    public static final String name  = "FlipTop";
    public static final String version = "@VERSION@";
    public static FlipTopConfig config;

    @Mod.Instance(modId)
    public static FlipTop instance;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @SidedProxy(serverSide = "com.elytradev.fliptop.proxy.CommonProxy", clientSide = "com.elytradev.fliptop.proxy.ClientProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FlipTopLog.info("Don't flip out! " + name + " is loading!");
        MinecraftForge.EVENT_BUS.register(FlipTopRecipes.class);
        config = FlipTopConfig.createConfig(event);
        MinecraftForge.EVENT_BUS.register(new LootHandler());

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        //MinecraftForge.EVENT_BUS.register(new SoundRegisterListener());
        //MinecraftForge.EVENT_BUS.register(LightHandler.class);
        ModItems.registerOreDict(); // register oredict
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
        }

    }


}
