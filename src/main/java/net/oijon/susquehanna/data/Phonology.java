package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.List;

//last edit: 1/6/23 -N3

public class Phonology {

	private List<String> phonoList = new ArrayList<String>();
	private PhonoSystem phonoSystem;
	
	/**
	 * Converts a string array of sounds into a phonology
	 * @param array The array to be converted
	 */
	public Phonology(String[] array) {
		this.phonoSystem = PhonoSystem.IPA;
		for (int i = 0; i < array.length; i++) {
			if (phonoSystem.isIn(array[i])) {
				phonoList.add(array[i]);
			}
		}
	}
	
	/**
	 * Converts a non-IPA string array into a phonology. 
	 * @param array The array to be converted
	 * @param sys The system to be used for this new phonology
	 */
	public Phonology(String[] array, PhonoSystem sys) {
		this.phonoSystem = sys;
		for (int i = 0; i < array.length; i++) {
			if (phonoSystem.isIn(array[i])) {
				phonoList.add(array[i]);
			}
		}
	}
	
	/**
	 * Allows the creation of an empty phonology. Defaults to using IPA
	 */
	public Phonology() {
		setPhonoSystem(PhonoSystem.IPA);
	}
	
	/**
	 * Allows the creation of an empty phonology with a set phonology system
	 */
	public Phonology(PhonoSystem sys) {
		setPhonoSystem(sys);
	}
	
	/**
	 * Gets the phono system attached to the phonology
	 * @return The phonology system attached
	 */
	public PhonoSystem getPhonoSystem() {
		return phonoSystem;
	}
	
	/**
	 * Sets the phono system attached to the phonology.
	 * This is a private method because it should only be used when creating a phonology.
	 * @param phonoSystem
	 */
	private void setPhonoSystem(PhonoSystem phonoSystem) {
		this.phonoSystem = phonoSystem;
	}
	
	public List<String> getList() {
		return phonoList;
	}
	
	public String get(int id) {
		return phonoList.get(id);
	}
	
	public void add(String value) {
		if (phonoSystem.isIn(value)) {
			phonoList.add(value);
		}
	}
	
	public boolean isIn(String value) {
		for (int i = 0; i < phonoList.size(); i++) {
			if (phonoList.get(i).equals(value)) {
				return true;
			}
		}
		return false;
	}
}
