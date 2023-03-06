package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.GT_Container_FusionComputer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_FusionComputer extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_FusionComputer(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_FusionComputer(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "FusionComputer.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null) {
        	int tScale = mContainer.mEnergy/1088435;
    		drawTexturedModalRect(x + 5, y + 156, 0, 251, tScale, 5);
        }
    }
}
