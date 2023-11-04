package net.oijon.susquehanna.gui.scenes.phonology;

import javafx.scene.control.Label;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.PHOSYSTable;
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
		PHOSYSTable table = new PHOSYSTable(App.getSelectedLang(), true, App.getCurrentFile());
		
		Label phonoSystemLabel = new Label("Phonology System - " + App.getSelectedLang().getPhono().getPhonoSystem().getName());
		log.debug(App.getSelectedLang().toString());
		PHOSYSTable testTable = new PHOSYSTable(App.getSelectedLang().getPhono().getPhonoSystem());
		
		addToLeft(phonoLabel);
		addToLeft(table);
		
		addToRight(phonoSystemLabel);
		addToRight(testTable);
	}
	
	private void buildNonViewable() {
		clear();
		
		Label noLangViewPhono = new Label("Could not display phonology."
				+ " Either no language is selected, or the phonology is invalid.");
		
		Label noLangViewPhoSys = new Label("Could not display phonology system."
				+ " Either no language is selected, or the phonology system is invalid.");
		
		addToLeft(noLangViewPhono);
		
		addToRight(noLangViewPhoSys);
	}
	
	private boolean hasViewablePhono() {
		if (App.getSelectedLang() == Language.NULL) {
			return false;
		} else {
			return true;
		}
	}

}
