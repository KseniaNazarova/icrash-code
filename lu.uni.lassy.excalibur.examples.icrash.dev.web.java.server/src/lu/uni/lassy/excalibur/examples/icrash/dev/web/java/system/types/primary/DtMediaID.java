package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public class DtMediaID extends DtString implements JIntIs {
	
	private static final long serialVersionUID = -4948442193286092846L;

	/**
	 * Instantiates a new datatype of the media id.
	 *
	 * @param s The primitive type of string to create the datatype
	 */
	public DtMediaID(PtString s) {
		super(s);
	}

	/** The minimum length of a media ID that is not acceptable. */
	private int _minLength = 0;
	
	/** The maximum length of a media ID that is acceptable. */
	private int _maxLength = 10;
	
	public PtBoolean is() {
		return new PtBoolean(this.value.getValue().length() > _minLength && this.value.getValue().length() <= _maxLength);
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the media ID is to have a minimum length greater than " + _minLength + " and a maximum length of " + _maxLength);
	}

}
