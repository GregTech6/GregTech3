package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.GT_Container_AdvancedTranslocator;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_AdvancedTranslocator extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_AdvancedTranslocator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_AdvancedTranslocator(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	String tINString = "", tOUTString = "";
    	switch (((GT_Container_AdvancedTranslocator)mContainer).mInputSide) {
    	case  0: tINString = "DOWN"; break;
    	case  1: tINString = "UP"; break;
    	case  2: tINString = "NORTH"; break;
    	case  3: tINString = "SOUTH"; break;
    	case  4: tINString = "WEST"; break;
    	case  5: tINString = "EAST"; break;
    	default: tINString = "FAIL"; break;
    	}
    	
    	switch (((GT_Container_AdvancedTranslocator)mContainer).mOutputSide) {
    	case  0: tOUTString = "DOWN"; break;
    	case  1: tOUTString = "UP"; break;
    	case  2: tOUTString = "NORTH"; break;
    	case  3: tOUTString = "SOUTH"; break;
    	case  4: tOUTString = "WEST"; break;
    	case  5: tOUTString = "EAST"; break;
    	default: tOUTString = "FAIL"; break;
    	}
    	
    	fontRenderer.drawString(tINString, 7, 7, 16448255);
    	fontRenderer.drawString(tOUTString, 138, 7, 16448255);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "AdvancedTranslocator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
