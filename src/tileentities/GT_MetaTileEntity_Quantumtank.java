package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidEvent;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaTileEntity_Quantumtank extends GT_MetaTileEntity_BasicTank {
	
	public GT_MetaTileEntity_Quantumtank(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_Quantumtank() {
		
	}
	
	@Override public boolean unbreakable()							{return true;}
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex < 2;}
	@Override public boolean isFacingValid(byte aFacing)			{return false;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 114);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_Quantumtank();
	}
	
    @Override
    public void onPostTick() {
    	if (mLiquid == null) mLiquid = new LiquidStack(0, 0, 0);
    	
    	if (getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getTimer()%10==0) {
    		if (mLiquid.itemID <= 0 || mLiquid.amount <= 0) {
    			mInventory[2] = null;
    		} else {
    			mInventory[2] = mLiquid.asItemStack();
    		}
    		if (mInventory[0] != null) {
    			if (mLiquid.itemID != 0 && LiquidContainerRegistry.isFilledContainer(mInventory[0]) && LiquidContainerRegistry.containsLiquid(mInventory[0], mLiquid) && LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0]).amount + mLiquid.amount < getCapacity()) {
    				boolean temp = true;
    				if (mInventory[0].getItem().hasContainerItem()) {
    					ItemStack tContainer = mInventory[0].getItem().getContainerItemStack(mInventory[0]);
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
    			} else if (mLiquid.itemID == 0 && LiquidContainerRegistry.isFilledContainer(mInventory[0]) && LiquidContainerRegistry.getLiquidForFilledItem(mInventory[0]).amount < getCapacity()) {
    				boolean temp = true;
    				if (mInventory[0].getItem().hasContainerItem()) {
    					ItemStack tContainer = mInventory[0].getItem().getContainerItemStack(mInventory[0]);
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
    	}
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
    	if (aSide == 0)
    		return 38;
    	else if (aSide == 1)
    		return 37;
    	else
    		return 36;
	}
	
	@Override
	protected String getDescription() {
		return "With a capacity of 488.28125 Chunks!";
	}
	
	@Override
	public LiquidStack getLiquid() {
		return mLiquid;
	}
	
	@Override
	public int getCapacity() {
		return 2000000000;
	}
	
	@Override
	public int fill(LiquidStack resource, boolean doFill) {
		if (resource == null || resource.itemID <= 0)
			return 0;
		
		if (mLiquid == null || mLiquid.itemID <= 0) {
			if(resource.amount <= getCapacity()) {
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
		if (mLiquid == null || mLiquid.itemID <= 0)
			return null;
		if (mLiquid.amount <= 0)
			return null;

		int used = maxDrain;
		if (mLiquid.amount < used)
			used = mLiquid.amount;

		if (doDrain) {
			mLiquid.amount -= used;
		}

		LiquidStack drained = new LiquidStack(mLiquid.itemID, used, mLiquid.itemMeta);

		if (mLiquid.amount <= 0)
			mLiquid = null;

		if (doDrain && getBaseMetaTileEntity()!=null)
			LiquidEvent.fireEvent(new LiquidEvent.LiquidDrainingEvent(drained, getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), this));

		return drained;
	}

	@Override
	public int getTankPressure() {
		return 100;
	}
}
