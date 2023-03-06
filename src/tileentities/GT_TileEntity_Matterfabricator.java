package gregtechmod.common.tileentities;

import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_ModHandler;

import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_TileEntity_Matterfabricator extends GT_TileEntityMetaID_Machine {
	
	public static int sMatterFabricationRate = 166666;
	
	public static boolean sMassfabdisabled = false;
	
	private int mProgresstime = 0, mProgressLast = 0, mEULast = 0, mAmplifier = 0, mAmplifierLast = 0;

    public boolean isAccessible(EntityPlayer aPlayer)	{return true;}
    public boolean isEnetInput()       					{return true;}
    public boolean isInputFacing(short aDirection)  	{return true;}
    public int getProgresstime()						{return mProgresstime;}
    public int maxProgresstime()						{return sMatterFabricationRate;}
    public int maxEUStore()            					{return 1000000;}
    public int maxEUInput()            					{return 8192;}
    public int getInventorySlotCount() 					{return 9;}
    
    public void storeAdditionalData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mProgresstime", mProgresstime);
    	aNBT.setInteger("mAmplifier", mAmplifier);
    }
    
    public void getAdditionalData(NBTTagCompound aNBT) {
    	mProgresstime = aNBT.getInteger("mProgresstime");
    	mAmplifier = aNBT.getInteger("mAmplifier");
    }
    
    public void onPostTickUpdate() {
	    if (!worldObj.isRemote) {
	    	mActive = (mEULast != getEnergyVar() || mAmplifierLast != mAmplifier || mProgressLast != mProgresstime);
	    	mEULast = getEnergyVar();
	    	mAmplifierLast = mAmplifier;
	    	mProgressLast = mProgresstime;
	    	
	    	if (getStored() > 0 && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
		    	Iterator<Entry<ItemStack, Integer>> tIterator = GT_ModHandler.getMassFabricatorList().entrySet().iterator();
		    	while (mAmplifier < 100000 && tIterator.hasNext()) {
		    		Entry<ItemStack, Integer> tEntry = tIterator.next();
		    		ItemStack tStack = tEntry.getKey();
		    		int tValue = (int)(((long)tEntry.getValue()*(long)maxProgresstime())/(long)166666);
			    	if (tValue > 0) {
			    		for (int i = 1; mAmplifier < 100000 && i < getInventorySlotCount(); i++) {
				    		if (mInventory[i] != null && mInventory[i].isItemEqual(tStack)) {
						    	mAmplifier += tValue;
					    		decrStackSize(i, 1);
				    		}
			    		}
			    	}
		    	}
		    	
		    	int tUsed = Math.min(sMassfabdisabled?8192:getCapacity(), Math.min(getStored(), mAmplifier));
	    		if (tUsed > 0) {
	    			mProgresstime += tUsed;
		    		mAmplifier -= tUsed;
		    		decreaseStoredEnergy(tUsed, true);
		    	}
		    	while (mProgresstime>maxProgresstime()) {
		    		if (spaceForOutput()) {
			    		mProgresstime -= maxProgresstime();
			    		addOutputProducts();
		    		} else break;
		    	}
	    	}
		}
    }
    
    private void addOutputProducts() {
	    if (mInventory[0] == null)
	    	mInventory[0] = GT_ModHandler.getIC2Item("matter", 1);
	    else if (mInventory[0].isItemEqual(GT_ModHandler.getIC2Item("matter", 1)))
	    	mInventory[0].stackSize = Math.min(64, 1 + mInventory[0].stackSize);
    }
    
    private boolean spaceForOutput() {
    	return (mInventory[0] == null || (mInventory[0].isItemEqual(GT_ModHandler.getIC2Item("matter", 1)) && mInventory[0].stackSize < 64));
    }
    
	@Override
	public boolean func_102007_a(int i, ItemStack itemstack, int j) {
		return i>0;
	}
	
	@Override
	public boolean func_102008_b(int i, ItemStack itemstack, int j) {
		return i==0;
	}
	
	
    @Override public String getInvName() {return GT_LanguageManager.mNameList1[14];}
    
	@Override
	public String getMainInfo() {
		return "Progress:";
	}
	@Override
	public String getSecondaryInfo() {
		return Math.max(0, ((long)getProgresstime()) / Math.max(1, ((long)maxProgresstime())/100)) + "%";
	}
	@Override
	public String getTertiaryInfo() {
		return "Energy: " + getStored();
	}
	@Override
	public boolean isGivingInformation() {
		return true;
	}
    
    @Override
    public int getTexture(int aSide, int aMeta) {
    	return mActive?31:30;
    }
    
	@Override
	public int getProgress() {
		return getProgresstime();
	}

	@Override
	public int getMaxProgress() {
		return maxProgresstime();
	}
}
