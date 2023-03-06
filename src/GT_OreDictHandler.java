package gregtechmod.common;

import gregtechmod.GT_Mod;
import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.common.items.GT_MetaItem_Cell;
import gregtechmod.common.items.GT_MetaItem_Component;
import gregtechmod.common.items.GT_MetaItem_Dust;
import gregtechmod.common.items.GT_MetaItem_Material;
import gregtechmod.common.items.GT_MetaItem_SmallDust;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class GT_OreDictHandler {
	private ArrayList<OreDictionary.OreRegisterEvent> mEvents = new ArrayList<OreDictionary.OreRegisterEvent>();
	private ArrayList<String> mIgnoredNames = new ArrayList<String>(), mValidPrefixes = new ArrayList<String>();
	private boolean mActivated = false;
	
	public GT_OreDictHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		mIgnoredNames.add("superconductor");
		mIgnoredNames.add("aluminumWire");
		mIgnoredNames.add("aluminiumWire");
		mIgnoredNames.add("silverWire");
		mIgnoredNames.add("tinWire");
		mIgnoredNames.add("eliteBattery");
		mIgnoredNames.add("advancedBattery");
		mIgnoredNames.add("transformer");
		mIgnoredNames.add("coil");
		mIgnoredNames.add("wireMill");
		mIgnoredNames.add("multimeter");
		mIgnoredNames.add("itemMultimeter");
		mIgnoredNames.add("chunkLazurite");
		mIgnoredNames.add("itemRecord");
        mIgnoredNames.add("aluminumNatural");
        mIgnoredNames.add("aluminiumNatural");
        mIgnoredNames.add("naturalAluminum");
        mIgnoredNames.add("naturalAluminium");
        mIgnoredNames.add("antimatterMilligram");
        mIgnoredNames.add("antimatterGram");
        mIgnoredNames.add("strangeMatter");
        mIgnoredNames.add("HSLivingmetalIngot");
        mIgnoredNames.add("oilMoving");
        mIgnoredNames.add("oilStill");
        mIgnoredNames.add("oilBucket");
        mIgnoredNames.add("petroleumOre");
        mIgnoredNames.add("dieselFuel");
        mIgnoredNames.add("lava");
        mIgnoredNames.add("water");
        mIgnoredNames.add("obsidianRod");
        mIgnoredNames.add("motor");
        mIgnoredNames.add("wrench");
        mIgnoredNames.add("batteryBox");
        mIgnoredNames.add("coalGenerator");
        mIgnoredNames.add("electricFurnace");
        mIgnoredNames.add("bronzeTube");
        mIgnoredNames.add("ironTube");
        mIgnoredNames.add("netherTube");
        mIgnoredNames.add("obbyTube");
        mIgnoredNames.add("unfinishedTank");
        mIgnoredNames.add("valvePart");
        mIgnoredNames.add("aquaRegia");
        mIgnoredNames.add("leatherSeal");
        mIgnoredNames.add("leatherSlimeSeal");
        mIgnoredNames.add("enrichedUranium");
        mIgnoredNames.add("batteryInfinite"); 
        mIgnoredNames.add("itemSuperconductor");

        mValidPrefixes.add("crafting");
        mValidPrefixes.add("ore");
        mValidPrefixes.add("oreNether");
        mValidPrefixes.add("oreEnd");
        mValidPrefixes.add("netherOre");
        mValidPrefixes.add("endOre");
        mValidPrefixes.add("stone");
        mValidPrefixes.add("pulp");
        mValidPrefixes.add("dust");
        mValidPrefixes.add("dustSmall");
        mValidPrefixes.add("nugget");
        mValidPrefixes.add("ingot");
        mValidPrefixes.add("gem");
        mValidPrefixes.add("log");
        mValidPrefixes.add("tree");
        mValidPrefixes.add("flower");
        mValidPrefixes.add("item");
        mValidPrefixes.add("brick");
        mValidPrefixes.add("plank");
        mValidPrefixes.add("sand");
        mValidPrefixes.add("glass");
        mValidPrefixes.add("dye");
        mValidPrefixes.add("slab");
        mValidPrefixes.add("stair");
        mValidPrefixes.add("clump");
        mValidPrefixes.add("paper");
        mValidPrefixes.add("book");
        mValidPrefixes.add("crop");
        mValidPrefixes.add("plasma_");
        mValidPrefixes.add("molecule_");
        
		/** because of the horribly failed OreDictEvents for vanilla Stuff */
		registerOre(new OreRegisterEvent("logWood", new ItemStack(Block.wood, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)));
        registerOre(new OreRegisterEvent("slabWood", new ItemStack(Block.woodSingleSlab, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)));
		registerOre(new OreRegisterEvent("plankWood", new ItemStack(Block.planks, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)));
		registerOre(new OreRegisterEvent("stickWood", new ItemStack(Item.stick, 1, 0)));
		registerOre(new OreRegisterEvent("treeLeaves", new ItemStack(Block.leaves, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)));
		registerOre(new OreRegisterEvent("treeSapling", new ItemStack(Block.sapling, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)));
		
        String[] dyes = {"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
        for(int i = 0; i < 16; i++) {
        	registerOre(new OreRegisterEvent(dyes[i], new ItemStack(Item.dyePowder, 1, i)));
        }
	}
    
	@ForgeSubscribe
    public void registerOre(OreDictionary.OreRegisterEvent aEvent) {
		if (aEvent == null || aEvent.Ore == null || aEvent.Name == null || aEvent.Name.equals("") || mIgnoredNames.contains(aEvent.Name)) return;

		try {
		
		if (aEvent.Ore.stackSize != 1) {
			System.err.println("'" + aEvent.Name + "' is either being misused by another Mod or has been wrongly registered, as the stackSize of the Event-Stack is not 1.");
		}
		
		aEvent.Ore.stackSize = 1;
		
		if (aEvent.Name.contains("Xych") || aEvent.Name.contains("xych") || aEvent.Name.contains("xyOre")) {
			//GT_ModHandler.addToRecyclerBlackList(aEvent.Ore);
			return;
		}
		
		if (GT_Mod.mPostLoaded) {
			System.err.println("WARNING: A Mod attempted to register " + aEvent.Name + " very late at the OreDict! Some Functionality may not work as expected!");
		}
		
		if (mValidPrefixes.contains(aEvent.Name)) {
			System.err.println("'" + aEvent.Name + "' is an invalid OreDictionary Name, as it is identical to the prefix '" + aEvent.Name + "'!");
			return;
		}
		
    	if (aEvent.Name.startsWith("itemDust")) {
    		System.err.println("'" + aEvent.Name + "' is an invalid OreDictionary Name, as it uses the prefix 'itemDust' instead of 'dust'. It will be re-registered with the correct 'dust'-prefix, and then be thrown into the recycle Bin to become Scrap.");
			GT_OreDictUnificator.registerOre(aEvent.Name.replaceFirst("itemDust", "dust"), aEvent.Ore.copy());
			aEvent.Ore.itemID = GT_ModHandler.getIC2Item("scrap", 1).getItem().itemID;
			aEvent.Ore.setItemDamage(0);
			return;
    	}
        
		if (aEvent.Name.equals("blueDye"))		{System.err.println("'blueDye'?, Are you sure that it shouldn't be using the more valid Name 'dyeBlue'?"); return;}
		if (aEvent.Name.equals("sapling"))		{System.err.println("'sapling'?, Are you sure that it shouldn't be using the more valid Name 'treeSapling'?"); return;}
		if (aEvent.Name.equals("saplingPalm"))	{System.err.println("'saplingPalm'?, Are you sure that it shouldn't be using the more valid Name 'treeSaplingPalm'?"); return;}
		if (aEvent.Name.equals("leaves"))		{System.err.println("'leaves'?, Are you sure that it shouldn't be using the more valid Name 'treeLeaves'?"); return;}
		if (aEvent.Name.equals("leavesPalm"))	{System.err.println("'leavesPalm'?, Are you sure that it shouldn't be using the more valid Name 'treeLeavesPalm'?"); return;}
		
		if (aEvent.Name.startsWith("ore") && !(aEvent.Ore.getItem() instanceof ItemBlock)) {
			System.err.println("WARNING: Detected Invalid OreDictionary Tag.");
			System.err.println("WARNING: Only Blocks can have the 'ore'-prefix");
			System.err.println("WARNING: A Nonblock-Item registered as " + aEvent.Name);
			System.err.println("WARNING: This can cause crashes! You were lucky it didn't crash");
			System.err.println("WARNING: Spamming Log to make this fatal error more visible");
			for (int i = 0; i < 50; i++) System.err.println("WARNING: !!!");
		}
		
		if (aEvent.Name.equals("battery"))				{GT_OreDictUnificator.registerOre("crafting10kEUStore"		, aEvent.Ore); return;}
		if (aEvent.Name.equals("basicCircuit"))			{GT_OreDictUnificator.registerOre("craftingCircuitTier02"	, aEvent.Ore); return;}
		if (aEvent.Name.equals("advancedCircuit"))		{GT_OreDictUnificator.registerOre("craftingCircuitTier04"	, aEvent.Ore); return;}
		if (aEvent.Name.equals("eliteCircuit"))			{GT_OreDictUnificator.registerOre("craftingCircuitTier06"	, aEvent.Ore); return;}
	    if (aEvent.Name.equals("MonazitOre"))			{GT_OreDictUnificator.registerOre("oreMonazit"				, aEvent.Ore); return;}
		if (aEvent.Name.equals("blockQuickSilver")) 	{GT_OreDictUnificator.registerOre("blockQuicksilver"		, aEvent.Ore); return;}
		if (aEvent.Name.equals("ingotQuickSilver")) 	{GT_OreDictUnificator.registerOre("ingotQuicksilver"		, aEvent.Ore); return;}
		if (aEvent.Name.equals("dustQuickSilver")) 		{GT_OreDictUnificator.registerOre("dustQuicksilver"			, aEvent.Ore); return;}
		if (aEvent.Name.equals("itemQuickSilver")) 		{GT_OreDictUnificator.registerOre("itemQuicksilver"			, aEvent.Ore); return;}
		if (aEvent.Name.equals("dustCharCoal")) 		{GT_OreDictUnificator.registerOre("dustCharcoal"			, aEvent.Ore); return;}
		if (aEvent.Name.equals("quartzCrystal"))		{GT_OreDictUnificator.registerOre("crystalQuartz"			, aEvent.Ore); return;}
    	if (aEvent.Name.equals("quartz"))				{GT_OreDictUnificator.registerOre("crystalQuartz"			, aEvent.Ore); return;}
    	if (aEvent.Name.equals("woodGas"))				{GT_OreDictUnificator.registerOre("gasWood"					, aEvent.Ore); return;}
    	if (aEvent.Name.equals("woodLog"))				{GT_OreDictUnificator.registerOre("logWood"					, aEvent.Ore); return;}
    	if (aEvent.Name.equals("pulpWood"))				{GT_OreDictUnificator.registerOre("dustWood"				, aEvent.Ore); return;}
    	if (aEvent.Name.equals("blockCobble"))			{GT_OreDictUnificator.registerOre("stoneCobble"				, aEvent.Ore); return;}
    	
    	if (aEvent.Name.startsWith("denseOre"))					{GT_OreDictUnificator.registerOre(aEvent.Name.replaceFirst("denseOre"	, "oreDense")	, aEvent.Ore); return;}
    	if (aEvent.Name.startsWith("netherOre")) 				{GT_OreDictUnificator.registerOre(aEvent.Name.replaceFirst("netherOre"	, "oreNether")	, aEvent.Ore); return;}
    	if (aEvent.Name.startsWith("endOre")) 					{GT_OreDictUnificator.registerOre(aEvent.Name.replaceFirst("endOre"		, "oreEnd")		, aEvent.Ore); return;}
    	if (aEvent.Name.startsWith("itemDrop"))					{GT_OreDictUnificator.registerOre(aEvent.Name.replaceFirst("itemDrop"	, "item")		, aEvent.Ore); return;}
    	
    	if (aEvent.Name.startsWith("stoneBlackGranite"))		{GT_OreDictUnificator.registerOre("stoneGranite", aEvent.Ore);}
		if (aEvent.Name.startsWith("stoneRedGranite"))			{GT_OreDictUnificator.registerOre("stoneGranite", aEvent.Ore);}
    	if (aEvent.Name.equals("craftingRawMachineTier01"))		{GT_OreDictUnificator.registerOre("craftingRawMachineTier00", aEvent.Ore);}
    	if (aEvent.Name.equals("dustDiamond"))					{GT_OreDictUnificator.registerOre("itemDiamond", aEvent.Ore);}
    	if (aEvent.Name.equals("gemDiamond"))					{GT_OreDictUnificator.registerOre("itemDiamond", aEvent.Ore);}
    	if (aEvent.Name.equals("dustLapis"))					{GT_OreDictUnificator.registerOre("itemLazurite", aEvent.Ore);}
    	if (aEvent.Name.equals("dustLapisLazuli"))				{GT_OreDictUnificator.registerOre("itemLazurite", aEvent.Ore);}
    	if (aEvent.Name.equals("dustLazurite"))					{GT_OreDictUnificator.registerOre("itemLazurite", aEvent.Ore);}
		if (aEvent.Name.equals("dustQuicksilver"))				{GT_OreDictUnificator.registerOre("itemQuicksilver", aEvent.Ore);}
		if (aEvent.Name.equals("ingotQuicksilver"))				{GT_OreDictUnificator.registerOre("itemQuicksilver", aEvent.Ore);}
		
		if (aEvent.Name.equals("dustSulfur"))					{GT_OreDictUnificator.registerOre("craftingSulfurToGunpowder", aEvent.Ore);}
		if (aEvent.Name.equals("dustSaltpeter"))				{GT_OreDictUnificator.registerOre("craftingSaltpeterToGunpowder", aEvent.Ore);}
    	
		try {
	    	if (aEvent.Name.equals("itemForcicium") || aEvent.Name.equals("ForciciumItem")) {
	    		GT_ModHandler.mForcicium = aEvent.Ore.copy();
	    	}
			if (aEvent.Name.equals("nuggetCopper") && aEvent.Ore.getItemName().equals("item.ItemNugget") && GT_ModHandler.mCopperNugget == null && aEvent.Ore.getItemDamage() == 1) {
	    		GT_ModHandler.mNuggetIron = new ItemStack(aEvent.Ore.getItem(), 1, 0);
	    		GT_ModHandler.mNuggetCopper = new ItemStack(aEvent.Ore.getItem(), 1, 1);
	    		GT_ModHandler.mNuggetTin = new ItemStack(aEvent.Ore.getItem(), 1, 2);
	    		GT_ModHandler.mNuggetSilver = new ItemStack(aEvent.Ore.getItem(), 1, 3);
	    		GT_ModHandler.mNuggetLead = new ItemStack(aEvent.Ore.getItem(), 1, 4);
			}
			if (aEvent.Name.equals("nuggetCopper") && aEvent.Ore.getItemName().equals("item.nuggetCopper") && GT_ModHandler.mCopperNugget == null && aEvent.Ore.getItemDamage() == 3) {
				GT_ModHandler.mIronNugget = new ItemStack(aEvent.Ore.getItem(), 1, 0);
	    		GT_ModHandler.mSilverNugget = new ItemStack(aEvent.Ore.getItem(), 1, 1);
	    		GT_ModHandler.mTinNugget = new ItemStack(aEvent.Ore.getItem(), 1, 2);
	    		GT_ModHandler.mCopperNugget = new ItemStack(aEvent.Ore.getItem(), 1, 3);
			}
			if (aEvent.Name.equals("dustNikolite") && aEvent.Ore.getItemName().equals("item.nikolite") && GT_ModHandler.mNikolite == null && aEvent.Ore.getItemDamage() == 6) {
	    		GT_ModHandler.mRuby = new ItemStack(aEvent.Ore.getItem(), 1, 0);
	    		GT_ModHandler.mGreenSapphire = new ItemStack(aEvent.Ore.getItem(), 1, 1);
	    		GT_ModHandler.mSapphire = new ItemStack(aEvent.Ore.getItem(), 1, 2);
	    		GT_ModHandler.mSilver = new ItemStack(aEvent.Ore.getItem(), 1, 3);
	    		GT_ModHandler.mTin = new ItemStack(aEvent.Ore.getItem(), 1, 4);
	    		GT_ModHandler.mCopper = new ItemStack(aEvent.Ore.getItem(), 1, 5);
	    		GT_ModHandler.mNikolite = new ItemStack(aEvent.Ore.getItem(), 1, 6);
			}
			if (aEvent.Name.equals("ingotBrass") && aEvent.Ore.getItemName().equals("item.ingotBrass") && GT_ModHandler.mBrass == null && aEvent.Ore.getItemDamage() == 2) {
	    		if (new ItemStack(aEvent.Ore.getItem(), 1, 0).getItemName().contains("red")) {
					GT_ModHandler.mRedAlloy = new ItemStack(aEvent.Ore.getItem(), 1, 0);
		    		GT_ModHandler.mBlueAlloy = new ItemStack(aEvent.Ore.getItem(), 1, 1);
		    		GT_ModHandler.mBrass = new ItemStack(aEvent.Ore.getItem(), 1, 2);
		    		GT_ModHandler.mSiliconBoule = new ItemStack(aEvent.Ore.getItem(), 1, 3);
		    		GT_ModHandler.mSiliconWafer = new ItemStack(aEvent.Ore.getItem(), 1, 4);
		    		GT_ModHandler.mBlueWafer = new ItemStack(aEvent.Ore.getItem(), 1, 5);
		    		GT_ModHandler.mRedWafer = new ItemStack(aEvent.Ore.getItem(), 1, 6);
		    		GT_ModHandler.mRPTinPlate = new ItemStack(aEvent.Ore.getItem(), 1, 7);
		    		GT_ModHandler.mFineCopper = new ItemStack(aEvent.Ore.getItem(), 1, 8);
		    		GT_ModHandler.mFineIron = new ItemStack(aEvent.Ore.getItem(), 1, 9);
		    		GT_ModHandler.mCopperCoil = new ItemStack(aEvent.Ore.getItem(), 1, 10);
		    		GT_ModHandler.mBlutricMotor = new ItemStack(aEvent.Ore.getItem(), 1, 11);
		    		GT_ModHandler.mCanvas = new ItemStack(aEvent.Ore.getItem(), 1, 12);
		    		ItemStack tStack1 = GT_ModHandler.mSiliconWafer.copy(); tStack1.stackSize = 16;
		    		GregTech_API.addSawmillRecipe(GT_ModHandler.mSiliconBoule.copy(), -1, tStack1, null, GT_ModHandler.getEmptyCell(1));
	    		}
	    	}
		} catch(Throwable e) {
			if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);
		}
		
    	if (aEvent.Name.startsWith("plate") || aEvent.Name.startsWith("ore") || aEvent.Name.startsWith("dust") || aEvent.Name.startsWith("gem") || aEvent.Name.startsWith("ingot") || aEvent.Name.startsWith("nugget") || aEvent.Name.startsWith("block")) {
    		GT_OreDictUnificator.addAssociation(aEvent.Name, aEvent.Ore.copy());
    	}
    	
    	if (mActivated)
    		registerRecipes(aEvent);
    	else
        	mEvents.add(aEvent);
    	
		} catch(Throwable e) {
			e.printStackTrace(GT_Log.err);
		}
    }
	
	/**
	 * Gets called during the PostLoad-Phase
	 */
    public void activateHandler() {
    	mActivated = true;
    	Iterator<OreDictionary.OreRegisterEvent> tIterator = mEvents.iterator();
		while (tIterator.hasNext()) registerRecipes(tIterator.next());
		mEvents = null;
    }
    
    public void registerRecipes(OreDictionary.OreRegisterEvent aEvent) {
    	if (aEvent.Ore == null || aEvent.Ore.getItem() == null) return;
    	
    	if (GregTech_API.DEBUG_MODE) {
    		GT_Log.out.println("OreDictRegistration: " + aEvent.Name + " -> ID: " + aEvent.Ore.itemID + ", Meta: " + aEvent.Ore.getItemDamage());
    	}
    	
    	aEvent.Ore.stackSize = 1;
    	ArrayList<ItemStack> tList;
    	Iterator<ItemStack> tIterator1;
    	ItemStack tStack1, tStack2, tStack3;
    	int tCellCount = 0, aMeta = aEvent.Ore.getItemDamage();
    	Item aItem = aEvent.Ore.getItem();
    	String aEventName = aEvent.Name;
    	
    	if (aEvent.Name.startsWith("plate") || aEvent.Name.startsWith("ore") || aEvent.Name.startsWith("dust") || aEvent.Name.startsWith("gem") || aEvent.Name.startsWith("ingot") || aEvent.Name.startsWith("nugget") || aEvent.Name.startsWith("block")) {
    		GT_OreDictUnificator.add(aEvent.Name, aEvent.Ore.copy());
    	}
    	
    	if (aEventName.startsWith("drop")) aEventName = aEventName.replaceFirst("drop", "item");
    	
	    if (aEventName.startsWith("stone")) {
	    	registerStoneRecipes(aItem, aMeta, aEventName, aEvent);
	    }
		else if (aEventName.startsWith("ore")) {
			registerOreRecipes(aItem, aMeta, aEventName, aEvent);
	    }
	    else if (aEventName.startsWith("dust")) {
	    	registerDustRecipes(aItem, aMeta, aEventName, aEvent);
	    }
	    else if (aEventName.startsWith("ingot")) {
	    	registerIngotRecipes(aItem, aMeta, aEventName, aEvent);
	    }
	    else if (aEventName.startsWith("block")) {
	    	registerBlockRecipes(aItem, aMeta, aEventName, aEvent);
	    }
	    else if (aEventName.startsWith("gem")) {
	    	registerGemRecipes(aItem, aMeta, aEventName, aEvent);
	    }
	    else if (aEventName.startsWith("crystal") && !aEventName.startsWith("crystalline")) {
	    	if (aEventName.equals("crystalQuartz")) {
	    		
	    	}
	    	else {
				System.out.println("Crystal Name: " + aEvent.Name + " !!!Unknown Crystal detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
	    	}
	    }
	    else if (aEventName.startsWith("plasma_")) {
	    	GregTech_API.addFuel(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta))==1?GT_ModHandler.getEmptyCell(1):null, 8192, 4);
	    	GregTech_API.addVacuumFreezerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta))==1?GT_OreDictUnificator.getFirstCapsulatedOre(aEventName.replaceFirst("plasma_", "molecule_"), 1):GT_OreDictUnificator.getFirstUnCapsulatedOre(aEventName.replaceFirst("plasma_", "molecule_"), 1), 100);
	    }
	    else if (aEventName.startsWith("molecule_")) {
		    if (aEventName.equals("molecule_1h")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(GT_MetaItem_Cell.instance.getStack(8, 1), new ItemStack(aItem, 4, aMeta), GT_MetaItem_Cell.instance.getStack(9, 5), 3500);
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 4, aMeta), GT_ModHandler.getIC2Item("airCell", 1), GT_ModHandler.getIC2Item("waterCell", 5), 10);
		    	}
	    		tCellCount =  4*GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) - 1;
	        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 4, aMeta), tCellCount<0?-tCellCount:0, GregTech_API.getGregTechItem(2, 1, 1), null, null, tCellCount>0?GT_ModHandler.getEmptyCell(tCellCount):null, 3000);
	    	}
		    else if (aEventName.equals("molecule_1he")) {
	    		tCellCount = 16*GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) - 1;
	    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), tCellCount<0?-tCellCount:0, GregTech_API.getGregTechItem(2, 1, 6), null, null, tCellCount>0?GT_ModHandler.getEmptyCell(tCellCount):null, 10000);
	    	}
		    else if (aEventName.equals("molecule_1d") || aEventName.equals("molecule_1h2")) {
	    		tList = GT_OreDictUnificator.getOres("molecule_1t");
	    		tList.addAll(GT_OreDictUnificator.getOres("molecule_1h3"));
	    		tIterator1 = tList.iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(3+128, 1), 128, -4096, 40000000);
	    		tIterator1 = GT_OreDictUnificator.getOres("molecule_1he3").iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(3+128, 1), 128, -2048, 60000000);
	    		
	    		tCellCount =  4*GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) - 1;
	    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 4, aMeta), tCellCount<0?-tCellCount:0, GregTech_API.getGregTechItem(2, 1, 2), null, null, tCellCount>0?GT_ModHandler.getEmptyCell(tCellCount):null, 3000);
	    	}
		    else if (aEventName.equals("molecule_1t") || aEventName.equals("molecule_1h3")) {
	    		tList = GT_OreDictUnificator.getOres("molecule_1d");
	    		tList.addAll(GT_OreDictUnificator.getOres("molecule_1h2"));
	    		tIterator1 = tList.iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_MetaItem_Cell.instance.getStack(3+128, 1), 128, -4096, 40000000);
	    	}
		    else if (aEventName.equals("molecule_1he3")) {
	    		tList = GT_OreDictUnificator.getOres("molecule_1d");
	    		tList.addAll(GT_OreDictUnificator.getOres("molecule_1h2"));
	    		tIterator1 = tList.iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_MetaItem_Cell.instance.getStack(3+128, 1), 128, -2048, 60000000);
	    	}
		    else if (aEventName.equals("molecule_1w")) {
	    		tIterator1 = GT_OreDictUnificator.getOres("molecule_1li").iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_ModHandler.getIC2Item("iridiumOre", 1), 512, -32768, 150000000);
	    		tIterator1 = GT_OreDictUnificator.getOres("molecule_1be").iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_MetaItem_Dust.instance.getStack(27, 1), 512, -32768, 100000000);
	    	}
		    else if (aEventName.equals("molecule_1li")) {
	    		tIterator1 = GT_OreDictUnificator.getOres("molecule_1w").iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("iridiumOre", 1), 512, -32768, 150000000);
	    	}
		    else if (aEventName.equals("molecule_1be")) {
	    		tIterator1 = GT_OreDictUnificator.getOres("molecule_1w").iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addFusionReactorRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(27, 1), 512, -32768, 100000000);
	    	}
		    else if (aEventName.equals("molecule_1c_4h") || aEventName.equals("molecule_1me")) {
	    		tCellCount =  5*GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) - 5;
	    		GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 5, aMeta), tCellCount<0?-tCellCount:0, GregTech_API.getGregTechItem(2, 4, 0), GregTech_API.getGregTechItem(2, 1, 8), null, tCellCount>0?GT_ModHandler.getEmptyCell(tCellCount):null, 150, 50);
	    	}
		    else if (aEventName.equals("molecule_1si")) {
	    		tCellCount = 2*GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta));
	    		GregTech_API.addBlastRecipe(new ItemStack(aItem, 2, aMeta), null, GT_MetaItem_Material.instance.getStack(36, 1), tCellCount>0?GT_ModHandler.getEmptyCell(tCellCount):null, 1000, 128, 1500);
	    	}
		    else if (aEventName.equals("molecule_1c")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(0, 4), GT_MetaItem_Cell.instance.getStack(9, 5), 3500);
		    		GregTech_API.addChemicalRecipe(GT_MetaItem_Cell.instance.getStack(15, 1), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(39, 2), 1500);
		    		GregTech_API.addChemicalRecipe(GT_MetaItem_Cell.instance.getStack(11, 1), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(33, 2), 250);
		    	}
		    }
		    else if (aEventName.equals("molecule_1ca")) {
	    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(8, 1), GT_MetaItem_Cell.instance.getStack(33, 2), 250);
		    }
		    else if (aEventName.equals("molecule_1na")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(GT_MetaItem_Cell.instance.getStack(36, 1), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(37, 2), 100);
		    	}
		    }
		    else if (aEventName.equals("molecule_1s")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(12, 1), GT_MetaItem_Cell.instance.getStack(37, 2), 100);
		    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_OreDictUnificator.get("dustSulfur", null, 1), null, null, GT_ModHandler.getEmptyCell(1), 40);
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("waterCell", 2), GT_MetaItem_Cell.instance.getStack(40, 3), 1150);
		    	}
		    }
		    else if (aEventName.equals("molecule_1na_1s")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 2, aMeta), GT_ModHandler.getIC2Item("airCell", 3), GT_MetaItem_Cell.instance.getStack(32, 5), 4000);
		    	}
		    }
		    else if (aEventName.equals("molecule_1cl")) {
		    	
		    }
		    else if (aEventName.equals("molecule_1k")) {
		    	
		    }
		    else if (aEventName.equals("molecule_1n")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("airCell", 1), GT_MetaItem_Cell.instance.getStack(38, 2), 1250);
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(8, 1), GT_MetaItem_Cell.instance.getStack(39, 2), 1500);
		    	}
		    }
		    else if (aEventName.equals("molecule_1n_1c")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getIC2Item("waterCell", 3), GT_MetaItem_Cell.instance.getStack(34, 6), 1750);
		    	}
		    }
		    else if (aEventName.equals("molecule_2h_1s_4o")) {
		    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 7, aMeta), 0, GT_MetaItem_Cell.instance.getStack(0, 2), GT_MetaItem_Cell.instance.getStack(36, 1), GT_ModHandler.getIC2Item("airCell", 2), GT_ModHandler.getEmptyCell(2), 40, 100);
		    }
		    else if (aEventName.equals("molecule_1n_2o")) {
		    	
		    }
		    else if (aEventName.equals("molecule_1hg")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		if (GT_ModHandler.mTCResource != null) GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, new ItemStack(GT_ModHandler.mTCResource.getItem(), 1, 3), null, null, GT_ModHandler.getEmptyCell(1), 40);
		    	}
		    }
		    else if (aEventName.equals("molecule_1ca_1c_3o")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_OreDictUnificator.get("dustCalcite", null, 1), null, null, GT_ModHandler.getEmptyCell(1), 40);
		    	}
		    }
		    else if (aEventName.equals("molecule_2na_2s_8o")) {
		    	
		    }
		    else if (aEventName.equals("molecule_2o")) {
		    	
		    }
		    else if (aEventName.equals("molecule_1o")) {
		    	
		    }
		    else if (aEventName.equals("molecule_3c_5h_3n_9o")) {
		    	if (GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) == 1) {
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("coalfuelCell", 4), GT_MetaItem_Cell.instance.getStack(35, 5), 250);
		    		GregTech_API.addChemicalRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(18, 4), GT_MetaItem_Cell.instance.getStack(22, 5), 250);
		    	}
		    }
	    	else {
				System.out.println("Molecule Name: " + aEvent.Name + " !!!Unknown Molecule detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
	    	}
    	}
	    else if (aEventName.startsWith("nugget")) {
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(aItem, 1, aMeta));
	    	if (aEventName.equals("nuggetGold")) {
	    		if (GT_ModHandler.mBCStoneGear==null) {
	    			tIterator1 = GT_OreDictUnificator.getOres("stoneCobble").iterator();
		    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 4, aMeta), GT_ModHandler.getRCItem("part.gear.gold.plate", 1), 800, 1);
	    		} else {
	    			GregTech_API.addAssemblerRecipe(GT_ModHandler.mBCStoneGear, new ItemStack(aItem, 4, aMeta), GT_ModHandler.getRCItem("part.gear.gold.plate", 1), 800, 1);
	    		}
	    	}
	    	if (aEventName.equals("nuggetIridium") || aEventName.equals("nuggetOsmium") || aEventName.equals("nuggetUranium") || aEventName.equals("nuggetPlutonium") || aEventName.equals("nuggetThorium"))
	    		GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 9, aMeta), GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("nugget", "ingot"), 1));
	    	else
	    		GregTech_API.addAlloySmelterRecipe(new ItemStack(aItem, 9, aMeta), null, GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("nugget", "ingot"), 1), 200, 1);
	    	GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("nugget", "ingot"), 1), new Object[] {aEventName, aEventName, aEventName, aEventName, aEventName, aEventName, aEventName, aEventName, aEventName});
    		GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.getFirstOre(aEventName, 9), new Object[] {aEventName.replaceFirst("nugget", "ingot")});
	    }
	    else if (aEventName.startsWith("element_")) {
	    	System.err.println("Depricated Prefix 'element_' @ " + aEvent.Name + " please change to 'molecule_'");
	    }
	    else if (aEventName.startsWith("cell_")) {
	    	System.err.println("Depricated Prefix 'cell_' @ " + aEvent.Name + " Cells are now detected automatically, if you register as 'molecule_', so you don't need to register with this prefix any longer");
	    }
	    else if (aEventName.startsWith("element")) {
	    	
	    }
	    else if (aEventName.startsWith("cell")) {
	    	
	    }
	    else if (aEventName.startsWith("crafting")) {
	    	if (aEventName.equals("craftingLiBattery")) {
	    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("cropnalyzer", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GregTech_API.getGregTechItem(63, 1, GregTech_API.getGregTechItem(63, 1, 0).getMaxDamage()-1), 12800, 16);
    		    tIterator1 = GT_OreDictUnificator.getOres("plateAluminium").iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), tIterator1.next().copy().splitStack(4), GT_MetaItem_Component.instance.getStack(26, 1), 3200, 4);
	    	} else if (aEventName.equals("craftingRawMachineTier01")) {
	    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.music, 4, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(GregTech_API.sBlockList[1], 1, 66), 800, 1);
	    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.stoneButton, 16, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(GregTech_API.sBlockList[1], 1, 67), 800, 1);
	    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Component.instance.getStack(22, 1), new ItemStack(GregTech_API.sBlockList[1], 1, 79), 1600, 2);
	    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Component.instance.getStack( 7, 1), GT_ModHandler.getIC2Item("solarPanel", 1), 1600, 2);
	    	}
	    }
	    else if (aEventName.startsWith("clump")) {
	    	
	    }
	    else if (aEventName.startsWith("glass")) {
	    	
	    }
	    else if (aEventName.startsWith("gear")) {
	    	
	    }
	    else if (aEventName.startsWith("food")) {
	    	
	    }
	    else if (aEventName.startsWith("flower")) {
	    	
	    }
	    else if (aEventName.startsWith("dye")) {
	    	GT_OreDictUnificator.addDye(new ItemStack(aItem, 1, aMeta));
	    }
	    else if (aEventName.startsWith("plate")) {
	    	boolean temp = true;
    		GT_ModHandler.removeRecipe(new ItemStack[] {new ItemStack(aItem, 1, aMeta)});
	    	if (temp) {
		    	if (aEventName.startsWith("plateAlloy")) {
		    		temp = false;
		    	} else {
		    		GT_ModHandler.removeRecipe(new ItemStack(aItem, 1, aMeta));
		    	}
	    	}
	    	if (temp) {
		    	if (aEventName.startsWith("plateDense")) {
		    		temp = false;
		    		GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("plateDense", "ingot"), 8));
			    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("plateDense", "dust"), 8), null, 0, false);
		    	} else {
		    		GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 8, aMeta), GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("plate", "plateDense"), 1));
		    		GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("plate", "ingot"), 1));
			    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("plate", "dust"), 1), null, 0, false);
			    }
	    	}
	    	if (temp) {
	    		if (aEventName.equals("plateCopper")) {
		    		GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 8, aMeta), GT_ModHandler.getIC2Item("denseCopperPlate", 1));
		    	}
			    else if (aEventName.equals("plateIron")) {
		    		GregTech_API.addBenderRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("part.rail.standard", 4), 150, 20);
			    }
			    else if (aEventName.equals("plateBronze")) {
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_MetaItem_Component.instance.getStack(22, 1), GT_MetaItem_Component.instance.getStack(33, 1), 400, 8);
		    		GregTech_API.addBenderRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getRCItem("part.rail.standard", 1), 50, 20);
			    }
			    else if (aEventName.equals("plateBrass")) {
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_MetaItem_Component.instance.getStack(22, 1), GT_MetaItem_Component.instance.getStack(34, 1), 400, 8);
			    }
	    		else if (aEventName.equals("plateRefinedIron")) {
	    			GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 5, aMeta), new ItemStack(Block.chest, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.hopperBlock), 800, 2);
	    			GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 5, aMeta), new ItemStack(Block.chestTrapped, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.hopperBlock), 800, 2);
	    			
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("pump", 1), GT_MetaItem_Component.instance.getStack(6, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.pressurePlateIron, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(11, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.pressurePlateGold, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(10, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("ecMeter", 1), GT_MetaItem_Component.instance.getStack(15, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 2, aMeta), new ItemStack(Block.fenceIron, 2), GT_MetaItem_Component.instance.getStack(9, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.torchRedstoneActive, 1), GT_MetaItem_Component.instance.getStack(30, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.torchRedstoneIdle, 1), GT_MetaItem_Component.instance.getStack(30, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.lever, 1), GT_MetaItem_Component.instance.getStack(31, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.workbench, 1), GT_MetaItem_Component.instance.getStack(64, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("energyCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(12, 1), 1600, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("lapotronCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(13, 1), 3200, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GregTech_API.getGregTechItem(37, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(14, 1), 6400, 16);
		    		
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 2, aMeta), GT_ModHandler.getIC2Item("electronicCircuit", 1), GT_MetaItem_Component.instance.getStack(22, 4), 800, 16);
	    			GregTech_API.addBenderRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("part.rail.standard", 5), 150, 20);
	    		    tIterator1 = GT_OreDictUnificator.getOres("plateElectrum").iterator();
		    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), tIterator1.next().copy().splitStack(2), GT_MetaItem_Component.instance.getStack(48, 2), 800, 1);
			    }
			    else if (aEventName.equals("plateSteel")) {
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 2, aMeta), GT_ModHandler.getRCItem("machine.beta.engine.steam.high", 1, GregTech_API.getGregTechBlock(1, 1, 34)), GT_MetaItem_Component.instance.getStack(80, 1), 1600, 32);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 2, aMeta), new ItemStack(Block.thinGlass, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(81, 1), 1600, 32);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_MetaItem_Component.instance.getStack(22, 1), GT_MetaItem_Component.instance.getStack(35, 1), 400, 8);
		    		GregTech_API.addBenderRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("part.rail.standard", 8), 150, 30);
			    }
			    else if (aEventName.equals("plateTitanium")) {
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_MetaItem_Component.instance.getStack(22, 1), GT_MetaItem_Component.instance.getStack(36, 1), 400, 8);
		    		GregTech_API.addBenderRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("part.rail.standard", 16), 150, 30);
			    }
			    else if (aEventName.equals("plateTungsten")) {
		    		GregTech_API.addBenderRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("part.rail.standard", 16), 150, 30);
			    }
			    else if (aEventName.equals("plateTungstenSteel")) {
		    		GregTech_API.addBenderRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getRCItem("part.rail.reinforced", 8), 100, 30);
			    }
			    else if (aEventName.equals("plateAluminium")) {
	    			GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 5, aMeta), new ItemStack(Block.chest, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.hopperBlock), 800, 2);
	    			GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 5, aMeta), new ItemStack(Block.chestTrapped, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.hopperBlock), 800, 2);
	    			
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("pump", 1), GT_MetaItem_Component.instance.getStack(6, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.pressurePlateIron, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(11, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.pressurePlateGold, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(10, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("ecMeter", 1), GT_MetaItem_Component.instance.getStack(15, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 2, aMeta), new ItemStack(Block.fenceIron, 2), GT_MetaItem_Component.instance.getStack(9, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.torchRedstoneActive, 1), GT_MetaItem_Component.instance.getStack(30, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.torchRedstoneIdle, 1), GT_MetaItem_Component.instance.getStack(30, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.lever, 1), GT_MetaItem_Component.instance.getStack(31, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.workbench, 1), GT_MetaItem_Component.instance.getStack(64, 1), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("energyCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(12, 1), 1600, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("lapotronCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(13, 1), 3200, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GregTech_API.getGregTechItem(37, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(14, 1), 6400, 16);
		    		
		    		tIterator1 = GT_OreDictUnificator.getOres("craftingLiBattery").iterator();
			    	while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 4, aMeta), GT_MetaItem_Component.instance.getStack(26, 1), 3200, 4);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 2, aMeta), GT_ModHandler.getIC2Item("electronicCircuit", 1), GT_MetaItem_Component.instance.getStack(22, 3), 800, 16);
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_MetaItem_Component.instance.getStack(22, 1), GT_MetaItem_Component.instance.getStack(32, 1), 400, 8);
			    	GregTech_API.addBenderRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("part.rail.standard", 2), 150, 10);
				    GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("generator", 1), new ItemStack(aItem, 4, aMeta), GT_ModHandler.getIC2Item("waterMill", 2), 6400, 8);
			    	tIterator1 = GT_OreDictUnificator.getOres("plateElectrum").iterator();
		    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), tIterator1.next().copy().splitStack(2), GT_MetaItem_Component.instance.getStack(48, 2), 800, 1);
			    }
			    else if (aEventName.equals("plateElectrum")) {
			    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("electronicCircuit", 1), new ItemStack(aItem, 2, aMeta), GT_MetaItem_Component.instance.getStack(49, 1), 1600, 2);
				    tIterator1 = GT_OreDictUnificator.getOres("plateRefinedIron").iterator();
		    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 2, aMeta), GT_MetaItem_Component.instance.getStack(48, 2), 800, 1);
		    		tIterator1 = GT_OreDictUnificator.getOres("plateAluminium").iterator();
		    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 2, aMeta), GT_MetaItem_Component.instance.getStack(48, 2), 800, 1);
		    		tIterator1 = GT_OreDictUnificator.getOres("plateSilicon").iterator();
		    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 4, aMeta), GT_MetaItem_Component.instance.getStack(49, 2), 1600, 2);
		    	}
			    else if (aEventName.equals("plateSilicon")) {
		    		tIterator1 = GT_OreDictUnificator.getOres("plateElectrum").iterator();
		    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), tIterator1.next().copy().splitStack(4), GT_MetaItem_Component.instance.getStack(49, 2), 1600, 2);
		    	}
			    else if (aEventName.equals("platePlatinum")) {
		    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("advancedCircuit", 1), GT_MetaItem_Component.instance.getStack(50, 1), 3200, 4);
		    	}
			    else if (aEventName.equals("plateMagnalium")) {
			    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("generator", 1), new ItemStack(aItem, 2, aMeta), GT_ModHandler.getIC2Item("windMill", 1), 6400, 8);
		    	}
	    	}
	    }
	    else if (aEventName.startsWith("dirtyGravel")) {
	    	
	    }
	    else if (aEventName.startsWith("cleanGravel")) {
	    	
	    }
	    else if (aEventName.startsWith("crystalline")) {
	    	
	    }
	    else if (aEventName.startsWith("reduced")) {
	    	
	    }
	    else if (aEventName.startsWith("paper")) {
	    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
	    }
	    else if (aEventName.startsWith("book")) {
	    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
	    }
	    else if (aEventName.startsWith("FZ.")) {
	    	
	    }
	    else if (aEventName.startsWith("crop")) {
	    	
	    }
	    else if (aEventName.startsWith("icbm:")) {
	    	
	    }
	    else if (aEventName.startsWith("mffs")) {
	    	
	    }
	    else if (aEventName.startsWith("MiscPeripherals$")) {
	    	
	    }
	    else if (aEventName.equals("gasWood")) {
    		tCellCount =  16*GT_ModHandler.getCapsuleCellContainerCount(new ItemStack(aItem, 1, aMeta)) - 16;
    		GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 16, aMeta), tCellCount<0?-tCellCount:0, GregTech_API.getGregTechItem(2, 4, 0), GregTech_API.getGregTechItem(2, 8, 8), GregTech_API.getGregTechItem(2, 4, 9), tCellCount>0?GT_ModHandler.getEmptyCell(tCellCount):null, 200, 100);
    	}
	    else if (aEventName.equals("woodRubber") || aEventName.equals("logRubber")) {
	    	if (aItem instanceof ItemBlock && GT_Mod.sWoodStackSize < aItem.getItemStackLimit()) aItem.setMaxStackSize(GT_Mod.sWoodStackSize);
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), 5, GT_ModHandler.getIC2Item("resin", 8), GT_ModHandler.getIC2Item("plantBall", 6), GregTech_API.getGregTechItem(2, 1, 9), GregTech_API.getGregTechItem(2, 4, 8), 5000);
    		GT_ModHandler.addSawmillRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("resin", 1), GT_MetaItem_Dust.instance.getStack(15, 16));
	    }
	    else if (aEventName.startsWith("log")) {
	    	if (aItem instanceof ItemBlock && GT_Mod.sWoodStackSize < aItem.getItemStackLimit()) aItem.setMaxStackSize(GT_Mod.sWoodStackSize);
	    	if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) {
		    	for (int i = 0; i < 16; i++) {
			    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, i));
			    	tStack2 = new ItemStack(aItem, 1, i);
			    	tStack1 = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2, null, null, null, null, null, null, null, null});
			    	if (tStack1 != null) {
			    		tStack1 = tStack1.copy();
			    		tStack1.stackSize = (tStack1.stackSize * 3) / 2;
			    		GT_ModHandler.addSawmillRecipe(tStack2, tStack1, GT_MetaItem_Dust.instance.getStack(15, 1));
			    	}
	    		}
	    	} else {
		    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
		    	tStack2 = new ItemStack(aItem, 1, aMeta);
		    	tStack1 = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2, null, null, null, null, null, null, null, null});
		    	if (tStack1 != null) {
		    		tStack1 = tStack1.copy();
		    		tStack1.stackSize = (tStack1.stackSize * 3) / 2;
		    		GT_ModHandler.addSawmillRecipe(tStack2, tStack1, GT_MetaItem_Dust.instance.getStack(15, 1));
		    	}
	    	}
	    }
	    else if (aEventName.startsWith("slabWood")) {
	    	if (aItem instanceof ItemBlock && GT_Mod.sPlankStackSize < aItem.getItemStackLimit()) aItem.setMaxStackSize(GT_Mod.sPlankStackSize);
	    	if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) {
		    	for (int i = 0; i < 16; i++) {
			    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, i));
			    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, i), GT_OreDictUnificator.get("dustSmallWood", 2), null, 0, false);
			    	GregTech_API.addCannerRecipe(new ItemStack(aItem, 3, i), GT_ModHandler.getRCItem("liquid.creosote.cell", 1), GT_ModHandler.getRCItem("part.tie.wood", 1), GT_ModHandler.getEmptyCell(1), 200, 4);
			    	GregTech_API.addCannerRecipe(new ItemStack(aItem, 3, i), GT_ModHandler.getRCItem("liquid.creosote.bucket", 1), GT_ModHandler.getRCItem("part.tie.wood", 1), new ItemStack(Item.bucketEmpty, 1), 200, 4);
			    }
	    	} else {
		    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
		    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustSmallWood", 2), null, 0, false);
		    	GregTech_API.addCannerRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("liquid.creosote.cell", 1), GT_ModHandler.getRCItem("part.tie.wood", 1), GT_ModHandler.getEmptyCell(1), 200, 4);
		    	GregTech_API.addCannerRecipe(new ItemStack(aItem, 3, aMeta), GT_ModHandler.getRCItem("liquid.creosote.bucket", 1), GT_ModHandler.getRCItem("part.tie.wood", 1), new ItemStack(Item.bucketEmpty, 1), 200, 4);
	    	}
	    }
	    else if (aEventName.startsWith("plankWood")) {
	    	if (aItem instanceof ItemBlock && GT_Mod.sPlankStackSize < aItem.getItemStackLimit()) aItem.setMaxStackSize(GT_Mod.sPlankStackSize);
	    	if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) {
		    	for (int i = 0; i < 16; i++) {
			    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, i));
			    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, i), GT_MetaItem_Dust.instance.getStack(15, 1), null, 0, false);
			    }
	    	} else {
		    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
		    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(15, 1), null, 0, false);
	    	}
	    	GregTech_API.addCNCRecipe(new ItemStack(aItem, 2, aMeta), GT_ModHandler.mBCWoodGear, 800, 1);
	    	tIterator1 = GT_OreDictUnificator.getOres("dustRedstone").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), tIterator1.next().copy().splitStack(1), new ItemStack(Block.music, 1), 800, 1);
	    	tIterator1 = GT_OreDictUnificator.getOres("gemDiamond").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), tIterator1.next().copy().splitStack(1), new ItemStack(Block.jukebox, 1), 1600, 1);
	    }
	    else if (aEventName.startsWith("treeSapling")) {
	    	if (aItem instanceof ItemBlock && GT_Mod.sWoodStackSize < aItem.getItemStackLimit()) aItem.setMaxStackSize(GT_Mod.sWoodStackSize);
	    	if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) {
		    	for (int i = 0; i < 16; i++) {
			    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, i));
		    		GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 4, i), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
			    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, i), GT_MetaItem_SmallDust.instance.getStack(15, 2), null, 0, false);
		    	}
	    	} else {
		    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
	    		GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 4, aMeta), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
		    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_SmallDust.instance.getStack(15, 2), null, 0, false);
	    	}
	    }
	    else if (aEventName.equals("treeLeaves")) {
	    	if (aItem instanceof ItemBlock && GT_Mod.sWoodStackSize < aItem.getItemStackLimit()) aItem.setMaxStackSize(GT_Mod.sWoodStackSize);
	    	if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) {
		    	for (int i = 0; i < 16; i++) {
			    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, i));
			    	GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 8, i), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
			    }
	    	} else {
		    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
		    	GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 8, aMeta), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
	    	}
	    }
	    else if (aEventName.startsWith("stickWood")) {
	    	tIterator1 = GT_OreDictUnificator.getOres("stoneCobble").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), tIterator1.next().copy().splitStack(1), new ItemStack(Block.lever, 1), 400, 1);
	    	GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Item.coal, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.torchWood, 4), 400, 1);
	    	GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("resin", 1), new ItemStack(Block.torchWood, 4), 400, 1);
	    	
	    	tIterator1 = GT_OreDictUnificator.getOres("dustRedstone").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), tIterator1.next().copy().splitStack(1), new ItemStack(Block.torchRedstoneActive, 1), 400, 1);
	    	
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) {
		    	for (int i = 0; i < 16; i++) {
			    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, i));
			    }
	    	} else {
		    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
	    	}
	    }
	    else if (aEventName.startsWith("stair")) {
	    	
	    }
	    else if (aEventName.startsWith("slab")) {
	    	
	    }
	    else if (aEventName.equals("itemCopperWire") || aEventName.equals("copperWire")) {
	    	GregTech_API.addAssemblerRecipe(GT_MetaItem_Component.instance.getStack(48, 1), new ItemStack(aItem, 3, aMeta), GT_ModHandler.getIC2Item("electronicCircuit", 1), 800, 1);
	    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("electronicCircuit", 1), new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("frequencyTransmitter", 1), 800, 1);
	    }
	    else if (aEventName.equals("itemLazurite") || aEventName.equals("lazurite")) {
    		tIterator1 = GT_OreDictUnificator.getOres("dustGlowstone").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 1, aMeta), tIterator1.next().copy().splitStack(1), GT_MetaItem_Component.instance.getStack(24, 2), 800, 2);
	    }
	    else if (aEventName.equals("itemDiamond") || aEventName.equals("diamond")) {
    		GregTech_API.addAssemblerRecipe(GT_ModHandler.mBCGoldGear, new ItemStack(aItem, 4, aMeta), GT_ModHandler.mBCDiamondGear, 1600, 2);
	    }
	    else if (aEventName.equals("itemIridium") || aEventName.equals("iridium")) {
	    	
	    }
    	else if (aEventName.equals("itemTar") || aEventName.equals("tar")) {
	    	
	    }
	    else if (aEventName.equals("fuelCoke") || aEventName.equals("coke")) {
	    	
	    }
	    else if (aEventName.equals("itemBeeswax") || aEventName.equals("beeswax")) {
	    	
	    }
	    else if (aEventName.equals("itemBeeComb") || aEventName.equals("beeComb")) {
	    	GT_OreDictUnificator.addAssociation(aEvent.Name, new ItemStack(aItem, 1, aMeta));
	    }
    	else if (aEventName.equals("itemForcicium") || aEventName.equals("ForciciumItem")) {
	    	
	    }
    	else if (aEventName.equals("itemForcillium")) {
	    	
	    }
	    else if (aEventName.equals("brickPeat") || aEventName.equals("peat")) {
	    	
	    }
	    else if (aEventName.equals("itemRoyalJelly") || aEventName.equals("royalJelly")) {
	    	
	    }
	    else if (aEventName.equals("itemHoneydew") || aEventName.equals("honeydew")) {
	    	
	    }
	    else if (aEventName.equals("itemHoney") || aEventName.equals("honey")) {
	    	
	    }
	    else if (aEventName.equals("itemPollen") || aEventName.equals("pollen")) {
	    	
	    }
	    else if (aEventName.equals("itemReedTypha") || aEventName.equals("reedTypha")) {
	    	
	    }
	    else if (aEventName.equals("itemSulfuricAcid") || aEventName.equals("sulfuricAcid")) {
	    	
	    }
	    else if (aEventName.equals("itemRubber") || aEventName.equals("rubber")) {
	    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("copperCableItem", 1)				, new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1), 100, 2);
	    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("goldCableItem", 1)					, new ItemStack(aItem, 2, aMeta), GT_ModHandler.getIC2Item("doubleInsulatedGoldCableItem", 1), 200, 2);
	    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("ironCableItem", 1)					, new ItemStack(aItem, 3, aMeta), GT_ModHandler.getIC2Item("trippleInsulatedIronCableItem", 1), 300, 2);
	    	
	    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("insulatedGoldCableItem", 1) 		, new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("doubleInsulatedGoldCableItem", 1), 100, 2);
	    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("insulatedIronCableItem", 1)			, new ItemStack(aItem, 2, aMeta), GT_ModHandler.getIC2Item("trippleInsulatedIronCableItem", 1), 200, 2);
	    	
	    	GregTech_API.addAssemblerRecipe(GT_ModHandler.getIC2Item("doubleInsulatedIronCableItem", 1)	, new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("trippleInsulatedIronCableItem", 1), 100, 2);
	    }
	    else if (aEventName.equals("itemPotash") || aEventName.equals("potash")) {
	    	
	    }
	    else if (aEventName.equals("itemCompressedCarbon") || aEventName.equals("compressedCarbon")) {
	    	
	    }
	    else if (aEventName.equals("itemManganese") || aEventName.equals("manganese")) {
	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(12, 1), null, 0, false);
	    }
	    else if (aEventName.equals("itemMagnesium") || aEventName.equals("magnesium")) {
	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(13, 1), null, 0, false);
	    }
	    else if (aEventName.equals("itemPhosphorite") || aEventName.equals("phosphorite") || aEventName.equals("itemPhosphorus") || aEventName.equals("phosphorus")) {
	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustPhosphorus", null, 1), null, 0, false);
	    }
	    else if (aEventName.equals("itemBitumen") || aEventName.equals("bitumen")) {
	    	
	    }
	    else if (aEventName.equals("itemBioFuel")) {
	    	
	    }
	    else if (aEventName.equals("itemEnrichedAlloy")) {
	    	
	    }
	    else if (aEventName.equals("itemQuicksilver") || aEventName.equals("quicksilver")) {
	    	GregTech_API.addCannerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getEmptyCell(1), GT_MetaItem_Cell.instance.getStack(16, 1), null, 100, 1);
	    }
	    else if (aEventName.equals("chunkOsmium") || aEventName.equals("itemOsmium") || aEventName.equals("osmium")) {
	    	
	    }
	    else if (aEventName.equals("sandOil") || aEventName.equals("oilsandsOre")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 2, aMeta), 1, GT_MetaItem_Cell.instance.getStack(17, 1), new ItemStack(Block.sand, 1, 0), null, null, 1000);
	    }
	    else if (aEventName.equals("itemSulfur") || aEventName.equals("sulfur")) {
	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(8, 1), null, 0, false);
	    }
	    else if (aEventName.equals("itemAluminum") || aEventName.equals("aluminum") || aEventName.equals("itemAluminium") || aEventName.equals("aluminium")) {
	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(18, 1), null, 0, false);
	    }
	    else if (aEventName.equals("itemSaltpeter") || aEventName.equals("saltpeter")) {
	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(9, 1), null, 0, false);
	    }
	    else if (aEventName.equals("itemUranium") || aEventName.equals("uranium")) {
	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustUranium", null, 1), null, 0, false);
	    }
	    else if (aEventName.equals("sandCracked")) {
	    	if (aItem.itemID < 4096) {
				GregTech_API.addJackHammerMinableBlock(Block.blocksList[aItem.itemID]);
	    	}
	    	if (aItem instanceof ItemBlock && aItem.getItemStackLimit() > GT_Mod.sBlockStackSize) aItem.setMaxStackSize(GT_Mod.sBlockStackSize);
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), -1, GT_ModHandler.getFuelCan(25000), GT_MetaItem_Dust.instance.getStack(9, 8), null, new ItemStack(Block.sand, 10), 2500);
	    }
	    else if (aEventName.startsWith("item")) {
	    	System.out.println("Item Name: " + aEvent.Name + " !!!Unknown Item detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
	    }
    	else {
			System.out.println("Thingy Name: " + aEvent.Name + " !!!Unknown 'Thingy' detected!!! This Object seems to probably not follow a valid OreDictionary Convention, or I missed a Convention. Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
    	}
    }
    
    private void registerStoneRecipes(Item aItem, int aMeta, String aEventName, OreDictionary.OreRegisterEvent aEvent) {
    	if (aItem.itemID < 4096) {
			GregTech_API.addJackHammerMinableBlock(Block.blocksList[aItem.itemID]);
    	}
    	if (aItem instanceof ItemBlock && aItem.getItemStackLimit() > GT_Mod.sBlockStackSize) aItem.setMaxStackSize(GT_Mod.sBlockStackSize);
    	
    	if (aEventName.startsWith("stoneCobble")) {
    		
    	}
    	else if (aEventName.startsWith("stoneSmooth")) {
    		
    	}
    	else if (aEventName.startsWith("stoneBricks")) {
    		
    	}
    	else if (aEventName.startsWith("stoneMossy")) {
    		
    	}
    	else if (aEventName.startsWith("stoneCracked")) {
    		
    	}
    	else if (aEventName.startsWith("stoneChiseled")) {
    		
    	}
    	else if (aEventName.startsWith("stoneVanilla")) {
    		
    	}
    	else if (aEventName.equals("stoneSand")) {
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) for (byte i = 0; i < 16; i++) {
    			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1,     i), new ItemStack(Block.sand, 1, 0), null, 10, false);
    		} else {
    			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.sand, 1, 0), null, 10, false);
    		}
    	}
    	else if (aEventName.equals("stoneEnd")) {
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) for (byte i = 0; i < 16; i++) {
    		    GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1,     i)	, GT_OreDictUnificator.get("dustEndstone", 1), GT_OreDictUnificator.get("dustEndstone", 1), 10, false);
    	    } else {
    	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta)	, GT_OreDictUnificator.get("dustEndstone", 1), GT_OreDictUnificator.get("dustEndstone", 1), 10, false);
    	    }
        }
    	else if (aEventName.equals("stoneNetherrack")) {
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) for (byte i = 0; i < 16; i++) {
    		    GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1,     i)	, GT_OreDictUnificator.get("stoneNetherrack", 1), GT_OreDictUnificator.get("stoneNetherrack", 1), 10, false);
    	    } else {
    	    	GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta)	, GT_OreDictUnificator.get("stoneNetherrack", 1), GT_OreDictUnificator.get("stoneNetherrack", 1), 10, false);
    		}
        }
    	else if (aEventName.equals("stoneNetherBrick")) {
    		
    	}
    	else if (aEventName.equals("stoneNetherQuartz")) {
    		
    	}
    	else if (aEventName.equals("stoneGranite")) {
    		
    	}
    	else if (aEventName.equals("stoneRedrock") || aEventName.equals("stoneRedRock")) {
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) for (byte i = 0; i < 16; i++) {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1,     i), GT_OreDictUnificator.get("stoneRedRock", 1), GT_OreDictUnificator.get("stoneRedRock", 1), 10, false);
    		} else {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("stoneRedRock", 1), GT_OreDictUnificator.get("stoneRedRock", 1), 10, false);
    		}
    	}
    	else if (aEventName.startsWith("stoneMarble")) {
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) for (byte i = 0; i < 16; i++) {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1,     i), GT_OreDictUnificator.get("dustMarble", 1), GT_OreDictUnificator.get("dustMarble", 1), 10, false);
    		} else {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustMarble", 1), GT_OreDictUnificator.get("dustMarble", 1), 10, false);
    		}
    	}
    	else if (aEventName.startsWith("stoneBasalt")) {
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) for (byte i = 0; i < 16; i++) {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1,     i), GT_OreDictUnificator.get("dustBasalt", 1), GT_OreDictUnificator.get("dustBasalt", 1), 10, false);
    		} else {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustBasalt", 1), GT_OreDictUnificator.get("dustBasalt", 1), 10, false);
    		}
    	}
    	else if (aEventName.startsWith("stoneFlint")) {
    		if (aMeta == GregTech_API.ITEM_WILDCARD_DAMAGE) for (byte i = 0; i < 16; i++) {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1,     i), GT_OreDictUnificator.get("dustFlint", 2), new ItemStack(Item.flint, 1), 50, false);
    		} else {
	    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustFlint", 2), new ItemStack(Item.flint, 1), 50, false);
    		}
    	}
    	else if (aEventName.startsWith("stoneChalk")) {
    		
    	}
    	else if (aEventName.startsWith("stoneMigmatite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneEpidote")) {
    		
    	}
    	else if (aEventName.startsWith("stoneChert")) {
    		
    	}
    	else if (aEventName.startsWith("stoneSoapstone")) {
    		
    	}
    	else if (aEventName.startsWith("stoneKomatiite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneGreywacke")) {
    		
    	}
    	else if (aEventName.startsWith("stoneGreenschist")) {
    		
    	}
    	else if (aEventName.startsWith("stoneBlueschist")) {
    		
    	}
    	else if (aEventName.startsWith("stoneGabbro")) {
    		
    	}
    	else if (aEventName.startsWith("stoneLignite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneQuartzite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneAndesite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneSiltstone")) {
    		
    	}
    	else if (aEventName.startsWith("stoneRhyolite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneShale")) {
    		
    	}
    	else if (aEventName.startsWith("stoneBlackGranite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneEclogite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneGneiss")) {
    		
    	}
    	else if (aEventName.startsWith("stoneRedGranite")) {
    		
    	}
    	else if (aEventName.startsWith("stoneLimestone")) {
    		
    	}
    	else {
			System.out.println("Stone Name: " + aEvent.Name + " !!!Unknown kind of Stone detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
    	}
    }
    
    private void registerOreRecipes(Item aItem, int aMeta, String aEventName, OreDictionary.OreRegisterEvent aEvent) {
    	boolean aNether = false, aEnd = false, aDense = false;
    	if (aEventName.startsWith("oreDense")) {aEventName = aEventName.replaceFirst("oreDense", "ore"); aDense = true;}
    	if (aEventName.startsWith("oreNether")) {aEventName = aEventName.replaceFirst("oreNether", "ore"); aNether = true;}
    	if (aEventName.startsWith("oreEnd")) {aEventName = aEventName.replaceFirst("oreEnd", "ore"); aEnd = true;}
    	
    	if (aItem instanceof ItemBlock && aItem.getItemStackLimit() > GT_Mod.sOreStackSize) aItem.setMaxStackSize(GT_Mod.sOreStackSize);
		if (aEventName.equals("oreLapis")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, new ItemStack(Item.dyePowder, aDense||aNether?18:12, 4), GT_MetaItem_Dust.instance.getStack(2, 3), null, GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Item.dyePowder, 12, 4), GT_MetaItem_Dust.instance.getStack(2, 1), 0, true);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreSodalite")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(5, aDense||aNether?18:12), GT_MetaItem_Dust.instance.getStack(18, 3), null, GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(5, 12), GT_MetaItem_Dust.instance.getStack(18, 1), 0, false);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreRedstone")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, new ItemStack(Item.redstone, aDense||aNether?15:10), GT_MetaItem_SmallDust.instance.getStack(250, 2), null, GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Item.redstone, 10), new ItemStack(Item.lightStoneDust, 1), 0, true);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreQuartz")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustNetherQuartz", null, 5), GT_OreDictUnificator.get("dustNetherrack", null, 1), null, GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustNetherQuartz", null, 3), GT_OreDictUnificator.get("dustNetherrack", null, 1), 0, false);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreNikolite")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.getFirstOre("dustNikolite", aDense||aNether?18:12), GT_MetaItem_SmallDust.instance.getStack(36, aDense||aNether?2:1), null, GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre("dustNikolite", aDense||aNether?15:10), GT_MetaItem_Dust.instance.getStack(36, 1), 0, false);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreIron")) {
    	    GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustIron", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustNickel", null, 1), 0, false);
    	    if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotIron", null, 1));
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustIron", null, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(244, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
    		GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(33, 1), GT_OreDictUnificator.get("ingotRefinedIron", null, aDense||aNether?5:3), GT_ModHandler.getEmptyCell(1), 100, 128, 1000);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreGold")) {
    	    GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustGold", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustCopper", null, 1), 0, false);
    	    if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotGold", null, 1));
			GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustGold", null, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(243, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
			GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_OreDictUnificator.get("dustGold", null, aDense||aNether?5:3), GT_MetaItem_SmallDust.instance.getStack(243, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
    		GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(32, 1), GT_OreDictUnificator.get("dustGold", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustCopper", null, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreSilver")) {
    	    GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustSilver", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustLead", null, 1), 0, false);
    	    if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotSilver", null, 1));
			GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustSilver", null, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(23, 2), null, GT_ModHandler.getEmptyCell(1));
			GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_OreDictUnificator.get("dustSilver", null, aDense||aNether?5:3), GT_MetaItem_SmallDust.instance.getStack(23, 2), null, GT_ModHandler.getEmptyCell(1));
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreLead")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(23, aDense||aNether?4:2), GT_OreDictUnificator.get("dustSilver", null, 1), 0, false);
	        if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotLead", null, 1));
			GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(23, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(246, 1), null, GT_ModHandler.getEmptyCell(1));
			GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_OreDictUnificator.get("dustLead", null, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(246, 1), null, GT_ModHandler.getEmptyCell(1));
		}
    	else if (aEventName.equals("oreSilverLead") || aEventName.equals("oreGalena")) {
    	    GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustGalena", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustSulfur", null, 1), aDense||aNether?100:50, false);
    	    GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustGalena", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustSulfur", null, aDense||aNether?2:1), null, GT_ModHandler.getEmptyCell(1));
    	    GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_OreDictUnificator.get("dustGalena", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustSulfur", null, aDense||aNether?2:1), GT_OreDictUnificator.get("dustSilver", null, aDense||aNether?2:1), GT_ModHandler.getEmptyCell(1));
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreDiamond")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, new ItemStack(Item.diamond, aDense||aNether?2:1), GT_MetaItem_SmallDust.instance.getStack(36, 6), GT_ModHandler.getIC2Item("hydratedCoalDust", 1), GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(36, aDense||aNether?3:2), GT_OreDictUnificator.get("dustCoal", null, 1), 0, true);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 5);
    	}
    	else if (aEventName.equals("oreEmerald")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, new ItemStack(Item.emerald, aDense||aNether?2:1), GT_MetaItem_SmallDust.instance.getStack(35, aDense||aNether?12:6), GT_MetaItem_SmallDust.instance.getStack(37, 2), GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(35, aDense||aNether?3:2), GT_MetaItem_Dust.instance.getStack(37, 1), 0, true);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 5);
    	}
    	else if (aEventName.equals("oreRuby")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("gemRuby", null, aDense||aNether?2:1), GT_MetaItem_SmallDust.instance.getStack(32, aDense||aNether?12:6), GT_MetaItem_SmallDust.instance.getStack(54, 2), GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(32, aDense||aNether?3:2), GT_MetaItem_Dust.instance.getStack(54, 1), 0, true);
    	}
    	else if (aEventName.equals("oreSapphire")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("gemSapphire", null, aDense||aNether?2:1), GT_MetaItem_SmallDust.instance.getStack(33, aDense||aNether?12:6), GT_MetaItem_SmallDust.instance.getStack(34, 2), GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(33, aDense||aNether?3:2), GT_MetaItem_Dust.instance.getStack(34, 1), 0, true);
    	}
    	else if (aEventName.equals("oreGreenSapphire")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("gemGreenSapphire", null, aDense||aNether?2:1), GT_MetaItem_SmallDust.instance.getStack(34, aDense||aNether?12:6), GT_MetaItem_SmallDust.instance.getStack(33, 2), GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(34, aDense||aNether?3:2), GT_MetaItem_Dust.instance.getStack(33, 1), 0, true);
    	}
    	else if (aEventName.equals("oreOlivine")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Material.instance.getStack(37, aDense||aNether?2:1), GT_MetaItem_SmallDust.instance.getStack(37, aDense||aNether?12:6), GT_MetaItem_SmallDust.instance.getStack(35, 2), GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(37, aDense||aNether?3:2), GT_MetaItem_Dust.instance.getStack(35, 1), 0, true);
    	}
    	else if (aEventName.equals("oreCoal")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, new ItemStack(Item.coal, aDense||aNether?2:1), GT_OreDictUnificator.get("dustCoal", null, aDense||aNether?2:1), GT_OreDictUnificator.get("dustSmallThorium", null, 1), GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustCoal", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustThorium", null, 1), 0, true);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 1);
    	}
    	else if (aEventName.equals("oreCopper")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustCopper", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustGold", null, 1), 0, false);
    		if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotCopper", null, 1));
			GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustCopper", null, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(242, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
			GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(32, 1), GT_OreDictUnificator.get("dustCopper", null, aDense||aNether?5:3), GT_MetaItem_SmallDust.instance.getStack(242, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
			GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_OreDictUnificator.get("dustCopper", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustGold", null, 1), null, GT_ModHandler.getEmptyCell(1));
		}
        else if (aEventName.equals("oreTin")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustTin", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustIron", null, 1), 0, false);
			if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotTin", null, 1));
			GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustTin", null, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(241, 1), GT_MetaItem_SmallDust.instance.getStack(24, 1), GT_ModHandler.getEmptyCell(1));
			GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(32, 1), GT_OreDictUnificator.get("dustTin", null, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(241, 1), GT_MetaItem_Dust.instance.getStack(24, 1), GT_ModHandler.getEmptyCell(1));
		}
    	else if (aEventName.equals("oreZinc")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(24, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(244, 2), null, GT_ModHandler.getEmptyCell(1));
    		GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(32, 1), GT_MetaItem_Dust.instance.getStack(24, aDense||aNether?6:3), GT_MetaItem_SmallDust.instance.getStack(244, 2), null, GT_ModHandler.getEmptyCell(1));
			if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(24, 1));
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(24, aDense||aNether?4:2), GT_OreDictUnificator.get("dustTin", null, 1), 0, false);
    	}
        else if (aEventName.equals("oreCassiterite") || aEventName.equals("oreCasserite")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustTin", null, aDense||aNether?3:2), GT_OreDictUnificator.get("dustTin", null, 1), 0, false);
			if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotTin", null, 1));
			GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustTin", null, aDense||aNether?6:3), null, null, GT_ModHandler.getEmptyCell(1));
		}
    	else if (aEventName.equals("oreIridium")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 10);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_ModHandler.getIC2Item("iridiumOre", aDense||aNether?3:2), GT_MetaItem_SmallDust.instance.getStack(27, 2), null, GT_ModHandler.getEmptyCell(1));
	        GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_ModHandler.getIC2Item("iridiumOre", aDense||aNether?3:2), GT_MetaItem_Dust.instance.getStack(27, 1), null, GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("iridiumOre", aDense||aNether?3:2), GT_MetaItem_Dust.instance.getStack(27, 1), 0, true);
    	}
    	else if (aEventName.equals("oreCooperite") || aEventName.equals("oreSheldonite")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 10);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(27, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(28, 1), GT_OreDictUnificator.get("nuggetIridium", null, 2), GT_ModHandler.getEmptyCell(1));
	        GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_MetaItem_Dust.instance.getStack(27, aDense||aNether?6:3), GT_MetaItem_Dust.instance.getStack(28, 1), GT_OreDictUnificator.get("nuggetIridium", null, 2), GT_ModHandler.getEmptyCell(1));
	        if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(27, 1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(27, aDense||aNether?4:2), GT_ModHandler.getIC2Item("iridiumOre", 1), 0, true);
    	}
    	else if (aEventName.equals("orePlatinum")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 7);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(27, aDense||aNether?4:2), GT_OreDictUnificator.get("nuggetIridium", null, 2), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
    		GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_MetaItem_Dust.instance.getStack(27, aDense||aNether?6:3), GT_OreDictUnificator.get("nuggetIridium", null, 2), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
    		if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(27, 1));
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(27, aDense||aNether?4:2), GT_ModHandler.getIC2Item("iridiumOre", 1), 0, false);
        }
    	else if (aEventName.equals("oreNickel")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(28, aDense||aNether?6:3), GT_MetaItem_SmallDust.instance.getStack(27, 1), GT_MetaItem_SmallDust.instance.getStack(243, 1), GT_ModHandler.getEmptyCell(1));
    		GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(16, 1), GT_MetaItem_Dust.instance.getStack(28, aDense||aNether?6:3), GT_MetaItem_Dust.instance.getStack(27, 1), null, GT_ModHandler.getEmptyCell(1));
			if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(28, 1));
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(28, aDense||aNether?4:2), GT_OreDictUnificator.get("dustTin", null, 1), 0, false);
        }
    	else if (aEventName.equals("orePyrite")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 1);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(3, 5), GT_MetaItem_Dust.instance.getStack(8, 2), null, GT_ModHandler.getEmptyCell(1));
	        GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(33, 1), GT_OreDictUnificator.get("ingotRefinedIron", null, 2), GT_ModHandler.getEmptyCell(1), 100, 128, 1500);
    		GT_ModHandler.addInductionSmelterRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Block.sand, 1), new ItemStack(Item.ingotIron, 1), GT_ModHandler.getTEItem("slagRich", 1), 300, 10);
    		GT_ModHandler.addInductionSmelterRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getTEItem("slagRich", 1), new ItemStack(Item.ingotIron, 2), GT_ModHandler.getTEItem("slag", 1), 300, 95);
	        GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Item.ingotIron, 1));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(3, 5), GT_OreDictUnificator.get("dustIron", null, 1), 0, true);
    	}
    	else if (aEventName.equals("oreCinnabar")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(11, 5), GT_MetaItem_SmallDust.instance.getStack(249, 2), GT_MetaItem_SmallDust.instance.getStack(250, 1), GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(11, 3), new ItemStack(Item.redstone, 1), 0, true);
    	}
    	else if (aEventName.equals("oreSphalerite")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(14, 5), GT_MetaItem_Dust.instance.getStack(24, 1), GT_MetaItem_SmallDust.instance.getStack(55, 1), GT_ModHandler.getEmptyCell(1));
	        GT_Mod.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Cell.instance.getStack(32, 1), GT_MetaItem_Dust.instance.getStack(14, 5), GT_MetaItem_Dust.instance.getStack(24, 3), GT_MetaItem_SmallDust.instance.getStack(55, 1), GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(14, 4), GT_MetaItem_Dust.instance.getStack(24, 1), 0, true);
    	}
    	else if (aEventName.equals("oreAluminium") || aEventName.equals("oreAluminum")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(18, aDense||aNether?6:3), GT_MetaItem_SmallDust.instance.getStack(19, 1), null, GT_ModHandler.getEmptyCell(1));
	        if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "aluminium", true)?GT_OreDictUnificator.get("dustAluminium", null, 1):GT_OreDictUnificator.get("ingotAluminium", null, 1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(18, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(19, 1), 0, true);
	    }
    	else if (aEventName.equals("oreNaturalAluminium") || aEventName.equals("oreNaturalAluminum")) {
    		
    	}
    	else if (aEventName.equals("oreSteel")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustSteel", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustSmallNickel", null, 2), null, GT_ModHandler.getEmptyCell(1));
	        if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "steel", true)?GT_OreDictUnificator.get("dustSteel", null, 1):GT_OreDictUnificator.get("ingotSteel", null, 1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustSteel", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustNickel", null, 2), 0, true);
		}
    	else if (aEventName.equals("oreTitan") || aEventName.equals("oreTitanium")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 5);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(19, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(18, 2), null, GT_ModHandler.getEmptyCell(1));
	        if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "titanium", true)?GT_OreDictUnificator.get("dustTitanium", null, 1):GT_OreDictUnificator.get("ingotTitanium", null, 1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(19, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(18, 1), 0, true);
		}
    	else if (aEventName.equals("oreChrome") || aEventName.equals("oreChromium")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 10);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(20, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(32, 1), null, GT_ModHandler.getEmptyCell(1));
	        if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "chrome", true)?GT_OreDictUnificator.get("dustChrome", null, 1):GT_OreDictUnificator.get("ingotChrome", null, 1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(20, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(32, 1), 0, true);
		}
    	else if (aEventName.equals("oreElectrum")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 5);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(21, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(242, 1), GT_MetaItem_SmallDust.instance.getStack(246, 1), GT_ModHandler.getEmptyCell(1));
	        if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(21, 1));
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(21, aDense||aNether?4:2), GT_OreDictUnificator.get("dustGold", null, 1), 0, false);
        }
    	else if (aEventName.equals("oreTungsten") || aEventName.equals("oreTungstate")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(22, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(12, 1), 0, true);
    		if (!aDense||aNether) GT_ModHandler.addOreToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "tungsten", true)?GT_OreDictUnificator.get("dustTungsten", null, 1):GT_OreDictUnificator.get("ingotTungsten", null, 1));
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(22, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(241, 3), GT_MetaItem_SmallDust.instance.getStack(12, 3), GT_ModHandler.getEmptyCell(1));
		}
    	else if (aEventName.equals("oreBauxite")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
	        GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(17, aDense||aNether?8:4), GT_MetaItem_Dust.instance.getStack(18, aDense||aNether?2:1), null, GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(17, aDense||aNether?8:4), GT_MetaItem_Dust.instance.getStack(18, 1), 0, true);
		}
    	else if (aEventName.equals("oreApatite")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 1);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre("gemApatite", aDense||aNether?16:8), GT_OreDictUnificator.get("dustPhosphorus", null, 1), 0, false);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.getFirstOre("gemApatite", aDense||aNether?24:12), GT_OreDictUnificator.get("dustPhosphorus", null, 1), null, GT_ModHandler.getEmptyCell(1));
    	}
    	else if (aEventName.equals("oreSulfur")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 1);
			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustSulfur", null, 3));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(8, aDense||aNether?16:8), GT_OreDictUnificator.get("dustSulfur", null, 1), 0, false);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(8, aDense||aNether?20:10), null, null, GT_ModHandler.getEmptyCell(1));
    	}
    	else if (aEventName.equals("oreSaltpeter")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustSaltpeter", null, 3));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(9, aDense||aNether?10:5), GT_OreDictUnificator.get("dustSaltpeter", null, 1), 0, false);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(9, aDense||aNether?14:7), null, null, GT_ModHandler.getEmptyCell(1));
    	}
    	else if (aEventName.equals("orePhosphorite")) {
			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustPhosphorus", null, 2));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustPhosphorus", null, aDense||aNether?6:3), GT_OreDictUnificator.get("dustPhosphorus", null, 1), 0, false);
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
    	}
    	else if (aEventName.equals("oreMagnesium")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(13, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(241, 2), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
	        
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(13, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(28, 1), 0, false);
		}
    	else if (aEventName.equals("oreManganese")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(12, aDense||aNether?4:2), GT_MetaItem_SmallDust.instance.getStack(241, 2), GT_MetaItem_SmallDust.instance.getStack(28, 1), GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
	        
	        GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(12, aDense||aNether?4:2), GT_MetaItem_Dust.instance.getStack(28, 1), 0, false);
    	}
    	else if (aEventName.equals("oreMonazit") || aEventName.equals("oreMonazite")) {
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, new ItemStack(GT_ModHandler.mForcicium.getItem(), aDense||aNether?20:10, GT_ModHandler.mForcicium.getItemDamage()), GT_OreDictUnificator.get("dustThorium", null, 2), null, GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
	    }
    	else if (aEventName.equals("oreFortronite")) {
    		//GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, new ItemStack(GT_ModHandler.mForcicium.getItem(), aDense||aNether?20:10, GT_ModHandler.mForcicium.getItemDamage()), GT_OreDictUnificator.get("dustThorium", null, 2), null, GT_ModHandler.getEmptyCell(1));
	        GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
	    }
    	else if (aEventName.equals("oreThorium")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustThorium", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustUranium", null, 1), 0, true);
    	    GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustThorium", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustSmallUranium", null, 1), null, GT_ModHandler.getEmptyCell(1));
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 5);
    	}
    	else if (aEventName.equals("orePlutonium")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustPlutonium", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustUranium", null, 1), 0, true);
    	    GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_OreDictUnificator.get("dustPlutonium", null, aDense||aNether?4:2), GT_OreDictUnificator.get("dustUranium", null, 1), null, GT_ModHandler.getEmptyCell(1));
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 15);
    	}
    	else if (aEventName.equals("oreOsmium")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 20);
    	}
    	else if (aEventName.equals("oreEximite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreMeutoite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("orePrometheum")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreDeep Iron")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
    	}
    	else if (aEventName.equals("oreDeepIron")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
    	}
    	else if (aEventName.equals("oreInfuscolium")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreOureclase")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreAredrite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreAstral Silver")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreAstralSilver")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreCarmot")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreMithril")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreRubracium")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreOrichalcum")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreAdamantine")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 5);
    	}
    	else if (aEventName.equals("oreAtlarus")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreIgnatius")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreShadow Iron")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreShadowIron")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("oreMidasium")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreVyroxeres")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreCeruclase")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreKalendrite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreVulcanite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreSanguinite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreLemurite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreAdluorite")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 3);
    	}
    	else if (aEventName.equals("oreBitumen")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
    	}
    	else if (aEventName.equals("oreCertusQuartz")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 4);
    	}
    	else if (aEventName.equals("orePotash")) {
    		GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 2);
    	}
    	else if (aEventName.equals("oreUranium")) {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 5);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(16, aDense||aNether?4:2), GT_OreDictUnificator.get("dustPlutonium", null, 1), 0, false);
    		GregTech_API.addGrinderRecipe(new ItemStack(aItem, 1, aMeta), -1, GT_MetaItem_Dust.instance.getStack(16, aDense||aNether?4:2), GT_OreDictUnificator.get("dustSmallPlutonium", null, 2), GT_OreDictUnificator.get("dustThorium", null, 1), GT_ModHandler.getEmptyCell(1));
    	}
    	else {
			GT_ModHandler.addValuableOre(aItem.itemID, aMeta, 1);
			System.out.println("Ore Name: " + aEvent.Name + " !!!Unknown Ore detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information. This Ore will still get added to the List of the IC2-Miner, but with a low Value.");
    	}
    }
    
    private void registerIngotRecipes(Item aItem, int aMeta, String aEventName, OreDictionary.OreRegisterEvent aEvent) {
    	Iterator<ItemStack> tIterator1;
    	ItemStack tStack1, tStack2, tStack3;
    	
    	GregTech_API.addBenderRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("ingot", "plate"), 1), 50, 20);
    	if (null != (tStack1 = GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("ingot", "block"), 1)))
    		if (GregTech_API.sConfiguration.addAdvConfig("StorageBlockCompressing", aEventName, true))
    			GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 9, aMeta), tStack1.copy());
    	
    	
    	if ((GT_ModHandler.mBCStoneGear != null && null != (tStack1 = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, new ItemStack(aItem, 1, aMeta), null, new ItemStack(aItem, 1, aMeta), GT_ModHandler.mBCStoneGear, new ItemStack(aItem, 1, aMeta), null, new ItemStack(aItem, 1, aMeta), null})))
    		|| null != (tStack1 = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, new ItemStack(aItem, 1, aMeta), null, new ItemStack(aItem, 1, aMeta), new ItemStack(Item.ingotIron, 1), new ItemStack(aItem, 1, aMeta), null, new ItemStack(aItem, 1, aMeta), null}))
    		|| null != (tStack1 = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, new ItemStack(aItem, 1, aMeta), null, new ItemStack(aItem, 1, aMeta), new ItemStack(Block.cobblestone, 1), new ItemStack(aItem, 1, aMeta), null, new ItemStack(aItem, 1, aMeta), null}))) {
    		
    		GregTech_API.addCNCRecipe(new ItemStack(aItem, 4, aMeta), tStack1, 800, 1);
    		
    		if (GT_ModHandler.mBCStoneGear==null) {
    			tIterator1 = GT_OreDictUnificator.getOres("stoneCobble").iterator();
	    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 4, aMeta), tStack1, 1600, 2);
    		} else {
    			GregTech_API.addAssemblerRecipe(GT_ModHandler.mBCStoneGear, new ItemStack(aItem, 4, aMeta), tStack1, 1600, 2);
    		}
    	}
    	
    	if (aEventName.equals("ingotUranium")) {
    		GregTech_API.addCannerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getEmptyCell(1), GT_ModHandler.getIC2Item("reactorUraniumSimple", 1), null, 100, 2);
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustUranium", null, 1), null, 0, false);
    	}
    	else if (aEventName.equals("ingotPlutonium")) {
    		GregTech_API.addCannerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getEmptyCell(1), GregTech_API.getGregTechItem(51, 1, 0), null, 100, 2);
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustPlutonium", null, 1), null, 0, false);
	    }
    	else if (aEventName.equals("ingotThorium")) {
    		GregTech_API.addCannerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getEmptyCell(1), GregTech_API.getGregTechItem(48, 1, 0), null, 100, 2);
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustThorium", null, 1), null, 0, false);
	    }
    	else if (aEventName.equals("ingotQuicksilver")) {
    		System.err.println("'ingotQuickSilver'?, Don't tell me there is an Armor made of that highly toxic and very likely to be melting Material!"); 
    		GT_ModHandler.addDustToIngotSmeltingRecipe(GT_OreDictUnificator.get("dustCinnabar", null, 1), new ItemStack(aItem, 1, aMeta));
    	}
	    else if (aEventName.equals("ingotManganese")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(GT_MetaItem_Dust.instance.getStack(12, 1), new ItemStack(aItem, 1, aMeta));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustManganese", null, 1), null, 0, false);
	    }
    	else if (aEventName.equals("ingotMagnesium")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(GT_MetaItem_Dust.instance.getStack(13, 1), new ItemStack(aItem, 1, aMeta));
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustMagnesium", null, 1), null, 0, false);
    	}
    	else if (aEventName.equals("ingotTungstenSteel")) {
    		
    	}
	    else if (aEventName.equals("ingotInvar")) {
	    	
	    }
	    else if (aEventName.equals("ingotHepatizon")) {
	    	
	    }
	    else if (aEventName.equals("ingotDamascus Steel")) {
	    	
	    }
	    else if (aEventName.equals("ingotAngmallen")) {
	    	
	    }
	    else if (aEventName.equals("ingotEximite")) {
	    	
	    }
	    else if (aEventName.equals("ingotMeutoite")) {
	    	
	    }
	    else if (aEventName.equals("ingotDesichalkos")) {
	    	
	    }
	    else if (aEventName.equals("ingotPrometheum")) {
	    	
	    }
	    else if (aEventName.equals("ingotDeep Iron")) {
	    	
	    }
	    else if (aEventName.equals("ingotInfuscolium")) {
	    	
	    }
	    else if (aEventName.equals("ingotOureclase")) {
	    	
	    }
	    else if (aEventName.equals("ingotAredrite")) {
	    	
	    }
	    else if (aEventName.equals("ingotAstral Silver")) {
	    	
	    }
	    else if (aEventName.equals("ingotCarmot")) {
	    	
	    }
	    else if (aEventName.equals("ingotAmordrine")) {
	    	
	    }
	    else if (aEventName.equals("ingotMithril")) {
	    	
	    }
	    else if (aEventName.equals("ingotRubracium")) {
	    	
	    }
	    else if (aEventName.equals("ingotOrichalcum")) {
	    	
	    }
	    else if (aEventName.equals("ingotAdamantine")) {
	    	
	    }
	    else if (aEventName.equals("ingotAtlarus")) {
	    	
	    }
	    else if (aEventName.equals("ingotBlack Steel")) {
	    	
	    }
	    else if (aEventName.equals("ingotHaderoth")) {
	    	
	    }
	    else if (aEventName.equals("ingotCelenegil")) {
	    	
	    }
	    else if (aEventName.equals("ingotTartarite")) {
	    	
	    }
	    else if (aEventName.equals("ingotIgnatius")) {
	    	
	    }
	    else if (aEventName.equals("ingotShadow Iron")) {
	    	
	    }
	    else if (aEventName.equals("ingotMidasium")) {
	    	
	    }
	    else if (aEventName.equals("ingotVyroxeres")) {
	    	
	    }
	    else if (aEventName.equals("ingotCeruclase")) {
	    	
	    }
	    else if (aEventName.equals("ingotKalendrite")) {
	    	
	    }
	    else if (aEventName.equals("ingotVulcanite")) {
	    	
	    }
	    else if (aEventName.equals("ingotSanguinite")) {
	    	
	    }
	    else if (aEventName.equals("ingotLemurite")) {
	    	
	    }
	    else if (aEventName.equals("ingotAdluorite")) {
	    	
	    }
	    else if (aEventName.equals("ingotShadow Steel")) {
	    	
	    }
	    else if (aEventName.equals("ingotInolashite")) {
	    	
	    }
	    else if (aEventName.equals("ingotAmordrine")) {
	    	
	    }
	    else if (aEventName.equals("ingotLivingMetal")) {
	    	
	    }
    	else if (aEventName.equals("ingotQuartz")) {
    		
    	}
    	else if (aEventName.equals("ingotGraphit")) {
    		
    	}
    	else if (aEventName.equals("ingotDuraluminum")) {
    		
    	}
    	else if (aEventName.equals("ingotIridium")) {
    		
    	}
    	else if (aEventName.equals("ingotOsmium")) {
    		
    	}
    	else if (aEventName.equals("ingotRefinedObsidian")) {
    		
    	}
    	else if (aEventName.equals("ingotRefinedGlowstone")) {
    		
    	}
    	else if (aEventName.equals("ingotSilver")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustSilver", null, 1), null, 0, false);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotGold").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotElectrum", null, 2), 100, 16);
    	}
    	else if (aEventName.equals("ingotBronze")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustBronze", null, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotCopper")) {
            GregTech_API.addWiremillRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("copperCableItem", 3), 100, 2);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotTin").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack(aItem, 3, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_OreDictUnificator.get("ingotBronze", null, 2), 100, 16);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotZinc").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack(aItem, 3, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_OreDictUnificator.get("ingotBrass", null, 4), 200, 16);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotZinc").iterator();
    		while (tIterator1.hasNext()) GT_ModHandler.addInductionSmelterRecipe(new ItemStack(aItem, 3, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_OreDictUnificator.get("ingotBrass", null, 4), null, 400, 0);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustCopper", null, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotTin")) {
    		GregTech_API.addWiremillRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("tinCableItem", 4), 150, 1);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotCopper").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 3, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotBronze", null, 2), 100, 16);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustTin", null, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotRefinedIron")) {
    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), null, GT_ModHandler.getIC2Item("machine", 1), 400, 8);
    		GregTech_API.addWiremillRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("ironCableItem", 6), 200, 2);
    		tIterator1 = GT_OreDictUnificator.getOres("dustCoal").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 2, tStack1.getItemDamage()), GT_OreDictUnificator.get("ingotSteel", null, 1), GT_MetaItem_Dust.instance.getStack(63, 2), 500, 128, 1000);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustRefinedIron", GT_OreDictUnificator.get("dustIron", null, 1), 1), null, 0, true);
        }
    	else if (aEventName.equals("ingotIron")) {
    		tIterator1 = GT_OreDictUnificator.getOres("dustRedstone").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 4, aMeta), tIterator1.next().copy().splitStack(1), new ItemStack(Item.compass, 1), 400, 4);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotNickel").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack(aItem, 2, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_OreDictUnificator.get("ingotInvar", null, 3), 150, 16);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustIron", null, 1), null, 0, false);
    		GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotRefinedIron", null, 1));
    		GT_ModHandler.addInductionSmelterRecipe(new ItemStack(aItem, 2, aMeta), new ItemStack(Block.sand, 1, 0), GT_OreDictUnificator.get("ingotRefinedIron", null, 2), GT_ModHandler.getTEItem("slag", 1), 400, 25);
    	}
    	else if (aEventName.equals("ingotGold")) {
    		GregTech_API.addAssemblerRecipe(GT_ModHandler.mBCIronGear, new ItemStack(aItem, 4, aMeta), GT_ModHandler.mBCGoldGear, 800, 1);
    		tIterator1 = GT_OreDictUnificator.getOres("dustRedstone").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 4, aMeta), tIterator1.next().copy().splitStack(1), new ItemStack(Item.pocketSundial, 1), 400, 4);
    		GregTech_API.addWiremillRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("goldCableItem", 6), 200, 1);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotSilver").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_OreDictUnificator.get("ingotElectrum", null, 2), 100, 16);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustGold", null, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotAluminium")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(18, 1), null, 0, false);
    		GT_ModHandler.removeFurnaceSmelting(null, new ItemStack(aItem, 1, aMeta));
	    }
    	else if (aEventName.equals("ingotTitanium")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(19, 1), null, 0, false);
    		GT_ModHandler.removeFurnaceSmelting(null, new ItemStack(aItem, 1, aMeta));
    	}
    	else if (aEventName.equals("ingotChrome")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(20, 1), null, 0, false);
    		GT_ModHandler.removeFurnaceSmelting(null, new ItemStack(aItem, 1, aMeta));
	    }
    	else if (aEventName.equals("ingotElectrum")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(21, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotTungsten")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(22, 1), null, 0, false);
    		GT_ModHandler.removeFurnaceSmelting(null, new ItemStack(aItem, 1, aMeta));
    		tIterator1 = GT_OreDictUnificator.getOres("ingotSteel").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), GT_MetaItem_Material.instance.getStack(5, 2), GT_MetaItem_Dust.instance.getStack(63, 4), 2000, 128, 3000);
    	}
    	else if (aEventName.equals("ingotSteel")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(26, 1), null, 0, false);
    		GT_ModHandler.removeFurnaceSmelting(null, new ItemStack(aItem, 1, aMeta));
    		tIterator1 = GT_OreDictUnificator.getOres("ingotTungsten").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addBlastRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(5, 2), GT_MetaItem_Dust.instance.getStack(63, 4), 2000, 128, 3000);
    	}
    	else if (aEventName.equals("ingotLead")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(23, 1), null, 0, false);
    		tIterator1 = GT_OreDictUnificator.getOres("dustObsidian").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 2, tStack1.getItemDamage()), GT_ModHandler.getTEItem("hardenedGlass", 2), 400, 16);
	    }
    	else if (aEventName.equals("ingotZinc")) {
    		tIterator1 = GT_OreDictUnificator.getOres("ingotCopper").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 3, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotBrass", null, 4), 200, 16);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotCopper").iterator();
    		while (tIterator1.hasNext()) GT_ModHandler.addInductionSmelterRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 3, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotBrass", null, 4), null, 400, 0);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(24, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotBrass")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(25, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotPlatinum")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(27, 1), null, 0, false);
        }
    	else if (aEventName.equals("ingotNickel")) {
    		tIterator1 = GT_OreDictUnificator.getOres("ingotIron").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 2, tStack1.getItemDamage()), new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotInvar", null, 3), 150, 16);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(28, 1), null, 0, false);
        }
    	else {
			System.out.println("Ingot Name: " + aEvent.Name + " !!!Unknown Ingot detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
    	}
    }
    
    private void registerDustRecipes(Item aItem, int aMeta, String aEventName, OreDictionary.OreRegisterEvent aEvent) {
    	Iterator<ItemStack> tIterator1;
    	ItemStack tStack1, tStack2, tStack3;
		if (aEventName.startsWith("dustSmall")) {
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(aItem, 1, aMeta));
    		GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("dustSmall", "dust"), 1), new Object[] {aEventName, aEventName, aEventName, aEventName});
    		GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.getFirstOre(aEventName, 4), new Object[] {(aEventName.equals("dustSmallGunpowder")?new ItemStack(Item.gunpowder, 1, 0):aEventName.replaceFirst("dustSmall", "dust"))});
    	}
    	else if (aEventName.startsWith("dustDirty")) {
    		
    	}
    	else if (aEventName.equals("dustWood")) {
            GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 8, aMeta), GT_MetaItem_Material.instance.getStack(15, 1));
    	}
    	else if (aEventName.equals("dustSteel")) {
            GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), null, GT_MetaItem_Material.instance.getStack(26, 1), null,  100, 128, 1000);
	    	GT_ModHandler.addRCBlastFurnaceRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(26, 1), 1000);
	    	if (GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "steel", true)) {
	    		if (FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(aItem, 1, aMeta)) != null)
	    			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get(new ItemStack(aItem, 1, aMeta)));
	    	} else {
	    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotSteel", null, 1));
	    	}
	    }
    	else if (aEventName.equals("dustChrome")) {
            GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), null, GT_MetaItem_Material.instance.getStack(20, 1), null,  800, 128, 1700);
	    	if (GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "chrome", true)) {
	    		if (FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(aItem, 1, aMeta)) != null)
	    			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get(new ItemStack(aItem, 1, aMeta)));
	    	} else {
	    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotChrome", null, 1));
	    	}
	    }
    	else if (aEventName.equals("dustTitanium")) {
            GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), null, GT_MetaItem_Material.instance.getStack(19, 1), null, 1000, 128, 1500);
	    	if (GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "titanium", true)) {
	    		if (FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(aItem, 1, aMeta)) != null)
	    			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get(new ItemStack(aItem, 1, aMeta)));
	    	} else {
	    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotTitanium", null, 1));
	    	}
    	}
    	else if (aEventName.equals("dustAluminium") || aEventName.equals("dustAluminum")) {
            GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), null, GT_MetaItem_Material.instance.getStack(18, 1), null,  200, 128, 1700);
	    	GT_ModHandler.addRCBlastFurnaceRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(18, 1), 1000);
	    	if (GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "aluminium", true)) {
	    		if (FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(aItem, 1, aMeta)) != null)
	    			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get(new ItemStack(aItem, 1, aMeta)));
	    	} else {
	    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotAluminium", null, 1));
	    	}
    	}
	    else if (aEventName.equals("dustTungsten")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 50000);
	        GregTech_API.addBlastRecipe(new ItemStack(aItem, 1, aMeta), null, GT_MetaItem_Material.instance.getStack(22, 1), null, 2000, 128, 2500);
	    	GregTech_API.addCannerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getEmptyCell(1), GT_MetaItem_Cell.instance.getStack(4, 1), null, 100, 1);
	    	if (GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "tungsten", true)) {
	    		if (FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(aItem, 1, aMeta)) != null)
	    			GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get(new ItemStack(aItem, 1, aMeta)));
	    	} else {
	    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotTungsten", null, 1));
	    	}
	    }
    	else if (aEventName.equals("dustSaltpeter")) {
        	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 10, aMeta), 7, GT_MetaItem_Cell.instance.getStack(14, 2), GT_MetaItem_Cell.instance.getStack(15, 2), null, GT_ModHandler.getIC2Item("airCell", 3), 50, 110);
    	}
    	else if (aEventName.equals("dustRedrock") || aEventName.equals("dustRedRock")) {
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 4, aMeta), 0, GT_MetaItem_Dust.instance.getStack(4, 2), GT_MetaItem_Dust.instance.getStack(7, 1), GT_OreDictUnificator.get("dustClay", null, 1), null, 100);	
		}
    	else if (aEventName.equals("dustMarble")) {
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 8, aMeta), 0, GT_MetaItem_Dust.instance.getStack(13, 1), GT_MetaItem_Dust.instance.getStack(4, 7), null, null, 1055);	
		}
    	else if (aEventName.equals("dustBasalt")) {
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), 0, GT_MetaItem_Dust.instance.getStack(37, 1), GT_MetaItem_Dust.instance.getStack(4, 3), GT_MetaItem_Dust.instance.getStack(7, 8), GT_MetaItem_Dust.instance.getStack(63, 4), 2040);	
		}
    	else if (aEventName.equals("dustLapis") || aEventName.equals("dustLapisLazuli")) {
    	    GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 4, aMeta), 0, GT_MetaItem_Dust.instance.getStack(2, 3), GT_MetaItem_SmallDust.instance.getStack(3, 1), GT_MetaItem_SmallDust.instance.getStack(4, 1), GT_MetaItem_SmallDust.instance.getStack(5, 2), 1500);
    	}
    	else if (aEventName.equals("dustSulfur")) {
    		GregTech_API.addCannerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getEmptyCell(1), GT_MetaItem_Cell.instance.getStack(36, 1), null, 100, 1);
    	}
    	else if (aEventName.equals("dustObsidian")) {
    		GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 4, aMeta), 3, GT_MetaItem_SmallDust.instance.getStack(13, 2), GT_OreDictUnificator.get("dustSmallIron", null, 2), GT_MetaItem_Cell.instance.getStack(7, 1), GT_ModHandler.getIC2Item("airCell", 2), 500, 5);
    		tIterator1 = GT_OreDictUnificator.getOres("dustLead").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 2, aMeta), GT_ModHandler.getTEItem("hardenedGlass", 2), 400, 16);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotLead").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 2, aMeta), GT_ModHandler.getTEItem("hardenedGlass", 2), 400, 16);
    	}
    	else if (aEventName.equals("dustRefinedObsidian")) {
    		
    	}
    	else if (aEventName.equals("dustRefinedGlowstone")) {
    		
    	}
    	else if (aEventName.equals("dustNetherQuartz")) {
    		
    	}
    	else if (aEventName.equals("dustNikolite")) {
	    	GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 5000);
    	}
    	else if (aEventName.equals("dustInvar")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotInvar", null, 1));
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 3, aMeta), 0, GT_OreDictUnificator.get("dustIron", null, 2), GT_OreDictUnificator.get("dustNickel", null, 1), null, null, 1000);
	    }
    	else if (aEventName.equals("dustMagnesium")) {
    		
    	}
	    else if (aEventName.equals("dustManganese")) {
	    	GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 5000);
	    }
	    else if (aEventName.equals("dustHepatizon")) {
	    	
	    }
	    else if (aEventName.equals("dustDamascus Steel")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_MetaItem_Dust.instance.getStack(26, 1), null, null, null, 960);	
		}
	    else if (aEventName.equals("dustAngmallen")) {
	    	
	    }
	    else if (aEventName.equals("dustEximite")) {
	    	
	    }
	    else if (aEventName.equals("dustMeutoite")) {
	    	
	    }
	    else if (aEventName.equals("dustDesichalkos")) {
	    	
	    }
	    else if (aEventName.equals("dustPrometheum")) {
	    	
	    }
	    else if (aEventName.equals("dustDeep Iron")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_MetaItem_Dust.instance.getStack(26, 1), null, null, null, 960);	
		}
	    else if (aEventName.equals("dustInfuscolium")) {
	    	
	    }
	    else if (aEventName.equals("dustOureclase")) {
	    	
	    }
	    else if (aEventName.equals("dustAredrite")) {
	    	
	    }
	    else if (aEventName.equals("dustAstral Silver")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_OreDictUnificator.get("dustSilver", null, 1), null, null, null, 960);	
	    }
	    else if (aEventName.equals("dustCarmot")) {
	    	
	    }
	    else if (aEventName.equals("dustAmordrine")) {
	    	
	    }
	    else if (aEventName.equals("dustMithril")) {
	    	
	    }
	    else if (aEventName.equals("dustRubracium")) {
	    	
	    }
	    else if (aEventName.equals("dustOrichalcum")) {
	    	
	    }
	    else if (aEventName.equals("dustAdamantine")) {
	    	
	    }
	    else if (aEventName.equals("dustAtlarus")) {
	    	
	    }
	    else if (aEventName.equals("dustBlack Steel")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_MetaItem_Dust.instance.getStack(26, 1), null, null, null, 960);	
		}
	    else if (aEventName.equals("dustHaderoth")) {
	    	
	    }
	    else if (aEventName.equals("dustCelenegil")) {
	    	
	    }
	    else if (aEventName.equals("dustTartarite")) {
	    	
	    }
	    else if (aEventName.equals("dustIgnatius")) {
	    	
	    }
	    else if (aEventName.equals("dustShadow Iron")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_MetaItem_Dust.instance.getStack(26, 1), null, null, null, 960);	
		}
	    else if (aEventName.equals("dustMidasium")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_OreDictUnificator.get("dustGold", null, 1), null, null, null, 960);	
		}
	    else if (aEventName.equals("dustVyroxeres")) {
	    	
	    }
	    else if (aEventName.equals("dustCeruclase")) {
	    	
	    }
	    else if (aEventName.equals("dustKalendrite")) {
	    	
	    }
	    else if (aEventName.equals("dustVulcanite")) {
	    	
	    }
	    else if (aEventName.equals("dustSanguinite")) {
	    	
	    }
	    else if (aEventName.equals("dustLemurite")) {
	    	
	    }
	    else if (aEventName.equals("dustAdluorite")) {
	    	
	    }
	    else if (aEventName.equals("dustShadow Steel")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_MetaItem_Dust.instance.getStack(26, 1), null, null, null, 960);	
		}
	    else if (aEventName.equals("dustInolashite")) {
	    	
	    }
	    else if (aEventName.equals("dustGunpowder")) {
    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 4, aMeta), new ItemStack(Block.sand, 4, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.tnt, 1), 400, 1);
	    }
	    else if (aEventName.equals("dustRedstone")) {
	    	tIterator1 = GT_OreDictUnificator.getOres("dustGlowstone").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(4), new ItemStack(aItem, 4, aMeta), new ItemStack(Block.redstoneLampIdle, 1), 400, 1);
	    	tIterator1 = GT_OreDictUnificator.getOres("stickWood").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 1, aMeta), new ItemStack(Block.torchRedstoneActive, 1), 400, 1);
	    	tIterator1 = GT_OreDictUnificator.getOres("ingotIron").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(4), new ItemStack(aItem, 1, aMeta), new ItemStack(Item.compass, 1), 400, 4);
	    	tIterator1 = GT_OreDictUnificator.getOres("ingotGold").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(4), new ItemStack(aItem, 1, aMeta), new ItemStack(Item.pocketSundial, 1), 400, 4);
	    	tIterator1 = GT_OreDictUnificator.getOres("plankWood").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(8), new ItemStack(aItem, 1, aMeta), new ItemStack(Block.music, 1), 800, 1);
    		GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 5000);
		    GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 10, aMeta), 4, GT_MetaItem_Cell.instance.getStack(7, 1), GT_MetaItem_Dust.instance.getStack(3, 5), GT_MetaItem_Dust.instance.getStack(32, 1), GT_MetaItem_Cell.instance.getStack(16, 3), 7000);
	    }
	    else if (aEventName.equals("dustGlowstone")) {
	    	tIterator1 = GT_OreDictUnificator.getOres("dustRedstone").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 4, aMeta), tIterator1.next().copy().splitStack(4), new ItemStack(Block.redstoneLampIdle, 1), 400, 1);
	    	GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 25000);
    		tIterator1 = GT_OreDictUnificator.getOres("itemLazurite").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(1), new ItemStack(aItem, 1, aMeta), GT_MetaItem_Component.instance.getStack(24, 2), 800, 2);
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), 1, new ItemStack(Item.redstone, 8), GT_OreDictUnificator.get("dustGold", null, 8), GT_MetaItem_Cell.instance.getStack(3, 1), null, 25000);
	    }
    	else if (aEventName.equals("dustClay")) {
    		GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 8, aMeta), 5, GT_MetaItem_Cell.instance.getStack(5, 1), GT_MetaItem_Cell.instance.getStack(7, 2), GT_MetaItem_Dust.instance.getStack(18, 2), GT_MetaItem_Cell.instance.getStack(12, 2), 200, 50);
    	}
    	else if (aEventName.equals("dustBronze")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotBronze", null, 1));
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "bronzeingotcrafting", true) || GT_ModHandler.getRecipeOutput(new ItemStack[] {GT_OreDictUnificator.get("ingotCopper", null, 1), GT_OreDictUnificator.get("ingotCopper", null, 1), null, GT_OreDictUnificator.get("ingotCopper", null, 1), GT_OreDictUnificator.get("ingotTin", null, 1), null, null, null, null})==null?1:2), aMeta), 0, GT_MetaItem_SmallDust.instance.getStack(243, 6), GT_MetaItem_SmallDust.instance.getStack(244, 2), null, null, 1500);
    	}
    	else if (aEventName.equals("dustBrass")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotBrass", null, 1));
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "bronzeingotcrafting", true) || GT_ModHandler.getRecipeOutput(new ItemStack[] {GT_OreDictUnificator.get("ingotCopper", null, 1), GT_OreDictUnificator.get("ingotCopper", null, 1), null, GT_OreDictUnificator.get("ingotCopper", null, 1), GT_OreDictUnificator.get("ingotTin", null, 1), null, null, null, null})==null?1:2), aMeta), 0, GT_MetaItem_SmallDust.instance.getStack(243, 3), GT_MetaItem_SmallDust.instance.getStack( 24, 1), null, null, 1500);
    	}
    	else if (aEventName.equals("dustCopper")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotCopper", null, 1));
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 3, aMeta), 0, GT_MetaItem_SmallDust.instance.getStack(242, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), null, null, 2400);
    	}
    	else if (aEventName.equals("dustGold")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotGold", null, 1));
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 3, aMeta), 0, GT_MetaItem_SmallDust.instance.getStack(243, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), null, null, 2400);
    	}
	    else if (aEventName.equals("dustNickel")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotNickel", null, 1));
        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 3, aMeta), 0, GT_MetaItem_SmallDust.instance.getStack(241, 1), GT_MetaItem_SmallDust.instance.getStack(242, 1), GT_MetaItem_SmallDust.instance.getStack(243, 1), null, 3450);
	    }
    	else if (aEventName.equals("dustIron")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotIron", null, 1));
        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 2, aMeta), 0, GT_MetaItem_SmallDust.instance.getStack(244, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), null, null, 1500);
        }
    	else if (aEventName.equals("dustRefinedIron")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotRefinedIron", null, 1));
    		GT_ModHandler.addExtractionRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("dustIron", null, 1));
        }
	    else if (aEventName.equals("dustTin")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotTin", null, 1));
        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 2, aMeta), 0, GT_MetaItem_SmallDust.instance.getStack( 24, 1), GT_MetaItem_SmallDust.instance.getStack(241, 1), null, null, 2100);
    	}
	    else if (aEventName.equals("dustZinc")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotZinc", null, 1));
        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_MetaItem_SmallDust.instance.getStack(244, 1), null, null, null, 1050);
	    }
    	else if (aEventName.equals("dustQuicksilver")) {
    		System.err.println("'dustQuickSilver'?, To melt that, you don't even need a Furnace...");
    		GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 1, GT_MetaItem_Cell.instance.getStack(16, 1), null, null, null, 100);
    	}
	    else if (aEventName.equals("dustGarnetRed")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), 0, GT_OreDictUnificator.get("dustPyrope", null, 3), GT_OreDictUnificator.get("dustAlmandine", null, 5), GT_OreDictUnificator.get("dustSpessartine", null, 8), null, 3000);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 16, GT_MetaItem_Material.instance.getStack(54, 3), GT_MetaItem_Dust.instance.getStack(63, 8));
	    }
	    else if (aEventName.equals("dustGarnetYellow")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), 0, GT_OreDictUnificator.get("dustAndradite", null, 5), GT_OreDictUnificator.get("dustGrossular", null, 8), GT_OreDictUnificator.get("dustUvarovite", null, 3), null, 3500);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 16, GT_MetaItem_Material.instance.getStack(55, 3), GT_MetaItem_Dust.instance.getStack(63, 8));
	    }
	    else if (aEventName.equals("dustPyrope")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 20, aMeta), 9, GregTech_API.getGregTechItem(1, 3, 13), GregTech_API.getGregTechItem(1, 2, 18), GregTech_API.getGregTechItem(2, 3, 7), GT_ModHandler.getIC2Item("airCell", 6), 1790, 50);
	    }
	    else if (aEventName.equals("dustAlmandine")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 20, aMeta), 9, GT_OreDictUnificator.get("dustIron", null, 3), GregTech_API.getGregTechItem(1, 2, 18), GregTech_API.getGregTechItem(2, 3, 7), GT_ModHandler.getIC2Item("airCell", 6), 1640, 50);
	    }
	    else if (aEventName.equals("dustSpessartine")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 20, aMeta), 9, GregTech_API.getGregTechItem(1, 2, 18), GT_MetaItem_Dust.instance.getStack(12, 3), GregTech_API.getGregTechItem(2, 3, 7), GT_ModHandler.getIC2Item("airCell", 6), 1810, 50);
	    }
	    else if (aEventName.equals("dustAndradite")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 20, aMeta), 12, GregTech_API.getGregTechItem(2, 3, 11), GT_OreDictUnificator.get("dustIron", null, 2), GregTech_API.getGregTechItem(2, 3, 7), GT_ModHandler.getIC2Item("airCell", 6), 1280, 50);
	    }
	    else if (aEventName.equals("dustGrossular")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 20, aMeta), 12, GregTech_API.getGregTechItem(2, 3, 11), GregTech_API.getGregTechItem(1, 2, 18), GregTech_API.getGregTechItem(2, 3, 7), GT_ModHandler.getIC2Item("airCell", 6), 2050, 50);
	    }
	    else if (aEventName.equals("dustUvarovite")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 20, aMeta), 12, GregTech_API.getGregTechItem(2, 3, 11), GregTech_API.getGregTechItem(1, 2, 20), GregTech_API.getGregTechItem(2, 3, 7), GT_ModHandler.getIC2Item("airCell", 6), 2200, 50);
	    }
	    else if (aEventName.equals("dustSphalerite")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 2, aMeta), 0, GregTech_API.getGregTechItem(1, 1, 24), GregTech_API.getGregTechItem(1, 1, 8), null, null, 150, 100);
    	}
	    else if (aEventName.equals("dustCinnabar")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 2, aMeta), 1, GregTech_API.getGregTechItem(2, 1, 16), GregTech_API.getGregTechItem(1, 1, 8), null, null, 100, 128);
    	}
    	else if (aEventName.equals("dustPlatinum")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 100000);
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_OreDictUnificator.get("nuggetIridium", null, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), null, null, 3000);
    	    GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Material.instance.getStack(27, 1));
    	}
    	else if (aEventName.equals("dustIridium")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 100000);
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_OreDictUnificator.get("nuggetPlatinum", null, 1), GT_MetaItem_SmallDust.instance.getStack(28, 1), null, null, 3000);
    	    GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotIridium", null, 1));
    	}
    	else if (aEventName.equals("dustOsmium")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 200000);
    	    GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotOsmium", null, 1));
    	}
	    else if (aEventName.equals("dustLead")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotLead"	, null, 1));
        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 2, aMeta), 0, GT_OreDictUnificator.get("dustSmallSilver", null, 1), null, null, null, 2400);
        	tIterator1 = GT_OreDictUnificator.getOres("dustObsidian").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAlloySmelterRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack((tStack1 = tIterator1.next()).getItem(), 2, tStack1.getItemDamage()), GT_ModHandler.getTEItem("hardenedGlass", 2), 400, 16);
	    }
    	else if (aEventName.equals("dustSilver")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotSilver", null, 1));
        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 2, aMeta), 0, GT_OreDictUnificator.get("dustSmallLead", null, 1), null, null, null, 2400);
    	}
    	else if (aEventName.equals("dustSilverLead") || aEventName.equals("dustGalena")) {
    		GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 2, aMeta), 0, GT_OreDictUnificator.get("dustSmallSilver", null, 3), GT_OreDictUnificator.get("dustSmallLead", null, 3), GT_OreDictUnificator.get("dustSmallSulfur", null, 2), null, 1000, 120);
    		GregTech_API.addBlastRecipe(new ItemStack(aItem, 2, aMeta), null, GT_OreDictUnificator.get("ingotSilver", null, 1), GT_OreDictUnificator.get("ingotLead", null, 1), 20, 128, 1500);
    	}
    	else if (aEventName.equals("dustEnderPearl")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 50000);
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 16, aMeta), 16, GregTech_API.getGregTechItem(2, 5, 15), GregTech_API.getGregTechItem(2, 1, 10), GregTech_API.getGregTechItem(2, 4, 14), GregTech_API.getGregTechItem(2, 6, 13), 1300, 50);
	    }
	    else if (aEventName.equals("dustEnderEye")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 75000);
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 2, aMeta), 0, GregTech_API.getGregTechItem(1, 1, 0), new ItemStack(Item.blazePowder, 1), null, null, 1850);
	    }
	    else if (aEventName.equals("dustOlivine")) {
	    	GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_ModHandler.getIC2Item("advancedCircuit", 1), GT_MetaItem_Component.instance.getStack(3, 4), 6400, 8);
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 50000);
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 9, aMeta), 3, GregTech_API.getGregTechItem(1, 2, 13), GT_OreDictUnificator.get("dustIron", null, 2), GregTech_API.getGregTechItem(2, 1, 7), GT_ModHandler.getIC2Item("airCell", 2), 600, 60);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 24, GT_MetaItem_Material.instance.getStack(37, 3), GT_MetaItem_Dust.instance.getStack(63, 12));
	    }
	    else if (aEventName.equals("dustEmerald")) {
	    	GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_ModHandler.getIC2Item("advancedCircuit", 1), GT_MetaItem_Component.instance.getStack(3, 4), 6400, 8);
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 50000);
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 29, aMeta), 18, GregTech_API.getGregTechItem(1, 2, 18), GregTech_API.getGregTechItem(2, 3, 10), GregTech_API.getGregTechItem(2, 6, 7), GT_ModHandler.getIC2Item("airCell", 9), 600, 50);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 24, new ItemStack(Item.emerald, 3), GT_MetaItem_Dust.instance.getStack(63, 12));
		}
	    else if (aEventName.equals("dustDiamond")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 125000);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 32, GT_ModHandler.getIC2Item("industrialDiamond", 3), GT_MetaItem_Dust.instance.getStack(63, 16));
	    }
	    else if (aEventName.equals("dustRuby")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 50000);
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 9, aMeta), 3, GregTech_API.getGregTechItem(1, 2, 18), GregTech_API.getGregTechItem(1, 1, 20), GT_ModHandler.getIC2Item("airCell", 3), null, 500, 50);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 24, GT_OreDictUnificator.get("gemRuby", GT_MetaItem_Material.instance.getStack(32, 1), 3), GT_MetaItem_Dust.instance.getStack(63, 12));
		}
	    else if (aEventName.equals("dustSapphire")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 50000);
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 8, aMeta), 3, GregTech_API.getGregTechItem(1, 2, 18), GT_ModHandler.getIC2Item("airCell", 3), null, null, 400, 50);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 24, GT_OreDictUnificator.get("gemSapphire", GT_MetaItem_Material.instance.getStack(33, 1), 3), GT_MetaItem_Dust.instance.getStack(63, 12));
		}
	    else if (aEventName.equals("dustGreenSapphire")) {
			GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 50000);
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 1, aMeta), 0, GregTech_API.getGregTechItem(1, 1, 33), null, null, null, 100, 50);
	    	GregTech_API.addImplosionRecipe(new ItemStack(aItem, 4, aMeta), 24, GT_OreDictUnificator.get("gemGreenSapphire", GT_MetaItem_Material.instance.getStack(34, 1), 3), GT_MetaItem_Dust.instance.getStack(63, 12));
		}
	    else if (aEventName.equals("dustAsh")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 2, aMeta), 1, GregTech_API.getGregTechItem(2, 1, 8), null, null, null, 25, 50);
	    }
	    else if (aEventName.equals("dustDarkAsh")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 2, aMeta), 0, GT_MetaItem_Dust.instance.getStack(62, 1), (tStack1=GT_ModHandler.getTEItem("slag", 1))==null?GT_MetaItem_Dust.instance.getStack(62, 1):tStack1, null, null, 250);
	    }
	    else if (aEventName.equals("dustCoal")) {
	    	GT_ModHandler.addLiquidTransposerFillRecipe(new ItemStack(aItem, 1, aMeta), new LiquidStack(Block.waterStill, 125), GT_ModHandler.getIC2Item("hydratedCoalDust", 1), 125);
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 1, aMeta), 2, GregTech_API.getGregTechItem(2, 2, 8), null, null, null, 40, 50);
    		tIterator1 = GT_OreDictUnificator.getOres("ingotRefinedIron").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addBlastRecipe(new ItemStack((tStack1 = tIterator1.next()).getItem(), 1, tStack1.getItemDamage()), new ItemStack(aItem, 2, aMeta), GT_OreDictUnificator.get("ingotSteel", null, 1), GT_MetaItem_Dust.instance.getStack(63, 2), 500, 128, 1000);
	    }
	    else if (aEventName.equals("dustCharcoal")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 1, aMeta), 1, GregTech_API.getGregTechItem(2, 1, 8), null, null, null, 20, 50);
	    }
	    else if (aEventName.equals("dustUranium")) {
	    	GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 1000000);
	    	GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotUranium", null, 1));
	       	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 4, aMeta), 4, GT_ModHandler.getIC2Item("reactorUraniumSimple", 4), GT_OreDictUnificator.get("dustSmallPlutonium", 1), GT_OreDictUnificator.get("dustThorium", 2), GT_OreDictUnificator.get("dustTungsten", 1), 10000);
	    }
	    else if (aEventName.equals("dustPlutonium")) {
	    	GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta), 2000000);
	    	GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre("ingotPlutonium", 1));
	    }
	    else if (aEventName.equals("dustThorium")) {
	    	GT_ModHandler.addIC2MatterAmplifier(new ItemStack(aItem, 1, aMeta),  500000);
	    	GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.getFirstOre("ingotThorium", 1));
	    }
	    else if (aEventName.equals("dustLazurite")) {
	    	GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 8, aMeta), GT_MetaItem_Material.instance.getStack(35, 1));
	       	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 29, aMeta), 11, GregTech_API.getGregTechItem(1, 3, 18), GregTech_API.getGregTechItem(2, 3, 7), GregTech_API.getGregTechItem(2, 4, 11), GregTech_API.getGregTechItem(2, 4, 12), 1475, 100);
	    }
	    else if (aEventName.equals("dustPyrite")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 3, aMeta), 0, GT_OreDictUnificator.get("dustIron", null, 1), GT_MetaItem_Dust.instance.getStack(8, 2), null, null, 120, 128);
	    }
	    else if (aEventName.equals("dustCalcite")) {
	    	GregTech_API.addCannerRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getEmptyCell(1), GT_MetaItem_Cell.instance.getStack(33, 1), null, 100, 1);
	       	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 10, aMeta), 7, GregTech_API.getGregTechItem(2, 2, 11), GregTech_API.getGregTechItem(2, 2, 8), GT_ModHandler.getIC2Item("airCell", 3), null, 700, 80);
	    }
	    else if (aEventName.equals("dustSodalite")) {
	        GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 23, aMeta), 8, GregTech_API.getGregTechItem(2, 4, 12), GregTech_API.getGregTechItem(1, 3, 18), GregTech_API.getGregTechItem(2, 3, 7), GregTech_API.getGregTechItem(2, 1, 13), 1350, 90);
	    }
	    else if (aEventName.equals("dustNetherrack")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), 0, new ItemStack(Item.redstone, 1), GT_MetaItem_Dust.instance.getStack(8, 4), GT_OreDictUnificator.get("dustCoal", null, 1), new ItemStack(Item.goldNugget, 1), 2400);
	    }
	    else if (aEventName.equals("dustEndstone")) {
	    	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 16, aMeta), 2, GT_MetaItem_Cell.instance.getStack(6, 1), GT_MetaItem_Cell.instance.getStack(3, 1), GT_MetaItem_SmallDust.instance.getStack(22, 1), new ItemStack(Block.sand, 12), 4800);
	    }
	    else if (aEventName.equals("dustFlint")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 8, aMeta), 2, GT_MetaItem_Cell.instance.getStack(7, 1), GT_ModHandler.getIC2Item("airCell", 1), null, null, 1000, 5);
	    	GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 5, aMeta), new ItemStack(Block.tnt, 3, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("industrialTnt", 5), 800, 2);
	    }
	    else if (aEventName.equals("dustBauxite")) {
	    	GregTech_API.addElectrolyzerRecipe(new ItemStack(aItem, 12, aMeta), 8, GT_MetaItem_Dust.instance.getStack(18, 8), GT_MetaItem_SmallDust.instance.getStack(19, 2), GT_MetaItem_Cell.instance.getStack(0, 5), GT_ModHandler.getIC2Item("airCell", 3), 2000, 128);
	    }
    	else if (aEventName.equals("dustElectrum")) {
    		GT_ModHandler.addDustToIngotSmeltingRecipe(new ItemStack(aItem, 1, aMeta), GT_OreDictUnificator.get("ingotElectrum"	, null, 1));
        	GregTech_API.addCentrifugeRecipe(new ItemStack(aItem, 1, aMeta), 0, GT_OreDictUnificator.get("dustSmallGold", null, 2), GT_OreDictUnificator.get("dustSmallSilver", null, 2), null, null, 975);
    	}
    	else if (aEventName.equals("dustPhosphorus")) {
    		
    	}
    	else if (aEventName.equals("dustWheat") || aEventName.equals("dustFlour")) {
    		GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), new ItemStack(Item.bread, 1, 0));
    	}
    	else if (aEventName.equals("dustQuartz")) {
    		
    	}
    	else {
			System.out.println("Dust Name: " + aEvent.Name + " !!!Unknown Dust detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
    	}
    }
    
    private void registerGemRecipes(Item aItem, int aMeta, String aEventName, OreDictionary.OreRegisterEvent aEvent) {
    	Iterator<ItemStack> tIterator1;
    	ItemStack tStack1, tStack2, tStack3;
    	
    	if (null != (tStack1 = GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("gem", "block"), 1)))
    		if (GregTech_API.sConfiguration.addAdvConfig("StorageBlockCompressing", aEventName, true))
    			GT_ModHandler.addCompressionRecipe(new ItemStack(aItem, 9, aMeta), tStack1.copy());
		
		if (aEventName.equals("gemRuby")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(32, 1), null, 0, false);
        }
    	else if (aEventName.equals("gemSapphire")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(33, 1), null, 0, false);
        }
    	else if (aEventName.equals("gemGreenSapphire")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(34, 1), null, 0, false);
        }
    	else if (aEventName.equals("gemDiamond")) {
	    	tIterator1 = GT_OreDictUnificator.getOres("plankWood").iterator();
    		while (tIterator1.hasNext()) GregTech_API.addAssemblerRecipe(tIterator1.next().copy().splitStack(8), new ItemStack(aItem, 1, aMeta), new ItemStack(Block.jukebox, 1), 1600, 1);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(36, 1), null, 0, false);
        }
    	else if (aEventName.equals("gemEmerald")) {
	    	GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_ModHandler.getIC2Item("advancedCircuit", 1), GT_MetaItem_Component.instance.getStack(3, 4), 3200, 4);
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(35, 1), null, 0, false);
        }
    	else if (aEventName.equals("gemOlivine")) {
    		GregTech_API.addAssemblerRecipe(new ItemStack(aItem, 8, aMeta), GT_ModHandler.getIC2Item("advancedCircuit", 1), GT_MetaItem_Component.instance.getStack(3, 4), 6400, 8);
			GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(37, 1), null, 0, false);
    	}
    	else if (aEventName.equals("gemGarnet") || aEventName.equals("gemGarnetRed")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(54, 1), null, 0, false);
    	}
    	else if (aEventName.equals("gemGarnetYellow")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_MetaItem_Dust.instance.getStack(55, 1), null, 0, false);
    	}
    	else if (aEventName.equals("gemApatite")) {
    		GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), GT_ModHandler.getIC2Item("fertilizer", 4), GT_OreDictUnificator.get("dustPhosphorus", null, 1), 50, false);
    	}
    	else {
			System.out.println("Gem Name: " + aEvent.Name + " !!!Unknown Gem detected!!! Please report to GregTech Intergalactical for additional compatiblity. This is not an Error, it's just an Information.");
    	}
    }
    
    private void registerBlockRecipes(Item aItem, int aMeta, String aEventName, OreDictionary.OreRegisterEvent aEvent) {
    	ItemStack tStack1, tStack2, tStack3;
    	
		if (aItem instanceof ItemBlock && GT_Mod.sBlockStackSize < aItem.getItemStackLimit()) aItem.setMaxStackSize(GT_Mod.sBlockStackSize);
    	if (aEventName.equals("blockQuicksilver")) System.err.println("'blockQuickSilver'?, In which Ice Desert can you actually place this as a solid Block?");
    	
    	tStack1 = GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("block", "ingot"), 1);
    	tStack2 = GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("block", "gem"), 1);
    	tStack3 = GT_OreDictUnificator.getFirstOre(aEventName.replaceFirst("block", "dust"), 1);
    	
    	GT_ModHandler.removeRecipe(new ItemStack[] {new ItemStack(aItem, 1, aMeta)});
    	
		if (tStack1 != null) GT_ModHandler.removeRecipe(new ItemStack[] {tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1});
		if (tStack2 != null) GT_ModHandler.removeRecipe(new ItemStack[] {tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2});
		if (tStack3 != null) GT_ModHandler.removeRecipe(new ItemStack[] {tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3});
		
		if (GregTech_API.sConfiguration.addAdvConfig("StorageBlockCrafting", aEventName, false)) {
			if (tStack1 == null && tStack2 == null && tStack3 != null) GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.getFirstOre(aEventName, 1), new Object[] {"XXX", "XXX", "XXX", 'X', aEventName.replaceFirst("block", "dust")});
			if (tStack2 != null) GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.getFirstOre(aEventName, 1), new Object[] {"XXX", "XXX", "XXX", 'X', aEventName.replaceFirst("block", "gem")});
    		if (tStack1 != null) GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.getFirstOre(aEventName, 1), new Object[] {"XXX", "XXX", "XXX", 'X', aEventName.replaceFirst("block", "ingot")});
    	}
		
		if (tStack1 != null) tStack1.stackSize = 9;
		if (tStack2 != null) tStack2.stackSize = 9;
		if (tStack3 != null) tStack3.stackSize = 9;
		
		if (GregTech_API.sConfiguration.addAdvConfig("StorageBlockDeCrafting", aEventName, tStack2 != null)) {
    		GT_ModHandler.addShapelessCraftingRecipe(tStack3, new Object[] {aEventName});
    		GT_ModHandler.addShapelessCraftingRecipe(tStack1, new Object[] {aEventName});
    		GT_ModHandler.addShapelessCraftingRecipe(tStack2, new Object[] {aEventName});
    	}
		
	    if (tStack1 != null) GT_ModHandler.addSmeltingRecipe(new ItemStack(aItem, 1, aMeta), tStack1.copy());
	    if (tStack3 != null) GT_ModHandler.addPulverisationRecipe(new ItemStack(aItem, 1, aMeta), tStack3.copy(), null, 0, false);
    	if (tStack1 == null && tStack2 == null && tStack3 != null) GT_ModHandler.addCompressionRecipe(tStack3.copy(), GT_OreDictUnificator.getFirstOre(aEventName, 1));
    }
}
