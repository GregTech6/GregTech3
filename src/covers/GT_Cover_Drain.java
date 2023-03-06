package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquid;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

public class GT_Cover_Drain extends GT_CoverBehavior {
	
	public GT_Cover_Drain(ItemStack aStack) {
		super(aStack);
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aCoverVariable == 1 && aTileEntity instanceof IMachineProgress && !((IMachineProgress)aTileEntity).isAllowedToWork()) return aCoverVariable;
		if (aSide != 0 && aTileEntity.getTimer() % 50 == 0) {
			if (aTileEntity instanceof ITankContainer) {
				if (aSide == 1) {
					if (aTileEntity.getWorld().isRaining()) {
						if (aTileEntity.getWorld().getPrecipitationHeight(aTileEntity.getXCoord(), aTileEntity.getZCoord()) - 2 < aTileEntity.getYCoord()) {
							int tAmount = (int)(aTileEntity.getBiome(aTileEntity.getXCoord(), aTileEntity.getZCoord()).rainfall*10);
							if (tAmount > 0) {
								((ITankContainer)aTileEntity).fill(ForgeDirection.getOrientation(aSide), new LiquidStack(Block.waterStill, aTileEntity.getWorld().isThundering()?tAmount*2:tAmount), true);
							}
						}
					}
				}
				short tID = aTileEntity.getBlockIDAtSide(aSide);
				LiquidStack tLiquid = null;
				if (aTileEntity.getMetaIDAtSide(aSide) == 0 && tID > 0) {
					if (tID == Block.lavaStill.blockID || tID == Block.lavaMoving.blockID) {
						tLiquid = new LiquidStack(Block.lavaStill, 1000);
					} else if (tID == Block.waterStill.blockID || tID == Block.waterMoving.blockID) {
						tLiquid = new LiquidStack(Block.waterStill, 1000);
					} else if (Block.blocksList[tID] instanceof ILiquid) {
						tLiquid = new LiquidStack(((ILiquid)Block.blocksList[tID]).stillLiquidId(), 1000);
					}
					
					if (tLiquid != null) {
						if (((ITankContainer)aTileEntity).fill(ForgeDirection.getOrientation(aSide), tLiquid, false) == tLiquid.amount) {
							((ITankContainer)aTileEntity).fill(ForgeDirection.getOrientation(aSide), tLiquid, true);
							aTileEntity.getWorld().setBlockToAir(aTileEntity.getXCoord() + ForgeDirection.getOrientation(aSide).offsetX, aTileEntity.getYCoord() + ForgeDirection.getOrientation(aSide).offsetY, aTileEntity.getZCoord() + ForgeDirection.getOrientation(aSide).offsetZ);
						}
					}
				}
			}
		}
		return aCoverVariable;
	}
	
	@Override
	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer) {
		aCoverVariable=(aCoverVariable+1)%2;
		if (aPlayer instanceof EntityPlayerMP) {
			if (aCoverVariable==0) ((EntityPlayerMP)aPlayer).sendChatToPlayer("Import");
			if (aCoverVariable==1) ((EntityPlayerMP)aPlayer).sendChatToPlayer("Import (conditional)");
		}
		return aCoverVariable;
	}
	
	@Override
	public boolean letsLiquidIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
}