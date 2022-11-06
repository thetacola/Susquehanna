package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Font;

//last edit: 11/4/22 -N3
//One thing i am concerned about here is that any change to a phonology would result in the creation of an entierly new orthography
public class Orthography {

	private ArrayList<Phoneme> phonemes;
	private Font conscript;
	
	/**
	 * Creates an empty orthography
	 */
	public Orthography() {
		
	}
	
	/**
	 * Creates an orthography with an attached phonology
	 * @param phono The phonology meant to be attached
	 */
	public Orthography(Phonology phono) {
		List<String> phonologyList = phono.getList();
		for (int i = 0; i < phonologyList.size(); i++) {
			Phoneme phoneme = new Phoneme(phonologyList.get(i));
			phonemes.add(phoneme);
		}
	}
	/**
	 * Sets a conscript and attaches it to the orthography
	 * In the future, this will instead be replaced by a Conscript object, and this method will be depreciated
	 * @param fontName The name of the font used
	 * @param size The preffered size of the font
	 */
	public void setScript(String fontName, double size) {
		conscript = new Font(fontName, size);
	}
	/**
	 * Gets the conscript attached to the orthography
	 * @return The conscript that is attached
	 */
	public Font getScript() {
		return conscript;
	}
	/**
	 * Adds a phoneme to the phoneme list
	 * @param phoneme The phoneme to be added
	 */
	public void addPhoneme(Phoneme phoneme) {
		phonemes.add(phoneme);
	}
	/**
	 * Removes a phoneme from the phoneme list
	 * @param i The index of the phoneme to be removed
	 */
	public void removePhoneme(int i) {
		phonemes.remove(i);
	}
}
