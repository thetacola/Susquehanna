package net.oijon.susquehanna.gui.toolboxes;

import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Backgrounds;

public class PhonologyTools extends Toolbox {

	public PhonologyTools() {
		super(Backgrounds.PHONOLOGYTOOLS);

		ToolButton viewPhonology = new ToolButton("phono.view");
        ToolButton editPhonemes = new ToolButton("phono.edit");
        ToolButton phonotactics = new ToolButton("phono.phonotactics");
        
        
        this.getChildren().addAll(viewPhonology, editPhonemes, phonotactics);
	}

}
