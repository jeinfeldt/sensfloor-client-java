package hdm.stuttgart.csm.smarthome.prototyp;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class IngoApp {
	
	// constants
	public static final String LOCALHOST = "localhost";
	public static final String PROTOCOL = "http";
	private static final String PORT_SEPERATOR = ":";
	private static final String PROTOCOL_SEPERATOR = "://";
	private static final String PORT = "8000";
	private static final String TOGGLE_ALL = "io_toggle_all";
	private static final String TOGGLE_EVENING = "io_toggle_evening";
	private static final String TOGGLE_NIGHT = "io_toggle_night";
	private static final String TOGGLE_MORNING = "io_toggle_morning";
	private static final String SET_BEDTIME = "set_bedtime";
	private static final String SET_WAKEUPTIME = "set_wakeuptime";
	private static final String SET_REFERENCETILE = "set_reference_tile";
	private static final String RESP_SUFFIX = "_resp";
	
	
	// event listener
	public static class ToggleAllListener implements Emitter.Listener {

		public void call(Object... args) {
			System.out.println("Ich habe was bekommen");
		}
		
	}
	
	public static void main(String[] args) throws URISyntaxException {	
		String socketHost = PROTOCOL+PROTOCOL_SEPERATOR+LOCALHOST+PORT_SEPERATOR+PORT; 
		Socket socky = IO.socket(socketHost);
		socky.on("test", new ToggleAllListener());
		socky.connect();
	}

}
