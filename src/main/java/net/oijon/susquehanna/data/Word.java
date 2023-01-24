package net.oijon.susquehanna.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

//last edit: 1/13/23 -N3

public class Word {

	private String name;
	private String meaning;
	private String pronounciation = " ";
	private String etymology = " ";
	private Language sourceLanguage = Language.NULL;
	private ArrayList<String> classes = new ArrayList<String>();
	private Date creationDate = Date.from(Instant.now());
	private Date editDate = Date.from(Instant.now());
	private ArrayList<Word> synonyms = new ArrayList<Word>();
	private ArrayList<Word> homonyms = new ArrayList<Word>();
	
	public Word(String name, String meaning) {
		this.name = name;
		this.meaning = meaning;
		//TODO: automatically get IPA from name via orthography
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMeaning() {
		return meaning;
	}
	
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	
	public void setPronounciation(String pronounciation) {
		this.pronounciation = pronounciation;
	}
	
	public String getPronounciation() {
		return pronounciation;
	}
	
	public void setEtymology(String etymology) {
		this.etymology = etymology;
	}
	
	public String getEtymology() {
		return etymology;
	}
	
	public void setSourceLanguage(Language sourceLanguage) {
		sourceLanguage = this.sourceLanguage;
	}
	
	public Language getSourceLanguage() {
		return sourceLanguage;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date date) {
		this.creationDate = date;
	}
	
	public Date getEditDate() {
		return editDate;
	}
	
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}
	
	public void addSynonym(Word syn) {
		synonyms.add(syn);
	}
	
	public void removeSynonym(int i) {
		synonyms.remove(i);
	}
	
	public void clearSynonyms() {
		synonyms.clear();
	}
	
	public void setSynonyms(ArrayList<Word> synonyms) {
		synonyms = this.synonyms;
	}
	
	public ArrayList<Word> getSynonyms() {
		return synonyms;
	}
	
	public void addHomonym(Word hom) {
		homonyms.add(hom);
	}
	
	public void removeHomonym(int i) {
		homonyms.remove(i);
	}
	
	public void setHomonyms(ArrayList<Word> homonyms) {
		homonyms = this.homonyms;
	}
	
	public ArrayList<Word> getHomonyms() {
		return homonyms;
	}
	
	public String toString() {
		String returnString = "===Word Start===\n";
		returnString += "wordname:" + name + "\n";
		returnString += "meaning:" + meaning + "\n";
		returnString += "pronounciation:" + pronounciation + "\n";
		returnString += "etymology:" + etymology + "\n";
		returnString += "sourceLanguage:" + sourceLanguage.getName() + "\n";
		returnString += "creationDate:" + creationDate.getTime() + "\n";
		returnString += "editDate:" + editDate.getTime() + "\n";
		returnString += "===Synonym Start===\n";
		for (int i = 0; i < synonyms.size(); i++) {
			returnString += ":" + synonyms.get(i).getName() + "\n";
		}
		returnString += "===Synonym End===\n";
		returnString += "===Homonym Start===\n";
		for (int i = 0; i < homonyms.size(); i++) {
			returnString += ":" + homonyms.get(i).getMeaning() + "\n";
		}
		returnString += "===Homonym End===\n";
		returnString += "===Classes Start===\n";
		for (int i = 0; i < classes.size(); i++) {
			returnString += ":" + classes.get(i) + "\n";
		}
		returnString += "===Classes End===\n";
		returnString += "===Word End===";
		return returnString;
	}
}
