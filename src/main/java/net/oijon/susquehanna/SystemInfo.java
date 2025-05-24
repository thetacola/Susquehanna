package net.oijon.susquehanna;

/**
 * Allows access to version numbers throughout the program.
 * 
 * Edit version information here to have it propagate throughout the entire program.
 * @author alex
 *
 */
public final class SystemInfo {

	/**
	 * Prevents instantiation
	 */
	private SystemInfo() {
		
	}
	
	/**
	 * Gets the version of Java run by the program
	 * @return The version of Java used
	 */
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Gets the version of JavaFX run by the program
     * @return The version of JavaFX used
     */
    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }
    
    /**
     * Gets the version number of this program.
     * Make sure to edit this before each release!
     * @return The version number of this program
     */
    public static String susquehannaVerNum() {
    	return "0.2.0";
    }
    
    /**
     * Gets the human-friendly name of the version
     * Make sure to edit this before each release!
     * @return The human-friendly name of the version
     */
    public static String susquehannaVerName() {
    	return "Cliffside";
    }
    
    /**
     * Marks if this build is a development build.
     * Make sure to edit this before each release!
     * @return True if development build, false otherwise
     */
    public static boolean isSnapshot() {
    	return true;
    }
    
    /**
     * Gets the build name of the program based off snapshot status and ver num
     * @return The build name
     */
    public static String buildName() {
    	if (isSnapshot()) {
    		return "25w21b";
    	} else {
    		return susquehannaVerName() + ", " + susquehannaVerNum();
    	}
    }
    
    /**
     * Gets the version of OLing run by the program
     * @return The version of OLing used
     */
    public static String olingVersion() {
    	return net.oijon.oling.info.Info.getVersion();
    }
    
    /**
     * Gets the version number of OLing run by the program
     * @return The version number of OLing used
     */
    public static String olingVersionNum() {
    	return net.oijon.oling.info.Info.getVersionNum();
    }
    
    /**
     * Gets the version of OLog run by the program
     * @return The version of OLog used
     */
    public static String ologVersion() {
    	return net.oijon.olog.info.Info.getVersion();
    }
    
    /**
     * Gets the version number of OLog run by the program
     * @return The version of OLog used
     */
    public static String ologVersionNum() {
    	return net.oijon.olog.info.Info.getVersionNum();
    }
        
    /**
     * Gets the full version string of this program
     * @return The full version string of this program
     */
    public static String susquehannaVersion() {
    	
    	String returnString = susquehannaVerNum() + " \"" + susquehannaVerName() + "\"";
    	
    	if (isSnapshot()) {
    		returnString += ", build " + buildName() + " ***SNAPSHOT VERSION***";
    	}
    	return returnString;
    }

}