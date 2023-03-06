package gregtechmod.common.tileentities;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_Recipe;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaTileEntity_Sawmill extends MetaTileEntity {

	public int mProgresstime = 0, mMaxProgresstime = 0, mEUt = 0, mUpdate = 5, mWaterAmount = 0;
	private ItemStack mOutputItem1, mOutputItem2, mOutputItem3;
	public boolean mMachine = false;
	
	public GT_MetaTileEntity_Sawmill(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_Sawmill() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return true;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isFacingValid(byte aFacing)			{return aFacing == 0;}
	@Override public boolean isEnetInput() 							{return true;}
	@Override public boolean isInputFacing(byte aSide)				{return true;}
    @Override public int maxEUInput()								{return 128;}
    @Override public int maxEUStore()								{return 10000;}
    @Override public int maxMJStore()								{return maxEUStore();}
    @Override public int maxSteamStore()							{return maxEUStore();}
	@Override public int getInvSize()								{return 6;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 116);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	@Override public int getProgresstime()							{return mProgresstime;}
	@Override public int maxProgresstime()							{return mMaxProgresstime;}
	@Override public int increaseProgress(int aProgress)			{mProgresstime += aProgress; return mMaxProgresstime-mProgresstime;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_Sawmill();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mEUt", mEUt);
    	aNBT.setInteger("mWaterAmount", mWaterAmount);
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
        if (mOutputItem3 != null) {
            NBTTagCompound tNBT = new NBTTagCompound();
        	mOutputItem3.writeToNBT(tNBT);
            aNBT.setTag("mOutputItem3", tNBT);
        }
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
    	mUpdate = 5;
    	mEUt = aNBT.getInteger("mEUt");
    	mWaterAmount = aNBT.getInteger("mWaterAmount");
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
    	NBTTagCompound tNBT3 = (NBTTagCompound)aNBT.getTag("mOutputItem3");
    	if (tNBT3 != null) {
    		mOutputItem3 = ItemStack.loadItemStackFromNBT(tNBT3);
    	}
	}
	
    private boolean checkMachine() {
    	for (int i = -1; i < 2; i++) for (int j = 1; j < 2; j++) for (int k = -1; k < 2; k++) {
    		if (getBaseMetaTileEntity().getBlockIDOffset(i, j-2, k) != GregTech_API.sBlockList[0].blockID) return false;
            if (getBaseMetaTileEntity().getMetaIDOffset (i, j-2, k) != (i==0&&k==0?14:13)) return false;
    	}
    	return true;
    }
    
    @Override
    public void onMachineBlockUpdate() {
    	mUpdate = 5;
    }
    
    @Override
    public void onPostTick() {
	    if (getBaseMetaTileEntity().isServerSide()) {
	    	if (mUpdate--==0) {
	        	mMachine = checkMachine();
	    	}
    		getBaseMetaTileEntity().setActive(mMachine);
	    	if (mMachine && mMaxProgresstime > 0) {
	    		if (mProgresstime < 0 || getBaseMetaTileEntity().decreaseStoredEnergyUnits(mEUt*(int)Math.pow(4, getBaseMetaTileEntity().getOverclockerUpgradeCount()), false)) {
	    			if ((mProgresstime+=(int)Math.pow(2, getBaseMetaTileEntity().getOverclockerUpgradeCount()))>=mMaxProgresstime) {
	    				addOutputProducts();
	    				mOutputItem1 = null;
	    				mOutputItem2 = null;
	    				mOutputItem3 = null;
	    				mProgresstime = 0;
	    				mMaxProgresstime = 0;
    					getBaseMetaTileEntity().setErrorDisplayID(0);
	    			}
	    		} else {
    				if (GregTech_API.sConstantEnergy) {
    					mProgresstime = -10;
    					getBaseMetaTileEntity().setErrorDisplayID(1);
    				}
	    		}
	    	} else {
	    		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().getEnergyStored() > 100) checkRecipe();
	    	}
		}
    }
    
    private void addOutputProducts() {
    	if (mOutputItem1 != null)
	    	if (mInventory[2] == null)
	    		mInventory[2] = mOutputItem1.copy();
	    	else if (mInventory[2].isItemEqual(mOutputItem1))
	    		mInventory[2].stackSize = Math.min(mOutputItem1.getMaxStackSize(), mOutputItem1.stackSize + mInventory[2].stackSize);
    	
    	if (mOutputItem2 != null)
	    	if (mInventory[3] == null)
	    		mInventory[3] = mOutputItem2.copy();
	    	else if (mInventory[3].isItemEqual(mOutputItem2))
	    		mInventory[3].stackSize = Math.min(mOutputItem2.getMaxStackSize(), mOutputItem2.stackSize + mInventory[3].stackSize);
    	
    	if (mOutputItem3 != null)
	    	if (mInventory[4] == null)
	    		mInventory[4] = mOutputItem3.copy();
	    	else if (mInventory[4].isItemEqual(mOutputItem3))
	    		mInventory[4].stackSize = Math.min(mOutputItem3.getMaxStackSize(), mOutputItem3.stackSize + mInventory[4].stackSize);
    }
    
    private boolean spaceForOutput(int aRecipe) {
    	if (mInventory[2] == null || GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput1 == null || (mInventory[2].stackSize + GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput1.stackSize <= mInventory[2].getMaxStackSize() && mInventory[2].isItemEqual(GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput1)))
    	if (mInventory[3] == null || GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput2 == null || (mInventory[3].stackSize + GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput2.stackSize <= mInventory[3].getMaxStackSize() && mInventory[3].isItemEqual(GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput2)))
    	if (mInventory[4] == null || GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput3 == null || (mInventory[4].stackSize + GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput3.stackSize <= mInventory[4].getMaxStackSize() && mInventory[4].isItemEqual(GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput3)))
    	if (mInventory[5] == null || GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput4 == null || (mInventory[5].stackSize + GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput4.stackSize <= mInventory[5].getMaxStackSize() && mInventory[5].isItemEqual(GT_Recipe.sSawmillRecipes.get(aRecipe).mOutput4)))
    		return true;
    	return false;
    }
    
    private boolean checkRecipe() {
    	if (!mMachine) return false;
    	
    	int tRecipe = GT_Recipe.findEqualSawmillRecipeIndex(mInventory[0], mInventory[1]);
    	
    	if (tRecipe != -1) {
    		if (spaceForOutput(tRecipe)) {
    			if (GT_Recipe.isRecipeInputEqual(true, true, mInventory[0], mInventory[1], GT_Recipe.sSawmillRecipes.get(tRecipe))) {
		        	if (mInventory[0] != null) if (mInventory[0].stackSize == 0) mInventory[0] = null;
		        	if (mInventory[1] != null) if (mInventory[1].stackSize == 0) mInventory[1] = null;
		        	
		        	mMaxProgresstime = GT_Recipe.sSawmillRecipes.get(tRecipe).mDuration;
		        	mEUt = GT_Recipe.sSawmillRecipes.get(tRecipe).mEUt;
		        	
		        	if (GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput1 == null) {
		        		mOutputItem1 = null;
		        	} else {
			        	mOutputItem1 = GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput1.copy();
		        	}
		        	if (GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput2 == null) {
		        		mOutputItem2 = null;
		        	} else {
			        	mOutputItem2 = GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput2.copy();
		        	}
		        	if (GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput3 == null) {
		        		mOutputItem3 = null;
		        	} else {
			        	mOutputItem3 = GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput3.copy();
		        	}
		        	return true;
    			}
    		}
    	} else {
    		ItemStack tWaterStack = (mWaterAmount>=1000?GT_ModHandler.getIC2Item("waterCell", mWaterAmount/1000):null);
        	tRecipe = GT_Recipe.findEqualSawmillRecipeIndex(mInventory[0], tWaterStack);
    		if (tRecipe != -1 && spaceForOutput(tRecipe)) {
    			if (GT_Recipe.isRecipeInputEqual(true, true, mInventory[0], tWaterStack, GT_Recipe.sSawmillRecipes.get(tRecipe))) {
		        	if (mInventory[0] != null) if (mInventory[0].stackSize == 0) mInventory[0] = null;
		        	mWaterAmount-=((mWaterAmount/1000)-tWaterStack.stackSize)*1000;
		        	
		        	mMaxProgresstime = GT_Recipe.sSawmillRecipes.get(tRecipe).mDuration;
		        	mEUt = GT_Recipe.sSawmillRecipes.get(tRecipe).mEUt;
		        	
		        	if (GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput1 == null) {
		        		mOutputItem1 = null;
		        	} else {
			        	mOutputItem1 = GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput1.copy();
		        	}
		        	if (GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput2 == null) {
		        		mOutputItem2 = null;
		        	} else {
			        	mOutputItem2 = GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput2.copy();
		        	}
		        	if (GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput3 == null) {
		        		mOutputItem3 = null;
		        	} else {
		        		if (!GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput3.isItemEqual(GT_ModHandler.getIC2Item("cell", 1)))
		        			mOutputItem3 = GT_Recipe.sSawmillRecipes.get(tRecipe).mOutput3.copy();
		        	}
		        	return true;
    			}
			}
    	}
    	
    	return false;
    }
    
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex>1;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aSide<2?aIndex==0:aIndex==1;
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (ForgeDirection.getOrientation(aSide) == ForgeDirection.UP)
			return 68+(aActive?1:0);
		if (ForgeDirection.getOrientation(aSide) == ForgeDirection.DOWN)
			return 71;
		if (ForgeDirection.getOrientation(aSide) == ForgeDirection.NORTH || ForgeDirection.getOrientation(aSide) == ForgeDirection.SOUTH)
			return 76;
		return 75;
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
	protected String getDescription() {
		return "Get mo Planks for ya Logs";
	}
	
	@Override
	public int fill(LiquidStack resource, boolean doFill) {
		if (resource == null || resource.itemID <= 0) return 0;
		LiquidStack tLiquid = new LiquidStack(Block.waterStill, mWaterAmount);
		if (!tLiquid.isLiquidEqual(resource)) return 0;
		
		int space = getCapacity() - tLiquid.amount;
		if (resource.amount <= space) {
			if (doFill && space > 0) {
				mWaterAmount += resource.amount;
			}
			return resource.amount;
		} else {
			if (doFill && space > 0) {
				mWaterAmount = getCapacity();
			}
			return space;
		}
	}
	
	@Override public LiquidStack getLiquid() {return new LiquidStack(Block.waterStill, mWaterAmount);}
	@Override public int getTankPressure() {return -100;}
	@Override public int getCapacity() {return 10000;}
}
