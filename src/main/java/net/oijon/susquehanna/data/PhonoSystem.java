package net.oijon.susquehanna.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//last edit: 12/22/22 -N3

public class PhonoSystem {

	private String name;
	private ArrayList<PhonoTable> tables = new ArrayList<PhonoTable>();
	
	/**
	 * Creates an IPA preset. Useful when we just want the default PhonoSystem.
	 */
	
	public static final PhonoSystem IPA = new PhonoSystem("IPA", new ArrayList<PhonoTable>(
			Arrays.asList(PhonoTable.IPAConsonants, PhonoTable.IPAVowels, PhonoTable.IPAOther)));
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
	/**
	 * Creates a PhonoSystem object with a blank category list. This list will need something added to it to work!
	 * @param name The name of the phono system
	 */
	public PhonoSystem(String name) {
		this.name = name;
	}
	
	/**
	 * Loads a PhonoSystem object from a file
	 */
	
	public PhonoSystem(File file) {
		try {
			//Take each line and put it into an ArrayList.
			//This probably is not the best memory wise, however it should work better than the previous spaghetti code
			ArrayList<String> fileContents = new ArrayList<String>();
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				fileContents.add(sc.nextLine());
			}
			sc.close();
			//Sets the global variables
			String name = null;
			for (int i = 0; i < fileContents.size(); i++) {
				if (fileContents.get(i).substring(0, 5).equals("name:")) {
					name = fileContents.get(i).substring(5);
					break;
				}
			}
			
			/**
			 * So here's the plan here:
			 * Each table starts with "===PhonoTable Start===" and ends with "===PhonoTable End==="
			 * So, if it encounters a table start, it will add everything between the two into a string
			 * This string will then be put into an array, where it will then be converted into a PhonoTable
			 * This is probably incredibly inefficient and a generally bad idea, but it's what I though of...
			 */
			if (name != null) {
				this.name = name;
				String entireDocument = "";
				String[] byTable;
				for (int i = 0; i < fileContents.size(); i++) {
					//spaghetti time
					entireDocument += fileContents.get(i) + "\n";
				}
				byTable = entireDocument.split("===PhonoTable Start===");
				//byTable[0] is globals that have already been handled and can be ignored
				ArrayList<PhonoTable> readTables = new ArrayList<PhonoTable>();
				for (int i = 1; i < byTable.length; i++) {
					String[] byTableByLine = byTable[i].split("\n");
					// Variables that are (hopefully) in the table
					String tableName = null;
					ArrayList<String> tableColumnNames = new ArrayList<String>();
					int tableSoundsPerCell = 0;
					ArrayList<String> tableRowNames = new ArrayList<String>();
					ArrayList<PhonoCategory> categoriesList = new ArrayList<PhonoCategory>();
					int onRow = 0;
					boolean canParseRowsFlag = false;
					int debugCountRows = 0;
					
					for (int j = 0; j < byTableByLine.length; j++) {
						// creates substrings to check, quite silly but java has forced my hand...
						
						// Starts off with values of "no" because it makes for a good starting value
						// if it can't be assigned something
						String tableNameSubstring = "no";
						String columnNamesSubstring = "no";
						String soundsPerCellSubstring = "no";
						String rowNamesSubstring = "no";
						
						
						if (byTableByLine[j].length() > 11) {
							tableNameSubstring = byTableByLine[j].substring(0, 10);
						}
						if (byTableByLine[j].length() > 13) {
							columnNamesSubstring = byTableByLine[j].substring(0, 12);
						}
						if (byTableByLine[j].length() > 14) {
							soundsPerCellSubstring = byTableByLine[j].substring(0, 14);
						}
						if (byTableByLine[j].length() > 10) {
							rowNamesSubstring = byTableByLine[j].substring(0, 9);
						}
						
						// inefficient
						if (tableNameSubstring.equals("tableName:")) {
							tableName = byTableByLine[j].substring(10);
						}
						else if (columnNamesSubstring.equals("columnNames:")) {
							String[] tableColumnNameStrings = byTableByLine[j].substring(12).split(",");
							for (int k = 0; k < tableColumnNameStrings.length; k++) {
								// removes quotes and adds it to the arraylist
								tableColumnNames.add(tableColumnNameStrings[k]);
							}
						}
						else if (soundsPerCellSubstring.equals("soundsPerCell:")) {
							tableSoundsPerCell = Integer.parseInt(byTableByLine[j].substring(14));
						}
						else if (rowNamesSubstring.equals("rowNames:")) {
							String[] tableColumnNameStrings = byTableByLine[j].substring(9).split(",");
							for (int k = 0; k < tableColumnNameStrings.length; k++) {
								// removes quotes and adds it to the arraylist
								tableRowNames.add(tableColumnNameStrings[k]);
								canParseRowsFlag = true;
							}
						}
						else if (byTableByLine[j].equals("===PhonoTable End===")){
							// prevents the else being triggered by an ending tag
						}
						else if (byTableByLine[j].equals("===PHOSYS End===")){
							// prevents the else being triggered by an ending tag
						}
						else if (canParseRowsFlag) {
							// this will most likely be the buggiest part of the entire thing
							if (byTableByLine[j].length() > 0) {
								PhonoCategory phonoCategory = new PhonoCategory(tableRowNames.get(onRow));
								for (int k = 0; k < byTableByLine[j].length(); k++) {
									String sound = Character.toString(byTableByLine[j].charAt(k));
									phonoCategory.addSound(sound);
								}
								categoriesList.add(phonoCategory);
								debugCountRows++;
								onRow++;
							}
							
						}
					}
					
					PhonoTable table = new PhonoTable(tableName, tableColumnNames, categoriesList, tableSoundsPerCell);
					this.tables.add(table);
				}
			} else {
				System.err.print("\n");
				System.err.println("File " + file.toString() + "is malformed!");
				System.err.println("Defaulting to IPA...");
				this.name = PhonoSystem.IPA.getName();
				this.tables = PhonoSystem.IPA.getTables();
			}
						
		} catch (FileNotFoundException e) {
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
	
	/**
	 * Converts a PhonoSystem object to a string
	 */
	public String toString() {
		String output = "===PHOSYS Start===\nname:" + name + "\n";
		for (int i = 0; i < tables.size(); i++) {
			output += tables.get(i).toString() + "\n";
		}
		output += "===PHOSYS End===";
		return output;
	}
	/**
	 * Checks if a given value exists in a phono system.
	 * @param value The value to be checked
	 * @return Returns true if value is found in phono system, false if not
	 */
	public boolean isIn(String value) {
		for (int i = 0; i < tables.size(); i++) {
			for (int j = 0; j < tables.get(i).size(); j++) {
				for (int k = 0; k < tables.get(i).getRow(j).size(); k++) {
					if (tables.get(i).getRow(j).getSound(k).equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Creates a file of the PhonoSystem.
	 */
	public void toFile() {
		
		String output = toString();
		
		File mainDir = new File(System.getProperty("user.home") + "/Susquehanna/phonoSystems");
		mainDir.mkdirs();
		File systemFile = new File(System.getProperty("user.home") + "/Susquehanna/phonoSystems/" + getName() + ".phosys");
		PrintWriter out;
		try {
			out = new PrintWriter(systemFile);
			out.println(output);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
