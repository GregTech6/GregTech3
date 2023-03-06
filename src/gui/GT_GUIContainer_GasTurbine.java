package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_Container_BasicTank;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_GasTurbine extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_GasTurbine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_BasicTank(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        fontRenderer.drawString("Gas Turbine", 8, 6, 4210752);
        if (mContainer != null) {
        	fontRenderer.drawString("Gas Amount", 10, 20, 16448255);
        	fontRenderer.drawString(toNumber(((GT_Container_BasicTank)mContainer).mContent), 10, 30, 16448255);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "BasicTank.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
