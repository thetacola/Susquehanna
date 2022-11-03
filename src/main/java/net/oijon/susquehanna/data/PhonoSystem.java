package net.oijon.susquehanna.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//last edit: 11/3/22 -N3

public class PhonoSystem {

	private String name;
	private ArrayList<PhonoCategory> categories = new ArrayList<PhonoCategory>();
	
	/**
	 * Creates an IPA preset. Useful when we just want the default PhonoSystem.
	 */
	public static final PhonoSystem IPA = new PhonoSystem("IPA", new ArrayList<PhonoCategory>(
			Arrays.asList(PhonoCategory.IPAPlosive, PhonoCategory.IPANasal, PhonoCategory.IPATrill, PhonoCategory.IPATap,
					PhonoCategory.IPAFricative, PhonoCategory.IPALateralFricative, PhonoCategory.IPAApproximant,
					PhonoCategory.IPALateralApproximant, PhonoCategory.IPAClick, PhonoCategory.IPAImplosive, PhonoCategory.IPAOther,
					PhonoCategory.IPAClose, PhonoCategory.IPANearClose, PhonoCategory.IPACloseMid, PhonoCategory.IPAMid,
					PhonoCategory.IPAOpenMid, PhonoCategory.IPACloseOpen, PhonoCategory.IPAOpen)));
	/**
	 * Creates a PhonoSystem object with a pre-defined ArrayList
	 * @param name The name of the phono system
	 * @param categories The pre-defined ArrayList
	 */
	public PhonoSystem(String name, ArrayList<PhonoCategory> categories) {
		this.name = name;
		this.categories = categories;
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
			//Sets the variables
			this.name = fileContents.get(0);
			
			for (int i = 1; i < fileContents.size(); i++) {
				String[] split1 = fileContents.get(i).split(":");
				if (split1.length > 1) {
					String[] split2 = split1[1].split(",");
					
					ArrayList<String> phonemes = new ArrayList<String>();
					for (int j = 0; j < split2.length; j++) {
						phonemes.add(split2[j]);
					}
					PhonoCategory category = new PhonoCategory(split1[0], phonemes);
					this.addCategory(category);
				}
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
			this.categories = PhonoSystem.IPA.getCategories();
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
	public ArrayList<PhonoCategory> getCategories() {
		return categories;
	}
	
	/**
	 * Adds a category to a phono system
	 * @param category The category to be added
	 */
	public void addCategory(PhonoCategory category) {
		categories.add(category);
	}
	
	/**
	 * Removes category based off index.
	 * @param i index
	 */
	public void removeCategory(int i) {
		categories.remove(i);
	}
	
	/**
	 * Removes category based off name. As this is slower than removing via index, removing via index is preferred.
	 * @param name Name of category to be removed
	 */
	public void removeCategory(String name) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getName().equals(name)) {
				categories.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Allows use of an XY coordinate system to get sounds
	 * @param x Index of category
	 * @param y Index of sound
	 * @return The sound at both indexes
	 */
	public String getSound(int x, int y) {
		return categories.get(x).getSound(y);
	}
	
	/**
	 * Converts a PhonoSystem object to a string
	 */
	public String toString() {
		String output = name + "\n";
		for (int i = 0; i < categories.size(); i++) {
			output += categories.get(i).getName() + ":";
			for (int j = 0; j < categories.get(i).getSounds().size(); j++) {
				if (j == categories.get(i).getSounds().size() - 1) {
					output += categories.get(i).getSound(j);
				} else {
					output += categories.get(i).getSound(j) + ",";
				}
			}
			output += "\n";
		}
		return output;
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
