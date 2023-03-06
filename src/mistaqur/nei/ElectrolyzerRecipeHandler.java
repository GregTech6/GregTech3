package gregtechmod.mistaqur.nei;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.common.gui.GT_GUIContainer_Electrolyzer;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;

public class ElectrolyzerRecipeHandler extends GT_RecipeHandler {
	
	public class CachedElectrolyzerRecipe extends CachedGT_Recipe {
		public int mDuration, mEUt;

		public CachedElectrolyzerRecipe(GT_Recipe aRecipe) {
			int xoffset = 4;
			int yoffset = 4;
			resources = new ArrayList<PositionedStack>();
			if (aRecipe.mInput1 != null)
				resources.add(new PositionedStack(aRecipe.mInput1, 80 - xoffset, 46 - yoffset));
			if (aRecipe.mInput2 != null)
				resources.add(new PositionedStack(aRecipe.mInput2, 50 - xoffset, 46 - yoffset));

			products = new ArrayList<PositionedStack>();
			if (aRecipe.mOutput1 != null)
				products.add(new PositionedStack(aRecipe.mOutput1, 50 - xoffset, 16 - yoffset));
			if (aRecipe.mOutput2 != null)
				products.add(new PositionedStack(aRecipe.mOutput2, 70 - xoffset, 16 - yoffset));
			if (aRecipe.mOutput3 != null)
				products.add(new PositionedStack(aRecipe.mOutput3, 90 - xoffset, 16 - yoffset));
			if (aRecipe.mOutput4 != null)
				products.add(new PositionedStack(aRecipe.mOutput4,110 - xoffset, 16 - yoffset));

			mDuration = aRecipe.mDuration;
			mEUt = aRecipe.mEUt;
		}
	}
	
	@Override
	public void loadTransferRects() {
		try {
		transferRects.add(new RecipeTransferRect(new Rectangle(74-4, 33-4, 30, 10), getRecipeId()));
		
		ArrayList<Class<? extends GuiContainer>> guis = new ArrayList<Class<? extends GuiContainer>>();
		ArrayList<RecipeTransferRect> transferRects2 = new ArrayList<RecipeTransferRect>();
		guis.add(GT_GUIContainer_Electrolyzer.class);
		transferRects2.add(new RecipeTransferRect(new Rectangle(74-5, 33-11, 30, 10), getRecipeId(), new Object[0]));
		RecipeTransferRectHandler.registerRectsToGuis(guis, transferRects2);
		} catch(Throwable e) {e.printStackTrace(GT_Log.out);}
	}
	
	@Override
	public String getRecipeName() {
		return "Industrial Electrolyzer";
	}
	
	@Override
	public String getRecipeId() {
		return "gregtech.Electrolyzer";
	}
	
	@Override
	public String getGuiTexture() {
		return GregTech_API.GUI_PATH + "NEIElectrolyzer.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "gregtech.electrolyzer";
	}
	
	@Override
	public ArrayList<GT_Recipe> getRecipeList() {
		return GT_Recipe.sElectrolyzerRecipes;
	}
	
	@Override
	public CachedGT_Recipe getRecipe(GT_Recipe irecipe) {
		return new CachedElectrolyzerRecipe(irecipe);
	}
	
	@Override
	public void drawExtras(GuiContainerManager gui, int recipe) {
		Integer time = ((CachedElectrolyzerRecipe)arecipes.get(recipe)).mDuration;
		gui.drawText(30, 80, new StringBuilder().append("EU: ").append(toNumber(time*((CachedElectrolyzerRecipe)arecipes.get(recipe)).mEUt)).toString(), 0xFF000000, false);
		gui.drawText(30, 90, new StringBuilder().append("Time: ").append(toNumber(time/20)).append(" secs").toString(), 0xFF000000, false);
		gui.drawText(30,100, new StringBuilder().append("MaxEnergy: ").append(toNumber(((CachedElectrolyzerRecipe)arecipes.get(recipe)).mEUt)).append(" EU/t").toString(), 0xFF000000, false);
		
	}

}
