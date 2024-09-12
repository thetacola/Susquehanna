package net.oijon.susquehanna.gui.toolboxes;

import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public class FileTools extends Toolbox {

	public FileTools() {
		super(Backgrounds.FILETOOLS);
		
		ToolButton addLanguage = new ToolButton("New\nLanguage");
        ToolButton openLanguage = new ToolButton("Open\nLanguage");
        ToolButton reportBug = new ToolButton("Report Bug");
        ToolButton info = new ToolButton("Info");
        
        // transfer actions
        addLanguage.createTransferAction("file.add");
        openLanguage.createTransferAction("file.open");
        reportBug.createTransferAction("file.report");
        info.createTransferAction("file.info");
        
        this.getChildren().addAll(addLanguage, openLanguage, info, reportBug);
	}
	
}
