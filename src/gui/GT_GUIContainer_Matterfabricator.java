package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.common.containers.GT_Container_Matterfabricator;
import gregtechmod.common.tileentities.GT_TileEntity_Matterfabricator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_Matterfabricator extends GT_GUIContainerMetaID_Machine {
	
    public GT_GUIContainer_Matterfabricator(InventoryPlayer aInventoryPlayer, GT_TileEntity_Matterfabricator aTileEntity, int aID) {
        super(new GT_Container_Matterfabricator(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString("Matterfabricator", 7,  4, 4210752);
    	fontRenderer.drawString(((GT_Container_Matterfabricator)mContainer).mPercentualProgress + "%", 128, 40, 16448255);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "Matterfabricator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null) {
        	int tScale = mContainer.mEnergy/Math.max(1, mContainer.mStorage/160);
    		drawTexturedModalRect(x + 8, y + 60, 0, 251, tScale, 5);
        }
    }
}
