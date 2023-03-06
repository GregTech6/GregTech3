package gregtechmod.api.util;

import net.minecraft.item.ItemStack;
import thermalexpansion.api.crafting.IPulverizerRecipe;

/**
 * Used in the Universal Macerator
 */
public class GT_PulverizerRecipe implements IPulverizerRecipe {
	private final ItemStack mInput, mOutput1, mOutput2;
	private final int mChance;
	
	public GT_PulverizerRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aChance) {
		mInput = aInput;
		mOutput1 = aOutput1;
		mOutput2 = aOutput2;
		mChance = aChance;
	}
	
	@Override
	public ItemStack getInput() {
		return mInput;
	}
	
	@Override
	public ItemStack getPrimaryOutput() {
		return mOutput1;
	}
	
	@Override
	public ItemStack getSecondaryOutput() {
		return mOutput2;
	}
	
	@Override
	public int getSecondaryOutputChance() {
		return mChance;
	}
	
	@Override
	public int getEnergy() {
		return 400;
	}

}
