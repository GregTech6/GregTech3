package gregtechmod.api.gui;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.util.GT_Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * Main Container-Class, used for all my GUIs
 */
public class GT_Container extends Container {

    public IGregTechTileEntity mTileEntity;
	public InventoryPlayer mPlayerInventory;
	
    public GT_Container (InventoryPlayer aPlayerInventory, IGregTechTileEntity aTileEntityInventory) {
        mTileEntity = aTileEntityInventory;
        mPlayerInventory = aPlayerInventory;
    }
    
    /**
     * To add the Slots to your GUI
     */
    public void addSlots(InventoryPlayer aPlayerInventory) {
    	
    }
    
    /**
     * Amount of regular Slots in the GUI (so, non-HoloSlots)
     */
    public int getSlotCount() {
    	return 0;
    }
    
    /**
     * Amount of ALL Slots in the GUI including HoloSlots and ArmorSlots, but excluding regular Player Slots
     */
    public int getAllSlotCount() {
    	if (inventorySlots != null) {
    		if (doesBindPlayerInventory()) {
    			return inventorySlots.size()-36;
    		} else {
    			return inventorySlots.size();
    		}
    	}
    	return getSlotCount();
    }
    
    /**
     * Start-Index of the usable Slots (the first non-HoloSlot)
     */
    public int getSlotStartIndex() {
    	return 0;
    }
    
    /**
     * Amount of Slots in the GUI the player can Shift-Click into. Uses also getSlotStartIndex
     */
    public int getShiftClickSlotCount() {
    	return 0;
    }

    /**
     * Is Player-Inventory visible?
     */
    public boolean doesBindPlayerInventory() {
    	return true;
    }
    
    /**
     * Override this Function with something like "return mTileEntity.isUseableByPlayer(aPlayer);"
     */
    @Override
    public boolean canInteractWith(EntityPlayer aPlayer) {
        return false;
    }
    
	protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
            	addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(aInventoryPlayer, i, 8 + i * 18, 142));
        }
    }
	
    @Override
	public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
    	if (aSlotIndex >= 0) {
    		if (inventorySlots.get(aSlotIndex) == null || inventorySlots.get(aSlotIndex) instanceof GT_Slot_Holo) return null;
    		if (!(inventorySlots.get(aSlotIndex) instanceof GT_Slot_Armor)) if (aSlotIndex < getAllSlotCount()) if (aSlotIndex < getSlotStartIndex() || aSlotIndex >= getSlotStartIndex() + getSlotCount()) return null;
    	}
    	
    	try {return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);} catch (Throwable e) {e.printStackTrace(GT_Log.err);}
    	
        ItemStack rStack = null;
        InventoryPlayer aPlayerInventory = aPlayer.inventory;
        Slot aSlot;
        ItemStack tTempStack;
        int tTempStackSize;
        ItemStack aHoldStack;
        
        if ((aShifthold == 0 || aShifthold == 1) && (aMouseclick == 0 || aMouseclick == 1)) {
            if (aSlotIndex == -999) {
                if (aPlayerInventory.getItemStack() != null && aSlotIndex == -999) {
                    if (aMouseclick == 0) {
                        aPlayer.dropPlayerItem(aPlayerInventory.getItemStack());
                        aPlayerInventory.setItemStack((ItemStack)null);
                    }
                    if (aMouseclick == 1) {
                        aPlayer.dropPlayerItem(aPlayerInventory.getItemStack().splitStack(1));

                        if (aPlayerInventory.getItemStack().stackSize == 0) {
                            aPlayerInventory.setItemStack((ItemStack)null);
                        }
                    }
                }
            } else if (aShifthold == 1) {
                aSlot = (Slot)this.inventorySlots.get(aSlotIndex);
                if (aSlot != null && aSlot.canTakeStack(aPlayer)) {
                    tTempStack = this.transferStackInSlot(aPlayer, aSlotIndex);
                    if (tTempStack != null) {
                        int var12 = tTempStack.itemID;
                        rStack = tTempStack.copy();
                        if (aSlot != null && aSlot.getStack() != null && aSlot.getStack().itemID == var12) {
                            slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
                        }
                    }
                }
            } else {
                if (aSlotIndex < 0) {
                    return null;
                }
                aSlot = (Slot)this.inventorySlots.get(aSlotIndex);
                if (aSlot != null) {
                    tTempStack = aSlot.getStack();
                    ItemStack var13 = aPlayerInventory.getItemStack();
                    if (tTempStack != null) {
                        rStack = tTempStack.copy();
                    }
                    if (tTempStack == null) {
                        if (var13 != null && aSlot.isItemValid(var13)) {
                            tTempStackSize = aMouseclick == 0 ? var13.stackSize : 1;
                            if (tTempStackSize > aSlot.getSlotStackLimit()) {
                                tTempStackSize = aSlot.getSlotStackLimit();
                            }
                            aSlot.putStack(var13.splitStack(tTempStackSize));

                            if (var13.stackSize == 0) {
                                aPlayerInventory.setItemStack((ItemStack)null);
                            }
                        }
                    } else if (aSlot.canTakeStack(aPlayer)) {
                        if (var13 == null) {
                            tTempStackSize = aMouseclick == 0 ? tTempStack.stackSize : (tTempStack.stackSize + 1) / 2;
                            aHoldStack = aSlot.decrStackSize(tTempStackSize);
                            aPlayerInventory.setItemStack(aHoldStack);
                            if (tTempStack.stackSize == 0) {
                                aSlot.putStack((ItemStack)null);
                            }
                            aSlot.onPickupFromSlot(aPlayer, aPlayerInventory.getItemStack());
                        } else if (aSlot.isItemValid(var13)) {
                            if (tTempStack.itemID == var13.itemID && tTempStack.getItemDamage() == var13.getItemDamage() && ItemStack.areItemStackTagsEqual(tTempStack, var13)) {
                                tTempStackSize = aMouseclick == 0 ? var13.stackSize : 1;
                                if (tTempStackSize > aSlot.getSlotStackLimit() - tTempStack.stackSize) {
                                    tTempStackSize = aSlot.getSlotStackLimit() - tTempStack.stackSize;
                                }
                                if (tTempStackSize > var13.getMaxStackSize() - tTempStack.stackSize) {
                                    tTempStackSize = var13.getMaxStackSize() - tTempStack.stackSize;
                                }
                                var13.splitStack(tTempStackSize);
                                if (var13.stackSize == 0) {
                                    aPlayerInventory.setItemStack((ItemStack)null);
                                }
                                tTempStack.stackSize += tTempStackSize;
                            } else if (var13.stackSize <= aSlot.getSlotStackLimit()) {
                                aSlot.putStack(var13);
                                aPlayerInventory.setItemStack(tTempStack);
                            }
                        } else if (tTempStack.itemID == var13.itemID && var13.getMaxStackSize() > 1 && (!tTempStack.getHasSubtypes() || tTempStack.getItemDamage() == var13.getItemDamage()) && ItemStack.areItemStackTagsEqual(tTempStack, var13)) {
                            tTempStackSize = tTempStack.stackSize;

                            if (tTempStackSize > 0 && tTempStackSize + var13.stackSize <= var13.getMaxStackSize()) {
                                var13.stackSize += tTempStackSize;
                                tTempStack = aSlot.decrStackSize(tTempStackSize);

                                if (tTempStack.stackSize == 0) {
                                    aSlot.putStack((ItemStack)null);
                                }

                                aSlot.onPickupFromSlot(aPlayer, aPlayerInventory.getItemStack());
                            }
                        }
                    }
                    aSlot.onSlotChanged();
                }
            }
        } else if (aShifthold == 2 && aMouseclick >= 0 && aMouseclick < 9) {
            aSlot = (Slot)this.inventorySlots.get(aSlotIndex);

            if (aSlot.canTakeStack(aPlayer)) {
                tTempStack = aPlayerInventory.getStackInSlot(aMouseclick);
                boolean var9 = tTempStack == null || aSlot.inventory == aPlayerInventory && aSlot.isItemValid(tTempStack);
                tTempStackSize = -1;

                if (!var9) {
                    tTempStackSize = aPlayerInventory.getFirstEmptyStack();
                    var9 |= tTempStackSize > -1;
                }

                if (aSlot.getHasStack() && var9) {
                    aHoldStack = aSlot.getStack();
                    aPlayerInventory.setInventorySlotContents(aMouseclick, aHoldStack);

                    if ((aSlot.inventory != aPlayerInventory || !aSlot.isItemValid(tTempStack)) && tTempStack != null) {
                        if (tTempStackSize > -1) {
                            aPlayerInventory.addItemStackToInventory(tTempStack);
                            aSlot.decrStackSize(aHoldStack.stackSize);
                            aSlot.putStack((ItemStack)null);
                            aSlot.onPickupFromSlot(aPlayer, aHoldStack);
                        }
                    } else {
                        aSlot.decrStackSize(aHoldStack.stackSize);
                        aSlot.putStack(tTempStack);
                        aSlot.onPickupFromSlot(aPlayer, aHoldStack);
                    }
                } else if (!aSlot.getHasStack() && tTempStack != null && aSlot.isItemValid(tTempStack)) {
                    aPlayerInventory.setInventorySlotContents(aMouseclick, (ItemStack)null);
                    aSlot.putStack(tTempStack);
                }
            }
        } else if (aShifthold == 3 && aPlayer.capabilities.isCreativeMode && aPlayerInventory.getItemStack() == null && aSlotIndex >= 0) {
            aSlot = (Slot)this.inventorySlots.get(aSlotIndex);
            if (aSlot != null && aSlot.getHasStack()) {
                tTempStack = aSlot.getStack().copy();
                tTempStack.stackSize = tTempStack.getMaxStackSize();
                aPlayerInventory.setItemStack(tTempStack);
            }
        }
        return rStack;
	}
    
    @Override
	public ItemStack transferStackInSlot(EntityPlayer aPlayer, int aSlotIndex) {
        ItemStack stack = null;
        Slot slotObject = (Slot)inventorySlots.get(aSlotIndex);
        
        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (getSlotCount() > 0 && slotObject != null && slotObject.getHasStack() && !(slotObject instanceof GT_Slot_Holo)) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();
            
            //TileEntity -> Player
            if (aSlotIndex < getAllSlotCount()) {
            	if (doesBindPlayerInventory())
            		if (!mergeItemStack(stackInSlot, getAllSlotCount(), getAllSlotCount()+36, true)) {
            			return null;
                }
            //Player -> TileEntity
            } else if (!mergeItemStack(stackInSlot, getSlotStartIndex(), getSlotStartIndex()+getShiftClickSlotCount(), false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }
        }
        return stack;
	}
    
    /**
     * merges provided ItemStack with the first avaliable one in the container/player inventory
     */
    @Override
    protected boolean mergeItemStack(ItemStack aStack, int aStartIndex, int aSlotCount, boolean par4) {
        boolean var5 = false;
        int var6 = aStartIndex;
        
        if (par4) {
            var6 = aSlotCount - 1;
        }

        Slot var7;
        ItemStack var8;

        if (aStack.isStackable()) {
            while (aStack.stackSize > 0 && (!par4 && var6 < aSlotCount || par4 && var6 >= aStartIndex)) {
                var7 = (Slot)this.inventorySlots.get(var6);
                var8 = var7.getStack();
                
                if (!(var7 instanceof GT_Slot_Holo) && !(var7 instanceof GT_Slot_Output) && var8 != null && var8.itemID == aStack.itemID && (!aStack.getHasSubtypes() || aStack.getItemDamage() == var8.getItemDamage()) && ItemStack.areItemStackTagsEqual(aStack, var8)) {
                    int var9 = var8.stackSize + aStack.stackSize;

                    if (var9 <= aStack.getMaxStackSize()) {
                        aStack.stackSize = 0;
                        var8.stackSize = var9;
                        var7.onSlotChanged();
                        var5 = true;
                    } else if (var8.stackSize < aStack.getMaxStackSize()) {
                        aStack.stackSize -= aStack.getMaxStackSize() - var8.stackSize;
                        var8.stackSize = aStack.getMaxStackSize();
                        var7.onSlotChanged();
                        var5 = true;
                    }
                }
                
                if (par4) {
                    --var6;
                } else {
                    ++var6;
                }
            }
        }

        if (aStack.stackSize > 0)
        {
            if (par4)
            {
                var6 = aSlotCount - 1;
            }
            else
            {
                var6 = aStartIndex;
            }

            while (!par4 && var6 < aSlotCount || par4 && var6 >= aStartIndex)
            {
                var7 = (Slot)this.inventorySlots.get(var6);
                var8 = var7.getStack();

                if (var8 == null)
                {
                    var7.putStack(aStack.copy());
                    var7.onSlotChanged();
                    aStack.stackSize = 0;
                    var5 = true;
                    break;
                }

                if (par4)
                {
                    --var6;
                }
                else
                {
                    ++var6;
                }
            }
        }

        return var5;
    }
}