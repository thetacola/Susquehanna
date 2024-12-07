package net.oijon.susquehanna.gui.scenes.phonology;

import javafx.scene.control.Label;
import net.oijon.oling.datatypes.language.Language;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.components.PhonemeTable;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.OnePageBook;
import net.oijon.susquehanna.gui.toolboxes.PhonologyTools;

public class ViewPhonoPage extends OnePageBook {

	PhonemeTable table;
	
	public ViewPhonoPage() {
		super();
		id = "phono.view";
		toolbox = new PhonologyTools();
		refreshOnNewTable();
	}
	
	@Override
	public void updateOnLanguageChange() {
		super.updateOnLanguageChange();
		refreshOnNewTable();
	}
	
	@Override
	public void refresh() {
		table.refresh();
	}
	
	public void refreshOnNewTable() {
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
		table = new PhonemeTable(App.getSelectedLang().getPhono(), false);
		
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
		return !App.getSelectedLang().equals(Language.NULL);
	}
	
}
