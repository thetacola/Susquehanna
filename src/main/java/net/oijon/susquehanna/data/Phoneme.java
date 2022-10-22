package net.oijon.susquehanna.data;

public class Phoneme {

	/*
	 * Connects writing to a sound, used in orthography.
	 */
	
	private String ortho;
	private String IPA;
	
	public Phoneme(String ortho, String IPA) {
		this.ortho = ortho;
		this.IPA = IPA;
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
