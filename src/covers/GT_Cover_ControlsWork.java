package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class GT_Cover_ControlsWork extends GT_CoverBehavior {
	
	public GT_Cover_ControlsWork(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aTileEntity instanceof IMachineProgress) {
			byte tRedstone = aTileEntity.getInputRedstoneSignal(aSide);
			if ((tRedstone>0) == (aCoverVariable==0)) ((IMachineProgress)aTileEntity).enableWorking(); else ((IMachineProgress)aTileEntity).disableWorking();
			((IMachineProgress)aTileEntity).setWorkDataValue(tRedstone);
		}
		return aCoverVariable;
	}
	
	@Override
	public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced) {
		if (aTileEntity instanceof IMachineProgress) {
			((IMachineProgress)aTileEntity).enableWorking();
			((IMachineProgress)aTileEntity).setWorkDataValue((byte)0);
		}
		return true;
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
	public boolean acceptsSidedRedstoneInput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
}