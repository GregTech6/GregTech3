package gregtechmod.common.covers;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_CoverBehavior;

public class GT_Cover_Generic extends GT_CoverBehavior {
	/**
	 * This is the Dummy, if there is a generic Cover without behavior
	 */
	public GT_Cover_Generic() {
		super();
		GregTech_API.sGenericBehavior = this;
	}
}