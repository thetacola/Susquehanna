package net.oijon.susquehanna;

//last edit: 1/9/24 -N3

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
    	return "0.1.0";
    }
    
    public static String susquehannaVerName() {
    	return "Cooperstown";
    }
    
    public static boolean isSnapshot() {
    	return true;
    }
    
    public static String buildName() {
    	if (isSnapshot()) {
    		return "24w01b";
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
        
    
    public static String susquehannaVersion() {
    	
    	String returnString = susquehannaVerNum() + " \"" + susquehannaVerName() + "\"";
    	
    	if (isSnapshot()) {
    		returnString += ", build " + buildName() + " ***SNAPSHOT VERSION***";
    	}
    	return returnString;
    }

}