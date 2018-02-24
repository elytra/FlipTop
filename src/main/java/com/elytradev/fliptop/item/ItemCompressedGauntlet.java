package com.elytradev.fliptop.item;

import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemCompressedGauntlet extends ItemBase {

    private final float attackDamage;
    private final ToolMaterial material;
    private boolean isThrowReady = false;

    public ItemCompressedGauntlet(String name) {
        super(name);
        material=ToolMaterial.IRON;
        setCreativeTab(CreativeTabs.COMBAT);
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
        attackDamage = 6.0F + material.getDamageVsEntity();
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (isThrowReady && attacker.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem()==ModItems.COMPRESSED_IRON_GAUNTLET) {
            target.addVelocity(0, 10, 0);
            stack.damageItem(50, attacker);
            attacker.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).damageItem(50, attacker);
            isThrowReady = false;
        } else {
            stack.damageItem(1, attacker);
        }
        return false;
    }

    public float getDamageVsEntity()
    {
        return this.material.getDamageVsEntity();
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = new ItemStack(ModItems.PRESS_PLATE);
        if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!world.isRemote && !isThrowReady) {
            if (player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem()==ModItems.COMPRESSED_IRON_GAUNTLET && player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem()==ModItems.COMPRESSED_IRON_GAUNTLET) {
                isThrowReady = true;
                player.sendMessage(new TextComponentTranslation("fliptop.msg.gauntletCharged"));
                return EnumActionResult.SUCCESS;
            } else {
                player.sendMessage(new TextComponentTranslation("fliptop.msg.gauntletChargeFail"));
            }
        }
        return EnumActionResult.FAIL;
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3.0F, 0));
        }

        return multimap;
    }
}
