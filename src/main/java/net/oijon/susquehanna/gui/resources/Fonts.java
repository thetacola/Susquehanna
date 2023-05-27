package net.oijon.susquehanna.gui.resources;

import java.io.InputStream;

import javafx.scene.text.Font;

public class Fonts {
	
	// input streams
	private static InputStream denyutIS = Fonts.class.getResourceAsStream("/font/Denyut.ttf");
	private static InputStream opensansIS = Fonts.class.getResourceAsStream("/font/OpenSans-Regular.ttf");
	private static InputStream opensansBoldIS = Fonts.class.getResourceAsStream("/font/OpenSans-Bold.ttf");
	private static InputStream opensansBoldItalicIS = Fonts.class.getResourceAsStream("/font/OpenSans-BoldItalic.ttf");
	private static InputStream opensansExtraBoldIS = Fonts.class.getResourceAsStream("/font/OpenSans-ExtraBold.ttf");
	private static InputStream opensansExtraBoldItalicIS = Fonts.class.getResourceAsStream("/font/OpenSans-ExtraBoldItalic.ttf");
	private static InputStream opensansItalicIS = Fonts.class.getResourceAsStream("/font/OpenSans-Italic.ttf");
	private static InputStream opensansLightIS = Fonts.class.getResourceAsStream("/font/OpenSans-Light.ttf");
	private static InputStream opensansLightItalicIS = Fonts.class.getResourceAsStream("/font/OpenSans-LightItalic.ttf");
	private static InputStream opensansMediumIS = Fonts.class.getResourceAsStream("/font/OpenSans-Medium.ttf");
	private static InputStream opensansMediumItalicIS = Fonts.class.getResourceAsStream("/font/OpenSans-MediumItalic.ttf");
	private static InputStream opensansSemiBoldIS = Fonts.class.getResourceAsStream("/font/OpenSans-SemiBold.ttf");
	private static InputStream opensansSemiBoldItalicIS = Fonts.class.getResourceAsStream("/font/OpenSans-SemiBoldItalic.ttf");
	// fonts
	public static Font DENYUT = Font.loadFont(denyutIS, 20);
	public static Font OPENSANS = Font.loadFont(opensansIS, 16);
	public static Font OPENSANS_BOLD = Font.loadFont(opensansBoldIS, 16);
	public static Font OPENSANS_BOLDITALIC = Font.loadFont(opensansBoldItalicIS, 16);
	public static Font OPENSANS_EXTRABOLD = Font.loadFont(opensansExtraBoldIS, 16);
	public static Font OPENSANS_EXTRABOLDITALIC = Font.loadFont(opensansExtraBoldItalicIS, 16);
	public static Font OPENSANS_ITALIC = Font.loadFont(opensansItalicIS, 16);
	public static Font OPENSANS_LIGHT = Font.loadFont(opensansLightIS, 16);
	public static Font OPENSANS_LIGHTITALIC = Font.loadFont(opensansLightItalicIS, 16);
	public static Font OPENSANS_MEDIUM = Font.loadFont(opensansMediumIS, 16);
	public static Font OPENSANS_MEDIUMITALIC = Font.loadFont(opensansMediumItalicIS, 16);
	public static Font OPENSANS_SEMIBOLD = Font.loadFont(opensansSemiBoldIS, 16);
	public static Font OPENSANS_SEMIBOLDITALIC = Font.loadFont(opensansSemiBoldItalicIS, 16);

}
