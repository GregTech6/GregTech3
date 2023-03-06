package gregtechmod.api.gui;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * The GUI-Container I use for all my MetaTileEntities
 */
public class GT_GUIContainerMetaTile_Machine extends GT_GUIContainer {
	
	public GT_ContainerMetaTile_Machine  mContainer;
	public int mID;
	
	public GT_GUIContainerMetaTile_Machine(GT_ContainerMetaTile_Machine aContainer, IGregTechTileEntity aTileEntity, int aID) {
		super(aContainer);
		mID = aID;
        mContainer = aContainer;
	}
	
    public GT_GUIContainerMetaTile_Machine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        this(new GT_ContainerMetaTile_Machine(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
}
