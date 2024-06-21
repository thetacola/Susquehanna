package net.oijon.susquehanna.gui.scenes.lexicon;

import javafx.scene.control.Label;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.WordDisplay;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.oling.datatypes.language.Language;

public class ViewWordsPage extends Book {

	public ViewWordsPage() {
		super();
		refresh();
	}
	
	@Override
	public void refresh() {
		if (hasViewableLexicon()) {
			buildViewable();
		} else {
			buildNonViewable();
		}		
	}

	private void buildViewable() {
		clear();
		
		for (int i = 0; i < App.getSelectedLang().getLexicon().size(); i++) {
			addToLeft(new WordDisplay(App.getSelectedLang().getLexicon().getWord(i), true));
		}
	}
	
	private void buildNonViewable() {
		clear();
		
		Label cantDisplay = new Label("Could not display lexicon."
				+ " Either no language is selected, or the lexicon is invalid.");
		
		addToLeft(cantDisplay);
	}
	
	private boolean hasViewableLexicon() {
		if (App.getSelectedLang() == Language.NULL) {
			return false;
		} else {
			return true;
		}
	}
	
}
