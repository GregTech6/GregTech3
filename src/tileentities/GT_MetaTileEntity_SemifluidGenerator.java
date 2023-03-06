package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidEvent;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaTileEntity_SemifluidGenerator extends GT_MetaTileEntity_BasicTank {
	
	public GT_MetaTileEntity_SemifluidGenerator(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_SemifluidGenerator() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex < 2;}
	@Override public boolean isFacingValid(byte aFacing)			{return false;}
	@Override public boolean isEnetOutput() 						{return true;}
	@Override public boolean isOutputFacing(byte aSide)				{return true;}
	@Override public int maxEUOutput()								{return 8;}
	@Override public int maxEUStore()								{return 1000000;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 120);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_SemifluidGenerator();
	}
	
    @Override
    public void onPostTick() {
    	if (mLiquid == null || (mLiquid.itemID != 0 && mLiquid.amount == 0)) mLiquid = new LiquidStack(0, 0, 0);
    	
    	if (getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getTimer()%10==0) {
    		
    		if (mLiquid.itemID <= 0 || mLiquid.amount <= 0) {
    			if (getBaseMetaTileEntity().getEnergyStored() < maxEUOutput())
    				mInventory[2] = null;
    			else
    				if (mInventory[2] == null)
    					mInventory[2] = new ItemStack(Block.fire, 1);
    		} else {
    			mInventory[2] = mLiquid.asItemStack();
    			while (getBaseMetaTileEntity().getEnergyStored() < maxEUOutput()*10 && mLiquid.amount > 0) {
    				if (getBaseMetaTileEntity().increaseStoredEnergyUnits(getFuelValue(mLiquid), true)) mLiquid.amount--;
    			}
    		}
    		if (getBaseMetaTileEntity().isAllowedToWork() && mInventory[0] != null && getBaseMetaTileEntity().getEnergyStored() <= 100) {
    			for (int i = 0; i < GT_Recipe.sDenseLiquidFuels.size(); i++) {
    				if (mInventory[0].isItemEqual(GT_Recipe.sDenseLiquidFuels.get(i).mInput1)) {
    					if (LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0]) == null) {
    						if (GT_Recipe.sDenseLiquidFuels.get(i).mOutput1 != null) {
    							if (mInventory[1] == null) {
    								if (getBaseMetaTileEntity().increaseStoredEnergyUnits(GT_Recipe.sDenseLiquidFuels.get(i).mStartEU*1000, true)) {
    									getBaseMetaTileEntity().decrStackSize(0, 1);
    									mInventory[1] = GT_Recipe.sDenseLiquidFuels.get(i).mOutput1.copy();
    								}
    							} else if (mInventory[1].isItemEqual(GT_Recipe.sDenseLiquidFuels.get(i).mOutput1) && mInventory[1].stackSize + GT_Recipe.sDenseLiquidFuels.get(i).mOutput1.stackSize <= mInventory[1].getMaxStackSize()) {
    								if (getBaseMetaTileEntity().increaseStoredEnergyUnits(GT_Recipe.sDenseLiquidFuels.get(i).mStartEU*1000, true)) {
        								getBaseMetaTileEntity().decrStackSize(0, 1);
        	    						mInventory[1].stackSize += GT_Recipe.sDenseLiquidFuels.get(i).mOutput1.stackSize;
    								}
    							}
    						} else {
    							if (getBaseMetaTileEntity().increaseStoredEnergyUnits(GT_Recipe.sDenseLiquidFuels.get(i).mStartEU*1000, true)) {
    								getBaseMetaTileEntity().decrStackSize(0, 1);
    							}
    						}
    					}
    					break;
    				}
    			}
    			if (mInventory[0] != null && mLiquid.itemID != 0 && LiquidContainerRegistry.isFilledContainer(mInventory[0]) && LiquidContainerRegistry.containsLiquid(mInventory[0], mLiquid) && isValidFuel(LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0])) && LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0]).amount + mLiquid.amount <= getCapacity()) {
    				boolean temp = true;
    				ItemStack tContainer;
    				if (null != (tContainer = GT_Utility.getContainerForFilledItem(mInventory[0]))) {
    					if (mInventory[1] == null) {
    						mInventory[1] = tContainer.copy();
    					} else if (mInventory[1].isItemEqual(tContainer) && mInventory[1].stackSize + tContainer.stackSize <= tContainer.getMaxStackSize()) {
    						mInventory[1].stackSize+=tContainer.stackSize;
    					} else {
    						temp = false;
    					}
    				}
    				if (temp) {
	    				mLiquid.amount+=LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0]).amount;
	    				getBaseMetaTileEntity().decrStackSize(0, 1);
    				}
    			} else if (mInventory[0] != null && mLiquid.itemID == 0 && LiquidContainerRegistry.isFilledContainer(mInventory[0]) && isValidFuel(LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0])) && LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0]).amount <= getCapacity()) {
    				boolean temp = true;
    				ItemStack tContainer;
    				if (null != (tContainer = GT_Utility.getContainerForFilledItem(mInventory[0]))) {
    					if (mInventory[1] == null) {
    						mInventory[1] = tContainer.copy();
    					} else if (mInventory[1].isItemEqual(tContainer) && mInventory[1].stackSize + tContainer.stackSize <= tContainer.getMaxStackSize()) {
    						mInventory[1].stackSize+=tContainer.stackSize;
    					} else {
    						temp = false;
    					}
    				}
    				if (temp) {
	    				LiquidStack tLiquid = LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0]);
	    				mLiquid = tLiquid.copy();
	    				getBaseMetaTileEntity().decrStackSize(0, 1);
    				}
    			}
    		}
			if (LiquidContainerRegistry.isEmptyContainer(mInventory[0])) {
				ItemStack tOutput = LiquidContainerRegistry.fillLiquidContainer(mLiquid, mInventory[0]);
				if (tOutput != null && (mInventory[1] == null || (tOutput.isItemEqual(mInventory[1]) && mInventory[1].stackSize < tOutput.getMaxStackSize()))) {
	   				tOutput.stackSize = 1;
					mLiquid.amount -= LiquidContainerRegistry.getLiquidForFilledItem(tOutput).amount;
					getBaseMetaTileEntity().decrStackSize(0, 1);
					if (mInventory[1] == null) {
						mInventory[1] = tOutput;
					} else {
						mInventory[1].stackSize++;
					}
					if (mLiquid.amount <= 0) {
	    				mLiquid = new LiquidStack(0, 0, 0);
					}
				}
			}
			getBaseMetaTileEntity().setActive(getBaseMetaTileEntity().getEnergyStored() >= maxEUOutput());
    	}
    }
    
    public boolean isValidFuel(LiquidStack aLiquid) {
    	return getFuelValue(aLiquid)>0;
    }
    
    /**
     * @return EU per MB
     */
    public int getFuelValue(LiquidStack aLiquid) {
    	if (aLiquid == null) return 0;
    	LiquidStack tLiquid;
    	for (int i = 0; i < GT_Recipe.sDenseLiquidFuels.size(); i++) {
    		if ((tLiquid = LiquidContainerRegistry.getLiquidForFilledItem(GT_Recipe.sDenseLiquidFuels.get(i).mInput1)) != null) if (aLiquid.isLiquidEqual(tLiquid)) return GT_Recipe.sDenseLiquidFuels.get(i).mStartEU;
        }
    	return 0;
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
    	if (aSide == 0)
    		return 38;
    	else if (aSide == 1)
    		return 29;
    	else
    		return 85;
	}

	@Override
	public String getMainInfo() {
		if (mLiquid == null || mLiquid.itemID == 0 || mLiquid.asItemStack() == null) return "";
		return "" + mLiquid.asItemStack().getDisplayName();
	}
	@Override
	public String getSecondaryInfo() {
		return "" + mLiquid.amount;
	}
	@Override
	public String getTertiaryInfo() {
		return "";
	}
	@Override
	public boolean isGivingInformation() {
		return true;
	}
	
	@Override
	protected String getDescription() {
		return "Makes Energy of more dense Liquids";
	}
	
	@Override
	public LiquidStack getLiquid() {
		return mLiquid;
	}
	
	@Override
	public int getCapacity() {
		return 10000;
	}

	@Override
	public int fill(LiquidStack resource, boolean doFill) {
		if (resource == null || resource.itemID <= 0 || !isValidFuel(resource))
			return 0;
		
		if (mLiquid == null || mLiquid.itemID <= 0) {
			if (resource.amount <= getCapacity()) {
				if (doFill)
					mLiquid = resource.copy();
				return resource.amount;
			} else {
				if (doFill) {
					mLiquid = resource.copy();
					mLiquid.amount = getCapacity();
					if (getBaseMetaTileEntity()!=null)
						LiquidEvent.fireEvent(new LiquidEvent.LiquidFillingEvent(mLiquid, getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), this));
				}
				return getCapacity();
			}
		}
		
		if (!mLiquid.isLiquidEqual(resource))
			return 0;

		int space = getCapacity() - mLiquid.amount;
		if (resource.amount <= space) {
			if (doFill)
				mLiquid.amount += resource.amount;
			return resource.amount;
		} else {
			if (doFill)
				mLiquid.amount = getCapacity();
			return space;
		}

	}
	@Override
	public LiquidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public int getTankPressure() {
		return -100;
	}
}
