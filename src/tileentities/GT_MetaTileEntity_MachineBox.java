package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.liquids.LiquidEvent;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaTileEntity_MachineBox extends GT_MetaTileEntity_BasicTank {
	
	public byte mTier = 0, oTier = 0;
	
	public static final String DESCRIPTIONTEXT = "You just need " + EnumChatFormatting.DARK_PURPLE + "I" + EnumChatFormatting.LIGHT_PURPLE + "m" + EnumChatFormatting.DARK_RED + "a" + EnumChatFormatting.RED + "g" + EnumChatFormatting.YELLOW + "i" + EnumChatFormatting.GREEN + "n" + EnumChatFormatting.AQUA + "a" + EnumChatFormatting.DARK_AQUA + "t" + EnumChatFormatting.BLUE + "i" + EnumChatFormatting.DARK_BLUE + "o" + EnumChatFormatting.DARK_PURPLE + "n" + EnumChatFormatting.RESET + " to use this.";
	
	public GT_MetaTileEntity_MachineBox(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_MachineBox() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return true;}
	@Override public boolean isFacingValid(byte aFacing)			{return true;}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	@Override public boolean isEnetOutput()							{return true;}
	@Override public boolean isEnetInput()							{return true;}
	@Override public boolean isOutputFacing(byte aSide)				{return aSide == getBaseMetaTileEntity().getFrontFacing();}
	@Override public boolean isInputFacing(byte aSide)				{return !isOutputFacing(aSide);}
	@Override public int getMinimumStoredEU()						{return 1000;}
	@Override public int maxEUInput()								{return 32;}
    @Override public int maxEUOutput()								{return 32;}
    @Override public int maxEUStore()								{return 10000;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex < 1;}
	@Override public int getInvSize()								{return 2;}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_MachineBox();
	}
	
	@Override public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setByte("mTier", mTier);
	}
	
	@Override public void loadNBTData(NBTTagCompound aNBT) {
		mTier = aNBT.getByte("mTier");
	}
	
	@Override
	public void onPreTick() {
		if (getBaseMetaTileEntity().isServerSide()) {
			mTier = getBaseMetaTileEntity().getTransformerUpgradeCount();
			if (mTier != oTier) {
				oTier = mTier;
				getBaseMetaTileEntity().issueTextureUpdate();
			}
		}
	}
	
	@Override public void onValueUpdate(short aValue) {
		mTier = (byte)aValue;
	}
	
	@Override public short getUpdateData() {
		return mTier;
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (aSide==aFacing) {
			if (mTier <= 0) return 282;
			if (mTier == 1) return 310;
			if (mTier == 2) return 17;
			if (mTier == 3) return 18;
			if (mTier >= 4) return 311;
		}
		if (mTier <= 0) {
			if (aSide==0) return 32;
			if (aSide==1) return 29;
	    	return 40;
		}
		if (mTier == 1) {
			return 9;
		}
		return 16;
	}
	
	@Override
	protected String getDescription() {
		return DESCRIPTIONTEXT;
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 0;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 0;
	}
	
	@Override
	public LiquidStack getLiquid() {
		return mLiquid;
	}
	
	@Override
	public int getCapacity() {
		return 1000;
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
		return 0;
	}
}
