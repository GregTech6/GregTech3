package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GT_MetaTileEntity_Printer extends GT_MetaTileEntity_BasicMachine {
	
	public GT_MetaTileEntity_Printer(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_Printer() {
		
	}
	
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 142);}
	@Override public int dechargerSlotStartIndex()					{return 0;}
	@Override public int dechargerSlotCount()						{return 0;}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_Printer();
	}
	
	@Override
    public void checkRecipe() {
		GT_Utility.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), 3, 4, (byte)64, (byte)1, (byte)64, (byte)1);
    	if (mInventory[3] != null) {
			bOutputBlocked = true;
		} else if (mInventory[1] != null && mInventory[1].stackSize > 0) {
    		if (mInventory[5] == null) {
        		if (mInventory[1].isItemEqual(new ItemStack(Item.reed, 1, 0))) {
            		mEUt = 1;
        			mMaxProgresstime = 200;
        			mOutputItem1 = new ItemStack(Item.paper, 1, 0);
        			mInventory[1].stackSize-=1;
        			return;
        		}
        		if (GT_OreDictUnificator.isItemStackInstanceOf(mInventory[1], "dustWood", false)) {
            		mEUt = 1;
        			mMaxProgresstime = 200;
        			mOutputItem1 = new ItemStack(Item.paper, 1, 0);
        			mInventory[1].stackSize-=1;
        			return;
        		}
    			if (mInventory[2] != null && mInventory[2].stackSize > 0) {
	        		if (mInventory[1].isItemEqual(new ItemStack(Item.paper, 1, 0)) && mInventory[1].stackSize >= 3 && mInventory[2].isItemEqual(new ItemStack(Item.leather, 1, 0))) {
	            		mEUt = 2;
	        			mMaxProgresstime = 400;
	        			mOutputItem1 = new ItemStack(Item.book, 1, 0);
	        			mInventory[1].stackSize-=3;
	        			mInventory[2].stackSize-=1;
	        			return;
	        		}
	        		if (mInventory[1].isItemEqual(new ItemStack(Item.paper, 1, 0)) && mInventory[1].stackSize >= 8 && mInventory[2].isItemEqual(new ItemStack(Item.compass, 1, 0))) {
	            		mEUt = 2;
	        			mMaxProgresstime = 400;
	        			mOutputItem1 = new ItemStack(Item.emptyMap, 1, 0);
	        			mInventory[1].stackSize-=8;
	        			mInventory[2].stackSize-=1;
	        			return;
	        		}
	        		if (GT_OreDictUnificator.isItemStackDye(mInventory[2])) {
	        			mOutputItem1 = GT_ModHandler.getRecipeOutput(new ItemStack[] {mInventory[1], mInventory[2]});
	        			if (mOutputItem1 != null) {
	        				mEUt = 2;
	            			mMaxProgresstime = 200;
	            			mInventory[1].stackSize-=1;
	            			mInventory[2].stackSize-=1;
	            			return;
	        			}
	        			mOutputItem1 = GT_ModHandler.getRecipeOutput(new ItemStack[] {mInventory[1], mInventory[1], mInventory[1], mInventory[1], mInventory[2], mInventory[1], mInventory[1], mInventory[1], mInventory[1]});
	        			if (mOutputItem1 != null && mInventory[1].stackSize > 7) {
	        				mEUt = 2;
	            			mMaxProgresstime = 1600;
	            			mInventory[1].stackSize-=8;
	            			mInventory[2].stackSize-=1;
	            			return;
	        			}
	        		}
    			}
    		} else {
    			if (mInventory[2] != null && mInventory[2].stackSize > 0) {
	        		if ((mInventory[5].itemID == Item.writableBook.itemID || mInventory[5].itemID == Item.writtenBook.itemID) && mInventory[1].isItemEqual(new ItemStack(Item.book, 1, 0)) && mInventory[2].isItemEqual(new ItemStack(Item.dyePowder, 1, 0))) {
	            		mEUt = 1;
	        			mMaxProgresstime = 200;
	        			mOutputItem1 = mInventory[5].copy().splitStack(1);
	        			mInventory[1].stackSize-=1;
	        			mInventory[2].stackSize-=1;
	        			return;
	        		}
	        		if ((mInventory[5].itemID == Item.map.itemID) && mInventory[1].isItemEqual(new ItemStack(Item.emptyMap, 1, 0)) && mInventory[2].isItemEqual(new ItemStack(Item.dyePowder, 1, 0))) {
	            		mEUt = 1;
	        			mMaxProgresstime = 100;
	        			mOutputItem1 = mInventory[5].copy().splitStack(1);
	        			mInventory[1].stackSize-=1;
	        			mInventory[2].stackSize-=1;
	        			return;
	        		}
    			}
    		}
    	}
		mOutputItem1 = null;
    }
	
	@Override
    public boolean hasTwoSeperateInputs() {
    	return true;
    }
    
	@Override
	public int getFrontFacingInactive() {
		return 33;
	}
	
	@Override
	public int getFrontFacingActive() {
		return 34;
	}
	
	@Override
	protected String getDescription() {
		return "It's printing Books and can paint Stuff";
	}
}
