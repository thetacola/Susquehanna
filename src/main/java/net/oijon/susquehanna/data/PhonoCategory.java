package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.Arrays;

//last edit: 11/4/22 -N3

public class PhonoCategory {

	/**
	 * You may be wondering, why not use Phonemes here?
	 * Well, that's because Phonemes are meant to attach orthography to phonology
	 * We aren't there yet. This is instead used by Phonology to check if a Phoneme is actually valid
	 * 
	 * * marks possible sounds with no symbol
	 * # marks impossible sounds
	 */
	
	private String name;
	private ArrayList<String> sounds; 
	
	//Creates premade IPA categories. Useful for when we want just a default phono system.
	public static final PhonoCategory IPAPlosive = new PhonoCategory("Plosive", new ArrayList<String>(
			Arrays.asList("p", "b", "*", "*", "*", "*", "t", "d", "*", "*", "ʈ", "ɖ", "c", "ɟ", "k", "ɡ", "q", "ɢ", "*", "#", "ʔ", "#")
			));
	public static final PhonoCategory IPANasal = new PhonoCategory("Nasal", new ArrayList<String>(
			Arrays.asList("*", "m", "*", "ɱ", "*", "*", "*", "n", "*", "*", "*", "ɳ", "*", "ɲ", "*", "ŋ", "*", "ɴ", "#", "#", "#", "#")
			));
	public static final PhonoCategory IPATrill = new PhonoCategory("Trill", new ArrayList<String>(
			Arrays.asList("*", "ʙ", "*", "*", "*", "*", "*", "r", "*", "*", "*", "*", "*", "*", "#", "#", "*", "ʀ", "*", "*", "#", "#")
			));
	public static final PhonoCategory IPATap = new PhonoCategory("Tap or Flap", new ArrayList<String>(
			Arrays.asList("*", "*", "*", "ⱱ", "*", "*", "*", "ɾ", "*", "*", "*", "ɽ", "*", "*", "#", "#", "*", "*", "*", "*", "#", "#")
			));
	public static final PhonoCategory IPAFricative = new PhonoCategory("Fricative", new ArrayList<String>(
			Arrays.asList("ɸ", "β", "f", "v", "θ", "ð", "s", "z", "ʃ", "ʒ", "ʂ", "ʐ", "ç", "ʝ", "x", "ɣ", "χ", "ʁ", "ħ", "ʕ", "h", "ɦ")
			));
	public static final PhonoCategory IPALateralFricative = new PhonoCategory("Lateral Fricative", new ArrayList<String>(
			Arrays.asList("#", "#", "#", "#", "*", "*", "ɬ", "ɮ", "*", "*", "*", "*", "*", "*", "*", "*", "*", "*", "#", "#", "#", "#")
			));
	public static final PhonoCategory IPAApproximant = new PhonoCategory("Approximant", new ArrayList<String>(
			Arrays.asList("*", "*", "*", "ʋ", "*", "*", "*", "ɹ", "*", "*", "*", "ɻ", "*", "j", "*", "ɰ", "*", "*", "*", "*", "#", "#")
			));
	public static final PhonoCategory IPALateralApproximant = new PhonoCategory("Lateral Approximant", new ArrayList<String>(
			Arrays.asList("#", "#", "#", "#", "*", "*", "*", "l", "*", "*", "*", "ɭ", "*", "ʎ", "*", "ʟ", "*", "*", "#", "#", "#", "#")
			));
	public static final PhonoCategory IPAClick = new PhonoCategory("Click", new ArrayList<String>(
			Arrays.asList("ʘ", "ǀ", "ǃ", "ǂ", "ǁ", "#", "#", "#", "#", "#")
			));
	public static final PhonoCategory IPAImplosive = new PhonoCategory("Implosive", new ArrayList<String>(
			Arrays.asList("ɓ", "ɗ", "ʄ", "ɠ", "ʛ", "#", "#", "#", "#", "#")
			));
	public static final PhonoCategory IPAOther = new PhonoCategory("Other", new ArrayList<String>(
			Arrays.asList("ʍ", "w", "ɥ", "ʜ", "ʢ", "ʡ", "ɕ", "ʑ", "ɺ", "ɧ")
			));
	
	public static final PhonoCategory IPAClose = new PhonoCategory("Close", new ArrayList<String>(
			Arrays.asList("i", "y", "ɨ", "ʉ", "ɯ", "u")
			));
	public static final PhonoCategory IPANearClose = new PhonoCategory("Near Close", new ArrayList<String>(
			Arrays.asList("ɪ","ʏ", "*", "*", "*", "ʊ")
			));
	public static final PhonoCategory IPACloseMid = new PhonoCategory("Close Mid", new ArrayList<String>(
			Arrays.asList("e", "ø", "ɘ", "ɵ", "ɤ", "o")
			));
	public static final PhonoCategory IPAMid = new PhonoCategory("Mid", new ArrayList<String>(
			Arrays.asList("*", "*", "ə", "*", "*", "*")
			));
	public static final PhonoCategory IPAOpenMid = new PhonoCategory("Open Mid", new ArrayList<String>(
			Arrays.asList("ɛ", "œ", "ɜ", "ɞ", "ʌ", "ɔ")
			));
	public static final PhonoCategory IPACloseOpen = new PhonoCategory("Close Open", new ArrayList<String>(
			Arrays.asList("æ", "*", "ɐ", "*", "*", "*")
			));
	public static final PhonoCategory IPAOpen = new PhonoCategory("Other", new ArrayList<String>(
			Arrays.asList("a", "ɶ", "*", "*", "ɑ", "ɒ")
			));
	
	/**
	 * Creates phono category for already created list
	 * @param name the name of the category
	 * @param sounds a pre-existing ArrayList of each sound
	 */
	public PhonoCategory(String name, ArrayList<String> sounds) {
		this.name = name;
		this.sounds = sounds;
	}
	
	/**
	 * Creates phono category for as-of-yet created list
	 * @param name the name of the category
	 */
	public PhonoCategory(String name) {
		this.name = name;
		this.sounds = new ArrayList<String>();
	}
	
	/**
	 * Gets list of all sounds in category
	 * @return all sounds in category
	 */
	public ArrayList<String> getSounds() {
		return sounds;
	}
	
	/**
	 * Gets category name
	 * @return category name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets sound at index i
	 * @param i index
	 * @return sound
	 */
	public String getSound(int i) {
		return sounds.get(i);
	}
	
	/**
	 * Deletes sound 
	 * @param i index of sound to be deleted
	 */
	public void removeSound(int i) {
		sounds.remove(i);
	}
	
	/**
	 * Adds sound to end of list
	 * @param sound the sound to be added
	 */
	public void addSound(String sound) {
		sounds.add(sound);
	}
	
	/**
	 * Returns the amount of sounds in a phono category
	 * @return The amount of sounds
	 */
	public int size() {
		return sounds.size();
	}
}
