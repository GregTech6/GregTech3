package gregtechmod.api.interfaces;

import gregtechmod.api.metatileentity.MetaTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.liquids.ITankContainer;

/**
 * A simple compound Interface for all my TileEntities.
 * 
 * Also delivers most of the Informations about my TileEntities.
 * 
 * It can cause Problems to include this Interface!
 */
public interface IGregTechTileEntity extends ICoverable, ITankContainer, ITurnable, IGregTechDeviceInformation, IUpgradableMachine, IDigitalChest, IDescribable, IMachineBlockUpdateable {
	/**
	 * Opens GUI-ID of GregTech
	 */
	public void openGUI(EntityPlayer aPlayer, int aID);
	
	/**
	 * gets the Error displayed on the GUI
	 */
	public int getErrorDisplayID();
	
	/**
	 * sets the Error displayed on the GUI
	 */
	public void setErrorDisplayID(int aErrorID);
	
	/**
	 * @return the MetaID of the Block or the MetaTileEntity ID.
	 */
	public int getMetaTileID();
	
	/**
	 * @return the MetaTileEntity which is belonging to this, or null if it doesnt has one.
	 */
	public MetaTileEntity getMetaTileEntity();
	
	/**
	 * Causes a general Texture update.
	 * Active-Status, Redstone-Boolean, Facing and a small value of your specified ExtraData (usually a secondary Facing or special Animation status) will get sent to the Client
	 * Sends 1 Integer to Client
	 */
	public void issueTextureUpdate();
	
	/**
	 * Causes basically all Updates.
	 * Sends all 8 Integers + an Integer containing the ID to the Client.
	 */
	public void issueClientUpdate();
	
	/**
	 * causes Explosion. Strength in Overload-EU
	 */
	public void doExplosion(int aExplosionEU);
	
    /**
     * Sets the Block on Fire in all 6 Directions
     */
    public void setOnFire();
    
	/**
	 * Sets initial Values from NBT
	 * @param tNBT is the NBTTag of readFromNBT
	 * @param aID is the MetaTileEntityID
	 */
	public void setInitialValuesAsNBT(NBTTagCompound aNBT, int aID);
	
	/**
	 * Called when leftclicking the TileEntity
	 */
	public void onLeftclick(EntityPlayer aPlayer);
	
	/**
	 * Called when rightclicking the TileEntity
	 */
	public void onRightclick(EntityPlayer aPlayer, byte aSide, float par1, float par2, float par3);
}