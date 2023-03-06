package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_LanguageManager;
import gregtechmod.common.containers.GT_Container_Item_Destructopack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_Destructopack extends GuiContainer {
	
    public GT_GUIContainer_Destructopack(InventoryPlayer aInventoryPlayer, ItemStack aItem) {
    	super(new GT_Container_Item_Destructopack(aInventoryPlayer, aItem));
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        //draw text and stuff here
        //the parameters for drawString are: string, x, y, color
        fontRenderer.drawString(GT_LanguageManager.mNameListItem[33], 8, 6, 4210752);
        //draws "Inventory" or your regional equivalent
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "Destructopack.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
