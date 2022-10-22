package net.oijon.susquehanna.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

//last edit: 10/22/22 -N3

public class PhonoSystem {

	private String name;
	private ArrayList<PhonoCategory> categories;
	
	//Creates an IPA preset. Useful when we just want the default PhonoSystem.
	public static final PhonoSystem IPA = new PhonoSystem("IPA", new ArrayList<PhonoCategory>(
			Arrays.asList(PhonoCategory.IPAPlosive, PhonoCategory.IPANasal, PhonoCategory.IPATrill, PhonoCategory.IPATap,
					PhonoCategory.IPAFricative, PhonoCategory.IPALateralFricative, PhonoCategory.IPAApproximant,
					PhonoCategory.IPALateralApproximant, PhonoCategory.IPAClick, PhonoCategory.IPAImplosive, PhonoCategory.IPAOther,
					PhonoCategory.IPAClose, PhonoCategory.IPANearClose, PhonoCategory.IPACloseMid, PhonoCategory.IPAMid,
					PhonoCategory.IPAOpenMid, PhonoCategory.IPACloseOpen, PhonoCategory.IPAOpen)));
	
	public PhonoSystem(String name, ArrayList<PhonoCategory> categories) {
		this.name = name;
		this.categories = categories;
	}
	public PhonoSystem(String name) {
		this.name = name;
	}
	/** FIXME: not adding categories correctly :(
	public PhonoSystem(File file) {
		if (file.exists()) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
				int lineNo = 0;
				while ((line = br.readLine()) != null) {
					if (lineNo == 0) {
						this.name = line;
					} else {
						String[] split1 = line.split(":"); //Splits name from data
						String[] split2 = split1[1].split(","); //Splits data into individual segments
						PhonoCategory phonoCategory = new PhonoCategory(split1[0]);
						
						for (int i = 0; i < split2.length; i++) {
							phonoCategory.addSound(split2[i]);
						}
						addCategory(phonoCategory);
						System.out.println("Added phonocategory " + phonoCategory.getName());
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("Warning: The file " + file.toString() + " does not exist! Defaulting to using IPA...");
			this.name = PhonoSystem.IPA.getName();
			this.categories = PhonoSystem.IPA.getCategories();
		}
		
	}
	*/
	
	public String getName() {
		return name;
	}
	
	public ArrayList<PhonoCategory> getCategories() {
		return categories;
	}
	
	public void addCategory(PhonoCategory category) {
		categories.add(category);
	}
	
	//Removes category based off index.
	public void removeCategory(int i) {
		categories.remove(i);
	}
	
	//Removes category based off name. As this is slower than removing via index, removing via index is preferred.
	public void removeCategory(String name) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getName().equals(name)) {
				categories.remove(i);
			}
		}
	}
	
	//Allows use of an XY coordinate system to get sounds, where X is the category and Y is the sound
	public String getSound(int x, int y) {
		return categories.get(x).getSound(y);
	}
	
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
