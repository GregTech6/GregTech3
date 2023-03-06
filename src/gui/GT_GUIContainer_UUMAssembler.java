package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.common.containers.GT_Container_UUMAssembler;
import gregtechmod.common.tileentities.GT_TileEntity_UUMAssembler;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_UUMAssembler extends GT_GUIContainerMetaID_Machine {
	
    public GT_GUIContainer_UUMAssembler(InventoryPlayer aInventoryPlayer, GT_TileEntity_UUMAssembler aTileEntity, int aID) {
        super(new GT_Container_UUMAssembler(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        //draw your Gui here, only thing you need to change is the path
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "UUMAssembler.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
