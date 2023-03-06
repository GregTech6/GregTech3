package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_Recipe;
import gregtechmod.common.GT_ComputercubeDescription;
import gregtechmod.common.containers.GT_Container_ComputerCube;
import gregtechmod.common.tileentities.GT_TileEntity_ComputerCube;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_ComputerCube extends GT_GUIContainerMetaID_Machine {
	
    public GT_GUIContainer_ComputerCube(InventoryPlayer aInventoryPlayer, GT_TileEntity_ComputerCube aTileEntity, int aID) {
        super(new GT_Container_ComputerCube(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    	if (aID == 5) xSize+=50;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	GT_Container_ComputerCube tContainer = (GT_Container_ComputerCube)mContainer;
    	GT_TileEntity_ComputerCube tTileEntity = (GT_TileEntity_ComputerCube)mTileEntity;
        if (tContainer != null) {
	    	switch (tContainer.mID) {
	    	case 0:
	        	fontRenderer.drawString("G.L.A.D.-OS", 64, 61, 16448255);
	            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	    		break;
	    	case 1:
	    		fontRenderer.drawString("Reactorstats:"																				, 7, 108, 16448255);
	    		fontRenderer.drawString(toNumber(tContainer.mEU) + "EU at " + tContainer.mEUOut + "EU/t"							, 7, 120, 16448255);
	    		fontRenderer.drawString("HEM: " + tContainer.mHEM/10000.0F															, 7, 128, 16448255);
	    		fontRenderer.drawString(toNumber(tContainer.mHeat) + "/" + toNumber(tContainer.mMaxHeat) + "Heat"					, 7, 136, 16448255);
	    		fontRenderer.drawString("Explosionpower: " + tContainer.mExplosionStrength/100.0F									, 7, 144, 16448255);
	    		fontRenderer.drawString("Runtime: " + (tContainer.mEUOut>0?((tContainer.mEU/tContainer.mEUOut)/20.0F):0.0F) + "secs", 7, 152, 16448255);
	    		break;
	    	case 2:
	    		fontRenderer.drawString("Scanner", 51, 7, 16448255);
	    		if (tContainer.mProgress == 0) {
	    			fontRenderer.drawString("Can be used to", 51, 24, 16448255);
	    			fontRenderer.drawString("scan Seedbags", 51, 32, 16448255);
	    		} else {
	    			fontRenderer.drawString("Progress:", 51, 24, 16448255);
	    			fontRenderer.drawString(tContainer.mProgress + "%", 51, 32, 16448255);
	    		}
	            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	    		break;
	    	case 3:
	    		fontRenderer.drawString("Centrifuge", 7, 7, 16448255);
	    		fontRenderer.drawString("Recipe: " + (tContainer.mMaxHeat+1) + "/" + GT_Recipe.sCentrifugeRecipes.size(), 7, 23, 16448255);
	    		fontRenderer.drawString("EU: " + toNumber(tContainer.mEU), 7, 31, 16448255);
	    		break;
	    	case 4:
	    		fontRenderer.drawString("Fusionreactor", 7, 7, 16448255);
	    		fontRenderer.drawString("Recipe: " + (tContainer.mMaxHeat+1) + "/" + GT_Recipe.sFusionRecipes.size(), 7, 23, 16448255);
	    		fontRenderer.drawString("Start: " + toNumber(tContainer.mEU) + "EU", 7, 31, 16448255);
	    		fontRenderer.drawString("EU/t: " + toNumber(tContainer.mEUOut), 7, 39, 16448255);
	    		fontRenderer.drawString(toNumber(tContainer.mHeat) + " Ticks", 7, 47, 16448255);
	    		if (tContainer.mEUOut<0)
		    		fontRenderer.drawString("IN: " + toNumber(-tContainer.mEUOut*tContainer.mHeat) + "EU", 7, 55, 16448255);
	    		else
	    			fontRenderer.drawString("OUT: " + toNumber(tContainer.mEUOut*tContainer.mHeat) + "EU", 7, 55, 16448255);
	    		break;
	    	case 5:
	    		if (tContainer.mMaxHeat >= 0 && tContainer.mMaxHeat < GT_ComputercubeDescription.sDescriptions.size())
	    			for (int i = 0; i < GT_ComputercubeDescription.sDescriptions.get(tContainer.mMaxHeat).mDescription.length; i++) {
	    				if (i == 0)
	    					fontRenderer.drawString(GT_ComputercubeDescription.sDescriptions.get(tContainer.mMaxHeat).mDescription[i], 7, 7, 16448255);
	    				else
	    					fontRenderer.drawString(GT_ComputercubeDescription.sDescriptions.get(tContainer.mMaxHeat).mDescription[i], 7, 7+8*i, 16448255);
	    		}
	    		break;
	    	case 6:
	    		fontRenderer.drawString("Electrolyzer", 7, 7, 16448255);
	    		fontRenderer.drawString("Recipe: " + (tContainer.mMaxHeat+1) + "/" + GT_Recipe.sElectrolyzerRecipes.size(), 7, 23, 16448255);
	    		fontRenderer.drawString("EU: " + toNumber(tContainer.mEU), 7, 31, 16448255);
	    		break;
	    	}
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	GT_Container_ComputerCube tContainer = (GT_Container_ComputerCube)mContainer;
    	if (tContainer != null) {
    		mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "ComputerCube" + (((GT_Container_ComputerCube)mContainer).mID) + ".png");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    		
	    	switch (tContainer.mID) {
	    	case 0:

	    		
	    		break;
	    	case 1:
	    		
	    		
	    		break;
	    	case 2:
	    		
	    		
	    		break;
	    	case 3:
	    		
	    		
	    		break;
	    	case 4:
	    		
	    		
	    		break;
	    	case 5:
	            if (tContainer.mExplosionStrength != 0)
	            	drawTexturedModalRect(x + 152, y +  6,  0, 166, 50, 50);
	            //if (tContainer.mMaxHeat == 0)
	            //	drawTexturedModalRect(x + 145, y + 56, 50, 166, 57, 57);
	    		break;
	    	}
    	}
    }
}
