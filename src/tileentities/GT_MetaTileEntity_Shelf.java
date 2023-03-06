package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_Shelf extends MetaTileEntity {
	
	public int mType = 0;
	
	public GT_MetaTileEntity_Shelf(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_Shelf() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return true;}
	@Override public int getInvSize()								{return 1;}
	@Override public boolean isFacingValid(byte aFacing)			{return aFacing > 1;}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
	@Override public boolean ownerControl()							{return false;}
	@Override public boolean isEnetOutput()							{return false;}
	@Override public boolean isEnetInput()							{return false;}
	@Override public boolean isOutputFacing(byte aSide)				{return false;}
	@Override public boolean isInputFacing(byte aSide)				{return false;}
	
	@Override public void onRightclick(EntityPlayer aPlayer) {
		ItemStack tStack = aPlayer.inventory.getStackInSlot(aPlayer.inventory.currentItem);
		if (tStack == null) {
			if (mInventory[0] != null && mInventory[0].stackSize > 0) {
				aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, mInventory[0]);
				getBaseMetaTileEntity().setInventorySlotContents(0, null);
				getBaseMetaTileEntity().issueTextureUpdate();
				mType = 0;
			}
		} else {
			if (mInventory[0] == null) {
				if (GT_OreDictUnificator.isItemStackInstanceOf(tStack, "paper", true)) {
					aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, null);
					getBaseMetaTileEntity().setInventorySlotContents(0, tStack);
					getBaseMetaTileEntity().issueTextureUpdate();
					mType = 1;
				} else if (GT_OreDictUnificator.isItemStackInstanceOf(tStack, "book", true)) {
					aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, null);
					getBaseMetaTileEntity().setInventorySlotContents(0, tStack);
					getBaseMetaTileEntity().issueTextureUpdate();
					mType = 2;
				} else if (tStack.itemID == GT_ModHandler.getIC2Item("filledTinCan", 1).itemID || tStack.itemID == GT_ModHandler.getIC2Item("tinCan", 1).itemID) {
					aPlayer.inventory.setInventorySlotContents(aPlayer.inventory.currentItem, null);
					getBaseMetaTileEntity().setInventorySlotContents(0, tStack);
					getBaseMetaTileEntity().issueTextureUpdate();
					mType = 3;
				}
			}
		}
	}
	
	@Override public void onLeftclick(EntityPlayer aPlayer) {
		if (mInventory[0] != null && mInventory[0].stackSize > 0) {
			ItemStack tOutput = mInventory[0].copy();
			if (!aPlayer.isSneaking()) tOutput.stackSize = 1;
			getBaseMetaTileEntity().decrStackSize(0, tOutput.stackSize);
			EntityItem tEntity = new EntityItem(getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetX + 0.5, getBaseMetaTileEntity().getYCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetY + 0.5, getBaseMetaTileEntity().getZCoord() + ForgeDirection.getOrientation(getBaseMetaTileEntity().getFrontFacing()).offsetZ + 0.5, tOutput);
			tEntity.motionX = 0;
			tEntity.motionY = 0;
			tEntity.motionZ = 0;
			getBaseMetaTileEntity().getWorld().spawnEntityInWorld(tEntity);
			if (mInventory[0] == null) mType = 0;
		}
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_Shelf();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setInteger("mType", mType);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		mType = aNBT.getInteger("mType");
	}
	
	@Override public void onValueUpdate(short aValue) {
		mType = aValue;
	}
	
	@Override public short getUpdateData() {
		return (short)mType;
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (aSide==aFacing) return 208 + mType;
    	return 10;
	}
	
	@Override
	protected String getDescription() {
		return "Decorative Item Storage";
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
