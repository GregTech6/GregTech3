package gregtechmod.common.items;

import gregtechmod.api.items.GT_Generic_Item;
import net.minecraft.item.ItemStack;

public class GT_Mortar_Item extends GT_Generic_Item {
	
	ItemStack mBrokenItem;
	
	public GT_Mortar_Item(int aID, String aName, int aMaxDamage, ItemStack aBrokenItem) {
		super(aID, aName, "Used to turn Ingots into Dust");
		setMaxDamage(aMaxDamage-1);
		setMaxStackSize(1);
		setNoRepair();
		mBrokenItem = aBrokenItem;
	}
	
    public ItemStack getContainerItemStack(ItemStack aStack) {
        if (aStack.getItemDamage() >= getMaxDamage()) return mBrokenItem.copy();
        return new ItemStack(itemID, 1, aStack.getItemDamage()+1);
    }
    
    public boolean hasContainerItem() {
        return true;
    }
    
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack par1ItemStack) {
        return false;
    }
}
