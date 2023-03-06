package gregtechmod.common.redstonecircuits;

import gregtechmod.api.interfaces.IRedstoneCircuitBlock;
import gregtechmod.api.util.GT_CircuitryBehavior;

public class GT_Circuit_BasicLogic extends GT_CircuitryBehavior {
	
	public GT_Circuit_BasicLogic(int aIndex) {
		super(aIndex);
	}
	
	@Override
	public void initParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
		aCircuitData[0] = 0;
	}
	
	@Override
	public void validateParameters(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
		if (aCircuitData[0] < 0) aCircuitData[0] = 0;
		if (aCircuitData[0] > 5) aCircuitData[0] = 5;
	}
	
	@Override
	public void onTick(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock) {
		if (aCircuitData[0] < 2) {
			aRedstoneCircuitBlock.setRedstone(aCircuitData[0]%2==(getAnyRedstone(aRedstoneCircuitBlock)?0:1)?(byte)15:0, aRedstoneCircuitBlock.getOutputFacing());
		} else if (aCircuitData[0] < 4) {
			aRedstoneCircuitBlock.setRedstone(aCircuitData[0]%2==(getOneRedstone(aRedstoneCircuitBlock)?0:1)?(byte)15:0, aRedstoneCircuitBlock.getOutputFacing());
		} else if (aCircuitData[0] < 6) {
			aRedstoneCircuitBlock.setRedstone(aCircuitData[0]%2==(getAllRedstone(aRedstoneCircuitBlock)?0:1)?(byte)15:0, aRedstoneCircuitBlock.getOutputFacing());
		}
	}
	
	@Override
	public String getName() {
		return "Basic Logic";
	}
	
	@Override
	public String getDescription() {
		return "Regular Logic Gates";
	}
	
	@Override
	public String getDataDescription(int[] aCircuitData, int aCircuitDataIndex) {
		if (aCircuitDataIndex == 0) {
			switch(aCircuitData[0]) {
			case  0: return "OR";
			case  1: return "NOR";
			case  2: return "XOR";
			case  3: return "XNOR";
			case  4: return "AND";
			case  5: return "NAND";
			}
		}
		return "";
	}
	
	@Override
	public boolean displayItemStack(int[] aCircuitData, IRedstoneCircuitBlock aRedstoneCircuitBlock, int aIndex) {
		return false;
	}
	
	@Override
	public String getDataDisplay(int[] aCircuitData, int aCircuitDataIndex) {
		return "";
	}
}