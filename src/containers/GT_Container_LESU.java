package gregtechmod.common.containers;

import gregtechmod.api.gui.GT_Slot_Armor;
import gregtechmod.common.tileentities.GT_TileEntity_LESU;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GT_Container_LESU extends GT_ContainerMetaID_Machine {

	public GT_Container_LESU(InventoryPlayer aInventoryPlayer, GT_TileEntity_LESU aTileEntity, int aID) {
		super(aInventoryPlayer, aTileEntity, aID);
	}

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity, 0, 128,  14));
        addSlotToContainer(new Slot(mTileEntity, 1, 128,  50));
        
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 36, 152, 59, 3));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 37, 152, 41, 2));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 38, 152, 23, 1));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 39, 152,  5, 0));
    }
    
    public int getSlotCount() {
    	return 2;
    }

    public int getShiftClickSlotCount() {
    	return 2;
    }
}
