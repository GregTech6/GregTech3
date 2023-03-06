package gregtechmod.api.interfaces;

/**
 * This File has just internal Information about the Redstone State of a TileEntity
 */
public interface IRedstoneEmitter extends IHasWorldObjectAndCoords {
	/**
	 * gets the Redstone Level the TileEntity should emit to the given Output Side
	 */
	byte getOutputRedstoneSignal(byte aSide);
	
	/**
	 * sets the Redstone Level the TileEntity should emit to the given Output Side
	 */
	void setOutputRedstoneSignal(byte aSide, byte aStrength);
	
	/**
	 * Causes a sided Redstone update.
	 * Sends 1 Integer to Client + causes @issueTextureUpdate()
	 */
	public void issueRedstoneUpdate();
}