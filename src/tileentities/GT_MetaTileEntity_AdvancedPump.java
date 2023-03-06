package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_ModHandler;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.liquids.ILiquid;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidEvent;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaTileEntity_AdvancedPump extends MetaTileEntity {
	
	public LiquidStack mLiquid = new LiquidStack(0, 0, 0);
	
	public ArrayList<ChunkPosition> mPumpList = new ArrayList<ChunkPosition>();
	
	public int mPumpedBlockID1 = -1, mPumpedBlockID2 = -1;
	
	public GT_MetaTileEntity_AdvancedPump(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_AdvancedPump() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return false;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return true;}
	@Override public boolean isFacingValid(byte aFacing)			{return false;}
	@Override public boolean isEnetInput() 							{return true;}
	@Override public boolean isInputFacing(byte aSide)				{return true;}
	@Override public int maxEUInput()								{return 128;}
    @Override public int maxEUStore()								{return 100000;}
    @Override public int maxMJStore()								{return maxEUStore();}
    @Override public int maxSteamStore()							{return maxEUStore();}
	@Override public int getInvSize()								{return 3;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 130);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_AdvancedPump();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		if (mLiquid != null) {
			aNBT.setInteger("mItemCount", mLiquid.amount);
    		aNBT.setInteger("mItemID"	, mLiquid.itemID);
    		aNBT.setInteger("mItemMeta"	, mLiquid.itemMeta);
		}
		aNBT.setInteger("mPumpedBlockID1", mPumpedBlockID1);
		aNBT.setInteger("mPumpedBlockID2", mPumpedBlockID2);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
    	mLiquid = new LiquidStack(aNBT.getInteger("mItemID"), aNBT.getInteger("mItemCount"), aNBT.getInteger("mItemMeta"));
    	mPumpedBlockID1 = aNBT.getInteger("mPumpedBlockID1");
    	mPumpedBlockID2 = aNBT.getInteger("mPumpedBlockID2");
	}
	
    @Override
    public void onPostTick() {
    	if (mLiquid == null) mLiquid = new LiquidStack(0, 0, 0);
    	
    	if (getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getTimer()%10==0) {
    		
    		if (LiquidContainerRegistry.isEmptyContainer(mInventory[1])) {
				ItemStack tOutput = LiquidContainerRegistry.fillLiquidContainer(mLiquid, mInventory[1]);
				if (tOutput != null && (mInventory[2] == null || (tOutput.isItemEqual(mInventory[2]) && mInventory[2].stackSize < tOutput.getMaxStackSize()))) {
	   				tOutput.stackSize = 1;
					mLiquid.amount -= LiquidContainerRegistry.getLiquidForFilledItem(tOutput).amount;
					getBaseMetaTileEntity().decrStackSize(1, 1);
					if (mInventory[2] == null) {
						mInventory[2] = tOutput;
					} else {
						mInventory[2].stackSize++;
					}
					if (mLiquid.amount <= 0) {
						mLiquid = new LiquidStack(0, 0, 0);
					}
				}
			}
    		
    		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().getEnergyStored() > 2560 && mLiquid.amount + 1000 <= getCapacity()) {
        		boolean tMovedOneDown = false;
        		
        		if (getBaseMetaTileEntity().getTimer()%100==0) {
        			tMovedOneDown = moveOneDown();
        		}
        		
        		if (mPumpedBlockID1 <= 0 || mPumpedBlockID2 <= 0 || mLiquid.itemID <= 0) {
            		getFluid(getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord(), getYOfPumpHead() - 1, getBaseMetaTileEntity().getZCoord()));
            		if (mPumpedBlockID1 <= 0 || mPumpedBlockID2 <= 0 || mLiquid.itemID <= 0) {
            			getFluid(getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord(), getYOfPumpHead(), getBaseMetaTileEntity().getZCoord() + 1));
            		}
            		if (mPumpedBlockID1 <= 0 || mPumpedBlockID2 <= 0 || mLiquid.itemID <= 0) {
            			getFluid(getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord(), getYOfPumpHead(), getBaseMetaTileEntity().getZCoord() - 1));
            		}
            		if (mPumpedBlockID1 <= 0 || mPumpedBlockID2 <= 0 || mLiquid.itemID <= 0) {
            			getFluid(getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord() + 1, getYOfPumpHead(), getBaseMetaTileEntity().getZCoord()));
            		}
            		if (mPumpedBlockID1 <= 0 || mPumpedBlockID2 <= 0 || mLiquid.itemID <= 0) {
            			getFluid(getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord() - 1, getYOfPumpHead(), getBaseMetaTileEntity().getZCoord()));
            		}
                } else {
                	if (getYOfPumpHead() < getBaseMetaTileEntity().getYCoord()) {
    		        	if (tMovedOneDown || (mPumpList.isEmpty() && getBaseMetaTileEntity().getTimer() % 200 == 100) || getBaseMetaTileEntity().getTimer() % 72000 == 100) {
    		        		mPumpList.clear();
    		        		for (int y = getBaseMetaTileEntity().getYCoord() - 1, yHead = getYOfPumpHead(); mPumpList.isEmpty() && y >= yHead; y--) {
    		        			scanForFluid(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord(), mPumpList, getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getZCoord(), 64);
    		        		}
    		        	}
    		        	if (!tMovedOneDown && !mPumpList.isEmpty()) {
    			        	consumeFluid(mPumpList.get(mPumpList.size()-1).x, mPumpList.get(mPumpList.size()-1).y, mPumpList.get(mPumpList.size()-1).z);
    			        	mPumpList.remove(mPumpList.size()-1);
    		        	}
                	}
        		}
    		}
    		getBaseMetaTileEntity().setActive(!mPumpList.isEmpty());
    	}
    }
    
    private boolean moveOneDown() {
    	if (mInventory[0] == null || mInventory[0].stackSize < 1 || !mInventory[0].isItemEqual(GT_ModHandler.getIC2Item("miningPipe", 1))) return false;
    	int yHead = getYOfPumpHead();
    	if (yHead <= 0) return false;
    	consumeFluid(getBaseMetaTileEntity().getXCoord(), yHead - 1, getBaseMetaTileEntity().getZCoord());
    	if (getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord(), yHead - 1, getBaseMetaTileEntity().getZCoord()) != 0) return false;
    	if (!getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord(), yHead - 1, getBaseMetaTileEntity().getZCoord(), GT_ModHandler.getIC2Item("miningPipeTip", 1).itemID)) return false;
    	if (yHead != getBaseMetaTileEntity().getYCoord()) getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord(), yHead, getBaseMetaTileEntity().getZCoord(), GT_ModHandler.getIC2Item("miningPipe", 1).itemID);
    	getBaseMetaTileEntity().decrStackSize(0, 1);
    	return true;
    }
    
    private int getYOfPumpHead() {
    	int y = getBaseMetaTileEntity().getYCoord() - 1;
    	while (getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord()) == GT_ModHandler.getIC2Item("miningPipe", 1).itemID) y--;
    	if (y == getBaseMetaTileEntity().getYCoord() - 1) {
    		if (getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord()) != GT_ModHandler.getIC2Item("miningPipeTip", 1).itemID) {
        		return y + 1;
    		}
    	} else {
    		if (getBaseMetaTileEntity().getBlockID(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord()) != GT_ModHandler.getIC2Item("miningPipeTip", 1).itemID) {
    			getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord(), y, getBaseMetaTileEntity().getZCoord(), GT_ModHandler.getIC2Item("miningPipeTip", 1).itemID);
    		}
    	}
    	return y;
    }
    
    private void scanForFluid(int aX, int aY, int aZ, ArrayList<ChunkPosition> aList, int mX, int mZ, int mDist) {
    	boolean pX = addIfFluidAndNotAlreadyAdded(aX + 1, aY, aZ, aList),
    			nX = addIfFluidAndNotAlreadyAdded(aX - 1, aY, aZ, aList),
    			pZ = addIfFluidAndNotAlreadyAdded(aX, aY, aZ + 1, aList),
    			nZ = addIfFluidAndNotAlreadyAdded(aX, aY, aZ - 1, aList);
    	
    	if (pX && aX < mX + mDist) {
    		scanForFluid(aX + 1, aY, aZ, aList, mX, mZ, mDist);
    	}
    	if (nX && aX > mX - mDist) {
    		scanForFluid(aX - 1, aY, aZ, aList, mX, mZ, mDist);
    	}
    	if (pZ && aZ < mZ + mDist) {
    		scanForFluid(aX, aY, aZ + 1, aList, mX, mZ, mDist);
    	}
    	if (nZ && aZ > mZ - mDist) {
    		scanForFluid(aX, aY, aZ - 1, aList, mX, mZ, mDist);
    	}
    	if (addIfFluidAndNotAlreadyAdded(aX, aY + 1, aZ, aList) || (aX == mX && aZ == mZ && aY < getBaseMetaTileEntity().getYCoord())) {
    		scanForFluid(aX, aY + 1, aZ, aList, mX, mZ, mDist);
    	}
    }
    
    private boolean addIfFluidAndNotAlreadyAdded(int aX, int aY, int aZ, ArrayList<ChunkPosition> aList) {
    	ChunkPosition tCoordinate = new ChunkPosition(aX, aY, aZ);
    	if (!aList.contains(tCoordinate)) {
    		int tID = getBaseMetaTileEntity().getBlockID(aX, aY, aZ);
    		if (mPumpedBlockID1 == tID || mPumpedBlockID2 == tID) {
    			aList.add(tCoordinate);
    			return true;
    		}
    	}
    	return false;
    }
    
    private void getFluid(int aID) {
    	if (aID > 0) {
			if (aID == Block.lavaStill.blockID || aID == Block.lavaMoving.blockID) {
				mPumpedBlockID1 = Block.lavaStill.blockID;
				mPumpedBlockID2 = Block.lavaMoving.blockID;
				mLiquid = new LiquidStack(Block.lavaStill.blockID, 0, 0);
				return;
			}
			if (aID == Block.waterStill.blockID || aID == Block.waterMoving.blockID) {
				mPumpedBlockID1 = Block.waterStill.blockID;
				mPumpedBlockID2 = Block.waterMoving.blockID;
				mLiquid = new LiquidStack(Block.waterStill.blockID, 0, 0);
				return;
			}
			if (Block.blocksList[aID] instanceof ILiquid) {
				int tID = ((ILiquid)Block.blocksList[aID]).stillLiquidId();
				if (tID > 0) {
					mPumpedBlockID1 = aID;
					mPumpedBlockID2 = tID;
					mLiquid = new LiquidStack(tID, 0, 0);
					return;
				}
			}
    	}
		mPumpedBlockID1 = -1;
		mPumpedBlockID2 = -1;
		mLiquid = new LiquidStack(0, 0, 0);
    }
    
    private boolean consumeFluid(int aX, int aY, int aZ) {
    	int tID = getBaseMetaTileEntity().getBlockID(aX, aY, aZ);
    	if (mPumpedBlockID1 == tID || mPumpedBlockID2 == tID) {
        	if (getBaseMetaTileEntity().getMetaID(aX, aY, aZ) == 0) {
        		mLiquid.amount += 1000;
        		getBaseMetaTileEntity().decreaseStoredEnergyUnits(1280, true);
        	} else {
        		getBaseMetaTileEntity().decreaseStoredEnergyUnits( 320, true);
        	}
    		getBaseMetaTileEntity().getWorld().setBlock(aX, aY, aZ, 0, 0, 3);
    		return true;
    	}
    	return false;
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
    	if (aSide == 0) return 110;
    	if (aSide == 1) return aActive?69:68;
    	return 109;
	}
	
	@Override
	protected String getDescription() {
		return "The best way of emptying Oceans!";
	}
	
	@Override
	public LiquidStack getLiquid() {
		return mLiquid;
	}
	
	@Override
	public int getCapacity() {
		return 16000;
	}
	
	@Override
	public int fill(LiquidStack resource, boolean doFill) {
		return 0;
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
		
		/*
		if (mLiquid.amount <= 0)
			mLiquid = null;
		 */
		
		if (doDrain && getBaseMetaTileEntity()!=null)
			LiquidEvent.fireEvent(new LiquidEvent.LiquidDrainingEvent(drained, getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), this));
		
		return drained;
	}
	
	@Override
	public int getTankPressure() {
		return 100;
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 2;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aStack.isItemEqual(GT_ModHandler.getIC2Item("miningPipe", 1))?aIndex==0:aIndex==1;
	}
}
