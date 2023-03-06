package gregtechmod.common.tileentities;

import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_ModHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class GT_TileEntity_UUMAssembler extends GT_TileEntityMetaID_Machine {
	
	public LiquidStack mUUMperBlob;
	
	public int mItemCount = 0, mItemID = GT_ModHandler.getIC2Item("matter", 1).itemID, mItemMeta = GT_ModHandler.getIC2Item("matter", 1).getItemDamage(), mStoredLiquidUUM = 0;
	private int[] mUUMprices = new int[20];
	private InternalTank mTank = new InternalTank(this);
	
    public boolean isAccessible(EntityPlayer aPlayer)	{return true;}
    public int getInventorySlotCount() 					{return 52;}
    
    public boolean isEnetInput()       					{return true;}
    public boolean isInputFacing(short aDirection)  	{return true;}
    public int maxEUStore()            					{return 100000;}
    public int maxEUInput()            					{return 128;}
    
    public GT_TileEntity_UUMAssembler() {
    	mUUMperBlob = LiquidContainerRegistry.getLiquidForFilledItem(GT_ModHandler.getIC2Item("matter", 1));
    	if (mUUMperBlob == null) mUUMperBlob = LiquidDictionary.getLiquid("liquidUU", 1000);
    }
    
    public void storeAdditionalData(NBTTagCompound aNBT) {
    	aNBT.setInteger ("mItemCount"		, mItemCount);
    	aNBT.setInteger ("mStoredLiquidUUM"	, mStoredLiquidUUM);
    	aNBT.setIntArray("mUUMprices"		, mUUMprices);
    }
    
    public void getAdditionalData(NBTTagCompound aNBT) {
    	mItemCount			= aNBT.getInteger ("mItemCount");
    	mStoredLiquidUUM	= aNBT.getInteger ("mStoredLiquidUUM");
    	mUUMprices			= aNBT.getIntArray("mUUMprices");
    }
    
    public void onPostTickUpdate() {
	    if (!worldObj.isRemote) {

	    	if (mUUMprices == null || mUUMprices.length < 20) {
	    		mUUMprices = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	    	}
	    	
	    	if (mUUMperBlob != null && mStoredLiquidUUM >= mUUMperBlob.amount) {
	    		mStoredLiquidUUM -= mUUMperBlob.amount;
	    		mItemCount++;
	    	}
	    	
	    	if (mInventory[0] != null && mItemCount + 256 < Integer.MAX_VALUE) {
	    		if (mInventory[0].itemID == mItemID) {
	    			if (mInventory[0].getItemDamage() == mItemMeta) {
	        			mItemCount += mInventory[0].stackSize;
	        			setInventorySlotContents(0, null);
	    			}
	    		}
	    	}
			if (mItemCount > 0) {
				if (mInventory[1] == null) {
	    			mItemCount--;
	    			mInventory[1] = new ItemStack(mItemID, 1, mItemMeta);
	    		}
	    		if (mInventory[1].itemID == mItemID) {
	    			if (mInventory[1].getItemDamage() == mItemMeta) {
	    	    		while (mInventory[1].stackSize < mInventory[1].getMaxStackSize() && mItemCount > 0) {
	    	    			mItemCount--;
	    	    			mInventory[1].stackSize++;
	    	    		}
	    			}
	    		}
			}
			
			updateRecipe();
		}
		if (mTickTimer%10==0) {
			checkUUMRecipesForCreation();
		}
    }

	public void updateRecipe() {
		InventoryCrafting aCrafting = new InventoryCrafting(new Container() {public boolean canInteractWith(EntityPlayer var1) {return false;}}, 3, 3);
		for (int i = 0; i < 9 ; i++) {
			ItemStack  tItemStack = mInventory[i + 2];
			if (tItemStack != null) tItemStack = tItemStack.copy();
			aCrafting.setInventorySlotContents(i, tItemStack);
		}
		
		ItemStack tStack = CraftingManager.getInstance().findMatchingRecipe(aCrafting, worldObj);
		if (tStack == null)
			setInventorySlotContents(11, null);
		else
			setInventorySlotContents(11, tStack.copy());
	}
	
	public void setUUMprice(int aIndex, int aPrice) {
		mUUMprices[aIndex] = aPrice;
	}
	
	private void checkUUMRecipesForCreation() {
		if (mInventory[1] != null && mInventory[1].isItemEqual(GT_ModHandler.getIC2Item("matter", 1))) {
			for (int i = 0; i < 20; i++) {
				int tUUMprice = mUUMprices[i], tEnergycosts = 512 * tUUMprice;
				if (tUUMprice > 0 && (getStored() >= tEnergycosts)) {
					if (mInventory[i + 12] != null && mInventory[i + 32] == null) {
						if (mInventory[1] != null && mInventory[1].isItemEqual(GT_ModHandler.getIC2Item("matter", 1))&& mInventory[1].stackSize >= tUUMprice) {
							if (decreaseStoredEnergy(tEnergycosts, true)) {
								mInventory[i + 32] = mInventory[i + 12].copy();
								decrStackSize(1, tUUMprice);
							}
						}
					}
				}
			}
		}
	}

    public boolean isValidSlot(int aIndex) {
    	return aIndex < 2 || aIndex > 31;
    }
    
    public int getContentCount() {
    	return mItemCount;
    }

    @Override public String getInvName() {return GT_LanguageManager.mNameList1[5];}

	@Override
	public String getMainInfo() {
		return GT_ModHandler.getIC2Item("matter", 1).getDisplayName();
	}
	@Override
	public String getSecondaryInfo() {
		return ""+(mItemCount>0?mItemCount+66:0);
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
    public int getTexture(int aSide, int aMeta) {
    	 if (aSide == 0)
    		 return  3;
    	 else if (aSide == 1)
    		 return  7;
    	 else
    		 return 11;
    }
	@Override
	public boolean isDigitalChest() {
		return true;
	}
	@Override
	public ItemStack[] getStoredItemData() {
		return new ItemStack[] {new ItemStack(mItemID, mItemCount, mItemMeta)};
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return mTank.fill(resource, doFill);
	}
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		return mTank.fill(resource, doFill);
	}
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return null;
	}
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[] {mTank};
	}
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		return mTank;
	}
	
	private class InternalTank implements ILiquidTank {
		public GT_TileEntity_UUMAssembler mAssembler;
		
		public InternalTank(GT_TileEntity_UUMAssembler aAssembler) {
			mAssembler = aAssembler;
		}
		
		@Override
		public LiquidStack getLiquid() {
			return new LiquidStack(mAssembler.mUUMperBlob.itemID, mAssembler.mStoredLiquidUUM, mAssembler.mUUMperBlob.itemMeta);
		}
		
		@Override
		public int getCapacity() {
			return 10000;
		}

		@Override
		public int fill(LiquidStack resource, boolean doFill) {
			if (mAssembler.mUUMperBlob == null || mAssembler.mUUMperBlob.itemID <= 0) return 0;
			if (resource == null || resource.itemID <= 0) return 0;
			if (!mAssembler.mUUMperBlob.isLiquidEqual(resource)) return 0;
			if (doFill) mAssembler.mStoredLiquidUUM += resource.amount;
			return resource.amount;
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

	@Override
	public void setItemCount(int aCount) {
		mItemCount = aCount;
	}
	@Override
	public int getMaxItemCount() {
		return 2000000000;
	}
}
