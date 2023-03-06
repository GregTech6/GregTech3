package gregtechmod.common.render;

import gregtechmod.GT_Mod;
import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_Utility;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

public class GT_Renderer extends RenderPlayer {
	
	public GT_Renderer() {
		super();
		RenderManager.instance.entityRenderMap.remove(EntityPlayer.class);
		RenderManager.instance.entityRenderMap.put(EntityPlayer.class, this);
		setRenderManager(RenderManager.instance);
	}
	
	@Override
	public void renderPlayer(EntityPlayer par1EntityPlayer, double par2, double par4, double par6, float par8, float par9) {
		if (GT_Utility.getFullInvisibility(par1EntityPlayer)) return;
		super.renderPlayer(par1EntityPlayer, par2, par4, par6, par8, par9);
	}
	
	@Override
    public void renderSpecials(EntityPlayer par1EntityPlayer, float par2) {
        if (GT_Utility.getFullInvisibility(par1EntityPlayer)) return;
		
        if (GT_Utility.getPotion(par1EntityPlayer, Integer.valueOf(Potion.invisibility.id))) return;
        
        super.renderSpecials(par1EntityPlayer, par2);
        
        boolean tLoaded = false;
        
        try {
            if (GT_Mod.mShowCapes) {
            	if (GT_Mod.sAdminNames.contains(par1EntityPlayer.username))
	    			tLoaded = loadDownloadableImageTexture("", GregTech_API.TEXTURE_FOLDER + "AdminCape.png");
	    		if (GT_Mod.sPremiumNames.contains(par1EntityPlayer.username))
	    			tLoaded = loadDownloadableImageTexture("", GregTech_API.TEXTURE_FOLDER + "PremiumCape.png");
		    	if (GT_Mod.mBrainTechCapeList.contains(par1EntityPlayer.username))
		    		tLoaded = loadDownloadableImageTexture("https://dl.dropbox.com/u/88825306/BrainTechCape.png", GregTech_API.TEXTURE_FOLDER + "BrainTechCape.png");
	    		if (GT_Mod.mGregTechCapeList.contains(par1EntityPlayer.username))
		    		tLoaded = loadDownloadableImageTexture("https://dl.dropbox.com/u/88825306/GregTechCape.png", GregTech_API.TEXTURE_FOLDER + "GregTechCape.png");
            }
            /**
             * One can't just turn the Main Capes OFF.
             */
    		if (par1EntityPlayer.username.equals("Mr_Brain"))
	    		tLoaded = loadDownloadableImageTexture("https://dl.dropbox.com/u/88825306/MrBrainCape.png", GregTech_API.TEXTURE_FOLDER + "MrBrainCape.png");
    		if (par1EntityPlayer.username.equals("GregoriusT"))
	    		tLoaded = loadDownloadableImageTexture("https://dl.dropbox.com/u/88825306/GregoriusCape.png", GregTech_API.TEXTURE_FOLDER + "GregoriusCape.png");
	    } catch (Throwable e) {
    		
    	}
    	
    	if (tLoaded) {
    		GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double var22 = par1EntityPlayer.field_71091_bM + (par1EntityPlayer.field_71094_bP - par1EntityPlayer.field_71091_bM) * (double)par2 - (par1EntityPlayer.prevPosX + (par1EntityPlayer.posX - par1EntityPlayer.prevPosX) * (double)par2);
            double var23 = par1EntityPlayer.field_71096_bN + (par1EntityPlayer.field_71095_bQ - par1EntityPlayer.field_71096_bN) * (double)par2 - (par1EntityPlayer.prevPosY + (par1EntityPlayer.posY - par1EntityPlayer.prevPosY) * (double)par2);
            double var8 = par1EntityPlayer.field_71097_bO + (par1EntityPlayer.field_71085_bR - par1EntityPlayer.field_71097_bO) * (double)par2 - (par1EntityPlayer.prevPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.prevPosZ) * (double)par2);
            float var10 = par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * par2;
            double var11 = (double)MathHelper.sin(var10 * (float)Math.PI / 180.0F);
            double var13 = (double)(-MathHelper.cos(var10 * (float)Math.PI / 180.0F));
            float var15 = (float)var23 * 10.0F;

            if (var15 < -6.0F)
            {
                var15 = -6.0F;
            }

            if (var15 > 32.0F)
            {
                var15 = 32.0F;
            }

            float var16 = (float)(var22 * var11 + var8 * var13) * 100.0F;
            float var17 = (float)(var22 * var13 - var8 * var11) * 100.0F;

            if (var16 < 0.0F)
            {
                var16 = 0.0F;
            }

            float var18 = par1EntityPlayer.prevCameraYaw + (par1EntityPlayer.cameraYaw - par1EntityPlayer.prevCameraYaw) * par2;
            var15 += MathHelper.sin((par1EntityPlayer.prevDistanceWalkedModified + (par1EntityPlayer.distanceWalkedModified - par1EntityPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * var18;

            if (par1EntityPlayer.isSneaking())
            {
                var15 += 25.0F;
            }

            GL11.glRotatef(6.0F + var16 / 2.0F + var15, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var17 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-var17 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			((ModelBiped)mainModel).renderCloak(0.0625F);
            GL11.glPopMatrix();
        }
    }
}