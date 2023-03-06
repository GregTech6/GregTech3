package gregtechmod.common.items;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.api.util.GT_Log;
import gregtechmod.common.blocks.GT_BlockMetaID_Block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class GT_MetaMachine_Item extends ItemBlock {
	public static int mItemID = 0;
	
	public String[] mString0 = new String[GregTech_API.MAXIMUM_METATILE_IDS],
					mString1 = new String[GregTech_API.MAXIMUM_METATILE_IDS],
					mString2 = new String[GregTech_API.MAXIMUM_METATILE_IDS],
					mString3 = new String[GregTech_API.MAXIMUM_METATILE_IDS],
					mString4 = new String[GregTech_API.MAXIMUM_METATILE_IDS],
					mString5 = new String[GregTech_API.MAXIMUM_METATILE_IDS],
					mString6 = new String[GregTech_API.MAXIMUM_METATILE_IDS];
	
    public GT_MetaMachine_Item(int par1) {
        super(par1);
        mItemID = par1;
        setMaxDamage(0);
        setHasSubtypes(true);
        setUnlocalizedName(GT_LanguageManager.mNameList1[0]);
		setCreativeTab(GregTech_API.TAB_GREGTECH);
    }
    
	@Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean par4) {
		try {
			int tDamage = aStack.getItemDamage();
			
			if (tDamage < 0 || tDamage >= GregTech_API.MAXIMUM_METATILE_IDS) return;
			
			if (mString1[tDamage] == null) {
				mString0[tDamage] = "";
				mString1[tDamage] = "";
				mString2[tDamage] = "";
				mString3[tDamage] = "";
				mString4[tDamage] = "";
				mString5[tDamage] = "";
				mString6[tDamage] = "";
				if (tDamage == 0) {
					mString0[tDamage] = "If you got this from a Wrench, which is NOT directly belonging to IC2";
					mString1[tDamage] = "then please report it at the corresponding Wrench-Mod, and NOT to GregTech.";
					mString2[tDamage] = "My Machines implement the IWrenchable Interface properly, as you can see using the IC2-Wrench.";
				} else {
					TileEntity temp = GregTech_API.sBlockList[1].createTileEntity(aPlayer.worldObj, tDamage>15?0:tDamage);
					if (temp != null) {
						temp.worldObj = aPlayer.worldObj; temp.xCoord = 0; temp.yCoord = 0; temp.zCoord = 0;
						if (temp instanceof IGregTechTileEntity) {
							IGregTechTileEntity tTileEntity = (IGregTechTileEntity)temp;
							tTileEntity.setInitialValuesAsNBT(new NBTTagCompound(), tDamage);
							if (tTileEntity.getDescription()    != null) mString0[tDamage] = tTileEntity.getDescription();
							if (tTileEntity.getInputVoltage()		> 0) mString1[tDamage] = "Max EU/p IN: "  + tTileEntity.getInputVoltage();					else mString1[tDamage] = "";
							if (tTileEntity.getOutputVoltage()		> 0) mString2[tDamage] = "Max EU/p OUT: " + tTileEntity.getOutputVoltage();					else mString2[tDamage] = "";
							if (tTileEntity.getOutputAmperage()		> 1) mString3[tDamage] = "Amount of Output Packets: " + tTileEntity.getOutputAmperage();	else mString3[tDamage] = "";
							if (tTileEntity.getEnergyCapacity()	> 10000) mString4[tDamage] = "EU Storage: "   + tTileEntity.getEnergyCapacity();				else mString4[tDamage] = "";
							mString5[tDamage] = (tTileEntity.isOverclockerUpgradable()?"O ":"") + (tTileEntity.isTransformerUpgradable()?"T ":"") + (tTileEntity.isBatteryUpgradable(0, (byte)0)?"B ":"") + (tTileEntity.isMJConverterUpgradable()?"M ":"");
							if (!mString5[tDamage].equals("")) mString5[tDamage] = "Possible Upgrades: " + mString5[tDamage];
							mString6[tDamage] = "";
						}
					}
				}
			}
			
			if (!mString0[tDamage].equals("")) aList.add(mString0[tDamage]);
			if (!mString1[tDamage].equals("")) aList.add(mString1[tDamage]);
			if (!mString2[tDamage].equals("")) aList.add(mString2[tDamage]);
			if (!mString3[tDamage].equals("")) aList.add(mString3[tDamage]);
			if (!mString4[tDamage].equals("")) aList.add(mString4[tDamage]);
			if (!mString5[tDamage].equals("")) aList.add(mString5[tDamage]);
			if (!mString6[tDamage].equals("")) aList.add(mString6[tDamage]);
			
			NBTTagCompound aNBT = aStack.getTagCompound();
			
			if (aNBT != null) {
				if (aNBT.getBoolean("mMJConverter")) aList.add("has MJ-Converter");
				if (aNBT.getBoolean("mSteamConverter")) aList.add("has Steam Upgrade");
				int tAmount = 0;
				if ((tAmount = aNBT.getByte("mOverclockers")) 		> 0) aList.add(tAmount + " Overclocker Upgrades");
				if ((tAmount = aNBT.getByte("mTransformers")) 		> 0) aList.add(tAmount + " Transformer Upgrades");
				if ((tAmount = aNBT.getByte("mRSEnergyCells"))		> 0) aList.add(tAmount + " Energy Cell Upgrades");
				if ((tAmount = aNBT.getByte("mSteamTanks"))			> 0) aList.add(tAmount + " Steam Tank Upgrades");
				if ((tAmount = aNBT.getInteger("mUpgradedStorage"))	> 0) aList.add(tAmount + " Additional EU-Storage");
			}
		} catch(Throwable e) {
			e.printStackTrace(GT_Log.err);
		}
    }
	
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (stack.getItemDamage() == 7)
    		if (0 < GT_BlockMetaID_Block.stepToFindOrCallLESUController(world, x, y, z, new ArrayList<ChunkPosition>(), false))
    			return true;
        return false;
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
		int tDamage = aStack.getItemDamage();
		if (tDamage < 0 || tDamage >= GregTech_API.MAXIMUM_METATILE_IDS) return "";
    	if (tDamage < 16) return getUnlocalizedName() + "." + GT_LanguageManager.mNameList1[tDamage];
    	
    	if (GregTech_API.mMetaTileList[tDamage] != null) {
    		return getUnlocalizedName() + "." + GregTech_API.mMetaTileList[tDamage].mName;
    	}
    	
    	return "";
    }
    
    @Override
    public boolean placeBlockAt(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta) {
       if (aStack.getItemDamage()>15) {
    	   if (!aWorld.setBlock(aX, aY, aZ, getBlockID(), 0, 3)) {
    		   return false;
    	   }
    	   IGregTechTileEntity tTileEntity = (IGregTechTileEntity)aWorld.getBlockTileEntity(aX, aY, aZ);
    	   if (tTileEntity != null) {
    		   if (aStack.getTagCompound() != null) {
        		   tTileEntity.setInitialValuesAsNBT(aStack.getTagCompound(), aStack.getItemDamage());
    		   } else {
        		   tTileEntity.setInitialValuesAsNBT(null, aStack.getItemDamage());
    		   }
    	   }
       } else {
    	   if (!aWorld.setBlock(aX, aY, aZ, getBlockID(), aStack.getItemDamage(), 3)) {
               return false;
           }
       }
       
       if (aWorld.getBlockId(aX, aY, aZ) == getBlockID()) {
           Block.blocksList[getBlockID()].onBlockPlacedBy(aWorld, aX, aY, aZ, aPlayer, aStack);
           Block.blocksList[getBlockID()].onPostBlockPlaced(aWorld, aX, aY, aZ, aStack.getItemDamage());
       }
       return true;
    }
}
