package net.oijon.susquehanna.data.phosys.tags;

import java.util.ArrayList;

public class Multitag {

	private String name;
	private ArrayList<Tag> subtags = new ArrayList<Tag>();
	private ArrayList<Multitag> subMultitags = new ArrayList<Multitag>();
	private boolean closed = false;
	
	public Multitag(String name, ArrayList<Tag> subtags, ArrayList<Multitag> subMultitags) {
		this.name = name;
	}
	public Multitag(ArrayList<Tag> subtags, ArrayList<Multitag> subMultitags) {
		this.subtags = subtags;
		this.subMultitags = subMultitags;
	}
	public Multitag(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	public ArrayList<Tag> getSubtags() {
		return subtags;
	}
	public ArrayList<Multitag> getSubMultitags() {
		return subMultitags;
	}
	public void addTag(Tag tag) {
		subtags.add(tag);
	}
	public void addMultitag(Multitag tag) {
		subMultitags.add(tag);
	}
	public boolean isSameTag(Multitag tag) {
		if (this.name.equals(tag.getName())) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isEqual(Multitag tag) {		
		if (this.name.equals(tag.getName())) {
			if (this.subtags.equals(tag.getSubtags())) {
				if (this.subMultitags.equals(tag.getSubMultitags())) {
					return true;
				}
			}
		}
		return false;
	}
	public void open() {
		this.closed = false;
	}
	public void close() {
		this.closed = true;
	}
	public String toString() {
		String returnString = this.getStart() + "\n";
		for (int i = 0; i < subtags.size(); i++) {
			returnString += subtags.get(i).toString() + "\n";
		}
		for (int i = 0; i < subMultitags.size(); i++) {
			returnString += subMultitags.get(i).toString() + "\n";
		}
		returnString += this.getEnd();
		return returnString;
	}
	public String getStart() {
		return "===" + this.name + " Start===";
	}
	public String getEnd() {
		return "===" + this.name + " End===";
	}
	
	public Multitag getMultitag(String name) throws Exception {
		for (int i = 0; i < subMultitags.size(); i++) {
			if (subMultitags.get(i).getName().equals(name)) {
				return subMultitags.get(i);
			}
		}
		for (int i = 0; i < subMultitags.size(); i++) {
			try {
				Multitag tag = subMultitags.get(i).getMultitag(name);
				return tag;
			} catch (Exception e) {
				continue;
			}
		}
		throw new Exception("Child multitag " + name + " not found in tag " + this.name + ".");
	}
	
	public Tag getDirectChild(String name) throws Exception {
		for (int i = 0; i < subtags.size(); i++) {
			if (subtags.get(i).getName().equals(name)) {
				return subtags.get(i);
			}
		}
		throw new Exception("Child tag " + name + " not found in tag " + this.name + ".");
	}
	
	public ArrayList<Tag> getUnattachedData() {
		ArrayList<Tag> returnList = new ArrayList<Tag>();
		for (int i = 0; i < subtags.size(); i++) {
			if (subtags.get(i).getName().equals("")) {
				returnList.add(subtags.get(i));
			}
		}
		return returnList;
		
	}
	
	public Tag getTag(String name) throws Exception {
		for (int i = 0; i < subtags.size(); i++) {
			if (subtags.get(i).getName().equals(name)) {
				return subtags.get(i);
			}
		}
		for (int i = 0; i < subMultitags.size(); i++) {
			try {
				Tag tag = subMultitags.get(i).getTag(name);
				return tag;
			} catch (Exception e) {
				continue;
			}
		}
		throw new Exception("Child tag " + name + " not found.");
	}
	
	
}
