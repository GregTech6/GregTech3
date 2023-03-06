package gregtechmod.api.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class GT_Config {
	public static volatile int VERSION = 303;
	
	public static boolean system = false;
	
	public static Configuration sConfigFileStandard, sConfigFileIDs, sConfigFileRecipes, sConfigFileAdvRecipes;
	
	public GT_Config(Configuration aConfigFileStandard, Configuration aConfigFileIDs, Configuration aConfigFileRecipes, Configuration aConfigFileAdvRecipes) {
		sConfigFileAdvRecipes = aConfigFileAdvRecipes;
		sConfigFileStandard = aConfigFileStandard;
		sConfigFileRecipes = aConfigFileRecipes;
		sConfigFileIDs = aConfigFileIDs;
	}
	
	public String getStackConfigName(ItemStack aStack) {
		if (aStack == null) return null;
		String rName;
		if ((rName = GT_OreDictUnificator.getAssociation(aStack)) != null && !rName.equals("")) return rName;
		try {if ((rName = aStack.getItemName()) != null && !rName.equals("")) return rName;} catch (Throwable e) {}
		return aStack.itemID + "." + aStack.getItemDamage();
	}
	
	public int addIDConfig(String aCategory, String aName, int aDefault) {
		if (aName == null) return aDefault;
		sConfigFileIDs.load();
		Property tProperty = sConfigFileIDs.get(aCategory, aName, aDefault);
		int rResult = tProperty.getInt(aDefault);
		if (!tProperty.wasRead()) sConfigFileIDs.save();
		return rResult;
	}
	
	public boolean addAdvConfig(String aCategory, ItemStack aStack, boolean aDefault) {
		return addAdvConfig(aCategory, getStackConfigName(aStack), aDefault);
	}
	
	public boolean addAdvConfig(String aCategory, String aName, boolean aDefault) {
		if (aName == null) return aDefault;
		sConfigFileAdvRecipes.load();
		Property tProperty = sConfigFileAdvRecipes.get(aCategory, aName, aDefault);
		boolean rResult = tProperty.getBoolean(aDefault);
		if (!tProperty.wasRead()) sConfigFileAdvRecipes.save();
		return rResult;
	}
	
	public int addAdvConfig(String aCategory, ItemStack aStack, int aDefault) {
		return addAdvConfig(aCategory, getStackConfigName(aStack), aDefault);
	}
	
	public int addAdvConfig(String aCategory, String aName, int aDefault) {
		if (aName == null) return aDefault;
		sConfigFileAdvRecipes.load();
		Property tProperty = sConfigFileAdvRecipes.get(aCategory, aName, aDefault);
		int rResult = tProperty.getInt(aDefault);
		if (!tProperty.wasRead()) sConfigFileAdvRecipes.save();
		return rResult;
	}
}
