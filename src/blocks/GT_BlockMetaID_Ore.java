package gregtechmod.common.blocks;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.common.items.GT_MetaItem_Dust;
import gregtechmod.common.items.GT_MetaItem_Material;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_BlockMetaID_Ore extends Block {

	public Icon[] mIcons = new Icon[96];
	
	public static final int Metablockcount = 13;
	
	public GT_BlockMetaID_Ore(int aID) {
        super(aID, Material.rock);
        setHardness(3.0F);
        setResistance(10.0F);
        setUnlocalizedName("BlockMetaID_Ore");
        setStepSound(Block.soundStoneFootstep);
		setCreativeTab(GregTech_API.TAB_GREGTECH);
        
        MinecraftForge.setBlockHarvestLevel(this,  0, "pickaxe",  1);
        MinecraftForge.setBlockHarvestLevel(this,  1, "pickaxe",  1);
        MinecraftForge.setBlockHarvestLevel(this,  2, "pickaxe",  3);
        MinecraftForge.setBlockHarvestLevel(this,  3, "pickaxe",  2);
        MinecraftForge.setBlockHarvestLevel(this,  4, "pickaxe",  2);
        MinecraftForge.setBlockHarvestLevel(this,  5, "pickaxe",  1);
        MinecraftForge.setBlockHarvestLevel(this,  6, "pickaxe",  1);
        MinecraftForge.setBlockHarvestLevel(this,  7, "pickaxe",  2);
        MinecraftForge.setBlockHarvestLevel(this,  8, "pickaxe",  1);
        MinecraftForge.setBlockHarvestLevel(this,  9, "pickaxe",  2);
        MinecraftForge.setBlockHarvestLevel(this, 10, "pickaxe",  3);
        MinecraftForge.setBlockHarvestLevel(this, 11, "pickaxe",  3);
        MinecraftForge.setBlockHarvestLevel(this, 12, "pickaxe",  2);
        MinecraftForge.setBlockHarvestLevel(this, 13, "pickaxe",  2);
        MinecraftForge.setBlockHarvestLevel(this, 14, "pickaxe",  2);
        MinecraftForge.setBlockHarvestLevel(this, 15, "pickaxe",  2);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    	for (int i = 0; i < mIcons.length; i++) mIcons[i] = par1IconRegister.registerIcon(GregTech_API.TEXTURE_PATH + (GregTech_API.sConfiguration.system?"troll":getUnlocalizedName() + "/" + i));
    }
	
	@Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        int var8 = 0;
    	switch(par5) {
    	case  2: var8 = 30 + par1World.rand.nextInt(21); break;
    	case  3: var8 =  3 + par1World.rand.nextInt( 5); break;
    	case  4: var8 =  3 + par1World.rand.nextInt( 5); break;
    	case  6: var8 =  1 + par1World.rand.nextInt( 1); break;
    	case  7: var8 =  3 + par1World.rand.nextInt( 3); break;
    	case  8: var8 =  1 + par1World.rand.nextInt( 1); break;
    	}
        if (var8>0) dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
    }
	
	@Override
    public ArrayList<ItemStack> getBlockDropped(World aWorld, int aX, int aY, int aZ, int aMeta, int aFortune) {
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
		switch (aMeta) {
		case  0:
			break;
		case  1:
			rList.add(new ItemStack(blockID, 1, aMeta));
			break;
		case  2:
			rList.add(GT_ModHandler.getIC2Item("iridiumOre", 1+aWorld.rand.nextInt(1 + (aFortune/2))));
			break;
		case  3:
			rList.add(GT_OreDictUnificator.get("gemRuby", null, 1 + aWorld.rand.nextInt(1 + aFortune)));
			if (aWorld.rand.nextInt(Math.max(1, 32/(aFortune+1))) == 0)
				rList.add(GT_MetaItem_Material.instance.getStack(54, 1));
			break;
		case  4:
			rList.add(GT_OreDictUnificator.get("gemSapphire", null, 1 + aWorld.rand.nextInt(1 + aFortune)));
			if (aWorld.rand.nextInt(Math.max(1, 64/(aFortune+1))) == 0)
				rList.add(GT_OreDictUnificator.get("gemGreenSapphire", null, 1));
			break;
		case  5:
			rList.add(new ItemStack(blockID, 1, aMeta));
			break;
		case  6:
			rList.add(GT_MetaItem_Dust.instance.getStack( 3, 2 + aWorld.rand.nextInt(1 + aFortune)));
			break;
		case  7:
			rList.add(GT_MetaItem_Dust.instance.getStack(11, 2 + aWorld.rand.nextInt(1 + aFortune)));
			if (aWorld.rand.nextInt(Math.max(1,  4/(aFortune+1))) == 0)
				rList.add(new ItemStack(Item.redstone, 1));
			break;
		case  8:
			rList.add(GT_MetaItem_Dust.instance.getStack(14, 2 + aWorld.rand.nextInt(1 + aFortune)));
			if (aWorld.rand.nextInt(Math.max(1,  4/(aFortune+1))) == 0)
				rList.add(GT_MetaItem_Dust.instance.getStack(24, 1));
			if (aWorld.rand.nextInt(Math.max(1, 32/(aFortune+1))) == 0)
				rList.add(GT_MetaItem_Material.instance.getStack(55, 1));
			break;
		case  9:
			rList.add(new ItemStack(blockID, 1, aMeta));
			break;
		case 10:
			rList.add(new ItemStack(blockID, 1, aMeta));
			break;
		case 11:
			rList.add(GT_OreDictUnificator.get("gemOlivine", null, 1 + aWorld.rand.nextInt(1 + aFortune)));
			break;
		case 12:
			rList.add(GT_MetaItem_Dust.instance.getStack( 5, 6 + 3 * aWorld.rand.nextInt(1 + aFortune)));
			if (aWorld.rand.nextInt(Math.max(1,  4/(aFortune+1))) == 0)
				rList.add(GT_MetaItem_Dust.instance.getStack(18, 1));
			break;
		case 13:
			break;
		case 14:
			break;
		case 15:
			break;
		}
        return rList;
    }
	
    public boolean canDragonDestroy(World world, int x, int y, int z) {
        return false;
    }
	
	@Override
    public float getBlockHardness(World world, int x, int y, int z) {
		if (world == null) return 0;
		Integer tMeta = world.getBlockMetadata(x, y, z);
		if (tMeta == null) tMeta = 0;
		switch (world.getBlockMetadata(x, y, z)) {
		case  1: return  3.0F;
		case  2: return 20.0F;
		case  3: return  4.0F;
		case  4: return  4.0F;
		case  5: return  3.0F;
		case  6: return  2.0F;
		case  7: return  3.0F;
		case  8: return  2.0F;
		case  9: return  4.0F;
		case 10: return  3.5F;
		default: return super.getBlockHardness(world, x, y, z);
		}
    }
	
	@Override
    public Icon getBlockTexture(IBlockAccess aWorld, int aX, int aY, int aZ, int aSide) {
		return getBlockTextureFromSideAndMetadata(Math.abs(aSide^aX^aY^aZ)%6, aWorld.getBlockMetadata(aX, aY, aZ));
    }
    
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int aSide, int aMeta) {
		if (aMeta < 0 || aMeta*6+aSide >= mIcons.length) return null;
		return mIcons[aMeta*6+aSide];
	}
	
	@Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockMetadata(par2, par3, par4);
    }
    
	@Override @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 1; i < Metablockcount; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
