package net.oijon.susquehanna.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;

import net.oijon.susquehanna.SystemInfo;

//last edit: 1/8/2023 -N3
public class Language {

	public static final Language NULL = new Language("null");
	private String autonym = "null";
	private String name = "null";
	private Phonology phono = new Phonology();
	private Language parent = Language.NULL;
	private boolean isReadOnly = false;
	private Date created = Date.from(Instant.now());
	private Date edited = Date.from(Instant.now());
	private String versionEdited = SystemInfo.susquehannaVersion();
	
	public static File[] getLanguageFiles() {
		File[] files;
		try {
            File f = new File(System.getProperty("user.home") + "/Susquehanna/");

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                	if (name.startsWith(".")) {
                		return false;
                	} else if (name.endsWith(".language")) {
                		return true;
                	}
                	return false;
                }
            };
            files = f.listFiles(filter);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            files = null;
        }
		return files;
	}
		
	public Language(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public String getAutonym() {
		return autonym;
	}
	public void setAutonym(String autonym) {
		this.autonym = autonym;
	}
	public Phonology getPhono() {
		return phono;
	}
	public void setPhono(Phonology phono) {
		this.phono = phono;
	}
	public Language getParent() {
		return parent;
	}
	public String getParentName() {
		return parent.getName();
	}
	public void setParent(Language parent) {
		this.parent = parent;
	}
	public Date getCreated() {
		return this.created;
	}
	public void setCreated(Date date) {
		this.created = date;
	}
	public Date getEdited() {
		return this.edited;
	}
	public void setEdited(Date date) {
		this.edited = date;
	}
	public boolean isReadOnly() {
		return isReadOnly;
	}
	public void setReadOnly(boolean bool) {
		this.isReadOnly = bool;
	}
	public String getVersion() {
		return versionEdited;
	}
	public void setVersion(String version) {
		this.versionEdited = version;
	}
	public void toFile(File file) throws IOException {
		edited = Date.from(Instant.now());
		String data = "===PHOSYS Start===\n";
		data += this.toString();
		data += "\n===PHOSYS End===";
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(data);
		bw.close();
	}
	public String toString() {
		String returnString = "===Meta Start===\n";
		returnString += "susquehannaVersion:" + versionEdited + "\n";
		returnString += "name:" + name + "\n";
		returnString += "autonym:" + autonym + "\n";
		returnString += "timeCreated:" + created.getTime() + "\n";
		returnString += "lastEdited:" + edited.getTime() + "\n";
		returnString += "readonly:" + isReadOnly + "\n";
		returnString += "parent:" + parent.getName() + "\n";
		returnString += "===Meta End===\n";
		returnString += phono.toString();
		return returnString;
	}
	
}
