package net.oijon.susquehanna.gui.scenes.settings;

import javafx.scene.control.Label;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.SettingsTools;

public class PreferencesPage extends Book {

	public PreferencesPage() {
		super();
		id = "settings.preferences";
		toolbox = new SettingsTools();
		
		Label testLabel = new Label("test");
		addToLeft(testLabel);
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
