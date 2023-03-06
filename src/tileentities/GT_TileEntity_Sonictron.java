package gregtechmod.common.tileentities;

import gregtechmod.GT_Mod;
import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;

public class GT_TileEntity_Sonictron extends GT_TileEntityMetaID_Machine {
	
	public int mCurrentIndex = -1;
	
	public boolean sendClientData = true, sendStacksizeData = false;
	
    public int getInventorySlotCount() 					{return 64;}
    public boolean isValidSlot(int aIndex)				{return false;}
    
	@Override
	public float getWrenchDropRate() {
		return 1.0F;
	}
	
    public void storeAdditionalData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mCurrentIndex", mCurrentIndex);
    }
    
    public void getAdditionalData(NBTTagCompound aNBT) {
    	mCurrentIndex = aNBT.getInteger("mCurrentIndex");
    }
    
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        super.setInventorySlotContents(slot, stack);
    	sendClientData = true;
    }
    
    public void onPostTickUpdate() {
    	if (!worldObj.isRemote) {
    		if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
		    	if (mCurrentIndex<0) {
		    		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 10, 0);
		    		mCurrentIndex=0;
		    	}
		    	if (sendStacksizeData) {
		        	for (int i = 0; i < getInventorySlotCount(); i++) {
		        		if (mInventory[i] != null) worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, i+500, mInventory[i].stackSize);
		        	}
		    		sendStacksizeData = false;
		    	}
		    	if (sendClientData) {
		        	for (int i = 0; i < getInventorySlotCount(); i++) {
		        		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, i+100, GT_Utility.stackToInt(mInventory[i]));
		        	}
		    		sendClientData = false;
		    		sendStacksizeData = true;
		    	}
    		}
	    	mRedstone = (mCurrentIndex == 63);
    	}
    	
		if (mTickTimer%2==0&&mCurrentIndex>-1) {
			GT_Mod.gregtechproxy.doSound(mInventory[mCurrentIndex], worldObj, xCoord+0.5, yCoord+0.5, zCoord+0.5);
			if (++mCurrentIndex>63) mCurrentIndex=-1;
		}
    }
    
    public Packet getDescriptionPacket() {
    	sendClientData = true;
        return super.getDescriptionPacket();
    }
    
    public boolean isAccessible(EntityPlayer aPlayer) {
    	ItemStack tStack = aPlayer.getCurrentEquippedItem();
    	if (tStack == null) return true;
    	if (tStack.isItemEqual(GregTech_API.getGregTechItem(32, 1, 0))) return false;
    	if (tStack.isItemEqual(GregTech_API.getGregTechItem(43, 1, 0))) return false;
    	return true;
    }
    
    public boolean receiveClientEvent(int aEventID, int aValue) {
    	super.receiveClientEvent(aEventID, aValue);
    	if (worldObj.isRemote) {
	    	switch(aEventID) {
	    	case 10:
	    		mCurrentIndex = aValue;
	    		break;
	    	}
	    	if (aEventID >= 100 && aEventID - 100 < getInventorySlotCount()) {
	    		mInventory[aEventID-100] = GT_Utility.intToStack(aValue);
	    	}
	    	if (aEventID >= 500 && aEventID - 500 < getInventorySlotCount()) {
	    		if (mInventory[aEventID-500]!=null) mInventory[aEventID-500].stackSize = aValue;
	    	}
    	}
    	return true;
    }
    
    @Override public String getInvName() {return GT_LanguageManager.mNameList1[6];}
    
    @Override
    public int getTexture(int aSide, int aMeta) {
    	return mCurrentIndex>-1?35:39;
    }
	@Override
	public void doEnergyExplosion() {}
}
