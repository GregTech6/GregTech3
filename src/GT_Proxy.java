package gregtechmod.common;

import gregtechmod.GT_Mod;
import gregtechmod.api.GregTech_API;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class GT_Proxy {
	public void initialize() {
		TickRegistry.registerTickHandler(new GT_TickHandler(), Side.SERVER);
		GameRegistry.registerFuelHandler(new GT_FuelHandler());
    	GameRegistry.registerWorldGenerator(new GT_Worldgenerator());
		addSounds();
	}
	
	public void doSound(ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
		
	}
	
	public int addArmor(String aPrefix) {
		return 0;
	}
	
	public void serverStart() {
		NetworkRegistry.instance().registerGuiHandler(GregTech_API.gregtechmod, new GT_GUIHandler());
	}
	
	public void addSounds() {
    	GT_Mod.mSoundItems.add(new ItemStack(Block.blockSteel, 1));
    	GT_Mod.mSoundNames.add("note.harp");
    	GT_Mod.mSoundCounts.add(25);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.blockGold, 1));
    	GT_Mod.mSoundNames.add("note.pling");
    	GT_Mod.mSoundCounts.add(25);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.stone, 1));
    	GT_Mod.mSoundNames.add("note.bd");
    	GT_Mod.mSoundCounts.add(25);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.wood, 1));
    	GT_Mod.mSoundNames.add("note.bassattack");
    	GT_Mod.mSoundCounts.add(25);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.planks, 1));
    	GT_Mod.mSoundNames.add("note.bass");
    	GT_Mod.mSoundCounts.add(25);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.glass, 1));
    	GT_Mod.mSoundNames.add("note.hat");
    	GT_Mod.mSoundCounts.add(25);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.sand, 1));
    	GT_Mod.mSoundNames.add("note.snare");
    	GT_Mod.mSoundCounts.add(25);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.recordCat, 1));
    	GT_Mod.mSoundNames.add("streaming.");
    	GT_Mod.mSoundCounts.add(12);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.tnt, 1));
    	GT_Mod.mSoundNames.add("random.explode");
    	GT_Mod.mSoundCounts.add(3);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.fire, 1));
    	GT_Mod.mSoundNames.add("fire.fire");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.flintAndSteel, 1));
    	GT_Mod.mSoundNames.add("fire.ignite");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.lavaMoving, 1));
    	GT_Mod.mSoundNames.add("liquid.lavapop");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.waterMoving, 1));
    	GT_Mod.mSoundNames.add("liquid.water");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.bucketWater, 1));
    	GT_Mod.mSoundNames.add("liquid.splash");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.bucketLava, 1));
    	GT_Mod.mSoundNames.add("random.fizz");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.portal, 1));
    	GT_Mod.mSoundNames.add("portal.portal");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.endPortal, 1));
    	GT_Mod.mSoundNames.add("portal.travel");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.endPortalFrame, 1));
    	GT_Mod.mSoundNames.add("portal.trigger");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.thinGlass, 1));
    	GT_Mod.mSoundNames.add("random.glass");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.enderPearl, 1));
    	GT_Mod.mSoundNames.add("random.orb");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.eyeOfEnder, 1));
    	GT_Mod.mSoundNames.add("random.levelup");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.stoneButton, 1));
    	GT_Mod.mSoundNames.add("random.click");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.cobblestone, 1));
    	GT_Mod.mSoundNames.add("damage.fallbig");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.dirt, 1));
    	GT_Mod.mSoundNames.add("damage.fallsmall");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.swordSteel, 1));
    	GT_Mod.mSoundNames.add("damage.hurtflesh");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.swordDiamond, 1));
    	GT_Mod.mSoundNames.add("random.hurt");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.bow, 1));
    	GT_Mod.mSoundNames.add("random.bow");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.arrow, 1));
    	GT_Mod.mSoundNames.add("random.drr");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.fishingRod, 1));
    	GT_Mod.mSoundNames.add("random.bowhit");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.shovelSteel, 1));
    	GT_Mod.mSoundNames.add("random.break");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.bucketEmpty, 1));
    	GT_Mod.mSoundNames.add("random.breath");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.potion, 1));
    	GT_Mod.mSoundNames.add("random.drink");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.glassBottle, 1));
    	GT_Mod.mSoundNames.add("random.burp");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.enderChest==null?Block.obsidian:Block.enderChest, 1));
    	GT_Mod.mSoundNames.add("random.chestopen");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.chest, 1));
    	GT_Mod.mSoundNames.add("random.chestclosed");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.doorSteel, 1));
    	GT_Mod.mSoundNames.add("random.door_open");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.doorWood, 1));
    	GT_Mod.mSoundNames.add("random.door_close");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Item.porkRaw, 1));
    	GT_Mod.mSoundNames.add("random.eat");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.cloth, 1));
    	GT_Mod.mSoundNames.add("step.cloth");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.grass, 1));
    	GT_Mod.mSoundNames.add("step.grass");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.gravel, 1));
    	GT_Mod.mSoundNames.add("step.gravel");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.snow, 1));
    	GT_Mod.mSoundNames.add("step.snow");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.pistonBase, 1));
    	GT_Mod.mSoundNames.add("tile.piston.out");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.pistonStickyBase, 1));
    	GT_Mod.mSoundNames.add("tile.piston.in");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.cobblestoneMossy, 1));
    	GT_Mod.mSoundNames.add("ambient.cave.cave");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.blockLapis, 1));
    	GT_Mod.mSoundNames.add("ambient.weather.rain");
    	GT_Mod.mSoundCounts.add(1);
    	GT_Mod.mSoundItems.add(new ItemStack(Block.blockDiamond, 1));
    	GT_Mod.mSoundNames.add("ambient.weather.thunder");
    	GT_Mod.mSoundCounts.add(1);
	}
}