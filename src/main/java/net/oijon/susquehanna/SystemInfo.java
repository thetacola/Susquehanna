package net.oijon.susquehanna;

//last edit: 1/24/23 -N3

/**
 * Allows access to version numbers throughout the program.
 * 
 * Edit version information here to have it propogate throughout the entire program.
 * @author alex
 *
 */
public class SystemInfo {

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }
    
    public static String susquehannaVerNum() {
    	return "0.0.3";
    }
    
    public static String susquehannaVerName() {
    	return "Otsego";
    }
    
    public static boolean isSnapshot() {
    	return true;
    }
    
    public static String buildName() {
    	if (isSnapshot()) {
    		return "23w04a";
    	} else {
    		return susquehannaVerName() + ", " + susquehannaVerNum();
    	}
    }
    
    public static String phosysVersion() {
    	return "PHOSYS 0.0.2";
    }
    
    public static String susquehannaVersion() {
    	
    	String returnString = susquehannaVerNum() + " \"" + susquehannaVerName() + "\"";
    	
    	if (isSnapshot()) {
    		returnString += ", build " + buildName() + " ***SNAPSHOT VERSION***";
    	}
    	return returnString;
    }

	public static String getLogVersionNum() {
		return "1.0.1";
	}

	public static String getLogVersion() {
		// TODO Auto-generated method stub
		return "Oijon Utils - v" + getLogVersionNum();
	}

}