package gregtechmod.common.items;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.items.GT_Tool_Item;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class GT_Crowbar_Item extends GT_Tool_Item {
	public GT_Crowbar_Item(int aID, String aName, int aMaxDamage) {
		super(aID, aName, "To remove Covers from Machines", aMaxDamage);
		GregTech_API.sCrowbarList.add(GT_Utility.stackToInt(new ItemStack(itemID, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)));
	}
}