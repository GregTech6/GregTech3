package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.GT_Container_ElectricInventoryManager;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class GT_GUIContainer_ElectricInventoryManager extends GT_GUIContainerMetaTile_Machine {
	
    public GT_GUIContainer_ElectricInventoryManager(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int aID) {
        super(new GT_Container_ElectricInventoryManager(aInventoryPlayer, aTileEntity, aID), aTileEntity, aID);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	mc.renderEngine.bindTexture(GregTech_API.GUI_PATH + "InventoryManager.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        
        if (mContainer != null) {
        	drawTexturedModalRect(x +   4, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[0]*18, 202, 18, 54);
        	drawTexturedModalRect(x +  60, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[1]*18, 202, 18, 54);
        	drawTexturedModalRect(x +  79, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[2]*18, 202, 18, 54);
        	drawTexturedModalRect(x + 135, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[3]*18, 202, 18, 54);
        	
        	drawTexturedModalRect(x +  23, y + 59, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[0]*18+126, 166, 18, 18);
        	drawTexturedModalRect(x +  41, y + 59, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[1]*18+126, 166, 18, 18);
        	drawTexturedModalRect(x +  98, y + 59, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[2]*18+126, 166, 18, 18);
        	drawTexturedModalRect(x + 116, y + 59, ((GT_Container_ElectricInventoryManager)mContainer).mRangeDirections[3]*18+126, 166, 18, 18);
        	
        	drawTexturedModalRect(x +   4, y + 59, 108, (((GT_Container_ElectricInventoryManager)mContainer).mTargetEnergy & 1)!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  60, y + 59, 108, (((GT_Container_ElectricInventoryManager)mContainer).mTargetEnergy & 2)!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  79, y + 59, 108, (((GT_Container_ElectricInventoryManager)mContainer).mTargetEnergy & 4)!=0?184:166, 18, 18);
        	drawTexturedModalRect(x + 135, y + 59, 108, (((GT_Container_ElectricInventoryManager)mContainer).mTargetEnergy & 8)!=0?184:166, 18, 18);
        	
        	int i = -1;
        	
        	drawTexturedModalRect(x +  23, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  23, y + 22, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  23, y + 40, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  41, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  41, y + 22, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  41, y + 40, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  98, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  98, y + 22, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x +  98, y + 40, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x + 116, y +  4, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x + 116, y + 22, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        	drawTexturedModalRect(x + 116, y + 40, ((GT_Container_ElectricInventoryManager)mContainer).mTargetDirections[++i]*18, (((GT_Container_ElectricInventoryManager)mContainer).mTargetInOut&(1<<i))!=0?184:166, 18, 18);
        }
    }
}
