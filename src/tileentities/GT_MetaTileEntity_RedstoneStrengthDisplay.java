package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_MetaTileEntity_RedstoneStrengthDisplay extends MetaTileEntity {
	
	public byte mRedstoneStrength = 0;
	
	public GT_MetaTileEntity_RedstoneStrengthDisplay(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_RedstoneStrengthDisplay() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return true;}
    @Override public boolean isValidSlot(int aIndex) 				{return false;}
	@Override public boolean isFacingValid(byte aFacing)			{return true;}
    @Override public int getInvSize()								{return 0;}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_RedstoneStrengthDisplay();
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
    public void onPreTick() {
	    if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().isServerSide()) {
	    	byte tRedstoneStrength = getBaseMetaTileEntity().getStrongestRedstone();
	    	if (tRedstoneStrength != mRedstoneStrength) {
	    		mRedstoneStrength = tRedstoneStrength;
	    		getBaseMetaTileEntity().issueTextureUpdate();
	    	}
		}
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (aSide==aFacing) {
			return 264+mRedstoneStrength;
		}
		if (aSide==0) return aRedstone?60:59;
		if (aSide==1) return aRedstone?58:57;
		return aRedstone?62:61;
	}
	
	@Override
	protected String getDescription() {
		return "Displays Redstone Strength";
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
