package gregtechmod.common.containers;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_Slot_Holo;
import gregtechmod.api.gui.GT_Slot_Output;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.common.blocks.GT_BlockMetaID_Machine;
import gregtechmod.common.tileentities.GT_TileEntity_ComputerCube;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_Container_ComputerCube extends GT_ContainerMetaID_Machine {

	public GT_Container_ComputerCube(InventoryPlayer aInventoryPlayer, GT_TileEntity_ComputerCube aTileEntity, int aID) {
		super(aInventoryPlayer, aTileEntity, aID);
	}

    public void addSlots(InventoryPlayer aInventoryPlayer) {
    	addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 156+(mID==5?50:0),   4, false, false, 1));
    	switch (mID) {
    	case 0:
    		break;
    	case 1:
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 156,  86, false, false, 1));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 156,  70, false, false, 1));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 156,  54, false, false, 1));
    		for (int y = 0; y < 6; y++) {
        		for (int x = 0; x < 9; x++) {
        			addSlotToContainer(new GT_Slot_Holo(mTileEntity, x + y * 9, 5 + x * 16, 5 + y * 16, false, false, 64));
        		}
    		}
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity, 113, 153,  28, false, false, 64));
        	break;
    	case 2:
    		addSlotToContainer(new Slot				(mTileEntity,  54,   8,  28));
    		addSlotToContainer(new Slot				(mTileEntity,  55,  26,  28));
    		addSlotToContainer(new GT_Slot_Output	(mTileEntity,  56, 134,  28));
    		addSlotToContainer(new GT_Slot_Output	(mTileEntity,  57, 152,  28));
    		break;
    	case 3:
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58,  88,  65, false, false, 1));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 104,  65, false, false, 1));
    		
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  59, 122,  35, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  60,  92,   5, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  61, 122,   5, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  62, 152,  35, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  63, 122,  65, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  64,  92,  35, false, false, 64));
    		break;
    	case 4:
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58,  88,  65, false, false, 1));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 104,  65, false, false, 1));

    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  59, 122,   5, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  60, 122,  65, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  61, 152,  35, false, false, 64));
    		break;
    	case 5:
        	addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 140+50, 146, false, false, 1));
        	addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 156+50, 146, false, false, 1));
        	
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  59, 156+50,  38, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  60, 156+50,  56, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  61, 156+50,  74, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  62, 156+50,  92, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  63, 156+50, 110, false, false, 64));
    		
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  64, 103+50,   7, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  65, 119+50,   7, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  66, 135+50,   7, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  67, 103+50,  23, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  68, 119+50,  23, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  69, 135+50,  23, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  70, 103+50,  39, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  71, 119+50,  39, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  72, 135+50,  39, false, false, 64));
        	break;
    	case 6:
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58,  88,  65, false, false, 1));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  58, 104,  65, false, false, 1));
    		
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  59, 122,  35, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  60,  92,   5, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  61, 122,   5, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  62, 152,  35, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  63, 122,  65, false, false, 64));
    		addSlotToContainer(new GT_Slot_Holo		(mTileEntity,  64,  92,  35, false, false, 64));
    		break;
    	}
    }
    
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
    	if (aSlotIndex < 0) return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    	if (mID != ((GT_TileEntity_ComputerCube)mTileEntity).mMode) return null;
    	Slot tSlot = (Slot)inventorySlots.get(aSlotIndex);
	    ItemStack tStack = tSlot.getStack();
	    if (tSlot != null) {
	    	if (aSlotIndex == 0) {
	    		if (aMouseclick == 0) {
	    			((GT_TileEntity_ComputerCube)mTileEntity).switchModeForward();
	    		} else {
	    			((GT_TileEntity_ComputerCube)mTileEntity).switchModeBackward();
	    		}
	    		aPlayer.openGui(GregTech_API.gregtechmod, GT_BlockMetaID_Machine.getComputerCubeGUIID(mTileEntity), mTileEntity.worldObj, mTileEntity.xCoord, mTileEntity.yCoord, mTileEntity.zCoord);
	    	} else {
	    		if (aSlotIndex <= 2 && mID == 3) {
	    			if (aSlotIndex == 1) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).switchCentrifugePageBackward();
	    				onCraftMatrixChanged(mTileEntity);
	    			} else if (aSlotIndex == 2) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).switchCentrifugePageForward();
	    				onCraftMatrixChanged(mTileEntity);
	    			}
	    		} else	if (aSlotIndex <= 2 && mID == 6) {
		    		if (aSlotIndex == 1) {
		    			((GT_TileEntity_ComputerCube)mTileEntity).switchElectrolyzerPageBackward();
		    			onCraftMatrixChanged(mTileEntity);
		    		} else if (aSlotIndex == 2) {
		    			((GT_TileEntity_ComputerCube)mTileEntity).switchElectrolyzerPageForward();
		    			onCraftMatrixChanged(mTileEntity);
		    		}
	    		} else if (aSlotIndex <= 2 && mID == 4) {
	    			if (aSlotIndex == 1) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).switchFusionPageBackward();
	    				onCraftMatrixChanged(mTileEntity);
	    			} else if (aSlotIndex == 2) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).switchFusionPageForward();
	    				onCraftMatrixChanged(mTileEntity);
	    			}
	    		} else if (aSlotIndex <= 2 && mID == 5) {
	    			if (aSlotIndex == 1) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).switchDescriptionPageBackward();
	    				onCraftMatrixChanged(mTileEntity);
	    			} else if (aSlotIndex == 2) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).switchDescriptionPageForward();
	    				onCraftMatrixChanged(mTileEntity);
	    			}
	    		} else if (aSlotIndex <= 58 && mID == 1) {
	    			if (aSlotIndex == 1) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).switchNuclearReactor();
	    				onCraftMatrixChanged(mTileEntity);
	    			} else if (aSlotIndex == 2) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).loadNuclearReactor();
	    				onCraftMatrixChanged(mTileEntity);
	    			} else if (aSlotIndex == 3) {
	    				((GT_TileEntity_ComputerCube)mTileEntity).saveNuclearReactor();
	    			} else {
		    			if (aShifthold == 1) {
		    		    	tSlot.putStack(null);
		    	    		return null;
		    	    	} else if (aMouseclick == 0) {
		    		    	if (tStack == null) {
		    		    		if (getSlot(58).getStack() != null && aSlotIndex != 58)
		    		    			tSlot.putStack(getSlot(58).getStack().copy());
		    		    		else
		    		    			tSlot.putStack(new ItemStack(GT_TileEntity_ComputerCube.sReactorList.get(0), 1));
		    			    	return null;
		    		    	} else {
		    		    		for (int i = 1; i < GT_TileEntity_ComputerCube.sReactorList.size(); i++) {
			    		    		if (GT_TileEntity_ComputerCube.sReactorList.get(i-1).itemID == tStack.itemID) {
			    					   	tSlot.putStack(new ItemStack(GT_TileEntity_ComputerCube.sReactorList.get(i), 1, 0));
			    					    if (tSlot.getStack() != null && tSlot.getStack().getItem() == GT_ModHandler.getIC2Item("reactorIsotopeCell", 1).getItem()) {
			    					    	tSlot.getStack().setItemDamage(tSlot.getStack().getMaxDamage()-1);
			    					    }
			    					   	return null;
		    		    			}
		    		    		}
		    			    	tSlot.putStack(null);
		    		    		return null;
		    		    	}
		    	    	} else {
		    		    	if (tStack == null) {
		    			    	return null;
		    		    	} else {
		    		    		if (tStack.stackSize < tStack.getMaxStackSize()) {
		    				    	tStack.stackSize++;
		    					    return null;
		    		    		} else {
		    		    			tStack.stackSize=1;
		    					    return null;
		    		    		}
		    		    	}
		    	    	}
	    			}
	    		} else {
	    			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	    		}
	    	}
    	}
    	return null;
    }
    
    public boolean doesBindPlayerInventory() {
    	return mID != 1 && mID != 5;
    }
    
    public int mEUOut, mHeat, mMaxHeat, mHEM, mExplosionStrength, mEU, mProgress;
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    	if (mTileEntity.worldObj.isRemote) return;
	    mEUOut = ((GT_TileEntity_ComputerCube)mTileEntity).mEUOut;
	    mHeat = ((GT_TileEntity_ComputerCube)mTileEntity).mHeat;
	    mMaxHeat = ((GT_TileEntity_ComputerCube)mTileEntity).mMaxHeat;
	    mHEM = (int)(((GT_TileEntity_ComputerCube)mTileEntity).mHEM*10000);
	    mExplosionStrength = (int)(((GT_TileEntity_ComputerCube)mTileEntity).mExplosionStrength*100);
	    mEU = ((GT_TileEntity_ComputerCube)mTileEntity).mEU;
	    mProgress = ((GT_TileEntity_ComputerCube)mTileEntity).mProgress;
        
        Iterator var2 = this.crafters.iterator();
        while (var2.hasNext()) {
            ICrafting var1 = (ICrafting)var2.next();
            var1.sendProgressBarUpdate(this, 101, mEUOut);
            var1.sendProgressBarUpdate(this, 102, mHeat & 65535);
            var1.sendProgressBarUpdate(this, 103, mMaxHeat & 65535);
            var1.sendProgressBarUpdate(this, 104, mHEM);
            var1.sendProgressBarUpdate(this, 105, mExplosionStrength);
            var1.sendProgressBarUpdate(this, 106, mHeat >>> 16);
            var1.sendProgressBarUpdate(this, 107, mMaxHeat >>> 16);
            var1.sendProgressBarUpdate(this, 108, mEU & 65535);
            var1.sendProgressBarUpdate(this, 109, mEU >>> 16);
            var1.sendProgressBarUpdate(this, 110, mProgress);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    	super.updateProgressBar(par1, par2);
    	switch (par1) {
    	case 101: mEUOut = par2; break;
    	case 102: mHeat = mHeat & -65536 | par2; break;
    	case 103: mMaxHeat = mMaxHeat & -65536 | par2; break;
    	case 104: mHEM = par2; break;
    	case 105: mExplosionStrength = par2; break;
    	case 106: mHeat = mHeat &  65535 | par2 << 16; break;
    	case 107: mMaxHeat = mMaxHeat &  65535 | par2 << 16; break;
    	case 108: mEU = mEU & -65536 | par2;
    	case 109: mEU = mEU &  65535 | par2 << 16; break;
    	case 110: mProgress = par2; break;
    	}
    }
    
    public int getSlotStartIndex() {
    	return 1;
    }
    
    public int getSlotCount() {
    	return mID==2?4:0;
    }

    public int getShiftClickSlotCount() {
    	return mID==2?2:0;
    }
}
