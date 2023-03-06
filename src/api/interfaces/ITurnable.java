package gregtechmod.api.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * A special Version of IWrenchable, basically a ripoff of every Mod added Version of Rotation Code.
 */
public interface ITurnable {
	/**
	 * Get the block's facing.
	 * @return Block facing
	 * @deprecated just to strike through the Function Name when attempted to be called
	 */
	@Deprecated
	short getFacing();
	
	/**
	 * Set the block's facing
	 * 
	 * @param facing facing to set the block to
	 */
	@Deprecated
	void setFacing(short aFacing);
	
	/**
	 * @return Gets the facing direction. Always returns the front side of the block.
	 */
	@Deprecated
	public ForgeDirection getDirection(IBlockAccess aWorld, int aX, int aY, int aZ);
	
	/**
	 * @param Sets the facing direction.
	 */
	@Deprecated
	public void setDirection(World aWorld, int aX, int aY, int aZ, ForgeDirection aDirection);
	
	/**
	 * Get the block's facing reversed.
	 * 
	 * @return opposite Block facing
	 */
	byte getBackFacing();
	
	/**
	 * Get the block's facing, like getFacing, but as byte type.
	 * 
	 * @return front Block facing
	 */
	byte getFrontFacing();
	
	/**
	 * Determine if the wrench can be used to set the block's facing.
	 * Called before wrenchCanRemove().
	 * 
	 * @param entityPlayer player using the wrench, may be null
	 * @param side block's side the wrench was clicked on
	 * @return Whether the wrenching was done and the wrench should be damaged
	 */
	boolean wrenchCanSetFacing(EntityPlayer aPlayer, int aFacing);
	
	/**
	 * Set the block's facing
	 * 
	 * @param facing facing to set the block to
	 */
	void setFrontFacing(byte aFacing);
	
	/**
	 * Determine if the wrench can be used to remove the block.
	 * Called if wrenchSetFacing fails.
	 *
	 * @param entityPlayer player using the wrench, may be null
	 * @return Whether the wrenching was done and the wrench should be damaged
	 */
	boolean wrenchCanRemove(EntityPlayer aPlayer);
	
	/**
	 * Determine the probability to drop the block as it is.
	 * The first entry in getBlockDropped will be replaced by blockid:meta if the drop is successful.
	 * 
	 * @return Probability from 0 to 1
	 */
	float getWrenchDropRate();
	
	/**
	 * Determine the item the block will drop when the wrenching is successful.
	 * 
	 * @param entityPlayer player using the wrench, may be null
	 * @return Item to drop
	 */
	ItemStack getWrenchDrop(EntityPlayer aPlayer);
}