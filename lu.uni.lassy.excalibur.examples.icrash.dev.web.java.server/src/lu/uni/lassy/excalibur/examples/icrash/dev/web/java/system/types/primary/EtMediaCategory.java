package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public enum EtMediaCategory implements JIntIs{

	/** ... */
	category_1,	
	category_2,	
	category_3;

	@Override
	public PtBoolean is() {
		return new PtBoolean(this.name() == EtMediaCategory.category_1.name() ||
				this.name() == EtMediaCategory.category_2.name() ||
				this.name() == EtMediaCategory.category_3.name());
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the alert status is to be one of the following:\n" +
				 EtMediaCategory.category_1.name() + "\n" +  EtMediaCategory.category_2.name() + "\n" +  EtMediaCategory.category_3.name());
	}
}
