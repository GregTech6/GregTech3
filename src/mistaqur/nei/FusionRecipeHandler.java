package gregtechmod.mistaqur.nei;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.common.gui.GT_GUIContainer_FusionComputer;

import java.awt.Rectangle;
import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;

public class FusionRecipeHandler extends GT_RecipeHandler {
	
	public class CachedFusionRecipe extends CachedGT_Recipe {
		public int mDuration;
		public int mEUt;
		public int mStartEU;
		
		public CachedFusionRecipe(GT_Recipe irecipe) {
			int xoffset = 122-118;
			int yoffset = 35-31;
			resources = new ArrayList<PositionedStack>();
			if (irecipe.mInput1 != null)
				resources.add(new PositionedStack(irecipe.mInput1, 48 - xoffset, 17 - yoffset));
			if (irecipe.mInput2 != null)
				resources.add(new PositionedStack(irecipe.mInput2, 48 - xoffset, 53 - yoffset));
			
			products = new ArrayList<PositionedStack>();
			if (irecipe.mOutput1 != null)
				products.add(new PositionedStack(irecipe.mOutput1, 108 - xoffset, 35 - yoffset));
			
			mDuration = irecipe.mDuration;
			mEUt = irecipe.mEUt;
			mStartEU = irecipe.mStartEU;
		}
	}

	@Override
	public void loadTransferRects() {
		try {
		transferRects.add(new RecipeTransferRect(new Rectangle(47-4, 34-4, 50, 18), getRecipeId()));
		
		ArrayList<Class<? extends GuiContainer>> guis = new ArrayList<Class<? extends GuiContainer>>();
		ArrayList<RecipeTransferRect> transferRects2 = new ArrayList<RecipeTransferRect>();
		guis.add(GT_GUIContainer_FusionComputer.class);
		transferRects2.add(new RecipeTransferRect(new Rectangle(154-5, 4-11, 18, 18), getRecipeId(), new Object[0]));
		RecipeTransferRectHandler.registerRectsToGuis(guis, transferRects2);
		} catch(Throwable e) {e.printStackTrace(GT_Log.out);}
	}
	
	@Override
	public String getRecipeName() {
		return "Fusion Reactor";
	}
	
	@Override
	public String getRecipeId() {
		return "gregtech.Fusionreactor";
	}
	
	@Override
	public String getGuiTexture() {
		return GregTech_API.GUI_PATH + "NEIFusionreactor.png";
	}
	
	@Override
	public String getOverlayIdentifier() {
		return "gregtech.fusion";
	}
	
	@Override
	public ArrayList<GT_Recipe> getRecipeList() {
		return GT_Recipe.sFusionRecipes;
	}
	
	@Override
	public CachedGT_Recipe getRecipe(GT_Recipe irecipe) {
		return new CachedFusionRecipe(irecipe);
	}

	@Override
	public void drawExtras(GuiContainerManager gui, int recipe) {
		CachedFusionRecipe t = ((CachedFusionRecipe)arecipes.get(recipe));
		gui.drawText(30, 80, new StringBuilder().append("Start: ").append(toNumber(t.mStartEU)).append("EU").toString(), 0xFF000000, false);
		gui.drawText(30, 90, new StringBuilder().append("EU/t: ").append(toNumber(t.mEUt)).toString(), 0xFF000000, false);
		gui.drawText(30, 100, new StringBuilder().append(toNumber(t.mDuration)).append(" Ticks").toString(), 0xFF000000, false);
		if (t.mEUt < 0)
			gui.drawText(30, 110, new StringBuilder().append("IN: ").append(toNumber(-t.mEUt * t.mDuration)).append("EU").toString(), 0xFF000000, false);
		else
			gui.drawText(30, 110, new StringBuilder().append("OUT: ").append(toNumber(t.mEUt * t.mDuration)).append("EU").toString(), 0xFF000000, false);
	}


}
