package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import gregtechmod.api.util.GT_ModHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_Scrapboxinator extends GT_MetaTileEntity_ElectricBufferSmall {
	
	public GT_MetaTileEntity_Scrapboxinator(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_Scrapboxinator() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return false;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public int getMinimumStoredEU()						{return 2000;}
	@Override public int maxEUStore()								{return 10000;}
    @Override public int maxMJStore()								{return maxEUStore();}
	@Override public int getInvSize()								{return 3;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 111);}
	
	public void onPostTick() {
		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getEnergyStored() >= 500 && (getBaseMetaTileEntity().getTimer()%200 == 0 || (mSuccess > 0 && getBaseMetaTileEntity().getTimer()%5 == 0) || mSuccess >= 20)) {
			if (mInventory[0] == null && mInventory[1] != null && mInventory[1].stackSize > 0) {
				if (mInventory[1].isItemEqual(GT_ModHandler.getIC2Item("scrapBox", 1))) {
					mInventory[0] = GT_ModHandler.getRandomScrapboxDrop(getBaseMetaTileEntity().getWorld());
					getBaseMetaTileEntity().decrStackSize(1, 1);
					getBaseMetaTileEntity().decreaseStoredEnergyUnits(100, true);
					mSuccess = 30;
				} else if (mInventory[1].isItemEqual(GT_ModHandler.getIC2Item("scrap", 1))) {
					if (mInventory[1].stackSize > 8) {
						mInventory[0] = GT_ModHandler.getRandomScrapboxDrop(getBaseMetaTileEntity().getWorld());
						getBaseMetaTileEntity().decrStackSize(1, 9);
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(200, true);
						mSuccess = 30;
					}
				} else {
					mInventory[0] = mInventory[1];
					mInventory[1] = null;
				}
			}
		}
		super.onPostTick();
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex==0;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex==1;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_Scrapboxinator();
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (ForgeDirection.getOrientation(aSide).getOpposite() == ForgeDirection.getOrientation(aFacing))
			return 113+(aRedstone?8:0);
		return 119+(aRedstone?8:0);
	}
	
	@Override
	protected String getDescription() {
		return "Makes Scrapboxes and Scrap into random Stuff!";
	}
}
