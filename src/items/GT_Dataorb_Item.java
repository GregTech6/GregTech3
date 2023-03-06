package gregtechmod.common.items;

import gregtechmod.api.items.GT_Generic_Item;
import gregtechmod.common.tileentities.GT_TileEntity_ComputerCube;
import gregtechmod.common.tileentities.GT_TileEntity_Sonictron;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GT_Dataorb_Item extends GT_Generic_Item {
	public GT_Dataorb_Item(int aID, String aName) {
		super(aID, aName, null);
		setNoRepair();
	}
	
	public ItemStack onItemRightClick(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
		return aStack;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float var8, float var9, float var10) {
		if (aStack.stackSize > 1 || aWorld.isRemote) return false;
    	TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
    	if (tTileEntity == null) return false;
    	if (tTileEntity instanceof GT_TileEntity_ComputerCube) {
	    	GT_TileEntity_ComputerCube tComputer = (GT_TileEntity_ComputerCube)tTileEntity;
	        if (tComputer != null && tComputer.mMode == 1 && tComputer.playerOwnsThis(aPlayer)) {
	            ItemStack[] tInventory = getNBTInventory(aStack);
	        	if (aPlayer.isSneaking()) {
	        		if (getDataTitle(aStack).equals("Reactorplan-Data"))
	        			copyInventory(tComputer.mInventory, tInventory, 54);
	        	} else {
	        		copyInventory(tInventory, tComputer.mInventory, 54);
	            	setDataTitle(aStack, "Reactorplan-Data");
	            	setDataName (aStack, "" + tInventory.hashCode());
	        	}
	            setNBTInventory(aStack, tInventory);
	            return true;
	        }
    	} else if (tTileEntity instanceof GT_TileEntity_Sonictron) {
	    	GT_TileEntity_Sonictron tSonictron = (GT_TileEntity_Sonictron)tTileEntity;
	        if (tSonictron != null) {
	            ItemStack[] tInventory = getNBTInventory(aStack);
	        	if (aPlayer.isSneaking()) {
	        		if (getDataTitle(aStack).equals("Sonictron-Data"))
	        			copyInventory(tSonictron.mInventory, tInventory, 64);
	        	} else {
	        		copyInventory(tInventory, tSonictron.mInventory, 64);
	            	setDataTitle(aStack, "Sonictron-Data");
	            	setDataName (aStack, "" + tInventory.hashCode());
	        	}
	            setNBTInventory(aStack, tInventory);
	            tSonictron.sendClientData = true;
	            return true;
	        }
	    }
        return false;
    }
    
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if (!getDataTitle(par1ItemStack).equals("")) {
			par3List.add(getDataTitle(par1ItemStack));
			par3List.add(getDataName(par1ItemStack));
		}
    }
    
    private void copyInventory(ItemStack aInventory[], ItemStack aNewContent[], int aIndexlength) {
    	for (int i = 0; i < aIndexlength; i++) {
    		if (aNewContent[i] == null)
    			aInventory[i] = null;
    		else
    			aInventory[i] = aNewContent[i].copy();
    	}
    }
    
    public static String getDataName(ItemStack aStack) {
    	NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
    	if (tNBTTagCompound == null) tNBTTagCompound = new NBTTagCompound();
    	return tNBTTagCompound.getString("mDataName");
    }

    public static String getDataTitle(ItemStack aStack) {
    	NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
    	if (tNBTTagCompound == null) tNBTTagCompound = new NBTTagCompound();
    	return tNBTTagCompound.getString("mDataTitle");
    }

    public static NBTTagCompound setDataName(ItemStack aStack, String aDataName) {
    	NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
    	if (tNBTTagCompound == null) tNBTTagCompound = new NBTTagCompound();
    	tNBTTagCompound.setString("mDataName", aDataName);
    	return tNBTTagCompound;
    }
    
    public static NBTTagCompound setDataTitle(ItemStack aStack, String aDataTitle) {
    	NBTTagCompound tNBTTagCompound = aStack.getTagCompound();
    	if (tNBTTagCompound == null) tNBTTagCompound = new NBTTagCompound();
    	tNBTTagCompound.setString("mDataTitle", aDataTitle);
    	return tNBTTagCompound;
    }
    
    public static ItemStack[] getNBTInventory(ItemStack aStack) {
    	ItemStack[] tInventory = new ItemStack[256];
    	NBTTagCompound tNBT = aStack.getTagCompound();
    	if (tNBT == null) return tInventory;
    	
    	NBTTagList tNBT_ItemList = tNBT.getTagList("Inventory");
        for (int i = 0; i < tNBT_ItemList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tNBT_ItemList.tagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < tInventory.length) {
                tInventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
        return tInventory;
    }
    
    public static NBTTagCompound setNBTInventory(ItemStack aStack, ItemStack[] aInventory) {
    	NBTTagCompound tNBT = aStack.getTagCompound();
    	if (tNBT == null) tNBT = new NBTTagCompound();
    	
        NBTTagList tNBT_ItemList = new NBTTagList();
        for (int i = 0; i < aInventory.length; i++) {
            ItemStack stack = aInventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                tNBT_ItemList.appendTag(tag);
            }
        }
        tNBT.setTag("Inventory", tNBT_ItemList);
        aStack.setTagCompound(tNBT);
        return tNBT;
    }
    
    public boolean getShareTag() {
        return true;
    }
}
