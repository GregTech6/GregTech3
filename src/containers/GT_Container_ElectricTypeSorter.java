package gregtechmod.common.containers;

import gregtechmod.api.gui.GT_ContainerMetaTile_Machine;
import gregtechmod.api.gui.GT_Slot_Holo;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricBufferSmall;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricTypeSorter;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet3Chat;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_Container_ElectricTypeSorter extends GT_ContainerMetaTile_Machine {

	public GT_Container_ElectricTypeSorter(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
		super(aInventoryPlayer, aTileEntity, aID);
	}

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity,  0,  25,  23));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 1,  71, 23, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 1,   8, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 1,  26, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 1,  44, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, 1, 134, 63, false, true, 1));
    }

    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
    	if (aSlotIndex < 1) return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
	    
    	Slot tSlot = (Slot)inventorySlots.get(aSlotIndex);
	    if (tSlot != null) {
	    	if (mTileEntity.getMetaTileEntity() == null) return null;
		    if (aSlotIndex == 1) {
		    	if (aMouseclick == 0)
		    		((GT_MetaTileEntity_ElectricTypeSorter)mTileEntity.getMetaTileEntity()).switchModeForward();
		    	else
		    		((GT_MetaTileEntity_ElectricTypeSorter)mTileEntity.getMetaTileEntity()).switchModeBackward();
		    	return null;
		    } else if (aSlotIndex == 2) {
		    	((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bOutput = !((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bOutput;
			    if (aPlayer instanceof EntityPlayerMP)
			    	if (((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bOutput)
			    		((EntityPlayerMP)aPlayer).playerNetServerHandler.sendPacketToPlayer(new Packet3Chat("Emit Energy to Outputside"));
			    	else
			    		((EntityPlayerMP)aPlayer).playerNetServerHandler.sendPacketToPlayer(new Packet3Chat("Don't emit Energy"));
		    	return null;
		    } else if (aSlotIndex == 3) {
		    	((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bRedstoneIfFull = !((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bRedstoneIfFull;
		    	if (aPlayer instanceof EntityPlayerMP)
				    if (((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bRedstoneIfFull)
			    		((EntityPlayerMP)aPlayer).playerNetServerHandler.sendPacketToPlayer(new Packet3Chat("Emit Redstone if slot contains something"));
			    	else
			    		((EntityPlayerMP)aPlayer).playerNetServerHandler.sendPacketToPlayer(new Packet3Chat("Don't emit Redstone"));
		    	return null;
		    } else if (aSlotIndex == 4) {
		    	((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bInvert = !((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bInvert;
		    	if (aPlayer instanceof EntityPlayerMP)
				    if (((GT_MetaTileEntity_ElectricBufferSmall)mTileEntity.getMetaTileEntity()).bInvert)
			    		((EntityPlayerMP)aPlayer).playerNetServerHandler.sendPacketToPlayer(new Packet3Chat("Invert Redstone"));
			    	else
			    		((EntityPlayerMP)aPlayer).playerNetServerHandler.sendPacketToPlayer(new Packet3Chat("Don't invert Redstone"));
		    	return null;
		    } else if (aSlotIndex == 5) {
		    	((GT_MetaTileEntity_ElectricTypeSorter)mTileEntity.getMetaTileEntity()).mTargetDirection = (byte) ((((GT_MetaTileEntity_ElectricTypeSorter)mTileEntity.getMetaTileEntity()).mTargetDirection + 1) % 6);
		    }
    	}
	    
    	return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }
    
    public int mTargetDirection = 0, mMode = 0;
    
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    	if (mTileEntity.isClientSide()) return;
    	mMode  = ((GT_MetaTileEntity_ElectricTypeSorter)mTileEntity.getMetaTileEntity()).mMode;
    	mTargetDirection  = ((GT_MetaTileEntity_ElectricTypeSorter)mTileEntity.getMetaTileEntity()).mTargetDirection;
    	
        Iterator var2 = this.crafters.iterator();
        while (var2.hasNext()) {
            ICrafting var1 = (ICrafting)var2.next();
            var1.sendProgressBarUpdate(this, 100, mMode);
            var1.sendProgressBarUpdate(this, 101, mTargetDirection);
        }
    }
    
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
    	super.updateProgressBar(par1, par2);
    	switch (par1) {
    	case 100: mMode = par2; break;
    	case 101: mTargetDirection = par2; break;
    	}
    }
    
    public int getSlotCount() {
    	return 1;
    }
    
    public int getShiftClickSlotCount() {
    	return 1;
    }
}
