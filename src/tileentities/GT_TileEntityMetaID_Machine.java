package gregtechmod.common.tileentities;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.interfaces.IIC2TileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_CoverBehavior;
import gregtechmod.api.util.GT_ModHandler;
import ic2.api.Direction;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidStack;

public class GT_TileEntityMetaID_Machine extends TileEntity implements IGregTechTileEntity, IIC2TileEntity {
	
    /**
     * @return true if this Device emits Energy
     */
    public boolean isEnetOutput() {return false;}
    
    /**
     * @return true if this Device consumes Energy
     */
    public boolean isEnetInput()  {return false;}
    
    /**
     * @return the amount of EU, which can be stored in this Device. Default is 0 EU.
     */
    public int maxEUStore()  {return 0;}
    
    /**
     * @return the amount of EU/t, which can be accepted by this Device before it explodes. Default is 0 EU/t.
     */
    public int maxEUInput()  {return 0;}
    
    /**
     * @return the amount of EU/t, which can be outputted by this Device. Default is 0 EU/t.
     */
    public int maxEUOutput() {return 0;}
    
    /**
     * @return true if that Direction is an Output.
     */
    public boolean isOutputFacing(short aDirection) {return false;}
    
    /**
     * @return true if that Direction is an Input.
     */
    public boolean isInputFacing(short aDirection) {return false;}
    
    /**
     * @param aFacing
     * @return if aFacing would be a valid Facing for this Device
     */
    public boolean isFacingValid(int aFacing) {return false;}
    
    /**
     * Is called by invalidate right before it removes the TileEntity from the E-net
     */
    public void onRemoval() {}
    
    /**
     * @return Amount of InventorySlots for this Device
     */
    public int getInventorySlotCount() {return 0;}
    
    /**
     * Use this to add custom Data instead of using writeToNBT
     */
    public void storeAdditionalData(NBTTagCompound aNBT) {}

    /**
     * Use this to read custom Data instead of using readFromNBT
     */
    public void getAdditionalData(NBTTagCompound aNBT) {}

    /**
     * Use this instead of updateEntity (gets called before all the E-net-Stuff)
     */
    public void onPreTickUpdate() {}
    
    /**
     * Use this instead of updateEntity (gets called after all the E-net-Stuff)
     */
    public void onPostTickUpdate() {}
    
    /**
     * Use this instead of updateEntity (gets called once before anything)
     */
    public void onFirstTickUpdate() {}
    
    /**
     * @return true if the Machine can be accessed
     */
    public boolean isAccessible(EntityPlayer aPlayer) {return false;}
    
    /**
     * @return if aIndex is a valid Slot. false for things like HoloSlots.
     */
    public boolean isValidSlot(int aIndex) {return true;}
    
    /**
     * This is used to set the internal Energy to the given Parameter. I use this for the IDSU.
     */
	public void setEnergyVar(int aEU) {
		mStoredEnergy = aEU;
	}

    /**
     * This is used to get the internal Energy. I use this for the IDSU.
     */
	public int getEnergyVar() {
		return mStoredEnergy;
	}
    
    /**
     * Determines the Tier of the Machine, used for charging Tools.
     */
    public int getTier() {
    	return getTier(Math.max(maxEUOutput(), maxEUInput()));
    }
    
    public int getTier(int aValue) {
    	int tMaxEU = aValue;
    	return tMaxEU<=32?1:tMaxEU<=128?2:tMaxEU<=512?3:tMaxEU<=2048?4:tMaxEU<=8192?5:6;
    }
    
    public int getChargeTier() {
    	return getTier();
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
     * returns if this Object is animated
     */
    public boolean hasAnimation() {
    	return false;
    }

    /**
     * returns the DebugLog
     */
	public ArrayList<String> getSpecialDebugInfo(EntityPlayer aPlayer, int aLogLevel, ArrayList<String> aList) {
		return aList;
	}
	
	public void onLeftclick(EntityPlayer aPlayer) {
		
	}
	
	@Override
	public String getMainInfo() {
		return "";
	}

	@Override
	public String getSecondaryInfo() {
		return "";
	}

	@Override
	public String getTertiaryInfo() {
		return "";
	}

	@Override
	public boolean isGivingInformation() {
		return false;
	}
	
    @Override public String getInvName() {return "Defaultmachine";}
    @Override public void openChest() {}
    @Override public void closeChest() {}
    
    // Basecode, which should not be touched in extending Devices
    
	public ItemStack[] mInventory;
	
	public boolean mIsAddedToEnet, mFirstTick, mNeedsUpdate = true, mActive, oActive, mRedstone, oRedstone, mReleaseEnergy = false;
	private int mStoredEnergy, oOutput, oX = 0, oY = 0, oZ = 0;;
	private short mFacing, oFacing;
	public String mOwnerName = "";
	
	public long mTickTimer;
	
    public GT_TileEntityMetaID_Machine() {
        mInventory = new ItemStack[getInventorySlotCount()];
        mIsAddedToEnet = false;
        mFirstTick = true;
        mStoredEnergy = 0;
        mTickTimer = 0;
        mFacing = -1;
    }


    
    @Override
	public void updateEntity() {
    	if (isInvalid()) return;
    	
    	try {
    	
    	mTickTimer++;
    	
    	if (mFirstTick) {
    		if (mFacing == -1) mFacing = 0;
    		if (mTickTimer>19) {
	    		mFirstTick = false;
	            onFirstTickUpdate();
	            mNeedsUpdate = true;
    		} else return;
    	}
    	
    	if (mTickTimer % 6000 == 0) mNeedsUpdate = true;
    	
    	onPreTickUpdate();
	    if (worldObj.isRemote) {
	    	if (mNeedsUpdate) {
			    worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
			    mNeedsUpdate = false;
	    	}
	    } else {
		    if (mNeedsUpdate) {
	    		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 0, mFacing);
		    	worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 1, mActive?1:0);
		    	worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 2, mRedstone?1:0);
			    mNeedsUpdate = false;
	    	}
	    	
	    	if (mActive != oActive) {
	    		oActive = mActive;
			    mNeedsUpdate = true;
	    	}

	    	if (mRedstone != oRedstone) {
		    	worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
	    		oRedstone = mRedstone;
			    mNeedsUpdate = true;
	    	}
	    	
	    	if (mTickTimer > 30 && (isEnetOutput()||isEnetInput()) && !mIsAddedToEnet) {
	    		mIsAddedToEnet = GT_ModHandler.addTileToEnet(worldObj, this);
	    	}
	    	
	    	if (xCoord != oX || yCoord != oY || zCoord != oZ) {
	    		oX = xCoord;
	    		oY = yCoord;
	    		oZ = zCoord;
		    	if (mIsAddedToEnet) GT_ModHandler.removeTileFromEnet(worldObj, this);
		    	mIsAddedToEnet = false;
			    mNeedsUpdate = true;
	    	}
	    	
		    if (mFacing != oFacing) {
		    	oFacing = mFacing;
		    	if (mIsAddedToEnet) GT_ModHandler.removeTileFromEnet(worldObj, this);
		    	mIsAddedToEnet = false;
		    }

		    if (getOutput() != oOutput) {
		    	oOutput = getOutput();
		    	if (mIsAddedToEnet) GT_ModHandler.removeTileFromEnet(worldObj, this);
		    	mIsAddedToEnet = false;
		    	mNeedsUpdate = true;
		    }
		    
		    if (mIsAddedToEnet && GregTech_API.sMachineFireExplosions && worldObj.rand.nextInt(1000) == 0) {
		    	switch (worldObj.rand.nextInt(6)) {
		    	case 0:
		    		if (worldObj.getBlockId(xCoord+1, yCoord, zCoord) == Block.fire.blockID) doEnergyExplosion();
		    		break;
		    	case 1:
		    		if (worldObj.getBlockId(xCoord-1, yCoord, zCoord) == Block.fire.blockID) doEnergyExplosion();
		    		break;
		    	case 2:
		    		if (worldObj.getBlockId(xCoord, yCoord+1, zCoord) == Block.fire.blockID) doEnergyExplosion();
		    		break;
		    	case 3:
		    		if (worldObj.getBlockId(xCoord, yCoord-1, zCoord) == Block.fire.blockID) doEnergyExplosion();
		    		break;
		    	case 4:
		    		if (worldObj.getBlockId(xCoord, yCoord, zCoord+1) == Block.fire.blockID) doEnergyExplosion();
		    		break;
		    	case 5:
		    		if (worldObj.getBlockId(xCoord, yCoord, zCoord-1) == Block.fire.blockID) doEnergyExplosion();
		    		break;
		    	}
		    }
		    
	        if (mIsAddedToEnet && isEnetOutput() && getEnergyVar() >= maxEUOutput() && maxEUOutput() > 0) {
	            setStoredEnergy(getEnergyVar() + GT_ModHandler.emitEnergyToEnet(maxEUOutput(), worldObj, this) - maxEUOutput());
	        }
	        
	        for (int j = 0; j < getChargeTier(); j++) {
		        for (int i = dechargerSlotStartIndex(); i < dechargerSlotCount()+dechargerSlotStartIndex(); i++) {
			        if (mInventory[i] != null && demandsEnergy()>0 && mInventory[i].getItem() instanceof IElectricItem) {
			            if (((IElectricItem)mInventory[i].getItem()).canProvideEnergy(mInventory[i]))
			                increaseStoredEnergy(ElectricItem.discharge(mInventory[i], maxEUStore() - getEnergyVar(), getChargeTier(), false, false));
			        }
		        }
		        
		        for (int i = rechargerSlotStartIndex(); i < rechargerSlotCount()+rechargerSlotStartIndex(); i++) {
			        if (getEnergyVar() > 0 && mInventory[i] != null && mInventory[i].getItem() instanceof IElectricItem)
			        	decreaseStoredEnergy(ElectricItem.charge(mInventory[i], getEnergyVar(), getChargeTier(), false, false), true);
		        }
	        }
	    }
    	onPostTickUpdate();
    	onInventoryChanged();
    	
    	} catch(Throwable e) {
    		System.err.println("Encountered Exception while ticking TileEntity, the Game should've crashed by now, but I prevented that. Please report immidietly to GregTech Intergalactical!!!");
    		e.printStackTrace();
    	}
    }

    @Override
	public boolean receiveClientEvent(int aEventID, int aValue) {
		super.receiveClientEvent(aEventID, aValue);
		if (worldObj.isRemote) {
			switch(aEventID) {
			case 0:
				mFacing = (short)aValue;
				mNeedsUpdate = true;
				break;
			case 1:
		    	mActive = (aValue!=0);
				mNeedsUpdate = true;
		    	break;
			case 2:
		    	mRedstone = (aValue!=0);
				mNeedsUpdate = true;
		    	break;
			}
		}
		return true;
	}
    
    @Override
    public void readFromNBT(NBTTagCompound aNBT) {
    	if (aNBT == null) return;
    	
        super.readFromNBT(aNBT);
        
        mStoredEnergy	= aNBT.getInteger("mStoredEnergy");
        mFacing			= aNBT.getShort("mFacing");
        mOwnerName		= aNBT.getString("mOwnerName");
    	mActive			= aNBT.getBoolean("mActive");
    	mRedstone		= aNBT.getBoolean("mRedstone");
    	
        getAdditionalData(aNBT);
        
        NBTTagList tagList = aNBT.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < mInventory.length) {
                mInventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound aNBT) {
        super.writeToNBT(aNBT);
        
        aNBT.setInteger("mStoredEnergy", mStoredEnergy);
        aNBT.setShort("mFacing", mFacing);
        aNBT.setString("mOwnerName", mOwnerName);
    	aNBT.setBoolean("mActive", mActive);
    	aNBT.setBoolean("mRedstone", mRedstone);
        
        storeAdditionalData(aNBT);
        
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < mInventory.length; i++) {
            ItemStack stack = mInventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        aNBT.setTag("Inventory", itemList);
    }

    @Override
    public void invalidate() {
    	onRemoval();
    	if (mIsAddedToEnet) mIsAddedToEnet = !GT_ModHandler.removeTileFromEnet(worldObj, this);
        super.invalidate();
    }

	@Override
    public void validate() {
        super.validate();
        mNeedsUpdate = true;
        mTickTimer = 0;
    }
    
    @Override
	public boolean isAddedToEnergyNet() {
		return mIsAddedToEnet;
	}
    
	@Override
	public boolean acceptsEnergyFrom(TileEntity aReceiver, Direction aDirection) {
    	if (isInvalid()||mReleaseEnergy) return false;
		return isInputFacing((short)aDirection.toSideValue());
	}
	
	@Override
	public boolean emitsEnergyTo(TileEntity aReceiver, Direction aDirection) {
    	if (isInvalid()||mReleaseEnergy) return mReleaseEnergy;
		return isOutputFacing((short)aDirection.toSideValue());
	}
	
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int aFacing) {
		return aFacing != mFacing && isFacingValid(aFacing);
	}
	
	@Override public short getFacing() {return getFrontFacing();}
	@Override public byte getBackFacing() {return (byte)ForgeDirection.getOrientation(getFrontFacing()).getOpposite().ordinal();}
	@Override public byte getFrontFacing() {return (byte)mFacing;}
	
	@Override
	public void setFacing(short aFacing) {
        mNeedsUpdate = true;
		if (isFacingValid(aFacing)) {
			mFacing = aFacing;
		}
	}

	@Override
	public boolean wrenchCanRemove(EntityPlayer aPlayer) {
		return playerOwnsThis(aPlayer);
	}

	@Override
	public float getWrenchDropRate() {
		return 0.8F;
	}
	
    @Override
    public int getSizeInventory() {
        return mInventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return mInventory[slot];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        mInventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= amt) {
                setInventorySlotContents(slot, null);
            } else {
                stack = stack.splitStack(amt);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            setInventorySlotContents(slot, null);
        }
        return stack;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean playerOwnsThis(EntityPlayer aPlayer) {
    	if (ownerControl())
    		if (mOwnerName.equals("")&&!worldObj.isRemote)
    			mOwnerName = aPlayer.username;
    		else
    			if (!aPlayer.username.equals("Player") && !mOwnerName.equals("Player") && !mOwnerName.equals(aPlayer.username))
    				return false;
    	return true;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
    	mNeedsUpdate = true;
        return playerOwnsThis(aPlayer)&&!mFirstTick&&mTickTimer>20&&worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && aPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64 && isAccessible(aPlayer);
    }
    
	@Override
	public int getStored() {
		return Math.min(getEnergyVar(), maxEUStore());
	}
	
	public boolean setStoredEnergy(int aEnergy) {
		if (aEnergy < 0) aEnergy = 0;
		setEnergyVar(aEnergy);
		return true;
	}
	
	public boolean decreaseStoredEnergy(int aEnergy, boolean aIgnoreTooLessEnergy) {
		if (getEnergyVar() - aEnergy >= 0 || aIgnoreTooLessEnergy) {
			setEnergyVar(getEnergyVar() - aEnergy);
			if (getEnergyVar() < 0) {
				setStoredEnergy(0);
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean increaseStoredEnergy(int aEnergy) {
		if (getEnergyVar() < maxEUStore()) {
			setStoredEnergy(getEnergyVar() + aEnergy);
			return true;
		}
		return false;
	}
	
	public void doExplosion(int aAmount) {
    	if (isAddedToEnergyNet() && GregTech_API.sMachineWireFire) {
	        try {
	        	mReleaseEnergy = true;
	        	
	        	GT_ModHandler.removeTileFromEnet(worldObj, this);
	        	GT_ModHandler.addTileToEnet(worldObj, this);

		        GT_ModHandler.emitEnergyToEnet(  32, worldObj, this);
		        GT_ModHandler.emitEnergyToEnet( 128, worldObj, this);
		        GT_ModHandler.emitEnergyToEnet( 512, worldObj, this);
		        GT_ModHandler.emitEnergyToEnet(2048, worldObj, this);
		        GT_ModHandler.emitEnergyToEnet(8192, worldObj, this);
	        } catch(Exception e) {}
    	}
    	mReleaseEnergy = false;
        float tStrength = aAmount<10?1.0F:aAmount<32?2.0F:aAmount<128?3.0F:aAmount<512?4.0F:aAmount<2048?5.0F:aAmount<4096?6.0F:aAmount<8192?7.0F:8.0F;
        int tX=xCoord, tY=yCoord, tZ=zCoord;
        worldObj.setBlock(tX, tY, tZ, 0);
        worldObj.createExplosion(null, tX+0.5, tY+0.5, tZ+0.5, tStrength, true);
    }
	
	@Override
	public int getMaxEnergyOutput() {
		if (mReleaseEnergy) return Integer.MAX_VALUE;
		return maxEUOutput();
	}
	
	@Override
	public int demandsEnergy() {
		if (mReleaseEnergy) return 0;
		return maxEUStore() - getEnergyVar();
	}
	
	@Override
	public int injectEnergy(Direction directionFrom, int aAmount) {
		if (aAmount > maxEUInput()) {
			doExplosion(aAmount);
			return 0;
		}
		setStoredEnergy(getEnergyVar() + aAmount);
		return 0;
	}
	
	public int getTexture(int aSide, int aMeta) {
		return 0;
	}
	
	@Override
	public int getCapacity() {
		return maxEUStore();
	}
	
	@Override
	public int getOutput() {
		return maxEUOutput();
	}
	
    public boolean isActive() {
    	return mActive;
    }
    
	public ArrayList<String> getDebugInfo(EntityPlayer aPlayer, int aLogLevel) {
		ArrayList<String> tList = new ArrayList<String>();
		if (aLogLevel > 2) {
			
		}
		if (aLogLevel > 1) {
			tList.add("Is" + (isAccessible(aPlayer)?" ":" not ") + "accessible for you");
		}
		if (aLogLevel > 0) {
			tList.add("Machine is " + (mActive?"active":"inactive"));
		}
		return getSpecialDebugInfo(aPlayer, aLogLevel, tList);
	}

	@Override
	public void setStored(int aEU) {
		setEnergyVar(aEU);
	}

	@Override
	public int addEnergy(int aEnergy) {
		if (aEnergy > 0)
			increaseStoredEnergy(aEnergy);
		else
			decreaseStoredEnergy(-aEnergy, true);
		return getStored();
	}
	
	@Override
	public boolean isTeleporterCompatible(Direction side) {
		return false;
	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(GregTech_API.sBlockList[1], 1, worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
	}
	
	public void doEnergyExplosion() {
		if (getStored() >= getCapacity()/5) doExplosion(getOutput()*(getStored() >= getCapacity()?4:getStored() >= getCapacity()/2?2:1));
	}
	
	@Override
	public int getMaxSafeInput() {
		return maxEUInput();
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
	@Override
	public boolean isStackValidForSlot(int i, ItemStack aStack) {
		if (!isValidSlot(i)) return false;
		if (aStack == null) return false;
		return true;
	}
	
	@Override
	public int[] getSizeInventorySide(int var1) {
		ArrayList<Integer> tList = new ArrayList<Integer>();
		for (int i = 0; i < getSizeInventory(); i++) if (isValidSlot(i)) tList.add(i);
		int[] rArray = new int[tList.size()];
		for (int i = 0; i < rArray.length; i++) rArray[i] = tList.get(i);
		return rArray;
	}
	
	@Override
	public boolean func_102007_a(int i, ItemStack itemstack, int j) {
		return isValidSlot(i);
	}
	
	@Override
	public boolean func_102008_b(int i, ItemStack itemstack, int j) {
		return isValidSlot(i);
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return 0;
	}
	
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		return 0;
	}
	
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}
	
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return null;
	}
	
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return null;
	}
	
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		return null;
	}
	
	@Override
	public void onMachineBlockUpdate() {
		
	}
	
	@Override
	public boolean isDigitalChest() {
		return false;
	}
	
	@Override
	public ItemStack[] getStoredItemData() {
		return null;
	}
	
	@Override
	public void setItemCount(int aCount) {
		
	}
	
	@Override
	public int getMaxItemCount() {
		return 0;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isMJConverterUpgradable() {
		return false;
	}

	@Override
	public boolean isOverclockerUpgradable() {
		return false;
	}

	@Override
	public boolean isTransformerUpgradable() {
		return false;
	}
	
	@Override
	public boolean hasMJConverterUpgrade() {
		return false;
	}

	@Override
	public byte getOverclockerUpgradeCount() {
		return 0;
	}

	@Override
	public byte getTransformerUpgradeCount() {
		return 0;
	}

	@Override
	public int getUpgradeStorageVolume() {
		return 0;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public int getProgress() {
		return 0;
	}

	@Override
	public int getMaxProgress() {
		return 0;
	}
	
	@Override
	public byte getOutputRedstoneSignal(byte aSide) {
		return mRedstone?(byte)15:0;
	}
	
	@Override
	public byte getInputRedstoneSignal(byte aSide) {
		return (byte)worldObj.getIndirectPowerLevelTo(ForgeDirection.getOrientation(aSide).offsetX + xCoord, ForgeDirection.getOrientation(aSide).offsetY + yCoord, ForgeDirection.getOrientation(aSide).offsetZ + zCoord, aSide);
	}
	
	@Override
	public boolean increaseProgress(int aProgressAmountInTicks) {
		return false;
	}
	
	@Override
	public boolean hasThingsToDo() {
		return false;
	}
	
	@Override
	public void enableWorking() {
		
	}
	
	@Override
	public void disableWorking() {
		
	}
	
	@Override
	public boolean isAllowedToWork() {
		return true;
	}
	
	@Override
	public void setOutputRedstoneSignal(byte aSide, byte aStrength) {
		
	}
	
	@Override public void setWorkDataValue(byte aValue) {
		
	}
	
	@Override public byte getWorkDataValue() {
		return 0;
	}
	
	@Override
	public BiomeGenBase getBiome(int aX, int aZ) {
		return worldObj.getBiomeGenForCoords(aX, aZ);
	}
	
	@Override public World getWorld() {return worldObj;}
	@Override public int getXCoord() {return xCoord;}
	@Override public int getYCoord() {return yCoord;}
	@Override public int getZCoord() {return zCoord;}
	@Override public boolean isServerSide() {return !worldObj.isRemote;}
	@Override public boolean isClientSide() {return  worldObj.isRemote;}
    @Override public int getRandomNumber(int aRange) {return worldObj.rand.nextInt(aRange);}
    @Override public short getBlockID(int aX, int aY, int aZ) {return (short)worldObj.getBlockId(aX, aY, aZ);}
    @Override public short getBlockIDOffset(int aX, int aY, int aZ) {return getBlockID(xCoord+aX, yCoord+aY, zCoord+aZ);}
    @Override public short getBlockIDAtSide(byte aSide) {return getBlockIDAtSideAndDistance(aSide, 1);}
    @Override public short getBlockIDAtSideAndDistance(byte aSide, int aDistance) {return getBlockIDOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public byte getMetaID(int aX, int aY, int aZ) {return (byte)worldObj.getBlockMetadata(aX, aY, aZ);}
    @Override public byte getMetaIDOffset(int aX, int aY, int aZ) {return getMetaID(xCoord+aX, yCoord+aY, zCoord+aZ);}
    @Override public byte getMetaIDAtSide(byte aSide) {return getMetaIDAtSideAndDistance(aSide, 1);}
    @Override public byte getMetaIDAtSideAndDistance(byte aSide, int aDistance) {return getMetaIDOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public TileEntity getTileEntity(int aX, int aY, int aZ) {return worldObj.getBlockTileEntity(aX, aY, aZ);}
    @Override public TileEntity getTileEntityOffset(int aX, int aY, int aZ) {return getTileEntity(xCoord+aX, yCoord+aY, zCoord+aZ);}
    @Override public TileEntity getTileEntityAtSide(byte aSide) {return getTileEntityAtSideAndDistance(aSide, 1);}
    @Override public TileEntity getTileEntityAtSideAndDistance(byte aSide, int aDistance) {return getTileEntityOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public IInventory getIInventory(int aX, int aY, int aZ) {TileEntity tTileEntity = getTileEntity(aX, aY, aZ); if (tTileEntity != null && tTileEntity instanceof IInventory) return (IInventory)tTileEntity; return null;}
    @Override public IInventory getIInventoryOffset(int aX, int aY, int aZ) {return getIInventory(getXCoord()+aX, getYCoord()+aY, getZCoord()+aZ);}
    @Override public IInventory getIInventoryAtSide(byte aSide) {return getIInventoryAtSideAndDistance(aSide, 1);}
    @Override public IInventory getIInventoryAtSideAndDistance(byte aSide, int aDistance) {return getIInventoryOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
    @Override public byte getLightLevel(int aX, int aY, int aZ) {return (byte)(worldObj.getLightBrightness(aX, aY, aZ)*15);}
	@Override public byte getLightLevelOffset(int aX, int aY, int aZ) {return getLightLevel(xCoord+aX, yCoord+aY, zCoord+aZ);}
	@Override public byte getLightLevelAtSide(byte aSide) {return getLightLevelAtSideAndDistance(aSide, 1);}
	@Override public byte getLightLevelAtSideAndDistance(byte aSide, int aDistance) {return getLightLevelOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
	@Override public boolean getSky(int aX, int aY, int aZ) {return worldObj.canBlockSeeTheSky(aX, aY, aZ);}
	@Override public boolean getSkyOffset(int aX, int aY, int aZ) {return getSky(xCoord+aX, yCoord+aY, zCoord+aZ);}
	@Override public boolean getSkyAtSide(byte aSide) {return getSkyAtSideAndDistance(aSide, 1);}
	@Override public boolean getSkyAtSideAndDistance(byte aSide, int aDistance) {return getSkyOffset(ForgeDirection.getOrientation(aSide).offsetX*aDistance, ForgeDirection.getOrientation(aSide).offsetY*aDistance, ForgeDirection.getOrientation(aSide).offsetZ*aDistance);}
	
	@Override
	public void setActive(boolean aActive) {
		mActive = aActive;
	}
	
	@Override
	public int getMetaTileID() {
		return getMetaIDOffset(0, 0, 0);
	}
	
	@Override
	public long getTimer() {
		return mTickTimer;
	}
	
	@Override
	public int getEnergyStored() {
		return getStored();
	}
	
	@Override
	public boolean increaseStoredEnergyUnits(int aEnergy, boolean aIgnoreTooMuchEnergy) {
		return increaseStoredEnergy(aEnergy);
	}
	
	@Override
	public boolean increaseStoredMJ(int aEnergy, boolean aIgnoreTooMuchEnergy) {
		return false;
	}

	@Override
	public void issueTextureUpdate() {
		mNeedsUpdate = true;
		
	}

	@Override
	public void issueCoverUpdate() {
		mNeedsUpdate = true;
		
	}

	@Override
	public void issueRedstoneUpdate() {
		mNeedsUpdate = true;
		
	}
	
	@Override
	public void issueBlockUpdate() {
		getWorld().notifyBlocksOfNeighborChange(getXCoord(), getYCoord(), getZCoord(), getBlockIDOffset(0, 0, 0));
	}
	
	@Override
	public void issueClientUpdate() {
		mNeedsUpdate = true;
	}

	@Override
	public void issueEnetUpdate() {
    	if (mIsAddedToEnet) GT_ModHandler.removeTileFromEnet(worldObj, this);
    	mIsAddedToEnet = false;
	}
	
	@Override public void openGUI(EntityPlayer aPlayer, int aID) {aPlayer.openGui(GregTech_API.gregtechmod, aID, getWorld(), getXCoord(), getYCoord(), getZCoord());}
    @Override public void openGUI(EntityPlayer aPlayer, int aID, Object aMod) {aPlayer.openGui(aMod, aID, getWorld(), getXCoord(), getYCoord(), getZCoord());}
    
	@Override
	public boolean isBatteryUpgradable(int aStorage, byte aTier) {
		return false;
	}
	
	@Override
	public boolean addMJConverterUpgrade() {
		return false;
	}
	
	@Override
	public boolean addOverclockerUpgrade() {
		return false;
	}
	
	@Override
	public boolean addTransformerUpgrade() {
		return false;
	}
	
	@Override
	public boolean addBatteryUpgrade(int aStorage, byte aTier) {
		return false;
	}
	
	@Override
	public void setGenericRedstoneOutput(boolean aOnOff) {
		mRedstone = aOnOff;
	}
	
	@Override
	public boolean hasInventoryBeenModified() {
		return false;
	}
	
	@Override
	public int getErrorDisplayID() {
		return 0;
	}
	
	@Override
	public void setErrorDisplayID(int aErrorID) {
		
	}
	
	@Override
	public MetaTileEntity getMetaTileEntity() {
		return null;
	}

	@Override
	public boolean hasWorkJustBeenEnabled() {
		return false;
	}

	@Override
	public void setCoverIDAtSide(byte aSide, int aID) {
		
	}

	@Override
	public void setCoverItemAtSide(byte aSide, ItemStack aCover) {
		
	}
	
	@Override
	public int getCoverIDAtSide(byte aSide) {
		return 0;
	}

	@Override
	public ItemStack getCoverItemAtSide(byte aSide) {
		return null;
	}
	
	@Override
	public GT_CoverBehavior getCoverBehaviorAtSide(byte aSide) {
		return GregTech_API.sGenericBehavior;
	}
	
	@Override
	public boolean canPlaceCoverIDAtSide(byte aSide, int aID) {
		return false;
	}
	
	@Override
	public boolean canPlaceCoverItemAtSide(byte aSide, ItemStack aCover) {
		return false;
	}
	
	@Override
	public void setCoverDataAtSide(byte aSide, int aData) {
		
	}
	
	@Override
	public int getCoverDataAtSide(byte aSide) {
		return 0;
	}

	@Override
	public byte getInternalInputRedstoneSignal(byte aSide) {
		return getInputRedstoneSignal(aSide);
	}
	
	@Override
	public byte getStrongestRedstone() {
		return (byte)Math.max(getInternalInputRedstoneSignal((byte)0), Math.max(getInternalInputRedstoneSignal((byte)1), Math.max(getInternalInputRedstoneSignal((byte)2), Math.max(getInternalInputRedstoneSignal((byte)3), Math.max(getInternalInputRedstoneSignal((byte)4), getInternalInputRedstoneSignal((byte)5))))));
	}

	@Override
	public void setInitialValuesAsNBT(NBTTagCompound aNBT, int aID) {
		readFromNBT(aNBT);
	}
	
	@Override
	public void onRightclick(EntityPlayer aPlayer, byte aSide, float par1, float par2, float par3) {
		
	}

	@Override
	public int getStoredMJ() {
		return 0;
	}

	@Override
	public void setLightValue(byte aLightValue) {
		
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
	
	@Override
	public int getEnergyCapacity() {
		return getCapacity();
	}

	@Override
	public int getOutputAmperage() {
		return 1;
	}

	@Override
	public int getOutputVoltage() {
		return getOutput();
	}

	@Override
	public int getInputVoltage() {
		return getMaxSafeInput();
	}

	@Override
	public boolean decreaseStoredEnergyUnits(int aEnergy, boolean aIgnoreTooLessEnergy) {
		return decreaseStoredEnergy(aEnergy, aIgnoreTooLessEnergy);
	}
	
	@Override
	public boolean inputEnergyFrom(byte aSide) {
		return false;
	}
	
	@Override
	public boolean outputsEnergyTo(byte aSide) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getMJCapacity() {
		return 0;
	}
	
	@Override public ForgeDirection getDirection(IBlockAccess aWorld, int aX, int aY, int aZ) {
		return ForgeDirection.getOrientation(getFrontFacing());
	}
	
	@Override public void setDirection(World aWorld, int aX, int aY, int aZ, ForgeDirection aFacing) {
		setFacing((short)aFacing.ordinal());
	}

	@Override
	public void setFrontFacing(byte aFacing) {
		setFacing(aFacing);
	}
	
	@Override
	public int getStoredSteam() {
		return 0;
	}
	
	@Override
	public int getSteamCapacity() {
		return 0;
	}
	
	@Override
	public boolean increaseStoredSteam(int aEnergy, boolean aIgnoreTooMuchEnergy) {
		return false;
	}
	
	@Override
	public boolean isSteamEngineUpgradable() {
		return false;
	}
	
	@Override
	public boolean addSteamEngineUpgrade() {
		return false;
	}
	
	@Override
	public boolean hasSteamEngineUpgrade() {
		return false;
	}
}