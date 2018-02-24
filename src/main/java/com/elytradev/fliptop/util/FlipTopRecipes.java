package com.elytradev.fliptop.util;

import com.elytradev.fliptop.item.ModItems;
import net.minecraft.init.Items;
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

        recipe(r, new ShapedOreRecipe(new ResourceLocation("fliptop:items"), new ItemStack(ModItems.IRON_GAUNTLET, 1),
                " p ", "psp", "p p",
                'p', new ItemStack(ModItems.PRESS_PLATE),
                's', new ItemStack(Items.STICK)
        ));
        recipe(r, new ShapedOreRecipe(new ResourceLocation("fliptop:items"), new ItemStack(ModItems.COMPRESSED_IRON_GAUNTLET, 1),
                "g g", " g ", "g g",
                'g', new ItemStack(ModItems.IRON_GAUNTLET)
        ));
    }

    public static <T extends IRecipe> T recipe(IForgeRegistry<IRecipe> registry, T t) {
        t.setRegistryName(new ResourceLocation(t.getRecipeOutput().getItem().getRegistryName()+"_"+t.getRecipeOutput().getItemDamage()));
        registry.register(t);
        return t;
    }

}
