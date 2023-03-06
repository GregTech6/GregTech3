package gregtechmod.api.interfaces;

/**
 * Interface for internal Code, which is mainly used for independent Energy conversion.
 */
public interface IBasicEnergyContainer {
	/**
	 * Disconnects and then automatically reconnects TileEntity from all Energy Networks, f.e when Input/Output behaviour/facing changes
	 */
	public void issueEnetUpdate();
	
	/**
	 * Gets the stored electric or kinetic Energy (with EU as reference Value)
	 */
	public int getEnergyStored();
	
	/**
	 * Gets the general electric Energy Capacity (with EU as reference Value)
	 */
	public int getEnergyCapacity();
	
	/**
	 * Gets the amount of Energy Packets per tick.
	 */
	public int getOutputAmperage();
	
	/**
	 * Gets the Output in EU/p.
	 */
	public int getOutputVoltage();
	
	/**
	 * Gets the maximum Input in EU/p.
	 */
	public int getInputVoltage();
	
	/**
	 * Decreases the Amount of stored Energy. If ignoring too less Energy, then it just sets the Energy to 0 and returns false.
	 */
	public boolean decreaseStoredEnergyUnits(int aEnergy, boolean aIgnoreTooLessEnergy);
	
	/**
	 * Increases the Amount of stored Energy. If ignoring too much Energy, then the Energy Limit is being ignored.
	 */
	public boolean increaseStoredEnergyUnits(int aEnergy, boolean aIgnoreTooMuchEnergy);
	
	/**
	 * Sided Energy Input
	 */
	public boolean inputEnergyFrom(byte aSide);
	
	/**
	 * Sided Energy Output
	 */
	public boolean outputsEnergyTo(byte aSide);
	
	/**
	 * returns the amount of MJ contained in this Block, in EU units!
	 */
	public int getStoredMJ();
	
	/**
	 * returns the amount of MJ containable in this Block, in EU units!
	 */
	public int getMJCapacity();
	
	/**
	 * Increases stored Energy. Energy Base Value is in EU, even though it's MJ!
	 * @param aEnergy The Energy to add to the Machine.
	 * @param aIgnoreTooMuchEnergy if it shall ignore if it has too much Energy.
	 * @return if it was successful
	 * 
	 * And yes, you can't directly decrease the MJ of a Machine. That is done by decreaseStoredEnergyUnits
	 */
	public boolean increaseStoredMJ(int aEnergy, boolean aIgnoreTooMuchEnergy);
	
	/**
	 * returns the amount of Steam contained in this Block, in EU units!
	 */
	public int getStoredSteam();
	
	/**
	 * returns the amount of Steam containable in this Block, in EU units!
	 */
	public int getSteamCapacity();
	
	/**
	 * Increases stored Energy. Energy Base Value is in EU, even though it's Steam!
	 * @param aEnergy The Energy to add to the Machine.
	 * @param aIgnoreTooMuchEnergy if it shall ignore if it has too much Energy.
	 * @return if it was successful
	 * 
	 * And yes, you can't directly decrease the Steam of a Machine. That is done by decreaseStoredEnergyUnits
	 */
	public boolean increaseStoredSteam(int aEnergy, boolean aIgnoreTooMuchEnergy);
}