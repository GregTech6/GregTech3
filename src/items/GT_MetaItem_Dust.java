package gregtechmod.common.items;

import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class GT_MetaItem_Dust extends GT_MetaItem_Abstract {

	public static GT_MetaItem_Abstract instance;
	
	public GT_MetaItem_Dust(int aID, String aName) {
		super(aID, aName);
		instance = this;
	}
	
	public static ItemStack[] getStackList() {
		return instance.mStackList;
	}
	
	public static ItemStack addItem(int aMeta, String aName, String aMaterial, String aToolTip, boolean aGlow) {
		GT_LanguageManager.addStringLocalization(instance.getUnlocalizedName() + "." + aMeta + ".name", aName);
		
		instance.mToolTipList[aMeta] = aToolTip;
		instance.mGlowList[aMeta] = aGlow;
		
		instance.mStackList[aMeta] = new ItemStack(instance.itemID, 1, aMeta);
		if (aMaterial != null && !aMaterial.equals("")) {
			GT_OreDictUnificator.add("dust" + aMaterial, instance.getUnunifiedStack(aMeta, 1));
		}
		GT_MetaItem_SmallDust.addItem(aMeta, aName, aMaterial, aToolTip, aGlow);
		return instance.getUnunifiedStack(aMeta, 1);
	}
}
