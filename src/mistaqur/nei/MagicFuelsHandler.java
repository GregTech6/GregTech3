package gregtechmod.mistaqur.nei;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.common.gui.GT_GUIContainer_MagicEnergyConverter;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;

public class MagicFuelsHandler extends GT_RecipeHandler {
	
	public class CachedMagicFuels extends CachedGT_Recipe {
		public int mStartEU;

		public CachedMagicFuels(GT_Recipe aRecipe) {
			int xoffset = 4;
			int yoffset = 4;
			resources = new ArrayList<PositionedStack>();
			if (aRecipe.mInput1 != null) {
				LiquidStack tLiquid;
				if (null != (tLiquid = LiquidContainerRegistry.getLiquidForFilledItem(aRecipe.mInput1))) {
					resources.add(new PositionedStack(tLiquid.asItemStack(), 80 - xoffset, 35 - yoffset));
					ArrayList<ItemStack> tList = new ArrayList<ItemStack>();
					for (LiquidContainerData tData : LiquidContainerRegistry.getRegisteredLiquidContainerData())
						if (tData.stillLiquid.isLiquidEqual(tLiquid))
							tList.add(tData.filled);
					if (tList.isEmpty())
						resources.add(new PositionedStack(aRecipe.mInput1, 61 - xoffset, 35 - yoffset));
					else
						resources.add(new PositionedStack(tList, 61 - xoffset, 35 - yoffset));
				} else {
					resources.add(new PositionedStack(aRecipe.mInput1, 61 - xoffset, 35 - yoffset));
				}
			}
			products = new ArrayList<PositionedStack>();
			
			mStartEU = aRecipe.mStartEU;
		}
	}
	
	@Override
	public void loadTransferRects() {
		try {
		transferRects.add(new RecipeTransferRect(new Rectangle(98-4, 34-4, 18, 18), getRecipeId()));
		
		ArrayList<Class<? extends GuiContainer>> guis = new ArrayList<Class<? extends GuiContainer>>();
		ArrayList<RecipeTransferRect> transferRects2 = new ArrayList<RecipeTransferRect>();
		guis.add(GT_GUIContainer_MagicEnergyConverter.class);
		transferRects2.add(new RecipeTransferRect(new Rectangle(79-5, 34-11, 18, 18), getRecipeId(), new Object[0]));
		RecipeTransferRectHandler.registerRectsToGuis(guis, transferRects2);
		} catch(Throwable e) {e.printStackTrace(GT_Log.out);}
	}
	
	@Override
	public String getRecipeName() {
		return "Magic Energy Converter";
	}
	
	@Override
	public String getRecipeId() {
		return "gregtech.MagicFuels";
	}
	
	@Override
	public String getGuiTexture() {
		return GregTech_API.GUI_PATH + "NEIFuel.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "gregtech.MagicFuels";
	}
	
	@Override
	public ArrayList<GT_Recipe> getRecipeList() {
		return GT_Recipe.sMagicFuels;
	}
	
	@Override
	public CachedGT_Recipe getRecipe(GT_Recipe irecipe) {
		return new CachedMagicFuels(irecipe);
	}
	
	@Override
	public void drawExtras(GuiContainerManager gui, int recipe) {
		CachedMagicFuels t = ((CachedMagicFuels)arecipes.get(recipe));
		gui.drawText(30, 80, new StringBuilder().append("EU: ").append(toNumber(t.mStartEU*1000)).append("EU").toString(), 0xFF000000, false);
	}

}
