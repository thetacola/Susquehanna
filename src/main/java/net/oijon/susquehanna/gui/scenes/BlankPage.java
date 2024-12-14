package net.oijon.susquehanna.gui.scenes;

public class BlankPage extends Book {
	
	/**
	 * Inherited from Book, does nothing as there's nothing to update on a blank page
	 */
	@Override
	public void refresh() {
		// do nothing
	}
	
	/**
	 * Sets the ID used for this blank page
	 * @param id The ID to be used for this blank page
	 */
	public void setID(String id) {
		super.id = id;
	}
	

}
