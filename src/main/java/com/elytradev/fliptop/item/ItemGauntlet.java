package com.elytradev.fliptop.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGauntlet extends ItemBase {

    private final float attackDamage;
    private final Item.ToolMaterial material;
    private boolean isThrowReady = false;

    public ItemGauntlet(String name) {
        super(name);
        material=ToolMaterial.IRON;
        setCreativeTab(CreativeTabs.COMBAT);
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
        attackDamage = 3.0F + material.getDamageVsEntity();
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (isThrowReady && attacker.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem()==ModItems.IRON_GAUNTLET) {
            target.addVelocity(0, 10, 0);
            stack.damageItem(10, attacker);
            attacker.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).damageItem(10, attacker);
        }
        stack.damageItem(1, attacker);
        return false;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = new ItemStack(ModItems.PRESS_PLATE);
        if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack offhand = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
        if(offhand.getItem() == ModItems.IRON_GAUNTLET) {
            isThrowReady = true;
        }
        return EnumActionResult.FAIL;
    }
}
