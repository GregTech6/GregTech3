package gregtechmod.common.containers;

import gregtechmod.api.gui.GT_Slot_Holo;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.common.tileentities.GT_TileEntity_UUMAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_UUMAssembler extends GT_ContainerMetaID_Machine {

	public GT_Container_UUMAssembler(InventoryPlayer aInventoryPlayer, GT_TileEntity_UUMAssembler aTileEntity, int aID) {
		super(aInventoryPlayer, aTileEntity, aID);
	}

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity,  0,  152,   5));
        addSlotToContainer(new Slot(mTileEntity,  1,  152,  41));

        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  2,  98,  6, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  3, 115,  6, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  4, 132,  6, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  5,  98, 23, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  6, 115, 23, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  7, 132, 23, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  8,  98, 40, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,  9, 115, 40, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 10, 132, 40, false, true, 1));
        
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 11, 115, 58, false, true, 64));

        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 12,   9,  6, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 13,  25,  6, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 14,  41,  6, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 15,  57,  6, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 16,  73,  6, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 17,   9, 22, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 18,  25, 22, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 19,  41, 22, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 20,  57, 22, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 21,  73, 22, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 22,   9, 38, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 23,  25, 38, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 24,  41, 38, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 25,  57, 38, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 26,  73, 38, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 27,   9, 54, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 28,  25, 54, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 29,  41, 54, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 30,  57, 54, false, true, 64));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 31,  73, 54, false, true, 64));
    }

    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aEntityPlayer) {
    	if (aSlotIndex < 0) return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aEntityPlayer);
    	if (aSlotIndex > 1) {
	    	Slot tSlot = (Slot)inventorySlots.get(aSlotIndex);
	    	if (tSlot != null) {
		    	if (aSlotIndex > 1 && aSlotIndex < 11) {
		    		if (tSlot.getStack() == null || tSlot.getStack().stackSize == 0) {
		    			ItemStack tStack = GT_ModHandler.getIC2Item("matter", 1);
		    			tSlot.putStack(tStack);
		    			return tStack;
		    		} else {
		    			tSlot.putStack(null);
		        		return null;
		    		}
		    	} else if (aSlotIndex == 11) {
		    		if (tSlot.getStack() != null) {
		    			addToList(tSlot.getStack());
		    			tSlot.putStack(null);
		    			return null;
		    		}
		    	} else if (aSlotIndex > 11 && aSlotIndex < 32) {
	    			tSlot.putStack(null);
		    		return null;
		    	}
	    	}
    	}
    	return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aEntityPlayer);
    }
    
    private void addToList(ItemStack aOutput) {
    	int tUUMprice = 0;
    	for (int i = 2; i < 11; i++) if (((Slot)inventorySlots.get(i)).getStack() != null) tUUMprice++;

    	for (int i = 12; i < 32; i++) {
        	Slot tSlot = (Slot)inventorySlots.get(i);
        	if (tSlot != null && tSlot.getStack() != null) {
        		if (tSlot.getStack().isItemEqual(aOutput))
        			return;
        	}
    	}

    	for (int i = 12; i < 32; i++) {
        	Slot tSlot = (Slot)inventorySlots.get(i);
        	if (tSlot != null) {
        		if (tSlot.getStack() == null && mTileEntity.mInventory[i+20] != null && mTileEntity.mInventory[i+20].isItemEqual(aOutput)) {
        			tSlot.putStack(aOutput);
            		((GT_TileEntity_UUMAssembler)mTileEntity).setUUMprice(i-12, tUUMprice);
        			return;
        		}
        	}
    	}
    	
    	for (int i = 12; i < 32; i++) {
        	Slot tSlot = (Slot)inventorySlots.get(i);
        	if (tSlot != null) {
        		if (tSlot.getStack() == null && mTileEntity.mInventory[i+20] == null) {
        			tSlot.putStack(aOutput);
            		((GT_TileEntity_UUMAssembler)mTileEntity).setUUMprice(i-12, tUUMprice);
        			return;
        		}
        	}
    	}
    }
    
    public int getSlotCount() {
    	return 2;
    }
    
    public int getShiftClickSlotCount() {
    	return 2;
    }
}
