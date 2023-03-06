package gregtechmod.api.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * 
 * The GUI-Container I use for all my Basic Machines
 * 
 * As the NEI-RecipeTransferRect Handler can't handle one GUI-Class for all GUIs I needed to produce some dummy-classes which extend this class
 */
public class GT_GUIContainer_BasicMachine extends GT_GUIContainerMetaTile_Machine {
	
	String mTextureFile = "", mName = "";
	
    public GT_GUIContainer_BasicMachine(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID, String aName, String aTextureFile) {
        super(new GT_Container_BasicMachine(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
        mTextureFile = aTextureFile;
        mName = aName;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString(mName, 8,  4, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + mTextureFile);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        if (mContainer != null) {
        	if (((GT_Container_BasicMachine)mContainer).mOutput) drawTexturedModalRect(x + 7, y + 62, 176, 18, 18, 18);
        	if (((GT_Container_BasicMachine)mContainer).mItemTransfer) drawTexturedModalRect(x + 25, y + 62, 176, 36, 18, 18);
        	if (((GT_Container_BasicMachine)mContainer).mSeperatedInputs) drawTexturedModalRect(x + 43, y + 62, 176, 54, 18, 18);
        	drawTexturedModalRect(x + 78, y + 24, 176, 0, ((GT_Container_BasicMachine)mContainer).mProgressBar, 18);
        }
    }
}
