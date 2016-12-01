package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public class DtEmail extends DtString implements JIntIs {

	private static final long serialVersionUID = -61005487586207285L;

	public DtEmail(PtString s){
		super(s);
	}
	
	/** The minimum length that an email can't be. */
	private int _minLength = 4;
	
	/** The maximum length that an email can be. */
	private int _maxLength = 256;
	
	@Override
	public PtBoolean is() {
		return new PtBoolean(this.value.getValue().length() > _minLength && this.value.getValue().length() <= _maxLength);
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the email is to have a minimum length of " + _minLength + " and a maximum length of " + _maxLength); 
	}

}
