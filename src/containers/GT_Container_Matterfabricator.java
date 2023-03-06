package gregtechmod.common.containers;

import gregtechmod.api.gui.GT_Slot_Output;
import gregtechmod.common.tileentities.GT_TileEntity_Matterfabricator;

import java.util.Iterator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_Container_Matterfabricator extends GT_ContainerMetaID_Machine {

	public GT_Container_Matterfabricator(InventoryPlayer aInventoryPlayer, GT_TileEntity_Matterfabricator aTileEntity, int aID) {
		super(aInventoryPlayer, aTileEntity, aID);
	}

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity, 1,   8,  14));
        addSlotToContainer(new Slot(mTileEntity, 2,  26,  14));
        addSlotToContainer(new Slot(mTileEntity, 3,  44,  14));
        addSlotToContainer(new Slot(mTileEntity, 4,  62,  14));
        
        addSlotToContainer(new Slot(mTileEntity, 5,   8,  32));
        addSlotToContainer(new Slot(mTileEntity, 6,  26,  32));
        addSlotToContainer(new Slot(mTileEntity, 7,  44,  32));
        addSlotToContainer(new Slot(mTileEntity, 8,  62,  32));
        
        addSlotToContainer(new GT_Slot_Output(mTileEntity, 0, 128, 14));
    }

    public int mPercentualProgress;
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    	if (mTileEntity.worldObj.isRemote) return;
    	mPercentualProgress =  Math.max(0, Math.min(30000, (((GT_TileEntity_Matterfabricator)mTileEntity).getProgresstime()) / Math.max(1, ((GT_TileEntity_Matterfabricator)mTileEntity).maxProgresstime()/100)));
    	
        Iterator var2 = this.crafters.iterator();
        while (var2.hasNext()) {
            ICrafting var1 = (ICrafting)var2.next();
            var1.sendProgressBarUpdate(this, 100, mPercentualProgress);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    	super.updateProgressBar(par1, par2);
    	switch (par1) {
    	case 100: mPercentualProgress = par2; break;
    	}
    }
    
    public int getSlotCount() {
    	return 9;
    }

    public int getShiftClickSlotCount() {
    	return 8;
    }
}
