package net.oijon.susquehanna.data;

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
	
	public String getName() {
		return name;
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
	
	public void toFile() {
		String output = name + "\n";
		for (int i = 0; i < categories.size(); i++) {
			output += categories.get(i).getName() + " : ";
			for (int j = 0; j < categories.get(i).getSounds().size(); j++) {
				output += "[" + categories.get(i).getSound(j) + "]";
			}
			output += "\n";
		}
		System.out.println("[DEBUG]");
		System.out.println(output);
	}
}
