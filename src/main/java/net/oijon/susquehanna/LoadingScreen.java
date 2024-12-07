package net.oijon.susquehanna;

import java.time.Instant;

import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.oijon.olog.Log;
import javafx.scene.layout.CornerRadii;

public class LoadingScreen extends Preloader {

	Log log = App.getLog();
	Stage stage;

	
	@Override
	public void start(Stage stage) throws Exception {
		
		Background color = new Background(new BackgroundFill(Color.web("#064288"),
				CornerRadii.EMPTY, Insets.EMPTY));
		
		this.stage = stage;
		
		if (log.isDebug()) {
			log.debug("Starting loading screen at " + Instant.now());
		}
		
		ImageView iv = new ImageView(new Image(App.class.getResourceAsStream("/img/loading-screen.png")));
		
		Label version = new Label();
		version.setBackground(color);
		version.setTextFill(Color.web("#ecca00"));
		version.setText(SystemInfo.susquehannaVersion());
		version.setContentDisplay(ContentDisplay.CENTER);
		
		VBox rootVBox = new VBox(iv, version);
		rootVBox.setAlignment(Pos.BOTTOM_CENTER);
		rootVBox.setBackground(color);
        Scene root = new Scene(rootVBox);
		
        stage.setScene(root);
        stage.setTitle("Susquehanna Conlang Manager");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/img/icon.png")));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        if (log.isDebug()) {
        	log.debug("Finished loading screen at " + Instant.now());
        }
	}
	
	
	@Override
	public void handleStateChangeNotification(StateChangeNotification evt) {
	    if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
	        if (stage.isShowing()) {
	            //fade out, hide stage at the end of animation
	            FadeTransition ft = new FadeTransition(
	                Duration.millis(1000), stage.getScene().getRoot());
	                ft.setFromValue(1.0);
	                ft.setToValue(0.0);
	                final Stage s = stage;
	                EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
	                    public void handle(ActionEvent t) {
	                        s.hide();
	                    }
	                };
	                ft.setOnFinished(eh);
	                ft.play();
	        } else {
	            stage.hide();
	        }
	    }
	}
}
