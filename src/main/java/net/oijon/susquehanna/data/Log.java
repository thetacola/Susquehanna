package net.oijon.susquehanna.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import com.diogonunes.jcolor.AnsiFormat;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

//last edit: 11/26/22 -N3

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
	
	private boolean debug = true;
	private File file;
	private String today;
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private String now;
	private File tempFile;
	
	AnsiFormat fDebug = new AnsiFormat(WHITE_TEXT());
	AnsiFormat fInfo = new AnsiFormat(CYAN_TEXT());
	AnsiFormat fError = new AnsiFormat(RED_TEXT());
	AnsiFormat fCritical = new AnsiFormat(BOLD(), RED_TEXT(), YELLOW_BACK());
	
	/**
	 * Creates the log object
	 */
	public Log() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.today = LocalDate.now().format(formatter);
		File logFile = new File(System.getProperty("user.home") + "/Susquehanna/logs/" + this.today + ".log");
		File tempFile = new File(System.getProperty("user.home") + "/Susquehanna/logs/.logtmp");
		int i = 0;
		while (logFile.exists()) {
			
			i++;
			logFile = new File(System.getProperty("user.home") + "/Susquehanna/logs/" + this.today + "(" + i + ")" + ".log");
		}
		try {
			File logDir = new File(System.getProperty("user.home") + "/Susquehanna/logs/");
			logDir.mkdirs();
			logFile.createNewFile();
			tempFile.createNewFile();
			FileWriter fw = new FileWriter(tempFile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(logFile.toString());
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
	 * Reads the temp file and gets the current log file
	 * @return
	 */
	private File readTempFile() {
		try {
			Scanner sc = new Scanner(tempFile);
			String filePath = "";
			while(sc.hasNextLine()) {
				filePath = sc.nextLine();
			}
			File logFile = new File(filePath);
			return logFile;
		} catch (FileNotFoundException e) {
			this.err(e.toString());
			return null;
		}
	}
	
	public void debug(String input) {
		if (debug) {
			setNow();
			String output = "[DEBUG]    [" + this.now + "] - " + input;
			System.out.println(fDebug.format(output));
			try {
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(fInfo.format(output));
				bw.newLine();
			    bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Prints an info line
	 * @param input What is to be printed
	 */
	public void info(String input) {
		setNow();
		String output = "[INFO]     [" + this.now + "] - " + input;
		System.out.println(fInfo.format(output));
		try {
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(fInfo.format(output));
			bw.newLine();
		    bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	/**
	 * Prints an error.
	 * @param input What is to be printed
	 */
	public void err(String input) {
		setNow();
		String output = "[ERROR]    [" + this.now + "] - " + input;
		System.out.println(fError.format(output));
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
	
	public void critical(String input) {
		setNow();
		String output = "[CRITICAL] [" + this.now + "] - " + input;
		System.out.println(fCritical.format(output));
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
		this.debug("=====================");
		this.debug("List of system properties:");
		Enumeration<Object> keyNames = properties.keys();
		while(keyNames.hasMoreElements()) {
			String key = keyNames.nextElement().toString();
			String value = properties.getProperty(key).toString();
			this.debug(key + " - " + value);
		}
		this.debug("=====================");
	}
	
	public void setDebug(boolean bool) {
		debug = bool;
	}
	
	public void closeLog() {
		this.info("Closing log...");
		this.tempFile.delete();
		this.info("Log closed.");
	}
	
}
