package net.oijon.susquehanna.gui.scenes.file;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.SystemInfo;
import net.oijon.susquehanna.gui.scenes.Book;

public class InfoPage extends Book {

	public InfoPage() {
		super();
		Label javaVersionLabel = new Label("Running on Java " + System.getProperty("java.version") + ".");
        javaVersionLabel.setFont(opensans);
        Label javaFXVersionLabel = new Label("Bundled with JavaFX " + System.getProperty("javafx.runtime.version") + ".");
        javaFXVersionLabel.setFont(opensans);
        Label utilsVersionLabel = new Label("Powered by " + SystemInfo.utilsVersion());
        utilsVersionLabel.setFont(opensans);
        Label versionLabel = new Label("Version " + SystemInfo.susquehannaVersion());
        versionLabel.setFont(opensans);
        Image bannerLogo = new Image(App.class.getResourceAsStream("/img/icon.png"));
        ImageView bannerLogoView = new ImageView(bannerLogo);
        
        Label madeByOijon = new Label("Brought to you by Oijon - oijon.net");
        madeByOijon.setFont(opensans);
        Image oijonLogo = new Image(App.class.getResourceAsStream("/img/oijon.png"));
        ImageView oijonView = new ImageView(oijonLogo);
        
        addToLeft(bannerLogoView);
        addToLeft(javaVersionLabel);
        addToLeft(javaFXVersionLabel);
        addToLeft(utilsVersionLabel);
        addToLeft(versionLabel);
        
        addToRight(oijonView);
        addToRight(madeByOijon);
	}

	@Override
	public void refresh() {
		
	}
	
}
