package gregtechmod.common.containers;

import gregtechmod.api.gui.GT_Slot_Armor;
import gregtechmod.api.gui.GT_Slot_Output;
import gregtechmod.common.tileentities.GT_TileEntity_ChargeOMat;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GT_Container_ChargeOMat extends GT_ContainerMetaID_Machine {

	public GT_Container_ChargeOMat(InventoryPlayer aInventoryPlayer, GT_TileEntity_ChargeOMat aTileEntity, int aID) {
		super(aInventoryPlayer, aTileEntity, aID);
	}

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity,  0,   8,   5));
        addSlotToContainer(new Slot(mTileEntity,  1,  26,   5));
        addSlotToContainer(new Slot(mTileEntity,  2,  44,   5));
        addSlotToContainer(new Slot(mTileEntity,  3,   8,  23));
        addSlotToContainer(new Slot(mTileEntity,  4,  26,  23));
        addSlotToContainer(new Slot(mTileEntity,  5,  44,  23));
        addSlotToContainer(new Slot(mTileEntity,  6,   8,  41));
        addSlotToContainer(new Slot(mTileEntity,  7,  26,  41));
        addSlotToContainer(new Slot(mTileEntity,  8,  44,  41));

        addSlotToContainer(new GT_Slot_Output(mTileEntity,  9,  97,   5));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 10, 115,   5));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 11, 133,   5));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 12,  97,  23));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 13, 115,  23));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 14, 133,  23));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 15,  97,  41));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 16, 115,  41));
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 17, 133,  41));
        
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 36, 152, 59, 3));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 37, 152, 41, 2));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 38, 152, 23, 1));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 39, 152,  5, 0));
    }
    
    public int getSlotCount() {
    	return 18;
    }

    public int getShiftClickSlotCount() {
    	return 9;
    }
}
