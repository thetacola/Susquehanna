package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.Arrays;

public class PhonoTable {

	private String name;
	private ArrayList<String> columnNames;
	private ArrayList<PhonoCategory> rows;
	private int soundsPerCell;
	
	public static final ArrayList<String> IPAConsonantColumnNames = new ArrayList<String>(
			Arrays.asList("Bilabial", "Labiodental", "Dental", "Alveolar", "Postalveolar", "Retroflex", "Palatal", 
					"Velar", "Uvular", "Pharyngeal", "Glottal"));
	public static final ArrayList<String> IPAVowelColumnNames = new ArrayList<String>(
			Arrays.asList("Front", "Central", "Back"));
	public static final PhonoTable IPAConsonants = new PhonoTable("IPA Consonants", IPAConsonantColumnNames, 
			new ArrayList<PhonoCategory>(
					Arrays.asList(
							PhonoCategory.IPAPlosive, PhonoCategory.IPANasal, PhonoCategory.IPATrill, PhonoCategory.IPATap,
							PhonoCategory.IPAFricative, PhonoCategory.IPALateralFricative, PhonoCategory.IPAApproximant,
							PhonoCategory.IPALateralApproximant)), 2);
	public static final PhonoTable IPAVowels = new PhonoTable("IPA Vowels", IPAVowelColumnNames, new ArrayList<PhonoCategory>(
			Arrays.asList(
					PhonoCategory.IPAClose, PhonoCategory.IPANearClose, PhonoCategory.IPACloseMid, PhonoCategory.IPAMid,
					PhonoCategory.IPAOpenMid, PhonoCategory.IPACloseOpen, PhonoCategory.IPAOpen)), 2);
	public static final PhonoTable IPAOther = new PhonoTable("IPA Non-Pulmonics", new ArrayList<String>(Arrays.asList("No column names")), 
			new ArrayList<PhonoCategory>(
					Arrays.asList(PhonoCategory.IPAClick, PhonoCategory.IPAImplosive, PhonoCategory.IPAOther, PhonoCategory.IPAEncodingAnomolies)), 1);
	
	public PhonoTable(String name, ArrayList<String> columnNames, ArrayList<PhonoCategory> rows, int soundsPerCell) {
		this.name = name;
		this.columnNames = columnNames;
		this.rows = rows;
		this.soundsPerCell = soundsPerCell;
	}
	
	public String toString() {
		String returnString = "===PhonoTable Start===\ntableName:" + name + "\ncolumnNames:";
		for (int i = 0; i < columnNames.size(); i++) {
			returnString += columnNames.get(i) + ",";
		}
		returnString = returnString.substring(0, returnString.length() - 1); // removes last comma
		returnString += "\nsoundsPerCell:" + soundsPerCell;
		returnString += "\nrowNames:";
		for (int i = 0; i < rows.size(); i++) {
			returnString +=rows.get(i).getName() + ",";
		}
		returnString = returnString.substring(0, returnString.length() - 1); // removes last comma
		returnString += "\n";
		for (int i = 0; i < rows.size(); i++) {
			returnString += ":";
			for (int j = 0; j < rows.get(i).size(); j++) {
				returnString += rows.get(i).getSound(j);
			}
			returnString += "\n";
		}
		returnString += "===PhonoTable End===";
		return returnString;
	}
	
	public String getName() {
		return name;
	}
	
	public int size() {
		return rows.size();
	}
	
	public PhonoCategory getRow(int i) {
		return rows.get(i);
	}
	
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}
	
	public int dataPerCell() {
		return soundsPerCell;
	}
	
	public boolean verify() {
		return true;
	}
}
