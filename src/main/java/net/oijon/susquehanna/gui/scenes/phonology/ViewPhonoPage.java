package net.oijon.susquehanna.gui.scenes.phonology;

import javafx.scene.control.Label;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.PhonemeTable;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.OnePageBook;

public class ViewPhonoPage extends OnePageBook {

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
		phonoLabel.setFont(Fonts.OPENSANS_BOLD);
		PhonemeTable testTable = new PhonemeTable(App.getSelectedLang().getPhono(), false);
		//PHOSYSTable table = new PHOSYSTable(App.getSelectedLang(), App.getCurrentFile());
		
		addToLeft(phonoLabel);
		addToLeft(testTable);
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
