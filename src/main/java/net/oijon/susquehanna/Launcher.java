package net.oijon.susquehanna;

/**
 * Launches the application. JavaFX does not like launching from App for some reason.
 * @author alex
 *
 */
public final class Launcher {
	
	private Launcher() {
		// prevents instantiation
	}
	
	public static void main(String[] args) {
		App.main(args);
	}
}
