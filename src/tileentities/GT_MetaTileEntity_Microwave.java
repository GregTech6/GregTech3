package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class GT_MetaTileEntity_Microwave extends GT_MetaTileEntity_BasicMachine {
	
	public GT_MetaTileEntity_Microwave(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_Microwave() {
		
	}
	
	@Override
	public void onRightclick(EntityPlayer aPlayer) {
		getBaseMetaTileEntity().openGUI(aPlayer, 148);
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_Microwave();
	}
	
	@Override
    public void checkRecipe() {
		GT_Utility.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), 1, 2, (byte)64, (byte)1, (byte)64, (byte)1);
		GT_Utility.moveStackFromSlotAToSlotB(getBaseMetaTileEntity(), getBaseMetaTileEntity(), 3, 4, (byte)64, (byte)1, (byte)64, (byte)1);
		if (mInventory[2] != null) {
			ItemStack tOutput = GT_ModHandler.getSmeltingOutput(mInventory[2]);
			if (GT_OreDictUnificator.isItemStackInstanceOf(mInventory[2], "ingot", true)
			||  GT_OreDictUnificator.isItemStackInstanceOf(tOutput		, "ingot", true)
			||  GT_OreDictUnificator.isItemStackInstanceOf(mInventory[2], "dustGunpowder", false)
			||  GT_OreDictUnificator.isItemStackInstanceOf(tOutput		, "dustGunpowder", false)
			||  mInventory[2].itemID == Item.egg.itemID) {
	    		mInventory[2] = null;
				getBaseMetaTileEntity().doExplosion(128);
				return;
			}
			if (TileEntityFurnace.getItemBurnTime(mInventory[2]) > 0 || TileEntityFurnace.getItemBurnTime(tOutput) > 0) {
	    		mInventory[2] = null;
				getBaseMetaTileEntity().setOnFire();
				return;
			}
			if (tOutput != null) {
				mOutputItem1 = tOutput;
				mEUt = 4;
	    		mMaxProgresstime = 25;
	    		mInventory[2].stackSize--;
			}
		}
    }
	
	@Override
	public int getFrontFacingInactive() {
		return 1;
	}
	
	@Override
	public int getFrontFacingActive() {
		return 5;
	}
	
	@Override
	protected String getDescription() {
		return "Did you really read the instruction Manual?";
	}
}
