package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        
        Label javaVersionLabel = new Label("Running on Java " + System.getProperty("java.version") + ".");
        Label javaFXVersionLabel = new Label("Bundled with JavaFX SDK 18.0.1.");
        Label algonquinVersionLabel = new Label("Bundled with AlgonquinTTS 0.2.2.");
        Label versionLabel = new Label("Version 0.0.1 \"Otsego\", build 22w29a ***SNAPSHOT VERSION***");
        javaVersionLabel.setTextFill(Color.WHITE);
        javaFXVersionLabel.setTextFill(Color.WHITE);
        algonquinVersionLabel.setTextFill(Color.WHITE);
        versionLabel.setTextFill(Color.WHITE);
        Image bannerLogo = new Image(App.class.getResourceAsStream("img/bannerlogo.png"));
        Image hoveredBannerLogo = new Image(App.class.getResourceAsStream("img/bannerlogo-hover.png"));
        ImageView bannerLogoView = new ImageView(bannerLogo);
        bannerLogoView.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouse) {
				bannerLogoView.setImage(hoveredBannerLogo);
			}
        	
        });
        bannerLogoView.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouse) {
				bannerLogoView.setImage(bannerLogo);
			}
        	
        });
        
        BackgroundFill backgroundFill = new BackgroundFill(Color.web("#004A7F"), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        
        VBox homeVBox = new VBox(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        
        Button homeButton = new Button("Home");
        VBox navVBox = new VBox(homeButton);
        ScrollPane navBox = new ScrollPane();
        navBox.setContent(navVBox);
        
        HBox homeHBox = new HBox(navBox, homeVBox);
        homeVBox.setAlignment(Pos.CENTER);
        homeHBox.setAlignment(Pos.TOP_LEFT);
        homeHBox.setHgrow(homeVBox, Priority.ALWAYS);
        
        homeHBox.setBackground(background);
        Scene home = new Scene(homeHBox);
        
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stage.setScene(home);			
			}
        });
        
        
        stage.setScene(home);
        stage.setMaximized(true);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/icon.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}