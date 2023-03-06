package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

public class GT_Cover_LiquidMeter extends GT_CoverBehavior {
	
	public GT_Cover_LiquidMeter(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aTileEntity instanceof ITankContainer) {
			ILiquidTank[] tTanks = ((ITankContainer)aTileEntity).getTanks(ForgeDirection.UNKNOWN);
			long tAll = 0, tFull = 0;
			if (tTanks != null) for (ILiquidTank tTank : tTanks) if (tTank != null) {
				tAll+=tTank.getCapacity();
				LiquidStack tLiquid = tTank.getLiquid();
				if (tLiquid != null) {
					tFull += tLiquid.amount;
				}
			}
			tAll/=15;
			if (tAll > 0) {
				aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable==0?(byte)(tFull/tAll):(byte)(15-tFull/tAll));
			} else {
				aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable==0?(byte)15:0);
			}
		} else {
			aTileEntity.setOutputRedstoneSignal(aSide, (byte)0);
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
