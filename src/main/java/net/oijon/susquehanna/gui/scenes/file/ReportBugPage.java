package net.oijon.susquehanna.gui.scenes.file;

import javafx.scene.web.WebView;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.FileTools;

//last edit: 9/12/24 -N3

/**
 * A book that allows for someone to report a bug via GitHub.
 * @author alex
 *
 */
public class ReportBugPage extends Book {

	public ReportBugPage() {
		super();
		id = "file.report";
		toolbox = new FileTools();
		
		WebView githubView = new WebView();
		githubView.getEngine().load("https://github.com/alexdcramer/Susquehanna/issues/new");
		addToLeft(githubView);
	}
	
	@Override
	public void refresh() {
		
	}

}
