package net.oijon.susquehanna.gui.toolboxes;

import net.oijon.susquehanna.gui.components.ToolButton;
import net.oijon.susquehanna.gui.resources.Backgrounds;
import net.oijon.susquehanna.gui.scenes.phonology.EditPhonoPage;
import net.oijon.susquehanna.gui.scenes.phonology.ViewPhonoPage;

public class PhonologyTools extends Toolbox {

	public PhonologyTools() {
		super(Backgrounds.PHONOLOGYTOOLS);

		ToolButton viewPhonology = new ToolButton("View\nPhonology");
        ToolButton editPhonemes = new ToolButton("Edit\nPhonology");
        ToolButton phonotactics = new ToolButton("Phonotactics");
        
        // transfer actions
        viewPhonology.createTransferAction("phono.view");
        editPhonemes.createTransferAction("phono.edit");
        
        this.getChildren().addAll(viewPhonology, editPhonemes, phonotactics);
	}

}
