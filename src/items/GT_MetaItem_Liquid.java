package gregtechmod.common.items;

import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_ModHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class GT_MetaItem_Liquid extends GT_MetaItem_Abstract {

	public static GT_MetaItem_Abstract instance;
	
	public GT_MetaItem_Liquid(int aID, String aName) {
		super(aID, aName);
		instance = this;
	}
	
	public static ItemStack[] getStackList() {
		return instance.mStackList;
	}
	
	public static ItemStack addItem(int aMeta, String aName, String aMaterial, String aToolTip, ItemStack aFullContainer, ItemStack aEmptyContainer, int aR, int aG, int aB) {
		GT_LanguageManager.addStringLocalization(instance.getUnlocalizedName() + "." + aMeta + ".name", "Liquid " + aName);
		
		instance.mToolTipList[aMeta] = aToolTip;
		
		if (aMaterial != null && !aMaterial.equals("")) {
			instance.mStackList[aMeta] = LiquidDictionary.getOrCreateLiquid("fluid"+aMaterial, new LiquidStack(instance.itemID, 1000, aMeta)).asItemStack();
		} else {
			instance.mStackList[aMeta] = new LiquidStack(instance.itemID, 1000, aMeta).asItemStack();
		}
		
		if (aFullContainer != null && aEmptyContainer != null)
			LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(instance.mStackList[aMeta].itemID, 1000, instance.mStackList[aMeta].getItemDamage()), aFullContainer, aEmptyContainer));
		
		GT_ModHandler.addLiquidTransposerRecipe(aEmptyContainer, new LiquidStack(instance.mStackList[aMeta].itemID, 1000, instance.mStackList[aMeta].getItemDamage()), aFullContainer, 160);
		
		return instance.getUnunifiedStack(aMeta, 1000);
	}
}
