package net.oijon.susquehanna.data;

import java.util.List;

public class Phonology {

	private List<String> phonoList;
	private final String[] plosive = {"p", "b", "t", "d", "ʈ", "ɖ", "c", "ɟ", "k", "ɡ", "q", "ɢ", "ʔ"};
	private final String[] nasal = {"m","ɱ","n","ɳ","ɲ","ŋ","ɴ"};
	private final String[] trill = {"ʙ","r","ʀ"};
	private final String[] tap = {"ⱱ","ɾ","ɽ"};
	private final String[] fricative = {"ɸ","β","f","v","θ","ð","s","z","ʃ","ʒ","ʂ","ʐ","ç","ʝ","x","ɣ","χ","ʁ","ħ","ʕ","h","ɦ"};
	private final String[] lateralFricative = {"ɬ","ɮ"};
	private final String[] approximant = {"ʋ","ɹ","ɻ","j","ɰ"};
	private final String[] lateralApproximant = {"l","ɭ","ʎ","ʟ"};
	
	private final String[] click = {"ʘ","ǀ","ǃ","ǂ","ǁ"};
	private final String[] implosive = {"ɓ","ɗ","ʄ","ɠ","ʛ"};
	private final String[] other = {"ʍ","w","ɥ","ʜ","ʢ","ʡ","ɕ","ʑ","ɺ","ɧ"};
	
	private final String[] close = {"i","y","ɨ","ʉ","ɯ","u"};
	private final String[] nearclose = {"ɪ","ʏ","ʊ"};
	private final String[] closemid = {"e","ø","ɘ","ɵ","ɤ","o"};
	private final String[] mid = {"ə"};
	private final String[] openmid = {"ɛ","œ","ɜ","ɞ","ʌ","ɔ"};
	private final String[] closeopen = {"æ","ɐ"};
	private final String[] open = {"a","ɶ","ɑ","ɒ"};
	
	private final String[] fullList = {
			"p", "b", "t", "d", "ʈ", "ɖ", "c", "ɟ", "k", "ɡ", "q", "ɢ", "ʔ",
			"m","ɱ","n","ɳ","ɲ","ŋ","ɴ",
			"ʙ","r","ʀ",
			"ⱱ","ɾ","ɽ",
			"ɸ","β","f","v","θ","ð","s","z","ʃ","ʒ","ʂ","ʐ","ç","ʝ","x","ɣ","χ","ʁ","ħ","ʕ","h","ɦ",
			"ɬ","ɮ",
			"ʋ","ɹ","ɻ","j","ɰ",
			"l","ɭ","ʎ","ʟ",
			"ʘ","ǀ","ǃ","ǂ","ǁ",
			"ɓ","ɗ","ʄ","ɠ","ʛ",
			"ʍ","w","ɥ","ʜ","ʢ","ʡ","ɕ","ʑ","ɺ","ɧ",
			"i","y","ɨ","ʉ","ɯ","u",
			"ɪ","ʏ","ʊ",
			"e","ø","ɘ","ɵ","ɤ","o",
			"ə",
			"ɛ","œ","ɜ","ɞ","ʌ","ɔ",
			"æ","ɐ",
			"a","ɶ","ɑ","ɒ"
			};
	
	public static final Phonology EMPTY = new Phonology(null);
	
	public Phonology(String[] phonoArray) {
		for (int i = 0; i < phonoArray.length; i++) {
			phonoList.add(phonoArray[i]);
		}
	}
	
	public List<String> getList() {
		return phonoList;
	}
	
	public String get(int id) {
		return phonoList.get(id);
	}
	public void add(String value) {
		phonoList.add(value);
	}
	
	public void sort() {
		//sorted like an ipa chart
		//vowels at the end
		//using bubble sort, not the most efficient but the dataset is relatively small
		//the two temp values here are set to 999 because that is much larger than any value in the array, thus moving to the back
		int temp = 999;
		int temp2 = 999;
		String temp3 = null;
		
		//TODO: diacritic support
		for (int i = 0; i < phonoList.size(); i++) {
			for (int j = i + 1; j < phonoList.size(); j++) {
				//find the core phoneme to compare
				for (int k = 0; k < fullList.length; k++) {
					for (int l = 0; l < phonoList.get(i).length(); l++) {
						Character tempChar = phonoList.get(i).charAt(l);
						if (tempChar.toString().equals(fullList[k])) {
							temp = k;
						}
					}
				}
				for (int k = 0; k < fullList.length; k++) {
					for (int l = 0; l < phonoList.get(j).length(); l++) {
						Character tempChar = phonoList.get(j).charAt(l);
						if (tempChar.toString().equals(fullList[k])) {
							temp2 = k;
						}
					}
				}
				//actually start sorting
				//this is a bit too many else ifs for my liking but i dont think a switch case would be good here
				if (temp2 > temp) {
					temp3 = phonoList.get(i);
					phonoList.set(i, phonoList.get(j));
					phonoList.set(j, temp3);
				}
				temp = 999;
				temp2 = 999;
				temp3 = null;
			}
		}
		System.out.println("Sorted list: " + phonoList.toString());
	}
}
