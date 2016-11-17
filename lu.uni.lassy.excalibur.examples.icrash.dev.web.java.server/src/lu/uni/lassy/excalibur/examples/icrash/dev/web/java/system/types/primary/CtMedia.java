package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;

public class CtMedia implements Serializable{

	private static final long serialVersionUID = -7823275041734117838L;
	
	/** The id of the media. */
	public DtMediaID id;
	
	/** The category of media. An example... */
	public EtMediaCategory category;
	
	public DtMediaName name;
	
	public DtEmail email;
	
	public PtBoolean init(DtMediaID id, DtMediaName name, DtEmail email, EtMediaCategory category){
		this.id = id;
		this.name = name;
		this.email = email;
		this.category = category;
		
		return new PtBoolean(true);
	}
	
}
