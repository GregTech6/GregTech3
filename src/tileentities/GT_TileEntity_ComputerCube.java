package gregtechmod.common.tileentities;

import gregtechmod.GT_Mod;
import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.common.GT_ComputercubeDescription;
import gregtechmod.common.items.GT_MetaItem_Cell;
import ic2.api.reactor.IC2Reactor;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class GT_TileEntity_ComputerCube extends GT_TileEntityMetaID_Machine implements IReactor {
	
	public static ArrayList<Item> sReactorList;
	
	public boolean mStarted = false;
	public int mMode = 0, mHeat = 0, mEUOut = 0, mMaxHeat = 10000, mEU = 0, mProgress = 0, mEUTimer = 0, mEULast1 = 0, mEULast2 = 0, mEULast3 = 0, mEULast4 = 0;
	public float mHEM = 1.0F, mExplosionStrength = 0.0F;
	
    public boolean isAccessible(EntityPlayer aPlayer) {
    	ItemStack tStack = aPlayer.getCurrentEquippedItem();
    	if (tStack == null) return true;
    	if (tStack.isItemEqual(GregTech_API.getGregTechItem(43, 1, 0))) return false;
    	return true;
    }
    
    public boolean isEnetInput()       					{return true;}
    public boolean isInputFacing(short aDirection)  	{return true;}
    public int maxEUStore()            					{return 10000;}
    public int maxEUInput()            					{return 32;}
    public boolean ownerControl()						{return true;}
    
    /**
     * Description of the Slots
     *   0 -  53 = ReactorPlannerMainSlots
     *  54 -  57 = ScannerSlots
     *  58       = DummySlot for the MenuButtons
     *  59 - 112 = ReactorPlannerSaveSlots
     * 113       = The ReactorPlannerCopySlot
     */
    public int getInventorySlotCount() {
    	return 114;
    }
    
    public boolean isValidSlot(int aIndex) {
    	return aIndex > 53 && aIndex < 58;
    }
    
	public void saveNuclearReactor() {
		for (int i = 0; i < 54; i++) {
			if (mInventory[i] == null)
				mInventory[i+59] = null;
			else
				mInventory[i+59] = mInventory[i].copy();
		}
	}
    
	public void loadNuclearReactor() {
		for (int i = 0; i < 54; i++) {
			if (mInventory[i+59] == null)
				mInventory[i] = null;
			else
				mInventory[i] = mInventory[i+59].copy();
		}
	}
    
    public void reset() {
    	mEU = 0;
    	mHeat = 0;
    	mEUOut = 0;
    	mMaxHeat = 10000;
        mHEM = 1.0F;
        mExplosionStrength = 0.0F;
        mProgress = 0;
        
        mInventory[113] = null;
        
		for (int i = 0; i < 54; i++) {
			mInventory[i   ] = null;
			mInventory[i+59] = null;
		}
		
		for (int i = 54; i < 58; i++) {
			if (mInventory[i] != null) {
				if (!worldObj.isRemote) worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, mInventory[i]));
				mInventory[i] = null;
			}
		}
    }
    
    public void switchModeForward() {
    	mMode = (mMode+1)%7;
    	switchMode();
    }
    
    public void switchModeBackward() {
    	mMode--;
    	if (mMode < 0) mMode = 6;
    	switchMode();
    }
    
    private void switchMode() {
    	reset();
    	if (mMode==1&&!GT_Mod.mReactorplanner) {
    		switchMode();
    		return;
    	}
        if (mMode==2&&!GT_Mod.mSeedscanner) {
        	switchMode();
        	return;
        }
        if (mMode==3) {
        	showCentrifugeRecipe(0);
        }
        if (mMode==4) {
        	showFusionRecipe(0);
        }
        if (mMode==5) {
        	showDescription(0);
        }
        if (mMode==6) {
        	showElectrolyzerRecipe(0);
        }
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 10, mMode);
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 11, mMaxHeat);
    }
    
    public void showDescription(int aIndex) {
    	mExplosionStrength = 0.0F;
    	if (aIndex >= GT_ComputercubeDescription.sDescriptions.size() || aIndex < 0) aIndex = 0;
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 0] == null) mInventory[59] = null; else  mInventory[59] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 0].copy();
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 1] == null) mInventory[60] = null; else  mInventory[60] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 1].copy();
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 2] == null) mInventory[61] = null; else  mInventory[61] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 2].copy();
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 3] == null) mInventory[62] = null; else  mInventory[62] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 3].copy();
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 4] == null) mInventory[63] = null; else  mInventory[63] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 4].copy();
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 5] == null) mInventory[64] = null; else {mInventory[64] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 5].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 6] == null) mInventory[65] = null; else {mInventory[65] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 6].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 7] == null) mInventory[66] = null; else {mInventory[66] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 7].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 8] == null) mInventory[67] = null; else {mInventory[67] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 8].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 9] == null) mInventory[68] = null; else {mInventory[68] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[ 9].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[10] == null) mInventory[69] = null; else {mInventory[69] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[10].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[11] == null) mInventory[70] = null; else {mInventory[70] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[11].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[12] == null) mInventory[71] = null; else {mInventory[71] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[12].copy(); mExplosionStrength = 100.0F;}
    	if (GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[13] == null) mInventory[72] = null; else {mInventory[72] = GT_ComputercubeDescription.sDescriptions.get(aIndex).mStacks[13].copy(); mExplosionStrength = 100.0F;}
    	mMaxHeat = aIndex;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 11, mMaxHeat);
    }
    
	public void switchDescriptionPageForward() {
		if (++mMaxHeat >= GT_ComputercubeDescription.sDescriptions.size()) mMaxHeat = 0;
		showDescription(mMaxHeat);
	}
	
	public void switchDescriptionPageBackward() {
		if (--mMaxHeat < 0) mMaxHeat = GT_ComputercubeDescription.sDescriptions.size() - 1;
		showDescription(mMaxHeat);
	}

    public void showCentrifugeRecipe(int aIndex) {
    	if (aIndex >= GT_Recipe.sCentrifugeRecipes.size() || aIndex < 0) aIndex = 0;
    	GT_Recipe tRecipe = GT_Recipe.sCentrifugeRecipes.get(aIndex);
    	if (tRecipe != null) {
    		if (tRecipe.mInput1  == null) mInventory[59] = null; else mInventory[59] = tRecipe.mInput1 .copy();
    		if (tRecipe.mInput2  == null) mInventory[60] = null; else mInventory[60] = tRecipe.mInput2 .copy();
    		if (tRecipe.mOutput1 == null) mInventory[61] = null; else mInventory[61] = tRecipe.mOutput1.copy();
    		if (tRecipe.mOutput2 == null) mInventory[62] = null; else mInventory[62] = tRecipe.mOutput2.copy();
    		if (tRecipe.mOutput3 == null) mInventory[63] = null; else mInventory[63] = tRecipe.mOutput3.copy();
    		if (tRecipe.mOutput4 == null) mInventory[64] = null; else mInventory[64] = tRecipe.mOutput4.copy();
    		mEU = tRecipe.mDuration * 5;
    		mMaxHeat = aIndex;
    	}
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 11, mMaxHeat);
    }
    
	public void switchCentrifugePageForward() {
		if (++mMaxHeat >= GT_Recipe.sCentrifugeRecipes.size()) mMaxHeat = 0;
		showCentrifugeRecipe(mMaxHeat);
	}
	
	public void switchCentrifugePageBackward() {
		if (--mMaxHeat < 0) mMaxHeat = GT_Recipe.sCentrifugeRecipes.size() - 1;
		showCentrifugeRecipe(mMaxHeat);
	}

    public void showElectrolyzerRecipe(int aIndex) {
    	if (aIndex >= GT_Recipe.sElectrolyzerRecipes.size() || aIndex < 0) aIndex = 0;
    	GT_Recipe tRecipe = GT_Recipe.sElectrolyzerRecipes.get(aIndex);
    	if (tRecipe != null) {
    		if (tRecipe.mInput1  == null) mInventory[59] = null; else mInventory[59] = tRecipe.mInput1 .copy();
    		if (tRecipe.mInput2  == null) mInventory[60] = null; else mInventory[60] = tRecipe.mInput2 .copy();
    		if (tRecipe.mOutput1 == null) mInventory[61] = null; else mInventory[61] = tRecipe.mOutput1.copy();
    		if (tRecipe.mOutput2 == null) mInventory[62] = null; else mInventory[62] = tRecipe.mOutput2.copy();
    		if (tRecipe.mOutput3 == null) mInventory[63] = null; else mInventory[63] = tRecipe.mOutput3.copy();
    		if (tRecipe.mOutput4 == null) mInventory[64] = null; else mInventory[64] = tRecipe.mOutput4.copy();
    		mEU = tRecipe.mDuration * tRecipe.mEUt;
    		mMaxHeat = aIndex;
    	}
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 11, mMaxHeat);
    }
    
	public void switchElectrolyzerPageForward() {
		if (++mMaxHeat >= GT_Recipe.sElectrolyzerRecipes.size()) mMaxHeat = 0;
		showElectrolyzerRecipe(mMaxHeat);
	}
	
	public void switchElectrolyzerPageBackward() {
		if (--mMaxHeat < 0) mMaxHeat = GT_Recipe.sElectrolyzerRecipes.size() - 1;
		showElectrolyzerRecipe(mMaxHeat);
	}
    
    public void showFusionRecipe(int aIndex) {
    	if (aIndex >= GT_Recipe.sFusionRecipes.size() || aIndex < 0) aIndex = 0;
    	GT_Recipe tRecipe = GT_Recipe.sFusionRecipes.get(aIndex);
    	if (tRecipe != null) {
    		if (tRecipe.mInput1  == null) mInventory[59] = null; else mInventory[59] = tRecipe.mInput1 .copy();
    		if (tRecipe.mInput2  == null) mInventory[60] = null; else mInventory[60] = tRecipe.mInput2 .copy();
    		if (tRecipe.mOutput1 == null) mInventory[61] = null; else mInventory[61] = tRecipe.mOutput1.copy();
    		mEU = tRecipe.mStartEU;
    		mEUOut = tRecipe.mEUt;
    		mHeat = tRecipe.mDuration;
    		mMaxHeat = aIndex;
    	}
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 11, mMaxHeat);
    }
    
	public void switchFusionPageForward() {
		if (++mMaxHeat >= GT_Recipe.sFusionRecipes.size()) mMaxHeat = 0;
		showFusionRecipe(mMaxHeat);
	}
	
	public void switchFusionPageBackward() {
		if (--mMaxHeat < 0) mMaxHeat = GT_Recipe.sFusionRecipes.size() - 1;
		showFusionRecipe(mMaxHeat);
	}
	
    public void switchNuclearReactor() {
    	if (mStarted)
    		stopNuclearReactor();
    	else
    		startNuclearReactor();
    }
    
    public void startNuclearReactor() {
    	mStarted = true;
    	mHeat = 0;
    	mEU = 0;
    }

    public void stopNuclearReactor() {
    	mStarted = false;
    }
    
    public void storeAdditionalData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mMode", mMode);
    	aNBT.setInteger("mProgress", mProgress);
    	aNBT.setBoolean("mStarted", mStarted);
    	aNBT.setInteger("mEU", mEU);
    	aNBT.setInteger("mHeat", mHeat);
    	aNBT.setInteger("mEUOut", mEUOut);
    	aNBT.setInteger("mMaxHeat", mMaxHeat);
    	aNBT.setFloat("mHEM", mHEM);
    	aNBT.setFloat("mExplosionStrength", mExplosionStrength);
    }

    public void getAdditionalData(NBTTagCompound aNBT) {
    	mMode = aNBT.getInteger("mMode");
    	mProgress = aNBT.getInteger("mProgress");
    	mStarted = aNBT.getBoolean("mStarted");
    	mEU = aNBT.getInteger("mEU");
    	mHeat = aNBT.getInteger("mHeat");
    	mEUOut = aNBT.getInteger("mEUOut");
    	mMaxHeat = aNBT.getInteger("mMaxHeat");
    	mHEM = aNBT.getFloat("mHEM");
    	mExplosionStrength = aNBT.getFloat("mExplosionStrength");
    }
	
    public void onFirstTickUpdate() {
    	if (sReactorList == null) {
    		sReactorList = new ArrayList<Item>();
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorUraniumSimple", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorUraniumDual", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorUraniumQuad", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorIsotopeCell", 1).getItem());

    		sReactorList.add(GregTech_API.sItemList[48]);
    		sReactorList.add(GregTech_API.sItemList[49]);
    		sReactorList.add(GregTech_API.sItemList[50]);
    		sReactorList.add(GregTech_API.sItemList[51]);
    		sReactorList.add(GregTech_API.sItemList[52]);
    		sReactorList.add(GregTech_API.sItemList[53]);
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorReflector", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorReflectorThick", 1).getItem());
    		sReactorList.add(GregTech_API.sItemList[40]);
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorCoolantSimple", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorCoolantTriple", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorCoolantSix", 1).getItem());
    		
    		sReactorList.add(GregTech_API.sItemList[34]);
    		sReactorList.add(GregTech_API.sItemList[35]);
    		sReactorList.add(GregTech_API.sItemList[36]);
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorCondensator", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorCondensatorLap", 1).getItem());
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorPlating", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorPlatingHeat", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorPlatingExplosive", 1).getItem());
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorVent", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorVentCore", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorVentGold", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorVentSpread", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorVentDiamond", 1).getItem());
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorHeatSwitch", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorHeatSwitchCore", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorHeatSwitchSpread", 1).getItem());
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorHeatSwitchDiamond", 1).getItem());
    		
    		sReactorList.add(GT_ModHandler.getIC2Item("reactorHeatpack", 1).getItem());
    		
    		for (int i = 0; i < Item.itemsList.length; i++) {
    			if (Item.itemsList[i] != null && Item.itemsList[i] instanceof IReactorComponent && !sReactorList.contains(Item.itemsList[i]) && !(Item.itemsList[i] instanceof GT_MetaItem_Cell)) {
    				sReactorList.add(Item.itemsList[i]);
    			}
    		}
    	}
    }
    
    public void onPostTickUpdate() {
    	if (!worldObj.isRemote) {
    		if (mMode == 2) {
    			if (mInventory[55] == null) {
    				mInventory[55] = mInventory[54];
    				mInventory[54] = null;
    			}
    			if (mInventory[57] == null) {
    				mInventory[57] = mInventory[56];
    				mInventory[56] = null;
    			}
    			if (GT_Mod.mSeedscanner && mInventory[55] != null && mInventory[55].itemID == GT_ModHandler.getIC2Item("cropSeed", 1).itemID && mInventory[55].getTagCompound() != null) {
    				if (mInventory[55].getTagCompound().getByte("scan") < 4) {
    					if (mProgress >= 100) {
    						mInventory[55].getTagCompound().setByte("scan", (byte)4);
    						mProgress = 0;
    					} else {
    						if (decreaseStoredEnergy(100, false)) {
    							mProgress++;
    						}
    					}
    				} else {
    					mProgress = 0;
    	    			if (mInventory[56] == null) {
    	    				mInventory[56] = mInventory[55];
    	    				mInventory[55] = null;
    	    			}
    				}
    			} else {
    				mProgress = 0;
    				if (mInventory[56] == null) {
	    				mInventory[56] = mInventory[55];
	    				mInventory[55] = null;
	    			}
    			}
    		}
    		if (mMode == 1 && GT_Mod.mReactorplanner && mStarted && decreaseStoredEnergy(32, false)) for (int i = 0; i < 25 && mStarted; i++) {
		        mEUOut = 0;
		        mMaxHeat = 10000;
		        mHEM = 1.0F;
		        mExplosionStrength = 10.0F;
		        float tMultiplier = 1.0F;
		        
		        for (int y = 0; y < 6; y++) {
		            for (int x = 0; x < 9; x++) {
		                ItemStack tStack = getStackInSlot(x + y * 9);
		                if (tStack != null) {
		                	if (tStack.getItem() instanceof IReactorComponent) {
			                    IReactorComponent tComponent = (IReactorComponent)tStack.getItem();
			                    
			                    tComponent.processChamber(this, tStack, x, y);
			                    
			                    float tInfluence = ((IReactorComponent)tStack.getItem()).influenceExplosion(this, tStack);
			                    
			                    if (tInfluence > 0.0F && tInfluence < 1.0F) {
			                    	tMultiplier *= tInfluence;
			                    } else {
			                    	mExplosionStrength += tInfluence;
			                    }
		                	} else {
		                		if (tStack.isItemEqual(GT_ModHandler.getIC2Item("nearDepletedUraniumCell", 1)) || tStack.isItemEqual(GT_ModHandler.getIC2Item("reEnrichedUraniumCell", 1))) {
		                			stopNuclearReactor();
		                		} else {
		                			setInventorySlotContents(x + y * 9, null);
		                		}
		                	}
		                }
		            }
		        }
		        
		        mEUOut *= IC2Reactor.getEUOutput();
		        
		        if (mEUOut == 0 && mEUTimer++ > 20 || mHeat >= mMaxHeat) stopNuclearReactor();
		        if (mEUOut != 0) mEUTimer = 0;
		        
		        mExplosionStrength *= mHEM * tMultiplier;
		        
		        mEU += mEUOut * 20;
		        
		        int tEU = mEULast1;
		        mEULast1 = mEULast2;
		        mEULast2 = mEULast3;
		        mEULast3 = mEULast4;
		        mEULast4 = mEUOut;
		        mEUOut = (mEUOut + mEULast1 + mEULast2 + mEULast3 + tEU) / 5;
    		}
    		if (mTickTimer%20==0) {
    			worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 10, mMode);
    			worldObj.addBlockEvent(xCoord, yCoord, zCoord, GregTech_API.sBlockList[1].blockID, 11, mMaxHeat);
    		}
    	}
    }

    @Override
    public boolean receiveClientEvent(int aEventID, int aValue) {
    	super.receiveClientEvent(aEventID, aValue);
    	if (worldObj.isRemote) {
	    	switch(aEventID) {
	    	case 10:
	    		mNeedsUpdate = true;
	    		mMode = aValue;
	    		break;
	    	case 11:
	    		mMaxHeat = aValue;
	    		break;
	    	}
    	}
    	return true;
    }
    
	@Override
	public boolean func_102007_a(int i, ItemStack itemstack, int j) {
		return mMode==2?i==54||i==55:false;
	}
	
	@Override
	public boolean func_102008_b(int i, ItemStack itemstack, int j) {
		return mMode==2?i==56||i==57:false;
	}
	
    @Override public String getInvName() {return GT_LanguageManager.mNameList1[4];}
    
    @Override
    public int getTexture(int aSide, int aMeta) {
    	switch (mMode) {
    	case  0: return  8;
    	case  1: return 46;
    	case  2: return 45;
    	default: return 48;
    	}
    }

	public ChunkCoordinates getPosition() {
		return new ChunkCoordinates(xCoord, yCoord, zCoord);
	}

	public World getWorld() {
		return worldObj;
	}

	public int getHeat() {
		return mHeat;
	}

	public void setHeat(int aHeat) {
		mHeat = aHeat;
	}

	public int addHeat(int aAmount) {
		mHeat += aAmount;
		return mHeat;
	}

	public int getMaxHeat() {
		return mMaxHeat;
	}

	public void setMaxHeat(int aMaxHeat) {
		mMaxHeat = aMaxHeat;
	}

	public float getHeatEffectModifier() {
		return mHEM;
	}

	public void setHeatEffectModifier(float aHEM) {
		mHEM = aHEM;
	}

	public int getOutput() {
		return mEUOut;
	}

	public int addOutput(int aEnergy) {
		mEUOut += aEnergy;
		return mEUOut;
	}
	
	public int getPulsePower() {
		return 1;
	}

	public ItemStack getItemAt(int x, int y) {
		if (x<0||x>8||y<0||y>5) return null;
		return getStackInSlot(x + y * 9);
	}

	public void setItemAt(int x, int y, ItemStack aStack) {
		setInventorySlotContents(x + y * 9, aStack);
	}

	public void explode() {
		stopNuclearReactor();
	}

	public int getTickRate() {
		return 1;
	}

	public boolean produceEnergy() {
		return true;
	}
	
	@Override
	public int getProgress() {
		return mProgress;
	}

	@Override
	public int getMaxProgress() {
		return mProgress>0?100:0;
	}
}
