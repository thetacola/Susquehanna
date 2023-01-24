package net.oijon.susquehanna.data;

import java.util.ArrayList;

//last edit: 1/13/23 -N3

public class Lexicon {

	private ArrayList<Word> wordList = new ArrayList<Word>();
	
	public Lexicon() {
		
	}
	
	public void addWord(Word word) {
		wordList.add(word);
	} 
	
	public void removeWord(Word word) {
		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).getName().equals(word.getName())) {
				if (wordList.get(i).getMeaning().equals(word.getMeaning())) {
					wordList.remove(i);
				}
			}
		}
	}
	
	public int size() {
		return wordList.size();
	}
	
	public Word getWord(int i) {
		return wordList.get(i);
	}
	
	public void checkSynonyms() {
		for (int i = 0; i < wordList.size(); i++) {
			for (int j = 0; j < wordList.size(); j++) {
				if (i != j) {
					if (wordList.get(i).getMeaning().equals(wordList.get(j).getMeaning())) {
						wordList.get(i).addSynonym(wordList.get(j));
					}
				}
			}
		}
	}
	
	public void checkHomonyms() {
		for (int i = 0; i < wordList.size(); i++) {
			for (int j = 0; j < wordList.size(); j++) {
				if (i != j) {
					if (wordList.get(i).getName().equals(wordList.get(j).getName())) {
						wordList.get(i).addHomonym(wordList.get(j));
					}
				}
			}
		}
	}
	
	public String toString() {
		String returnString = "===Lexicon Start===\n";
		for (int i = 0; i < wordList.size(); i++) {
			returnString += wordList.get(i).toString() + "\n";
		}
		returnString += "===Lexicon End===";
		return returnString;
	}
}
