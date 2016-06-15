package hdm.stuttgart.csm.smarthome.prototyp;

import java.net.URISyntaxException;

import hdm.stuttgart.csm.smarthome.object.SensFloor;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class IngoApp {
	
	// constants
	public static final String LOCALHOST = "localhost";
	public static final String PROTOCOL = "http";
	public static final String PORT_SEPERATOR = ":";
	public static final String PROTOCOL_SEPERATOR = "://";
	public static final String PORT = "8000";
	public static final String RESET_REFERENCE = "reset_reference";
	public static final String PROPERTY_PATH = "sensfloor-config.properties";
	
	// variables
	public static SensFloor carpet;
	
	// event listener
	public static class ReferenceListener implements Emitter.Listener {

		public void call(Object... args) {
			carpet.resetReferenceTile();
		}
	}
	
	public static void main(String[] args) throws URISyntaxException {
		// socket listener for node service
		String socketHost = PROTOCOL+PROTOCOL_SEPERATOR+LOCALHOST+PORT_SEPERATOR+PORT; 
		Socket socky = IO.socket(socketHost);
		socky.on(RESET_REFERENCE, new ReferenceListener());
		socky.connect();
		
		// initialisation for sensfloor
		try {
			// connect to the machine
			carpet = new SensFloor(PROPERTY_PATH);
			// opening connection
			carpet.openConnection();
			// initialize and add listeners
			NodeClusterEventHandler nodeHandler = new NodeClusterEventHandler(socky, true);
			carpet.addClusterEventHandler(nodeHandler);
		} catch (Exception e) {
			System.err.println("Could not connect to sensfloor socket");
			e.printStackTrace();
		}
	}

}
