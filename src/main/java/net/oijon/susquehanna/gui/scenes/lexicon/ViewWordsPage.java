package net.oijon.susquehanna.gui.scenes.lexicon;

import javafx.scene.control.Label;
import net.oijon.susquehanna.App;
import net.oijon.susquehanna.gui.DetailedWordList;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.utils.parser.data.Language;

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
		
		DetailedWordList wordList = new DetailedWordList(App.getSelectedLang());
		
		addToLeft(wordList);
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
