package gregtechmod.api.metatileentity;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_Utility;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * Extend this Class to add a new MetaMachine
 * Call the Constructor with the desired ID at the load-phase (not preload and also not postload!)
 * Implement the newMetaEntity-Method to return a new ready instance of your MetaTileEntity
 */
public abstract class MetaTileEntity implements ILiquidTank {
	public static volatile int VERSION = 303;
	
	public String mName;
	public int mID;
	
	/**
	 * accessibility to this Field is no longer given, see below
	 */
	private BaseMetaTileEntity mBaseMetaTileEntity;
	
	/**
	 * new getter for the BaseMetaTileEntity, which restricts usage to certain Functions.
	 */
	public IGregTechTileEntity getBaseMetaTileEntity() {
		return mBaseMetaTileEntity;
	}
	
	/**
	 * The Inventory of the MetaTileEntity. Amount of Slots can be larger than 256. HAYO!
	 */
	public ItemStack[] mInventory = new ItemStack[getInvSize()];
	
	/**
	 * This registers your Machine at the List.
	 * Use only ID's larger than 512, because i reserved these ones.
	 * @param aID the ID
	 * @example for Constructor overload.
	 * 
	 * 	public GT_MetaTileEntity_EBench(int aID, String mName, String mNameRegional) {
	 * 		super(aID, mName, mNameRegional);
	 * 	}
	 */
	public MetaTileEntity(int aID, String aBasicName, String aRegionalName) {
		if (GregTech_API.mMetaTileList[aID] == null) {
			GregTech_API.mMetaTileList[aID] = this;
		} else {
			throw new IllegalArgumentException("MetaMachine-Slot Nr. " + aID + " is already occupied!");
		}
		mID = aID;
		mName = aBasicName.replaceAll(" ", "_");
		setBaseMetaTileEntity(GregTech_API.constructBaseMetaTileEntity());
		GT_LanguageManager.addStringLocalization("tile.BlockMetaID_Machine." + mName + ".name", aRegionalName);
	}
	
	/**
	 * Sets the BaseMetaTileEntity of this
	 */
	protected void setBaseMetaTileEntity(BaseMetaTileEntity aBaseMetaTileEntity) {
		if (mBaseMetaTileEntity != null && aBaseMetaTileEntity == null) {
			mBaseMetaTileEntity.mMetaTileEntity.inValidate();
			mBaseMetaTileEntity.mMetaTileEntity = null;
		}
		mBaseMetaTileEntity = aBaseMetaTileEntity;
		if (mBaseMetaTileEntity != null) {
			mBaseMetaTileEntity.mMetaTileEntity = this;
		}
	}
	
	/**
	 * This is the normal Constructor.
	 */
	public MetaTileEntity() {}
	
	/**
	 * @param aTileEntity is just because the internal Variable "mBaseMetaTileEntity" is set after this Call.
	 * @return a newly created and ready MetaTileEntity
	 */
	public abstract MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);
	
	/**
	 * ^= writeToNBT
	 */
	public abstract void saveNBTData(NBTTagCompound aNBT);
	
	/**
	 * ^= readFromNBT
	 */
	public abstract void loadNBTData(NBTTagCompound aNBT);
	
	/**
	 * Adds the NBT-Information to the ItemStack, when being wrenched
	 * Used to store Machine specific Upgrade Data.
	 */
	public void setItemNBT(NBTTagCompound aNBT) {}
	
	/**
	 * From new ISidedInventory
	 */
	public abstract boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack);
	
	/**
	 * From new ISidedInventory
	 */
	public abstract boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack);
	
	/**
	 * Amount of all InventorySlots
	 */
	public abstract int getInvSize();
	
	/**
	 * Index of the Texture, if the Icon call fails. You have to implement this even if you don't need it.
	 * 
	 * @param aSide is the Side of the Block
	 * @param aFacing is the direction the Block is facing
	 * @param aActive if the Machine is currently active (use this instead of calling mBaseMetaTileEntity.mActive!!!)
	 * @param aActive if the Machine is currently outputting a RedstoneSignal (use this instead of calling mBaseMetaTileEntity.mRedstone!!!)
	 */
	public abstract int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone);
	
	/**
	 * Icon of the Texture. If this returns null then it falls back to getTextureIndex
	 * 
	 * @param aSide is the Side of the Block
	 * @param aFacing is the direction the Block is facing
	 * @param aActive if the Machine is currently active (use this instead of calling mBaseMetaTileEntity.mActive!!!)
	 * @param aActive if the Machine is currently outputting a RedstoneSignal (use this instead of calling mBaseMetaTileEntity.mRedstone!!!)
	 */
	public Icon getTextureIcon(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		return null;
	}
	
	/**
	 * get a small Description
	 */
	protected abstract String getDescription();
	
	/**
	 * Called right before this Machine explodes
	 */
	public void onExplosion() {}
	
	/**
	 * The First processed Tick which was passed to this MetaTileEntity
	 */
	public void onFirstTick() {}
	
	/**
	 * The Tick before all the generic handling happens, what gives a slightly faster reaction speed.
	 * Don't use this if you really don't need to. @onPostTick is better suited for ticks.
	 * This happens still after the Cover handling.
	 */
	public void onPreTick() {}
	
	/**
	 * The Tick after all the generic handling happened.
	 * Recommended to use this like updateEntity.
	 */
	public void onPostTick() {}
	
	/**
	 * Called when this MetaTileEntity gets (intentionally) disconnected from the BaseMetaTileEntity.
	 * Doesn't get called when this thing is moved by Frames or similar hacks.
	 */
	public void inValidate() {}
	
	/**
	 * Called when the BaseMetaTileEntity gets invalidated, what happens right before the @inValidate above gets called
	 */
	public void onRemoval() {}
	
	/**
	 * When a GUI is opened
	 */
	public void onOpenGUI() {}
	
	/**
	 * When a GUI is closed
	 */
	public void onCloseGUI() {}
	
	/**
	 * a Player rightclicks the Machine
	 * Sneaky rightclicks are not getting passed to this!
	 */
	public void onRightclick(EntityPlayer aPlayer) {}

	/**
	 * a Player rightclicks the Machine
	 * Sneaky rightclicks are not getting passed to this!
	 */
	public void onRightclick(EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
		onRightclick(aPlayer);
	}
	
	/**
	 * a Player leftclicks the Machine
	 * Sneaky leftclicks are getting passed to this unlike with the rightclicks.
	 */
	public void onLeftclick(EntityPlayer aPlayer) {}
	
	/**
	 * Called Clientside with the Data got from @getUpdateData
	 */
	public void onValueUpdate(short aValue) {}
	
	/**
	 * return a small bit of Data, like a secondary Facing for example with this Function, for the Client
	 * It is getting updated with @issueTextureUpdate
	 */
	public short getUpdateData() {return 0;}
	
	/**
	 * Called to actually play the Sound.
	 * Do not insert Client/Server checks. That is already done for you.
	 * Do not use @playSoundEffect, Minecraft doesn't like that at all. Use @playSound instead.
	 */
    public void doSound(int aIndex, double aX, double aY, double aZ) {}
    public void startSoundLoop(int aIndex, double aX, double aY, double aZ) {}
    public void stopSoundLoop(int aIndex, double aX, double aY, double aZ) {}
	
    /**
     * Sends the Event for the Sound Triggers, only usable Server Side!
     */
    public final  void sendSound(int aIndex) {
    	getBaseMetaTileEntity().getWorld().addBlockEvent(getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), getBaseMetaTileEntity().getBlockIDOffset(0, 0, 0), 4, aIndex);
    }
    
    /**
     * Sends the Event for the Sound Triggers, only usable Server Side!
     */
    public final void sendLoopStart(int aIndex) {
    	getBaseMetaTileEntity().getWorld().addBlockEvent(getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), getBaseMetaTileEntity().getBlockIDOffset(0, 0, 0), 5, aIndex);
    }
    
    /**
     * Sends the Event for the Sound Triggers, only usable Server Side!
     */
    public final void sendLoopEnd(int aIndex) {
    	getBaseMetaTileEntity().getWorld().addBlockEvent(getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), getBaseMetaTileEntity().getBlockIDOffset(0, 0, 0), 6, aIndex);
    }
    
    /**
     * @return true if this Device emits Energy at all
     */
    public boolean isEnetOutput() {return false;}
    
    /**
     * @return true if this Device consumes Energy at all
     */
    public boolean isEnetInput()  {return false;}
    
    /**
     * @return the amount of EU, which can be stored in this Device. Default is 0 EU.
     */
    public int maxEUStore()  {return 0;}

    /**
     * @return the amount of EU/t, which can be accepted by this Device before it explodes.
     */
    public int maxEUInput()  {return 0;}
    
    /**
     * @return the amount of EU/t, which can be outputted by this Device.
     */
    public int maxEUOutput() {return 0;}
    
    /**
     * @return the amount of E-net Impulses of the maxEUOutput size, which can be outputted by this Device.
     * Default is 1 Pulse, this shouldn't be set to smaller Values than 1, as it won't output anything in that Case!
     */
    public int maxEUPulses()  {return 1;}
    
    /**
     * @return true if that Side is an Output.
     */
    public boolean isOutputFacing(byte aSide) {return false;}
    
    /**
     * @return true if that Side is an Input.
     */
    public boolean isInputFacing(byte aSide) {return false;}
    
    /**
     * @param aFacing
     * @return if aFacing would be a valid Facing for this Device. Used for wrenching.
     */
    public boolean isFacingValid(byte aFacing) {return false;}
    
    /**
     * @return true if the Machine can be accessed
     */
    public boolean isAccessAllowed(EntityPlayer aPlayer) {return false;}
    
    /**
     * @return if aIndex is a valid Slot. false for things like HoloSlots. Is used for determining if an Item is dropped upon Block destruction and for Inventory Access Management
     */
    public boolean isValidSlot(int aIndex) {return true;}
    
    /**
     * @return if aIndex can be set to Zero stackSize, when being removed.
     */
    public boolean setStackToZeroInsteadOfNull(int aIndex) {
    	return false;
    }
    
    /**
     * This is used to set the internal Energy to the given Parameter. I use this for the IDSU.
     */
	public void setEUVar(int aEU) {
		mBaseMetaTileEntity.mStoredEnergy = aEU;
	}
	
    /**
     * This is used to get the internal Energy. I use this for the IDSU.
     */
	public int getEUVar() {
		return mBaseMetaTileEntity.mStoredEnergy;
	}

    /**
     * This is used to set the internal MJ Energy to the given Parameter.
     */
	public void setMJVar(int aMJ) {
		mBaseMetaTileEntity.mStoredMJ = aMJ;
	}
	
    /**
     * This is used to get the internal MJ Energy.
     */
	public int getMJVar() {
		return mBaseMetaTileEntity.mStoredMJ;
	}

    /**
     * This is used to set the internal Steam Energy to the given Parameter.
     */
	public void setSteamVar(int aSteam) {
		mBaseMetaTileEntity.mStoredSteam = aSteam;
	}
	
    /**
     * This is used to get the internal Steam Energy.
     */
	public int getSteamVar() {
		return mBaseMetaTileEntity.mStoredSteam;
	}
	
    /**
     * @return the amount of Steam, which can be stored in this Device. Default is 0 EU.
     */
    public int maxSteamStore()  {return 0;}
    
    /**
     * @return the amount of MJ, which can be stored in this Device. Default is 0 EU.
     */
    public int maxMJStore()  {return 0;}
    
	/**
	 * @return the amount of EU, which this Device stores before starting to emit Energy.
	 * useful if you don't want to emit stored Energy until a certain Level is reached.
	 */
	public int getMinimumStoredEU() {
		return 0;
	}
	
    /**
     * Determines the Tier of the Machine, used for de-charging Tools.
     */
    public int getInputTier() {
    	return GT_Utility.getTier(getBaseMetaTileEntity().getInputVoltage());
    }
    
    /**
     * Determines the Tier of the Machine, used for charging Tools.
     */
    public int getOutputTier() {
    	return GT_Utility.getTier(getBaseMetaTileEntity().getOutputVoltage());
    }
    
    /**
     * gets the first RechargerSlot
     */
    public int rechargerSlotStartIndex() {
    	return 0;
    }
    
    /**
     * gets the amount of RechargerSlots
     */
    public int rechargerSlotCount() {
    	return 0;
    }
    
    /**
     * gets the first DechargerSlot
     */
    public int dechargerSlotStartIndex() {
    	return 0;
    }

    /**
     * gets the amount of DechargerSlots
     */
    public int dechargerSlotCount() {
    	return 0;
    }
    
    /**
     * gets if Protected from other Players or not
     */
    public boolean ownerControl() {
    	return false;
    }

    /**
     * gets if Protected from other Players Wrenches or not.
     */
    public boolean ownerWrench() {
    	return ownerControl();
    }
    
    /**
     * gets if it has hardness -1 and so is only breakable via Wrench (just to prevent accidently breaking the Blocks)
     */
    public boolean unbreakable() {
    	return false;
    }
    
    /**
     * returns the DebugLog
     */
	public ArrayList<String> getSpecialDebugInfo(EntityPlayer aPlayer, int aLogLevel, ArrayList<String> aList) {
		return aList;
	}
	
    /**
     * If this Side can connect to inputting pipes
     */
    public boolean isLiquidInput(byte aSide) {
    	return true;
    }
    
    /**
     * If this Side can connect to outputting pipes
     */
    public boolean isLiquidOutput(byte aSide) {
    	return true;
    }
    
	/**
	 * gets the contained Liquid
	 */
	public LiquidStack getLiquid() {return null;}
	
	/**
	 * tries to fill this Tank
	 */
	public int fill(LiquidStack resource, boolean doFill) {return 0;}
	
	/**
	 * tries to empty this Tank
	 */
	public LiquidStack drain(int maxDrain, boolean doDrain) {return null;}
	
	/**
	 * Tank pressure
	 */
	public int getTankPressure() {return 0;}
	
	/**
	 * Liquid Capacity
	 */
	public int getCapacity() {return 0;}
	
	/**
	 * When a Machine Update occurs
	 */
	public void onMachineBlockUpdate() {}
	
	/**
	 * For the rare case you need this Function
	 */
	public void receiveClientEvent(int aEventID, int aValue) {}
	
	/**
	 * If this is just a simple Machine, which can be wrenched at 100%
	 */
	public boolean isSimpleMachine() {return false;}
	
	/**
	 * If this accepts up to 4 Overclockers
	 */
	public boolean isOverclockerUpgradable() {return false;}
	
	/**
	 * If this accepts Transformer Upgrades
	 */
	public boolean isTransformerUpgradable() {return false;}
	
	/**
	 * If this accepts Battery Upgrades
	 */
	public boolean isBatteryUpgradable() {return false;}
	
	/**
	 * Progress this machine has already made
	 */
	public int getProgresstime() {return 0;}
	
	/**
	 * Progress this Machine has to do to produce something
	 */
	public int maxProgresstime() {return 0;}

	/**
	 * Increases the Progress, returns the overflown Progress.
	 */
	public int increaseProgress(int aProgress) {return 0;}
	
	/**
	 * If this TileEntity makes use of Sided Redstone behaviors.
	 * Determines only, if the Output Redstone Array is getting filled with 0 for true, or 15 for false.
	 */
	public boolean hasSidedRedstoneOutputBehavior() {
		return false;
	}
	
	/**
	 * When the Facing gets changed
	 */
	public void onFacingChange() {}
	
	public String getMainInfo() {return "";}
	public String getSecondaryInfo() {return "";}
	public String getTertiaryInfo() {return "";}
	public boolean isGivingInformation() {return false;}
	
	public boolean isDigitalChest() {return false;}
	public ItemStack[] getStoredItemData() {return null;}
	public void setItemCount(int aCount) {}
	public int getMaxItemCount() {return 0;}
	
	public int getSizeInventory() {return getInvSize();}
	public ItemStack getStackInSlot(int aIndex) {if (aIndex >= 0 && aIndex < mInventory.length) return mInventory[aIndex]; return null;}
	public void setInventorySlotContents(int aIndex, ItemStack aStack) {if (aIndex >= 0 && aIndex < mInventory.length) mInventory[aIndex] = aStack;}
	public String getInvName() {if (GregTech_API.mMetaTileList[mID] != null) return GregTech_API.mMetaTileList[mID].mName; return "";}
	public int getInventoryStackLimit() {return 64;}
}
