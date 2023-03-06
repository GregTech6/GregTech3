package gregtechmod.common.containers;

import gregtechmod.api.gui.GT_Slot_Armor;
import gregtechmod.api.gui.GT_Slot_Holo;
import gregtechmod.common.tileentities.GT_TileEntity_AESU;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_AESU extends GT_ContainerMetaID_Machine {

	public GT_Container_AESU(InventoryPlayer aInventoryPlayer, GT_TileEntity_AESU aTileEntity, int aID) {
		super(aInventoryPlayer, aTileEntity, aID);
	}

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity, 0, 128,  14));
        addSlotToContainer(new Slot(mTileEntity, 1, 128,  50));

        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 107,  5, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 107, 23, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 107, 41, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 2, 107, 59, false, false, 1));
        
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 36, 152, 59, 3));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 37, 152, 41, 2));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 38, 152, 23, 1));
        addSlotToContainer(new GT_Slot_Armor(this, aInventoryPlayer, 39, 152,  5, 0));
    }

    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
    	if (aSlotIndex < 0) return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	    Slot tSlot = (Slot)inventorySlots.get(aSlotIndex);
	    ItemStack tStack = tSlot.getStack();
	    if (tSlot != null) {
	    	int tOutput = ((GT_TileEntity_AESU)mTileEntity).mOutput;
	    	switch(aSlotIndex) {
	    	case 2:
	    		((GT_TileEntity_AESU)mTileEntity).mOutput = Math.min(2048, tOutput + (aShifthold==1?256:64));
	        	return null;
	    	case 3:
	    		((GT_TileEntity_AESU)mTileEntity).mOutput = Math.min(2048, tOutput + (aShifthold==1?16:1));
	        	return null;
	    	case 4:
	    		((GT_TileEntity_AESU)mTileEntity).mOutput = Math.max(   0, tOutput - (aShifthold==1?16:1));
	        	return null;
	    	case 5:
	    		((GT_TileEntity_AESU)mTileEntity).mOutput = Math.max(   0, tOutput - (aShifthold==1?256:64));
	        	return null;
	    	}
    	}
	    return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }
    
    public int getSlotCount() {
    	return 2;
    }

    public int getShiftClickSlotCount() {
    	return 2;
    }
}
