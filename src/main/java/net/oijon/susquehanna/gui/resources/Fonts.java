package net.oijon.susquehanna.gui.resources;

import java.io.InputStream;

import javafx.scene.text.Font;

public class Fonts {
	
	private static InputStream loadInputStream(String name) {
		return Fonts.class.getResourceAsStream("/font/" + name);
	}
	
	// fonts
	public static Font DENYUT = Font.loadFont(loadInputStream("Denyut.ttf"), 20);
	public static Font OPENSANS = Font.loadFont(loadInputStream("OpenSans-Regular.ttf"), 16);
	public static Font OPENSANS_BOLD = Font.loadFont(loadInputStream("OpenSans-Bold.ttf"), 16);
	public static Font OPENSANS_BOLDITALIC = Font.loadFont(loadInputStream("OpenSans-BoldItalic.ttf"), 16);
	public static Font OPENSANS_EXTRABOLD = Font.loadFont(loadInputStream("OpenSans-ExtraBold.ttf"), 16);
	public static Font OPENSANS_EXTRABOLDITALIC = Font.loadFont(loadInputStream("OpenSans-ExtraBoldItalic.ttf"), 16);
	public static Font OPENSANS_ITALIC = Font.loadFont(loadInputStream("OpenSans-Italic.ttf"), 16);
	public static Font OPENSANS_LIGHT = Font.loadFont(loadInputStream("OpenSans-Light.ttf"), 16);
	public static Font OPENSANS_LIGHTITALIC = Font.loadFont(loadInputStream("OpenSans-LightItalic.ttf"), 16);
	public static Font OPENSANS_MEDIUM = Font.loadFont(loadInputStream("OpenSans-Medium.ttf"), 16);
	public static Font OPENSANS_MEDIUMITALIC = Font.loadFont(loadInputStream("OpenSans-MediumItalic.ttf"), 16);
	public static Font OPENSANS_SEMIBOLD = Font.loadFont(loadInputStream("OpenSans-SemiBold.ttf"), 16);
	public static Font OPENSANS_SEMIBOLDITALIC = Font.loadFont(loadInputStream("OpenSans-SemiBoldItalic.ttf"), 16);

}
