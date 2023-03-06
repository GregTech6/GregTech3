package gregtechmod.common.items;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.items.GT_Tool_Item;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class GT_Screwdriver_Item extends GT_Tool_Item {
	public GT_Screwdriver_Item(int aID, String aName, int aMaxDamage) {
		super(aID, aName, "To screw Covers on Machines", aMaxDamage);
		GregTech_API.sScrewdriverList.add(GT_Utility.stackToInt(new ItemStack(itemID, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)));
	}
}