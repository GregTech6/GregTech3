package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class GT_Cover_EUMeter extends GT_CoverBehavior {
	
	public GT_Cover_EUMeter(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		int tScale = Math.max(Math.max(aTileEntity.getEnergyCapacity(), aTileEntity.getMJCapacity()), aTileEntity.getSteamCapacity())/15;
		if (tScale > 0) {
			aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable==0?(byte)(aTileEntity.getEnergyStored()/tScale):(byte)(15-(aTileEntity.getEnergyStored()/tScale)));
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
	public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
}
