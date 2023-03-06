package gregtechmod.common.tileentities;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_ModHandler;

import java.util.ArrayList;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.common.AuraManager;
import thaumcraft.common.entities.EntityWisp;

public class GT_MetaTileEntity_MagicEnergyAbsorber extends MetaTileEntity {
	
	public static int sEnergyPerEnderCrystal = 32, sEnergyFromVis = 12800;
	
	public static ArrayList<EntityEnderCrystal> sUsedDragonCrystalList;
	
	public EntityEnderCrystal mTargetedCrystal;
	
	public boolean isActive1 = false, isActive2 = false;
	
	public GT_MetaTileEntity_MagicEnergyAbsorber(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_MagicEnergyAbsorber() {
		
	}
	
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public boolean isValidSlot(int aIndex)				{return aIndex < 2;}
	@Override public boolean isFacingValid(byte aFacing)			{return false;}
	@Override public boolean isEnetOutput() 						{return true;}
	@Override public boolean isOutputFacing(byte aSide)				{return true;}
	@Override public int maxEUOutput()								{return Math.max(128, sEnergyPerEnderCrystal);}
	@Override public int getInvSize()								{return 3;}
	@Override public int maxEUStore()								{return Math.max(10000, Math.max(sEnergyFromVis, sEnergyPerEnderCrystal));}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 126);}
	@Override public boolean isAccessAllowed(EntityPlayer aPlayer)	{return true;}
    
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_MagicEnergyAbsorber();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		aNBT.setBoolean("isActive1", isActive1);
		aNBT.setBoolean("isActive2", isActive2);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		isActive1 = aNBT.getBoolean("isActive1");
		isActive2 = aNBT.getBoolean("isActive2");
	}
	
    @Override
    public void onPostTick() {
    	if (getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getTimer()%10==0) {
    		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().getEnergyStored() < maxEUOutput()) {
	    		try {
		    		if (mInventory[0] != null && mInventory[0].isItemEnchanted() && GT_ModHandler.isElectricItem(mInventory[0])) {
		                NBTTagList tEnchantments = mInventory[0].getEnchantmentTagList();
		                if (tEnchantments != null) {
		                    for (int i = 0; i < tEnchantments.tagCount(); i++) {
		                        short tID = ((NBTTagCompound)tEnchantments.tagAt(i)).getShort("id");
		                        short tLevel = ((NBTTagCompound)tEnchantments.tagAt(i)).getShort("lvl");
		                        if (tID > -1 && tID < Enchantment.enchantmentsList.length) {
			                        Enchantment tEnchantment = Enchantment.enchantmentsList[tID];
			                        if (tEnchantment != null) {
			                        	float tPercentage = (float)tLevel/(float)tEnchantment.getMaxLevel();
			                        	int tValue = 100000/tEnchantment.getWeight();
			                        	getBaseMetaTileEntity().increaseStoredEnergyUnits(((int)(tValue*tPercentage))*(GregTech_API.sConfiguration.addAdvConfig("disabledrecipes", "enchantmenttable", false)?1:100), true);
			                        	//tEnchantments.removeTag(i--);
			                        }
		                        }
		                    }
		                    mInventory[0].stackTagCompound.removeTag("ench");
		                }
		    		}
		    		
		            if (mInventory[0] != null && mInventory[1] == null) {
		            	if (mInventory[0].getItem() instanceof ItemEnchantedBook)
		            		mInventory[0] = new ItemStack(Item.book, 1, mInventory[0].stackSize);
		            	mInventory[1] = mInventory[0];
		            	mInventory[0] = null;
		            }
    			} catch(Throwable e) {e.printStackTrace(GT_Log.err);}
    		}
    		
    		if (sEnergyPerEnderCrystal > 0 && isActive1) {
    			if (sUsedDragonCrystalList == null)
    				sUsedDragonCrystalList = new ArrayList<EntityEnderCrystal>();
    			
    			if (mTargetedCrystal == null) {
	    			ArrayList<EntityEnderCrystal> tList = (ArrayList<EntityEnderCrystal>)getBaseMetaTileEntity().getWorld().getEntitiesWithinAABB(EntityEnderCrystal.class, AxisAlignedBB.getBoundingBox(getBaseMetaTileEntity().getXCoord()-64, getBaseMetaTileEntity().getYCoord()-64, getBaseMetaTileEntity().getZCoord()-64, getBaseMetaTileEntity().getXCoord()+64, getBaseMetaTileEntity().getYCoord()+64, getBaseMetaTileEntity().getZCoord()+64));
	    			if (tList != null && !tList.isEmpty()) {
	    				tList.removeAll(sUsedDragonCrystalList);
	    				if (tList.size() > 0) {
	    					mTargetedCrystal = tList.get(0);
	    					if (mTargetedCrystal != null) {
		    					sUsedDragonCrystalList.add(mTargetedCrystal);
	    					}
	    				}
	    			}
    			} else {
    				if (mTargetedCrystal.isEntityAlive()) {
    					getBaseMetaTileEntity().increaseStoredEnergyUnits(sEnergyPerEnderCrystal*10, false);
    				} else {
    					sUsedDragonCrystalList.remove(mTargetedCrystal);
    					mTargetedCrystal = null;
    				}
    			}
    		}
    		
    		if (sEnergyFromVis > 0 && isActive2 && getBaseMetaTileEntity().getEnergyStored() < sEnergyFromVis) {
    			try {
    				if (AuraManager.decreaseClosestAura(getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), 1)) {
    					getBaseMetaTileEntity().increaseStoredEnergyUnits(sEnergyFromVis, true);
    					ObjectTags tTags = new ObjectTags();
    					tTags.add(EnumTag.MECHANISM, 1 + getBaseMetaTileEntity().getRandomNumber(3));
    					tTags.add(EnumTag.VOID, 1 + getBaseMetaTileEntity().getRandomNumber(2));
    					tTags.add(EnumTag.FLUX, 1 + getBaseMetaTileEntity().getRandomNumber(2));
    					AuraManager.addFluxToClosest(getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), tTags);
    					ArrayList<EntityWisp> tList = (ArrayList<EntityWisp>)getBaseMetaTileEntity().getWorld().getEntitiesWithinAABB(EntityWisp.class, AxisAlignedBB.getBoundingBox(getBaseMetaTileEntity().getXCoord()-8, getBaseMetaTileEntity().getYCoord()-8, getBaseMetaTileEntity().getZCoord()-8, getBaseMetaTileEntity().getXCoord()+8, getBaseMetaTileEntity().getYCoord()+8, getBaseMetaTileEntity().getZCoord()+8));
    	    			if (!tList.isEmpty()) getBaseMetaTileEntity().doExplosion(2048);
    				}
    			} catch(Throwable e) {}
    		}
    		
			getBaseMetaTileEntity().setActive(getBaseMetaTileEntity().getEnergyStored() >= maxEUOutput());
    	}
    }
    
    @Override
	public void onExplosion() {
    	if (sEnergyFromVis > 0 && isActive2) {
	    	try {
				ObjectTags tTags = new ObjectTags();
				tTags.add(EnumTag.MECHANISM, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				tTags.add(EnumTag.DESTRUCTION, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				tTags.add(EnumTag.FLUX, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				tTags.add(EnumTag.EVIL, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				tTags.add(EnumTag.FIRE, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				tTags.add(EnumTag.DARK, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				tTags.add(EnumTag.POWER, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				tTags.add(EnumTag.VOID, 50 + getBaseMetaTileEntity().getRandomNumber(50));
				AuraManager.addFluxToClosest(getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getYCoord(), getBaseMetaTileEntity().getZCoord(), tTags);
			} catch(Throwable e) {}
    	}
    }
    
    @Override
    public void inValidate() {
    	if (mTargetedCrystal != null && sUsedDragonCrystalList != null) sUsedDragonCrystalList.remove(mTargetedCrystal);
    }
    
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
    	return aActive?91:92;
	}
	
	@Override
	protected String getDescription() {
		return "Absorbs Magic Energy and disenchants Items";
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex==1;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex==0;
	}
}
