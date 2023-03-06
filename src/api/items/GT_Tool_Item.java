package gregtechmod.api.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * This is just a basic Tool, which has normal durability and doesn't break Blocks.
 */
public class GT_Tool_Item extends GT_Generic_Item {
	
	public GT_Tool_Item(int aID, String aName, String aTooltip, int aMaxDamage) {
		super(aID, aName, aTooltip);
		setMaxDamage(aMaxDamage);
	}
	
	@Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
		super.addInformation(aStack, aPlayer, aList, aF3_H);
		aList.add((aStack.getMaxDamage() - aStack.getItemDamage()) + " / " + aStack.getMaxDamage());
    }
}