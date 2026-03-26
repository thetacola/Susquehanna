package net.oijon.susquehanna.gui.scenes.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.LocaleBundle;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.FileTools;
import net.oijon.oling.LegacyParser;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.language.LanguageProperty;

public class OpenLangPage extends Book {

	private VBox languageSelect = new VBox();

	LocaleBundle lb = App.lb;
	
	public OpenLangPage() {
		super();
		id = "file.open";
		toolbox = new FileTools();
		
		refresh();
		Button refreshLanguageList = new Button(lb.get("gen.refresh"));
        
        refreshLanguageList.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent e) {
        		refresh();
        	}
        });
        
        addToLeft(languageSelect);
        addToLeft(refreshLanguageList);
	}
	
	@Override
	public void refresh() {
		File homeDir = new File(System.getProperty("user.home") + "/Susquehanna/");
		File[] legacyFiles = Language.getLanguageFiles(homeDir);
    	log.warn("Found " + legacyFiles.length + " legacy language(s)");
    	if (log.isDebug()) {
	    	log.debug("Language(s) found:");
	    	for (File file : legacyFiles) {
	    		log.debug(file.getName());
	    	}
    	}
    	languageSelect.getChildren().clear();
    	
    	File[] xmlFiles = homeDir.listFiles(new FilenameFilter() {
    		public boolean accept(File dir, String name) {
    			return name.toLowerCase().endsWith(".xml");
    		}
    	});
    	log.info("Found " + xmlFiles.length + " language(s)");
    	
    	if (xmlFiles != null) {
	        for (File file : xmlFiles) {
	        	HBox box = createSelectionBox(file);
	        	languageSelect.getChildren().add(box);
	        }
        }
    	if (legacyFiles != null) {
    		for (File file : legacyFiles) {
    			HBox box = createSelectionBox(file);
    			box.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null)));
	        	languageSelect.getChildren().add(box);
    		}
    	}
	}
	
	private HBox createSelectionBox(File file) {
		Label nameLabel = new Label();
    	nameLabel.setFont(Fonts.OPENSANS);
    	Label timeCreatedLabel = new Label();
    	timeCreatedLabel.setFont(Fonts.OPENSANS);
    	Label lastAccessedLabel = new Label();
    	lastAccessedLabel.setFont(Fonts.OPENSANS);
    	Image icon = new Image(App.class.getResourceAsStream("/img/no-image.png"));
    	ImageView iconView = new ImageView(icon);
    	Button select = new Button(lb.get("gen.select"));
    	select.setPrefSize(100, 30);
    	select.setMinSize(100, 30);
    	Button delete = new Button(lb.get("gen.delete"));
    	delete.setPrefSize(100, 30);
    	delete.setMinSize(100, 30);
    	VBox infoBox = new VBox();
    	HBox buttonHBox = new HBox();
    	HBox box = new HBox();
    	
    	try (InputStream input = new FileInputStream(file)) {
            Properties prop = new Properties();

            prop.load(input);
            String name = prop.getProperty("name");
            nameLabel.setText(name);
            Long timeCreated = Long.valueOf(prop.getProperty("timeCreated"));
            Date timeCreatedDate = new Date(timeCreated);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String timeCreatedString = sdf.format(timeCreatedDate);
            timeCreatedLabel.setText(" " + lb.get("file.open.createdon") + " " + timeCreatedString);
            
            Long lastAccessed = Long.valueOf(prop.getProperty("lastEdited"));
            Date lastAccessedDate = new Date(lastAccessed);
            String lastAccessedString = sdf.format(lastAccessedDate);
            lastAccessedLabel.setText(" " + lb.get("file.open.lastaccessed") + " " + lastAccessedString);
            
            select.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String[] splitDot = file.getName().split(Pattern.quote("."));
					String ext = splitDot[splitDot.length - 1];
					Language l = Language.NULL;
					if (ext.equals("language")) {
						LegacyParser parser = new LegacyParser(file);
						try {
							l = parser.parseLanguage();
							App.setSelectedLang(l, file);
						} catch (Exception e) {
							App.setSelectedLangNull();
							e.printStackTrace();
							log.err("Unable to select language!");
							log.err(e.toString());
						}
					} else if (ext.equals("xml")) {
						try {
							DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				            DocumentBuilder builder = factory.newDocumentBuilder();
							
							Scanner reader = new Scanner(file, StandardCharsets.UTF_8);
				            boolean firstLine = true;
				            String data = "";
				            while (reader.hasNextLine()) {
				                if (firstLine) {
				                    data = reader.nextLine();
				                    String[] splitData = data.split("<\\?xml");
				                    data = "<?xml" + splitData[1];
				                    firstLine = false;
				                } else {

				                    data += reader.nextLine() + "\n";
				                }
				            }
				            reader.close();
				            Document doc = builder.parse(new InputSource(new StringReader(data)));
				            Element element = doc.getDocumentElement();
				            l = new Language(element);
				            App.setSelectedLang(l, file);
						} catch (Exception e) {
							App.setSelectedLangNull();
							e.printStackTrace();
							log.err("Unable to select language!");
							log.err(e.toString());
						}
					} else {
						App.setSelectedLangNull();
						log.err("Unable to load language from " + file.getName() + 
								" (Unsupported file extension)");
					}
					log.info("Selected language: "
							+ App.getSelectedLang().getProperties().getProperty(LanguageProperty.NAME));
				}
            });
            delete.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					Stage delete = new Stage();
					delete.setWidth(600);
					delete.setHeight(150);
					
					Label label = new Label(lb.get("file.open.deleteconfirm").replace("{0}", name));
					label.setFont(Fonts.OPENSANS);
					Button yes = new Button(lb.get("gen.yes"));
					yes.setPrefSize(50, 25);
					yes.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							File mainFolder = new File(System.getProperty("user.home") + "/Susquehanna/" + name);
							File langFile = new File(System.getProperty("user.home") + "/Susquehanna/" + name + ".language");
							
							if (App.getSelectedLang().getProperties().getProperty(LanguageProperty.NAME)
									== name) {
								App.setSelectedLangNull();
								log.err("The currently selected language is being deleted! Unselecting " + name + "...");
							}
							
							try {
								input.close();
							} catch (IOException e) {
								log.err(e.toString());
								e.printStackTrace();
							}
							File[] allContents = mainFolder.listFiles();
						    if (allContents != null) {
						        for (File file : allContents) {
						            if (file.delete()) {
						            	log.info("Deleted " + file.toString());
						            }
						        }
						    }
							mainFolder.delete();
							if (langFile.delete()) {
								log.info("Successfully deleted language " + name + " at " + langFile.toString());
							} else {
								log.err("Unable to delete " + name + " at " + langFile.toString());
							}
							refresh();
							delete.close();
						}
						
					});
					Button no = new Button(lb.get("gen.no"));
					no.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							delete.close();
						}
						
					});
					no.setPrefSize(50, 25);
					
					HBox buttons = new HBox(yes, no);
					buttons.setAlignment(Pos.CENTER);
					VBox popupBox = new VBox(label, buttons);
					popupBox.setAlignment(Pos.CENTER);
					
					Scene popup = new Scene(popupBox);
					delete.setScene(popup);
					delete.show();
				}
            	
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    	
    	buttonHBox.getChildren().addAll(select, delete);
    	infoBox.getChildren().addAll(nameLabel, timeCreatedLabel, lastAccessedLabel, buttonHBox);
    	box.getChildren().addAll(iconView, infoBox);
    	
    	return box;
    	//languageList.setText(fileNames);
	}

}
