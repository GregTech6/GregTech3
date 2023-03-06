package gregtechmod.common.containers;

import gregtechmod.api.gui.GT_Slot_Armor;
import gregtechmod.common.tileentities.GT_TileEntity_IDSU;

import java.util.Iterator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_Container_IDSU extends GT_ContainerMetaID_Machine {

	public GT_Container_IDSU(InventoryPlayer aInventoryPlayer, GT_TileEntity_IDSU aTileEntity, int aID) {
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

    public int mPlayerHash;
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        mPlayerHash = ((GT_TileEntity_IDSU)mTileEntity).mPlayerHash;
    	
        Iterator var2 = this.crafters.iterator();
        while (var2.hasNext()) {
            ICrafting var1 = (ICrafting)var2.next();
            var1.sendProgressBarUpdate(this, 100, mPlayerHash & 65535);
            var1.sendProgressBarUpdate(this, 101, mPlayerHash >>> 16);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    	super.updateProgressBar(par1, par2);
    	switch (par1) {
    	case 100: mPlayerHash = mPlayerHash & -65536 | par2; break;
    	case 101: mPlayerHash = mPlayerHash &  65535 | par2 << 16; break;
    	}
    }
    
    public int getSlotCount() {
    	return 2;
    }

    public int getShiftClickSlotCount() {
    	return 2;
    }
}
