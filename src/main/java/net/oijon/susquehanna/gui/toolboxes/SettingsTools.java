package net.oijon.susquehanna.gui.toolboxes;

import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public class SettingsTools extends Toolbox {

	public SettingsTools() {
		super(Backgrounds.SETTINGSTOOLS);
		
		ToolButton preferences = new ToolButton("Preferences");
		ToolButton mods = new ToolButton("Mods");
		
		preferences.createTransferAction("settings.preferences");
		mods.createTransferAction("settings.mods");
		
		this.getChildren().addAll(preferences, mods);
		
	}
	
}
