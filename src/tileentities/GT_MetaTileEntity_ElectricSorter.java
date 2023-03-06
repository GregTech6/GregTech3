package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_Utility;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_ElectricSorter extends GT_MetaTileEntity_ElectricBufferSmall {
	
	public byte mTargetDirection, oTargetDirection;
	
	public GT_MetaTileEntity_ElectricSorter(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_ElectricSorter() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return false;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return true;}
    @Override public int maxEUStore()								{return 10000;}
    @Override public int maxMJStore()								{return maxEUStore()/2;}
	@Override public int getMinimumStoredEU()						{return 2000;}
    @Override public boolean isValidSlot(int aIndex)				{return aIndex<1;}
    @Override public boolean isOutputFacing(byte aSide)				{return mTargetDirection == aSide || getBaseMetaTileEntity().getBackFacing() == aSide;}
	@Override public int getInvSize()								{return 11;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 107);}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_ElectricSorter();
	}

	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
    	aNBT.setInteger("mTargetDirection", mTargetDirection);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mTargetDirection = (byte)aNBT.getInteger("mTargetDirection");
    	oTargetDirection = -1;
	}
	
	public void onPostTick() {
		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getEnergyStored() >= 1000 && (getBaseMetaTileEntity().hasWorkJustBeenEnabled() || getBaseMetaTileEntity().getTimer()%20 == 0 || (mSuccess > 0 && getBaseMetaTileEntity().getTimer()%5 == 0))) {
			if (mInventory[0] != null) {
				
				ArrayList<ItemStack> tList = new ArrayList<ItemStack>();
				for (int i = 1; i < 10; i++) tList.add(mInventory[i]);
				
				int tPrice = 0;
				
				getBaseMetaTileEntity().decreaseStoredEnergyUnits(tPrice = GT_Utility.moveOneItemStack(getBaseMetaTileEntity(), getBaseMetaTileEntity().getIInventoryAtSide(mTargetDirection), getBaseMetaTileEntity().getBackFacing(), (byte)ForgeDirection.getOrientation(mTargetDirection).getOpposite().ordinal(), tList, false, (byte)64, (byte)1, (byte)64, (byte)1)*3, true);
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
	
	@Override
	protected String getDescription() {
		return "The Sorter will put matching Items into the Blue Side.";
	}
}
