package gregtechmod.common.tileentities;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_Recipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_VacuumFreezer extends MetaTileEntity {

	public int mProgresstime = 0, mMaxProgresstime = 0, mEUt = 0, mUpdate = 5;
	public ItemStack mOutputItem1;
	public boolean mMachine = false;
	
	public GT_MetaTileEntity_VacuumFreezer(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_VacuumFreezer() {
		
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
	@Override public int getInvSize()								{return 2;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 122);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	@Override public int getProgresstime()							{return mProgresstime;}
	@Override public int maxProgresstime()							{return mMaxProgresstime;}
	@Override public int increaseProgress(int aProgress)			{mProgresstime += aProgress; return mMaxProgresstime-mProgresstime;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_VacuumFreezer();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mEUt", mEUt);
    	aNBT.setInteger("mProgresstime", mProgresstime);
    	aNBT.setInteger("mMaxProgresstime", mMaxProgresstime);
    	
        if (mOutputItem1 != null) {
            NBTTagCompound tNBT = new NBTTagCompound();
        	mOutputItem1.writeToNBT(tNBT);
            aNBT.setTag("mOutputItem1", tNBT);
        }
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
    	mUpdate = 5;
    	mEUt = aNBT.getInteger("mEUt");
    	mProgresstime = aNBT.getInteger("mProgresstime");
    	mMaxProgresstime = aNBT.getInteger("mMaxProgresstime");
    	
    	NBTTagCompound tNBT1 = (NBTTagCompound)aNBT.getTag("mOutputItem1");
    	if (tNBT1 != null) {
    		mOutputItem1 = ItemStack.loadItemStackFromNBT(tNBT1);
    	}
	}
    
    private boolean checkMachine() {
    	for (int i = -1; i < 2; i++) for (int j = -1; j < 2; j++) for (int k = -1; k < 2; k++) {
    		if (i!=0||j!=0||k!=0) {
    			if (getBaseMetaTileEntity().getBlockIDOffset(i, j-2, k) != GregTech_API.sBlockList[0].blockID) return false;
            	if (getBaseMetaTileEntity().getMetaIDOffset (i, j-2, k) != ((i==0&&j==0&&k!=0)||(i==0&&j!=0&&k==0)||(i!=0&&j==0&&k==0)?15:14)) return false;
    		} else {
    			if (getBaseMetaTileEntity().getBlockIDOffset(i, j-2, k) != 0) return false;
    		}
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
	    	if (mInventory[1] == null)
	    		mInventory[1] = mOutputItem1.copy();
	    	else if (mInventory[1].isItemEqual(mOutputItem1))
	    		mInventory[1].stackSize = Math.min(mOutputItem1.getMaxStackSize(), mOutputItem1.stackSize + mInventory[1].stackSize);
    }
    
    private boolean spaceForOutput(int aRecipe) {
    	if (mInventory[1] == null || GT_Recipe.sVacuumRecipes.get(aRecipe).mOutput1 == null || (mInventory[1].stackSize + GT_Recipe.sVacuumRecipes.get(aRecipe).mOutput1.stackSize <= mInventory[1].getMaxStackSize() && mInventory[1].isItemEqual(GT_Recipe.sVacuumRecipes.get(aRecipe).mOutput1)))
    		return true;
    	return false;
    }
    
    private boolean checkRecipe() {
    	if (!mMachine) return false;
    	
    	int tRecipe = GT_Recipe.findEqualVacuumRecipeIndex(mInventory[0], null);
    	
    	if (tRecipe != -1) {
    		if (spaceForOutput(tRecipe) && GT_Recipe.isRecipeInputEqual(true, true, mInventory[0], null, GT_Recipe.sVacuumRecipes.get(tRecipe))) {
	        	if (mInventory[0] != null) if (mInventory[0].stackSize == 0) mInventory[0] = null;
	        	
	        	mMaxProgresstime = GT_Recipe.sVacuumRecipes.get(tRecipe).mDuration;
	        	mEUt = GT_Recipe.sVacuumRecipes.get(tRecipe).mEUt;
	        	
	        	if (GT_Recipe.sVacuumRecipes.get(tRecipe).mOutput1 == null) {
	        		mOutputItem1 = null;
	        	} else {
		        	mOutputItem1 = GT_Recipe.sVacuumRecipes.get(tRecipe).mOutput1.copy();
	        	}
	        	return true;
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
		return 77;
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
		return "Cools down anything";
	}
}
