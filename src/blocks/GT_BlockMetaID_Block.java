package gregtechmod.common.blocks;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.common.tileentities.GT_TileEntity_LESU;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_BlockMetaID_Block extends Block {
	
	public Icon[] mIcons = new Icon[52];
	
	public static boolean mConnectedMachineTextures = true;
	
	public GT_BlockMetaID_Block(int aID) {
        super(aID, Material.iron);
        setHardness(3.0F);
        setResistance(10.0F);
        setUnlocalizedName("BlockMetaID_Block");
        setStepSound(Block.soundMetalFootstep);
		setCreativeTab(GregTech_API.TAB_GREGTECH);
        for (int i = 0; i < 16; i++) MinecraftForge.setBlockHarvestLevel(this, i, "pickaxe",  2);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
    	for (int i = 0; i < mIcons.length; i++) mIcons[i] = par1IconRegister.registerIcon(GregTech_API.TEXTURE_PATH + (GregTech_API.sConfiguration.system?"troll":getUnlocalizedName() + "/" + i));
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateSilver")			, mIcons[ 3]);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateRuby")			, mIcons[ 4]);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateSapphire")		, mIcons[ 5]);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateAluminium")		, mIcons[ 7]);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateTitanium")		, mIcons[ 8]);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateChrome")			, mIcons[ 9]);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateSteel")			, mIcons[11]);
    	GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateBrass")			, mIcons[12]);
	}
	
	@Override
    public boolean isBeaconBase(World aWorld, int aX, int aY, int aZ, int beaconX, int beaconY, int beaconZ) {
        return !GregTech_API.isMachineBlock(blockID, aWorld.getBlockMetadata(aX, aY, aZ));
    }
	
	@Override
	public void breakBlock(World aWorld, int aX, int aY, int aZ, int par5, int par6) {
		if (aWorld.getBlockMetadata(aX, aY, aZ) == 6) {
			stepToFindOrCallLESUController(aWorld, aX, aY, aZ, new ArrayList<ChunkPosition>(), true);
		}
		if (GregTech_API.isMachineBlock(blockID, aWorld.getBlockMetadata(aX, aY, aZ))) {
			GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
		}
	}
	
	@Override
    public void onBlockAdded(World aWorld, int aX, int aY, int aZ) {
		if (aWorld.getBlockMetadata(aX, aY, aZ) == 6) {
			stepToFindOrCallLESUController(aWorld, aX, aY, aZ, new ArrayList<ChunkPosition>(), true);
		}
		if (GregTech_API.isMachineBlock(blockID, aWorld.getBlockMetadata(aX, aY, aZ))) {
			GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
		}
	}
    
	@Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
		return false;
	}
	
	@Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		if (world == null) return 0;
		Integer tMeta = world.getBlockMetadata(x, y, z);
		if (tMeta == null) tMeta = 0;
		if (tMeta ==  0) return  30.0F;
		if (tMeta ==  1) return  30.0F;
		if (tMeta ==  2) return 300.0F;
		if (tMeta ==  3) return  30.0F;
		if (tMeta ==  4) return  30.0F;
		if (tMeta ==  5) return  30.0F;
		if (tMeta ==  6) return  30.0F;
		if (tMeta ==  7) return  30.0F;
		if (tMeta ==  8) return 200.0F;
		if (tMeta ==  9) return 100.0F;
		if (tMeta == 10) return 200.0F;
		if (tMeta == 11) return 100.0F;
		if (tMeta == 12) return  30.0F;
		if (tMeta == 13) return  30.0F;
		if (tMeta == 14) return  60.0F;
		if (tMeta == 15) return  30.0F;
		return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

	@Override
    public float getBlockHardness(World world, int x, int y, int z) {
		if (world == null) return 0;
		Integer tMeta = world.getBlockMetadata(x, y, z);
		if (tMeta == null) tMeta = 0;
		if (tMeta ==  2) return 100.0F;
		if (tMeta ==  8) return 10.0F;
		if (tMeta ==  9) return 10.0F;
		if (tMeta == 10) return 10.0F;
        return 3.0F;
    }
    
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int aSide, int aMeta) {
		if (aMeta < 0 || aMeta >= mIcons.length) return null;
		return mIcons[aMeta];
	}
	
	@Override
	public int damageDropped(int par1) {
        return par1;
	}
	
	@Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockMetadata(par2, par3, par4);
    }
	
	@Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
        return blockID;
    }
    
	@Override @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 1; i < 16; ++i) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
	
	public static boolean isLESUBlock(World aWorld, int aX, int aY, int aZ) {
		return isLESUStorage(aWorld, aX, aY, aZ) || isLESUController(aWorld, aX, aY, aZ);
	}

	public static boolean isLESUStorage(World aWorld, int aX, int aY, int aZ) {
		return aWorld.getBlockId(aX, aY, aZ) == GregTech_API.sBlockList[0].blockID && aWorld.getBlockMetadata(aX, aY, aZ) == 6;
	}
	
	public static boolean isLESUController(World aWorld, int aX, int aY, int aZ) {
		return aWorld.getBlockId(aX, aY, aZ) == GregTech_API.sBlockList[1].blockID && aWorld.getBlockMetadata(aX, aY, aZ) == 7;
	}
	
	private void createLESUBlock(World aWorld, int aX, int aY, int aZ) {
		stepToFindOrCallLESUController(aWorld, aX, aY, aZ, new ArrayList<ChunkPosition>(), true);
	}
	
	private void destroyLESUBlock(World aWorld, int aX, int aY, int aZ) {
		stepToFindOrCallLESUController(aWorld, aX, aY, aZ, new ArrayList<ChunkPosition>(), true);
	}
	
	public static int stepToFindOrCallLESUController(World aWorld, int aX, int aY, int aZ, ArrayList<ChunkPosition> aList, boolean aCall) {
		ChunkPosition tCoord = new ChunkPosition(aX, aY, aZ);
		aList.add(tCoord);
		int tControllerCount = 0;
		if (isLESUController(aWorld, aX, aY, aZ)) {
			GT_TileEntity_LESU tTileEntity = (GT_TileEntity_LESU)(aWorld.getBlockTileEntity(aX, aY, aZ));
			if (tTileEntity != null) {
				if (aCall) tTileEntity.notifyLESUchange();
				tControllerCount++;
			}
		}
		if (isLESUBlock(aWorld, aX + 1, aY, aZ)&&!aList.contains(new ChunkPosition(aX + 1, aY, aZ))) tControllerCount += stepToFindOrCallLESUController(aWorld, aX + 1, aY, aZ, aList, aCall);
		if (isLESUBlock(aWorld, aX - 1, aY, aZ)&&!aList.contains(new ChunkPosition(aX - 1, aY, aZ))) tControllerCount += stepToFindOrCallLESUController(aWorld, aX - 1, aY, aZ, aList, aCall);
		if (isLESUBlock(aWorld, aX, aY + 1, aZ)&&!aList.contains(new ChunkPosition(aX, aY + 1, aZ))) tControllerCount += stepToFindOrCallLESUController(aWorld, aX, aY + 1, aZ, aList, aCall);
		if (isLESUBlock(aWorld, aX, aY - 1, aZ)&&!aList.contains(new ChunkPosition(aX, aY - 1, aZ))) tControllerCount += stepToFindOrCallLESUController(aWorld, aX, aY - 1, aZ, aList, aCall);
		if (isLESUBlock(aWorld, aX, aY, aZ + 1)&&!aList.contains(new ChunkPosition(aX, aY, aZ + 1))) tControllerCount += stepToFindOrCallLESUController(aWorld, aX, aY, aZ + 1, aList, aCall);
		if (isLESUBlock(aWorld, aX, aY, aZ - 1)&&!aList.contains(new ChunkPosition(aX, aY, aZ - 1))) tControllerCount += stepToFindOrCallLESUController(aWorld, aX, aY, aZ - 1, aList, aCall);
		return tControllerCount;
	}
	
	public static int stepToGetLESUAmount(World aWorld, int aX, int aY, int aZ, ArrayList<ChunkPosition> aList) {
		ChunkPosition tCoord = new ChunkPosition(aX, aY, aZ);
		aList.add(tCoord);
		int tStorageCount = 1;
		if (isLESUStorage(aWorld, aX + 1, aY, aZ)&&!aList.contains(new ChunkPosition(aX + 1, aY, aZ))) tStorageCount += stepToGetLESUAmount(aWorld, aX + 1, aY, aZ, aList);
		if (isLESUStorage(aWorld, aX - 1, aY, aZ)&&!aList.contains(new ChunkPosition(aX - 1, aY, aZ))) tStorageCount += stepToGetLESUAmount(aWorld, aX - 1, aY, aZ, aList);
		if (isLESUStorage(aWorld, aX, aY + 1, aZ)&&!aList.contains(new ChunkPosition(aX, aY + 1, aZ))) tStorageCount += stepToGetLESUAmount(aWorld, aX, aY + 1, aZ, aList);
		if (isLESUStorage(aWorld, aX, aY - 1, aZ)&&!aList.contains(new ChunkPosition(aX, aY - 1, aZ))) tStorageCount += stepToGetLESUAmount(aWorld, aX, aY - 1, aZ, aList);
		if (isLESUStorage(aWorld, aX, aY, aZ + 1)&&!aList.contains(new ChunkPosition(aX, aY, aZ + 1))) tStorageCount += stepToGetLESUAmount(aWorld, aX, aY, aZ + 1, aList);
		if (isLESUStorage(aWorld, aX, aY, aZ - 1)&&!aList.contains(new ChunkPosition(aX, aY, aZ - 1))) tStorageCount += stepToGetLESUAmount(aWorld, aX, aY, aZ - 1, aList);
		return tStorageCount;
	}
	
	@Override
    public Icon getBlockTexture(IBlockAccess aWorld, int xCoord, int yCoord, int zCoord, int aSide) {
        int tMeta = aWorld.getBlockMetadata(xCoord, yCoord, zCoord);
		if (tMeta < 13 || (xCoord == 0 && yCoord == 0 && zCoord == 0) || !mConnectedMachineTextures) return this.getBlockTextureFromSideAndMetadata(aSide, tMeta);
        int tStartIndex=(tMeta==15?40:tMeta==14?28:16);
		
    	boolean[] tConnectedSides = {
    		aWorld.getBlockId(xCoord, yCoord-1, zCoord) == blockID && aWorld.getBlockMetadata(xCoord, yCoord-1, zCoord) == tMeta,
    		aWorld.getBlockId(xCoord, yCoord+1, zCoord) == blockID && aWorld.getBlockMetadata(xCoord, yCoord+1, zCoord) == tMeta,
    		aWorld.getBlockId(xCoord+1, yCoord, zCoord) == blockID && aWorld.getBlockMetadata(xCoord+1, yCoord, zCoord) == tMeta,
    		aWorld.getBlockId(xCoord, yCoord, zCoord+1) == blockID && aWorld.getBlockMetadata(xCoord, yCoord, zCoord+1) == tMeta,
    		aWorld.getBlockId(xCoord-1, yCoord, zCoord) == blockID && aWorld.getBlockMetadata(xCoord-1, yCoord, zCoord) == tMeta,
    		aWorld.getBlockId(xCoord, yCoord, zCoord-1) == blockID && aWorld.getBlockMetadata(xCoord, yCoord, zCoord-1) == tMeta
    	};
    	
    	switch (aSide) {
    	case 0:
    		if (tConnectedSides[0]) return mIcons[tStartIndex+7];
    		
    		if (tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+6];

    		if (!tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+2];
    		if (tConnectedSides[4]&&!tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+3];
    		if (tConnectedSides[4]&&tConnectedSides[5]&&!tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+4];
    		if (tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+5];

    		if (!tConnectedSides[4]&&!tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+8];
    		if (tConnectedSides[4]&&!tConnectedSides[5]&&!tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+9];
    		if (tConnectedSides[4]&&tConnectedSides[5]&&!tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+10];
    		if (!tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+11];
    		
    		if (!tConnectedSides[4]&&!tConnectedSides[5]&&!tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+7];
    		
    		if (!tConnectedSides[4]&&!tConnectedSides[2]) return mIcons[tStartIndex+1];
    		if (!tConnectedSides[5]&&!tConnectedSides[3]) return mIcons[tStartIndex+0];
    	case 1:
    		if (tConnectedSides[1]) return mIcons[tStartIndex+7];
    		
    		if (tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+6];

    		if (!tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+2];
    		if (tConnectedSides[4]&&!tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+3];
    		if (tConnectedSides[4]&&tConnectedSides[5]&&!tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+4];
    		if (tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+5];
    		
    		if (!tConnectedSides[4]&&!tConnectedSides[5]&&tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+8];
    		if (tConnectedSides[4]&&!tConnectedSides[5]&&!tConnectedSides[2]&&tConnectedSides[3]) return mIcons[tStartIndex+9];
    		if (tConnectedSides[4]&&tConnectedSides[5]&&!tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+10];
    		if (!tConnectedSides[4]&&tConnectedSides[5]&&tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+11];
    		
    		if (!tConnectedSides[4]&&!tConnectedSides[5]&&!tConnectedSides[2]&&!tConnectedSides[3]) return mIcons[tStartIndex+7];
    		
    		if (!tConnectedSides[2]&&!tConnectedSides[4]) return mIcons[tStartIndex+1];
    		if (!tConnectedSides[3]&&!tConnectedSides[5]) return mIcons[tStartIndex+0];
    	case 2:
    		if (tConnectedSides[5]) return mIcons[tStartIndex+7];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+6];

    		if (!tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+2];
    		if (tConnectedSides[2]&&!tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+5];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&!tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+4];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+3];
    		
    		if (!tConnectedSides[2]&&!tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+11];
    		if (tConnectedSides[2]&&!tConnectedSides[0]&&!tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+10];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&!tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+9];
    		if (!tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+8];
    		
    		if (!tConnectedSides[2]&&!tConnectedSides[0]&&!tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+7];
    		
    		if (!tConnectedSides[2]&&!tConnectedSides[4]) return mIcons[tStartIndex+1];
    		if (!tConnectedSides[0]&&!tConnectedSides[1]) return mIcons[tStartIndex+0];
    	case 3:
    		if (tConnectedSides[3]) return mIcons[tStartIndex+7];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+6];

    		if (!tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+4];
    		if (tConnectedSides[2]&&!tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+5];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&!tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+2];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+3];
    		
    		if (!tConnectedSides[2]&&!tConnectedSides[0]&&tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+10];
    		if (tConnectedSides[2]&&!tConnectedSides[0]&&!tConnectedSides[4]&&tConnectedSides[1]) return mIcons[tStartIndex+11];
    		if (tConnectedSides[2]&&tConnectedSides[0]&&!tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+8];
    		if (!tConnectedSides[2]&&tConnectedSides[0]&&tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+9];
    		
    		if (!tConnectedSides[2]&&!tConnectedSides[0]&&!tConnectedSides[4]&&!tConnectedSides[1]) return mIcons[tStartIndex+7];
    		
    		if (!tConnectedSides[2]&&!tConnectedSides[4]) return mIcons[tStartIndex+1];
    		if (!tConnectedSides[0]&&!tConnectedSides[1]) return mIcons[tStartIndex+0];
    	case 4:
    		if (tConnectedSides[4]) return mIcons[tStartIndex+7];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+6];

    		if (!tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+5];
    		if (tConnectedSides[0]&&!tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+4];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&!tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+3];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+2];
    		
    		if (!tConnectedSides[0]&&!tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+10];
    		if (tConnectedSides[0]&&!tConnectedSides[3]&&!tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+9];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&!tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+8];
    		if (!tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+11];
    		
    		if (!tConnectedSides[0]&&!tConnectedSides[3]&&!tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+7];
    		
    		if (!tConnectedSides[0]&&!tConnectedSides[1]) return mIcons[tStartIndex+0];
    		if (!tConnectedSides[3]&&!tConnectedSides[5]) return mIcons[tStartIndex+1];
    	case 5:
    		if (tConnectedSides[2]) return mIcons[tStartIndex+7];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+6];

    		if (!tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+5];
    		if (tConnectedSides[0]&&!tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+2];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&!tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+3];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+4];
    		
    		if (!tConnectedSides[0]&&!tConnectedSides[3]&&tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+11];
    		if (tConnectedSides[0]&&!tConnectedSides[3]&&!tConnectedSides[1]&&tConnectedSides[5]) return mIcons[tStartIndex+8];
    		if (tConnectedSides[0]&&tConnectedSides[3]&&!tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+9];
    		if (!tConnectedSides[0]&&tConnectedSides[3]&&tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+10];
    		
    		if (!tConnectedSides[0]&&!tConnectedSides[3]&&!tConnectedSides[1]&&!tConnectedSides[5]) return mIcons[tStartIndex+7];
    		
    		if (!tConnectedSides[0]&&!tConnectedSides[1]) return mIcons[tStartIndex+0];
    		if (!tConnectedSides[3]&&!tConnectedSides[5]) return mIcons[tStartIndex+1];
    	default: return mIcons[tStartIndex+7];
    	}
    }
}