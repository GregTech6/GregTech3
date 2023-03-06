package gregtechmod.api.metatileentity;

import gregtechmod.api.interfaces.IIC2TileEntity;
import ic2.api.Direction;
import net.minecraft.tileentity.TileEntity;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This file contains all the needed 'implements' of the Interfaces for the Industrial Craft Stuff.
 */
public class BaseMetaTileEntityIC extends BaseMetaTileEntity implements IIC2TileEntity {
	public BaseMetaTileEntityIC() {
		super();
	}
	
	@Override
	public int addEnergy(int aEnergy) {
		if (!hasValidMetaTileEntity()) return 0;
		if (aEnergy > 0)
			increaseStoredEnergyUnits( aEnergy, true);
		else
			decreaseStoredEU(-aEnergy, true);
		return mMetaTileEntity.getEUVar();
	}
	
	@Override public boolean acceptsEnergyFrom(TileEntity aReceiver, Direction aDirection) {return inputEnergyFrom((byte)aDirection.toSideValue());}
    @Override public boolean emitsEnergyTo(TileEntity aReceiver, Direction aDirection) {return outputsEnergyTo((byte)aDirection.toSideValue());}
	@Override public boolean isAddedToEnergyNet() {return mIsAddedToEnet;}
	@Override public boolean isTeleporterCompatible(Direction side) {return hasValidMetaTileEntity() && mMetaTileEntity.isEnetOutput() && getOutput() >= 128 && getCapacity() >= 500000;}
	@Override public void setStored(int aEU) {if (hasValidMetaTileEntity()) setStoredEU(aEU);}
	@Override public int demandsEnergy() {if (mReleaseEnergy || !hasValidMetaTileEntity() || !mMetaTileEntity.isEnetInput()) return 0; return getCapacity() - getStored();}
	@Override public int injectEnergy(Direction aDirection, int aAmount) {if (!inputEnergyFrom((byte)aDirection.toSideValue())) return aAmount; if (aAmount > getMaxSafeInput()) {doExplosion(aAmount); return 0;} increaseStoredEnergyUnits(aAmount, true); return 0;}
	@Override public int getCapacity() {return getEnergyCapacity();}
    @Override public int getStored() {return Math.min(getMetaTileEntity()==null?0:getMetaTileEntity().getEUVar(), getCapacity());}
	@Override public int getMaxSafeInput() {return getInputVoltage();}
	@Override public int getMaxEnergyOutput() {if (mReleaseEnergy) return Integer.MAX_VALUE; return getOutput();}
	@Override public int getOutput() {return getOutputVoltage();}
}