package gregtechmod.common.items;

import gregtechmod.api.interfaces.ICapsuleCellContainer;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class GT_MetaItem_Cell extends GT_MetaItem_Abstract implements ICapsuleCellContainer {

	public static GT_MetaItem_Abstract instance;
	
	public GT_MetaItem_Cell(int aID, String aName) {
		super(aID, aName);
		instance = this;
	}
	
	public static ItemStack[] getStackList() {
		return instance.mStackList;
	}
	
	public static ItemStack addItem(int aMeta, String aName, String aMaterial, String aToolTip) {
		GT_LanguageManager.addStringLocalization(instance.getUnlocalizedName() + "." + aMeta + ".name", aName);
		
		instance.mToolTipList[aMeta] = aToolTip;
		
		instance.mStackList[aMeta] = new ItemStack(instance.itemID, 1, aMeta);
		
		GT_ModHandler.addExtractionRecipe(instance.getUnunifiedStack(aMeta, 1), GT_ModHandler.getIC2Item("cell", 1));
		
		if (aMaterial != null && !aMaterial.equals("")) {
			GT_OreDictUnificator.registerOre(aMaterial, instance.getUnunifiedStack(aMeta, 1));
			if (aMaterial.equals("molecule_1me")) {
				GT_OreDictUnificator.registerOre("molecule_1c_4h", instance.getUnunifiedStack(aMeta, 1));
			}
			if (aMaterial.equals("molecule_1d")) {
				GT_OreDictUnificator.registerOre("molecule_1h2", instance.getUnunifiedStack(aMeta, 1));
			}
			if (aMaterial.equals("molecule_1t")) {
				GT_OreDictUnificator.registerOre("molecule_1h3", instance.getUnunifiedStack(aMeta, 1));
			}
		}
		
		return instance.getUnunifiedStack(aMeta, 1);
	}
	
	@Override
	public int CapsuleCellContainerCount(ItemStack aStack) {
		return 1;
	}
}
