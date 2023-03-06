package gregtechmod;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IMachineBlockUpdateable;
import gregtechmod.api.items.GT_Generic_Item;
import gregtechmod.api.metatileentity.BaseMetaTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_BaseCrop;
import gregtechmod.api.util.GT_CircuitryBehavior;
import gregtechmod.api.util.GT_Config;
import gregtechmod.api.util.GT_CoverBehavior;
import gregtechmod.api.util.GT_ItsNotMyFaultException;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.api.util.GT_Utility;
import gregtechmod.common.GT_ComputercubeDescription;
import gregtechmod.common.GT_ConnectionHandler;
import gregtechmod.common.GT_DummyWorld;
import gregtechmod.common.GT_OreDictHandler;
import gregtechmod.common.GT_PacketHandler;
import gregtechmod.common.GT_Proxy;
import gregtechmod.common.GT_Worldgenerator;
import gregtechmod.common.blocks.GT_BlockMetaID_Block;
import gregtechmod.common.blocks.GT_BlockMetaID_Block2;
import gregtechmod.common.blocks.GT_BlockMetaID_Machine;
import gregtechmod.common.blocks.GT_BlockMetaID_Ore;
import gregtechmod.common.blocks.GT_Block_LightSource;
import gregtechmod.common.covers.GT_Cover_ControlsWork;
import gregtechmod.common.covers.GT_Cover_Conveyor;
import gregtechmod.common.covers.GT_Cover_Crafting;
import gregtechmod.common.covers.GT_Cover_DoesWork;
import gregtechmod.common.covers.GT_Cover_Drain;
import gregtechmod.common.covers.GT_Cover_EUMeter;
import gregtechmod.common.covers.GT_Cover_EnergyOnly;
import gregtechmod.common.covers.GT_Cover_Generic;
import gregtechmod.common.covers.GT_Cover_ItemMeter;
import gregtechmod.common.covers.GT_Cover_ItemValve;
import gregtechmod.common.covers.GT_Cover_LiquidMeter;
import gregtechmod.common.covers.GT_Cover_None;
import gregtechmod.common.covers.GT_Cover_Redstone;
import gregtechmod.common.covers.GT_Cover_RedstoneOnly;
import gregtechmod.common.covers.GT_Cover_Screen;
import gregtechmod.common.covers.GT_Cover_SolarPanel;
import gregtechmod.common.covers.GT_Cover_Valve;
import gregtechmod.common.covers.GT_Cover_Vent;
import gregtechmod.common.items.GT_Crowbar_Item;
import gregtechmod.common.items.GT_Dataorb_Item;
import gregtechmod.common.items.GT_Debug_Item;
import gregtechmod.common.items.GT_Destructopack_Item;
import gregtechmod.common.items.GT_Jackhammer_Item;
import gregtechmod.common.items.GT_MetaBlock2_Item;
import gregtechmod.common.items.GT_MetaBlock_Item;
import gregtechmod.common.items.GT_MetaItem_Abstract;
import gregtechmod.common.items.GT_MetaItem_Cell;
import gregtechmod.common.items.GT_MetaItem_Component;
import gregtechmod.common.items.GT_MetaItem_Dust;
import gregtechmod.common.items.GT_MetaItem_Gas;
import gregtechmod.common.items.GT_MetaItem_Liquid;
import gregtechmod.common.items.GT_MetaItem_Material;
import gregtechmod.common.items.GT_MetaItem_Nugget;
import gregtechmod.common.items.GT_MetaItem_Plasma;
import gregtechmod.common.items.GT_MetaItem_SmallDust;
import gregtechmod.common.items.GT_MetaMachine_Item;
import gregtechmod.common.items.GT_MetaOre_Item;
import gregtechmod.common.items.GT_Mortar_Item;
import gregtechmod.common.items.GT_NeutronReflector_Item;
import gregtechmod.common.items.GT_Rockcutter_Item;
import gregtechmod.common.items.GT_Scanner_Item;
import gregtechmod.common.items.GT_Screwdriver_Item;
import gregtechmod.common.items.GT_SensorCard_Item;
import gregtechmod.common.items.GT_SensorKit_Item;
import gregtechmod.common.items.GT_Sonictron_Item;
import gregtechmod.common.items.GT_Teslastaff_Item;
import gregtechmod.common.redstonecircuits.GT_Circuit_BasicLogic;
import gregtechmod.common.redstonecircuits.GT_Circuit_BitAnd;
import gregtechmod.common.redstonecircuits.GT_Circuit_CombinationLock;
import gregtechmod.common.redstonecircuits.GT_Circuit_Pulser;
import gregtechmod.common.redstonecircuits.GT_Circuit_Randomizer;
import gregtechmod.common.redstonecircuits.GT_Circuit_RedstoneMeter;
import gregtechmod.common.redstonecircuits.GT_Circuit_Repeater;
import gregtechmod.common.redstonecircuits.GT_Circuit_Timer;
import gregtechmod.common.tileentities.GT_MetaTileEntity_AdvancedPump;
import gregtechmod.common.tileentities.GT_MetaTileEntity_AdvancedTranslocator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_AlloySmelter;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Assembler;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Barrel;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Bender;
import gregtechmod.common.tileentities.GT_MetaTileEntity_BlastFurnace;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Canner;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Centrifuge;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ChemicalReactor;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Compressor;
import gregtechmod.common.tileentities.GT_MetaTileEntity_CropHarvestor;
import gregtechmod.common.tileentities.GT_MetaTileEntity_DieselGenerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_DistillationTower;
import gregtechmod.common.tileentities.GT_MetaTileEntity_DragonEggEnergySiphon;
import gregtechmod.common.tileentities.GT_MetaTileEntity_E_Furnace;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricAutoWorkbench;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricBufferAdvanced;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricBufferLarge;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricBufferSmall;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricInventoryManager;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricItemClearer;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricRegulatorAdvanced;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricSorter;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ElectricTypeSorter;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Electrolyzer;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Extractor;
import gregtechmod.common.tileentities.GT_MetaTileEntity_FusionComputer;
import gregtechmod.common.tileentities.GT_MetaTileEntity_FusionEnergyInjector;
import gregtechmod.common.tileentities.GT_MetaTileEntity_FusionExtractor;
import gregtechmod.common.tileentities.GT_MetaTileEntity_FusionInjector;
import gregtechmod.common.tileentities.GT_MetaTileEntity_GasTurbine;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Grinder;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ImplosionCompressor;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Macerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_MachineBox;
import gregtechmod.common.tileentities.GT_MetaTileEntity_MagicEnergyAbsorber;
import gregtechmod.common.tileentities.GT_MetaTileEntity_MagicEnergyConverter;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Microwave;
import gregtechmod.common.tileentities.GT_MetaTileEntity_PlasmaGenerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Printer;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Pulverizer;
import gregtechmod.common.tileentities.GT_MetaTileEntity_QuantumChest;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Quantumtank;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Recycler;
import gregtechmod.common.tileentities.GT_MetaTileEntity_RedstoneButtonPanel;
import gregtechmod.common.tileentities.GT_MetaTileEntity_RedstoneCircuitBlock;
import gregtechmod.common.tileentities.GT_MetaTileEntity_RedstoneNoteBlock;
import gregtechmod.common.tileentities.GT_MetaTileEntity_RedstoneStrengthDisplay;
import gregtechmod.common.tileentities.GT_MetaTileEntity_RockBreaker;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Safe;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Sawmill;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Scrapboxinator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_SemifluidGenerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Shelf;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Shelf_Compartment;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Shelf_Desk;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Shelf_FileCabinet;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Shelf_Iron;
import gregtechmod.common.tileentities.GT_MetaTileEntity_ThermalGenerator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Translocator;
import gregtechmod.common.tileentities.GT_MetaTileEntity_VacuumFreezer;
import gregtechmod.common.tileentities.GT_MetaTileEntity_Wiremill;
import gregtechmod.common.tileentities.GT_TileEntityMetaID_Machine;
import gregtechmod.common.tileentities.GT_TileEntity_AESU;
import gregtechmod.common.tileentities.GT_TileEntity_ChargeOMat;
import gregtechmod.common.tileentities.GT_TileEntity_ComputerCube;
import gregtechmod.common.tileentities.GT_TileEntity_IDSU;
import gregtechmod.common.tileentities.GT_TileEntity_LESU;
import gregtechmod.common.tileentities.GT_TileEntity_LightSource;
import gregtechmod.common.tileentities.GT_TileEntity_Lightningrod;
import gregtechmod.common.tileentities.GT_TileEntity_Matterfabricator;
import gregtechmod.common.tileentities.GT_TileEntity_PlayerDetector;
import gregtechmod.common.tileentities.GT_TileEntity_Sonictron;
import gregtechmod.common.tileentities.GT_TileEntity_Supercondensator;
import gregtechmod.common.tileentities.GT_TileEntity_Superconductor;
import gregtechmod.common.tileentities.GT_TileEntity_UUMAssembler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.Mod.ServerStopping;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * @author Gregorius Techneticies
 */
@Mod(modid = "GregTech_Addon", name="GregTech-Addon", version="MC151_V3.03", useMetadata=false, dependencies="required-after:IC2; after:factorization; after:Railcraft; after:ThermalExpansion; after:ThermalExpansion|Transport; after:ThermalExpansion|Energy; after:ThermalExpansion|Factory; after:XyCraft; after:MetallurgyCore; after:MetallurgyBase; after:MetallurgyEnder; after:MetallurgyFantasy; after:MetallurgyNether; after:MetallurgyPrecious; after:MetallurgyUtility; after:BuildCraft|Silicon; after:BuildCraft|Core; after:BuildCraft|Transport; after:BuildCraft|Factory; after:BuildCraft|Energy; after:BuildCraft|Builders; after:LiquidUU; after:TwilightForest; after:Forestry; after:RedPowerCore; after:RedPowerBase; after:RedPowerMachine; after:RedPowerCompat; after:RedPowerWiring; after:RedPowerLogic; after:RedPowerLighting; after:RedPowerWorld; after:RedPowerControl; after:Tubestuff; after:ICBM; after:Mekanism; after:MekanismGenerators; after:MekanismTools;")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {"gregtech"}, packetHandler = GT_PacketHandler.class, connectionHandler = GT_ConnectionHandler.class)
public class GT_Mod {
    @Instance
    public static GT_Mod instance;
    
    @SidedProxy(clientSide = "gregtechmod.common.GT_Client", serverSide = "gregtechmod.common.GT_Proxy")
    public static GT_Proxy gregtechproxy;
    
	public static volatile int VERSION = 303;
	
    public static Random Randomizer = new Random(42);
    
    public static final GT_OreDictHandler sOreDict = new GT_OreDictHandler();
    
    public static boolean sHungerEffect = true, sUnificatorRP = false, sUnificatorTE = false, sUnificatorFR = false, sUnificatorRC = false, sUnificatorTC = false, mOnline = true, mPreLoaded = false, mLoaded = false, mPostLoaded = false, mAlreadyPlayed = false, mShowCapes = true, mDetectIDConflicts = false, mDebugMode = false, mDoNotInit = false, mSeedscanner = true, mReactorplanner = true;
    
	public static int sUpgradeCount = 4, sTincellCount = 8, sBarrelItemCount = 32768, sQuantumItemCount = 2000000000, sBlockStackSize = 64, sOreStackSize = 64, sWoodStackSize = 64, sPlankStackSize = 64;
    
    public static final int[] sItemIDs = new int[256], sBlockIDs = new int[] {4058, 4059, 4060, 4061, 4057};
    /** Needed for getting the Save Files */
    public static World mUniverse = null;
    /** Cape Lists! Dunno why, but as of now 7 people donated, without leaving a MC-Username for a Cape. */
    public static final ArrayList<String> sPremiumNames = new ArrayList<String>(), sAdminNames = new ArrayList<String>(), mBrainTechCapeList = new ArrayList<String>(), mGregTechCapeList = new ArrayList<String>();
    
    public static final ArrayList<String>			mSoundNames		= new ArrayList<String>();
    public static final ArrayList<ItemStack>		mSoundItems		= new ArrayList<ItemStack>();
    public static final ArrayList<Integer>			mSoundCounts	= new ArrayList<Integer>();
    
    static {
    	checkVersions();
    }
    
    private static final void checkVersions() {
    	if (   VERSION != GregTech_API			.VERSION
        	|| VERSION != BaseMetaTileEntity	.VERSION
    		|| VERSION != MetaTileEntity		.VERSION
    	    || VERSION != GT_CircuitryBehavior	.VERSION
    	    || VERSION != GT_CoverBehavior		.VERSION
    		|| VERSION != GT_Config				.VERSION
    		|| VERSION != GT_LanguageManager	.VERSION
    		|| VERSION != GT_ModHandler			.VERSION
    		|| VERSION != GT_OreDictUnificator	.VERSION
    		|| VERSION != GT_Recipe				.VERSION
    		|| VERSION != GT_Utility			.VERSION)
    		throw new GT_ItsNotMyFaultException("One of your Mods included GregTech-API Files inside it's download, mention this to the Mod Author, who does this bad thing, and tell him/her to use invocation. I have added a Version check, to prevent Authors from breaking my Mod that way.");
    }
    
    public GT_Mod() {
		mGregTechCapeList.add("SpwnX");
		mGregTechCapeList.add("bsaa");
		mGregTechCapeList.add("Kadah");
		mGregTechCapeList.add("kanni");
		mGregTechCapeList.add("Stute");
		mGregTechCapeList.add("t3hero");
		mGregTechCapeList.add("Hotchi");
		mGregTechCapeList.add("jagoly");
		mGregTechCapeList.add("nallar");
		mGregTechCapeList.add("BH5432");
		mGregTechCapeList.add("Sibmer");
		mGregTechCapeList.add("meep310");
		mGregTechCapeList.add("Seldron");
		mGregTechCapeList.add("hohounk");
		mGregTechCapeList.add("freebug");
		mGregTechCapeList.add("Neonbeta");
		mGregTechCapeList.add("yinscape");
		mGregTechCapeList.add("voooon24");
		mGregTechCapeList.add("Quintine");
		mGregTechCapeList.add("peach774");
		mGregTechCapeList.add("lepthymo");
		mGregTechCapeList.add("Abouttabs");
		mGregTechCapeList.add("Johnstaal");
		mGregTechCapeList.add("djshiny99");
		mGregTechCapeList.add("megatronp");
		mGregTechCapeList.add("DZCreeper");
		mGregTechCapeList.add("Kane_Hart");
		mGregTechCapeList.add("Truculent");
		mGregTechCapeList.add("vidplace7");
		mGregTechCapeList.add("simon6689");
		mGregTechCapeList.add("UnknownXLV");
		mGregTechCapeList.add("cublikefoot");
		mGregTechCapeList.add("chainman564");
		mGregTechCapeList.add("michaelbrady");
		mGregTechCapeList.add("GarretSidzaka");
		mGregTechCapeList.add("The_Hypersonic");
		mGregTechCapeList.add("CrafterOfMines57");
		
		mGregTechCapeList.add("adamros");
		mGregTechCapeList.add("alexbegt");
		
		mBrainTechCapeList.add("Friedi4321");
    }
    
    @PreInit
    public void preload(FMLPreInitializationEvent aEvent) {
    	checkVersions();
    	if (GregTech_API.isGregTechLoaded()) {
    		throw new GT_ItsNotMyFaultException("Why did you install my Addon twice? Remove the second gregtechmod.zip out of your mods-Folder, you need only one of them. And another Question: Why the Hack did Forge not detect that before me?");
    	}
		GregTech_API.gregtechmod = this;
    	File tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "GregTech.cfg");
    	Configuration tConfig1 = new Configuration(tFile);
    	tConfig1.load();
    	tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "BlockItemIDs.cfg");
    	Configuration tConfig2 = new Configuration(tFile);
    	tConfig2.load();
    	tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "Recipes.cfg");
    	Configuration tConfig3 = new Configuration(tFile);
    	tConfig3.load();
    	tFile = new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "DynamicConfig.cfg");
    	Configuration tConfig4 = new Configuration(tFile);
    	tConfig4.load();
    	GT_Log.mLogFile = new File(aEvent.getModConfigurationDirectory().getParentFile(), "GregTech.log");
    	if (GT_Log.mLogFile.exists()) {
    		mAlreadyPlayed = true;
    		try {
    			GT_Log.out = GT_Log.err = new PrintStream(GT_Log.mLogFile);
    		} catch (FileNotFoundException e) {
        		GT_Log.out = System.out;
        		GT_Log.err = System.err;
    		}
    	} else {
    		GT_Log.out = System.out;
    		GT_Log.err = System.err;
    	}
    	
    	mDoNotInit = (!tFile.getAbsolutePath().toLowerCase().contains("voltz")) && (tFile.getAbsolutePath().toLowerCase().contains(".technic") || tFile.getAbsolutePath().toLowerCase().contains("tekkit"));
    	if (mDoNotInit) {
            GT_Log.out.println("GT_Mod: Detected Technic Launcher.");
            GT_Log.out.println("GT_Mod: Errored.");
    		/**
    		 * Hello Tekkit user. I'm well aware that you decompiled my Addon to get it into Tekkit.
    		 * However, I will not change this Code, even though I know about that.
    		 * Why? Because it's to prevent Kakermix from adding it, without being kind enough to just ask, and not to prevent Tekkit Users from playing.
    		 * If he would ask me, then I will remove this barrier in future Versions of this Addon.
    		 * Don't try to make me think you are him. I already got PMs by People, who were obviously not him.
    		 */
    		System.err.println("*"); System.err.println("*"); System.err.println("*");
    		System.err.println("Hello, Gregorius Techneticies here,");
    		System.err.println("I see you most likely use Tekkit, but this Mod won't load, until Kakermix asks me PERSONALLY for the inclusion of my Mod. So bug HIM for it.");
    		System.err.println("PS. I could have exploded your Worlds, but i didn't for Publicityreasons.");
    		System.err.println("PPS. This Addon is Part of the FTB-Pack. It's even easier to use than Tekkit");
    		System.err.println("*"); System.err.println("*"); System.err.println("*");
    		tConfig1.get("Switching this won't help", "Tekkitsupport", false);
    		tConfig1.save();
        	return;
    	}
    	
		new GT_Cover_None();
		new GT_Cover_Generic();
		new GT_Cover_Redstone();
    	
        GT_Log.out.println("GT_Mod: Creating Config Object.");
    	GregTech_API.sConfiguration = new GT_Config(tConfig1, tConfig2, tConfig3, tConfig4);
    	
    	GT_Log.out.println("GT_Mod: Setting Configs");
    	GregTech_API.DEBUG_MODE					= GT_Config.sConfigFileStandard.get("general", "Debug", false).getBoolean(false);
    	GregTech_API.UE_ENERGY_COMPATIBILITY	= GT_Config.sConfigFileStandard.get("general", "UniversalElectricityPower", true).getBoolean(true);
    	
    	GregTech_API.sMachineFlammable			= GT_Config.sConfigFileStandard.get("machines", "machines_flammable"				, true ).getBoolean(false);
    	GregTech_API.sMachineFireExplosions		= GT_Config.sConfigFileStandard.get("machines", "fire_causes_explosions"			, true ).getBoolean(false);
    	GregTech_API.sMachineNonWrenchExplosions= GT_Config.sConfigFileStandard.get("machines", "explosions_on_nonwrenching"		, true ).getBoolean(false);
    	GregTech_API.sMachineWireFire			= GT_Config.sConfigFileStandard.get("machines", "wirefire_on_explosion"				, true ).getBoolean(false);
    	GregTech_API.sMachineRainExplosions		= GT_Config.sConfigFileStandard.get("machines", "rain_causes_explosions"			, true ).getBoolean(false);
    	GregTech_API.sMachineThunderExplosions	= GT_Config.sConfigFileStandard.get("machines", "lightning_causes_explosions"		, true ).getBoolean(false);
    	GregTech_API.sConstantEnergy			= GT_Config.sConfigFileStandard.get("machines", "constant_need_of_energy"			, true ).getBoolean(false);
    	
    	sUnificatorTC = GT_Config.sConfigFileStandard.get("unificatortargets", "Thaumcraft"			, true ).getBoolean(false);
    	sUnificatorRP = GT_Config.sConfigFileStandard.get("unificatortargets", "Redpower"			, true ).getBoolean(false);
    	sUnificatorRC = GT_Config.sConfigFileStandard.get("unificatortargets", "Railcraft"			, false).getBoolean(false);
    	sUnificatorTE = GT_Config.sConfigFileStandard.get("unificatortargets", "ThermalExpansion"	, false).getBoolean(false);
    	sUnificatorFR = GT_Config.sConfigFileStandard.get("unificatortargets", "Forestry"			, false).getBoolean(false);
    	
    	sHungerEffect = GT_Config.sConfigFileStandard.get("general", "unsuspiciousHungerConfig"		, true ).getBoolean(true);
    	
    	sBlockIDs[ 0] = GT_Config.sConfigFileIDs.getBlock("Block"					, 4058).getInt();
    	sBlockIDs[ 1] = GT_Config.sConfigFileIDs.getBlock("Machine"					, 4059).getInt();
    	sBlockIDs[ 2] = GT_Config.sConfigFileIDs.getBlock("Ore"						, 4060).getInt();
    	sBlockIDs[ 3] = GT_Config.sConfigFileIDs.getBlock("LightSource"				, 4061).getInt();
    	sBlockIDs[ 4] = GT_Config.sConfigFileIDs.getBlock("Block2"					, 4057).getInt();
    	
    	sItemIDs[  0] = GT_Config.sConfigFileIDs.getItem("MATERIALS"  				, 21000).getInt();
    	sItemIDs[  1] = GT_Config.sConfigFileIDs.getItem("DUSTS"  					, 21001).getInt();
    	sItemIDs[  2] = GT_Config.sConfigFileIDs.getItem("CELLS"  					, 21002).getInt();
    	sItemIDs[  3] = GT_Config.sConfigFileIDs.getItem("COMPONENTS"  				, 21003).getInt();
    	sItemIDs[  4] = GT_Config.sConfigFileIDs.getItem("SMALLDUSTS"  				, 21004).getInt();
    	sItemIDs[  5] = GT_Config.sConfigFileIDs.getItem("NUGGETS"  				, 21005).getInt();
    	
    	sItemIDs[ 13] = GT_Config.sConfigFileIDs.getItem("LIQUIDS"  				, 21013).getInt();
    	sItemIDs[ 14] = GT_Config.sConfigFileIDs.getItem("GASSES"  					, 21014).getInt();
    	sItemIDs[ 15] = GT_Config.sConfigFileIDs.getItem("PLASMA"  					, 21015).getInt();
    	sItemIDs[ 16] = GT_Config.sConfigFileIDs.getItem("NCSensorCard"				, 21016).getInt();
    	sItemIDs[ 17] = GT_Config.sConfigFileIDs.getItem("NCSensorKit"				, 21017).getInt();
    	sItemIDs[ 18] = GT_Config.sConfigFileIDs.getItem("CheatyDevice"				, 21018).getInt();
    	
    	sItemIDs[ 30] = GT_Config.sConfigFileIDs.getItem("IronMortar"				, 21030).getInt();
    	sItemIDs[ 31] = GT_Config.sConfigFileIDs.getItem("Mortar"					, 21031).getInt();
    	sItemIDs[ 32] = GT_Config.sConfigFileIDs.getItem("HandheldSonictron"		, 21032).getInt();
    	sItemIDs[ 33] = GT_Config.sConfigFileIDs.getItem("Destructopack"			, 21033).getInt();
    	sItemIDs[ 34] = GT_Config.sConfigFileIDs.getItem("Heliumcoolant060k"		, 21034).getInt();
    	sItemIDs[ 35] = GT_Config.sConfigFileIDs.getItem("Heliumcoolant120k"		, 21035).getInt();
    	sItemIDs[ 36] = GT_Config.sConfigFileIDs.getItem("Heliumcoolant180k"		, 21036).getInt();
    	sItemIDs[ 37] = GT_Config.sConfigFileIDs.getItem("LapotronicEnergycrystal"	, 21037).getInt();
    	sItemIDs[ 38] = GT_Config.sConfigFileIDs.getItem("CloakingDevice"			, 21038).getInt();
    	sItemIDs[ 39] = GT_Config.sConfigFileIDs.getItem("JackHammerIron"			, 21039).getInt();
    	sItemIDs[ 40] = GT_Config.sConfigFileIDs.getItem("Neutronreflector"			, 21040).getInt();
    	sItemIDs[ 41] = GT_Config.sConfigFileIDs.getItem("JackHammerSteel"			, 21041).getInt();
    	sItemIDs[ 42] = GT_Config.sConfigFileIDs.getItem("JackHammerDiamond"		, 21042).getInt();
    	sItemIDs[ 43] = GT_Config.sConfigFileIDs.getItem("Dataorb"					, 21043).getInt();
    	sItemIDs[ 44] = GT_Config.sConfigFileIDs.getItem("Lamphelmet"				, 21044).getInt();
    	sItemIDs[ 45] = GT_Config.sConfigFileIDs.getItem("Lapotronpack"				, 21045).getInt();
    	sItemIDs[ 46] = GT_Config.sConfigFileIDs.getItem("Rockcutter"				, 21046).getInt();
    	sItemIDs[ 47] = GT_Config.sConfigFileIDs.getItem("Teslastaff"				, 21047).getInt();
    	sItemIDs[ 48] = GT_Config.sConfigFileIDs.getItem("Thorium1"					, 21048).getInt();
    	sItemIDs[ 49] = GT_Config.sConfigFileIDs.getItem("Thorium2"					, 21049).getInt();
    	sItemIDs[ 50] = GT_Config.sConfigFileIDs.getItem("Thorium4"					, 21050).getInt();
    	sItemIDs[ 51] = GT_Config.sConfigFileIDs.getItem("Plutonium1"				, 21051).getInt();
    	sItemIDs[ 52] = GT_Config.sConfigFileIDs.getItem("Plutonium2"				, 21052).getInt();
    	sItemIDs[ 53] = GT_Config.sConfigFileIDs.getItem("Plutonium4"				, 21053).getInt();
    	sItemIDs[ 54] = GT_Config.sConfigFileIDs.getItem("LithiumCell"				, 21054).getInt();
    	sItemIDs[ 55] = GT_Config.sConfigFileIDs.getItem("DebugScanner"				, 21055).getInt();
    	sItemIDs[ 56] = GT_Config.sConfigFileIDs.getItem("LithiumbatteryEmpty"		, 21056).getInt();
    	sItemIDs[ 57] = GT_Config.sConfigFileIDs.getItem("LithiumbatteryFull"		, 21057).getInt();
		sItemIDs[ 58] = GT_Config.sConfigFileIDs.getItem("Lithiumpack"				, 21058).getInt();
		//Shield
		sItemIDs[ 60] = GT_Config.sConfigFileIDs.getItem("NaKcoolant060k"			, 21060).getInt();
    	sItemIDs[ 61] = GT_Config.sConfigFileIDs.getItem("NaKcoolant120k"			, 21061).getInt();
    	sItemIDs[ 62] = GT_Config.sConfigFileIDs.getItem("NaKcoolant180k"			, 21062).getInt();
    	sItemIDs[ 63] = GT_Config.sConfigFileIDs.getItem("Scanner"					, 21063).getInt();
    	sItemIDs[ 64] = GT_Config.sConfigFileIDs.getItem("Crowbar"					, 21064).getInt();
    	sItemIDs[ 65] = GT_Config.sConfigFileIDs.getItem("Screwdriver"				, 21065).getInt();
    	
    	mDebugMode = (GT_Config.sConfigFileStandard.get("general", "SpecialDebugMode", 0).getInt(0) == "GregoriusT".hashCode());
    	mShowCapes = GT_Config.sConfigFileStandard.get("general", "ShowCapes", true).getBoolean(false);
    	mOnline = GT_Config.sConfigFileStandard.get("general", "online", true).getBoolean(false);
    	GT_BlockMetaID_Block.mConnectedMachineTextures = GT_Config.sConfigFileStandard.get("general", "ConnectedMachineCasingTextures", true).getBoolean(false);
    	
    	sTincellCount = Math.min(64, Math.max(1, GT_Config.sConfigFileRecipes.get("usefullrecipes", "TincellsPer4Tin", 4).getInt()));
		
    	mReactorplanner		 											= GT_Config.sConfigFileStandard.get("features", "Reactorplanner"						, true ).getBoolean(false);
    	mSeedscanner		 											= GT_Config.sConfigFileStandard.get("features", "Seedscanner"							, true ).getBoolean(false);
    	GT_TileEntity_Matterfabricator.sMatterFabricationRate			= GT_Config.sConfigFileStandard.get("features", "MatterFabricationRate"					, 16666666).getInt();
    	GT_MetaTileEntity_MagicEnergyAbsorber.sEnergyPerEnderCrystal	= GT_Config.sConfigFileStandard.get("features", "EnderCrystalEnergyPerTick"				, 32).getInt();
    	GT_MetaTileEntity_MagicEnergyAbsorber.sEnergyFromVis			= GT_Config.sConfigFileStandard.get("features", "VisEnergyPerUnit"						, 12800).getInt();
    	GT_MetaTileEntity_DragonEggEnergySiphon.sDragonEggEnergyPerTick = GT_Config.sConfigFileStandard.get("features", "DragonEggEnergyPerTick"				, 1024).getInt();
    	GT_MetaTileEntity_DragonEggEnergySiphon.sAllowMultipleEggs		= GT_Config.sConfigFileStandard.get("features", "AllowDuplicatedDragonEggs"				, false).getBoolean(false);
    	sUpgradeCount													= Math.min(64, Math.max( 1, GT_Config.sConfigFileStandard.get("features", "UpgradeStacksize"		,  4).getInt()));
    	sOreStackSize													= Math.min(64, Math.max(16, GT_Config.sConfigFileStandard.get("features", "MaxOreStackSize"			, 64).getInt()));
    	sWoodStackSize													= Math.min(64, Math.max(16, GT_Config.sConfigFileStandard.get("features", "MaxLogStackSize"			, 64).getInt()));
    	sPlankStackSize													= Math.min(64, Math.max(16, GT_Config.sConfigFileStandard.get("features", "MaxPlankStackSize"		, 64).getInt()));
    	sBlockStackSize													= Math.min(64, Math.max(16, GT_Config.sConfigFileStandard.get("features", "MaxOtherBlockStackSize"	, 64).getInt()));
    	
    	sBarrelItemCount												= Math.max(193, GT_Config.sConfigFileStandard.get("features", "DigitalChestMaxItemCount", 32768).getInt());
    	sQuantumItemCount												= Math.max(sBarrelItemCount, GT_Config.sConfigFileStandard.get("features", "QuantumChestMaxItemCount", 2000000000).getInt());
    	
    	if (GT_TileEntity_Matterfabricator.sMatterFabricationRate <= 0) GT_TileEntity_Matterfabricator.sMatterFabricationRate = 166666666;
    	sTincellCount = Math.min(64, Math.max(1, sTincellCount));
    	
    	GT_Worldgenerator.sIridiumProbability	= GT_Config.sConfigFileStandard.get("worldgeneration", "IridiumProbability"		, 20).getInt();
    	GT_Worldgenerator.sAsteroids			= GT_Config.sConfigFileStandard.get("worldgeneration", "EnderAsteroids"			, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 1]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Galenaore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 2]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Iridiumore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 3]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Rubyore"					, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 4]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Sapphireore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 5]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Bauxiteore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 6]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Pyriteore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 7]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Cinnabarore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 8]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Sphaleriteore"			, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[ 9]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Tungstateore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[10]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Cooperiteore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[11]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Olivineore"				, true ).getBoolean(false);
    	GT_Worldgenerator.sGeneratedOres[12]	= GT_Config.sConfigFileStandard.get("worldgeneration", "Sodaliteore"				, true ).getBoolean(false);
    	
    	if (!GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "massfabricator", true) && GT_TileEntity_Matterfabricator.sMatterFabricationRate > 150000) GT_TileEntity_Matterfabricator.sMatterFabricationRate = 150000;
    	
    	GT_Config.system = (Calendar.getInstance().get(2) + 1 == 4 && Calendar.getInstance().get(5) >= 1 && Calendar.getInstance().get(5) <= 2);
    	
    	GT_Log.out.println("GT_Mod: Saving Configs");
    	GT_Config.sConfigFileStandard.save();
    	GT_Config.sConfigFileRecipes.save();
    	GT_Config.sConfigFileIDs.save();
    	
        GT_Log.out.println("GT_Mod: Generating Lang-File");
    	GT_LanguageManager.sLangFile = new Configuration(new File(new File(aEvent.getModConfigurationDirectory(), "GregTech"), "GregTech.lang"));
    	
        //GT_Log.out.println("GT_Mod: Removing all Scrapbox Drops.");
        //try {GT_ModHandler.getScrapboxList().clear();} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
        GT_Log.out.println("GT_Mod: Adding Scrap with a Chance of 200.00F to the Scrapbox Drops.");
        GT_ModHandler.addScrapboxDrop(200.00F, GT_ModHandler.getIC2Item("scrap", 1));

        GT_Log.out.println("GT_Mod: Autodisable Secret Recipe hiding due to stupid Battery Bug.");
        try {
        	Class.forName("ic2.core.IC2").getField("enableSecretRecipeHiding").setBoolean(null, false);
        } catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
        
        mPreLoaded = true;
        GT_Log.out.println("GT_Mod: Preload-Phase finished!");
    }
    
	@Init
    public void load(FMLInitializationEvent aEvent) {
    	if (mDoNotInit) return;
    	checkVersions();
        GT_Log.out.println("GT_Mod: Beginning Load-Phase.");
    	
		ItemStack tStack = null, tStack2 = null, tStack3 = null;
		
        GT_Log.out.println("GT_Mod: Adding Blocks.");
		GameRegistry.registerBlock(GregTech_API.sBlockList[0] = new GT_BlockMetaID_Block	(sBlockIDs[0]), GT_MetaBlock_Item.class		, GT_LanguageManager.mNameList0[0]	, GregTech_API.MOD_ID);
		GameRegistry.registerBlock(GregTech_API.sBlockList[1] = new GT_BlockMetaID_Machine	(sBlockIDs[1]), GT_MetaMachine_Item.class	, GT_LanguageManager.mNameList1[0]	, GregTech_API.MOD_ID);
		GameRegistry.registerBlock(GregTech_API.sBlockList[2] = new GT_BlockMetaID_Ore		(sBlockIDs[2]), GT_MetaOre_Item.class		, GT_LanguageManager.mNameList2[0]	, GregTech_API.MOD_ID);
		GameRegistry.registerBlock(GregTech_API.sBlockList[4] = new GT_BlockMetaID_Block2	(sBlockIDs[4]), GT_MetaBlock2_Item.class	, GT_LanguageManager.mNameList3[0]	, GregTech_API.MOD_ID);
		GameRegistry.registerBlock(GregTech_API.sBlockList[3] = new GT_Block_LightSource	(sBlockIDs[3]), ItemBlock.class				, "GT_TransparentTileEntity"		, GregTech_API.MOD_ID);
		
		LanguageRegistry.addName(GregTech_API.sBlockList[0], GT_LanguageManager.mRegionalNameList0[0]);
		LanguageRegistry.addName(GregTech_API.sBlockList[1], GT_LanguageManager.mRegionalNameList1[0]);
		LanguageRegistry.addName(GregTech_API.sBlockList[2], GT_LanguageManager.mRegionalNameList2[0]);
		LanguageRegistry.addName(GregTech_API.sBlockList[4], GT_LanguageManager.mRegionalNameList3[0]);
		
		for (int i=0;i<16;i++) {
			GT_LanguageManager.addStringLocalization("tile.BlockMetaID_Block."		+ GT_LanguageManager.mNameList0[i] + ".name", GT_LanguageManager.mRegionalNameList0[i]);
			GT_LanguageManager.addStringLocalization("tile.BlockMetaID_Machine."	+ GT_LanguageManager.mNameList1[i] + ".name", GT_LanguageManager.mRegionalNameList1[i]);
			GT_LanguageManager.addStringLocalization("tile.BlockMetaID_Ore."		+ GT_LanguageManager.mNameList2[i] + ".name", GT_LanguageManager.mRegionalNameList2[i]);
			GT_LanguageManager.addStringLocalization("tile.BlockMetaID_Block2."		+ GT_LanguageManager.mNameList3[i] + ".name", GT_LanguageManager.mRegionalNameList3[i]);
		}
		
		GregTech_API.registerMachineBlock(GregTech_API.sBlockList[0].blockID, (1|2|64|1024|8192|16384|32768));
		
        GT_Log.out.println("GT_Mod: Register OreDict Entries of Non-GT-Items.");
    	GT_OreDictUnificator.registerOre("molecule_2o"				, GT_ModHandler.getIC2Item("airCell", 1));
		GT_OreDictUnificator.registerOre("chunkLazurite"			, new ItemStack(Block.blockLapis, 1));
		GT_OreDictUnificator.registerOre("gemDiamond"				, GT_ModHandler.getIC2Item("industrialDiamond", 1));
		GT_OreDictUnificator.registerOre("craftingIndustrialDiamond", GT_ModHandler.getIC2Item("industrialDiamond", 1));
		GT_OreDictUnificator.registerOre("itemDiamond"				, GT_ModHandler.getIC2Item("industrialDiamond", 1));
		
		GT_OreDictUnificator.registerOre("stoneMossy"				, new ItemStack(Block.cobblestoneMossy	, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneCobble"				, new ItemStack(Block.cobblestoneMossy	, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneCobble"				, new ItemStack(Block.cobblestone		, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneSmooth"				, new ItemStack(Block.stone				, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneBricks"				, new ItemStack(Block.stoneBrick		, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneMossy"				, new ItemStack(Block.stoneBrick		, 1, 1));
        GT_OreDictUnificator.registerOre("stoneCracked"				, new ItemStack(Block.stoneBrick		, 1, 2));
        GT_OreDictUnificator.registerOre("stoneChiseled"			, new ItemStack(Block.stoneBrick		, 1, 3));
        GT_OreDictUnificator.registerOre("stoneSand"				, new ItemStack(Block.sandStone			, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneNetherrack"			, new ItemStack(Block.netherrack		, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneNetherBrick"			, new ItemStack(Block.netherBrick		, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        GT_OreDictUnificator.registerOre("stoneEnd"					, new ItemStack(Block.whiteStone		, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
        
    	GT_OreDictUnificator.registerOre("itemIridium"				, GT_ModHandler.getIC2Item("iridiumOre", 1));
    	
    	GT_OreDictUnificator.add("plateAlloyIridium"		, GT_ModHandler.getIC2Item("iridiumPlate", 1));
    	GT_OreDictUnificator.add("plateAlloyAdvanced"		, GT_ModHandler.getIC2Item("advancedAlloy", 1));
    	GT_OreDictUnificator.add("plateDenseCopper"			, GT_ModHandler.getIC2Item("denseCopperPlate", 1));
    	
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.record13, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordCat, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordBlocks, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordStrad, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordStal, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordFar, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordMall, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordMellohi, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordWard, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.recordChirp, 1));
    	GT_OreDictUnificator.registerOre("itemRecord" , new ItemStack(Item.record11, 1));
    	
    	if (Block.enderChest != null)
    	GT_OreDictUnificator.registerOre("craftingEnderChest"	, new ItemStack(Block.enderChest, 1));
    	
    	GT_OreDictUnificator.registerOre("glassReinforced"		, GT_ModHandler.getIC2Item("reinforcedGlass", 1));
    	GT_OreDictUnificator.registerOre("glassReinforced"		, GT_ModHandler.getTEItem("hardenedGlass", 1));
    	
        GT_Log.out.println("GT_Mod: Register Unification Entries");
        
    	GT_OreDictUnificator.add("oreCoal"			, new ItemStack(Block.oreCoal, 1));
    	GT_OreDictUnificator.add("oreIron"			, new ItemStack(Block.oreIron, 1));
    	GT_OreDictUnificator.add("oreLapis"			, new ItemStack(Block.oreLapis, 1));
    	GT_OreDictUnificator.add("oreRedstone"		, new ItemStack(Block.oreRedstone, 1));
    	GT_OreDictUnificator.add("oreRedstone"		, new ItemStack(Block.oreRedstoneGlowing, 1));
    	GT_OreDictUnificator.add("oreGold"			, new ItemStack(Block.oreGold, 1));
    	GT_OreDictUnificator.add("oreDiamond"		, new ItemStack(Block.oreDiamond, 1));
    	GT_OreDictUnificator.add("oreEmerald"		, new ItemStack(Block.oreEmerald, 1));
        GT_OreDictUnificator.add("oreNetherQuartz"	, new ItemStack(Block.oreNetherQuartz, 1));
        
    	GT_OreDictUnificator.add("oreGalena"			, new ItemStack(GregTech_API.sBlockList[2], 1, 1));
    	GT_OreDictUnificator.add("oreIridium"			, new ItemStack(GregTech_API.sBlockList[2], 1, 2));
    	GT_OreDictUnificator.add("oreRuby"				, new ItemStack(GregTech_API.sBlockList[2], 1, 3));
    	GT_OreDictUnificator.add("oreSapphire"			, new ItemStack(GregTech_API.sBlockList[2], 1, 4));
    	GT_OreDictUnificator.add("oreBauxite"			, new ItemStack(GregTech_API.sBlockList[2], 1, 5));
    	GT_OreDictUnificator.add("oreNetherPyrite"		, new ItemStack(GregTech_API.sBlockList[2], 1, 6));
    	GT_OreDictUnificator.add("oreNetherCinnabar"	, new ItemStack(GregTech_API.sBlockList[2], 1, 7));
    	GT_OreDictUnificator.add("oreNetherSphalerite"	, new ItemStack(GregTech_API.sBlockList[2], 1, 8));
    	GT_OreDictUnificator.add("oreEndTungstate"		, new ItemStack(GregTech_API.sBlockList[2], 1, 9));
    	GT_OreDictUnificator.add("oreEndCooperite"		, new ItemStack(GregTech_API.sBlockList[2], 1,10));
    	GT_OreDictUnificator.add("oreEndOlivine"		, new ItemStack(GregTech_API.sBlockList[2], 1,11));
    	GT_OreDictUnificator.add("oreEndSodalite"		, new ItemStack(GregTech_API.sBlockList[2], 1,12));
        
		GT_OreDictUnificator.add("craftingSaltpeterToGunpowder"	, GT_ModHandler.getTEItem("crystalNiter", 1));
		GT_OreDictUnificator.add("craftingSulfurToGunpowder"	, GT_ModHandler.getTEItem("crystalSulfur", 1));
		
		GT_OreDictUnificator.add("itemRubber"			, GT_ModHandler.getIC2Item("rubber", 1));
	    GT_OreDictUnificator.add("gemDiamond"			, new ItemStack(Item.diamond, 1));
		GT_OreDictUnificator.add("gemEmerald"			, new ItemStack(Item.emerald, 1));
		GT_OreDictUnificator.add("nuggetGold"			, new ItemStack(Item.goldNugget, 1));
		GT_OreDictUnificator.add("ingotGold"			, new ItemStack(Item.ingotGold, 1));
		GT_OreDictUnificator.add("ingotIron"			, new ItemStack(Item.ingotIron, 1));
		GT_OreDictUnificator.add("ingotTin"				, GT_ModHandler.getIC2Item("tinIngot", 1));
		GT_OreDictUnificator.add("ingotCopper"			, GT_ModHandler.getIC2Item("copperIngot", 1));
		GT_OreDictUnificator.add("ingotBronze"			, GT_ModHandler.getIC2Item("bronzeIngot", 1));
		GT_OreDictUnificator.add("ingotRefinedIron"		, GT_ModHandler.getIC2Item("refinedIronIngot", 1));
		GT_OreDictUnificator.add("ingotUranium"			, GT_ModHandler.getIC2Item("uraniumIngot", 1));
		GT_OreDictUnificator.add("dustLapis"			, new ItemStack(Item.dyePowder, 1, 4));
    	GT_OreDictUnificator.add("dustRedstone"			, new ItemStack(Item.redstone, 1));
    	GT_OreDictUnificator.add("dustNetherQuartz"		, new ItemStack(Item.field_94583_ca, 1));
    	GT_OreDictUnificator.add("dustGunpowder"		, new ItemStack(Item.gunpowder, 1));
    	GT_OreDictUnificator.add("dustGlowstone"		, new ItemStack(Item.lightStoneDust, 1));
        GT_OreDictUnificator.add("blockIron"			, new ItemStack(Block.blockSteel, 1, 0));
        GT_OreDictUnificator.add("blockGold"			, new ItemStack(Block.blockGold, 1, 0));
        GT_OreDictUnificator.add("blockDiamond"			, new ItemStack(Block.blockDiamond, 1, 0));
        GT_OreDictUnificator.add("blockEmerald"			, new ItemStack(Block.blockEmerald, 1, 0));
        GT_OreDictUnificator.add("blockLapis"			, new ItemStack(Block.blockLapis, 1, 0));
        GT_OreDictUnificator.add("blockRedstone"		, new ItemStack(Block.blockRedstone, 1, 0));
		GT_OreDictUnificator.add("blockCopper"			, GT_ModHandler.getIC2Item("copperBlock", 1));
        GT_OreDictUnificator.add("blockTin"				, GT_ModHandler.getIC2Item("tinBlock", 1));
        GT_OreDictUnificator.add("blockBronze"			, GT_ModHandler.getIC2Item("bronzeBlock", 1));
        GT_OreDictUnificator.add("blockUranium"			, GT_ModHandler.getIC2Item("uraniumBlock", 1));
        GT_OreDictUnificator.add("blockSilver"			, new ItemStack(GregTech_API.sBlockList[0], 1, 3));
        GT_OreDictUnificator.add("blockRuby"			, new ItemStack(GregTech_API.sBlockList[0], 1, 4));
        GT_OreDictUnificator.add("blockSapphire"		, new ItemStack(GregTech_API.sBlockList[0], 1, 5));
        GT_OreDictUnificator.add("blockAluminum"		, new ItemStack(GregTech_API.sBlockList[0], 1, 7));
        GT_OreDictUnificator.add("blockAluminium"		, new ItemStack(GregTech_API.sBlockList[0], 1, 7));
        GT_OreDictUnificator.add("blockTitanium"		, new ItemStack(GregTech_API.sBlockList[0], 1, 8));
        GT_OreDictUnificator.add("blockChrome"			, new ItemStack(GregTech_API.sBlockList[0], 1, 9));
        GT_OreDictUnificator.add("blockSteel"			, new ItemStack(GregTech_API.sBlockList[0], 1,11));
        GT_OreDictUnificator.add("blockBrass"			, new ItemStack(GregTech_API.sBlockList[0], 1,12));
        GT_OreDictUnificator.add("blockLead"			, new ItemStack(GregTech_API.sBlockList[4], 1, 0));
        GT_OreDictUnificator.add("blockElectrum"		, new ItemStack(GregTech_API.sBlockList[4], 1, 1));
        GT_OreDictUnificator.add("blockZinc"			, new ItemStack(GregTech_API.sBlockList[4], 1, 2));
        GT_OreDictUnificator.add("blockOlivine"			, new ItemStack(GregTech_API.sBlockList[4], 1, 3));
        GT_OreDictUnificator.add("blockGreenSapphire"	, new ItemStack(GregTech_API.sBlockList[4], 1, 4));
        GT_OreDictUnificator.add("blockPlatinum"		, new ItemStack(GregTech_API.sBlockList[4], 1, 5));
        GT_OreDictUnificator.add("blockTungsten"		, new ItemStack(GregTech_API.sBlockList[4], 1, 6));
        GT_OreDictUnificator.add("blockNickel"			, new ItemStack(GregTech_API.sBlockList[4], 1, 7));
        GT_OreDictUnificator.add("blockInvar"			, new ItemStack(GregTech_API.sBlockList[4], 1,10));
        GT_OreDictUnificator.add("blockOsmium"			, new ItemStack(GregTech_API.sBlockList[4], 1,11));
        GT_OreDictUnificator.add("blockIridium"			, new ItemStack(GregTech_API.sBlockList[4], 1,12));
        
        GT_Log.out.println("GT_Mod: Register other Mods Unification Targets.");
        if (sUnificatorFR) {
        	GT_OreDictUnificator.override("ingotCopper"		, GT_ModHandler.getFRItem("ingotCopper", 1));
        	GT_OreDictUnificator.override("ingotTin"		, GT_ModHandler.getFRItem("ingotTin", 1));
        	GT_OreDictUnificator.override("ingotBronze"		, GT_ModHandler.getFRItem("ingotBronze", 1));
        	GT_OreDictUnificator.override("dustAsh"			, GT_ModHandler.getFRItem("ash", 1));
        	GT_OreDictUnificator.override("dustWood"		, GT_ModHandler.getFRItem("woodPulp", 1));
        	GT_OreDictUnificator.override("pulpWood"		, GT_ModHandler.getFRItem("woodPulp", 1));
        }
        if (sUnificatorRC) {
        	GT_OreDictUnificator.override("nuggetIron"		, GT_ModHandler.getRCItem("nugget.iron", 1));
        	GT_OreDictUnificator.override("nuggetSteel"		, GT_ModHandler.getRCItem("nugget.steel", 1));
        	GT_OreDictUnificator.override("ingotSteel"		, GT_ModHandler.getRCItem("part.ingot.steel", 1));
        	GT_OreDictUnificator.override("dustCharcoal"	, GT_ModHandler.getRCItem("dust.charcoal", 1));
        	GT_OreDictUnificator.override("dustObsidian"	, GT_ModHandler.getRCItem("dust.obsidian", 1));
        	GT_OreDictUnificator.override("dustSaltpeter"	, GT_ModHandler.getRCItem("dust.saltpeter", 1));
        	GT_OreDictUnificator.override("dustSulfur"		, GT_ModHandler.getRCItem("dust.sulfur", 1));
        }
        if (sUnificatorTE) {
        	GT_OreDictUnificator.override("dustWood"		, GT_ModHandler.getTEItem("sawdust", 1));
    		GT_OreDictUnificator.override("pulpWood"		, GT_ModHandler.getTEItem("sawdust", 1));
        	GT_OreDictUnificator.override("dustGold"		, GT_ModHandler.getTEItem("dustGold", 1));
        	GT_OreDictUnificator.override("dustBrass"		, GT_ModHandler.getTEItem("dustBrass", 1));
        	GT_OreDictUnificator.override("dustBronze"		, GT_ModHandler.getTEItem("dustBronze", 1));
        	GT_OreDictUnificator.override("dustCopper"		, GT_ModHandler.getTEItem("dustCopper", 1));
        	GT_OreDictUnificator.override("dustElectrum"	, GT_ModHandler.getTEItem("dustElectrum", 1));
        	GT_OreDictUnificator.override("dustInvar"		, GT_ModHandler.getTEItem("dustInvar", 1));
        	GT_OreDictUnificator.override("dustIron"		, GT_ModHandler.getTEItem("dustIron", 1));
        	GT_OreDictUnificator.override("dustLead"		, GT_ModHandler.getTEItem("dustLead", 1));
        	GT_OreDictUnificator.override("dustNickel"		, GT_ModHandler.getTEItem("dustNickel", 1));
        	GT_OreDictUnificator.override("dustObsidian"	, GT_ModHandler.getTEItem("dustObsidian", 1));
        	GT_OreDictUnificator.override("dustPlatinum"	, GT_ModHandler.getTEItem("dustPlatinum", 1));
        	GT_OreDictUnificator.override("dustSilver"		, GT_ModHandler.getTEItem("dustSilver", 1));
        	GT_OreDictUnificator.override("dustTin"			, GT_ModHandler.getTEItem("dustTin", 1));
        	GT_OreDictUnificator.override("ingotCopper"		, GT_ModHandler.getTEItem("ingotCopper", 1));
        	GT_OreDictUnificator.override("ingotElectrum"	, GT_ModHandler.getTEItem("ingotElectrum", 1));
        	GT_OreDictUnificator.override("ingotInvar"		, GT_ModHandler.getTEItem("ingotInvar", 1));
        	GT_OreDictUnificator.override("ingotLead"		, GT_ModHandler.getTEItem("ingotLead", 1));
        	GT_OreDictUnificator.override("ingotNickel"		, GT_ModHandler.getTEItem("ingotNickel", 1));
        	GT_OreDictUnificator.override("ingotPlatinum"	, GT_ModHandler.getTEItem("ingotPlatinum", 1));
        	GT_OreDictUnificator.override("ingotSilver"		, GT_ModHandler.getTEItem("ingotSilver", 1));
        	GT_OreDictUnificator.override("ingotTin"		, GT_ModHandler.getTEItem("ingotTin", 1));
        	GT_OreDictUnificator.override("nuggetCopper"	, GT_ModHandler.getTEItem("nuggetCopper", 1));
        	GT_OreDictUnificator.override("nuggetElectrum"	, GT_ModHandler.getTEItem("nuggetElectrum", 1));
        	GT_OreDictUnificator.override("nuggetInvar"		, GT_ModHandler.getTEItem("nuggetInvar", 1));
        	GT_OreDictUnificator.override("nuggetLead"		, GT_ModHandler.getTEItem("nuggetLead", 1));
        	GT_OreDictUnificator.override("nuggetNickel"	, GT_ModHandler.getTEItem("nuggetNickel", 1));
        	GT_OreDictUnificator.override("nuggetPlatinum"	, GT_ModHandler.getTEItem("nuggetPlatinum", 1));
        	GT_OreDictUnificator.override("nuggetSilver"	, GT_ModHandler.getTEItem("nuggetSilver", 1));
        	GT_OreDictUnificator.override("nuggetTin"		, GT_ModHandler.getTEItem("nuggetTin", 1));
        	GT_OreDictUnificator.override("blockCopper"		, GT_ModHandler.getTEItem("blockCopper", 1));
        	GT_OreDictUnificator.override("blockElectrum"	, GT_ModHandler.getTEItem("blockElectrum", 1));
        	GT_OreDictUnificator.override("blockInvar"		, GT_ModHandler.getTEItem("blockInvar", 1));
        	GT_OreDictUnificator.override("blockLead"		, GT_ModHandler.getTEItem("blockLead", 1));
        	GT_OreDictUnificator.override("blockNickel"		, GT_ModHandler.getTEItem("blockNickel", 1));
        	GT_OreDictUnificator.override("blockPlatinum"	, GT_ModHandler.getTEItem("blockPlatinum", 1));
        	GT_OreDictUnificator.override("blockSilver"		, GT_ModHandler.getTEItem("blockSilver", 1));
        	GT_OreDictUnificator.override("blockTin"		, GT_ModHandler.getTEItem("blockTin", 1));
        }
        if (sUnificatorRP) {
        	GT_OreDictUnificator.override("gemRuby"			, GT_ModHandler.mRuby);
        	GT_OreDictUnificator.override("gemSapphire"		, GT_ModHandler.mSapphire);
        	GT_OreDictUnificator.override("gemGreenSapphire", GT_ModHandler.mGreenSapphire);
        	GT_OreDictUnificator.override("ingotSilver"		, GT_ModHandler.mSilver);
        	GT_OreDictUnificator.override("ingotCopper"		, GT_ModHandler.mCopper);
        	GT_OreDictUnificator.override("ingotTin"		, GT_ModHandler.mTin);
        	GT_OreDictUnificator.override("ingotBrass"		, GT_ModHandler.mBrass);
        	GT_OreDictUnificator.override("nuggetIron"		, GT_ModHandler.mIronNugget);
        	GT_OreDictUnificator.override("nuggetSilver"	, GT_ModHandler.mSilverNugget);
        	GT_OreDictUnificator.override("nuggetTin"		, GT_ModHandler.mTinNugget);
        	GT_OreDictUnificator.override("nuggetCopper"	, GT_ModHandler.mCopperNugget);
        }
        if (sUnificatorTC) {
        	GT_OreDictUnificator.override("nuggetIron"		, GT_ModHandler.mNuggetIron);
        	GT_OreDictUnificator.override("nuggetSilver"	, GT_ModHandler.mNuggetSilver);
        	GT_OreDictUnificator.override("nuggetTin"		, GT_ModHandler.mNuggetTin);
        	GT_OreDictUnificator.override("nuggetCopper"	, GT_ModHandler.mNuggetCopper);
        	GT_OreDictUnificator.override("nuggetLead"		, GT_ModHandler.mNuggetLead);
        }
        
    	//GT_OreDictUnificator.override("plateTin"		, GT_ModHandler.getRCItem("part.plate.tin", 1)); needs 50% Iron and 50% Tin to be crafted
    	GT_OreDictUnificator.override("plateIron"		, GT_ModHandler.getRCItem("part.plate.iron", 1));
    	GT_OreDictUnificator.override("plateSteel"		, GT_ModHandler.getRCItem("part.plate.steel", 1));
        
        
        GT_Log.out.println("GT_Mod: Register TileEntities.");
		GameRegistry.registerTileEntity(GT_TileEntityMetaID_Machine.class	, GT_LanguageManager.mNameList1[ 0]);
		GameRegistry.registerTileEntity(GT_TileEntity_Lightningrod.class	, GT_LanguageManager.mNameList1[ 2]);
		GameRegistry.registerTileEntity(GT_TileEntity_ComputerCube.class	, GT_LanguageManager.mNameList1[ 4]);
		GameRegistry.registerTileEntity(GT_TileEntity_UUMAssembler.class	, GT_LanguageManager.mNameList1[ 5]);
		GameRegistry.registerTileEntity(GT_TileEntity_Sonictron.class		, GT_LanguageManager.mNameList1[ 6]);
		GameRegistry.registerTileEntity(GT_TileEntity_LESU.class			, GT_LanguageManager.mNameList1[ 7]);
		GameRegistry.registerTileEntity(GT_TileEntity_IDSU.class			, GT_LanguageManager.mNameList1[ 8]);
		GameRegistry.registerTileEntity(GT_TileEntity_AESU.class			, GT_LanguageManager.mNameList1[ 9]);
		GameRegistry.registerTileEntity(GT_TileEntity_ChargeOMat.class		, GT_LanguageManager.mNameList1[10]);
		GameRegistry.registerTileEntity(GT_TileEntity_Superconductor.class	, GT_LanguageManager.mNameList1[12]);
		GameRegistry.registerTileEntity(GT_TileEntity_PlayerDetector.class	, GT_LanguageManager.mNameList1[13]);
		GameRegistry.registerTileEntity(GT_TileEntity_Matterfabricator.class, GT_LanguageManager.mNameList1[14]);
		GameRegistry.registerTileEntity(GT_TileEntity_Supercondensator.class, GT_LanguageManager.mNameList1[15]);
		GameRegistry.registerTileEntity(GT_TileEntity_LightSource.class		, "GT_LightSource");
		GameRegistry.registerTileEntity(GregTech_API.constructBaseMetaTileEntity().getClass(), "MetatileEntity");
		
		GT_Log.out.println("GT_Mod: Testing BaseMetaTileEntity.");
		if (GregTech_API.constructBaseMetaTileEntity() == null) {
			GT_Log.out.print("GT_Mod: Fatal Error ocurred while initializing TileEntities, crashing Minecraft.");
			throw new RuntimeException("");
		}
		
		GT_Log.out.println("GT_Mod: Register MetaTileEntities.");
		new GT_MetaTileEntity_ElectricAutoWorkbench		(16, "GT_E_Craftingtable"		, "Electric Craftingtable");
		new GT_MetaTileEntity_Translocator				(17, "GT_Translocator"			, "Electric Translocator");
		new GT_MetaTileEntity_ElectricBufferSmall		(18, "GT_E_Buffer_Small"		, "Small Electric Buffer");
		new GT_MetaTileEntity_ElectricBufferLarge		(19, "GT_E_Buffer_Large"		, "Large Electric Buffer");
		new GT_MetaTileEntity_AdvancedTranslocator		(20, "GT_Adv_Translocator"		, "Advanced Translocator");
		new GT_MetaTileEntity_ElectricBufferAdvanced	(21, "GT_Adv_Buffer"			, "Advanced Buffer");
		new GT_MetaTileEntity_RockBreaker				(22, "GT_RockBreaker"			, "Electric Rock Breaker");
		new GT_MetaTileEntity_ElectricSorter			(23, "GT_E_Sorter"				, "Electric Sorter");
		new GT_MetaTileEntity_ElectricItemClearer		(24, "GT_E_ItemClearer"			, "Electric Item Clearer");
		new GT_MetaTileEntity_Electrolyzer				(25, "GT_Electrolyzer"			, "Industrial Electrolyzer");
		new GT_MetaTileEntity_CropHarvestor				(26, "GT_Harvestor"				, "Crop Harvestor");
		new GT_MetaTileEntity_Scrapboxinator			(27, "GT_Scrapboxinator"		, "Scrapboxinator");
		new GT_MetaTileEntity_Grinder					(28, "GT_Grinder"				, "Industrial Grinder");
		new GT_MetaTileEntity_BlastFurnace				(29, "GT_BlastFurnace"			, "Industrial Blast Furnace");
		new GT_MetaTileEntity_Quantumtank				(30, "GT_QuantumTank"			, "Quantum Tank");
		new GT_MetaTileEntity_ImplosionCompressor		(31, "GT_ImplosionCompressor"	, "Implosion Compressor");
		new GT_MetaTileEntity_Sawmill					(32, "GT_Sawmill"				, "Industrial Sawmill");
		new GT_MetaTileEntity_DieselGenerator			(33, "GT_DieselGenerator"		, "Diesel Generator");
		new GT_MetaTileEntity_GasTurbine				(34, "GT_GasTurbine"			, "Gas Turbine");
		new GT_MetaTileEntity_ThermalGenerator			(35, "GT_ThermalGenerator"		, "Thermal Generator");
		new GT_MetaTileEntity_SemifluidGenerator		(36, "GT_SemifluidGenerator"	, "Semifluid Generator");
		new GT_MetaTileEntity_PlasmaGenerator			(37, "GT_PlasmaGenerator"		, "Plasma Generator");
		new GT_MetaTileEntity_VacuumFreezer				(38, "GT_VacuumFreezer"			, "Vacuum Freezer");
		new GT_MetaTileEntity_ElectricRegulatorAdvanced	(39, "GT_RegulatorAdvanced"		, "Advanced Regulator");
		new GT_MetaTileEntity_DragonEggEnergySiphon		(40, "GT_DragonEggEnergySiphon"	, "Dragon Egg Energy Siphon");
		new GT_MetaTileEntity_ChemicalReactor			(41, "GT_ChemicalReactor"		, "Chemical Reactor");
		new GT_MetaTileEntity_MagicEnergyConverter		(42, "GT_MagicConverter"		, "Magic Energy Converter");
		new GT_MetaTileEntity_MagicEnergyAbsorber		(43, "GT_MagicAbsorber"			, "Magic Energy Absorber");
		new GT_MetaTileEntity_DistillationTower			(44, "GT_DistillationTower"		, "Distillation Tower");
		new GT_MetaTileEntity_Safe						(45, "GT_Safe"					, "Advanced Safe");
		new GT_MetaTileEntity_ElectricInventoryManager	(46, "GT_InventoryManager"		, "Inventory Manager");
		new GT_MetaTileEntity_AdvancedPump				(47, "GT_Pump"					, "Advanced Pump");
		new GT_MetaTileEntity_Barrel					(48, "GT_Barrel"				, "Digital Chest");
		new GT_MetaTileEntity_QuantumChest				(49, "GT_QuantumChest"			, "Quantum Chest");
		new GT_MetaTileEntity_Macerator					(50, "GT_Macerator"				, "Automatic Macerator");
		new GT_MetaTileEntity_Extractor					(51, "GT_Extractor"				, "Automatic Extractor");
		new GT_MetaTileEntity_Compressor				(52, "GT_Compressor"			, "Automatic Compressor");
		new GT_MetaTileEntity_Recycler					(53, "GT_Recycler"				, "Automatic Recycler");
		new GT_MetaTileEntity_E_Furnace					(54, "GT_E_Furnace"				, "Automatic E-Furnace");
		new GT_MetaTileEntity_Wiremill					(55, "GT_Wiremill"				, "Automatic Wiremill");
		new GT_MetaTileEntity_AlloySmelter				(56, "GT_AlloySmelter"			, "Alloy Smelter");
		new GT_MetaTileEntity_Canner					(57, "GT_Canner"				, "Automatic Canning Machine");
		new GT_MetaTileEntity_ElectricTypeSorter		(58, "GT_E_T_Sorter"			, "Electric Type Sorter");
		new GT_MetaTileEntity_Bender					(59, "GT_Bender"				, "Plate Bending Machine");
		new GT_MetaTileEntity_Assembler					(60, "GT_Assembler"				, "Assembling Machine");
		new GT_MetaTileEntity_Printer					(61, "GT_Printer"				, "Printing Factory");
		new GT_MetaTileEntity_Centrifuge				(62, "GT_Centrifuge"			, "Industrial Centrifuge");
		new GT_MetaTileEntity_Microwave					(63, "GT_Microwave"				, "Microwave Oven");
		new GT_MetaTileEntity_Pulverizer				(64, "GT_Pulverizer"			, "Universal Macerator");
		//new GT_MetaTileEntity_RedstoneLamp			(65, "GT_RedstoneLamp"			, "Redstone Controlled Lamp");
		new GT_MetaTileEntity_RedstoneNoteBlock			(66, "GT_RedstoneNoteBlock"		, "Redstone Note Block");
		new GT_MetaTileEntity_RedstoneButtonPanel		(67, "GT_RedstoneButtonPanel"	, "Button Panel");
		new GT_MetaTileEntity_RedstoneStrengthDisplay	(68, "GT_RedstoneDisplayBlock"	, "Redstone Display");
		new GT_MetaTileEntity_RedstoneCircuitBlock		(69, "GT_RedstoneCircuitBlock"	, "Redstone Circuit Block");
		new GT_MetaTileEntity_Shelf						(70, "GT_Shelf"					, "Wood encased Shelf");
		new GT_MetaTileEntity_Shelf_Iron				(71, "GT_Shelf_Iron"			, "Metal encased Shelf");
		new GT_MetaTileEntity_Shelf_FileCabinet			(72, "GT_Shelf_FileCabinet"		, "File Cabinet");
		new GT_MetaTileEntity_Shelf_Desk				(73, "GT_Shelf_Desk"			, "Metal encased Desk");
		new GT_MetaTileEntity_Shelf_Compartment			(74, "GT_Shelf_Compartment"		, "Compartment");
		
		new GT_MetaTileEntity_MachineBox				(79, "GT_MachineBox"			, "Machine Box");
		new GT_MetaTileEntity_FusionComputer			(80, "GT_Fusion_Computer"		, "Fusion Control Computer");
		new GT_MetaTileEntity_FusionEnergyInjector		(81, "GT_Fusion_Energy"			, "Fusion Energy Injector");
		new GT_MetaTileEntity_FusionInjector			(82, "GT_Fusion_Injector"		, "Fusion Material Injector");
		new GT_MetaTileEntity_FusionExtractor			(83, "GT_Fusion_Extractor"		, "Fusion Material Extractor");
		
        GT_Log.out.println("GT_Mod: Register Redstone Circuit behaviours.");
		new GT_Circuit_Timer(0);
		new GT_Circuit_BasicLogic(1);
		new GT_Circuit_Repeater(2);
		new GT_Circuit_Pulser(3);
		new GT_Circuit_RedstoneMeter(4);
		
		new GT_Circuit_Randomizer(8);
		
		new GT_Circuit_CombinationLock(16);
		new GT_Circuit_BitAnd(17);
		
        GT_Log.out.println("GT_Mod: Register Armor Textures.");
		int tArmorID1 = gregtechproxy.addArmor("lapotronpack"), tArmorID2 = gregtechproxy.addArmor("lithiumbatpack"), tArmorID3 = gregtechproxy.addArmor("claokingdevice");
		
        GT_Log.out.println("GT_Mod: Register Meta-ID Items.");
		GregTech_API.sItemList[ 0] = new GT_MetaItem_Material	(sItemIDs[  0], "GT_Materials");
		GregTech_API.sItemList[ 1] = new GT_MetaItem_Dust		(sItemIDs[  1], "GT_Dusts");
		GregTech_API.sItemList[ 2] = new GT_MetaItem_Cell		(sItemIDs[  2], "GT_Cells");
		GregTech_API.sItemList[ 3] = new GT_MetaItem_Component	(sItemIDs[  3], "GT_Components");
		GregTech_API.sItemList[ 4] = new GT_MetaItem_SmallDust	(sItemIDs[  4], "GT_SmallDusts");
		GregTech_API.sItemList[ 5] = new GT_MetaItem_Nugget		(sItemIDs[  5], "GT_Nuggets");
		
		GregTech_API.sItemList[13] = new GT_MetaItem_Liquid		(sItemIDs[ 13], "GT_Liquids");
		GregTech_API.sItemList[14] = new GT_MetaItem_Gas		(sItemIDs[ 14], "GT_Gasses");
		GregTech_API.sItemList[15] = new GT_MetaItem_Plasma		(sItemIDs[ 15], "GT_Plasma");
		
        GT_Log.out.println("GT_Mod: Adding All Sub-Items with their OreDict and LiquidDict Entries.");
		GT_MetaItem_Material.addItem(  0, "Copper Credit"			, "", "0.125 Credits", false);
		GT_MetaItem_Material.addItem(  1, "Silver Credit"			, "", "8 Credits", false);
		GT_MetaItem_Material.addItem(  2, "Gold Credit"				, "", "64 Credits", false);
		GT_MetaItem_Material.addItem(  3, "Diamond Credit"			, "", "512 Credits", false);
		GT_MetaItem_Material.addItem(  4, "Iridium Alloy Ingot"		, "", "", false);
		GT_MetaItem_Material.addItem(  5, "Hot Tungstensteel Ingot"	, "", "", false);
		GT_MetaItem_Material.addItem(  6, "Tungstensteel Ingot"	, "ingotTungstenSteel"	, "Vacuum Hardened"	, false);
		GT_MetaItem_Material.addItem(  7, "Oil Berry"				, "", "", false);
		GT_MetaItem_Material.addItem(  8, "Indigo Blossom"			, "", "", false);
		GT_MetaItem_Material.addItem(  9, "Indigo Dye"				, "dyeBlue", "", false);
		
		GT_MetaItem_Material.addItem( 13, "Magnalium Plate"		, "plateMagnalium"		, "MgAl2"			, false);
		GT_MetaItem_Material.addItem( 14, "Flour"				, "dustWheat"			, ""				, false);
		GT_MetaItem_Material.addItem( 15, "Wood Plate"			, "plankWood"			, ""				, false);
		GT_MetaItem_Material.addItem( 16, "Iridium Ingot"		, "ingotIridium"		, "Ir"				, false);
		GT_MetaItem_Material.addItem( 17, "Silver Ingot"		, "ingotSilver"			, "Ag"				, false);
		GT_MetaItem_Material.addItem( 18, "Aluminium Ingot"		, "ingotAluminium"		, "Al"				, false);
		GT_MetaItem_Material.addItem( 19, "Titanium Ingot"		, "ingotTitanium"		, "Ti"				, false);
		GT_MetaItem_Material.addItem( 20, "Chrome Ingot"		, "ingotChrome"			, "Cr"				, false);
		GT_MetaItem_Material.addItem( 21, "Electrum Ingot"		, "ingotElectrum"		, "AgAu"			, false);
		GT_MetaItem_Material.addItem( 22, "Tungsten Ingot"		, "ingotTungsten"		, "W"				, false);
		GT_MetaItem_Material.addItem( 23, "Lead Ingot"			, "ingotLead"			, "Pb"				, false);
		GT_MetaItem_Material.addItem( 24, "Zinc Ingot"			, "ingotZinc"			, "Zn"				, false);
		GT_MetaItem_Material.addItem( 25, "Brass Ingot"			, "ingotBrass"			, "ZnCu3"			, false);
		GT_MetaItem_Material.addItem( 26, "Steel Ingot"			, "ingotSteel"			, "Fe"				, false);
		GT_MetaItem_Material.addItem( 27, "Platinum Ingot"		, "ingotPlatinum"		, "Pt"				, false);
		GT_MetaItem_Material.addItem( 28, "Nickel Ingot"		, "ingotNickel"			, "Ni"				, false);
		GT_MetaItem_Material.addItem( 29, "Invar Ingot"			, "ingotInvar"			, "Fe2Ni"			, false);
		GT_MetaItem_Material.addItem( 30, "Osmium Ingot"		, "ingotOsmium"			, "Os"				, false);
		
		GT_MetaItem_Material.addItem( 32, "Ruby"				, "gemRuby"				, "Al2O6Cr"			, false);
		GT_MetaItem_Material.addItem( 33, "Sapphire"			, "gemSapphire"			, "Al2O6"			, false);
		GT_MetaItem_Material.addItem( 34, "Green Sapphire"		, "gemGreenSapphire"	, "Al2O6"			, false);
		GT_MetaItem_Material.addItem( 35, "Chunk of Lazurite"	, "chunkLazurite"		, "(Al6Si6Ca8Na8)8"	, false);
		GT_MetaItem_Material.addItem( 36, "Silicon Plate"		, "plateSilicon"		, "Si2"				, false);
		GT_MetaItem_Material.addItem( 37, "Olivine"				, "gemOlivine"			, "Mg2Fe2SiO4"		, false);
		GT_MetaItem_Material.addItem( 38, "Thorium Ingot"		, "ingotThorium"		, "Th"				, true);
		GT_MetaItem_Material.addItem( 39, "Plutonium Ingot"		, "ingotPlutonium"		, "Pu"				, true);
		
		GT_MetaItem_Material.addItem( 54, "Red Garnet"			, "gemGarnetRed"		, "(Al2Mg3Si3O12)3(Al2Fe3Si3O12)5(Al2Mn3Si3O12)8", false);
		GT_MetaItem_Material.addItem( 55, "Yellow Garnet"		, "gemGarnetYellow"		, "(Ca3Fe2Si3O12)5(Ca3Al2Si3O12)8(Ca3Cr2Si3O12)3", false);
		
		GT_MetaItem_Material.addItem( 64, "Iron Plate"			, "plateIron"			, "Fe"				, false);
		GT_MetaItem_Material.addItem( 65, "Gold Plate"			, "plateGold"			, "Au"				, false);
		GT_MetaItem_Material.addItem( 66, "Refined Iron Plate"	, "plateRefinedIron"	, "Fe"				, false);
		GT_MetaItem_Material.addItem( 67, "Tin Plate"			, "plateTin"			, "Sn"				, false);
		GT_MetaItem_Material.addItem( 68, "Copper Plate"		, "plateCopper"			, "Cu"				, false);
		GT_MetaItem_Material.addItem( 69, "Silver Plate"		, "plateSilver"			, "Ag"				, false);
		GT_MetaItem_Material.addItem( 70, "Bronze Plate"		, "plateBronze"			, "SnCu3"			, false);
		GT_MetaItem_Material.addItem( 71, "Electrum Plate"		, "plateElectrum"		, "AgAu"			, false);
		GT_MetaItem_Material.addItem( 72, "Nickel Plate"		, "plateNickel"			, "Ni"				, false);
		GT_MetaItem_Material.addItem( 73, "Invar Plate"			, "plateInvar"			, "Fe2Ni"			, false);
		GT_MetaItem_Material.addItem( 74, "Lead Plate"			, "plateLead"			, "Pb"				, false);
		GT_MetaItem_Material.addItem( 75, "Aluminium Plate"		, "plateAluminium"		, "Al"				, false);
		GT_MetaItem_Material.addItem( 76, "Chrome Plate"		, "plateChrome"			, "Cr"				, false);
		GT_MetaItem_Material.addItem( 77, "Titanium Plate"		, "plateTitanium"		, "Ti"				, false);
		GT_MetaItem_Material.addItem( 78, "Steel Plate"			, "plateSteel"			, "Fe"				, false);
		GT_MetaItem_Material.addItem( 79, "Platinum Plate"		, "platePlatinum"		, "Pt"				, false);
		GT_MetaItem_Material.addItem( 80, "Tungsten Plate"		, "plateTungsten"		, "W"				, false);
		GT_MetaItem_Material.addItem( 81, "Brass Plate"			, "plateBrass"			, "ZnCu3"			, false);
		GT_MetaItem_Material.addItem( 82, "Zinc Plate"			, "plateZinc"			, "Zn"				, false);
		GT_MetaItem_Material.addItem( 83, "Tungstensteel Plate"	, "plateTungstenSteel"	, "Vacuum Hardened"	, false);
		GT_MetaItem_Material.addItem( 84, "Osmium Plate"		, "plateOsmium"			, "Os"				, false);
		
		GT_MetaItem_Nugget.addItem(16, "Iridium Nugget"			, "Iridium"				, "Ir"				, false);
		GT_MetaItem_Nugget.addItem(17, "Silver Nugget"			, "Silver"				, "Ag"				, false);
		GT_MetaItem_Nugget.addItem(18, "Aluminium Nugget"		, "Aluminium"			, "Al"				, false);
		GT_MetaItem_Nugget.addItem(19, "Titanium Nugget"		, "Titanium"			, "Ti"				, false);
		GT_MetaItem_Nugget.addItem(20, "Chrome Nugget"			, "Chrome"				, "Cr"				, false);
		GT_MetaItem_Nugget.addItem(21, "Electrum Nugget"		, "Electrum"			, "AgAu"			, false);
		GT_MetaItem_Nugget.addItem(22, "Tungsten Nugget"		, "Tungsten"			, "W"				, false);
		GT_MetaItem_Nugget.addItem(23, "Lead Nugget"			, "Lead"				, "Pb"				, false);
		GT_MetaItem_Nugget.addItem(24, "Zinc Nugget"			, "Zinc"				, "Zn"				, false);
		GT_MetaItem_Nugget.addItem(25, "Brass Nugget"			, "Brass"				, "ZnCu3"			, false);
		GT_MetaItem_Nugget.addItem(26, "Steel Nugget"			, "Steel"				, "Fe"				, false);
		GT_MetaItem_Nugget.addItem(27, "Platinum Nugget"		, "Platinum"			, "Pt"				, false);
		GT_MetaItem_Nugget.addItem(28, "Nickel Nugget"			, "Nickel"				, "Ni"				, false);
		GT_MetaItem_Nugget.addItem(29, "Invar Nugget"			, "Invar"				, "Fe2Ni"			, false);
		GT_MetaItem_Nugget.addItem(30, "Osmium Nugget"			, "Osmium"				, "Os"				, false);
		
		GT_MetaItem_Nugget.addItem(241, "Iron Nugget"			, "Iron"				, "Fe"				, false);
		GT_MetaItem_Nugget.addItem(243, "Copper Nugget"			, "Copper"				, "Cu"				, false);
		GT_MetaItem_Nugget.addItem(244, "Tin Nugget"			, "Tin"					, "Sn"				, false);
		GT_MetaItem_Nugget.addItem(245, "Bronze Nugget"			, "Bronze"				, "SnCu3"			, false);
		
		GT_MetaItem_Dust.addItem(  0, "Ender Pearl Dust"		, "EnderPearl"		, "BeK4N5Cl6"			, false);
		GT_MetaItem_Dust.addItem(  1, "Ender Eye Dust"			, "EnderEye"		, "BeK4N5Cl6C4S2"		, false);
		GT_MetaItem_Dust.addItem(  2, "Lazurite Dust"			, "Lazurite"		, "Al6Si6Ca8Na8"		, false);
		GT_MetaItem_Dust.addItem(  3, "Pyrite Dust"				, "Pyrite"			, "FeS2"				, false);
		GT_MetaItem_Dust.addItem(  4, "Calcite Dust"			, "Calcite"			, "CaCO3"				, false);
		GT_MetaItem_Dust.addItem(  5, "Sodalite Dust"			, "Sodalite"		, "Al3Si3Na4Cl"			, false);
		GT_MetaItem_Dust.addItem(  6, "Netherrack Dust"			, "Netherrack"		, ""					, false);
		GT_MetaItem_Dust.addItem(  7, "Flint Dust"				, "Flint"			, "SiO2"				, false);
		GT_MetaItem_Dust.addItem(  8, "Sulfur Dust"				, "Sulfur"			, "S"					, false);
		GT_MetaItem_Dust.addItem(  9, "Saltpeter Dust"			, "Saltpeter"		, "KNO3"				, false);
		GT_MetaItem_Dust.addItem( 10, "Endstone Dust"			, "Endstone"		, ""					, false);
		GT_MetaItem_Dust.addItem( 11, "Cinnabar Dust"			, "Cinnabar"		, "HgS"					, false);
		GT_MetaItem_Dust.addItem( 12, "Manganese Dust"			, "Manganese"		, "Mn"					, false);
		GT_MetaItem_Dust.addItem( 13, "Magnesium Dust"			, "Magnesium"		, "Mg"					, false);
		GT_MetaItem_Dust.addItem( 14, "Sphalerite Dust"			, "Sphalerite"		, "ZnS"					, false);
		GT_MetaItem_Dust.addItem( 15, "Wood Pulp"				, "Wood"			, ""					, false);
		GT_OreDictUnificator.add("pulpWood", GT_MetaItem_Dust.instance.getUnunifiedStack(15, 1));
		GT_OreDictUnificator.add("pulpWood", GT_ModHandler.getTEItem("sawdust", 1));
		GT_MetaItem_Dust.addItem( 16, "Uranium Dust"			, "Uranium"			, "U"					, true);
		GT_MetaItem_Dust.addItem( 17, "Bauxite Dust"			, "Bauxite"			, "TiAl16H10O12"		, false);
		GT_MetaItem_Dust.addItem( 18, "Aluminium Dust"			, "Aluminium"		, "Al"					, false);
		GT_MetaItem_Dust.addItem( 19, "Titanium Dust"			, "Titanium"		, "Ti"					, false);
		GT_MetaItem_Dust.addItem( 20, "Chrome Dust"				, "Chrome"			, "Cr"					, false);
		GT_MetaItem_Dust.addItem( 21, "Electrum Dust"			, "Electrum"		, "AgAu"				, false);
		GT_MetaItem_Dust.addItem( 22, "Tungsten Dust"			, "Tungsten"		, "W"					, false);
		GT_MetaItem_Dust.addItem( 23, "Lead Dust"				, "Lead"			, "Pb"					, false);
		GT_MetaItem_Dust.addItem( 24, "Zinc Dust"				, "Zinc"			, "Zn"					, false);
		GT_MetaItem_Dust.addItem( 25, "Brass Dust"				, "Brass"			, "ZnCu3"				, false);
		GT_MetaItem_Dust.addItem( 26, "Steel Dust"				, "Steel"			, "Fe"					, false);
		GT_MetaItem_Dust.addItem( 27, "Platinum Dust"			, "Platinum"		, "Pt"					, false);
		GT_MetaItem_Dust.addItem( 28, "Nickel Dust"				, "Nickel"			, "Ni"					, false);
		GT_MetaItem_Dust.addItem( 29, "Invar Dust"				, "Invar"			, "Fe2Ni"				, false);
		GT_MetaItem_Dust.addItem( 30, "Osmium Dust"				, "Osmium"			, "Os"					, false);
		
		GT_MetaItem_Dust.addItem( 32, "Ruby Dust"				, "Ruby"			, "Al2O6Cr"				, false);
		GT_MetaItem_Dust.addItem( 33, "Sapphire Dust"			, "Sapphire"		, "Al2O6"				, false);
		GT_MetaItem_Dust.addItem( 34, "Green Sapphire Dust"		, "GreenSapphire"	, "Al2O6"				, false);
		GT_MetaItem_Dust.addItem( 35, "Emerald Dust"			, "Emerald"			, "Be3Al2Si6O18"		, false);
		GT_MetaItem_Dust.addItem( 36, "Diamond Dust"			, "Diamond"			, "C128"				, false);
		GT_MetaItem_Dust.addItem( 37, "Olivine Dust"			, "Olivine"			, "Mg2Fe2SiO4"			, false);
		
		GT_MetaItem_Dust.addItem( 44, "Galena Dust"				, "Galena"			, "Pb3Ag3S2"			, false);
		GT_MetaItem_Dust.addItem( 45, "Phosphorus Dust"			, "Phosphorus"		, "Ca3(PO4)2"			, false);
		GT_MetaItem_Dust.addItem( 46, "Obsidian Dust"			, "Obsidian"		, "MgFeSi2O8"			, false);
		GT_MetaItem_Dust.addItem( 47, "Charcoal Dust"			, "Charcoal"		, "C"					, false);
		
		GT_MetaItem_Dust.addItem( 54, "Red Garnet Dust"			, "GarnetRed"		, "(Al2Mg3Si3O12)3(Al2Fe3Si3O12)5(Al2Mn3Si3O12)8", false);
		GT_MetaItem_Dust.addItem( 55, "Yellow Garnet Dust"		, "GarnetYellow"	, "(Ca3Fe2Si3O12)5(Ca3Al2Si3O12)8(Ca3Cr2Si3O12)3", false);
		GT_MetaItem_Dust.addItem( 56, "Pyrope Dust"				, "Pyrope"			, "Al2Mg3Si3O12"		, false);
		GT_MetaItem_Dust.addItem( 57, "Almandine Dust"			, "Almandine"		, "Al2Fe3Si3O12"		, false);
		GT_MetaItem_Dust.addItem( 58, "Spessartine Dust"		, "Spessartine"		, "Al2Mn3Si3O12"		, false);
		GT_MetaItem_Dust.addItem( 59, "Andradite Dust"			, "Andradite"		, "Ca3Fe2Si3O12"		, false);
		GT_MetaItem_Dust.addItem( 60, "Grossular Dust"			, "Grossular"		, "Ca3Al2Si3O12"		, false);
		GT_MetaItem_Dust.addItem( 61, "Uvarovite Dust"			, "Uvarovite"		, "Ca3Cr2Si3O12"		, false);
		GT_MetaItem_Dust.addItem( 62, "Ashes"					, "Ash"				, "C"					, false);
		GT_MetaItem_Dust.addItem( 63, "Dark Ashes"				, "DarkAsh"			, "C"					, false);
		GT_MetaItem_Dust.addItem( 64, "Redrock Dust"			, "RedRock"			, "(Na2LiAl2Si2)((CaCO3)2SiO2)3"	, false);
		GT_MetaItem_Dust.addItem( 65, "Marble Dust"				, "Marble"			, "Mg(CaCO3)7"			, false);
		GT_MetaItem_Dust.addItem( 66, "Basalt Dust"				, "Basalt"			, "(Mg2Fe2SiO4)(CaCO3)3(SiO2)8C4"	, false);
		
		GT_MetaItem_Dust.addItem( 80, "Thorium Dust"			, "Thorium"			, "Th"					, true);
		GT_MetaItem_Dust.addItem( 81, "Plutonium Dust"			, "Plutonium"		, "Pu"					, true);
		
		GT_MetaItem_Dust.addItem(240, "Coal Dust"				, "Coal"			, "C2"					, false);
		GT_MetaItem_Dust.addItem(241, "Iron Dust"				, "Iron"			, "Fe"					, false);
		GT_MetaItem_Dust.addItem(242, "Gold Dust"				, "Gold"			, "Au"					, false);
		GT_MetaItem_Dust.addItem(243, "Copper Dust"				, "Copper"			, "Cu"					, false);
		GT_MetaItem_Dust.addItem(244, "Tin Dust"				, "Tin"				, "Sn"					, false);
		GT_MetaItem_Dust.addItem(245, "Bronze Dust"				, "Bronze"			, "SnCu3"				, false);
		GT_MetaItem_Dust.addItem(246, "Silver Dust"				, "Silver"			, "Ag"					, false);
		GT_MetaItem_Dust.addItem(247, "Clay Dust"				, "Clay"			, "Na2LiAl2Si2"			, false);
		
		GT_MetaItem_SmallDust.addItem(248, "Gunpowder"			, "Gunpowder"		, ""					, false);
		GT_MetaItem_SmallDust.addItem(249, "Redstone Dust"		, "Redstone"		, ""					, false);
		GT_MetaItem_SmallDust.addItem(250, "Glowstone Dust"		, "Glowstone"		, ""					, false);
		
		GT_MetaItem_Cell.addItem(  0, "Hydrogen Cell"			, "molecule_1h"			, "H");
		GT_MetaItem_Cell.addItem(  1, "Deuterium Cell"			, "molecule_1d"			, "H-2");
		GT_MetaItem_Cell.addItem(  2, "Tritium Cell"			, "molecule_1t"			, "H-3");
		GT_MetaItem_Cell.addItem(  3, "Helium Cell"				, "molecule_1he"		, "He");
		GT_MetaItem_Cell.addItem(  4, "Wolframium Cell"			, "molecule_1w"			, "W");
		GT_MetaItem_Cell.addItem(  5, "Lithium Cell"			, "molecule_1li"		, "Li");
		GT_MetaItem_Cell.addItem(  6, "Helium-3 Cell"			, "molecule_1he3"		, "He-3");
		GT_MetaItem_Cell.addItem(  7, "Silicon Cell"			, "molecule_1si"		, "Si");
		GT_MetaItem_Cell.addItem(  8, "Carbon Cell"				, "molecule_1c"			, "C");
		GT_MetaItem_Cell.addItem(  9, "Methane Cell"			, "molecule_1me"		, "CH4");
		GT_MetaItem_Cell.addItem( 10, "Berylium Cell"			, "molecule_1be"		, "Be");
		GT_MetaItem_Cell.addItem( 11, "Calcium Cell"			, "molecule_1ca"		, "Ca");
		GT_MetaItem_Cell.addItem( 12, "Sodium Cell"				, "molecule_1na"		, "Na");
		GT_MetaItem_Cell.addItem( 13, "Chlorite Cell"			, "molecule_1cl"		, "Cl");
		GT_MetaItem_Cell.addItem( 14, "Potassium Cell"			, "molecule_1k"			, "K");
		GT_MetaItem_Cell.addItem( 15, "Nitrogen Cell"			, "molecule_1n"			, "N");
		GT_MetaItem_Cell.addItem( 16, "Mercury Cell"			, "molecule_1hg"		, "Hg");
		GT_MetaItem_Cell.addItem( 17, "Oil Cell"				, ""					, "");
		GT_MetaItem_Cell.addItem( 18, "Diesel Cell"				, ""					, "");
		GT_MetaItem_Cell.addItem( 19, "Bio Diesel Cell"			, ""					, "");
		GT_MetaItem_Cell.addItem( 20, "Biomass Cell"			, ""					, "");
		
		GT_MetaItem_Cell.addItem( 22, "Nitro-Diesel Cell"		, ""					, "");
		GT_MetaItem_Cell.addItem( 23, "Ice Cell"				, ""					, "H2O");
		GT_MetaItem_Cell.addItem( 24, "Seed Oil Cell"			, ""					, "");
		
		GT_MetaItem_Cell.addItem( 32, "Sodium Persulfate Cell"	, "molecule_2na_2s_8o"	, "Na2S2O8");
		GT_MetaItem_Cell.addItem( 33, "Calcium Carbonate Cell"	, "molecule_1ca_1c_3o"	, "CaCO3");
		GT_MetaItem_Cell.addItem( 34, "Glyceryl Cell"			, "molecule_3c_5h_3n_9o", "C3H5N3O9");
		GT_MetaItem_Cell.addItem( 35, "Nitro-Coalfuel Cell"		, ""					, "");
		GT_MetaItem_Cell.addItem( 36, "Sulfur Cell"				, "molecule_1s"			, "S");
		GT_MetaItem_Cell.addItem( 37, "Sodium Sulfide Cell"		, "molecule_1na_1s"		, "NaS");
		GT_MetaItem_Cell.addItem( 38, "Nitrogen Dioxide Cell"	, "molecule_1n_2o"		, "NO2");
		GT_MetaItem_Cell.addItem( 39, "Nitrocarbon Cell"		, "molecule_1n_1c"		, "NC");
		GT_MetaItem_Cell.addItem( 40, "Sulfuric Acid Cell"		, "molecule_2h_1s_4o"	, "H2SO4");
		
		GT_MetaItem_Cell.addItem(131, "Helium Plasma Cell"		, "plasma_1he"			, "He");
		
		GT_MetaItem_Plasma.addItem(  3, "Helium"		, "Helium"			, "He"	, GT_MetaItem_Cell.instance.getUnunifiedStack(131, 1), GT_ModHandler.getEmptyCell(1));
		
		GT_MetaItem_Gas.addItem(  0, "Hydrogen"			, "Hydrogen"		, "H"	, GT_MetaItem_Cell.instance.getUnunifiedStack( 0, 1), GT_ModHandler.getEmptyCell(1));
		GT_MetaItem_Gas.addItem(  1, "Deuterium"		, "Deuterium"		, "H-2"	, GT_MetaItem_Cell.instance.getUnunifiedStack( 1, 1), GT_ModHandler.getEmptyCell(1));
		GT_MetaItem_Gas.addItem(  2, "Tritium"			, "Tritium"			, "H-3"	, GT_MetaItem_Cell.instance.getUnunifiedStack( 2, 1), GT_ModHandler.getEmptyCell(1));
		GT_MetaItem_Gas.addItem(  3, "Helium"			, "Helium"			, "He"	, GT_MetaItem_Cell.instance.getUnunifiedStack( 3, 1), GT_ModHandler.getEmptyCell(1));
		GT_MetaItem_Gas.addItem(  6, "Helium-3"			, "Helium-3"		, "He-3", GT_MetaItem_Cell.instance.getUnunifiedStack( 6, 1), GT_ModHandler.getEmptyCell(1));
		GT_MetaItem_Gas.addItem(  9, "Methane"			, "Methane"			, "CH4"	, GT_MetaItem_Cell.instance.getUnunifiedStack( 9, 1), GT_ModHandler.getEmptyCell(1));
		GT_MetaItem_Gas.addItem( 15, "Nitrogen"			, "Nitrogen"		, "N"	, GT_MetaItem_Cell.instance.getUnunifiedStack(15, 1), GT_ModHandler.getEmptyCell(1));
		GT_MetaItem_Gas.addItem( 38, "Nitrogen Dioxide"	, "NitrogenDioxide"	, "NO2"	, GT_MetaItem_Cell.instance.getUnunifiedStack(38, 1), GT_ModHandler.getEmptyCell(1));
		
		GT_MetaItem_Liquid.addItem(  4, "Wolframium"			, "Wolframium"			, "W"			, GT_MetaItem_Cell.instance.getUnunifiedStack( 4, 1), GT_ModHandler.getEmptyCell(1),  34,  32,  40);
		GT_MetaItem_Liquid.addItem(  5, "Lithium"				, "Lithium"				, "Li"			, GT_MetaItem_Cell.instance.getUnunifiedStack( 5, 1), GT_ModHandler.getEmptyCell(1), 145, 191, 255);
		GT_MetaItem_Liquid.addItem(  7, "Silicon"				, "Silicon"				, "Si"			, GT_MetaItem_Cell.instance.getUnunifiedStack( 7, 1), GT_ModHandler.getEmptyCell(1),  63,  36,  85);
		GT_MetaItem_Liquid.addItem( 10, "Berylium"				, "Berylium"			, "Be"			, GT_MetaItem_Cell.instance.getUnunifiedStack(10, 1), GT_ModHandler.getEmptyCell(1),   0,  46,   0);
		GT_MetaItem_Liquid.addItem( 11, "Calcium"				, "Calcium"				, "Ca"			, GT_MetaItem_Cell.instance.getUnunifiedStack(11, 1), GT_ModHandler.getEmptyCell(1), 234,  79,  97);
		GT_MetaItem_Liquid.addItem( 12, "Sodium"				, "Sodium"				, "Na"			, GT_MetaItem_Cell.instance.getUnunifiedStack(12, 1), GT_ModHandler.getEmptyCell(1),  43,  72, 164);
		GT_MetaItem_Liquid.addItem( 13, "Chlorite"				, "Chlorite"			, "Cl"			, GT_MetaItem_Cell.instance.getUnunifiedStack(13, 1), GT_ModHandler.getEmptyCell(1),  25, 100,  95);
		GT_MetaItem_Liquid.addItem( 14, "Potassium"				, "Potassium"			, "K"			, GT_MetaItem_Cell.instance.getUnunifiedStack(14, 1), GT_ModHandler.getEmptyCell(1), 164, 181, 181);
		GT_MetaItem_Liquid.addItem( 16, "Mercury"				, "Mercury"				, "Hg"			, GT_MetaItem_Cell.instance.getUnunifiedStack(16, 1), GT_ModHandler.getEmptyCell(1), 170, 170, 170);
		GT_MetaItem_Liquid.addItem( 22, "Nitro Diesel"			, "NitroFuel"			, ""			, GT_MetaItem_Cell.instance.getUnunifiedStack(22, 1), GT_ModHandler.getEmptyCell(1), 191, 255, 100);
		GT_MetaItem_Liquid.addItem( 32, "Sodium Persulfate"		, "SodiumPersulfate"	, "Na2S2O8"		, GT_MetaItem_Cell.instance.getUnunifiedStack(32, 1), GT_ModHandler.getEmptyCell(1),   0, 103,  67);
		GT_MetaItem_Liquid.addItem( 33, "Calcium Carbonate"		, "CalciumCarbonate"	, "CaCO3"		, GT_MetaItem_Cell.instance.getUnunifiedStack(33, 1), GT_ModHandler.getEmptyCell(1),  91,  16,  18);
		GT_MetaItem_Liquid.addItem( 34, "Glyceryl"				, "Glyceryl"			, "C3H5N3O9"	, GT_MetaItem_Cell.instance.getUnunifiedStack(34, 1), GT_ModHandler.getEmptyCell(1),  52, 157, 157);
		GT_MetaItem_Liquid.addItem( 35, "Nitro Coalfuel"		, "NitroCoalFuel"		, ""			, GT_MetaItem_Cell.instance.getUnunifiedStack(35, 1), GT_ModHandler.getEmptyCell(1),   0,  43,  43);
		
		GT_MetaItem_Component.addItem( 0, "Energy Flow Circuit"			, "craftingCircuitTier07"		, "Manages large amounts of Energy");
		GT_MetaItem_Component.addItem( 1, "Data Control Circuit"		, "eliteCircuit"				, "Basic Computer Processor");
		GT_MetaItem_Component.addItem( 2, "Superconductor"				, "craftingSuperconductor"		, "Conducts Energy losslessly");
		GT_MetaItem_Component.addItem( 3, "Data Storage Circuit"		, "craftingCircuitTier05"		, "Stores Data");
		GT_MetaItem_Component.addItem( 4, "Computer Monitor"			, "craftingMonitorTier02"		, "Displays things");
		GT_MetaItem_Component.addItem( 5, "Conveyor Module"				, "craftingConveyor"			, "Moves Items around");
		GT_MetaItem_Component.addItem( 6, "Pump Module"					, "craftingPump"				, "Moves Liquids around");
		GT_MetaItem_Component.addItem( 7, "Solar Panel"					, "craftingSolarPanel"			, "Makes Energy from Sun");
		GT_MetaItem_Component.addItem( 8, "Item Transport Valve"		, "craftingItemValve"			, "Moves Items and Liquids at once!");
		GT_MetaItem_Component.addItem( 9, "Drain"						, "craftingDrain"				, "Collects Liquids and Rain");
		GT_MetaItem_Component.addItem(10, "Redstone Liquid Detector"	, "craftingLiquidMeter"			, "Outputs Redstone depending on stored Liquids");
		GT_MetaItem_Component.addItem(11, "Redstone Item Detector"		, "craftingItemMeter"			, "Outputs Redstone depending on stored Items");
		GT_MetaItem_Component.addItem(12, "Energy Crystal Upgrade"		, "crafting100kEUStore"			, "Adds 100000 EU to the Energy Capacity");
		GT_MetaItem_Component.addItem(13, "Lapotron Crystal Upgrade"	, "crafting1kkEUStore"			, "Adds 1 Million EU to the Energy Capacity");
		GT_MetaItem_Component.addItem(14, "Energy Orb Upgrade"			, "crafting10kkEUStore"			, "Adds 10 Million EU to the Energy Capacity");
		GT_MetaItem_Component.addItem(15, "Redstone EU Meter"			, "craftingEUMeter"				, "Outputs Redstone depending on stored Energy");
		GT_MetaItem_Component.addItem(16, "BrainTech Aerospace Advanced Reinforced Duct Tape FAL-84", "craftingDuctTape", "If this doesn't fix it, then it's broken");
		GT_MetaItem_Component.addItem(17, "Diamond Sawblade"			, "craftingDiamondBlade"		, "Caution! This is very sharp.");
		GT_MetaItem_Component.addItem(18, "Diamond Grinder"				, "craftingGrinder"				, "Fancy Grinding Head");
		GT_MetaItem_Component.addItem(19, "Kanthal Heating Coil"		, "craftingHeatingCoilTier01"	, "Standard Heating Coil"); //33% Iron 33% Chrome 33% Aluminium
		GT_MetaItem_Component.addItem(20, "Nichrome Heating Coil"		, "craftingHeatingCoilTier02"	, "Advanced Heating Coil"); //20% Chrome 80% Nickel
		GT_MetaItem_Component.addItem(21, "Cupronickel Heating Coil"	, "craftingHeatingCoilTier00"	, "Cheap and simple Heating Coil"); //50% Copper 50% Nickel
		GT_MetaItem_Component.addItem(22, "Machine Parts"				, "craftingMachineParts"		, "Random Machine Parts");
		GT_MetaItem_Component.addItem(23, "Wolframium Grinder"			, "craftingGrinder"				, "Regular Grinding Head");
		GT_MetaItem_Component.addItem(24, "Advanced Circuit Parts"		, "craftingCircuitPartsTier04"	, "Part of advanced Circuitry");
		GT_MetaItem_Component.addItem(25, "Pneumatic Generator Upgrade"	, "craftingPneumaticGenerator"	, "Lets Machines accept MJ");
		GT_MetaItem_Component.addItem(26, "Lithium Battery Upgrade"		, "craftingLiBatteryUpgrade"	, "Adds 100000 EU to the Energy Capacity");
		GT_MetaItem_Component.addItem(27, "HV-Transformer Upgrade"		, "craftingHVTUpgrade"			, "Higher Tier of Transformer Upgrade");
		GT_MetaItem_Component.addItem(28, "RS-Energy-Cell Upgrade"		, "craftingEnergyCellUpgrade"	, "Adds 100000 MJ to the Energy Capacity");
		GT_MetaItem_Component.addItem(29, "Quantum-Chest Upgrade"		, "craftingQuantumChestUpgrade"	, "Upgrades a Digital Chest to a Quantum Chest");
		GT_MetaItem_Component.addItem(30, "Active Machine Detector"		, "craftingWorkDetector"		, "Emits Restone if the Machine has work");
		GT_MetaItem_Component.addItem(31, "Redstone Machine Controller"	, "craftingWorkController"		, "This can control Machines with Redstone");
		GT_MetaItem_Component.addItem(32, "Aluminium Machine Hull"		, "craftingRawMachineTier01"	, "Machine Block");
		GT_MetaItem_Component.addItem(33, "Bronze Machine Hull"			, "craftingRawMachineTier01"	, "Machine Block");
		GT_MetaItem_Component.addItem(34, "Brass Machine Hull"			, "craftingRawMachineTier00"	, "Cheap Machine Block");
		GT_MetaItem_Component.addItem(35, "Steel Machine Hull"			, "craftingRawMachineTier02"	, "Advanced Machine Block");
		GT_MetaItem_Component.addItem(36, "Titanium Machine Hull"		, "craftingRawMachineTier03"	, "Very Advanced Machine Block");
		GT_OreDictUnificator.registerOre("craftingRawMachineTier02", GT_MetaItem_Component.instance.getUnunifiedStack(36, 1));
		
		GT_MetaItem_Component.addItem(48, "Basic Circuit Board"			, "craftingCircuitBoardTier02"	, "Just a simple Circuit Plate");
		GT_MetaItem_Component.addItem(49, "Advanced Circuit Board"		, "craftingCircuitBoardTier04"	, "Standard Circuit Plate");
		GT_MetaItem_Component.addItem(50, "Elite Circuit Board"			, "craftingCircuitBoardTier06"	, "Highly advanced Circuit Plate");
		
		GT_MetaItem_Component.addItem(64, "Crafting Module"				, "craftingWorkBench"			, "A Workbench on a Cover");
		
		GT_MetaItem_Component.addItem(80, "Steam Upgrade"				, "craftingSteamUpgrade"		, "Lets Machines consume Steam");
		GT_MetaItem_Component.addItem(81, "Steam Tank"					, "craftingSteamTank"			, "Increases Steam Capacity by 64 Buckets");
        
		GT_Log.out.println("GT_Mod: Trying to add Nuclear Control Sensor Kit/Card...");
		try {
		Class.forName("shedar.mods.ic2.nuclearcontrol.api.IRemoteSensor");
		GregTech_API.sItemList[ 16] = new GT_SensorCard_Item							(sItemIDs[ 16], GT_LanguageManager.mNameListItem[16]);
		GregTech_API.sItemList[ 17] = new GT_SensorKit_Item								(sItemIDs[ 17], GT_LanguageManager.mNameListItem[17]);
        GT_Log.out.println("GT_Mod: ...succeeded.");
		} catch (Throwable e) {
	    GT_Log.out.println("GT_Mod: ...failed.");
		}
		
        GT_Log.out.println("GT_Mod: Register Regular Items.");
        GregTech_API.sItemList[ 18] = GregTech_API.constructElectricArmorItem			(sItemIDs[ 18], GT_LanguageManager.mNameListItem[18], 1000000000, Integer.MAX_VALUE, 1, 10, -1, 100.0D, true, 1, tArmorID1);
		
		GregTech_API.sItemList[ 30] = new GT_Mortar_Item								(sItemIDs[ 30], GT_LanguageManager.mNameListItem[30],       64, GT_OreDictUnificator.get("dustIron", 1));
		GregTech_API.sItemList[ 31] = new GT_Generic_Item								(sItemIDs[ 31], GT_LanguageManager.mNameListItem[31], "Used to turn Ingots into Dust");
		GregTech_API.sItemList[ 32] = new GT_Sonictron_Item								(sItemIDs[ 32], GT_LanguageManager.mNameListItem[32]);
		GregTech_API.sItemList[ 33] = new GT_Destructopack_Item							(sItemIDs[ 33], GT_LanguageManager.mNameListItem[33]);
		GregTech_API.sItemList[ 34] = GregTech_API.constructCoolantCellItem				(sItemIDs[ 34], GT_LanguageManager.mNameListItem[34],    60000, 1);
		GregTech_API.sItemList[ 35] = GregTech_API.constructCoolantCellItem				(sItemIDs[ 35], GT_LanguageManager.mNameListItem[35],   180000, 3);
		GregTech_API.sItemList[ 36] = GregTech_API.constructCoolantCellItem				(sItemIDs[ 36], GT_LanguageManager.mNameListItem[36],   360000, 6);
		GregTech_API.sItemList[ 37] = GregTech_API.constructElectricEnergyStorageItem	(sItemIDs[ 37], GT_LanguageManager.mNameListItem[37], 10000000, 8192, 4, 37, 37);
		GregTech_API.sItemList[ 38] = GregTech_API.constructElectricArmorItem			(sItemIDs[ 38], GT_LanguageManager.mNameListItem[38], 10000000, 8192, 4,  0,   512, 0.0D, false, 1, tArmorID3);
		try {GregTech_API.sItemList[ 39] = new GT_Jackhammer_Item						(sItemIDs[ 39], GT_LanguageManager.mNameListItem[39],    10000, 1,  7.5F,  50);															} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		try {GregTech_API.sItemList[ 40] = new GT_NeutronReflector_Item					(sItemIDs[ 40], GT_LanguageManager.mNameListItem[40],        0);																		} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		try {GregTech_API.sItemList[ 41] = new GT_Jackhammer_Item						(sItemIDs[ 41], GT_LanguageManager.mNameListItem[41],    10000, 1, 15.0F, 100);															} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		try {GregTech_API.sItemList[ 42] = new GT_Jackhammer_Item						(sItemIDs[ 42], GT_LanguageManager.mNameListItem[42],   100000, 2, 45.0F, 250);															} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		GregTech_API.sItemList[ 43] = new GT_Dataorb_Item								(sItemIDs[ 43], GT_LanguageManager.mNameListItem[43]);
		GregTech_API.sItemList[ 44] = GregTech_API.constructElectricArmorItem			(sItemIDs[ 44], GT_LanguageManager.mNameListItem[44],    10000,   32, 1,  0, 16|32, 0.0D, false, 0, tArmorID1);
		GregTech_API.sItemList[ 45] = GregTech_API.constructElectricArmorItem			(sItemIDs[ 45], GT_LanguageManager.mNameListItem[45], 10000000, 8192, 4,  0,     0, 0.0D,  true, 1, tArmorID1);
		try {GregTech_API.sItemList[ 46] = new GT_Rockcutter_Item						(sItemIDs[ 46], GT_LanguageManager.mNameListItem[46]);																					} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		try {GregTech_API.sItemList[ 47] = new GT_Teslastaff_Item						(sItemIDs[ 47], GT_LanguageManager.mNameListItem[47]);																					} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		GregTech_API.sItemList[ 48] = GregTech_API.constructRadioactiveCellItem			(sItemIDs[ 48], GT_LanguageManager.mNameListItem[48],    25000, 1, -5, null);
		GregTech_API.sItemList[ 49] = GregTech_API.constructRadioactiveCellItem			(sItemIDs[ 49], GT_LanguageManager.mNameListItem[49],    25000, 2, -5, null);
		GregTech_API.sItemList[ 50] = GregTech_API.constructRadioactiveCellItem			(sItemIDs[ 50], GT_LanguageManager.mNameListItem[50],    25000, 4, -5, null);
		GregTech_API.sItemList[ 51] = GregTech_API.constructRadioactiveCellItem			(sItemIDs[ 51], GT_LanguageManager.mNameListItem[51],    20000, 1,  2, GT_ModHandler.getIC2Item("nearDepletedUraniumCell", 4));
		GregTech_API.sItemList[ 52] = GregTech_API.constructRadioactiveCellItem			(sItemIDs[ 52], GT_LanguageManager.mNameListItem[52],    20000, 2,  2, GT_ModHandler.getIC2Item("nearDepletedUraniumCell", 4));
		GregTech_API.sItemList[ 53] = GregTech_API.constructRadioactiveCellItem			(sItemIDs[ 53], GT_LanguageManager.mNameListItem[53],    20000, 4,  2, GT_ModHandler.getIC2Item("nearDepletedUraniumCell", 4));
	  //GregTech_API.sItemList[ 54] = GregTech_API.constructRadioactiveCellItem			(sItemIDs[ 54], GT_LanguageManager.mNameListItem[54],      200, 1,  0, GT_MetaItem_Cell.instance.getUnunifiedStack(2, 1));
		try {GregTech_API.sItemList[ 55] = new GT_Debug_Item							(sItemIDs[ 55], GT_LanguageManager.mNameListItem[55]);																					} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		GregTech_API.sItemList[ 56] = GregTech_API.constructElectricEnergyStorageItem	(sItemIDs[ 56], GT_LanguageManager.mNameListItem[56],   100000,  128, 1, 56, 57).setMaxStackSize(16).setMaxDamage(0);
		GregTech_API.sItemList[ 57] = GregTech_API.constructElectricEnergyStorageItem	(sItemIDs[ 57], GT_LanguageManager.mNameListItem[57],   100000,  128, 1, 56, 57);
		GregTech_API.sItemList[ 58] = GregTech_API.constructElectricArmorItem			(sItemIDs[ 58], GT_LanguageManager.mNameListItem[58],   600000,  128, 1,  0,  0, 0.0D,  true, 1, tArmorID2);
	  //try {GregTech_API.sItemList[ 59] = Shield																																								} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		GregTech_API.sItemList[ 60] = GregTech_API.constructCoolantCellItem				(sItemIDs[ 60], GT_LanguageManager.mNameListItem[60],    60000, 1);
		GregTech_API.sItemList[ 61] = GregTech_API.constructCoolantCellItem				(sItemIDs[ 61], GT_LanguageManager.mNameListItem[61],   180000, 3);
		GregTech_API.sItemList[ 62] = GregTech_API.constructCoolantCellItem				(sItemIDs[ 62], GT_LanguageManager.mNameListItem[62],   360000, 6);
		try {GregTech_API.sItemList[ 63] = new GT_Scanner_Item							(sItemIDs[ 63], GT_LanguageManager.mNameListItem[63]);																					} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		GregTech_API.sItemList[ 64] = new GT_Crowbar_Item								(sItemIDs[ 64], GT_LanguageManager.mNameListItem[64], 500);
		GregTech_API.sItemList[ 65] = new GT_Screwdriver_Item							(sItemIDs[ 65], GT_LanguageManager.mNameListItem[65], 500);
		
        GT_Log.out.println("GT_Mod: Hiding certain Items from NEI.");
		try {
		Class.forName("codechicken.nei.api.API");
		codechicken.nei.api.API.hideItem(GregTech_API.sBlockList[3].blockID);
		if (getGregTechItem( 4, 1, 0) != null) codechicken.nei.api.API.hideItem(getGregTechItem( 4, 1, 0).itemID);
		if (getGregTechItem(13, 1, 0) != null) codechicken.nei.api.API.hideItem(getGregTechItem(13, 1, 0).itemID);
		if (getGregTechItem(14, 1, 0) != null) codechicken.nei.api.API.hideItem(getGregTechItem(14, 1, 0).itemID);
		if (getGregTechItem(15, 1, 0) != null) codechicken.nei.api.API.hideItem(getGregTechItem(15, 1, 0).itemID);
		if (getGregTechItem(16, 1, 0) != null) codechicken.nei.api.API.hideItem(getGregTechItem(16, 1, 0).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("clayDust", 1).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("coalDust", 1).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("ironDust", 1).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("goldDust", 1).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("tinDust" , 1).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("copperDust", 1).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("bronzeDust", 1).itemID);
		codechicken.nei.api.API.hideItem(GT_ModHandler.getIC2Item("silverDust", 1).itemID);
		} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
		
        GT_Log.out.println("GT_Mod: Register regular Item Names.");
		for (int i = 16; i < GregTech_API.sItemList.length; i++) {
			if (GregTech_API.sItemList[i] != null) {
				GT_LanguageManager.addStringLocalization(GregTech_API.sItemList[i].getUnlocalizedName() + ".name", GT_LanguageManager.mRegionalNameListItem[i]);
			}
		}
		
        GT_Log.out.println("GT_Mod: Registering GT/IC2-Circuitry and similar to the OreDict.");
    	GT_OreDictUnificator.registerOre("craftingLiBattery"		, getGregTechItem(56, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("craftingLiBattery"		, getGregTechItem(57, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("advancedBattery"			, getGregTechItem(56, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("advancedBattery"			, getGregTechItem(57, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("craftingCircuitTier08"	, getGregTechItem(43, 1, 0));
    	
    	GT_OreDictUnificator.registerOre("battery"					, GT_ModHandler.getIC2Item("reBattery", 1));
    	GT_OreDictUnificator.registerOre("battery"					, GT_ModHandler.getIC2Item("chargedReBattery", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("crafting60kEUPack"		, GT_ModHandler.getIC2Item("batPack", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("crafting100kEUStore"		, getGregTechItem(56, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("crafting100kEUStore"		, getGregTechItem(57, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("crafting300kEUPack"		, GT_ModHandler.getIC2Item("lapPack", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("crafting600kEUPack"		, getGregTechItem(58, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("crafting100kEUStore"		, GT_ModHandler.getIC2Item("energyCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("crafting1kkEUStore"		, GT_ModHandler.getIC2Item("lapotronCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting10kkEUStore"		, getGregTechItem(37, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting10kkEUPack"		, getGregTechItem(45, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		
		GT_OreDictUnificator.registerOre("crafting10kCoolantStore"	, GT_ModHandler.getIC2Item("reactorCoolantSimple", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting30kCoolantStore"	, GT_ModHandler.getIC2Item("reactorCoolantTriple", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting60kCoolantStore"	, GT_ModHandler.getIC2Item("reactorCoolantSix", 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting60kCoolantStore"	, getGregTechItem(60, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting60kCoolantStore"	, getGregTechItem(34, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting180kCoolantStore"	, getGregTechItem(61, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting180kCoolantStore"	, getGregTechItem(35, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting360kCoolantStore"	, getGregTechItem(62, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		GT_OreDictUnificator.registerOre("crafting360kCoolantStore"	, getGregTechItem(36, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		
    	GT_OreDictUnificator.registerOre("copperWire"		, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1));
    	GT_OreDictUnificator.registerOre("copperWire"		, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1));
    	GT_OreDictUnificator.registerOre("copperWire"		, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1));
    	
		GT_OreDictUnificator.registerOre("craftingRawMachineTier01"		, GT_ModHandler.getTEItem("machineFrame", 1));
    	GT_OreDictUnificator.registerOre("craftingRawMachineTier01"		, GT_ModHandler.getIC2Item("machine", 1));
    	GT_OreDictUnificator.registerOre("craftingRawMachineTier02"		, GT_ModHandler.getIC2Item("advancedMachine", 1));
    	GT_OreDictUnificator.registerOre("craftingRawMachineTier04"		, new ItemStack(GregTech_API.sBlockList[0], 1, 10));
		
    	GT_OreDictUnificator.registerOre("craftingCircuitTier00"		, new ItemStack(Block.torchRedstoneIdle, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("craftingCircuitTier00"		, new ItemStack(Block.torchRedstoneActive, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("craftingCircuitTier00"		, new ItemStack(Block.lever, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
    	GT_OreDictUnificator.registerOre("basicCircuit"					, GT_ModHandler.getIC2Item("electronicCircuit", 1));
    	GT_OreDictUnificator.registerOre("advancedCircuit"				, GT_ModHandler.getIC2Item("advancedCircuit", 1));
    	GT_OreDictUnificator.registerOre("craftingCircuitTier10"		, new ItemStack(GregTech_API.sBlockList[1], 1, 4));
    	
    	GT_OreDictUnificator.registerOre("craftingWorkBench"			, new ItemStack(Block.workbench, 1));
    	GT_OreDictUnificator.registerOre("craftingWorkBench"			, new ItemStack(GregTech_API.sBlockList[1], 1, 16));
    	
    	GT_OreDictUnificator.registerOre("craftingPump"					, GT_ModHandler.getIC2Item("pump", 1));
    	GT_OreDictUnificator.registerOre("craftingElectromagnet"		, GT_ModHandler.getIC2Item("magnetizer", 1));
    	GT_OreDictUnificator.registerOre("craftingTeleporter"			, GT_ModHandler.getIC2Item("teleporter", 1));
    	
    	GT_OreDictUnificator.registerOre("craftingMacerator"			, GT_ModHandler.getTEItem("pulverizer", 1));
    	GT_OreDictUnificator.registerOre("craftingMacerator"			, GT_ModHandler.getIC2Item("macerator", 1));
    	GT_OreDictUnificator.registerOre("craftingMacerator"			, new ItemStack(GregTech_API.sBlockList[1], 1,50));
    	
    	GT_OreDictUnificator.registerOre("craftingExtractor"			, GT_ModHandler.getIC2Item("extractor", 1));
    	GT_OreDictUnificator.registerOre("craftingExtractor"			, new ItemStack(GregTech_API.sBlockList[1], 1,51));
    	
    	GT_OreDictUnificator.registerOre("craftingCompressor"			, GT_ModHandler.getIC2Item("compressor", 1));
    	GT_OreDictUnificator.registerOre("craftingCompressor"			, new ItemStack(GregTech_API.sBlockList[1], 1,52));
    	
    	GT_OreDictUnificator.registerOre("craftingRecycler"				, GT_ModHandler.getIC2Item("recycler", 1));
    	GT_OreDictUnificator.registerOre("craftingRecycler"				, new ItemStack(GregTech_API.sBlockList[1], 1,53));
    	
    	GT_OreDictUnificator.registerOre("craftingElectricFurnace"		, GT_ModHandler.getIC2Item("electroFurnace", 1));
    	GT_OreDictUnificator.registerOre("craftingElectricFurnace"		, new ItemStack(GregTech_API.sBlockList[1], 1,54));
    	
    	GT_OreDictUnificator.registerOre("paperVanilla"			, new ItemStack(Item.paper, 1));
    	GT_OreDictUnificator.registerOre("paperMap"				, new ItemStack(Item.emptyMap, 1));
    	GT_OreDictUnificator.registerOre("paperMap"				, new ItemStack(Item.map, 1));
    	GT_OreDictUnificator.registerOre("bookVanilla"			, new ItemStack(Item.book, 1));
    	GT_OreDictUnificator.registerOre("bookWritable"			, new ItemStack(Item.writableBook, 1));
    	GT_OreDictUnificator.registerOre("bookWritten"			, new ItemStack(Item.writtenBook, 1));
    	GT_OreDictUnificator.registerOre("bookEnchanted"		, new ItemStack(Item.enchantedBook, 1));
    	
        GT_Log.out.println("GT_Mod: Register colors to the OreDict.");
    	GT_OreDictUnificator.registerOre("dyeCyan"				, GT_MetaItem_Dust.instance.getUnunifiedStack(2, 1));
    	GT_OreDictUnificator.registerOre("dyeBlue"				, GT_MetaItem_Dust.instance.getUnunifiedStack(5, 1));
    	
        GT_Log.out.println("GT_Mod: Send InterModCommunication to mistaqurs NEI Plugins.");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Alloy Smelter"				+"@"+"gregtech.Alloy");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Assembling Machine"		+"@"+"gregtech.Assembler");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Plate Bending Machine"		+"@"+"gregtech.Bender");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Industrial Blast Furnace"	+"@"+"gregtech.Blast");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Automatic Canning Machine"	+"@"+"gregtech.Canner");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Industrial Centrifuge"		+"@"+"gregtech.Centrifuge");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Chemical Reactor"			+"@"+"gregtech.Chemicalreactor");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Distillation Tower"		+"@"+"gregtech.Distillation");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Industrial Electrolyzer"	+"@"+"gregtech.Electrolyzer");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Fusion Reactor"			+"@"+"gregtech.Fusionreactor");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Industrial Grinder"		+"@"+"gregtech.Grinder");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Implosion Compressor"		+"@"+"gregtech.Implosion");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Industrial Sawmill"		+"@"+"gregtech.Sawmill");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Vacuum Freezer"			+"@"+"gregtech.VacuumFreezer");
    	FMLInterModComms.sendRuntimeMessage(GregTech_API.gregtechmod, "NEIPlugins", "register-crafting-handler", GregTech_API.MOD_ID+"@"+"Wiremill"					+"@"+"gregtech.Wiremill");
    	
    	mLoaded = true;
        GT_Log.out.println("GT_Mod: Load-Phase finished!");
	}
	
    @PostInit
    public void postload(FMLPostInitializationEvent aEvent) {
    	if (mDoNotInit) return;
    	checkVersions();
        GT_Log.out.println("GT_Mod: Beginning PostLoad-Phase.");
        
        GT_Log.out.println("GT_Mod: Initializing Proxy.");
		gregtechproxy.initialize();
		
		ItemStack tStack, tStack2, tStack3;
		String tName;
		LiquidStack tLiquid;
		Item tItem;
		boolean temp = false;
		
        GT_Log.out.println("GT_Mod: Checking if Items got Overloaded.");
		for (int i = 0; i < GregTech_API.sItemList.length; i++) {
			if (GregTech_API.sItemList[i] != null && Item.itemsList[GregTech_API.sItemList[i].itemID] != GregTech_API.sItemList[i]) {
		        GT_Log.err.println("GT_Mod: Another Mods ItemID is conflicting.");
		        GT_Log.err.println("GT_Mod: Errored.");
				throw new GT_ItsNotMyFaultException("One of the GregTech-Items got overloaded. Check the ID-Config of the Mod you just installed for Conflicts mentioned in the Log with 'CONFLICT @'. That is an Item-ID-Conflict! Don't bother ANY Modauthor with that, we most likely won't help you at all!");
			}
		}
		
        GT_Log.out.println("GT_Mod: Scanning for certain kinds of compatible Machineblocks.");
		if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2 = GT_OreDictUnificator.get("ingotBronze", 1), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2}))) {
			GT_OreDictUnificator.registerOre("craftingRawMachineTier01", tStack);
			GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get("dustBronze" , 8), null, 0, false);
			GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get("ingotBronze", 8));
		}
		if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2 = GT_OreDictUnificator.get("ingotRefinedIron", 1), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2}))) {
			GT_OreDictUnificator.registerOre("craftingRawMachineTier01", tStack);
			GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get("dustRefinedIron", GT_OreDictUnificator.get("dustIron", 8), 8), null, 0, false);
			GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get("ingotRefinedIron", 8));
		}
		if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2 = GT_OreDictUnificator.get("plateBronze", 1), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2}))) {
			GT_OreDictUnificator.registerOre("craftingRawMachineTier01", tStack);
			GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get("dustBronze" , 8), null, 0, false);
			GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get("ingotBronze", 8));
		}
		if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2 = GT_OreDictUnificator.get("plateRefinedIron", 1), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2}))) {
			GT_OreDictUnificator.registerOre("craftingRawMachineTier01", tStack);
			GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get("dustRefinedIron", GT_OreDictUnificator.get("dustIron", 8), 8), null, 0, false);
			GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get("ingotRefinedIron", 8));
		}
		if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2 = GT_OreDictUnificator.get("ingotIron", 1), tStack3 = new ItemStack(Block.glass, 1, 0), tStack2, tStack3, GT_OreDictUnificator.get("ingotGold", 1), tStack3, tStack2, tStack3, tStack2}))) {
			GT_OreDictUnificator.registerOre("craftingRawMachineTier01", tStack);
			GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get("dustIron", 4), GT_OreDictUnificator.get("dustGold", 1), 0, false);
		}
		
        GT_Log.out.println("GT_Mod: Adding Food Recipes to the Automatic Canning Machine. (also during the following Item Iteration)");
		addCannerRecipe(new ItemStack(Item.rottenFlesh, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("tinCan", 2), GT_ModHandler.getIC2Item("filledTinCan", 2, 1), null, 200, 1);
		addCannerRecipe(new ItemStack(Item.spiderEye, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("tinCan", 1), GT_ModHandler.getIC2Item("filledTinCan", 1, 1), null, 100, 1);
		addCannerRecipe(new ItemStack(Item.poisonousPotato, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("tinCan", 1), GT_ModHandler.getIC2Item("filledTinCan", 1, 1), null, 100, 1);
		addCannerRecipe(new ItemStack(Item.cake, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("tinCan", 6), GT_ModHandler.getIC2Item("filledTinCan", 6, 0), null, 600, 1);
		addCannerRecipe(new ItemStack(Block.cake, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("tinCan", 6), GT_ModHandler.getIC2Item("filledTinCan", 6, 0), null, 600, 1);
		addCannerRecipe(new ItemStack(Item.bowlSoup, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("tinCan", 3), GT_ModHandler.getIC2Item("filledTinCan", 3, 0), new ItemStack(Item.bowlEmpty, 1), 300, 1);
		
        GT_Log.out.println("GT_Mod: Scanning ItemList.");
		for (int i = 0; i < Item.itemsList.length; i++) if ((tItem = Item.itemsList[i]) != null && (tName = tItem.getUnlocalizedName()) != null) {
			if (tItem instanceof ItemFood && tItem.itemID != GT_ModHandler.getIC2Item("filledTinCan", 1, 0).itemID) {
		        int tFoodValue = (int)Math.ceil(((ItemFood)tItem).getHealAmount() / 2.0D);
		        if (tFoodValue > 0) {
		        	addCannerRecipe(new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("tinCan", tFoodValue), GT_ModHandler.getIC2Item("filledTinCan", tFoodValue, 0), tItem.hasContainerItem()?new ItemStack(tItem.getContainerItem(), 1, 0):null, tFoodValue*100, 1);
		        }
		    }
		    if (tName.equals("item.myst.page")) {
				GT_OreDictUnificator.registerOre("paperMystcraft", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.myst.agebook")) {
				GT_OreDictUnificator.registerOre("bookMystcraftAge", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.myst.linkbook")) {
				GT_OreDictUnificator.registerOre("bookMystcraftLink", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.myst.notebook")) {
				GT_OreDictUnificator.registerOre("bookNotes", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.itemManuelBook")) {
				GT_OreDictUnificator.registerOre("bookWritten", new ItemStack(tItem, 1, 0));
		    }
		    if (tName.equals("item.blueprintItem")) {
				GT_OreDictUnificator.registerOre("paperBlueprint", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.ccprintout")) {
				GT_OreDictUnificator.registerOre("paperWritten", new ItemStack(tItem, 1, 0));
				GT_OreDictUnificator.registerOre("paperWritten", new ItemStack(tItem, 1, 1));
				GT_OreDictUnificator.registerOre("bookWritten", new ItemStack(tItem, 1, 2));
		    }
		    if (tName.equals("item.blueprintItem")) {
				GT_OreDictUnificator.registerOre("paperBlueprint", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.wirelessmap")) {
				GT_OreDictUnificator.registerOre("paperMap", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.ItemResearchNotes")) {
				GT_OreDictUnificator.registerOre("paperResearch", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    if (tName.equals("item.ItemThaumonomicon")) {
				GT_OreDictUnificator.registerOre("bookThaumonomicon", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
		    }
		    
			if (tName.equals("item.ItemEssence")) {
				GT_ModHandler.mTCFluxEssence = new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE);
			}
			if (tName.equals("item.ItemWispEssence")) {
				GT_ModHandler.mTCWispEssence = new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE);
			}
			if (tName.equals("item.ItemResource")) {
				GT_ModHandler.mTCResource = new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE);
				GT_OreDictUnificator.registerOre("itemQuicksilver", new ItemStack(tItem, 1, 3));
				GT_OreDictUnificator.registerOre("paperResearchFragment", new ItemStack(tItem, 1, 9));
			}
			if (tName.equals("item.ItemShard")) {
				GT_ModHandler.mTCCrystal = new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE);
			}
			if (tName.equals("item.ItemEnderDust")) {
				GT_OreDictUnificator.registerOre("dustEnderPearl", new ItemStack(tItem, 1, 0));
			}
			if (tName.equals("tile.extrabiomes.redrock") && tItem.itemID < 4096) {
				GT_OreDictUnificator.registerOre("stoneRedRock", new ItemStack(tItem, 1,  0));
				GT_OreDictUnificator.registerOre("stoneRedRock", new ItemStack(tItem, 1,  1));
				GT_OreDictUnificator.registerOre("stoneRedRock", new ItemStack(tItem, 1,  2));
			}
			if (tName.equals("tile.rpstone") && tItem.itemID < 4096) {
				GT_OreDictUnificator.registerOre("stoneMarble", new ItemStack(tItem, 1, 0));
				GT_OreDictUnificator.registerOre("stoneBasalt", new ItemStack(tItem, 1, 1));
				GT_OreDictUnificator.registerOre("stoneMarble", new ItemStack(tItem, 1, 2));
				GT_OreDictUnificator.registerOre("stoneBasalt", new ItemStack(tItem, 1, 3));
				GT_OreDictUnificator.registerOre("stoneBasalt", new ItemStack(tItem, 1, 4));
				GT_OreDictUnificator.registerOre("stoneBasalt", new ItemStack(tItem, 1, 5));
				GT_OreDictUnificator.registerOre("stoneBasalt", new ItemStack(tItem, 1, 6));
			}
			
			if (tName.equals("tile.enderchest")) {
				GT_OreDictUnificator.registerOre("craftingEnderChest", new ItemStack(tItem, 1, GregTech_API.ITEM_WILDCARD_DAMAGE));
			}
			if (tName.equals("tile.autoWorkbenchBlock")) {
				GT_OreDictUnificator.registerOre("craftingWorkBench", new ItemStack(tItem, 1, 0));
			}
			if (tName.equals("tile.pumpBlock")) {
				GT_OreDictUnificator.registerOre("craftingPump", new ItemStack(tItem, 1, 0));
			}
			if (tName.equals("tile.tankBlock")) {
				GT_OreDictUnificator.registerOre("craftingTank", new ItemStack(tItem, 1, 0));
			}
			
			if (tName.equals("item.drawplateDiamond"))	GT_ModHandler.mDiamondDrawplate	= new ItemStack(tItem, 1);
			
			if (tName.equals("item.woodenGearItem"))	GT_ModHandler.mBCWoodGear		= new ItemStack(tItem, 1);
			if (tName.equals("item.stoneGearItem"))		GT_ModHandler.mBCStoneGear		= new ItemStack(tItem, 1);
			if (tName.equals("item.ironGearItem"))		GT_ModHandler.mBCIronGear		= new ItemStack(tItem, 1);
			if (tName.equals("item.goldGearItem"))		GT_ModHandler.mBCGoldGear		= new ItemStack(tItem, 1);
			if (tName.equals("item.diamondGearItem"))	GT_ModHandler.mBCDiamondGear	= new ItemStack(tItem, 1);
			
			if (tName.equals("item.canLava"))			addCentrifugeRecipe(new ItemStack(tItem, 16), 0, GT_OreDictUnificator.get("ingotElectrum", 1), GT_OreDictUnificator.get("ingotCopper", 4), GT_OreDictUnificator.get("dustSmallTungsten", 1), GT_OreDictUnificator.get("ingotTin", 6), 15000);
			if (tName.equals("item.refractoryLava"))	addCentrifugeRecipe(new ItemStack(tItem, 16), 0, GT_OreDictUnificator.get("ingotElectrum", 1), GT_OreDictUnificator.get("ingotCopper", 4), GT_OreDictUnificator.get("dustSmallTungsten", 1), GT_OreDictUnificator.get("ingotTin", 2), 10000);
		}
		
        GT_Log.out.println("GT_Mod: Getting Storage Blocks of Redpower for the OreDictUnification.");
        if (GT_ModHandler.mNikolite != null) {
        	GT_OreDictUnificator.set("dustNikolite", GT_ModHandler.mNikolite, sUnificatorRP);
        	tStack2 = GT_ModHandler.mRuby;
        	tStack3 = GT_MetaItem_Material.instance.getUnunifiedStack(32, 1);
	        if (tStack2 != null) {
	        	GT_ModHandler.removeRecipe(new ItemStack[] {tStack2,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3});
	        	if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2}))) {
		        	GT_OreDictUnificator.set("blockRuby", tStack, sUnificatorRP);
	        	}
	        }
        	tStack2 = GT_ModHandler.mSapphire;
        	tStack3 = GT_MetaItem_Material.instance.getUnunifiedStack(33, 1);
	        if (tStack2 != null) {
	        	GT_ModHandler.removeRecipe(new ItemStack[] {tStack2,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3});
	        	if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2}))) {
		        	GT_OreDictUnificator.set("blockSapphire", tStack, sUnificatorRP);
	        	}
	        }
        	tStack2 = GT_ModHandler.mGreenSapphire;
        	tStack3 = GT_MetaItem_Material.instance.getUnunifiedStack(34, 1);
	        if (tStack2 != null) {
	        	GT_ModHandler.removeRecipe(new ItemStack[] {tStack2,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3});
	        	if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2}))) {
		        	GT_OreDictUnificator.set("blockGreenSapphire", tStack, sUnificatorRP);
	        	}
	        }
        	tStack2 = GT_ModHandler.mSilver;
        	tStack3 = GT_MetaItem_Material.instance.getUnunifiedStack(17, 1);
	        if (tStack2 != null) {
	        	GT_ModHandler.removeRecipe(new ItemStack[] {tStack2,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3});
	        	if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2}))) {
		        	GT_OreDictUnificator.set("blockSilver", tStack, sUnificatorRP);
	        	}
	        }
        	tStack2 = GT_ModHandler.mCopper;
        	tStack3 = GT_ModHandler.getIC2Item("copperIngot", 1);
	        if (tStack2 != null) {
	        	GT_ModHandler.removeRecipe(new ItemStack[] {tStack2,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3});
	        	if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2}))) {
		        	GT_OreDictUnificator.set("blockCopper", tStack, sUnificatorRP);
	        	}
	        }
        	tStack2 = GT_ModHandler.mTin;
        	tStack3 = GT_ModHandler.getIC2Item("tinIngot", 1);
	        if (tStack2 != null) {
	        	GT_ModHandler.removeRecipe(new ItemStack[] {tStack2,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3});
	        	if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2}))) {
		        	GT_OreDictUnificator.set("blockTin", tStack, sUnificatorRP);
	        	}
	        }
        	tStack2 = GT_ModHandler.mBrass;
        	tStack3 = GT_MetaItem_Material.instance.getUnunifiedStack(25, 1);
	        if (tStack2 != null) {
	        	GT_ModHandler.removeRecipe(new ItemStack[] {tStack2,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3,tStack3});
	        	if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2,tStack2}))) {
		        	GT_OreDictUnificator.set("blockBrass", tStack, sUnificatorRP);
	        	}
	        }
        }
        
        GT_Log.out.println("GT_Mod: Grabbing Liquids of other Mods to register Liquid Cells, and adding Liquid Transposer Recipes for them");
		if (null != (tLiquid = LiquidDictionary.getLiquid("Oil"			, 1000))) {LiquidContainerRegistry.registerLiquid(new LiquidContainerData(tLiquid, tStack = GT_MetaItem_Cell.instance.getUnunifiedStack(17, 1), GT_ModHandler.getEmptyCell(1))); GT_ModHandler.addLiquidTransposerRecipe(GT_ModHandler.getEmptyCell(1), tLiquid, tStack, 160);}
		if (null != (tLiquid = LiquidDictionary.getLiquid("Fuel"		, 1000))) {LiquidContainerRegistry.registerLiquid(new LiquidContainerData(tLiquid, tStack = GT_MetaItem_Cell.instance.getUnunifiedStack(18, 1), GT_ModHandler.getEmptyCell(1))); GT_ModHandler.addLiquidTransposerRecipe(GT_ModHandler.getEmptyCell(1), tLiquid, tStack, 160);}
		if (null != (tLiquid = LiquidDictionary.getLiquid("biofuel"		, 1000))) {LiquidContainerRegistry.registerLiquid(new LiquidContainerData(tLiquid, tStack = GT_MetaItem_Cell.instance.getUnunifiedStack(19, 1), GT_ModHandler.getEmptyCell(1))); GT_ModHandler.addLiquidTransposerRecipe(GT_ModHandler.getEmptyCell(1), tLiquid, tStack, 160);}
		if (null != (tLiquid = LiquidDictionary.getLiquid("biomass"		, 1000))) {LiquidContainerRegistry.registerLiquid(new LiquidContainerData(tLiquid, tStack = GT_MetaItem_Cell.instance.getUnunifiedStack(20, 1), GT_ModHandler.getEmptyCell(1))); GT_ModHandler.addLiquidTransposerRecipe(GT_ModHandler.getEmptyCell(1), tLiquid, tStack, 160);}
		if (null != (tLiquid = LiquidDictionary.getLiquid("ice"			, 1000))) {LiquidContainerRegistry.registerLiquid(new LiquidContainerData(tLiquid, tStack = GT_MetaItem_Cell.instance.getUnunifiedStack(23, 1), GT_ModHandler.getEmptyCell(1))); GT_ModHandler.addLiquidTransposerRecipe(GT_ModHandler.getEmptyCell(1), tLiquid, tStack, 160);}
		if (null != (tLiquid = LiquidDictionary.getLiquid("seedoil"		, 1000))) {LiquidContainerRegistry.registerLiquid(new LiquidContainerData(tLiquid, tStack = GT_MetaItem_Cell.instance.getUnunifiedStack(24, 1), GT_ModHandler.getEmptyCell(1))); GT_ModHandler.addLiquidTransposerRecipe(GT_ModHandler.getEmptyCell(1), tLiquid, tStack, 160);}
		GT_MetaItem_Gas.addItem(127, "Water", "Steam", "H2O", null, null);
		
        GT_Log.out.println("GT_Mod: Initializing various Fuels.");
		addFuel(GT_ModHandler.getIC2Item("biofuelCell", 1)		, GT_ModHandler.getEmptyCell(1),   6, 0);
		addFuel(GT_ModHandler.getIC2Item("coalfuelCell", 1)		, GT_ModHandler.getEmptyCell(1),  16, 0);
		addFuel(GT_MetaItem_Cell.instance.getStack(19, 1)		, GT_ModHandler.getEmptyCell(1),  32, 0);
		addFuel(GT_MetaItem_Cell.instance.getStack(35, 1)		, GT_ModHandler.getEmptyCell(1),  48, 0);
		addFuel(GT_MetaItem_Cell.instance.getStack(18, 1)		, GT_ModHandler.getEmptyCell(1), 384, 0);
		addFuel(GT_MetaItem_Cell.instance.getStack(22, 1)		, GT_ModHandler.getEmptyCell(1), 400, 0);
		
		addFuel(GT_MetaItem_Cell.instance.getStack( 0, 1)		, GT_ModHandler.getIC2Item("waterCell", 1),  15, 1);
		addFuel(GT_MetaItem_Cell.instance.getStack( 9, 1)		, GT_ModHandler.getIC2Item("waterCell", 1),  45, 1);
		
		addFuel(GT_ModHandler.getIC2Item("lavaCell", 1)			, GT_ModHandler.getEmptyCell(1),  30, 2);
		addFuel(GT_ModHandler.getIC2Item("reactorHeatpack", 1)	, GT_ModHandler.getEmptyCell(1),  30, 2);
		
		addFuel(GT_MetaItem_Cell.instance.getStack(24, 1)		, GT_ModHandler.getEmptyCell(1),   2, 3);
		addFuel(GT_ModHandler.getRCItem("liquid.creosote.cell", 1), GT_ModHandler.getEmptyCell(1), 3, 3);
		addFuel(GT_MetaItem_Cell.instance.getStack(20, 1)		, GT_ModHandler.getEmptyCell(1),   8, 3);
		addFuel(GT_MetaItem_Cell.instance.getStack(12, 1)		, GT_ModHandler.getEmptyCell(1),  30, 3);
		addFuel(GT_MetaItem_Cell.instance.getStack( 5, 1)		, GT_ModHandler.getEmptyCell(1),  60, 3);
		addFuel(GT_MetaItem_Cell.instance.getStack(17, 1)		, GT_ModHandler.getEmptyCell(1),  64, 3);
		
		addFuel(GT_MetaItem_Cell.instance.getStack(16, 1)		, GT_ModHandler.getEmptyCell(1),   8, 5);
		addFuel(new ItemStack(Item.eyeOfEnder, 1)				, null,     20, 5);
		addFuel(new ItemStack(Item.netherStar, 1)				, null, 100000, 5);
		addFuel(new ItemStack(Block.beacon, 1)					, null, 100000, 5);
		
		if (GT_ModHandler.mTCResource != null) {
			addFuel(new ItemStack(GT_ModHandler.mTCResource.getItem(), 1, 3), null, 8, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCResource.getItem(), 1, 4), null, 2, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCResource.getItem(), 1, 6), null, 3, 5);
		}
		
		if (GT_ModHandler.mTCCrystal != null) {
			addFuel(new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 0), new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 5), 160, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 1), new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 5), 320, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 2), new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 5), 160, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 3), new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 5), 160, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 4), new ItemStack(GT_ModHandler.mTCCrystal.getItem(), 1, 5), 240, 5);
		}
		if (GT_ModHandler.mTCFluxEssence != null) {
			addFuel(new ItemStack(GT_ModHandler.mTCFluxEssence.getItem(), 1,  7), new ItemStack(GT_ModHandler.mTCFluxEssence.getItem(), 1, 0),  80, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCFluxEssence.getItem(), 1, 10), new ItemStack(GT_ModHandler.mTCFluxEssence.getItem(), 1, 0), 160, 5);
			addFuel(new ItemStack(GT_ModHandler.mTCFluxEssence.getItem(), 1, 41), new ItemStack(GT_ModHandler.mTCFluxEssence.getItem(), 1, 0), 120, 5);
		}
		
		GT_ModHandler.addBoilerFuel(new LiquidStack(GT_MetaItem_Gas		.instance.getStack( 0, 1).itemID, 1000, GT_MetaItem_Gas		.instance.getStack( 0, 1).getItemDamage()),  2000);
		GT_ModHandler.addBoilerFuel(new LiquidStack(GT_MetaItem_Gas		.instance.getStack( 9, 1).itemID, 1000, GT_MetaItem_Gas		.instance.getStack( 9, 1).getItemDamage()),  3000);
		GT_ModHandler.addBoilerFuel(new LiquidStack(GT_MetaItem_Liquid	.instance.getStack(35, 1).itemID, 1000, GT_MetaItem_Liquid	.instance.getStack(35, 1).getItemDamage()), 18000);
		GT_ModHandler.addBoilerFuel(new LiquidStack(GT_MetaItem_Liquid	.instance.getStack( 5, 1).itemID, 1000, GT_MetaItem_Liquid	.instance.getStack( 5, 1).getItemDamage()), 24000);
		
        GT_Log.out.println("GT_Mod: Changing maximum Stacksizes if configured.");
    	GT_ModHandler.getIC2Item("overclockerUpgrade", 1).getItem().setMaxStackSize(sUpgradeCount);
    	Item.cake.setMaxStackSize(64);
    	Item.itemsList[Block.woodSingleSlab.blockID].setMaxStackSize(sPlankStackSize);
    	Item.itemsList[Block.woodDoubleSlab.blockID].setMaxStackSize(sPlankStackSize);
    	Item.itemsList[Block.stairsWoodOak.blockID].setMaxStackSize(sPlankStackSize);
    	Item.itemsList[Block.stairsWoodBirch.blockID].setMaxStackSize(sPlankStackSize);
    	Item.itemsList[Block.stairsWoodJungle.blockID].setMaxStackSize(sPlankStackSize);
    	Item.itemsList[Block.stairsWoodSpruce.blockID].setMaxStackSize(sPlankStackSize);
    	Item.itemsList[Block.leaves.blockID].setMaxStackSize(sWoodStackSize);
    	
    	Item.itemsList[Block.stoneSingleSlab.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.stoneDoubleSlab.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.stairsBrick.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.stairsNetherBrick.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.stairsSandStone.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.stairsStoneBrick.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.stairsCobblestone.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.ice.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.slowSand.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.glowStone.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.snow.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.blockSnow.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.blockSteel.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.blockGold.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.blockEmerald.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.blockLapis.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.blockDiamond.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.blockClay.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.redstoneLampIdle.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.redstoneLampActive.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.dirt.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.grass.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.mycelium.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.gravel.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.sand.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.brick.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.cloth.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.melon.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.pumpkin.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.pumpkinLantern.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.dispenser.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.obsidian.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.pistonBase.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.pistonStickyBase.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.workbench.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.glass.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.jukebox.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.anvil.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.jukebox.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.chest.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.music.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.mobSpawner.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.bookShelf.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.furnaceIdle.blockID].setMaxStackSize(sBlockStackSize);
    	Item.itemsList[Block.furnaceBurning.blockID].setMaxStackSize(sBlockStackSize);
    	
        GT_Log.out.println("GT_Mod: Adding worldgenerated Chest Content.");
        
        ChestGenHooks tChest;
        tChest = ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST				); tChest.setMax(tChest.getMax()+ 8); tChest.setMin(tChest.getMin()+ 4);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST				); tChest.setMax(tChest.getMax()+ 6); tChest.setMin(tChest.getMin()+ 3);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST		); tChest.setMax(tChest.getMax()+ 8); tChest.setMin(tChest.getMin()+ 4);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST		); tChest.setMax(tChest.getMax()+12); tChest.setMin(tChest.getMin()+ 6);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER	); tChest.setMax(tChest.getMax()+ 2); tChest.setMin(tChest.getMin()+ 1);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR			); tChest.setMax(tChest.getMax()+ 4); tChest.setMin(tChest.getMin()+ 2);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH			); tChest.setMax(tChest.getMax()+12); tChest.setMin(tChest.getMin()+ 6);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING		); tChest.setMax(tChest.getMax()+ 8); tChest.setMin(tChest.getMin()+ 4);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR		); tChest.setMax(tChest.getMax()+ 6); tChest.setMin(tChest.getMin()+ 3);
        tChest = ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY			); tChest.setMax(tChest.getMax()+16); tChest.setMin(tChest.getMin()+ 8);
        
        tStack = GT_Utility.getWrittenBook("GregTech Manual I", "Gregorius Techneticies", new String[] {
        		  "So, this is probably your first time using a Product of GregTech Intergalactical, so you might ask yourself where to begin with? This World is very complex, and you may want to aquire a few basic Resources first."
        		, "You need many Resources to start with, as there are: Wood, Sticky Resin or Slimeballs, Sand, Silk, Food, Iron, Copper, Tin, Flint, Redstone, Coal (or another good Fuel for the Oven) and a bunch of Cobblestones would also be very useful."
        		, "All Resources aquired? Good, then cook your Ores, Food and Slimeballs in your Stone Oven, and build a Bed so that you can rest. Then you might need a Mortar for the production of Bronze Dust. Bronze is much better than wasting Iron."
        		, "So, now that you have a set of Tools, you can begin to make an Iron Oven out of your old Stone Oven. And then you can begin to produce Refined Iron, by smelting Iron Ingots again. Also smelt a few clumps of Sticky Resin into Rubber."
        		, "Congratulations, you now have all you need for your first Machine, the Extractor. With it, you can get more Rubber from Resin. But you need to power it, so craft yourself a Generator out of your old Iron Furnace."
        		, "So, now that you can electrically extract Rubber, you might want to smelt your Stuff using electric Energy, so just build an electric Furnace out of another Iron Furnace. For the next task you need at least one Compressor."
        		, "All the Machines we made until now were very basic and not really efficient. That is because you cant just smash a few Ingots together to get properly working Machines. You need Plates. And for Plates we need a Plate Bending Machine."
        		, "To make Plate Bending Machines, you need Pistons, Compressors and Conveyor Modules. With this Machine you can now properly flatten all Ingots into the Plates. Those Plates are the core Component for all GregTech Machines."
        		, "You might have found enough Diamonds for a first Macerator during any of these Steps. If not then, you might want to search for them as they are definetly needed for progressing further."
        		});
        if (tStack != null) {
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST				, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,  20));
        tStack = null;
        }

        tStack = GT_Utility.getWrittenBook("GregTech Manual II", "Gregorius Techneticies", new String[] {
        		  "You have your Basic Workshop and want to do amazing things with it? You wan't to build your first Fusion Reactor? Well, this is the wrong Book for that, but we are working on it. Before that we definetly need better ways of making Circuitry."
        		, "The Assembling Machine is perfectly suited for that. It can create Circuitry much cheaper than a normal Steve could do. If you don't have the needed Materials then go mining, and come back when you have crafted the Assembling Machine."
        		, "This Device cuts down your Copper Cable Costs and gives you the possibility to use more advanced Materials for Circuitry. The Assembler might need some time for that, but it's the quality which counts. Well, and you get more quantity."
        		, "You got it? Now build a Wiremill to make more Cables from Copper than you usually do by handcrafting. This increases your efficiency in producing Machines extremly. And people still complain about being too expensive..."
        		, "And now we can go to some of the advanced Functions of these Devices. They can automatically output their Content to adjacent Inventories, but not only that, they can also output their contained Energy to adjacent Machines!"
        		, "you just need to wrench the output Facing into the correct direction, and keep the first two Buttons inside the GUI enabled. You can also upgrade Macerator, Extractor, Compressor and E-Furnace, using a Conveyor Module to get these abilities."
        		, "Now we go for the Alloy Smelter. Have you been tired of macerating, crafting and smelting Metals to get Alloys? This Machine is the perfect way to get Alloys in just one step! Just put your Ingots into it, and it will smelt them together."
        		, "The Printing Factory, is a perfect Way of printing your Papers. This Book was printed using such a Machine. It can not only copy Books or create Maps, it can also make Paper from Reeds or Wood Pulp, and is also capable of Coloring your Wool!"
        		, "Also interessting are Industrial Electrolyzer and Industrial Centrifuge. They are capable of seperating the components of several Dusts or Liquids. The Centrifuge can work with pumped Lava to produce several nice Metals from it."
        		});
        if (tStack != null) {
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST				, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,  15));
        tStack = null;
        }
        
        tStack = GT_Utility.getWrittenBook("GregTech Machine Safety", "Gregorius Techneticies", new String[] {
        		  "As you probably know, every electric Machine can be quite fragile under certain circumstances. This Manual explains why your Machines could explode or burn down your Workshop, and how to prevent it."
        		, "1. Do not use a Pickaxe or something else, what is not a Wrench, to dismantle an electric Machine. This will cause either an Explosion or a just broken Machine if you are very lucky."
        		, "2. Keep your Machines away from any explosives! Every electric Machine will cause a chain reaction of Explosions when being broken like that."
        		, "3. Keep your Machines away from Fire. The are inflammable and will explode, if exposed too long to adjacent Fires."
        		, "4. Do not place your Machines outside. They must have a Roof or Rain and Thunder Storms will set them on Fire. A Cover ontop of an electric Machine is sufficient to prevent that, so your Solar Panels are automatically safe."
      			, "5. Make sure, that the Maximum Input Voltage of a Machine is not exceeded. That will cause an immidiate Explosion."
      			, "6. Do not let your Machines explode. If that happens, the Machine will overcharge the adjacent Wiring, what will cause all connected Machines to explode, unless they can receive 8192EU/p."
      			});
	    if (tStack != null) {
	    ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST				, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
	    ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
	    ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
	    ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
	    ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
	    ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,  20));
	    tStack = null;
	    }
	    
        tStack = GT_Utility.getWrittenBook("Cover up!", "Gregorius Techneticies", new String[] {
        		  "Have you ever wondered how the Cover System works? This Book is about how to Cover your Machines up properly. But what does a Cover do, except fancy looking? And how to install Covers? And what If you want to remove the Cover?"
        		, "Usually every regular Plate like Advanced Alloy, Refined Iron or Bronze Plates can be used as Cover. Even Covers of other Companies can be supported as well. To place a Cover, just get one and rightclick the Machine with it."
        		, "Covers can do many things such as impoving Machines (see Upgrade Dictionary for Details), but their main purpose is preventing interactions between adjacent Blocks."
        		, "To remove a Cover, you need a Blue Crowbar or something similar, like a Red Crowbar for example. Just use the Crowbar on it, to get it back. If you want to adjust Upgrade Covers, you need a Screwdriver. Just screw around until you are set."
        		, "If you are low on Materials, but really need a Cover, then just take your Screwdriver and unscrew the outer hull of the Machine, turn it around, screw it back on and viola, you got a Cover for free."
        		});
        if (tStack != null) {
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,  10));
        tStack = null;
        }
        
        tStack = GT_Utility.getWrittenBook("Greg-OS Manual I", "Gregorius Techneticies", new String[] {
        		  "Redstone Circuits are the main Component of a well running Base. The GregTech Redstone Circuit Block is ideal for processing Logic. It can compute everything! From a simple and consistent Timer to the Combo Lock on your Door!"
        		, "It has a very simple interface. You just need to push as few Buttons, and you are good to go. Just don't forget to push the Start-Button right under the Energy Flow Button, after you adjusted your Configuration."
        		, "Every Redstone Circuit has a diffrent Energy consumption. Yes, running this Device needs Energy, as indicated by the small blinking Lightning Bolt on the Screen. The needed Energy is just one Energy Unit per outputted Redstone Pulse."
        		, "To switch between diffrent Redstone Circuits, just push the Button under the start Button. It looks like two sidewards pointing Arrows. The Lightning Bolt Button causes the Machine to output its Energy to the Block on its output Side."
        		, "The four Buttons on the Left are controlling the System Parameters of the Redstone Circuit. You only need to right/leftclick them to adjust the Value. If you have an ItemStack in your Hand, then it copies a special Value into the selected Parameter."
        		, "If you have something like an AND-Gate, and need to limit the Inputs for that, just place a Redstone preventing Cover on that Side. Any Cover will work, but since you need to power the Block from one Side you should use a special Cover for that."
        		, "Said special Cover is the Redstone Preventor Cover, which can be attached by clicking twice with a Screwdriver on a Machine. That Cover looks Red and lets everything go through except Redstone Signals."
        		});
        if (tStack != null) {
        	ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        	ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   3));
        	ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   5));
            ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,  20));
        tStack = null;
        }
        
        tStack = GT_Utility.getWrittenBook("Upgrade Dictionary V3.03a", "Gregorius Techneticies", new String[] {
        		  "This Book lists all Items, which can be used as special Upgrade for GregTech Machines. As Books are distributed via Worldgen, this Book can be outdated on older Maps (see Version Number on the Title)."
        		, "Reactor Vents of any kind can be used to increase the efficiency of all processing Machines. It only works if the Cover is not obstructed by a solid block. All Objects without Collision Box are not obstructing it (Torches, Buttons, Levers etc.)"
        		, "The Drain can be used to extract Liquid Blocks right in front of it into the Block it is attached to. If it is attached on the top side of the Machine, it is also able to collect Rain Water. It doesnt work pointing downwards for obvious Reasons."
        		, "A Solar Panel Cover does what a normal Solar Panel does, it powers the Machine with Solar Energy, when Sun is shining. This works only if the Panel is attached to the upwards pointing face of the Block, as the Sunlight shines straight downwards."
        		, "Computer Screens have the spcial ability to make only the GUI (or other rightclick action) of a Block accessible. Anything else gets Blocked. It also looks good, if you want to hide your Machines without having a Crowbar in your hand."
        		, "Valves are ideal for transferring Liquids between Machines. They transfer 1 Bucket per tick! The best pipes can only do 80 Millibuckets per tick! If the Machine with the Valve can consume Energy, then it will consume 10EU per Bucket."
        		, "Conveyor Modules can transport Items from one Machine to another. Just attach them on the right side and they will pump out Items at 2EU per Item, if the Machine can consume Energy at all."
        		, "The Liquid, Item, Energy and Progress Meters, are emitting Redstone depending on the amount of their respective measurement inside the attached Machine."
        		, "Work Controller Covers are controlling if the Machine is allowed to work or not, depending on injected Redstone. These Covers can be used for almost every Machine, even for the Button Panel, as Key locking Method."
        		, "Overclocker Upgrades, as you know them from Industrial Corp, can be used to speed up Progress in GregTech Machines as well, but more efficiently. Every Overclocker doubles the Speed but also quadruples the Energy Costs."
        		, "Transformer Upgrades are used to increase th Voltage which is being handled by a Machine in- and output wise. Also, in case of output, it splits the EU-Packets to four. Higher Voltages such as 2048 and 8192EU/p need an HV-Transformer Upgrade."
        		, "Battery Upgrades of various kinds are increasing the Energy Storage of a Machine. Some Battery Upgrades, like the Lapotronic Energy Orb Upgrade, require a certain Voltage Tier for the Machine to be attached."
        		, "The Pneumatic Generator Upgrade is capable of letting Machines consume Kinetic Energy from Engines, also called MJ (Michael Jacksons). This Upgrade does NOT let you output any of this Energy in form of Electricity!"
        		, "Redstone Energy Cell Upgrades just let you store more kinetic Energy inside your Machine, if a Pneumatic Generator is installed."
        		, "Heating Coils are capable of upgrading the Efficiency of all Furnace alike Devices, including the electric Blast Furnace, when using 4 Coils at once. Note, that you need to install the Coils in a certain order for it to work."
        		});
        if (tStack != null) {
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   5));
        tStack = null;
        }
        
        tStack = GT_Utility.getWrittenBook("Crop Dictionary V3.03a", "Mr. Kenny", new String[] {
        		  "This is a List of all Crops I stumbled upon my tests. Some mutations of them had quite dangerous properties, which almost killed me, so be aware and don't use experimental WeedEx, like I did."
        		, "The generic Crops, like Pumpkins, Melons, Wheat, Sugar Canes, Cacti, Carrots, Potatoes, Nether Wart, Roses, Dandelions, Cocoa, Brown Mushooms and Red Mushrooms are not being further described here as they are commonly known."
        		, "Weed. The most dangerous Plant. It grows on empty Crop Sticks, which are very likely used for cross breeding. This Plant will spread over to other non-Weed Plants and assimilate them in a bad way. Use WeedEx on empty sticks to protect them."
        		, "Warning: Highly advanced Crops (fast growing ones) can behave like Weed but are immune to it as well. So you might want to make sure that your good Plants are seperated from nonresistent Plants."
        		, "Hops. A plant primary used to brew Beer. Nothing much to say about it."
        		, "Coffee. Well, it grows Coffee Beans, which can then be blended into Coffeee Powder to make Coffee."
        		, "Indigo. This Plant is giving you Indigo Blossoms, which can either be used for Blue Dye or Plant Balls. Four of these can also be used to plant new Indigo Plants. They are not Redpower compatible."
        		, "Flax. A Plant to give you just Strings. You can easyly get it via cross breeding. It is also not Redpower compatible."
        		, "Stick Reed. That Reeds are sticky. Perfect for making Rubber from them."
        		, "Terra Wart. It is like edible Milk. It cures all Poisons."
        		, "Red Wheat. It's Red, it's Wheat, it drops Redstone Dust."
        		, "Trollweed, uhh I mean Ferru. Grows Iron. Yes, real Iron. Only condition for that is having an Iron Ore Block under the tilled Farmland."
        		, "Coppon. Cotton Candy, I mean Cotton Copper, is an easy way to get Copper from Plants."
        		, "Tine. Name comes from Tin and Pine. This tiny Tree drops Tin Nuggets, yay. Perfect for Tinpinchers."
        		, "Plumbilia. This Heavy Metal Plant seems to like Music. It drops very dense Lead Nuggets, instead of the Bass."
        		, "Argentia. Silver. Well, at least it's something."
        		, "Aurelia. GOLD!!! It looks like trollweed, but instead of Iron it is Gold. Same for the Ore Block under it."
        		, "Oil Berries. These Vine alike Crops are dropping Oil Berries. Four Oil Berries can be put into a Cell to get an Oil Cell."
        		, "Bobsyeruncleranks. Because Bob is your Uncle. Drops Emeralds and Emerald Dust."
        		, "Withereed. Drops Coal. Real Coal, not that cheap Charcoal."
        		, "Blazereed. I think a Blaze Spawner is better than this."
        		, "Diareed. A Direwolfs way to get Diremonds."
        		, "Eggplant. Seems that there is a Chicken somewhere in your Field."
        		, "Corium. This Leathery Plant lets you safe the Life of a Million innocent Cows, what practically ruins the Environment, due to the Methane output of these not so innocent Animals."
        		, "Corpseplant. Plants vs. Zombies went a bit wrong. Maybe this Zombie Plant wants to infiltrate your Garden."
        		, "Creeperweed. General Spaz experimented with Crops as well, to finally get rid of Etho. This Plant made it possible for him, to gather TNT without killing his good Friends."
        		, "Enderbloom. Seems the Ender Dragoon, made it finally into cross breeding, and this Ender Flower is what he got."
        		, "Meatrose. This is how to get Meat the beautiful Way, without needing to slaughter harmless Animals."
        		, "Milkwart. Use a Bucket to harvest this Plant."
        		, "Slimeplant. So it's a Tree, and it grows Slimeballs. A very bouncy Plant."
        		, "Spidernip. Cats looo ...wait, this isn't Catnip... , Spiders looooove this Plant. They just can't stop making their Webs in there."
        		, "Tearstalks. This Plant drops the probably rarest Item of all. Ghast Tears. These Tears are extremly rare, I guess even more rare than Nether Stars as they can't just be farmed, well, until now."
        		});
        if (tStack != null) {
        	ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   2));
        	ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   3));
        	ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   5));
            ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,  10));
        tStack = null;
        }
        
        tStack = GT_Utility.getWrittenBook("Microwave Oven Manual", "Kitchen Industries", new String[] {
        		  "You just got a Microwave Oven and asked yourself 'why do I even need it?'. It's simple, the Microwave can cook for just 100 EU and at an insane speed. Not even a normal E-furnace can do it that fast and cheap!"
        		, "This is the cheapest and fastest way to cook for you. That is why the Microwave Oven can be found in almost every Kitchen (see www.youwannabuyakitchen.ly)."
        		, "Long time exposure to Microwaves can cause Cancer, but we doubt Steve lives long enough to die because of that."
        		, "Do not insert any Metals. It might result in an Explosion."
        		, "Do not dry Animals with it. It will result in a Hot Dog, no matter which Animal you put into it."
        		, "Do not insert inflammable Objects. The Oven will catch on Fire."
        		, "Do not insert Eggs. Just don't."
        		});
        if (tStack != null) {
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(tStack.copy()										, 1, 1,   1));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY		, new WeightedRandomChestContent(tStack.copy()										, 1, 1,  10));
        tStack = null;
        }
        
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotSilver"			, 1), 1, 6, 120));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotLead"			, 1), 1, 6,  30));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotSteel"			, 1), 1, 6,  60));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotBronze"			, 1), 1, 6,  60));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemEmerald"			, 1), 1, 6,  20));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemRuby"				, 1), 1, 6,  20));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemSapphire"			, 1), 1, 6,  20));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGreenSapphire"	, 1), 1, 6,  20));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemOlivine"			, 1), 1, 6,  20));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetRed"		, 1), 1, 6,  40));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST			, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetYellow"		, 1), 1, 6,  40));
        
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotSilver"			, 1), 4,16,  12));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotPlatinum"		, 1), 2, 8,   4));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemRuby"				, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemSapphire"			, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGreenSapphire"	, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemOlivine"			, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetRed"		, 1), 2, 8,   4));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetYellow"		, 1), 2, 8,   4));
        
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotBronze"			, 1), 4,16,  12));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemRuby"				, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemSapphire"			, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGreenSapphire"	, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemOlivine"			, 1), 2, 8,   2));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetRed"		, 1), 2, 8,   4));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST	, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetYellow"		, 1), 2, 8,   4));
        
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER, new WeightedRandomChestContent(new ItemStack(Item.fireballCharge				, 1), 2, 8,  30));
        
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotSilver"			, 1), 1, 4,  12));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotLead"			, 1), 1, 4,   3));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotSteel"			, 1), 1, 4,   6));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotBronze"			, 1), 1, 4,   6));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemEmerald"			, 1), 1, 4,   2));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemRuby"				, 1), 1, 4,   2));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemSapphire"			, 1), 1, 4,   2));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGreenSapphire"	, 1), 1, 4,   2));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemOlivine"			, 1), 1, 4,   2));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetRed"		, 1), 1, 4,   4));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR		, new WeightedRandomChestContent(GT_OreDictUnificator.get("gemGarnetYellow"		, 1), 1, 4,   4));
        
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotSteel"			, 1), 4,12,  12));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotBronze"			, 1), 4,12,  12));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotBrass"			, 1), 4,12,  12));
        
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotSteel"			, 1), 8,32,  12));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING		, new WeightedRandomChestContent(GT_OreDictUnificator.get("ingotBronze"			, 1), 8,32,  12));
        
        GT_Log.out.println("GT_Mod: Adding non-OreDict Machine Recipes.");
        GT_ModHandler.addCompressionRecipe(GT_ModHandler.getIC2Item("coalChunk", 1), GT_ModHandler.getIC2Item("industrialDiamond", 1));
		GT_ModHandler.addCompressionRecipe(new ItemStack(Block.sand, 4, 0), new ItemStack(Block.sandStone, 1, 0));
		GT_ModHandler.addCompressionRecipe(new ItemStack(Item.wheat, 8, 0), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
		GT_ModHandler.addCompressionRecipe(new ItemStack(Item.reed, 8, 0), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
		GT_ModHandler.addCompressionRecipe(new ItemStack(Block.cactus, 8, 0), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
		GT_ModHandler.addCompressionRecipe(new ItemStack(Item.clay, 4, 0), new ItemStack(Block.blockClay, 1));
		GT_ModHandler.addCompressionRecipe(GT_ModHandler.getIC2Item("iridiumOre", 1), GT_MetaItem_Material.instance.getStack(16, 1));
		GT_ModHandler.addCompressionRecipe(GT_MetaItem_Material.instance.getStack(8, 8), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
		GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.reed, 1), new ItemStack(Item.sugar, 2), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.blockClay, 1), GT_OreDictUnificator.get("dustClay", 2), null, 0, false);
		GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.blockSnow, 1), new ItemStack(Item.snowball, 4), null, 0, false);
	    GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.stick, 1), GT_MetaItem_SmallDust.instance.getStack(15, 2), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.coal, 1, 1), GT_MetaItem_Dust.instance.getStack(47, 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.melon, 1, 0), new ItemStack(Item.melon, 8, 0), new ItemStack(Item.melonSeeds, 1), 80, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.pumpkin, 1, 0), new ItemStack(Item.pumpkinSeeds, 4, 0), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.melon, 1, 0), new ItemStack(Item.melonSeeds, 1, 0), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.wheat, 1, 0), GT_OreDictUnificator.get("dustWheat", 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("plantBall", 1), new ItemStack(Block.dirt, 1, 0), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("crop", 1), GT_OreDictUnificator.get("dustWood", 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.enderPearl, 1)	, GT_MetaItem_Dust.instance.getStack( 0, 1), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.eyeOfEnder, 1)	, GT_MetaItem_Dust.instance.getStack( 1, 2), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.flint, 1)		, GT_MetaItem_SmallDust.instance.getStack( 7, 2), GT_MetaItem_SmallDust.instance.getStack( 7, 1), 10, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.coal, 1), GT_OreDictUnificator.get("dustCoal", 1), null, 0, false);
        GT_ModHandler.addSmeltingRecipe(GT_ModHandler.getIC2Item("hydratedCoalDust", 1), GT_OreDictUnificator.get("dustCoal", 1));
        GT_ModHandler.addLiquidTransposerEmptyRecipe(GT_ModHandler.getIC2Item("hydratedCoalDust", 1), new LiquidStack(Block.waterStill, 100), GT_OreDictUnificator.get("dustCoal", 1), 125);
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item("airCell", 1), GT_ModHandler.getEmptyCell(1));
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item("filledTinCan", 1, 0), GT_ModHandler.getIC2Item("tinCan", 1));
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item("filledTinCan", 1, 1), GT_ModHandler.getIC2Item("tinCan", 1));
        GT_ModHandler.addExtractionRecipe(GT_MetaItem_Material.instance.getStack(8, 1), GT_MetaItem_Material.instance.getStack(9, 1));
        addBenderRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), GT_OreDictUnificator.getFirstOre("plateAlloyAdvanced", 1), 100, 8);
    	addDistillationRecipe(GT_MetaItem_Cell.instance.getStack(17, 16), 17, GT_MetaItem_Cell.instance.getStack(18, 16), GT_MetaItem_Cell.instance.getStack(40, 16), GT_MetaItem_Cell.instance.getStack(34, 1), null, 16000, 128);
        addDistillationRecipe(GT_MetaItem_Cell.instance.getStack(20, 16), 0, GT_MetaItem_Cell.instance.getStack(19,  8), null, null, GT_ModHandler.getEmptyCell(8), 400, 32);
        addElectrolyzerRecipe(new ItemStack(Block.waterStill, 6), 5, GT_MetaItem_Cell.instance.getStack(0, 4), GT_ModHandler.getIC2Item("airCell", 1), null, null, 775, 120);
        addElectrolyzerRecipe(GT_ModHandler.getIC2Item("electrolyzedWaterCell", 6), 0, GT_MetaItem_Cell.instance.getStack(0, 4), GT_ModHandler.getIC2Item("airCell", 1), null, GT_ModHandler.getEmptyCell(1), 100, 30);
        addElectrolyzerRecipe(GT_ModHandler.getIC2Item("waterCell", 1), 0, GT_ModHandler.getIC2Item("electrolyzedWaterCell", 1), null, null, null, 200, 128);
        addElectrolyzerRecipe(new ItemStack(Item.bucketWater, 1), 1, GT_ModHandler.getIC2Item("electrolyzedWaterCell", 1), new ItemStack(Item.bucketEmpty, 1), null, null, 200, 128);
    	addElectrolyzerRecipe(new ItemStack(Item.dyePowder, 3, 15), 1, GT_MetaItem_Cell.instance.getStack(11, 1), null, null, null, 24, 106);
    	addVacuumFreezerRecipe(GT_ModHandler.getIC2Item("reactorCoolantSimple"	, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("reactorCoolantSimple"	, 1), 100);
    	addVacuumFreezerRecipe(GT_ModHandler.getIC2Item("reactorCoolantTriple"	, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("reactorCoolantTriple"	, 1), 300);
    	addVacuumFreezerRecipe(GT_ModHandler.getIC2Item("reactorCoolantSix"		, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_ModHandler.getIC2Item("reactorCoolantSix"		, 1), 600);
    	addVacuumFreezerRecipe(getGregTechItem(34, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), getGregTechItem(34, 1, 0),  700);
    	addVacuumFreezerRecipe(getGregTechItem(35, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), getGregTechItem(35, 1, 0), 2000);
    	addVacuumFreezerRecipe(getGregTechItem(36, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), getGregTechItem(36, 1, 0), 3900);
    	addVacuumFreezerRecipe(getGregTechItem(60, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), getGregTechItem(60, 1, 0),  500);
    	addVacuumFreezerRecipe(getGregTechItem(61, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), getGregTechItem(61, 1, 0), 1500);
    	addVacuumFreezerRecipe(getGregTechItem(62, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), getGregTechItem(62, 1, 0), 3000);
    	addVacuumFreezerRecipe(GT_MetaItem_Material.instance.getStack(5, 1), GT_OreDictUnificator.get("ingotTungstenSteel", 1), 450);
		addVacuumFreezerRecipe(GT_ModHandler.getIC2Item("waterCell", 1), GT_MetaItem_Cell.instance.getStack(23, 1), 50);
		addCentrifugeRecipe(GT_MetaItem_Cell.instance.getStack(23, 1), 0, new ItemStack(Block.ice, 1), null, null, GT_ModHandler.getEmptyCell(1), 40);
        addCentrifugeRecipe(new ItemStack(Item.magmaCream, 1), 0, new ItemStack(Item.blazePowder, 1), new ItemStack(Item.slimeBall, 1), null, null, 500);
        addImplosionRecipe(GT_MetaItem_Material.instance.getStack(4, 1), 8, GT_ModHandler.getIC2Item("iridiumPlate", 1), GT_OreDictUnificator.get("dustDarkAsh", 4));
        addGrinderRecipe(new ItemStack(Block.netherrack, 16), -1, new ItemStack(Item.goldNugget, 1), GT_MetaItem_Dust.instance.getStack(6, 16), null, GT_ModHandler.getEmptyCell(1));
		addGrinderRecipe(new ItemStack(Block.netherrack, 8), GT_MetaItem_Cell.instance.getStack(16, 1), new ItemStack(Item.goldNugget, 1), GT_MetaItem_Dust.instance.getStack(6, 8), null, GT_ModHandler.getEmptyCell(1));
		addCannerRecipe(GT_MetaItem_Material.instance.getStack(7, 4), GT_ModHandler.getEmptyCell(1), GT_MetaItem_Cell.instance.getStack(17, 1), null, 100, 1);
		addCannerRecipe(GT_ModHandler.getIC2Item("compressedPlantBall", 1), GT_ModHandler.getEmptyCell(1), GT_ModHandler.getIC2Item("bioCell", 1), null, 100, 1);
		addCannerRecipe(GT_ModHandler.getIC2Item("hydratedCoalClump", 1), GT_ModHandler.getEmptyCell(1), GT_ModHandler.getIC2Item("hydratedCoalCell", 1), null, 100, 1);
		addCannerRecipe(new ItemStack(Item.bucketLava), GT_ModHandler.getEmptyCell(1), GT_ModHandler.getIC2Item("lavaCell", 1), new ItemStack(Item.bucketEmpty, 1), 100, 1);
		addCannerRecipe(new ItemStack(Item.bucketWater), GT_ModHandler.getEmptyCell(1), GT_ModHandler.getIC2Item("waterCell", 1), new ItemStack(Item.bucketEmpty, 1), 100, 1);
		addCannerRecipe(GT_ModHandler.getIC2Item("biofuelCell", 6), GT_ModHandler.getIC2Item("fuelCan", 1), GT_ModHandler.getFuelCan(26040), GT_ModHandler.getEmptyCell(6), 600, 1);
		addCannerRecipe(GT_ModHandler.getIC2Item("coalfuelCell", 6), GT_ModHandler.getIC2Item("fuelCan", 1), GT_ModHandler.getFuelCan(76440), GT_ModHandler.getEmptyCell(6), 600, 1);
		addAssemblerRecipe(GT_MetaItem_Component.instance.getStack(49, 1), GT_MetaItem_Component.instance.getStack(24, 2), GT_ModHandler.getIC2Item("advancedCircuit", 1), 1600, 2);
		addAssemblerRecipe(GT_MetaItem_Component.instance.getStack(50, 1), GT_MetaItem_Component.instance.getStack(3, 1), GT_MetaItem_Component.instance.getStack(1, 1), 3200, 4);
		addAssemblerRecipe(GT_MetaItem_Component.instance.getStack(50, 1), GT_ModHandler.getIC2Item("lapotronCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), GT_MetaItem_Component.instance.getStack(0, 1), 3200, 4);
		addAssemblerRecipe(GT_MetaItem_Component.instance.getStack( 1, 1), GT_MetaItem_Component.instance.getStack(3, 8), getGregTechItem(43, 1, 0), 12800, 16);
		addAssemblerRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 6), GT_MetaItem_Component.instance.getStack(3, 4), getGregTechItem(32, 1, 0), 6400, 8);
		addAssemblerRecipe(GT_ModHandler.getIC2Item("waterMill", 2), null, GT_ModHandler.getIC2Item("generator", 1), 6400, 8);
    	addAssemblerRecipe(GT_ModHandler.getIC2Item("generator", 1), GT_ModHandler.getIC2Item("carbonPlate", 4), GT_ModHandler.getIC2Item("windMill", 1), 6400, 8);
    	addAssemblerRecipe(new ItemStack(Block.stoneSingleSlab, 3, 0), GT_ModHandler.getRCItem("part.rebar", 1), GT_ModHandler.getRCItem("part.tie.stone", 1), 128, 8);
    	addAssemblerRecipe(new ItemStack(Block.stoneSingleSlab, 3, 7), GT_ModHandler.getRCItem("part.rebar", 1), GT_ModHandler.getRCItem("part.tie.stone", 1), 128, 8);
    	addAssemblerRecipe(getGregTechItem(58, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), null, getGregTechItem(56, 6, 0), 3200, 4);
    	addAssemblerRecipe(GT_ModHandler.getIC2Item("batPack", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), null, GT_ModHandler.getIC2Item("reBattery", 6), 1600, 2);
    	addAssemblerRecipe(GT_ModHandler.mBCIronGear					, GT_ModHandler.getIC2Item("generator", 1), GT_MetaItem_Component.instance.getStack(25, 1), 3200, 4);
    	addAssemblerRecipe(GT_ModHandler.getRCItem("part.gear.iron", 1)	, GT_ModHandler.getIC2Item("generator", 1), GT_MetaItem_Component.instance.getStack(25, 1), 3200, 4);
    	addAssemblerRecipe(GT_ModHandler.getRCItem("part.gear.steel", 1), GT_ModHandler.getIC2Item("generator", 1), GT_MetaItem_Component.instance.getStack(25, 1), 3200, 4);
    	addAssemblerRecipe(GT_ModHandler.getTEItem("gearInvar", 1)		, GT_ModHandler.getIC2Item("generator", 1), GT_MetaItem_Component.instance.getStack(25, 1), 3200, 4);
    	addAssemblerRecipe(GT_ModHandler.getIC2Item("hvTransformer", 1)	, GT_ModHandler.getIC2Item("transformerUpgrade", 1), GT_MetaItem_Component.instance.getStack(27, 1), 3200, 4);
    	addAssemblerRecipe(GT_ModHandler.getTEItem("energyFrameFull", 1), GT_ModHandler.getIC2Item("energyStorageUpgrade", 1), GT_MetaItem_Component.instance.getStack(28, 4), 6400, 8);
    	addAssemblerRecipe(GT_ModHandler.getRCItem("part.tie.wood" , 4)	, null, GT_ModHandler.getRCItem("part.railbed.wood" , 1), 800, 1);
    	addAssemblerRecipe(GT_ModHandler.getRCItem("part.tie.stone", 4)	, null, GT_ModHandler.getRCItem("part.railbed.stone", 1), 800, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.wood" , 1), tStack = GT_ModHandler.getRCItem("part.rail.standard"	, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.wood" , 1), tStack = GT_ModHandler.getRCItem("part.rail.advanced"	, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.wood" , 1), tStack = GT_ModHandler.getRCItem("part.rail.reinforced"	, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.wood" , 1), tStack = GT_ModHandler.getRCItem("part.rail.speed"		, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.wood" , 1), tStack = GT_ModHandler.getRCItem("part.rail.wood"		, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.stone", 1), tStack = GT_ModHandler.getRCItem("part.rail.standard" 	, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.stone", 1), tStack = GT_ModHandler.getRCItem("part.rail.advanced"	, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.stone", 1), tStack = GT_ModHandler.getRCItem("part.rail.reinforced"	, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.stone", 1), tStack = GT_ModHandler.getRCItem("part.rail.speed" 		, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(tStack2 = GT_ModHandler.getRCItem("part.railbed.stone", 1), tStack = GT_ModHandler.getRCItem("part.rail.wood" 		, 6), GT_ModHandler.getRecipeOutput(new ItemStack[] {tStack, null, tStack, tStack, tStack2, tStack, tStack, null, tStack}), 1600, 1);
    	addAssemblerRecipe(GT_MetaItem_Component.instance.getStack(5, 1), GT_MetaItem_Component.instance.getStack(6, 1), GT_MetaItem_Component.instance.getStack(8, 1), 3200, 4);
		
    	GT_ModHandler.removeRecipe(new ItemStack[] {GT_ModHandler.getIC2Item("uraniumIngot", 1), GT_ModHandler.getEmptyCell(1)});
		GT_ModHandler.removeRecipe(new ItemStack[] {new ItemStack(Item.bucketLava), GT_ModHandler.getEmptyCell(1)});
		GT_ModHandler.removeRecipe(new ItemStack[] {new ItemStack(Item.bucketWater), GT_ModHandler.getEmptyCell(1)});
		GT_ModHandler.removeRecipe(new ItemStack[] {GT_ModHandler.getIC2Item("compressedPlantBall", 1), GT_ModHandler.getEmptyCell(1)});
		GT_ModHandler.removeRecipe(new ItemStack[] {GT_ModHandler.getIC2Item("hydratedCoalClump", 1), GT_ModHandler.getEmptyCell(1)});
		
        addCentrifugeRecipe(GT_ModHandler.getIC2Item("nearDepletedUraniumCell", 1), 0, GT_OreDictUnificator.get("dustThorium", 1), null, null, GT_ModHandler.getEmptyCell(1), 500);
        addCentrifugeRecipe(GT_ModHandler.getIC2Item("reEnrichedUraniumCell", 8), 0, GT_ModHandler.getIC2Item("nearDepletedUraniumCell", 3), GT_OreDictUnificator.get("dustPlutonium", 1), GT_OreDictUnificator.get("dustThorium", 4), GT_ModHandler.getEmptyCell(5), 20000);
    	
        addCentrifugeRecipe(new ItemStack(Block.dirt, 16), 0, GT_ModHandler.getIC2Item("compressedPlantBall", 1), GT_ModHandler.getIC2Item("plantBall", 1), new ItemStack(Item.clay, 1), new ItemStack(Block.sand, 8), 2500);
        addCentrifugeRecipe(new ItemStack(Block.grass, 16), 0, GT_ModHandler.getIC2Item("compressedPlantBall", 2), GT_ModHandler.getIC2Item("plantBall", 2), new ItemStack(Item.clay, 1), new ItemStack(Block.sand, 8), 2500);
        addCentrifugeRecipe(new ItemStack(Block.mycelium, 8), 0, new ItemStack(Block.mushroomBrown, 2), new ItemStack(Block.mushroomRed, 2), new ItemStack(Item.clay, 1), new ItemStack(Block.sand, 4), 1650);
        
        addCentrifugeRecipe(new ItemStack(Item.appleGold			,  1, 1), 2, GT_MetaItem_Cell.instance.getStack(9, 2), new ItemStack(Item.ingotGold, 64), null, null, 10000);
        addCentrifugeRecipe(new ItemStack(Item.appleGold			,  1, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), new ItemStack(Item.goldNugget, 6), null, null, 10000);
        addCentrifugeRecipe(new ItemStack(Item.goldenCarrot		,  1, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), new ItemStack(Item.goldNugget, 6), null, null, 10000);
        addCentrifugeRecipe(new ItemStack(Item.speckledMelon		,  8, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), new ItemStack(Item.goldNugget, 6), null, null, 10000);
        addCentrifugeRecipe(new ItemStack(Item.appleRed			, 32, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.bowlSoup			, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.bread				, 64, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.porkRaw			, 12, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.porkCooked		, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.beefRaw			, 12, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.beefCooked		, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.fishRaw			, 12, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.fishCooked		, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.chickenRaw		, 12, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.chickenCooked		, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.melon				, 64, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Block.pumpkin			, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.rottenFlesh		, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.spiderEye			, 32, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.carrot			, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.potato			, 16, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.poisonousPotato	, 12, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.bakedPotato		, 24, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.cookie			, 64, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        addCentrifugeRecipe(new ItemStack(Item.cake				,  8, 0), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null,  5000);
        
        addCentrifugeRecipe(new ItemStack(Block.mushroomCapBrown	, 12, GregTech_API.ITEM_WILDCARD_DAMAGE), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null, 5000);
        addCentrifugeRecipe(new ItemStack(Block.mushroomCapRed	, 12, GregTech_API.ITEM_WILDCARD_DAMAGE), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null, 5000);
        addCentrifugeRecipe(new ItemStack(Block.mushroomBrown	, 32), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null, 5000);
        addCentrifugeRecipe(new ItemStack(Block.mushroomRed		, 32), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null, 5000);
        addCentrifugeRecipe(new ItemStack(Item.netherStalkSeeds	, 32), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null, 5000);
        addCentrifugeRecipe(GT_ModHandler.getIC2Item("terraWart"	, 16), 1, GT_MetaItem_Cell.instance.getStack(9, 1), null, null, null, 5000);
        
        addElectrolyzerRecipe(new ItemStack(Item.sugar, 32), 7, GT_MetaItem_Cell.instance.getStack(8, 2), GT_ModHandler.getIC2Item("waterCell", 5), null, null, 210, 32);
        addElectrolyzerRecipe(new ItemStack(Item.blazePowder, 4), 0, GT_OreDictUnificator.get("dustDarkAsh", 1), GT_OreDictUnificator.get("dustSulfur", 1), null, null, 300, 25);
        addElectrolyzerRecipe(new ItemStack(Block.sand, 16), 2, GT_MetaItem_Cell.instance.getStack(7, 1), GT_ModHandler.getIC2Item("airCell", 1), null, null, 1000, 25);
        addCentrifugeRecipe(GT_ModHandler.getIC2Item("resin", 4), 0, GT_OreDictUnificator.get("itemRubber", 14), GT_ModHandler.getIC2Item("compressedPlantBall", 1), GT_ModHandler.getIC2Item("plantBall", 1), null, 1300);
        addCentrifugeRecipe(new ItemStack(Block.slowSand, 16), 1, GT_MetaItem_Cell.instance.getStack(17, 1), GT_MetaItem_Dust.instance.getStack(9, 4), GT_OreDictUnificator.get("dustCoal", 1), new ItemStack(Block.sand, 10), 2500);
        addCentrifugeRecipe(GT_ModHandler.getIC2Item("lavaCell", 16)	, 0, GT_OreDictUnificator.get("ingotElectrum", 1), GT_OreDictUnificator.get("ingotCopper", 4), GT_OreDictUnificator.get("dustSmallTungsten", 1), GT_OreDictUnificator.get("ingotTin", Math.min(64, 2 + 64/sTincellCount)), 15000);
        addCentrifugeRecipe(new ItemStack(Block.lavaStill, 16)		, 0, GT_OreDictUnificator.get("ingotElectrum", 1), GT_OreDictUnificator.get("ingotCopper", 4), GT_OreDictUnificator.get("dustSmallTungsten", 1), GT_OreDictUnificator.get("ingotTin", 2), 10000);        
		
        if (!GregTech_API.sConfiguration.addAdvConfig("StorageBlockCrafting", "blockGlowstone", false)) GT_ModHandler.removeRecipe(new ItemStack[] {tStack = new ItemStack(Item.lightStoneDust, 1), tStack, null, tStack, tStack, null, null, null, null});
        GT_ModHandler.addCompressionRecipe(new ItemStack(Item.lightStoneDust, 4, 0), new ItemStack(Block.glowStone, 1));
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("machine", 1), GT_OreDictUnificator.get("dustRefinedIron", GT_OreDictUnificator.get("dustIron", 8), 8), null, 0, false);
        GT_ModHandler.addSmeltingRecipe(GT_ModHandler.getIC2Item("machine", 1), GT_OreDictUnificator.get("ingotRefinedIron", 8));
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.obsidian, 1), GT_OreDictUnificator.get("dustObsidian", 2), null, 0, false);
        GT_ModHandler.addExtractionRecipe(new ItemStack(Item.slimeBall, 1), GT_OreDictUnificator.get("itemRubber", 2));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Item.slimeBall,1), GT_ModHandler.getIC2Item("resin", 1));
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item("resin", 1), GT_OreDictUnificator.get("itemRubber", 3));
        
        GT_Log.out.println("GT_Mod: Adding Stuff to the Recycler Blacklist.");
        if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "easymobgrinderrecycling", true)) {
        	// Skeletons
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.arrow, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.bone, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.dyePowder, 1, 15));
			
			// Zombies
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.rottenFlesh, 1, 0));
			
			// Spiders
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.silk, 1, 0));
			
			// Chicken Eggs
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.egg, 1, 0));
        }
		if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "easystonerecycling", true)) {
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.sand, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.sandStone, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.glass, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.glassBottle, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.potion, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getSmeltingOutput(new ItemStack(Block.stone, 1, 0)));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.stone, 1, 0), null, new ItemStack(Block.stone, 1, 0), null, new ItemStack(Block.stone, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.stone, 1, 0), new ItemStack(Block.glass, 1, 0), new ItemStack(Block.stone, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.cobblestone, 1, 0), new ItemStack(Block.glass, 1, 0), new ItemStack(Block.cobblestone, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.sandStone, 1, 0), new ItemStack(Block.glass, 1, 0), new ItemStack(Block.sandStone, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.sand, 1, 0), new ItemStack(Block.glass, 1, 0), new ItemStack(Block.sand, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.sandStone, 1, 0), new ItemStack(Block.sandStone, 1, 0), new ItemStack(Block.sandStone, 1, 0), new ItemStack(Block.sandStone, 1, 0), new ItemStack(Block.sandStone, 1, 0), new ItemStack(Block.sandStone, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.glass, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.glass, 1, 0), new ItemStack(Block.glass, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(GT_ModHandler.getRecipeOutput(new ItemStack[] {new ItemStack(Block.glass, 1, 0), null, null, new ItemStack(Block.glass, 1, 0)}));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.thinGlass, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.cobblestone, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.cobblestoneWall, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stairsSandStone, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stairsCobblestone, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stairsStoneBrick, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.furnaceBurning, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.furnaceIdle, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneSingleSlab, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneDoubleSlab, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneSingleSlab, 1, 1));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneDoubleSlab, 1, 1));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneSingleSlab, 1, 3));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneDoubleSlab, 1, 3));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneSingleSlab, 1, 5));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneDoubleSlab, 1, 5));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneSingleSlab, 1, 7));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneDoubleSlab, 1, 7));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.pressurePlateStone, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneButton, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneBrick, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneBrick, 1, 1));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneBrick, 1, 2));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stoneBrick, 1, 3));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.stone, 1, 0));
			GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.lever, 1, 0));
		}
		GT_ModHandler.addToRecyclerBlackList(new ItemStack(Item.snowball, 1));
		GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.ice, 1));
		GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.snow, 1));
		GT_ModHandler.addToRecyclerBlackList(new ItemStack(Block.blockSnow, 1));
		
		
        GT_Log.out.println("GT_Mod: (re-)adding Scrapbox Drops.");
        GT_ModHandler.addScrapboxDrop(9.50F, new ItemStack(Item.hoeWood));
        GT_ModHandler.addScrapboxDrop(2.00F, new ItemStack(Item.axeWood));
        GT_ModHandler.addScrapboxDrop(2.00F, new ItemStack(Item.swordWood));
        GT_ModHandler.addScrapboxDrop(2.00F, new ItemStack(Item.shovelWood));
        GT_ModHandler.addScrapboxDrop(2.00F, new ItemStack(Item.pickaxeWood));
        GT_ModHandler.addScrapboxDrop(2.00F, new ItemStack(Item.sign));
        GT_ModHandler.addScrapboxDrop(9.50F, new ItemStack(Item.stick));
        GT_ModHandler.addScrapboxDrop(5.00F, new ItemStack(Block.dirt));
        GT_ModHandler.addScrapboxDrop(3.00F, new ItemStack(Block.grass));
        GT_ModHandler.addScrapboxDrop(3.00F, new ItemStack(Block.gravel));
        GT_ModHandler.addScrapboxDrop(0.50F, new ItemStack(Block.pumpkin));
        GT_ModHandler.addScrapboxDrop(1.00F, new ItemStack(Block.slowSand));
        GT_ModHandler.addScrapboxDrop(2.00F, new ItemStack(Block.netherrack));
        GT_ModHandler.addScrapboxDrop(1.00F, new ItemStack(Item.bone));
        GT_ModHandler.addScrapboxDrop(9.00F, new ItemStack(Item.rottenFlesh));
        GT_ModHandler.addScrapboxDrop(0.40F, new ItemStack(Item.porkCooked));
        GT_ModHandler.addScrapboxDrop(0.40F, new ItemStack(Item.beefCooked));
        GT_ModHandler.addScrapboxDrop(0.40F, new ItemStack(Item.chickenCooked));
        GT_ModHandler.addScrapboxDrop(0.50F, new ItemStack(Item.appleRed));
        GT_ModHandler.addScrapboxDrop(0.50F, new ItemStack(Item.bread));
        GT_ModHandler.addScrapboxDrop(0.10F, new ItemStack(Item.cake));
        GT_ModHandler.addScrapboxDrop(1.00F, GT_ModHandler.getIC2Item("filledTinCan", 1, 0));
        GT_ModHandler.addScrapboxDrop(2.00F, GT_ModHandler.getIC2Item("filledTinCan", 1, 1));
		GT_ModHandler.addScrapboxDrop(0.20F, GT_MetaItem_Cell.instance.getStack(7, 1));
		GT_ModHandler.addScrapboxDrop(1.00F, GT_ModHandler.getIC2Item("waterCell", 1));
		GT_ModHandler.addScrapboxDrop(2.00F, GT_ModHandler.getEmptyCell(1));
        GT_ModHandler.addScrapboxDrop(5.00F, new ItemStack(Item.paper));
        GT_ModHandler.addScrapboxDrop(1.00F, new ItemStack(Item.leather));
        GT_ModHandler.addScrapboxDrop(1.00F, new ItemStack(Item.feather));
        GT_ModHandler.addScrapboxDrop(0.70F, GT_ModHandler.getIC2Item("plantBall", 1));
        GT_ModHandler.addScrapboxDrop(3.80F, GT_OreDictUnificator.get("dustWood", 1));
        GT_ModHandler.addScrapboxDrop(0.60F, new ItemStack(Item.slimeBall));
        GT_ModHandler.addScrapboxDrop(0.80F, GT_OreDictUnificator.get("itemRubber", 1));
        GT_ModHandler.addScrapboxDrop(2.70F, GT_ModHandler.getIC2Item("suBattery", 1));
		GT_ModHandler.addScrapboxDrop(0.80F, GT_MetaItem_Component.instance.getStack(22,1));
		GT_ModHandler.addScrapboxDrop(1.20F, GT_MetaItem_Component.instance.getStack(24,1));
		GT_ModHandler.addScrapboxDrop(1.80F, GT_MetaItem_Component.instance.getStack(48,1));
		GT_ModHandler.addScrapboxDrop(0.40F, GT_MetaItem_Component.instance.getStack(49,1));
		GT_ModHandler.addScrapboxDrop(0.20F, GT_MetaItem_Component.instance.getStack(50,1));
		GT_ModHandler.addScrapboxDrop(2.00F, GT_ModHandler.getIC2Item("insulatedCopperCableItem", 1));
		GT_ModHandler.addScrapboxDrop(0.40F, GT_ModHandler.getIC2Item("doubleInsulatedGoldCableItem", 1));
        GT_ModHandler.addScrapboxDrop(0.90F, new ItemStack(Item.redstone));
        GT_ModHandler.addScrapboxDrop(0.80F, new ItemStack(Item.lightStoneDust));
        GT_ModHandler.addScrapboxDrop(0.80F, GT_OreDictUnificator.get("dustCoal"		, 1));
		GT_ModHandler.addScrapboxDrop(2.50F, GT_OreDictUnificator.get("dustCharcoal"	, 1));
        GT_ModHandler.addScrapboxDrop(1.00F, GT_OreDictUnificator.get("dustIron"		, 1));
        GT_ModHandler.addScrapboxDrop(1.00F, GT_OreDictUnificator.get("dustGold"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustSilver"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustElectrum"	, 1));
        GT_ModHandler.addScrapboxDrop(1.20F, GT_OreDictUnificator.get("dustTin"			, 1));
        GT_ModHandler.addScrapboxDrop(1.20F, GT_OreDictUnificator.get("dustCopper"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustBauxite"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustAluminium"	, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustLead"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustNickel"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustZinc"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustBrass"		, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_OreDictUnificator.get("dustSteel"		, 1));
		GT_ModHandler.addScrapboxDrop(1.50F, GT_OreDictUnificator.get("dustObsidian"	, 1));
		GT_ModHandler.addScrapboxDrop(1.50F, GT_OreDictUnificator.get("dustSulfur"		, 1));
		GT_ModHandler.addScrapboxDrop(2.00F, GT_OreDictUnificator.get("dustSaltpeter"	, 1));
		GT_ModHandler.addScrapboxDrop(2.00F, GT_MetaItem_Dust.instance.getStack( 2, 1));
		GT_ModHandler.addScrapboxDrop(2.00F, GT_MetaItem_Dust.instance.getStack( 3, 1));
		GT_ModHandler.addScrapboxDrop(2.00F, GT_MetaItem_Dust.instance.getStack( 4, 1));
		GT_ModHandler.addScrapboxDrop(2.00F, GT_MetaItem_Dust.instance.getStack( 5, 1));
		GT_ModHandler.addScrapboxDrop(4.00F, GT_MetaItem_Dust.instance.getStack( 6, 1));
		GT_ModHandler.addScrapboxDrop(4.00F, GT_MetaItem_Dust.instance.getStack( 7, 1));
		GT_ModHandler.addScrapboxDrop(0.03F, GT_MetaItem_Dust.instance.getStack(27, 1));
		GT_ModHandler.addScrapboxDrop(0.03F, GT_MetaItem_Dust.instance.getStack(22, 1));
		GT_ModHandler.addScrapboxDrop(0.03F, GT_MetaItem_Dust.instance.getStack(20, 1));
		GT_ModHandler.addScrapboxDrop(0.03F, GT_MetaItem_Dust.instance.getStack(19, 1));
		GT_ModHandler.addScrapboxDrop(0.03F, GT_MetaItem_Dust.instance.getStack(13, 1));
		GT_ModHandler.addScrapboxDrop(0.03F, GT_MetaItem_Dust.instance.getStack(10, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_MetaItem_Material.instance.getStack(54, 1));
		GT_ModHandler.addScrapboxDrop(0.50F, GT_MetaItem_Material.instance.getStack(55, 1));
		GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get("gemOlivine"		, 1));
		GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get("gemRuby"			, 1));
		GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get("gemSapphire"		, 1));
		GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get("gemGreenSapphire", 1));
		GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get("gemEmerald"		, 1));
		GT_ModHandler.addScrapboxDrop(0.05F, GT_OreDictUnificator.get("gemDiamond"		, 1));
		
		
        GT_Log.out.println("GT_Mod: Adding Slab Recipes.");
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneDoubleSlab	, 1, 8), new Object[] {"BB"		, 'B', new ItemStack(Block.stoneSingleSlab, 1, 0)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneDoubleSlab	, 1, 0), new Object[] {"BB"		, 'B', new ItemStack(Block.stoneSingleSlab, 1, 8)}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneDoubleSlab	, 1, 0), new Object[] {"B", "B"	, 'B', new ItemStack(Block.stoneSingleSlab, 1, 0)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.cobblestone		, 1, 0), new Object[] {"B", "B"	, 'B', new ItemStack(Block.stoneSingleSlab, 1, 3)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.brick			, 1, 0), new Object[] {"B", "B"	, 'B', new ItemStack(Block.stoneSingleSlab, 1, 4)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneBrick		, 1, 0), new Object[] {"B", "B"	, 'B', new ItemStack(Block.stoneSingleSlab, 1, 5)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.netherBrick		, 1, 0), new Object[] {"B", "B"	, 'B', new ItemStack(Block.stoneSingleSlab, 1, 6)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.blockNetherQuartz, 1, 0), new Object[] {"B", "B"	, 'B', new ItemStack(Block.stoneSingleSlab, 1, 7)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.stoneDoubleSlab	, 1, 8), new Object[] {"B", "B"	, 'B', new ItemStack(Block.stoneSingleSlab, 1, 8)}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.planks			, 1, 0), new Object[] {"B", "B"	, 'B', new ItemStack(Block.woodSingleSlab, 1, 0)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.planks			, 1, 1), new Object[] {"B", "B"	, 'B', new ItemStack(Block.woodSingleSlab, 1, 1)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.planks			, 1, 2), new Object[] {"B", "B"	, 'B', new ItemStack(Block.woodSingleSlab, 1, 2)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.planks			, 1, 3), new Object[] {"B", "B"	, 'B', new ItemStack(Block.woodSingleSlab, 1, 3)}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.stick, 2, 0), new Object[] {new ItemStack(Block.deadBush, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Item.stick, 2, 0), new Object[] {new ItemStack(Block.tallGrass, 1, 0)}));
		
        GT_Log.out.println("GT_Mod: Adding Wool and Color releated Recipes.");
		for (int i = 0; i < 16; i++) {
			GT_ModHandler.addExtractionRecipe(new ItemStack(Block.cloth, 1, i), new ItemStack(Block.cloth, 1, 0));
			GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.cloth, 1, i), new ItemStack(Item.silk, 2), new ItemStack(Item.silk, 1), 50, false);
		}
		
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  0), new Object[] {new ItemStack(Block.cloth, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "dyeWhite"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  1), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeOrange"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  2), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeMagenta"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  3), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeLightBlue"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  4), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeYellow"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  5), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeLime"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  6), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyePink"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  7), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeGray"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  8), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeLightGray"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1,  9), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeCyan"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1, 10), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyePurple"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1, 11), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeBlue"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1, 12), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeBrown"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1, 13), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeGreen"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1, 14), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeRed"});
		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Block.cloth, 1, 15), new Object[] {new ItemStack(Block.cloth, 1,  0), "dyeBlack"});
		
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("blackPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("redPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("greenPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("brownPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("bluePainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("purplePainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("cyanPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("lightGreyPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("darkGreyPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("pinkPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("limePainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("yellowPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("cloudPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("magentaPainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("orangePainter", 1)});
		GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("painter", 1), new Object[] {GT_ModHandler.getIC2Item("whitePainter", 1)});
		
        GT_Log.out.println("GT_Mod: Adding 'The holy Planks of Sengir'.");
    	tStack = GT_MetaItem_Material.instance.getStack(15, 1);
    	tStack.setItemName("The holy Planks of Sengir");
    	tStack.addEnchantment(Enchantment.smite, 10);
    	GT_ModHandler.addCraftingRecipe(tStack, new Object[] {"XXX", "XDX", "XXX", 'X', new ItemStack(Item.netherStar, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'D', new ItemStack(Block.dragonEgg, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)});
    	
    	GT_ModHandler.removeRecipe(new ItemStack[] {GT_OreDictUnificator.get("ingotCopper", 1), GT_OreDictUnificator.get("ingotTin", 1), null, GT_OreDictUnificator.get("ingotCopper", 1)});
    	if (null != GT_ModHandler.removeRecipe(new ItemStack[] {GT_OreDictUnificator.get("ingotCopper", 1), GT_OreDictUnificator.get("ingotCopper", 1), null, GT_OreDictUnificator.get("ingotCopper", 1), GT_OreDictUnificator.get("ingotTin", 1)})) {
			if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "bronzeingotcrafting", true)) GT_Log.out.println("GT_Mod: Nerfing Forestrys invalid Bronze Recipe (even though it's still logical, seeing the amount of Ingots, but IC added Bronze first, so...).");
			GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("ingotBronze", GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "bronzeingotcrafting", true)?1:4), new Object[] {"ingotCopper", "ingotCopper", "ingotCopper", "ingotTin"});
		}
		
		if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "enchantmenttable", false)) {
			GT_Log.out.println("GT_Mod: Removing the Recipe of the Enchantment Table, to have more Fun at enchanting with the Anvil and Books from Dungeons.");
			GT_ModHandler.removeRecipe(new ItemStack(Block.enchantmentTable, 1));
			GT_Log.out.println("GT_Mod: De-Enchanting using the Magic Energy Absorber is now 100 times more lucrative.");
		}
		
		if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "enderchest", false)) {
			GT_ModHandler.removeRecipe(new ItemStack(Block.enderChest, 1));
		}
		
        GT_Log.out.println("GT_Mod: Adding Mixed Metal Ingot Recipes.");
        GT_ModHandler.removeRecipe(new ItemStack[] {GT_OreDictUnificator.get("ingotRefinedIron", 1), GT_OreDictUnificator.get("ingotRefinedIron", 1), GT_OreDictUnificator.get("ingotRefinedIron", 1), GT_OreDictUnificator.get("ingotBronze", 1), GT_OreDictUnificator.get("ingotBronze", 1), GT_OreDictUnificator.get("ingotBronze", 1), GT_OreDictUnificator.get("ingotTin", 1), GT_OreDictUnificator.get("ingotTin", 1), GT_OreDictUnificator.get("ingotTin", 1)});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateRefinedIron", 'Y', "plateBronze", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateRefinedIron", 'Y', "plateBronze", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateRefinedIron", 'Y', "plateBronze", 'Z', "plateAluminium"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateRefinedIron", 'Y', "plateBrass", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateRefinedIron", 'Y', "plateBrass", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateRefinedIron", 'Y', "plateBrass", 'Z', "plateAluminium"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateNickel", 'Y', "plateBronze", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateNickel", 'Y', "plateBronze", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateNickel", 'Y', "plateBronze", 'Z', "plateAluminium"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateNickel", 'Y', "plateBrass", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateNickel", 'Y', "plateBrass", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 1), new Object[] {"X", "Y", "Z", 'X', "plateNickel", 'Y', "plateBrass", 'Z', "plateAluminium"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateInvar", 'Y', "plateBronze", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateInvar", 'Y', "plateBronze", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateInvar", 'Y', "plateBronze", 'Z', "plateAluminium"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateInvar", 'Y', "plateBrass", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateInvar", 'Y', "plateBrass", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateInvar", 'Y', "plateBrass", 'Z', "plateAluminium"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateSteel", 'Y', "plateBronze", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateSteel", 'Y', "plateBronze", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateSteel", 'Y', "plateBronze", 'Z', "plateAluminium"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateSteel", 'Y', "plateBrass", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 2), new Object[] {"X", "Y", "Z", 'X', "plateSteel", 'Y', "plateBrass", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateSteel", 'Y', "plateBrass", 'Z', "plateAluminium"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTitanium", 'Y', "plateBronze", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTitanium", 'Y', "plateBronze", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 4), new Object[] {"X", "Y", "Z", 'X', "plateTitanium", 'Y', "plateBronze", 'Z', "plateAluminium"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTitanium", 'Y', "plateBrass", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTitanium", 'Y', "plateBrass", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 4), new Object[] {"X", "Y", "Z", 'X', "plateTitanium", 'Y', "plateBrass", 'Z', "plateAluminium"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTungsten", 'Y', "plateBronze", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTungsten", 'Y', "plateBronze", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 4), new Object[] {"X", "Y", "Z", 'X', "plateTungsten", 'Y', "plateBronze", 'Z', "plateAluminium"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTungsten", 'Y', "plateBrass", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 3), new Object[] {"X", "Y", "Z", 'X', "plateTungsten", 'Y', "plateBrass", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 4), new Object[] {"X", "Y", "Z", 'X', "plateTungsten", 'Y', "plateBrass", 'Z', "plateAluminium"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 5), new Object[] {"X", "Y", "Z", 'X', "plateTungstenSteel", 'Y', "plateBronze", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 5), new Object[] {"X", "Y", "Z", 'X', "plateTungstenSteel", 'Y', "plateBronze", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 6), new Object[] {"X", "Y", "Z", 'X', "plateTungstenSteel", 'Y', "plateBronze", 'Z', "plateAluminium"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 5), new Object[] {"X", "Y", "Z", 'X', "plateTungstenSteel", 'Y', "plateBrass", 'Z', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 5), new Object[] {"X", "Y", "Z", 'X', "plateTungstenSteel", 'Y', "plateBrass", 'Z', "plateZinc"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mixedMetalIngot", 6), new Object[] {"X", "Y", "Z", 'X', "plateTungstenSteel", 'Y', "plateBrass", 'Z', "plateAluminium"});
        
        GT_Log.out.println("GT_Mod: Adding Rolling Machine Recipes.");
		GT_ModHandler.addRollingMachineRecipe(GT_MetaItem_Material.instance.getStack(13, 3), new Object[] {"AAA", "MMM", "AAA", 'A', "plateAluminium", 'M', "dustMagnesium"});
		GT_ModHandler.addRollingMachineRecipe(GT_MetaItem_Material.instance.getStack(13, 3), new Object[] {"AAA", "MMM", "AAA", 'A', "plateAluminium", 'M', "ingotMagnesium"});
		GT_ModHandler.addRollingMachineRecipe(GT_MetaItem_Material.instance.getStack(13, 3), new Object[] {"AAA", "MMM", "AAA", 'A', "plateAluminium", 'M', "plateMagnesium"});
		
        GT_ModHandler.addRollingMachineRecipe(GT_MetaItem_Component.instance.getStack(19, 3), new Object[] {"AAA", "BCC", "BBC", 'A', "ingotRefinedIron", 'B', "ingotChrome", 'C', "ingotAluminium"});
        GT_ModHandler.addRollingMachineRecipe(GT_MetaItem_Component.instance.getStack(20, 1), new Object[] {" B ", "BAB", " B ", 'A', "ingotChrome", 'B', "ingotNickel"});
        GT_ModHandler.addRollingMachineRecipe(GT_MetaItem_Component.instance.getStack(21, 3), new Object[] {"BAB", "A A", "BAB", 'A', "ingotCopper", 'B', "ingotNickel"});
        
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rail.standard"		,  4), new Object[] {"X X", "X X", "X X", 'X', "ingotAluminium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rail.standard"		, 32), new Object[] {"X X", "X X", "X X", 'X', "ingotTitanium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rail.standard"		, 32), new Object[] {"X X", "X X", "X X", 'X', "ingotTungsten"});
	    
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rail.reinforced"	, 32), new Object[] {"X X", "X X", "X X", 'X', "ingotTungstenSteel"});
	    
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rebar"				,  2), new Object[] {"  X", " X ", "X  ", 'X', "ingotAluminium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rebar"				, 16), new Object[] {"  X", " X ", "X  ", 'X', "ingotTitanium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rebar"				, 16), new Object[] {"  X", " X ", "X  ", 'X', "ingotTungsten"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("part.rebar"				, 48), new Object[] {"  X", " X ", "X  ", 'X', "ingotTungstenSteel"});
	    
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("post.metal.light.blue"	,  8), new Object[] {"XXX", " X ", "XXX", 'X', "ingotAluminium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("post.metal.purple"		, 64), new Object[] {"XXX", " X ", "XXX", 'X', "ingotTitanium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("post.metal.black"		, 64), new Object[] {"XXX", " X ", "XXX", 'X', "ingotTungsten"});
	    
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("post.metal.light.blue"	,  8), new Object[] {"X X", "XXX", "X X", 'X', "ingotAluminium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("post.metal.purple"		, 64), new Object[] {"X X", "XXX", "X X", 'X', "ingotTitanium"});
	    GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getRCItem("post.metal.black"		, 64), new Object[] {"X X", "XXX", "X X", 'X', "ingotTungsten"});
	    
		
		GT_Log.out.println("GT_Mod: Beginning to add regular Crafting Recipes.");
		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("doubleInsulatedGoldCableItem", 4), new Object[] {"RRR", "RGR", "RRR", 'G', "ingotGold", 'R', "itemRubber"});
		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getEmptyCell(sTincellCount), new Object[] {" T ", "T T", " T ", 'T', "ingotTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("scaffold", 4), new Object[] {"WWW", " S ", "S S", 'W', "plankWood", 'S', "stickWood"});
        
        GT_ModHandler.addCraftingRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"WWW", "CBC", "CRC", 'W', "plankWood", 'C', "stoneCobble", 'R', "dustRedstone", 'B', "ingotIron"});
        GT_ModHandler.addCraftingRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"WWW", "CBC", "CRC", 'W', "plankWood", 'C', "stoneCobble", 'R', "dustRedstone", 'B', "ingotBronze"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"WWW", "CBC", "CRC", 'W', "plankWood", 'C', "stoneCobble", 'R', "dustRedstone", 'B', "ingotAluminium"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"WWW", "CBC", "CRC", 'W', "plankWood", 'C', "stoneCobble", 'R', "dustRedstone", 'B', "ingotRefinedIron"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"WWW", "CBC", "CRC", 'W', "plankWood", 'C', "stoneCobble", 'R', "dustRedstone", 'B', "ingotSteel"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {"WWW", "CBC", "CRC", 'W', "plankWood", 'C', "stoneCobble", 'R', "dustRedstone", 'B', "ingotTitanium"});
    	
    	GT_ModHandler.addCraftingRecipe(getGregTechItem(37, 1, 0), new Object[] {"LLL", "LIL", "LLL", 'L', "crafting1kkEUStore", 'I', "plateAlloyIridium"});
    	GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(0, 4), new Object[] {"AWA", "LIL", "AWA", 'L', "crafting1kkEUStore", 'I', "plateAlloyIridium", 'A', "craftingCircuitTier04", 'W', "plateTungsten"});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(1, 4), new Object[] {"AEA", "EIE", "AEA", 'E', "craftingCircuitTier05", 'I', "plateAlloyIridium", 'A', "craftingCircuitTier04"});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(2, 4), new Object[] {"CCC", "WIW", "LLL", 'L', "craftingCircuitTier07", 'W', "plateTungsten", 'I', "plateAlloyIridium", 'C', getGregTechItem(34, 1, 0)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(2, 4), new Object[] {"CCC", "WIW", "LLL", 'L', "craftingCircuitTier07", 'W', "plateTungsten", 'I', "plateAlloyIridium", 'C', getGregTechItem(60, 1, 0)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(2, 4), new Object[] {"CCC", "WIW", "LLL", 'L', "craftingCircuitTier07", 'W', "plateTungsten", 'I', "plateAlloyIridium", 'C', GT_ModHandler.getIC2Item("reactorCoolantSix", 1)});
        GT_ModHandler.addShapelessCraftingRecipe(getGregTechItem(43, 1, 0), new Object[] {getGregTechItem(43, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(4, 1), new Object[] {"AGA", "RPB", "ALA", 'A', "plateAluminium", 'L', "dustGlowstone", 'R', "dyeRed", 'G', "dyeLime", 'B', "dyeBlue", 'P', new ItemStack(Block.thinGlass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(5, 1), new Object[] {"GGG", "AAA", "CBC", false, 'A', "plateAluminium", 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'G', new ItemStack(Block.glass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(5, 1), new Object[] {"GGG", "RRR", "CBC", false, 'R', "plateRefinedIron", 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'G', new ItemStack(Block.glass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(5, 1), new Object[] {"GGG", "AAA", "CBC", false, 'A', "ingotAluminium", 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'G', new ItemStack(Block.glass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(5, 1), new Object[] {"GGG", "RRR", "CBC", false, 'R', "ingotRefinedIron", 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'G', new ItemStack(Block.glass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(7, 1), new Object[] {"SGS", "CPC", 'C', "craftingCircuitTier02", 'G', new ItemStack(Block.thinGlass, 1), 'P', GT_ModHandler.getIC2Item("carbonPlate", 1), 'S', "plateSilicon"});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(17, 4), new Object[] {"DSD", "S S", "DSD", 'D', "dustDiamond", 'S', "plateSteel"});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(18, 2), new Object[] {"DSD", "SGS", "DSD", 'G', "craftingIndustrialDiamond", 'D', "dustDiamond", 'S', "plateSteel"});
        GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(23, 2), new Object[] {"TST", "SBS", "TST", 'B', "blockSteel", 'T', "plateTungsten", 'S', "plateSteel"});
        
        GT_ModHandler.addCraftingRecipe(getGregTechItem(49, 1, 0), new Object[] {"CPC"				, 'C', getGregTechItem(48, 1, 0), 'P', "plateCopper"});
        GT_ModHandler.addCraftingRecipe(getGregTechItem(50, 1, 0), new Object[] {" C ", "PPP", " C ", 'C', getGregTechItem(49, 1, 0), 'P', "plateCopper"});
        GT_ModHandler.addCraftingRecipe(getGregTechItem(52, 1, 0), new Object[] {"CPC"				, 'C', getGregTechItem(51, 1, 0), 'P', "plateCopper"});
        GT_ModHandler.addCraftingRecipe(getGregTechItem(53, 1, 0), new Object[] {" C ", "PPP", " C ", 'C', getGregTechItem(52, 1, 0), 'P', "plateCopper"});
        GT_ModHandler.addCraftingRecipe(null, new Object[] {"CPC"				, 'C', GT_ModHandler.getIC2Item("reactorUraniumSimple", 1), 'P', "plateDenseCopper"});
        GT_ModHandler.addCraftingRecipe(null, new Object[] {" C ", "PPP", " C "	, 'C', GT_ModHandler.getIC2Item("reactorUraniumDual", 1)  , 'P', "plateDenseCopper"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorUraniumDual", 1), new Object[] {"CPC"				, 'C', GT_ModHandler.getIC2Item("reactorUraniumSimple", 1), 'P', "plateCopper"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorUraniumQuad", 1), new Object[] {" C ", "PPP", " C ", 'C', GT_ModHandler.getIC2Item("reactorUraniumDual", 1)  , 'P', "plateCopper"});
        
        GT_ModHandler.addCraftingRecipe(getGregTechItem(64, 1, 0), new Object[] {" BI", "BIB", "IB ", 'I', "ingotRefinedIron", 'B', "dyeBlue"});
        GT_ModHandler.addCraftingRecipe(getGregTechItem(65, 1, 0), new Object[] {"I " , " S"        , 'I', "ingotRefinedIron", 'S', "stickWood"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorVent", 1), new Object[] {"AIA", "I I", "AIA", 'I', new ItemStack(Block.fenceIron, 1), 'A', "plateAluminium"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("reactorPlatingExplosive", 1), new Object[] {GT_ModHandler.getIC2Item("reactorPlating", 1), "plateLead"});
        
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 1,10), new Object[] {"CTC", "TMT", "CTC", 'C', "plateChrome"  , 'T', "plateTitanium", 'M', "craftingRawMachineTier02"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 4,13), new Object[] {"AAA", "CMC", "AAA", 'C', "craftingCircuitTier02", 'A', "plateRefinedIron", 'M', "craftingRawMachineTier01"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 4,13), new Object[] {"AAA", "CMC", "AAA", 'C', "craftingCircuitTier02", 'A', "plateAluminium", 'M', "craftingRawMachineTier01"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 4,14), new Object[] {"SSS", "CMC", "SSS", 'C', "craftingCircuitTier04", 'S', "plateSteel", 'M', "craftingRawMachineTier02"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 4,15), new Object[] {"TTT", "CMC", "TTT", 'C', "craftingCircuitTier06", 'T', "plateChrome", 'M', "craftingRawMachineTier04"});
    	
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("generator", 1), new Object[] {"B"  , "M"  , "F"  , false, 'B', "crafting10kEUStore", 'M', "craftingRawMachineTier01", 'F', new ItemStack(Block.furnaceIdle, 1, GregTech_API.ITEM_WILDCARD_DAMAGE)});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("generator", 1), new Object[] {" B ", "RRR", " F ", false, 'B', "crafting10kEUStore", 'R', "ingotRefinedIron", 'F', GT_ModHandler.getIC2Item("ironFurnace", 1)});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("generator", 1), new Object[] {" B ", "RRR", " F ", false, 'B', "crafting10kEUStore", 'R', "ingotAluminium"  , 'F', GT_ModHandler.getIC2Item("ironFurnace", 1)});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("generator", 1), new Object[] {" B ", "RRR", " F ", false, 'B', "crafting10kEUStore", 'R', "ingotInvar"      , 'F', GT_ModHandler.getIC2Item("ironFurnace", 1)});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("generator", 1), new Object[] {" B ", "RRR", " F ", false, 'B', "crafting10kEUStore", 'R', "plateRefinedIron", 'F', GT_ModHandler.getIC2Item("ironFurnace", 1)});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("generator", 1), new Object[] {" B ", "RRR", " F ", false, 'B', "crafting10kEUStore", 'R', "plateAluminium"  , 'F', GT_ModHandler.getIC2Item("ironFurnace", 1)});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("generator", 1), new Object[] {" B ", "RRR", " F ", false, 'B', "crafting10kEUStore", 'R', "plateInvar"      , 'F', GT_ModHandler.getIC2Item("ironFurnace", 1)});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "fusionreactor", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 80), new Object[] {"CCC", "PHP", "CCC", 'C', "craftingCircuitTier07", 'P', "craftingCircuitTier10", 'H', new ItemStack(GregTech_API.sBlockList[0], 1,  1)});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 81), new Object[] {"SCS", "CHC", "SCS", 'C', "craftingCircuitTier07", 'H', new ItemStack(GregTech_API.sBlockList[1], 1,15), 'S', "craftingSuperconductor"});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 82), new Object[] {"PWP", "CMC", "PCP", 'C', "craftingCircuitTier07", 'M', "craftingRawMachineTier04", 'W', new ItemStack(Block.chest, 1), 'P', "craftingPump"});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 83), new Object[] {"PCP", "CMC", "PWP", 'C', "craftingCircuitTier07", 'M', "craftingRawMachineTier04", 'W', new ItemStack(Block.chest, 1), 'P', "craftingPump"});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 1,  1), new Object[] {"ESE", "CMC", "EIE", 'I', GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "iridiumreflector", true)?getGregTechItem(40, 1, 0):"plateAlloyIridium", 'S', "craftingSuperconductor", 'E', "craftingCircuitTier07", 'C', "craftingHeatingCoilTier02", 'M', "craftingRawMachineTier04"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "lightningrod", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 2), new Object[] {"IAI", "AMA", "IAI", 'I', "craftingCircuitTier07", 'A', "craftingRawMachineTier04", 'M', new ItemStack(GregTech_API.sBlockList[1], 1,15)});
    	}
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 4), new Object[] {"CGD", "GAG", "DGC", 'D', "craftingCircuitTier07", 'G', "craftingMonitorTier02", 'C', "craftingCircuitTier08", 'A', "craftingRawMachineTier02"});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "lesu", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 1, 6), new Object[] {"LLL", "LML", "LLL", 'M', "craftingCircuitTier02", 'L', "chunkLazurite"});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 7), new Object[] {" L ", "ACA", " M ", 'A', "craftingCircuitTier04", 'C', new ItemStack(GregTech_API.sBlockList[0], 1, 6), 'L', GT_ModHandler.getIC2Item("lvTransformer", 1), 'M', GT_ModHandler.getIC2Item("mvTransformer", 1)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "idsu", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 8), new Object[] {"IMI", "MCM", "IMI", 'I', "plateAlloyIridium", 'C', GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "enderchest", false)?new ItemStack(Item.eyeOfEnder, 1):"craftingEnderChest", 'M', GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "aesu", true)?new ItemStack(GregTech_API.sBlockList[1], 1, 9):"crafting10kkEUStore"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "aesu", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 9), new Object[] {"MMM", "MCM", "MMM", 'C', "craftingCircuitTier10", 'M', "crafting10kkEUStore"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "chargeomat", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,10), new Object[] {"BCB", "TMT", "BAB", 'C', "craftingCircuitTier10", 'M', "crafting10kkEUStore", 'A', "craftingRawMachineTier02", 'T', new ItemStack(Block.chest, 1), 'B', "craftingCircuitTier07"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "digitalchest", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,48), new Object[] {"AAA", "ADA", "ASA", 'D', "craftingCircuitTier08", 'S', "craftingMonitorTier02", 'A', "plateRefinedIron"});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,48), new Object[] {"AAA", "ADA", "ASA", 'D', "craftingCircuitTier08", 'S', "craftingMonitorTier02", 'A', "plateAluminium"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "quantumchest", true)) {
            GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,49), new Object[] {"ASA", "BTB", "ADA", 'A', "craftingCircuitTier08", 'S', "craftingMonitorTier02", 'D', GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "digitalchest", true)?new ItemStack(GregTech_API.sBlockList[1], 1,48):"craftingCircuitTier07", 'T', "craftingTeleporter", 'B', "craftingRawMachineTier04"});
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,49), new Object[] {new ItemStack(GregTech_API.sBlockList[1], 1, 3)});
            GT_ModHandler.addCraftingRecipe(GT_MetaItem_Component.instance.getStack(29, 1), new Object[] {"ASA", "BTB", "ADA", 'A', "craftingCircuitTier08", 'S', "craftingMonitorTier02", 'D', "plateAluminium", 'T', "craftingTeleporter", 'B', "craftingRawMachineTier04"});
    		if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "quantumtank", true)) GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,30), new Object[] {"APA", "PQP", "APA", 'A', "craftingCircuitTier07", 'P', "platePlatinum", 'L', "craftingCircuitTier07", 'Q', new ItemStack(GregTech_API.sBlockList[1], 1, 49)});
    	} else {
    		if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "quantumtank", true)) GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,30), new Object[] {"ASA", "BTB", "ALA", 'A', "craftingCircuitTier08", 'S', "craftingMonitorTier02", 'L', "craftingCircuitTier07", 'T', "craftingTeleporter", 'B', "craftingRawMachineTier04"});
    	}
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,62), new Object[] {"RCR", "AEA", "RCR", 'C', "craftingCircuitTier04", 'A', "craftingRawMachineTier02",  'E', "craftingExtractor", 'R', "plateRefinedIron"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,62), new Object[] {"RCR", "AEA", "RCR", 'C', "craftingCircuitTier04", 'A', "craftingRawMachineTier02",  'E', "craftingExtractor", 'R', "plateAluminium"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,25), new Object[] {"RDR", "CEC", "RMR", 'E', GT_ModHandler.getIC2Item("electrolyzer", 1), 'M', "craftingElectromagnet", 'D', "craftingExtractor", 'R', "plateRefinedIron", 'C', "craftingCircuitTier04"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,25), new Object[] {"RDR", "CEC", "RMR", 'E', GT_ModHandler.getIC2Item("electrolyzer", 1), 'M', "craftingElectromagnet", 'D', "craftingExtractor", 'R', "plateAluminium", 'C', "craftingCircuitTier04"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,38), new Object[] {"RPR", "CGC", "RPR", 'C', "craftingCircuitTier04", 'P', "craftingPump", 'G', "glassReinforced", 'R', "plateRefinedIron"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,38), new Object[] {"RPR", "CGC", "RPR", 'C', "craftingCircuitTier04", 'P', "craftingPump", 'G', "glassReinforced", 'R', "plateAluminium"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,41), new Object[] {"RMR", "CEC", "RDR", 'M', "craftingElectromagnet", 'D', "craftingExtractor", 'E', "craftingCompressor", 'R', "plateInvar", 'C', "craftingCircuitTier04"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,41), new Object[] {"RMR", "CEC", "RDR", 'M', "craftingElectromagnet", 'D', "craftingExtractor", 'E', "craftingCompressor", 'R', "plateAluminium", 'C', "craftingCircuitTier04"});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "superconductorwire", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 4,12), new Object[] {"ALA", "CCC", "ALA", 'C', "craftingSuperconductor", 'A', "craftingRawMachineTier02", 'L', "craftingCircuitTier07"});
    	}
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,15), new Object[] {"LEL", "CAC", "LEL", 'C', "craftingSuperconductor", 'A', "craftingRawMachineTier04", 'L', "craftingCircuitTier07", 'E', "crafting10kkEUStore"});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "playerdetector", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,13), new Object[] {" E ", "ACA", " E ", 'C', "craftingCircuitTier10", 'A', "craftingCircuitTier04", 'E', "craftingCircuitTier05"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "matterfabricator", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,14), new Object[] {"ETE", "ALA", "ETE", 'L', "crafting10kkEUStore", 'A', "craftingRawMachineTier04", 'E', "craftingCircuitTier07", 'T', "craftingTeleporter"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "sonictron", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 6), new Object[] {"CRC", "NAN", "CJC", 'C', "craftingCircuitTier02", 'N', new ItemStack(Block.music, 1), 'A', "craftingRawMachineTier02", 'J', new ItemStack(Block.jukebox, 1), 'R', "itemRecord"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "electricautocraftingtable", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,16), new Object[] {"GBG", "CTC", "GAG", false, 'B', "crafting10kEUStore", 'A', "craftingRawMachineTier02"	, 'C', "craftingCircuitTier04", 'T', "craftingWorkBench", 'G', "plateElectrum"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "advancedpump", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,47), new Object[] {"CPC", "PMP", "CPC", 'C', "craftingCircuitTier04", 'M', "craftingRawMachineTier02"	, 'P', "craftingPump"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "wiremill", true)) {
    		tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, new ItemStack(Item.diamond, 1), null, new ItemStack(Item.ingotIron, 1), null, new ItemStack(Item.ingotIron, 1), null, new ItemStack(Item.ingotIron, 1), null});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,55), new Object[] {"BDB", "CMC", "BQB", 'M', "craftingRawMachineTier01", 'C', "craftingCircuitTier02", 'B', "plateBrass", 'D', tStack==null?"gemDiamond":tStack, 'Q', "craftingConveyor"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "alloysmelter", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,56), new Object[] {"IHI", "CEC", "IQI", 'H', "craftingHeatingCoilTier00", 'C', "craftingCircuitTier02", 'E', "craftingElectricFurnace", 'I', "plateInvar", 'Q', "craftingConveyor"});
        }
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "automaticmachines", true)) {
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,50), new Object[] {"craftingConveyor", "craftingMacerator"});
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,51), new Object[] {"craftingConveyor", "craftingExtractor"});
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,52), new Object[] {"craftingConveyor", "craftingCompressor"});
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,53), new Object[] {"craftingConveyor", "craftingRecycler"});
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,54), new Object[] {"craftingConveyor", "craftingElectricFurnace"});
    	}
    	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,57), new Object[] {"craftingConveyor", GT_ModHandler.getIC2Item("canner", 1)});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "microwaveoven", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,63), new Object[] {"AAA", "L M", "AAA", 'A', "plateAluminium", 'L', "plateLead", 'M', "craftingElectromagnet"});
        }

    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "universalmacerator", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,64), new Object[] {"SGS", "SPS", "SBS", 'S', "plateSteel", 'B', "craftingRawMachineTier03", 'P', "craftingMacerator", 'G', "craftingGrinder"});
        }
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,59), new Object[] {"PCP", "RQR", "PCP", 'C', "craftingCircuitTier02", 'P', new ItemStack(Block.pistonBase, 1), 'R', "craftingCompressor", 'Q', "craftingConveyor"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,60), new Object[] {"CPC", "RQR", "CRC", 'C', "craftingCircuitTier02", 'P', new ItemStack(Block.pistonBase, 1), 'R', "plateRefinedIron", 'Q', "craftingConveyor"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,60), new Object[] {"CPC", "RQR", "CRC", 'C', "craftingCircuitTier02", 'P', new ItemStack(Block.pistonBase, 1), 'R', "plateAluminium", 'Q', "craftingConveyor"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,61), new Object[] {"RPR", "CQC", "RRR", 'C', "craftingCircuitTier02", 'P', new ItemStack(Block.pistonBase, 1), 'R', "plateRefinedIron", 'Q', "craftingConveyor"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,61), new Object[] {"RPR", "CQC", "RRR", 'C', "craftingCircuitTier02", 'P', new ItemStack(Block.pistonBase, 1), 'R', "plateAluminium", 'Q', "craftingConveyor"});
        
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "automation", true)) {
    		// Translocators
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,17), new Object[] {"GBG", "CPC", "GAG", false, 'B', "crafting10kEUStore", 'A', "craftingRawMachineTier00"	, 'C', "craftingCircuitTier02", 'G', "plateElectrum", 'P', "craftingConveyor"});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,20), new Object[] {"GBG", "CPC", "GAG", false, 'B', "crafting10kEUStore", 'A', "craftingRawMachineTier02"	, 'C', "craftingCircuitTier04", 'G', "plateElectrum", 'P', "craftingConveyor"});
    		
    		// Electric Sorters
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,23), new Object[] {"GBG", "CHC", "GAG", false, 'B', "crafting10kEUStore", 'A', "craftingMonitorTier02"		, 'C', "craftingCircuitTier02", 'G', "plateElectrum", 'H', new ItemStack(GregTech_API.sBlockList[1], 1,17)});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,58), new Object[] {"GBG", "CHC", "GAG", false, 'B', "crafting10kEUStore", 'A', "craftingMonitorTier02"		, 'C', "craftingCircuitTier04", 'G', "plateElectrum", 'H', new ItemStack(GregTech_API.sBlockList[1], 1,17)});
    		
    		// Adv. Buffer
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,21), new Object[] {"GBG", "CHC", "GAG", false, 'B', "crafting10kEUStore", 'A', "craftingMonitorTier02"		, 'C', "craftingCircuitTier04", 'G', "plateElectrum", 'H', new ItemStack(GregTech_API.sBlockList[1], 1,18)});
    		
    		// Electric Regulator
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,39), new Object[] {"CHC", "HAH", "CHC", 'A', "craftingMonitorTier02", 'C', "craftingCircuitTier04", 'H', new ItemStack(GregTech_API.sBlockList[1], 1,21)});
    		
    		// large Electric Buffer
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,19), new Object[] {"GBG", "CBC", "GBG", 'C', "craftingCircuitTier04", 'G', "plateElectrum", 'B', "craftingConveyor"});
    		
    		// Inventory Manager
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,46), new Object[] {"CRC", "SMS", "CEC", 'M', "craftingCircuitTier10", 'C', "craftingCircuitTier05", 'R', new ItemStack(GregTech_API.sBlockList[1], 1,39), 'S', new ItemStack(GregTech_API.sBlockList[1], 1,23), 'E', "crafting100kEUStore"});
    		
    		// Large and Small Buffer crafting Recipes
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,19), new Object[] {"TTT", "TCT", "TTT", 'T', new ItemStack(GregTech_API.sBlockList[1], 1, 18)		, 'C', "craftingCircuitTier04"});
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 8,18), new Object[] {new ItemStack(GregTech_API.sBlockList[1], 1, 19)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "itemclearer", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,24), new Object[] {"GBG", "PMH", "GCG", false, 'B', "crafting10kEUStore", 'M', "craftingRawMachineTier02"	, 'C', "craftingCircuitTier02", 'G', "plateElectrum", 'P', "craftingTeleporter", 'H', new ItemStack(GregTech_API.sBlockList[1], 1,18)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "rockbreaker", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,22), new Object[] {"ODO", "CMC", "OAO", 'D', "itemDiamond", 'A', "craftingRawMachineTier02"	, 'C', "craftingCircuitTier02", 'M', GT_ModHandler.getIC2Item("miningDrill", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'O', "plateInvar"});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,22), new Object[] {"ODO", "CMC", "OAO", 'D', "itemDiamond", 'A', "craftingRawMachineTier02"	, 'C', "craftingCircuitTier02", 'M', GT_ModHandler.getIC2Item("miningDrill", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'O', "plateAluminium"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "cropharvestor", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,26), new Object[] {"GHG", "DPM", "GCG", 'D', "craftingDiamondBlade", 'M', "craftingRawMachineTier02", 'C', "craftingCircuitTier02", 'G', "plateElectrum", 'P', new ItemStack(Block.pistonStickyBase, 1), 'H', GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "itemclearer", true)?new ItemStack(GregTech_API.sBlockList[1], 1,24):new ItemStack(GregTech_API.sBlockList[1], 1,18)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "scrapboxinator", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,27), new Object[] {"GHG", "DPM", "GCG", 'P', new ItemStack(Block.pressurePlatePlanks, 1), 'M', "craftingRawMachineTier02"	, 'C', "craftingCircuitTier02", 'G', "plateElectrum", 'D', new ItemStack(Block.dispenser, 1), 'H', GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "itemclearer", true)?new ItemStack(GregTech_API.sBlockList[1], 1,24):new ItemStack(GregTech_API.sBlockList[1], 1,18)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "advancedsafe", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,45), new Object[] {"C", "S", "B", 'C', "craftingCircuitTier04", 'S', GT_ModHandler.getIC2Item("personalSafe", 1), 'B', new ItemStack(GregTech_API.sBlockList[1], 1,18)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "industrialgrinder", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,28), new Object[] {"EAP", "GGG", "ABA", 'B', "craftingRawMachineTier02", 'A', "craftingCircuitTier04", 'G', "craftingGrinder", 'E', new ItemStack(GregTech_API.sBlockList[1], 1,25), 'P', "craftingPump"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "distillationtower", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,44), new Object[] {"CAC", "PBP", "EAE", 'B', "craftingRawMachineTier04", 'A', "craftingCircuitTier07", 'C', new ItemStack(GregTech_API.sBlockList[1], 1,62), 'E', new ItemStack(GregTech_API.sBlockList[1], 1,25), 'P', "craftingPump"});
        }
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "sawmill", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,32), new Object[] {"PAP", "DDD", "ABA", 'B', "craftingRawMachineTier02", 'A', "craftingCircuitTier04", 'D', "craftingDiamondBlade", 'P', "craftingPump"});
        }
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,33), new Object[] {"XXX", "X X", "CGC", 'X', "plateRefinedIron", 'C', "craftingCircuitTier02", 'G', GT_ModHandler.getIC2Item("generator", 1)});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,33), new Object[] {"XXX", "X X", "CGC", 'X', "plateAluminium", 'C', "craftingCircuitTier02", 'G', GT_ModHandler.getIC2Item("generator", 1)});
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,34), new Object[] {"XCX", "WPW", "XCX", 'X', "plateInvar", 'C', "craftingCircuitTier04", 'W', GT_ModHandler.getIC2Item("windMill", 1), 'P', "glassReinforced"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,34), new Object[] {"XCX", "WPW", "XCX", 'X', "plateAluminium", 'C', "craftingCircuitTier04", 'W', GT_ModHandler.getIC2Item("windMill", 1), 'P', "glassReinforced"});
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,35), new Object[] {"XXX", "XPX", "CGC", 'X', "plateInvar", 'C', "craftingCircuitTier02", 'G', GT_ModHandler.getIC2Item("geothermalGenerator", 1), 'P', "glassReinforced"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,35), new Object[] {"XXX", "XPX", "CGC", 'X', "plateAluminium", 'C', "craftingCircuitTier02", 'G', GT_ModHandler.getIC2Item("geothermalGenerator", 1), 'P', "glassReinforced"});
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,36), new Object[] {"XXX", "XPX", "CGC", 'X', "plateRefinedIron", 'C', "craftingCircuitTier02", 'G', GT_ModHandler.getIC2Item("generator", 1), 'P', "glassReinforced"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,36), new Object[] {"XXX", "XPX", "CGC", 'X', "plateAluminium", 'C', "craftingCircuitTier02", 'G', GT_ModHandler.getIC2Item("generator", 1), 'P', "glassReinforced"});
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,37), new Object[] {"XXX", "XPX", "CGC", 'X', "plateTungstenSteel", 'C', "craftingCircuitTier07", 'G', GT_ModHandler.getIC2Item("generator", 1), 'P', GT_ModHandler.getIC2Item("hvTransformer", 1)});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "dragoneggenergysiphon", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,40), new Object[] {"CTC", "ISI", "CLC", 'C', "craftingCircuitTier07", 'L', "crafting10kkEUStore", 'S', new ItemStack(GregTech_API.sBlockList[1], 1,15), 'T', "craftingTeleporter", 'I', "plateAlloyIridium"});
    	}
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,42), new Object[] {"CTC", "PSP", "CLC", 'C', "craftingCircuitTier04", 'L', "crafting1kkEUStore", 'S', new ItemStack(Block.beacon, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'T', "craftingTeleporter", 'P', GT_ModHandler.mTCResource==null?"platePlatinum":new ItemStack(GT_ModHandler.mTCResource.getItem(), 1, 2)});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "magicenergyabsorber", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,43), new Object[] {"CSC", "IBI", "CMC", 'C', "craftingCircuitTier07", 'S', "craftingSuperconductor", 'B', new ItemStack(Block.beacon, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'M', new ItemStack(GregTech_API.sBlockList[1], 1,42), 'I', "plateAlloyIridium"});
    	}
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,29), new Object[] {"ACA", "CMC", "FCF", 'M', "craftingRawMachineTier02", 'A', "craftingCircuitTier02", 'C', "craftingHeatingCoilTier00", 'F', GT_ModHandler.getIC2Item("inductionFurnace", 1)});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,31), new Object[] {"ABA", "DCD", "ABA", 'B', "craftingRawMachineTier02", 'D', "craftingCircuitTier02", 'C', "craftingCompressor", 'A', "plateAlloyAdvanced"});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "redstonecircuitblock", true)) {
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 68), new Object[] {"PPP", "CAC", "PPP", 'P', "plateRefinedIron", 'A', "craftingMonitorTier02", 'C', new ItemStack(Item.field_94585_bY /**Comparator*/, 1)});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 68), new Object[] {"PPP", "CAC", "PPP", 'P', "plateAluminium"	, 'A', "craftingMonitorTier02", 'C', new ItemStack(Item.field_94585_bY /**Comparator*/, 1)});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 69), new Object[] {"PRP", "CAC", "PRP", 'P', "plateRefinedIron", 'A', "craftingCircuitTier02", 'C', new ItemStack(Item.field_94585_bY /**Comparator*/, 1), 'R', new ItemStack(Item.redstoneRepeater, 1)});
    		GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1, 69), new Object[] {"PRP", "CAC", "PRP", 'P', "plateAluminium"	, 'A', "craftingCircuitTier02", 'C', new ItemStack(Item.field_94585_bY /**Comparator*/, 1), 'R', new ItemStack(Item.redstoneRepeater, 1)});
        }
    	
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 4,70), new Object[] {"WWW", "A A", "WWW", 'W', "plankWood", 'A', "plateAluminium"});
    	GT_ModHandler.addCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 4,71), new Object[] {"III", "A A", "III", 'I', "plateRefinedIron", 'A', "plateAluminium"});
    	
    	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,71), new Object[] {new ItemStack(GregTech_API.sBlockList[1], 1,74)});
    	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,72), new Object[] {new ItemStack(GregTech_API.sBlockList[1], 1,71)});
    	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,73), new Object[] {new ItemStack(GregTech_API.sBlockList[1], 1,72)});
    	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[1], 1,74), new Object[] {new ItemStack(GregTech_API.sBlockList[1], 1,73)});
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "reinforcedblocks", true))	{
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 1, 2), new Object[] {GT_ModHandler.getIC2Item("reinforcedStone", 1), "ingotIridium"});
    		GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[0], 1, 2), new Object[] {GT_ModHandler.getIC2Item("reinforcedStone", 1), "plateIridium"});
        	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[4], 1, 8), new Object[] {GT_ModHandler.getIC2Item("reinforcedStone", 1), "plateTungstenSteel"});
        	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[4], 1, 9), new Object[] {new ItemStack(GregTech_API.sBlockList[0], 1, 2), "plateTungstenSteel"});
        	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[4], 1, 9), new Object[] {new ItemStack(GregTech_API.sBlockList[4], 1, 8), "plateIridium"});
        	GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(GregTech_API.sBlockList[4], 1, 9), new Object[] {new ItemStack(GregTech_API.sBlockList[4], 1, 8), "ingotIridium"});
    	}
    	
    	addAssemblerRecipe(GT_ModHandler.getIC2Item("electronicCircuit", 1), GT_ModHandler.getIC2Item("frequencyTransmitter", 1), getGregTechItem(17, 1, 0), 1600, 2);
    	addAssemblerRecipe(getGregTechItem(16, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), null, GT_ModHandler.getIC2Item("electronicCircuit", 2), 1600, 2);
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "destructopack", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(33, 1, 0), new Object[] {"ARA", "RLR", "ARA", 'A', "craftingCircuitTier04", 'R', "plateRefinedIron", 'L', new ItemStack(Item.bucketLava, 1)});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(33, 1, 0), new Object[] {"ARA", "RLR", "ARA", 'A', "craftingCircuitTier04", 'R', "plateAluminium", 'L', new ItemStack(Item.bucketLava, 1)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "heliumcoolant", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(34, 1, 0), new Object[] {" T ", "THT", " T ", 'H', "molecule_1he", 'T', "ingotTin"});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(35, 1, 0), new Object[] {"TTT", "HHH", "TTT", 'H', getGregTechItem(34, 1, 0), 'T', "ingotTin"});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(36, 1, 0), new Object[] {"THT", "TCT", "THT", 'H', getGregTechItem(35, 1, 0), 'T', "ingotTin", 'C', "plateDenseCopper"});
    		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("overclockerUpgrade", 2), new Object[] {" H ", "WCW", "   ", 'H', getGregTechItem(34, 1, 0), 'W', "copperWire", 'C', "craftingCircuitTier02"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "nakcoolant", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(60, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new Object[] {"TNT", "KCK", "TNT", 'N', "molecule_1na", 'K', "molecule_1k", 'T', "ingotTin", 'C', GT_ModHandler.getIC2Item("reactorCoolantSimple", 1)});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(60, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new Object[] {"TKT", "NCN", "TKT", 'N', "molecule_1na", 'K', "molecule_1k", 'T', "ingotTin", 'C', GT_ModHandler.getIC2Item("reactorCoolantSimple", 1)});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(61, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new Object[] {"TTT", "HHH", "TTT", 'H', getGregTechItem(60, 1, 0), 'T', "ingotTin"});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(62, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new Object[] {"THT", "TCT", "THT", 'H', getGregTechItem(61, 1, 0), 'T', "ingotTin", 'C', "plateDenseCopper"});
    		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("overclockerUpgrade", 2), new Object[] {" H ", "WCW", "   ", 'H', getGregTechItem(60, 1, 0), 'W', "copperWire", 'C', "craftingCircuitTier02"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "iridiumreflector", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(40, 1, 0), new Object[] {"NNN", "NIN", "NNN", 'I', "plateAlloyIridium", 'N', GT_ModHandler.getIC2Item("reactorReflectorThick", 1)});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "lapotronpack", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(45, 1, 0), new Object[] {"CLC", "SPS", "CIC", 'L', "crafting10kkEUStore", 'C', "craftingCircuitTier07", 'I', "plateAlloyIridium", 'P', "crafting300kEUPack", 'S', "craftingSuperconductor"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "lithiumbattery", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(56, 1, 0), new Object[] {" C ", "ALA", "ALA", 'C', GT_ModHandler.getIC2Item("doubleInsulatedGoldCableItem", 1), 'L', "molecule_1li", 'A', "plateAluminium"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "lithiumbatpack", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(58, 1, 0), new Object[] {"LCL", "LAL", "L L", 'L', "craftingLiBattery", 'C', "craftingCircuitTier04", 'A', "plateAluminium"});
    	}
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "lighthelmet", true)) {
    		GT_ModHandler.addShapelessCraftingRecipe(getGregTechItem(44, 1, 0), new Object[] {GT_ModHandler.getIC2Item("solarHelmet", 1), GT_ModHandler.getIC2Item("luminator", 1)});	
    	}
    	
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "rockcutter", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(46, 1, 0), new Object[] {"DR ", "DR ", "DCB", false, 'B', "crafting10kEUStore", 'C', "craftingCircuitTier02", 'R', "plateTitanium", 'D', "dustDiamond"});
        }
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "jackhammer", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(39, 1, 0), new Object[] {"RBR", " C ", " R ", false, 'B', "crafting10kEUStore", 'C', "craftingCircuitTier02", 'R', "ingotRefinedIron"});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(41, 1, 0), new Object[] {"RBR", " C ", " R ", false, 'B', "crafting10kEUStore", 'C', "craftingCircuitTier04", 'R', "ingotSteel"});
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(42, 1, 0), new Object[] {"RBR", " C ", " D ", 'B', "crafting100kEUStore", 'C', "craftingCircuitTier04", 'R', "ingotTitanium", 'D', "dustDiamond"});
        }
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "teslastaff", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(47, 1, 0), new Object[] {"LS ", "SI ", "  I", 'L', "crafting10kkEUStore", 'S', "craftingSuperconductor", 'I', "plateAlloyIridium"});
        }
    	if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "cloakingdevice", true)) {
    		GT_ModHandler.addCraftingRecipe(getGregTechItem(38, 1, 0), new Object[] {"CIC", "ILI", "CIC", 'L', "crafting10kkEUStore", 'I', "plateAlloyIridium", 'C', "ingotChrome"});
    	}
    	
        GT_ModHandler.addShapelessCraftingRecipe(getGregTechItem(31, 1, 0), new Object[] {new ItemStack(Item.bowlEmpty, 1), new ItemStack(Item.flint, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("hydratedCoalDust", 1)	, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Item.coal, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustGold", 1)			, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotGold"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustClay", 1)			, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.blockClay, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustCopper", 1)			, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotCopper"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustTin", 1)				, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotTin"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustSilver", 1)			, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotSilver"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustElectrum", 1)		, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotElectrum"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustWheat", 1)			, new Object[] {getGregTechItem(31, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Item.wheat, 1)});
        
        GT_ModHandler.addCraftingRecipe(getGregTechItem(30, 1, 0), new Object[] {" R ", "SRS", "SSS", 'S', "stoneBricks", 'R', "ingotRefinedIron"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("hydratedCoalDust", 1)	, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Item.coal, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustGold", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotGold"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustClay", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.blockClay, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustCopper", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotCopper"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustTin", 1)				, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotTin"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustBronze", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotBronze"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustSilver", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotSilver"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustElectrum", 1)		, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotElectrum"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustBrass", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotBrass"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustIron", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotIron"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustIron", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), "ingotRefinedIron"});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Item.flint, 1)						, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Block.gravel, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustWheat", 1)			, new Object[] {getGregTechItem(30, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), new ItemStack(Item.wheat, 1)});
        
        if (!GregTech_API.sConfiguration.addAdvConfig("blastfurnacerequirements", "steel", true)) {
            GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustSteel"	, 1)	, new Object[] {"dustIron", "dustCoal", "dustCoal"});
        	GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustSteel"	, 1)	, new Object[] {"dustRefinedIron", "dustCoal", "dustCoal"});
        }
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustElectrum", 2)		, new Object[] {"dustSilver", "dustGold"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustElectrum", 1)		, new Object[] {"dustSmallSilver", "dustSmallSilver", "dustSmallGold", "dustSmallGold"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustBrass"	, 4)		, new Object[] {"dustCopper", "dustCopper", "dustCopper", "dustZinc"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustBrass"	, 1)		, new Object[] {"dustSmallCopper", "dustSmallCopper", "dustSmallCopper", "dustSmallZinc"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustBronze"	, 2)		, new Object[] {"dustCopper", "dustCopper", "dustCopper", "dustTin"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustInvar"	, 3)		, new Object[] {"dustIron", "dustIron", "dustNickel"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get("dustInvar"	, 3)		, new Object[] {"dustRefinedIron", "dustRefinedIron", "dustNickel"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_SmallDust.instance.getStack( 29, 3)	, new Object[] {"dustSmallIron", "dustSmallIron", "dustSmallNickel"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_SmallDust.instance.getStack( 29, 3)	, new Object[] {"dustSmallRefinedIron", "dustSmallRefinedIron", "dustSmallNickel"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_SmallDust.instance.getStack(245, 2)	, new Object[] {"dustSmallCopper", "dustSmallCopper", "dustSmallCopper", "dustSmallTin"});
        
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Item.gunpowder, 3), new Object[] {"dustCoal", "craftingSulfurToGunpowder", "craftingSaltpeterToGunpowder", "craftingSaltpeterToGunpowder"});
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Item.gunpowder, 2), new Object[] {"dustCharcoal", "craftingSulfurToGunpowder", "craftingSaltpeterToGunpowder", "craftingSaltpeterToGunpowder"});
        
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("fertilizer", 4), new Object[] {GT_ModHandler.getIC2Item("fertilizer", 1), "dustPhosphorus"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("fertilizer", 2), new Object[] {GT_ModHandler.getIC2Item("fertilizer", 1), new ItemStack(Item.dyePowder, 1, 15)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("fertilizer", 3), new Object[] {GT_ModHandler.getIC2Item("fertilizer", 1), "dustSulfur", "molecule_1ca"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("fertilizer", 3), new Object[] {GT_ModHandler.getIC2Item("fertilizer", 1), "molecule_1s", "molecule_1ca"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("fertilizer", 2), new Object[] {GT_ModHandler.getIC2Item("fertilizer", 1), "dustAsh", "dustAsh", "dustAsh"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("fertilizer", 2), new Object[] {GT_ModHandler.getIC2Item("fertilizer", 1), "dustDarkAsh"});
        
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Dust.instance.getStack(9, 10), new Object[] {"molecule_1k", "molecule_1k", "molecule_1n", "molecule_1n", "molecule_2o", "molecule_2o", "molecule_2o"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Dust.instance.getStack(9,  5), new Object[] {"molecule_1k", "molecule_1n", "molecule_1o", "molecule_1o", "molecule_1o"});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("carbonFiber",1), new Object[] {"molecule_1c", "molecule_1c", "molecule_1c", "molecule_1c", "molecule_1c", "molecule_1c", "molecule_1c", "molecule_1c", "molecule_1c"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("suBattery", 32), new Object[] {"C", "Q", "R", 'Q', "molecule_1hg", 'R', "dustRedstone", 'C', "copperWire"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("suBattery", 32), new Object[] {"C", "R", "Q", 'Q', "molecule_1hg", 'R', "dustRedstone", 'C', "copperWire"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("suBattery", 32), new Object[] {"C", "S", "R", 'S', "molecule_2h_1s_4o", 'R', "dustLead", 'C', "copperWire"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("suBattery", 32), new Object[] {"C", "R", "S", 'S', "molecule_2h_1s_4o", 'R', "dustLead", 'C', "copperWire"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reBattery",  2), new Object[] {" C ", "TLT", "TST", 'S', "molecule_2h_1s_4o", 'L', "dustLead", 'C', "copperWire", 'T', "plateTin"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reBattery",  2), new Object[] {" C ", "TST", "TLT", 'S', "molecule_2h_1s_4o", 'L', "dustLead", 'C', "copperWire", 'T', "plateTin"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("glassFiberCableItem", 4), new Object[] {"GGG", "XDX", "GGG", 'G', new ItemStack(Block.glass, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'X', "dustRedstone", 'D', "itemDiamond"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("glassFiberCableItem", 6), new Object[] {"GGG", "XDX", "GGG", 'G', new ItemStack(Block.glass, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'X', "ingotSilver", 'D', "itemDiamond"});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("glassFiberCableItem", 8), new Object[] {"GGG", "XDX", "GGG", 'G', new ItemStack(Block.glass, 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'X', "ingotElectrum", 'D', "itemDiamond"});
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("electronicCircuit", 1), new Object[] {"CCC", "SRS", "CCC", 'C', "copperWire", 'R', "ingotRefinedIron", 'S', "dustRedstone"});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("electronicCircuit", 1), new Object[] {"CSC", "CRC", "CSC", 'C', "copperWire", 'R', "ingotRefinedIron", 'S', "dustRedstone"});
    	
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("advancedCircuit", 1), new Object[] {"SGS", "LCL", "SGS", 'C', GT_ModHandler.getIC2Item("electronicCircuit", 1), 'S', "dustRedstone", 'G', "dustGlowstone", 'L', "itemLazurite"});
       	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("advancedCircuit", 1), new Object[] {"SLS", "GCG", "SLS", 'C', GT_ModHandler.getIC2Item("electronicCircuit", 1), 'S', "dustRedstone", 'G', "dustGlowstone", 'L', "itemLazurite"});
       	
        if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "energycrystalruby", true)) {
        	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("energyCrystal", 1), new Object[] {"DDD", "DRD", "DDD", 'D', "dustRedstone", 'R', "gemRuby"});
        }
        
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("lapotronCrystal", 1), new Object[] {"LCL", "LSL", "LCL", 'C', "craftingCircuitTier02", 'S', GT_ModHandler.getIC2Item("energyCrystal", 1, GregTech_API.ITEM_WILDCARD_DAMAGE), 'L', "itemLazurite"});
    	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("lapPack", 1), new Object[] {"LCL", "LBL", "L L", 'C', "craftingCircuitTier04", 'B', "crafting60kEUPack",  'L', "chunkLazurite"});
    	
        if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "lapotroncrystalsapphire", true)) {
        	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("lapotronCrystal", 1), new Object[] {"LCL", "LSL", "LCL", 'C', "craftingCircuitTier02", 'S', "gemSapphire", 'L', "itemLazurite"});
        }
        if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "mininglaser", true)) {
        	if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("miningLaser", 1)))
        		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("miningLaser", 1), new Object[] {"RHE", "TTC", " AA", 'C', "craftingCircuitTier04", 'H', GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "heliumcoolant", true)?getGregTechItem(36, 1, GregTech_API.ITEM_WILDCARD_DAMAGE):GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "nakcoolant", true)?getGregTechItem(62, 1, GregTech_API.ITEM_WILDCARD_DAMAGE):GT_ModHandler.getIC2Item("reactorCoolantSix", 1), 'R', "gemRuby", 'T', "plateTitanium", 'E', "crafting100kEUStore", 'A', "plateAlloyAdvanced"});
        }
        
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("luminator", 16), new Object[] {"RTR", "GHG", "GGG", 'H', "molecule_1he", 'T', "ingotTin", 'R', "ingotRefinedIron", 'G', new ItemStack(Block.glass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("luminator", 16), new Object[] {"RTR", "GHG", "GGG", 'H', "molecule_1hg", 'T', "ingotTin", 'R', "ingotRefinedIron", 'G', new ItemStack(Block.glass, 1)});
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("mfeUnit", 1), new Object[] {"GCG", "CMC", "GCG", 'C', "crafting100kEUStore", 'G', GT_ModHandler.getIC2Item("doubleInsulatedGoldCableItem", 1), 'M', "craftingRawMachineTier01"});
        
        //GT_ModHandler.addShapelessCraftingRecipe(getGregTechItem(54, 1, 0), new Object[] {"molecule_1li", GT_ModHandler.getEmptyCell(1)});
        
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getEmptyCell(1), new Object[] {GT_ModHandler.getIC2Item("airCell", 1)});
        
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Material.instance.getStack(0, 8)	, new Object[] {GT_ModHandler.getIC2Item("coin", 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("coin", 8)			, new Object[] {GT_MetaItem_Material.instance.getStack(1, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Material.instance.getStack(1, 8)	, new Object[] {GT_MetaItem_Material.instance.getStack(2, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Material.instance.getStack(2, 8)	, new Object[] {GT_MetaItem_Material.instance.getStack(3, 1)});
        
        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item("coin", 1)			, new Object[] {GT_MetaItem_Material.instance.getStack(0, 1), GT_MetaItem_Material.instance.getStack(0, 1), GT_MetaItem_Material.instance.getStack(0, 1), GT_MetaItem_Material.instance.getStack(0, 1), GT_MetaItem_Material.instance.getStack(0, 1), GT_MetaItem_Material.instance.getStack(0, 1), GT_MetaItem_Material.instance.getStack(0, 1), GT_MetaItem_Material.instance.getStack(0, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Material.instance.getStack(1, 1)	, new Object[] {GT_ModHandler.getIC2Item("coin", 1), GT_ModHandler.getIC2Item("coin", 1), GT_ModHandler.getIC2Item("coin", 1), GT_ModHandler.getIC2Item("coin", 1), GT_ModHandler.getIC2Item("coin", 1), GT_ModHandler.getIC2Item("coin", 1), GT_ModHandler.getIC2Item("coin", 1), GT_ModHandler.getIC2Item("coin", 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Material.instance.getStack(2, 1)	, new Object[] {GT_MetaItem_Material.instance.getStack(1, 1), GT_MetaItem_Material.instance.getStack(1, 1), GT_MetaItem_Material.instance.getStack(1, 1), GT_MetaItem_Material.instance.getStack(1, 1), GT_MetaItem_Material.instance.getStack(1, 1), GT_MetaItem_Material.instance.getStack(1, 1), GT_MetaItem_Material.instance.getStack(1, 1), GT_MetaItem_Material.instance.getStack(1, 1)});
        GT_ModHandler.addShapelessCraftingRecipe(GT_MetaItem_Material.instance.getStack(3, 1)	, new Object[] {GT_MetaItem_Material.instance.getStack(2, 1), GT_MetaItem_Material.instance.getStack(2, 1), GT_MetaItem_Material.instance.getStack(2, 1), GT_MetaItem_Material.instance.getStack(2, 1), GT_MetaItem_Material.instance.getStack(2, 1), GT_MetaItem_Material.instance.getStack(2, 1), GT_MetaItem_Material.instance.getStack(2, 1), GT_MetaItem_Material.instance.getStack(2, 1)});
        
        ItemStack tMat = GT_ModHandler.getIC2Item("matter", 1);
    	
        GT_ModHandler.removeRecipe(new ItemStack[] {tStack = GT_OreDictUnificator.get("dustSulfur", 1), tStack, tStack, tStack, new ItemStack(Item.coal, 1, 0), tStack, tStack, tStack, tStack});
        GT_ModHandler.removeRecipe(new ItemStack[] {tStack = GT_OreDictUnificator.get("dustSulfur", 1), tStack, tStack, tStack, new ItemStack(Item.coal, 1, 1), tStack, tStack, tStack, tStack});
        GT_ModHandler.removeRecipe(new ItemStack[] {tStack = new ItemStack(Item.seeds, 1), tStack, tStack, tStack, null, tStack, tStack, tStack, tStack});
		
        GT_Log.out.println("GT_Mod: Applying harder Recipes for Electric Tools and several Blocks.");
        if (GT_ModHandler.removeRecipe(GT_OreDictUnificator.get("plateAlloyIridium", 1))) {
    		GT_ModHandler.addRollingMachineRecipe(GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "iridiumplate", true)?GT_MetaItem_Material.instance.getStack(4, 1):GT_OreDictUnificator.get("plateAlloyIridium", 1), new Object[] {"IAI", "ADA", "IAI", 'D', GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "iridiumplate", true)?"craftingIndustrialDiamond":"dustDiamond", 'A', "plateAlloyAdvanced", 'I', "ingotIridium"});
    	}
        if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "blockbreaker", false)) {
        	GT_ModHandler.addCraftingRecipe(GT_ModHandler.removeRecipe(new ItemStack[] {new ItemStack(Block.cobblestone, 1), new ItemStack(Item.pickaxeSteel, 1), new ItemStack(Block.cobblestone, 1), new ItemStack(Block.cobblestone, 1), new ItemStack(Block.pistonBase, 1), new ItemStack(Block.cobblestone, 1), new ItemStack(Block.cobblestone, 1), new ItemStack(Item.redstone, 1), new ItemStack(Block.cobblestone, 1)}), new Object[] {"RGR", "RPR", "RCR" , 'G', "craftingGrinder", 'C', "craftingCircuitTier04", 'R', "plateSteel", 'P', new ItemStack(Block.pistonBase, 1)});
        }
        if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "beryliumreflector", true)) {
        	if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("reactorReflectorThick", 1)))
        		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("reactorReflectorThick", 1), new Object[] {" N ", "NBN", " N ", 'B', "molecule_1be", 'N', GT_ModHandler.getIC2Item("reactorReflector", 1)});
        }
        if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "macerator", true)) {
        	if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("macerator", 1))) {
        		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("macerator", 1), new Object[] {"FDF", "DMD", "FAF", 'F', new ItemStack(Item.flint, 1), 'A', "craftingCircuitTier04", 'M', "craftingRawMachineTier01", 'D', "itemDiamond"});
        		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("macerator", 1), new Object[] {"FDF", "AMA", "FAF", 'F', new ItemStack(Item.flint, 1), 'A', "craftingCircuitTier02", 'M', "craftingRawMachineTier01", 'D', "craftingGrinder"});
        	}
        }
        
    	if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "windmill", true)) {
        	GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("windMill", 1));
        }
		if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "watermill", true)) {
        	GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("waterMill", 1));
        }
        if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "solarpanel", true)) {
        	GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("solarPanel", 1));
        }
        
        if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "nuke", true)) {
        	if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("nuke", 1)))
        		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("nuke", 1), new Object[] {"CRC", "NPN", "CRC", 'C', "craftingCircuitTier04", 'R', GT_ModHandler.getIC2Item("reEnrichedUraniumCell", 1), 'P', "ingotPlutonium", 'N', GT_ModHandler.getIC2Item("reactorReflectorThick", 1)});
        }
        
        if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "nanosaber", true)) {
        	if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("nanoSaber", 1)))
        		GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("nanoSaber", 1), new Object[] {"PI ", "PI ", "CLC", 'L', "crafting1kkEUStore", 'I', "plateAlloyIridium", 'P', "platePlatinum", 'C', "craftingCircuitTier07"});
        }
        
		if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "quarry", true) && GT_ModHandler.mBCDiamondGear != null) {
			GT_ModHandler.addCraftingRecipe(GT_ModHandler.removeRecipe(new ItemStack[] {GT_ModHandler.mBCIronGear, new ItemStack(Item.redstone, 1), GT_ModHandler.mBCIronGear, GT_ModHandler.mBCGoldGear, GT_ModHandler.mBCIronGear, GT_ModHandler.mBCGoldGear, GT_ModHandler.mBCDiamondGear, new ItemStack(Item.pickaxeDiamond, 1), GT_ModHandler.mBCDiamondGear}), new Object[] {"ICI", "GIG", "DPD", 'C', "craftingCircuitTier04", 'D', GT_ModHandler.mBCDiamondGear, 'G', GT_ModHandler.mBCGoldGear, 'I', GT_ModHandler.mBCIronGear, 'P', GT_ModHandler.getIC2Item("diamondDrill", 1, GregTech_API.ITEM_WILDCARD_DAMAGE)});
		}
		
		if (GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "namefix", true) && GT_ModHandler.mBCDiamondGear != null) {
	        GT_ModHandler.addCraftingRecipe(GT_ModHandler.removeRecipe(new ItemStack(Item.flintAndSteel, 1))?new ItemStack(Item.flintAndSteel, 1):null, new Object[] {"S ", " F",false, 'F', new ItemStack(Item.flint, 1), 'S', "nuggetSteel"});
		}
		
        if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("diamondDrill"		, 1)))	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("diamondDrill"		, 1), new Object[] {" D ", "DMD", "TAT", false, 'M', GT_ModHandler.getIC2Item("miningDrill", 1), 'D', "craftingIndustrialDiamond", 'T', "plateTitanium", 'A', "craftingCircuitTier04"});
        if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("miningDrill"		, 1)))	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("miningDrill"		, 1), new Object[] {" S ", "SCS", "SBS", false, 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'S', GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "electricsteeltools", true)?"plateSteel":"plateRefinedIron"});
        if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("chainsaw"			, 1)))	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("chainsaw"			, 1), new Object[] {" SS", "SCS", "BS ", false, 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'S', GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "electricsteeltools", true)?"plateSteel":"plateRefinedIron"});
        if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("electricHoe"		, 1)))	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("electricHoe"		, 1), new Object[] {"SS ", " C ", " B ", false, 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'S', GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "electricsteeltools", true)?"plateSteel":"plateRefinedIron"});
        if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("electricTreetap"	, 1)))	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("electricTreetap"	, 1), new Object[] {" B ", "SCS", "S  ", false, 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'S', GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "electricsteeltools", true)?"plateSteel":"plateRefinedIron"});
        if (GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("electricWrench"	, 1)))	GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item("electricWrench"	, 1), new Object[] {"S S", "SCS", " B ", false, 'C', "craftingCircuitTier02", 'B', "crafting10kEUStore", 'S', GregTech_API.sConfiguration.addAdvConfig("harderrecipes", "electricsteeltools", true)?"plateSteel":"plateRefinedIron"});
        
        GT_Log.out.println("GT_Mod: Removing Q-Armor Recipes if configured.");
        if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "QHelmet"		, false)) GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("quantumHelmet", 1));
        if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "QPlate"		, false)) GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("quantumBodyarmor", 1));
        if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "QLegs"			, false)) GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("quantumLeggings", 1));
        if (GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "QBoots"		, false)) GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("quantumBoots", 1));
        
        GT_Log.out.println("GT_Mod: Removing Mass Fabricator Recipe if configured.");
        if (GT_TileEntity_Matterfabricator.sMassfabdisabled = GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "massfabricator",  true)) GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item("massFabricator", 1));
        
        
        GT_Log.out.println("GT_Mod: Adding/Removing/Overloading UUM Recipes.");
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("gemDiamond"		, 1)	, new Object[] {"UUU", "UUU", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("gemEmerald"		, 2)	, new Object[] {"UUU", "UUU", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("gemRuby"			, 2)	, new Object[] {" UU", "UUU", "UU ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("gemSapphire"		, 2)	, new Object[] {"UU ", "UUU", " UU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("gemGreenSapphire"	, 2)	, new Object[] {" UU", "UUU", " UU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("gemOlivine"		, 2)	, new Object[] {"UU ", "UUU", "UU ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustIron", 4)				, new Object[] {"U U", " U ", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustGold", 2)				, new Object[] {" U ", "UUU", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustTin", 10)				, new Object[] {"   ", "U U", "  U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustCopper", 10)			, new Object[] {"  U", "U U", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustZinc", 10)				, new Object[] {"   ", "U U", "U  ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustNickel", 10)			, new Object[] {"U  ", "U U", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustLead", 14)				, new Object[] {"UUU", "UUU", "U  ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustSilver", 14)			, new Object[] {" U ", "UUU", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustPlatinum", 1)			, new Object[] {"  U", "UUU", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustTungsten", 6)			, new Object[] {"U  ", "UUU", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustGlowstone", 32)		, new Object[] {" U ", "U U", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustSmallOsmium", 1)		, new Object[] {"U U", "UUU", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustTitanium", 2)			, new Object[] {"UUU", " U ", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustAluminium", 16)		, new Object[] {" U ", " U ", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustRedstone", 24)			, new Object[] {"   ", " U ", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.getFirstOre("dustNikolite", 12)	, new Object[] {"UUU", " U ", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.coal, 8)							, new Object[] {"  U", "U  ", "  U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.enderPearl, 1)					, new Object[] {"UUU", "U U", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.blazeRod, 4)						, new Object[] {"U U", "UU ", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.waterlily, 32)					, new Object[] {"U U", "UUU", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.leather, 32)						, new Object[] {"U U", " U ", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.silk, 32)							, new Object[] {"U U", "   ", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.obsidian, 12)					, new Object[] {"U U", "U U", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.netherrack, 16)					, new Object[] {"  U", " U ", "U  ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.waterStill, 1)					, new Object[] {"   ", " U ", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.lavaStill, 1)					, new Object[] {" U ", " U ", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.stone, 16)						, new Object[] {"   ", " U ", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.wood, 8, 0)						, new Object[] {" U ", "   ", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.wood, 8, 1)						, new Object[] {"U  ", "   ", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.wood, 8, 2)						, new Object[] {"  U", "   ", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.wood, 8, 3)						, new Object[] {"   ", "U  ", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.glass, 32)						, new Object[] {" U ", "U U", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.grass, 16)						, new Object[] {"   ", "U  ", "U  ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.sandStone, 16)					, new Object[] {"   ", "  U", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.cobblestoneMossy, 16)			, new Object[] {"   ", " U ", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.blockSnow, 16)					, new Object[] {"U U", "   ", "   ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.cactus, 48)						, new Object[] {" U ", "UUU", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.vine, 24)						, new Object[] {"U  ", "U  ", "U  ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.dyePowder, 9, 4)					, new Object[] {" U ", " U ", " UU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.feather, 32)						, new Object[] {" U ", " U ", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.gunpowder, 15)					, new Object[] {"UUU", "U  ", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.clay, 48)							, new Object[] {"UU ", "U  ", "UU ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.dyePowder, 32, 3)					, new Object[] {"UU ", "  U", "UU ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.dyePowder, 48, 0)					, new Object[] {" UU", " UU", " U ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.reed, 48)							, new Object[] {"U U", "U U", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.flint, 32)						, new Object[] {" U ", "UU ", "UU ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Item.bone, 32)							, new Object[] {"U  ", "UU ", "U  ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.stoneBrick, 48, 3)				, new Object[] {"UU ", "UU ", "U  ", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(new ItemStack(Block.mycelium, 24)					, new Object[] {"   ", "U U", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_ModHandler.getIC2Item("resin", 21)				, new Object[] {"U U", "   ", "U U", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        GT_ModHandler.addUUMRecipe(GT_ModHandler.getIC2Item("iridiumOre", 1)			, new Object[] {"UUU", " U ", "UUU", true, 'U', GT_ModHandler.getIC2Item("matter", 1)});
        
        GT_ModHandler.removeRecipe(new ItemStack[] {null, null, null, null, null, null, tMat, tMat, tMat});
        
        if (GregTech_API.sConfiguration.addAdvConfig("gregtechrecipes", "matterfabricator", true) && GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "massfabricator", true) && GT_TileEntity_Matterfabricator.sMatterFabricationRate >= 10000000)
        	GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustPlutonium", 1), new Object[] {"U", "R", true, 'R', "dustUranium", 'U', GT_ModHandler.getIC2Item("matter", 1)});
        else
        	GT_ModHandler.addUUMRecipe(GT_OreDictUnificator.get("dustPlutonium", 1), new Object[] {"UUU", "URU", "UUU", true, 'R', "dustUranium", 'U', GT_ModHandler.getIC2Item("matter", 1)});
        
        GT_Log.out.println("GT_Mod: Adding all the Reverse Recipes for the Furnace/Macerator/Sawmill.");
        GT_Utility.applyUsagesForMaterials(GT_ModHandler.mRuby			== null?GT_OreDictUnificator.get("gemRuby"			, 1):GT_ModHandler.mRuby			, GT_OreDictUnificator.get("dustRuby"			, 1), false, true);
        GT_Utility.applyUsagesForMaterials(GT_ModHandler.mSapphire		== null?GT_OreDictUnificator.get("gemSapphire"		, 1):GT_ModHandler.mSapphire		, GT_OreDictUnificator.get("dustSapphire"		, 1), false, true);
        GT_Utility.applyUsagesForMaterials(GT_ModHandler.mGreenSapphire	== null?GT_OreDictUnificator.get("gemGreenSapphire"	, 1):GT_ModHandler.mGreenSapphire	, GT_OreDictUnificator.get("dustGreenSapphire"	, 1), false, true);
    	GT_Utility.applyUsagesForMaterials(GT_ModHandler.mBrass			== null?GT_OreDictUnificator.get("ingotBrass"		, 1):GT_ModHandler.mBrass			, GT_OreDictUnificator.get("dustBrass"			, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_ModHandler.mSilver		== null?GT_OreDictUnificator.get("ingotSilver"		, 1):GT_ModHandler.mSilver			, GT_OreDictUnificator.get("dustSilver"			, 1), true, true);
        GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("gemOlivine"		, 1), GT_MetaItem_Dust.instance.getStack(37, 1), false, true);
        GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotAluminium"	, 1), GT_MetaItem_Dust.instance.getStack(18, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotTitanium"		, 1), GT_MetaItem_Dust.instance.getStack(19, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotChrome"		, 1), GT_MetaItem_Dust.instance.getStack(20, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotSteel"		, 1), GT_MetaItem_Dust.instance.getStack(26, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(21, 1), GT_MetaItem_Dust.instance.getStack(21, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(22, 1), GT_MetaItem_Dust.instance.getStack(22, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(23, 1), GT_MetaItem_Dust.instance.getStack(23, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(24, 1), GT_MetaItem_Dust.instance.getStack(24, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(27, 1), GT_MetaItem_Dust.instance.getStack(27, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(28, 1), GT_MetaItem_Dust.instance.getStack(28, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(29, 1), GT_MetaItem_Dust.instance.getStack(29, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(31, 1), GT_MetaItem_Dust.instance.getStack(31, 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_MetaItem_Material.instance.getStack(16, 1), GT_ModHandler.getIC2Item("iridiumOre", 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_ModHandler.getIC2Item("iridiumOre", 1), GT_ModHandler.getIC2Item("iridiumOre", 1), false, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotOsmium", 1), GT_OreDictUnificator.get("dustOsmium", 1), false, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotRefinedIron", 1), GT_OreDictUnificator.get("dustIron", 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotBronze", 1), GT_OreDictUnificator.get("dustBronze", 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotCopper", 1), GT_OreDictUnificator.get("dustCopper", 1), true, true);
    	GT_Utility.applyUsagesForMaterials(GT_OreDictUnificator.get("ingotTin", 1), GT_OreDictUnificator.get("dustTin", 1), true, true);
    	GT_Utility.applyUsagesForMaterials(new ItemStack(Item.ingotIron, 1), GT_OreDictUnificator.get("dustIron", 1), true, true);
    	GT_Utility.applyUsagesForMaterials(new ItemStack(Item.ingotGold, 1), GT_OreDictUnificator.get("dustGold", 1), true, true);
    	GT_Utility.applyUsagesForMaterials(new ItemStack(Item.diamond, 1), GT_OreDictUnificator.get("dustDiamond", 1), false, true);
    	GT_Utility.applyUsagesForMaterials(new ItemStack(Block.planks, 1), GT_MetaItem_SmallDust.instance.getStack(15, 4), false, true);
    	
    	if (GT_ModHandler.mTCResource != null) {
    		GT_Utility.applyUsagesForMaterials(new ItemStack(GT_ModHandler.mTCResource.getItem(), 1, 2), new ItemStack(GT_ModHandler.mTCResource.getItem(), 1, 2), true, false);
    	}
    	
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.helmetChain, 1)			, GT_MetaItem_SmallDust.instance.getStack(26, 5), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.plateChain, 1)			, GT_MetaItem_SmallDust.instance.getStack(26, 8), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.legsChain, 1)			, GT_MetaItem_SmallDust.instance.getStack(26, 7), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.bootsChain, 1)			, GT_MetaItem_SmallDust.instance.getStack(26, 4), null, 0, true);
        
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.pocketSundial, 1)		, GT_OreDictUnificator.get("dustGold", 4), GT_OreDictUnificator.get("dustRedstone", 1), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.compass, 1)				, GT_OreDictUnificator.get("dustIron", 4), GT_OreDictUnificator.get("dustRedstone", 1), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.shears, 1)				, GT_OreDictUnificator.get("dustIron", 2), null, 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.minecartEmpty, 1)		, GT_OreDictUnificator.get("dustIron", 5), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.bucketEmpty, 1)			, GT_OreDictUnificator.get("dustIron", 3), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.doorSteel, 1)			, GT_OreDictUnificator.get("dustIron", 6), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.fenceIron, 2)			, GT_MetaItem_SmallDust.instance.getStack(241, 3), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("wrench", 1)		, GT_OreDictUnificator.get("dustBronze", 6), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("ironFence", 2)	, GT_MetaItem_SmallDust.instance.getStack(241, 3), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("fuelCan", 1)		, GT_OreDictUnificator.get("dustTin", 7), null, 0, true);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("tinCan", 1)		, GT_MetaItem_SmallDust.instance.getStack(244, 5), null, 0, true);
        
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Item.pocketSundial, 1)	, GT_OreDictUnificator.get("ingotGold", 4));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Item.compass, 1)			, GT_OreDictUnificator.get("ingotIron", 4));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Item.shears, 1)			, GT_OreDictUnificator.get("ingotIron", 2));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Item.minecartEmpty, 1)	, GT_OreDictUnificator.get("ingotIron", 5));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Item.bucketEmpty, 1)		, GT_OreDictUnificator.get("ingotIron", 3));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Item.doorSteel, 1)		, GT_OreDictUnificator.get("ingotIron", 6));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Block.anvil, 1, 0)		, GT_OreDictUnificator.get("ingotIron", 31));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Block.anvil, 1, 1)		, GT_OreDictUnificator.get("ingotIron", 20));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Block.anvil, 1, 2)		, GT_OreDictUnificator.get("ingotIron", 10));
        GT_ModHandler.addSmeltingRecipe(GT_ModHandler.getIC2Item("wrench", 1)	, GT_OreDictUnificator.get("ingotBronze", 6));
        GT_ModHandler.addSmeltingRecipe(GT_ModHandler.getIC2Item("fuelCan", 1)	, GT_OreDictUnificator.get("ingotTin", 7));
        GT_ModHandler.addSmeltingRecipe(GT_ModHandler.getIC2Item("tinCan", 1)	, GT_OreDictUnificator.get("ingotTin", 1));
        
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.enchantmentTable, 1), GT_OreDictUnificator.get("dustDiamond", 2), GT_OreDictUnificator.get("dustObsidian", 4), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.stoneButton, 1), new ItemStack(Block.sand, 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.sign, 1, 0), GT_OreDictUnificator.get("dustWood", 2), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Item.doorWood, 1, 0), GT_OreDictUnificator.get("dustWood", 6), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.chest, 1, 0), GT_OreDictUnificator.get("dustWood", 8), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.woodenButton, 1, 0), GT_OreDictUnificator.get("dustWood", 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.pressurePlateStone, 1), new ItemStack(Block.sand, 2), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.pressurePlatePlanks, 1), GT_OreDictUnificator.get("dustWood", 2), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.torchRedstoneActive, 1), GT_OreDictUnificator.get("dustSmallWood", 2), new ItemStack(Item.redstone, 1), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.torchRedstoneIdle, 1), GT_OreDictUnificator.get("dustSmallWood", 2), new ItemStack(Item.redstone, 1), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.lever, 1), new ItemStack(Block.sand, 1), GT_OreDictUnificator.get("dustSmallWood", 2), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Block.ladder, 1, 0), GT_OreDictUnificator.get("dustWood", 1), null, 0, false);
        
        tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, GT_OreDictUnificator.get("ingotTin", 1), null, GT_OreDictUnificator.get("ingotTin", 1), null, GT_OreDictUnificator.get("ingotTin", 1), null, null, null});
        if (tStack != null) {
        	int tTinCanNuggetCount = 27/tStack.stackSize;
        	if (tTinCanNuggetCount > 0) {
            	tStack.stackSize = 1;
        		if (tTinCanNuggetCount % 9 == 0)
        			GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get("ingotTin", tTinCanNuggetCount/9));
        		else
        			GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get("nuggetTin", tTinCanNuggetCount));
        	}
        }
        
        if (sTincellCount%4==0)
        	GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getEmptyCell(1), GT_MetaItem_SmallDust.instance.getStack(244, 16/sTincellCount), null, 0, true);
        else
        	GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getEmptyCell(4), GT_MetaItem_SmallDust.instance.getStack(244, 64/sTincellCount), null, 0, true);
        
        GT_Log.out.println("GT_Mod: Adding JackHammer minable Blocks.");
        
		addJackHammerMinableBlock(Block.glowStone);
		
        GT_Log.out.println("GT_Mod: Adding Glowstone and Soulsand to the miners Valuable List.");
    	GT_ModHandler.addValuableOre(Block.glowStone.blockID, 0, 1);
    	GT_ModHandler.addValuableOre(Block.slowSand.blockID, 0, 1);
    	
        GT_Log.out.println("GT_Mod: Activating OreDictionary Handler");
        sOreDict.activateHandler();
        
        GT_Log.out.println("GT_Mod: Iterating through the Seed-List of ForgeHooks, with a brilliant and 100% Invocation-free Method, to add Recipes for gaining Seed Oil from Seeds.");
        
		temp = false;
        try {
        	GT_DummyWorld tWorld = new GT_DummyWorld();
			tLiquid = LiquidDictionary.getLiquid("seedoil", 15);
			
			while (tWorld.mRandom.mIterationStep > 0) {
				ItemStack tSeed = ForgeHooks.getGrassSeed(tWorld);
				if (tSeed != null) {
					if (tLiquid != null && GT_ModHandler.addSqueezerRecipe(tSeed, tLiquid, 15)) {
						temp = true;
					} else {
						tSeed = tSeed.copy();
						tSeed.stackSize = 64;
						addCentrifugeRecipe(tSeed, 1, GT_MetaItem_Cell.instance.getStack(24, 1), null, null, null, 200);
					}
				}
			}
			
			GT_Log.out.println("GT_Mod: Iterating through the Grass-Flower-List of ForgeHooks, with a brilliant and 100% Invocation-free Method, to add Extractor Recipes for gaining more Dye from Flowers and also Compression Recipes for Plantballs.");
			tWorld.mRandom.mIterationStep = Integer.MAX_VALUE;
			while (tWorld.mRandom.mIterationStep > 0) {
				try {
					ForgeHooks.plantGrass(tWorld, 0, 0, 0);
					if (tWorld.mLastSetBlock != null) {
						ItemStack tColor = GT_ModHandler.getRecipeOutput(new ItemStack[] {tWorld.mLastSetBlock, null, null, null, null, null, null, null, null});
						if (GT_OreDictUnificator.isItemStackDye(tColor)) {
							tColor = tColor.copy();
							tColor.stackSize++;
							GT_ModHandler.addExtractionRecipe(tWorld.mLastSetBlock, tColor);
						}
						GT_ModHandler.addCompressionRecipe(tWorld.mLastSetBlock.copy().splitStack(8), GT_ModHandler.getIC2Item("compressedPlantBall", 1));
					}
				} catch(Throwable e) {
					GT_Log.out.println("Minor Bug: Wasn't able to simulate the planting of a Flower with Bonemeal, to add Extractor Recipe for Dye:\n");
					e.printStackTrace(GT_Log.out);
				}
			}
        } catch (Throwable e) {
        	GT_Log.out.println("GT_Mod: failed to iterate somehow, maybe it's your Forge Version causing it. But it's not that important\n");
        	e.printStackTrace(GT_Log.out);
        }
        
		if (temp) {
			GT_Log.out.println("GT_Mod: Forestry was properly loaded, so the Seed Recipes got added to the Squeezer.");
		} else {
			GT_Log.out.println("GT_Mod: Forestry was NOT loaded, so the Recipes got added to the Industrial Centrifuge.");
			addCentrifugeRecipe(new ItemStack(Item.melonSeeds, 64, 0), 1, GT_MetaItem_Cell.instance.getStack(24, 1), null, null, null, 200);
			addCentrifugeRecipe(new ItemStack(Item.pumpkinSeeds, 64, 0), 1, GT_MetaItem_Cell.instance.getStack(24, 1), null, null, null, 200);
			addCentrifugeRecipe(new ItemStack(Item.seeds, 64, 0), 1, GT_MetaItem_Cell.instance.getStack(24, 1), null, null, null, 200);
	    }
		
        GT_ModHandler.addExtractionRecipe(new ItemStack(Block.plantRed, 1, 0), new ItemStack(Item.dyePowder, 3, 1));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Block.plantYellow, 1, 0), new ItemStack(Item.dyePowder, 3, 11));
        
		GT_Log.out.println("GT_Mod: Adding Default Description Set of the Computer Cube");
		GT_ComputercubeDescription.addStandardDescriptions();
		
        GT_Log.out.println("GT_Mod: Register Crops to IC2.");
        try {
	        new GT_BaseCrop(124, "Indigo"			, "Eloraam"					, GT_MetaItem_Material.instance.getStack(8, 1)	, null																																						, GT_MetaItem_Material.instance.getStack(8, 4)	, 2, 4,    0, 1, 4, 1, 1, 0, 4, 0, new String[] {"Flower", "Color", "Ingredient"});
	        new GT_BaseCrop(125, "Flax"				, "Eloraam"					, new ItemStack(Item.silk, 1)					, null																																						, null											, 2, 4,    0, 1, 4, 1, 1, 2, 0, 1, new String[] {"Silk", "Vine", "Addictive"});
	        new GT_BaseCrop(126, "Oilberries"		, "Spacetoad"				, GT_MetaItem_Material.instance.getStack(7, 1)	, null																																						, null											, 9, 4,    0, 1, 4, 6, 1, 2, 1,12, new String[] {"Fire", "Dark", "Reed", "Rotten", "Coal", "Oil"});
	        new GT_BaseCrop(127, "Bobsyeruncleranks", "GenerikB"				, GT_OreDictUnificator.get("dustSmallEmerald",1), new ItemStack[] {new ItemStack(Item.emerald, 1)}																											, null											,11, 4,    0, 1, 4, 4, 0, 8, 2, 9, new String[] {"Shiny", "Vine", "Emerald", "Berylium", "Crystal"});
	        new GT_BaseCrop(128, "Diareed"			, "Direwolf20"				, GT_OreDictUnificator.get("dustSmallDiamond",1), new ItemStack[] {new ItemStack(Item.diamond, 1)}																											, null											,12, 4,    0, 1, 4, 5, 0,10, 2,10, new String[] {"Fire", "Shiny", "Reed", "Coal", "Diamond", "Crystal"});
	        new GT_BaseCrop(129, "Withereed"		, "CovertJaguar"			, GT_OreDictUnificator.get("dustCoal", 1)		, new ItemStack[] {new ItemStack(Item.coal, 1), new ItemStack(Item.coal, 1)}																				, null											, 8, 4,    0, 1, 4, 2, 0, 4, 1, 3, new String[] {"Fire", "Undead", "Reed", "Coal", "Rotten", "Wither"});
	        new GT_BaseCrop(130, "Blazereed"		, "Mr. Brain"				, new ItemStack(Item.blazePowder, 1)			, new ItemStack[] {new ItemStack(Item.blazeRod, 1)}																											, null											, 6, 4,    0, 1, 4, 0, 4, 1, 0, 0, new String[] {"Fire", "Blaze", "Reed", "Sulfur"});
	        new GT_BaseCrop(131, "Eggplant"			, "Link"					, new ItemStack(Item.egg, 1)					, new ItemStack[] {new ItemStack(Item.chickenRaw, 1), new ItemStack(Item.feather , 1), new ItemStack(Item.feather , 1), new ItemStack(Item.feather , 1)}	, null											, 6, 3,  900, 2, 3, 0, 4, 1, 0, 0, new String[] {"Chicken", "Egg", "Edible", "Feather", "Flower", "Addictive"});
	        new GT_BaseCrop(132, "Corium"			, "Gregorius Techneticies"	, new ItemStack(Item.leather, 1)				, null																																						, null											, 6, 4,    0, 1, 4, 0, 2, 3, 1, 0, new String[] {"Cow", "Silk", "Vine"});
	        new GT_BaseCrop(133, "Corpseplant"		, "Mr. Kenny"				, new ItemStack(Item.rottenFlesh, 1)			, new ItemStack[] {new ItemStack(Item.dyePowder, 1, 15), new ItemStack(Item.dyePowder, 1, 15), new ItemStack(Item.bone, 1)}									, null											, 5, 4,    0, 1, 4, 0, 2, 1, 0, 3, new String[] {"Toxic", "Undead", "Vine", "Edible", "Rotten"});
	        new GT_BaseCrop(134, "Creeperweed"		, "General Spaz"			, GT_OreDictUnificator.get("dustGunpowder", 1)	, null																																						, null											, 7, 4,    0, 1, 4, 3, 0, 5, 1, 3, new String[] {"Creeper", "Vine", "Explosive", "Fire", "Sulfur", "Saltpeter", "Coal"});
	        new GT_BaseCrop(135, "Enderbloom"		, "RichardG"				, GT_OreDictUnificator.get("dustEnderPearl", 1)	, new ItemStack[] {new ItemStack(Item.enderPearl, 1), new ItemStack(Item.enderPearl , 1), new ItemStack(Item.eyeOfEnder , 1)}								, null											,10, 4,    0, 1, 4, 5, 0, 2, 1, 6, new String[] {"Ender", "Flower", "Shiny"});
	        new GT_BaseCrop(136, "Meatrose"			, "VintageBeef"				, new ItemStack(Item.dyePowder, 1, 9)			, new ItemStack[] {new ItemStack(Item.beefRaw, 1), new ItemStack(Item.porkRaw , 1), new ItemStack(Item.chickenRaw , 1), new ItemStack(Item.fishRaw , 1)}	, null											, 7, 4, 1500, 1, 4, 0, 4, 1, 3, 0, new String[] {"Edible", "Flower", "Cow", "Fish", "Chicken", "Pig"});
	        new GT_BaseCrop(137, "Milkwart"			, "Mr. Brain"				, new ItemStack(Item.bucketMilk, 1)				, null																																						, null											, 6, 3,  900, 1, 3, 0, 3, 0, 1, 0, new String[] {"Edible", "Milk", "Cow"});
	        new GT_BaseCrop(138, "Slimeplant"		, "Neowulf"					, new ItemStack(Item.slimeBall, 1)				, null																																						, null											, 6, 4,    0, 3, 4, 3, 0, 0, 0, 2, new String[] {"Slime", "Bouncy", "Sticky", "Bush"});
	        new GT_BaseCrop(139, "Spidernip"		, "Mr. Kenny"				, new ItemStack(Item.silk, 1)					, new ItemStack[] {new ItemStack(Item.spiderEye, 1), new ItemStack(Block.web , 1)}																			, null											, 4, 4,  600, 1, 4, 2, 1, 4, 1, 3, new String[] {"Toxic", "Silk", "Spider", "Flower", "Ingredient", "Addictive"});
	        new GT_BaseCrop(140, "Tearstalks"		, "Neowulf"					, new ItemStack(Item.ghastTear, 1)				, null																																						, null											, 8, 4,    0, 1, 4, 1, 2, 0, 0, 0, new String[] {"Healing", "Nether", "Ingredient", "Reed", "Ghast"});
	        new GT_BaseCrop(141, "Tine"				, "Gregorius Techneticies"	, GT_OreDictUnificator.get("nuggetTin", 1)		, null																																						, null											, 5, 3,    0, 2, 3, 2, 0, 3, 0, 0, new String[] {"Shiny", "Metal", "Pine", "Tin", "Bush"});
	        new GT_BaseCrop(142, "Coppon"			, "Mr. Brain"				, GT_OreDictUnificator.get("nuggetCopper", 1)	, null																																						, null											, 6, 3,    0, 2, 3, 2, 0, 1, 1, 1, new String[] {"Shiny", "Metal", "Cotton", "Copper", "Bush"});
	        new GT_BaseCrop(143, "Brown Mushrooms"	, "Mr. Brain"				, new ItemStack(Block.mushroomBrown, 1)			, null																																						, new ItemStack(Block.mushroomBrown, 4)			, 1, 3,    0, 1, 3, 0, 2, 0, 0, 2, new String[] {"Edible", "Mushroom", "Ingredient"});
	        new GT_BaseCrop(144, "Red Mushrooms"	, "Mr. Kenny"				, new ItemStack(Block.mushroomRed, 1)			, null																																						, new ItemStack(Block.mushroomRed  , 4)			, 1, 3,    0, 1, 3, 0, 1, 3, 0, 2, new String[] {"Toxic", "Mushroom", "Ingredient"});
	        new GT_BaseCrop(145, "Argentia"			, "Eloraam"					, GT_OreDictUnificator.get("nuggetSilver", 1)	, null																																						, null											, 7, 4,    0, 3, 4, 2, 0, 1, 0, 0, new String[] {"Shiny", "Metal", "Silver", "Reed"});
	        new GT_BaseCrop(146, "Plumbilia"		, "KingLemming"				, GT_OreDictUnificator.get("nuggetLead", 1)		, null																																						, null											, 6, 4,    0, 3, 4, 2, 0, 3, 1, 1, new String[] {"Heavy", "Metal", "Lead", "Reed"});
        } catch(Throwable e) {
            GT_Log.err.println("GT_Mod: Failed to register Crops to IC2.");
        }
        
		GT_Log.out.println("GT_Mod: Registering Railcraft Crowbar to be usable on GregTech Covers");
		if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[] {null, tStack2 = new ItemStack(Item.dyePowder, 0, 1), tStack3 = new ItemStack(Item.ingotIron, 1), tStack2, tStack3, tStack2, tStack3, tStack2, null}))) {
			tStack.setItemDamage(GregTech_API.ITEM_WILDCARD_DAMAGE);
			GregTech_API.sCrowbarList.add(GT_Utility.stackToInt(tStack));
		}
        
		GT_Log.out.println("GT_Mod: Adding Cover Items. Icon Registry has to call these Client Side again");
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateIron")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateGold")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateDiamond")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateEmerald")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateRedstone")		, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateRefinedIron")		, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateTin")				, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateCopper")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateBronze")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateUranium")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateSilver")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateRuby")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateSapphire")		, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateAluminium")		, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateTitanium")		, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateChrome")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateSteel")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateBrass")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateLead")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateElectrum")		, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateZinc")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateOlivine")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateGreenSapphire")	, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("platePlatinum")		, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateTungsten")		, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateNickel")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateTungstenSteel")	, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateAlloyIridium")	, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateInvar")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateOsmium")			, null);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateIridium")			, null);
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateAlloyAdvanced")	, null);
		GregTech_API.registerCover(GT_MetaItem_Material.instance.getStack(15, 1)		, null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorPlating", 1)		, null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorPlatingHeat", 1)	, null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorPlatingExplosive",1), null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVent", 1)			, null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentCore", 1)		, null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentGold", 1)		, null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentSpread",1)		, null);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentDiamond",1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 0, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 1, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 4, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 5, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 6, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 7, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 8, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 9, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(10, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(11, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(15, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(30, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(31, 1)		, null);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(64, 1)		, null);
		
		GregTech_API.sCovers.put(-1, null);
		GregTech_API.sCovers.put(-2, null);
		
		GT_Log.out.println("GT_Mod: Adding Cover Behaviors");
		new GT_Cover_Vent(new ItemStack[] {GT_ModHandler.getIC2Item("reactorVent", 1), GT_ModHandler.getIC2Item("reactorVentCore", 1), GT_ModHandler.getIC2Item("reactorVentSpread", 1)}, 1);
		new GT_Cover_Vent(new ItemStack[] {GT_ModHandler.getIC2Item("reactorVentDiamond", 1), GT_ModHandler.getIC2Item("reactorVentGold", 1)}, 2);
		new GT_Cover_DoesWork(GT_MetaItem_Component.instance.getStack(30, 1));
		new GT_Cover_ControlsWork(GT_MetaItem_Component.instance.getStack(31, 1));
		new GT_Cover_EUMeter(GT_MetaItem_Component.instance.getStack(15, 1));
		new GT_Cover_EnergyOnly(GT_MetaItem_Component.instance.getStack(0, 1));
		new GT_Cover_RedstoneOnly(GT_MetaItem_Component.instance.getStack(1, 1));
		new GT_Cover_Screen(GT_MetaItem_Component.instance.getStack(4, 1));
		new GT_Cover_Conveyor(GT_MetaItem_Component.instance.getStack(5, 1));
		new GT_Cover_Valve(GT_MetaItem_Component.instance.getStack(6, 1));
		new GT_Cover_SolarPanel(GT_MetaItem_Component.instance.getStack(7, 1), 1, 0);
		new GT_Cover_ItemValve(GT_MetaItem_Component.instance.getStack(8, 1));
		new GT_Cover_Drain(GT_MetaItem_Component.instance.getStack(9, 1));
		new GT_Cover_LiquidMeter(GT_MetaItem_Component.instance.getStack(10, 1));
		new GT_Cover_ItemMeter(GT_MetaItem_Component.instance.getStack(11, 1));
		new GT_Cover_Crafting(GT_MetaItem_Component.instance.getStack(64, 1));
		
    	mPostLoaded = true;
        GT_Log.out.println("GT_Mod: PostLoad-Phase finished!");
    }
    
    @ServerStarting
    public void start(FMLServerStartingEvent aEvent) {
    	if (mDoNotInit) return;
    	gregtechproxy.serverStart();
    	mUniverse = null;
    	GT_TileEntity_IDSU.sEnergyList = null;
    	GT_MetaTileEntity_MagicEnergyAbsorber.sUsedDragonCrystalList = null;
    }
    
    @ServerStopping
    public void stop(FMLServerStoppingEvent aEvent) {
    	if (mDoNotInit) return;
    	writeIDSUData();
    	mUniverse = null;
    	GT_TileEntity_IDSU.sEnergyList = null;
		try {
		if (GregTech_API.DEBUG_MODE || GT_Log.out != System.out) {
			if (GregTech_API.DEBUG_MODE) System.out.println("BEGIN GregTech-Item-Print");
			GT_Log.out.println("*");
			GT_Log.out.println("Printing List of all registered Objects inside the OreDictionary:");
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			
		    String[] tOres = OreDictionary.getOreNames();
		    for (int i = 0; i < tOres.length; i++) {
		    	GT_Log.out.println(tOres[i]);
		    }
		    
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			GT_Log.out.println("Outputting all the Names inside the Itemslist, this List can become very long");
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			
			for (int i = 0; i < Item.itemsList.length; i++) {
		    	if (Item.itemsList[i] != null) {
		    		GT_Log.out.println(Item.itemsList[i].getUnlocalizedName());
					if (Item.itemsList[i].getHasSubtypes()) {
						String tName = "";
						for (int j = 0; j < 16; j++) {
							try {
							tName = Item.itemsList[i].getUnlocalizedName(new ItemStack(Item.itemsList[i], 1, j));
				    		if (tName != null && !tName.equals(""))
				    			GT_Log.out.println(j + ": " + Item.itemsList[i].getUnlocalizedName());
							} catch (Throwable e) {}
						}
					}
		    	}
		    }
			
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			GT_Log.out.println("Outputting all the Names registered by Railcraft");
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			
			try {
				for (String tName : mods.railcraft.api.core.items.TagList.getTags())
				GT_Log.out.println(tName);
			} catch (Throwable e) {}

			if (GregTech_API.DEBUG_MODE) {
				System.out.println("*"); System.out.println("*"); System.out.println("*");
				System.out.println("Outputting all the Names registered by Thermal Expansion");
				System.out.println("*"); System.out.println("*"); System.out.println("*");
				
				try {
					thermalexpansion.api.item.ItemRegistry.printItemNames();
				} catch (Throwable e) {}
			}
			
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			GT_Log.out.println("Outputting all the Names inside the Biomeslist");
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			
			for (int i = 0; i < BiomeGenBase.biomeList.length; i++) {
		    	if (BiomeGenBase.biomeList[i] != null)
		    		GT_Log.out.println(BiomeGenBase.biomeList[i].biomeID + " = " + BiomeGenBase.biomeList[i].biomeName);
		    }
			
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
			GT_Log.out.println("END GregTech-Debug");
			GT_Log.out.println("*"); GT_Log.out.println("*"); GT_Log.out.println("*");
    	}
		} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
    }
    
	public static void addFusionReactorRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aFusionDurationInTicks, int aFusionEnergyPerTick, int aEnergyNeededForStartingFusion) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aFusionDurationInTicks = GregTech_API.sConfiguration.addAdvConfig("fusionreactor", aOutput1, aFusionDurationInTicks)) <= 0) return;
		new GT_Recipe(aInput1, aInput2, aOutput1, aFusionDurationInTicks, aFusionEnergyPerTick, aEnergyNeededForStartingFusion);
	}
	
	public static void addCentrifugeRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("centrifuge", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aInput2>0?GT_ModHandler.getEmptyCell(aInput2):aInput2<0?GT_ModHandler.getIC2Item("fuelCan", -aInput2):null, aOutput1, aOutput2, aOutput3, aOutput4, aDuration);
	}
	
	public static void addElectrolyzerRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("electrolyzer", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aInput2>0?GT_ModHandler.getEmptyCell(aInput2):aInput2<0?GT_ModHandler.getIC2Item("fuelCan", -aInput2):null, aOutput1, aOutput2, aOutput3, aOutput4, aDuration, aEUt);
	}
	
	public static void addChemicalRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("chemicalreactor", aOutput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aInput2, aOutput1, aDuration);
	}
	
	public static void addBlastRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt, int aLevel) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("blastfurnace", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aInput2, aOutput1, aOutput2, aDuration, aEUt, aLevel);
	}
	
	public static void addCannerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("canning", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aEUt, aInput2, aDuration, aOutput1, aOutput2);
	}
	
	public static void addAlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("alloysmelting", aInput2==null?aInput1:aOutput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aInput2, aEUt, aDuration, aOutput1);
	}
	
	public static void addCNCRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("cutting", aOutput1, aDuration)) <= 0) return;
		//new GT_Recipe(aInput1, aEUt, aOutput1, aDuration);
	}
	
	public static void addAssemblerRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("assembling", aOutput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aEUt, aInput2, aDuration, aOutput1);
	}
	
	public static void addWiremillRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("wiremill", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aEUt, aDuration, aOutput1);
	}
	
	public static void addBenderRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("bender", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aEUt, aDuration, aInput1, aOutput1);
	}
	
	public static void addImplosionRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aInput2 = GregTech_API.sConfiguration.addAdvConfig("implosion", aInput1, aInput2)) <= 0) return;
		new GT_Recipe(aInput1, aInput2, aOutput1, aOutput2);
	}
	
	public static void addGrinderRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4) {
		if (aInput1 == null || aOutput1 == null) return;
		if (!GregTech_API.sConfiguration.addAdvConfig("grinder", aInput1, true)) return;
		new GT_Recipe(aInput1, aInput2>0?GT_ModHandler.getEmptyCell(aInput2):aInput2<0?GT_ModHandler.getIC2Item("waterCell", -aInput2):null, aOutput1, aOutput2, aOutput3, aOutput4);
		if (aInput2 == -1 && aOutput4 != null && aOutput4.isItemEqual(GT_ModHandler.getEmptyCell(1)) && aOutput4.stackSize == 1) {
			new GT_Recipe(aInput1, new ItemStack(Item.bucketWater, 1), aOutput1, aOutput2, aOutput3, new ItemStack(Item.bucketEmpty, 1));
		}
	}
	
	public static void addGrinderRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4) {
		if (aInput1 == null || aOutput1 == null) return;
		if (!GregTech_API.sConfiguration.addAdvConfig("grinder", aInput1, true)) return;
		new GT_Recipe(aInput1, aInput2, aOutput1, aOutput2, aOutput3, aOutput4);
	}
	
	public static void addDistillationRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3, ItemStack aOutput4, int aDuration, int aEUt) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("distillation", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aInput2, aOutput1, aOutput2, aOutput3, aOutput4, aDuration, aEUt);
	}
	
	public static void addVacuumFreezerRecipe(ItemStack aInput1, ItemStack aOutput1, int aDuration) {
		if (aInput1 == null || aOutput1 == null) return;
		if ((aDuration = GregTech_API.sConfiguration.addAdvConfig("vacuumfreezer", aInput1, aDuration)) <= 0) return;
		new GT_Recipe(aInput1, aOutput1, aDuration);
	}
	
	public static void addSawmillRecipe(ItemStack aInput1, int aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3) {
		if (aInput1 == null || aOutput1 == null) return;
		if (!GregTech_API.sConfiguration.addAdvConfig("sawmill", aInput1, true)) return;
		new GT_Recipe(aInput1, aInput2>0?GT_ModHandler.getEmptyCell(aInput2):aInput2<0?GT_ModHandler.getIC2Item("waterCell", -aInput2):null, aOutput1, aOutput2, aOutput3);
		if (aInput2 == -1 && aOutput3 != null && aOutput3.isItemEqual(GT_ModHandler.getEmptyCell(1)) && aOutput3.stackSize == 1) {
			new GT_Recipe(aInput1, new ItemStack(Item.bucketWater, 1), aOutput1, aOutput2, new ItemStack(Item.bucketEmpty, 1));
		}
	}
	
	public static void addSawmillRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, ItemStack aOutput3) {
		if (aInput1 == null || aOutput1 == null) return;
		if (!GregTech_API.sConfiguration.addAdvConfig("sawmill", aInput1, true)) return;
		new GT_Recipe(aInput1, aInput2, aOutput1, aOutput2, aOutput3);
	}
	
	public static void addFuel(ItemStack aInput1, ItemStack aOutput1, int aEU, int aType) {
		if (aInput1 == null || aOutput1 == null) return;
		new GT_Recipe(aInput1, aOutput1, GregTech_API.sConfiguration.addAdvConfig("fuel_"+aType, aInput1, aEU), aType);
	}
	
	public static void addJackHammerMinableBlock(Block aBlock) {
		if (aBlock != null && !GT_Jackhammer_Item.mineableBlocks.contains(aBlock)) GT_Jackhammer_Item.mineableBlocks.add(aBlock);
	}
	
	public static void addSonictronSound(ItemStack aItemStack, String aSoundName) {
		if (aItemStack == null || aSoundName == null || aSoundName.equals("")) return;
		mSoundItems.add(aItemStack);
		mSoundNames.add(aSoundName);
		if (aSoundName.startsWith("note.")) {
			mSoundCounts.add(25);
		} else {
			mSoundCounts.add(1);
		}
	}
	
	public static void addComputercubeDescriptionSet(ItemStack[] aItemStack, String[] aText) {
		new GT_ComputercubeDescription(aText, aItemStack);
	}
	
	public static void causeMachineUpdate(World aWorld, int aX, int aY, int aZ) {
		stepToUpdateMachine(aWorld, aX, aY, aZ, new ArrayList<ChunkPosition>());
	}
	
	public static void stepToUpdateMachine(World aWorld, int aX, int aY, int aZ, ArrayList<ChunkPosition> aList) {
		aList.add(new ChunkPosition(aX, aY, aZ));
		TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
		if (tTileEntity != null && tTileEntity instanceof IMachineBlockUpdateable) {
			((IMachineBlockUpdateable)tTileEntity).onMachineBlockUpdate();
		}
		if (aList.size() < 5 || (tTileEntity != null && tTileEntity instanceof IMachineBlockUpdateable) || GregTech_API.isMachineBlock(aWorld.getBlockId(aX, aY, aZ), aWorld.getBlockMetadata(aX, aY, aZ))) {
			if (!aList.contains(new ChunkPosition(aX + 1, aY, aZ))) stepToUpdateMachine(aWorld, aX + 1, aY, aZ, aList);
			if (!aList.contains(new ChunkPosition(aX - 1, aY, aZ))) stepToUpdateMachine(aWorld, aX - 1, aY, aZ, aList);
			if (!aList.contains(new ChunkPosition(aX, aY + 1, aZ))) stepToUpdateMachine(aWorld, aX, aY + 1, aZ, aList);
			if (!aList.contains(new ChunkPosition(aX, aY - 1, aZ))) stepToUpdateMachine(aWorld, aX, aY - 1, aZ, aList);
			if (!aList.contains(new ChunkPosition(aX, aY, aZ + 1))) stepToUpdateMachine(aWorld, aX, aY, aZ + 1, aList);
			if (!aList.contains(new ChunkPosition(aX, aY, aZ - 1))) stepToUpdateMachine(aWorld, aX, aY, aZ - 1, aList);
		}
	}
	
	public static ItemStack getUnificatedOreDictStack(ItemStack aOreStack) {
		if (!mLoaded) {
			System.err.println(aOreStack.itemID + "." + aOreStack.getItemDamage() + " - OreDict Unification Entries are not registered now, please call it in the postload phase.");
		}
		return GT_OreDictUnificator.get(aOreStack);
	}
	
    public static ItemStack getGregTechBlock(int aIndex, int aAmount, int aMeta) {
    	if (aIndex < GregTech_API.sBlockList.length && aIndex >= 0) 
	    	if (GregTech_API.sBlockList[aIndex] != null) 
	    		return new ItemStack(GregTech_API.sBlockList[aIndex], aAmount, aMeta);
	    	else
	    		System.err.println("GT_API: This Blockindex wasn't initialized, you either called it before the Init-Phase (Only Post-Init or later will work), or this Item doesnt exist now");
    	else
    		System.err.println("GT_API: The Index " + aIndex + " is not part of my Itemrange");
    	return null;
    }
    
    public static ItemStack getGregTechItem(int aIndex, int aAmount, int aDamage) {
    	if (aIndex < GregTech_API.sItemList.length && aIndex >= 0) 
	    	if (GregTech_API.sItemList[aIndex] != null) {
	    		if (GregTech_API.sItemList[aIndex] instanceof GT_MetaItem_Abstract) {
	    			Field tField = GT_Utility.getPublicField(GregTech_API.sItemList[aIndex], "instance");
	    			if (tField != null) {
	    				try {
	    					ItemStack rStack = null;
	    					rStack = ((GT_MetaItem_Abstract)tField.get(GregTech_API.sItemList[aIndex])).getStack(aDamage, aAmount);
	    					if (rStack != null) {
	    						return rStack;
	    					}
	    				} catch(Throwable e) {if (GregTech_API.DEBUG_MODE) e.printStackTrace(GT_Log.err);}
	    			}
	    		}
	    		return new ItemStack(GregTech_API.sItemList[aIndex], aAmount, aDamage);
	    	}
	    	else
	    		System.err.println("GT_API: This Itemindex wasn't initialized, you either called it before the Init-Phase (Only Post-Init or later will work), or this Item doesnt exist now");
    	else
    		System.err.println("GT_API: The Index " + aIndex + " is not part of my Itemrange");
    	return null;
    }
    
    public static File getSaveDirectory() {
    	if (mUniverse == null) return null;
        SaveHandler tSaveHandler = (SaveHandler)mUniverse.getSaveHandler();
        File rFile = null;
        Field[] tFields = SaveHandler.class.getDeclaredFields();
        for (int i = 0; i < tFields.length; ++i) {
            if (tFields[i].getType() == File.class)  {
            	tFields[i].setAccessible(true);
                try {
                    File tFile = (File)tFields[i].get(tSaveHandler);
                    if (rFile == null || rFile.getParentFile() == tFile) {
                    	rFile = tFile;
                    }
                } catch (Exception e) {
                	
                }
            }
        }
        return rFile;
    }
    
    public static void writeIDSUData() {
    	if (mUniverse != null && GT_TileEntity_IDSU.sEnergyList != null && !mUniverse.isRemote) {
			try {
		        File tDirectory = getSaveDirectory();
		        if (tDirectory != null) {
			        NBTTagCompound tNBT = new NBTTagCompound();
		            NBTTagList tList = new NBTTagList();
		            Iterator<Entry<Integer, Integer>> tIterator = GT_TileEntity_IDSU.sEnergyList.entrySet().iterator();
		            while (tIterator.hasNext()) {
		                Entry<Integer, Integer> tEntry = tIterator.next();
			            NBTTagCompound tTag = new NBTTagCompound();
			            tTag.setInteger("Hash", tEntry.getKey());
				        tTag.setInteger("EU", tEntry.getValue());
					    tList.appendTag(tTag);
		            }
		            
				    tNBT.setTag("Energy", tList);
				    try {
				        CompressedStreamTools.writeCompressed(tNBT, new FileOutputStream(new File(tDirectory, "GT_IDSU_Energyvalues.dat")));
				    } catch(StackOverflowError e) {
				        GT_Log.err.println("Ignored Stackoverflow at writing IDSU Data!");
				    }
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    public static void readIDSUData() {
    	if (mUniverse != null && GT_TileEntity_IDSU.sEnergyList == null && !mUniverse.isRemote) {
    		GT_TileEntity_IDSU.sEnergyList = new HashMap<Integer, Integer>();
			try {
	            File tDirectory = getSaveDirectory();
	            if (tDirectory != null) {
			        try {
			        	NBTTagCompound tNBT = CompressedStreamTools.readCompressed(new FileInputStream(new File(tDirectory, "GT_IDSU_Energyvalues.dat")));
			            NBTTagList tList = tNBT.getTagList("Energy");
				        for (int i = 0; i < tList.tagCount(); i++) {
			                NBTTagCompound tTag = (NBTTagCompound)tList.tagAt(i);
			                GT_TileEntity_IDSU.sEnergyList.put(tTag.getInteger("Hash"), tTag.getInteger("EU"));
			            }
		            } catch(StackOverflowError e) {
				        GT_Log.err.println("Ignored Stackoverflow at reading IDSU Data!");
			        }
	        	}
			} catch (Exception e) {
               	if (!(e instanceof java.io.FileNotFoundException))
               		e.printStackTrace();
			}
	    }
    }
}
