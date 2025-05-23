package net.oijon.susquehanna.gui.scenes.phonology;

import javafx.scene.control.Label;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.components.PhonemeTable;
import net.oijon.susquehanna.gui.resources.Fonts;
import net.oijon.susquehanna.gui.scenes.OnePageBook;
import net.oijon.susquehanna.gui.toolboxes.PhonologyTools;
import net.oijon.oling.datatypes.language.Language;

public class EditPhonoPage extends OnePageBook {

	PhonemeTable table;
	
	public EditPhonoPage() {
		super();
		id = "phono.edit";
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
		if (table != null) {
			table.refresh();
		}
	}
	
	public void refreshOnNewTable() {
		if (hasViewablePhono()) {
			buildViewable();
		} else {
			buildNonViewable();
		}
	}

	private void buildViewable() {
		clear();
		
		Label phonoLabel = new Label("Phonology");
		phonoLabel.setFont(Fonts.OPENSANS_BOLD);
		table = new PhonemeTable(App.getSelectedLang().getPhono(), true);
		
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
		return !App.getSelectedLang().equals(Language.NULL);
	}

}
