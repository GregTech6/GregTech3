package gregtechmod.common.blocks;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.interfaces.IDebugableBlock;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.api.metatileentity.BaseMetaTileEntity;
import gregtechmod.api.util.GT_Log;
import gregtechmod.api.util.GT_ModHandler;
import gregtechmod.api.util.GT_OreDictUnificator;
import gregtechmod.common.items.GT_MetaItem_Component;
import gregtechmod.common.items.GT_MetaItem_Material;
import gregtechmod.common.tileentities.GT_TileEntityMetaID_Machine;
import gregtechmod.common.tileentities.GT_TileEntity_AESU;
import gregtechmod.common.tileentities.GT_TileEntity_ChargeOMat;
import gregtechmod.common.tileentities.GT_TileEntity_ComputerCube;
import gregtechmod.common.tileentities.GT_TileEntity_IDSU;
import gregtechmod.common.tileentities.GT_TileEntity_LESU;
import gregtechmod.common.tileentities.GT_TileEntity_Lightningrod;
import gregtechmod.common.tileentities.GT_TileEntity_Matterfabricator;
import gregtechmod.common.tileentities.GT_TileEntity_PlayerDetector;
import gregtechmod.common.tileentities.GT_TileEntity_Sonictron;
import gregtechmod.common.tileentities.GT_TileEntity_Supercondensator;
import gregtechmod.common.tileentities.GT_TileEntity_Superconductor;
import gregtechmod.common.tileentities.GT_TileEntity_UUMAssembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GT_BlockMetaID_Machine extends BlockContainer implements IDebugableBlock {
	public Icon mIcons[] = new Icon[320];
	
	public GT_BlockMetaID_Machine(int aID) {
        super(aID, new GT_MachineMaterial());
        setHardness(10.0F);
        setResistance(10.0F);
        setUnlocalizedName("BlockMetaID_Machine");
        setStepSound(Block.soundMetalFootstep);
		setCreativeTab(GregTech_API.TAB_GREGTECH);
        for (int i = 0; i < 16; i++) MinecraftForge.setBlockHarvestLevel(this, i, "wrench", 1);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister aIconRegister) {
		GregTech_API.FAIL_ICON = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + (GregTech_API.sConfiguration.system?"troll":getUnlocalizedName() + "/failed"));
		
		mIcons[   0] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine");
		mIcons[   2] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_logo");
		mIcons[   3] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_logo_bottom");
		mIcons[  11] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_uum");
		mIcons[  12] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_lesu");
		mIcons[  13] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_lesu_lv_out");
		mIcons[  14] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_lesu_mv_out");
		mIcons[  15] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_lesu_hv_out");
		mIcons[  30] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_matterfab");
		mIcons[  31] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_matterfab_active");
		mIcons[  70] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_grinder");
		mIcons[  71] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_connector");
		mIcons[  72] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_fire");
		mIcons[  73] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_implosion");
		mIcons[  74] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_itnt");
		mIcons[  75] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_saw");
		mIcons[  76] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_water");
		mIcons[  77] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_ice");
		mIcons[  78] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_oil");
		mIcons[ 109] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pump");
		mIcons[ 110] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_miningpipe");
		mIcons[ 111] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_vent_rotating");
		mIcons[ 112] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_green");
		mIcons[ 113] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_red");
		mIcons[ 114] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_crafting");
		mIcons[ 115] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_cobblegen");
		mIcons[ 116] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_blue");
		mIcons[ 117] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_yellow");
		mIcons[ 118] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_cropharvesting");
		mIcons[ 119] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_scrapbox");
		mIcons[ 120] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_green_redstone");
		mIcons[ 121] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_red_redstone");
		mIcons[ 122] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_crafting_redstone");
		mIcons[ 123] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_cobblegen_redstone");
		mIcons[ 124] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_blue_redstone");
		mIcons[ 125] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_yellow_redstone");
		mIcons[ 126] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_cropharvesting_redstone");
		mIcons[ 127] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_scrapbox_redstone");
		mIcons[ 212] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_magenta");
		mIcons[ 213] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_cyan");
		mIcons[ 220] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_magenta_redstone");
		mIcons[ 221] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_pipe_cyan_redstone");
		mIcons[ 258] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_vent");
		mIcons[ 310] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_mv_out");
		
		mIcons[ 128] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_back");
		mIcons[ 129] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_back");
		mIcons[ 130] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_back");
		mIcons[ 131] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_back");
		mIcons[ 132] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_back");
		mIcons[ 133] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_back");
		mIcons[ 134] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_back");
		mIcons[ 135] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_back");
		mIcons[ 136] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_back_redstone");
		mIcons[ 137] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_back_redstone");
		mIcons[ 138] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_back_redstone");
		mIcons[ 139] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_back_redstone");
		mIcons[ 140] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_back_redstone");
		mIcons[ 141] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_back_redstone");
		mIcons[ 142] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_back_redstone");
		mIcons[ 143] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_back_redstone");
		mIcons[ 144] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_right");
		mIcons[ 145] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_right");
		mIcons[ 146] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_right");
		mIcons[ 147] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_right");
		mIcons[ 148] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_right");
		mIcons[ 149] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_right");
		mIcons[ 150] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_right");
		mIcons[ 151] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_right");
		mIcons[ 152] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_right_redstone");
		mIcons[ 153] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_right_redstone");
		mIcons[ 154] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_right_redstone");
		mIcons[ 155] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_right_redstone");
		mIcons[ 156] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_right_redstone");
		mIcons[ 157] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_right_redstone");
		mIcons[ 158] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_right_redstone");
		mIcons[ 159] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_right_redstone");
		mIcons[ 160] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_down");
		mIcons[ 161] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_down");
		mIcons[ 162] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_down");
		mIcons[ 163] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_down");
		mIcons[ 164] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_down");
		mIcons[ 165] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_down");
		mIcons[ 166] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_down");
		mIcons[ 167] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_down");
		mIcons[ 168] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_down_redstone");
		mIcons[ 169] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_down_redstone");
		mIcons[ 170] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_down_redstone");
		mIcons[ 171] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_down_redstone");
		mIcons[ 172] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_down_redstone");
		mIcons[ 173] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_down_redstone");
		mIcons[ 174] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_down_redstone");
		mIcons[ 175] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_down_redstone");
		mIcons[ 176] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_left");
		mIcons[ 177] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_left");
		mIcons[ 178] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_left");
		mIcons[ 179] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_left");
		mIcons[ 180] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_left");
		mIcons[ 181] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_left");
		mIcons[ 182] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_left");
		mIcons[ 183] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_left");
		mIcons[ 184] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_left_redstone");
		mIcons[ 185] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_left_redstone");
		mIcons[ 186] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_left_redstone");
		mIcons[ 187] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_left_redstone");
		mIcons[ 188] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_left_redstone");
		mIcons[ 189] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_left_redstone");
		mIcons[ 190] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_left_redstone");
		mIcons[ 191] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_left_redstone");
		mIcons[ 192] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_up");
		mIcons[ 193] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_up");
		mIcons[ 194] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_up");
		mIcons[ 195] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_up");
		mIcons[ 196] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_up");
		mIcons[ 197] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_up");
		mIcons[ 198] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_up");
		mIcons[ 199] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_up");
		mIcons[ 200] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_translocator_up_redstone");
		mIcons[ 201] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advtranslocator_up_redstone");
		mIcons[ 202] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_buffer_up_redstone");
		mIcons[ 203] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_largebuffer_up_redstone");
		mIcons[ 204] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_advbuffer_up_redstone");
		mIcons[ 205] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_inserter_up_redstone");
		mIcons[ 206] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_sorter_up_redstone");
		mIcons[ 207] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_regulator_up_redstone");
		
		mIcons[   4] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_square");
		mIcons[   6] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_lightning");
		mIcons[   7] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_crafting");
		mIcons[   8] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_logo");
		mIcons[  23] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_grid");
		mIcons[  24] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_grid_active");
		mIcons[  45] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_scanning");
		mIcons[  46] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_radioactive");
		mIcons[  47] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen");
		mIcons[  48] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_random1");
		mIcons[  49] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_random2");
		mIcons[  50] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_random3");
		mIcons[  55] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_data");
		mIcons[  63] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_text");
		mIcons[  68] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_inactive");
		mIcons[  69] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/adv_machine_screen_active");
		
		mIcons[  16] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine");
		mIcons[  17] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine_hv_out");
		mIcons[  18] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine_ev_out");
		mIcons[  19] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine_glass");
		mIcons[  20] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine_glass_yellow");
		mIcons[  21] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine_rod");
		mIcons[  22] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine_dimensional");
		mIcons[ 311] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/high_adv_machine_qev_out");
		
		mIcons[  29] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top");
		mIcons[  37] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_screen_data");
		mIcons[  41] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_centrifuge");
		mIcons[  42] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_centrifuge_active1");
		mIcons[  43] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_centrifuge_active2");
		mIcons[  44] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_centrifuge_active3");
		mIcons[  52] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_redstone_main_off");
		mIcons[  53] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_redstone_main_on");
		mIcons[  57] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_redstone_off");
		mIcons[  58] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_redstone_on");
		mIcons[  79] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_pipe");
		mIcons[  80] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_dieselmotor");
		mIcons[  81] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_turbine");
		mIcons[  83] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_glass");
		mIcons[  84] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_glass_orange");
		mIcons[  85] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_glass_gauge");
		mIcons[  86] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_glass_cyan");
		mIcons[  87] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_glass_magenta");
		mIcons[  88] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_glass_yellow");
		mIcons[  89] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_dragonegg");
		mIcons[  90] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_crystal_cyan");
		mIcons[  91] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_crystal_yellow");
		mIcons[  92] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_crystal");
		mIcons[ 228] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_macerator");
		mIcons[ 229] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_macerator_active");
		mIcons[ 230] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_gauge");
		mIcons[ 231] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_gauge_green");
		mIcons[ 232] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_recycler");
		mIcons[ 233] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_recycler_active");
		mIcons[ 234] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_gauge_brown");
		mIcons[ 235] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_wiremill");
		mIcons[ 236] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_wiremill_active");
		mIcons[ 237] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_circuitry");
		mIcons[ 259] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_vent_rotating");
		mIcons[ 281] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_dieselmotor_active");
		mIcons[ 282] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_top_lv_out");
		
		mIcons[  32] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_bottom");
		mIcons[  38] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_bottom_pipe");
		mIcons[  54] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_bottom_redstone_main_off");
		mIcons[  56] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_bottom_redstone_main_on");
		mIcons[  59] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_bottom_redstone_off");
		mIcons[  60] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_bottom_redstone_on");
		mIcons[  82] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_bottom_turbine");
		
		mIcons[   1] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_microwave");
		mIcons[   5] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_microwave_active");
		mIcons[  25] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_centrifuge");
		mIcons[  26] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_centrifuge_active1");
		mIcons[  27] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_centrifuge_active2");
		mIcons[  28] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_centrifuge_active3");
		mIcons[  33] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_printer");
		mIcons[  34] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_printer_active");
		mIcons[  36] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_pipe");
		mIcons[  40] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side");
		mIcons[  61] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_redstone_off");
		mIcons[  62] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_redstone_on");
		mIcons[  64] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_electrolyzer");
		mIcons[  65] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_electrolyzer_active");
		mIcons[  66] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_chemical");
		mIcons[  67] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_chemical_active");
		mIcons[  93] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_redstone_main_off");
		mIcons[  94] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_redstone_main_on");
		mIcons[ 214] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_safe");
		mIcons[ 215] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_compartment");
		mIcons[ 216] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_shelf");
		mIcons[ 217] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_shelf_paper");
		mIcons[ 218] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_shelf_books");
		mIcons[ 219] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_shelf_cans");
		mIcons[ 222] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_desk");
		mIcons[ 223] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_cabinet");
		mIcons[ 224] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_controlpanel");
		mIcons[ 225] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_controlpanel_active");
		mIcons[ 226] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_gauge_red");
		mIcons[ 227] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_gauge_green");
		mIcons[ 238] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_bender");
		mIcons[ 239] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_bender_active");
		mIcons[ 240] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_pump");
		mIcons[ 241] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_pump_active");
		mIcons[ 242] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_gauge");
		mIcons[ 243] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_gauge_blue");
		mIcons[ 244] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_macerator");
		mIcons[ 245] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_macerator_active");
		mIcons[ 246] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_extractor");
		mIcons[ 247] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_extractor_active");
		mIcons[ 248] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_compressor");
		mIcons[ 249] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_compressor_active");
		mIcons[ 250] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_furnace");
		mIcons[ 251] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_furnace_active");
		mIcons[ 252] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_alloysmelter");
		mIcons[ 253] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_alloysmelter_active");
		mIcons[ 254] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_canner");
		mIcons[ 255] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_canner_active");
		mIcons[ 256] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_pulverizer");
		mIcons[ 257] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_side_pulverizer_active");

		mIcons[  96] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_0");
		mIcons[  97] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_1");
		mIcons[  98] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_2");
		mIcons[  99] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_3");
		mIcons[ 100] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_4");
		mIcons[ 101] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_5");
		mIcons[ 102] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_6");
		mIcons[ 103] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_7");
		mIcons[ 104] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_8");
		mIcons[ 105] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_9");
		mIcons[ 106] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_10");
		mIcons[ 107] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_11");
		mIcons[ 108] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/superconductor_in");
		
		mIcons[   9] = mIcons[   0];
		
		mIcons[  10] = Block.planks.getBlockTextureFromSideAndMetadata(0, 0); //                	   wood
		mIcons[ 208] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/wood_shelf");
		mIcons[ 209] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/wood_shelf_paper");
		mIcons[ 210] = Block.bookShelf.getBlockTextureFromSideAndMetadata(2, 0); //               	   wood_shelf_books
		mIcons[ 211] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/wood_shelf_cans");
		
		mIcons[  35] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/audio_out_active");
		mIcons[  39] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/audio_out");
		mIcons[ 293] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/audio");
		
		mIcons[ 291] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/lamp_off");
		mIcons[ 292] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/lamp_on");
		
		mIcons[ 264] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_0");
		mIcons[ 265] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_1");
		mIcons[ 266] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_2");
		mIcons[ 267] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_3");
		mIcons[ 268] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_4");
		mIcons[ 269] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_5");
		mIcons[ 270] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_6");
		mIcons[ 271] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_7");
		mIcons[ 272] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_8");
		mIcons[ 273] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_9");
		mIcons[ 274] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_10");
		mIcons[ 275] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_11");
		mIcons[ 276] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_12");
		mIcons[ 277] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_13");
		mIcons[ 278] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_14");
		mIcons[ 279] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_redstone_o_meter_15");
		
		mIcons[ 294] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_0");
		mIcons[ 295] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_1");
		mIcons[ 296] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_2");
		mIcons[ 297] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_3");
		mIcons[ 298] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_4");
		mIcons[ 299] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_5");
		mIcons[ 300] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_6");
		mIcons[ 301] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_7");
		mIcons[ 302] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_8");
		mIcons[ 303] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_9");
		mIcons[ 304] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_10");
		mIcons[ 305] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_11");
		mIcons[ 306] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_12");
		mIcons[ 307] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_13");
		mIcons[ 308] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_14");
		mIcons[ 309] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/machine_button_15");
		
		mIcons[  51] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover");
		mIcons[  95] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_noredstone");
		mIcons[ 260] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_redstone_emitter");
		mIcons[ 261] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_redstone_controller");
		mIcons[ 262] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_redstone_eumeter");
		mIcons[ 263] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_conveyor");
		mIcons[ 280] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_valve");
		mIcons[ 283] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_itemvalve");
		mIcons[ 284] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_drain");
		mIcons[ 285] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_solar_0");
		mIcons[ 286] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_redstone_itemmeter");
		mIcons[ 287] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_redstone_liquidmeter");
		mIcons[ 288] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_datacircuit");
		mIcons[ 289] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_energycircuit");
		mIcons[ 290] = aIconRegister.registerIcon(GregTech_API.TEXTURE_PATH + getUnlocalizedName() + "/cover_crafting");
		
		GregTech_API.sCovers.put(-1, mIcons[51]);
		GregTech_API.sCovers.put(-2, mIcons[95]);
		
		GregTech_API.registerCover(GT_MetaItem_Material .instance.getStack(15, 1), mIcons[ 10]);
		
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(30, 1), mIcons[260]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(31, 1), mIcons[261]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(15, 1), mIcons[262]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 0, 1), mIcons[289]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 1, 1), mIcons[288]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 4, 1), mIcons[ 48]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 5, 1), mIcons[263]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 6, 1), mIcons[280]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 7, 1), mIcons[285]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 8, 1), mIcons[283]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack( 9, 1), mIcons[284]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(10, 1), mIcons[287]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(11, 1), mIcons[286]);
		GregTech_API.registerCover(GT_MetaItem_Component.instance.getStack(64, 1), mIcons[290]);
		
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateIron")			, Block.blockSteel.getBlockTextureFromSide(0));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateGold")			, Block.blockGold.getBlockTextureFromSide(0));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateDiamond")			, Block.blockDiamond.getBlockTextureFromSide(0));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateEmerald")			, Block.blockEmerald.getBlockTextureFromSide(0));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateRedstone")		, Block.blockRedstone.getBlockTextureFromSide(0));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateTin")				, Block.blocksList[GT_ModHandler.getIC2Item("tinBlock", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("tinBlock", 1).getItemDamage()));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateCopper")			, Block.blocksList[GT_ModHandler.getIC2Item("copperBlock", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("copperBlock", 1).getItemDamage()));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateBronze")			, Block.blocksList[GT_ModHandler.getIC2Item("bronzeBlock", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("bronzeBlock", 1).getItemDamage()));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateUranium")			, Block.blocksList[GT_ModHandler.getIC2Item("uraniumBlock", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("uraniumBlock", 1).getItemDamage()));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateRefinedIron")		, Block.blocksList[GT_ModHandler.getIC2Item("machine", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("machine", 1).getItemDamage()));
		GregTech_API.registerCover(GT_OreDictUnificator.getOres("plateAlloyAdvanced")	, Block.blocksList[GT_ModHandler.getIC2Item("reinforcedStone", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("reinforcedStone", 1).getItemDamage()));
		
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorPlating", 1)		, Block.blocksList[GT_ModHandler.getIC2Item("reactorChamber", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("reactorChamber", 1).getItemDamage()));
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorPlatingHeat", 1)	, Block.blocksList[GT_ModHandler.getIC2Item("reactorChamber", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("reactorChamber", 1).getItemDamage()));
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorPlatingExplosive",1), Block.blocksList[GT_ModHandler.getIC2Item("reactorChamber", 1).itemID].getBlockTextureFromSideAndMetadata(2, GT_ModHandler.getIC2Item("reactorChamber", 1).getItemDamage()));
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVent", 1)			, mIcons[259]);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentCore", 1)		, mIcons[259]);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentGold", 1)		, mIcons[111]);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentSpread",1)		, mIcons[258]);
		GregTech_API.registerCover(GT_ModHandler.getIC2Item("reactorVentDiamond",1)		, mIcons[111]);
		
		try {
			for (gregtechmod.api.util.GT_BaseCrop tCrop : gregtechmod.api.util.GT_BaseCrop.sCropList) {
				tCrop.registerSprites(aIconRegister);
			}
		} catch(Throwable e) {}
		
		for (int i = 0; i < mIcons.length; i++) if (mIcons[i] == null) GT_Log.out.println("Free Machine-Icon-ID at: " + i);
    }
	
	@Override
    public Icon getBlockTexture(IBlockAccess aIBlockAccess, int aX, int aY, int aZ, int aSide) {
		byte tMeta = (byte)aIBlockAccess.getBlockMetadata(aX, aY, aZ);
		TileEntity tTileEntity = aIBlockAccess.getBlockTileEntity(aX, aY, aZ);
		
		Icon rIcon = null;
		int tIndex = -1;
		
		if (tTileEntity == null) {
			tIndex = -2;
		} else {
			if (tTileEntity instanceof GT_TileEntityMetaID_Machine) {
				tIndex = ((GT_TileEntityMetaID_Machine)tTileEntity).getTexture(aSide, tMeta);
			} else if (tTileEntity instanceof BaseMetaTileEntity && ((BaseMetaTileEntity)tTileEntity).getMetaTileEntity() != null) {
				rIcon = ((BaseMetaTileEntity)tTileEntity).getTextureIcon((byte)aSide, tMeta);
				if (rIcon == null) tIndex = ((BaseMetaTileEntity)tTileEntity).getTextureIndex((byte)aSide, tMeta);
			} else {
				tIndex = -2;
			}
		}
		
		if (rIcon == null) {
			if (tIndex >= 0 && tIndex < mIcons.length) {
				rIcon = mIcons[tIndex];
				if (rIcon == null) System.err.println("GregTech: Missing Texture for index: " + tIndex);
			} else {
				if (tIndex != -2)
					System.err.println("GregTech: Invalid Texture Index: " + tIndex);
			}
		}
		
		if (GregTech_API.sConfiguration.system || rIcon == null) rIcon = GregTech_API.FAIL_ICON;
		return rIcon;
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int aSide, int aMeta) {
		if (aMeta < 0 || aMeta >= GregTech_API.MAXIMUM_METATILE_IDS) return GregTech_API.FAIL_ICON;
		
		Icon rIcon = null;
		int tIndex = -1;
		
		try {
			if (aMeta > 0 && aMeta < 16) {
				tIndex = ((GT_TileEntityMetaID_Machine)createTileEntity(null, aMeta)).getTexture(aSide, aMeta);
			} else if (GregTech_API.mMetaTileList[aMeta] != null) {
				rIcon = GregTech_API.mMetaTileList[aMeta].getTextureIcon((byte)aSide, (byte)4, true, false);
				if (rIcon == null) tIndex = GregTech_API.mMetaTileList[aMeta].getTextureIndex((byte)aSide, (byte)4, true, false);
			}
		} catch(Throwable e) {
			tIndex = -2;
		}
		
		if (rIcon == null && tIndex >= 0 && tIndex < mIcons.length) rIcon = mIcons[tIndex];
		if (GregTech_API.sConfiguration.system || rIcon == null) rIcon = GregTech_API.FAIL_ICON;
		return rIcon;
	}
	
	@Override
    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
		if (tTileEntity instanceof BaseMetaTileEntity) {
			if (((BaseMetaTileEntity)tTileEntity).privateAccess()) return -1;
			if (((BaseMetaTileEntity)tTileEntity).unbreakable()) return -1;
		}
		if (tTileEntity instanceof GT_TileEntityMetaID_Machine) {
			if (((GT_TileEntityMetaID_Machine)tTileEntity).ownerControl()) return -1;
		}
		return super.getBlockHardness(aWorld, aX, aY, aZ);
    }
	
	@Override
    public float getPlayerRelativeBlockHardness(EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ) {
        TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
		if (tTileEntity instanceof BaseMetaTileEntity) {
			if (((BaseMetaTileEntity)tTileEntity).privateAccess()) return -1;
			if (((BaseMetaTileEntity)tTileEntity).unbreakable()) return -1;
		}
		if (tTileEntity instanceof GT_TileEntityMetaID_Machine) {
			if (((GT_TileEntityMetaID_Machine)tTileEntity).ownerControl()) return -1;
		}
		return super.getPlayerRelativeBlockHardness(aPlayer, aWorld, aX, aY, aZ);
    }
	
	@Override
	public boolean onBlockActivated(World aWorld, int aX, int aY, int aZ, EntityPlayer aPlayer, int aSide, float par1, float par2, float par3) {
		TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
		
	    if (tTileEntity == null || aPlayer.isSneaking()) return false;

		if (tTileEntity instanceof BaseMetaTileEntity) {
		    if (!aWorld.isRemote && !((BaseMetaTileEntity)tTileEntity).isUseableByPlayer(aPlayer)) return true;
		    ((BaseMetaTileEntity)tTileEntity).onRightclick(aPlayer, (byte)aSide, par1, par2, par3);
		    return true;
		}
		
	    if (aWorld.isRemote) return true;
	    
		if (tTileEntity instanceof GT_TileEntityMetaID_Machine) {
		    if (!((GT_TileEntityMetaID_Machine)tTileEntity).isUseableByPlayer(aPlayer)) return true;
		    
		    int tMetaData = aWorld.getBlockMetadata(aX, aY, aZ), tGuiID = tMetaData;
		    
		    if (tMetaData == 4) tGuiID = getComputerCubeGUIID(tTileEntity);
		    if (tMetaData == 13)
		    	((GT_TileEntity_PlayerDetector)tTileEntity).rightClick(aPlayer);
		    else
		    	aPlayer.openGui(GregTech_API.gregtechmod, tGuiID, aWorld, aX, aY, aZ);
		    return true;
		}
		
		return true;
	}
	
	@Override
	public void onBlockClicked(World aWorld, int aX, int aY, int aZ, EntityPlayer aPlayer) {
		TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
	    if (tTileEntity != null) {
	    	if (tTileEntity instanceof GT_TileEntityMetaID_Machine) {
	    		((GT_TileEntityMetaID_Machine)tTileEntity).onLeftclick(aPlayer);
	    	} else if (tTileEntity instanceof BaseMetaTileEntity) {
		    	((BaseMetaTileEntity)tTileEntity).onLeftclick(aPlayer);
		    }
		}
	}
	
	public static int getComputerCubeGUIID(TileEntity aTileEntity) {
	    switch (((GT_TileEntity_ComputerCube)aTileEntity).mMode) {
	    case 1:
	    	return 32;
	    case 2:
	    	return 34;
	    case 3:
	    	return 35;
	    case 4:
	    	return 36;
	    case 5:
	    	return 37;
	    case 6:
	    	return 38;
	    default:
	    	return 4;
	    }
	}
	
	@Override
    public int getDamageValue(World world, int x, int y, int z) {
	    TileEntity tTileEntity = world.getBlockTileEntity(x, y, z);
	    
	    if (tTileEntity != null && tTileEntity instanceof IGregTechTileEntity) {
        	return ((IGregTechTileEntity)tTileEntity).getMetaTileID();
	    }
	    
	    return 0;
    }

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
    	ret.add(GT_MetaItem_Component.instance.getStack(22, 1));
        return ret;
    }
	
	@Override
	public void breakBlock(World aWorld, int aX, int aY, int aZ, int par5, int par6) {
	    dropItems(aWorld, aX, aY, aZ);
		GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
	    super.breakBlock(aWorld, aX, aY, aZ, par5, par6);
	}
	
	private void dropItems(World aWorld, int aX, int aY, int aZ){
	    Random rand = new Random();
	
	    TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
	    
	    if (tTileEntity == null) return;
	    
	    if (tTileEntity instanceof IGregTechTileEntity) {
	    	IGregTechTileEntity tGregTechTileEntity = (IGregTechTileEntity)tTileEntity;
	    	
    		for (byte i = 0; i < 6; i++) {
    			ItemStack tStack = tGregTechTileEntity.getCoverItemAtSide(i);
    			if (tStack != null) {
    				aWorld.spawnEntityInWorld(new EntityItem(aWorld, aX+0.5, aY+0.5, aZ+0.5, tStack));
    			}
    		}
    		
	        for (int i = 0; i < tGregTechTileEntity.getSizeInventory(); i++) {
	            ItemStack item = tGregTechTileEntity.getStackInSlot(i);
	
	            if (item != null && item.stackSize > 0 && tGregTechTileEntity.isValidSlot(i)) {
	                float rx = rand.nextFloat() * 0.8F + 0.1F;
		            float ry = rand.nextFloat() * 0.8F + 0.1F;
		            float rz = rand.nextFloat() * 0.8F + 0.1F;
		            
		            EntityItem entityItem = new EntityItem(aWorld, aX + rx, aY + ry, aZ + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
		            if (item.hasTagCompound()) {
		                entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
		            }
		            
		            float factor = 0.05F;
		            entityItem.motionX = rand.nextGaussian() * factor;
		            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
		            entityItem.motionZ = rand.nextGaussian() * factor;
		            aWorld.spawnEntityInWorld(entityItem);
		            item.stackSize = 0;
		        }
		    }
	    }
	}
	
	@Override
    public int isProvidingWeakPower(IBlockAccess var1, int aX, int aY, int aZ, int aSide) {
		if (aSide < 0 || aSide > 5) return 0;
		
        TileEntity tTileEntity = var1.getBlockTileEntity(aX, aY, aZ);
        
        if (tTileEntity != null && tTileEntity instanceof IGregTechTileEntity)
            return ((IGregTechTileEntity)tTileEntity).getOutputRedstoneSignal((byte)ForgeDirection.getOrientation(aSide).getOpposite().ordinal());
        
        return 0;
    }
	
	@Override
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        TileEntity tTileEntity = world.getBlockTileEntity(x, y, z);
        
        if (GregTech_API.sMachineNonWrenchExplosions && tTileEntity != null && (player == null || !player.capabilities.isCreativeMode))
            if (tTileEntity instanceof GT_TileEntityMetaID_Machine)
            	((GT_TileEntityMetaID_Machine)tTileEntity).doEnergyExplosion();
            else
            	if (tTileEntity instanceof BaseMetaTileEntity)
            		((BaseMetaTileEntity)tTileEntity).doEnergyExplosion();
        
        return world.setBlock(x, y, z, 0, 0, 3);
    }
    
	@Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int par5, float chance, int par7) {
        if (!world.isRemote && GregTech_API.sMachineNonWrenchExplosions) {
            TileEntity tTileEntity = world.getBlockTileEntity(x, y, z);
            
            if (tTileEntity != null && chance < 1.0F) {
                if (tTileEntity instanceof GT_TileEntityMetaID_Machine)
                	((GT_TileEntityMetaID_Machine)tTileEntity).doEnergyExplosion();
                else
                	if (tTileEntity instanceof BaseMetaTileEntity)
                		((BaseMetaTileEntity)tTileEntity).doEnergyExplosion();
            } else {
            	super.dropBlockAsItemWithChance(world, x, y, z, par5, chance, par7);
            }
        }
    }
    
	@Override
    public boolean canConnectRedstone(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        return true;
    }

	@Override
    public boolean canProvidePower() {
        return true;
    }
	
	@Override
    public boolean isBlockNormalCube(World world, int x, int y, int z)  {
        return false;
    }

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

	@Override
    public boolean canBeReplacedByLeaves(World world, int x, int y, int z) {
        return false;
    }
    
	@Override
	public TileEntity createNewTileEntity(World aWorld) {
		return GregTech_API.constructBaseMetaTileEntity();
	}
	
	@Override
	public TileEntity createTileEntity(World aWorld, int aMeta) {
		switch(aMeta) {
		case  0: return GregTech_API.constructBaseMetaTileEntity();
		case  2: return new GT_TileEntity_Lightningrod();
		case  4: return new GT_TileEntity_ComputerCube();
		case  5: return new GT_TileEntity_UUMAssembler();
		case  6: return new GT_TileEntity_Sonictron();
		case  7: return new GT_TileEntity_LESU();
		case  8: return new GT_TileEntity_IDSU();
		case  9: return new GT_TileEntity_AESU();
		case 10: return new GT_TileEntity_ChargeOMat();
		case 12: return new GT_TileEntity_Superconductor();
		case 13: return new GT_TileEntity_PlayerDetector();
		case 14: return new GT_TileEntity_Matterfabricator();
		case 15: return new GT_TileEntity_Supercondensator();
		default: return null;
		}
	}
	
	@Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return 30.0F;
    }
	
	@Override @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 1; i < GregTech_API.mMetaTileList.length; ++i)
        	if (i < 16 && i != 1 && i != 3 && i != 11)
        		par3List.add(new ItemStack(par1, 1, i));
        	else
        		if (i > 15 && GregTech_API.mMetaTileList[i] != null)
        			par3List.add(new ItemStack(par1, 1, i));
    }
    
	@Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
		return false;
	}
	
	@Override
    public void onBlockPlacedBy(World aWorld, int aX, int aY, int aZ, EntityLiving aPlayer, ItemStack aStack) {
		TileEntity tTileEntity = aWorld.getBlockTileEntity(aX, aY, aZ);
		
		if (tTileEntity == null) return;
		
		if (tTileEntity instanceof IGregTechTileEntity) {
			IGregTechTileEntity var6 = (IGregTechTileEntity)tTileEntity;
	        if (aPlayer == null) {
	            var6.setFrontFacing((byte)1);
	        } else {
	            int var7 = MathHelper.floor_double((double)(aPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	            int var8 = Math.round(aPlayer.rotationPitch);
	            if (var8 >= 65 && var6.wrenchCanSetFacing(null, 1)) {
	                var6.setFrontFacing((byte)1);
	            } else if (var8 <= -65 && var6.wrenchCanSetFacing(null, 0)) {
	                var6.setFrontFacing((byte)0);
	            } else {
	                switch (var7) {
	                case 0: var6.setFrontFacing((byte)2); break;
	                case 1: var6.setFrontFacing((byte)5); break;
	                case 2: var6.setFrontFacing((byte)3); break;
	                case 3: var6.setFrontFacing((byte)4); break;
	                }
	            }
	        }
		}
    }
	
	@Override
    public void onBlockAdded(World aWorld, int aX, int aY, int aZ) {
		if (GregTech_API.isMachineBlock(blockID, aWorld.getBlockMetadata(aX, aY, aZ))) {
			GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
		}
	}
	
	@Override
	public ArrayList<String> getDebugInfo(EntityPlayer aPlayer, int aX, int aY, int aZ, int aLogLevel) {
		TileEntity tTileEntity = aPlayer.worldObj.getBlockTileEntity(aX, aY, aZ);
		if (tTileEntity == null) return null;
		
		if (tTileEntity instanceof GT_TileEntityMetaID_Machine) {
	        return ((GT_TileEntityMetaID_Machine)tTileEntity).getDebugInfo(aPlayer, aLogLevel);
		}

		if (tTileEntity instanceof BaseMetaTileEntity) {
	        return ((BaseMetaTileEntity)tTileEntity).getDebugInfo(aPlayer, aLogLevel);
		}
		
		return null;
	}
	
	@Override
    public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
        return 0;
    }
    
	@Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
        return GregTech_API.sMachineFlammable;
    }
    
	@Override
    public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face) {
        return GregTech_API.sMachineFlammable?100:0;
    }
	
	@Override
    public boolean isFireSource(World world, int x, int y, int z, int metadata, ForgeDirection side) {
        return GregTech_API.sMachineFlammable;
    }
}
