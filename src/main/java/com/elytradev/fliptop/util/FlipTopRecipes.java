package com.elytradev.fliptop.util;

import com.elytradev.fliptop.item.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class FlipTopRecipes {

    @SubscribeEvent
    public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {

        IForgeRegistry<IRecipe> r = event.getRegistry();

        // Crafting bench recipes

        if(!FlipTopConfig.disablePlateCrafting) {
            recipe(r, new ShapedOreRecipe(new ResourceLocation("fliptop:items"), new ItemStack(ModItems.PRESS_PLATE, 4),
                    " p ", "bbb", " b ",
                    'p', new ItemStack(Item.getItemFromBlock(Blocks.PUMPKIN)),
                    'b', new ItemStack(Item.getItemFromBlock(Blocks.IRON_BLOCK))
            ));
        }
        recipe(r, new ShapedOreRecipe(new ResourceLocation("fliptop:items"), new ItemStack(ModItems.IRON_GAUNTLET, 1),
                " p ", "psp", "p p",
                'p', new ItemStack(ModItems.PRESS_PLATE),
                's', new ItemStack(Items.STICK)
        ));
        if(!FlipTopConfig.trueCompression) {
            recipe(r, new ShapedOreRecipe(new ResourceLocation("fliptop:items"), new ItemStack(ModItems.COMPRESSED_IRON_GAUNTLET, 1),
                    "g g", " g ", "g g",
                    'g', new ItemStack(ModItems.IRON_GAUNTLET)
            ));
        } else {
            recipe(r, new ShapedOreRecipe(new ResourceLocation("fliptop:items"), new ItemStack(ModItems.COMPRESSED_IRON_GAUNTLET, 1),
                    "ggg", "ggg", "ggg",
                    'g', new ItemStack(ModItems.IRON_GAUNTLET)
            ));
        }
    }

    public static <T extends IRecipe> T recipe(IForgeRegistry<IRecipe> registry, T t) {
        t.setRegistryName(new ResourceLocation(t.getRecipeOutput().getItem().getRegistryName()+"_"+t.getRecipeOutput().getItemDamage()));
        registry.register(t);
        return t;
    }

}
