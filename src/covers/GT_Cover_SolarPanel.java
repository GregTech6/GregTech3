package gregtechmod.common.covers;

import gregtechmod.api.interfaces.ICoverable;
import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.item.ItemStack;

public class GT_Cover_SolarPanel extends GT_CoverBehavior {
	
	private final int mEUtDay, mEUtNight;
	
	public GT_Cover_SolarPanel(ItemStack aStack, int aEUtDay, int aEUtNight) {
		super(aStack);
		mEUtDay = aEUtDay;
		mEUtNight = aEUtNight;
	}
	
	@Override
	public int doCoverThings(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		if (aSide == 1) {
			if (aTileEntity.getInputVoltage() >= mEUtDay) {
				if (!aTileEntity.getWorld().isThundering()) {
					boolean bRain = aTileEntity.getWorld().isRaining() && aTileEntity.getBiome(aTileEntity.getXCoord(), aTileEntity.getZCoord()).rainfall>0;
					if (!bRain || aTileEntity.getWorld().isDaytime()) {
						if (aTileEntity.getSkyAtSide((byte)aSide)) {
							aTileEntity.increaseStoredEnergyUnits(bRain || !aTileEntity.getWorld().isDaytime()?mEUtNight:mEUtDay, false);
						}
					}
				}
			}
		}
		return aCoverVariable;
	}
}