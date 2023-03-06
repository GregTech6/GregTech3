package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_AlloySmelter extends GT_MetaTileEntity_BasicMachine {
	
	public int mHeatingCoilTier = 0;
	
	public GT_MetaTileEntity_AlloySmelter(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_AlloySmelter() {
		
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
    	aNBT.setByte("mHeatingCoilTier", (byte)mHeatingCoilTier);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
    	mHeatingCoilTier = aNBT.getByte("mHeatingCoilTier");
	}
	
	@Override
	public void setItemNBT(NBTTagCompound aNBT) {
		super.setItemNBT(aNBT);
		if (mHeatingCoilTier > 0) aNBT.setByte("mHeatingCoilTier", (byte)mHeatingCoilTier);
	}
	
	@Override
	public void onRightclick(EntityPlayer aPlayer) {
	    ItemStack tPlayerItem = aPlayer.inventory.getCurrentItem();
	    if (mHeatingCoilTier <= 0 && GT_OreDictUnificator.isItemStackInstanceOf(tPlayerItem, "craftingHeatingCoilTier01", false)) {
	    	if (!aPlayer.capabilities.isCreativeMode) tPlayerItem.stackSize--;
		    mHeatingCoilTier = 1;
	    	return;
	    }
	    if (mHeatingCoilTier == 1 && GT_OreDictUnificator.isItemStackInstanceOf(tPlayerItem, "craftingHeatingCoilTier02", false)) {
	    	if (!aPlayer.capabilities.isCreativeMode) tPlayerItem.stackSize--;
		    mHeatingCoilTier = 2;
	    	return;
	    }
	    if (mHeatingCoilTier == 2 && GT_OreDictUnificator.isItemStackInstanceOf(tPlayerItem, "craftingHeatingCoilTier03", false)) {
	    	if (!aPlayer.capabilities.isCreativeMode) tPlayerItem.stackSize--;
		    mHeatingCoilTier = 3;
	    	return;
	    }
	    getBaseMetaTileEntity().openGUI(aPlayer, 137);
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_AlloySmelter();
	}
	
	@Override
    public void checkRecipe() {
		GT_Utility.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), 3, 4, (byte)64, (byte)1, (byte)64, (byte)1);
    	if (mInventory[1] != null || mInventory[2] != null) {
    		int tRecipeIndex = GT_Recipe.findEqualAlloySmelterRecipeIndex(mInventory[1], mInventory[2]);
    		if (tRecipeIndex >= 0 && spaceForOutput(GT_Recipe.sAlloySmelterRecipes.get(tRecipeIndex).mOutput1, null) && GT_Recipe.isRecipeInputEqual(true, true, mInventory[1], mInventory[2], GT_Recipe.sAlloySmelterRecipes.get(tRecipeIndex))) {
        		mEUt = GT_Recipe.sAlloySmelterRecipes.get(tRecipeIndex).mEUt;
    			mMaxProgresstime = GT_Recipe.sAlloySmelterRecipes.get(tRecipeIndex).mDuration / (1+mHeatingCoilTier);
    			mOutputItem1 = GT_Recipe.sAlloySmelterRecipes.get(tRecipeIndex).mOutput1.copy();
    			return;
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
		return 252;
	}
	
	@Override
	public int getFrontFacingActive() {
		return 253;
	}
	
	@Override
	protected String getDescription() {
		return "HighTech combination Smelter";
	}
}
