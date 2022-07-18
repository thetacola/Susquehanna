package gui;

import java.net.URISyntaxException;

import com.pixelduke.control.Ribbon;
import com.pixelduke.control.ribbon.RibbonGroup;
import com.pixelduke.control.ribbon.RibbonTab;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = System.getProperty("java.version");

        Label javaVersionLabel = new Label("Running on Java " + javaVersion + ".");
        Label javaFXVersionLabel = new Label("Bundled with JavaFX SDK 18.0.1.");
        Label algonquinVersionLabel = new Label("Bundled with AlgonquinTTS 0.2.1.");
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
        
        
        
        RibbonTab home = new RibbonTab();
        Ribbon ribbon = new Ribbon();
        
        VBox mainVBox = new VBox(bannerLogoView, javaVersionLabel, javaFXVersionLabel, algonquinVersionLabel, versionLabel);
        mainVBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainVBox);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/icon.png")));
        BackgroundFill backgroundFill = new BackgroundFill(Color.web("#004A7F"), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        mainVBox.setBackground(background);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}