package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class GT_Cover_ItemMeter extends GT_CoverBehavior {
	
	public GT_Cover_ItemMeter(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		int[] tSlots = aTileEntity.getSizeInventorySide(aSide);
		int tAll = 0, tFull = 0;
		for (int i : tSlots) {
			tAll+=64;
			ItemStack tStack = aTileEntity.getStackInSlot(i);
			if (tStack != null) {
				tFull += (tStack.stackSize*64) / tStack.getMaxStackSize();
			}
		}
		tAll/=15;
		if (tAll > 0) {
			aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable==0?(byte)(tFull/tAll):(byte)(15-tFull/tAll));
		} else {
			aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable==0?(byte)15:0);
		}
		return aCoverVariable;
	}
	
	@Override
	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer) {
		if (aPlayer instanceof EntityPlayerMP) {
			if (aCoverVariable==0)
				((EntityPlayerMP)aPlayer).sendChatToPlayer("Inverted");
			else
				((EntityPlayerMP)aPlayer).sendChatToPlayer("Normal");
		}
		return aCoverVariable==0?1:0;
	}
	
	@Override
	public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsLiquidIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsLiquidOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
}
