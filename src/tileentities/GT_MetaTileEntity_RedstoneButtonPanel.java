package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_RedstoneButtonPanel extends MetaTileEntity {
	
	public byte mRedstoneStrength = 0, mUpdate = 0;
	
	public GT_MetaTileEntity_RedstoneButtonPanel(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_RedstoneButtonPanel() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return true;}
    @Override public boolean isValidSlot(int aIndex) 				{return false;}
	@Override public boolean isFacingValid(byte aFacing)			{return true;}
    @Override public int getInvSize()								{return 0;}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_RedstoneButtonPanel();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setByte("mRedstoneStrength", mRedstoneStrength);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		mRedstoneStrength = aNBT.getByte("mRedstoneStrength");
	}
	
	@Override
	public void onValueUpdate(short aValue) {
		mRedstoneStrength = (byte)aValue;
	}
	
	@Override
	public short getUpdateData() {
		return mRedstoneStrength;
	}
	
	@Override
	public void onRightclick(EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
		if (getBaseMetaTileEntity().isServerSide() && aSide == getBaseMetaTileEntity().getFrontFacing()) {
			mUpdate = 2;
			switch (aSide) {
			case 0:
				mRedstoneStrength = (byte)((byte)(  aX*4) + 4 * (byte)(  aZ*4));
				break;
			case 1:
				mRedstoneStrength = (byte)((byte)(  aX*4) + 4 * (byte)(  aZ*4));
				break;
			case 2:
				mRedstoneStrength = (byte)((byte)(4-aX*4) + 4 * (byte)(4-aY*4));
				break;
			case 3:
				mRedstoneStrength = (byte)((byte)(  aX*4) + 4 * (byte)(4-aY*4));
				break;
			case 4:
				mRedstoneStrength = (byte)((byte)(  aZ*4) + 4 * (byte)(4-aY*4));
				break;
			case 5:
				mRedstoneStrength = (byte)((byte)(4-aZ*4) + 4 * (byte)(4-aY*4));
				break;
			}
		}
	}
	
    @Override
    public void onPreTick() {
	    if (getBaseMetaTileEntity().isServerSide()) {
	    	getBaseMetaTileEntity().setGenericRedstoneOutput(true);
	    	if (mUpdate > 0) mUpdate--; else if (getBaseMetaTileEntity().isAllowedToWork()) mRedstoneStrength = 0;
	    	for (byte i = 0; i < 6; i++) getBaseMetaTileEntity().setOutputRedstoneSignal(i, i == getBaseMetaTileEntity().getFrontFacing()?(byte)0:mRedstoneStrength);
		}
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (aSide==aFacing) {
			return 294+mRedstoneStrength;
		}
		if (aSide==0) return aRedstone?56:54;
		if (aSide==1) return aRedstone?53:52;
		return aRedstone?94:93;
	}
	
	@Override
	protected String getDescription() {
		return "A Panel with 16 Buttons";
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return false;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return false;
	}
}
