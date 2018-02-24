package com.elytradev.fliptop.item;

import com.elytradev.fliptop.util.FlipTopConfig;
import com.google.common.collect.Multimap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return isThrowReady;
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if(isThrowReady) {
            if (attacker.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == ModItems.IRON_GAUNTLET) {
                target.addVelocity(0, 1, 0);
                stack.damageItem(10, attacker);
                attacker.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).damageItem(10, attacker);
                isThrowReady = false;
                attacker.playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1, 1);
            } else {
                isThrowReady = false;
            }
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

    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote && !isThrowReady && handIn == EnumHand.MAIN_HAND) {
            if (playerIn.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == ModItems.IRON_GAUNTLET && playerIn.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == ModItems.IRON_GAUNTLET) {
                playerIn.setActiveHand(handIn);
                return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
            }
        }
        if(handIn == EnumHand.OFF_HAND) {
            return ActionResult.newResult(EnumActionResult.PASS, itemstack);
        }
        return ActionResult.newResult(EnumActionResult.FAIL, itemstack);
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 1200;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            if (this.getMaxItemUseDuration(stack) - timeLeft >= FlipTopConfig.chargeTime) {
                isThrowReady = true;
                entityLiving.playSound(SoundEvents.ENTITY_IRONGOLEM_HURT, 1, 1);
            }
        }
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
