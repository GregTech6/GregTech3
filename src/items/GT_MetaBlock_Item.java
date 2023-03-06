package gregtechmod.common.items;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.common.blocks.GT_BlockMetaID_Block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class GT_MetaBlock_Item extends ItemBlock {
    public GT_MetaBlock_Item(int par1) {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
        setUnlocalizedName(GT_LanguageManager.mNameList0[0]);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
    }
    
    @Override
    public int getMetadata(int par1) {
        return par1;
    }
    
	@Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean par4) {
		aList.add("Mobs can't spawn on this Block");
    }

	@Override
	public String getUnlocalizedName(ItemStack aStack) {
    	return getUnlocalizedName() + "." + GT_LanguageManager.mNameList0[aStack.getItemDamage()];
    }
	
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
    	if (stack.getItemDamage() == 6)
    		if (1 < GT_BlockMetaID_Block.stepToFindOrCallLESUController(world, x, y, z, new ArrayList<ChunkPosition>(), false))
    			return true;
    	return false;
    }
}
