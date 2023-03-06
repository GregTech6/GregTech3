package gregtechmod.common.items;

import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class GT_MetaItem_Material extends GT_MetaItem_Abstract {

	public static GT_MetaItem_Abstract instance;
	
	public GT_MetaItem_Material(int aID, String aName) {
		super(aID, aName);
		instance = this;
	}
	
	public static ItemStack[] getStackList() {
		return instance.mStackList;
	}
	
	public static ItemStack addItem(int aMeta, String aName, String aMaterial, String aToolTip, boolean aGlowing) {
		GT_LanguageManager.addStringLocalization(instance.getUnlocalizedName() + "." + aMeta + ".name", aName);
		
		instance.mToolTipList[aMeta] = aToolTip;
		instance.mGlowList[aMeta] = aGlowing;
		instance.mStackList[aMeta] = new ItemStack(instance.itemID, 1, aMeta);
		if (aMaterial != null && !aMaterial.equals("")) {
			if (aMaterial.startsWith("ingot") || aMaterial.startsWith("gem") || aMaterial.startsWith("dust") || aMaterial.startsWith("plate"))
				GT_OreDictUnificator.add(aMaterial, instance.getUnunifiedStack(aMeta, 1));
			else
				GT_OreDictUnificator.registerOre(aMaterial, instance.getUnunifiedStack(aMeta, 1));
		}
		return instance.getUnunifiedStack(aMeta, 1);
	}
}
