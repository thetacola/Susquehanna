package net.oijon.susquehanna.gui.scenes.settings;

import net.oijon.susquehanna.gui.components.OrderedList;
import net.oijon.susquehanna.gui.scenes.Book;
import net.oijon.susquehanna.gui.toolboxes.SettingsTools;

public class ModsPage extends Book {

	public ModsPage() {
		super();
		id = "settings.mods";
		toolbox = new SettingsTools();
		
		OrderedList testlist = new OrderedList();
		testlist.add("foo");
		testlist.add("bar");
		addToLeft(testlist);
		
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
