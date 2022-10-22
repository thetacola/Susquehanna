package net.oijon.susquehanna.data;

import java.util.ArrayList;
import java.util.Date;

public class Word {

	private String name;
	private String meaning;
	private String IPA;
	private String etymology;
	private Language sourceLanguage;
	private ArrayList<String> classes;
	private Date creationDate;
	private Date editDate;
	private ArrayList<Word> synonyms;
	private ArrayList<Word> homonyms;
	
	public Word(String name, String meaning) {
		name = this.name;
		meaning = this.meaning;
		//TODO: automatically get IPA from name via orthography
	}
	
	public void setIPA(String IPA) {
		IPA = this.IPA;
	}
	
	public String getIPA() {
		return IPA;
	}
	
	public void setEtymology(String etymology) {
		etymology = this.etymology;
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
	
	public void setEditDate(Date editDate) {
		editDate = this.editDate;
	}
	
	public void addSynonym(Word syn) {
		synonyms.add(syn);
	}
	
	public void removeSynonym(int i) {
		synonyms.remove(i);
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
}
