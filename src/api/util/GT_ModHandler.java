package gregtechmod.api.util;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.ICapsuleCellContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This is the Interface I use for interacting with other Mods.
 * 
 * Due to the many imports, this File can cause compile Problems if not all the APIs are installed
 */
public class GT_ModHandler {
	public static volatile int VERSION = 303;
	
	/**
	 * These are getting assigned somewhen in the Load Phase
	 */
	public static ItemStack
			mTCFluxEssence = null,
			mTCWispEssence = null,
			mTCResource = null,
			mTCCrystal = null,
			mNuggetIron	= null,
			mNuggetCopper = null,
			mNuggetTin = null,
			mNuggetSilver = null,
			mNuggetLead	= null,
			mForcicium = null,
			mIronNugget = null,
			mSilverNugget = null,
			mTinNugget = null,
			mCopperNugget = null,
			mRuby = null,
			mGreenSapphire = null,
			mSapphire = null,
			mSilver = null,
			mTin = null,
			mCopper = null,
			mNikolite = null,
			mRedAlloy = null,
			mBlueAlloy = null,
			mBrass = null,
			mSiliconBoule = null,
			mSiliconWafer = null,
			mBlueWafer = null,
			mRedWafer = null,
			mRPTinPlate = null,
			mFineCopper = null,
			mFineIron = null,
			mCopperCoil = null,
			mBlutricMotor = null,
			mCanvas = null,
			mDiamondDrawplate = null,
			mBCWoodGear = null,
			mBCStoneGear = null,
			mBCIronGear = null,
			mBCGoldGear = null,
			mBCDiamondGear = null,
			mIC_Cell = null;
	
	public static boolean isIC2loaded() {
		return getIC2Item("resin", 1) != null;
	}
	
	public static boolean isRCloaded() {
		return getRCItem("machine.alpha.rolling.machine", 1) != null;
	}
	
	public static boolean isTEloaded() {
		return getTEItem("slag", 1) != null;
	}
	
	private static LiquidStack mSteam;
	
	/**
	 * Returns if that Liquid is Steam
	 */
	public static boolean isSteam(LiquidStack aLiquid) {
		if (aLiquid == null) return false;
		String tName = LiquidDictionary.findLiquidName(aLiquid);
		return tName != null && tName.equals("Steam");
	}
	
	/**
	 * Returns a Liquid Stack with given amount of Steam.
	 */
	public static LiquidStack getSteam(int aAmount) {
		if (mSteam == null) mSteam = LiquidDictionary.getLiquid("Steam", aAmount);
		return mSteam;
	}
	
	/**
	 * I was just really tired of always writing the same String, without being able to just use Ctrl+Space for Auto-Completing the Function
	 */
	public static ItemStack getEmptyCell(int aAmount) {
		if (mIC_Cell == null) mIC_Cell = getIC2Item("cell", 1);
		return new ItemStack(mIC_Cell.getItem(), aAmount, 0);
	}
	
	private static final Map<String, ItemStack> sIC2ItemMap = new HashMap<String, ItemStack>();
	
	/**
	 * Gets an Item from IndustrialCraft
	 */
	public static ItemStack getIC2Item(String aItem, int aAmount) {
		if (aItem == null || aItem.equals("")) return null;
		ItemStack rStack = sIC2ItemMap.get(aItem);
		if (rStack == null) try {sIC2ItemMap.put(aItem, rStack = ic2.api.item.Items.getItem(aItem));} catch (Throwable e) {}
		if (rStack == null) return null;
		rStack = rStack.copy();
		rStack.stackSize = aAmount;
		return rStack;
	}
	
	/**
	 * Gets an Item from IndustrialCraft, and returns a Replacement Item if not possible
	 */
	public static ItemStack getIC2Item(String aItem, int aAmount, ItemStack aReplacement) {
		ItemStack rStack = getIC2Item(aItem, aAmount);
		if (rStack == null) return aReplacement==null?null:aReplacement.copy().splitStack(aAmount);
		return rStack;
	}
	
	/**
	 * Gets an Item from IndustrialCraft, but the Damage Value can be specified
	 */
	public static ItemStack getIC2Item(String aItem, int aAmount, int aMeta) {
		ItemStack rStack = getIC2Item(aItem, aAmount);
		if (rStack == null) return null;
		rStack.setItemDamage(aMeta);
		return rStack;
	}
	
	/**
	 * Gets an Item from IndustrialCraft, but the Damage Value can be specified, and returns a Replacement Item with the same Damage if not possible
	 */
	public static ItemStack getIC2Item(String aItem, int aAmount, int aMeta, ItemStack aReplacement) {
		ItemStack rStack = getIC2Item(aItem, aAmount, aReplacement);
		if (rStack == null) return null;
		rStack.setItemDamage(aMeta);
		return rStack;
	}
	
	/**
	 * Gets an Item from RailCraft
	 */
	public static ItemStack getRCItem(String aItem, int aAmount) {
		if (aItem == null || aItem.equals("")) return null;
		ItemStack rStack = null;
		try {
			rStack = GameRegistry.findItemStack("Railcraft", aItem, aAmount);
		} catch (Throwable e) {}
		if (rStack == null) return null;
		rStack = rStack.copy();
		rStack.stackSize = aAmount;
		return rStack;
	}
	
	/**
	 * Gets an Item from RailCraft, and returns a Replacement Item if not possible
	 */
	public static ItemStack getRCItem(String aItem, int aAmount, ItemStack aReplacement) {
		ItemStack rStack = getRCItem(aItem, aAmount);
		if (rStack == null) return aReplacement==null?null:aReplacement.copy().splitStack(aAmount);
		return rStack;
	}
	
	/**
	 * Gets an Item from RailCraft, but the Damage Value can be specified
	 */
	public static ItemStack getRCItem(String aItem, int aAmount, int aMeta) {
		ItemStack rStack = getRCItem(aItem, aAmount);
		if (rStack == null) return null;
		rStack.setItemDamage(aMeta);
		return rStack;
	}
	
	/**
	 * Gets an Item from RailCraft, but the Damage Value can be specified, and returns a Replacement Item with the same Damage if not possible
	 */
	public static ItemStack getRCItem(String aItem, int aAmount, int aMeta, ItemStack aReplacement) {
		ItemStack rStack = getRCItem(aItem, aAmount, aReplacement);
		if (rStack == null) return null;
		rStack.setItemDamage(aMeta);
		return rStack;
	}
	
	/**
	 * Gets an Item from ThermoCraft
	 */
	public static ItemStack getTEItem(String aItem, int aAmount) {
		if (aItem == null || aItem.equals("")) return null;
		ItemStack rStack = null;
		try {
			rStack = thermalexpansion.api.item.ItemRegistry.getItem(aItem, aAmount);
		} catch (Throwable e) {}
		if (rStack == null) return null;
		rStack = rStack.copy();
		rStack.stackSize = aAmount;
		return rStack;
	}
	
	/**
	 * Gets an Item from ThermoCraft, but the Damage Value can be specified
	 */
	public static ItemStack getTEItem(String aItem, int aAmount, int aMeta) {
		ItemStack rStack = getTEItem(aItem, aAmount);
		if (rStack == null) return null;
		rStack.setItemDamage(aMeta);
		return rStack;
	}
	
	/**
	 * Gets an Item from ThermoCraft, and returns a Replacement Item if not possible
	 */
	public static ItemStack getTEItem(String aItem, int aAmount, ItemStack aReplacement) {
		ItemStack rStack = getTEItem(aItem, aAmount);
		if (rStack == null) return aReplacement==null?null:aReplacement.copy().splitStack(aAmount);
		return rStack;
	}
	
	/**
	 * Gets an Item from ThermoCraft, but the Damage Value can be specified, and returns a Replacement Item with the same Damage if not possible
	 */
	public static ItemStack getTEItem(String aItem, int aAmount, int aMeta, ItemStack aReplacement) {
		ItemStack rStack = getTEItem(aItem, aAmount, aReplacement);
		if (rStack == null) return null;
		rStack.setItemDamage(aMeta);
		return rStack;
	}
	
	/**
	 * Gets an Item from Forestry
	 */
	public static ItemStack getFRItem(String aItem, int aAmount) {
		if (aItem == null || aItem.equals("")) return null;
		ItemStack rStack = null;
		try {
			rStack = forestry.api.core.ItemInterface.getItem(aItem);
		} catch (Throwable e) {}
		if (rStack == null) return null;
		rStack = rStack.copy();
		rStack.stackSize = aAmount;
		return rStack;
	}
	
	/**
	 * Gets an Item from Forestry, but the Damage Value can be specified
	 */
	public static ItemStack getFRItem(String aItem, int aAmount, int aMeta) {
		ItemStack rStack = getFRItem(aItem, aAmount);
		if (rStack == null) return null;
		rStack.setItemDamage(aMeta);
		return rStack;
	}
	
	/**
	 * @param aValue Fuel value in EU
	 */
	public static ItemStack getFuelCan(int aValue) {
		if (aValue <= 0) return getIC2Item("fuelCan", 1);
		ItemStack rFuelCanStack = getIC2Item("filledFuelCan", 1);
		if (rFuelCanStack == null) return null;
		NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setInteger("value", aValue/5);
        rFuelCanStack.setTagCompound(tNBT);
        return rFuelCanStack;
	}
	
	/**
	 * @param aFuelCan the Item you want to check
	 * @return the exact Value in EU the Fuel Can is worth if its even a Fuel Can.
	 */
	public static int getFuelCanValue(ItemStack aFuelCan) {
		if (aFuelCan == null || !aFuelCan.isItemEqual(getIC2Item("filledFuelCan", 1))) return 0;
		NBTTagCompound tNBT = aFuelCan.getTagCompound();
		if (tNBT == null) return 0;
		return tNBT.getInteger("value")*5;
	}
	
	/**
	 * adds an RC-Boiler Fuel
	 */
	public static void addBoilerFuel(LiquidStack aLiquid, int aValue) {
		if (aLiquid == null || aValue <= 0) return;
		if (!GregTech_API.sConfiguration.addAdvConfig("boilerfuels", aLiquid.asItemStack(), true)) return;
		try {
			mods.railcraft.api.fuel.FuelManager.addBoilerFuel(aLiquid, aValue);
		} catch(Throwable e) {}
	}
    
	/**
	 * OUT OF ORDER
	 */
	public static boolean getModeKeyDown(EntityPlayer aPlayer) {
		return false;
	}
	
	/**
	 * OUT OF ORDER
	 */
	public static boolean getBoostKeyDown(EntityPlayer aPlayer) {
		return false;
	}
	
	/**
	 * OUT OF ORDER
	 */
	public static boolean getJumpKeyDown(EntityPlayer aPlayer) {
		return false;
	}
	
	/**
	 * Adds a Valuable Ore to the Miner
	 */
	public static boolean addValuableOre(int aID, int aMeta, int aValue) {
		if (aValue <= 0) return false;
		try {
			Class.forName("ic2.core.IC2").getMethod("addValuableOre", int.class, int.class, int.class).invoke(null, aID, aMeta, aValue);
		} catch (Throwable e) {}
		return true;
	}
	
	/**
	 * Adds a Scrapbox Drop. Fails at April first for the "suddenly Hoes"-Feature of IC2
	 */
	public static boolean addScrapboxDrop(float aChance, ItemStack aOutput) {
		aOutput = GT_OreDictUnificator.get(aOutput);
		if (aOutput == null || aChance <= 0) return false;
		if (GregTech_API.sConfiguration.system && !aOutput.isItemEqual(new ItemStack(Item.hoeWood, 1, 0))) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("scrapboxdrops", aOutput, true)) return false;
		try {
			ic2.api.recipe.Recipes.scrapboxDrops.addRecipe(aOutput.copy().splitStack(1), aChance);
		} catch (Throwable e) {}
		return true;
	}
	
	/**
	 * Adds an Item to the Recycler Blacklist
	 */
	public static boolean addToRecyclerBlackList(ItemStack aRecycledStack) {
		if (aRecycledStack == null) return false;		
		try {
			ic2.api.recipe.Recipes.recyclerBlacklist.add(aRecycledStack.copy());
		} catch (Throwable e) {}
		return true;
	}
	
	/**
	 * Just simple Furnace smelting. Unbelievable how Minecraft fails at making a simple ItemStack->ItemStack mapping...
	 */
	public static boolean addSmeltingRecipe(ItemStack aInput, ItemStack aOutput) {
		aOutput = GT_OreDictUnificator.get(aOutput);
		if (aInput == null || aOutput == null) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("furnace", aInput, true)) return false;
		FurnaceRecipes.smelting().addSmelting(aInput.itemID, aInput.getItemDamage(), aOutput.copy(), 0.0F);
		return true;
	}
	
	/**
	 * Adds a Recipe to Forestrys Squeezer
	 */
	public static boolean addSqueezerRecipe(ItemStack aInput, LiquidStack aOutput, int aTime) {
		if (aInput == null || aOutput == null) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("squeezer", aInput, true)) return false;
		try {
			forestry.api.recipes.RecipeManagers.squeezerManager.addRecipe(aTime>0?aTime:100, new ItemStack[] {aInput.copy()}, aOutput);
		} catch(Throwable e) {
			return false;
		}
		return true;
	}
	
	/**
	 * LiquidTransposer Recipe for both directions
	 */
	public static boolean addLiquidTransposerRecipe(ItemStack aEmptyContainer, LiquidStack aLiquid, ItemStack aFullContainer, int aMJ) {
		aFullContainer = GT_OreDictUnificator.get(aFullContainer);
		if (aEmptyContainer == null || aFullContainer == null || aLiquid == null) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("liquidtransposer", aFullContainer, true)) return false;
		try {
			thermalexpansion.api.crafting.CraftingManagers.transposerManager.addFillRecipe(aMJ, aEmptyContainer, aFullContainer, aLiquid, true, false);
		} catch(Throwable e) {}
		return true;
	}
	
	/**
	 * LiquidTransposer Recipe for filling Containers
	 */
	public static boolean addLiquidTransposerFillRecipe(ItemStack aEmptyContainer, LiquidStack aLiquid, ItemStack aFullContainer, int aMJ) {
		aFullContainer = GT_OreDictUnificator.get(aFullContainer);
		if (aEmptyContainer == null || aFullContainer == null || aLiquid == null) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("liquidtransposerfilling", aFullContainer, true)) return false;
		try {
			thermalexpansion.api.crafting.CraftingManagers.transposerManager.addFillRecipe(aMJ, aEmptyContainer, aFullContainer, aLiquid, false, false);
		} catch(Throwable e) {}
		return true;
	}
	
	/**
	 * LiquidTransposer Recipe for emptying Containers
	 */
	public static boolean addLiquidTransposerEmptyRecipe(ItemStack aFullContainer, LiquidStack aLiquid, ItemStack aEmptyContainer, int aMJ) {
		aEmptyContainer = GT_OreDictUnificator.get(aEmptyContainer);
		if (aFullContainer == null || aEmptyContainer == null || aLiquid == null) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("liquidtransposeremptying", aFullContainer, true)) return false;
		try {
			thermalexpansion.api.crafting.CraftingManagers.transposerManager.addExtractionRecipe(aMJ, aFullContainer, aEmptyContainer, aLiquid, 100, false, false);
		} catch(Throwable e) {}
		return true;
	}
	
	/**
	 * IC2-Extractor Recipe. Overloads old Recipes automatically
	 */
	public static boolean addExtractionRecipe(ItemStack aInput, ItemStack aOutput) {
		aOutput = GT_OreDictUnificator.get(aOutput);
		if (aInput == null || aOutput == null) return false;
		GT_Utility.removeSimpleIC2MachineRecipe(aInput, null, getExtractorRecipeList());
		if (!GregTech_API.sConfiguration.addAdvConfig("extractor", aInput, true)) return false;
		GT_Utility.addSimpleIC2MachineRecipe(aInput, aOutput, getExtractorRecipeList());
		return true;
	}
	
	/**
	 * RC-BlastFurnace Recipes
	 */
	public static boolean addRCBlastFurnaceRecipe(ItemStack aInput, ItemStack aOutput, int aTime) {
		aOutput = GT_OreDictUnificator.get(aOutput);
		if (aInput == null || aOutput == null || aTime <= 0) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("rcblastfurnace", aInput, true)) return false;
		aInput = aInput.copy();
		aOutput = aOutput.copy();
		if (!isRCloaded()) return false;
		try {
			mods.railcraft.api.crafting.RailcraftCraftingManager.blastFurnace.addRecipe(aInput, true, false, aTime, aOutput);
		} catch (Throwable e) {}
		return true;
	}
	
	private static Map<Integer, Object> sPulverizerRecipes = new HashMap<Integer, Object>();
	
	/**
	 * @return Object that can be cast into IPulverizerRecipe
	 */
	public static Object getPulverizerRecipe(ItemStack aInput) {
		if (aInput == null) return null;
		Object tObject = sPulverizerRecipes.get(GT_Utility.stackToInt(aInput));
		if (tObject != null) {
			return tObject;
		}
		try {
    		for (thermalexpansion.api.crafting.IPulverizerRecipe tRecipe : thermalexpansion.api.crafting.CraftingManagers.pulverizerManager.getRecipeList()) {
    			if (GT_Utility.areStacksEqual(tRecipe.getInput(), aInput)) {
		    		return tRecipe;
    			}
    		}
		} catch(Throwable e) {}
		return null;
	}
	
	/**
	 * Several Pulverizer Type Recipes.
	 */
	public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aChance, boolean aOverwrite) {
		aOutput1 = GT_OreDictUnificator.get(aOutput1);
		aOutput2 = GT_OreDictUnificator.get(aOutput2);
		if (aInput == null || aOutput1 == null) return false;
		GT_Utility.removeSimpleIC2MachineRecipe(aInput, null, getMaceratorRecipeList());
		if (!GregTech_API.sConfiguration.addAdvConfig("maceration", aInput, true)) return false;
		GT_Utility.addSimpleIC2MachineRecipe(aInput, aOutput1, getMaceratorRecipeList());
		if (!GT_OreDictUnificator.isItemStackInstanceOf(aOutput1, "dustWood", false) && !GT_OreDictUnificator.isItemStackInstanceOf(aOutput1, "dustWood", false)) {
			try {
				if (GT_OreDictUnificator.isItemStackInstanceOf(aInput, "ingot", true)) {
					appeng.api.Util.getGrinderRecipeManage().addRecipe(aInput, aOutput1, 5);
				}
			} catch(Throwable e) {}
			try {
				if (aInput.itemID != Block.obsidian.blockID) {
					mods.railcraft.api.crafting.IRockCrusherRecipe tRecipe = mods.railcraft.api.crafting.RailcraftCraftingManager.rockCrusher.createNewRecipe(aInput.copy().splitStack(1), true, false);
					tRecipe.addOutput(aOutput1.copy(), 1.0F/aInput.stackSize);
					tRecipe.addOutput(aOutput2.copy(), (0.01F*(aChance<=0?10:aChance))/aInput.stackSize);
				}
			} catch(Throwable e) {}
			try {
				if (aOutput2 == null) {
					thermalexpansion.api.crafting.CraftingManagers.pulverizerManager.addRecipe(400, aInput.copy(), aOutput1.copy(), aOverwrite);
				} else {
					
					thermalexpansion.api.crafting.CraftingManagers.pulverizerManager.addRecipe(400, aInput.copy(), aOutput1.copy(), aOutput2.copy(), aChance<=0?10:aChance, aOverwrite);
				}
			} catch(Throwable e) {}
			try {
				sPulverizerRecipes.put(GT_Utility.stackToInt(aInput), new gregtechmod.api.util.GT_PulverizerRecipe(aInput, aOutput1, aOutput2, aChance));
			} catch(Throwable e) {}
		} else {
			try {
				if (aOutput2 == null)
					thermalexpansion.api.crafting.CraftingManagers.sawmillManager.addRecipe(80, aInput.copy(), aOutput1.copy(), aOverwrite);
				else
					thermalexpansion.api.crafting.CraftingManagers.sawmillManager.addRecipe(80, aInput.copy(), aOutput1.copy(), aOutput2.copy(), aChance<=0?10:aChance, aOverwrite);
			} catch(Throwable e) {}
		}
		return true;
	}
	
	/**
	 * Adds a Recipe to the Sawmills of GregTech and ThermalCraft
	 */
	public static boolean addSawmillRecipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2) {
		aOutput1 = GT_OreDictUnificator.get(aOutput1);
		aOutput2 = GT_OreDictUnificator.get(aOutput2);
		if (aInput1 == null || aOutput1 == null) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("sawmill", aInput1, true)) return false;
	    try {
	    	thermalexpansion.api.crafting.CraftingManagers.sawmillManager.addRecipe(160, aInput1, aOutput1, aOutput2, 100, false);
		} catch(Throwable e) {}
	    GregTech_API.addSawmillRecipe(aInput1, -1, aOutput1, aOutput2, getEmptyCell(1));
		return true;
	}
	
	/**
	 * Induction Smelter Recipes for TE
	 */
	public static boolean addInductionSmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aEnergy, int aChance) {
		aOutput1 = GT_OreDictUnificator.get(aOutput1);
		aOutput2 = GT_OreDictUnificator.get(aOutput2);
		if (aInput1 == null || aOutput1 == null) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("inductionsmelter", aOutput1, true)) return false;
	    try {
	    	thermalexpansion.api.crafting.CraftingManagers.smelterManager.addRecipe(aEnergy, aInput1, aInput2, aOutput1, aOutput2, aChance, false);
		} catch(Throwable e) {}
		return true;
	}
	
	/**
	 * Smelts dusts to Ingots
	 */
	public static boolean addDustToIngotSmeltingRecipe(ItemStack aInput, ItemStack aOutput) {
		aOutput = GT_OreDictUnificator.get(aOutput);
		if (aInput == null || aOutput == null) return false;
		try {
			new thermalexpansion.api.crafting.CraftingHelpers().addSmelterDustToIngotsRecipe(aInput, aOutput);
		} catch(Throwable e) {}
		addSmeltingRecipe(aInput, aOutput);
		return true;
	}
	
	/**
	 * Smelts Ores to Ingots
	 */
	public static boolean addOreToIngotSmeltingRecipe(ItemStack aInput, ItemStack aOutput) {
		aOutput = GT_OreDictUnificator.get(aOutput);
		if (aInput == null || aOutput == null) return false;
		try {
			new thermalexpansion.api.crafting.CraftingHelpers().addSmelterOreToIngotsRecipe(aInput, aOutput);
		} catch(Throwable e) {}
		FurnaceRecipes.smelting().addSmelting(aInput.itemID, aInput.getItemDamage(), aOutput.copy(), 0.0F);
		return true;
	}
	
	private static Map<ItemStack, ItemStack>	sExtractorRecipes	= new HashMap<ItemStack, ItemStack>();
	private static Map<ItemStack, ItemStack>	sMaceratorRecipes	= new HashMap<ItemStack, ItemStack>();
	private static Map<ItemStack, ItemStack>	sCompressorRecipes	= new HashMap<ItemStack, ItemStack>();
	private static Map<ItemStack, Integer>		sMassfabRecipes		= new HashMap<ItemStack, Integer>();
	private static Map<ItemStack, Float>		sScrapboxRecipes	= new HashMap<ItemStack, Float>();
	
	public static Map<ItemStack, ItemStack> getExtractorRecipeList() {
		try {
			return (Map<ItemStack, ItemStack>)GT_Utility.getField(ic2.api.recipe.Recipes.extractor.getClass(), "recipes").get(ic2.api.recipe.Recipes.extractor);
		} catch(Throwable e) {}
		return sExtractorRecipes;
	}
	
	public static Map<ItemStack, ItemStack> getCompressorRecipeList() {
		try {
			return (Map<ItemStack, ItemStack>)GT_Utility.getField(ic2.api.recipe.Recipes.compressor.getClass(), "recipes").get(ic2.api.recipe.Recipes.compressor);
		} catch(Throwable e) {}
		return sCompressorRecipes;
	}
	
	public static Map<ItemStack, ItemStack> getMaceratorRecipeList() {
		try {
			return (Map<ItemStack, ItemStack>)GT_Utility.getField(ic2.api.recipe.Recipes.macerator.getClass(), "recipes").get(ic2.api.recipe.Recipes.macerator);
		} catch(Throwable e) {}
		return sMaceratorRecipes;
	}
	
	public static Map<ItemStack, Integer> getMassFabricatorList() {
		try {
			return (Map<ItemStack, Integer>)GT_Utility.getField(ic2.api.recipe.Recipes.matterAmplifier.getClass(), "recipes").get(ic2.api.recipe.Recipes.matterAmplifier);
		} catch(Throwable e) {}
		return sMassfabRecipes;
	}
	
	public static Map<ItemStack, Float> getScrapboxList() {
		try {
			return (Map<ItemStack, Float>)GT_Utility.getField(ic2.api.recipe.Recipes.scrapboxDrops.getClass(), "recipes").get(ic2.api.recipe.Recipes.scrapboxDrops);
		} catch(Throwable e) {}
		return sScrapboxRecipes;
	}
	
	/**
	 * IC2-Compressor Recipe. Overloads old Recipes automatically
	 */
	public static boolean addCompressionRecipe(ItemStack aInput, ItemStack aOutput) {
		aOutput = GT_OreDictUnificator.get(aOutput);
		if (aInput == null || aOutput == null) return false;
		GT_Utility.removeSimpleIC2MachineRecipe(aInput, null, getCompressorRecipeList());
		if (!GregTech_API.sConfiguration.addAdvConfig("compression", aInput, true)) return false;
		GT_Utility.addSimpleIC2MachineRecipe(aInput, aOutput, getCompressorRecipeList());
		return true;
	}
	
	/**
	 * @param aValue Scrap = 5000, Scrapbox = 45000, Diamond Dust 125000
	 */
	public static boolean addIC2MatterAmplifier(ItemStack aAmplifier, int aValue) {
		if (aAmplifier == null || aValue <= 0) return false;
		if (!GregTech_API.sConfiguration.addAdvConfig("massfabamplifier", aAmplifier, true)) return false;
		try {
			ic2.api.recipe.Recipes.matterAmplifier.addRecipe(aAmplifier, aValue);
		} catch(Throwable e) {}
		return true;
	}
	
	/**
	 * Rolling Machine Crafting Recipe
	 */
	public static boolean addRollingMachineRecipe(ItemStack aResult, Object[] aRecipe) {
		aResult = GT_OreDictUnificator.get(aResult);
		if (aResult == null || aRecipe == null) return false;
		try {
			mods.railcraft.api.crafting.RailcraftCraftingManager.rollingMachine.getRecipeList().add(new ShapedOreRecipe(aResult.copy(), aRecipe));
		} catch(Throwable e) {
			return addCraftingRecipe(aResult.copy(), aRecipe);
		}
		return true;
	}
	
	/**
	 * Special Handler for UUM Recipes
	 */
	public static boolean addUUMRecipe(ItemStack aResult, Object... aRecipe) {
		if (GregTech_API.sConfiguration.addAdvConfig("uumrecipe", aResult, true))
			return addCraftingRecipe(aResult, aRecipe);
		else
			return addCraftingRecipe(null, aRecipe);
	}
	
	/**
	 * Regular Crafting Recipes. Deletes conflicting Recipes too.
	 */
	public static boolean addCraftingRecipe(ItemStack aResult, Object... aRecipe) {
		aResult = GT_OreDictUnificator.get(aResult);
		if (aRecipe == null) return false;
		try { while (true) {
		    String shape = "";
		    int idx = 0;
		    if (aRecipe[idx] instanceof Boolean) {
		    	idx++;
		    }
	        while (aRecipe[idx] instanceof String) {
	            String s = (String)aRecipe[idx++];
	            shape += s;
	            while (s.length() < 3) s+=" ";
	            if (s.length() > 3) throw new IllegalArgumentException();
	        }
		    if (aRecipe[idx] instanceof Boolean) {
		    	idx++;
		    }
	        HashMap<Character, ItemStack> itemMap = new HashMap<Character, ItemStack>();
	        itemMap.put(' ', null);
	        for (; idx < aRecipe.length; idx += 2) {
				if (aRecipe[idx] == null || aRecipe[idx + 1] == null) {
					if (GregTech_API.DEBUG_MODE) GT_Log.err.println("Missing Item for shaped Recipe: " + aResult.getDisplayName());
					return false;
				}
	            Character chr = (Character)aRecipe[idx];
	            Object in = aRecipe[idx + 1];
	            Object val = null;
	            if (in instanceof ItemStack) {
	                itemMap.put(chr, ((ItemStack)in).copy());
	            } else if (in instanceof String) {
	            	ItemStack tStack = GT_OreDictUnificator.getFirstOre((String)in, 1);
	            	if (tStack == null) break;
	                itemMap.put(chr, tStack);
	            } else {
	                throw new IllegalArgumentException();
	            }
	        }
	        ItemStack[] tRecipe = new ItemStack[9];
	        int x = -1;
	        for (char chr : shape.toCharArray()) {
	        	tRecipe[++x] = itemMap.get(chr);
	            if (tRecipe[x] != null)
	            	if (tRecipe[x].getItemDamage() == GregTech_API.ITEM_WILDCARD_DAMAGE)
	            		tRecipe[x].setItemDamage(0);
	            
		    }
	        removeRecipe(tRecipe);
	        break;
		}} catch(Throwable e) {e.printStackTrace(GT_Log.err);}
		
		if (aResult == null) return false;
		try {
			ic2.api.recipe.Recipes.advRecipes.addRecipe(aResult.copy(), aRecipe);
		} catch (Throwable e) {}
		return true;
	}
	
	/**
	 * Shapeless Crafting Recipes. Deletes conflicting Recipes too.
	 */
	public static boolean addShapelessCraftingRecipe(ItemStack aResult, Object... aRecipe) {
		aResult = GT_OreDictUnificator.get(aResult);
		if (aResult == null || aRecipe == null) return false;
		try {
	        ItemStack[] tRecipe = new ItemStack[9];
	        int i = 0;
			for (Object tObject : aRecipe) {
				if (tObject == null) {
					if (GregTech_API.DEBUG_MODE) GT_Log.err.println("Missing Item for shapeless Recipe: " + aResult.getDisplayName());
					return false;
				}
				if (tObject instanceof ItemStack) {
					tRecipe[i] = (ItemStack)tObject;
				} else if (tObject instanceof String) {
					tRecipe[i] = GT_OreDictUnificator.getFirstOre((String)tObject, 1);
				} else if (tObject instanceof Boolean) {
					
				} else {
	                throw new IllegalArgumentException();
				}
				i++;
			}
	        removeRecipe(tRecipe);
		} catch(Throwable e) {e.printStackTrace(GT_Log.err);}
		try {
			ic2.api.recipe.Recipes.advRecipes.addShapelessRecipe(aResult.copy(), aRecipe);
		} catch (Throwable e) {}
		return true;
	}
	
	/**
	 * OUT OF ORDER
	 */
	public static boolean removeFurnaceSmelting(ItemStack aInput, ItemStack aOutput) {
		if (aInput != null) {
			
			
			
		} else if (aOutput != null) {
			
			
			
		}
		return false;
	}
	
	/**
	 * Removes a Crafting Recipe and gives you the former output of it.
	 * @param aRecipe The content of the Crafting Grid as ItemStackArray with length 9
	 * @return the output of the old Recipe or null if there was nothing.
	 */
    public static ItemStack removeRecipe(ItemStack[] aRecipe) {
    	if (aRecipe == null) return null;
    	ItemStack rReturn = null;
		InventoryCrafting aCrafting = new InventoryCrafting(new Container() {public boolean canInteractWith(EntityPlayer var1) {return false;}}, 3, 3);
		for (int i = 0; i < aRecipe.length && i < 9; i++) aCrafting.setInventorySlotContents(i, aRecipe[i]);
		ArrayList<IRecipe> tList = (ArrayList<IRecipe>)CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < tList.size(); i++) {
			if (tList.get(i).matches(aCrafting, null)) {
				rReturn = tList.get(i).getRecipeOutput();
				tList.remove(i--);
			}
		}
		return rReturn;
    }
    
	/**
	 * Removes a Crafting Recipe.
	 * @param aOutput The output of the Recipe.
	 * @return if it has removed at least one Recipe.
	 */
    public static boolean removeRecipe(ItemStack aOutput) {
    	if (aOutput == null) return false;
    	boolean rReturn = false;
    	ItemStack tStack;
		ArrayList<IRecipe> tList = (ArrayList<IRecipe>)CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < tList.size(); i++) {
			if ((tStack = tList.get(i).getRecipeOutput()) != null) {
				if (tStack.isItemEqual(aOutput)) {
					tList.remove(i--);
					rReturn = true;
				}
			}
		}
		return rReturn;
    }
    
    /**
     * Gives you a copy of the Output from a Crafting Recipe
     */
    public static ItemStack getRecipeOutput(ItemStack[] aRecipe) {
    	if (aRecipe == null) return null;
		InventoryCrafting aCrafting = new InventoryCrafting(new Container() {public boolean canInteractWith(EntityPlayer var1) {return false;}}, 3, 3);
		for (int i = 0; i < 9 && i < aRecipe.length; i++) aCrafting.setInventorySlotContents(i, aRecipe[i]);
		ArrayList<IRecipe> tList = (ArrayList<IRecipe>)CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < tList.size(); i++) {
			if (tList.get(i).matches(aCrafting, null)) {
				ItemStack tOutput = tList.get(i).getRecipeOutput();
				if (tOutput == null || tOutput.stackSize <= 0) {
					// Seriously, who would ever do that shit?
					throw new GT_ItsNotMyFaultException("Seems another Mod added a Crafting Recipe with null Output. Tell the Developer of said Mod to fix that.");
				} else {
					return tOutput.copy();
				}
			}
		}
		return null;
    }
    
    /**
     * Gives you a list of the Outputs from a Crafting Recipe
     * If you have multiple Mods, which add Bronze Armor for example
     */
    public static ArrayList<ItemStack> getRecipeOutputs(ItemStack[] aRecipe) {
    	if (aRecipe == null) return null;
    	ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
    	InventoryCrafting aCrafting = new InventoryCrafting(new Container() {public boolean canInteractWith(EntityPlayer var1) {return false;}}, 3, 3);
		for (int i = 0; i < 9 && i < aRecipe.length; i++) aCrafting.setInventorySlotContents(i, aRecipe[i]);
		ArrayList<IRecipe> tList = (ArrayList<IRecipe>)CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < tList.size(); i++) {
			if (tList.get(i).matches(aCrafting, null)) {
				rList.add(tList.get(i).getRecipeOutput().copy());
			}
		}
		return rList;
    }
    
    /**
     * Used in my own Macerator. Decreases StackSize of the Input if wanted.
     */
    public static ItemStack getMaceratorOutput(ItemStack aInput, boolean aRemoveInput) {
    	if (aInput == null) return null;
		for (Entry<ItemStack, ItemStack> tEntry : getMaceratorRecipeList().entrySet()) {
			if (GT_Utility.areStacksEqual(tEntry.getKey(), aInput) && tEntry.getValue() != null) {
				if (aInput.stackSize >= tEntry.getKey().stackSize) {
					if (aRemoveInput) aInput.stackSize-=tEntry.getKey().stackSize;
					return tEntry.getValue().copy();
				}
				return null;
			}
		}
    	return null;
    }
    
    /**
     * Used in my own Extractor. Decreases StackSize of the Input if wanted.
     */
    public static ItemStack getExtractorOutput(ItemStack aInput, boolean aRemoveInput) {
    	if (aInput == null) return null;
		for (Entry<ItemStack, ItemStack> tEntry : getExtractorRecipeList().entrySet()) {
			if (GT_Utility.areStacksEqual(tEntry.getKey(), aInput) && tEntry.getValue() != null) {
				if (aInput.stackSize >= tEntry.getKey().stackSize) {
					if (aRemoveInput) aInput.stackSize-=tEntry.getKey().stackSize;
					return tEntry.getValue().copy();
				}
				return null;
			}
		}
    	return null;
    }
    
    /**
     * Used in my own Compressor. Decreases StackSize of the Input if wanted.
     */
    public static ItemStack getCompressorOutput(ItemStack aInput, boolean aRemoveInput) {
    	if (aInput == null) return null;
		for (Entry<ItemStack, ItemStack> tEntry : getCompressorRecipeList().entrySet()) {
			if (GT_Utility.areStacksEqual(tEntry.getKey(), aInput) && tEntry.getValue() != null) {
				if (aInput.stackSize >= tEntry.getKey().stackSize) {
					if (aRemoveInput) aInput.stackSize-=tEntry.getKey().stackSize;
					return tEntry.getValue().copy();
				}
				return null;
			}
		}
    	return null;
    }
    
    /**
     * Used in my own Furnace.
     */
    public static ItemStack getSmeltingOutput(ItemStack aInput) {
    	if (aInput == null) return null;
    	return FurnaceRecipes.smelting().getSmeltingResult(aInput);
    }

    /**
     * Used in my own Recycler.
     */
    public static ItemStack getRecyclerOutput(ItemStack aInput, Random aRandom) {
    	if (aInput == null || aRandom == null) return null;
		try {
			if (ic2.api.recipe.Recipes.recyclerBlacklist.contains(aInput)) return null;
		} catch (Throwable e) {}
    	if (aRandom.nextInt(8) > 0) return null;
    	return getIC2Item("scrap", 1);
    }
    
    /**
     * For the Scrapboxinator
     */
	public static ItemStack getRandomScrapboxDrop(World aWorld) {
		ItemStack rStack = null;
		try {
			rStack = GT_OreDictUnificator.get((ItemStack)Class.forName("ic2.core.item.ItemScrapbox").getMethod("getDrop", World.class).invoke(null, aWorld));
		} catch (Throwable e) {}
		if (rStack == null) return getRandomScrapboxDrop(aWorld);
		return rStack;
	}
	
    /**
     * Adds TileEntity to E-net
     */
	public static boolean addTileToEnet(World aWorld, TileEntity aTileEntity) {
		try {
			if (aTileEntity instanceof ic2.api.energy.tile.IEnergyTile) {
				ic2.api.energy.EnergyNet.getForWorld(aWorld).addTileEntity(aTileEntity);
				return true;
			}
		} catch(Throwable e) {}
		return false;
	}
	
    /**
     * Removes TileEntity from E-net
     */
	public static boolean removeTileFromEnet(World aWorld, TileEntity aTileEntity) {
		try {
			if (aTileEntity instanceof ic2.api.energy.tile.IEnergyTile) {
				ic2.api.energy.EnergyNet.getForWorld(aWorld).removeTileEntity(aTileEntity);
				return true;
			}
		} catch(Throwable e) {}
		return false;
	}
	
    /**
     * Emits Energy to E-net
     * @return the remaining Energy.
     */
	public static int emitEnergyToEnet(int aAmount, World aWorld, TileEntity aTileEntity) {
		try {
			if (aTileEntity instanceof ic2.api.energy.tile.IEnergySource) return ic2.api.energy.EnergyNet.getForWorld(aWorld).emitEnergyFrom((ic2.api.energy.tile.IEnergySource)aTileEntity, aAmount);
		} catch(Throwable e) {}
		return aAmount;
	}
	
	/**
	 * Charges an Electric Item. Only if it's a valid Electric Item of course.
	 * @return the actually used Energy.
	 */
	public static int chargeElectricItem(ItemStack aStack, int aCharge, int aTier, boolean aIgnoreLimit, boolean aSimulate) {
		try {
			if (isElectricItem(aStack)) {
				return ic2.api.item.ElectricItem.charge(aStack, aCharge, aTier, aIgnoreLimit, aSimulate);
			}
		} catch (Throwable e) {}
		return 0;
	}
	
	/**
	 * Discharges an Electric Item. Only if it's a valid Electric Item for that of course.
	 * @return the Energy got from the Item.
	 */
	public static int dischargeElectricItem(ItemStack aStack, int aCharge, int aTier, boolean aIgnoreLimit, boolean aSimulate) {
		try {
			if (isElectricItem(aStack)) {
				if (((ic2.api.item.IElectricItem)aStack.getItem()).canProvideEnergy(aStack)) {
					return ic2.api.item.ElectricItem.discharge(aStack, aCharge, aTier, aIgnoreLimit, aSimulate);
				}
			}
		} catch (Throwable e) {}
		return 0;
	}
	
	/**
	 * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
	 * @return if the action was successful
	 */
	public static boolean canUseElectricItem(ItemStack aStack, int aCharge) {
		try {
			if (isElectricItem(aStack)) {
				return ic2.api.item.ElectricItem.canUse(aStack, aCharge);
			}
		} catch (Throwable e) {}
		return false;
	}
	
	/**
	 * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
	 * @return if the action was successful
	 */
	public static boolean useElectricItem(ItemStack aStack, int aCharge, EntityPlayer aPlayer) {
		try {
			if (isElectricItem(aStack)) {
				if (ic2.api.item.ElectricItem.canUse(aStack, aCharge)) {
					return ic2.api.item.ElectricItem.use(aStack, aCharge, aPlayer);
				}
			}
		} catch (Throwable e) {}
		return false;
	}
	
	/**
	 * Uses an Item. Tries to discharge in case of Electric Items
	 */
	public static boolean damageOrDechargeItem(ItemStack aStack, int aDamage, int aDecharge, EntityPlayer aPlayer) {
		if (aPlayer != null && aPlayer.capabilities.isCreativeMode) return true;
		if (aStack != null) {
			if (GT_ModHandler.isElectricItem(aStack)) {
				return GT_ModHandler.useElectricItem(aStack, 1000, aPlayer);
			} else {
				aStack.damageItem(aDamage, aPlayer);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Is this an electric Item, which can charge other Items?
	 */
	public static boolean isChargerItem(ItemStack aStack) {
		try {
			if (isElectricItem(aStack)) {
				return ((ic2.api.item.IElectricItem)aStack.getItem()).canProvideEnergy(aStack);
			}
		} catch (Throwable e) {}
		return false;
	}
	
	/**
	 * Is this an electric Item?
	 */
	public static boolean isElectricItem(ItemStack aStack) {
		try {
			return aStack != null && aStack.getItem() instanceof ic2.api.item.IElectricItem;
		} catch (Throwable e) {}
		return false;
	}
	
	
	public static int getCapsuleCellContainerCountMultipliedWithStackSize(ItemStack aStack) {
		if (aStack == null) return 0;
		return getCapsuleCellContainerCount(aStack)*aStack.stackSize;
	}
	
	public static int getCapsuleCellContainerCount(ItemStack aStack) {
		if (aStack == null) return 0;
		if (aStack.isItemEqual(getEmptyCell(1))) return 1;
		Item tItem = aStack.getItem();
		if (tItem == null) return 0;
		ItemStack tStack = null;
		if (tItem.hasContainerItem() && null != (tStack = tItem.getContainerItemStack(aStack)) && tStack.isItemEqual(getEmptyCell(1))) return tStack.stackSize;
		if (tItem instanceof ICapsuleCellContainer) return ((ICapsuleCellContainer)tItem).CapsuleCellContainerCount(aStack);
		tStack = GT_Utility.getContainerForFilledItem(aStack);
		if (tStack != null && tStack.isItemEqual(getEmptyCell(1))) return 1;
		if (tItem == getIC2Item("airCell"					, 1).getItem()) return 1;
		if (tItem == getIC2Item("waterCell"					, 1).getItem()) return 1;
		if (tItem == getIC2Item("lavaCell"					, 1).getItem()) return 1;
		if (tItem == getIC2Item("hydratedCoalCell"			, 1).getItem()) return 1;
		if (tItem == getIC2Item("coalfuelCell"				, 1).getItem()) return 1;
		if (tItem == getIC2Item("bioCell"					, 1).getItem()) return 1;
		if (tItem == getIC2Item("biofuelCell"				, 1).getItem()) return 1;
		if (tItem == getIC2Item("electrolyzedWaterCell"		, 1).getItem()) return 1;
		if (tItem == getIC2Item("nearDepletedUraniumCell"	, 1).getItem()) return 1;
		if (tItem == getIC2Item("reactorIsotopeCell"		, 1).getItem()) return 1;
		if (tItem == getIC2Item("reEnrichedUraniumCell"		, 1).getItem()) return 1;
		if (tItem == getIC2Item("hydratingCell"				, 1).getItem()) return 1;
		if (tItem == getIC2Item("reactorCoolantSimple"		, 1).getItem()) return 1;
		if (tItem == getIC2Item("reactorCoolantTriple"		, 1).getItem()) return 3;
		if (tItem == getIC2Item("reactorCoolantSix"			, 1).getItem()) return 6;
		if (tItem == getIC2Item("reactorUraniumSimple"		, 1).getItem()) return 1;
		if (tItem == getIC2Item("reactorUraniumDual"		, 1).getItem()) return 2;
		if (tItem == getIC2Item("reactorUraniumQuad"		, 1).getItem()) return 4;
		return 0;
	}
}
