package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design;

import java.io.Serializable;

public class MediaBean implements Serializable {

	private Integer id;
	private String name;
	private String email;
	private String category;
	
	public MediaBean(Integer id, String name, String email, String category){
		this.id = id;
		this.name = name;
		this.email = email;
		this.category = category;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}	
	
}
