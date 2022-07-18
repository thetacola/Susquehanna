package gui;

import java.net.URISyntaxException;

import com.pixelduke.control.ribbon.RibbonTab;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        Label versionLabel = new Label("Running on Java " + javaVersion + ".");
        versionLabel.setTextFill(Color.WHITE);
        Image bannerLogo = new Image(App.class.getResourceAsStream("img/bannerlogo.png"));
        ImageView bannerLogoView = new ImageView(bannerLogo);
        
        RibbonTab ribbon = new RibbonTab();
        
        VBox mainVBox = new VBox(bannerLogoView, versionLabel);
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