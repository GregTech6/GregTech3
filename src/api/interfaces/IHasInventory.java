package gregtechmod.api.interfaces;

import net.minecraft.inventory.ISidedInventory;

public interface IHasInventory extends ISidedInventory, IHasWorldObjectAndCoords {
	
	/**
	 * if the Inventory of this TileEntity got modified this tick
	 */
	public boolean hasInventoryBeenModified();

	/**
	 * if this is just a Holoslot
	 */
	public boolean isValidSlot(int aSlot);
}