package net.oijon.susquehanna.data;

public class Language {

	private String autonym;
	private String name;
	private Phonology phono;
	private Language parent;
	
	
	public Language(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public String getAutonym() {
		return autonym;
	}
	public void setAutonym(String autonym) {
		this.autonym = autonym;
	}
	public Phonology getPhono() {
		return phono;
	}
	public void setPhono(Phonology phono) {
		this.phono = phono;
	}
	public Language getParent() {
		return parent;
	}
	public String getParentName() {
		return parent.getName();
	}
	public void setParent(Language parent) {
		this.parent = parent;
	}
}
