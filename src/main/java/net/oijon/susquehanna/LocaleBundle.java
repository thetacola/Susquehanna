package net.oijon.susquehanna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

import net.oijon.olog.Log;

/**
 * Like ResourceBundle, but reads off file system. Used to be possible with ResourceBundle, but no
 * longer is...
 */
public class LocaleBundle {

	static Log log = App.getLog();
	private Properties p = new Properties();
	
	public LocaleBundle() {
		
	}
	
	public LocaleBundle(File dir, Locale l) {
		load(dir, l);
	}
	
	public void load(File dir, Locale l) {
		log.info("Loading localization pack for " + l.toString() + "...");
		File[] files = dir.listFiles();
		boolean found = false;
		Locale searchLocale = l;
		while (!found) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String expectedName = "SusquehannaBundle_" + searchLocale.getLanguage();
					if (searchLocale.getCountry() != "") {
						expectedName += "_" + searchLocale.getCountry();
					}
					expectedName += ".properties";
					log.debug("Looking for " + expectedName + "...");
					if (files[i].getName().equals(expectedName)) {
						try {
							FileInputStream fis = new FileInputStream(files[i]);
							InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
							BufferedReader br = new BufferedReader(isr);
							
							p.load(br);
							found = true;
						} catch (Exception e) {
							log.err("Unable to load locale, despite finding it!");
							e.printStackTrace();
						}
					}
				}
			}
			if (found == false) {
				if (!searchLocale.getCountry().equals("")) {
					log.warn("Unable to find pack for " + searchLocale.toString() + ". Searching for " +
							searchLocale.getLanguage() + ".");
					searchLocale = new Locale(searchLocale.getLanguage());
				} else if (!searchLocale.getLanguage().equals("en")) {
					log.warn("Unable to find pack for " + searchLocale.toString() + ". Searching for " +
							Locale.US.toString() + ".");
					searchLocale = Locale.US;
				} else {
					log.err("Unable to find any localization!");
					break;
				}
			} else {
				log.info("Loaded pack for " + searchLocale.toString() + "!");
			}
		}
	}
	
	public String get(String input) {
		String returnStr = p.getProperty(input);
		if (returnStr == null) {
			log.warn("Unable to find localization for " + input);
			returnStr = input;
		}
		return returnStr;
	}
}
