package net.oijon.susquehanna.language;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import net.oijon.oling.datatypes.InvalidXMLException;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.language.LanguageProperty;
import net.oijon.olog.Log;
import net.oijon.susquehanna.App;

public class SusquehannaLanguage {
	Language l = Language.NULL;
	File langFile;
	File metadataFile;
	static Log log = App.getLog();
	
	public SusquehannaLanguage(File langFile, File metadataFile) {
		this.langFile = langFile;
		this.metadataFile = metadataFile;
	}
	
	public Language getLanguage() {
		return l;
	}
	
	public File getFile() {
		return langFile;
	}
	
	public void read() {
		if (langFile != null) {
			try {
				l = Language.parse(langFile);
			} catch (ParserConfigurationException | IOException | SAXException | InvalidXMLException e) {
				log.err("Unable to read language from file " + langFile.toString() + "!");
				e.printStackTrace();
				l = Language.NULL;
			}
		}
	}
	
	public void write() {
		if (langFile == null || metadataFile == null) {
			log.err("Unable to write to file! File for language " +
				l.getProperties().getProperty(LanguageProperty.NAME) + " not set!");
		} else {
			
			try {
				l.toFile(langFile);
				
				Properties metadata = new Properties();
				metadata.setProperty("name", l.getProperties().getProperty(LanguageProperty.NAME));
				metadata.setProperty("autonym", l.getProperties().getProperty(LanguageProperty.AUTONYM));
				metadata.setProperty("id", l.getProperties().getProperty(LanguageProperty.ID));
				metadata.setProperty("version_edited", l.getProperties().getProperty(LanguageProperty.VERSION_EDITED));
				
				metadata.setProperty("created", Long.toString(l.getProperties().getCreated().getTime()));
				metadata.setProperty("edited", Long.toString(l.getProperties().getEdited().getTime()));
				
				metadata.store(new FileOutputStream(metadataFile), null);
			} catch (IOException | TransformerException | ParserConfigurationException e) {
				log.err("Unable to write language " + l.getProperties().getProperty(LanguageProperty.NAME));
				e.printStackTrace();
			}
		}
		
	}
	
}
