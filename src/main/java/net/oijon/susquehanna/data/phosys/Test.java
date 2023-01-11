package net.oijon.susquehanna.data.phosys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import net.oijon.susquehanna.data.Language;
import net.oijon.susquehanna.data.Log;
import net.oijon.susquehanna.data.PhonoSystem;
import net.oijon.susquehanna.data.Phonology;

public class Test {

	public static void main(String args[]) {
		Log log = new Log();
		log.info("Test 1: Reading a single multitag correctly");
		File file = new File(System.getProperty("user.home") + "/Susquehanna/.meta.language");
		String fileContents = "";
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				log.info(line);
				fileContents += line + "\n";
			}
			sc.close();
			
			Parser parser = new Parser(fileContents);
			log.info(parser.getPHOSYSTag().toString());
		} catch (FileNotFoundException e) {
			log.err(e.toString());
		}
		log.info("=====================");
		log.info("Test 2: Reading a whole file correctly");
		file = new File(System.getProperty("user.home") + "/Susquehanna/.English.language");
		fileContents = "";
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				log.info(line);
				fileContents += line + "\n";
			}
			sc.close();
			
			Parser parser = new Parser(fileContents);
			log.info(parser.getPHOSYSTag().toString());
		} catch (FileNotFoundException e) {
			log.err(e.toString());
		}
		log.info("=====================");
		log.info("Test 3: Parsing a PhonoSystem from a .language file");
		file = new File(System.getProperty("user.home") + "/Susquehanna/.English.language");
		fileContents = "";
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				log.info(line);
				fileContents += line + "\n";
			}
			sc.close();
			
			Parser parser = new Parser(fileContents);
			PhonoSystem phonoSystem = parser.parsePhonoSys();
			log.info(phonoSystem.toString());
		} catch (Exception e) {
			log.err(e.toString());
		}
		log.info("=====================");
		log.info("Test 4: Parsing a Phonology from a .language file");
		file = new File(System.getProperty("user.home") + "/Susquehanna/.English.language");
		fileContents = "";
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				log.info(line);
				fileContents += line + "\n";
			}
			sc.close();
			
			Parser parser = new Parser(fileContents);
			Phonology phonology = parser.parsePhono();
			log.info(phonology.toString());
		} catch (Exception e) {
			log.err(e.toString());
		}
		log.info("=====================");
		log.info("Test 5: Parsing a Language from a .language file");
		file = new File(System.getProperty("user.home") + "/Susquehanna/.English.language");
		try {
			Parser parser = new Parser(file);
			Language lang = parser.parseLanguage();
			log.info(lang.toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.err(e.toString());
		}
	}
	
}
