package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Font;

//last edit: 10/22/22 -N3

public class Orthography {

	private ArrayList<Phoneme> phonemes;
	private Font conscript;
	
	public static final Orthography EMPTY = new Orthography(Phonology.EMPTY);
	
	//creates orthography object
	public Orthography(Phonology phono) {
		phono.sort();
		List<String> phonologyList = phono.getList();
		for (int i = 0; i < phonologyList.size(); i++) {
			Phoneme phoneme = new Phoneme(phonologyList.get(i));
			phonemes.add(phoneme);
		}
	}
	//Sets the conscript attached to the orthography
	public void setScript(String fontName, double size) {
		conscript = new Font(fontName, size);
	}
	//Gets the conscript attached to the orthography
	public Font getScript() {
		return conscript;
	}
	//Adds a phoneme to the phoneme list
	public void addPhoneme(Phoneme phoneme) {
		phonemes.add(phoneme);
	}
	//Removes phoneme i from the phoneme list
	public void removePhoneme(int i) {
		phonemes.remove(i);
	}
}
