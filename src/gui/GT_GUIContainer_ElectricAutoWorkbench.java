package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.GT_Container_ElectricAutoWorkbench;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_ElectricAutoWorkbench extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_ElectricAutoWorkbench(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_ElectricAutoWorkbench(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "ElectricAutoWorkbench.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null) {
        	int tMode = ((GT_Container_ElectricAutoWorkbench)mContainer).mMode;
        	if (tMode != 0) drawTexturedModalRect(x + 120, y + 40, tMode*18, 166, 18, 18);
        	tMode = ((GT_Container_ElectricAutoWorkbench)mContainer).mThroughPut;
        	drawTexturedModalRect(x + 120, y + 4, tMode*18, 184, 18, 18);
        }
    }
}
