package net.oijon.susquehanna.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Properties;

//last edit: 11/4/22 -N3

/**
 * TODO: Get log to work on other classes
 * This could be done by creating a temporary file with the name of the current file
 * This file could then be deleted once the program closes
 */

/**
 * Simple log utility to help with getting console output to file
 * @author N3ther
 *
 */
public class Log {

	private File file;
	private String today;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private String now;
	
	/**
	 * Creates the log object
	 */
	public Log() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.today = LocalDate.now().format(formatter);
		File logFile = new File(System.getProperty("user.home") + "/Susquehanna/logs/" + this.today + ".log");
		int i = 0;
		while (logFile.exists()) {
			i++;
			logFile = new File(System.getProperty("user.home") + "/Susquehanna/logs/" + this.today + "(" + i + ")" + ".log");
		}
		try {
			File logDir = new File(System.getProperty("user.home") + "/Susquehanna/logs/");
			logDir.mkdirs();
			logFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.file = logFile;
	}
	
	/**
	 * Sets the definition of "now", as in what is the time the print was sent out
	 */
	private void setNow() {
		LocalDateTime now = LocalDateTime.now();
		this.now = this.dtf.format(now);
	}
	
	/**
	 * Prints a line
	 * @param input What is to be printed
	 */
	public void println(String input) {
		setNow();
		String output = "[" + this.now + "] - " + input;
		System.out.println(output);
		try {
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(output);
			bw.newLine();
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	/**
	 * Prints without creating a new line
	 * @param input What is to be printed
	 */
	public void print(String input) {
		setNow();
		String output = "[" + this.now + "] - " + input;
		System.out.print(output);
		try {
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(output);
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	/**
	 * Prints an error, identical to println() but with an [ERROR] tag added to easily identify it in logs
	 * @param input What is to be printed
	 */
	public void err(String input) {
		setNow();
		String output = "[ERROR] [" + this.now + "] - " + input;
		System.out.println(output);
		try {
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(output);
			bw.newLine();
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	/**
	 * Prints system information to console and writes to file
	 */
	public void logSystemInfo() {
		Properties properties = System.getProperties();
		this.println("=====================");
		this.println("List of system properties:");
		Enumeration<Object> keyNames = properties.keys();
		while(keyNames.hasMoreElements()) {
			String key = keyNames.nextElement().toString();
			String value = properties.getProperty(key).toString();
			this.println(key + " - " + value);
		}
		this.println("=====================");
	}
	
}
