package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.GT_Container_MagicEnergyAbsorber;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_MagicEnergyAbsorber extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_MagicEnergyAbsorber(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_MagicEnergyAbsorber(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        fontRenderer.drawString("Magic Energy Absorber", 8, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw your Gui here, only thing you need to change is the path
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "MagicAbsorber.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null && ((GT_Container_MagicEnergyAbsorber)mContainer).isActive1) {
        	drawTexturedModalRect(x + 10, y + 35, 176, 0, 16, 16);
        }
        if (mContainer != null && ((GT_Container_MagicEnergyAbsorber)mContainer).isActive2) {
        	drawTexturedModalRect(x + 10, y + 18, 176, 0, 16, 16);
        }
    }
}
