package net.oijon.susquehanna.gui.toolboxes;

import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public class SettingsTools extends Toolbox {

	public SettingsTools() {
		super(Backgrounds.SETTINGSTOOLS);
		
		ToolButton locale = new ToolButton("settings.locale");
        
        this.getChildren().addAll(locale);
        
	}
	
}
