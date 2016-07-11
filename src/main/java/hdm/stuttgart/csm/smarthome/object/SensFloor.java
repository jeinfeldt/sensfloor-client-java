package hdm.stuttgart.csm.smarthome.object;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hdm.stuttgart.csm.smarthome.connect.ISensFloorConnector;
import hdm.stuttgart.csm.smarthome.connect.SensFloorConnector;
import hdm.stuttgart.csm.smarthome.event.ClusterListener;
import hdm.stuttgart.csm.smarthome.event.ClusterEventHandler;
import io.socket.client.Socket;

/**
 * Main class for interaction with a sensfloor. Handles
 * the connection process and the orchestration of the configration
 * and event handling.
 * 
 * @author Joerg Einfeldt, Thomas Derleth, Merle Hiort, Marc Stauffer
 */
public class SensFloor {
	
	// constants
	private final String PROTOCOL_KEY = "protocol";
	private final String HOST_KEY = "host";
	private final String PORT_KEY = "port";
	private final String REFERENCE_X_KEY = "referenceX";
	private final String REFERENCE_Y_KEY = "referenceY";
	private final String SOCKET_DELAY_KEY = "socketDelay";
	private final String CAPACITY_THRESHOLD_KEY = "capacityThreshold";
	private final String CLUSTER_EVENT = "cluster";
	
	// attributes
	private Socket socket;
	private ISensFloorConnector connector;
	private List<ClusterEventHandler> clusterHandlers;
	private ClusterListener clusterListener;
	private Long capacityThreshold;
	private Properties prop;
	private Tile referenceTile = null;
	private int socketDelay;
	
	/**
	 * Initialises sensfloor with given values. Protocol, address, port are binding.
	 * @param protocol Connection protocoll for sensfloor (http)
	 * @param address Connection address for sensfloor (IP)
	 * @param port Connection port for sensfloor (8000)
	 * @param referenceTile Reference tile for sensfloor interaction (can be null)
	 * @param capacityThreshold Threshold used for reseting reference tile (can be null)
	 * @param socketDelay Delay for sensfloor interaction (0 for no delay)
	 */
	public SensFloor(String protocol, String address, String port, Tile referenceTile, Long capacityThreshold, int socketDelay){
		init(protocol, address, port, referenceTile, capacityThreshold, socketDelay);
	}
	
	/**
	 * Initialises sensfloor with values read from property file.
	 * @param propertyFile File to read
	 * @throws IOException Thrown if property file cannot be read
	 */
	public SensFloor(String propertyFile) throws IOException{
		// reading information from properties
		this.prop = readProperties(propertyFile);
		String protocol = prop.getProperty(PROTOCOL_KEY);
		String address = prop.getProperty(HOST_KEY);
		String port = prop.getProperty(PORT_KEY);
		int socketDelay = Integer.parseInt(prop.getProperty(SOCKET_DELAY_KEY));
		
		// optional properties
		Tile refTile = null;
		Long capacityThreshold = null;
		
		// capacity
		if(prop.getProperty(CAPACITY_THRESHOLD_KEY) != null){
			capacityThreshold = Long.parseLong(prop.getProperty(CAPACITY_THRESHOLD_KEY));			
		}
		// reference  tile
		if(prop.getProperty(REFERENCE_X_KEY)!=null && prop.getProperty(REFERENCE_Y_KEY)!=null){
			double refX = Double.parseDouble(prop.getProperty(REFERENCE_X_KEY));
			double refY = Double.parseDouble(prop.getProperty(REFERENCE_Y_KEY));
			refTile = new Tile(refX, refY, 0);
		}
		init(protocol, address, port, refTile, capacityThreshold, socketDelay);
	}
		
	/**
	 * Open connection to sensfloor, no communication is executed until this method is called. 
	 * @throws URISyntaxException
	 */
	public void openConnection() throws URISyntaxException{
		socket = connector.connect();
		// add event listeners
		socket.on(CLUSTER_EVENT, this.clusterListener);
	}
	
	/**
	 * Closes connection to sensfloor.
	 */
	public void closeConnection(){
		connector.disconnect();
		socket = null;
	}
	
	/**
	 * Adds handler for event "cluster" to event scheduling
	 * @param handler Handler to be added
	 */
	public void addClusterEventHandler(ClusterEventHandler handler){
		// init handler with current path
		handler.setPath(this.clusterListener.getPath());
		// add to list
		clusterHandlers.add(handler);
	}
	
	/**
	 * Removes handler for event "cluster" from event scheduling 
	 * @param handler
	 */
	public void removeClusterEventHandler(ClusterEventHandler handler){
		clusterHandlers.remove(handler);
	}
	
	/**
	 * Removes all handlers for event "cluster"
	 */
	public void clearClusterEventHandlers(){
		clusterHandlers.clear();
	}
	
	/**
	 * Set reference tile to null for event scheduling
	 */
	public void resetReferenceTile(){
		this.referenceTile = null;
		this.clusterListener.setReferenceTile(null);
	}
	
	/**
	 * 
	 * Get currently used reference tile
	 * @return Reference tile
	 */
	public Tile getReferenceTile(){
		this.referenceTile = clusterListener.getReferenceTile();
		return this.referenceTile;
	}
	
	// initialises all attributes
	private void init(String protocol, String address, String port, Tile referenceTile, Long capacityThreshold, int socketDelay){
		// guard clause
		if(protocol == null || address == null || port == null ){
			throw new IllegalArgumentException("SensFloor must at least be provided with: protocol, address and port");
		}
		this.connector = new SensFloorConnector(protocol, address, port);
		this.referenceTile = referenceTile;
		this.capacityThreshold = capacityThreshold;
		this.socketDelay = socketDelay;
		this.clusterHandlers = new ArrayList<ClusterEventHandler>();
		this.clusterListener = new ClusterListener(this.clusterHandlers, this.referenceTile, this.capacityThreshold, this.socketDelay);
	}
	
	// reads properties from file
	private Properties readProperties(String propertyFile) throws IOException{
		Properties prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propertyFile + "' not found");
		}
		return prop;
	}
}
