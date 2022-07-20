package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Font;

public class Orthography {

	private List<String> phono = new ArrayList<String>();
	private List<String> mappings = new ArrayList<String>();
	private Font conscript;
	
	public static final Orthography EMPTY = new Orthography(Phonology.EMPTY);
	
	//TODO: have sorted after phono changes
	public Orthography(Phonology phono) {
		phono.sort();
		this.phono = phono.getList();
	}
	public void setScript(String fontName, double size) {
		conscript = new Font(fontName, size);
	}
	public Font getScript() {
		return conscript;
	}
	public void addPhono(String phoneme) {
		phono.add(phoneme);
	}
	public List<String> getPhono() {
		return phono;
	}
	public List<String> getMapping() {
		return mappings;
	}
	public void addMapping(int id, String mapping) {
		mappings.set(id, mapping);
	}
}
