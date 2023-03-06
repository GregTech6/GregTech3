package gregtechmod.api.interfaces;

import gregtechmod.api.util.GT_CoverBehavior;
import net.minecraft.item.ItemStack;

public interface ICoverable extends IRedstoneTileEntity, IHasWorldObjectAndCoords, IHasInventory, IBasicEnergyContainer {
	public boolean			canPlaceCoverIDAtSide	(byte aSide, int aID);
	public boolean			canPlaceCoverItemAtSide	(byte aSide, ItemStack aCover);
	public void				setCoverDataAtSide		(byte aSide, int aData);
	public void				setCoverIDAtSide		(byte aSide, int aID);
	public void				setCoverItemAtSide		(byte aSide, ItemStack aCover);
	public int				getCoverDataAtSide		(byte aSide);
	public int				getCoverIDAtSide		(byte aSide);
	public ItemStack		getCoverItemAtSide		(byte aSide);
	public GT_CoverBehavior	getCoverBehaviorAtSide	(byte aSide);
	
	/**
	 * For use by the regular MetaTileEntities, doesn't account for special Cover Sides, which can accept Redstone
	 * 
	 * returns 0 if the Side is Covered and doesnt let Redstone go through
	 */
	public byte getInternalInputRedstoneSignal		(byte aSide);
	
	/**
	 * Causes a general Cover Texture update.
	 * Sends 6 Integers to Client + causes @issueTextureUpdate()
	 */
	public void issueCoverUpdate();
}