package net.oijon.susquehanna.gui.scenes.file;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.LocaleBundle;
import net.oijon.susquehanna.SystemInfo;
import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.FileTools;

public class WelcomePage extends Book {

	private LocaleBundle lb = App.lb;
	
	
	public WelcomePage() {
		super();
		this.id = "file.welcome";
		this.toolbox = new FileTools();
		refresh();
	}
	
	@Override
	public void refresh() {
		clearLeft();
		clearRight();
		
		ImageView susquehannalogo = new ImageView(new Image(InfoPage.class.getResourceAsStream("/img/icon.png")));
		
		Label welcome = new Label(lb.get(id + ".welcome"));
		welcome.setFont(Fonts.OPENSANS_BOLD);
		//welcome.setPadding(new Insets(40, 0, 0, 0));
		
		Label version = new Label(SystemInfo.susquehannaVersion());
		version.setFont(Fonts.OPENSANS);
		
		addToLeft(susquehannalogo);
		addToLeft(welcome);
		addToLeft(version);
		
		Label commonButtons = new Label(lb.get(id + ".common"));
		commonButtons.setFont(Fonts.OPENSANS_ITALIC);
		commonButtons.setPadding(new Insets(10));
		
		// TODO: add more as the application grows.
		// make sure not to add anything requiring a language to be open first,
		// as that may confuse the user.
		HBox buttons = new HBox();
		buttons.setAlignment(Pos.TOP_CENTER);
		buttons.setSpacing(10);
		
		ToolButton add = new ToolButton("file.add");
		ToolButton open = new ToolButton("file.open");
		ToolButton locale = new ToolButton("settings.locale");
		
		add.setTextFill(Color.BLACK);
		open.setTextFill(Color.BLACK);
		locale.setTextFill(Color.BLACK);
		
		buttons.getChildren().addAll(add, open, locale);
		
		addToRight(commonButtons);
		addToRight(buttons);
	}

}
