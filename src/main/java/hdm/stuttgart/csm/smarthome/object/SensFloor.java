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
 * SensFloor Class for Interaction with the SensFloor Carpet
 * 1. Initialize with SensFloor address
 * 2. Add specific event listeners (clusters)
 * 3. Open Connection
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
	
	// constructors
	public SensFloor(String protocol, String address, String port, Tile referenceTile, Long capacityThreshold, int socketDelay){
		init(protocol, address, port, referenceTile, capacityThreshold, socketDelay);
	}
	
	public SensFloor(String propertyFile) throws IOException{
		this.prop = readProperties(propertyFile);
		// reading information from properties
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
		
	// connection
	public void openConnection() throws URISyntaxException{
		socket = connector.connect();
		// add event listeners
		socket.on(CLUSTER_EVENT, this.clusterListener);
	}
	
	public void closeConnection(){
		connector.disconnect();
		socket = null;
	}
	
	//logic
	public void addClusterEventHandler(ClusterEventHandler handler){
		// init handler with current path
		handler.setPath(this.clusterListener.getPath());
		// add to list
		clusterHandlers.add(handler);
	}
	
	public void removeClusterEventHandler(ClusterEventHandler handler){
		clusterHandlers.remove(handler);
	}
	
	public void clearClusterEventHandlers(){
		clusterHandlers.clear();
	}
	
	public void resetReferenceTile(){
		setReferenceTile(null);
		this.clusterListener.setReferenceTile(null);
	}
	
	// GETTERS AND SETTERS
	public Properties getProperties() {
		return prop;
	}

	public void setProperties(Properties properties) {
		this.prop = properties;
	}
	
	public void setReferenceTile(Tile tile) {
		this.referenceTile = tile;
	}
	
	public Tile getReferenceTile(){
		this.referenceTile = clusterListener.getReferenceTile();
		return this.referenceTile;
	}
	// internal initialisation of attributes
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
	
	// utilities
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
