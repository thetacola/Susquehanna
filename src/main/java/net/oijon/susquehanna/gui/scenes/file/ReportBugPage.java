package net.oijon.susquehanna.gui.scenes.file;

import javafx.scene.web.WebView;
import net.oijon.susquehanna.gui.scenes.Book;

//last edit: 5/24/23 -N3

/**
 * A book that allows for someone to report a bug via GitHub.
 * @author alex
 *
 */
public class ReportBugPage extends Book {

	public ReportBugPage() {
		super();
		WebView githubView = new WebView();
		githubView.getEngine().load("https://github.com/alexdcramer/Susquehanna/issues/new");
		addToLeft(githubView);
	}
	
	@Override
	public void refresh() {
		
	}

}
