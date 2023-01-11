package net.oijon.susquehanna.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import net.oijon.susquehanna.data.phosys.Parser;

//last edit: 12/22/22 -N3

public class PhonoSystem {

	private String name;
	private ArrayList<PhonoTable> tables = new ArrayList<PhonoTable>();
	private ArrayList<String> diacriticList = new ArrayList<String>();
	/**
	 * Creates an IPA preset. Useful when we just want the default PhonoSystem.
	 */
	
	public static final PhonoSystem IPA = new PhonoSystem("IPA", new ArrayList<PhonoTable>(
			Arrays.asList(PhonoTable.IPAConsonants, PhonoTable.IPAVowels, PhonoTable.IPAOther)), PhonoCategory.IPADiacritics);
	/**
	 * Creates a PhonoSystem object with a pre-defined ArrayList
	 * @param name The name of the phono system
	 * @param categories The pre-defined ArrayList
	 * @param columnNames The names of each column on the chart
	 */
	public PhonoSystem(String name, ArrayList<PhonoTable> tables) {
		this.name = name;
		this.tables = tables;
	}
	public PhonoSystem(String name, ArrayList<PhonoTable> tables, ArrayList<String> diacriticList) {
		this.name = name;
		this.tables = tables;
		this.diacriticList = diacriticList;
	}
	/**
	 * Creates a PhonoSystem object with a blank category list. This list will need something added to it to work!
	 * @param name The name of the phono system
	 */
	public PhonoSystem(String name) {
		this.name = name;
	}
	
	/**
	 * Loads a PhonoSystem object from a file
	 * 
	 */
	
	public PhonoSystem(File file) {
		try {
			Parser parser = new Parser(file);
			// this is silly
			PhonoSystem parsedSys = parser.parsePhonoSys();
			this.diacriticList = parsedSys.getDiacritics();
			this.name = parsedSys.getName();
			this.tables = parsedSys.getTables();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.print("\n");
			for (int i = 0; i < 30; i++) {
				System.err.print("+=");
			}
			System.err.print("\n");
			System.err.println("Exception encountered! " + e.toString());
			System.err.println("Defaulting to IPA...");
			this.name = PhonoSystem.IPA.getName();
			this.tables = PhonoSystem.IPA.getTables();
		}
	}
	
	/**
	 * Gets the name of the phono system
	 * @return The name of the phono system
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets an ArrayList of all of the categories added
	 * @return ArrayList of several PhonoCategory instances
	 */
	public ArrayList<PhonoTable> getTables() {
		return tables;
	}
	
	/**
	 * Adds a table to a phono system
	 * @param table The table to be added
	 */
	public void addTable(PhonoTable table) {
		tables.add(table);
	}
	
	/**
	 * Removes table based off index.
	 * @param i index
	 */
	public void removeTable(int i) {
		tables.remove(i);
	}
	
	/**
	 * Removes table based off name. As this is slower than removing via index, removing via index is preferred.
	 * @param name Name of category to be removed
	 */
	public void removeTable(String name) {
		for (int i = 0; i < tables.size(); i++) {
			if (tables.get(i).getName().equals(name)) {
				tables.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Allows use of an XY coordinate system to get sounds
	 * @param i Index of table
	 * @param x Index of category
	 * @param y Index of sound
	 * @return The sound at both indexes
	 */
	public String getSound(int i, int x, int y) {
		return tables.get(i).getRow(x).getSound(y);
	}
	
	public void addDiacritic(String value) {
		diacriticList.add(value);
	}
	public void setDiacritics(ArrayList<String> newList) {
		diacriticList = newList;
	}
	public ArrayList<String> getDiacritics() {
		return diacriticList;
	}
	/**
	 * Converts a PhonoSystem object to a string
	 */
	public String toString() {
		String output = "===Tablelist Start===\n";
		output += "tablelistName:" + name + "\n";
		output += "diacriticList:";
		for (int i = 0; i < diacriticList.size(); i++) {
			output += diacriticList.get(i) + ",";
		}
		if (output.charAt(output.length() - 1) == ',') {
			output = output.substring(0, output.length() - 1);
		}
		output += "\n";
		for (int i = 0; i < tables.size(); i++) {
			output += tables.get(i).toString() + "\n";
		}
		output += "===Tablelist End===";
		return output;
	}
	/**
	 * Checks if a given value exists in a phono system.
	 * @param value The value to be checked
	 * @return Returns true if value is found in phono system, false if not
	 */
	public boolean isIn(String value) {
		for (int i = 0; i < diacriticList.size(); i++) {
			value = value.replace(Character.toString(diacriticList.get(i).charAt(0)), "");
		}
		if (value.length() > 1) {
			value = Character.toString(value.charAt(0));
		}
		for (int i = 0; i < tables.size(); i++) {
			for (int j = 0; j < tables.get(i).size(); j++) {
				for (int k = 0; k < tables.get(i).getRow(j).size(); k++) {
					if (tables.get(i).getRow(j).getSound(k).equals(value)) {
						return true;
					}
				}
			}
		}
		System.out.println(value + " not in sys");
		return false;
	}
	
	/**
	 * Creates a file of the PhonoSystem.
	 */
	public void toFile() {
		String output = "===PHOSYS Start===\n";
		output += toString();
		output += "\n===PHOSYS End===";
		
		File mainDir = new File(System.getProperty("user.home") + "/Susquehanna/phonoSystems");
		mainDir.mkdirs();
		File systemFile = new File(System.getProperty("user.home") + "/Susquehanna/phonoSystems/" + getName() + ".phosys");
		PrintWriter out;
		try {
			out = new PrintWriter(systemFile);
			out.println(output);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}
