package gregtechmod.api.metatileentity.implementations;

import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This is the main construct for my Basic Machines such as the Automatic Extractor
 * Extend this class to make a simple Machine
 */
public abstract class GT_MetaTileEntity_BasicMachine extends MetaTileEntity {
	public boolean bOutput = false, bOutputBlocked = false, bItemTransfer = true, bSeperatedInputs = hasTwoSeperateInputs();
	public int mMainFacing = -1, mProgresstime = 0, mMaxProgresstime = 0, mEUt = 0;
	
	public ItemStack mOutputItem1, mOutputItem2;
	
	public GT_MetaTileEntity_BasicMachine(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_BasicMachine() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return true;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex > 0;}
	@Override public boolean isFacingValid(byte aFacing)			{return (mMainFacing > 1 || aFacing > 1);}
	@Override public boolean isEnetInput() 							{return true;}
	@Override public boolean isEnetOutput() 						{return true;}
	@Override public boolean isInputFacing(byte aSide)				{if (aSide==mMainFacing) return false; return !isOutputFacing(aSide);}
	@Override public boolean isOutputFacing(byte aSide)				{if (aSide==mMainFacing) return false; return bOutput?getBaseMetaTileEntity().getBackFacing()==aSide:false;}
	@Override public int getMinimumStoredEU()						{return 1000;}
	@Override public int maxEUInput()								{return 32;}
    @Override public int maxEUOutput()								{return bOutput?32:0;}
    @Override public int maxEUStore()								{return 2000;}
    @Override public int maxMJStore()								{return maxEUStore();}
    @Override public int maxSteamStore()							{return maxEUStore();}
	@Override public int getInvSize()								{return 6;}
	@Override public int dechargerSlotStartIndex()					{return 5;}
	@Override public int dechargerSlotCount()						{return 1;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	@Override public int getProgresstime()							{return mProgresstime;}
	@Override public int maxProgresstime()							{return mMaxProgresstime;}
	@Override public int increaseProgress(int aProgress)			{mProgresstime += aProgress; return mMaxProgresstime-mProgresstime;}
	@Override public boolean isLiquidInput (byte aSide)				{return aSide != mMainFacing;}
	@Override public boolean isLiquidOutput(byte aSide)				{return aSide != mMainFacing;}
    
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
    	aNBT.setBoolean("bOutput", bOutput);
    	aNBT.setBoolean("bItemTransfer", bItemTransfer);
    	aNBT.setBoolean("bSeperatedInputs", bSeperatedInputs);
    	aNBT.setInteger("mEUt", mEUt);
    	aNBT.setInteger("mMainFacing", mMainFacing);
    	aNBT.setInteger("mProgresstime", mProgresstime);
    	aNBT.setInteger("mMaxProgresstime", mMaxProgresstime);
        if (mOutputItem1 != null) {
            NBTTagCompound tNBT = new NBTTagCompound();
        	mOutputItem1.writeToNBT(tNBT);
            aNBT.setTag("mOutputItem1", tNBT);
        }
        if (mOutputItem2 != null) {
            NBTTagCompound tNBT = new NBTTagCompound();
        	mOutputItem2.writeToNBT(tNBT);
            aNBT.setTag("mOutputItem2", tNBT);
        }
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		bOutput = aNBT.getBoolean("bOutput");
		bItemTransfer = aNBT.getBoolean("bItemTransfer");
		bSeperatedInputs = aNBT.getBoolean("bSeperatedInputs");
    	mEUt = aNBT.getInteger("mEUt");
		mMainFacing = aNBT.getInteger("mMainFacing");
    	mProgresstime = aNBT.getInteger("mProgresstime");
    	mMaxProgresstime = aNBT.getInteger("mMaxProgresstime");
    	NBTTagCompound tNBT1 = (NBTTagCompound)aNBT.getTag("mOutputItem1");
    	if (tNBT1 != null) {
    		mOutputItem1 = ItemStack.loadItemStackFromNBT(tNBT1);
    	}
    	NBTTagCompound tNBT2 = (NBTTagCompound)aNBT.getTag("mOutputItem2");
    	if (tNBT2 != null) {
    		mOutputItem2 = ItemStack.loadItemStackFromNBT(tNBT2);
    	}
	}
	
	public void onPostTick() {
	    if (getBaseMetaTileEntity().isServerSide()) {
			if (mMainFacing < 2 && getBaseMetaTileEntity().getFrontFacing() > 1) {
				mMainFacing = getBaseMetaTileEntity().getFrontFacing();
				getBaseMetaTileEntity().issueTextureUpdate();
				getBaseMetaTileEntity().issueEnetUpdate();
			}
	    	if (mMaxProgresstime > 0) {
	    		getBaseMetaTileEntity().setActive(true);
	    		if (mProgresstime < 0 || getBaseMetaTileEntity().decreaseStoredEnergyUnits(mEUt*(int)Math.pow(4, getBaseMetaTileEntity().getOverclockerUpgradeCount()), false)) {
	    			if ((mProgresstime+=(int)Math.pow(2, getBaseMetaTileEntity().getOverclockerUpgradeCount()))>=mMaxProgresstime) {
	    				addOutputProducts();
	    				mOutputItem1 = null;
	    				mOutputItem2 = null;
	    				mProgresstime = 0;
	    				mMaxProgresstime = 0;
	    				if (needsImmidiateOutput()) bOutputBlocked = true;
	    			}
	    		}
	    	} else {
	    		getBaseMetaTileEntity().setActive(false);
	    	}
	    	
			if (bItemTransfer && ((getBaseMetaTileEntity().isActive() && mMaxProgresstime <= 0) || getBaseMetaTileEntity().getTimer()%1200 == 0 || bOutputBlocked) && getBaseMetaTileEntity().getBackFacing() != mMainFacing && getBaseMetaTileEntity().getEnergyStored() >= 500) {
				if (mInventory[3] != null || mInventory[4] != null) {
					TileEntity tTileEntity2 = getBaseMetaTileEntity().getTileEntityAtSide(getBaseMetaTileEntity().getBackFacing());
					if (tTileEntity2 != null && tTileEntity2 instanceof IInventory) {
						int tCost = GT_Utility.moveOneItemStack(getBaseMetaTileEntity(), (IInventory)tTileEntity2, getBaseMetaTileEntity().getBackFacing(), getBaseMetaTileEntity().getFrontFacing(), null, false, (byte)64, (byte)1, (byte)64, (byte)1);
						if (tCost > 0) {
							getBaseMetaTileEntity().decreaseStoredEnergyUnits(tCost, true);
							tCost = GT_Utility.moveOneItemStack(getBaseMetaTileEntity(), (IInventory)tTileEntity2, getBaseMetaTileEntity().getBackFacing(), getBaseMetaTileEntity().getFrontFacing(), null, false, (byte)64, (byte)1, (byte)64, (byte)1);
							if (tCost > 0) {
								getBaseMetaTileEntity().decreaseStoredEnergyUnits(tCost, true);
							}
						}
					}
				}
			}
			
	    	if (mMaxProgresstime <= 0 && getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().getEnergyStored() > 900) {
	    		checkRecipe();
	        	if (mInventory[1] != null && mInventory[1].stackSize <= 0) mInventory[1] = null;
	        	if (mInventory[2] != null && mInventory[2].stackSize <= 0) mInventory[2] = null;
	        	if (mMaxProgresstime > 0) {
		        	mOutputItem1 = GT_OreDictUnificator.get(mOutputItem1);
		        	mOutputItem2 = GT_OreDictUnificator.get(mOutputItem2);
	        	} else {
		        	mOutputItem1 = null;
		        	mOutputItem2 = null;
	        	}
	        	if (mOutputItem1 == null) {
		        	mOutputItem2 = null;
	        		mMaxProgresstime = 0;
    				mProgresstime = 0;
    				mEUt = 0;
	        	}
	        	if (mOutputItem1 != null && mOutputItem1.stackSize > 64) mOutputItem1.stackSize = 64;
	        	if (mOutputItem2 != null && mOutputItem2.stackSize > 64) mOutputItem2.stackSize = 64;
	    	}
	    }
	}
	
	@Override public void onValueUpdate(short aValue) {
		mMainFacing = aValue;
	}
	
	@Override public short getUpdateData() {
		return (short)mMainFacing;
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (mMainFacing < 2) {
			if (aSide == aFacing)
				if (aActive)
					return getFrontFacingActive();
				else
					return getFrontFacingInactive();
			else
				if (aSide == 0)
					return 32;
				else
					if (aSide == 1)
						if (aActive)
							return getTopFacingActive();
						else
							return getTopFacingInactive();
					else
						if (aActive)
							return getSideFacingActive();
						else
							return getSideFacingInactive();
		} else {
			if (aSide == mMainFacing)
				if (aActive)
					return getFrontFacingActive();
				else
					return getFrontFacingInactive();
			else
				if (ForgeDirection.getOrientation(aSide).getOpposite().ordinal() == aFacing)
					if (aSide == 0)
						return 38;
					else
						if (aSide == 1)
							return 79;
						else
							return 36;
				else
					if (aSide == 0)
						return 32;
					else
						if (aSide == 1)
							if (aActive)
								return getTopFacingActive();
							else
								return getTopFacingInactive();
						else
							if (aActive)
								return getSideFacingActive();
							else
								return getSideFacingInactive();
		}
	}
	
    private void addOutputProducts() {
    	if (mOutputItem1 != null)
	    	if (mInventory[3] == null)
	    		mInventory[3] = mOutputItem1.copy();
	    	else if (mInventory[3].isItemEqual(mOutputItem1))
	    		mInventory[3].stackSize = Math.min(mOutputItem1.getMaxStackSize(), mOutputItem1.stackSize + mInventory[3].stackSize);
    	if (mOutputItem2 != null)
	    	if (mInventory[4] == null)
	    		mInventory[4] = mOutputItem2.copy();
	    	else if (mInventory[4].isItemEqual(mOutputItem2))
	    		mInventory[4].stackSize = Math.min(mOutputItem2.getMaxStackSize(), mOutputItem2.stackSize + mInventory[4].stackSize);
    }
    
    public boolean spaceForOutput(ItemStack aOutput1, ItemStack aOutput2) {
    	if (mInventory[3] == null || aOutput1 == null || (mInventory[3].stackSize + aOutput1.stackSize <= mInventory[3].getMaxStackSize() && mInventory[3].isItemEqual(aOutput1)))
    	if (mInventory[4] == null || aOutput2 == null || (mInventory[4].stackSize + aOutput2.stackSize <= mInventory[4].getMaxStackSize() && mInventory[4].isItemEqual(aOutput2)))
    		return true;
    	bOutputBlocked = true;
    	return false;
    }
    
    public abstract void checkRecipe();
    
    public boolean needsImmidiateOutput() {
    	return false;
    }
    
    public boolean hasTwoSeperateInputs() {
    	return false;
    }
    
    /** Fallback to the regular Machine Outside Texture */
	public int getSideFacingActive() {
		// Since this relies on my Texture Indices I would recommend the use of @getTextureIcon in @MetaTileEntity
		return 40;
	}
	
    /** Fallback to the regular Machine Outside Texture */
	public int getSideFacingInactive() {
		// Since this relies on my Texture Indices I would recommend the use of @getTextureIcon in @MetaTileEntity
		return 40;
	}
	
    /** Fallback to the regular Machine Outside Texture */
	public int getFrontFacingActive() {
		// Since this relies on my Texture Indices I would recommend the use of @getTextureIcon in @MetaTileEntity
		return 40;
	}
	
    /** Fallback to the regular Machine Outside Texture */
	public int getFrontFacingInactive() {
		// Since this relies on my Texture Indices I would recommend the use of @getTextureIcon in @MetaTileEntity
		return 40;
	}
	
    /** Fallback to the regular Machine Outside Texture */
	public int getTopFacingActive() {
		// Since this relies on my Texture Indices I would recommend the use of @getTextureIcon in @MetaTileEntity
		return 29;
	}
	
    /** Fallback to the regular Machine Outside Texture */
	public int getTopFacingInactive() {
		// Since this relies on my Texture Indices I would recommend the use of @getTextureIcon in @MetaTileEntity
		return 29;
	}
	
	@Override
	public String getMainInfo() {
		return "Progress:";
	}
	@Override
	public String getSecondaryInfo() {
		return (mProgresstime/20)+"secs";
	}
	@Override
	public String getTertiaryInfo() {
		return "/"+(mMaxProgresstime/20)+"secs";
	}
	@Override
	public boolean isGivingInformation() {
		return true;
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aSide!=mMainFacing?aIndex==3||aIndex==4:false;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aSide!=mMainFacing?bSeperatedInputs?aSide<2?aIndex==1:aIndex==2:aIndex==1||aIndex==2:false;
	}
}
