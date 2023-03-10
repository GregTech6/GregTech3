package gregtechmod.common.tileentities;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_Recipe;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaTileEntity_FusionComputer extends MetaTileEntity {

	public int mProgresstime = 0, mMaxProgresstime = 0, mEUt = 0, mUpdate = 100;
	private ItemStack mOutputItem1;
	public boolean mMachine = true;
	
	private ArrayList<IGregTechTileEntity> mPlasmaExtractors   = new ArrayList<IGregTechTileEntity>();
	private ArrayList<IGregTechTileEntity> mEnergyInjectors    = new ArrayList<IGregTechTileEntity>();
	private ArrayList<IGregTechTileEntity> mPrimaryInjectors   = new ArrayList<IGregTechTileEntity>();
	private ArrayList<IGregTechTileEntity> mSecondaryInjectors = new ArrayList<IGregTechTileEntity>();
	
	public GT_MetaTileEntity_FusionComputer(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_FusionComputer() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return false;}
    @Override public boolean isValidSlot(int aIndex) 				{return false;}
	@Override public boolean isFacingValid(byte aFacing)			{return aFacing > 1;}
	@Override public int getInvSize()								{return 1;}
    @Override public int maxEUStore()								{return 160000000;}
    @Override public int getEUVar()									{return getStoredEU();}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 143);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	@Override public int getProgresstime()							{return mProgresstime;}
	@Override public int maxProgresstime()							{return mMaxProgresstime;}
	@Override public int increaseProgress(int aProgress)			{mProgresstime += aProgress; return mMaxProgresstime-mProgresstime;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_FusionComputer();
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
    	mUpdate = 100;
    	mEUt = aNBT.getInteger("mEUt");
    	mProgresstime = aNBT.getInteger("mProgresstime");
    	mMaxProgresstime = aNBT.getInteger("mMaxProgresstime");
    	
    	NBTTagCompound tNBT1 = (NBTTagCompound)aNBT.getTag("mOutputItem1");
    	if (tNBT1 != null) {
    		mOutputItem1 = ItemStack.loadItemStackFromNBT(tNBT1);
    	}
	}
	
	private void setComputerOf(MetaTileEntity aMetaTileEntity, boolean setreset) {
		if (aMetaTileEntity != null) {
			if (aMetaTileEntity instanceof GT_MetaTileEntity_FusionInjector) {
				((GT_MetaTileEntity_FusionInjector)aMetaTileEntity).mFusionComputer = setreset?getBaseMetaTileEntity():null;
				if (setreset)
					if (aMetaTileEntity.getBaseMetaTileEntity().getYCoord() > getBaseMetaTileEntity().getYCoord())
						mPrimaryInjectors.add(aMetaTileEntity.getBaseMetaTileEntity());
					else
						mSecondaryInjectors.add(aMetaTileEntity.getBaseMetaTileEntity());
			}
			if (aMetaTileEntity instanceof GT_MetaTileEntity_FusionEnergyInjector) {
				((GT_MetaTileEntity_FusionEnergyInjector)aMetaTileEntity).mFusionComputer = setreset?getBaseMetaTileEntity():null;
				if (setreset)
					mEnergyInjectors.add(aMetaTileEntity.getBaseMetaTileEntity());
			}
			if (aMetaTileEntity instanceof GT_MetaTileEntity_FusionExtractor) {
				((GT_MetaTileEntity_FusionExtractor)aMetaTileEntity).mFusionComputer = setreset?getBaseMetaTileEntity():null;
				if (setreset)
					mPlasmaExtractors.add(aMetaTileEntity.getBaseMetaTileEntity());
			}
		}
	}
	
	private void reset() {
		for (IGregTechTileEntity tTileEntity : mPlasmaExtractors  ) setComputerOf(tTileEntity.getMetaTileEntity(), false);
		for (IGregTechTileEntity tTileEntity : mPrimaryInjectors  ) setComputerOf(tTileEntity.getMetaTileEntity(), false);
		for (IGregTechTileEntity tTileEntity : mSecondaryInjectors) setComputerOf(tTileEntity.getMetaTileEntity(), false);
		for (IGregTechTileEntity tTileEntity : mEnergyInjectors   ) setComputerOf(tTileEntity.getMetaTileEntity(), false);
		
		mPlasmaExtractors   = new ArrayList<IGregTechTileEntity>();
		mPrimaryInjectors   = new ArrayList<IGregTechTileEntity>();
		mSecondaryInjectors = new ArrayList<IGregTechTileEntity>();
		mEnergyInjectors    = new ArrayList<IGregTechTileEntity>();
	}
	
    private boolean checkMachine() {
    	reset();
    	int xCenter = getBaseMetaTileEntity().getXCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetX*5, yCenter = getBaseMetaTileEntity().getYCoord(), zCenter = getBaseMetaTileEntity().getZCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetZ*5;
    	
    	if (isAdvancedMachineCasing(xCenter + 5, yCenter, zCenter) || xCenter + 5 == getBaseMetaTileEntity().getXCoord())
    	if (isAdvancedMachineCasing(xCenter - 5, yCenter, zCenter) || xCenter - 5 == getBaseMetaTileEntity().getXCoord())
    	if (isAdvancedMachineCasing(xCenter, yCenter, zCenter + 5) || zCenter + 5 == getBaseMetaTileEntity().getZCoord())
    	if (isAdvancedMachineCasing(xCenter, yCenter, zCenter - 5) || zCenter - 5 == getBaseMetaTileEntity().getZCoord())
    	if (checkCoils(xCenter, yCenter, zCenter))
    	if (checkHulls(xCenter, yCenter, zCenter))
    	if (checkUpperOrLowerHulls(xCenter, yCenter+1, zCenter))
    	if (checkUpperOrLowerHulls(xCenter, yCenter-1, zCenter))
        if (addIfEnergyInjector(xCenter+4, yCenter, zCenter+3))
        if (addIfEnergyInjector(xCenter+4, yCenter, zCenter-3))
        if (addIfEnergyInjector(xCenter+4, yCenter, zCenter+5))
        if (addIfEnergyInjector(xCenter+4, yCenter, zCenter-5))
        if (addIfEnergyInjector(xCenter-4, yCenter, zCenter+3))
        if (addIfEnergyInjector(xCenter-4, yCenter, zCenter-3))
        if (addIfEnergyInjector(xCenter-4, yCenter, zCenter+5))
        if (addIfEnergyInjector(xCenter-4, yCenter, zCenter-5))
        if (addIfEnergyInjector(xCenter+3, yCenter, zCenter+4))
        if (addIfEnergyInjector(xCenter-3, yCenter, zCenter+4))
        if (addIfEnergyInjector(xCenter+5, yCenter, zCenter+4))
        if (addIfEnergyInjector(xCenter-5, yCenter, zCenter+4))
        if (addIfEnergyInjector(xCenter+3, yCenter, zCenter-4))
        if (addIfEnergyInjector(xCenter-3, yCenter, zCenter-4))
        if (addIfEnergyInjector(xCenter+5, yCenter, zCenter-4))
        if (addIfEnergyInjector(xCenter-5, yCenter, zCenter-4))
        if (addIfExtractor(xCenter+1, yCenter, zCenter-5))
        if (addIfExtractor(xCenter+1, yCenter, zCenter+5))
        if (addIfExtractor(xCenter-1, yCenter, zCenter-5))
        if (addIfExtractor(xCenter-1, yCenter, zCenter+5))
        if (addIfExtractor(xCenter+1, yCenter, zCenter-7))
        if (addIfExtractor(xCenter+1, yCenter, zCenter+7))
        if (addIfExtractor(xCenter-1, yCenter, zCenter-7))
        if (addIfExtractor(xCenter-1, yCenter, zCenter+7))
        if (addIfExtractor(xCenter+5, yCenter, zCenter-1))
        if (addIfExtractor(xCenter+5, yCenter, zCenter+1))
        if (addIfExtractor(xCenter-5, yCenter, zCenter-1))
        if (addIfExtractor(xCenter-5, yCenter, zCenter+1))
        if (addIfExtractor(xCenter+7, yCenter, zCenter-1))
        if (addIfExtractor(xCenter+7, yCenter, zCenter+1))
        if (addIfExtractor(xCenter-7, yCenter, zCenter-1))
        if (addIfExtractor(xCenter-7, yCenter, zCenter+1))
        if (addIfInjector(xCenter+1, yCenter+1, zCenter-6))
        if (addIfInjector(xCenter+1, yCenter+1, zCenter+6))
        if (addIfInjector(xCenter-1, yCenter+1, zCenter-6))
        if (addIfInjector(xCenter-1, yCenter+1, zCenter+6))
        if (addIfInjector(xCenter-6, yCenter+1, zCenter+1))
        if (addIfInjector(xCenter+6, yCenter+1, zCenter+1))
        if (addIfInjector(xCenter-6, yCenter+1, zCenter-1))
        if (addIfInjector(xCenter+6, yCenter+1, zCenter-1))
        if (addIfInjector(xCenter+1, yCenter-1, zCenter-6))
        if (addIfInjector(xCenter+1, yCenter-1, zCenter+6))
        if (addIfInjector(xCenter-1, yCenter-1, zCenter-6))
        if (addIfInjector(xCenter-1, yCenter-1, zCenter+6))
        if (addIfInjector(xCenter-6, yCenter-1, zCenter+1))
        if (addIfInjector(xCenter+6, yCenter-1, zCenter+1))
        if (addIfInjector(xCenter-6, yCenter-1, zCenter-1))
        if (addIfInjector(xCenter+6, yCenter-1, zCenter-1))
        	return true;
    	reset();
    	return false;
    }
    
    private boolean checkCoils(int aX, int aY, int aZ) {
		return     isFusionCoil(aX + 6, aY, aZ - 1)
				&& isFusionCoil(aX + 6, aY, aZ    )
				&& isFusionCoil(aX + 6, aY, aZ + 1)
				
				&& isFusionCoil(aX + 5, aY, aZ - 3)
				&& isFusionCoil(aX + 5, aY, aZ - 2)
				&& isFusionCoil(aX + 5, aY, aZ + 2)
				&& isFusionCoil(aX + 5, aY, aZ + 3)
				
				&& isFusionCoil(aX + 4, aY, aZ - 4)
				&& isFusionCoil(aX + 4, aY, aZ + 4)
				
				&& isFusionCoil(aX + 3, aY, aZ - 5)
				&& isFusionCoil(aX + 3, aY, aZ + 5)
				
				&& isFusionCoil(aX + 2, aY, aZ - 5)
				&& isFusionCoil(aX + 2, aY, aZ + 5)
				
				&& isFusionCoil(aX + 1, aY, aZ - 6)
				&& isFusionCoil(aX + 1, aY, aZ + 6)
				
				&& isFusionCoil(aX    , aY, aZ - 6)
				&& isFusionCoil(aX    , aY, aZ + 6)
				
				&& isFusionCoil(aX - 1, aY, aZ - 6)
				&& isFusionCoil(aX - 1, aY, aZ + 6)
				
				&& isFusionCoil(aX - 2, aY, aZ - 5)
				&& isFusionCoil(aX - 2, aY, aZ + 5)
				
				&& isFusionCoil(aX - 3, aY, aZ - 5)
				&& isFusionCoil(aX - 3, aY, aZ + 5)
				
				&& isFusionCoil(aX - 4, aY, aZ - 4)
				&& isFusionCoil(aX - 4, aY, aZ + 4)
				
				&& isFusionCoil(aX - 5, aY, aZ - 3)
				&& isFusionCoil(aX - 5, aY, aZ - 2)
				&& isFusionCoil(aX - 5, aY, aZ + 2)
				&& isFusionCoil(aX - 5, aY, aZ + 3)
				
				&& isFusionCoil(aX - 6, aY, aZ - 1)
				&& isFusionCoil(aX - 6, aY, aZ    )
				&& isFusionCoil(aX - 6, aY, aZ + 1);
    }
    
    private boolean checkUpperOrLowerHulls(int aX, int aY, int aZ) {
		return     isAdvancedMachineCasing(aX + 6, aY, aZ    )
				
				&& isAdvancedMachineCasing(aX + 5, aY, aZ - 3)
				&& isAdvancedMachineCasing(aX + 5, aY, aZ - 2)
				&& isAdvancedMachineCasing(aX + 5, aY, aZ + 2)
				&& isAdvancedMachineCasing(aX + 5, aY, aZ + 3)
				
				&& isAdvancedMachineCasing(aX + 4, aY, aZ - 4)
				&& isAdvancedMachineCasing(aX + 4, aY, aZ + 4)
				
				&& isAdvancedMachineCasing(aX + 3, aY, aZ - 5)
				&& isAdvancedMachineCasing(aX + 3, aY, aZ + 5)
				
				&& isAdvancedMachineCasing(aX + 2, aY, aZ - 5)
				&& isAdvancedMachineCasing(aX + 2, aY, aZ + 5)
				
				&& isAdvancedMachineCasing(aX    , aY, aZ - 6)
				&& isAdvancedMachineCasing(aX    , aY, aZ + 6)
				
				&& isAdvancedMachineCasing(aX - 2, aY, aZ - 5)
				&& isAdvancedMachineCasing(aX - 2, aY, aZ + 5)
				
				&& isAdvancedMachineCasing(aX - 3, aY, aZ - 5)
				&& isAdvancedMachineCasing(aX - 3, aY, aZ + 5)
				
				&& isAdvancedMachineCasing(aX - 4, aY, aZ - 4)
				&& isAdvancedMachineCasing(aX - 4, aY, aZ + 4)
				
				&& isAdvancedMachineCasing(aX - 5, aY, aZ - 3)
				&& isAdvancedMachineCasing(aX - 5, aY, aZ - 2)
				&& isAdvancedMachineCasing(aX - 5, aY, aZ + 2)
				&& isAdvancedMachineCasing(aX - 5, aY, aZ + 3)
				
				&& isAdvancedMachineCasing(aX - 6, aY, aZ    );
    }
    
    private boolean checkHulls(int aX, int aY, int aZ) {
		return     isAdvancedMachineCasing(aX + 6, aY, aZ - 3)
				&& isAdvancedMachineCasing(aX + 6, aY, aZ - 2)
				&& isAdvancedMachineCasing(aX + 6, aY, aZ + 2)
				&& isAdvancedMachineCasing(aX + 6, aY, aZ + 3)
				
				&& isAdvancedMachineCasing(aX + 3, aY, aZ - 6)
				&& isAdvancedMachineCasing(aX + 3, aY, aZ + 6)
				&& isAdvancedMachineCasing(aX + 2, aY, aZ - 6)
				&& isAdvancedMachineCasing(aX + 2, aY, aZ + 6)
				
				&& isAdvancedMachineCasing(aX - 2, aY, aZ - 6)
				&& isAdvancedMachineCasing(aX - 2, aY, aZ + 6)
				&& isAdvancedMachineCasing(aX - 3, aY, aZ - 6)
				&& isAdvancedMachineCasing(aX - 3, aY, aZ + 6)
				
				&& isAdvancedMachineCasing(aX - 7, aY, aZ    )
				&& isAdvancedMachineCasing(aX + 7, aY, aZ    )
				&& isAdvancedMachineCasing(aX    , aY, aZ - 7)
				&& isAdvancedMachineCasing(aX    , aY, aZ + 7)
				
				&& isAdvancedMachineCasing(aX - 6, aY, aZ - 3)
				&& isAdvancedMachineCasing(aX - 6, aY, aZ - 2)
				&& isAdvancedMachineCasing(aX - 6, aY, aZ + 2)
				&& isAdvancedMachineCasing(aX - 6, aY, aZ + 3)
				
				&& isAdvancedMachineCasing(aX - 4, aY, aZ - 2)
				&& isAdvancedMachineCasing(aX - 4, aY, aZ + 2)
				&& isAdvancedMachineCasing(aX + 4, aY, aZ - 2)
				&& isAdvancedMachineCasing(aX + 4, aY, aZ + 2)
				
				&& isAdvancedMachineCasing(aX - 2, aY, aZ - 4)
				&& isAdvancedMachineCasing(aX - 2, aY, aZ + 4)
				&& isAdvancedMachineCasing(aX + 2, aY, aZ - 4)
				&& isAdvancedMachineCasing(aX + 2, aY, aZ + 4);
    }
    
    private boolean addIfEnergyInjector(int aX, int aY, int aZ) {
    	if (isEnergyInjector(aX, aY, aZ)) {
    		setComputerOf(getMetaTileEntity(aX, aY, aZ), true);
    		return true;
    	}
    	return isAdvancedMachineCasing(aX, aY, aZ);
    }
    
    private boolean addIfInjector(int aX, int aY, int aZ) {
    	if (isInjector(aX, aY, aZ)) {
    		setComputerOf(getMetaTileEntity(aX, aY, aZ), true);
    		return true;
    	}
    	return isAdvancedMachineCasing(aX, aY, aZ);
    }
    
    private boolean addIfExtractor(int aX, int aY, int aZ) {
    	if (isExtractor(aX, aY, aZ)) {
    		setComputerOf(getMetaTileEntity(aX, aY, aZ), true);
    		return true;
    	}
    	return isAdvancedMachineCasing(aX, aY, aZ);
    }
    
    private boolean isAdvancedMachineCasing(int aX, int aY, int aZ) {
    	return getBaseMetaTileEntity().getBlockID(aX, aY, aZ) == GregTech_API.sBlockList[0].blockID && getBaseMetaTileEntity().getMetaID(aX, aY, aZ) == 15;
    }
    
    private boolean isFusionCoil(int aX, int aY, int aZ) {
    	return getBaseMetaTileEntity().getBlockID(aX, aY, aZ) == GregTech_API.sBlockList[0].blockID && getBaseMetaTileEntity().getMetaID(aX, aY, aZ) ==  1;
    }
    
    private boolean isEnergyInjector(int aX, int aY, int aZ) {
    	MetaTileEntity tMetaTileEntity = getMetaTileEntity(aX, aY, aZ);
    	if (tMetaTileEntity == null) return false;
    	return tMetaTileEntity instanceof GT_MetaTileEntity_FusionEnergyInjector;
    }
    
    private boolean isInjector(int aX, int aY, int aZ) {
    	MetaTileEntity tMetaTileEntity = getMetaTileEntity(aX, aY, aZ);
    	if (tMetaTileEntity == null) return false;
    	return tMetaTileEntity instanceof GT_MetaTileEntity_FusionInjector;
    }
    
    private boolean isExtractor(int aX, int aY, int aZ) {
    	MetaTileEntity tMetaTileEntity = getMetaTileEntity(aX, aY, aZ);
    	if (tMetaTileEntity == null) return false;
    	return tMetaTileEntity instanceof GT_MetaTileEntity_FusionExtractor;
    }
    
    private MetaTileEntity getMetaTileEntity(int aX, int aY, int aZ) {
    	TileEntity tTileEntity = getBaseMetaTileEntity().getTileEntity(aX, aY, aZ);
    	if (!(tTileEntity instanceof IGregTechTileEntity)) return null;
    	if (((IGregTechTileEntity)tTileEntity).getMetaTileEntity() == null) return null;
    	return ((IGregTechTileEntity)tTileEntity).getMetaTileEntity();
    }
    
    @Override
    public void onMachineBlockUpdate() {
    	mUpdate = 100;
    }
    
    @Override
    public void onPostTick() {
	    if (getBaseMetaTileEntity().isServerSide()) {
	    	if (mUpdate--==0) {
	        	mMachine = checkMachine();
	    	}
	    	if (mMaxProgresstime > 0) {
	    		if (mMachine && decreaseStoredEU(-mEUt)) {
		    		if (++mProgresstime>mMaxProgresstime) {
		    			addOutput(mOutputItem1);
		    			mOutputItem1 = null;
		    			mProgresstime = 0;
		    			mMaxProgresstime = 0;
			    		checkRecipe();
		    		}
		    	} else {
	    			addOutput(mOutputItem1);
	    			mOutputItem1 = null;
	    			mProgresstime = 0;
	    			mMaxProgresstime = 0;
		    	}
	    	} else {
	    		if (getBaseMetaTileEntity().isAllowedToWork()) checkRecipe();
	    	}
	    	getBaseMetaTileEntity().setActive(mMaxProgresstime > 0);
		}
    }
    
    private boolean checkRecipe() {
    	if (!mMachine) return false;
    	int tRecipe = GT_Recipe.findEqualFusionRecipeIndex(getPrimaryInput(), getSecondaryInput());
    	if (tRecipe != -1 && consumeInput(GT_Recipe.sFusionRecipes.get(tRecipe).mInput1, GT_Recipe.sFusionRecipes.get(tRecipe).mInput2, getBaseMetaTileEntity().isActive()?0:GT_Recipe.sFusionRecipes.get(tRecipe).mStartEU)) {
    		mMaxProgresstime = GT_Recipe.sFusionRecipes.get(tRecipe).mDuration;
		    mEUt = GT_Recipe.sFusionRecipes.get(tRecipe).mEUt;
		    if (GT_Recipe.sFusionRecipes.get(tRecipe).mOutput1 == null) {
		    	mOutputItem1 = null;
		    } else {
			  	mOutputItem1 = GT_Recipe.sFusionRecipes.get(tRecipe).mOutput1.copy();
		    }
		    return true;
    	}
    	return false;
    }
    
    private ItemStack getPrimaryInput() {
    	for (IGregTechTileEntity tTileEntity : mPrimaryInjectors) {
    		if (tTileEntity.getMetaTileEntity() != null && tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_FusionInjector) {
    			ItemStack rStack = ((GT_MetaTileEntity_FusionInjector)tTileEntity.getMetaTileEntity()).getMaterial();
    			if (rStack != null) return rStack;
    		}
    	}
    	return null;
    }
    
    private ItemStack getSecondaryInput() {
    	for (IGregTechTileEntity tTileEntity : mSecondaryInjectors) {
    		if (tTileEntity.getMetaTileEntity() != null && tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_FusionInjector) {
    			ItemStack rStack = ((GT_MetaTileEntity_FusionInjector)tTileEntity.getMetaTileEntity()).getMaterial();
    			if (rStack != null) return rStack;
    		}
    	}
    	return null;
    }
    
    private int getStoredEU() {
    	int rEU = 0;
    	for (IGregTechTileEntity tTileEntity : mEnergyInjectors) {
    		rEU += tTileEntity.getEnergyStored();
    	}
    	return rEU;
    }
    
    private boolean decreaseStoredEU(int aEU) {
    	if (aEU <= 0) return true;
    	if (getStoredEU() < aEU) return false;
    	for (IGregTechTileEntity tTileEntity : mEnergyInjectors) {
    		if (aEU > tTileEntity.getEnergyStored()) {
    			aEU -= tTileEntity.getEnergyStored();
    			if (!tTileEntity.decreaseStoredEnergyUnits(tTileEntity.getEnergyStored(), true)) return false;
    		} else {
    			return tTileEntity.decreaseStoredEnergyUnits(aEU, true);
    		}
    	}
    	return false;
    }
    
    private boolean consumeInput(ItemStack aInput1, ItemStack aInput2, int aEU) {
    	if (aInput1 != null && aInput2 != null) {
	    	if (aEU <= 0 || getStoredEU() >= aEU) {
		    	for (IGregTechTileEntity tTileEntity : mPrimaryInjectors) {
		    		if (tTileEntity.getMetaTileEntity() != null && tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_FusionInjector) {
		    			ItemStack tStack = ((GT_MetaTileEntity_FusionInjector)tTileEntity.getMetaTileEntity()).getMaterial();
		    			if (tStack != null) {
		    				if (tStack.isItemEqual(aInput1) && tStack.stackSize >= aInput1.stackSize) {
			    		    	for (IGregTechTileEntity tTileEntity2 : mSecondaryInjectors) {
			    		    		if (tTileEntity2.getMetaTileEntity() != null && tTileEntity2.getMetaTileEntity() instanceof GT_MetaTileEntity_FusionInjector) {
			    		    			if (((GT_MetaTileEntity_FusionInjector)tTileEntity2.getMetaTileEntity()).consumeMaterial(aInput2)) {
			    		    				return decreaseStoredEU(aEU) && ((GT_MetaTileEntity_FusionInjector)tTileEntity.getMetaTileEntity()).consumeMaterial(aInput1);
			    		    			}
			    		    		}
			    		    	}
			    		    	return false;
		    				}
		    			}
		    		}
		    	}
		    	for (IGregTechTileEntity tTileEntity : mSecondaryInjectors) {
		    		if (tTileEntity.getMetaTileEntity() != null && tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_FusionInjector) {
		    			ItemStack tStack = ((GT_MetaTileEntity_FusionInjector)tTileEntity.getMetaTileEntity()).getMaterial();
		    			if (tStack != null) {
		    				if (tStack.isItemEqual(aInput1) && tStack.stackSize >= aInput1.stackSize) {
			    		    	for (IGregTechTileEntity tTileEntity2 : mPrimaryInjectors) {
			    		    		if (tTileEntity2.getMetaTileEntity() != null && tTileEntity2.getMetaTileEntity() instanceof GT_MetaTileEntity_FusionInjector) {
			    		    			if (((GT_MetaTileEntity_FusionInjector)tTileEntity2.getMetaTileEntity()).consumeMaterial(aInput2)) {
			    		    				return decreaseStoredEU(aEU) && ((GT_MetaTileEntity_FusionInjector)tTileEntity.getMetaTileEntity()).consumeMaterial(aInput1);
			    		    			}
			    		    		}
			    		    	}
			    		    	return false;
		    				}
		    			}
		    		}
		    	}
	    	}
    	}
    	return false;
    }
    
    private void addOutput(ItemStack aOutput) {
    	if (aOutput == null) return;
    	LiquidStack tLiquid = LiquidContainerRegistry.getLiquidForFilledItem(aOutput);
    	if (tLiquid == null) {
    		for (IGregTechTileEntity tTileEntity : mPlasmaExtractors) {
    			ItemStack tStack = tTileEntity.getStackInSlot(1);
    			if (tStack == null) {
    				tTileEntity.setInventorySlotContents(1, aOutput.copy());
    				return;
    			}
    			if (tStack.isItemEqual(aOutput) && tStack.stackSize + aOutput.stackSize <= tStack.getMaxStackSize()) {
    				tStack.stackSize+=aOutput.stackSize;
        			return;
    			}
    		}
    	} else {
    		for (IGregTechTileEntity tTileEntity : mPlasmaExtractors) {
    			if (tTileEntity.getMetaTileEntity().fill(tLiquid, false) == tLiquid.amount) {
    				tTileEntity.getMetaTileEntity().fill(tLiquid, true);
    				return;
    			}
    		}
    	}
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (aSide != aFacing) return aActive?20:19;
		return aActive?50:48;
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
		return "FUUUUUUU-SION, HAH!";
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return false;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return false;
	}
}
