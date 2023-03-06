package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.GT_Container_Centrifuge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_Centrifuge extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_Centrifuge(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_Centrifuge(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 4210752);
        fontRenderer.drawString("Industrial", 110,  4, 4210752);
        fontRenderer.drawString("Centrifuge", 110, 12, 4210752);
        if ((((GT_Container_Centrifuge)mContainer).mDisplayErrorCode & 1) != 0)
        	fontRenderer.drawString("Insufficient Energy Line!", 8, ySize - 94, 4210752);
        else
            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "Centrifuge.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null) {
        	int tScale = ((GT_Container_Centrifuge)mContainer).mProgressScale;
        	drawTexturedModalRect(x + 83, y + 33 - tScale, 193, 33 - tScale, 10, tScale);
        	drawTexturedModalRect(x + 78 - tScale, y + 38, 188 - tScale, 38 , tScale, 10);
        	drawTexturedModalRect(x + 83, y + 53, 193, 53, 10, tScale);
        	drawTexturedModalRect(x + 98, y + 38, 208, 38, tScale, 10);
        }
    }
}
