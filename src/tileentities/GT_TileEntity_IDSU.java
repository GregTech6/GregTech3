package gregtechmod.common.tileentities;

import gregtechmod.api.util.GT_LanguageManager;
import ic2.api.Direction;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GT_TileEntity_IDSU extends GT_TileEntityMetaID_Machine {
	
	public static HashMap<Integer, Integer> sEnergyList;
	
	public int mPlayerHash = 0;
	
    public boolean isFacingValid(int aFacing) 			{return aFacing != getFacing();}
    public boolean isAccessible(EntityPlayer aPlayer)	{if (mPlayerHash == 0 && !worldObj.isRemote) mPlayerHash = aPlayer.username.hashCode(); return true;}
    public boolean isEnetOutput()      					{return mPlayerHash != 0;}
    public boolean isEnetInput()       					{return mPlayerHash != 0 && sEnergyList != null;}
    public boolean isOutputFacing(short aDirection) 	{return aDirection == getFacing();}
    public boolean isInputFacing(short aDirection)  	{return aDirection != getFacing();}
    public int maxEUStore()            					{return 1000000000;}
    public int maxEUInput()            					{return 2048;}
    public int maxEUOutput()           					{return 2048;}
    public int getInventorySlotCount() 					{return 2;}
    public boolean ownerControl()						{return true;}
    
    public void storeAdditionalData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mPlayerHash", mPlayerHash);
    }

    public void getAdditionalData(NBTTagCompound aNBT) {
    	mPlayerHash = aNBT.getInteger("mPlayerHash");
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
    
    /**
     * This is used to set the internal Energy to the given Parameter. I use this for the IDSU.
     */
	public void setEnergyVar(int aEU) {
	    if (!worldObj.isRemote && sEnergyList != null) {
	    	sEnergyList.remove(mPlayerHash);
	    	sEnergyList.put(mPlayerHash, aEU);
	    }
	}

    /**
     * This is used to get the internal Energy. I use this for the IDSU.
     */
	public int getEnergyVar() {
	    if (!worldObj.isRemote && sEnergyList != null) {
	    	Integer tEU = sEnergyList.get(mPlayerHash);
	    	if (tEU == null) tEU = 0;
	    	return tEU;
	    }
		return 0;
	}
    
    @Override public String getInvName() {return GT_LanguageManager.mNameList1[8];}
    
    @Override
    public int getTexture(int aSide, int aMeta) {
    	if (aSide == getFacing())
    		return 18;
    	else
    		return 22;
    }

	@Override
	public boolean isTeleporterCompatible(Direction side) {
		return true;
	}
	@Override
	public void doEnergyExplosion() {}
}
