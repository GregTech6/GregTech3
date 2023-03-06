package gregtechmod.api.metatileentity;

import gregtechmod.api.interfaces.IBCTileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.PowerFramework;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This file contains all the needed 'implements' of the Interfaces for the Michael Jackson Stuff.
 */
public class BaseMetaTileEntityMJ extends BaseMetaTileEntity implements IBCTileEntity {
	IPowerProvider mProvider;
	
	public BaseMetaTileEntityMJ() {
		super();
	}
	
	@Override
	public void updateStatus() {
		super.updateStatus();
		if (hasMJConverterUpgrade()) {
			if (mProvider == null) {
				mProvider = PowerFramework.currentFramework.createPowerProvider();
				mProvider.configure(100, 1, 100, 1, 10000);
			} else {
				if (getMJCapacity() > 0 && getStoredMJ() < getMJCapacity()) {
					increaseStoredMJ((int)mProvider.useEnergy(1, getMJCapacity() - getStoredMJ(), true), true);
				}
			}
		}
	}
	
	@Override
	public void setPowerProvider(IPowerProvider aProvider) {
		mProvider = aProvider;
	}
	
	@Override
	public IPowerProvider getPowerProvider() {
		return mProvider;
	}
	
	@Override
	public void doWork() {
		
	}
	
	@Override
	public int powerRequest(ForgeDirection aSide) {
		if (getPowerProvider() == null || !hasValidMetaTileEntity()) return 0;
		if (!getMetaTileEntity().isEnetInput() || !getCoverBehaviorAtSide((byte)aSide.ordinal()).letsEnergyIn((byte)aSide.ordinal(), getCoverIDAtSide((byte)aSide.ordinal()), getCoverDataAtSide((byte)aSide.ordinal()), this)) return 0;
		return (int) Math.ceil(Math.min(getPowerProvider().getMaxEnergyReceived(), getPowerProvider().getMaxEnergyStored() - getPowerProvider().getEnergyStored()));
	}
}