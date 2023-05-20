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

	/**
	 * Builds the book if the phonology is currently viewable
	 */
	private void buildViewable() {
		clear();
		
		Label phonoLabel = new Label("Phonology");
		PHOSYSTable table = new PHOSYSTable(App.getSelectedLang(), App.getCurrentFile());
		
		addToLeft(phonoLabel);
		addToLeft(table);
	}
	
	/**
	 * Builds the book if the phonology is not currently viewable
	 */
	private void buildNonViewable() {
		clear();
		
		Label noLangViewPhono = new Label("Could not display phonology."
				+ " Either no language is selected, or the phonology is invalid.");
		
		addToLeft(noLangViewPhono);
	}
	
	/**
	 * Checks if the there is currently a selected language.
	 * @return
	 */
	private boolean hasViewablePhono() {
		if (App.getSelectedLang().equals(Language.NULL)) {
			return false;
		} else {
			return true;
		}
	}
	
}
