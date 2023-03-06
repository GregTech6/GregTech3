package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

public class GT_Cover_Valve extends GT_CoverBehavior {
	
	public GT_Cover_Valve(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aCoverVariable > 1 && aTileEntity instanceof IMachineProgress && !((IMachineProgress)aTileEntity).isAllowedToWork()) return aCoverVariable;
		if (aTileEntity instanceof ITankContainer) {
			TileEntity tTileEntity = aTileEntity.getTileEntityAtSide(aSide);
			if (tTileEntity != null && tTileEntity instanceof ITankContainer) {
				ITankContainer tTank1 = (ITankContainer)aTileEntity, tTank2 = (ITankContainer)tTileEntity;
				if (aCoverVariable%2 == 0) {
					LiquidStack tLiquid = tTank1.drain(ForgeDirection.getOrientation(aSide), 1000, false);
					if (tLiquid != null) {
						tLiquid = tLiquid.copy();
						tLiquid.amount = tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tLiquid, false);
						if (tLiquid.amount > 0) {
							if (aTileEntity.getEnergyCapacity() >= Math.min(1, tLiquid.amount/100)) {
								if (aTileEntity.getEnergyStored() >= Math.min(1, tLiquid.amount/100)) {
									aTileEntity.decreaseStoredEnergyUnits(Math.min(1, tLiquid.amount/100), true);
									tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tTank1.drain(ForgeDirection.getOrientation(aSide), tLiquid.amount, true), true);
								}
							} else {
								tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tTank1.drain(ForgeDirection.getOrientation(aSide), tLiquid.amount, true), true);
							}
						}
					}
				} else {
					LiquidStack tLiquid = tTank2.drain(ForgeDirection.getOrientation(aSide).getOpposite(), 1000, false);
					if (tLiquid != null) {
						tLiquid = tLiquid.copy();
						tLiquid.amount = tTank1.fill(ForgeDirection.getOrientation(aSide), tLiquid, false);
						if (tLiquid.amount > 0) {
							if (aTileEntity.getEnergyCapacity() >= Math.min(1, tLiquid.amount/100)) {
								if (aTileEntity.getEnergyStored() >= Math.min(1, tLiquid.amount/100)) {
									aTileEntity.decreaseStoredEnergyUnits(Math.min(1, tLiquid.amount/100), true);
									tTank1.fill(ForgeDirection.getOrientation(aSide), tTank2.drain(ForgeDirection.getOrientation(aSide).getOpposite(), tLiquid.amount, true), true);
								}
							} else {
								tTank1.fill(ForgeDirection.getOrientation(aSide), tTank2.drain(ForgeDirection.getOrientation(aSide).getOpposite(), tLiquid.amount, true), true);
							}
						}
					}
				}
			}
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
	public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	@Override
	public boolean letsLiquidIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return aCoverVariable%2 != 0;
	}
	
	@Override
	public boolean letsLiquidOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return aCoverVariable%2 == 0;
	}
}