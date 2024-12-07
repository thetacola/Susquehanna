package net.oijon.susquehanna;

/**
 * Allows access to version numbers throughout the program.
 * 
 * Edit version information here to have it propogate throughout the entire program.
 * @author alex
 *
 */
public final class SystemInfo {

	private SystemInfo() {
		
	}
	
    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }
    
    public static String susquehannaVerNum() {
    	return "0.1.1";
    }
    
    public static String susquehannaVerName() {
    	return "Index";
    }
    
    public static boolean isSnapshot() {
    	return false;
    }
    
    public static String buildName() {
    	if (isSnapshot()) {
    		return "24w36c";
    	} else {
    		return susquehannaVerName() + ", " + susquehannaVerNum();
    	}
    }
    
    public static String olingVersion() {
    	return net.oijon.oling.info.Info.getVersion();
    }
    
    public static String olingVersionNum() {
    	return net.oijon.oling.info.Info.getVersionNum();
    }
    
    public static String ologVersion() {
    	return net.oijon.olog.info.Info.getVersion();
    }
    
    public static String ologVersionNum() {
    	return net.oijon.olog.info.Info.getVersionNum();
    }
        
    
    public static String susquehannaVersion() {
    	
    	String returnString = susquehannaVerNum() + " \"" + susquehannaVerName() + "\"";
    	
    	if (isSnapshot()) {
    		returnString += ", build " + buildName() + " ***SNAPSHOT VERSION***";
    	}
    	return returnString;
    }

}