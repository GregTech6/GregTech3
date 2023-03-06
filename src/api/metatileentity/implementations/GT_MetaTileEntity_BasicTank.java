package gregtechmod.api.metatileentity.implementations;

import gregtechmod.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This is the main construct for my generic Tanks. Filling and emptying behavior have to be implemented manually
 */
public abstract class GT_MetaTileEntity_BasicTank extends MetaTileEntity {
	
	public LiquidStack mLiquid = new LiquidStack(0, 0, 0);
	
	public GT_MetaTileEntity_BasicTank(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_BasicTank() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex < 2;}
	@Override public int getInvSize()								{return 3;}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		if (mLiquid != null) {
			try {
				NBTTagCompound tNBT = new NBTTagCompound("mLiquid");
				mLiquid.writeToNBT(tNBT);
				aNBT.setCompoundTag("mLiquid", tNBT);
			} catch(Throwable e) {}
		}
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
    	mLiquid = LiquidStack.loadLiquidStackFromNBT(aNBT.getCompoundTag("mLiquid"));
		if (mLiquid == null) mLiquid = new LiquidStack(aNBT.getInteger("mItemID"), aNBT.getInteger("mItemCount"), aNBT.getInteger("mItemMeta"));
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
    	if (aSide == 0)
    		return 38;
    	else if (aSide == 1)
    		return 80;
    	else
    		return 36;
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex==1;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex==0;
	}
}
