package com.elytradev.fliptop;

import com.elytradev.fliptop.util.FlipTopConfig;
import com.elytradev.fliptop.block.ModBlocks;
import com.elytradev.fliptop.item.ModItems;
import com.elytradev.fliptop.proxy.CommonProxy;
import com.elytradev.fliptop.util.FlipTopRecipes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;


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
            ModBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public void lootLoad(LootTableLoadEvent evt) {
            if (evt.getName().toString().equals("minecraft:entities/iron_golem")) {
                // do stuff with evt.getTable()
                LootEntry entry = new LootEntryTable(new ResourceLocation("fliptop:inject/iron_golem"), 1, 1, new LootCondition[0], "golem_entry"); // weight doesn't matter since it's the only entry in the pool. Other params set as you wish.

                LootPool pool = new LootPool(new LootEntry[] {entry}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "golem_pool"); // Other params set as you wish.

                evt.getTable().addPool(pool);
            }
        }
    }


}
