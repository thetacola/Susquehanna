package net.oijon.susquehanna.gui.scenes.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.oling.Parser;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.oling.datatypes.language.LanguageProperty;

public class OpenLangPage extends Book {

	private VBox languageSelect = new VBox();
	
	public OpenLangPage() {
		super();
		refresh();
		Button refreshLanguageList = new Button("Refresh");
        
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
		File[] files = Language.getLanguageFiles(
				new File(System.getProperty("user.home") + "/Susquehanna/"));
    	log.info("Found " + files.length + " language(s)");
    	log.debug("Language(s) found:");
    	for (int i = 0; i < files.length; i++) {
    		log.debug(files[i].getName());
    	}
    	languageSelect.getChildren().clear();
    	
    	if (files != null) {
	        for (int i = 0; i < files.length; i++) {
	        	Label nameLabel = new Label();
	        	nameLabel.setFont(opensans);
	        	Label timeCreatedLabel = new Label();
	        	timeCreatedLabel.setFont(opensans);
	        	Label lastAccessedLabel = new Label();
	        	lastAccessedLabel.setFont(opensans);
	        	Image icon = new Image(App.class.getResourceAsStream("/img/no-image.png"));
	        	ImageView iconView = new ImageView(icon);
	        	Button select = new Button("Select");
	        	select.setPrefSize(100, 30);
	        	select.setMinSize(100, 30);
	        	Button delete = new Button("Delete");
	        	delete.setPrefSize(100, 30);
	        	delete.setMinSize(100, 30);
	        	VBox infoBox = new VBox();
	        	HBox buttonHBox = new HBox();
	        	HBox box = new HBox();
	        	
	        	try (InputStream input = new FileInputStream(files[i])) {
	        		final File file = files[i];
	                Properties prop = new Properties();
	
	                prop.load(input);
	                String name = prop.getProperty("name");
	                nameLabel.setText(name);
	                Long timeCreated = Long.valueOf(prop.getProperty("timeCreated"));
	                Date timeCreatedDate = new Date(timeCreated);
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	                String timeCreatedString = sdf.format(timeCreatedDate);
	                timeCreatedLabel.setText(" Created on: " + timeCreatedString);
	                
	                Long lastAccessed = Long.valueOf(prop.getProperty("lastEdited"));
	                Date lastAccessedDate = new Date(lastAccessed);
	                String lastAccessedString = sdf.format(lastAccessedDate);
	                lastAccessedLabel.setText(" Last modified: " + lastAccessedString);
	                
	                select.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Parser parser = new Parser(file);
							Language l = Language.NULL;
							try {
								l = parser.parseLanguage();
								App.setSelectedLang(l, file);
							} catch (Exception e) {
								App.setSelectedLangNull();
								e.printStackTrace();
								log.err("Unable to select language!");
								log.err(e.toString());
							}
							log.info("Selected language: "
							+ App.getSelectedLang().getProperties().getProperty(LanguageProperty.NAME));
						}
	                	
	                });
	                delete.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Stage delete = new Stage();
							delete.setWidth(400);
							delete.setHeight(150);
							
							Label label = new Label("Are you sure you want to delete " + name + "?");
							Button yes = new Button("Yes");
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
							Button no = new Button("No");
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
	        	languageSelect.getChildren().add(box);
	        	//languageList.setText(fileNames);
	        }
        }
	}

}
