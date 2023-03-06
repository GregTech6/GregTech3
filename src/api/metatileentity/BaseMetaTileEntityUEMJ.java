package gregtechmod.api.metatileentity;

import gregtechmod.api.interfaces.IBCTileEntity;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.PowerFramework;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * A Combination of BC and UE Power Framework
 */
public class BaseMetaTileEntityUEMJ extends BaseMetaTileEntityUE implements IBCTileEntity {
	IPowerProvider mProvider;
	
	public BaseMetaTileEntityUEMJ() {
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
		if (getPowerProvider() == null || !getCoverBehaviorAtSide((byte)aSide.ordinal()).letsEnergyIn((byte)aSide.ordinal(), getCoverIDAtSide((byte)aSide.ordinal()), getCoverDataAtSide((byte)aSide.ordinal()), this)) return 0;
		return (int) Math.ceil(Math.min(getPowerProvider().getMaxEnergyReceived(), getPowerProvider().getMaxEnergyStored() - getPowerProvider().getEnergyStored()));
	}
}