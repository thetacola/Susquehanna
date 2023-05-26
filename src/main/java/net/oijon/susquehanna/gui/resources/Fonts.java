package net.oijon.susquehanna.gui.resources;

import java.io.InputStream;

import javafx.scene.text.Font;

public class Fonts {
	
	// input streams
	private static InputStream denyutIS = Fonts.class.getResourceAsStream("/font/Denyut.ttf");
	private static InputStream opensansIS = Fonts.class.getResourceAsStream("/font/OpenSans-Regular.ttf");
	
	// fonts
	public static Font DENYUT = Font.loadFont(denyutIS, 20);
	public static Font OPENSANS = Font.loadFont(opensansIS, 16);

}
