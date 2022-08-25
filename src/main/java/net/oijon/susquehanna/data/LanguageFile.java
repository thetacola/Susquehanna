package net.oijon.susquehanna.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

public class LanguageFile {

	public void createFolders(Language language) {
		File mainDir = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName());
		File phonoDir = new File(mainDir + "/phonology");
		File orthoDir = new File(mainDir + "/orthography");
		File grammarDir = new File(mainDir + "/grammar");
		File lexiconDir = new File(mainDir + "/lexicon");
		mainDir.mkdirs();
		phonoDir.mkdirs();
		orthoDir.mkdirs();
		grammarDir.mkdirs();
		lexiconDir.mkdirs();
		File languageFile = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName() + ".language");
		try (OutputStream output = new FileOutputStream(languageFile)) {
			
			Properties prop = new Properties();
			
			prop.setProperty("name", language.getName());
			Long now = System.currentTimeMillis();
			prop.setProperty("timeCreated", now.toString());
			prop.setProperty("lastEdited", now.toString());
			prop.setProperty("mainFolder", mainDir.toString());
			prop.setProperty("phonologyFolder", phonoDir.toString());
			prop.setProperty("orthographyFolder", orthoDir.toString());
			prop.setProperty("grammarFolder", grammarDir.toString());
			prop.setProperty("lexiconFolder", lexiconDir.toString());
			prop.setProperty("icon", null);
			prop.setProperty("readonly", "false");
			
			prop.store(output, null);
			
			System.out.println(prop);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createLanguage(Language language) {
		File mainDir = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName());
		File phonoDir = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName() + "/phonology");
		File orthoDir = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName() + "/orthography");
		File grammarDir = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName() + "/grammar");
		File lexiconDir = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName() + "/lexicon");
		mainDir.mkdirs();
		phonoDir.mkdirs();
		orthoDir.mkdirs();
		grammarDir.mkdirs();
		lexiconDir.mkdirs();
		File languageFile = new File(System.getProperty("user.home") + "/Susquehanna/" + language.getName() + ".language");
		if (!languageFile.exists()) {
			try (OutputStream output = new FileOutputStream(languageFile)) {
				
				Properties prop = new Properties();
				
				prop.setProperty("name", language.getName());
				Long now = System.currentTimeMillis();
				prop.setProperty("timeCreated", now.toString());
				prop.setProperty("lastEdited", now.toString());
				prop.setProperty("mainFolder", mainDir.toString());
				prop.setProperty("phonologyFolder", phonoDir.toString());
				prop.setProperty("orthographyFolder", orthoDir.toString());
				prop.setProperty("grammarFolder", grammarDir.toString());
				prop.setProperty("lexiconFolder", lexiconDir.toString());
				prop.setProperty("icon", "");
				if (language.getAutonym() != null) {
					prop.setProperty("autonym", language.getAutonym());
				} else {
					prop.setProperty("autonym", "");
				}
				prop.setProperty("readonly", "false");
				
				prop.store(output, null);
				
				System.out.println(prop);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static File[] getLanguageFiles() {
		File[] files;
		try {
            File f = new File(System.getProperty("user.home") + "/Susquehanna/");

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return name.endsWith(".language");
                }
            };
            files = f.listFiles(filter);

            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            files = null;
        }
		return files;
	}
	
	public static void createTestLanguage() {
		File mainDir = new File(System.getProperty("user.home") + "/Susquehanna/testlang");
		File phonoDir = new File(System.getProperty("user.home") + "/Susquehanna/testlang/phonology");
		File orthoDir = new File(System.getProperty("user.home") + "/Susquehanna/testlang/orthography");
		File grammarDir = new File(System.getProperty("user.home") + "/Susquehanna/testlang/grammar");
		File lexiconDir = new File(System.getProperty("user.home") + "/Susquehanna/testlang/lexicon");
		mainDir.mkdirs();
		phonoDir.mkdirs();
		orthoDir.mkdirs();
		grammarDir.mkdirs();
		lexiconDir.mkdirs();
		File languageFile = new File(System.getProperty("user.home") + "/Susquehanna/testlang.language");
		try (OutputStream output = new FileOutputStream(languageFile)) {
			
			Properties prop = new Properties();
			
			prop.setProperty("name", "testlang");
			Long now = System.currentTimeMillis();
			prop.setProperty("timeCreated", now.toString());
			prop.setProperty("lastEdited", now.toString());
			prop.setProperty("mainFolder", mainDir.toString());
			prop.setProperty("phonologyFolder", phonoDir.toString());
			prop.setProperty("orthographyFolder", orthoDir.toString());
			prop.setProperty("grammarFolder", grammarDir.toString());
			prop.setProperty("lexiconFolder", lexiconDir.toString());
			prop.setProperty("icon", "");
			prop.setProperty("readonly", "false");
			
			prop.store(output, null);
			
			System.out.println(prop);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTTSFiles() throws URISyntaxException, IOException {
		net.oijon.algonquin.gui.GUILauncher.copyFiles();
	}
}
