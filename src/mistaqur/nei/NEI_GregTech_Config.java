package gregtechmod.mistaqur.nei;

import codechicken.nei.api.IConfigureNEI;

public class NEI_GregTech_Config implements IConfigureNEI {
	@Override
	public void loadConfig() {
		new CentrifugeRecipeHandler();
		new ElectrolyzerRecipeHandler();
		new ChemicalRecipeHandler();
		new VacuumFreezerRecipeHandler();
		new GrinderRecipeHandler();
		new BlastRecipeHandler();
		new SawmillRecipeHandler();
		new ImplosionRecipeHandler();
		new FusionRecipeHandler();
		new DistillationRecipeHandler();
		new WiremillRecipeHandler();
		new AlloySmelterRecipeHandler();
		new CannerRecipeHandler();
		new BenderRecipeHandler();
		new AssemblerRecipeHandler();
		
		new DieselFuelsHandler();
		new TurbineFuelsHandler();
		new HotFuelsHandler();
		new DenseLiquidFuelsHandler();
		new PlasmaFuelsHandler();
		new MagicFuelsHandler();
	}
	
	@Override
	public String getName() {
		return "GregTech NEI Plugin";
	}
	
	/**
	 * This is just last time I was on looking for this Version Number :P
	 */
	@Override
	public String getVersion() {
		return "(3.02c)";
	}

}

