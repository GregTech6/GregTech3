package gregtechmod.common;

import gregtechmod.api.GregTech_API;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class GT_FuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack aFuel) {
		if (aFuel == null) return 0;
		if (aFuel.isItemEqual(new ItemStack(Item.sign, 1)))				return   600;
		if (aFuel.isItemEqual(GregTech_API.getGregTechItem(1, 1,15)))	return   100;
		if (aFuel.isItemEqual(GregTech_API.getGregTechItem(0, 1,15)))	return  1600;
		return 0;
	}
}