package net.oijon.susquehanna;

import org.junit.jupiter.api.Test;

import net.oijon.olog.Log;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class UnitTests {

	Log log = new Log(System.getProperty("user.home") + "/.Susquehanna_unittests");
	
	@Test
	void testEmptyHomeDir() {
		log.info("Testing home dir initialization...");
		File susquehannaDir = new File(System.getProperties().get("user.home") + "/Susquehanna");
		File backupDir = new File(System.getProperties().get("user.home") + "/Susquehanna_backup");
		if (susquehannaDir.exists()) {
			log.info("Moving current home dir to " + backupDir.toString());
			susquehannaDir.renameTo(backupDir);
		}
		
		assertFalse(susquehannaDir.exists());
		
		log.info("Launching application...");
		Launcher.main(new String[0]);
		log.info("Application closed. Deleting test home dir...");
		
		assertTrue(susquehannaDir.exists());
		File config = new File(susquehannaDir.toString() + "/config.properties");
		File localizationPacks = new File(susquehannaDir.toString() + "/localizationPacks");
		File de = new File(localizationPacks.toString() + "/SusquehannaBundle_de_DE.properties");
		File enUS = new File(localizationPacks.toString() + "/SusquehannaBundle_en_US.properties");
		
		assertTrue(config.exists());
		assertTrue(de.exists());
		assertTrue(enUS.exists());
		
		// deletes everything made in the test
		try {
			Files.walkFileTree(susquehannaDir.toPath(), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Test home dir deleted. Moving backup back to " + susquehannaDir.toString());
		backupDir.renameTo(susquehannaDir);
		log.info("Home dir initialization test successful!");
	}
}
