package gregtechmod.api.util;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IDebugableBlock;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.interfaces.IUpgradableMachine;
import gregtechmod.api.items.GT_EnergyArmor_Item;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * Just a few Utility Functions I use.
 */
public class GT_Utility {
	public static volatile int VERSION = 303;
	
	/**
	 * You give this Function a Material and it will scan almost everything for adding recycling Recipes
	 * 
	 * @param aMat a Material, for example an Ingot or a Gem.
	 * @param aOutput the Dust you usually get from macerating aMat
	 * @param aBackSmelting allows to reverse smelt into aMat (false for Gems)
	 * @param aBackMacerating allows to reverse macerate into aMat
	 */
	public static void applyUsagesForMaterials(ItemStack aMat, ItemStack aOutput, boolean aBackSmelting, boolean aBackMacerating) {
		if (aMat == null || aOutput == null) return;
		aMat = aMat.copy();
		aOutput = aOutput.copy();
		ItemStack tMt2 = new ItemStack(Item.stick, 1), tStack;
		ArrayList<ItemStack> tList;
		if (aOutput.stackSize < 1) aOutput.stackSize = 1;
		boolean bSawdust = GT_OreDictUnificator.isItemStackInstanceOf(aOutput, "dustSmallWood", false);
		
		if (!aMat.isItemEqual(new ItemStack(Item.ingotIron, 1))) {
			if ((tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {aMat, null, aMat, null, aMat, null, null, null, null})) != null)
				if (tStack.isItemEqual(new ItemStack(Item.bucketEmpty, 1)))
					GT_ModHandler.removeRecipe(new ItemStack[] {aMat, null, aMat, null, aMat, null, null, null, null});
			if ((tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, null, null, aMat, null, aMat, null, aMat, null})) != null)
				if (tStack.isItemEqual(new ItemStack(Item.bucketEmpty, 1)))
					GT_ModHandler.removeRecipe(new ItemStack[] {null, null, null, aMat, null, aMat, null, aMat, null});
			if ((tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {aMat, null, aMat, aMat, aMat, aMat, null, null, null})) != null)
				if (tStack.isItemEqual(new ItemStack(Item.minecartEmpty, 1)))
					GT_ModHandler.removeRecipe(new ItemStack[] {aMat, null, aMat, aMat, aMat, aMat, null, null, null});
			if ((tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, null, null, aMat, null, aMat, aMat, aMat, aMat})) != null)
				if (tStack.isItemEqual(new ItemStack(Item.minecartEmpty, 1)))
					GT_ModHandler.removeRecipe(new ItemStack[] {null, null, null, aMat, null, aMat, aMat, aMat, aMat});
		}
		
		int aOutItem = aOutput.itemID, aOutDamage = aOutput.getItemDamage(), aOutAmount = aOutput.stackSize;
		int aInItem = aMat.itemID, aInDamage = aMat.getItemDamage(), aInAmount = aMat.stackSize;
    	
		tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, aMat, aMat, aMat, null, aMat, null, null, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 5*aOutAmount+(bSawdust?0:0), aOutDamage), null, 0, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 5, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, null, aMat, aMat, aMat, aMat, aMat, aMat, aMat}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 8*aOutAmount+(bSawdust?0:0), aOutDamage), null, 0, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 8, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, aMat, aMat, aMat, null, aMat, aMat, null, aMat}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 7*aOutAmount+(bSawdust?0:0), aOutDamage), null, 0, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 7, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, null, null, aMat, null, aMat, aMat, null, aMat}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 4*aOutAmount+(bSawdust?0:0), aOutDamage), null, 0, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 4, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, null, null, aMat, null, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 2*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 2, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, aMat, aMat, aMat, tMt2, aMat, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 5*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 5, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, aMat, aMat, null, tMt2, null, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, null, null, tMt2, null, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, aMat, null, aMat, tMt2, null, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, aMat, null, tMt2, aMat, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, aMat, null, null, tMt2, null, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 2*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 2, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, aMat, null, tMt2, null, null, tMt2, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 2*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 2, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, null, aMat, null, null, null, aMat, tMt2}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, null, null, null, aMat, tMt2, aMat, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, null, aMat, null, aMat, null, null, tMt2}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, null, aMat, null, aMat, tMt2, null, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, aMat, null, null, tMt2, null, null, aMat, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 2*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 2, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, tMt2, null, null, tMt2, null, aMat, aMat, aMat}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, tMt2, null, null, tMt2, null, null, aMat, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, tMt2, null, null, tMt2, null, aMat, aMat, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, tMt2, aMat, null, tMt2, null, null, aMat, aMat}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 3*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 3, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, tMt2, null, null, tMt2, null, aMat, aMat, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 2*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 2, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, null, null, null, tMt2, null, null, null, tMt2}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, null, aMat, null, tMt2, null, tMt2, null, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?4:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1), 100, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, null, null, null, tMt2, null, null, null, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {null, null, aMat, null, tMt2, null, null, null, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, tMt2, null, null, null, null, null, null, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	    tList = GT_ModHandler.getRecipeOutputs(new ItemStack[] {aMat, null, null, tMt2, null, null, null, null, null}); for (int i = 0; i < tList.size(); i++) {if (aBackMacerating) GT_ModHandler.addPulverisationRecipe(tList.get(i), new ItemStack(aOutItem, 1*aOutAmount+(bSawdust?2:0), aOutDamage), bSawdust?null:GT_OreDictUnificator.get("dustWood", null, 1),  50, false); if (aBackSmelting && tList.get(i).stackSize == 1 && aInAmount == 1) GT_ModHandler.addSmeltingRecipe(tList.get(i), new ItemStack(aInItem, 1, aInDamage));}
	}
	
	public static Field getPublicField(Object aObject, String aField) {
		Field rField = null;
		try {
			rField = aObject.getClass().getDeclaredField(aField);
		} catch (Throwable e) {}
		return rField;
	}
	
	public static Field getField(Object aObject, String aField) {
		Field rField = null;
		try {
			rField = aObject.getClass().getDeclaredField(aField);
			rField.setAccessible(true);
		} catch (Throwable e) {}
		return rField;
	}
	
	public static Field getField(Class aObject, String aField) {
		Field rField = null;
		try {
			rField = aObject.getDeclaredField(aField);
			rField.setAccessible(true);
		} catch (Throwable e) {}
		return rField;
	}
	
    public static boolean getPotion(EntityLiving aPlayer, int aPotionIndex) {
        try  {
        	Field tPotionHashmap = null;
        	
            Field[] var3 = EntityLiving.class.getDeclaredFields();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field var6 = var3[var5];
                if (var6.getType() == HashMap.class) {
                    tPotionHashmap = var6;
                    tPotionHashmap.setAccessible(true);
                    break;
                }
            }

            if (tPotionHashmap != null) return ((HashMap)tPotionHashmap.get(aPlayer)).get(Integer.valueOf(aPotionIndex)) != null;
        } catch (Throwable var7) {
        	
        }
    	return false;
    }
	
    public static void removePotion(EntityLiving aPlayer, int aPotionIndex) {
        try  {
        	Field tPotionHashmap = null;
        	
            Field[] var3 = EntityLiving.class.getDeclaredFields();
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Field var6 = var3[var5];
                if (var6.getType() == HashMap.class) {
                    tPotionHashmap = var6;
                    tPotionHashmap.setAccessible(true);
                    break;
                }
            }

            if (tPotionHashmap != null) ((HashMap)tPotionHashmap.get(aPlayer)).remove(Integer.valueOf(aPotionIndex));
        } catch (Throwable var7) {
        	
        }
    }
	
	public static boolean getFullInvisibility(EntityPlayer aPlayer) {
		try {
			if (GT_Utility.getPotion(aPlayer, Integer.valueOf(Potion.invisibility.id))) {
				for (int i = 0; i < 4; i++) {
					if (aPlayer.inventory.armorInventory[i] != null) {
						if (aPlayer.inventory.armorInventory[i].getItem() instanceof GT_EnergyArmor_Item) {
							if ((((GT_EnergyArmor_Item)aPlayer.inventory.armorInventory[i].getItem()).mSpecials & 512) != 0) {
								if (ic2.api.item.ElectricItem.canUse(aPlayer.inventory.armorInventory[i], 10000)) {
									return true;
								}
							}
						}
					}
				}
			}
		} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		return false;
	}
	
	public static ItemStack suckOneItemStackAt(World aWorld, int aX, int aY, int aZ, int aL, int aH, int aW) {
		ArrayList<EntityItem> tList = (ArrayList<EntityItem>)aWorld.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(aX, aY, aZ, aX+aL, aY+aH, aZ+aW));
		if (tList.size()>0) {
			aWorld.removeEntity(tList.get(0));
			return tList.get(0).getEntityItem();
		}
		return null;
	}
	
	public static byte getTier(int aValue) {
		return (byte)(aValue<=32?1:aValue<=128?2:aValue<=512?3:aValue<=2048?4:aValue<=8192?5:6);
	}
	
	/**
	 * Moves Stack from Inv-Slot to Inv-Slot, without checking if its even allowed.
	 * @return the Amount of moved Items
	 */
	public static byte moveStackFromSlotAToSlotB(IInventory aTileEntity1, IInventory aTileEntity2, int aGrabFrom, int aPutTo, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
		if (aTileEntity1 == null || aTileEntity2 == null || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMaxMoveAtOnce <= 0 || aMinMoveAtOnce > aMaxMoveAtOnce) return 0;
		
		ItemStack tStack1 = aTileEntity1.getStackInSlot(aGrabFrom), tStack2 = aTileEntity2.getStackInSlot(aPutTo), tStack3 = null;
		if (tStack1 != null) {
			if (tStack2 != null && (!tStack1.isItemEqual(tStack2) || !ItemStack.areItemStackTagsEqual(tStack1, tStack2))) return 0;
			tStack3 = tStack1.copy();
			aMaxTargetStackSize = (byte)Math.min(aMaxTargetStackSize, Math.min(tStack3.getMaxStackSize(), Math.min(tStack2==null?Integer.MAX_VALUE:tStack2.getMaxStackSize(), aTileEntity2.getInventoryStackLimit())));
			tStack3.stackSize = Math.min(tStack3.stackSize, aMaxTargetStackSize - (tStack2 == null?0:tStack2.stackSize));
			if (tStack3.stackSize > aMaxMoveAtOnce) tStack3.stackSize = aMaxMoveAtOnce;
			if (tStack3.stackSize + (tStack2==null?0:tStack2.stackSize) >= aMinTargetStackSize && tStack3.stackSize >= aMinMoveAtOnce) {
				tStack3 = aTileEntity1.decrStackSize(aGrabFrom, tStack3.stackSize);
				if (tStack3 != null) {
					if (tStack2 == null) {
						aTileEntity2.setInventorySlotContents(aPutTo, tStack3.copy());
					} else {
						tStack2.stackSize += tStack3.stackSize;
					}
					return (byte)tStack3.stackSize;
				}
			}
		}
		return 0;
	}
	
	public static boolean isAllowedToTakeFromSlot(IInventory aTileEntity, int aSlot, byte aSide, ItemStack aStack) {
		if (ForgeDirection.getOrientation(aSide) == ForgeDirection.UNKNOWN) {
			return isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte)0, aStack)
				|| isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte)1, aStack)
				|| isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte)2, aStack)
				|| isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte)3, aStack)
				|| isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte)4, aStack)
				|| isAllowedToTakeFromSlot(aTileEntity, aSlot, (byte)5, aStack);
		}
		if (aTileEntity instanceof ISidedInventory) return ((ISidedInventory)aTileEntity).func_102008_b(aSlot, aStack, aSide);
		return true;
	}
	
	public static boolean isAllowedToPutIntoSlot(IInventory aTileEntity, int aSlot, byte aSide, ItemStack aStack) {
		if (ForgeDirection.getOrientation(aSide) == ForgeDirection.UNKNOWN) {
			return isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte)0, aStack)
				|| isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte)1, aStack)
				|| isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte)2, aStack)
				|| isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte)3, aStack)
				|| isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte)4, aStack)
				|| isAllowedToPutIntoSlot(aTileEntity, aSlot, (byte)5, aStack);
		}
		if (aTileEntity instanceof ISidedInventory && !((ISidedInventory)aTileEntity).func_102007_a(aSlot, aStack, aSide)) return false;
		return aTileEntity.isStackValidForSlot(aSlot, aStack);
	}
	
	/**
	 * Moves Stack from Inv-Side to Inv-Side.
	 * @return the Amount of moved Items
	 */
	public static byte moveOneItemStack(IInventory aTileEntity1, IInventory aTileEntity2, byte aGrabFrom, byte aPutTo, ArrayList<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
		return moveOneItemStack(aTileEntity1, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, true);
	}
	
	/**
	 * This is only because I needed an additional Parameter for the Double Chest Check.
	 */
	private static byte moveOneItemStack(IInventory aTileEntity1, IInventory aTileEntity2, byte aGrabFrom, byte aPutTo, ArrayList<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce, boolean aDoCheckChests) {
		if (aTileEntity1 == null || aTileEntity2 == null || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMaxMoveAtOnce <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMinMoveAtOnce > aMaxMoveAtOnce) return 0;
		
		int[] tGrabSlots = null, tPutSlots = null;
		
		if (aTileEntity1 instanceof ISidedInventory) tGrabSlots = ((ISidedInventory)aTileEntity1).getSizeInventorySide(aGrabFrom);
		if (aTileEntity2 instanceof ISidedInventory) tPutSlots = ((ISidedInventory)aTileEntity2).getSizeInventorySide(aPutTo);
		
		if (tGrabSlots == null) {
			tGrabSlots = new int[aTileEntity1.getSizeInventory()];
			for (int i = 0; i < tGrabSlots.length; i++) tGrabSlots[i] = i;
		}
		if (tPutSlots == null) {
			tPutSlots = new int[aTileEntity2.getSizeInventory()];
			for (int i = 0; i < tPutSlots.length; i++) tPutSlots[i] = i;
		}
		
		for (int i = 0; i < tGrabSlots.length; i++) {
			for (int j = 0; j < tPutSlots.length; j++) {
				if (listContainsItem(aFilter, aTileEntity1.getStackInSlot(tGrabSlots[i]), true, aInvertFilter)) {
					if (isAllowedToTakeFromSlot(aTileEntity1, tGrabSlots[i], aGrabFrom, aTileEntity1.getStackInSlot(tGrabSlots[i]))) {
						if (isAllowedToPutIntoSlot(aTileEntity2, tPutSlots[j], aPutTo, aTileEntity1.getStackInSlot(tGrabSlots[i]))) {
							byte tMovedItemCount = moveStackFromSlotAToSlotB(aTileEntity1, aTileEntity2, tGrabSlots[i], tPutSlots[j], aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce);
							if (tMovedItemCount > 0) return tMovedItemCount;
						}
					}
				}
			}
		}
		
		if (aDoCheckChests && aTileEntity1 instanceof TileEntityChest) {
			TileEntityChest tTileEntity1 = (TileEntityChest)aTileEntity1;
			if (tTileEntity1.adjacentChestChecked) {
				byte tAmount = 0;
				if (tTileEntity1.adjacentChestXNeg != null) {
					tAmount = moveOneItemStack(tTileEntity1.adjacentChestXNeg, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				} else if (tTileEntity1.adjacentChestZNeg != null) {
					tAmount = moveOneItemStack(tTileEntity1.adjacentChestZNeg, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				} else if (tTileEntity1.adjacentChestXPos != null) {
					tAmount = moveOneItemStack(tTileEntity1.adjacentChestXPos, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				} else if (tTileEntity1.adjacentChestZPosition != null) {
					tAmount = moveOneItemStack(tTileEntity1.adjacentChestZPosition, aTileEntity2, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				}
				if (tAmount != 0) return tAmount;
			}
		}
		if (aDoCheckChests && aTileEntity2 instanceof TileEntityChest) {
			TileEntityChest tTileEntity2 = (TileEntityChest)aTileEntity2;
			if (tTileEntity2.adjacentChestChecked) {
				byte tAmount = 0;
				if (tTileEntity2.adjacentChestXNeg != null) {
					tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestXNeg, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				} else if (tTileEntity2.adjacentChestZNeg != null) {
					tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestZNeg, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				} else if (tTileEntity2.adjacentChestXPos != null) {
					tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestXPos, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				} else if (tTileEntity2.adjacentChestZPosition != null) {
					tAmount = moveOneItemStack(aTileEntity1, tTileEntity2.adjacentChestZPosition, aGrabFrom, aPutTo, aFilter, aInvertFilter, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce, false);
				}
				if (tAmount != 0) return tAmount;
			}
		}
		return 0;
	}
	
	/**
	 * Moves Stack from Inv-Side to Inv-Slot.
	 * @return the Amount of moved Items
	 */
	public static byte moveOneItemStackIntoSlot(IInventory aTileEntity1, IInventory aTileEntity2, byte aGrabFrom, int aPutTo, ArrayList<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
		if (aTileEntity1 == null || aTileEntity2 == null || aPutTo < 0 || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMaxMoveAtOnce <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMinMoveAtOnce > aMaxMoveAtOnce) return 0;
		
		int[] tGrabSlots = null;
		
		if (aTileEntity1 instanceof ISidedInventory) tGrabSlots = ((ISidedInventory)aTileEntity1).getSizeInventorySide(aGrabFrom);
		
		if (tGrabSlots == null) {
			tGrabSlots = new int[aTileEntity1.getSizeInventory()];
			for (int i = 0; i < tGrabSlots.length; i++) tGrabSlots[i] = i;
		}
		
		for (int i = 0; i < tGrabSlots.length; i++) {
			if (listContainsItem(aFilter, aTileEntity1.getStackInSlot(tGrabSlots[i]), true, aInvertFilter)) {
				if (isAllowedToTakeFromSlot(aTileEntity1, tGrabSlots[i], aGrabFrom, aTileEntity1.getStackInSlot(tGrabSlots[i]))) {
					if (isAllowedToPutIntoSlot(aTileEntity2, aPutTo, (byte)6, aTileEntity1.getStackInSlot(tGrabSlots[i]))) {
						byte tMovedItemCount = moveStackFromSlotAToSlotB(aTileEntity1, aTileEntity2, tGrabSlots[i], aPutTo, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce);
						if (tMovedItemCount > 0) return tMovedItemCount;
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * Moves Stack from Inv-Slot to Inv-Slot.
	 * @return the Amount of moved Items
	 */
	public static byte moveFromSlotToSlot(IInventory aTileEntity1, IInventory aTileEntity2, int aGrabFrom, int aPutTo, ArrayList<ItemStack> aFilter, boolean aInvertFilter, byte aMaxTargetStackSize, byte aMinTargetStackSize, byte aMaxMoveAtOnce, byte aMinMoveAtOnce) {
		if (aTileEntity1 == null || aTileEntity2 == null || aGrabFrom < 0 || aPutTo < 0 || aMaxTargetStackSize <= 0 || aMinTargetStackSize <= 0 || aMaxMoveAtOnce <= 0 || aMinTargetStackSize > aMaxTargetStackSize || aMinMoveAtOnce > aMaxMoveAtOnce) return 0;
		if (listContainsItem(aFilter, aTileEntity1.getStackInSlot(aGrabFrom), true, aInvertFilter)) {
			if (isAllowedToTakeFromSlot(aTileEntity1, aGrabFrom, (byte)6, aTileEntity1.getStackInSlot(aGrabFrom))) {
				if (isAllowedToPutIntoSlot(aTileEntity2, aPutTo, (byte)6, aTileEntity1.getStackInSlot(aGrabFrom))) {
					byte tMovedItemCount = moveStackFromSlotAToSlotB(aTileEntity1, aTileEntity2, aGrabFrom, aPutTo, aMaxTargetStackSize, aMinTargetStackSize, aMaxMoveAtOnce, aMinMoveAtOnce);
					if (tMovedItemCount > 0) return tMovedItemCount;
				}
			}
		}
		return 0;
	}
	
	public static boolean listContainsItem(ArrayList<ItemStack> aList, ItemStack aStack, boolean aTrueIfListEmpty, boolean aInvertFilter) {
		if (aStack == null || aStack.stackSize < 1) return false;
		if (aList == null) return aTrueIfListEmpty;
		while (aList.contains(null)) aList.remove(null);
		if (aList.size() < 1) return aTrueIfListEmpty;
		Iterator<ItemStack> tIterator = aList.iterator();
		ItemStack tStack = null;
		while (tIterator.hasNext()) if ((tStack = tIterator.next())!= null && areStacksEqual(aStack, tStack)) return !aInvertFilter;
		return aInvertFilter;
	}
	
	public static boolean areStacksEqual(ItemStack aStack1, ItemStack aStack2) {
		if (aStack1 == null || aStack2 == null || aStack1.itemID != aStack2.itemID) return false;
		return aStack1.getItemDamage() == aStack2.getItemDamage() || aStack1.getItemDamage() == GregTech_API.ITEM_WILDCARD_DAMAGE || aStack2.getItemDamage() == GregTech_API.ITEM_WILDCARD_DAMAGE;
	}
	
	public static ItemStack getContainerForFilledItem(ItemStack aStack) {
		if (aStack == null || aStack.isItemEqual(GT_ModHandler.getIC2Item("matter", 1))) return null;
		LiquidContainerData tData[] = LiquidContainerRegistry.getRegisteredLiquidContainerData();
		for (int i = 0; i < tData.length; i++)
			if (tData[i].filled != null && tData[i].filled.isItemEqual(aStack))
				return tData[i].container;
		return null;
	}
	
	public static boolean removeSimpleIC2MachineRecipe(ItemStack aInput, ItemStack aOutput, Map<ItemStack, ItemStack> aRecipeList) {
		if ((aInput == null && aOutput == null) || aRecipeList == null) return false;
		Iterator<Map.Entry<ItemStack, ItemStack>> tIterator = aRecipeList.entrySet().iterator();
		while (tIterator.hasNext()) {
			Map.Entry<ItemStack, ItemStack> tEntry = tIterator.next();
			ItemStack tInput = tEntry.getKey(), tOutput = tEntry.getValue();
			if ((aInput == null || tInput.isItemEqual(aInput)) && (aOutput == null || tOutput.isItemEqual(aOutput))) {
				aRecipeList.remove(tEntry.getKey());
				removeSimpleIC2MachineRecipe(aInput, aOutput, aRecipeList);
				return true;
			}
		}
		return false;
	}
	
	public static boolean addSimpleIC2MachineRecipe(ItemStack aInput, ItemStack aOutput, Map<ItemStack, ItemStack> aRecipeList) {
		if (aInput == null || aOutput == null || aRecipeList == null) return false;
		aRecipeList.put(aInput.copy(), aOutput.copy());
		return true;
	}
	
	private static int sBookCount = 0;
	
	public static ItemStack getWrittenBook(String aTitle, String aAuthor, String[] aPages) {
		if (aTitle == null || aAuthor == null || aPages == null || aPages.length <= 0 || aTitle.equals("") || aAuthor.equals("")) return null;
		sBookCount++;
		ItemStack rStack = new ItemStack(Item.writtenBook, 1);
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setString("title", aTitle);
        tNBT.setString("author", aAuthor);
        NBTTagList tNBTList = new NBTTagList("pages");
        for (int i = 0; i < aPages.length; i++) {
	        if (i < 48) 
	        	if (aPages[i].length() < 256)
	        		tNBTList.appendTag(new NBTTagString("PAGE " + i, aPages[i]));
	        	else
	        		GT_Log.err.print("String for written Book too long! -> " + aPages[i]);
	        else {
        		GT_Log.err.print("Too much Pages for written Book! -> " + aTitle);
	        	break;
	        }
        }
		tNBTList.appendTag(new NBTTagString("FINAL PAGE", "Credits to " + aAuthor + " for writing this Book. This was Book Nr. " + sBookCount + " at its creation. Gotta get 'em all!"));
        tNBT.setTag("pages", tNBTList);
        rStack.setTagCompound(tNBT);
        GregTech_API.sBookList.put(aTitle, rStack);
		return rStack.copy();
	}
	
	public static int stackToInt(ItemStack aStack) {
		if (aStack == null) return 0;
		return aStack.itemID | (aStack.getItemDamage()<<16);
	}
	
	public static ItemStack intToStack(int aStack) {
		int tID = aStack&(~0>>>16), tMeta = aStack>>>16;
		if (tID > 0 && tID < Item.itemsList.length && Item.itemsList[tID] != null) return new ItemStack(tID, 1, tMeta);
		return null;
	}
	
	public static long stacksToLong(ItemStack aStack1, ItemStack aStack2) {
		if (aStack1 == null) return 0;
		return ((long)stackToInt(aStack1)) | (((long)stackToInt(aStack2)) << 32);
	}
	
	public static boolean isItemStackInList(ItemStack aStack, Collection<Integer> aList) {
		if (aStack == null || aList == null) return false;
		ItemStack tStack = aStack.copy(); tStack.setItemDamage(GregTech_API.ITEM_WILDCARD_DAMAGE);
		return aList.contains(stackToInt(tStack)) || aList.contains(stackToInt(aStack));
	}
	
	public static synchronized int getCoordinateScan(ArrayList<String> aList, EntityPlayer aPlayer, World aWorld, int aScanLevel, int aX, int aY, int aZ, int aSide, float aClickX, float aClickY, float aClickZ) {
		if (aList == null) return 0;
		
		int rEUAmount = 0;
		
		TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
	    
	    Block tBlock = Block.blocksList[aWorld.getBlockId(aX, aY, aZ)];
	    
    	aList.add("-----");
	    try {
		    if (tTileEntity != null && tTileEntity instanceof IInventory)
		    	aList.add("Name: " + ((IInventory)tTileEntity).getInvName() + "  ID: " + tBlock.blockID + "  MetaData: " + aWorld.getBlockMetadata(aX, aY, aZ));
		    else
		    	aList.add("Name: " + tBlock.getUnlocalizedName() + "  ID: " + tBlock.blockID + "  MetaData: " + aWorld.getBlockMetadata(aX, aY, aZ));
		    
		    aList.add("Hardness: " + tBlock.getBlockHardness(aWorld, aX, aY, aZ) + "  Blast Resistance: " + tBlock.getExplosionResistance(aPlayer, aWorld, aX, aY, aZ, aPlayer.posX, aPlayer.posY, aPlayer.posZ));
		} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
	    if (tTileEntity != null) {
			try {if (tTileEntity instanceof ic2.api.reactor.IReactorChamber) {
				rEUAmount+=500;
			    tTileEntity = (TileEntity)(((ic2.api.reactor.IReactorChamber)tTileEntity).getReactor());
			}} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.reactor.IReactor) {
				rEUAmount+=500;
				aList.add("Heat: " + ((ic2.api.reactor.IReactor)tTileEntity).getHeat() + "/" + ((ic2.api.reactor.IReactor)tTileEntity).getMaxHeat()
						+ "  HEM: " + ((ic2.api.reactor.IReactor)tTileEntity).getHeatEffectModifier() + "  Base EU Output: " + ((ic2.api.reactor.IReactor)tTileEntity).getOutput());
			}} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.tile.IWrenchable) {
				rEUAmount+=100;
		        aList.add("Facing: " + ((ic2.api.tile.IWrenchable)tTileEntity).getFacing() + " / Chance: " + (((ic2.api.tile.IWrenchable)tTileEntity).getWrenchDropRate()*100) + "%");
			    aList.add(((ic2.api.tile.IWrenchable)tTileEntity).wrenchCanRemove(aPlayer)?"You can remove this with a Wrench":"You can NOT remove this with a Wrench");
			}} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.energy.tile.IEnergyTile) {
				rEUAmount+=200;
			    aList.add(((ic2.api.energy.tile.IEnergyTile)tTileEntity).isAddedToEnergyNet()?"Added to E-net":"Not added to E-net! Bug?");
			}} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.energy.tile.IEnergySink) {
				rEUAmount+=400;
		        aList.add("Demanded Energy: " + ((ic2.api.energy.tile.IEnergySink)tTileEntity).demandsEnergy());
		        aList.add("Max Safe Input: " + ((ic2.api.energy.tile.IEnergySink)tTileEntity).getMaxSafeInput());
		    }} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.energy.tile.IEnergySource) {
				rEUAmount+=400;
		        aList.add("Max Energy Output: " + ((ic2.api.energy.tile.IEnergySource)tTileEntity).getMaxEnergyOutput());
		    }} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.energy.tile.IEnergyConductor) {
				rEUAmount+=200;
		        aList.add("Conduction Loss: " + ((ic2.api.energy.tile.IEnergyConductor)tTileEntity).getConductionLoss());
		    }} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.tile.IEnergyStorage) {
				rEUAmount+=200;
		        aList.add("Contained Energy: " + ((ic2.api.tile.IEnergyStorage)tTileEntity).getStored() + " of " + ((ic2.api.tile.IEnergyStorage)tTileEntity).getCapacity());
		        aList.add(((ic2.api.tile.IEnergyStorage)tTileEntity).isTeleporterCompatible(ic2.api.Direction.YP)?"Teleporter Compatible":"Not Teleporter Compatible");
			}} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof IUpgradableMachine) {
				rEUAmount+=500;
		    	int tValue = 0;
		    	if (0 < (tValue = ((IUpgradableMachine)tTileEntity).getOverclockerUpgradeCount())) aList.add(tValue	+ " Overclocker Upgrades");
		    	if (0 < (tValue = ((IUpgradableMachine)tTileEntity).getTransformerUpgradeCount())) aList.add(tValue	+ " Transformer Upgrades");
		    	if (0 < (tValue = ((IUpgradableMachine)tTileEntity).getUpgradeStorageVolume()   )) aList.add(tValue	+ " Upgraded EU Capacity");
		    }} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof IMachineProgress) {
				rEUAmount+=400;
		    	int tValue = 0;
		    	if (0 < (tValue = ((IMachineProgress)tTileEntity).getMaxProgress())) aList.add("Progress: " + tValue + " / " + ((IMachineProgress)tTileEntity).getProgress());
		    }} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ICoverable) {
				rEUAmount+=300;
		    	String tString = ((ICoverable)tTileEntity).getCoverBehaviorAtSide((byte)aSide).getDescription((byte)aSide, ((ICoverable)tTileEntity).getCoverIDAtSide((byte)aSide), ((ICoverable)tTileEntity).getCoverDataAtSide((byte)aSide), (ICoverable)tTileEntity);
		    	if (tString != null && !tString.equals("")) aList.add(tString);
		    }} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
			try {if (tTileEntity instanceof ic2.api.crops.ICropTile) {
				if (((ic2.api.crops.ICropTile)tTileEntity).getScanLevel() < 4) {
					rEUAmount+=10000;
					((ic2.api.crops.ICropTile)tTileEntity).setScanLevel((byte)4);
				}
				if (((ic2.api.crops.ICropTile)tTileEntity).getID() >= 0 && ((ic2.api.crops.ICropTile)tTileEntity).getID() < ic2.api.crops.Crops.instance.getCropList().length && ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile)tTileEntity).getID()] != null) {
					rEUAmount+=1000;
					aList.add("Type -- Crop-Name: " + ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile)tTileEntity).getID()].name()
			        		+ "  Growth: " + ((ic2.api.crops.ICropTile)tTileEntity).getGrowth()
			        		+ "  Gain: " + ((ic2.api.crops.ICropTile)tTileEntity).getGain()
			        		+ "  Resistance: " + ((ic2.api.crops.ICropTile)tTileEntity).getResistance()
			        		);
			        aList.add("Plant -- Fertilizer: " + ((ic2.api.crops.ICropTile)tTileEntity).getNutrientStorage()
			        		+ "  Water: " + ((ic2.api.crops.ICropTile)tTileEntity).getHydrationStorage()
			        		+ "  Weed-Ex: " + ((ic2.api.crops.ICropTile)tTileEntity).getWeedExStorage()
			        		+ "  Scan-Level: " + ((ic2.api.crops.ICropTile)tTileEntity).getScanLevel()
			        		);
			        aList.add("Environment -- Nutrients: " + ((ic2.api.crops.ICropTile)tTileEntity).getNutrients()
			        		+ "  Humidity: " + ((ic2.api.crops.ICropTile)tTileEntity).getHumidity()
			        		+ "  Air-Quality: " + ((ic2.api.crops.ICropTile)tTileEntity).getAirQuality()
			        		);
			        String tString = "";
			        for (String tAttribute : ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile)tTileEntity).getID()].attributes()) {
			        	tString += ", " + tAttribute;
			        }
			        aList.add("Attributes:" + tString.replaceFirst(",", ""));
			        aList.add("Discovered by: " + ic2.api.crops.Crops.instance.getCropList()[((ic2.api.crops.ICropTile)tTileEntity).getID()].discoveredBy());
				}
			}} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
    	}
	    try {if (tBlock instanceof IDebugableBlock) {
	    	rEUAmount+=500;
	        ArrayList<String> temp = ((IDebugableBlock)tBlock).getDebugInfo(aPlayer, aX, aY, aZ, 3);
	        if (temp != null) aList.addAll(temp);
	    }} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		return rEUAmount;
	}
}