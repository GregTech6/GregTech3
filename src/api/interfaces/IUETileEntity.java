package gregtechmod.api.interfaces;

import universalelectricity.core.block.IConnector;
import universalelectricity.core.block.IElectricityStorage;
import universalelectricity.core.block.IVoltage;
import universalelectricity.prefab.implement.IRotatable;

public interface IUETileEntity extends IConnector, IElectricityStorage, IVoltage, IRotatable {
	
}