package gregtechmod.api.util;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GT_LanguageManager {
	public static volatile int VERSION = 303;
	
	/** Yeah! I got a File for Slang! */
    public static Configuration sLangFile;
	
	public static String[] mNameList3		= {"GT_LeadBlock", "GT_ElectrumBlock", "GT_ZincBlock", "GT_OlivineBlock", "GT_GreenSapphireBlock", "GT_PlatinumBlock", "GT_TungstenBlock", "GT_NickelBlock", "GT_TungstenSteelWall", "GT_TungstenSteelIridiumWall", "GT_InvarBlock", "GT_OsmiumBlock", "GT_IridiumBlock", "", "", ""};
	public static String[] mNameList0		= {"GT_Block", "Fusioncoil", "Iridium_reinforced_Stone", "Block_of_Silver", "Block_of_Ruby", "Block_of_Sapphire", "LESU_Block", "Block_of_Aluminium", "Block_of_Titanium", "Block_of_Chrome", "Highly_Advanced_Machineblock", "Block_of_Steel", "Block_of_Brass", "Standard_Machine_Casing", "Reinforced_Machine_Casing", "Advanced_Machine_Casing"};
    public static String[] mNameList1		= {"GT_BUG", "Fusionreactor", "Lightningrod", "Quantumchest", "GregTech_Computercube", "UUM_Assembler", "Sonictron", "Lapotronic_Energystorageunit", "Interdimensional_Energystorageunit", "Adjustable_Energystorageunit", "Charge_O_Mat", "Industrial_Centrifuge", "Superconductorwire", "Playerdetector", "Matterfabricator", "Supercondensator"};
    public static String[] mNameList2		= {"GT_Ore", "Galena_Ore", "Iridium_Ore", "Ruby_Ore", "Sapphire_Ore", "Bauxite_Ore", "Pyrite_Ore", "Cinnabar_Ore", "Sphalerite_Ore", "Tungstate_Ore", "Cooperite_Ore", "Olivine_Ore", "Sodalite_Ore", "", "", ""};
    public static String[] mNameListItem	= {"GT_MetaItem_00", "GT_MetaItem_01", "GT_MetaItem_02", "GT_MetaItem_03", "GT_MetaItem_04", "GT_MetaItem_05", "GT_MetaItem_06", "GT_MetaItem_07", "GT_MetaItem_08", "GT_MetaItem_09", "GT_MetaItem_10", "GT_MetaItem_11", "GT_MetaItem_12", "GT_MetaItem_13", "GT_MetaItem_14", "GT_MetaItem_15", "GregTech_Sensorcard", "GregTech_Sensorkit", "Ultimate_Cheat_Armor", "", "", "", "", "", "", "", "", "", "", "", "Iron_Mortar", "Flint_Mortar", "Sonictron", "Destructopack", "60k_Helium_Coolantcell", "180k_Helium_Coolantcell", "360k_Helium_Coolantcell", "Lapotronic_Energyorb", "Cloaking_Device", "Iron_Jack_Hammer", "Iridium_Neutronreflector", "Steel_Jack_Hammer", "Diamond_Jack_Hammer", "Dataorb", "Lighthelmet", "Lapotronpack", "Rockcutter", "Teslastaff", "Thoriumcell", "Double_Thoriumcell", "Quad_Thoriumcell", "Plutoniumcell", "Double_Plutoniumcell", "Quad_Plutoniumcell", "Lithium_Reactor_Cell", "Debug_Scanner", "Lithium_Battery_Empty", "Lithium_Battery", "Lithium_Batpack", "Personal_Force_Field_Generator", "60k_NaK_Coolantcell", "180k_NaK_Coolantcell", "360k_NaK_Coolantcell", "GT_Scanner", "GT_Crowbar", "GT_Screwdriver", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
	
    public static String[] mRegionalNameList3		= {"Block of Lead", "Block of Electrum", "Block of Zinc", "Block of Olivine", "Block of Green Sapphire", "Block of Platinum", "Block of Tungsten", "Block of Nickel", "Tungstensteel Block", "Iridium Reinforced Tungstensteel Block", "Invar Block", "Osmium Block", "Iridium Block", "", "", ""};
    public static String[] mRegionalNameList0		= {"GT_Block", "Fusion Coil", "Iridium Reinforced Stone", "Block of Silver", "Block of Ruby", "Block of Sapphire", "LESU-Block", "Block of Aluminium", "Block of Titanium", "Block of Chrome", "Highly Advanced Machineblock", "Block of Steel", "Block of Brass", "Standard Machine Casing", "Reinforced Machine Casing", "Advanced Machine Casing"};
    public static String[] mRegionalNameList1		= {"You ran into a serious Bug, if you have legitly aquired this Block, please report it immidietly", "Fusion Reactor", "Lightning Rod", "Old Quantum Chest", "GregTech Computer Cube", "UUM-Assembler", "Sonictron", "Lapotronic Energy Storage Unit", "Interdimensional Energy Storage Unit", "Adjustable Energy Storage Unit", "Charge-O-Mat", "Industrial Centrifuge", "Superconductor Wire", "Player Detector", "Matter Fabricator", "Supercondensator"};
    public static String[] mRegionalNameList2		= {"GT_Ore", "Galena Ore", "Iridium Ore", "Ruby Ore", "Sapphire Ore", "Bauxite Ore", "Pyrite Ore", "Cinnabar Ore", "Sphalerite Ore", "Tungstate Ore", "Sheldonite Ore", "Olivine Ore", "Sodalite Ore", "", "", ""};
    public static String[] mRegionalNameListItem	= {"GT_MetaItem_00", "GT_MetaItem_01", "GT_MetaItem_02", "GT_MetaItem_03", "GT_MetaItem_04", "GT_MetaItem_05", "GT_MetaItem_06", "GT_MetaItem_07", "GT_MetaItem_08", "GT_MetaItem_09", "GT_MetaItem_10", "GT_MetaItem_11", "GT_MetaItem_12", "GT_MetaItem_13", "GT_MetaItem_14", "GT_MetaItem_15", "GregTech Sensor Card", "GregTech Sensor Kit", "Ultimate Cheat Armor", "", "", "", "", "", "", "", "", "", "", "", "Iron Mortar", "Flint Mortar", "Sonictron", "Destructopack", "60k Helium Coolant Cell", "180k Helium Coolant Cell", "360k Helium Coolant Cell", "Lapotronic Energy Orb", "Cloaking Device", "Iron Jack Hammer", "Iridium Neutron Reflector", "Steel Jack Hammer", "Diamond Jack Hammer", "Data Orb", "Light Helmet", "Lapotron Pack", "Rock Cutter", "Tesla Staff", "Thorium Cell", "Double Thorium Cell", "Quad Thorium Cell", "Plutonium Cell", "Double Plutonium Cell", "Quad Plutonium Cell", "Lithium Reactor Cell", "Debug Scanner", "Lithium-Battery", "Lithium-Battery", "Lithium-Batpack", "Personal Force Field Generator", "60k NaK Coolantcell", "180k NaK Coolantcell", "360k NaK Coolantcell", "Portable Scanner", "Crowbar", "Screwdriver", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    
	public static String addStringLocalization(String aKey, String aValue) {
		sLangFile.load();
		Property tProperty = sLangFile.get("LanguageFile", aKey, aValue);
		aValue = tProperty.getString();
		if (!tProperty.wasRead()) sLangFile.save();
		LanguageRegistry.instance().addStringLocalization(aKey, aValue);
		return aValue;
	}
}