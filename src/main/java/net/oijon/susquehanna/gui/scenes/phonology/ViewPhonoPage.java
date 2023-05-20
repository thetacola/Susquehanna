package net.oijon.susquehanna.gui.scenes.phonology;

import javafx.scene.control.Label;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.PHOSYSTable;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.utils.parser.data.Language;

public class ViewPhonoPage extends Book {

	public ViewPhonoPage() {
		super();
		refresh();
	}
	
	@Override
	public void refresh() {
		if (hasViewablePhono()) {
			buildViewable();
		} else {
			buildNonViewable();
		}		
	}

	private void buildViewable() {
		clear();
		
		Label phonoLabel = new Label("Phonology");
		PHOSYSTable table = new PHOSYSTable(App.getSelectedLang(), App.getCurrentFile());
		
		addToLeft(phonoLabel);
		addToLeft(table);
	}
	
	private void buildNonViewable() {
		clear();
		
		Label noLangViewPhono = new Label("Could not display phonology."
				+ " Either no language is selected, or the phonology is invalid.");
		
		addToLeft(noLangViewPhono);
	}
	
	private boolean hasViewablePhono() {
		if (App.getSelectedLang() == Language.NULL) {
			return false;
		} else {
			return true;
		}
	}
	
}
