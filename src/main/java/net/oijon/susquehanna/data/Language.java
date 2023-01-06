package net.oijon.susquehanna.data;

//last edit: 1/6/2023 -N3
public class Language {

	public static final Language NULL = new Language("null");
	private String autonym = "null";
	private String name = "null";
	private Phonology phono = new Phonology();
	private Language parent = Language.NULL;
	
	
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
	public String toString() {
		String returnString = "autonym:" + autonym;
		returnString += "\nname:" + name;
		returnString += "\n===BEGIN PHONO===\n";
		returnString += phono.toString();
		returnString += "\n===END PHONO===";
		returnString += "\nparent:" + parent.getName();
		return returnString;
	}
	
}
