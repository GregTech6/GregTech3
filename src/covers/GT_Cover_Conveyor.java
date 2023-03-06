package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.util.GT_CoverBehavior;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class GT_Cover_Conveyor extends GT_CoverBehavior {
	
	public GT_Cover_Conveyor(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aCoverVariable > 1 && aTileEntity instanceof IMachineProgress && !((IMachineProgress)aTileEntity).isAllowedToWork()) return aCoverVariable;
		if (aTileEntity.getEnergyCapacity() >= 128) {
			if (aTileEntity.getEnergyStored() >= 128) {
				aTileEntity.decreaseStoredEnergyUnits(GT_Utility.moveOneItemStack(aCoverVariable%2==0?aTileEntity:aTileEntity.getIInventoryAtSide(aSide), aCoverVariable%2!=0?aTileEntity:aTileEntity.getIInventoryAtSide(aSide), (byte)(aCoverVariable%2!=0?ForgeDirection.getOrientation(aSide).getOpposite().ordinal():aSide), (byte)(aCoverVariable%2==0?ForgeDirection.getOrientation(aSide).getOpposite().ordinal():aSide), null, false, (byte)64, (byte)1, (byte)64, (byte)1), true);
			}
		} else {
			GT_Utility.moveOneItemStack(aCoverVariable%2==0?aTileEntity:aTileEntity.getIInventoryAtSide(aSide), aCoverVariable%2!=0?aTileEntity:aTileEntity.getIInventoryAtSide(aSide), aSide, (byte)ForgeDirection.getOrientation(aSide).getOpposite().ordinal(), null, false, (byte)64, (byte)1, (byte)64, (byte)1);
		}
		return aCoverVariable;
	}
	
	@Override
	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer) {
		aCoverVariable=(aCoverVariable+1)%4;
		if (aPlayer instanceof EntityPlayerMP) {
			if (aCoverVariable==0) ((EntityPlayerMP)aPlayer).sendChatToPlayer("Export");
			if (aCoverVariable==1) ((EntityPlayerMP)aPlayer).sendChatToPlayer("Import");
			if (aCoverVariable==2) ((EntityPlayerMP)aPlayer).sendChatToPlayer("Export (conditional)");
			if (aCoverVariable==3) ((EntityPlayerMP)aPlayer).sendChatToPlayer("Import (conditional)");
		}
		return aCoverVariable;
	}
	
	@Override
	public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
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
		return aCoverVariable%2 != 0;
	}
	
	@Override
	public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return aCoverVariable%2 == 0;
	}
}