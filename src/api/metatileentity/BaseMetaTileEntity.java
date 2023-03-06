package gregtechmod.api.metatileentity;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.util.GT_CoverBehavior;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_Utility;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * This is the main TileEntity for EVERYTHING.
 */
public class BaseMetaTileEntity extends TileEntity implements IGregTechTileEntity {
	public static volatile int VERSION = 303;
	
	protected MetaTileEntity mMetaTileEntity;
	protected int mStoredMJ = 0, mStoredEnergy = 0, mStoredSteam = 0;
	protected boolean mIsAddedToEnet = false, mReleaseEnergy = false;
	protected double mRestJoules = 0.0;
	
	private byte[] mSidedRedstone = new byte[] {15,15,15,15,15,15};
	private int[] mCoverSides = new int[] {0,0,0,0,0,0}, mCoverData = new int[] {0,0,0,0,0,0};
	private boolean mActive = false, mRedstone = false, mWorkUpdate = false, mSteamConverter = false, mMJConverter = false, mInventoryChanged = false, mWorks = true, oActive = false, mNeedsSidedRedstoneUpdate = true, mNeedsCoverUpdate = true, mNeedsUpdate = true, mNeedsBlockUpdate = true, mSendClientData = true, oRedstone = false;
	private byte oLightValue = 0, mLightValue = 0, mRSEnergyCells = 0, mSteamTanks = 0, mOverclockers = 0, mTransformers = 0, mOtherUpgrades = 0, mFacing = -1, oFacing = -1, mWorkData = 0;
	private int mDisplayErrorCode = 0, oOutput = 0, oX = 0, oY = 0, oZ = 0, mID = 0, mUpgradedStorage = 0;
	private long mTickTimer = 0;
	private String mOwnerName = "";
	
	public BaseMetaTileEntity() {}
	
	@Override
    public void writeToNBT(NBTTagCompound aNBT) {
    	super.writeToNBT(aNBT);
        aNBT.setInteger		("mID"				, mID);
        aNBT.setInteger		("mStoredMJ"		, mStoredMJ);
        aNBT.setInteger		("mStoredSteam"		, mStoredSteam);
        aNBT.setInteger		("mStoredEnergy"	, mStoredEnergy);
        aNBT.setInteger		("mUpgradedStorage"	, mUpgradedStorage);
        aNBT.setIntArray	("mCoverData"		, mCoverData);
        aNBT.setIntArray	("mCoverSides"		, mCoverSides);
    	aNBT.setByteArray	("mRedstoneSided"	, mSidedRedstone);
        aNBT.setByte		("mLightValue"		, mLightValue);
        aNBT.setByte		("mOverclockers"	, mOverclockers);
        aNBT.setByte		("mTransformers"	, mTransformers);
        aNBT.setByte		("mRSEnergyCells"	, mRSEnergyCells);
        aNBT.setByte		("mSteamTanks"		, mSteamTanks);
        aNBT.setByte		("mOtherUpgrades"	, mOtherUpgrades);
        aNBT.setByte		("mWorkData"		, mWorkData);
        aNBT.setShort		("mFacing"			, getFrontFacing());
        aNBT.setString		("mOwnerName"		, mOwnerName);
    	aNBT.setBoolean		("mMJConverter"		, mMJConverter);
    	aNBT.setBoolean		("mSteamConverter"	, mSteamConverter);
    	aNBT.setBoolean		("mActive"			, mActive);
    	aNBT.setBoolean		("mRedstone"		, mRedstone);
    	aNBT.setBoolean		("mWorks"			, !mWorks);
    	aNBT.setDouble		("mRestJoules"		, mRestJoules);
    	
    	if (hasValidMetaTileEntity()) {
            NBTTagList tItemList = new NBTTagList();
            for (int i = 0; i < mMetaTileEntity.mInventory.length; i++) {
                ItemStack tStack = mMetaTileEntity.mInventory[i];
                if (tStack != null) {
                    NBTTagCompound tTag = new NBTTagCompound();
                    tTag.setInteger("IntSlot", i);
                    tStack.writeToNBT(tTag);
                    tItemList.appendTag(tTag);
                }
            }
            aNBT.setTag("Inventory", tItemList);
            
    		try {
    			mMetaTileEntity.saveNBTData(aNBT);
    		} catch(Throwable e) {
    			System.err.println("Encountered CRITICAL ERROR while saving MetaTileEntity, the Chunk whould've been corrupted by now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
    			e.printStackTrace(GT_Log.err);
    		}
    	}
    }
	
	@Override
	public void readFromNBT(NBTTagCompound aNBT) {
		super.readFromNBT(aNBT);
		setInitialValuesAsNBT(aNBT, 0);
    }
	
	@Override
	public void setInitialValuesAsNBT(NBTTagCompound aNBT, int aID) {
		if (aNBT == null) {
			if (aID>0) mID=aID; else mID=mID>0?mID:0;
			if (mID!=0) createNewMetatileEntity(mID);
			mSidedRedstone = new byte[0];
		} else {
	        if (aID<=0) 	mID	= aNBT.getInteger	("mID"); else mID = aID;
	        mStoredMJ			= aNBT.getInteger	("mStoredMJ");
	        mStoredSteam		= aNBT.getInteger	("mStoredSteam");
	        mStoredEnergy		= aNBT.getInteger	("mStoredEnergy");
	        mUpgradedStorage	= aNBT.getInteger	("mUpgradedStorage")+aNBT.getByte("mBatteries")*10000 + aNBT.getByte("mLiBatteries")*100000;
	    	mCoverSides 		= aNBT.getIntArray	("mCoverSides");
	        mCoverData 			= aNBT.getIntArray	("mCoverData");
	        mSidedRedstone		= aNBT.getByteArray ("mRedstoneSided");
	        mLightValue			= aNBT.getByte		("mLightValue");
	        mSteamTanks			= aNBT.getByte		("mSteamTanks");
	        mRSEnergyCells		= aNBT.getByte		("mRSEnergyCells");
	        mOverclockers		= aNBT.getByte		("mOverclockers");
	        mTransformers		= aNBT.getByte		("mTransformers");
	        mWorkData			= aNBT.getByte		("mWorkData");
	        mFacing  	  = (byte)aNBT.getShort		("mFacing");
	        mOwnerName			= aNBT.getString	("mOwnerName");
	        mMJConverter		= aNBT.getBoolean	("mMJConverter");
	        mSteamConverter		= aNBT.getBoolean	("mSteamConverter");
	    	mActive				= aNBT.getBoolean	("mActive");
	    	mRedstone			= aNBT.getBoolean	("mRedstone");
	    	mWorks				=!aNBT.getBoolean	("mWorks");
	    	mOtherUpgrades		= (byte)(aNBT.getByte("mOtherUpgrades")+aNBT.getByte("mBatteries")+aNBT.getByte("mLiBatteries"));
	    	mRestJoules			= aNBT.getDouble	("mRestJoules");
	    	
	    	if (mID!=0 && createNewMetatileEntity(mID)) {
	            NBTTagList tItemList = aNBT.getTagList("Inventory");
	            for (int i = 0; i < tItemList.tagCount(); i++) {
	                NBTTagCompound tTag = (NBTTagCompound)tItemList.tagAt(i);
	                int tSlot = tTag.getInteger("IntSlot");
	                if (tSlot >= 0 && tSlot < mMetaTileEntity.mInventory.length) {
	                	mMetaTileEntity.mInventory[tSlot] = ItemStack.loadItemStackFromNBT(tTag);
	                }
	            }
	            
	    		try {
	    			mMetaTileEntity.loadNBTData(aNBT);
	        	} catch(Throwable e) {
	        		System.err.println("Encountered Exception while loading MetaTileEntity, the Server should've crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
	        		e.printStackTrace(GT_Log.err);
	        	}
			}
		}
		
    	if (mCoverData.length != 6)    	mCoverData	   = new int[] { 0, 0, 0, 0, 0, 0};
    	if (mCoverSides.length != 6)    mCoverSides    = new int[] { 0, 0, 0, 0, 0, 0};
        if (mSidedRedstone.length != 6) if (hasValidMetaTileEntity() && mMetaTileEntity.hasSidedRedstoneOutputBehavior()) mSidedRedstone = new byte[] {0,0,0,0,0,0}; else mSidedRedstone = new byte[] {15,15,15,15,15,15};
    	
    	issueClientUpdate();
	}
	
	private boolean createNewMetatileEntity(int aID) {
		if (aID < 16 || aID >= GregTech_API.MAXIMUM_METATILE_IDS || GregTech_API.mMetaTileList[aID] == null) {
			GT_Log.err.println("MetaID " + aID + " not loadable => locking TileEntity!");
		} else {
			if (aID != 0) {
				if (hasValidMetaTileEntity()) mMetaTileEntity.setBaseMetaTileEntity(null);
				GregTech_API.mMetaTileList[aID].newMetaEntity(this).setBaseMetaTileEntity(this);
				issueClientUpdate();
	    		mTickTimer = 0;
				mID = aID;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Used for ticking special BaseMetaTileEntities, which need that for Energy Conversion
	 * It's called right before onPostTick()
	 */
	public void updateStatus() {
		
	}
	
	/**
	 * Called when trying to charge Items
	 */
	public void chargeItem(ItemStack aStack) {
		decreaseStoredEU(GT_ModHandler.chargeElectricItem(aStack, mMetaTileEntity.getEUVar(), mMetaTileEntity.getOutputTier(), false, false), true);
	}
	
	/**
	 * Called when trying to discharge Items
	 */
	public void dischargeItem(ItemStack aStack) {
		increaseStoredEnergyUnits(GT_ModHandler.dischargeElectricItem(aStack, getEnergyCapacity() - mMetaTileEntity.getEUVar(), mMetaTileEntity.getInputTier(), false, false), true);
	}
	
	@Override
    public void updateEntity() {
		
    	if (!hasValidMetaTileEntity()) {
    		if (mMetaTileEntity == null) {
	    		return;
	    	} else {
	    		mMetaTileEntity.setBaseMetaTileEntity(this);
	    	}
    	}
    	
    	try {
    	
    	if (hasValidMetaTileEntity()) {
    	    if (mTickTimer++==0) {
        		if (mFacing == -1) mFacing = 0;
        		mMetaTileEntity.onFirstTick();
        		if (!hasValidMetaTileEntity()) return;
		    	issueClientUpdate();
    	    }
    	    
	    	/*
	    	if (mLightValue != oLightValue) {
	    		getWorld().setLightValue(EnumSkyBlock.Block, getXCoord(), getYCoord(), getZCoord(), mLightValue);
	    		getWorld().updateLightByType(EnumSkyBlock.Block, getXCoord(), getYCoord(), getZCoord());
	    		oLightValue = mLightValue;
		    	issueTextureUpdate();
	    	}
	    	*/
    	    
    	    if (isClientSide()) {
    	    	if (mNeedsUpdate) {
    			    getWorld().markBlockForRenderUpdate(getXCoord(), getYCoord(), getZCoord());
    			    mNeedsUpdate = false;
    	    	}
    	    }
    	    
    	    if (isServerSide()) {
    	    	for (byte i = 0; i < 6; i++) {
    	    		mCoverData[i] = getCoverBehaviorAtSide(i).doCoverThings(i, getCoverIDAtSide(i), mCoverData[i], this);
    	    	}
    	    }
    	    
    		mMetaTileEntity.onPreTick();
        	
    		if (!hasValidMetaTileEntity()) return;
    		
    	    if (isServerSide()) {
    	    	if (mActive != oActive) {
    	    		oActive = mActive;
    	    		issueTextureUpdate();
    	    	}
    	    	
    	    	if (mRedstone != oRedstone) {
    	    		oRedstone = mRedstone;
    	    		issueBlockUpdate();
    	    		issueTextureUpdate();
    	    	}
    	    	
    	    	if (getXCoord() != oX || getYCoord() != oY || getZCoord() != oZ) {
    	    		oX = getXCoord();
    	    		oY = getYCoord();
    	    		oZ = getZCoord();
    		    	issueEnetUpdate();
    		    	issueTextureUpdate();
    	    	}
    	    	
    		    if (mFacing != oFacing) {
    		    	oFacing = mFacing;
    		    	issueEnetUpdate();
    	    		issueBlockUpdate();
    		    	issueTextureUpdate();
    		    }
    		    
    		    if (getOutputVoltage() != oOutput) {
    		    	oOutput = getOutputVoltage();
    		    	issueEnetUpdate();
    		    }
    		    
	    	    if (mMetaTileEntity.isEnetOutput()||mMetaTileEntity.isEnetInput() && !mIsAddedToEnet) {
	    	    	mIsAddedToEnet = GT_ModHandler.addTileToEnet(getWorld(), this);
	    	    }
    		    
	    	    if (mIsAddedToEnet && mMetaTileEntity.isEnetOutput() && mMetaTileEntity.getEUVar() >= Math.max(getOutputVoltage(), mMetaTileEntity.getMinimumStoredEU()) && getOutputVoltage() > 0) {
		    	    setStoredEU(mMetaTileEntity.getEUVar() + GT_ModHandler.emitEnergyToEnet(getOutputVoltage(), getWorld(), this) - getOutputVoltage());
		    	}
    	    	
    	        try {
	    	        if (getEnergyCapacity() > 0) {
		    	        if (GregTech_API.sMachineFireExplosions && getRandomNumber(1000) == 0 && getBlockIDAtSide((byte)getRandomNumber(6)) == Block.fire.blockID) {
		        		    doEnergyExplosion();
		    	        }
		    	        
		    	        if (getCoverIDAtSide((byte)1) == 0) {
			    	       	if (getWorld().getPrecipitationHeight(getXCoord(), getZCoord()) - 2 < getYCoord()) {
			        	       	if (GregTech_API.sMachineRainExplosions && getRandomNumber(1000) == 0 && getWorld().isRaining()) {
			            	    	if (getRandomNumber(10)==0) {
			            	    		doEnergyExplosion();
			            	    	} else {
			            	    		setOnFire();
			            	    	}
			        	       	}
			        	       	if (GregTech_API.sMachineThunderExplosions && getRandomNumber(2500) == 0 && getWorld().isThundering()) {
			            	    	doEnergyExplosion();
			        	       	}
			    	       	}
		    	        }
	    	        }
    	        } catch(Throwable e) {
    	        	System.err.println("Encountered Exception while checking for Explosion conditions");
    	        	e.printStackTrace(GT_Log.err);
    	        }
    	        
        	    try {
		    	    if (mMetaTileEntity.dechargerSlotCount() > 0 && getEnergyStored() < getEnergyCapacity()) {
			    	    for (int j = 0; j < mMetaTileEntity.getInputTier(); j++) {
			    	        for (int i = mMetaTileEntity.dechargerSlotStartIndex(); i < mMetaTileEntity.dechargerSlotCount()+mMetaTileEntity.dechargerSlotStartIndex(); i++) {
			    		        if (mMetaTileEntity.mInventory[i] != null && getEnergyStored() < getEnergyCapacity()) {
			    		        	dischargeItem(mMetaTileEntity.mInventory[i]);
			    		        	mInventoryChanged = true;
			    		        }
			    	        }
			    	    }
		    	    }
		    	    
		    	    if (mMetaTileEntity.rechargerSlotCount() > 0 && mMetaTileEntity.getEUVar() > 0) {
			        	for (int j = 0; j < mMetaTileEntity.getOutputTier(); j++) {
		    		        for (int i = mMetaTileEntity.rechargerSlotStartIndex(); i < mMetaTileEntity.rechargerSlotCount()+mMetaTileEntity.rechargerSlotStartIndex(); i++) {
		    			        if (mMetaTileEntity.getEUVar() > 0 && mMetaTileEntity.mInventory[i] != null) {
		    			        	chargeItem(mMetaTileEntity.mInventory[i]);
		    			        	mInventoryChanged = true;
		    			        }
		    		        }
		    	        }
	        	    }
        	    } catch(Throwable e) {
        	    	System.err.println("Encountered Exception while charging/decharging Items");
        	    	e.printStackTrace(GT_Log.err);
        	    }
   	        }
    	    
    	    try {
    	    	updateStatus();
    	    } catch(Throwable e) {
    	    	System.err.println("Encountered Exception in Cross Mod Energy Systems");
    	    	e.printStackTrace(GT_Log.err);
    	    }
    	    
    		if (!hasValidMetaTileEntity()) return;
    		
    	    mMetaTileEntity.onPostTick();
    	    
    		if (!hasValidMetaTileEntity()) return;
    		
        	if (isServerSide()) {
    		    if (mSendClientData) {
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0),  3, mID);
	    	    	mSendClientData = false;
	    	    	issueRedstoneUpdate();
	    	    	issueCoverUpdate();
    	    	}
    		    
    		    if (mNeedsCoverUpdate) {
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0),  7, getCoverIDAtSide((byte)0));
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0),  8, getCoverIDAtSide((byte)1));
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0),  9, getCoverIDAtSide((byte)2));
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0), 10, getCoverIDAtSide((byte)3));
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0), 11, getCoverIDAtSide((byte)4));
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0), 12, getCoverIDAtSide((byte)5));
    	    		mNeedsCoverUpdate = false;
    	    		issueTextureUpdate();
    	    	}
    		    
    		    if (mNeedsSidedRedstoneUpdate) {
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0),  2, (mSidedRedstone[0]&15)|((mSidedRedstone[1]&15)<<4)|((mSidedRedstone[2]&15)<<8)|((mSidedRedstone[3]&15)<<12)|((mSidedRedstone[4]&15)<<16)|((mSidedRedstone[5]&15)<<20));
    		    	mNeedsSidedRedstoneUpdate = false;
    	    		issueTextureUpdate();
    		    }
    		    
    		    if (mNeedsUpdate) {
    	    		getWorld().addBlockEvent(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0),  0, (getFrontFacing()&7) | (mActive?8:0) | (mRedstone?16:0) | ((mLightValue&15) << 5) | (mMetaTileEntity.getUpdateData() << 16));
    			    mNeedsUpdate = false;
    	    	}
    		    
    	    	if (mNeedsBlockUpdate) {
    		    	getWorld().notifyBlocksOfNeighborChange(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0));
    	    		mNeedsBlockUpdate = false;
    	    	}
    	    }
    	}
    	
    	} catch(Throwable e) {
    		System.err.println("Encountered Exception while ticking TileEntity, the Game should've crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
    		e.printStackTrace(GT_Log.err);
    	}
    	
    	mWorkUpdate = mInventoryChanged = false;
    }
	
	@Override
    public Packet getDescriptionPacket() {
		issueClientUpdate();
        return null;
    }
	
    @Override
    public boolean receiveClientEvent(int aEventID, int aValue) {
		super.receiveClientEvent(aEventID, aValue);
		
		if (hasValidMetaTileEntity()) {
			try {
				mMetaTileEntity.receiveClientEvent(aEventID, aValue);
			} catch(Throwable e) {
				System.err.println("Encountered Exception while receiving Data from the Server, the Client should've been crashed by now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
				e.printStackTrace(GT_Log.err);
			}
		}
		
		if (isClientSide()) {
	    	issueTextureUpdate();
			switch(aEventID) {
			case  0:
				mFacing   = (byte)(aValue& 7);
				mActive   = ((aValue& 8) != 0);
				mRedstone = ((aValue&16) != 0);
				mLightValue = (byte)((aValue>>>5)&15);
				if (hasValidMetaTileEntity()) mMetaTileEntity.onValueUpdate((short)(aValue>>>16));
				break;
			case  2:
				mSidedRedstone[0] = (byte)( aValue& 15          );
				mSidedRedstone[1] = (byte)((aValue&(15<< 4))>> 4);
				mSidedRedstone[2] = (byte)((aValue&(15<< 8))>> 8);
				mSidedRedstone[3] = (byte)((aValue&(15<<12))>>12);
				mSidedRedstone[4] = (byte)((aValue&(15<<16))>>16);
				mSidedRedstone[5] = (byte)((aValue&(15<<20))>>20);
				break;
			case  3:
				if (mID != aValue && aValue > 0) {
			    	mID = aValue;
			    	createNewMetatileEntity(mID);
				}
		    	break;
			case  4:
		    	if (hasValidMetaTileEntity() && mTickTimer > 20) mMetaTileEntity.doSound(aValue, getXCoord()+0.5, getYCoord()+0.5, getZCoord()+0.5);
		    	break;
			case  5:
				if (hasValidMetaTileEntity() && mTickTimer > 20) mMetaTileEntity.startSoundLoop(aValue, getXCoord()+0.5, getYCoord()+0.5, getZCoord()+0.5);
		    	break;
			case  6:
				if (hasValidMetaTileEntity() && mTickTimer > 20) mMetaTileEntity.stopSoundLoop(aValue, getXCoord()+0.5, getYCoord()+0.5, getZCoord()+0.5);
	    		break;
			case  7:
				mCoverSides[0] = aValue;
		    	break;
			case  8:
				mCoverSides[1] = aValue;
		    	break;
			case  9:
				mCoverSides[2] = aValue;
		    	break;
			case 10:
				mCoverSides[3] = aValue;
		    	break;
			case 11:
				mCoverSides[4] = aValue;
		    	break;
			case 12:
				mCoverSides[5] = aValue;
		    	break;
			}
		}
		return true;
	}
    
	public ArrayList<String> getDebugInfo(EntityPlayer aPlayer, int aLogLevel) {
		ArrayList<String> tList = new ArrayList<String>();
		if (aLogLevel > 2) {
			tList.add("Meta-ID: " + mID + (hasValidMetaTileEntity()?" valid":" invalid") + (mMetaTileEntity==null?" MetaTileEntity == null!":" "));
		}
		if (aLogLevel > 1) {
			tList.add("Is" + (mMetaTileEntity.isAccessAllowed(aPlayer)?" ":" not ") + "accessible for you");
		}
		if (aLogLevel > 0) {
			if (getMJCapacity() > 0 && hasMJConverterUpgrade()) tList.add(getStoredMJ() + " of " + getMJCapacity() + " MJ");
			tList.add("Machine is " + (mActive?"active":"inactive"));
		}
		return mMetaTileEntity.getSpecialDebugInfo(aPlayer, aLogLevel, tList);
	}
	
	@Override public void issueTextureUpdate() {mNeedsUpdate = true;}
	@Override public void issueCoverUpdate() {mNeedsCoverUpdate = true;}
	@Override public void issueRedstoneUpdate() {mNeedsSidedRedstoneUpdate = true;}
	@Override public void issueBlockUpdate() {mNeedsBlockUpdate = true;}
	@Override public void issueClientUpdate() {mSendClientData = true;}
	@Override public void issueEnetUpdate() {if (mIsAddedToEnet) GT_ModHandler.removeTileFromEnet(getWorld(), this); mIsAddedToEnet = false;}
	
	@Override public int getXCoord() {return xCoord;}
	@Override public int getYCoord() {return yCoord;}
	@Override public int getZCoord() {return zCoord;}
	@Override public World getWorld() {return worldObj;}
	@Override public boolean isServerSide() {return !getWorld().isRemote;}
	@Override public boolean isClientSide() {return  getWorld().isRemote;}
    @Override public int getRandomNumber(int aRange) {return getWorld().rand.nextInt(aRange);}
	@Override public BiomeGenBase getBiome(int aX, int aZ) {return getWorld().getBiomeGenForCoords(aX, aZ);}
    
    @Override public short getBlockID(int aX, int aY, int aZ) {return (short)getWorld().getBlockId(aX, aY, aZ);}
    @Override public short getBlockIDOffset(int aX, int aY, int aZ) {return getBlockID(getXCoord()+aX, getYCoord()+aY, getZCoord()+aZ);}
    @Override public short getBlockIDAtSide(byte aSide) {return getBlockIDAtSideAndDistance(aSide, 1);}
    @Override public short getBlockIDAtSideAndDistance(byte aSide, int aDistance) {return getBlockIDOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public byte getMetaID(int aX, int aY, int aZ) {return (byte)getWorld().getBlockMetadata(aX, aY, aZ);}
    @Override public byte getMetaIDOffset(int aX, int aY, int aZ) {return getMetaID(getXCoord()+aX, getYCoord()+aY, getZCoord()+aZ);}
    @Override public byte getMetaIDAtSide(byte aSide) {return getMetaIDAtSideAndDistance(aSide, 1);}
    @Override public byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {return getMetaIDOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public byte getLightLevel(int aX, int aY, int aZ) {return (byte)(getWorld().getLightBrightness(aX, aY, aZ)*15);}
	@Override public byte getLightLevelOffset(int aX, int aY, int aZ) {return getLightLevel(getXCoord()+aX, getYCoord()+aY, getZCoord()+aZ);}
	@Override public byte getLightLevelAtSide(byte aSide) {return getLightLevelAtSideAndDistance(aSide, 1);}
	@Override public byte getLightLevelAtSideAndDistance(byte aSide, int aDistance) {return getLightLevelOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
	@Override public boolean getSky(int aX, int aY, int aZ) {return getWorld().canBlockSeeTheSky(aX, aY, aZ);}
	@Override public boolean getSkyOffset(int aX, int aY, int aZ) {return getSky(getXCoord()+aX, getYCoord()+aY, getZCoord()+aZ);}
	@Override public boolean getSkyAtSide(byte aSide) {return getSkyAtSideAndDistance(aSide, 1);}
	@Override public boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {return getSkyOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
	@Override public TileEntity getTileEntity(int aX, int aY, int aZ) {return getWorld().getBlockTileEntity(aX, aY, aZ);}
    @Override public TileEntity getTileEntityOffset(int aX, int aY, int aZ) {return getTileEntity(getXCoord()+aX, getYCoord()+aY, getZCoord()+aZ);}
    @Override public TileEntity getTileEntityAtSide(byte aSide) {return getTileEntityAtSideAndDistance(aSide, 1);}
    @Override public TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {return getTileEntityOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public IInventory getIInventory(int aX, int aY, int aZ) {TileEntity tTileEntity = getTileEntity(aX, aY, aZ); if (tTileEntity != null && tTileEntity instanceof IInventory) return (IInventory)tTileEntity; return null;}
    @Override public IInventory getIInventoryOffset(int aX, int aY, int aZ) {return getIInventory(getXCoord()+aX, getYCoord()+aY, getZCoord()+aZ);}
    @Override public IInventory getIInventoryAtSide(byte aSide) {return getIInventoryAtSideAndDistance(aSide, 1);}
    @Override public IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance) {return getIInventoryOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public void openGUI(EntityPlayer aPlayer, int aID) {aPlayer.openGui(GregTech_API.gregtechmod, aID, getWorld(), getXCoord(), getYCoord(), getZCoord());}
    @Override public void openGUI(EntityPlayer aPlayer, int aID, Object aMod) {aPlayer.openGui(aMod, aID, getWorld(), getXCoord(), getYCoord(), getZCoord());}
    @Override public byte getStrongestRedstone() {return (byte)Math.max(getInternalInputRedstoneSignal((byte)0), Math.max(getInternalInputRedstoneSignal((byte)1), Math.max(getInternalInputRedstoneSignal((byte)2), Math.max(getInternalInputRedstoneSignal((byte)3), Math.max(getInternalInputRedstoneSignal((byte)4), getInternalInputRedstoneSignal((byte)5))))));}
	
    public boolean getRedstone() {return getRedstone((byte)0)||getRedstone((byte)1)||getRedstone((byte)2)||getRedstone((byte)3)||getRedstone((byte)4)||getRedstone((byte)5);}
    public boolean getRedstone(byte aSide) {return getInternalInputRedstoneSignal(aSide) > 0;}
    
    public Icon getCoverTexture(byte aSide) {return GregTech_API.sCovers.get(getCoverIDAtSide(aSide));}
	
	@Override public String getMainInfo() {if (hasValidMetaTileEntity()) return mMetaTileEntity.getMainInfo(); return "";}
	@Override public String getSecondaryInfo() {if (hasValidMetaTileEntity()) return mMetaTileEntity.getSecondaryInfo(); return "";}
	@Override public String getTertiaryInfo() {if (hasValidMetaTileEntity()) return mMetaTileEntity.getTertiaryInfo(); return "";}
	@Override public boolean isGivingInformation() {if (hasValidMetaTileEntity()) return mMetaTileEntity.isGivingInformation(); return false;}
	@Override public boolean wrenchCanSetFacing(EntityPlayer aPlayer, int aDirection) {if (hasValidMetaTileEntity()) return getFrontFacing() != aDirection && mMetaTileEntity.isFacingValid((byte)aDirection); return false;}
	@Override public ForgeDirection getDirection(IBlockAccess aWorld, int aX, int aY, int aZ) {return ForgeDirection.getOrientation(getFrontFacing());}
	@Override public void setDirection(World aWorld, int aX, int aY, int aZ, ForgeDirection aFacing) {setFacing((short)aFacing.ordinal());}
	@Override public short getFacing() {return getFrontFacing();}
	@Override public byte getBackFacing() {return (byte)ForgeDirection.getOrientation(getFrontFacing()).getOpposite().ordinal();}
	@Override public byte getFrontFacing() {return mFacing;}
	@Override public void setFrontFacing(byte aFacing) {if (hasValidMetaTileEntity() && mMetaTileEntity.isFacingValid((byte)aFacing)) {mFacing = (byte)aFacing; mMetaTileEntity.onFacingChange(); onMachineBlockUpdate();}}
	@Override public void setFacing(short aFacing) {if (hasValidMetaTileEntity() && mMetaTileEntity.isFacingValid((byte)aFacing)) {mFacing = (byte)aFacing; mMetaTileEntity.onFacingChange(); onMachineBlockUpdate();}}
	@Override public boolean wrenchCanRemove(EntityPlayer aPlayer) {if (hasValidMetaTileEntity() && mMetaTileEntity.ownerWrench()) return playerOwnsThis(aPlayer, true); return true;}
	@Override public float getWrenchDropRate() {if (hasValidMetaTileEntity() && mMetaTileEntity.isSimpleMachine()) return 1.0F; return 0.8F;}
	@Override public int getSizeInventory() {if (hasValidMetaTileEntity()) return mMetaTileEntity.getSizeInventory(); else return 0;}
	@Override public ItemStack getStackInSlot(int aIndex) {if (hasValidMetaTileEntity()) return mMetaTileEntity.getStackInSlot(aIndex); return null;}
	@Override public void setInventorySlotContents(int aIndex, ItemStack aStack) {mInventoryChanged = true; if (hasValidMetaTileEntity()) mMetaTileEntity.setInventorySlotContents(aIndex, aStack);}
	@Override public String getInvName() {if (hasValidMetaTileEntity()) return mMetaTileEntity.getInvName(); if (GregTech_API.mMetaTileList[mID] != null) return GregTech_API.mMetaTileList[mID].mName; return "";}
	@Override public int getInventoryStackLimit() {if (hasValidMetaTileEntity()) return mMetaTileEntity.getInventoryStackLimit(); return 64;}
	@Override public void openChest()  {if (hasValidMetaTileEntity()) mMetaTileEntity.onOpenGUI();}
	@Override public void closeChest() {if (hasValidMetaTileEntity()) mMetaTileEntity.onCloseGUI();}
	@Override public boolean isUseableByPlayer(EntityPlayer aPlayer) {return hasValidMetaTileEntity() && playerOwnsThis(aPlayer, false) && mTickTimer>40 && getTileEntityOffset(0, 0, 0) == this && aPlayer.getDistanceSq(getXCoord() + 0.5, getYCoord() + 0.5, getZCoord() + 0.5) < 64 && mMetaTileEntity.isAccessAllowed(aPlayer);}
	@Override public void validate() {super.validate(); issueTextureUpdate(); mTickTimer = 0;}
    @Override public void invalidate() {if (hasValidMetaTileEntity()) {mMetaTileEntity.onRemoval(); mMetaTileEntity.setBaseMetaTileEntity(null);} if (mIsAddedToEnet) mIsAddedToEnet = !GT_ModHandler.removeTileFromEnet(getWorld(), this); super.invalidate();}
	@Override public boolean isInvNameLocalized() {return false;}
    @Override public ItemStack getStackInSlotOnClosing(int slot) {ItemStack stack = getStackInSlot(slot); if (stack != null) setInventorySlotContents(slot, null); return stack;}
    @Override public ItemStack decrStackSize(int aIndex, int aAmount) {mInventoryChanged = true; ItemStack stack = getStackInSlot(aIndex); if (stack != null) {if (stack.stackSize <= aAmount) {if (hasValidMetaTileEntity() && mMetaTileEntity.setStackToZeroInsteadOfNull(aIndex)) stack.stackSize = 0; else setInventorySlotContents(aIndex, null);} else {stack = stack.splitStack(aAmount); if (stack.stackSize == 0) {if (hasValidMetaTileEntity() && !mMetaTileEntity.setStackToZeroInsteadOfNull(aIndex)) setInventorySlotContents(aIndex, null);}}} return stack;}
	@Override public void onMachineBlockUpdate() {if (hasValidMetaTileEntity()) mMetaTileEntity.onMachineBlockUpdate();}
	@Override public int getProgress() {return hasValidMetaTileEntity()?mMetaTileEntity.getProgresstime():0;}
	@Override public int getMaxProgress() {return hasValidMetaTileEntity()?mMetaTileEntity.maxProgresstime():0;}
	@Override public boolean increaseProgress(int aProgressAmountInTicks) {return hasValidMetaTileEntity()?mMetaTileEntity.increaseProgress(aProgressAmountInTicks)!=aProgressAmountInTicks:false;}
	@Override public boolean hasThingsToDo() {return getMaxProgress()>0;}
	@Override public void enableWorking() {if (!mWorks) mWorkUpdate = true; mWorks = true;}
	@Override public void disableWorking() {mWorks = false;}
	@Override public boolean isAllowedToWork() {return mWorks;}
	@Override public boolean hasWorkJustBeenEnabled() {return mWorkUpdate;}
	@Override public void setWorkDataValue(byte aValue) {mWorkData = aValue;}
	@Override public byte getWorkDataValue() {return mWorkData;}
    @Override public int getMetaTileID() {return mID;}
	@Override public boolean isActive() {return mActive;}
    @Override public void setActive(boolean aActive) {mActive = aActive;}
	@Override public long getTimer() {return mTickTimer;}
	@Override public int getEnergyStored() {return hasValidMetaTileEntity()?Math.max(Math.max(getStoredMJ(), getStoredSteam()), getMetaTileEntity().getEUVar()):0;}
	@Override public int getEnergyCapacity() {return hasValidMetaTileEntity()?mMetaTileEntity.maxEUStore() + getUpgradeStorageVolume():0;}
	@Override public boolean decreaseStoredEnergyUnits(int aEnergy, boolean aIgnoreTooLessEnergy) {if (!hasValidMetaTileEntity()) return false; return decreaseStoredMJ(aEnergy, false) || decreaseStoredSteam(aEnergy, false) || decreaseStoredEU(aEnergy, aIgnoreTooLessEnergy) || (aIgnoreTooLessEnergy && (decreaseStoredMJ(aEnergy, true) || decreaseStoredSteam(aEnergy, true)));}
	@Override public boolean increaseStoredEnergyUnits(int aEnergy, boolean aIgnoreTooMuchEnergy) {if (!hasValidMetaTileEntity()) return false; if (mMetaTileEntity.getEUVar() < getEnergyCapacity() || aIgnoreTooMuchEnergy) {setStoredEU(mMetaTileEntity.getEUVar() + aEnergy); return true;} return false;}
	@Override public boolean inputEnergyFrom(byte aSide) {if (!getCoverBehaviorAtSide(aSide).letsEnergyIn (aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this)) return false; if (isInvalid()||mReleaseEnergy) return false;			if (hasValidMetaTileEntity() && mMetaTileEntity.isEnetInput ()) return mMetaTileEntity.isInputFacing (aSide); return false;}
	@Override public boolean outputsEnergyTo(byte aSide) {if (!getCoverBehaviorAtSide(aSide).letsEnergyOut(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this)) return false; if (isInvalid()||mReleaseEnergy) return mReleaseEnergy;	if (hasValidMetaTileEntity() && mMetaTileEntity.isEnetOutput()) return mMetaTileEntity.isOutputFacing(aSide); return false;}
	@Override public int getOutputAmperage() {if (hasValidMetaTileEntity()) return mMetaTileEntity.maxEUPulses()==1?mTransformers>0?4:1:mMetaTileEntity.maxEUPulses(); return 0;}
	@Override public int getOutputVoltage() {if (hasValidMetaTileEntity() && mMetaTileEntity.isEnetOutput()) return mMetaTileEntity.maxEUOutput() * (mTransformers>0?(int)Math.pow(4, mTransformers-1):1); return 0;}
	@Override public int getInputVoltage() {if (hasValidMetaTileEntity()) return mMetaTileEntity.maxEUInput()*(int)Math.pow(4, mTransformers); return Integer.MAX_VALUE;}
	@Override public boolean increaseStoredMJ(int aEnergy, boolean aIgnoreTooMuchEnergy) {if (!hasValidMetaTileEntity()) return false; if (mMetaTileEntity.getMJVar() < getMJCapacity()	|| aIgnoreTooMuchEnergy) {setStoredMJ(mMetaTileEntity.getMJVar() + aEnergy); return true;} return false;}
	@Override public boolean increaseStoredSteam(int aEnergy, boolean aIgnoreTooMuchEnergy) {if (!hasValidMetaTileEntity()) return false; if (mMetaTileEntity.getSteamVar() < getSteamCapacity() || aIgnoreTooMuchEnergy) {setStoredSteam(mMetaTileEntity.getSteamVar() + aEnergy); return true;} return false;}
	@Override public String getDescription() {if (hasValidMetaTileEntity()) return mMetaTileEntity.getDescription(); return "";}
    @Override public boolean isValidSlot(int aIndex) {if (hasValidMetaTileEntity()) return mMetaTileEntity.isValidSlot(aIndex); return false;}
    @Override public int getStoredMJ() {if (hasValidMetaTileEntity()) return Math.min(mMetaTileEntity.getMJVar(), getMJCapacity()); return 0;}
    @Override public int getMJCapacity() {if (hasValidMetaTileEntity()) return mMetaTileEntity.maxMJStore() + mRSEnergyCells * 100000; return 0;}
    @Override public int getStoredSteam() {if (hasValidMetaTileEntity()) return Math.min(mMetaTileEntity.getSteamVar(), getSteamCapacity()); return 0;}
    @Override public int getSteamCapacity() {if (hasValidMetaTileEntity()) return mMetaTileEntity.maxSteamStore() + mSteamTanks * 32000; return 0;}
    
    public boolean setStoredEU			(int aEnergy) {if (!hasValidMetaTileEntity()) return false; if (aEnergy < 0) aEnergy = 0; mMetaTileEntity.setEUVar		(aEnergy); return true;}
    public boolean setStoredMJ			(int aEnergy) {if (!hasValidMetaTileEntity()) return false; if (aEnergy < 0) aEnergy = 0; mMetaTileEntity.setMJVar		(aEnergy); return true;}
    public boolean setStoredSteam		(int aEnergy) {if (!hasValidMetaTileEntity()) return false; if (aEnergy < 0) aEnergy = 0; mMetaTileEntity.setSteamVar	(aEnergy); return true;}
    public boolean decreaseStoredEU		(int aEnergy, boolean aIgnoreTooLessEnergy)	{if (!hasValidMetaTileEntity()) return false; if (mMetaTileEntity.getEUVar()	- aEnergy >= 0	|| aIgnoreTooLessEnergy) {mMetaTileEntity.setEUVar		(mMetaTileEntity.getEUVar()		- aEnergy); if (mMetaTileEntity.getEUVar()		< 0) {setStoredEU	(0); return false;} return true;} return false;}
    public boolean decreaseStoredMJ		(int aEnergy, boolean aIgnoreTooLessEnergy)	{if (!hasValidMetaTileEntity()) return false; if (mMetaTileEntity.getMJVar()	- aEnergy >= 0	|| aIgnoreTooLessEnergy) {mMetaTileEntity.setMJVar		(mMetaTileEntity.getMJVar()		- aEnergy); if (mMetaTileEntity.getMJVar() 		< 0) {setStoredMJ	(0); return false;} return true;} return false;}
    public boolean decreaseStoredSteam	(int aEnergy, boolean aIgnoreTooLessEnergy)	{if (!hasValidMetaTileEntity()) return false; if (mMetaTileEntity.getSteamVar()	- aEnergy >= 0	|| aIgnoreTooLessEnergy) {mMetaTileEntity.setSteamVar	(mMetaTileEntity.getSteamVar()	- aEnergy); if (mMetaTileEntity.getSteamVar()	< 0) {setStoredSteam(0); return false;} return true;} return false;}
	
    public boolean playerOwnsThis(EntityPlayer aPlayer, boolean aCheckPrecicely) {if (!hasValidMetaTileEntity()) return false; if (aCheckPrecicely || mMetaTileEntity.ownerControl() || mOwnerName.equals("")) if (mOwnerName.equals("")&&isServerSide()) mOwnerName = aPlayer.username; else if (!aPlayer.username.equals("Player") && !mOwnerName.equals("Player") && !mOwnerName.equals(aPlayer.username)) return false; return true;}
    public boolean privateAccess() {if (!hasValidMetaTileEntity()) return false; return mMetaTileEntity.ownerControl();}
    public boolean unbreakable() {if (!hasValidMetaTileEntity()) return false; return mMetaTileEntity.unbreakable();}
    public void doEnergyExplosion() {if (getEnergyCapacity() > 0 && getEnergyStored() >= getEnergyCapacity()/5) doExplosion(getOutputVoltage()*(getEnergyStored() >= getEnergyCapacity()?4:getEnergyStored() >= getEnergyCapacity()/2?2:1));}
	public int getTextureIndex(byte aSide, byte aMeta) {if (hasValidMetaTileEntity()) return mMetaTileEntity.getTextureIndex(aSide, getFrontFacing(), mActive, getOutputRedstoneSignal(aSide)>0); return -2;}
	public Icon getTextureIcon(byte aSide, byte aMeta) {Icon rIcon = getCoverTexture(aSide); if (rIcon != null) return rIcon; if (hasValidMetaTileEntity()) return mMetaTileEntity.getTextureIcon(aSide, getFrontFacing(), mActive, getOutputRedstoneSignal(aSide)>0); return null;}
    public boolean hasValidMetaTileEntity() {return mMetaTileEntity != null && mMetaTileEntity.getBaseMetaTileEntity() == this;}
    
    @Override public int fill(ForgeDirection aDirection, LiquidStack aLiquid, boolean doFill) {
		if (hasValidMetaTileEntity() && (aDirection == ForgeDirection.UNKNOWN || (mMetaTileEntity.isLiquidInput((byte)aDirection.ordinal()) && getCoverBehaviorAtSide((byte)aDirection.ordinal()).letsLiquidIn((byte)aDirection.ordinal(), getCoverIDAtSide((byte)aDirection.ordinal()), getCoverDataAtSide((byte)aDirection.ordinal()), this)))) {
    		if (hasSteamEngineUpgrade() && GT_ModHandler.isSteam(aLiquid)) {
        		int tSteam = Math.min(aLiquid.amount/2, getSteamCapacity() - getStoredSteam());
	    		if (tSteam > 0) {
	        		if (doFill) increaseStoredSteam(tSteam, true);
	    			return tSteam*2;
	    		}
    		} else {
        		return mMetaTileEntity.fill(aLiquid, doFill);
    		}
    	}
		return 0;
    }
    
	@Override public int fill(int aIndex, LiquidStack aLiquid, boolean doFill) {
		return 0;
	}
	
	@Override public LiquidStack drain(ForgeDirection aDirection, int maxDrain, boolean doDrain) {
		if (hasValidMetaTileEntity() && (aDirection == ForgeDirection.UNKNOWN || (mMetaTileEntity.isLiquidOutput((byte)aDirection.ordinal()) && getCoverBehaviorAtSide((byte)aDirection.ordinal()).letsLiquidOut((byte)aDirection.ordinal(), getCoverIDAtSide((byte)aDirection.ordinal()), getCoverDataAtSide((byte)aDirection.ordinal()), this)))) {
			return mMetaTileEntity.drain(maxDrain, doDrain);
		}
		return null;
	}
	
	/* You are not supposed to get the Steam back out of a Machine, but this Code would perfectly work for that.
	@Override public LiquidStack drain(ForgeDirection aDirection, int maxDrain, boolean doDrain) {
		if (hasValidMetaTileEntity() && (aDirection == ForgeDirection.UNKNOWN || (mMetaTileEntity.isLiquidOutput((byte)aDirection.ordinal()) && getCoverBehaviorAtSide((byte)aDirection.ordinal()).letsLiquidOut((byte)aDirection.ordinal(), getCoverIDAtSide((byte)aDirection.ordinal()), getCoverDataAtSide((byte)aDirection.ordinal()), this)))) {
			LiquidStack tLiquid = mMetaTileEntity.drain(maxDrain, doDrain);
			if (tLiquid == null && getStoredSteam() > 0) {
				tLiquid = GT_ModHandler.getSteam(Math.min(maxDrain, getStoredSteam()*2));
				if (tLiquid == null) return null;
				tLiquid.amount-=tLiquid.amount%2;
				if (tLiquid.amount<=0) return null;
				if (doDrain) decreaseStoredSteam(tLiquid.amount/2, true);
			}
			return tLiquid;
		}
		return null;
	}
	*/
	
	@Override public LiquidStack drain(int aIndex, int maxDrain, boolean doDrain) {
		return null;
	}
	
	@Override public ILiquidTank[] getTanks(ForgeDirection aDirection) {
		if (hasValidMetaTileEntity() && (mMetaTileEntity.getCapacity()>0 || hasSteamEngineUpgrade()) && (aDirection == ForgeDirection.UNKNOWN || ((mMetaTileEntity.isLiquidInput((byte)aDirection.ordinal()) && getCoverBehaviorAtSide((byte)aDirection.ordinal()).letsLiquidIn((byte)aDirection.ordinal(), getCoverIDAtSide((byte)aDirection.ordinal()), getCoverDataAtSide((byte)aDirection.ordinal()), this)) || (mMetaTileEntity.isLiquidOutput((byte)aDirection.ordinal()) && getCoverBehaviorAtSide((byte)aDirection.ordinal()).letsLiquidOut((byte)aDirection.ordinal(), getCoverIDAtSide((byte)aDirection.ordinal()), getCoverDataAtSide((byte)aDirection.ordinal()), this)))))
			return new ILiquidTank[] {mMetaTileEntity};
		return new ILiquidTank[] {};
	}
	
	@Override public ILiquidTank getTank(ForgeDirection aSide, LiquidStack aLiquid) {
		if (hasValidMetaTileEntity() && (mMetaTileEntity.getCapacity()>0 || hasSteamEngineUpgrade()) && (aLiquid == null || (aLiquid != null && aLiquid.isLiquidEqual(mMetaTileEntity.getLiquid()))))
			return mMetaTileEntity;
		return null;
	}
	
    @Override
    public void doExplosion(int aAmount) {
    	if (mIsAddedToEnet && GregTech_API.sMachineWireFire) {
	        try {
	        	mReleaseEnergy = true;
	        	
	        	GT_ModHandler.removeTileFromEnet(getWorld(), this);
	        	GT_ModHandler.addTileToEnet(getWorld(), this);
		        GT_ModHandler.emitEnergyToEnet(  32, getWorld(), this);
		        GT_ModHandler.emitEnergyToEnet( 128, getWorld(), this);
		        GT_ModHandler.emitEnergyToEnet( 512, getWorld(), this);
		        GT_ModHandler.emitEnergyToEnet(2048, getWorld(), this);
		        GT_ModHandler.emitEnergyToEnet(8192, getWorld(), this);
	        } catch(Exception e) {}
    	}
    	mReleaseEnergy = false;
    	if (hasValidMetaTileEntity()) mMetaTileEntity.onExplosion();
    	float tStrength = aAmount<10?1.0F:aAmount<32?2.0F:aAmount<128?3.0F:aAmount<512?4.0F:aAmount<2048?5.0F:aAmount<4096?6.0F:aAmount<8192?7.0F:8.0F;
    	int tX=getXCoord(), tY=getYCoord(), tZ=getZCoord();
    	getWorld().setBlock(tX, tY, tZ, 0);
    	getWorld().createExplosion(null, tX+0.5, tY+0.5, tZ+0.5, tStrength, true);
    }
	
	@Override
	public ItemStack getWrenchDrop(EntityPlayer aPlayer) {
		ItemStack rStack = new ItemStack(GregTech_API.sBlockList[1], 1, mID);
		NBTTagCompound tNBT = new NBTTagCompound();
    	if (mMJConverter		) tNBT.setBoolean	("mMJConverter"		, mMJConverter);
    	if (mSteamConverter		) tNBT.setBoolean	("mSteamConverter"	, mSteamConverter);
		if (mTransformers		> 0) tNBT.setByte	("mTransformers"	, mTransformers);
		if (mOverclockers		> 0) tNBT.setByte	("mOverclockers"	, mOverclockers);
		if (mRSEnergyCells		> 0) tNBT.setByte	("mRSEnergyCells"	, mRSEnergyCells);
		if (mSteamTanks			> 0) tNBT.setByte	("mSteamTanks"		, mSteamTanks);
		if (mOtherUpgrades		> 0) tNBT.setByte	("mOtherUpgrades"	, mOtherUpgrades);
		if (mUpgradedStorage	> 0) tNBT.setInteger("mUpgradedStorage"	, mUpgradedStorage);
		if (hasValidMetaTileEntity()) mMetaTileEntity.setItemNBT(tNBT);
		if (!tNBT.hasNoTags()) rStack.setTagCompound(tNBT);
		return rStack;
	}
	
	public int getUpgradeCount() {
		return (mMJConverter?1:0)+(mSteamConverter?1:0)+mSteamTanks+mTransformers+mOverclockers+mOtherUpgrades+mRSEnergyCells;
	}
	
	@Override
	public void onRightclick(EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
		if (isClientSide()) {
			if (getCoverBehaviorAtSide(aSide).onCoverRightclickClient(aSide, this, aPlayer, aX, aY, aZ)) return;
		}
		if (isServerSide()) {
			issueClientUpdate();
			
			if (GT_Utility.isItemStackInList(aPlayer.inventory.getCurrentItem(), GregTech_API.sScrewdriverList)) {
				if (GT_ModHandler.damageOrDechargeItem(aPlayer.inventory.getCurrentItem(), 1, 1000, aPlayer)) {
					if (getCoverIDAtSide(aSide) == -2) setCoverIDAtSide(aSide, -1); else
					if (getCoverIDAtSide(aSide) == -1) setCoverIDAtSide(aSide, -2); else
					if (getCoverIDAtSide(aSide) ==  0) setCoverIDAtSide(aSide, -1); else
					setCoverDataAtSide(aSide, getCoverBehaviorAtSide(aSide).onCoverScrewdriverclick(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this, aPlayer));
				}
				return;
			}
			
			if (getCoverIDAtSide(aSide) == 0) {
				if (GT_Utility.isItemStackInList(aPlayer.inventory.getCurrentItem(), GregTech_API.sCovers.keySet())) {
					if (GregTech_API.getCoverBehavior(aPlayer.inventory.getCurrentItem()).isCoverPlaceable(aSide, GT_Utility.stackToInt(aPlayer.inventory.getCurrentItem()), this)) setCoverItemAtSide(aSide, aPlayer.inventory.getCurrentItem());
					if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					return;
				}
			} else {
				if (GT_Utility.isItemStackInList(aPlayer.inventory.getCurrentItem(), GregTech_API.sCrowbarList)) {
					if (GT_ModHandler.damageOrDechargeItem(aPlayer.inventory.getCurrentItem(), 1, 1000, aPlayer)) {
						ItemStack tStack = GT_Utility.intToStack(getCoverIDAtSide(aSide));
						if (tStack != null) {
							EntityItem tEntity = new EntityItem(getWorld(), getXCoord() + ForgeDirection.getOrientation(aSide).offsetX + 0.5, getYCoord() + ForgeDirection.getOrientation(aSide).offsetY + 0.5, getZCoord() + ForgeDirection.getOrientation(aSide).offsetZ + 0.5, tStack);
							tEntity.motionX = 0;
							tEntity.motionY = 0;
							tEntity.motionZ = 0;
							getWorld().spawnEntityInWorld(tEntity);
						}
						if (getCoverBehaviorAtSide(aSide).onCoverRemoval(aSide, getCoverIDAtSide(aSide), mCoverData[aSide], this, false)) {
							setCoverIDAtSide(aSide, 0);
							if (mMetaTileEntity.hasSidedRedstoneOutputBehavior()) {
								setOutputRedstoneSignal(aSide, (byte) 0);
							} else {
								setOutputRedstoneSignal(aSide, (byte)15);
							}
						}
						return;
					}
				}
			}
			if (getCoverBehaviorAtSide(aSide).onCoverRightclick(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this, aPlayer, aX, aY, aZ)) return;
			
			if (!getCoverBehaviorAtSide(aSide).isGUIClickable(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this)) return;
			
			if (isUpgradable() && aPlayer.inventory.getCurrentItem() != null) {
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 25))) {
					if (addMJConverterUpgrade()) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 80))) {
					if (addSteamEngineUpgrade()) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GT_ModHandler.getIC2Item("overclockerUpgrade", 1))) {
					if (addOverclockerUpgrade()) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (getInputVoltage() < 512 && getOutputVoltage() < 128){
					if (aPlayer.inventory.getCurrentItem().isItemEqual(GT_ModHandler.getIC2Item("transformerUpgrade", 1))) {
						if (addTransformerUpgrade()) {
							if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
						}
						return;
					}
				} else {
					if (aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 27))) {
						if (addTransformerUpgrade()) {
							if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
						}
						return;
					}
				}
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GT_ModHandler.getIC2Item("energyStorageUpgrade", 1))) {
					if (addBatteryUpgrade(10000, (byte)1)) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 26))) {
					if (addBatteryUpgrade(100000, (byte)1)) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 12))) {
					if (addBatteryUpgrade(100000, (byte)2)) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 13))) {
					if (addBatteryUpgrade(1000000, (byte)3)) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 14))) {
					if (addBatteryUpgrade(10000000, (byte)4)) {
						if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					}
					return;
				}
				if (hasMJConverterUpgrade() && aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 28))) {
					mRSEnergyCells++;
					if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					return;
				}
				if (hasSteamEngineUpgrade() && aPlayer.inventory.getCurrentItem().isItemEqual(GregTech_API.getGregTechItem(3, 1, 81))) {
					mSteamTanks++;
					if (!aPlayer.capabilities.isCreativeMode) aPlayer.inventory.getCurrentItem().stackSize--;
					return;
				}
			}
			
			try {
				if (aPlayer != null && hasValidMetaTileEntity() && mID > 15) mMetaTileEntity.onRightclick(aPlayer, aSide, aX, aY, aZ);
	    	} catch(Throwable e) {
	    		System.err.println("Encountered Exception while rightclicking TileEntity, the Game should've crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
	    		e.printStackTrace(GT_Log.err);
	    	}
		}
	}
	
	@Override
	public void onLeftclick(EntityPlayer aPlayer) {
		issueClientUpdate();
		
		try {
			if (aPlayer != null && hasValidMetaTileEntity() && mID > 15) mMetaTileEntity.onLeftclick(aPlayer);
    	} catch(Throwable e) {
    		System.err.println("Encountered Exception while leftclicking TileEntity, the Game should've crashed now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
    		e.printStackTrace(GT_Log.err);
    	}
	}
	
	@Override
	public boolean isDigitalChest() {
		if (hasValidMetaTileEntity()) return mMetaTileEntity.isDigitalChest();
		return false;
	}
	
	@Override
	public ItemStack[] getStoredItemData() {
		if (hasValidMetaTileEntity()) return mMetaTileEntity.getStoredItemData();
		return null;
	}
	
	@Override
	public void setItemCount(int aCount) {
		if (hasValidMetaTileEntity()) mMetaTileEntity.setItemCount(aCount);
	}
	
	@Override
	public int getMaxItemCount() {
		if (hasValidMetaTileEntity()) return mMetaTileEntity.getMaxItemCount();
		return 0;
	}
	
	/**
	 * Can put aStack into Slot
	 */
	@Override
	public boolean isStackValidForSlot(int aIndex, ItemStack aStack) {
		return isValidSlot(aIndex);
	}
	
	/**
	 * returns all valid Inventory Slots, no matter which Side (Unless it's covered).
	 * The Side Stuff is done in the following two Functions.
	 */
	@Override
	public int[] getSizeInventorySide(int aSide) {
		if (hasValidMetaTileEntity() && (getCoverBehaviorAtSide((byte)aSide).letsItemsIn((byte)aSide, getCoverIDAtSide((byte)aSide), getCoverDataAtSide((byte)aSide), this) || getCoverBehaviorAtSide((byte)aSide).letsItemsOut((byte)aSide, getCoverIDAtSide((byte)aSide), getCoverDataAtSide((byte)aSide), this))) {
			ArrayList<Integer> tList = new ArrayList<Integer>();
			for (int i = 0; i < getSizeInventory(); i++) if (isValidSlot(i)) tList.add(i);
			int[] rArray = new int[tList.size()];
			for (int i = 0; i < rArray.length; i++) rArray[i] = (int)tList.get(i);
			return rArray;
		}
		return new int[0];
	}
	
	/**
	 * Can put aStack into Slot at Side
	 */
	@Override
	public boolean func_102007_a(int aIndex, ItemStack aStack, int aSide) {
		return hasValidMetaTileEntity() && getCoverBehaviorAtSide((byte)aSide).letsItemsIn ((byte)aSide, getCoverIDAtSide((byte)aSide), getCoverDataAtSide((byte)aSide), this) && isValidSlot(aIndex) && aStack != null && mMetaTileEntity.allowPutStack (aIndex, (byte)aSide, aStack);
	}
	
	/**
	 * Can pull aStack out of Slot from Side
	 */
	@Override
	public boolean func_102008_b(int aIndex, ItemStack aStack, int aSide) {
		return hasValidMetaTileEntity() && getCoverBehaviorAtSide((byte)aSide).letsItemsOut((byte)aSide, getCoverIDAtSide((byte)aSide), getCoverDataAtSide((byte)aSide), this) && isValidSlot(aIndex) && aStack != null && mMetaTileEntity.allowPullStack(aIndex, (byte)aSide, aStack);
	}
	
	@Override
	public boolean isUpgradable() {
		return hasValidMetaTileEntity() && getUpgradeCount() < 16;
	}
	
	@Override
	public boolean isMJConverterUpgradable() {
		return isUpgradable()&&!hasMJConverterUpgrade()&&getMJCapacity()>0;
	}
	
	@Override
	public boolean isOverclockerUpgradable() {
		return isUpgradable()&&mMetaTileEntity.isOverclockerUpgradable()&&mOverclockers<4;
	}
	
	@Override
	public boolean isTransformerUpgradable() {
		return isUpgradable()&&mMetaTileEntity.isTransformerUpgradable()&&getInputVoltage()<8192&&getOutputVoltage()<2048;
	}
	
	@Override
	public boolean isBatteryUpgradable(int aStorage, byte aTier) {
		return isUpgradable()&&mMetaTileEntity.isBatteryUpgradable()&&GT_Utility.getTier(getInputVoltage())>=aTier&&aStorage+getEnergyCapacity()>0;
	}
	
	@Override
	public boolean hasMJConverterUpgrade() {
		return mMJConverter;
	}
	
	@Override
	public byte getOverclockerUpgradeCount() {
		return (byte)mOverclockers;
	}
	
	@Override
	public byte getTransformerUpgradeCount() {
		return (byte)mTransformers;
	}
	
	@Override
	public int getUpgradeStorageVolume() {
		return mUpgradedStorage;
	}
	
	@Override
	public byte getInternalInputRedstoneSignal(byte aSide) {
		return getCoverBehaviorAtSide(aSide).acceptsSidedRedstoneInput(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this)?0:getInputRedstoneSignal(aSide);
	}
	
	@Override
	public byte getInputRedstoneSignal(byte aSide) {
		return getCoverBehaviorAtSide(aSide).acceptsSidedRedstoneInput(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this)||getCoverBehaviorAtSide(aSide).letsRedstoneGoIn(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this)?(byte)getWorld().getIndirectPowerLevelTo(ForgeDirection.getOrientation(aSide).offsetX + getXCoord(), ForgeDirection.getOrientation(aSide).offsetY + getYCoord(), ForgeDirection.getOrientation(aSide).offsetZ + getZCoord(), aSide):0;
	}
	
	@Override
	public byte getOutputRedstoneSignal(byte aSide) {
		return getCoverBehaviorAtSide(aSide).manipulatesSidedRedstoneOutput(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this) || (mRedstone && getCoverBehaviorAtSide(aSide).letsRedstoneGoOut(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this))?mSidedRedstone[aSide]:0;
	}
	
	@Override
	public void setOutputRedstoneSignal(byte aSide, byte aStrength) {
		aStrength = aStrength>15?15:aStrength<0?0:aStrength;
		if (aSide >= 0 && aSide < 6 && mSidedRedstone[aSide] != aStrength) {
			mSidedRedstone[aSide] = aStrength;
			issueRedstoneUpdate();
    		issueBlockUpdate();
		}
	}
	
	@Override
	public boolean isSteamEngineUpgradable() {
		return isUpgradable()&&!hasSteamEngineUpgrade()&&getSteamCapacity()>0;
	}
	
	@Override
	public boolean addSteamEngineUpgrade() {
		if (isSteamEngineUpgradable()) {
			issueBlockUpdate();
			mSteamConverter = true;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasSteamEngineUpgrade() {
		return mSteamConverter;
	}
	
	@Override
	public boolean addMJConverterUpgrade() {
		if (isMJConverterUpgradable()) {
			mMJConverter = true;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addOverclockerUpgrade() {
		if (isOverclockerUpgradable()) {
			mOverclockers++;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addTransformerUpgrade() {
		if (isTransformerUpgradable()) {
			mTransformers++;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addBatteryUpgrade(int aStorage, byte aTier) {
		if (isBatteryUpgradable(aStorage, aTier)) {
			mUpgradedStorage+=aStorage;
			mOtherUpgrades++;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean hasInventoryBeenModified() {
		return mInventoryChanged;
	}
	
	@Override
	public void setGenericRedstoneOutput(boolean aOnOff) {
		mRedstone = aOnOff;
	}
	
	@Override
	public int getErrorDisplayID() {
		return mDisplayErrorCode;
	}
	
	@Override
	public void setErrorDisplayID(int aErrorID) {
		mDisplayErrorCode = aErrorID;
	}
	
	@Override
	public MetaTileEntity getMetaTileEntity() {
		return hasValidMetaTileEntity()?mMetaTileEntity:null;
	}
	
	@Override
	public void setCoverIDAtSide(byte aSide, int aID) {
		if (aSide >= 0 && aSide < 6) {
			mCoverSides[aSide] = aID;
			mCoverData[aSide] = 0;
			issueEnetUpdate();
			issueCoverUpdate();
			issueBlockUpdate();
		}
	}
	
	@Override
	public void setCoverItemAtSide(byte aSide, ItemStack aCover) {
		setCoverIDAtSide(aSide, GT_Utility.stackToInt(aCover));
	}
	
	@Override
	public int getCoverIDAtSide(byte aSide) {
		if (aSide >= 0 && aSide < 6) return mCoverSides[aSide]; return 0;
	}
	
	@Override
	public ItemStack getCoverItemAtSide(byte aSide) {
		return GT_Utility.intToStack(getCoverIDAtSide(aSide));
	}
	
	@Override
	public GT_CoverBehavior getCoverBehaviorAtSide(byte aSide) {
		return GregTech_API.getCoverBehavior(getCoverIDAtSide(aSide));
	}
	
	@Override
	public boolean canPlaceCoverIDAtSide(byte aSide, int aID) {
		return getCoverIDAtSide(aSide) == 0;
	}
	
	@Override
	public boolean canPlaceCoverItemAtSide(byte aSide, ItemStack aCover) {
		return getCoverIDAtSide(aSide) == 0;
	}
	
	@Override
	public void setCoverDataAtSide(byte aSide, int aData) {
		if (aSide >= 0 && aSide < 6) mCoverData[aSide] = aData;
	}
	
	@Override
	public int getCoverDataAtSide(byte aSide) {
		if (aSide >= 0 && aSide < 6) return mCoverData[aSide];
		return 0;
	}
	
	@Override
	public void setLightValue(byte aLightValue) {
		mLightValue = (byte)(aLightValue & 15);
	}
	
	@Override
	public void setOnFire() {
		for (byte i = 0; i < 6; i++) {
			short tID = getBlockIDAtSide(i);
			Block tBlock = Block.blocksList[tID];
			if (tID == 0 || tBlock == null || null == tBlock.getCollisionBoundingBoxFromPool(getWorld(), getXCoord() + ForgeDirection.getOrientation(i).offsetX, getYCoord() + ForgeDirection.getOrientation(i).offsetY, getZCoord() + ForgeDirection.getOrientation(i).offsetZ)) {
    			getWorld().setBlock(ForgeDirection.getOrientation(i).offsetX+getXCoord(), ForgeDirection.getOrientation(i).offsetY+getYCoord(), ForgeDirection.getOrientation(i).offsetZ+getZCoord(), Block.fire.blockID);
    		}
    	}
	}
}