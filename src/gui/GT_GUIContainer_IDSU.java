package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.common.containers.GT_Container_IDSU;
import gregtechmod.common.tileentities.GT_TileEntity_IDSU;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_IDSU extends GT_GUIContainerMetaID_Machine {
	
    public GT_GUIContainer_IDSU(InventoryPlayer aInventoryPlayer, GT_TileEntity_IDSU aTileEntity, int aID) {
        super(new GT_Container_IDSU(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	fontRenderer.drawString("I.D.S.U.", 11, 8, 16448255);
        if (mContainer != null) {
        	fontRenderer.drawString("ID: " + ((GT_Container_IDSU)mContainer).mPlayerHash, 11, 16, 16448255);
        	fontRenderer.drawString("EU: " + toNumber(mContainer.mEnergy), 11, 24, 16448255);
        	fontRenderer.drawString("MAX: " + toNumber(mContainer.mStorage), 11, 32, 16448255);
        	fontRenderer.drawString("MAX EU/t IN: " + toNumber(mContainer.mInput), 11, 40, 16448255);
        	fontRenderer.drawString("EU/t OUT: " + toNumber(mContainer.mOutput), 11, 48, 16448255);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "IDSU.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null) {
        	int tScale = mContainer.mEnergy/Math.max(1, mContainer.mStorage/116);
    		drawTexturedModalRect(x + 8, y + 73, 0, 251, tScale, 5);
        }
    }
}
