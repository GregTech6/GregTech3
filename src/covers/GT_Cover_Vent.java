package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.interfaces.IMachineProgress;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class GT_Cover_Vent extends GT_CoverBehavior {
	
	private final int mEfficiency;
	
	public GT_Cover_Vent(ItemStack[] aCovers, int aEfficiency) {
		super(aCovers);
		mEfficiency = aEfficiency;
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aTileEntity instanceof IMachineProgress) {
			if (((IMachineProgress)aTileEntity).hasThingsToDo() && aCoverVariable != ((IMachineProgress)aTileEntity).getProgress() && aCoverVariable % 60 == 0) {
				Block tBlock = Block.blocksList[aTileEntity.getBlockIDAtSide(aSide)];
				if (tBlock == null || null == tBlock.getCollisionBoundingBoxFromPool(aTileEntity.getWorld(), aTileEntity.getXCoord() + ForgeDirection.getOrientation(aSide).offsetX, aTileEntity.getYCoord() + ForgeDirection.getOrientation(aSide).offsetY, aTileEntity.getZCoord() + ForgeDirection.getOrientation(aSide).offsetZ)) {
					((IMachineProgress)aTileEntity).increaseProgress(mEfficiency);
				}
			}
			return ((IMachineProgress)aTileEntity).getProgress();
		}
		return 0;
	}
}