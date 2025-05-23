package net.oijon.susquehanna.gui.toolboxes;

import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public class OrthographyTools extends Toolbox {

	public OrthographyTools() {
		super(Backgrounds.ORTHOGRAPHYTOOLS);
		
		ToolButton viewOrthography = new ToolButton("ortho.view");
        ToolButton editOrthography = new ToolButton("ortho.edit");
        ToolButton script = new ToolButton("ortho.script");
        
        this.getChildren().addAll(viewOrthography, editOrthography, script);
        
	}
	
}
