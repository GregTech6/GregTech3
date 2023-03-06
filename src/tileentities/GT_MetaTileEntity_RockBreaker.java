package gregtechmod.common.tileentities;

import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.MetaTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class GT_MetaTileEntity_RockBreaker extends GT_MetaTileEntity_ElectricBufferSmall {
	
	public GT_MetaTileEntity_RockBreaker(int aID, String mName, String mNameRegional) {
		super(aID, mName, mNameRegional);
	}
	
	public GT_MetaTileEntity_RockBreaker() {
		
	}
	
	@Override public boolean isTransformerUpgradable()				{return true;}
	@Override public boolean isOverclockerUpgradable()				{return true;}
	@Override public boolean isBatteryUpgradable()					{return true;}
	@Override public boolean isSimpleMachine()						{return false;}
	@Override public int getMinimumStoredEU()						{return 1000+(int)Math.pow(4, getBaseMetaTileEntity().getOverclockerUpgradeCount())*100;}
	@Override public int maxEUStore()								{return 10000;}
    @Override public int maxMJStore()								{return maxEUStore();}
	@Override public int getInvSize()								{return 3;}
	@Override public void onRightclick(EntityPlayer aPlayer)		{getBaseMetaTileEntity().openGUI(aPlayer, 106);}
	
	public void onPostTick() {
		if (getBaseMetaTileEntity().isAllowedToWork() && getBaseMetaTileEntity().isServerSide() && getBaseMetaTileEntity().getEnergyStored() >= 1000 && (getBaseMetaTileEntity().getTimer()%(40/(int)Math.pow(2, getBaseMetaTileEntity().getOverclockerUpgradeCount()))) == 0) {
			ItemStack tOutput = new ItemStack(Block.cobblestone, 1);
			
			boolean tWater = true;
			
			if (getBaseMetaTileEntity().getBlockIDOffset(0, 0, +1) != 9)
			if (getBaseMetaTileEntity().getBlockIDOffset(0, 0, -1) != 9)
			if (getBaseMetaTileEntity().getBlockIDOffset(-1, 0, 0) != 9)
			if (getBaseMetaTileEntity().getBlockIDOffset(+1, 0, 0) != 9)
				tWater = false;
			
			if (getBaseMetaTileEntity().getBlockIDOffset(0, +1, 0) == 11)
				tOutput = new ItemStack(Block.stone, 1);
			else
				if (getBaseMetaTileEntity().getBlockIDOffset(0, 0, +1) != 11)
				if (getBaseMetaTileEntity().getBlockIDOffset(0, 0, -1) != 11)
				if (getBaseMetaTileEntity().getBlockIDOffset(-1, 0, 0) != 11)
				if (getBaseMetaTileEntity().getBlockIDOffset(+1, 0, 0) != 11)
					tOutput = null;
			
			if (tOutput != null && tWater) {
				if (mInventory[0] == null) {
					if (mInventory[1] != null && mInventory[1].getItem() == Item.redstone) {
						mInventory[0] = new ItemStack(Block.obsidian, 1);
						getBaseMetaTileEntity().decrStackSize(1, 1);
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(500*(int)Math.pow(4, getBaseMetaTileEntity().getOverclockerUpgradeCount()), true);
					} else {
						mInventory[0] = tOutput;
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(100*(int)Math.pow(4, getBaseMetaTileEntity().getOverclockerUpgradeCount()), true);
					}
				} else {
					if (mInventory[1] != null && mInventory[1].getItem() == Item.redstone && mInventory[0].itemID == Block.obsidian.blockID && mInventory[0].stackSize < 64) {
						mInventory[0].stackSize++;
						getBaseMetaTileEntity().decrStackSize(1, 1);
						getBaseMetaTileEntity().decreaseStoredEnergyUnits(500*(int)Math.pow(4, getBaseMetaTileEntity().getOverclockerUpgradeCount()), true);
					} else {
						if (mInventory[0].isItemEqual(tOutput) && mInventory[0].stackSize < 64) {
							mInventory[0].stackSize++;
							getBaseMetaTileEntity().decreaseStoredEnergyUnits(100*(int)Math.pow(4, getBaseMetaTileEntity().getOverclockerUpgradeCount()), true);
						}
					}
				}
			}
		}
		super.onPostTick();
	}
	
	@Override
	public boolean allowPullStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 0;
	}
	
	@Override
	public boolean allowPutStack(int aIndex, byte aSide, ItemStack aStack) {
		return aIndex == 1;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GT_MetaTileEntity_RockBreaker();
	}
	
	@Override
	public int getTextureIndex(byte aSide, byte aFacing, boolean aActive, boolean aRedstone) {
		if (ForgeDirection.getOrientation(aSide).getOpposite() == ForgeDirection.getOrientation(aFacing))
			return 113+(aRedstone?8:0);
		return 115+(aRedstone?8:0);
	}
	
	@Override
	protected String getDescription() {
		return "Electric Cobble Generator Mk VI";
	}
}
