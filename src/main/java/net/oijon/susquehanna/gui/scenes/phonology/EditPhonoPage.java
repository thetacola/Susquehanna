package net.oijon.susquehanna.gui.scenes.phonology;

import javafx.scene.control.Label;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.PhonemeTable;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.oling.datatypes.Language;

public class EditPhonoPage extends Book {

	public EditPhonoPage() {
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
		PhonemeTable table = new PhonemeTable(App.getSelectedLang().getPhono(), true);
		
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
