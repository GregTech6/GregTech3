package gregtechmod.common.items;

import gregtechmod.api.GregTech_API;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_Jackhammer_Item extends ItemTool implements IElectricItem {
	public int mCharge, mTransfer, mTier, mEnergyConsumption;
	
    public static Set mineableBlocks = new HashSet();
    
	public GT_Jackhammer_Item(int aID, String aName, int aCharge, int aTier, float aQuality, int aEnergyConsumption) {
		super(aID, 0, EnumToolMaterial.IRON, new Block[0]);
		setCreativeTab(GregTech_API.TAB_GREGTECH);
		setMaxStackSize(1);
		setMaxDamage(100);
		setNoRepair();
		setUnlocalizedName(aName);
		mCharge = aCharge;
		mTier = aTier;
		mTransfer = mTier*50;
        efficiencyOnProperMaterial = aQuality;
        mEnergyConsumption = aEnergyConsumption;
        
        mineableBlocks.add(Block.cobblestone);
        mineableBlocks.add(Block.stone);
        mineableBlocks.add(Block.sandStone);
        mineableBlocks.add(Block.cobblestoneMossy);
        mineableBlocks.add(Block.netherrack);
        mineableBlocks.add(Block.glowStone);
        mineableBlocks.add(Block.netherBrick);
        mineableBlocks.add(Block.whiteStone);
	}
	
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister) {
        this.iconIndex = par1IconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName());
    }
    
	@Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
		aList.add("Tier: " + mTier);
    }
	
	@Override
	public boolean onItemUse(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float var8, float var9, float var10) {
		ElectricItem.use(aStack, 0, aPlayer);
		return false;
	}
	
	@Override
    public void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
    }

	@Override
    public float getStrVsBlock(ItemStack var1, Block var2) {
        return !ElectricItem.canUse(var1, mEnergyConsumption) ? 1.0F : (ForgeHooks.isToolEffective(var1, var2, 0) ? efficiencyOnProperMaterial : (canHarvestBlock(var2) ? efficiencyOnProperMaterial : 1.0F));
    }

	@Override
    public float getStrVsBlock(ItemStack var1, Block var2, int var3) {
        return !ElectricItem.canUse(var1, mEnergyConsumption) ? 1.0F : (ForgeHooks.isToolEffective(var1, var2, var3) ? efficiencyOnProperMaterial : (canHarvestBlock(var2) ? efficiencyOnProperMaterial : 1.0F));
    }

	@Override
    public boolean canHarvestBlock(Block var1) {
        return mineableBlocks.contains(var1);
    }
	
	@Override
    public boolean hitEntity(ItemStack var1, EntityLiving var2, EntityLiving var3) {
        return true;
    }

	@Override
    public int getItemEnchantability() {
        return 0;
    }
	
	@Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return false;
    }
    
	@Override
    public int getDamageVsEntity(Entity var1) {
        return 1;
    }

	@Override
    public boolean onBlockDestroyed(ItemStack var1, World var2, int var3, int var4, int var5, int var6, EntityLiving var7) {
        ElectricItem.use(var1, 0, (EntityPlayer)var7);
		if ((double)Block.blocksList[var3].getBlockHardness(var2, var4, var5, var6) != 0.0D && ElectricItem.canUse(var1, mEnergyConsumption)) {
            if (var7 instanceof EntityPlayer) {
                ElectricItem.use(var1, mEnergyConsumption, (EntityPlayer)var7);
            } else {
                ElectricItem.discharge(var1, mEnergyConsumption, mTier, true, false);
            }
        }
        return true;
    }

	@Override
    public boolean hasEffect(ItemStack par1ItemStack) {
        return false;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int var1, CreativeTabs var2, List var3) {
        ItemStack tCharged = new ItemStack(this, 1), tUncharged = new ItemStack(this, 1, getMaxDamage());
        ElectricItem.charge(tCharged, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
        var3.add(tCharged);
        var3.add(tUncharged);
    }

	@Override
    public boolean getShareTag() {
        return true;
    }
    
	@Override
	public boolean canProvideEnergy(ItemStack aStack) {
		return false;
	}
	
	@Override
	public int getChargedItemId(ItemStack aStack) {
		return itemID;
	}
	
	@Override
	public int getEmptyItemId(ItemStack aStack) {
		return itemID;
	}
	
	@Override
	public int getMaxCharge(ItemStack aStack) {
		return mCharge;
	}
	
	@Override
	public int getTier(ItemStack aStack) {
		return mTier;
	}
	
	@Override
	public int getTransferLimit(ItemStack aStack) {
		return mTransfer;
	}
}
