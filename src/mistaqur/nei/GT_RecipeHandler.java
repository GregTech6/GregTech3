package gregtechmod.mistaqur.nei;

import gregtechmod.api.util.GT_Recipe;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.TemplateRecipeHandler;

public abstract class GT_RecipeHandler extends TemplateRecipeHandler {
	
	public GT_RecipeHandler() {
		API.registerRecipeHandler(this);
		API.registerUsageHandler(this);
	}
	
	public abstract class CachedGT_Recipe extends CachedRecipe {
		public ArrayList<PositionedStack> products;
		public ArrayList<PositionedStack> resources;

		@Override
		public ArrayList<PositionedStack> getIngredients() {
			return resources;
		}

		@Override
		public PositionedStack getResult() {
			return null;
		}
		@Override
		public ArrayList<PositionedStack> getOtherStacks() {
			return products;
		}
	}
	
	@Override
	public abstract String getRecipeName();
	
	public abstract String getRecipeId();
	
	public abstract String getGuiTexture();

	@Override
	public abstract String getOverlayIdentifier();
	
	public abstract ArrayList<GT_Recipe> getRecipeList();
	
	public abstract CachedGT_Recipe getRecipe(GT_Recipe irecipe);

	@Override
	public void drawBackground(GuiContainerManager gui, int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.bindTexture(getGuiTexture());
		gui.drawTexturedModalRect(0, 0, 4, 4, 168, 79);
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}

	@Override
	public void loadTransferRects() {
		
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if(outputId.equals(getRecipeId())) {
			for (GT_Recipe irecipe : getRecipeList()) {
				arecipes.add(getRecipe(irecipe));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		for (GT_Recipe irecipe : getRecipeList()) {
			CachedGT_Recipe recipe = getRecipe(irecipe);
			if (recipe.contains(recipe.products,result)) {
				arecipes.add(recipe);
			}
		}
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		for (GT_Recipe irecipe : getRecipeList()) {
			CachedGT_Recipe recipe = getRecipe(irecipe);
			if (recipe.contains(recipe.resources,ingredient)) {
				arecipes.add(recipe);
			}
		}
	}
	
	public String toNumber(int aNumber) {
		String tString = "";
		boolean temp = true; 
		boolean negative = false;
		if (aNumber < 0) {
		  aNumber *= -1;
		  negative = true;
		}
		for (int i = 1000000000; i > 0; i /= 10) {
		  int tDigit = aNumber / i % 10;
		  if ((temp) && (tDigit != 0)) temp = false;
		  if (!temp) {
			tString = tString + tDigit;
			if (i != 1) for (int j = i; j > 0; j /= 1000) if (j == 1) tString = tString + ",";
		  }
		}
		if (tString.equals("")) tString = "0";
		return negative ? "-" + tString : tString;
	}
}
