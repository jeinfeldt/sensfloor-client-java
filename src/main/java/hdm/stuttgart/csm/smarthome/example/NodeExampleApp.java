package hdm.stuttgart.csm.smarthome.example;

import java.net.URISyntaxException;

import hdm.stuttgart.csm.smarthome.object.SensFloor;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/***
 * Example application using the sensfloor-client. In this implementation the sensfloor is
 * used to communicate with a node app. 
 * 
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 */
public class NodeExampleApp {
	
	// constants
	public static final String SET_REFERENCE = "set_reference";
	public static final String PROPERTY_PATH = "sensfloor-config.properties";
	public static final String SOCKET_HOST = "http://localhost:3000";
	public static final Long CAP_THRESHOLD = 90l;
	
	// variables
	public static SensFloor carpet;
	public static Socket socky;
	
	// event listener for communication with node app
	public static class ReferenceListener implements Emitter.Listener {
		public void call(Object... args) {
			carpet.resetReferenceTile();
		}
	}
	
	public static void main(String[] args) throws URISyntaxException {
		// socket listener for node service
		socky = IO.socket(SOCKET_HOST);
		socky.on(SET_REFERENCE, new ReferenceListener());
		socky.connect();
		
		// initialisation for sensfloor
		try {
			// connect to the machine
			carpet = new SensFloor(PROPERTY_PATH);
			// opening connection
			carpet.openConnection();
			// initialise and add listeners
			NodeClusterEventHandler nodeHandler = new NodeClusterEventHandler(socky, carpet.getReferenceTile(), CAP_THRESHOLD);
			carpet.addClusterEventHandler(nodeHandler);
		} catch (Exception e) {
			System.err.println("Could not connect to sensfloor socket");
			e.printStackTrace();
		}
	}

}
