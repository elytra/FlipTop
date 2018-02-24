package com.elytradev.fliptop.item;

import com.elytradev.fliptop.FlipTop;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static ItemBase PRESS_PLATE = new ItemBase("press_plate").setCreativeTab(CreativeTabs.MATERIALS);
    public static ItemGauntlet IRON_GAUNTLET = new ItemGauntlet("iron_gauntlet");
    public static ItemBase[] allItems = {
        PRESS_PLATE, IRON_GAUNTLET
    };

    public static void register(IForgeRegistry<Item> registry) {
        registry.registerAll(allItems);
    }

    public static void registerModels() {
        for (int i = 0; i < allItems.length ; i++) {
            allItems[i].registerItemModel();
        }
    }

    public static void registerOreDict() {
        for (int i = 0; i < allItems.length ; i++) {
            allItems[i].initOreDict();
        }
    }
}
