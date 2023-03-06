package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Utility;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_ElectricAutoWorkbench extends MetaTileEntity {
	
	public int mMode = 0, mCurrentSlot = 0, mThroughPut = 0, mTicksUntilNextUpdate = 20;
	public boolean mLastCraftSuccessful = false;
	
	public GT_MetaTileEntity_ElectricAutoWorkbench(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_ElectricAutoWorkbench() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return false;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex < 19;}
	@Override public boolean isFacingValid(byte aFacing)			{return true;}
	@Override public boolean isEnetInput() 							{return true;}
	@Override public boolean isEnetOutput() 						{return true;}
	@Override public boolean isInputFacing(byte aSide)				{return !isOutputFacing(aSide);}
	@Override public boolean isOutputFacing(byte aSide)				{return aSide == getBaseMetaTileEntity().getBackFacing();}
	@Override public int getMinimumStoredEU()						{return 3000;}
    @Override public int maxEUInput()								{return 32;}
    @Override public int maxEUOutput()								{return mThroughPut%2==0?32:0;}
    @Override public int maxEUStore()								{return 10000;}
    @Override public int maxMJStore()								{return maxEUStore();}
    @Override public int maxSteamStore()							{return maxEUStore();}
	@Override public int getInvSize()								{return 30;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 100);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_ElectricAutoWorkbench();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
    	aNBT.setInteger("mMode", mMode);
    	aNBT.setInteger("mThroughPut", mThroughPut);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		mMode = aNBT.getInteger("mMode");
		mThroughPut = aNBT.getInteger("mThroughPut");
	}
	
	public void switchModeForward() {
		mMode = (mMode + 1) % 8;
		switchMode();
	}

	public void switchModeBackward() {
		mMode--;
		if (mMode < 0) mMode = 7;
		switchMode();
	}

	private void switchMode() {
		mInventory[28] = null;
	}
	
	public void switchThrough() {
		mThroughPut = (mThroughPut + 1) % 4;
	}

	@Override
	protected String getDescription() {
		return "Automatic Crafting Table Mk III";
	}
	
	public void onPostTick() {
		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getEnergyStored() >= (mMode==5||mMode==6?128:2048) && (getBaseMetaTileEntity().hasWorkJustBeenEnabled() || --mTicksUntilNextUpdate<1)) {
			mTicksUntilNextUpdate = 32;
			if (mInventory[18] == null) {
				ItemStack[] tRecipe = new ItemStack[9];
				ItemStack tTempStack = null, tOutput = null;
				
				if (mInventory[8] != null && mThroughPut < 2 && mMode != 0) {
					if (mInventory[18] == null) {
						mInventory[18] = mInventory[8];
						mInventory[8] = null;
					}
				} else {
					if (!mLastCraftSuccessful) {
						mCurrentSlot = (mCurrentSlot+1)%18;
						for (int i = 0; i < 17 && mInventory[mCurrentSlot] == null; i++)
							mCurrentSlot = (mCurrentSlot+1)%18;
					}
					switch (mMode) {
					case 0:
						if (mInventory[mCurrentSlot] != null && !isItemTypeExcluded(mInventory[mCurrentSlot])) {
							if (mInventory[18] == null && mThroughPut < 2 && mCurrentSlot < 8) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						for (int i = 0; i < 9; i++) {
							tRecipe[i] = mInventory[i+19];
							if (tRecipe[i] != null) {
								tRecipe[i] = tRecipe[i].copy();
								tRecipe[i].stackSize = 1;
							}
						}
						break;
					case 1:
						if (isItemTypeExcluded(mInventory[mCurrentSlot])) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						tRecipe[0] = tTempStack;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							tRecipe[1] = tTempStack;
							tRecipe[3] = tTempStack;
							tRecipe[4] = tTempStack;
						} else break;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							tRecipe[2] = tTempStack;
							tRecipe[5] = tTempStack;
							tRecipe[6] = tTempStack;
							tRecipe[7] = tTempStack;
							tRecipe[8] = tTempStack;
						} else break;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							if (mInventory[18] == null) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						break;
					case 2:
						if (isItemTypeExcluded(mInventory[mCurrentSlot])) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						tRecipe[0] = tTempStack;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							if (mInventory[18] == null) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						break;
					case 3:
						if (isItemTypeExcluded(mInventory[mCurrentSlot])) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						tRecipe[0] = tTempStack;
						tRecipe[1] = tTempStack;
						tRecipe[3] = tTempStack;
						tRecipe[4] = tTempStack;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							if (mInventory[18] == null) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						break;
					case 4:
						if (isItemTypeExcluded(mInventory[mCurrentSlot])) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						tRecipe[0] = tTempStack;
						tRecipe[1] = tTempStack;
						tRecipe[2] = tTempStack;
						tRecipe[3] = tTempStack;
						tRecipe[4] = tTempStack;
						tRecipe[5] = tTempStack;
						tRecipe[6] = tTempStack;
						tRecipe[7] = tTempStack;
						tRecipe[8] = tTempStack;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							if (mInventory[18] == null) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						break;
					case 5:
						if (isItemTypeExcluded(mInventory[mCurrentSlot])) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						tRecipe[0] = tTempStack;
						
						tOutput = GT_OreDictUnificator.get(tTempStack);
						
						if (tOutput != null && tOutput.isItemEqual(tTempStack)) tOutput = null;
						
						if (tOutput == null) {
							tRecipe[0] = null;
							if (mInventory[18] == null) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
						}
						break;
					case 6:
						if (isItemTypeExcluded(mInventory[mCurrentSlot]) || !GT_OreDictUnificator.isItemStackInstanceOf(mInventory[mCurrentSlot], "dustSmall", true)) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						tRecipe[0] = tTempStack;
						tRecipe[1] = tTempStack;
						tRecipe[3] = tTempStack;
						tRecipe[4] = tTempStack;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							if (mInventory[18] == null) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						break;
					case 7:
						if (isItemTypeExcluded(mInventory[mCurrentSlot]) || !GT_OreDictUnificator.isItemStackInstanceOf(mInventory[mCurrentSlot], "nugget", true)) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						tRecipe[0] = tTempStack;
						tRecipe[1] = tTempStack;
						tRecipe[2] = tTempStack;
						tRecipe[3] = tTempStack;
						tRecipe[4] = tTempStack;
						tRecipe[5] = tTempStack;
						tRecipe[6] = tTempStack;
						tRecipe[7] = tTempStack;
						tRecipe[8] = tTempStack;
						if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
							if (mInventory[18] == null) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						break;
					case 8:
						if (isItemTypeExcluded(mInventory[mCurrentSlot]) || mInventory[mCurrentSlot].getItemDamage() <= 0 || !mInventory[mCurrentSlot].getItem().isRepairable()) {
							if (mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[mCurrentSlot];
								mInventory[mCurrentSlot] = null;
								mTicksUntilNextUpdate = 1;
							}
							break;
						}
						tTempStack = mInventory[mCurrentSlot].copy();
						tTempStack.stackSize = 1;
						for (int i = mCurrentSlot + 1; i < 18; i++) {
							if (mInventory[i] != null && mInventory[i].isItemEqual(tTempStack) && mInventory[mCurrentSlot].getItemDamage() > 0) {
								tRecipe[0] = tTempStack;
								tRecipe[1] = mInventory[i].copy();
								if (GT_ModHandler.getRecipeOutput(tRecipe) == null) {
									if (mInventory[18] == null) {
										mInventory[18] = mInventory[mCurrentSlot];
										mInventory[mCurrentSlot] = null;
										mTicksUntilNextUpdate = 1;
									}
									break;
								}
							}
						}
						break;
					}
				}
				
				if (tOutput == null) tOutput = GT_ModHandler.getRecipeOutput(tRecipe);
				
				if (tOutput != null || mMode == 0) mInventory[28] = tOutput;
				
				if (tOutput == null) {
					mLastCraftSuccessful = false;
				} else {
					if ((tTempStack = GT_OreDictUnificator.get(tOutput)) != null) {
						tTempStack.stackSize = tOutput.stackSize;
						tOutput = tTempStack;
					}
					
					mInventory[28] = tOutput.copy();
					ArrayList<ItemStack> tList = recipeContent(tRecipe), tContent = benchContent();
					if (tList.size() > 0 && tContent.size() > 0) {
						
						boolean success = true;
						for (int i = 0; i < tList.size() && success; i++) {
							success = false;
							for (int j = 0; j < tContent.size() && !success; j++) {
								if (tList.get(i).itemID == tContent.get(j).itemID) {
									if (tList.get(i).getItemDamage() == tContent.get(j).getItemDamage()) {
										if (tList.get(i).stackSize <= tContent.get(j).stackSize) {
											success = true;
										}
									}
								}
							}
						}
						
						if (success) {
							mLastCraftSuccessful = true;
							
							int tCellCount = -GT_ModHandler.getCapsuleCellContainerCount(tOutput)*tOutput.stackSize;
							
							for (int i = 8; i > -1; i--) {
								for (int j = 17; j > -1; j--) {
									if (tRecipe[i] != null && mInventory[j] != null) {
										if (tRecipe[i].itemID == mInventory[j].itemID) {
											if (tRecipe[i].getItemDamage() == mInventory[j].getItemDamage()) {
												if (mInventory[j].getItem().hasContainerItem()) {
													ItemStack tStack = mInventory[j].getItem().getContainerItemStack(mInventory[j]);
													if (tStack != null) {
														for (int k = 9; k < 18; k++) {
															if (mInventory[k] == null) {
																mInventory[k] = tStack.copy();
																break;
															} else if (mInventory[k].isItemEqual(tStack) && mInventory[k].stackSize + tStack.stackSize <= tStack.getMaxStackSize()) {
																mInventory[k].stackSize += tStack.stackSize;
																break;
															}
														}
													}
												} else if (GT_ModHandler.getCapsuleCellContainerCount(mInventory[j]) != 0) {
													tCellCount += GT_ModHandler.getCapsuleCellContainerCount(mInventory[j]);
												}
												getBaseMetaTileEntity().decrStackSize(j, 1);
												break;
											}
										}
									}
								}
							}
							
							for (int k = 9; k < 18 && tCellCount > 0; k++) {
								if (mInventory[k] == null || mInventory[k].isItemEqual(GT_ModHandler.getIC2Item("cell", 1))) {
									if (mInventory[k] == null) {
										mInventory[k] = GT_ModHandler.getIC2Item("cell", 1);
										tCellCount--;
									}
									while (mInventory[k].stackSize < GT_ModHandler.getIC2Item("cell", 1).getItem().getItemStackLimit() && tCellCount-- > 0) mInventory[k].stackSize++;
								}
							}
							
							mInventory[18] = tOutput.copy();
							getBaseMetaTileEntity().decreaseStoredEnergyUnits(mMode==5||mMode==6||mMode==7?128:2048, true);
							mTicksUntilNextUpdate = 1;
						} else {
							mLastCraftSuccessful = false;
							if (mInventory[8] != null && mInventory[18] == null && mThroughPut < 2) {
								mInventory[18] = mInventory[8];
								mInventory[8] = null;
								mTicksUntilNextUpdate = 1;
							}
						}
					}
					
					if (mInventory[18] == null && mThroughPut < 2) {
						ItemStack tStack = null;
						for (int i = 0; i < 8; i++) {
							for (int j = i; ++j < 9;) {
								if (mInventory[i] != null && mInventory[j] != null && mInventory[i].isItemEqual(mInventory[j]) && mInventory[i].getMaxStackSize() > 8) {
									mInventory[18] = mInventory[j];
									mInventory[j] = null;
									mTicksUntilNextUpdate = 1;
									break;
								}
							}
						}
					}
				}
			}
			
			if (mThroughPut < 2) {
				TileEntity tTileEntity1, tTileEntity2;
				
				
				tTileEntity2 = getBaseMetaTileEntity().getTileEntityAtSide(getBaseMetaTileEntity().getBackFacing());
				
				if (tTileEntity2 != null) {
					if (tTileEntity2 instanceof IInventory) {
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(GT_Utility.moveOneItemStack(getBaseMetaTileEntity(), (IInventory)tTileEntity2, getBaseMetaTileEntity().getBackFacing(), getBaseMetaTileEntity().getFrontFacing(), null, false, (byte)64, (byte)1, (byte)64, (byte)1)*10, true);
					}
				}
			}
		}
	}
	
	private boolean isItemTypeExcluded(ItemStack aStack) {
		if (aStack == null) return true;
		for (int i = 19; i < 28; i++) if (mInventory[i] != null && mInventory[i].isItemEqual(aStack)) return true;
		return false;
	}
	
	private ArrayList<ItemStack> recipeContent(ItemStack[] tRecipe) {
		ArrayList<ItemStack> tList = new ArrayList<ItemStack>();
		for (int i = 0; i < 9; i++) {
			if (tRecipe[i] != null) {
				boolean temp = false;
				for (int j = 0; j < tList.size(); j++) {
					if (tRecipe[i].itemID == tList.get(j).itemID) {
						if (tRecipe[i].getItemDamage() == tList.get(j).getItemDamage()) {
							tList.get(j).stackSize++;
							temp = true;
							break;
						}
					}
				}
				if (!temp) tList.add(tRecipe[i].copy());
			}
		}
		return tList;
	}

	private ArrayList<ItemStack> benchContent() {
		ArrayList<ItemStack> tList = new ArrayList<ItemStack>();
		for (int i = 0; i < 18; i++) {
			if (mInventory[i] != null) {
				boolean temp = false;
				for (int j = 0; j < tList.size(); j++) {
					if (mInventory[i].itemID == tList.get(j).itemID) {
						if (mInventory[i].getItemDamage() == tList.get(j).getItemDamage()) {
							tList.get(j).stackSize += mInventory[i].stackSize;
							temp = true;
							break;
						}
					}
				}
				if (!temp) tList.add(mInventory[i].copy());
			}
		}
		return tList;
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return mMode==0?aIndex>=9:aIndex>=18;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return mMode==0?aIndex<9:aIndex<18;
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing)
			return 112;
		if (ForgeDirection.getOrientation(aSide).getOpposite() == ForgeDirection.getOrientation(aFacing))
			return 113;
		return 114;
	}
}
