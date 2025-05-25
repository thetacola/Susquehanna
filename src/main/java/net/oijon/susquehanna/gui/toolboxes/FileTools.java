package net.oijon.susquehanna.gui.toolboxes;

import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public class FileTools extends Toolbox {

	public FileTools() {
		super(Backgrounds.FILETOOLS);
		
		ToolButton addLanguage = new ToolButton("file.add");
        ToolButton openLanguage = new ToolButton("file.open");
        ToolButton reportBug = new ToolButton("file.report");
        ToolButton info = new ToolButton("file.info");
        ToolButton welcome = new ToolButton("file.welcome");
        
        this.getChildren().addAll(addLanguage, openLanguage, info, reportBug, welcome);
	}
	
}
