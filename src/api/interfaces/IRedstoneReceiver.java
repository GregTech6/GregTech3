package gregtechmod.api.interfaces;

/**
 * This File has just internal Information about the Redstone State of a TileEntity
 */
public interface IRedstoneReceiver extends IHasWorldObjectAndCoords {
	/**
	 * gets the Redstone Level of the TileEntity to the given Input Side
	 */
	byte getInputRedstoneSignal(byte aSide);
	
	/**
	 * gets the strongest Redstone Level the TileEntity receives
	 */
	public byte getStrongestRedstone();
}