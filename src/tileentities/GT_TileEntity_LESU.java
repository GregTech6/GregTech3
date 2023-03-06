package gregtechmod.common.tileentities;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.common.blocks.GT_BlockMetaID_Block;
import ic2.api.Direction;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;

public class GT_TileEntity_LESU extends GT_TileEntityMetaID_Machine {
	
	private boolean mNotify = true, mInit = true;
	private int mStorage = 2000000000;

    public boolean isFacingValid(int aFacing) 			{return aFacing != getFacing();}
    public boolean isAccessible(EntityPlayer aPlayer)	{return true;}
    public boolean isEnetOutput()      					{return true;}
    public boolean isEnetInput()       					{return true;}
    public boolean isOutputFacing(short aDirection) 	{return aDirection == getFacing();}
    public boolean isInputFacing(short aDirection)  	{return aDirection != getFacing();}
    public int maxEUStore()            					{return mStorage;}
    public int maxEUInput()            					{return getTier(maxEUOutput())==1?32:getTier(maxEUOutput())==2?128:512;}
    public int maxEUOutput()           					{return Math.min(Math.max(mStorage/1000000+4, 1), 512);}
    public int getInventorySlotCount() 					{return 2;}
    
    public void storeAdditionalData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mStorage", mStorage);
    }

    public void getAdditionalData(NBTTagCompound aNBT) {
    	mStorage = aNBT.getInteger("mStorage");
    }
    
    public void onPreTickUpdate() {
	    if (!worldObj.isRemote) {
	    	if (mNotify || mInit) {
    			mStorage = 1000000;
	    		if (GT_BlockMetaID_Block.stepToFindOrCallLESUController(worldObj, xCoord, yCoord, zCoord, new ArrayList<ChunkPosition>(), mInit) < 2) {
	    			mStorage = Math.min(2000000000, 1000000*GT_BlockMetaID_Block.stepToGetLESUAmount(worldObj, xCoord, yCoord, zCoord, new ArrayList<ChunkPosition>()));
	    		}
	    		mNotify = mInit = false;
	    		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 10, mStorage);
	    		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID);
	    	}
    	}
    }

    @Override
    public boolean receiveClientEvent(int aEventID, int aValue) {
    	super.receiveClientEvent(aEventID, aValue);
    	if (worldObj.isRemote) {
	    	switch(aEventID) {
	    	case 10:
	    		mStorage = aValue;
	    		break;
	    	}
    	}
    	return true;
    }
    
    public int rechargerSlotStartIndex() {
    	return 0;
    }
    
    public int rechargerSlotCount() {
    	return 1;
    }
    
    public int dechargerSlotStartIndex() {
    	return 1;
    }
    
    public int dechargerSlotCount() {
    	return 1;
    }
    
    public void notifyLESUchange() {
    	mNotify = true;
    }
    
    @Override public String getInvName() {return GT_LanguageManager.mNameList1[7];}
    
    @Override
    public int getTexture(int aSide, int aMeta) {
    	if (aSide == getFacing())
    		return 12+getTier();
    	else
    		return 12;
    }
    
	@Override
	public boolean isTeleporterCompatible(Direction side) {
		return true;
	}
}
