package net.oijon.susquehanna.data;

//last edit: 10/22/22 -N3

public class Phoneme {

	/*
	 * Connects writing to a sound, used in orthography.
	 * Basically just a tuple lol
	 */
	
	private String ortho;
	private String IPA;
	
	public Phoneme(String ortho, String IPA) {
		this.ortho = ortho;
		this.IPA = IPA;
	}
	
	public Phoneme(String IPA) {
		this.IPA = IPA;
		this.ortho = null;
	}
	
	public String getOrtho() {
		return ortho;
	}
	
	public void setOrtho(String ortho) {
		this.ortho = ortho;
	}
	
	public String getIPA() {
		return IPA;
	}
	
	public void setIPA(String IPA) {
		this.IPA = IPA;
	}
	
}
