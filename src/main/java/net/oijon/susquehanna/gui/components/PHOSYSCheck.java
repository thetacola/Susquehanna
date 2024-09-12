package net.oijon.susquehanna.gui.components;

import javafx.scene.control.CheckBox;

/**
 * Creates a CheckBox with an attached sound.
 * 
 * Used to create editable PHOSYSTables
 * @author alex
 *
 */
public class PHOSYSCheck extends CheckBox {

	private String sound;
	
	public PHOSYSCheck(String sound) {
		this.sound = sound;
	}
	
	/**
	 * Should only be used to initialize a variable
	 */
	public PHOSYSCheck() {
		this.sound = " ";
	}
	
	public String getSound() {
		return sound;
	}
	
}
