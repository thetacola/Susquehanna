package net.oijon.susquehanna.data;

public class Language {

	private String autonym;
	private String name;
	private Phonology phono;
	
	
	public Language(String name, Phonology phono) {
		this.name = name;
		this.phono = phono;
	}
	
	public String getName() {
		return name;
	}
	public String getAutonym() {
		return autonym;
	}
	public Phonology getPhono() {
		return phono;
	}
}
