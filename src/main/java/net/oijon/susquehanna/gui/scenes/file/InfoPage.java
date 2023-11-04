package net.oijon.susquehanna.gui.scenes.file;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.oijon.susquehanna.SystemInfo;
import net.oijon.susquehanna.gui.scenes.Book;

public class InfoPage extends Book {

	
	public InfoPage() {
		super();
		Label javaVersionLabel = new Label("Running on Java " + System.getProperty("java.version") + ".");
        javaVersionLabel.setFont(opensans);
        Label javaFXVersionLabel = new Label("Bundled with JavaFX " + System.getProperty("javafx.runtime.version") + ".");
        javaFXVersionLabel.setFont(opensans);
        Label olingVersionLabel = new Label("Powered by " + SystemInfo.olingVersion());
        olingVersionLabel.setFont(opensans);
        Label versionLabel = new Label("Version " + SystemInfo.susquehannaVersion());
        versionLabel.setFont(opensans);
        ImageView logo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/icon.png")));
        ImageView oijonLogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/oijon.png")));
        
        Label madeByOijon = new Label("Brought to you by Oijon - oijon.net");
        madeByOijon.setFont(opensans);
        
        addToLeft(logo);
        addToLeft(javaVersionLabel);
        addToLeft(javaFXVersionLabel);
        addToLeft(olingVersionLabel);
        addToLeft(versionLabel);
        
        addToRight(oijonLogo);
        addToRight(madeByOijon);
	}

	@Override
	public void refresh() {
		
	}
	
}
