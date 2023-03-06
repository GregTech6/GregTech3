package gregtechmod.common.items;

import gregtechmod.api.interfaces.IGregTechDeviceInformation;
import gregtechmod.api.items.GT_Generic_Item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import shedar.mods.ic2.nuclearcontrol.api.CardState;
import shedar.mods.ic2.nuclearcontrol.api.ICardWrapper;
import shedar.mods.ic2.nuclearcontrol.api.IPanelDataSource;
import shedar.mods.ic2.nuclearcontrol.api.IRemoteSensor;
import shedar.mods.ic2.nuclearcontrol.api.PanelSetting;
import shedar.mods.ic2.nuclearcontrol.api.PanelString;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_SensorCard_Item extends GT_Generic_Item implements IRemoteSensor, IPanelDataSource {
	
    public static final int DISPLAY_MAIN = 1;
    public static final int DISPLAY_SECOND = 2;
    public static final int DISPLAY_TERTIARY = 4;

    public static final UUID CARD_TYPE = new UUID(0, 41);
    
	public GT_SensorCard_Item(int aID, String aName) {
		super(aID, aName, "Insert into Display Panel");
		setMaxStackSize(1);
	}
	
	private ChunkCoordinates getCoordinates(ItemStack aStack) {
		if (aStack != null) {
	        NBTTagCompound tNBT = aStack.getTagCompound();
	        if (tNBT != null) return new ChunkCoordinates(tNBT.getInteger("x"), tNBT.getInteger("y"), tNBT.getInteger("z"));
		}
		return null;
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
		super.addInformation(aStack, aPlayer, aList, aF3_H);
        ChunkCoordinates target = getCoordinates(aStack);
        if (target == null) {
    		aList.add("Missing Coodinates!");
        } else {
    		aList.add("Device at:");
            aList.add(String.format("x: %d, y: %d, z: %d", target.posX, target.posY, target.posZ));
        }
    }
    
	@Override
	public CardState update(TileEntity panel, ICardWrapper card, int maxRange) {
        ChunkCoordinates target = card.getTarget();
        TileEntity tTileEntity = panel.worldObj.getBlockTileEntity(target.posX, target.posY, target.posZ);
        if (tTileEntity != null && tTileEntity instanceof IGregTechDeviceInformation && ((IGregTechDeviceInformation)tTileEntity).isGivingInformation()) {
            card.setString("mString1", ((IGregTechDeviceInformation)tTileEntity).getMainInfo());
            card.setString("mString2", ((IGregTechDeviceInformation)tTileEntity).getSecondaryInfo());
            card.setString("mString3", ((IGregTechDeviceInformation)tTileEntity).getTertiaryInfo());
            return CardState.OK;
        } else {
            return CardState.NO_TARGET;
        }
	}

	@Override
	public List<PanelString> getStringData(int displaySettings, ICardWrapper card, boolean showLabels) {
        List<PanelString> result = new LinkedList<PanelString>();
        
        if((displaySettings & DISPLAY_MAIN) != 0)  {
            PanelString line = new PanelString();
        	line.textLeft = card.getString("mString1");
            result.add(line);
        }
        if((displaySettings & DISPLAY_SECOND) != 0) {
            PanelString line = new PanelString();
        	line.textLeft = card.getString("mString2");
            result.add(line);
        }
        if((displaySettings & DISPLAY_TERTIARY) != 0) {
            PanelString line = new PanelString();
        	line.textLeft = card.getString("mString3");
            result.add(line);
        }
        return result;
	}

	@Override
    public List<PanelSetting> getSettingsList() {
        List<PanelSetting> result = new ArrayList<PanelSetting>(3);
        result.add(new PanelSetting("Primary", DISPLAY_MAIN, CARD_TYPE));
        result.add(new PanelSetting("Secondary", DISPLAY_SECOND, CARD_TYPE));
        result.add(new PanelSetting("Tertiary", DISPLAY_TERTIARY, CARD_TYPE));
        return result;
    }

	@Override
	public UUID getCardType() {
		return CARD_TYPE;
	}
	
    @Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {}
}
