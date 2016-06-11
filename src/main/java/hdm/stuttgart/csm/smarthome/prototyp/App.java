package hdm.stuttgart.csm.smarthome.prototyp;

import java.net.URISyntaxException;
import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import hdm.stuttgart.csm.smarthome.object.SensFloor;

/**
 * This is a small sample app to check whether we are able to connect to a socket via the java client
 * and fetch data from a mock-implementation of the SensFloor API. Also we are testing whether
 * the link to the hue is working
 * 
 * @author Smeep	
 *
 */
public class App {
	
	// constants
	public static final String PROTOCOL = "http";
	public static final String LOCALHOST = "localhost";
	public static final String AWS_HOST = "ec2-52-39-157-195.us-west-2.compute.amazonaws.com";
	public static final String SMARTLAB_HOST = "192.168.178.25";
	public static final String PORT = "8000";
	public static final String HUE_URL = "http://biffel:8080/CMD?Sleeping=";
	public static final String PROPERTY_PATH = "sensfloor-config.properties";
	
	public static void main(String[] args) {	
		try {
			// connect to the machine
			SensFloor carpet = new SensFloor(PROPERTY_PATH);
			// initialize and add listeners
			ClusterEventHandler hueHandler = new HueClusterEventHandler(HUE_URL, true);
			carpet.addClusterEventHandler(hueHandler);
			// opening connection
			carpet.openConnection();
			
		} catch (Exception e) {
			System.err.println("Could not connect to socket");
			e.printStackTrace();
		}
	}
}

