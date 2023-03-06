package gregtechmod.common.gui;

import gregtechmod.api.gui.GT_GUIContainer;
import gregtechmod.common.containers.GT_ContainerMetaID_Machine;
import gregtechmod.common.tileentities.GT_TileEntityMetaID_Machine;
import net.minecraft.entity.player.InventoryPlayer;

public class GT_GUIContainerMetaID_Machine extends GT_GUIContainer {

	public GT_TileEntityMetaID_Machine mTileEntity;
	public GT_ContainerMetaID_Machine  mContainer;
	public int mID;
	
	public GT_GUIContainerMetaID_Machine(GT_ContainerMetaID_Machine aContainer, GT_TileEntityMetaID_Machine aTileEntity, int aID) {
		super(aContainer);
		mID = aID;
        mTileEntity = aTileEntity;
        mContainer  = aContainer;
	}
	
    public GT_GUIContainerMetaID_Machine(InventoryPlayer aInventoryPlayer, GT_TileEntityMetaID_Machine aTileEntity, int aID) {
        this(new GT_ContainerMetaID_Machine(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
}
