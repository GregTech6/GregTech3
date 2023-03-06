package gregtechmod.api.items;

import gregtechmod.api.GregTech_API;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Extended by most Items, also used as a fallback Item, to prevent the accidental deletion when Errors occur.
 */
public class GT_Generic_Item extends Item {
	
	private final String mTooltip;
	
	public GT_Generic_Item(int aID, String aName, String aTooltip) {
		super(aID);
		setUnlocalizedName(aName);
		setCreativeTab(GregTech_API.TAB_GREGTECH);
		mTooltip = aTooltip;
	}
	
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister) {
        this.iconIndex = par1IconRegister.registerIcon(GregTech_API.TEXTURE_PATH + (GregTech_API.sConfiguration.system?"troll":getUnlocalizedName()));
    }
    
	@Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
		if (mTooltip != null && !mTooltip.equals("")) aList.add(mTooltip);
    }
}