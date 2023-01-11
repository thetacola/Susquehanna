package net.oijon.susquehanna.data.phosys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import net.oijon.susquehanna.SystemInfo;
import net.oijon.susquehanna.data.Language;
import net.oijon.susquehanna.data.Log;
import net.oijon.susquehanna.data.PhonoCategory;
import net.oijon.susquehanna.data.PhonoSystem;
import net.oijon.susquehanna.data.PhonoTable;
import net.oijon.susquehanna.data.Phonology;
import net.oijon.susquehanna.data.phosys.tags.Multitag;
import net.oijon.susquehanna.data.phosys.tags.Tag;

public class Parser {
	
	Log log = new Log(true);
	private Multitag tag;
	
	public Parser(String input) {
		log.setDebug(true);
		input.replace("	", "");
		String[] splitLines = input.split("\n");
		/**
		 * New plan:
		 * Because the entire file has to be in a PHOSYS tag, just use that with parseMulti :)
		 */
		if (splitLines[0].equals("===PHOSYS Start===")) {
			parseMulti(input);
		} else {
			log.err("Input is not a valid PHOSYS file!");
		}
	}
	
	public Parser(File file) {
		log.setDebug(true);
		try {
			Scanner scanner = new Scanner(file);
			String wholeFile = "";
			while (scanner.hasNextLine()) {
				wholeFile += scanner.nextLine() + "\n";
			}
			String[] splitLines = wholeFile.split("\n");
			log.debug(wholeFile);
			if (splitLines[0].equals("===PHOSYS Start===")) {
				parseMulti(wholeFile);
			} else {
				log.err("Input is not a valid PHOSYS file!");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.critical("File " + file.toString() + " not found! Cannot parse.");
		}
		
	}
	
	public Multitag getPHOSYSTag() {
		return this.tag;
	}
	
	private Multitag parseMulti(String input) {
		input = input.replace("	", "");
		String[] splitLines = input.split("\n");
		String[] splitSpace = splitLines[0].split(" ");
		String tagName = splitSpace[0].substring(3);
		
		Multitag tag = new Multitag(tagName);
		for (int i = 1; i < splitLines.length; i++) {
			splitSpace = splitLines[i].split(" ");
			String[] splitColon = splitLines[i].split(":");
			if (splitSpace.length == 2 & splitColon.length != 2) {
				if (splitSpace[1].equals("Start===")) {
					String name = splitSpace[0].substring(3);
					int lineNum = i + 1;
					String tagInput = "";
					for (int j = i; j < splitLines.length; j++) {
						if (!splitLines[j].equals("===" + name + " End===")) {
							tagInput += splitLines[j] + "\n";
						} else if (splitLines[j].equals("===" + name + " End===")){
							Multitag childTag = parseMulti(tagInput);
							log.debug("Adding child multitag " + name + " to tag " + tag.getName());
							tag.addMultitag(childTag);
							i = j;
							break;
						}
						if (j == splitLines.length - 1) {
							log.err("Tag " + name + " on line " + lineNum + " is not closed!");
						}
					}
				}
			} else if (splitColon.length == 2) {
				String name = splitColon[0];
				String data = splitColon[1];
				log.debug("Adding child tag " + name + " with data " + data + " to tag " + tag.getName());
				Tag childTag = new Tag(name, data);
				tag.addTag(childTag);
			} else if (splitLines[i] != "") {
				String data = splitLines[i];
				Tag childTag = new Tag("", data);
				tag.addTag(childTag);
			}
		}
		this.tag = tag;
		return tag;
	}
	
	public PhonoSystem parsePhonoSys() throws Exception {
		try {
			Multitag tablelist = this.tag.getMultitag("Tablelist");
			Tag tablelistName = tablelist.getDirectChild("tablelistName");
			Tag diacriticList;
			try {
				diacriticList = tablelist.getDirectChild("diacriticList");
			} catch (Exception e) {
				log.warn(e.toString());
				diacriticList = new Tag("diacriticList", "");
			}
			PhonoSystem phonoSystem = new PhonoSystem(tablelistName.value());
			ArrayList<String> diacritics = new ArrayList<String>(Arrays.asList(diacriticList.value().split(",")));
			phonoSystem.setDiacritics(diacritics);
			for (int i = 0; i < tablelist.getSubMultitags().size(); i++) {
				if (tablelist.getSubMultitags().get(i).getName().equals("PhonoTable")) {
					Multitag phonoTableTag = tablelist.getSubMultitags().get(i);
					Tag tableName = phonoTableTag.getDirectChild("tableName");
					Tag columnNames = phonoTableTag.getDirectChild("columnNames");
					Tag soundsPerCell = phonoTableTag.getDirectChild("soundsPerCell");
					Tag rowNames = phonoTableTag.getDirectChild("rowNames");
					ArrayList<Tag> tableData = phonoTableTag.getUnattachedData();
					log.debug("Data for table " + tableName.value() + ":");
					log.debug(tableData.toString());
					
					String name = tableName.value();
					ArrayList<String> columns = new ArrayList<String>(Arrays.asList(columnNames.value().split(",")));
					ArrayList<String> rowNamesList = new ArrayList<String>(Arrays.asList(rowNames.value().split(",")));
					int perCell = 0;
					try {
						perCell = Integer.parseInt(soundsPerCell.value());
					} catch (NumberFormatException nfe) {
						log.err("soundsPerCell must be integer in " + tableName.value());
						log.err(nfe.toString());
						throw nfe;
					}
					
					ArrayList<PhonoCategory> cats = new ArrayList<PhonoCategory>();
					for (int j = 0; j < rowNamesList.size(); j++) {
						PhonoCategory cat = new PhonoCategory(rowNamesList.get(j));
						// TODO: allow multiple character sounds?
						try {
							String catData = tableData.get(j).value();
							for (int k = 0; k < catData.length(); k++) {
								cat.addSound(Character.toString(catData.charAt(k)));
							}
							cats.add(cat);
						} catch (IndexOutOfBoundsException e) {
							log.warn("No data found in table " + name);
						}
					}
					
					PhonoTable phonoTable = new PhonoTable(name, columns, cats, perCell);
					phonoSystem.addTable(phonoTable);
				}
			}
			return phonoSystem;
		} catch (Exception e) {
			log.err(e.toString());
			throw e;
		}
	}
	public Phonology parsePhono() throws Exception {
		try {
			PhonoSystem phonoSystem = this.parsePhonoSys();
			Multitag phonoTag = this.tag.getMultitag("Phonology");
			Tag soundListTag = phonoTag.getDirectChild("soundlist");
			String soundData = soundListTag.value();
			String[] soundList = soundData.split(",");
			// TODO: parse phonotactics
			Phonology phono = new Phonology(soundList, phonoSystem);
			return phono;
		} catch (Exception e) {
			e.printStackTrace();
			log.err(e.toString());
			throw e;
		}
	}
	public Language parseLanguage() throws Exception {
		Phonology phono = this.parsePhono();
		Multitag meta = this.tag.getMultitag("Meta");
		Tag ver = meta.getDirectChild("susquehannaVersion");
		if (!ver.value().equals(SystemInfo.susquehannaVersion())) {
			log.warn("This language was last modified in a different version of Susquehanna!");
			log.warn("This may cause issues with reading and writing to the language!");
			log.warn("It is highly reccomended that you back up your language before editing!");
			log.warn("Expected version: " + SystemInfo.susquehannaVersion());
			log.warn("Recieved version: " + ver.value());
		}
		Tag timeCreated = meta.getDirectChild("timeCreated");
		Tag lastEdited = meta.getDirectChild("lastEdited");
		Tag readonly = meta.getDirectChild("readonly");
		Tag name = meta.getDirectChild("name");
		Tag autonym = meta.getDirectChild("autonym");
		Tag parent = meta.getDirectChild("parent");
		Language lang = new Language(name.value());
		lang.setPhono(phono);
		lang.setCreated(new Date(Long.parseLong(timeCreated.value())));
		lang.setEdited(new Date(Long.parseLong(lastEdited.value())));
		lang.setAutonym(autonym.value());
		lang.setReadOnly(Boolean.parseBoolean(readonly.value()));
		lang.setParent(new Language(parent.value()));
		lang.setVersion(ver.value());
		return lang;
	}
	

}
