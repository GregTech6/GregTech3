package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_ElectricTypeSorter extends GT_MetaTileEntity_ElectricBufferSmall {
	
	public byte mTargetDirection, oTargetDirection, mMode = 0;
	
	public static String[] sTypeList = {"ore", "gem", "nugget", "dustSmall", "dust", "ingot", "block", "treeLeaves", "treeSapling", "log", "plank", "", "", "beeComb"};
	
	public GT_MetaTileEntity_ElectricTypeSorter(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_ElectricTypeSorter() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return false;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return true;}
    @Override public int maxEUStore()								{return 10000;}
    @Override public int maxMJStore()								{return maxEUStore();}
	@Override public int getMinimumStoredEU()						{return 2000;}
    @Override public boolean isValidSlot(int aIndex)				{return aIndex<1;}
    @Override public boolean isOutputFacing(byte aSide)				{return mTargetDirection == aSide || getBaseMetaTileEntity().getBackFacing() == aSide;}
	@Override public int getInvSize()								{return 2;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 139);}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_ElectricTypeSorter();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
    	aNBT.setInteger("mMode", mMode);
    	aNBT.setInteger("mTargetDirection", mTargetDirection);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMode = (byte)aNBT.getInteger("mMode");
		mTargetDirection = (byte)aNBT.getInteger("mTargetDirection");
    	oTargetDirection = -1;
	}
	
	public void switchModeForward() {
		mMode++;
		switchMode();
	}
	
	public void switchModeBackward() {
		mMode--;
		switchMode();
	}
	
	public void switchMode() {
		if (mMode>=sTypeList.length) mMode = 0;
		if (mMode<0) mMode = (byte)(sTypeList.length-1);
	}
	
	public void onPostTick() {
		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getEnergyStored() >= 1000 && (getBaseMetaTileEntity().hasWorkJustBeenEnabled() || getBaseMetaTileEntity().getTimer()%20 == 0 || (mSuccess > 0 && getBaseMetaTileEntity().getTimer()%5 == 0))) {
			if (mInventory[0] != null) {
				int tPrice = 0;
				
				if (((mMode == 11 && mInventory[0].getItem() instanceof ItemFood) || (mMode == 12 && GT_Recipe.findEqualGrinderRecipeIndex(mInventory[0], new ItemStack(Item.bucketWater, 1)) > -1) || GT_OreDictUnificator.isItemStackInstanceOf(mInventory[0], sTypeList[mMode], true))) {
					getBaseMetaTileEntity().decreaseStoredEnergyUnits(tPrice = GT_Utility.moveOneItemStack(getBaseMetaTileEntity(), getBaseMetaTileEntity().getIInventoryAtSide(mTargetDirection), getBaseMetaTileEntity().getBackFacing(), (byte)ForgeDirection.getOrientation(mTargetDirection).getOpposite().ordinal(), null, false, (byte)64, (byte)1, (byte)64, (byte)1)*3, true);
				}
				if (tPrice <= 0) {
					getBaseMetaTileEntity().decreaseStoredEnergyUnits(tPrice = GT_Utility.moveOneItemStack(getBaseMetaTileEntity(), getBaseMetaTileEntity().getIInventoryAtSide(getBaseMetaTileEntity().getBackFacing()), getBaseMetaTileEntity().getBackFacing(), getBaseMetaTileEntity().getFrontFacing(), null, false, (byte)64, (byte)1, (byte)64, (byte)1)*2, true);
				}
				if (tPrice > 0) {
					mSuccess = 30;
				}
			}
			getBaseMetaTileEntity().setGenericRedstoneOutput(bInvert);
			if (bRedstoneIfFull) {
				getBaseMetaTileEntity().decreaseStoredEnergyUnits(1, true);
				if (mInventory[0] != null) {
					getBaseMetaTileEntity().setGenericRedstoneOutput(!bInvert);
				}
			}
		}
		if (getBaseMetaTileEntity().isServerSide() && mTargetDirection != oTargetDirection) {
			oTargetDirection = mTargetDirection;
			getBaseMetaTileEntity().issueTextureUpdate();
		}
	}
	
	@Override
	protected String getDescription() {
		return "Like the regular Sorter, but with special Item Types instead of Filter Items";
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (aSide == mTargetDirection)
			return 116+(aRedstone?8:0);
		if (aSide == aFacing)
			return 134+(aRedstone?8:0);
		if (ForgeDirection.getOrientation(aSide).getOpposite() == ForgeDirection.getOrientation(aFacing))
			return 113+(aRedstone?8:0);
		int tIndex = 134+(aRedstone?8:0);
		
		switch (aFacing) {
		case 0:
			return tIndex+64;
		case 1:
			return tIndex+32;
		case 2: switch (aSide) {
			case 0: return tIndex+32;
			case 1: return tIndex+32;
			case 4: return tIndex+16;
			case 5: return tIndex+48;
			}
		case 3: switch (aSide) {
			case 0: return tIndex+64;
			case 1: return tIndex+64;
			case 4: return tIndex+48;
			case 5: return tIndex+16;
			}
		case 4: switch (aSide) {
			case 0: return tIndex+16;
			case 1: return tIndex+16;
			case 2: return tIndex+48;
			case 3: return tIndex+16;
			}
		case 5: switch (aSide) {
			case 0: return tIndex+48;
			case 1: return tIndex+48;
			case 2: return tIndex+16;
			case 3: return tIndex+48;
			}
		}
		return tIndex;
	}

	@Override public void onValueUpdate(short aValue) {
		mTargetDirection = (byte)aValue;
	}
	
	@Override public short getUpdateData() {
		return (short)mTargetDirection;
	}
}
