package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//last edit: 1/27/23 -N3

public class Phonology {

	private List<String> phonoList = new ArrayList<String>(Arrays.asList(" "));
	private PhonoSystem phonoSystem;
	Log log = new Log(true);
	
	/**
	 * Converts a string array of sounds into a phonology
	 * @param pf The file the phonology is saved to
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
			} else {
				log.warn(array[i] + " is not in PhonoSystem " + sys.getName());
			}
		}
	}
	
	/**
	 * Allows the creation of an empty phonology from a file
	 */
	public Phonology() {
		setPhonoSystem(PhonoSystem.IPA);
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
	
	/**
	 * Gets the list of all phonemes
	 * @return The list of all phonemes
	 */
	public List<String> getList() {
		return phonoList;
	}
	
	/**
	 * Gets a phoneme by index
	 * @param id The index of the requested phoneme
	 * @return The phoneme at index id
	 */
	public String get(int id) {
		return phonoList.get(id);
	}
	
	/**
	 * Adds a phoneme to the phonology
	 * @param value The phoneme to be added
	 */
	public void add(String value) {
		if (phonoSystem.isIn(value)) {
			phonoList.add(value);
		}
	}
	
	/**
	 * Removes a phoneme from the phonology
	 * @param value The phoneme to be removed
	 */
	public void remove(String value) {
		for (int i = 0; i < phonoList.size(); i++) {
			if (value.equals(phonoList.get(i))) {
				phonoList.remove(i);
			}
		}
	}
	
	/**
	 * Checks if a phoneme is in a phonology
	 * @param value The phoneme to check
	 * @return True if the phoneme is found, false otherwise.
	 */
	public boolean isIn(String value) {
		for (int i = 0; i < phonoList.size(); i++) {
			if (phonoList.get(i).equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		String returnString = "===Phonology Start===\n";
		returnString += "soundlist:";
		for (int i = 0; i < phonoList.size(); i++) {
			returnString += this.get(i) + ",";
		}
		if (returnString.charAt(returnString.length() - 1) == ',') {
			returnString = returnString.substring(0, returnString.length() - 1); // removes final comma
		}
		returnString += "\n";
		returnString += this.phonoSystem.toString() + "\n";
		returnString += "===Phonology End===";
		return returnString;
	}
	
	public void clear() {
		phonoList.clear();
	}
}
