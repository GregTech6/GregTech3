package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidEvent;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaTileEntity_FusionExtractor extends GT_MetaTileEntity_BasicTank {
	
	public IGregTechTileEntity mFusionComputer;
	
	public GT_MetaTileEntity_FusionExtractor(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_FusionExtractor() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex < 2;}
	@Override public boolean isFacingValid(byte aFacing)			{return false;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 145);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_FusionExtractor();
	}
	
	@Override
    public void onPostTick() {
    	if (mLiquid == null || (mLiquid.itemID != 0 && mLiquid.amount == 0)) mLiquid = new LiquidStack(0, 0, 0);
    	
    	if (getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getTimer()%20==0) {
    		if (mLiquid.itemID <= 0 || mLiquid.amount <= 0) {
    			mInventory[2] = null;
    		} else {
    			mInventory[2] = mLiquid.asItemStack();
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
    		getBaseMetaTileEntity().setActive(mFusionComputer!=null&&mFusionComputer.isActive());
    	}
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
    	if (aSide == 0 || aSide == 1) return aActive?20:19;
    	return 16;
	}

	@Override
	protected String getDescription() {
		return "Extracts your fused Materials out of the Coils";
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
		return +100;
	}
}
