package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.GT_Container_BlastFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_BlastFurnace extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_BlastFurnace(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_BlastFurnace(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString("Industrial Blast Furnace", 8,  4, 4210752);
        if (((GT_Container_BlastFurnace)mContainer).mMachine)
        	fontRenderer.drawString("Heat Capacity: " + ((GT_Container_BlastFurnace)mContainer).mHeatCapacity + " K", 8, ySize - 103, 4210752);
        else
        	fontRenderer.drawString("Incomplete Machine Casing!", 8, ySize - 103, 4210752);
        if (((mContainer).mDisplayErrorCode & 1) != 0)
        	fontRenderer.drawString("Insufficient Energy Line!", 8, ySize - 94, 4210752);
        else
            fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "Blast.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null) {
        	int tScale = ((GT_Container_BlastFurnace)mContainer).mProgressScale;
        	drawTexturedModalRect(x + 58, y + 28, 176, 0, tScale, 11);
        }
    }
}
