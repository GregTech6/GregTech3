package gregtechmod.common.tileentities;

import gregtechmod.api.util.GT_LanguageManager;
import net.minecraft.entity.player.EntityPlayer;

public class GT_TileEntity_Supercondensator extends GT_TileEntityMetaID_Machine {
	
    public boolean isFacingValid(int aFacing) 			{return aFacing != getFacing();}
    public boolean isAccessible(EntityPlayer aPlayer)	{return false;}
    public boolean isEnetOutput()      					{return true;}
    public boolean isEnetInput()       					{return true;}
    public boolean isOutputFacing(short aDirection) 	{return aDirection == getFacing();}
    public boolean isInputFacing(short aDirection)  	{return aDirection != getFacing();}
    public int maxEUStore()            					{return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)?10000000:8192;}
    public int maxEUInput()            					{return 1000000;}
    public int maxEUOutput()           					{return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)?1000000:8192;}
    
    @Override public String getInvName() {return GT_LanguageManager.mNameList1[15];}
    
	@Override
	public float getWrenchDropRate() {
		return 1.0F;
	}
    
    @Override
    public int getTexture(int aSide, int aMeta) {
    	if (aSide == getFacing())
    		return 103;
    	else
    		return 108;
    }
}
