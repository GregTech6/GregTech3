package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class GT_Cover_ItemValve extends GT_Cover_Valve {
	
	public GT_Cover_ItemValve(ItemStack aStack) {
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
		return super.doCoverThings(aSide, aCoverID, aCoverVariable, aTileEntity);
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