package gregtechmod.common.items;

import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class GT_MetaItem_SmallDust extends GT_MetaItem_Abstract {

	public static GT_MetaItem_Abstract instance;
	
	public GT_MetaItem_SmallDust(int aID, String aName) {
		super(aID, aName);
		instance = this;
	}
	
	public static ItemStack[] getStackList() {
		return instance.mStackList;
	}
	
	public static ItemStack addItem(int aMeta, String aName, String aMaterial, String aToolTip, boolean aGlow) {
		GT_LanguageManager.addStringLocalization(instance.getUnlocalizedName() + "." + aMeta + ".name", "Tiny Pile of " + aName);

		instance.mToolTipList[aMeta] = aToolTip;
		instance.mGlowList[aMeta] = aGlow;

		instance.mStackList[aMeta] = new ItemStack(instance.itemID, 1, aMeta);
		if (aMaterial != null && !aMaterial.equals("")) {
			GT_OreDictUnificator.add("dustSmall" + aMaterial, instance.getUnunifiedStack(aMeta, 1));
		}
		
        GT_ModHandler.addToRecyclerBlackList(new ItemStack(instance.itemID, 1, aMeta));
        
		return instance.getUnunifiedStack(aMeta, 1);
	}
}