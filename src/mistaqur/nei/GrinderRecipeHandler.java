package gregtechmod.mistaqur.nei;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.common.gui.GT_GUIContainer_Grinder;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;

public class GrinderRecipeHandler extends GT_RecipeHandler {
	
	public class CachedGrinderRecipe extends CachedGT_Recipe {
		public int mDuration, mEUt;

		public CachedGrinderRecipe(GT_Recipe aRecipe) {
			int xoffset = 4;
			int yoffset = 4;
			resources = new ArrayList<PositionedStack>();
			if (aRecipe.mInput1 != null)
				resources.add(new PositionedStack(aRecipe.mInput1, 34 - xoffset, 16 - yoffset));
			if (aRecipe.mInput2 != null)
				resources.add(new PositionedStack(aRecipe.mInput2, 34 - xoffset, 34 - yoffset));

			products = new ArrayList<PositionedStack>();
			if (aRecipe.mOutput1 != null)
				products.add(new PositionedStack(aRecipe.mOutput1, 86 - xoffset, 25 - yoffset));
			if (aRecipe.mOutput2 != null)
				products.add(new PositionedStack(aRecipe.mOutput2,104 - xoffset, 25 - yoffset));
			if (aRecipe.mOutput3 != null)
				products.add(new PositionedStack(aRecipe.mOutput3,122 - xoffset, 25 - yoffset));
			if (aRecipe.mOutput4 != null)
				products.add(new PositionedStack(aRecipe.mOutput4,140 - xoffset, 25 - yoffset));
			
			mDuration = aRecipe.mDuration;
			mEUt = aRecipe.mEUt;
		}
	}
	
	@Override
	public void loadTransferRects() {
		try {
		transferRects.add(new RecipeTransferRect(new Rectangle(56-4, 26-4, 24, 15), getRecipeId()));
		
		ArrayList<Class<? extends GuiContainer>> guis = new ArrayList<Class<? extends GuiContainer>>();
		ArrayList<RecipeTransferRect> transferRects2 = new ArrayList<RecipeTransferRect>();
		guis.add(GT_GUIContainer_Grinder.class);
		transferRects2.add(new RecipeTransferRect(new Rectangle(56-5, 26-11, 24, 15), getRecipeId(), new Object[0]));
		RecipeTransferRectHandler.registerRectsToGuis(guis, transferRects2);
		} catch(Throwable e) {e.printStackTrace(GT_Log.out);}
	}
	
	@Override
	public String getRecipeName() {
		return "Industrial Grinder";
	}
	
	@Override
	public String getRecipeId() {
		return "gregtech.Grinder";
	}
	
	@Override
	public String getGuiTexture() {
		return GregTech_API.GUI_PATH + "NEIGrinder.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "gregtech.Grinder";
	}
	
	@Override
	public ArrayList<GT_Recipe> getRecipeList() {
		return GT_Recipe.sGrinderRecipes;
	}
	
	@Override
	public CachedGT_Recipe getRecipe(GT_Recipe irecipe) {
		return new CachedGrinderRecipe(irecipe);
	}
	
	@Override
	public void drawExtras(GuiContainerManager gui, int recipe) {
		Integer time = ((CachedGrinderRecipe)arecipes.get(recipe)).mDuration;
		gui.drawText(30, 80, new StringBuilder().append("EU: ").append(toNumber(time*((CachedGrinderRecipe)arecipes.get(recipe)).mEUt)).toString(), 0xFF000000, false);
		gui.drawText(30, 90, new StringBuilder().append("Time: ").append(toNumber(time/20)).append(" secs").toString(), 0xFF000000, false);
		gui.drawText(30,100, new StringBuilder().append("MaxEnergy: ").append(toNumber(((CachedGrinderRecipe)arecipes.get(recipe)).mEUt)).append(" EU/t").toString(), 0xFF000000, false);
		
	}

}